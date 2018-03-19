package mx.gob.sat.siat.pjenvionyv.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.inconsistencia.DyccInconsistenciaDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.dao.util.DycaActoAdmtvosDAO;

import mx.gob.sat.siat.dyc.domain.documento.DycaActoAdmtvosDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloColasDTO;
import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloDetalleDTO;
import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vistas.dao.DycvEmpleadoDAO;
import mx.gob.sat.siat.nyv.sistema.webservice.DocumentoGeneradoVO;
import mx.gob.sat.siat.nyv.sistema.webservice.RegistroActoAdministrativoVO;
import mx.gob.sat.siat.nyv.sistema.webservice.ResponseRegistro;
import mx.gob.sat.siat.nyv.sistema.webservice.WSNyVSistemaImplProxy;
import mx.gob.sat.siat.pjenvionyv.dao.EnvioANyVDAO;
import mx.gob.sat.siat.pjenvionyv.generico.util.common.CamposUtil;
import mx.gob.sat.siat.pjenvionyv.generico.util.common.JODConverter;
import mx.gob.sat.siat.pjenvionyv.service.GeneraFallosService;
import mx.gob.sat.siat.pjenvionyv.service.RealizarEnvioService;

import org.apache.axis.AxisProperties;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Esta clase se encarga de realizar el envio de todos los documentos que se van
 * a enviar a NyV, cada documento que se envia es tomado de un documento de word
 * y se convierte a pdf para poder ser enviado.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "realizarEnvioService")
@Scope(value = "prototype")
public class RealizarEnvioServiceImpl implements RealizarEnvioService {

    public static final int FIRMA_FIN = 172;
    public static final int FIRMA_INICIO = 164;

    private static final String DIAGONAL = "/";
    private static final String DOCX = "docx";
    private static final String PDF = "pdf";
    private static final String NYV = "rutaFileSystemNyV";
    private static final String DYC = "/siat/dyc";

    private static final String ESTATUS_DOCUMENTO_NYV = "GENERADO CON FIRMA";
    private static final String PROCESOORIGEN = "DEVOLUCIONES Y COMPENSACIONES";
    private static final String LOG_LLENAR_DATOS_DOCUMENTO = "llenarDatosDocumento: ";
    private static final String LOG_GENERA_FALLO = "generaFallo: ";
    private static final String LOG_FORMAT_DOUBLE_STRING = "%s: %s";
    private static final Logger LOGGER = Logger.getLogger(RealizarEnvioServiceImpl.class);

    private static final Integer DYCC_FALLOCOLA_DYC = 1;
    private static final Integer DYCC_FALLOCOLA_NYV = 2;
    private static final Integer MARCA_NO_OK_RESPUESTA = 0;
    private static final Integer MARCA_AMPARADO = 1;
    private static final Integer MARCA_SINMEDIOSDECONTACTO = 2;
    private static final Integer MARCA_TIENEFECHANOTIFICACION = null;
    private static final Integer ID_INCONSISTENCIA_AMPARADO = 9;
    private static final Integer ID_INCONSISTENCIA_SINMEDIOSDECONTACTO = 10;

    private static final String ERROR_DATOS_APROBADOR = "Hubo un error al buscar los datos del aprobador";
    private static final String ERROR_GENERA_DIRECTORIO = "No se pudo generar el directorio";
    private static final String ERROR_GENERA_FALLO = "No se pudo insertar el fallo con el mensaje";
    private static final String ERROR_LLAMADO_NYVWS = "Error al llamar el WS de NYV";
    private static final String ERROR_NYV = "Error al guardar el archivo con el WS de NyV, causa";
    private static final String ERROR_CONVERTIR_DOCUMENTO_WORD = "No se pudo convertir el documento de word. Causa";
    private static final String ERROR_EMPLEADO_NULL = "No se obtuvieron los datos del empleado";
    private static final String MENSAJE_AMPARADO = "Contribuyente amparado.";
    private static final String MENSAJE_SINMEDIOSDECONTACTO = "Contribuyente sin medios de contacto.";
    private static final String MENSAJE_INCONSISTENCIA_UPDATE = " Documento No. "; 

    @Autowired
    private DycaActoAdmtvosDAO dycaActoAdmtvosDAO;

