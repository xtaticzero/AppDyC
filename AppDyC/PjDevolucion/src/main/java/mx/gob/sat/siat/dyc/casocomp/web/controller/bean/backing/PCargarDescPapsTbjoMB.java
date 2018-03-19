package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.siat.dyc.casocomp.service.PapelesTrabajoService;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.model.PapelesTrabajoDataModel;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridPapelesTrabajoBean;

import org.apache.log4j.Logger;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


@ManagedBean(name = "mbPapelesTrabajo")
@ViewScoped
public class PCargarDescPapsTbjoMB
{
    private static final Logger LOG = Logger.getLogger(PCargarDescPapsTbjoMB.class);

    @ManagedProperty("#{servicePapelesTrabajo}")
    private PapelesTrabajoService service;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;

    private StreamedContent archivoADescargar;
    private UploadedFile archivoSubido;
    private String descripcion;
    private boolean deshabBtnsAccArchExtte;
    
    private boolean mostrarDlgReempArchivo = Boolean.FALSE;

    private PapelesTrabajoDataModel papelesTrabajo;
    private FilaGridPapelesTrabajoBean papelTrabajoSelec;

    @PostConstruct
    public void cargarInfoInicial()
    {
        LOG.debug("PCargarDescPapsTbjoMB INICIO cargarInfoInicial");
        Map<String, Object> infoInicial = service.obtenerInfoIniPapelesTrabajo(mbSession.getNumControl());
        List<FilaGridPapelesTrabajoBean> filasPapelesTrabajo = (List<FilaGridPapelesTrabajoBean>)infoInicial.get("filasPapelesTrabajo");
        LOG.debug("filasPapelesTrabajo.size() ->" + filasPapelesTrabajo.size());
        this.setPapelesTrabajo(new PapelesTrabajoDataModel(filasPapelesTrabajo));
        deshabBtnsAccArchExtte = Boolean.TRUE;
    }

    public void onRowSelect()
    {
        LOG.debug("PCargarDescPapsTbjoMB INICIO onRowSelect");
        LOG.debug("papelTrabajoSelec ->" + papelTrabajoSelec + "<-");
        deshabBtnsAccArchExtte = false;
    }

    public void eliminarPapelTrabajo()
    {
        LOG.debug("INICIO eliminarPapelTrabajo");
        LOG.debug("papelTrabajoSelec ->" + papelTrabajoSelec + "<-");
        LOG.info("Se eliminara el papel de trabajo con id ->" + papelTrabajoSelec.getIdPapelTrabajo() + "<-");
        service.eliminarPapelTrabajo(papelTrabajoSelec.getIdPapelTrabajo());
        List<FilaGridPapelesTrabajoBean> filasPapelesTrabajo = (List<FilaGridPapelesTrabajoBean>)papelesTrabajo.getWrappedData();
        filasPapelesTrabajo.remove(papelTrabajoSelec);
        deshabBtnsAccArchExtte = Boolean.TRUE;
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "El archivo '" + papelTrabajoSelec.getNombreArchivo() + "' se eliminó exitosamente", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
    }

    public void descargarPapelTrabajo()
    {
        LOG.debug("INICIO descargarPapelTrabajo");
        LOG.debug("Se descargara el papelTrabajo con Id ->" + papelTrabajoSelec.getIdPapelTrabajo());
        Map<String, Object> respuesta = service.descargarPapelTrabajo(papelTrabajoSelec.getIdPapelTrabajo());
        File archivoPapelTrabajo = (File)respuesta.get("archivoPapelTrabajo");
        try
        {
            InputStream stream = new FileInputStream(archivoPapelTrabajo);
            archivoADescargar = new DefaultStreamedContent(stream, new MimetypesFileTypeMap().getContentType(archivoPapelTrabajo), archivoPapelTrabajo.getName());
        }
        catch (FileNotFoundException e)
        {
            LOG.error(e.getMessage());
        }
    }

    public void subirArchivo()
    {
        LOG.debug("INICIO subirArchivo");
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());  

        InputStream secuenciaEntrada;
        try{
            if(archivoSubido == null){
                FacesMessage mensajeFaces = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe cargar un archivo", null);
                FacesContext.getCurrentInstance().addMessage(null, mensajeFaces);
                return;
            }
            secuenciaEntrada = archivoSubido.getInputstream();
        }catch (IOException e){
            LOG.error(e.getMessage());
            return;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("numControl", mbSession.getNumControl());
        String nombreArchivo = archivoSubido.getFileName();
        LOG.debug("nombre completo del archivo que se recibe en servidor WebLogic ->" + nombreArchivo + "<-");
        nombreArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf('\\') + 1, nombreArchivo.length());
        LOG.debug("nombre (sin path) de archivo ->" + nombreArchivo + "<-");

        String cabeceraVirus = request.getHeader("X-Content-Scanning");
        LOG.info("cabeceraVirus ->" + cabeceraVirus + "<-");

