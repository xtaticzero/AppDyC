/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.tesofe.web.controller.bean.backing;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.tesofe.dto.InformacionEnvioATESOFE;
import mx.gob.sat.siat.dyc.tesofe.service.GestionPagosTESOFEService;
import mx.gob.sat.siat.dyc.tesofe.service.ItemDownload;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajes;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


/**
 * @author JEFG
 */
@ManagedBean(name = "gestionPago")
@ViewScoped
public class GestionPagoMB extends AbstractPage {

    private static final Logger LOGGER = Logger.getLogger(GestionPagoMB.class);

    private static final String DMYH = "ddMMyyHHmmss";
    private static final String URI_TESOFE_RECIBIR = "/siat/dyc/TESOFE/recibir/";
    private static final String URI_TESOFE_MODIFICAR = "/siat/dyc/TESOFE/modificar/";
    private static final String GENERAR_ARCHIVOS = "1";
    private static final int NUMERO_0 = 0;
    private static final int NUMERO_50 = 50;
    private static final int NUMERO_100 = 100;

    private Date fechaPago;
    private Date fechaReporte;
    private Date fechaPresentacion;
    private Date fechaBusqueda;
    private UploadedFile uploadDocumento;
    private ArchivoCargaDescarga cargaArchivo;
    private boolean banderaBoton;
    private boolean flagBotonReporteDiario;
    private boolean flagFechaPago;
    private boolean showUpload;
    private boolean flagAutoISR;

    private String select;
    private String nombreArchivo;
    private String nombreArchivoADescargar;
    private Integer progress;
    private AccesoUsr accesoUsr;
    private List<InformacionEnvioATESOFE> listaEnvioTes;
    private List<File> listaDeArchivosDePago;

    @ManagedProperty("#{calcularFechasService}")
    private CalcularFechasService diaHabilService;

    @ManagedProperty("#{gestionPagosTESOFEService}")
    private GestionPagosTESOFEService gestionPagoService;

    @ManagedProperty("#{facesMessage}")
    private IFacesMessage messageSolicitud;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @PostConstruct
    public void init() {
        banderaBoton = Boolean.FALSE;
        this.accesoUsr = serviceObtenerSesion.execute();
        setProgress(NUMERO_0);
        flagBotonReporteDiario = Boolean.TRUE;
        listaEnvioTes = new ArrayList<InformacionEnvioATESOFE>();
        setShowUpload(Boolean.FALSE);
        setFlagFechaPago(Boolean.TRUE);
        setCargaArchivo(new ArchivoCargaDescarga());
        setSelect("1");
        setFlagAutoISR(Boolean.FALSE);

        try {
            getFechasDefault();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            JSFUtils.messageGlobal(e.getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }


    public void cargarArchivosTESOFE() {

        if (!banderaBoton) {
            setProgress(NUMERO_0);
            listaEnvioTes = new ArrayList<InformacionEnvioATESOFE>();
            try {
                banderaBoton = Boolean.TRUE;

                if (getSelect().equals(GENERAR_ARCHIVOS)) {
                    setProgress(NUMERO_50);
                    generarArchivos(getGestionPagoService().gestionarPagoAnteTESOFE(getFechaPago(),
                                                                                    getFechaPresentacion(), isFlagAutoISR()));
                } else {
                    generarArchivos(getGestionPagoService().modificar(getFechaPago(), getFechaPresentacion(),
                                                                      URI_TESOFE_MODIFICAR.concat(getNombreArchivo()), isFlagAutoISR()));
                    setProgress(NUMERO_50);
                    deleteArchivo();
                    defaultConsulta();
                }

                RequestContext.getCurrentInstance().reset("tabGestionPago:form2:dtDocAdjuntos");
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                JSFUtils.messageGlobal("Error al generar archivos TESOFE!.." + e.getMessage(),
                                       FacesMessage.SEVERITY_ERROR);
            }
            LOGGER.info("termina GENERAR ARCHIVOS TESOFE");
            banderaBoton = Boolean.FALSE;
            setProgress(NUMERO_100);
        }
    }

    public void generarReporteDeDiario() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();

            byte[] datos = getGestionPagoService().listarDatosParaReporteDeRetroDeTESOFE(fechaReporte,  isFlagAutoISR());
            if (datos != null && datos.length > 0) {
                response.setContentType("text/csv");
                response.addHeader("Content-Disposition", "inline;filename=resumenDiario.csv");
                response.getOutputStream().write(datos);
                response.setStatus(HttpServletResponse.SC_OK);
                response.flushBuffer();
                response.getOutputStream().close();
                facesContext.responseComplete();
            } else {
                JSFUtils.messageGlobal("No hay registros para el día de hoy", FacesMessage.SEVERITY_INFO);
                flagBotonReporteDiario = Boolean.TRUE;
            }

        } catch (Exception e) {
            JSFUtils.messageGlobal("Error al generar el reporte", FacesMessage.SEVERITY_ERROR);
            LOGGER.error("generarReporteDeDiario(): " + e);
        }
    }