    @Autowired
    private DyccAprobadorDAO dyccAprobadorDAO;

    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;

    @Autowired
    private IDycpServicioDAO iDycpServicioDAO;

    @Autowired
    private JODConverter jodConverter;

    @Autowired
    private DycvEmpleadoDAO satSiat01MVDAO;

    @Autowired
    private EnvioANyVDAO envioANyVDAO;

    @Autowired
    private GeneraFallosService generaFallosService;

    @Autowired
    private DyccInconsistenciaDAO dyccInconsistenciaDAO;

    public RealizarEnvioServiceImpl() {
        super();
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void realizarCambios(String mensaje) throws SIATException {
        LOGGER.debug("realizarCambios");
        LOGGER.info("realizarCambios mensaje: " + mensaje);

        DyctDocumentoDTO objetoDocumento = null;
        FileInputStream archivo = null;

        objetoDocumento = dyctDocumentoDAO.encontrar(mensaje);
        LOGGER.debug(objetoDocumento);
        if (objetoDocumento == null) {
            return;
        }
        String rutaNyV = "";
        ResponseRegistro respuesta = null;
        DycvEmpleadoDTO objetoEmpleado = null;
        DyccAprobadorDTO objetoAprobador = null;
        DycaActoAdmtvosDTO objetoActoAdmtvo = null;
        StringBuilder rutaDocumentoWord = null;
        StringBuilder rutaDocumentoPdf = null;
        Properties propiedades = new Properties();
        Calendar fechaDocumento = new GregorianCalendar();
        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
        } catch (IOException e) {
            throw new SIATException(e);
        } finally {
            try {
                archivo.close();
            } catch (Exception e) {
                LOGGER.error("Error al cerrar el archivo de configuracion: " + e);
            }
        }

        DycpServicioDTO objetoServicio
                = iDycpServicioDAO.encontrar(objetoDocumento.getDycpServicioDTO().getNumControl());
        LOGGER.debug(ExtractorUtil.ejecutar(objetoServicio));
        LOGGER.error("DycpServicioDTO objetoServicio.getNumControl: " + objetoServicio.getNumControl());
        LOGGER.info("nyv switcheo");
        AxisProperties.setProperty("http.nonProxyHosts", propiedades.getProperty("urlAmbiente"));
        String endpoint;
        if (propiedades.getProperty("activoFase2NYV").equals("1")) {
            LOGGER.error("activo fase 2");
            endpoint = propiedades.getProperty("endPointNYVFaseII");
        } else {
            LOGGER.error("activo fase 1");
            endpoint = propiedades.getProperty("endPoint");
        }
        LOGGER.info("endpoint: " + endpoint);
        WSNyVSistemaImplProxy wSNyVSistemaImpl = new WSNyVSistemaImplProxy(endpoint);
        RegistroActoAdministrativoVO registroActoAdministrativoVO = new RegistroActoAdministrativoVO();

        fechaDocumento.setTime(objetoDocumento.getFechaRegistro());
        objetoAprobador = dyccAprobadorDAO.encontrar(objetoDocumento.getDyccAprobadorDTO().getNumEmpleado());

        if (objetoAprobador != null) {
            try {
                LOGGER.info("objetoAprobador.getNumEmpleado(): " + objetoAprobador.getNumEmpleado());
                objetoEmpleado = satSiat01MVDAO.consultarXIDEmpleado(objetoAprobador.getNumEmpleado());
                objetoActoAdmtvo
                        = dycaActoAdmtvosDAO.consultar(objetoAprobador.getCentroCosto(), objetoDocumento.getDyccMatDocumentosDTO().getIdPlantilla());
                if (propiedades.getProperty("activoFase2NYV").equals("1")) {
                    registroActoAdministrativoVO.setPlazo(0);
                    objetoActoAdmtvo.setCveActoAdmtvo(objetoActoAdmtvo.getCveActoAdmtvoFase2());
                }
                LOGGER.debug(ExtractorUtil.ejecutar(objetoActoAdmtvo));

                DocumentoGeneradoVO[] listaDocumentos = new DocumentoGeneradoVO[1];
                listaDocumentos[0]
                        = llenarDatosDocumento(objetoDocumento.getCadenaOriginal(), objetoDocumento, objetoActoAdmtvo,
                                objetoEmpleado);
                LOGGER.info("metodo llenarDatosDocumento() terminado");
                registroActoAdministrativoVO.setClaveUnidadAdmin(objetoActoAdmtvo.getCveUnidadAdmtva().toString());
                registroActoAdministrativoVO.setClaveActoAdmin(objetoActoAdmtvo.getCveActoAdmtvo());
                registroActoAdministrativoVO.setProcesoOrigen(PROCESOORIGEN);
                registroActoAdministrativoVO.setNumeroReferencia(objetoActoAdmtvo.getPrefijo()
                        + objetoDocumento.getNumControlDoc());
                registroActoAdministrativoVO.setNumeroReferenciaOrigen(objetoDocumento.getDycpServicioDTO().getNumControl());
                registroActoAdministrativoVO.setRfcDestinatario(objetoServicio.getRfcContribuyente());
                registroActoAdministrativoVO.setNumeroEmpleado(String.format("%011d", objetoEmpleado.getNoEmpleado()));
                registroActoAdministrativoVO.setDocumentos(listaDocumentos);
                LOGGER.info("se ejecuta actualiarPDFenBaseyFileSystem()");
                rutaNyV = actualiarPDFenBaseyFileSystem(objetoDocumento, objetoActoAdmtvo);
                LOGGER.error("se ejecuta registrarActoAdministrativo()");
                respuesta = wSNyVSistemaImpl.registrarActoAdministrativo(registroActoAdministrativoVO);
                LOGGER.error("respuesta: " + respuesta);
                LOGGER.error("getFolio: " + respuesta.getFolio());
                LOGGER.error("getMensajeRespuesta: " + respuesta.getMensajeRespuesta());
                LOGGER.error("getCodigoRespuesta: " + respuesta.getCodigoRespuesta());

            } catch (RemoteException ex) {
                generaFallo(String.format(LOG_FORMAT_DOUBLE_STRING, ERROR_LLAMADO_NYVWS, ex.getMessage()),
                        DYCC_FALLOCOLA_NYV, objetoDocumento.getNumControlDoc(),
                        objetoActoAdmtvo.getNombrePlantilla(), objetoActoAdmtvo.getCveActoAdmtvo(),
                        objetoActoAdmtvo.getCveUnidadAdmtva(),
                        objetoDocumento.getDycpServicioDTO().getNumControl());
                throw new SIATException(ERROR_LLAMADO_NYVWS + ": " + ex);
            }

        } else {
            throw new SIATException(ERROR_DATOS_APROBADOR);
        }

        /**
         * se marcan como Contribuyente amparado. 1 Contribuyente sin medios de
         * contacto. 2
         */
        if (respuesta.getCodigoRespuesta().equals("NO_OK")) {
            LOGGER.error(ExtractorUtil.ejecutar(llenarDatosDocumento(objetoDocumento.getCadenaOriginal(),
                    objetoDocumento, objetoActoAdmtvo,
                    objetoEmpleado)));
            LOGGER.error(ExtractorUtil.ejecutar(registroActoAdministrativoVO));
            LOGGER.error("Error al guardar el archivo con NyV, causa: " + respuesta.getMensajeRespuesta());
            generaFallo(String.format(LOG_FORMAT_DOUBLE_STRING, ERROR_NYV, respuesta.getMensajeRespuesta()),
                    DYCC_FALLOCOLA_NYV, objetoDocumento.getNumControlDoc(), objetoActoAdmtvo.getNombrePlantilla(),
                    objetoActoAdmtvo.getCveActoAdmtvo(), objetoActoAdmtvo.getCveUnidadAdmtva(),
                    objetoDocumento.getDycpServicioDTO().getNumControl());
            // se agrega la marca al documento de la fecha de notificacion
            agregarMarcaFechaNotificacion(respuesta, objetoDocumento.getNumControlDoc());
            // se guarda la inconsitencia
            guardarInconsistenciaRespuestaNyV(respuesta, objetoDocumento);
            throw new SIATException(String.format(LOG_FORMAT_DOUBLE_STRING, ERROR_NYV,
                    respuesta.getMensajeRespuesta()));

        } else {

            /**
             * (SI TODO SALIO BIEN) AQUI SE TIENE QUE ACTUALIZAR LA INFORMACION
             * EN BD Y ELIMINAR LOS ARCHIVOS OBSOLETOS:
             */
            LOGGER.info("REGISTRO EXITOSO: FOLIO-NYV: " + respuesta.getFolio() + ", NUMCONTROL: "
                    + objetoDocumento.getDycpServicioDTO().getNumControl() + ", RFC:"
                    + objetoServicio.getRfcContribuyente());
            LOGGER.error("REGISTRO EXITOSO: FOLIO-NYV: " + respuesta.getFolio() + ", NUMCONTROL: "
                    + objetoDocumento.getDycpServicioDTO().getNumControl() + ", RFC:"
                    + objetoServicio.getRfcContribuyente());
            dyctDocumentoDAO.actualizarFolioNYV(respuesta.getFolio(), objetoDocumento.getNumControlDoc());
            dyctDocumentoDAO.actualizarDocumentoAPdf(objetoDocumento.getNombreArchivo().replace(DOCX, PDF),
                    objetoDocumento.getNumControlDoc());
            // se agrega la marca al documento de la fecha de notificacion
            agregarMarcaFechaNotificacion(respuesta, objetoDocumento.getNumControlDoc());

            envioANyVDAO.actualizarRutaDocumentoPdf(rutaNyV, objetoDocumento.getNumControlDoc());

            rutaDocumentoWord = new StringBuilder();
            rutaDocumentoPdf = new StringBuilder();
            File documento
                    = new File(rutaDocumentoWord.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo()).toString());
            File documentopdf
                    = new File(rutaDocumentoPdf.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo().replace(DOCX,
                            PDF)).toString());

