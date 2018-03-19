package mx.gob.sat.siat.dyc.dao.rfc.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.rfc.DycbEstadoRfcDAO;
import mx.gob.sat.siat.dyc.dao.rfc.impl.mapper.DycbEstadoRfcMapper;
import mx.gob.sat.siat.dyc.domain.rfc.DycbEstadoRfcDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dycbEstadoRfcDAO")
public class DycbEstadoRfcDAOImpl implements DycbEstadoRfcDAO{

    private Logger log = Logger.getLogger(DycbEstadoRfcDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public DycbEstadoRfcDAOImpl() {
        super();
    }

    @Override
    public List<DycbEstadoRfcDTO> buscaRegistros(String rfc, Integer alta, Integer baja) {
        String sql =
            "SELECT D.IDESTADORFC, D.RFC, D.IDTIPOACCIONRFC, D.IDMOTIVORFC, D.OBSERVACIONES, D.USUARIORESP, MAX(D.FECHAMODIFICACION) AS FECHAMODIFICACION\n" +
            "FROM DYCB_ESTADORFC D WHERE D.RFC = ? AND D.IDTIPOACCIONRFC IN (?,?) \n" +
            "GROUP BY D.IDESTADORFC, D.RFC, D.IDTIPOACCIONRFC, D.IDMOTIVORFC, D.OBSERVACIONES, D.USUARIORESP \n" +
            "ORDER BY FECHAMODIFICACION DESC";
        List<DycbEstadoRfcDTO> objectoEstado = new ArrayList<DycbEstadoRfcDTO>();
        try {
            objectoEstado = jdbcTemplateDYC.query(sql, new Object[] { rfc, alta, baja }, new DycbEstadoRfcMapper());
        } catch (DataAccessException dae) {
            log.error("Ocurrio un error al obtener la informacion: " + dae.getMessage());
        }
        return objectoEstado;
    }

    @Override
    public void insertar(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException {
        try {

            String sql = "SELECT DYCQ_IDESTADORFC.NEXTVAL AS IDDECLARACION FROM DUAL";
            Integer consecutivo = jdbcTemplateDYC.queryForObject(sql, Integer.class);

            dycbEstadoRfcDTO.setId(consecutivo);

            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_BITACORA.toString(),
                                   new Object[] { dycbEstadoRfcDTO.getId(), dycbEstadoRfcDTO.getDycpRfcDTO().getRfc(),
                                                  dycbEstadoRfcDTO.getDyccTipoAccionRfcDTO().getIdTipoAccionRfc(),
                                                  dycbEstadoRfcDTO.getDyccMotivoRfcDTO().getIdMotivoRfc(),
                                                  dycbEstadoRfcDTO.getObservaciones(),
                                                  dycbEstadoRfcDTO.getUsuarioResp() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAR_BITACORA + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dycbEstadoRfcDTO));
            throw new SIATException(dae);
        }
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
