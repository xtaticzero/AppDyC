/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.DictaminadorService;
import mx.gob.sat.siat.dyc.casocomp.service.AprobarResolucionService;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


/**
 * Clase ManagedBean para vista aprobar resoluciones embebida en dictaminar aviso, caso compensación.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date aGOSTO 18, 2014
 * */
@ManagedBean(name = "aprobarResolucionComp")
@ViewScoped
public class PAprobarResolucionMB {
    private static final Logger LOG = Logger.getLogger(PAprobarResolucionMB.class);
    private static final String NO_REDIRECCIONAMIENTO = "";

    @ManagedProperty("#{serviceAprobarResolComp}")
    private AprobarResolucionService service;
    
    @ManagedProperty("#{dictaminadorService}")
    private DictaminadorService dictaminadorService;
    
    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;

    private String observaciones;
    private Integer claveResolucion;

    private Boolean mostrarDlgMensaje;
    private Boolean mostrarDlgSuperiores;
    private Boolean pidioEscalar = false;
    private List<ItemComboBean> superiores;
    private Integer idSuperior;
    private String dlgTitle;
    private String mensaje;
    
    private String nombreDictaminador = "";
    private String numEmpDictaminador = "";

    private DictaminadorVO dictaminadorSeleccionado = null;
    private boolean confirmacionReasignacion = Boolean.FALSE;
    
    private List<DictaminadorVO> listaDictaminadoresReasignacion = new ArrayList<DictaminadorVO>();
    
    private static final String MENSAJE_ERROR_VALIDACION_JEFE_ESCALAMIENTO = "El aprobador seleccionado está Inactivo o causo baja en AGS, seleccione otro aprobador.";
    private static final String MENSAJE_ERROR_REALIZAR_RESOLUCION = "Ocurrió un error al registrar la resolución.";

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;

    public PAprobarResolucionMB() {
        mostrarDlgMensaje = Boolean.FALSE;
    }
    
    public String aceptoEscalar(){
        pidioEscalar = Boolean.TRUE;
        LOG.info("acepta");
        return determinarResolucion();
    }

    public String noAceptoEscalar(){
        pidioEscalar = false;
        LOG.info("no acepta");
        return determinarResolucion();
    }
    
    public String preguntaSiEscala (){
        mbSession.setRealizarReasignacion( false );
        
        if ( accionNoAprobarResolucion() ){
            muestraOpcionReasignarDictaminador();

            return NO_REDIRECCIONAMIENTO;
        } else {
            return determinaEscalamiento();
        }
    }
    
    private boolean accionNoAprobarResolucion (){
        return claveResolucion == ConstantesDyCNumerico.VALOR_2;
    }
    
    private void muestraOpcionReasignarDictaminador (){
        cargaInformacionDictaminador();
        ocultarComentariosResolucion();
        muestraControlesReasignacionDictaminador();
        ocultarControlesEscalamiento();
    }
    
    private void cargaInformacionDictaminador (){
        
        DictaminadorVO dictaminador = dictaminadorService.obtenerDictaminador( mbSession.getNumDictaminador() );
        if ( dictaminador != null ){
            nombreDictaminador = dictaminador.getNombreCompleto();
            setNumeroEmpleadoDictaminador( mbSession.getNumDictaminador() );
        }
    }
    
    private void ocultarComentariosResolucion (){
        RequestContext.getCurrentInstance().execute( "dlgAResolucion.hide();" );
    }
    
    private void muestraControlesReasignacionDictaminador (){
        RequestContext.getCurrentInstance().execute( "wvDlgAReasigDictaminador.show();" );
    }
    
    private void ocultarControlesEscalamiento () {
        RequestContext.getCurrentInstance().execute( "dlgAEscalar.hide();" );
    }
    
    private String determinaEscalamiento (){
        AccesoUsr accesoUsrL = mbSession.getAccesoUsr();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put( "claveResolucion"     , claveResolucion );
        params.put( "claveADM"            , mbSession.getClaveAdm() );
        params.put( "observaciones"       , observaciones );
        params.put( "numControl"          , mbSession.getNumControl() );
        params.put( "numEmpleado"         , mbSession.getNumEmpleado() );
        params.put( "nombreEmpleado"      , mbSession.getNombreEmpleado() );
        params.put( "cargoOrganizacional" , mbSession.getCargoOrganizacional() );
        params.put( "tipoAdministracion"  , mbSession.getTipoUnidadAdmva() );
        params.put( "centroCosto"         , accesoUsrL.getCentroCosto() );
        params.put( "dictaminador"        , mbSession.getDictaminador() );
        params.put( "numDictaminador"     , mbSession.getNumDictaminador() );
        params.put( "nivelAprobador"      , mbSession.getNivelAprobador() );
        params.put( "realizarAsignacion"  , (Boolean) mbSession.getRealizarReasignacion() );
        

        if ( service.puedeEscalar( params ) ){
            muestraOpcionEscalamiento();

            return NO_REDIRECCIONAMIENTO;
        }

        return determinarResolucion();
    }
    