        if(cabeceraVirus != null)
        {
            imprimirNombresCabeceras(request);
            FacesMessage mensajeFaces = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se detecto virus en el archivo que intenta subir!", null);
            FacesContext.getCurrentInstance().addMessage(null, mensajeFaces);
            return;
        }

        params.put("nombreArchivo", nombreArchivo);
        params.put("secuenciaEntrada", secuenciaEntrada);
        params.put("tipoArchivo", archivoSubido.getContentType());
        params.put("descripcion", descripcion);
        LOG.debug("mostrarDlgReempArchivo ->" + mostrarDlgReempArchivo + "<-");
        params.put("reemplazarArchivo", mostrarDlgReempArchivo);
        LOG.debug("archivoSubido.getSize() ->" + archivoSubido.getSize() + "<-");

        Map<String, Object> respuesta = service.subirPapelTrabajo(params);
        LOG.debug("respuesta ->" + respuesta + "<-");
        javax.faces.application.FacesMessage.Severity severidad;

        if((Boolean)respuesta.get("success"))
        {
            LOG.debug("El Service regreso EXITO en subirPapelTrabajo");
            severidad = FacesMessage.SEVERITY_INFO;
            papelesTrabajo.setWrappedData(respuesta.get("filasPapelesTrabajo"));
            this.descripcion = "";
        }
        else
        {
            LOG.info("NO se pudo subir el archivo, mensaje del Service ->" + respuesta.get("mensaje") + "<-");
            severidad = FacesMessage.SEVERITY_ERROR;
            this.descripcion = "";
        }

        mostrarDlgReempArchivo = (Boolean)respuesta.get("yaExiste");

        FacesMessage mensajeFaces = new FacesMessage(severidad, (String)respuesta.get("mensaje"), null);
        FacesContext.getCurrentInstance().addMessage(null, mensajeFaces);
        LOG.debug("FIN subirArchivo");
    }

    private void imprimirNombresCabeceras(HttpServletRequest request)
    {
        LOG.info("--Virus Detectado---------------- ->" + request + "<--------------------------------------");

        Enumeration names = request.getHeaderNames();
        int contCabeceras = 1;
        while (names.hasMoreElements())
        {
            String nombreCabecera = (String) names.nextElement();
            LOG.info("nombreCabecera " + contCabeceras + "->" + nombreCabecera + "<------------------------");
            Enumeration values = request.getHeaders(nombreCabecera);
            if (values != null)
            {
                while (values.hasMoreElements())
                {
                    String value = (String) values.nextElement();
                    LOG.info("valor : ->" + value + "<-");
                }
            }
            contCabeceras ++;
        }

        LOG.info("------------------------------------fin cabeceras---------------------------------------");
    }

    public void limpiarCarga()
    {
        mostrarDlgReempArchivo = Boolean.FALSE;
        descripcion = "";
    }

    public void reemplazarArchivo()
    {
        LOG.debug("INICIO reemplazarArchivo");
        subirArchivo();
        mostrarDlgReempArchivo = Boolean.FALSE;
    }

    public void setArchivoSubido(UploadedFile file) {
        this.archivoSubido = file;
    }

    public UploadedFile getArchivoSubido() {
        return archivoSubido;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDeshabBtnsAccArchExtte(boolean varBotonEliminar) {
        this.deshabBtnsAccArchExtte = varBotonEliminar;
    }

    public boolean isDeshabBtnsAccArchExtte() {
        return deshabBtnsAccArchExtte;
    }

    public void setArchivoADescargar(StreamedContent filePapeles) {
        this.archivoADescargar = filePapeles;
    }

    public StreamedContent getArchivoADescargar() {
        return archivoADescargar;
    }

    public void setMostrarDlgReempArchivo(boolean confirmarArchivo) {
        this.mostrarDlgReempArchivo = confirmarArchivo;
    }

    public boolean isMostrarDlgReempArchivo() {
        return mostrarDlgReempArchivo;
    }

    public ManagerSesionAdmCasosCompMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionAdmCasosCompMB mbSession) {
        this.mbSession = mbSession;
    }

    public PapelesTrabajoDataModel getPapelesTrabajo() {
        return papelesTrabajo;
    }

    public void setPapelesTrabajo(PapelesTrabajoDataModel papelesTrabajo) {
        this.papelesTrabajo = papelesTrabajo;
    }

    public FilaGridPapelesTrabajoBean getPapelTrabajoSelec() {
        return papelTrabajoSelec;
    }

    public void setPapelTrabajoSelec(FilaGridPapelesTrabajoBean papelTrabajoSelec) {
        this.papelTrabajoSelec = papelTrabajoSelec;
    }

    public PapelesTrabajoService getService() {
        return service;
    }

    public void setService(PapelesTrabajoService service) {
        this.service = service;
    }
}
