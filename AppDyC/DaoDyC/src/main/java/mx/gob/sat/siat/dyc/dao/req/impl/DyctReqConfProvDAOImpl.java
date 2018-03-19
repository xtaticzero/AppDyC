package mx.gob.sat.siat.dyc.dao.req.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctDocumentoMapper;
import mx.gob.sat.siat.dyc.dao.req.DyctReqConfProvDAO;
import mx.gob.sat.siat.dyc.dao.req.impl.mapper.ReqConfProvMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqConfProvDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctReqConfProvDAOImpl implements DyctReqConfProvDAO {
    private static final Logger LOG = Logger.getLogger(DyctReqConfProvDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void insertar(DyctReqConfProvDTO dyctReqConfProvDTO) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.REQCONFPROV_INSERT.toString(),
                                   new Object[] { dyctReqConfProvDTO.getDyctDocumentoDTO().getNumControlDoc(),
                                                  dyctReqConfProvDTO.getRfcProveedor() });

        } catch (DataAccessException dae) {
            LOG.error(dae.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.REQCONFPROV_INSERT + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctReqConfProvDTO));
            throw new SIATException(dae);
        }
    }


    @Override
    public List<DyctReqConfProvDTO> selecXServicioProveedor(DycpServicioDTO servicio, String rfcProveedor) {
        LOG.info("DycReqConfProvDAOImpl INICIO selecXServicioProveedor");
        LOG.info("rfcProveedor ->" + rfcProveedor + "<-");
        String query =
            "   SELECT * FROM DYCT_REQCONFPROV RC, DYCT_DOCUMENTO DR" + "   WHERE RC.NUMCONTROLDOC = DR.NUMCONTROLDOC " +
            "   AND DR.NUMCONTROL = ?" + "   AND RC.RFCPROVEEDOR = ?";

        DyctDocumentoMapper mapperDocumento = new DyctDocumentoMapper();
        ReqConfProvMapper mapper = new ReqConfProvMapper();
        mapper.setMapperDocumento(mapperDocumento);
        return this.getJdbcTemplateDYC().query(query, new Object[] { servicio.getNumControl(), rfcProveedor }, mapper);
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }
}
