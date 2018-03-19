/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.catalogo.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccSubMotivoResDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccSubMotivoResMapper;
import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoResDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Federico Chopin Gachuz
 * @date Diciembre 11, 2013
 *
 * */
@Repository(value = "dyccSubMotivoResDAO")
public class DyccSubMotivoResDAOImpl implements DyccSubMotivoResDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccSubMotivoResDAOImpl.class.getName());

    public DyccSubMotivoResDAOImpl() {
        super();
    }

    @Override
    public List<DyccMotivoResDTO> buscarSubMotivosResolucion(int motivoRes) {

        try {

            List<DyccMotivoResDTO> lDyccSubMotivoResDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.EMITIRRESOLUCION_BUSCARSUBMOTIVOSRESOLUCION.toString(),
                                      new Object[] { motivoRes }, new DyccSubMotivoResMapper());
            return lDyccSubMotivoResDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_BUSCARSUBMOTIVOSRESOLUCION.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    " motivoRes " + motivoRes);
            throw dae;
        }
    }
}
