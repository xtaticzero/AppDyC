
/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.service.solicitud.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.xml.ws.BindingProvider;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevolucionRegistroVO;
import static mx.gob.sat.siat.dyc.generico.util.Utils.obtenerPropiedadDelMenu;
import mx.gob.sat.siat.dyc.registro.service.solicitud.ConsultaDevolucionesPendientesServices;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistraSolDevService;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tempuri2.ArrayOfMedioComunicacion;
import org.tempuri2.BuzonNotificaciones;
import org.tempuri2.BuzonNotificacionesSoap;


/**
 * Servicio para el Caso de uso registrar
 * @author Paola Rivera
 */
@Service(value = "registraSolDevService")
public class RegistraSolDevServiceImpl implements RegistraSolDevService {

    private static final Logger LOG = Logger.getLogger(RegistraSolDevServiceImpl.class.getName());
    private static final int TIEMPO_WS=5000;

    @Autowired
    private PersonaIDCService personaIDCService;
    @Autowired
    private DyccMensajeUsrService dyccMensajeUsrService;
    @Autowired
    private ConsultaDevolucionesPendientesServices serviceConsDevsPend;
    private  BuzonNotificacionesSoap buzonNotificacionesSoap ;

    /**
     * @param rfcRetenedor
     * @return
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    public ParamOutputTO<PersonaDTO> validaRFCRetenedor(String rfcRetenedor) throws SIATException {
        PersonaDTO persona = new PersonaDTO();
        persona.setRfc(rfcRetenedor);
        persona = personaIDCService.buscaPersona(persona);
        if (persona != null) {
            return new ParamOutputTO<PersonaDTO>(persona);
        } else {
            DyccMensajeUsrDTO mensaje =
                this.dyccMensajeUsrService.obtieneMensaje(ConstantesIds.MSG_3, ConstantesIds.CU_REGISTRO_SOLDEVLINEA,
                                                          ConstantesIds.CU_REGISTRO_SOLDEVLINEA);
            throw new SIATException(mensaje.getDescripcion());
        }

    }


    /**
     * @param rfcControlador
     * @return
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    public String validaRFCControlador(String rfcControlador) throws SIATException {
        DyccMensajeUsrDTO mensaje = new DyccMensajeUsrDTO();
        PersonaDTO persona = new PersonaDTO();
        persona.setRfc(rfcControlador);
        persona = personaIDCService.buscaPersona(persona);
        if (persona != null) {
            return "existe";
        } else {
            try {
                mensaje =
                        this.dyccMensajeUsrService.obtieneMensaje(ConstantesIds.MSG_47, ConstantesIds.CU_REGISTRO_SOLDEVLINEA);
            } catch (SIATException e) {
                LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
            }
            throw new SIATException(mensaje.getDescripcion());
        }

    }


    /**
     * @param rfc
     * @return
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Transactional(readOnly = true)
    @Override
    public ParamOutputTO<SolicitudDevolucionRegistroVO> solicitudesPendientes(String rfc) throws SIATException {
        List<SolicitudDevolucionRegistroVO> devoluciones = null;
        try {
            devoluciones = serviceConsDevsPend.solicitudesPendientes(rfc).getOutputs();
        } catch (DataAccessException e) {
            LOG.info("ERROR " + e.getMessage());
            throw new SIATException(e);
        }
        return new ParamOutputTO<SolicitudDevolucionRegistroVO>(devoluciones);
    }
   public boolean consultaSiRfcAmparado(String rfc) {
      

        Properties propiedades = new Properties();
        FileInputStream archivo = null;
        int intentos = 0;
        while (intentos < 2) {
            intentos++;
          
   
                    if (buzonNotificacionesSoap == null) {
                try {

                    archivo = new FileInputStream("/siat/dyc/configuraciondyc/automaticasdyc.properties");
                    propiedades.load(archivo);
             
                    BuzonNotificaciones buzonNotificaciones = new BuzonNotificaciones();

                    buzonNotificacionesSoap = buzonNotificaciones.getBuzonNotificacionesSoap();
                    BindingProvider bp = (BindingProvider) buzonNotificacionesSoap;
                    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, propiedades.getProperty("buzonnotif.service.wsdl"));
                    bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", TIEMPO_WS);
                    bp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", TIEMPO_WS);
                } catch (Exception ex) {
                    buzonNotificacionesSoap = null;
                    LOG.error("Error al consultar WS", ex);
                } finally {
                    try {
                        if (archivo != null) {
                            archivo.close();
                        }
                    } catch (IOException e) {
                        LOG.error("Error al cerrar el archivo ", e);
                    }
                }
            }
            
            try {
                ArrayOfMedioComunicacion amc = buzonNotificacionesSoap.obtieneMediosComunicacion(rfc);
                LOG.error("longitud lista " + amc.getMedioComunicacion().size());
                return !(amc.getMedioComunicacion().get(0).getAmparado() == 0);
              
            } catch (Exception ex) {
                buzonNotificacionesSoap = null;
                LOG.error("Error al consultar WS2", ex);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public  boolean desdeTramitesYNoEstaAmparado(String rfc) {
        try {
           if (obtenerPropiedadDelMenu("tramites") == 1  && !consultaSiRfcAmparado(rfc)) {
                return Boolean.TRUE;
            }
           
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
         return false;
    }
}
