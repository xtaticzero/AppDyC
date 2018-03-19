package mx.gob.sat.siat.dyc.comunica.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.RemoteException;

import java.text.ParseException;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import mx.gob.sat.siat.dyc.comunica.service.ProcesarDatosARCAService;
import mx.gob.sat.siat.dyc.comunica.service.RetroalimentarNotificacionesService;
import mx.gob.sat.siat.dyc.dao.parametros.DyccParametrosAppDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.generico.util.FechaUtil;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.DyctDocumentoVO;
import mx.gob.sat.siat.nyv.sistema.webservice.ResponseConsulta;
import mx.gob.sat.siat.nyv.sistema.webservice.WSNyVSistemaImpl;
import mx.gob.sat.siat.nyv.sistema.webservice.WSNyVSistemaImplProxy;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Esta clase se utiliza para consultar la informaci&ocaute;n de los
 * tr&aacute;mites a los que les falta registrar la fecha de
 * notificaci&oacute;n.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "procesarDatosARCAService")
public class ProcesarDatosARCAServiceImpl implements ProcesarDatosARCAService {

    private static final int MAXIMO_REGISTROS_NYV_ID = 35;
    /* tramites con fecha de resolucion dentro de los INTERVALO_DIAS_DATOSPARANYV 
       dias anteriores a la fecha de ejecucion */
    private static final int INTERVALO_DIAS_DATOSPARANYV = 60;

    private static final String PIPE = "|";

    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;

    @Autowired
    private DyccParametrosAppDAO dyccParametrosAppDAO;

    @Autowired(required = true)
    private RetroalimentarNotificacionesService retroalimentarNotificacionesService;

    private static final Logger LOGGER = Logger.getLogger(ProcesarDatosARCAServiceImpl.class);