    private void muestraOpcionEscalamiento (){
        ocultarComentariosResolucion();
        ocultarControlesReasignacionDictaminador();
        mostrarControlesEscalamiento();
    }
    
    private void ocultarControlesReasignacionDictaminador (){
        RequestContext.getCurrentInstance().execute( "wvDlgAReasigDictaminador.hide();" );
    }
    
    private void mostrarListadoReasignacionDictaminador (){
        RequestContext.getCurrentInstance().execute( "wvDlgReasignacionDictaminador.show();" );
    }
    
    private void mostrarControlesEscalamiento () {
        RequestContext.getCurrentInstance().execute( "dlgAEscalar.show();" );
    }
    
    public String determinarResolucion() {
        AccesoUsr accesoUsrL = mbSession.getAccesoUsr();

        LOG.info("PAprobarResolucionMB INICIO determinarResolucion \n claveResolucion ->" + claveResolucion +
                 "<- \n observaciones ->" + observaciones + "<-");

        mbSession.setMensaje(null);
        mbSession.setSalida( NO_REDIRECCIONAMIENTO );

        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put( "claveResolucion"     , claveResolucion);
        params.put( "claveADM"            , mbSession.getClaveAdm());
        params.put( "observaciones"       , observaciones);
        params.put( "numControl"          , mbSession.getNumControl());
        params.put( "numEmpleado"         , mbSession.getNumEmpleado());
        params.put( "nombreEmpleado"      , mbSession.getNombreEmpleado());
        params.put( "cargoOrganizacional" , mbSession.getCargoOrganizacional());
        params.put( "tipoAdministracion"  , mbSession.getTipoUnidadAdmva());
        params.put( "centroCosto"         , accesoUsrL.getCentroCosto());
        
        params.put( "dictaminador"        , mbSession.getDictaminador());
        params.put( "numDictaminador"     , mbSession.getNumDictaminador());
        params.put( "nivelAprobador"      , mbSession.getNivelAprobador());
        
        params.put( "realizarAsignacion"  , (Boolean) mbSession.getRealizarReasignacion() );
        
        if(dictaminadorSeleccionado!=null){
        params.put( "esCambioEmpleado", (Boolean) true );   
        params.put( "nombreAprobReasignado", dictaminadorSeleccionado.getNombreCompleto());
        params.put( "numAprobReasignado"   , dictaminadorSeleccionado.getNumEmpleado());
        }else{
            params.put( "esCambioEmpleado", (Boolean) false );   
        }
        Map<String, Object> respuesta;
        
        try {
            params.put("seEscala", pidioEscalar);
            respuesta = service.determinarResolucion(params);
            LOG.info("respuesta ->" + respuesta + "<-");
        } catch ( SIATException e ){

            LOG.error("Ocurrió un ERROR ->" + e.getMessage());
            JSFUtils.messageGlobal( MENSAJE_ERROR_REALIZAR_RESOLUCION, FacesMessage.SEVERITY_ERROR );
            mbSession.setMensaje( MENSAJE_ERROR_REALIZAR_RESOLUCION );
            mbSession.setSalida( "compensacionRegistrada" );

            return mbSession.getSalida();
        }

        if ((Boolean)respuesta.get("success")) {
            if ((Boolean)respuesta.get("seCompletoResolucion")) {
                /** La determinación de la resolución se realizo satisfactoriamente  */
                mbSession.setMensaje( respuesta.get("mensaje").toString() );
                mbSession.setSalida("compensacionRegistrada");
                ocultarComentariosResolucion();
            } else {
                LOG.debug("NO se completo la Resolucion");
                superiores = (List<ItemComboBean>)respuesta.get("superiores");
                mostrarDlgSuperiores = Boolean.TRUE;
            }
        } else {
            /** mbSession.setMensaje( (String)respuesta.get( "mensaje" ) ); **/
            JSFUtils.messageGlobal( (String)respuesta.get( "mensaje" ), FacesMessage.SEVERITY_ERROR);
        }

        return mbSession.getSalida();
    }

