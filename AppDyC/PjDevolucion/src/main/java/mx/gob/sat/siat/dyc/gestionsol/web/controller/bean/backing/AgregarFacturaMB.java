package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.admcat.service.matrizaprobador.AprobadorService;
import mx.gob.sat.siat.dyc.domain.DyccEstadoReqDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccEstadoDocDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqConfProvDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.service.DycaDocumentoService;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.DycpSolicitudDevolucionDTO;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


/**
 * @author J. Isaac Carbajal Bernal
 */
@ManagedBean(name = "agregarFacturasMB")
@SessionScoped

public class AgregarFacturaMB {
    public AgregarFacturaMB() {
        super();
    }

    private static final Logger LOG = Logger.getLogger(AgregarFacturaMB.class);

    private static final String ADMINISTRARSOLICITUDESMB_SESSION = "administrarSolicitudesMB";

    @ManagedProperty(value = "#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    @ManagedProperty(value = "#{templateNumberService}")
    private TemplateNumberService templateNumberService;

    private DycpSolicitudDevolucionDTO dycpSolicitudDevolucionDTO;

    private List<DycpSolicitudDevolucionDTO> facturas = new ArrayList<DycpSolicitudDevolucionDTO>();

    private DycpSolicitudDevolucionDTO facturaToRemove;

    @ManagedProperty(value = "#{aprobadorService}")
    private AprobadorService aprobadorService;

    @ManagedProperty("#{dycaDocumentoService}")
    private DycaDocumentoService dycaDocumentoService;

    private String numControlDoc;
    private String numFactura;
    private String rfcProveedor;
    private Date fechaFactura;
    private String concepto;
    private BigDecimal importe;
    private BigDecimal ivaTrasladado;
    private String numControl;
    private BigDecimal total;
    private String importeS;
    private String ivaTrasladadoS;
    private String totalS;
    private boolean form;
    private Date fechaMax;
    private boolean varBotonGuardar = Boolean.TRUE;
    private boolean varBotonBorrar = Boolean.TRUE;
    private boolean varBotonEnviarAAprobacion = Boolean.TRUE;
    private boolean varBotonOcultar = Boolean.TRUE;

    private List<DyccAprobadorDTO> listaJefesSup = new ArrayList<DyccAprobadorDTO>();

    private String numEmpleadoStr;
    private int idUnidad;
    private int centroCosto;
    private Integer numEmp;
    private Long idDocumentoSeq;
    private String generarDocPlantilla;
    private String generarDocNumControl;
    private String generarDocRFC;

    private int numeroPlantilla;
    private int queryConsultar;

    private Boolean success;
    private String url;
    private String nombreArchivo;

    private boolean documentoCargar;

    private StreamedContent fileFacturas;

    private int claveAdm;

    private UploadedFile file;

    private ArchivoCargaDescarga archivo;

    private Mensaje mensaje;

    private boolean paginador;

    private boolean botonAceptar;

    private String nombreCompleto;

    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        rfcProveedor = getCountryParam(fc);
        obtenerFecha();
        varBotonGuardar = Boolean.TRUE;
        varBotonBorrar = Boolean.TRUE;
        varBotonOcultar = Boolean.TRUE;

        setVarBotonEnviarAAprobacion(Boolean.TRUE);
        cargarComboJefesSup();
        numEmp = null;
        success = Boolean.FALSE;

        documentoCargar = Boolean.FALSE;

        archivo = new ArchivoCargaDescarga();
        mensaje = new Mensaje();

        botonAceptar = Boolean.FALSE;

    }

