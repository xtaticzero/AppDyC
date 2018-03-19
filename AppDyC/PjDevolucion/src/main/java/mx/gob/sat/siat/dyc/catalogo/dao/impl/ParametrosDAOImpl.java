package mx.gob.sat.siat.dyc.catalogo.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.ParametrosDAO;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "parametrosDAO")
public class ParametrosDAOImpl implements ParametrosDAO {
    public ParametrosDAOImpl() {
        super();
    }

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }


    @Override
    public Integer getObtenerMonto(int parametro) {
        String sql = "select valor from dycc_parametrosapp where idparametro = ?";
        String result = (String)getJdbcTemplateDYC().queryForObject(sql, new Object[] { parametro }, String.class);
        return Integer.parseInt(result);
    }

    @Override
    public String getConcepto(int concepto) {
        String resultado;
        List<String> result =
            jdbcTemplateDYC.queryForList(SQLOracleDyC.SQLCONCEPTO.toString(), new Object[] { concepto }, String.class);
        if (result.isEmpty()) {
            resultado = "0";
        } else {
            resultado = result.get(0);
        }
        return resultado;
    }

    @Override
    public String getImpuesto(int impuesto) {
        String resultado;
        List<String> result =
            jdbcTemplateDYC.queryForList(SQLOracleDyC.SQLIMPUESTO.toString(), new Object[] { impuesto }, String.class);
        if (result.isEmpty()) {
            resultado = "0";
        } else {
            resultado = result.get(0);
        }
        return resultado;
    }


    @Override
    public String getEjercicio(int ejercicio) {
        String resultado;
        List<String> result =
            jdbcTemplateDYC.queryForList(SQLOracleDyC.SQLEJERCICIO.toString(), new Object[] { ejercicio }, String.class);
        if (result.isEmpty()) {
            resultado = "0";
        } else {
            resultado = result.get(0);
        }
        return resultado;
    }


    @Override
    public String getPeriodo(int periodo) {
        String resultado;
        List<String> result =
            jdbcTemplateDYC.queryForList(SQLOracleDyC.SQLPERIODO.toString(), new Object[] { periodo }, String.class);
        if (result.isEmpty()) {
            resultado = "0";
        } else {
            resultado = result.get(0);
        }
        return resultado;
    }

    @Override
    public Integer getObtenMontoCompensacion(int parametro) {
        String sql = "select valor from dycc_parametrosapp where idparametro = ?";
        String resul = (String)getJdbcTemplateDYC().queryForObject(sql, new Object[] { parametro }, String.class);
        return Integer.parseInt(resul);
    }
}
