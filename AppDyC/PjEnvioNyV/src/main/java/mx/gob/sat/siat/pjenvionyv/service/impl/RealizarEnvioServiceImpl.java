package mx.gob.sat.siat.pjenvionyv.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import mx.gob.sat.siat.dyc.dao.util.DycaActoAdmtvosDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.domain.documento.DycaActoAdmtvosDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vistas.dao.DycvEmpleadoDAO;
import mx.gob.sat.siat.nyv.sistema.webservice.DocumentoGeneradoVO;
import mx.gob.sat.siat.nyv.sistema.webservice.RegistroActoAdministrativoVO;
import mx.gob.sat.siat.nyv.sistema.webservice.ResponseRegistro;
import mx.gob.sat.siat.nyv.sistema.webservice.WSNyVSistemaImplProxy;
import mx.gob.sat.siat.pjenvionyv.dao.EnvioANyVDAO;
import mx.gob.sat.siat.pjenvionyv.generico.util.common.JODConverter;
import mx.gob.sat.siat.pjenvionyv.service.RealizarEnvioService;

import org.apache.axis.AxisProperties;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Esta clase se encarga de realizar el envio de todos los documentos que se van a enviar a NyV, cada documento que
 * se envia es tomado de un documento de word y se convierte a pdf para poder ser enviado.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "realizarEnvioService")
public class RealizarEnvioServiceImpl implements RealizarEnvioService {
    
    public static final int FIRMA_FIN    = 172;
    public static final int FIRMA_INICIO = 164;
    public static final int DILIGENCIADO = 7;
    
    private static final String DIAGONAL="/";
    private static final String DOCX = "docx";
    private static final String PDF = "pdf";
    private static final String NYV = "rutaFileSystemNyV";
    private static final String DYC = "/siat/dyc";
        
    private static final String CONSULTAR_SOLICITUD   = "SELECT * FROM DYCP_SOLICITUD WHERE NUMCONTROL=?";
    private static final String ESTATUS_DOCUMENTO_NYV ="GENERADO CON FIRMA";
    private static final String PROCESOORIGEN         = "DEVOLUCIONES Y COMPENSACIONES";
    private static final Logger LOGGER = Logger.getLogger(RealizarEnvioServiceImpl.class);
    
    @Autowired
    private DycaActoAdmtvosDAO dycaActoAdmtvosDAO;
    
    @Autowired
    private DyccAprobadorDAO dyccAprobadorDAO;
    
    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;
    
    @Autowired
    private IDycpServicioDAO iDycpServicioDAO;
    
    @Autowired(required = true)
    private DycpSolicitudDAO dycpSolicitudDAO;
        
    @Autowired
    private JODConverter jodConverter;
    
    @Autowired
    private DycvEmpleadoDAO satSiat01MVDAO;
    
    @Autowired
    private EnvioANyVDAO envioANyVDAO;
    
