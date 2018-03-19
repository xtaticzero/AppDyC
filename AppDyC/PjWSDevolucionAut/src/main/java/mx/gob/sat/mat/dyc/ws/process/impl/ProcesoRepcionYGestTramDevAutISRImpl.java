/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.process.impl;

import java.io.Serializable;
import javax.xml.ws.WebServiceContext;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionDevManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionRegistroYGestion;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.mat.dyc.ws.process.ProcesoConfirmarAutorizacionPago;
import mx.gob.sat.mat.dyc.ws.process.ProcesoDictaminadorAut;
import mx.gob.sat.mat.dyc.ws.process.ProcesoRepcionYGestTramDevAutISR;
import mx.gob.sat.mat.dyc.ws.utils.ProcesoRepcionYGestTramHelper;
import mx.gob.sat.mat.dyc.ws.utils.TipoTramiteEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
@Component("procesoRepcionYGestTramDevAutISR")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProcesoRepcionYGestTramDevAutISRImpl implements ProcesoRepcionYGestTramDevAutISR, Serializable {

    private static final long serialVersionUID = 7568773117243513650L;

    private final transient Logger logger;

    public ProcesoRepcionYGestTramDevAutISRImpl() {
        logger = Logger.getLogger(getClass());
    }

    @Autowired
    @Qualifier("procesoDictaminadorAut")
    private ProcesoDictaminadorAut procesoDictaminadorAut;

    @Autowired
    @Qualifier("procesoConfirmarAutorizacionPago")
    private ProcesoConfirmarAutorizacionPago procesoConfirmarAutorizacionPago;

    @Override
    @Transactional(readOnly = false,
            isolation = Isolation.DEFAULT,
            propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    public NotificacionRegistroYGestion execute(RegistroDevolucionAut registroDevolucionAut, NotificacionRegistroYGestion notificacionRegYPago, WebServiceContext wsContext) throws ServiceException {
        NotificacionDevManual notificacionRegistro = procesoDictaminadorAut.execute(ProcesoRepcionYGestTramHelper.castRegistroDevolucionAutToDevolucionManual(registroDevolucionAut),TipoTramiteEnum.REPCION_GEST_TRAM_DEV_AUT);
        logger.info(notificacionRegistro.getDatosSolicitud().getEstadoRegistro());
        logger.info(notificacionRegistro.getDatosSolicitud().getMotivo());
        procesoConfirmarAutorizacionPago.execute(registroDevolucionAut, notificacionRegYPago, notificacionRegistro, wsContext);
        return notificacionRegYPago;
    }

}
