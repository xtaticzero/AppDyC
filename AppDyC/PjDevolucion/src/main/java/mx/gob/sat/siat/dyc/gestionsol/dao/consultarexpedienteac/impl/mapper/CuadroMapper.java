package mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroBean;

import org.springframework.jdbc.core.RowMapper;


public class CuadroMapper implements RowMapper<CuadroBean>{

    @Override
    public CuadroBean mapRow(ResultSet rs, int i) throws SQLException{
        CuadroBean cuadro = new CuadroBean();
                cuadro.setIdDetalleAviso(rs.getInt("IdDetalleAviso"));
                cuadro.setOrigenComp(rs.getString("OrigenComp"));
                cuadro.setTipoTramite(rs.getString("TipTramite"));
                cuadro.setPresentoDIOT(rs.getInt("PresentDiot"));
                cuadro.setNumOperacionDIOT(rs.getLong("NumOpDiot"));
                cuadro.setFechaPresentacionDIOT(rs.getDate("FechaDiot"));
                cuadro.setConcepto(rs.getString("Concepto"));
                cuadro.setImpuesto(rs.getString("Impuesto"));
                cuadro.setEjercicio(rs.getInt("Ejercicio"));
                cuadro.setPeriodo(rs.getString("Periodo"));
                cuadro.setTipoDePeriodo(rs.getString("TipoPeriodo"));
                cuadro.setEsRemanente(rs.getInt("EsRema"));
                cuadro.setNumeroControl(rs.getString("DelNumControl"));
                cuadro.setImpActualizado(rs.getDouble("ImpAct"));
                cuadro.setCantImpCompensa(rs.getDouble("ImpComp"));
                cuadro.setRemImpCompensa(rs.getDouble("ImpRem"));
                return cuadro;

    }
}