    public void fileUpload() {

        /**   LOG.info("------------------- CARGAR DOCUMENTO ---------------");
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        String cabeceraVirus = request.getHeader("X-Content-Scanning");
        LOG.info("cabeceraVirus ->" + cabeceraVirus + "<-");

        if (cabeceraVirus != null) {
            LOG.info("Se detecto virus");
            FacesMessage mensajeFaces =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se detecto virus en el archivo que intenta subir!",
                                 null);
            FacesContext.getCurrentInstance().addMessage(null, mensajeFaces);
            return;
        } */

        String nom1 = null;
        String nomCorrecto1 = null;

        if (null != file) {
            nom1 = file.getFileName();
            nomCorrecto1 = nom1.substring(nom1.lastIndexOf('\\') + 1, nom1.length());
        }

        try {

            if (null == file) {

                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS, "Error: debe seleccionar un archivo");

            } else if (!nomCorrecto1.equals(nombreArchivo)) {

                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS,
                        "Error: el nombre del archivo no corresponde con el generado");

            } else {

                String nom = file.getFileName();
                String nomCorrecto = nom.substring(nom.lastIndexOf('\\') + 1, nom.length());

                if (nomCorrecto.length() > ConstantesDyCNumerico.VALOR_50) {

                    mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS,
                            "Error: tamaño de nombre de archivo inválido");

                } else {

                    archivo.escribirArchivo(nombreArchivo, file.getInputstream(), url,
                                            ConstantesDyCNumerico.VALOR_22005);

                    mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_REQS,
                            "Archivo: " + nomCorrecto + " cargado con éxito.");

                    setVarBotonEnviarAAprobacion(false);

