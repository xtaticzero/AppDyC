/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.mensajes.impl;


import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.mensajes.DyccMensajeUsrDAO;
import mx.gob.sat.siat.dyc.dao.mensajes.impl.mapper.DyccMensajeUsrMapper;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Clase Dao implementación para la tabla [DYCC_MENSAJEUSR].
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Agosto 19, 2015
 * */
@Repository
public class DyccMensajeUsrDAOImpl implements DyccMensajeUsrDAO{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOG = Logger.getLogger(DyccMensajeUsrDAOImpl.class.getName());

    /**
     *
     * @param mensajeUsrDTO
     * @return
     * @throws SQLException
     */
    @Override
    public DyccMensajeUsrDTO obtieneMensaje(DyccMensajeUsrDTO mensajeUsrDTO) throws SQLException {
        DyccMensajeUsrDTO mensajeUsr = new DyccMensajeUsrDTO();
        try {
            mensajeUsr =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEMENSAJE.toString(), new Object[] { mensajeUsrDTO.getIdMensaje(),
                                                                                                          mensajeUsrDTO.getIdGrupoOperacion(),
                                                                                                          mensajeUsrDTO.getDyccTipoMensajeDTO().getIdTipoMensaje() },
                                                        new DyccMensajeUsrMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEMENSAJE + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(mensajeUsrDTO));
        }

        return mensajeUsr;
    }

}
