/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.Serializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.EntityBasicUpLoad;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.generico.util.common.ValidaArchivoUpload;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.AnexoRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.ArchivoSolventadoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.DocRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.EstatusRequerimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.NotaExpedienteDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.AcuseReciboService;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.service.registrarinformacion.SolventarRequerimientoService;
import mx.gob.sat.siat.dyc.gestionsol.vo.solventacion.SolventacionRequerimientoVO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;


/**
 * Managed bean para la vista de adicionarSolicitud.jsf
 *
 * @author David Guerrero Reyes
 * @author Jesus Alfredo Hernandez Orozco.
 */
@ManagedBean(name = "solventarRequerimientoMB")
@SessionScoped
public class SolventarRequerimientoMB extends AbstractPage {

    private int idTablaDoc;
    private Integer numReq;
    private Integer adm;

    private long documentosAdjuntos;
    private long anexosAdjuntos;

    private String descripcion;
    private String estatusEnvio;
    private String mensajeConfirmacion;
    private String nom;
    private String notasAclaratorias;
    private String numControl;
    private String numControlDoc;
    private String paginaAnterior;
    private String prueba;
    private String rfcContribuyente;
    private String tituloSolventar;
    private String urlArchivo;

    private Utils utils;
    private UploadedFile file;

    private DocRequeridoDTO selectDocumentoReqDTO;
    private AnexoRequeridoDTO selectAnexoReqDTO;
    private EstatusRequerimientoDTO estatusRequerimientoDTO;
    private ArchivoCargaDescarga archivo;

    private List<AnexoRequeridoDTO> lstAnexosReqDTO;
    private List<EntityBasicUpLoad> listaArchivos;
    private List<DocRequeridoDTO> lstDocumentosReqDTO;

    private static String[] tipoArchivo;
    private FacesMessage msg;
    private AccesoUsr accesoUsr;
    private AnexoRequeridoDTO seleccionoAnexoDescargar;
    private DefaultStreamedContent descargarAnexo;

    private boolean btnEnviar;

    private static final String CONTENT_SCANNING = "X-Content-Scanning";
    private Logger log = Logger.getLogger(SolventarRequerimientoMB.class.getName());

    @ManagedProperty(value = "#{ejecutaFIELMB}")
    private EjecutaFielMB ejecutaFielMB;

    @ManagedProperty("#{solventarRequerimientoService}")
    private SolventarRequerimientoService solventarRequerimientoService;

    @ManagedProperty("#{dyccMensajeUsrService}")
    private DyccMensajeUsrService consultarDyccMensajeUsrService;

    @ManagedProperty("#{acuseReciboService}")
    private AcuseReciboService acuseReciboService;

    @ManagedProperty(value = "#{diaHabilService}")
    private DiaHabilService diaHabilService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    static {
        tipoArchivo = new String[ConstantesDyCNumerico.VALOR_8];
        tipoArchivo[ConstantesDyCNumerico.VALOR_0] = ".zip";
        tipoArchivo[ConstantesDyCNumerico.VALOR_1] = ".jpg";
        tipoArchivo[ConstantesDyCNumerico.VALOR_2] = ".doc";
        tipoArchivo[ConstantesDyCNumerico.VALOR_3] = ".docx";
        tipoArchivo[ConstantesDyCNumerico.VALOR_4] = ".xls";
        tipoArchivo[ConstantesDyCNumerico.VALOR_5] = ".xlsx";
        tipoArchivo[ConstantesDyCNumerico.VALOR_6] = ".pdf";
        tipoArchivo[ConstantesDyCNumerico.VALOR_7] = ".jpeg";
    }

    /**
     * Constructor
     */
    public SolventarRequerimientoMB() {
        super();
        descripcion = "";
        notasAclaratorias = "";
        tituloSolventar = "";
        msg = new FacesMessage("");
        archivo = new ArchivoCargaDescarga();
        accesoUsr = new AccesoUsr();
        lstAnexosReqDTO = new ArrayList<AnexoRequeridoDTO>();
        lstDocumentosReqDTO = new ArrayList<DocRequeridoDTO>();
    }