    public String registrarSuperiorAprobacion() {
        LOG.debug("\n\nINICIO registrarSuperiorAprobacion || idSuperior ->" + idSuperior + "<-");
        
        if ( !jefeEscalamientoValido() /*|| true */){
            muestraMensajeJefeEscalamientoNoValido();
            
            return NO_REDIRECCIONAMIENTO;
        }
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("numEmpleadoAprob", idSuperior);
        params.put("numControl", mbSession.getNumControl());
        Map<String, Object> respuesta;
        
        LOG.info( "resolucion 2" );

        try {

            respuesta = service.escalarAprobacion( params );

            if ( (Boolean) respuesta.get( "success" ) ){
                mensaje = "La aprobación de la resolución se escaló satisfactoriamente";
            } else {
                mensaje = "Ocurrió un error al escalar la aprobación";
            }

            mbSession.setMensaje( mensaje );
            mbSession.setSalida( "compensacionRegistrada" );

        } catch ( SIATException e ){
            LOG.error("###\n\n" + e.getMessage());
        }

        /**mostrarDlgMensaje = Boolean.TRUE;*/
        return mbSession.getSalida();
    }
    
    private boolean jefeEscalamientoValido (){
        try {
            return validacionAgs.getEstatusEmpleadoActivo( idSuperior );

        } catch ( SIATException ex ){
            LOG.info( 
                "Error al validar al jefe : " + idSuperior + 
                " " + ex.getDescripcion()
            );
        }
        
        return false;
    }
    
    private void muestraMensajeJefeEscalamientoNoValido (){
        FacesMessage mensajeProceso = new FacesMessage( FacesMessage.SEVERITY_ERROR, MENSAJE_ERROR_VALIDACION_JEFE_ESCALAMIENTO, "" );
        FacesContext.getCurrentInstance().addMessage( null, mensajeProceso );
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getClaveResolucion() {
        return claveResolucion;
    }

    public void setClaveResolucion(Integer claveResolucion) {
        this.claveResolucion = claveResolucion;
    }

    public AprobarResolucionService getService() {
        return service;
    }

    public void setService(AprobarResolucionService service) {
        this.service = service;
    }

    public ManagerSesionAdmCasosCompMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionAdmCasosCompMB mbSession) {
        this.mbSession = mbSession;
    }

    public Boolean getMostrarDlgMensaje() {
        return mostrarDlgMensaje;
    }

    public void setMostrarDlgMensaje(Boolean mostrarDlgMensaje) {
        this.mostrarDlgMensaje = mostrarDlgMensaje;
    }

    public Boolean getMostrarDlgSuperiores() {
        return mostrarDlgSuperiores;
    }

    public void setMostrarDlgSuperiores(Boolean mostrarDlgSuperiores) {
        this.mostrarDlgSuperiores = mostrarDlgSuperiores;
    }

    public List<ItemComboBean> getSuperiores() {
        return superiores;
    }

    public void setSuperiores(List<ItemComboBean> superiores) {
        this.superiores = superiores;
    }

    public Integer getIdSuperior() {
        return idSuperior;
    }

