/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
 */
package mx.gob.sat.siat.dyc.cargaaut.web.controller.bean.backing;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.sat.mat.dyc.cargaautomaticas.CargaMasivoAutomaticasService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.tesofe.dto.InformacionEnvioATESOFE;
import mx.gob.sat.siat.dyc.tesofe.service.ItemDownload;
import mx.gob.sat.siat.dyc.tesofe.web.controller.bean.backing.GestionPagoMB;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajes;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * @author JEFG
 */
@ManagedBean(name = "cargaMasivaAutorizadaMB")
@ViewScoped
public class CargaMasivaAutorizadaMB extends AbstractPage {

    private static final Logger LOGGER = Logger.getLogger(GestionPagoMB.class);

    private static final String MSG001 = "El nombre del archivo, no presenta la nomenclatura establecida “tramitesSIVAD.txt”.";
    private static final String MSG002 = "El Tipo de archivo no corresponde a un Documento de texto (.txt).";
    private static final String MSG003 = "El formato de codificación del archivo es incorrecto.";
    private static final String MSG004 = "La distribución del contenido (layout) del archivo es incorrecta. ";
    private static final String MSG005 = "El contenido del archivo presenta caracteres inválidos.";
    private static final String MSG007 = "Archivo cargado exitosamente.";
    private static final String MSG008 = "Error en la carga del Archivo, favor de cargarlo nuevamente.";

    private static final String NOMENCLARUTA_VALIDA = "tramitesSIVAD";
    private static final String EXTENSION_VALIDA = ".txt";
    private static final String PIPE = "|";



    private String path;


    private String fileName;
    private static final int NUMERO_0 = 0;
    private static final int NUMERO_10 = 10;
    private static final int NUMERO_30 = 30;
    private static final int NUMERO_100 = 100;

    private static final String ENCODING_UTF8 = "UTF-8";

    private File archivoCargar;

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

