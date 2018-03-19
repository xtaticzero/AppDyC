/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.process.impl;

import java.io.Serializable;
import java.math.BigInteger;
import mx.gob.sat.mat.dyc.devolucionaut.service.ConsultaExistenciaSolDevService;
import mx.gob.sat.mat.dyc.ws.domain.BusquedaTramitesManuales;
import mx.gob.sat.mat.dyc.ws.domain.DatosContribuyente;
import mx.gob.sat.mat.dyc.ws.domain.DatosRegistroMATDYC;
import mx.gob.sat.mat.dyc.ws.domain.DatosSolicitud;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionConsulSolIRS;
import mx.gob.sat.mat.dyc.ws.process.ProcesoConsultarExistenciaSolDev;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component("procesoConsultarExistenciaSolDev")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE, proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ProcesoConsultarExistenciaSolDevImpl implements ProcesoConsultarExistenciaSolDev, Serializable {
    
    private static final Logger LOG = Logger.getLogger(ProcesoConsultarExistenciaSolDevImpl.class);
    private static final long serialVersionUID = 5831788649180589570L;

    @Autowired
    private transient ConsultaExistenciaSolDevService consultaExistenciaSolDevService;
    
    @Override
    public NotificacionConsulSolIRS procesarSolCompIRSDevAut ( BusquedaTramitesManuales busquedaTramites ) throws SIATException{
        LOG.info("ProcesoConsultarExistenciaSolDevImpl.serialVersionUID:: " + serialVersionUID + " " + ProcesoConsultarExistenciaSolDevImpl.class.hashCode());
        NotificacionConsulSolIRS registroSalida = generarSalida();
        String folioRetorno;

        try {

            String     rfc       = busquedaTramites.getDatosContribuyente().getRFC();
            BigInteger impuesto  = busquedaTramites.getDatosICEP().getImpuesto();
            BigInteger concepto  = busquedaTramites.getDatosICEP().getConcepto();
            BigInteger ejercicio = busquedaTramites.getDatosICEP().getEjercicio();
            String     periodo   = busquedaTramites.getDatosICEP().getPeriodo();

            folioRetorno = consultaExistenciaSolDevService
                                .consultarSolCompIRSDevAut( rfc, impuesto, concepto, ejercicio, periodo );

            asignarValoresSalida(
                registroSalida, 
                folioRetorno, 
                busquedaTramites.getDatosContribuyente().getRFC() 
            );
            return registroSalida;

        } catch (Exception ex) {
            LOG.error( "Error al consultar la existencia de una sol. de dev. ::: " + ex.getMessage() );
            throw new SIATException("Error al consultar la solicitud:: " + ex.getMessage(), ex);
        }
        
    }

    private NotificacionConsulSolIRS generarSalida(){
        NotificacionConsulSolIRS registroSalida = new NotificacionConsulSolIRS();

        registroSalida.setDatosContribuyente( new DatosContribuyente() );
        registroSalida.setDatosSolicitud( new DatosSolicitud() );

        return registroSalida;
    }
    
    private void asignarValoresSalida ( NotificacionConsulSolIRS registroSalida, 
                                      String folioRetorno, String rfc ){

        registroSalida.getDatosContribuyente().setRFC( rfc );

        if ( folioRetorno != null ){

            registroSalida.setDatosRegistroMATDYC( new DatosRegistroMATDYC() );
            registroSalida.getDatosRegistroMATDYC().setFolioMATDYC( folioRetorno );

            registroSalida.getDatosSolicitud().setEstadoRegistro(BigInteger.ONE );

        } else {

            registroSalida.getDatosSolicitud().setEstadoRegistro(BigInteger.ZERO );
        }
    }
    
}

