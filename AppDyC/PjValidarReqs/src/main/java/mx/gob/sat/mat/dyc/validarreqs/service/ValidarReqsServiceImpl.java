package mx.gob.sat.mat.dyc.validarreqs.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import mx.gob.sat.mat.dyc.validarreqs.dao.ValidarReqsDAOImpl;
import mx.gob.sat.siat.cobranza.diahabil.api.DiasHabilFacade;
import mx.gob.sat.siat.dyc.dao.DyccParametrosAppDAO;
import mx.gob.sat.siat.dyc.dao.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.DyctSeguimientoDAO;
import mx.gob.sat.siat.dyc.dao.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.domain.compensacion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyctSeguimientoDTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ValidarReqsServiceImpl
{
    @Autowired(required = true)
    private DiasHabilFacade diaHabilFacade;

    @Autowired
    private ValidarReqsDAOImpl dao;

    @Autowired
    private DyccParametrosAppDAO daoParametros;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DyctDocumentoDAO daoDocumento;
    
    @Autowired
    private DyctSeguimientoDAO daoSeguimiento;

    private static final Logger LOG = Logger.getLogger(ValidarReqsServiceImpl.class);
    
    private int plazoResolverRequerimientoComp;
    
    private static final int ID_PARAM_PLAZO_NUMDIAS_HABILES_SOLVENTAR_REQ_COMP = 16;
    
    private static final int HORA_VENCIMIENTO = 18;
    private static final int MINUTO_VENCIMIENTO = 0;
    private static final int SEGUNDO_VENCIMIENTO = 0;
    
    public void execute()
    {
        List<DyctReqInfoDTO> reqsValidar = dao.obtenerReqsValidar();

        plazoResolverRequerimientoComp = daoParametros.encontrar(ID_PARAM_PLAZO_NUMDIAS_HABILES_SOLVENTAR_REQ_COMP).getValor().intValue();
        LOG.info("plazoResolverRequerimientoComp (Dias Habiles) ->" + plazoResolverRequerimientoComp + "<-");

        LOG.info("numero de requerimientos que cumplen con los estatus para validar vencimiento ->" + reqsValidar.size() + "<-");
        int contReqsVencidos = 0;

        for (DyctReqInfoDTO reqInfo : reqsValidar)
        {
             if(reqInfo.getDycpServicioDTO().getDyccTipoServicioDTO() == Constantes.TiposServicio.SOLICITUD_DEVOLUCION){
                 LOG.info("El requerimiento '" + reqInfo.getNumControlDoc() + "' pertenece a una devolución, NO será analizado su vencimiento.");
             }
             else if(reqInfo.getDycpServicioDTO().getDyccTipoServicioDTO() == Constantes.TiposServicio.CASO_COMPENSACION)
             {
                if (requerimientoVencido(reqInfo))
                {
                    LOG.info("El requerimiento esta vencido ->" + reqInfo.getNumControlDoc() + "<-");
                    try 
                    {
                        DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
                        compensacion.setNumControl(reqInfo.getDycpServicioDTO().getNumControl());
                        compensacion.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.EN_PROCESO);
                        daoCompensacion.actualizarEstadocomp(compensacion);
                        

                        reqInfo.setDyccEstadoReqDTO(Constantes.EstadosReq.VENCIDO);
                        daoDocumento.actualizarEstadoreq(reqInfo);

                        DyctSeguimientoDTO seguimiento = new DyctSeguimientoDTO();
                        seguimiento.setComentarios("El requerimiento se cambia a VENCIDO debido a que el proceso aútomatico detectó que el plazo para solventar termino");
                        seguimiento.setFecha(new Date());
                        seguimiento.setResponsable("MAT-DYC");
                        seguimiento.setDyctDocumentoDTO(reqInfo);
                        seguimiento.setDyccAccionSegDTO(Constantes.AccionesSeg.VENCIMIENTO);
                        LOG.error("justo antes de insertar en seguimiento ");
                        daoSeguimiento.insertar(seguimiento);
                    }
                    catch (SIATException e) {
                        LOG.error("ocurrio un error al realizar operaciones por vencimiento de requerimiento ->" + e.getMessage());
                        ManejadorLogException.getTraceError(e);
                    }
    
                    contReqsVencidos++;
                }
                else {
                    LOG.info("el requerimiento " + reqInfo.getNumControlDoc() + " NO está vencido");
                }
            }
         }

         LOG.info("numero total de requerimiento vencidos ->" + contReqsVencidos + "<-");
    }

    private Boolean requerimientoVencido(DyctReqInfoDTO documento)
    {
        Date fechaNotificacion = documento.getFechaNotificacion();
        LOG.debug("fechaNotificacion ->" + fechaNotificacion);
        LOG.debug("plazoResolverRequerimiento ->" + plazoResolverRequerimientoComp);
        try {
            Date fechaVencimiento = diaHabilFacade.buscarDiaFederalRecaudacion(fechaNotificacion, plazoResolverRequerimientoComp);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(fechaVencimiento);
            calendar.set(Calendar.HOUR_OF_DAY, HORA_VENCIMIENTO);
            calendar.set(Calendar.MINUTE, MINUTO_VENCIMIENTO);
            calendar.set(Calendar.SECOND, SEGUNDO_VENCIMIENTO);

            fechaVencimiento = calendar.getTime();
            LOG.debug("fechaVencimiento ->" + fechaVencimiento);
            return new Date().after(fechaVencimiento);
        } catch (Exception e) {
            LOG.error(" Ocurrió un error al validar el vencimiento del documento " + documento +
                      " el estado del documento no será contemplado como vencido. El mensaje de la excepcion es : " +
                      e.getMessage());
            ManejadorLogException.getTraceError(e);
        }
        return Boolean.FALSE;
    }
}
