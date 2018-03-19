/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.service.impl;

import mx.gob.sat.siat.dyc.dao.mensajes.DyccMensajeUsrDAO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccTipoMensajeDTO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Clase service para la tabla [DYCC_MENSAJEUSR].
 * @author Paola Rivera
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Agosto 19, 2015
 * */
@Service(value = "dyccMensajeUsrService")
public class DyccMensajeUsrServiceImpl implements DyccMensajeUsrService {

    private static final Logger LOG = Logger.getLogger(DyccMensajeUsrServiceImpl.class.getName());

    @Autowired
    private DyccMensajeUsrDAO dyccMensajeUsrDAO;

    public DyccMensajeUsrServiceImpl() {
        super();
    }

    public DyccMensajeUsrDTO encontrar(Integer idMensaje, Integer idGrupoOperacion)  {
        try {
            return obtieneMensaje(idMensaje, idGrupoOperacion);
        } catch (SIATException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
            return null;
        }
    }

    @Override
    public DyccMensajeUsrDTO obtieneMensaje(Integer idMensaje, Integer idCasoDeUso) throws SIATException {
        DyccMensajeUsrDTO mensaje = new DyccMensajeUsrDTO();
        mensaje.setIdGrupoOperacion(idCasoDeUso);
        mensaje.setIdMensaje(idMensaje);

        DyccTipoMensajeDTO dyccTipoMensajeDTO = new DyccTipoMensajeDTO();
        dyccTipoMensajeDTO.setIdTipoMensaje(ConstantesDyCNumerico.VALOR_1);
        mensaje.setDyccTipoMensajeDTO(dyccTipoMensajeDTO);

        try {
            mensaje = dyccMensajeUsrDAO.obtieneMensaje(mensaje);
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }

        return mensaje;
    }


    public DyccMensajeUsrDTO obtieneMensaje(long idMensaje, long idCasoDeUso,
                                            Integer idTipoMensaje) throws SIATException {
        DyccMensajeUsrDTO mensaje = new DyccMensajeUsrDTO();

        mensaje.setIdMensaje((int)idMensaje);
        mensaje.setIdGrupoOperacion((int)idCasoDeUso);
        mensaje.setDyccTipoMensajeDTO(new DyccTipoMensajeDTO());
        mensaje.getDyccTipoMensajeDTO().setIdTipoMensaje(idTipoMensaje);

        try {
            mensaje = dyccMensajeUsrDAO.obtieneMensaje(mensaje);
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
            throw new SIATException(e);
        }

        return mensaje;
    }

}
