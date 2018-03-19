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

import mx.gob.sat.siat.dyc.catalogo.dao.DyccMotivoResDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccMotivoResMapper;
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
@Repository(value = "dyccMotivoResDAO")
public class DyccMotivoResDAOImpl implements DyccMotivoResDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccMotivoResDAOImpl.class.getName());

    public DyccMotivoResDAOImpl() {
        super();
    }

    @Override
    public List<DyccMotivoResDTO> buscarMotivosResolucion(int tipoResol) {

        try {

            List<DyccMotivoResDTO> lDyccMotivoResDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.EMITIRRESOLUCION_BUSCARMOTIVOSRESOLUCION.toString(),
                                      new Object[] { tipoResol }, new DyccMotivoResMapper());
            return lDyccMotivoResDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_BUSCARMOTIVOSRESOLUCION.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    " tipoResol " + tipoResol);
            throw dae;
        }
    }
    
    @Override
    public List<DyccMotivoResDTO> buscarMotivosResolucionDesistimiento(int tipoResol) {

        try {

            List<DyccMotivoResDTO> lDyccMotivoResDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.EMITIRRESOLUCION_BUSCARMOTIVOSRESOLUCION_DESISTIMIENTO.toString(),
                                      new Object[] { tipoResol }, new DyccMotivoResMapper());
            return lDyccMotivoResDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_BUSCARMOTIVOSRESOLUCION_DESISTIMIENTO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    " tipoResol " + tipoResol);
            throw dae;
        }
    }
}
