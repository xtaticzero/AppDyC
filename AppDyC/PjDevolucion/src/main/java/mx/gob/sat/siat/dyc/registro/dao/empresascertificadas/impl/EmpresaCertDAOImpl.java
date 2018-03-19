package mx.gob.sat.siat.dyc.registro.dao.empresascertificadas.impl;

import java.sql.SQLException;

import mx.gob.sat.siat.dyc.registro.dao.empresascertificadas.EmpresaCertDAO;
import mx.gob.sat.siat.dyc.registro.dao.empresascertificadas.procedures.ConsultaEmpresasCertSP;
import mx.gob.sat.siat.dyc.registro.dto.empresascertificadas.EmpresaCertVO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "empresaCertDAO")
public class EmpresaCertDAOImpl implements EmpresaCertDAO {

    private static final Logger LOG = Logger.getLogger(EmpresaCertDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateInformixDyC;

    public EmpresaCertDAOImpl() {
        super();
    }

    @Override
    public EmpresaCertVO consultaEmpresaCertificada(EmpresaCertVO empresaCert) throws SIATException {
        EmpresaCertVO empresaCertificada = null;
        ConsultaEmpresasCertSP consultaEmpresasCert =
            new ConsultaEmpresasCertSP(jdbcTemplateInformixDyC, SQLInformixSIAT.STORE_PROCADURE_EMPRESAS_CERTIFICADAS);
        try {
            empresaCertificada = consultaEmpresasCert.obtieneEstadoDelCertificado(empresaCert);
        } catch (DataAccessException dae) {
            LOG.error(dae);
            ExtractorUtil.ejecutar(empresaCert);
            throw dae;

        } catch (SQLException e) {
            LOG.error(e);
            ExtractorUtil.ejecutar(empresaCert);
            throw new SIATException(e);
        }

        return empresaCertificada;
    }
}
