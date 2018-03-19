package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctPapelTrabajoDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarPapelTrabajoService;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


/**
 * @author Federico Chopin Gachuz
 **/
@ManagedBean(name = "cargaPapelesMB")
@SessionScoped
public class CargaPapelesMB extends AbstractPage {

    private static final Logger LOG = Logger.getLogger(CargaPapelesMB.class);

    @ManagedProperty(value = "#{administrarPapelTrabajoService}")
    private AdministrarPapelTrabajoService administrarPapelTrabajoService;

    @ManagedProperty("#{registroPistasAuditoria}")
    private RegistroPistasAuditoria registroPistasAuditoria;

    private List<DyctPapelTrabajoDTO> listaPapelTrabajo = new ArrayList<DyctPapelTrabajoDTO>();
    private DyctPapelTrabajoDTO dyctPapelTrabajoDTO;
    private StreamedContent filePapeles;
    private UploadedFile file;
    private String pathStr = ConstantesDyCURL.URL_DOCUMENTOS;
    private File path = null;
    private String numControl;
    private String descripcion = "";
    private boolean varBotonEliminar;
    private boolean varBotonDescarga;
    private boolean existe = false;
    private String dialogPregunta = "dlgNota.show();";
    private boolean confirmarArchivo = Boolean.FALSE;
    private boolean nuevoOreemplazo = Boolean.TRUE;
    private Integer idPapel;
    private ArchivoCargaDescarga archivo;
    private int claveAdm;
    private String rfcContribuyente;

    private Map<String, String> reemplaceMensajes;

    private PistaAuditoriaVO pistaAuditoria;

    private boolean paginador;

    private static String[] tipoArchivo;

    private String nombreCompleto;

    private String numEmpleadoStr;

    static {
        tipoArchivo = new String[ConstantesDyCNumerico.VALOR_7];

        tipoArchivo[ConstantesDyCNumerico.VALOR_0] = ".zip";
        tipoArchivo[ConstantesDyCNumerico.VALOR_1] = ".jpg";
        tipoArchivo[ConstantesDyCNumerico.VALOR_2] = ".doc";
        tipoArchivo[ConstantesDyCNumerico.VALOR_3] = ".docx";
        tipoArchivo[ConstantesDyCNumerico.VALOR_4] = ".xls";
        tipoArchivo[ConstantesDyCNumerico.VALOR_5] = ".xlsx";
        tipoArchivo[ConstantesDyCNumerico.VALOR_6] = ".pdf";

    }

    public void inicial() {

        if (listaPapelTrabajo.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador = Boolean.TRUE;
        } else {
            paginador = Boolean.FALSE;
        }

        archivo = new ArchivoCargaDescarga();
        setDataModel(new SIATDataModel());
        getDataModel().setWrappedData(listaPapelTrabajo);
        varBotonEliminar = Boolean.TRUE;
        varBotonDescarga = Boolean.TRUE;
    }

    public void onRowSelect() {
        varBotonEliminar = Boolean.FALSE;
        varBotonDescarga = Boolean.FALSE;
    }

