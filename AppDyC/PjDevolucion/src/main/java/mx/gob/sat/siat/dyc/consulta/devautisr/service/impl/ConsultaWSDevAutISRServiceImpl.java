/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.ConsultaTramiteDevolucionService;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.ResponseConsultarDetalleTramiteDevAut;
import mx.gob.sat.mat.dyc.wsconsultadevautisr.ResponseConsultarExistenciaTramiteDevAut;
import mx.gob.sat.siat.dyc.consulta.devautisr.service.ConsultaWSDevAutISRService;
import mx.gob.sat.siat.dyc.consulta.devautisr.util.ConvertVOHelperViejo;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRDetalleVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.TramiteExisteConsultaVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service(value = "consultaWSDevAutISRService")
public class ConsultaWSDevAutISRServiceImpl implements ConsultaWSDevAutISRService {

    private static final Logger LOG = Logger.getLogger(ConsultaWSDevAutISRServiceImpl.class);
    private static final String IDENTIFICADOR_LOG = "MAT_SAD_WS Consulta de tramites:";

    private static final int CASE_2016 = 2016;

    public static final int DIEZ_MILL = 10000;

    private mx.gob.sat.mat.dyc.wsconsultadevautisr.ConsultaTramiteDevolucionServiceImpl port;
    private mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.ConsultaTramiteDevolucionServiceImpl portV2;

    private void conectarWS2016() throws IOException {
        Properties propiedades = new Properties();
        FileInputStream archivo = null;

        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);

