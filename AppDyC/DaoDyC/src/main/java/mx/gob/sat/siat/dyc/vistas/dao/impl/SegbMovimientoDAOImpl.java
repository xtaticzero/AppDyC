/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.vistas.dao.impl;

import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vistas.dao.SegbMovimientoDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * ManagedBean pantalla MantenerMatrizDictaminadores.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * @date Abril 16, 2014
 */
@Repository(value = "segbMovimientoDAO")
public class SegbMovimientoDAOImpl implements SegbMovimientoDAO {
    private static final Logger LOG = Logger.getLogger("loggerDyC");

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public SegbMovimientoDAOImpl() {
        super();
    }

    @Override
    public void agregaIdentificador(SegbMovimientoDTO segbMovimiento) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_IDENTIFICADOR_MOV.toString(),
                                   new Object[] { segbMovimiento.getIdentificador(), segbMovimiento.getFolio() });
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZA_IDENTIFICADOR_MOV + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(segbMovimiento));
            throw new SIATException(dae);
        }
    }

}
