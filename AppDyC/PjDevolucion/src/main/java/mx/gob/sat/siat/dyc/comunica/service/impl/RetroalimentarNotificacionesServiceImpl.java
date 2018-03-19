package mx.gob.sat.siat.dyc.comunica.service.impl;


import java.sql.Date;

import java.text.ParseException;

import java.util.List;

import mx.gob.sat.siat.dyc.comunica.service.RetroalimentarNotificacionesService;
import mx.gob.sat.siat.dyc.comunica.util.constante.ConstantesRetroNYV;
import mx.gob.sat.siat.dyc.comunica.util.recurso.MetodosGenerales;
import mx.gob.sat.siat.dyc.comunica.util.recurso.ReglasDeNegocioRetroNYV;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctReqInfoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.generico.util.FechaUtil;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DyctDocumentoVO;
import mx.gob.sat.siat.nyv.sistema.webservice.ResponseConsulta;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Verifica con base a reglas de negocio las fechas de inicio y fin de los plazos legales; tomando como
 * punto de partida la fecha de notificaci&oacute;n del contribuyente.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "retroalimentarNotificacionesService")
public class RetroalimentarNotificacionesServiceImpl implements RetroalimentarNotificacionesService {

    @Autowired
    private DyctDocumentoDAO dyctDocumentoReqDAO;

    @Autowired(required = true)
    private CalcularFechasService calcularFechasService;

    @Autowired
    private DyctReqInfoDAO dyctReqInfoDAO;

    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;

    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;

    private static final Logger LOGGER = Logger.getLogger(RetroalimentarNotificacionesServiceImpl.class);