            mx.gob.sat.mat.dyc.wsconsultadevautisr.ConsultaTramiteDevolucionService service = new ConsultaTramiteDevolucionService();
            DefaultHandlerResolver defaultHandlerResolver = new DefaultHandlerResolver();
            List<Handler> lstHandler = new ArrayList<Handler>();
            lstHandler.add(new ServiceLogHandler());
            defaultHandlerResolver.setHandlerList(lstHandler);
            service.setHandlerResolver(defaultHandlerResolver);
            port = service.getConsultaTramiteDevolucionServicePort();
            BindingProvider bp = (BindingProvider) port;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, propiedades.getProperty("endpointWSConsultaDevAutISR_2016"));
            bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", DIEZ_MILL);
            bp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", DIEZ_MILL);

        } finally {
            try {
                if (archivo != null) {
                    archivo.close();
                }
            } catch (IOException e) {
                LOG.error("Error al cerrar el archivo de salida txt: " + e);
            }
        }

    }

    private void conectarWS2017() throws IOException {
        Properties propiedades = new Properties();
        FileInputStream archivo = null;

        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);

            mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.ConsultaTramiteDevolucionService2017 devolucionService2017 = new mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.ConsultaTramiteDevolucionService2017();

            DefaultHandlerResolver defaultHandlerResolver = new DefaultHandlerResolver();
            List<Handler> lstHandler = new ArrayList<Handler>();
            lstHandler.add(new ServiceLogHandler());
            defaultHandlerResolver.setHandlerList(lstHandler);
            devolucionService2017.setHandlerResolver(defaultHandlerResolver);

            portV2 = devolucionService2017.getConsultaTramiteDevolucionService2017Port();
            BindingProvider bpV2 = (BindingProvider) portV2;
            bpV2.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, propiedades.getProperty("endpointWSConsultaDevAutISR_2017"));
            bpV2.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", DIEZ_MILL);
            bpV2.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", DIEZ_MILL);
        } finally {
            try {
                if (archivo != null) {
                    archivo.close();
                }
            } catch (IOException e) {
                LOG.error("Error al cerrar el archivo de salida txt: " + e);
            }
        }

    }

    @Override
    public DatosTramiteISRVO consultarExistenciaTramitesDevAut(String rfc, int ejercicio) throws SIATException {
        int intento = ConstantesDyCNumerico.VALOR_0;
        String mensajeError = ConstantesDyC.EMPTY_STRING;

        while (intento < ConstantesDyCNumerico.VALOR_3) {
            intento++;
            try {

                validateClientByExercise(CASE_2016);

                ResponseConsultarExistenciaTramiteDevAut result = port.consultarExistenciaTramitesDevAut(rfc, ejercicio);
                if (ejercicio != result.getEjercicio()) {
                    LOG.error(IDENTIFICADOR_LOG + " Error los ejercicios no son iguales: \n ejercicio consulta :" + ejercicio + " ejercicio respuesta :" + (String.valueOf(result.getEjercicio())));
                    throw new SIATException("objeto devuelto como nulo");
                }
                return ConvertVOHelperViejo.convertTODatosTramiteISRVO(result);
            } catch (Exception ex) {
                mensajeError = ex.getMessage();
                port = null;
                LOG.error(IDENTIFICADOR_LOG + "Error de conexion fallida con WS conuslta dev ISR intento:" + intento + " con rfc:" + rfc + " ejercicio:" + ejercicio, ex);

            }

        }

        DateFormat fechaHoraF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = fechaHoraF.format(new Date());

        LOG.error(IDENTIFICADOR_LOG + "numero maximo de intentos sobrepasado: fecha y Hora:" + fechaHora + " RFC:" + rfc + "ejercicio: " + ejercicio + " error:" + mensajeError);
        throw new SIATException("Error de conexion fallida con WS conuslta dev ISR Maximo numero de intentos realizados");
    }

    @Override
    public DatosTramiteISRDetalleVO consultarDetalleTramiteDevAut(Long folioDeclaracion, int ejercicio) throws SIATException {
        int intento = 0;
        String mensajeError = "";

        while (intento < ConstantesDyCNumerico.VALOR_3) {
            intento++;
            try {

                validateClientByExercise(ejercicio);

                if (ejercicio <= CASE_2016 && folioDeclaracion != null) {
                    ResponseConsultarDetalleTramiteDevAut result = port.consultarDetalleTramiteDevAut(folioDeclaracion);
                    if (result != null) {
                        return ConvertVOHelperViejo.convertTODatosTramiteISRDetalleVO(result);
                    } else {
                        throw new SIATException("objeto devuelto como nulo");
                    }
                }

            } catch (Exception ex) {
                mensajeError = ex.getMessage();
                port = null;
                LOG.error(IDENTIFICADOR_LOG + "Error de conexion fallida con WS conuslta detalle dev ISR intento:" + intento + " folio declaracion:" + folioDeclaracion, ex);
            }

        }

        DateFormat fechaHoraF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = fechaHoraF.format(new Date());

        LOG.error(IDENTIFICADOR_LOG + "numero maximo de intentos sobrepasado: fecha y Hora:" + fechaHora + " folio declaracion:" + folioDeclaracion + " error:" + mensajeError);
        throw new SIATException("Error de conexion fallida con WS conuslta detalle dev ISR Maximo numero de intentos realizados");

    }

    @Override
    public List<DatosTramiteISRDetalleVO> consultarDetalleTramiteDevAut(List<TramiteExisteConsultaVO> lstFolios, int ejercicio) throws SIATException {
        int intento = 0;
        String mensajeError = "";

        while (intento < ConstantesDyCNumerico.VALOR_3) {
            intento++;
            try {

                validateClientByExercise(ejercicio);

                if (ejercicio > CASE_2016 && lstFolios != null) {

                    mx.gob.sat.mat.dyc.wsconsultadevautisr.v2.ResponseConsultarDetalleTramiteDevAut result = portV2.consultarDetalleTramiteDevAut(getLstFolios(lstFolios));

                    if (result != null) {
                        List<DatosTramiteISRDetalleVO> lstResult = ConvertVOHelperViejo.convertTODatosTramiteISRDetalleVO(result, lstFolios);
                        if (lstResult != null) {
                            for (DatosTramiteISRDetalleVO resultTmp : lstResult) {
                                LOG.info(resultTmp);
                            }
                        }
                        return lstResult;
                    } else {
                        throw new SIATException("objeto devuelto como nulo");
                    }
                }

            } catch (Exception ex) {
                mensajeError = ex.getMessage();
                port = null;

                StringBuilder strFolios = new StringBuilder(" ");
                for (TramiteExisteConsultaVO strFoliosAdd : lstFolios) {
                    strFolios = strFolios.append("\n".concat(String.valueOf(strFoliosAdd.getFolioDeclaracion())));
                }
                LOG.error(IDENTIFICADOR_LOG + "Error de conexion fallida con WS conuslta detalle dev ISR intento:" + intento + " folios declaraciones :" + strFolios.toString(), ex);
            }

        }

        DateFormat fechaHoraF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = fechaHoraF.format(new Date());

        StringBuilder strFolios = new StringBuilder(" ");
        for (TramiteExisteConsultaVO strFoliosAdd : lstFolios) {
            strFolios = strFolios.append("\n".concat(String.valueOf(strFoliosAdd.getFolioDeclaracion())));
        }

        LOG.error(IDENTIFICADOR_LOG + "numero maximo de intentos sobrepasado: fecha y Hora:" + fechaHora + " folios declaraciones :" + strFolios.toString() + " error:" + mensajeError);
        throw new SIATException("Error de conexion fallida con WS conuslta detalle dev ISR Maximo numero de intentos realizados");
    }

    private void validateClientByExercise(int ejercicio) throws IOException {
        if (ejercicio <= CASE_2016) {
            if (port == null) {
                conectarWS2016();
            }
        } else {
            if (portV2 == null) {
                conectarWS2017();
            }
        }
    }

    private List<Long> getLstFolios(List<TramiteExisteConsultaVO> lstFolios) {
        if (lstFolios != null && !lstFolios.isEmpty()) {
            List<Long> lstFoliosLong = new ArrayList<Long>();

            for (TramiteExisteConsultaVO tramite : lstFolios) {
                lstFoliosLong.add(tramite.getFolioDeclaracion());
            }
            return lstFoliosLong;
        }
        return new ArrayList<Long>();
    }

}
