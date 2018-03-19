package mx.gob.sat.siat.dyc.dao.declaracion.impl;

import mx.gob.sat.siat.dyc.dao.declaracion.DyctNotaDAO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctNotaDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctNotaDAOImpl implements DyctNotaDAO {
    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public Long insertar(DyctNotaDTO nota) {
        Long idNota = this.jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDNOTACC.NEXTVAL FROM DUAL", Long.class);

        String sentSQLInsert =
            " INSERT INTO DYCT_NOTA (IDNOTA, TEXTO, FECHA, USUARIO, NUMCONTROL) VALUES(?, ?, ?, ?, ?) ";
        Object[] parametros = new Object[ConstantesDyCNumerico.VALOR_5];
        parametros[ConstantesDyCNumerico.VALOR_0] = idNota;
        parametros[ConstantesDyCNumerico.VALOR_1] = nota.getTexto();
        parametros[ConstantesDyCNumerico.VALOR_2] = nota.getFecha();
        parametros[ConstantesDyCNumerico.VALOR_3] = nota.getUsuario();
        parametros[ConstantesDyCNumerico.VALOR_4] = nota.getDycpServicioDTO().getNumControl();
        this.jdbcTemplateDYC.update(sentSQLInsert, parametros);
        return idNota;
    }
}
