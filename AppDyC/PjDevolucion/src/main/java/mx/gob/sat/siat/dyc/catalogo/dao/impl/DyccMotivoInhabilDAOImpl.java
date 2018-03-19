/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccMotivoInhabilDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccMotivoInhabilMapper;
import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoInhabilDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * DAO creado para el DTO DyccMotivoInhabilDTO
 * @author Alfredo Ramirez
 * @since 16/08/2013
 *
 */
@Repository(value = "dyccMotivoInhabilDAO")
public class DyccMotivoInhabilDAOImpl implements DyccMotivoInhabilDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccMotivoInhabilDAOImpl.class);

    public DyccMotivoInhabilDAOImpl() {
        super();
    }

    @Override
    public List<DyccMotivoInhabilDTO> consultarMotivosInhabil(String tipoCalendario) {
        List<DyccMotivoInhabilDTO> dyccMotivoInhabil = new ArrayList<DyccMotivoInhabilDTO>();
        try {
            dyccMotivoInhabil =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_MOTIVOSINHABIL.toString().replace("<p1>", tipoCalendario),
                                               new Object[] { }, new DyccMotivoInhabilMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTARCATALOGOS_MOTIVOSINHABIL + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    tipoCalendario);
        }
        return dyccMotivoInhabil;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

}
