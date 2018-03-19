package mx.gob.sat.siat.dyc.dao.util.impl;

import mx.gob.sat.siat.dyc.dao.util.DycaActoAdmtvosDAO;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DycaActoAdmtvosMapper;
import mx.gob.sat.siat.dyc.domain.documento.DycaActoAdmtvosDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DycaActoAdmtvosDAOImpl implements DycaActoAdmtvosDAO {
    public DycaActoAdmtvosDAOImpl() {
        super();
    }

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOGGER = Logger.getLogger(DycaActoAdmtvosDAOImpl.class);

    /**
     * Se busca el acto administrativo dado de alta en NyV el cual esta asociado a la plantilla
     *
     *
     * @param claveUnidadAdministrativa
     * @param idPlantilla
     * @return
     */
    @Override
    public DycaActoAdmtvosDTO consultar(Integer claveUnidadAdministrativa, Integer idPlantilla) throws SIATException{

        DycaActoAdmtvosDTO actoAdministrativo = null;

        try {
            actoAdministrativo =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_ACTOADMTVO.toString(), new Object[] { claveUnidadAdministrativa,
                                                                                                    idPlantilla },
                                                   new DycaActoAdmtvosMapper());

        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.CONSULTA_ACTOADMTVO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                         " claveUnidadAdministrativa: " + claveUnidadAdministrativa + ", idPlantilla: " + idPlantilla);
            throw new SIATException ("No se encuentra registrada la plantilla: "+idPlantilla+" en la tabla de DYCAActosAdmtovs", e);
        }
        return actoAdministrativo;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
