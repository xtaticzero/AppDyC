/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.service;

import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * Clase interface para la tabla [DYCC_MENSAJEUSR].
 * @author Paola Rivera
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Agosto 19, 2015
 * */
public interface DyccMensajeUsrService {

    DyccMensajeUsrDTO encontrar(Integer idMensaje, Integer idGrupoOperacion);
    
    /**
     * @param idMensaje
     * @param idCasoDeUso
     * @return
     */
    DyccMensajeUsrDTO obtieneMensaje(Integer idMensaje, Integer idCasoDeUso) throws SIATException;

    /**
     * @param idMensaje
     * @param idCasoDeUso
     * @param idTipoMensaje
     * @return
     * @throws SIATException
     */
    DyccMensajeUsrDTO obtieneMensaje(long idMensaje, long idCasoDeUso, Integer idTipoMensaje) throws SIATException;

}