                    documentoCargar = false;

                }
            }
        } catch (StringIndexOutOfBoundsException e) {

            LOG.error(e.getMessage());
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS, "Error: seleccione un archivo valido");

        } catch (IOException e) {

            LOG.error(e.getMessage());
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS, "Error al tratar de enviar el documento.");

        } catch (Exception e) {

            LOG.error(e.getMessage());
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS, "Error al tratar de enviar el documento.");

        }
    }


    public void downloadDocumentos() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(url.concat("/"));
        ruta.append(nombreArchivo);

        File tempFile = new File(ruta.toString());

        byte[] contenido = null;
        try {
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (IOException e) {
            LOG.error("ERROR: " + e.getMessage());
        }

        fileFacturas =
                new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                                           nombreArchivo);

    }

    public void initJefe() {
        numEmp = null;
    }


    public void valida() {

        int countErrores = 0;


        form = validaFormulario();
        if (form) {
            dycpSolicitudDevolucionDTO = null;
            DycpSolicitudDevolucionDTO input = new DycpSolicitudDevolucionDTO();
            input.setNumFactura(numFactura.toUpperCase());
            input.setRfcProveedor(rfcProveedor.toUpperCase());
            input.setFechaFactura(fechaFactura);
            input.setConcepto(concepto.toUpperCase());
            input.setImporte(importe);
            input.setIvaTrasladado(ivaTrasladado);
            input.setNumControl(numControl);
            input.setTotal(total);
            setDycpSolicitudDevolucionDTO(input);

            if (input.getNumFactura() == null) {
                muestraMensajeError("el campo número de factura es obligatorio");
                ++countErrores;
            }

            limpiarCampos();


            if (existeFacturaEnLista(rfcProveedor.toUpperCase(), input.getNumFactura())) {
                muestraMensajeError("la factura que intenta agregar ya está en la lista");
                ++countErrores;
            }

            try {
                if (existeFacturaEnBD(rfcProveedor.toUpperCase(), input.getNumFactura())) {
                    muestraMensajeError("la factura que intenta guardar ya fue registrada para este trámite");
                    ++countErrores;
                }
            } catch (SIATException e) {
                muestraMensajeError("por el momento no es posible consultar facturas existentes");
                ++countErrores;
            }

            if (countErrores == 0) {
                facturas.add(input);

                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_INFO, "", "La factura se guardó correctamente"));

                if (facturas.size() > ConstantesDyCNumerico.VALOR_5) {
                    paginador = Boolean.TRUE;
                } else {
                    paginador = Boolean.FALSE;
                }

            }


            if (facturas.size() > 0) {
                setVarBotonGuardar(Boolean.FALSE);
            }
        }

    }

    private boolean existeFacturaEnLista(String rfcProveedor, String numFactura) {
        boolean encontrado = Boolean.FALSE;


        for (DycpSolicitudDevolucionDTO factura : facturas) {


            if (factura.getNumFactura().equals(numFactura) && factura.getRfcProveedor().equals(rfcProveedor)) {
                encontrado = Boolean.TRUE;
                break;
            }

        }

        return encontrado;

    }


    public void muestraMensajeError(String error) {
        FacesMessage errorExiste = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", error);
        FacesContext.getCurrentInstance().addMessage(null, errorExiste);
    }

    /**
     * Validacion del formulario Mensaje16
     */
    public boolean validaFormulario() {
        boolean errorImp = Boolean.FALSE;
        boolean errorTol = Boolean.FALSE;
        boolean errorIVA = Boolean.FALSE;

        boolean errorNumFactura = Boolean.FALSE;
        boolean errorFechaFactura = Boolean.FALSE;
        boolean errorConcepto = Boolean.FALSE;

        String a = "";
        if (numFactura.equals(a)) {
            muestraMensajeError("el campo número de factura es obligatorio");
        } else {
            errorNumFactura = Boolean.TRUE;
        }

        if (fechaFactura == null) {
            muestraMensajeError("el campo fecha es obligatorio");
        } else {
            errorFechaFactura = Boolean.TRUE;
        }

        if (concepto.equals(a)) {
            muestraMensajeError("el campo concepto es obligatorio");
        } else {
            errorConcepto = Boolean.TRUE;
        }

        if (null == importeS) {
            muestraMensajeError("el campo importe es obligatorio");
        } else {
            //Importe
            errorImp = importe();
        }

        if (null == ivaTrasladadoS) {
            muestraMensajeError("el campo IVA trasladado es obligatorio");
        } else {
            //IVA Trasladado
            errorIVA = ivaTrasladado();
        }

        if (null == totalS) {
            muestraMensajeError("el campo total es obligatorio");
        } else {
            //total
            errorTol = total();
        }

        boolean error = validaciones(errorImp, errorTol, errorIVA, errorNumFactura, errorFechaFactura, errorConcepto);

        return error;
    }

    public boolean validaciones(boolean errorImp, boolean errorTol, boolean errorIVA, boolean errorNumFactura,
                                boolean errorFechaFactura, boolean errorConcepto) {
        boolean errorF = errorImp && errorTol && errorIVA && errorNumFactura ? Boolean.TRUE : Boolean.FALSE;
        boolean error = errorF && errorFechaFactura && errorConcepto ? Boolean.TRUE : Boolean.FALSE;

        return error;
    }

    public boolean importe() {
        boolean imp;
        try {
            importe = new BigDecimal(importeS);
            imp = Boolean.TRUE;
        } catch (Exception e) {
            muestraMensajeError("el campo importe debe ser numerico");
            imp = Boolean.FALSE;
        }
        return imp;
    }

    public boolean ivaTrasladado() {
        //IVA Trasladado
        boolean iva;
        try {
            ivaTrasladado = new BigDecimal(ivaTrasladadoS);
            iva = Boolean.TRUE;
        } catch (Exception e) {
            muestraMensajeError("el campo IVA trasladado debe ser numerico");
            iva = Boolean.FALSE;
        }
        return iva;
    }

    public boolean total() {
        boolean tol;
        try {
            total = new BigDecimal(totalS);
            tol = Boolean.TRUE;
        } catch (Exception e) {
            muestraMensajeError("el campo total debe ser numerico");
            tol = Boolean.FALSE;
        }
        return tol;
    }

    public boolean existeFacturaEnBD(String rfcProveedor, String numFactura) throws SIATException {
        return dycpSolicitudService.buscarNumFactura(numFactura, rfcProveedor);
    }

    /*mabc
     * */

    public String insertarFactura() {

        boolean facturaInsertada = Boolean.FALSE;

        if (numEmp == null) {
            muestraMensajeError("debe seleccionar un jefe");
            return null;
        }

        idDocumentoSeq = getAdministrarSolicitudesService().getIdDocumento();

        DyctDocumentoDTO dyctDocumentoDTO = llenarObjeto();

        List<DyctFacturaReqDTO> lstDyctFacturaReqDTO = new ArrayList<DyctFacturaReqDTO>();

        for (DycpSolicitudDevolucionDTO dycpSolicitudDevolucionDTOD : facturas) {
            DyctFacturaReqDTO dyctFacturaReqDTO = new DyctFacturaReqDTO();

            DyctDocumentoDTO dyctDocumentoDTO1 = new DyctDocumentoDTO();
            dyctDocumentoDTO1.setNumControlDoc(this.getNumControlDoc());

            DyctReqConfProvDTO dyctReqConfProvDTO = new DyctReqConfProvDTO();
            dyctReqConfProvDTO.setDyctDocumentoDTO(dyctDocumentoDTO1);

            dyctFacturaReqDTO.setNumeroFactura(dycpSolicitudDevolucionDTOD.getNumFactura());
            dyctFacturaReqDTO.setDyctReqConfProvDTO(dyctReqConfProvDTO);
            dyctFacturaReqDTO.setFechaEmision(new Date());
            dyctFacturaReqDTO.setConcepto(dycpSolicitudDevolucionDTOD.getConcepto());
            dyctFacturaReqDTO.setImporte(dycpSolicitudDevolucionDTOD.getImporte());
            dyctFacturaReqDTO.setIvaTrasladado(dycpSolicitudDevolucionDTOD.getIvaTrasladado());
            dyctFacturaReqDTO.setTotal(dycpSolicitudDevolucionDTOD.getTotal());

            lstDyctFacturaReqDTO.add(dyctFacturaReqDTO);
        }


        try {
            facturaInsertada =
                    dycpSolicitudService.insertarFacturas(dyctDocumentoDTO, null, lstDyctFacturaReqDTO, rfcProveedor.toUpperCase(),
                                                          nombreCompleto);

            if (facturaInsertada) {
                facturas.clear();

                setVarBotonEnviarAAprobacion(Boolean.TRUE);
                setVarBotonGuardar(Boolean.TRUE);

                documentoCargar = Boolean.FALSE;

                this.setNumEmp(null);

                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_INFO, "El documento se mandó a aprobación correctamente",
                                                                              null));

            } else {

                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al intentar mandar el documento a aprobación",
                                                                              null));

            }
        } catch (SIATException e) {

            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al intentar mandar el documento a aprobación",
                                                                          null));
        }

        numEmp = null;

        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        AdministrarSolicitudesMB referenciaBeanSession =
            contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION) == null ? new AdministrarSolicitudesMB() :
            (AdministrarSolicitudesMB)contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION);
        referenciaBeanSession.buscarSolicitudes1();

        return "adminiSolicitudes";

    }

    public DyctDocumentoDTO llenarObjeto() {

        DyccTipoDocumentoDTO dyccTipoDocumentoDTO = new DyccTipoDocumentoDTO();
        dyccTipoDocumentoDTO.setIdTipoDocumento(ConstantesDyCNumerico.VALOR_3);

        DyccEstadoDocDTO dyccEstadoDocDTO = new DyccEstadoDocDTO();
        dyccEstadoDocDTO.setIdEstadoDoc(ConstantesDyCNumerico.VALOR_5);

        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        dycpServicioDTO.setNumControl(this.numControl);

        DyccEstadoReqDTO dyccEstadoReqDTO = new DyccEstadoReqDTO();
        dyccEstadoReqDTO.setIdEstadoReq(ConstantesDyCNumerico.VALOR_1);

        DyccAprobadorDTO dyccAprobadorDTO = new DyccAprobadorDTO();
        dyccAprobadorDTO.setNumEmpleado(numEmp);

        DyccMatDocumentosDTO plantilla = new DyccMatDocumentosDTO();
        plantilla.setIdPlantilla(numeroPlantilla);

        DyctDocumentoDTO dyctDocumentoDTO = new DyctDocumentoDTO();

        dyctDocumentoDTO.setDyccMatDocumentosDTO(plantilla);
        dyctDocumentoDTO.setNumControlDoc(this.getNumControlDoc());
        dyctDocumentoDTO.setDyccTipoDocumentoDTO(dyccTipoDocumentoDTO);
        dyctDocumentoDTO.setUrl(url);
        dyctDocumentoDTO.setFechaRegistro(new Date());
        dyctDocumentoDTO.setNombreArchivo(nombreArchivo);
        dyctDocumentoDTO.setDyccEstadoReqDTO(dyccEstadoReqDTO);
        dyctDocumentoDTO.setDyccEstadoDocDTO(dyccEstadoDocDTO);
        dyctDocumentoDTO.setDycpServicioDTO(dycpServicioDTO);
        dyctDocumentoDTO.setDyccAprobadorDTO(dyccAprobadorDTO);

        return dyctDocumentoDTO;

    }

    public void mantenerDocumento() {
LOG.info("mantenerDocumento");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat formatoValores = new DecimalFormat("$#,##0.00");

        /** if (idUnidad == ConstantesDyCNumerico.VALOR_81 || idUnidad == ConstantesDyCNumerico.VALOR_82) {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_135;
        } else {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_3;
        } */

        numeroPlantilla = ConstantesDyCNumerico.VALOR_3;

        queryConsultar = ConstantesDyCNumerico.VALOR_1;

        List<String> listaFolios = new ArrayList<String>();
        List<String> listaFechas = new ArrayList<String>();
        List<String> listaConceptos = new ArrayList<String>();
        List<String> listaImportes = new ArrayList<String>();
        List<String> listaIva = new ArrayList<String>();
        List<String> listaTotales = new ArrayList<String>();

        for (DycpSolicitudDevolucionDTO factura : facturas) {
            listaFolios.add(factura.getNumFactura());
            listaFechas.add(formatoFecha.format(factura.getFechaFactura()));
            listaConceptos.add(factura.getConcepto());
            listaImportes.add(String.valueOf(formatoValores.format(factura.getImporte())));
            listaIva.add(String.valueOf(formatoValores.format(factura.getIvaTrasladado())));
            listaTotales.add(String.valueOf(formatoValores.format(factura.getTotal())));
        }

        Map<String, Object> datos = new HashMap<String, Object>();

        datos.put("idPlantilla", numeroPlantilla);
        datos.put("numControl", numControl);
        datos.put("claveAdm", claveAdm);
        datos.put("rfcProveedor", rfcProveedor.toUpperCase());
        datos.put("queryAConsultar", queryConsultar);
        datos.put("listaFolios", listaFolios);
        datos.put("listaFechas", listaFechas);
        datos.put("CONCEPTO", listaConceptos);
        datos.put("listaImportes", listaImportes);
        datos.put("listaIva", listaIva);
        datos.put("listaTotales", listaTotales);

        try {

            Map<String, Object> datosRespuesta;


            datosRespuesta = templateNumberService.templateCreated(datos);


            cargarGenerarDocumento(datosRespuesta);

        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }

        setVarBotonOcultar(Boolean.FALSE);

        /**  setVarBotonEnviarAAprobacion(false); */

    }

    public void cargarGenerarDocumento(Map<String, Object> respuesta) {

        success = (Boolean)respuesta.get("success");

        if (success) {

            url = (String)respuesta.get("url");
            nombreArchivo = (String)respuesta.get("nombreArchivo");
            numControlDoc = (String)respuesta.get("NUMERODOCUMENTO");

            documentoCargar = Boolean.TRUE;

            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El documento se generó correctamente"));

            RequestContext.getCurrentInstance().execute("document.getElementById('docGen').click()");

            botonAceptar = Boolean.TRUE;

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ocurrió un error al generar el documento, intentelo de nuevo"));
        }

    }


    public void eliminarFactura(DycpSolicitudDevolucionDTO factura) {
        facturas.remove(factura);

        if (facturas.size() == 0) {
            setVarBotonGuardar(Boolean.TRUE);
        } else {
            setVarBotonGuardar(Boolean.FALSE);
        }
    }


    public String eliminarFactura() {

        if (facturaToRemove != null) {
            facturas.remove(facturaToRemove);

            if (facturas.size() > ConstantesDyCNumerico.VALOR_5) {
                paginador = Boolean.TRUE;
            } else {
                paginador = Boolean.FALSE;
            }

        }

        if (facturas.size() == 0) {
            setVarBotonOcultar(Boolean.TRUE);
            setVarBotonGuardar(Boolean.TRUE);
            documentoCargar = Boolean.FALSE;

        } else {
            setVarBotonGuardar(Boolean.FALSE);
        }

        return "";
    }


    public void obtenerFecha() {
        fechaMax = new Date();
    }


    public String irAAdministrarSolicitudes() {

        numFactura = "";
        concepto = "";
        importe = BigDecimal.ZERO;
        ivaTrasladado = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
        fechaFactura = null;

        facturas.clear();

        if (facturas.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador = Boolean.TRUE;
        } else {
            paginador = Boolean.FALSE;
        }

        numEmp = null;

        return "administrarSolicitudesDevolucion";
    }

    private void limpiarCampos() {
        numFactura = "";
        fechaFactura = null;
        concepto = "";
        importe = BigDecimal.ZERO;
        ivaTrasladado = BigDecimal.ZERO;
        total = BigDecimal.ZERO;

        importeS = "";
        ivaTrasladadoS = "";
        totalS = "";

    }

    public void cargarComboJefesSup() {

        setListaJefesSup(this.aprobadorService.consultarAprobadores(getIdUnidad(), centroCosto));

    }

    public void accionGuardarJefes() {

        try {
            getDycaDocumentoService().actualizarDocumentoAprobacion(numEmp, idDocumentoSeq);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }

        FacesContext.getCurrentInstance().addMessage(null,
                                                     new FacesMessage(FacesMessage.SEVERITY_INFO, "El documento se mandó a aprobación correctamente",
                                                                      null));
        setVarBotonEnviarAAprobacion(Boolean.TRUE);
    }

    public void onRowSelect() {
        setVarBotonBorrar(Boolean.FALSE);
    }

    public void setFacturaToRemove(DycpSolicitudDevolucionDTO factura) {
        facturaToRemove = factura;
    }

    public DycpSolicitudDevolucionDTO getFacturaToRemove() {
        return facturaToRemove;
    }

    public String getCountryParam(FacesContext fc) {

        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("rfcProveedor");

    }


    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setDycpSolicitudDevolucionDTO(DycpSolicitudDevolucionDTO dycpSolicitudDevolucionDTO) {
        this.dycpSolicitudDevolucionDTO = dycpSolicitudDevolucionDTO;
    }

    public DycpSolicitudDevolucionDTO getDycpSolicitudDevolucionDTO() {
        return dycpSolicitudDevolucionDTO;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura.toUpperCase().trim();
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setRfcProveedor(String rfcProveedor) {
        this.rfcProveedor = rfcProveedor.toUpperCase().trim();
    }

    public String getRfcProveedor() {
        return rfcProveedor;
    }

    public void setFechaFactura(Date fechaFactura) {
        if (fechaFactura != null) {
            this.fechaFactura = (Date)fechaFactura.clone();
        } else {
            this.fechaFactura = null;
        }
    }

    public Date getFechaFactura() {
        if (fechaFactura != null) {
            return (Date)fechaFactura.clone();
        } else {
            return null;
        }
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto.toUpperCase().trim();
    }

    public String getConcepto() {
        return concepto;
    }


    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setFechaMax(Date fechaMax) {
        if (null != fechaMax) {
            this.fechaMax = (Date)fechaMax.clone();
        } else {
            this.fechaMax = null;
        }
    }

    public Date getFechaMax() {
        if (null != fechaMax) {
            return (Date)fechaMax.clone();
        } else {
            return null;
        }
    }

    public void setImporteS(String importeS) {
        this.importeS = importeS;
    }

    public String getImporteS() {
        return importeS;
    }

    public void setIvaTrasladadoS(String ivaTrasladadoS) {
        this.ivaTrasladadoS = ivaTrasladadoS;
    }

    public String getIvaTrasladadoS() {
        return ivaTrasladadoS;
    }

    public void setTotalS(String totalS) {
        this.totalS = totalS;
    }

    public String getTotalS() {
        return totalS;
    }

    public void setForm(boolean form) {
        this.form = form;
    }

    public boolean isForm() {
        return form;
    }

    public List<DycpSolicitudDevolucionDTO> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<DycpSolicitudDevolucionDTO> facturas) {
        this.facturas = facturas;
    }


    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }


    public boolean isVarBotonGuardar() {
        return varBotonGuardar;
    }

    public void setVarBotonGuardar(boolean varBotonGuardar) {
        this.varBotonGuardar = varBotonGuardar;
    }


    public boolean isVarBotonBorrar() {
        return varBotonBorrar;
    }

    public void setVarBotonBorrar(boolean varBotonBorrar) {
        this.varBotonBorrar = varBotonBorrar;
    }


    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public List<DyccAprobadorDTO> getListaJefesSup() {
        return listaJefesSup;
    }

    public void setListaJefesSup(List<DyccAprobadorDTO> listaJefesSup) {
        this.listaJefesSup = listaJefesSup;
    }


    public boolean isVarBotonEnviarAAprobacion() {
        return varBotonEnviarAAprobacion;
    }

    public void setVarBotonEnviarAAprobacion(boolean varBotonEnviarAAprobacion) {
        this.varBotonEnviarAAprobacion = varBotonEnviarAAprobacion;
    }


    public Integer getNumEmp() {
        return numEmp;
    }

    public void setNumEmp(Integer numEmp) {
        this.numEmp = numEmp;
    }

    public DycaDocumentoService getDycaDocumentoService() {
        return dycaDocumentoService;
    }

    public void setDycaDocumentoService(DycaDocumentoService dycaDocumentoService) {
        this.dycaDocumentoService = dycaDocumentoService;
    }


    public String getGenerarDocPlantilla() {
        return generarDocPlantilla;
    }

    public void setGenerarDocPlantilla(String generarDocPlantilla) {
        this.generarDocPlantilla = generarDocPlantilla;
    }

    public String getGenerarDocNumControl() {
        return generarDocNumControl;
    }

    public void setGenerarDocNumControl(String generarDocNumControl) {
        this.generarDocNumControl = generarDocNumControl;
    }

    public String getGenerarDocRFC() {
        return generarDocRFC;
    }

    public void setGenerarDocRFC(String generarDocRFC) {
        this.generarDocRFC = generarDocRFC;
    }

    public void setNumeroPlantilla(int numeroPlantilla) {
        this.numeroPlantilla = numeroPlantilla;
    }

    public int getNumeroPlantilla() {
        return numeroPlantilla;
    }

    public void setQueryConsultar(int queryConsultar) {
        this.queryConsultar = queryConsultar;
    }

    public int getQueryConsultar() {
        return queryConsultar;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }


    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setFileFacturas(StreamedContent fileFacturas) {
        this.fileFacturas = fileFacturas;
    }

    public StreamedContent getFileFacturas() {
        return fileFacturas;
    }

    public void setClaveAdm(int claveAdm) {
        this.claveAdm = claveAdm;
    }

    public int getClaveAdm() {
        return claveAdm;
    }

    public void setTemplateNumberService(TemplateNumberService templateNumberService) {
        this.templateNumberService = templateNumberService;
    }

    public TemplateNumberService getTemplateNumberService() {
        return templateNumberService;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setDocumentoCargar(boolean documentoCargar) {
        this.documentoCargar = documentoCargar;
    }

    public boolean isDocumentoCargar() {
        return documentoCargar;
    }

    public void setArchivo(ArchivoCargaDescarga archivo) {
        this.archivo = archivo;
    }

    public ArchivoCargaDescarga getArchivo() {
        return archivo;
    }

    public void setPaginador(boolean paginador) {
        this.paginador = paginador;
    }

    public boolean isPaginador() {
        return paginador;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setAprobadorService(AprobadorService aprobadorService) {
        this.aprobadorService = aprobadorService;
    }

    public AprobadorService getAprobadorService() {
        return aprobadorService;
    }

    public void setVarBotonOcultar(boolean varBotonOcultar) {
        this.varBotonOcultar = varBotonOcultar;
    }

    public boolean isVarBotonOcultar() {
        return varBotonOcultar;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setIvaTrasladado(BigDecimal ivaTrasladado) {
        this.ivaTrasladado = ivaTrasladado;
    }

    public BigDecimal getIvaTrasladado() {
        return ivaTrasladado;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setCentroCosto(int centroCosto) {
        this.centroCosto = centroCosto;
    }

    public int getCentroCosto() {
        return centroCosto;
    }

    public void setBotonAceptar(boolean botonAceptar) {
        this.botonAceptar = botonAceptar;
    }

    public boolean isBotonAceptar() {
        return botonAceptar;
    }
}