    public void setIdSuperior(Integer idSuperior) {
        this.idSuperior = idSuperior;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setDlgTitle(String dlgTitle) {
        this.dlgTitle = dlgTitle;
    }

    public String getDlgTitle() {
        return dlgTitle;
    }

    public void setPidioEscalar(Boolean pidioEscalar) {
        this.pidioEscalar = pidioEscalar;
    }

    public Boolean getPidioEscalar() {
        return pidioEscalar;
    }
    
    public String getNombreDictaminador (){
        return nombreDictaminador;
    }
    
    public void setNombreDictaminador ( String nombreDictaminador ){
        this.nombreDictaminador = nombreDictaminador;
    }

    public String getNumEmpDictaminador (){
        return numEmpDictaminador;
    }
    
    public void setNumeroEmpleadoDictaminador ( Integer numeroEmpleadoDictaminador ){
        this.numEmpDictaminador = String.valueOf( numeroEmpleadoDictaminador );
    }
    
    public void setDictaminadorService ( DictaminadorService dictaminadorService ){
        this.dictaminadorService = dictaminadorService;
    }
    
    public DictaminadorService getDictaminadorService (){
        return dictaminadorService;
    }
    
    public void mostrarListaDictaminadoresReasignacion (){
        LOG.info( "mostrarListaDictaminadoresReasignacion " );
        cargarListaDictaminadores();
        ocultarControlesReasignacionDictaminador();
        mostrarListadoReasignacionDictaminador();        
    }
    
    private void cargarListaDictaminadores (){
        
        DictaminadorVO dictaminador = new DictaminadorVO();
        dictaminador.setClaveAdm( mbSession.getClaveAdm() );
        
        List<DictaminadorVO> registros;
        
        try {
             registros = dictaminadorService.obtenerDictaminadoresActivos( dictaminador );
        } catch ( SQLException error ){
            registros = new ArrayList<DictaminadorVO>();
            LOG.info( "No se pudieron obtener dictaminadores con la clvAdm : " + mbSession.getClaveAdm() + 
                        " " + error.getMessage() );
        }

        listaDictaminadoresReasignacion = registros;
    }
            
    public String omitirReasignarDictaminador (){
        mbSession.setRealizarReasignacion( false );

        return determinarResolucion();
    }
    
    public String reasignarDictaminador (){
        LOG.info( "reasignarDictaminador" );
        if ( dictaminadorReasignacionValido() /*&& false */){
            ocultarListadoReasignacionDictaminador();
            mbSession.setRealizarReasignacion( true );

            return determinarResolucion();
        }

        muestraMensajeDictaminadorNoValido();

        return NO_REDIRECCIONAMIENTO;
    }
    
    private void ocultarListadoReasignacionDictaminador (){
        RequestContext.getCurrentInstance().execute( "wvDlgReasignacionDictaminador.hide();" );
    }
    
    private boolean dictaminadorReasignacionValido (){
        try {
            return validacionAgs.getEstatusEmpleadoActivo( dictaminadorSeleccionado.getNumEmpleado() );

        } catch ( SIATException ex ){
            LOG.info( 
                "Error al validar el empleado : " + dictaminadorSeleccionado.getNumEmpleado() + 
                " " + ex.getDescripcion()
            );
        }
        
        return false;
    }
    
    private void muestraMensajeDictaminadorNoValido (){
        FacesMessage mensajeProceso = new FacesMessage( FacesMessage.SEVERITY_ERROR, getDetalleErrorDictaminador(), "" );
        FacesContext.getCurrentInstance().addMessage( null, mensajeProceso );
    }
    
    private String getDetalleErrorDictaminador (){
        if ( dictaminadorSeleccionado != null ){
            return "El dictaminador seleccionado está Inactivo o causo baja en AGS, seleccione otro aprobador.";
        }
        
        return "Dictaminador no seleccionado.";
    }
    
    public String cancelarReasignarDictaminador (){
        confirmacionReasignacion = false;
        dictaminadorSeleccionado = null;
        
        return preguntaSiEscala();
    }
    
    
    public void mostrarConfirmacionReasignarDictaminador (){
        LOG.info( "mostrarConfirmacionReasignarDictaminador" );
        mostrarListadoReasignacionDictaminador();
    }
    
    public void cRd (){
        LOG.info( "confirmacion reasignación del dictaminador" );
    }
    
    public void setDictaminadorSeleccionado ( DictaminadorVO dictaminadorSeleccionado ){
        this.dictaminadorSeleccionado = dictaminadorSeleccionado;
    }
    
    public DictaminadorVO getDictaminadorSeleccionado (){
        return dictaminadorSeleccionado;
    }
    
    public void onRowSelect ( SelectEvent evento ){
        recuperaRegistroSeleccionado( evento );
        habilitaConfirmacionReasignacion();
        LOG.info( "registro seleccionado :" + dictaminadorSeleccionado.getNumEmpleado() );
    }
    
    private void recuperaRegistroSeleccionado ( SelectEvent evento ) {
        dictaminadorSeleccionado = getRegistroSeleccionado( evento );
    }
    
    private DictaminadorVO getRegistroSeleccionado ( SelectEvent evento ){
        return (DictaminadorVO) evento.getObject();
    }
    
    private void habilitaConfirmacionReasignacion (){
        confirmacionReasignacion = Boolean.TRUE;
    }
    
    public boolean isConfirmacionReasignacion (){
        return confirmacionReasignacion;
    }
    
    public List<DictaminadorVO> getListaDictaminadoresReasignacion (){
        return listaDictaminadoresReasignacion;
    }

    public void setListaDictaminadoresReasignacion ( List<DictaminadorVO> listaDictaminadoresReasignacion ){
        this.listaDictaminadoresReasignacion = listaDictaminadoresReasignacion;
    }
    
    public void setValidacionAgs( SatAgsEmpleadosMVService validacionAgs ){
        this.validacionAgs = validacionAgs;
    }
    
    public SatAgsEmpleadosMVService getValidacionAgs (){
        return validacionAgs;
    }

}
