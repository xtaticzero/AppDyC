/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.web.controller.bean.backing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;

import mx.gob.sat.siat.dyc.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRDetalleVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.InconsistenciaTramiteVO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.registro.util.validador.ValidadorRNRegistro;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoArchivo;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesCaracteres;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;

/**
 *
 * @author root
 */
@ManagedBean(name = "consultaDevISRDetalleMB")
@SessionScoped
public class DetalleConsultaDevautisrMB {
          
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;
    
    @ManagedProperty(value = "#{consultaDevautisrMB}")
    private ConsultaDevautisrMB consultaDevautisrMB;

    private String contenidoDetalle;
    private String rfc;
    private String nombreCompleto;
    private String fileName;
    private long folioDeclaracion;
    private DatosTramiteISRDetalleVO selectedDetalleVO;
    
    private Logger log = Logger.getLogger(DetalleConsultaDevautisrMB.class);
    private List<ArchivoVO> listaDocumentos;
    private ArchivoVO archivoSelecionado;
    private UploadedFile file;
    private String nombreArchivo;
    private String nombreArchivoModificado;
    private boolean mortrarAcciones;
    private boolean mostrarBotones;
    private boolean mostrarAdjuntarDocumentos;
    private boolean mostrarCuentaBancaria;    
    private boolean habilitarEnviar;
    private boolean habilitarSiguiente;
    private TramiteDTO tramite;
    private StringBuilder carpetaTemp;
    private int size;
    private PersonaDTO contribuyente;    
    private ArchivoCargaDescarga cargaArchivo;
    private boolean habilitaBtnSig;
    
    private Mensaje mensaje;
    
    @ManagedProperty(value = "#{personaIDCService}")
    private PersonaIDCService personaIDCService;
    
    @ManagedProperty("#{validadorRNRegistro}")
    private ValidadorRNRegistro validadorRN;
    
    @PostConstruct
    public void init() {
       
            mensaje = new Mensaje();
            fileName = ConstantesDyC.EMPTY_STRING;
            selectedDetalleVO = consultaDevautisrMB.getSelectedDatosTramiteISRDetalleVOs();

            folioDeclaracion = selectedDetalleVO.getFolioDeclaracion();
            rfc = consultaDevautisrMB.getRfc();
            nombreCompleto = selectedDetalleVO.getApellidoPaterno() + ConstantesDyC.SPACE_STRING + selectedDetalleVO.getApellidoMaterno() + ConstantesDyC.SPACE_STRING +
            selectedDetalleVO.getNombres();
            contenidoDetalle = consultaDevautisrMB.getContenidoDetalle();
            validarBotonSiguienteManual();
            //CC
            cargaArchivo = new ArchivoCargaDescarga();

            tramite = consultaDevautisrMB.getSelectedDatosTramiteISRDetalleVOs().getTramite();
            habilitarEnviar = Boolean.FALSE;
            mostrarAdjuntarDocumentos = Boolean.FALSE;
            mostrarBotones = Boolean.TRUE;
            habilitaBtnSig = consultaDevautisrMB.isHabilitaBtnSiguiente();
            contribuyente = new PersonaDTO();
            contribuyente.setRfc(rfc);

            try 
            {
                contribuyente = personaIDCService.buscaPersona(contribuyente);                    
                contribuyente.setDomicilio(personaIDCService.buscaUbicacion(contribuyente));            
                getUrlLocalidad();

            } catch (SIATException ex) {
                log.error(ex);
            }               
        
    }
    
