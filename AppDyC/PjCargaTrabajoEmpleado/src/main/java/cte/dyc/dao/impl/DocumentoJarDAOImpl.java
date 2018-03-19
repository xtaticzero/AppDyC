package cte.dyc.dao.impl;


import cte.dyc.dao.DocumentoJarDAO;
import cte.dyc.dao.impl.mapper.DocumentoJarMapper;
import cte.dyc.dto.DyctDocumentoDTO;
import cte.dyc.generico.ExtractorUtil;
import cte.dyc.generico.util.common.SIATException;
import cte.dyc.generico.util.constante.ConstantesDyC;
import cte.dyc.generico.util.querys.SQLOracleDyC;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "documentoJarDAO")
public class DocumentoJarDAOImpl implements DocumentoJarDAO, SQLOracleDyC {
    private Logger log = Logger.getLogger("loggerDYC");

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public DocumentoJarDAOImpl() {
        super();
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    @Override
    public List<DyctDocumentoDTO> consultarDocumentoAprobador(Integer empleado) throws SIATException {
        List<DyctDocumentoDTO> lista;
        try {
            lista =
jdbcTemplateDYC.query(CONSULTA_DOCUMENTO_APROBADORES, new Object[] { empleado, empleado, empleado, empleado },
                      new DocumentoJarMapper());

        } catch (Exception dae) {
            log.error(ConstantesDyC.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC.TEXTO_2_ERROR_DAO +
                      CONSULTA_DOCUMENTO_APROBADORES + ConstantesDyC.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(empleado));
            throw new SIATException(dae);
        }
        return lista;
    }
}