    /**
     * Post-constructor
     */
    public void init() {
        try {

            btnEnviar = false;

            RequestContext requestContext = RequestContext.getCurrentInstance();
            this.setDataModel(new SIATDataModel<Serializable>());
            this.setDataModelAnexo(new SIATDataModel<Serializable>());
            this.setDataModelDocumento(new SIATDataModel<Serializable>());

            this.listaArchivos = new ArrayList<EntityBasicUpLoad>();
            tituloSolventar = "Solventación de requerimientos (" + getNumControl() + ")";
            urlArchivo = ConstantesDyCURL.URL_DOCUMENTOS + '/' + getAdm().toString() + '/'
                    + getRfcContribuyente() + '/' + getNumControl()
                    + ConstantesDyCURL.TIPO_INFO_ADICIONAL;

            accesoUsr = serviceObtenerSesion.execute();
            idTablaDoc = ConstantesDyCNumerico.VALOR_0;
            estatusRequerimientoDTO = solventarRequerimientoService.consultaEstatusReq(
                    this.numControlDoc, numControl);

            if (estatusRequerimientoDTO.getNumControl() != null) {

                boolean resultadoRegla = validaReglas(estatusRequerimientoDTO.getNumeroReq());

                if (!resultadoRegla) {

                    lstDocumentosReqDTO = solventarRequerimientoService.consultaDocumentosReq(
                            numControl, estatusRequerimientoDTO.getNumRequerimiento());

                    lstAnexosReqDTO = solventarRequerimientoService.consultaAnexoReq(numControl,
                            estatusRequerimientoDTO.getNumRequerimiento());

                } else {

                    btnEnviar = Boolean.TRUE;

                    msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                            ConstantesDyCNumerico.VALOR_1, ConstantesDyCNumerico.VALOR_37)
                            .getDescripcion());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    setEstatusEnvio("Enviado");
                }

            } else {
                setMensajeConfirmacion("Para poder solventar un requerimiento, debe de iniciar con la búsqueda del mismo.");
                requestContext.execute("confirmationSave.show();");
            }
        } catch (SIATException e) {
            log.error("Hubo un error al cargar la informacion del requerimiento: " + e);
        }
    }

    public boolean validaReglas(int numRequerimiento) {

        Date fechaResultado = null;
        Date fechaHoy = null;
        boolean fechaMayor = false;

        if (null != estatusRequerimientoDTO
                && null != estatusRequerimientoDTO.getFechaNotificacion()) {

            if (numControl.startsWith("DC")) {
                if (numRequerimiento == ConstantesDyCNumerico.VALOR_1) {
                    fechaResultado = getDiaHabilService().buscarDiaFederalRecaudacion(
                            estatusRequerimientoDTO.getFechaNotificacion(),
                            ConstantesDyCNumerico.VALOR_21);
                } else if (numRequerimiento == ConstantesDyCNumerico.VALOR_2) {
                    fechaResultado = getDiaHabilService().buscarDiaFederalRecaudacion(
                            estatusRequerimientoDTO.getFechaNotificacion(),
                            ConstantesDyCNumerico.VALOR_11);
                }
            } else {
                fechaResultado = getDiaHabilService().buscarDiaFederalRecaudacion(
                        estatusRequerimientoDTO.getFechaNotificacion(),
                        ConstantesDyCNumerico.VALOR_21);
            }

            fechaHoy = new Date();

            SimpleDateFormat formatResultado = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);
            String fResultado = formatResultado.format(fechaResultado);

            SimpleDateFormat formatHoy = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);
            String fHoy = formatHoy.format(fechaHoy);

            try {

                fechaResultado = formatResultado.parse(fResultado);
                fechaHoy = formatHoy.parse(fHoy);

            } catch (ParseException e) {
                log.error("ERROR: " + e.getMessage());
            }

            if (null != fechaResultado && fechaHoy.after(fechaResultado)) {
                fechaMayor = Boolean.TRUE;
            }

        }

        boolean validacion = false;

        if (fechaMayor) {

            validacion = Boolean.TRUE;

            if (numControl.startsWith("DC")) {
                administrarSolicitudesService.actualizarEstados(numControl, numControlDoc,true);
            } else {
                administrarSolicitudesService.actualizarEstadosComp(numControl, numControlDoc);
            }

        }

        return validacion;
    }

    public void enviaDatos(String numeroControl, Integer numReq, Integer adm,
            String rfcContribuyente, String numControlDoc, String paginaAnterior) {
        setNumControl(numeroControl);
        setNumReq(numReq);
        setAdm(adm);
        setRfcContribuyente(rfcContribuyente);
        setNumControlDoc(numControlDoc);
        setPaginaAnterior(paginaAnterior);
    }

    /**
     * Agrega un documento adjunto a un documento requerido.
     */
    public void agregarDocumento() {
        long tamanoMax = ConstantesDyCNumerico.VALOR_4194304;
        ArchivoSolventadoDTO archivoSel = new ArchivoSolventadoDTO();
        ValidaArchivoUpload validacion = new ValidaArchivoUpload();
        try {
    
            if (!archivoYaExiste(file)) {

                log.info("archivo no existe!!");
                if (validaNumeroArchivo() && validacion.validaRuta(file)) {
                    if (validacion.validaTipo(tipoArchivo, file)) {
                        log.info("Tipo de archivo a subir: " + file.getContentType());

                        if (validacion.validaTamano(tamanoMax, file)) {
                            HttpServletRequest request = (HttpServletRequest) FacesContext
                                    .getCurrentInstance().getExternalContext().getRequest();

                            if (request.getHeader(CONTENT_SCANNING) == null) {
                                agregarDocumentoRequerido(archivoSel);
                            } else {
                                msg.setDetail("Se detectó virus en el archivo que se intenta subir");
                                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                            }
                        } else {
                            msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                                    ConstantesDyCNumerico.VALOR_4, ConstantesDyCNumerico.VALOR_37)
                                    .getDescripcion());
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    } else {
                        msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                                ConstantesDyCNumerico.VALOR_6, ConstantesDyCNumerico.VALOR_37)
                                .getDescripcion());
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
            }
        } catch (SIATException e) {
            log.error("Hubo un errror al obtener el mensaje: " + e);
        }
    }

    /**
     *
     * @param file archivo cargado
     * @return verdadero si el archivo ya esta en la lista de adjuntos
     */
    public boolean archivoYaExiste(UploadedFile file) {

        boolean yaExiste = Boolean.FALSE;

        FacesMessage errorArchivo
                = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo " + file.getFileName() + " ya existe");

        for (DocRequeridoDTO archivoUpload : lstDocumentosReqDTO) {

            for (ArchivoSolventadoDTO archivoSolventadoDTO : archivoUpload.getArchivoSol()) {

                if (archivoSolventadoDTO.getNombre().equals(file.getFileName())) {
                    FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
                    yaExiste = Boolean.TRUE;
                    break;
                }
            }
        }
        
        return yaExiste;
    }

    public boolean validaNumeroArchivo() {
        boolean validacion = Boolean.TRUE;

        FacesMessage errorArchivo = new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "",
                "Atención. Se ha alcanzado la cantidad máxima (999) de archivos para adjuntar en este punto requerido; si desea continuar adjuntando mas archivos, puedes incluirlos en otro punto requerido y hacer la aclaración correspondiente.");

        for (DocRequeridoDTO archivoUpload : lstDocumentosReqDTO) {

            if (archivoUpload.getIdTabla().equals(selectDocumentoReqDTO.getIdTabla())
                    && (selectDocumentoReqDTO.getOrden() == ConstantesDyCNumerico.VALOR_2)) {

                List<ArchivoSolventadoDTO> lista = archivoUpload.getArchivoSol();

                if (lista.size() >= ConstantesDyCNumerico.VALOR_999) {
                    FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
                    validacion = false;
                } else {
                    validacion = Boolean.TRUE;
                }
            }
        }

        return validacion;
    }

    /**
     * Agrega archivos a los documentos requeridos
     *
     * @param archivoSel
     */
    private void agregarDocumentoRequerido(ArchivoSolventadoDTO archivoSel) {

        try {
            obtenNombre();
            archivoSel.setNombre(getNom());
            archivoSel.setTamano(file.getSize());
            archivoSel.setDescripcion(descripcion);
            archivoSel.setArchivoFisico(file);
            archivoSel.setEstatus(ConstantesArchivo.EDO_ANEXO_ADJUNTADO);

            for (DocRequeridoDTO archivoUpload : lstDocumentosReqDTO) {

                if (archivoUpload.getIdTabla().equals(selectDocumentoReqDTO.getIdTabla())) {

                    archivoSel.setIdConsecutivoDoc(archivoUpload.getArchivoSol().size() == 0 ? 1
                            : ConstantesDyCNumerico.VALOR_2);
                    archivoUpload.getArchivoSol().add(archivoSel);

                }
            }
            msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                    ConstantesDyCNumerico.VALOR_7, ConstantesDyCNumerico.VALOR_37).getDescripcion());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            log.info(e);
        }

    }

    /**
     * Adiciona los documentos que son requeridos para los anexos listados en
     * pantalla.
     *
     * @throws SIATException
     */
    public void agregarAnexo() throws SIATException {

        long tamanoMax = ConstantesArchivo.TAMANO_ARCHIVO;
        ArchivoSolventadoDTO archivoSel = new ArchivoSolventadoDTO();
        ValidaArchivoUpload validacion = new ValidaArchivoUpload();

        if (validacion.validaRuta(file)) {
            if (validacion.validaTipo(tipoArchivo, file)) {
                log.info("Tipo de archivo a subir: " + file.getContentType());

                if (validacion.validaTamano(tamanoMax, file)) {
                    try {
                        HttpServletRequest request = (HttpServletRequest) FacesContext
                                .getCurrentInstance().getExternalContext().getRequest();
                        if (request.getHeader(CONTENT_SCANNING) == null) {
                            obtenNombre();
                            archivoSel.setNombre(getNom());
                            archivoSel.setTamano(file.getSize());
                            archivoSel.setDescripcion(descripcion);
                            archivoSel.setArchivoFisico(file);
                            archivoSel.setEstatus(ConstantesArchivo.EDO_ANEXO_ADJUNTADO);
                            archivoSel.setIdConsecutivoDoc(idTablaDoc);

                            for (AnexoRequeridoDTO archivoUpload : lstAnexosReqDTO) {
                                if (archivoUpload.getIdTabla().equals(
                                        selectAnexoReqDTO.getIdTabla())) {
                                    archivoUpload.setArchivoSol(archivoSel);
                                }
                            }
                        } else {
                            msg.setDetail("Se detectó virus en el archivo que se intenta subir");
                            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }

                        msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                                ConstantesDyCNumerico.VALOR_7, ConstantesDyCNumerico.VALOR_37)
                                .getDescripcion());
                        FacesContext.getCurrentInstance().addMessage(null, msg);

                    } catch (Exception e) {
                        log.info(e);
                    }
                } else {
                    msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                            ConstantesDyCNumerico.VALOR_4, ConstantesDyCNumerico.VALOR_37)
                            .getDescripcion());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                        ConstantesDyCNumerico.VALOR_6, ConstantesDyCNumerico.VALOR_37)
                        .getDescripcion());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    /**
     * Guarda todos los documentos adjuntados en pantalla y las notas escritas
     * en ella
     */
    public void solventarRequerimiento() {
        documentosAdjuntos = 0;
        anexosAdjuntos = 0;
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        RequestContext rc = RequestContext.getCurrentInstance();
        NotaExpedienteDTO notaExpedienteDTO = new NotaExpedienteDTO();
        SeguimientoDTO seguimientoDTO = new SeguimientoDTO();
        SolventacionRequerimientoVO objetoSolventar = null;

        setEstatusEnvio(ConstantesArchivo.MSG_SINENVIAR);

        validaDocumentoAnexo();

        if ((lstDocumentosReqDTO.size() == documentosAdjuntos)
                && (lstAnexosReqDTO.size() == anexosAdjuntos)) {

            seguimientoDTO.setIdAccionSeg(ConstantesDyCNumerico.VALOR_3);
            seguimientoDTO.setComentarios(getNotasAclaratorias());
            seguimientoDTO.setFecha(new Date());
            seguimientoDTO.setNumControlDoc(getNumControlDoc());
            seguimientoDTO.setResponsable(accesoUsr.getNombreCompleto());

            notaExpedienteDTO.setDescripcion(getNotasAclaratorias());
            notaExpedienteDTO.setFechaRegistro(new Date());
            notaExpedienteDTO.setNumControl(getNumControl());
            notaExpedienteDTO.setUsuario(accesoUsr.getNombreCompleto());

            objetoSolventar = new SolventacionRequerimientoVO();
            objetoSolventar.setNumControl(numControl);
            objetoSolventar.setNumControlDoc(numControlDoc);
            objetoSolventar.setIdEstadoSol(ConstantesDyCNumerico.VALOR_3);
            objetoSolventar.setIdEstadoReq(ConstantesDyCNumerico.VALOR_5);
            objetoSolventar.setSeguimientoDTO(seguimientoDTO);
            objetoSolventar.setNotaExpedienteDTO(notaExpedienteDTO);
            objetoSolventar.setIdTipoTramite(Integer.valueOf(estatusRequerimientoDTO
                    .getIdTipoTramite().toString()));
            objetoSolventar.setLstDocumentosReqDTO(lstDocumentosReqDTO);
            objetoSolventar.setLstAnexosReqDTO(lstAnexosReqDTO);
            objetoSolventar.setUrlArchivo(urlArchivo);

            if (accesoUsr.getTipoAuth().equals(String.valueOf(ConstantesDyCNumerico.VALOR_2))) {
                try {
                    solventarRequerimientoService.actualizarInformacion(objetoSolventar);
                } catch (SIATException e) {
                    log.error("Error al solventar: " + e);
                }

            } else {
                try {
                    ejecutaFielMB.setDatosSolventarRequerimiento(accesoUsr, objetoSolventar,
                            numControlDoc);
                    JSFUtils.getExternalContext().redirect(
                            JSFUtils.getExternalContext().getRequestContextPath()
                                    + ValidaDatosSolicitud.FIEL);
                } catch (Exception e) {
                    log.error("Error al pasar a la pantalla de firmado: " + e);
                }
            }

            msg.setDetail("Archivos enviados con exito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            setEstatusEnvio("Enviado");
            session.setAttribute("statusSolventacion", "solventado");
            setMensajeConfirmacion("Los documentos se guardaron correctamente");
            rc.update("idConfirmacion");
            rc.execute("confirmationSave.show();");

        } else {
            try {
                msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                        ConstantesDyCNumerico.VALOR_2, ConstantesDyCNumerico.VALOR_37)
                        .getDescripcion());
                setEstatusEnvio(ConstantesArchivo.MSG_SINENVIAR);
            } catch (SIATException e) {
                log.error(e);
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        notaExpedienteDTO = null;
        seguimientoDTO = null;
    }

    /**
     * Valida que el numero de documentos que se adjuntan por parte de los
     * documentos requeridos y de los anexos sean todos los que se piden en
     * pantalla.
     */
    public void validaDocumentoAnexo() {
        for (DocRequeridoDTO documentoReq : lstDocumentosReqDTO) {
            if (documentoReq.getArchivoSol() != null) {
                for (ArchivoSolventadoDTO archivoUpload : documentoReq.getArchivoSol()) {
                    if (archivoUpload.getEstatus().equals(ConstantesArchivo.EDO_ANEXO_ADJUNTADO)) {
                        documentosAdjuntos = documentosAdjuntos + 1;
                        break;
                    }
                }
            }
        }
        for (AnexoRequeridoDTO anexoReq : lstAnexosReqDTO) {
            if (anexoReq.getArchivoSol() != null
                    && anexoReq.getArchivoSol().getEstatus()
                            .equals(ConstantesArchivo.EDO_ANEXO_ADJUNTADO)) {
                anexosAdjuntos = anexosAdjuntos + 1;
            }
        }
    }

    /**
     * Borra un documento requerido que fue borrado en pantalla.
     */
    public void borrarDocumento() {
        try {
            for (DocRequeridoDTO archivoUpload : lstDocumentosReqDTO) {
                if ((archivoUpload.getIdTabla().equals(selectDocumentoReqDTO.getIdTabla()))
                        && archivoUpload.getArchivoSol().get(0).getEstatus()
                                .equals(ConstantesArchivo.EDO_ANEXO_ADJUNTADO)) {
                    archivoUpload.getArchivoSol().clear();
                    msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                            ConstantesDyCNumerico.VALOR_8, ConstantesDyCNumerico.VALOR_37)
                            .getDescripcion());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }

        } catch (Exception e) {
            log.error("Hubo un error al borrar un documento requerido en pantalla: " + e);
        }
    }

    /**
     * Quita un anexo que fue borrado en pantalla.
     */
    public void borrarAnexo() {
        try {
            for (AnexoRequeridoDTO archivoUpload : lstAnexosReqDTO) {
                if ((archivoUpload.getIdTabla().equals(selectAnexoReqDTO.getIdTabla()))
                        && archivoUpload.getArchivoSol().getEstatus()
                                .equals(ConstantesArchivo.EDO_ANEXO_ADJUNTADO)) {
                    archivoUpload.setArchivoSol(null);
                    msg.setDetail(consultarDyccMensajeUsrService.obtieneMensaje(
                            ConstantesDyCNumerico.VALOR_8, ConstantesDyCNumerico.VALOR_37)
                            .getDescripcion());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }

        } catch (Exception e) {
            log.error("Hubo un error al borrar el anexo: " + e);
        }
    }

    /**
     * Borra los datos en pantalla
     */
    public void limpiarDatos() {
        this.lstDocumentosReqDTO.clear();
        this.lstAnexosReqDTO.clear();
        this.notasAclaratorias = "";
        this.descripcion = "";
        setSelectDocumentoReqDTO(null);
    }

    /**
     * Cancela la solventacion y regresa a la pagina anterior sin guardar
     * registro en base de datos.
     *
     * @return
     */
    public String cancelar() {
        if (getPaginaAnterior() == null) {
            setPaginaAnterior("cancelaSolventacion");
        }
        if (getEstatusEnvio() != null && getEstatusEnvio().equals("Enviado")) {
            setEstatusEnvio(ConstantesArchivo.MSG_SINENVIAR);
        }
        return getPaginaAnterior();
    }

    public void obtenNombre() {
        String nomArchivo = file.getFileName();
        setNom(nomArchivo.substring(nomArchivo.lastIndexOf('\\') + 1, nomArchivo.length()));
    }

    public void downloadAnexoPlantilla() {
        descargarAnexo = archivo.descargarArchivo(seleccionoAnexoDescargar.getUrl());
        setDescargarAnexo(descargarAnexo);
    }

    public void setUtils(Utils utils) {
        this.utils = utils;
    }

    public Utils getUtils() {
        return utils;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    public void setSolventarRequerimientoService(
            SolventarRequerimientoService solventarRequerimientoService) {
        this.solventarRequerimientoService = solventarRequerimientoService;
    }

    public SolventarRequerimientoService getSolventarRequerimientoService() {
        return solventarRequerimientoService;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setLstDocumentosReqDTO(List<DocRequeridoDTO> lstDocumentosReqDTO) {
        this.lstDocumentosReqDTO = lstDocumentosReqDTO;
    }

    public List<DocRequeridoDTO> getLstDocumentosReqDTO() {
        return lstDocumentosReqDTO;
    }

    public void setLstAnexosReqDTO(List<AnexoRequeridoDTO> lstAnexosReqDTO) {
        this.lstAnexosReqDTO = lstAnexosReqDTO;
    }

    public List<AnexoRequeridoDTO> getLstAnexosReqDTO() {
        return lstAnexosReqDTO;
    }

    public void setSelectDocumentoReqDTO(DocRequeridoDTO selectDocumentoReqDTO) {
        this.selectDocumentoReqDTO = selectDocumentoReqDTO;
    }

    public DocRequeridoDTO getSelectDocumentoReqDTO() {
        return selectDocumentoReqDTO;
    }

    public void setSelectAnexoReqDTO(AnexoRequeridoDTO selectAnexoReqDTO) {
        this.selectAnexoReqDTO = selectAnexoReqDTO;
    }

    public AnexoRequeridoDTO getSelectAnexoReqDTO() {
        return selectAnexoReqDTO;
    }

    public void setEstatusRequerimientoDTO(EstatusRequerimientoDTO estatusRequerimientoDTO) {
        this.estatusRequerimientoDTO = estatusRequerimientoDTO;
    }

    public EstatusRequerimientoDTO getEstatusRequerimientoDTO() {
        return estatusRequerimientoDTO;
    }

    public void setMsg(FacesMessage msg) {
        this.msg = msg;
    }

    public FacesMessage getMsg() {
        return msg;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setListaArchivos(List<EntityBasicUpLoad> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public List<EntityBasicUpLoad> getListaArchivos() {
        return listaArchivos;
    }

    public void setNotasAclaratorias(String notasAclaratorias) {
        this.notasAclaratorias = notasAclaratorias;
    }

    public String getNotasAclaratorias() {
        return notasAclaratorias;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setConsultarDyccMensajeUsrService(
            DyccMensajeUsrService consultarDyccMensajeUsrService) {
        this.consultarDyccMensajeUsrService = consultarDyccMensajeUsrService;
    }

    public DyccMensajeUsrService getConsultarDyccMensajeUsrService() {
        return consultarDyccMensajeUsrService;
    }

    public void setTituloSolventar(String tituloSolventar) {
        this.tituloSolventar = tituloSolventar;
    }

    public String getTituloSolventar() {
        return tituloSolventar;
    }

    public void setNumReq(Integer numReq) {
        this.numReq = numReq;
    }

    public Integer getNumReq() {
        return numReq;
    }

    public void setAdm(Integer adm) {
        this.adm = adm;
    }

    public Integer getAdm() {
        return adm;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setMensajeConfirmacion(String mensajeConfirmacion) {
        this.mensajeConfirmacion = mensajeConfirmacion;
    }

    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }

    public void setEstatusEnvio(String estatusEnvio) {
        this.estatusEnvio = estatusEnvio;
    }

    public String getEstatusEnvio() {
        return estatusEnvio;
    }

    public void setSeleccionoAnexoDescargar(AnexoRequeridoDTO seleccionoAnexoDescargar) {
        this.seleccionoAnexoDescargar = seleccionoAnexoDescargar;
    }

    public AnexoRequeridoDTO getSeleccionoAnexoDescargar() {
        return seleccionoAnexoDescargar;
    }

    public void setDescargarAnexo(DefaultStreamedContent descargarAnexo) {
        this.descargarAnexo = descargarAnexo;
    }

    public DefaultStreamedContent getDescargarAnexo() {
        downloadAnexoPlantilla();
        return descargarAnexo;
    }

    public void setAcuseReciboService(AcuseReciboService acuseReciboService) {
        this.acuseReciboService = acuseReciboService;
    }

    public AcuseReciboService getAcuseReciboService() {
        return acuseReciboService;
    }

    public void setEjecutaFielMB(EjecutaFielMB ejecutaFielMB) {
        this.ejecutaFielMB = ejecutaFielMB;
    }

    public EjecutaFielMB getEjecutaFielMB() {
        return ejecutaFielMB;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public void setAdministrarSolicitudesService(
            AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    public void setDiaHabilService(DiaHabilService diaHabilService) {
        this.diaHabilService = diaHabilService;
    }

    public DiaHabilService getDiaHabilService() {
        return diaHabilService;
    }

    public void setBtnEnviar(boolean btnEnviar) {
        this.btnEnviar = btnEnviar;
    }

    public boolean isBtnEnviar() {
        return btnEnviar;
    }
}