        public void subirArchivo() throws IOException, SIATException  {                
        FacesMessage msgInfo = new FacesMessage (FacesMessage.SEVERITY_INFO, "", "");        
                
        if(validaArchivo()){
            ArchivoVO archivo = new ArchivoVO();                                    
            try {
                String descArchivo = (new String(nombreArchivo.getBytes(ConstantesDyC.CODIFICACION_ISO),
                        ConstantesDyC.CODIFICACION_UTF8).toUpperCase());
                archivo.setNombre(descArchivo);
                archivo.setDescripcion(descArchivo);
                fileName = getNombreDocumento(ValidaDatosSolicitud.validaCaracteres(file.getFileName()));
                
                if(listaDocumentos != null)
                {
                    for(ArchivoVO objecto : listaDocumentos)
                    {
                        if(fileName.equals(objecto.getNombreArchivo()))
                        {
                            mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS, "El documento ya existe.");
                        return ;
                        }
                    }
                }
                
                archivo.setNombreArchivo(fileName);       
                archivo.setUrl(carpetaTemp.toString());                
                cargaArchivo.escribirArchivo(archivo.getNombreArchivo(), file.getInputstream(), archivo.getUrl(), ConstantesDyCNumerico.VALOR_4096);
            
                if(listaDocumentos==null){
                    listaDocumentos = new ArrayList<ArchivoVO>();                                                
                }                              
                size+=1;
                archivo.setTramite(size);                                        
                listaDocumentos.add(archivo);
                habilitarEnviar = Boolean.TRUE;
                tramite.setDocumentos(listaDocumentos);
                consultaDevautisrMB.getSelectedDatosTramiteISRDetalleVOs().getTramite().setDocumentos(listaDocumentos);
                nombreArchivo ="";                    
                msgInfo.setDetail ("Archivo agregado con éxito");
                FacesContext.getCurrentInstance ().addMessage (null, msgInfo);                        
            } catch (SIATException ex) {
                log.error(ex);
                throw new SIATException("ERROR AL ESCRIBIR ARCHIVOI EN DISCO", ex);
            }
        }        
    }
    
    private boolean validaArchivo() {
        FacesMessage msgError = new FacesMessage (FacesMessage.SEVERITY_ERROR, "", "");        
        boolean archivoValido = Boolean.TRUE;
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        String cabeceraVirus = request.getHeader("X-Content-Scanning");
        log.info("cabeceraVirus ->" + cabeceraVirus + "<-");        
        
        if (cabeceraVirus != null) {
            log.info("Se detecto virus");            
            msgError.setDetail ("Se detecto virus en el archivo que intenta subir!");
            FacesContext.getCurrentInstance ().addMessage (null, msgError);            
            archivoValido = false;
        }
        
        if (null == file && archivoValido) {                        
            msgError.setDetail ("El archivo no existe o no está disponible. Verifique el archivo o intente de nuevo.");            
            archivoValido = false;
        } else if (null == nombreArchivo || nombreArchivo.trim().equals(ConstantesCaracteres.CADENA_VACIA)) {                        
            msgError.setDetail (ConstantesArchivo.NOMBRE_DOCUMENTO);            
            archivoValido = false;
        } else if (validarExtensionValida()) {                                       
            msgError.setDetail ("El archivo seleccionado debe tener una extención valida");
            archivoValido = false;
        }else if (file.getSize() > ConstantesDyCNumerico.VALOR_4194304) {                        
            msgError.setDetail ("El archivo " + file.getFileName() + ConstantesTipoArchivo.MENSAJE_DOCUMENTO);
            archivoValido = false;            
        }        
        
        if(!archivoValido){
            nombreArchivo ="";                    
            FacesContext.getCurrentInstance ().addMessage (null, msgError);
        }
                
        return archivoValido;
    }               
    
    public String getUrlLocalidad() 
    {
        Map<String, Object> respValidador = validadorRN.identificarAdministracion(getTramite().getOrigenSaldo().getIdNum(),
            getTramite().getRolesContribuyente(),getTramite().getPersona().getDomicilio().getClaveAdmonLocal(), getTramite().getTipoTramite().getIdNum());
        consultaDevautisrMB.getSelectedDatosTramiteISRDetalleVOs().getTramite().getRolesContribuyente().setClaveLocalidad((String)respValidador.get("claveSirNumControl"));
        consultaDevautisrMB.getSelectedDatosTramiteISRDetalleVOs().getTramite().getRolesContribuyente().setClaveAdmon((Integer)respValidador.get("claveAdministracion"));
                
        if(carpetaTemp==null || carpetaTemp.toString().equals("")){            
            carpetaTemp = new StringBuilder();                    
            carpetaTemp.append(ConstantesDyCURL.URL_DOCUMENTOS);
            carpetaTemp.append("/");
            carpetaTemp.append((String)respValidador.get("claveSirNumControl"));
            carpetaTemp.append("/");
            carpetaTemp.append(rfc);
            carpetaTemp.append("/");
            carpetaTemp.append(ValidaDatosSolicitud.CARPETATEMP + new SimpleDateFormat("ddMMyyHHmmss").format(new Date()));                    
            HttpSession session;
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
            session.setAttribute("urlDirectorio",carpetaTemp.toString());                                
        }
        return carpetaTemp.toString();
    }    

    private String contentType(String type) {
        log.info(type);
        String strType = " ";
        try {
            strType = type.substring(type.lastIndexOf(ConstantesArchivo.PUNTO)).toLowerCase();
        } catch (StringIndexOutOfBoundsException e) {
            log.error(e.getMessage());
        }
        return strType;
    }
    
    private boolean validarExtensionValida(){
        String extensionArchivo = contentType(file.getFileName());
        List<String> listaExtensionesValidas = new ArrayList<String>();
        listaExtensionesValidas.add(".zip"); 
        listaExtensionesValidas.add(".doc");
        listaExtensionesValidas.add(".xls");
        listaExtensionesValidas.add(".pdf");
        listaExtensionesValidas.add(".jpeg");
        
        for(String extension : listaExtensionesValidas){
            if(extension.equals(extensionArchivo)){
                return Boolean.FALSE;
            }
        }        
        return Boolean.TRUE;
    }
    
    public void eliminarArchivo() {
        FacesMessage msgInfo = new FacesMessage (FacesMessage.SEVERITY_INFO, "", "");        
        if(archivoSelecionado!=null&& listaDocumentos!=null){
            for(ArchivoVO archivo:listaDocumentos){
                if(archivoSelecionado.getNombre().equals(archivo.getNombre()) && 
                        archivoSelecionado.getTramite().intValue()== archivo.getTramite().intValue()){
                    try {
                        String url = ValidaDatosSolicitud.DELETE + carpetaTemp.toString() + "/" + archivoSelecionado.getNombreArchivo();
                        Runtime.getRuntime().exec(url);
                    
                        listaDocumentos.remove(archivo);
                        consultaDevautisrMB.getSelectedDatosTramiteISRDetalleVOs().getTramite().setDocumentos(listaDocumentos);
                        mortrarAcciones = false;                    
                        msgInfo.setDetail ("Archivo eliminado con éxito");
                        FacesContext.getCurrentInstance ().addMessage (null, msgInfo);
                        break;
                    } catch (IOException ex) {
                        log.error(ex);
                    }
                }                
            }
        }
        if(listaDocumentos==null || listaDocumentos.isEmpty()){
            mortrarAcciones = Boolean.FALSE;            
            habilitarEnviar = Boolean.FALSE;
        }
        tramite.setDocumentos(listaDocumentos);
    }
    
    public void modificarArchivo() {
        FacesMessage msgInfo = new FacesMessage (FacesMessage.SEVERITY_INFO, "", "");        
        for(ArchivoVO archivo : listaDocumentos){
            if(archivo.getTramite().equals(archivoSelecionado.getTramite())){
                archivo.setNombre(nombreArchivoModificado);
                msgInfo.setDetail ("Archivo modificado con éxito");
                FacesContext.getCurrentInstance ().addMessage (null, msgInfo);
                break;
            }            
        }
        tramite.setDocumentos(listaDocumentos);
        consultaDevautisrMB.getSelectedDatosTramiteISRDetalleVOs().getTramite().setDocumentos(listaDocumentos);
    }
    
    public void cancelarModificarArchivo() {
        nombreArchivoModificado = archivoSelecionado.getNombre();        
    }
    
    public void onRowSelect() {
        mortrarAcciones = Boolean.TRUE;        
        nombreArchivoModificado = archivoSelecionado.getNombre();
    }
    
    public void regresarPantallaPrincipal(){
        try {
            FacesContext temp = FacesContext.getCurrentInstance();        
            temp.getExternalContext().redirect("consultarDevCont.jsf");
            consultaDevautisrMB.getSelectedDatosTramiteISRDetalleVOs().getTramite().setDocumentos(null);
        } catch (IOException ex) {
              log.error(ex.getMessage());
        }        
    }
    
    public void verPantallaAdjuntarDocumentos() throws SIATException, IOException
    {

        if(consultaDevautisrMB.detenerRegistroSolicitud(selectedDetalleVO))
        {
           return ;
        }
        
         mostrarAdjuntarDocumentos = Boolean.TRUE;  
         consultaDevautisrMB.setInstanciado(mostrarAdjuntarDocumentos);
         mostrarBotones = Boolean.FALSE;
    }
    
    public void validarBotonSiguienteManual()
    {
        habilitarSiguiente = Boolean.FALSE;
        
        if(selectedDetalleVO.getInconsistenciasTramite() != null)
        {
            for( InconsistenciaTramiteVO inconsis:selectedDetalleVO.getInconsistenciasTramite())
            {
                if(inconsis.getGenerarDevolucionManual() == 1)
                {
                   habilitarSiguiente = Boolean.TRUE;
                   break;
                }
            }
        }        
    }
    
    
    public void verPantallaDetalleRechazo(){
        mostrarBotones=Boolean.TRUE;
        mostrarAdjuntarDocumentos=Boolean.FALSE;   
        consultaDevautisrMB.setInstanciado(mostrarAdjuntarDocumentos);
    }
    
    public void verISRDetalleNuevo(){
        
        mostrarBotones = Boolean.TRUE;
        mostrarAdjuntarDocumentos = Boolean.FALSE;
        consultaDevautisrMB.setInstanciado(mostrarAdjuntarDocumentos);
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try { 
            ServletContext sc = (ServletContext)facesContext.getExternalContext().getContext();
            facesContext.getExternalContext().redirect(sc.getContextPath() + "/faces/resources/pages/consulta/consultaDevISRDetalleNuevo.xhtml");

        } catch (IOException e) {
            log.error(e);
        }
    }
    
    
    public void pantallaCuentaBancaria(){
        mostrarAdjuntarDocumentos=Boolean.FALSE;
        mostrarCuentaBancaria = Boolean.TRUE;
        consultaDevautisrMB.setInstanciado(mostrarAdjuntarDocumentos);
    }
    
    public void retroPantallaCuentaBancaria(){
        mostrarAdjuntarDocumentos=Boolean.TRUE;        
        mostrarCuentaBancaria = Boolean.FALSE;
        consultaDevautisrMB.setInstanciado(mostrarAdjuntarDocumentos);
    }
    
    
    public void limpiarSubirArchivo()  {
        file=null;
        nombreArchivo ="";                
    }
    
    public String goToConsultaDevAutISR(){
        if(listaDocumentos!=null &&!listaDocumentos.isEmpty()){
            try {
                Runtime.getRuntime().exec("rm -r "+carpetaTemp.toString());
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        consultaDevautisrMB.cancelarArchivo();        
        return "goConsultaDevolucionesContribuyente";
    }
    
     private String getNombreDocumento(String string) {

        return string.substring(string.lastIndexOf('\\') + ConstantesDyCNumerico.VALOR_1, string.length());
    }
    
    public String getContenidoDetalle() {
        return contenidoDetalle;
    }

    public void setContenidoDetalle(String contenidoDetalle) {
        this.contenidoDetalle = contenidoDetalle;
    }
    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public ConsultaDevautisrMB getConsultaDevautisrMB() {
        return consultaDevautisrMB;
    }

    public void setConsultaDevautisrMB(ConsultaDevautisrMB consultaDevautisrMB) {
        this.consultaDevautisrMB = consultaDevautisrMB;
    }
    
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public long getFolioDeclaracion() {
        return folioDeclaracion;
    }

    public void setFolioDeclaracion(long folioDeclaracion) {
        this.folioDeclaracion = folioDeclaracion;
    }

    public DatosTramiteISRDetalleVO getSelectedDetalleVO() {
        return selectedDetalleVO;
    }

    public void setSelectedDetalleVO(DatosTramiteISRDetalleVO selectedDetalleVO) {
        this.selectedDetalleVO = selectedDetalleVO;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public List<ArchivoVO> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<ArchivoVO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public ArchivoVO getArchivoSelecionado() {
        return archivoSelecionado;
    }

    public void setArchivoSelecionado(ArchivoVO archivoSelecionado) {
        this.archivoSelecionado = archivoSelecionado;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivoModificado() {
        return nombreArchivoModificado;
    }

    public void setNombreArchivoModificado(String nombreArchivoModificado) {
        this.nombreArchivoModificado = nombreArchivoModificado;
    }

    public boolean isMortrarAcciones() {
        return mortrarAcciones;
    }

    public void setMortrarAcciones(boolean mortrarAcciones) {
        this.mortrarAcciones = mortrarAcciones;
    }

    public boolean isMostrarBotones() {
        return mostrarBotones;
    }

    public void setMostrarBotones(boolean mostrarBotones) {
        this.mostrarBotones = mostrarBotones;
    }

    public boolean isMostrarAdjuntarDocumentos() {
        return mostrarAdjuntarDocumentos;
    }

    public void setMostrarAdjuntarDocumentos(boolean mostrarAdjuntarDocumentos) {
        this.mostrarAdjuntarDocumentos = mostrarAdjuntarDocumentos;
    }

    public boolean isMostrarCuentaBancaria() {
        return mostrarCuentaBancaria;
    }

    public void setMostrarCuentaBancaria(boolean mostrarCuentaBancaria) {
        this.mostrarCuentaBancaria = mostrarCuentaBancaria;
    }

    public TramiteDTO getTramite() {
        return tramite;
    }

    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    public StringBuilder getCarpetaTemp() {
        return carpetaTemp;
    }

    public void setCarpetaTemp(StringBuilder carpetaTemp) {
        this.carpetaTemp = carpetaTemp;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public PersonaDTO getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(PersonaDTO contribuyente) {
        this.contribuyente = contribuyente;
    }

    public ArchivoCargaDescarga getCargaArchivo() {
        return cargaArchivo;
    }

    public void setCargaArchivo(ArchivoCargaDescarga cargaArchivo) {
        this.cargaArchivo = cargaArchivo;
    }

    public PersonaIDCService getPersonaIDCService() {
        return personaIDCService;
    }

    public void setPersonaIDCService(PersonaIDCService personaIDCService) {
        this.personaIDCService = personaIDCService;
    }

    public ValidadorRNRegistro getValidadorRN() {
        return validadorRN;
    }

    public void setValidadorRN(ValidadorRNRegistro validadorRN) {
        this.validadorRN = validadorRN;
    }

    public boolean isHabilitarEnviar() {
        return habilitarEnviar;
    }

    public void setHabilitarEnviar(boolean habilitarEnviar) {
        this.habilitarEnviar = habilitarEnviar;
    }

    public boolean isHabilitarSiguiente() {
        return habilitarSiguiente;
    }

    public void setHabilitarSiguiente(boolean habilitarSiguiente) {
        this.habilitarSiguiente = habilitarSiguiente;
    }

    public boolean isHabilitaBtnSig() {
        return habilitaBtnSig;
    }

    public void setHabilitaBtnSig(boolean habilitaBtnSig) {
        this.habilitaBtnSig = habilitaBtnSig;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
