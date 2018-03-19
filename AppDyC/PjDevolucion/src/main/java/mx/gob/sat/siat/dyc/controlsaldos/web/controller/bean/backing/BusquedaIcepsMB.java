package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.io.IOException;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.siat.dyc.controlsaldos.service.BusquedaIcepsService;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.model.GridBusquedaIcepsDataModel;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridBusquedaIceps;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.trabajo.web.controller.bean.backing.CatalogosEstaticosMB;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;

import org.apache.log4j.Logger;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;


/**
 * Huetzin Cruz Lozano
 *
 * */
@ManagedBean(name = "mbBusquedaIceps")
@SessionScoped
public class BusquedaIcepsMB
{
    private static final Logger LOG = Logger.getLogger(BusquedaIcepsMB.class);

    @ManagedProperty(value = "#{dyccMensajeUsrService}")
    private DyccMensajeUsrService serviceMensajes;

    @ManagedProperty(value = "#{serviceBusquedaIceps}")
    private BusquedaIcepsService service;

    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;

    @ManagedProperty(value = "#{mbCatalogosEstaticos}")
    private CatalogosEstaticosMB mbCatalogos;

    @ManagedProperty(value = "#{mbDlgDetalleIcep}")
    private DlgDetalleIcepMB mbDlgDetalleIcep;

    @ManagedProperty(value = "#{mbPermisosAjustes}")
    private PermisosAjusteMB mbPermisosAjustes;

    @ManagedProperty(value = "#{mbBitacoraAjustes}")
    private BitacoraAjustesMB mbBitacora;

    private List<ItemComboBean> conceptos;
    private List<ItemComboBean> periodos;

    private Integer optRadio;

    private String rfc;
    private String rfcCont;
    private Integer idImpuesto;
    private Integer idConcepto;
    private String idTipoPeriodo;
    private Integer idPeriodo;
    private Integer ejercicio;
    private Integer tipoSaldo;
    private String numControl;
    private Integer idSaldoIcep;
    
    private boolean registraIcep = false;
    private String expRFC = ConstantesDyC.EXP_REG_RFC;

    private GridBusquedaIcepsDataModel icepsEncontrados;
    private FilaGridBusquedaIceps icepSeleccionado;
    
    private Integer fieldsetActivo;
    private String claveBusqueda;
    private String resultadoBusqueda;
        
    @PostConstruct
    public void init()
    {
        fieldsetActivo = 1;        
    }

    public String buscar2()
    {
        claveBusqueda = claveBusqueda.trim().toUpperCase();
        //requiredMessage="Se debe introducir el parámetro de búsqueda"
        LOG.debug("claveBusqueda ->" + claveBusqueda + "<-");

        if("".equals(claveBusqueda)){
            LOG.debug("el parametro de busqueda esta vacía.");
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage("", "Debe introducir el parámetro de búsqueda"));

            return null;
        }

        if("CALC".equals(claveBusqueda)){
            //TODO: Validar porque cuando llega a desglose.jsf el pe:inputNumber no funciona
            LOG.debug(" ir a calculadora ;");
        }

        if("EXTRACTORINFO".equals(claveBusqueda)){
            return "extractorInfo";
        }

