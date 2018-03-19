package mx.gob.sat.siat.dyc.dao.anexo.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.anexo.DyctAnexoDAO;
import mx.gob.sat.siat.dyc.dao.anexo.impl.mapper.DyctAnexoConsultarExpedienteMapper;
import mx.gob.sat.siat.dyc.domain.anexo.DycaSolAnexoTramDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.ConsultarExpedienteVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author JEFG
 *
 * */
@Repository(value = "dyctAnexoDAO")
public class DyctAnexoDAOImpl implements DyctAnexoDAO {
    private static final Logger LOG = Logger.getLogger(DyctAnexoDAOImpl.class.getName());
    @Autowired(required =true)
    private JdbcTemplate jdbcTemplateDYC;

    public DyctAnexoDAOImpl() {
        super();
    }

    @Override
    public void insertarAnexo(DycaSolAnexoTramDTO input) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAANEXO.toString(),
                                   new Object[] { input.getDyccAnexoTramiteDTO().getDyccMatrizAnexosDTO().getIdAnexo(),
                                                  input.getDyccAnexoTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                  input.getNumControl(), input.getNombreArchivo(), input.getUrl() });
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAANEXO + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(input));
            throw new SIATException(dae);
        }
    }

    @Override
    public List<ConsultarExpedienteVO> buscarDocsAnexos(String numControl){

        try {

            List<ConsultarExpedienteVO> lDyctAnexoDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDOCSANEXOSPORNUMERODECONTROL.toString(),
                                      new Object[] { numControl }, new DyctAnexoConsultarExpedienteMapper());

            return lDyctAnexoDTO;

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDOCSANEXOSPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

    }


    @Override
    public Integer buscaAnexos(String numControl, String anexos) {
        return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_SOLANEXO_NUMCTRLYARCHIVO.toString(),
                                              new Object[] { numControl, anexos }, Integer.class);
    }

    @Override
    public void actualizarUrl(String numControl, String url) {
        String sql = SQLOracleDyC.UPDATE_SOLANEXO + url + SQLOracleDyC.WHERE_SOLANEXO;
        jdbcTemplateDYC.update(sql, new Object[] { numControl });
    }

}
