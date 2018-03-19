package mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.springframework.jdbc.core.RowMapper;


public class DyctMovSaldoMapper implements RowMapper<DyctMovSaldoDTO>
{
    private DyctSaldoIcepDTO saldoIcep;
    private DyccMovIcepDTO tipoMovimiento;

    @Override
    public DyctMovSaldoDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyctSaldoIcepDTO saldoIcepL;
        if(saldoIcep != null)    {
            saldoIcepL = saldoIcep;
        }
        else
        {
            saldoIcepL = new DyctSaldoIcepDTO();
            saldoIcepL.setIdSaldoIcep(rs.getInt("IDSALDOICEP"));
        }

        DyccMovIcepDTO tipoMovimientoL;
        if(tipoMovimiento != null){
            tipoMovimientoL = tipoMovimiento;
        }
        else{
            tipoMovimientoL = BuscadorConstantes.obtenerMovIcep(rs.getInt("IDMOVIMIENTO"), rs.getInt("IDAFECTACION"));
        }

        String nombreColumnFechaRegistro = UtilsDominio.existeColumna(rs, "FECHAREGISTRO_MOVSALDO") ? "FECHAREGISTRO_MOVSALDO" : "FECHAREGISTRO" ;
        
        DyctMovSaldoDTO movimiento = new DyctMovSaldoDTO();
        movimiento.setIdMovSaldo(rs.getInt("IDMOVSALDO"));
        movimiento.setDyctSaldoIcepDTO(saldoIcepL);
        movimiento.setImporte(rs.getBigDecimal("IMPORTE"));
        movimiento.setFechaRegistro(rs.getTimestamp(nombreColumnFechaRegistro));
        movimiento.setFechaOrigen(rs.getDate("FECHAORIGEN"));
        movimiento.setDyccMovIcepDTO(tipoMovimientoL);
        movimiento.setIdOrigen(rs.getString("IDORIGEN"));
        return movimiento;
    }

    public DyctSaldoIcepDTO getSaldoIcep() {
        return saldoIcep;
    }

    public void setSaldoIcep(DyctSaldoIcepDTO saldoIcep) {
        this.saldoIcep = saldoIcep;
    }

    public DyccMovIcepDTO getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(DyccMovIcepDTO tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
}