    @ManagedProperty("#{cargaMasivoAutomaticasService}")
    private CargaMasivoAutomaticasService cargaMasivoAutomaticasService;

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
        FileInputStream archivo = null;
        Properties propiedades = new Properties();
          try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
            path=propiedades.getProperty("pathFile");
            fileName=propiedades.getProperty("fileName");
        } catch (IOException e) {
            LOGGER.error("Error al leer el properties ", e);
        } finally {
            try {
                if(archivo!=null){
                   archivo.close();
                }
            } catch (Exception e) {
                LOGGER.error("Error al cerrar el archivo de configuracion: ", e);
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        LOGGER.info("archivo:" + event.getFile().getFileName());
        nombreArchivo = event.getFile().getFileName();
        uploadDocumento = event.getFile();
    }

    public void cargarDocumento() {
        setProgress(NUMERO_0);
        if (null == getUploadDocumento()) {
            showMessage(ConstantesDyCNumerico.VALOR_25, FacesMessage.SEVERITY_INFO);
            LOGGER.info("vacio");
        } else if (validarArchivo()) {
            LOGGER.info("Regreso TRUE!!!");
            showMessage(MSG007, FacesMessage.SEVERITY_INFO);
        }
        nombreArchivo = null;
        uploadDocumento = null;
        setProgress(NUMERO_100);
    }

    private boolean validarArchivo() {
        boolean resultValidacionArchivo = false;
        boolean caracterInvalido = false;

        int i = 1;
        long numeroLineas = 0;

        String linea;
        setProgress(1);
        Pattern p = Pattern.compile("[^A-Za-z0-9ñÑ\\\\&]+");
        BufferedReader b=null;
        BufferedReader a=null;
        try{
        String[] nombres = obtenerNombreArchivo().split("\\.");
        //valida que la nomenclatura sea valida tramitesSIVAD
        if (nombres.length <= 2 && nombres[0].equals(NOMENCLARUTA_VALIDA)) {
            LOGGER.info("Nomenclatura valida");

            //Valida sí el archivo es de extensión .txt y que la extensión este en minuscula
            if (obtenerNombreArchivo().substring(obtenerNombreArchivo().length() - ConstantesDyCNumerico.VALOR_4,
                    obtenerNombreArchivo().length()).equals(EXTENSION_VALIDA)) {

                LOGGER.info("Se valido que el Archivo es txt con extensión en Minuscula");

                if (!isUTF8()) {
                    showMessage(MSG003, FacesMessage.SEVERITY_INFO);
                    LOGGER.info("NO ES UTF8");
                    return false;
                }


                //Lee el archivo creando un File para su manipulación
                b = new BufferedReader(new InputStreamReader(getUploadDocumento().getInputstream(),ENCODING_UTF8));
                try {
                    setProgress(NUMERO_10);

                    String stringcontador=null;
                    while ((stringcontador=b.readLine()) != null) {                    
                      if ( stringcontador.length() == 0 || stringcontador.trim().length() == 0) {
                            LOGGER.error("El archivo tiene lineasVacias");
                            showMessage(MSG004, FacesMessage.SEVERITY_INFO);
                            return false;
                        }
                        numeroLineas++;
                    }
                  
                    setProgress(NUMERO_30);
                    if (numeroLineas == 0) {
                        LOGGER.error("El archivo no tiene lineas, resultado: " + numeroLineas);
                        showMessage(MSG004, FacesMessage.SEVERITY_INFO);

                        return false;
                    } else {
                        //El archvo contiene información
                        LOGGER.error("El archivo tiene " + numeroLineas + " lineas");
                        double proceso = NUMERO_30;
                        boolean primeraLinea = true;
                        a = new BufferedReader(new InputStreamReader(getUploadDocumento().getInputstream(),ENCODING_UTF8));
                        String stringAnalizar=null;
                     while ((stringAnalizar=a.readLine()) != null) {    
                            LOGGER.info("Linea " + i++ + "\n" + stringAnalizar + "\n");
                            proceso = proceso- 1 + (double)ConstantesDyCNumerico.VALOR_70 / (double)numeroLineas ;
                            setProgress((int) proceso);
                            linea = stringAnalizar;
                            if (primeraLinea) {
                                if (linea.startsWith("\uFEFF")) {
                                    linea = linea.substring(1);
                                }
                                primeraLinea = false;
                            }
                            //Validando la estructura, Número de control y RFC, que contenga el |
                            if ( linea.contains(PIPE)) {
                                LOGGER.info("Contiene PIPE");

                                if (linea.isEmpty() || linea.length() != linea.trim().length()) {
                                    showMessage(MSG004, FacesMessage.SEVERITY_INFO);
                                    return false;

                                }
                                String[] abc = linea.split("\\|");

                                Matcher m;

                                LOGGER.info("Cantidad de cadenas: " + abc.length);

                                int primera = linea.indexOf(PIPE);
                                int ultima = linea.lastIndexOf(PIPE);

                                if (primera != ultima) {
                                    LOGGER.error("El Documento tiene más de un pipe '|' en una de sus líneas");
                                    showMessage(MSG004, FacesMessage.SEVERITY_INFO);
                                    return false;
                                }

                                for (String lineaN : abc) {
                                    LOGGER.info("Cadena: " + lineaN);

                                    m = p.matcher(lineaN);
                                    caracterInvalido = m.find();

                                    if (caracterInvalido) {
                                        LOGGER.info("El archivo contiene elementos invalidos");
                                        //Mensaje para informar al usuario que el documento contiene caracteres invalidos
                                        showMessage(MSG005, FacesMessage.SEVERITY_INFO);
                                        return false;
                                    }

                                }
                            } else {
                                showMessage(MSG004, FacesMessage.SEVERITY_INFO);
                                return false;
                            }
                        }
                     a.close();
                        try {
                            guardarArchivo(getUploadDocumento().getInputstream(),path+fileName);
                            cargaMasivoAutomaticasService.crearRegistroMasivas(accesoUsr.getUsuario());
                        } catch (Exception e) {
                            LOGGER.error("error al guardar registro",e);
                            showMessage(MSG008, FacesMessage.SEVERITY_INFO);
                            return false;
                        }
                        resultValidacionArchivo = true;
                    }

                    //Ciclo para leer las lineas del archivo
                } catch (IOException iex) {
                    LOGGER.error("Error ocurrido al eleer las lineas del archivo temporal" + iex);
                }

            } else {
                //Mensaje para notificar que el archivo es !=txt
                showMessage(MSG002, FacesMessage.SEVERITY_INFO);
            }
        } else {
            //Mensaje para notificar que el archivo contiene una nomenclatura incorrecta
            showMessage(MSG001, FacesMessage.SEVERITY_INFO);
            LOGGER.info("Nomenclatura valida");
        }
        }catch(Exception e){
           LOGGER.info("error en validarArchivo",e);
           showMessage(MSG008, FacesMessage.SEVERITY_INFO);
           return false;  
        }finally{
             if (a != null) {
                try {
                    a.close();
                } catch (Exception ex) {
                    LOGGER.error("error al cerrar archivo a", ex);
                }
            }
             if (b != null) {
                try {
                    b.close();
                } catch (Exception ex) {
                    LOGGER.error("error al cerrar archivo b", ex);
                }
            }
        }
        return resultValidacionArchivo;
    }

    private String getMessage(int idMensaje) {
        return messageSolicitud.getMessage(idMensaje, ConstantesMensajes.CU_REGISTRO_SOLDEVLINEA);
    }

    private void showMessage(int idMensaje, Severity severity) {
        JSFUtils.messageGlobal(getMessage(idMensaje), severity);
    }

    private void showMessage(String mensaje, Severity severity) {
        JSFUtils.messageGlobal(mensaje, severity);
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
        this.fechaReporte = (fechaReporte != null) ? (Date) fechaReporte.clone() : null;
    }

    public Date getFechaReporte() {
        return (fechaReporte != null) ? (Date) fechaReporte.clone() : null;
    }

    public Date getFechaPresentacion1() {
        return (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
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
            this.fechaBusqueda = (Date) fechaBusqueda.clone();
        } else {
            this.fechaBusqueda = null;
        }
    }

    public Date getFechaBusqueda() {
        if (fechaBusqueda != null) {
            return (Date) fechaBusqueda.clone();
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
        this.fechaPago = (fechaPago != null) ? (Date) fechaPago.clone() : null;
    }

    public Date getFechaPago() {
        return (fechaPago != null) ? (Date) fechaPago.clone() : null;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
    }

    public Date getFechaPresentacion() {
        return (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
    }

    public void setUploadDocumento(UploadedFile uploadDocumento) {
        this.uploadDocumento = uploadDocumento;
    }

    public UploadedFile getUploadDocumento() {
        return uploadDocumento;
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

    public File getArchivoCargar() {
        return archivoCargar;
    }

    public void setArchivoCargar(File archivoCargar) {
        this.archivoCargar = archivoCargar;
    }

    public boolean isBanderaBoton() {
        return banderaBoton;
    }

    public void setBanderaBoton(boolean banderaBoton) {
        this.banderaBoton = banderaBoton;
    }
  public  void guardarArchivo(InputStream inputStream,String destino) throws IOException {
 
    FileUtils.copyInputStreamToFile(inputStream, new File(destino));

    }
  private String obtenerNombreArchivo(){
     
       String[] nombres = getUploadDocumento().getFileName().split("\\\\");
      if(nombres.length==1){
          return nombres[0];
      }else{
          return nombres[nombres.length-1];
      }
  }
    private boolean isUTF8() {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(uploadDocumento.getInputstream());
            CharsetDetector cd = new CharsetDetector();
            cd.setText(bis);
            CharsetMatch cm = cd.detect();
            if (cm != null) {
                Reader re = cm.getReader();

                if (re != null) {
                    String ff = cm.getName();
                    if (!ENCODING_UTF8.equals(ff) && !"ISO-8859-2".equals(ff)) {
                        re.close();
                        return false;
                    }
                    re.close();
                }
            } else {
                return false;
            }
        } catch (Exception ex) {
            LOGGER.error("error en isUTF8", ex);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ex) {
                    LOGGER.error("error al cerrar archivo", ex);
                }
            }
        }

        return true;
    }

    public CargaMasivoAutomaticasService getCargaMasivoAutomaticasService() {
        return cargaMasivoAutomaticasService;
    }

    public void setCargaMasivoAutomaticasService(CargaMasivoAutomaticasService cargaMasivoAutomaticasService) {
        this.cargaMasivoAutomaticasService = cargaMasivoAutomaticasService;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