    public RealizarEnvioServiceImpl() {
        super();
    }
    
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = SIATException.class)
    public void realizarCambios(DyctDocumentoDTO objetoDocumento) throws SIATException {
        try {
            String rutaNyV="";
            ResponseRegistro respuesta       = null;
            DycvEmpleadoDTO objetoEmpleado   = null;
            DyccAprobadorDTO objetoAprobador = null;
            DycaActoAdmtvosDTO objetoActoAdmtvo = null;
            StringBuilder rutaDocumentoWord = null;
            StringBuilder rutaDocumentoPdf  = null;
            Properties propiedades  = new Properties();
            Calendar fechaDocumento = new GregorianCalendar();
            FileInputStream archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
            
            DycpSolicitudDTO objetoSolicitud =
                dycpSolicitudDAO.consultarSolicitud(CONSULTAR_SOLICITUD, objetoDocumento.getDycpServicioDTO().getNumControl());
            LOGGER.debug(ExtractorUtil.ejecutar(objetoSolicitud));
            
            DycpServicioDTO objetoServicio =
                iDycpServicioDAO.encontrar(objetoDocumento.getDycpServicioDTO().getNumControl());
            LOGGER.debug(ExtractorUtil.ejecutar(objetoServicio));
            
            AxisProperties.setProperty("http.nonProxyHoslts", propiedades.getProperty("urlAmbiente"));
            
            WSNyVSistemaImplProxy wSNyVSistemaImpl = new WSNyVSistemaImplProxy(propiedades.getProperty("endPoint"));
            RegistroActoAdministrativoVO registroActoAdministrativoVO = new RegistroActoAdministrativoVO();
            
            if (!wSNyVSistemaImpl.fueraHorarioLaboral()) {
                fechaDocumento.setTime(objetoDocumento.getFechaRegistro());
                objetoAprobador = dyccAprobadorDAO.encontrar(objetoDocumento.getDyccAprobadorDTO().getNumEmpleado());

                if (objetoAprobador != null) {
                    objetoEmpleado = satSiat01MVDAO.consultarXIDEmpleado(objetoAprobador.getNumEmpleado());
                    objetoActoAdmtvo =
                            dycaActoAdmtvosDAO.consultar(objetoAprobador.getCentroCosto(), objetoDocumento.getDyccMatDocumentosDTO().getIdPlantilla());
                    LOGGER.debug(ExtractorUtil.ejecutar(objetoActoAdmtvo));

                    DocumentoGeneradoVO[] listaDocumentos = new DocumentoGeneradoVO[1];
                    listaDocumentos[0] =
                            llenarDatosDocumento(objetoSolicitud.getCadenaOriginal(),
                                                 objetoDocumento, objetoActoAdmtvo, objetoEmpleado);
                    registroActoAdministrativoVO.setClaveUnidadAdmin(objetoAprobador.getCentroCosto().toString());
                    registroActoAdministrativoVO.setClaveActoAdmin(objetoActoAdmtvo.getCveActoAdmtvo());
                    registroActoAdministrativoVO.setProcesoOrigen(PROCESOORIGEN);
                    registroActoAdministrativoVO.setNumeroReferencia(objetoActoAdmtvo.getPrefijo() +
                                                                     objetoDocumento.getNumControlDoc());
                    registroActoAdministrativoVO.setNumeroReferenciaOrigen(objetoDocumento.getDycpServicioDTO().getNumControl());
                    registroActoAdministrativoVO.setRfcDestinatario(objetoServicio.getRfcContribuyente());
                    registroActoAdministrativoVO.setNumeroEmpleado(String.format("%011d",
                                                                                 objetoDocumento.getDyccAprobadorDTO().getNumEmpleado()));
                    registroActoAdministrativoVO.setDocumentos(listaDocumentos);
                    
                    rutaNyV   = actualiarPDFenBaseyFileSystem(objetoDocumento);
                    respuesta = wSNyVSistemaImpl.registrarActoAdministrativo(registroActoAdministrativoVO);

                } else {
                    throw new SIATException("Hubo un error al buscar los datos del aprobador");
                }
                
                if (respuesta.getCodigoRespuesta().equals("NO_OK")) {
                    LOGGER.error(ExtractorUtil.ejecutar(llenarDatosDocumento(objetoSolicitud.getCadenaOriginal(),
                                                                             objetoDocumento, objetoActoAdmtvo,
                                                                             objetoEmpleado)));
                    LOGGER.error(ExtractorUtil.ejecutar(registroActoAdministrativoVO));
                    LOGGER.error("Error al guardar el archivo con NyV, causa: "+respuesta.getMensajeRespuesta());
                    throw new SIATException("Error al guardar el archivo con el WS de NyV, causa: " +
                                            respuesta.getMensajeRespuesta());

                } else {
                    //(SI TODO SALIÓ BIEN) AQUÍ SE TIENE QUE ACTUALIZAR LA INFORMACIÓN EN BD Y ELIMINAR LOS ARCHIVOS OBSOLETOS:
                    LOGGER.error("REGISTRO EXITOSO: FOLIO-NYV: "+respuesta.getFolio()+", NUMCONTROL: "+objetoDocumento.getDycpServicioDTO().getNumControl()+", RFC:"+objetoServicio.getRfcContribuyente());
                    dyctDocumentoDAO.actualizarFolioNYV(respuesta.getFolio(), objetoDocumento.getNumControlDoc());
                    dyctDocumentoDAO.actualizarDocumentoAPdf(objetoDocumento.getNombreArchivo().replace(DOCX, PDF), objetoDocumento.getNumControlDoc());
                    dyctDocumentoDAO.actualizarEstadoDoc(DILIGENCIADO, objetoDocumento.getNumControlDoc());
                    envioANyVDAO.actualizarRutaDocumentoPdf(rutaNyV, objetoDocumento.getNumControlDoc());
                    
                    rutaDocumentoWord = new StringBuilder();
                    rutaDocumentoPdf  = new StringBuilder();
                    File documento    = new File(rutaDocumentoWord.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo()).toString());
                    File documentopdf = new File(rutaDocumentoPdf.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo().replace(DOCX, PDF)).toString());
                    
                    if (documento.delete() && documentopdf.delete()) {
                        LOGGER.info("SI SE ELIMINAN");
                    }
                    rutaDocumentoWord = null;
                    rutaDocumentoPdf = null;
                }
            }
        } catch (Exception e) {
            LOGGER.error("RealizarEnvioServiceImpl; method: realizarCambios(); error: "+e);
            LOGGER.error(ExtractorUtil.ejecutar(objetoDocumento));
            throw new SIATException(e);
        }
    }
    

    
    private DocumentoGeneradoVO llenarDatosDocumento(String cadenaOriginal, DyctDocumentoDTO objetoDocumento, DycaActoAdmtvosDTO objetoActoAdmtvo, DycvEmpleadoDTO objetoEmpleado) throws SIATException {
        Properties propiedades = new Properties();

        Calendar fechaDocumento       = new GregorianCalendar();
        Calendar fechaVigenciaFiel    = new GregorianCalendar();
        StringBuilder rutaDocumento   = new StringBuilder();
        DocumentoGeneradoVO documento = new DocumentoGeneradoVO();        
        fechaDocumento.setTime(objetoDocumento.getFechaRegistro());
        fechaVigenciaFiel.setTime(new java.util.Date(System.currentTimeMillis()));
        fechaVigenciaFiel.add(Calendar.DAY_OF_MONTH, 1);
        
        generarPDF(objetoDocumento);
        
        DycpSolicitudDTO objetoSolicitud = dycpSolicitudDAO.encontrar(objetoDocumento.getDycpServicioDTO().getNumControl());
        
        documento.setCadenaOriginal(cadenaOriginal);
        documento.setEstatusDocumento(ESTATUS_DOCUMENTO_NYV);
        documento.setFechaDocumento(fechaDocumento);
        documento.setFirma((String)objetoSolicitud.getSelloDigital().subSequence(FIRMA_INICIO, FIRMA_FIN));
        documento.setTipoDocumento(objetoActoAdmtvo.getNombrePlantilla());
        documento.setUrlDocumento(rutaDocumento.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo()).toString().replace(DOCX, PDF).replace(DYC, propiedades.getProperty(NYV)));
        documento.setRfcFirmante(objetoEmpleado.getRfc());
        documento.setNombreFirmante(objetoEmpleado.getNombreCompleto());
        documento.setPuestoFirmante(objetoEmpleado.getNombrePuesto());
        documento.setFechaVigenciaFiel(fechaVigenciaFiel);
        LOGGER.debug("JAHO - Informacion del objeto generado: ");
        LOGGER.debug(ExtractorUtil.ejecutar(documento));
        objetoSolicitud=null;
        return documento;
    }
    
    /**Genera un documento pdf utilizando libre office y lo guarda en la misma ruta donde esta creado el documento de Word.
     *
     * @param objetoDocumento
     * @return
     */
    private void generarPDF(DyctDocumentoDTO objetoDocumento) throws SIATException {
        StringBuilder rutaDocumentoWord = new StringBuilder();
        StringBuilder rutaDocumentoPDF  = new StringBuilder();
        File archivoDocx = new File(rutaDocumentoWord.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo()).toString());
        File archivoPDF  = new File(rutaDocumentoPDF.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo().replace(DOCX, PDF)).toString());
        LOGGER.info("Ruta de documento destino: --- " + rutaDocumentoPDF.toString());
        LOGGER.info("JAHO - Entro a generar el PDF...");
        LOGGER.debug(ExtractorUtil.ejecutar(objetoDocumento));
        try {            
            jodConverter.convertToPDF(archivoDocx, archivoPDF);
            LOGGER.info("JAHO - Se creo el PDF...");
        } catch (Exception e) {
            throw new SIATException("No se pudo convertir el documento de word. Causa: "+e);
        }
    }
    
    /**
     *
     * @param objetoDocumento
     * @throws SIATException
     */
    private String actualiarPDFenBaseyFileSystem(DyctDocumentoDTO objetoDocumento) throws SIATException {
        
        FileInputStream archivo = null;
        Properties propiedades  = new Properties();
        StringBuilder rutaDocumentoPdf = new StringBuilder();
        StringBuilder rutaDocumentoNyV = new StringBuilder();
        
        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
        } catch (FileNotFoundException e) {
            LOGGER.error("No se pudo abrir el properties" + e.getMessage());
            throw new SIATException ("No se pudo abrir el properties" + e.getMessage());
        }catch (IOException e){
            LOGGER.error("No se pudo cargar el properties " + e.getMessage());
            throw new SIATException ("No se pudo abrir el properties" + e.getMessage());
        }
        
        String rutaNYVString= rutaDocumentoNyV.append(objetoDocumento.getUrl().replace(DYC, propiedades.getProperty(NYV))).toString();
        
        File documentopdf = new File(rutaDocumentoPdf.append(objetoDocumento.getUrl()).append(DIAGONAL).append(objetoDocumento.getNombreArchivo().replace(DOCX, PDF)).toString());
        File rutaNYV      = new File(rutaNYVString);
        
        if(rutaNYV.exists() || rutaNYV.mkdirs()) {
            LOGGER.info("Si se creó la ruta");
            StringBuilder archivoEnvio = new StringBuilder();
            File archivoNYV = new File (archivoEnvio.append(rutaNYVString).append(DIAGONAL).append(objetoDocumento.getNombreArchivo().replace(DOCX, PDF)).toString());
            rutaNYV.setExecutable(Boolean.TRUE, false);
            rutaNYV.setReadable(Boolean.TRUE, false);
            rutaNYV.setWritable(Boolean.TRUE, false);
            
            try {
                FileUtils.copyFile(documentopdf, archivoNYV);
                archivoNYV.setExecutable(Boolean.TRUE, false);
                archivoNYV.setReadable(Boolean.TRUE, false);
                archivoNYV.setWritable(Boolean.TRUE, false);
            } catch (IOException e) {
                LOGGER.error("Error al copiar: "+e);
            }
        } else {
            throw new SIATException ("No se pudo generar el directorio: "+rutaNYVString);
        }
        return rutaNYVString;
    }
}