        try {
            Map<String, Object> respuesta = service.buscarIcepsXpalabraclave(claveBusqueda);
            
            if((Boolean)respuesta.get("excedeInt")){
                FacesContext context = FacesContext.getCurrentInstance();  
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Parámetro no válido -", "el id ICEP excede el tamaño permitido, favor de verificar"));
                return null;
            }

            List<FilaGridBusquedaIceps> filas = (List<FilaGridBusquedaIceps>)respuesta.get("filas");
            agregaId(filas);
            Boolean busco = (Boolean)respuesta.get("busco");

            LOG.debug("icepsEncontrados en ManageBean ->" + filas.size() + "<-");
            icepsEncontrados = new GridBusquedaIcepsDataModel(filas);

            if (busco)
            {
                if (filas.isEmpty())
                {
                    FacesContext context = FacesContext.getCurrentInstance();  
                    context.addMessage(null, new FacesMessage("Sin resultados -", "ningún ICEP en la base de datos cumple con el criterio de búsqueda"));
                }
                if (filas.size() == 1){
                    resultadoBusqueda =
                            "Se encontró " + filas.size() + " registro para el parámetro de búsqueda '" + claveBusqueda + "'";
                }else{
                    resultadoBusqueda =
                        "Se encontraron " + filas.size() + " registros para el parámetro de búsqueda '" + claveBusqueda + "'";
                }
            } else {
                LOG.debug("el parametro NO es válido");
                FacesContext context = FacesContext.getCurrentInstance();  
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Parámetro no válido -", "el parámetro de búsqueda '" + claveBusqueda +
                                       "' no cumple con el formato ni de RFC ni de número de control, favor de verificar"));
            }
        } catch (SIATException siate) {
            LOG.error(siate);
        }
        return null;
    }

    public void buscarXIcep()
    {
        LOG.debug("INICIO buscarXIcep");
        
        ItemComboBean itemConcepto = obtenerItemComboSeleccionado(conceptos, idConcepto);
        ItemComboBean itemImpuesto = obtenerItemComboSeleccionado(getMbCatalogos().getImpuestos(), idImpuesto);
        itemConcepto.setItemPadre(itemImpuesto);
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put("rfc", rfcCont);
        params.put("itemConcepto", itemConcepto);
        params.put("idEjercicio", ejercicio);
        params.put("itemPeriodo", obtenerItemComboSeleccionado(periodos, idPeriodo));
        params.put("idTipoSaldoIcep", tipoSaldo);

        LOG.debug(params);

        try {
            
            List<FilaGridBusquedaIceps> filas = service.buscarIcep(params);
            agregaId(filas);
            icepsEncontrados = new GridBusquedaIcepsDataModel(filas);

            int nfilas = filas.size();

            if (nfilas == 0) {
                FacesContext context = FacesContext.getCurrentInstance();  
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sin resultados - ", "No se encontró ningún registro que cumpla con los parámetros de búsqueda"));
            }
            if(nfilas == 1){
                resultadoBusqueda = "Se encontró " + nfilas + " registro para los parámetros de búsqueda";
            }
            else{
                resultadoBusqueda = "Se encontraron " + nfilas + " registros para los parámetros de búsqueda";
            }
            LOG.debug("numero de iceps encontrados ->" + nfilas + "<-");
        } catch (SIATException siate) {
            LOG.error(siate);
        }
    }
    
    private void agregaId(List<FilaGridBusquedaIceps> filas){
        /** Este metodo le asigna un id para que sean unicos en la vista y no se pierda al momento de seleccionar desde el datatable */
        if(filas != null){
            for (int i = 0; i < filas.size(); i++) {
                filas.get(i).setId(i);            
            }
        }
    }

    private ItemComboBean obtenerItemComboSeleccionado(List<ItemComboBean> lista, Integer id)
    {
        LOG.debug("id ->" + id + "<-");
        if(lista != null){
            for(ItemComboBean itemAux : lista)
            {
                if(itemAux.getId().equals(id)){
                    return itemAux;
                }
            }
        }
        return null;
    }

    public void limpiar()
    {
        LOG.debug("INICIO limpiar");
        claveBusqueda = null;
        rfcCont = null;
        idImpuesto = null;
        idConcepto = null;
        idTipoPeriodo = null;
        idPeriodo = null;
        ejercicio = null;
        tipoSaldo = null;
        resultadoBusqueda = null;
        icepSeleccionado = null;
        if(icepsEncontrados != null && icepsEncontrados.getWrappedData() != null) {
           ((List)icepsEncontrados.getWrappedData()).clear();
        }
    }

    public String dirigirRegistrarIcep(){
        return "registraInformacionIcep";
    }
    
    public String irADetalle()
    {
        LOG.debug("icepSeleccionado ->" + icepSeleccionado + "<-");
        if (null != icepSeleccionado)
        {
            mbSession.setIdSaldoIcep(icepSeleccionado.getIdSaldoIcep());
            mbSession.setRfc(icepSeleccionado.getRfc());
            return "detalleIcep";    
        }

        LOG.info("No se ha seleccionado icep a consultar ");

        return null;
    }

    public String regresar() {
        return "criteriosBusqueda";
    }

    public void adicionarIcep() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        JSFUtils.getExternalContext().redirect(request.getContextPath() +
                                               "/faces/resources/pages/controlsaldo/registraInformacionIcep.jsf");
        LOG.info(">>>>>>dirige a vista de informacion icep ");
    }

    public void actualizarConceptos()
    {
        LOG.debug("INICIO actualizarConceptos");
        if(idImpuesto > 0){
            conceptos = service.obtenerConceptosXImpuesto(idImpuesto);
        }
        else{
            conceptos = null; 
        }
    }

    public void actualizarPeriodos()
    {
        LOG.debug("INICIO actualizarPeriodos");
        LOG.debug("idTipoPeriodo ->" + idTipoPeriodo + "<-");
        if(idTipoPeriodo != null && !"".equals(idTipoPeriodo)){
            periodos = service.obtenerPeriodosXTipo(idTipoPeriodo);
            if(periodos.size() == 1){
                idPeriodo = periodos.get(0).getId();
            }
        }
        else{
            periodos = null; 
        }
    }

    public void handleToggle(ToggleEvent event)
    {
        LOG.debug("INICIO handleToggle");

        javax.faces.component.UIComponent x =    event.   getComponent() ;
        if(event.getVisibility() == Visibility.VISIBLE)
        {
            LOG.debug("id del fieldset ->" + x.getId() + "<-");
            if(x.getId().equals("fstPalabraClave")){
                LOG.debug("fieldsetActivo = 1");
                fieldsetActivo = 1;
            }
            else if(x.getId().equals("fstBusquedaIcep")){
                fieldsetActivo = 2;
            }
        }

        LOG.debug("fieldsetActivo ->" + fieldsetActivo + "<-");
    }

    public void manejarAgregarIcep()
    {
        LOG.debug("INICIO manejarAgregarIcep")    ;
       /** claveBusqueda = claveBusqueda.trim().toUpperCase();
        LOG.debug("claveBusqueda ->" + claveBusqueda);

        if(service.esRFCValido(claveBusqueda))
        {
            mbDlgDetalleIcep.setRfc(claveBusqueda);
            mbDlgDetalleIcep.obtenerNombreRazonSocial();
        }*/

        mbDlgDetalleIcep.setMostrarse(Boolean.TRUE);
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setTipoSaldo(Integer tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public Integer getTipoSaldo() {
        return tipoSaldo;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setOptRadio(Integer optRadio) {
        this.optRadio = optRadio;
    }

    public Integer getOptRadio() {
        return optRadio;
    }

    public void setIdImpuesto(Integer impuesto) {
        this.idImpuesto = impuesto;
    }

    public Integer getIdImpuesto() {
        return idImpuesto;
    }

    public void setConceptos(List<ItemComboBean> listaConceptos) {
        this.conceptos = listaConceptos;
    }

    public List<ItemComboBean> getConceptos() throws SQLException {
        return conceptos;
    }

    public void setPeriodos(List<ItemComboBean> listaPeriodos) {
        this.periodos = listaPeriodos;
    }

    public List<ItemComboBean> getPeriodos() throws SQLException {
        return periodos;
    }

    public void setIdTipoPeriodo(String tipoPeriodo) {
        this.idTipoPeriodo = tipoPeriodo;
    }

    public String getIdTipoPeriodo() {
        return idTipoPeriodo;
    }

    public void setIcepsEncontrados(GridBusquedaIcepsDataModel allIcep) {
        this.icepsEncontrados = allIcep;
    }

    public GridBusquedaIcepsDataModel getIcepsEncontrados() {
        return icepsEncontrados;
    }

    public void setRfcCont(String rfcCont) {
        this.rfcCont = rfcCont;
    }

    public String getRfcCont() {
        return rfcCont;
    }

    public void setRegistraIcep(boolean registraIcep) {
        this.registraIcep = registraIcep;
    }

    public boolean isRegistraIcep() {
        return registraIcep;
    }

    public void setExpRFC(String expRFC) {
        this.expRFC = expRFC;
    }

    public String getExpRFC() {
        return expRFC;
    }

    public void setIcepSeleccionado(FilaGridBusquedaIceps selectIcep) {
        this.icepSeleccionado = selectIcep;
    }

    public FilaGridBusquedaIceps getIcepSeleccionado() {
        return icepSeleccionado;
    }

    public void setOptRadio(int optRadio) {
        this.optRadio = optRadio;
    }

    public BusquedaIcepsService getService() {
        return service;
    }

    public void setService(BusquedaIcepsService service) {
        this.service = service;
    }

    public DyccMensajeUsrService getServiceMensajes() {
        return serviceMensajes;
    }

    public void setServiceMensajes(DyccMensajeUsrService dyccMensajeUsrService) {
        this.serviceMensajes = dyccMensajeUsrService;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public String getClaveBusqueda() {
        return claveBusqueda;
    }

    public void setClaveBusqueda(String claveBusqueda) {
        this.claveBusqueda = claveBusqueda;
    }

    public Integer getFieldsetActivo() {
        return fieldsetActivo;
    }

    public void setFieldsetActivo(Integer fieldsetActivo) {
        this.fieldsetActivo = fieldsetActivo;
    }

    public String getResultadoBusqueda() {
        return resultadoBusqueda;
    }

    public void setResultadoBusqueda(String resultadoBusqueda) {
        this.resultadoBusqueda = resultadoBusqueda;
    }

    public DlgDetalleIcepMB getMbDlgDetalleIcep() {
        return mbDlgDetalleIcep;
    }

    public void setMbDlgDetalleIcep(DlgDetalleIcepMB mbDlgDetalleIcep) {
        this.mbDlgDetalleIcep = mbDlgDetalleIcep;
    }

    public CatalogosEstaticosMB getMbCatalogos() {
        return mbCatalogos;
    }

    public void setMbCatalogos(CatalogosEstaticosMB mbCatalogos) {
        this.mbCatalogos = mbCatalogos;
    }

    public PermisosAjusteMB getMbPermisosAjustes() {
        return mbPermisosAjustes;
    }

    public void setMbPermisosAjustes(PermisosAjusteMB mbPermisosAjustes) {
        this.mbPermisosAjustes = mbPermisosAjustes;
    }

    public BitacoraAjustesMB getMbBitacora() {
        return mbBitacora;
    }

    public void setMbBitacora(BitacoraAjustesMB mbBitacora) {
        this.mbBitacora = mbBitacora;
    }
}