            if (documento.delete() && documentopdf.delete()) {
                LOGGER.info("SI SE ELIMINAN");
            }
            rutaDocumentoWord = null;
            rutaDocumentoPdf = null;
        }
    }

    private DocumentoGeneradoVO llenarDatosDocumento(String cadenaOriginal, DyctDocumentoDTO objetoDocumento,
            DycaActoAdmtvosDTO objetoActoAdmtvo,
            DycvEmpleadoDTO objetoEmpleado) throws SIATException {
        Properties propiedades = new Properties();
        FileInputStream archivo = null;
        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
        } catch (IOException e) {
            throw new SIATException(e);
        } finally {
            try {
                archivo.close();
            } catch (Exception e) {
                generaFallo(e.getMessage(), DYCC_FALLOCOLA_DYC, objetoDocumento.getNumControlDoc(),
                        objetoActoAdmtvo.getNombrePlantilla(), objetoActoAdmtvo.getCveActoAdmtvo(),
                        objetoActoAdmtvo.getCveUnidadAdmtva(),
                        objetoDocumento.getDycpServicioDTO().getNumControl());
                LOGGER.error("Error al cerrar el archivo de configuracion: " + e);
            }
        }
        Calendar fechaDocumento = new GregorianCalendar();
        Calendar fechaVigenciaFiel = new GregorianCalendar();
        StringBuilder rutaDocumento = new StringBuilder();
        DocumentoGeneradoVO documento = new DocumentoGeneradoVO();
        fechaDocumento.setTime(objetoDocumento.getFechaRegistro());
        fechaVigenciaFiel.setTime(new java.util.Date(System.currentTimeMillis()));
        fechaVigenciaFiel.add(Calendar.DAY_OF_MONTH, 1);

        generarPDF(objetoDocumento, objetoActoAdmtvo);
        LOGGER.debug(new StringBuilder().append(LOG_LLENAR_DATOS_DOCUMENTO).append(objetoDocumento).toString());
        LOGGER.debug(new StringBuilder().append(LOG_LLENAR_DATOS_DOCUMENTO).append(objetoEmpleado).toString());
        LOGGER.debug(new StringBuilder().append(LOG_LLENAR_DATOS_DOCUMENTO).append(cadenaOriginal).toString());
        LOGGER.debug(new StringBuilder().append(LOG_LLENAR_DATOS_DOCUMENTO).append(objetoActoAdmtvo).toString());
        try {
           documento.setCadenaOriginal(cadenaOriginal);
            documento.setEstatusDocumento(ESTATUS_DOCUMENTO_NYV);
            documento.setFechaDocumento(fechaDocumento);
            documento.setFirma((objetoDocumento.getFirmaElectronia()!=null)?(String)objetoDocumento.getFirmaElectronia().subSequence(objetoDocumento.getFirmaElectronia().length()-ConstantesDyCNumerico.VALOR_8, objetoDocumento.getFirmaElectronia().length()):(String)objetoDocumento.getSelloDigital().subSequence(FIRMA_INICIO, FIRMA_FIN));
             if(propiedades.getProperty("activoFase2NYV").equals("1")){
                 documento.setCveDocumentoTipo(objetoActoAdmtvo.getCveDocumentoTipo());
             }else{
                   documento.setTipoDocumento(objetoActoAdmtvo.getNombrePlantilla());
                   documento.setCveDocumentoTipo(objetoActoAdmtvo.getCveDocumentoTipo());
             }
            documento.setUrlDocumento(rutaDocumento.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo()).toString().replace(DOCX,
                                                                                                                                                                    PDF).replace(DYC,
                                                                                                                                                                                 propiedades.getProperty(NYV)));
            documento.setRfcFirmante(objetoEmpleado.getRfc());
            documento.setNombreFirmante(objetoEmpleado.getNombreCompleto());
            documento.setPuestoFirmante(objetoEmpleado.getNombrePuesto());
            documento.setFechaVigenciaFiel(fechaVigenciaFiel);
        } catch (Exception e) {
            LOGGER.error(e + ".\nObjeto a enviar: " + ExtractorUtil.ejecutar(documento)
                    + " \nObjeto documento: " + ExtractorUtil.ejecutar(objetoDocumento));
            String error = "Error: " + e;

            if (objetoEmpleado == null) {
                error = ERROR_EMPLEADO_NULL;
            }

            generaFallo(error, DYCC_FALLOCOLA_DYC, objetoDocumento.getNumControlDoc(),
                    objetoActoAdmtvo.getNombrePlantilla(), objetoActoAdmtvo.getCveActoAdmtvo(),
                    objetoActoAdmtvo.getCveUnidadAdmtva(), objetoDocumento.getDycpServicioDTO().getNumControl());
            throw new SIATException("error: " + e);
        }
        LOGGER.debug("termina llenarDatosDocumento: Informacion del objeto generado: ");
        LOGGER.debug(ExtractorUtil.ejecutar(documento));
        return documento;
    }

    /**
     * Genera un documento pdf utilizando libre office y lo guarda en la misma
     * ruta donde esta creado el documento de Word.
     *
     * @param objetoDocumento
     * @return
     */
    private void generarPDF(DyctDocumentoDTO objetoDocumento,
            DycaActoAdmtvosDTO objetoActoAdmtvo) throws SIATException {
   
        LOGGER.error("generarPDF()");
        StringBuilder rutaDocumentoWord = new StringBuilder();
        StringBuilder rutaDocumentoPDF = new StringBuilder();
        File archivoDocx
                = new File(rutaDocumentoWord.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo()).toString());
        File archivoPDF
                = new File(rutaDocumentoPDF.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo().replace(DOCX,
                        PDF)).toString());
        LOGGER.info("Ruta de documento origen: --- " + rutaDocumentoWord.toString());
        LOGGER.info("Ruta de documento destino: --- " + rutaDocumentoPDF.toString());
        LOGGER.info("JAHO - Entro a generar el PDF...");
        LOGGER.debug(ExtractorUtil.ejecutar(objetoDocumento));
        try {
            LOGGER.error("Ejecutar convertToPDF()");
            jodConverter.convertToPDF(archivoDocx, archivoPDF);
            LOGGER.info("JAHO - Se creo el PDF...");
        } catch (Exception e) {
            generaFallo(String.format(LOG_FORMAT_DOUBLE_STRING, ERROR_CONVERTIR_DOCUMENTO_WORD, e.getMessage()),
                    DYCC_FALLOCOLA_DYC, objetoDocumento.getNumControlDoc(), objetoActoAdmtvo.getNombrePlantilla(),
                    objetoActoAdmtvo.getCveActoAdmtvo(), objetoActoAdmtvo.getCveUnidadAdmtva(),
                    objetoDocumento.getDycpServicioDTO().getNumControl());
            LOGGER.info("Exception: " + e.getCause());
            throw new SIATException(ERROR_CONVERTIR_DOCUMENTO_WORD + ": " + e);
        }
    }

    /**
     *
     * @param objetoDocumento
     * @throws SIATException
     */
    private String actualiarPDFenBaseyFileSystem(DyctDocumentoDTO objetoDocumento,
            DycaActoAdmtvosDTO objetoActoAdmtvo) throws SIATException {

        FileInputStream archivo = null;
        Properties propiedades = new Properties();
        StringBuilder rutaDocumentoPdf = new StringBuilder();
        StringBuilder rutaDocumentoNyV = new StringBuilder();

        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
        } catch (IOException e) {
            throw new SIATException(e);
        } finally {
            try {
                archivo.close();
            } catch (Exception e) {
                LOGGER.error("Error al cerrar el archivo de configuracion: " + e);
            }
        }

          String rutaNYVString =
            rutaDocumentoNyV.append(objetoDocumento.getUrl().replace(obtenerPuntoDeMontajeActual(objetoDocumento.getUrl()), propiedades.getProperty(NYV))).toString();

        File documentopdf
                = new File(rutaDocumentoPdf.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo().replace(DOCX,
                        PDF)).toString());
        File rutaNYV = new File(rutaNYVString);

        if (rutaNYV.exists() || rutaNYV.mkdirs()) {
            LOGGER.info("Si se creó la ruta");
            StringBuilder archivoEnvio = new StringBuilder();
            File archivoNYV
                    = new File(archivoEnvio.append(rutaNYVString).append(DIAGONAL).append(objetoDocumento.getNombreArchivo().replace(DOCX,
                            PDF)).toString());

            boolean a = rutaNYV.setExecutable(Boolean.TRUE, false);
            boolean b = rutaNYV.setReadable(Boolean.TRUE, false);
            boolean c = rutaNYV.setWritable(Boolean.TRUE, false);
            LOGGER.debug("a: " + a + " b:" + b + " c:" + c);

            try {
                FileUtils.copyFile(documentopdf, archivoNYV);
                boolean d = archivoNYV.setExecutable(Boolean.TRUE, false);
                boolean e = archivoNYV.setReadable(Boolean.TRUE, false);
                boolean f = archivoNYV.setWritable(Boolean.TRUE, false);
                LOGGER.debug("d: " + d + " e:" + e + " f:" + f);

            } catch (IOException e) {
                LOGGER.error("Error al copiar: " + e);
            }
        } else {
            generaFallo(String.format(LOG_FORMAT_DOUBLE_STRING, ERROR_GENERA_DIRECTORIO, rutaNYVString),
                    DYCC_FALLOCOLA_DYC, objetoDocumento.getNumControlDoc(), objetoActoAdmtvo.getNombrePlantilla(),
                    objetoActoAdmtvo.getCveActoAdmtvo(), objetoActoAdmtvo.getCveUnidadAdmtva(),
                    objetoDocumento.getDycpServicioDTO().getNumControl());
            throw new SIATException(String.format(LOG_FORMAT_DOUBLE_STRING, ERROR_GENERA_DIRECTORIO, rutaNYVString));
        }
        return rutaNYVString;
    }

    private void generaFallo(String causa, Integer fallo, String mensaje, String tipoDocumento,
            Integer actoAdministrativo, Integer local, String numControl) {

        generaFalloValidado(CamposUtil.corrigeCampoString(causa), CamposUtil.corrigeCampoInteger(fallo),
                CamposUtil.corrigeCampoString(mensaje), CamposUtil.corrigeCampoString(tipoDocumento),
                CamposUtil.corrigeCampoInteger(actoAdministrativo), CamposUtil.corrigeCampoInteger(local),
                CamposUtil.corrigeCampoString(numControl));
    }

    @Transactional(readOnly = false)
    private void generaFalloValidado(String causa, Integer fallo, String mensaje, String tipoDocumento,
            Integer actoAdministrativo, Integer local, String numControl) {
        LOGGER.debug(new StringBuilder().append(LOG_GENERA_FALLO).append("causa - ").append(causa));
        LOGGER.debug(new StringBuilder().append(LOG_GENERA_FALLO).append("fallo - ").append(fallo));
        LOGGER.debug(new StringBuilder().append(LOG_GENERA_FALLO).append("mensaje - ").append(mensaje));
        LOGGER.debug(new StringBuilder().append(LOG_GENERA_FALLO).append("tipoDocumento - ").append(tipoDocumento));
        LOGGER.debug(new StringBuilder().append(LOG_GENERA_FALLO).append("actoAdministrativo - ").append(actoAdministrativo));
        LOGGER.debug(new StringBuilder().append(LOG_GENERA_FALLO).append("local - ").append(local));
        LOGGER.debug(new StringBuilder().append(LOG_GENERA_FALLO).append("numControl - ").append(numControl));
        try {
            DyccFalloDetalleDTO dyccFalloDetalleDTO = new DyccFalloDetalleDTO(causa, new DyccFalloColasDTO(fallo));
            dyccFalloDetalleDTO = generaFallosService.getDyccFalloDetalleDTO(dyccFalloDetalleDTO);
            DyctFalloMensajesDTO dyctFalloMensajesDTO
                    = new DyctFalloMensajesDTO(mensaje, tipoDocumento, actoAdministrativo, local, numControl,
                            new DyccFalloDetalleDTO(dyccFalloDetalleDTO.getIdFalloDetalle()));

            generaFallosService.insertFalloMensajes(dyctFalloMensajesDTO);
        } catch (Exception e) {
            LOGGER.error(String.format(LOG_FORMAT_DOUBLE_STRING, ERROR_GENERA_FALLO, mensaje));
        }
    }

    /**
     * Metodo para marcar los documentos cuando tienen fecha de notificacion,
     * cuando estan amparados o no tienen medios de notificacion en la tabla
     * DYCT_DOCUMENTO.
     *
     * @param respuesta Object ResponseRegistro retornado al ejecutar el WS
     * registrarActoAdministrativo()
     * @param numControlDoc numero de control del documento
     */
    private void agregarMarcaFechaNotificacion(ResponseRegistro respuesta, String numControlDoc) {
        LOGGER.error("agregarMarcaFechaNotificacion: " + numControlDoc);
        Integer marcaFechaNotificacion = null;
        if (respuesta != null) {
            try {
                if (respuesta.getCodigoRespuesta() != null && respuesta.getCodigoRespuesta().equals("NO_OK")) {
                    LOGGER.error("getMensajeRespuesta: " + respuesta.getMensajeRespuesta());
                    /**
                     * Contribuyente amparado. (y NO se regresara un FOLIO)
                     */
                    if (respuesta.getMensajeRespuesta().equalsIgnoreCase(MENSAJE_AMPARADO)) {
                        /**
                         * MARCA_AMPARADO = 1;
                         */
                        marcaFechaNotificacion = MARCA_AMPARADO;
                    } /**
                     * Contribuyente sin medios de contacto. (y NO se regresara
                     * un FOLIO)
                     */
                    else if (respuesta.getMensajeRespuesta().equalsIgnoreCase(MENSAJE_SINMEDIOSDECONTACTO)) {
                        /**
                         * MARCA_SINMEDIOSDECONTACTO = 2;
                         */
                        marcaFechaNotificacion = MARCA_SINMEDIOSDECONTACTO;
                    } else {
                        /**
                         * Mensajes diferentes a los anteriores y NO se genera
                         * un FOLIO MARCA_NO_OK_RESPUESTA = 0;
                         */
                        marcaFechaNotificacion = MARCA_NO_OK_RESPUESTA;
                    }

                } else {
                    /**
                     * tiene el estatus NOTIFICADO o PENDIENTE DE ENVIO, que
                     * significa que a mas tardar despues de 4 dias habiles va a
                     * tener una fecha de notificacion
                     */
                    marcaFechaNotificacion = MARCA_TIENEFECHANOTIFICACION;
                }
                dyctDocumentoDAO.agregaMarcaFechaNotificacionByNumControlDoc(numControlDoc, marcaFechaNotificacion);

            } catch (SIATException e) {
                LOGGER.error("Error al ejecutar dyctDocumentoDAO.agregaMarcaFechaNotificacionByNumControlDoc(): " + numControlDoc);
            }
        }
    }

    /**
     * Metodo para guardar las inconsistencias cuando la respuesta de NyV sea
     * Contribuyente amparado o Contribuyente sin medios de contacto.
     *
     * @param respuesta bject ResponseRegistro retornado al ejecutar el WS
     * registrarActoAdministrativo()
     * @param numControl numero de control de la solicitud
     */
    private void guardarInconsistenciaRespuestaNyV(ResponseRegistro respuesta, DyctDocumentoDTO documento) {
        LOGGER.error("guardarInconsistenciaRespuestaNyV: " + documento);
        if (respuesta.getMensajeRespuesta() != null) {
            LOGGER.error("mensaje respuesta: " + respuesta.getMensajeRespuesta());
            /**
             * Contribuyente amparado o Contribuyente sin medios de contacto.
             */
            if (respuesta.getMensajeRespuesta().equalsIgnoreCase(MENSAJE_AMPARADO)
                    || respuesta.getMensajeRespuesta().equalsIgnoreCase(MENSAJE_SINMEDIOSDECONTACTO)) {
                DyccInconsistDTO inconsistenciaCatalogo = new DyccInconsistDTO();
                DycaSolInconsistDTO dycaSolicitudInconsistencia = new DycaSolInconsistDTO();

                if (respuesta.getMensajeRespuesta().equalsIgnoreCase(MENSAJE_AMPARADO)) {
                    /**
                     * ConstantesDyCNumerico.VALOR_9
                     */
                    inconsistenciaCatalogo.setIdInconsistencia(ID_INCONSISTENCIA_AMPARADO);
                } else if (respuesta.getMensajeRespuesta().equalsIgnoreCase(MENSAJE_SINMEDIOSDECONTACTO)) {
                    inconsistenciaCatalogo.setIdInconsistencia(ID_INCONSISTENCIA_SINMEDIOSDECONTACTO);
                }
                LOGGER.error("IdInconsistencia: " + inconsistenciaCatalogo.getIdInconsistencia());
                DycpSolicitudDTO solicitudDTO = new DycpSolicitudDTO();
                solicitudDTO.setNumControl(documento.getDycpServicioDTO().getNumControl());
                dycaSolicitudInconsistencia.setDycpSolicitudDTO(solicitudDTO);
                dycaSolicitudInconsistencia.setDyccInconsistDTO(inconsistenciaCatalogo);
                dycaSolicitudInconsistencia.setDescripcion(respuesta.getMensajeRespuesta());

                DycaSolInconsistDTO dTOFind = dyccInconsistenciaDAO
                        .buscarSolicitudInconsitencia(dycaSolicitudInconsistencia.getDyccInconsistDTO().getIdInconsistencia(),
                                dycaSolicitudInconsistencia.getDycpSolicitudDTO().getNumControl());
                if (dTOFind == null) {
                    LOGGER.info("dTOFind: == null, SE INSERTA NUEVA INCONSISTENCIA!!");
                    dyccInconsistenciaDAO.insertarInconsistencia(dycaSolicitudInconsistencia);
                } else {
                    LOGGER.info("SE ACTUALIZA  INCONSISTENCIA!!");
                    dycaSolicitudInconsistencia.setDescripcion(respuesta.getMensajeRespuesta()
                            .concat(MENSAJE_INCONSISTENCIA_UPDATE).concat(documento.getNumControlDoc()));
                    dyccInconsistenciaDAO.actualizarSolicitudInconsistencia(dycaSolicitudInconsistencia);
                }
            } else {
                LOGGER.error("NO HAY INCONSISTENCIA!!: " + respuesta.getMensajeRespuesta());
            }
        }
    }
    private static String obtenerPuntoDeMontajeActual(String url) {
        String[] arreglo = url.split(DIAGONAL);
        return DIAGONAL + arreglo[1] + DIAGONAL + arreglo[ConstantesDyCNumerico.VALOR_2];
    }
}