    /**
     * Realiza el procesamiento de los datos de ARCA para la retroalimentacion
     *
     * @return verdadero si los datos se procesaron exitosamente, falso en caso
     * contrario
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public boolean procesarDatosArca() throws SIATException {
        LOGGER.info("procesarDatosArca");
        boolean banderaProcesar = false;
        List<DyctDocumentoVO> listaDatosNyV = null;
        try {
            DyccParametrosAppDTO objeto = dyccParametrosAppDAO.encontrar(MAXIMO_REGISTROS_NYV_ID);
            listaDatosNyV = dyctDocumentoDAO.buscarFolioNYV(Integer.valueOf(objeto.getValorStr()), INTERVALO_DIAS_DATOSPARANYV);

        } catch (NumberFormatException e) {
            LOGGER.error("procesarDatosArca(): " + e);
            throw new SIATException(e);
        } catch (SIATException e) {
            LOGGER.error("procesarDatosArca(): " + e);
            throw new SIATException(e);
        }

        if (listaDatosNyV != null && listaDatosNyV.size() > 0) {
            banderaProcesar = recorrerLista(listaDatosNyV);
        }

        return banderaProcesar;
    }

    /**
     * Recorre cada uno de los datos que obtiene de la base de datos de arca
     * para procesarlos...
     *
     * @param listaDatosParaNyV
     * @return
     */
    private boolean recorrerLista(List<DyctDocumentoVO> listaDatosParaNyV) throws SIATException {
        LOGGER.info("recorrerLista listaDatosParaNyV-->"+ listaDatosParaNyV.size() + "<--");
        boolean banderaRecorrer = Boolean.TRUE;
        Properties propiedades = new Properties();
        FileInputStream archivo = null;
        WSNyVSistemaImpl wSNyVSistemaImpl;

        BufferedWriter archivoLog = null;
        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
            wSNyVSistemaImpl = new WSNyVSistemaImplProxy(propiedades.getProperty("endPoint"));

            String nombreCarpeta = "/home/DyC/jar/lib/logsNotYVer";
            String nombreArchivo = FechaUtil.darFormatoFecha(new Date(System.currentTimeMillis()), "yyyyMMdd_HHmmss");
            LOGGER.error("Se procesarán: " + listaDatosParaNyV.size() + " registros.");
            LOGGER.info("crearDirectorioLogs: "+ nombreCarpeta);
            crearDirectorioLogs(nombreCarpeta);
            LOGGER.info("directorios creados!!");

            File fileSuccess = new File(nombreCarpeta, (nombreArchivo + ".txt"));
            boolean create = fileSuccess.createNewFile();
            if(!create) {
                throw new SIATException("Error al crear el txt de salida " + fileSuccess.getAbsolutePath());
            }
            LOGGER.error("create: " + create);
            archivoLog = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileSuccess), "UTF-8"));

            String lineaLog;
            String fechaNotificacion;
            DyctDocumentoVO documento;
            for (int i = 0; i< listaDatosParaNyV.size(); i++) {
                documento = listaDatosParaNyV.get(i);
                try {
                    LOGGER.error("PROCESANDO:: " + documento.getNumControl() + " " + (i+1) + " de " + listaDatosParaNyV.size());
                    long tiempoInicio = System.currentTimeMillis();
                    ResponseConsulta respuesta = wSNyVSistemaImpl.consultarActoAdministrativo(documento.getFolioNYV());
                    long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                    LOGGER.error("JAHO - El tiempo de demora es de:" + totalTiempo / ConstantesDyCNumerico.VALOR_1000
                            + " segundos");

                    if (respuesta != null) {
                        LOGGER.error("JAHO - OBJETO-NYV-RESPUESTA: " + ExtractorUtil.ejecutar(respuesta));

                        if (respuesta.getCodigoRespuesta() != null && respuesta.getCodigoRespuesta().equals("OK")) {
                            boolean actualizado = respuesta.getFechaNotificacion() != null ? 
                                    retroalimentarNotificacionesService.retroalimentarNotificacion(documento, respuesta) : false;

                            fechaNotificacion = respuesta.getFechaNotificacion() != null ? 
                                    FechaUtil.darFormatoFecha(respuesta.getFechaNotificacion().getTime(), "dd/MM/yyyy") : null;
                            lineaLog = (documento.getNumControl() + PIPE + documento.getNumControlDoc() + PIPE
                                    + "FOLIO-NYV:" + respuesta.getFolioActo() + PIPE
                                    + fechaNotificacion + PIPE + respuesta.getEstatus()+ PIPE
                                    + (actualizado ? "ACTUALIZADO" : "NO ACTUALIZADO") + PIPE);
                        } else {
                            lineaLog = documento.getNumControl() + PIPE + documento.getNumControlDoc() + 
                                    PIPE + "ERROR CodigoRespuesta " + PIPE + respuesta.getCodigoRespuesta();
                        }
                    } else {

                        lineaLog = documento.getNumControl() + PIPE + documento.getNumControlDoc() + 
                                PIPE + "ERROR EN LA RESPUESTA DEL WEB SERVICE";
                    }
                } catch (RemoteException e) {
                    lineaLog = documento.getNumControl() + PIPE + documento.getNumControlDoc() + 
                            PIPE + "ERROR EN INVOCACIÓN AL WEB SERVICE";
                    LOGGER.error("RemoteException::consultarActoAdministrativo " + documento.getFolioNYV() + " " + e);

                } catch (SIATException e) {
                    lineaLog = documento.getNumControl() + PIPE + documento.getNumControlDoc() +
                            PIPE + "ERROR AL ACTUALIZAR EL DOCUMENTO";
                    LOGGER.error("SIATException::retroalimentarNotificacion " + documento.getFolioNYV() + " " + e);

                }

                archivoLog.write(lineaLog);
                archivoLog.newLine();
            }

        } catch (IOException ioe) {
            LOGGER.error("recorrerLista::IOException " + ioe);
            throw new SIATException(ioe);
        } catch (ParseException pe) {
            LOGGER.error("recorrerLista::ParseException " + pe);
            throw new SIATException(pe);
        } finally {
            try {
                if (archivoLog != null) {
                    archivoLog.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error al cerrar el archivo de salida txt: " + e);
            }
            try {
                if (archivo != null) {
                    archivo.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error al cerrar el archivo de configuracion: " + e);
            }
        }
        return banderaRecorrer;
    }

    private void crearDirectorioLogs(String nombreCarpeta) throws SIATException {
        LOGGER.error("crearDirectorioLogs -->" + nombreCarpeta + "<--");
        File rutaLogs = new File(nombreCarpeta);
        if (!rutaLogs.exists()) {
        LOGGER.error("rutaLogs no existe se ejecuta crearCarpeta() ");
            if (Utils.crearCarpeta(nombreCarpeta)) {
                LOGGER.error("Se crea exitosamente la carpeta: " + nombreCarpeta);
            } else {
                throw new SIATException("Error al crear el directorio de salida");
            }
        }
    }
}