    public void activarBotonReporteDiario() {
        if (fechaReporte != null) {
            flagBotonReporteDiario = false;
        } else {
            flagBotonReporteDiario = Boolean.TRUE;
        }
    }

    public void validaFechaPago() {
        if (getSelect().equals(GENERAR_ARCHIVOS)) {
            setShowUpload(Boolean.FALSE);
        } else {
            setShowUpload(Boolean.TRUE);
        }

        if ((getFechaPresentacion() != null && getFechaPago() != null) &&
            getFechaPresentacion().after(getFechaPago())) {
            setFechaPago(null);
            showMessage(ConstantesDyCNumerico.VALOR_67, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void validaFechaPresentacion() {
        if (null == getFechaPresentacion()) {
            showMessage(ConstantesDyCNumerico.VALOR_66, FacesMessage.SEVERITY_ERROR);
        } else {
            if ((getFechaPresentacion() != null && getFechaPago() != null) &&
                getFechaPresentacion().after(getFechaPago())) {
                setFechaPago(null);
                showMessage(ConstantesDyCNumerico.VALOR_67, FacesMessage.SEVERITY_ERROR);
            } 
            setFlagFechaPago(Boolean.FALSE);
        }
    }

    public void showUploadFile() {
        banderaBoton = false;
        if (!getSelect().equals(GENERAR_ARCHIVOS)) {
            listaEnvioTes = new ArrayList<InformacionEnvioATESOFE>();
            setUploadDocumento(null);
            setShowUpload(Boolean.TRUE);
        } else {
            deleteArchivo();
            setShowUpload(Boolean.FALSE);
        }
    }

    private void generarArchivos(List<InformacionEnvioATESOFE> lista) throws SIATException {
        if (!lista.isEmpty()) {
            listaEnvioTes.addAll(lista);
            setFlagFechaPago(Boolean.TRUE);
        } else {
            listaEnvioTes = new ArrayList<InformacionEnvioATESOFE>();
            setFechaPresentacion(null);
            JSFUtils.messageGlobal("No se encontraron registros!...  Ingresa otra fecha de consulta.",
                                   FacesMessage.SEVERITY_INFO);
        }
    }

    public void updateArchivoTESOFE(FileUploadEvent event) {
        setUploadDocumento(event.getFile());
        cargarDocumentoTESOFE();
    }

    public void cargarDocumentoTESOFE() {
        try {
            if (null == getUploadDocumento()) {
                showMessage(ConstantesDyCNumerico.VALOR_25, FacesMessage.SEVERITY_INFO);
            } else

            if (isArchivoValido()) {
                String fileName = getUploadDocumento().getFileName();
                fileName = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
                setNombreArchivo(new SimpleDateFormat(DMYH).format(new Date()).concat("_" + fileName));
                LOGGER.info("Nombre del documento: " + fileName);
                LOGGER.info("Renombrado: " + getNombreArchivo());
                try {
                    getCargaArchivo().escribirArchivo(getNombreArchivo(), getUploadDocumento().getInputstream(),
                                                      getSelect().equals(GENERAR_ARCHIVOS) ? URI_TESOFE_RECIBIR :
                                                      URI_TESOFE_MODIFICAR, ConstantesDyCNumerico.VALOR_4096);
                } catch (IOException e) {
                    LOGGER.error(e);
                    JSFUtils.messageGlobal("Error al escribir el archivo en disco!..", FacesMessage.SEVERITY_ERROR);
                    return;
                }
                JSFUtils.messageGlobal("El archivo se guardó correctamente!..", FacesMessage.SEVERITY_INFO);
            }
        } catch (SIATException e) {
            JSFUtils.messageGlobal(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }


    private boolean isArchivoValido() throws SIATException {
        if (getSelect().equals(GENERAR_ARCHIVOS)) {
            if (getUploadDocumento().getFileName().contains(".txt")) {
                return Boolean.TRUE;
            } else {
                JSFUtils.messageGlobal("Archivo inválido!... Debe ser tipo '.txt'", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            String contentType = getUploadDocumento().getFileName();
            try {
                contentType = contentType.substring(contentType.lastIndexOf(ConstantesArchivo.PUNTO)).toLowerCase();
            } catch (StringIndexOutOfBoundsException e) {
                LOGGER.error(e.getMessage());
                throw new SIATException("Archivo dañado!..", e);
            }
            if (".dat".equals(contentType)) {
                setShowUpload(Boolean.FALSE);
                return Boolean.TRUE;
            } else {
                JSFUtils.messageByIDComponent("fileMod", FacesMessage.SEVERITY_INFO,
                                              "Archivo inválido!... Debe ser tipo '.dat'");
            }
        }
        return false;
    }


    private String getMessage(int idMensaje) {
        return messageSolicitud.getMessage(idMensaje, ConstantesMensajes.CU_REGISTRO_SOLDEVLINEA);
    }

    private void showMessage(int idMensaje, Severity severity) {
        JSFUtils.messageGlobal(getMessage(idMensaje), severity);
    }

    private void getFechasDefault() throws SIATException {
        HttpSession session;
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
        Object isAutoISR = session.getAttribute("autoISR");
        setFechaPresentacion(getDiaHabilService().calculaFechaSugerida(new Date(), ConstantesDyCNumerico.VALOR_2));
        if (isAutoISR != null) {
            setFechaPago(getFechaPresentacion());
            session.setAttribute("autoISR", null);
            setFlagAutoISR(Boolean.TRUE);
        } else {
            setFechaPago(getDiaHabilService().calculaFechaSugerida(getFechaPresentacion(), ConstantesDyCNumerico.VALOR_1));
        }
    }

    private void deleteArchivo() {
        if (null != getNombreArchivo()) {
            try {
                ValidaDatosSolicitud.deleteDocumentos(URI_TESOFE_MODIFICAR.concat(getNombreArchivo()));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            setNombreArchivo(null);
        }
    }

    /**
     * Busca los archivos de pago que fueron generados de la fecha especificada.
     */
    public void buscarArchivosDePago() {
        try {
            listaDeArchivosDePago = gestionPagoService.listarArchivosDePagoPorFecha(fechaBusqueda, isFlagAutoISR());
        } catch (SIATException e) {
            LOGGER.error("Error al buscar los archivos: " + e);
        }
    }

    public void defaultConsulta() {
        setSelect("1");
        setShowUpload(Boolean.FALSE);
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getSelect() {
        return select;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setMessageSolicitud(IFacesMessage messageSolicitud) {
        this.messageSolicitud = messageSolicitud;
    }

    public IFacesMessage getMessageSolicitud() {
        return messageSolicitud;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = (fechaReporte != null) ? (Date)fechaReporte.clone() : null;
    }

    public Date getFechaReporte() {
        return (fechaReporte != null) ? (Date)fechaReporte.clone() : null;
    }

    public Date getFechaPresentacion1() {
        return (fechaPresentacion != null) ? (Date)fechaPresentacion.clone() : null;
    }

    public void setFlagBotonReporteDiario(boolean flagBotonReporteDiario) {
        this.flagBotonReporteDiario = flagBotonReporteDiario;
    }

    public boolean isFlagBotonReporteDiario() {
        return flagBotonReporteDiario;
    }

    public void setListaDeArchivosDePago(List<File> listaDeArchivosDePago) {
        this.listaDeArchivosDePago = listaDeArchivosDePago;
    }

    public List<File> getListaDeArchivosDePago() {
        return listaDeArchivosDePago;
    }

    public void setFechaBusqueda(Date fechaBusqueda) {
        if (fechaBusqueda != null) {
            this.fechaBusqueda = (Date)fechaBusqueda.clone();
        } else {
            this.fechaBusqueda = null;
        }
    }

    public Date getFechaBusqueda() {
        if (fechaBusqueda != null) {
            return (Date)fechaBusqueda.clone();
        } else {
            return null;
        }
    }

    public StreamedContent getArchivoDePago() {
        ArchivoCargaDescarga archivo = new ArchivoCargaDescarga();
        return archivo.descargarArchivo(nombreArchivoADescargar);
    }

    public void setNombreArchivoADescargar(String nombreArchivoADescargar) {
        this.nombreArchivoADescargar = nombreArchivoADescargar;
    }

    public String getNombreArchivoADescargar() {
        return nombreArchivoADescargar;
    }

    static class ItemDownloadArchivo implements ItemDownload {

        private String nombreArchivo;
        private StreamedContent file;

        public ItemDownloadArchivo(String nombreArchivo, StreamedContent file) {
            this.nombreArchivo = nombreArchivo;
            this.file = file;
        }


        public void setNombreArchivo(String nombreArchivo) {
            this.nombreArchivo = nombreArchivo;
        }

        @Override
        public String getNombreArchivo() {
            return nombreArchivo;
        }

        public void setFile(StreamedContent file) {
            this.file = file;
        }

        @Override
        public StreamedContent getFile() {
            return file;
        }
    }
    
    public void onTabChange(TabChangeEvent event) {
        String tabSeleccionada = event.getTab().getId();
        String paginaAutomaticas = "gestionPagoAutoISR.jsf";
        
        if (tabSeleccionada.equals("tab5")) {
            LOGGER.info("Se selecciono la opcion para Automatica de ISR PF");
            FacesContext temp = FacesContext.getCurrentInstance();
            try {
                temp.getExternalContext().redirect(paginaAutomaticas);
                HttpSession session;
                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
                session.setAttribute("autoISR", Boolean.TRUE);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = (fechaPago != null) ? (Date)fechaPago.clone() : null;
    }

    public Date getFechaPago() {
        return (fechaPago != null) ? (Date)fechaPago.clone() : null;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = (fechaPresentacion != null) ? (Date)fechaPresentacion.clone() : null;
    }

    public Date getFechaPresentacion() {
        return (fechaPresentacion != null) ? (Date)fechaPresentacion.clone() : null;
    }

    public void setUploadDocumento(UploadedFile uploadDocumento) {
        this.uploadDocumento = uploadDocumento;
    }

    public UploadedFile getUploadDocumento() {
        return uploadDocumento;
    }

    public void setDiaHabilService(CalcularFechasService diaHabilService) {
        this.diaHabilService = diaHabilService;
    }

    public CalcularFechasService getDiaHabilService() {
        return diaHabilService;
    }

    public void setGestionPagoService(GestionPagosTESOFEService gestionPago) {
        this.gestionPagoService = gestionPago;
    }

    public GestionPagosTESOFEService getGestionPagoService() {
        return gestionPagoService;
    }

    public void setFlagFechaPago(boolean flagFechaPago) {
        this.flagFechaPago = flagFechaPago;
    }

    public boolean isFlagFechaPago() {
        return flagFechaPago;
    }

    public void setCargaArchivo(ArchivoCargaDescarga cargaArchivo) {
        this.cargaArchivo = cargaArchivo;
    }

    public ArchivoCargaDescarga getCargaArchivo() {
        return cargaArchivo;
    }

    public void setShowUpload(boolean btnDescarga) {
        this.showUpload = btnDescarga;
    }

    public boolean isShowUpload() {
        return showUpload;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public List<InformacionEnvioATESOFE> getListaEnvioTes() {
        return listaEnvioTes;
    }

    public void setListaEnvioTes(List<InformacionEnvioATESOFE> listaEnvioTes) {
        this.listaEnvioTes = listaEnvioTes;
    }

    public boolean isFlagAutoISR() {
        return flagAutoISR;
    }

    public void setFlagAutoISR(boolean flagAutoISR) {
        this.flagAutoISR = flagAutoISR;
    }
}
