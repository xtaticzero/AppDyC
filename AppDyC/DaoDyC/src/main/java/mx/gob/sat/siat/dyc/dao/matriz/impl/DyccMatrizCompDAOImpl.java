package mx.gob.sat.siat.dyc.dao.matriz.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.matriz.DyccMatrizCompDAO;
import mx.gob.sat.siat.dyc.dao.matriz.impl.mapper.DyccMatrizCompMapper;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.DyccTipoTramiteDAOImpl;
import mx.gob.sat.siat.dyc.domain.DyccMatrizCompDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccMatrizCompDAOImpl implements DyccMatrizCompDAO{

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOG = Logger.getLogger(DyccTipoTramiteDAOImpl.class.getName());


    @Override
    public List<DyccMatrizCompDTO> selecXConcepOrigConcepDestOrigensaldo(DyccConceptoDTO conceptoOrigen,
                                                                         DyccConceptoDTO conceptoDestino,
                                                                         DyccOrigenSaldoDTO origenSaldo) {

        List<DyccMatrizCompDTO> lmatriz = new ArrayList<DyccMatrizCompDTO>();

        try {
            DyccMatrizCompMapper mapper = new DyccMatrizCompMapper();
            
            mapper.setOrigenSaldo(origenSaldo);
            mapper.setConceptoOrigen(conceptoOrigen);
            mapper.setConceptoDestino(conceptoDestino);
            
            lmatriz =
                    getJdbcTemplateDYC().query(SQLOracleDyC.SQL_OBTENER_POR_CONCEPTO_ORIGEN_DESTINO_SALDO.toString(), new Object[] { origenSaldo.getIdOrigenSaldo(),
                                                                                                             conceptoDestino.getIdConcepto(),
                                                                                                             conceptoOrigen.getIdConcepto() },
                                              mapper );

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_POR_CONCEPTO_ORIGEN_DESTINO_SALDO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      conceptoOrigen + " - " + conceptoDestino + " - " + origenSaldo);
        }


        return lmatriz;
    }


    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