    /**
     * Implementa el caso de uso de EnviarRetroalimientacionDeNotificaciones
     *
     * @param documento
     * @param respuestaNyV Datos proporcionados por arca en su base de datos.
     * @return 
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public boolean retroalimentarNotificacion(DyctDocumentoVO documento, ResponseConsulta respuestaNyV) throws SIATException {
        
        return actualizarDatos(respuestaNyV, documento);
        
    }

    /**
     * Realiza todas las operaciones del caso de uso RetroalimientarNotificaciones
     *
     * @param respuestaNyV
     * @return
     * @throws SIATException
     */
    private boolean actualizarDatos(ResponseConsulta respuestaNyV, DyctDocumentoVO documento) throws SIATException {

        MetodosGenerales objetoMetodosGenerales = new MetodosGenerales();

        Integer actualizado = 0;
        Date fechaInicial = new Date(System.currentTimeMillis());
        Date fechaFinPlazoLegal;

        List<Integer> listaRNFDC951 = ReglasDeNegocioRetroNYV.generarListaRNFDC951();
        List<Integer> listaRNFDC952 = ReglasDeNegocioRetroNYV.generarListaRNFDC952();
        List<Integer> listaRNFDC9531 = ReglasDeNegocioRetroNYV.generarListaRNFDC9531();
        List<Integer> listaRNFDC9532 = ReglasDeNegocioRetroNYV.generarListaRNFDC9532();
        List<Integer> listaRNFDC954 = ReglasDeNegocioRetroNYV.generarListaRNFDC954();

        String ids951 = objetoMetodosGenerales.generarClausulaIN(listaRNFDC951);
        String ids952 = objetoMetodosGenerales.generarClausulaIN(listaRNFDC952);
        String ids9531 = objetoMetodosGenerales.generarClausulaIN(listaRNFDC9531);
        String ids9532 = objetoMetodosGenerales.generarClausulaIN(listaRNFDC9532);
        String ids954 = objetoMetodosGenerales.generarClausulaIN(listaRNFDC954);

        try {
            

            if (respuestaNyV.getRespuesta() != null &&
                (respuestaNyV.getRespuesta().equals(ConstantesRetroNYV.NOTIFICADOXCONTRIBUYENTE) ||
                 respuestaNyV.getRespuesta().equals(ConstantesRetroNYV.NOTIFICADOXSISTEMA))) {

                dyctDocumentoDAO.actualizarEstadoDoc(ConstantesRetroNYV.DILIGENCIADO, documento.getNumControlDoc());
                LOGGER.info("JAHO - ACTUALIZA EL DOCUMENTO A ESTUS DE DILIGENCIADO: " + documento.getNumControlDoc() +
                            ", plantilla: " + documento.getIdPlantilla());
                //VALIDA SI LOS DATOS CONCUERDAN CON LA REGLA RNFDC951:
                if (dyctDocumentoReqDAO.buscarDocumentoEnPlantillas(documento.getNumControlDoc(), ids951) > 0) {
                    LOGGER.info("JAHO - ENTRA EN EL PRIMER IF: RNFDC951");
         
                        //SE CONGELA EL CONTEO DEL PLAZO LEGAL AL SIGUIENTE DIA HABIL:
                    if(dyctReqInfoDAO.buscarReqInfo(documento.getNumControlDoc())==null){
                        DyctReqInfoDTO dyctReqInfoDTO=new DyctReqInfoDTO();
                        dyctReqInfoDTO.setNumControlDoc(documento.getNumControlDoc());
                        dyctReqInfoDTO.setFechaNotificacion(new Date(respuestaNyV.getFechaNotificacion().getTime().getTime()));
                        dyctReqInfoDAO.insertar(dyctReqInfoDTO);
                        LOGGER.info("JAHO - INSERTA NUEVO REGISTRO REQINFO:"+documento.getNumControlDoc());
                        actualizado=1;
                    }else{
                        
                        actualizado=dyctReqInfoDAO.actualizarFecha("", new Date(respuestaNyV.getFechaNotificacion().getTime().getTime()),
                                                               documento.getNumControlDoc());
                    }
                   
                } 
                 //VALIDA SI LOS DATOS CONCUERDAN CON LA REGLA RNFDC952:
                if (dyctDocumentoReqDAO.buscarDocumentoEnPlantillas(documento.getNumControlDoc(), ids952) > 0) {
                    LOGGER.info("JAHO - ENTRA EN EL SEGUNDO IF: RNFDC952");
                    dycpSolicitudDAO.actualizarFechaFinTramite(new Date(respuestaNyV.getFechaNotificacion().getTime().getTime()),
                                                               documento.getNumControl());


                    //VALIDA SI LOS DATOS CONCUERDAN CON LA REGLA RNFDC953 PARA EL PLAZO DE 20 DIAS Y SE ACTUALIZAN LAS FECHAS INICIO Y FIN DE LOS PLAZOS LEGALES:
                } else if (dyctDocumentoReqDAO.buscarDocumentoEnPlantillas(documento.getNumControlDoc(), ids9531) >
                           0) {
                    LOGGER.info("JAHO - ENTRA EN EL TERCER IF: RNFDC9531");
                    fechaFinPlazoLegal =
                            new Date(calcularFechasService.calcularFechaFinal(fechaInicial, ConstantesRetroNYV.DIASHABILES21,
                                                                              1).getTime());
                    acutalizarFechasTramite(documento.getNumControlDoc(),
                                            new Date(respuestaNyV.getFechaNotificacion().getTime().getTime()),
                                            fechaFinPlazoLegal);

                    //VALIDA SI LOS DATOS CONCUERDAN CON LA REGLA RNFDC953 PARA EL PLAZO DE 15 DIAS Y SE ACTUALIZAN LAS FECHAS INICIO Y FIN DE LOS PLAZOS LEGALES:
                } else if (dyctDocumentoReqDAO.buscarDocumentoEnPlantillas(documento.getNumControlDoc(), ids9532) >
                           0) {
                    LOGGER.info("JAHO - ENTRA EN EL CUARTO IF: RNFDC9532");
                    fechaFinPlazoLegal =
                            new Date(calcularFechasService.calcularFechaFinal(fechaInicial, ConstantesRetroNYV.DIASHABILES15,
                                                                              1).getTime());
                    acutalizarFechasTramite(documento.getNumControlDoc(),
                                            new Date(respuestaNyV.getFechaNotificacion().getTime().getTime()),
                                            fechaFinPlazoLegal);

                    //VALIDA SI LOS DATOS CONCUERDAN CON LA REGLA RNFDC954 Y SE ACTUALIZAN LAS FECHAS INICIO Y FIN DE LOS PLAZOS LEGALES:
                } else if (dyctDocumentoReqDAO.buscarDocumentoEnPlantillas(documento.getNumControlDoc(), ids954) > 0) {
                    LOGGER.info("JAHO - ENTRA EN EL QQUINTO IF: RNFDC954");
                    fechaFinPlazoLegal =
                            new Date(calcularFechasService.calcularFechaFinal(fechaInicial, ConstantesRetroNYV.DIASHABILES11,
                                                                              1).getTime());
                    acutalizarFechasTramite(documento.getNumControlDoc(),
                                            new Date(respuestaNyV.getFechaNotificacion().getTime().getTime()),
                                            fechaFinPlazoLegal);
                }
                LOGGER.info("JAHO - TERMINA EL PROCESO");
            }else{
                 LOGGER.info("JAHO - NO ENTRA AL PROCESO POR EL TIPO DE RESPUESTA " + documento.getNumControlDoc()); 
            }                                     

        } catch (ParseException e) {
            LOGGER.error("actualizarDatos(), error: " + e);
        }
        return actualizado > 0;
    }

    /**
     * Actualiza las fechas de inicio y fin de los tramites legales a los dias habiles correspondientes dependiendo de la regla de negocio que se valida:
     *
     * @param idDocumentoReq identificador de documento de requerimiento
     * @param fechaInicioPlazoLegal fecha de resultado obtenido de los datos de arca
     * @param fechaFinPlazoLegal fecha que se calcula dependiendo de la regla de negocio que se aplica, ya sea de 10, 15 o 20 dias habiles.
     * @throws SIATException
     */
    private void acutalizarFechasTramite(String idDocumentoReq, Date fechaInicioPlazoLegal,
                                         Date fechaFinPlazoLegal) throws SIATException, ParseException {
        dyctDocumentoReqDAO.actualizarFechaInicioPlazoLegal(idDocumentoReq, fechaInicioPlazoLegal);
        dyctDocumentoReqDAO.actualizarFechaFinPlazoLegal(idDocumentoReq, fechaFinPlazoLegal);
        LOGGER.error("DOCUMENTO-retroalimentado: '" + idDocumentoReq + "'. fechaInicioPlazoLegal: " +
                     ((fechaInicioPlazoLegal != null) ? FechaUtil.darFormatoFecha(fechaInicioPlazoLegal, "dd/MM/yyyy") :
                      "") + ". fechaFinPlazoLegal: " +
                     ((fechaFinPlazoLegal != null) ? FechaUtil.darFormatoFecha(fechaFinPlazoLegal, "dd/MM/yyyy") : ""));
    }
}