    public void bajaPapeles() {
        try {
            administrarPapelTrabajoService.bajaPapelTrabajo(dyctPapelTrabajoDTO.getIdPapelTrabajo());
            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, "El archivo se eliminó exitosamente", null);
            FacesContext.getCurrentInstance().addMessage(null, exito);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            FacesMessage error =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error al intentar eliminar el archivo",
                                 null);
            FacesContext.getCurrentInstance().addMessage(null, error);
        }


        try {
            listaPapelTrabajo = administrarPapelTrabajoService.buscarPapelTrabajo(numControl);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
        
        if (listaPapelTrabajo.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador = Boolean.TRUE;
        } else {
            paginador = Boolean.FALSE;
        }

        this.getDataModel().setWrappedData(listaPapelTrabajo);
    }

    public void downloadPapeles() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(dyctPapelTrabajoDTO.getUrl().concat("/"));
        ruta.append(dyctPapelTrabajoDTO.getNombreArchivo());

        File tempFile = new File(ruta.toString());

        byte[] contenido = null;
        try {
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (IOException e) {
            LOG.error("ERROR: " + e.getMessage());
        }

        filePapeles =
                new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                                           dyctPapelTrabajoDTO.getNombreArchivo());

        registrarPistaAuditoria(ConstantesDyCNumerico.VALOR_536, ConstantesDyCNumerico.VALOR_112);
    }

    public boolean validaTipo() {

        String contentType = file.getFileName();

        try {
            contentType = contentType.substring(contentType.lastIndexOf(ConstantesArchivo.PUNTO)).toLowerCase();
        } catch (StringIndexOutOfBoundsException e) {
            LOG.error(e.getMessage());
            FacesMessage errorTipo =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: seleccione un archivo valido", null);
            FacesContext.getCurrentInstance().addMessage(null, errorTipo);
        }

        boolean tip = false;

        for (int a = 0; a < tipoArchivo.length; a++) {
            if (contentType.equals(tipoArchivo[a])) {
                tip = Boolean.TRUE;
                return tip;
            }
        }

        return tip;
    }

    public void uploadTwo() {

        long tamanioMax = ConstantesDyCNumerico.VALOR_1048576;
        long tamanioMaxZip = ConstantesDyCNumerico.VALOR_4194304;

        String contentType2 = null;

        String nom1 = null;
        String nomCorrecto1 = null;

        if (null != file) {
            contentType2 = file.getFileName();
            nom1 = file.getFileName();
            nomCorrecto1 = nom1.substring(nom1.lastIndexOf('\\') + 1, nom1.length());
        }

        try {
            if (null != contentType2) {
                contentType2 = contentType2.substring(contentType2.lastIndexOf(ConstantesArchivo.PUNTO)).toLowerCase();
            }
            
            validaciones(contentType2, tamanioMax, tamanioMaxZip, nomCorrecto1);

           
        } catch (StringIndexOutOfBoundsException e) {
            LOG.error(e.getMessage());
            FacesMessage errorTipo =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: seleccione un archivo valido", null);
            FacesContext.getCurrentInstance().addMessage(null, errorTipo);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            FacesMessage errorArchivo =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al tratar de enviar el documento.", null);
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
        }
    }
    
    public void validaciones(String contentType2, long tamanioMax, long tamanioMaxZip, String nomCorrecto1) {
        
        if (null == file) {

            FacesMessage errorNulo =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: debe seleccionar un archivo", null);
            FacesContext.getCurrentInstance().addMessage(null, errorNulo);

        } else if (!validaTipo()) {

            FacesMessage errorTipo =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: el tipo del archivo no es correcto, favor de verificar",
                                 null);
            FacesContext.getCurrentInstance().addMessage(null, errorTipo);

        } else if (contentType2.equals(tipoArchivo[0]) && file.getSize() > tamanioMaxZip) {

            FacesMessage errorTamanio =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: el archivo " + nomCorrecto1 +
                                 " sobrepasa el peso permitido (4Mb)", null);
            FacesContext.getCurrentInstance().addMessage(null, errorTamanio);

        } else if (!contentType2.equals(tipoArchivo[0]) && file.getSize() > tamanioMax) {

            FacesMessage errorTamanio =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: el archivo " + nomCorrecto1 +
                                 " sobrepasa el peso permitido (1Mb)", null);
            FacesContext.getCurrentInstance().addMessage(null, errorTamanio);

        } else {

            String nom = file.getFileName();
            String nomCorrecto = nom.substring(nom.lastIndexOf('\\') + 1, nom.length());
            if (!buscarArchivo(nomCorrecto)) {
                cargaArchivo();
            } else {
                confirmarArchivo = Boolean.TRUE;
                nuevoOreemplazo = Boolean.FALSE;
            }
        }

        
    }

    public void limpiarCarga() {

        confirmarArchivo = Boolean.FALSE;

    }

    public boolean buscarArchivo(String nombre) {
        boolean existeArchivo = Boolean.FALSE;
        for (DyctPapelTrabajoDTO objeto : listaPapelTrabajo) {
            if (objeto.getNombreArchivo().equals(nombre)) {
                idPapel = objeto.getIdPapelTrabajo();
                existeArchivo = Boolean.TRUE;
                break;
            }
        }
        return existeArchivo;
    }

    public void cargaArchivo() {
        path = new File(pathStr);
        String nom = file.getFileName();
        String nomCorrecto = nom.substring(nom.lastIndexOf('\\') + 1, nom.length());

        if (nomCorrecto.length() > ConstantesDyCNumerico.VALOR_50) {
            FacesMessage errorNulo =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: tamaño de nombre de documento inválido", null);
            FacesContext.getCurrentInstance().addMessage(null, errorNulo);
        } else {
            try {

                StringBuilder ruta = new StringBuilder();

                ruta.append(ConstantesDyCURL.URL_DOCUMENTOS);
                ruta.append("/");
                ruta.append(String.valueOf(claveAdm).concat("/"));
                /**ruta.append(rfcContribuyente.concat("/"));*/
                ruta.append(Utilerias.corregirRFC(rfcContribuyente).concat("/"));
                ruta.append(numControl);
                ruta.append(ConstantesDyCURL.TIPO_ARCHIVO_PAPEL);

                DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
                dycpServicioDTO.setNumControl(numControl);

                DyctExpedienteDTO dyctExpedienteDTO = new DyctExpedienteDTO();
                dyctExpedienteDTO.setServicioDTO(dycpServicioDTO);

                copiarArchivo(nomCorrecto, file.getInputstream(), ruta.toString());
                DyctPapelTrabajoDTO dyctPapelTrabajoDto = new DyctPapelTrabajoDTO();
                dyctPapelTrabajoDto.setNombreArchivo(nomCorrecto);
                dyctPapelTrabajoDto.setDescripcion(descripcion.toUpperCase());
                dyctPapelTrabajoDto.setFechaRegistro(new Date());
                dyctPapelTrabajoDto.setDyctExpedienteDTO(dyctExpedienteDTO);
                dyctPapelTrabajoDto.setUrl(ruta.toString());
                dyctPapelTrabajoDto.setIdPapelTrabajo(idPapel);
                administrarPapelTrabajoService.insertarPapelTrabajo(dyctPapelTrabajoDto, nuevoOreemplazo, numEmpleadoStr,
                                                             nombreCompleto);

                listaPapelTrabajo = administrarPapelTrabajoService.buscarPapelTrabajo(numControl);

                if (listaPapelTrabajo.size() > ConstantesDyCNumerico.VALOR_5) {
                    paginador = Boolean.TRUE;
                } else {
                    paginador = Boolean.FALSE;
                }

                this.getDataModel().setWrappedData(listaPapelTrabajo);
                FacesMessage exito =
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "El archivo se guardó exitosamente", null);
                FacesContext.getCurrentInstance().addMessage(null, exito);
            } catch (IOException e) {
                LOG.error(e.getMessage());
                FacesMessage errorArchivo =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al tratar de enviar el documento.", null);
                FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
            } catch (SIATException e) {
                LOG.error(e.getMessage());
                FacesMessage errorArchivo =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al tratar de enviar el documento.", null);
                FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
            }
        }
        confirmarArchivo = Boolean.FALSE;
        nuevoOreemplazo = Boolean.TRUE;
    }

    public void registrarPistaAuditoria(int movimiento, int idMensaje) {

        reemplaceMensajes = new HashMap<String, String>();

        reemplaceMensajes.put("<numeroDeEmpleado>", numEmpleadoStr);
        reemplaceMensajes.put("<nombreDelEmpleado>", nombreCompleto);
        reemplaceMensajes.put("<numeroDeControlDeLaSolicitudDeDevolucion>", numControl);

        pistaAuditoria = new PistaAuditoriaVO();

        pistaAuditoria.setRemplaceMensajes(reemplaceMensajes);
        pistaAuditoria.setIdGrupoOperacion(ConstantesDyCNumerico.VALOR_32);
        pistaAuditoria.setIdProceso(Procesos.DYC00005);
        pistaAuditoria.setIdMensaje(idMensaje);
        pistaAuditoria.setMovimiento(movimiento);
        pistaAuditoria.setIdentificador(numControl);


        try {
            registroPistasAuditoria.registrarPistaAuditoria(pistaAuditoria);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
    }

    public void copiarArchivo(String fileName, InputStream in, String ruta) throws SIATException {
        archivo.escribirArchivo(fileName, in, ruta, ConstantesDyCNumerico.VALOR_4096);
    }

    public void clear() {
        this.descripcion = "";
        RequestContext.getCurrentInstance().execute("dlgCargaPapeles.show()");
    }

    public void regresar() {

        /** return "dictaminarSolicitud3"; */

        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ServletContext sc = (ServletContext)context.getExternalContext().getContext();
            context.getExternalContext().redirect(sc.getContextPath() +
                                                  "/faces/resources/pages/gestionsol/dictaminarSolicitud.jsf");
        } catch (IOException e) {
            LOG.error("Error al redireccionar a la página de error" + e.getMessage());
        }

    }

    public void setListaPapelTrabajo(List<DyctPapelTrabajoDTO> listaPapelTrabajo) {
        this.listaPapelTrabajo = listaPapelTrabajo;
    }

    public List<DyctPapelTrabajoDTO> getListaPapelTrabajo() {
        return listaPapelTrabajo;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public File getPath() {
        return path;
    }

    public void setDyctPapelTrabajoDTO(DyctPapelTrabajoDTO dyctPapelTrabajoDTO) {
        this.dyctPapelTrabajoDTO = dyctPapelTrabajoDTO;
    }

    public DyctPapelTrabajoDTO getDyctPapelTrabajoDTO() {
        return dyctPapelTrabajoDTO;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setVarBotonDescarga(boolean varBotonDescarga) {
        this.varBotonDescarga = varBotonDescarga;
    }

    public boolean isVarBotonDescarga() {
        return varBotonDescarga;
    }

    public void setVarBotonEliminar(boolean varBotonEliminar) {
        this.varBotonEliminar = varBotonEliminar;
    }

    public boolean isVarBotonEliminar() {
        return varBotonEliminar;
    }

    public void setFilePapeles(StreamedContent filePapeles) {
        this.filePapeles = filePapeles;
    }

    public StreamedContent getFilePapeles() {
        return filePapeles;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setDialogPregunta(String dialogPregunta) {
        this.dialogPregunta = dialogPregunta;
    }

    public String getDialogPregunta() {
        return dialogPregunta;
    }

    public void setConfirmarArchivo(boolean confirmarArchivo) {
        this.confirmarArchivo = confirmarArchivo;
    }

    public boolean isConfirmarArchivo() {
        return confirmarArchivo;
    }

    public void setNuevoOreemplazo(boolean nuevoOreemplazo) {
        this.nuevoOreemplazo = nuevoOreemplazo;
    }

    public boolean isNuevoOreemplazo() {
        return nuevoOreemplazo;
    }

    public void setArchivo(ArchivoCargaDescarga archivo) {
        this.archivo = archivo;
    }

    public ArchivoCargaDescarga getArchivo() {
        return archivo;
    }

    public void setClaveAdm(int claveAdm) {
        this.claveAdm = claveAdm;
    }

    public int getClaveAdm() {
        return claveAdm;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setIdPapel(Integer idPapel) {
        this.idPapel = idPapel;
    }

    public Integer getIdPapel() {
        return idPapel;
    }

    public void setPaginador(boolean paginador) {
        this.paginador = paginador;
    }

    public boolean isPaginador() {
        return paginador;
    }

    public void setReemplaceMensajes(Map<String, String> reemplaceMensajes) {
        this.reemplaceMensajes = reemplaceMensajes;
    }

    public Map<String, String> getReemplaceMensajes() {
        return reemplaceMensajes;
    }

    public void setPistaAuditoria(PistaAuditoriaVO pistaAuditoria) {
        this.pistaAuditoria = pistaAuditoria;
    }

    public PistaAuditoriaVO getPistaAuditoria() {
        return pistaAuditoria;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }


    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setRegistroPistasAuditoria(RegistroPistasAuditoria registroPistasAuditoria) {
        this.registroPistasAuditoria = registroPistasAuditoria;
    }

    public RegistroPistasAuditoria getRegistroPistasAuditoria() {
        return registroPistasAuditoria;
    }

    public void setAdministrarPapelTrabajoService(AdministrarPapelTrabajoService administrarPapelTrabajoService) {
        this.administrarPapelTrabajoService = administrarPapelTrabajoService;
    }

    public AdministrarPapelTrabajoService getAdministrarPapelTrabajoService() {
        return administrarPapelTrabajoService;
    }
}
