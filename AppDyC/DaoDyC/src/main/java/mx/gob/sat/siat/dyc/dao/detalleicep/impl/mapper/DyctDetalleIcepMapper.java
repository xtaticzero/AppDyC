package mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDetalleIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


public class DyctDetalleIcepMapper implements RowMapper<DyctDetalleIcepDTO>
{
    private DyctSaldoIcepDTO saldoIcep;

    @Override
    public DyctDetalleIcepDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyctSaldoIcepDTO dyctSaldoICEPDTO;

        if(saldoIcep != null){
            dyctSaldoICEPDTO = saldoIcep;
        }
        else
        {
            dyctSaldoICEPDTO = new DyctSaldoIcepDTO();
            dyctSaldoICEPDTO.setIdSaldoIcep(rs.getInt("IDSALDOICEP"));
        }

        DyccMovIcepDTO dyccMovIcepDTO = new DyccMovIcepDTO();
        dyccMovIcepDTO.setIdMovimiento(rs.getInt("IDMOVIMIENTO"));
        dyccMovIcepDTO.setDyccAfectaIcepDTO(BuscadorConstantes.obtenerTipoAfectacionIcep(rs.getInt("IDAFECTACION")));

        DyctDetalleIcepDTO movimiento = new DyctDetalleIcepDTO();
        movimiento.setImporteMovimiento (rs.getBigDecimal("IMPORTEMOVIMIENTO"));
        movimiento.setFechaMovimiento   (rs.getDate("FECHAMOVIMIENTO"));
        movimiento.setIdDetalleIcep     (rs.getInt("IDDETALLEICEP"));
        movimiento.setNumControl   (rs.getString("NUMCONTROL"));
        movimiento.setDyctSaldoIcepDTO  (dyctSaldoICEPDTO);
        movimiento.setDyccMovIcepDTO    (dyccMovIcepDTO);

        return movimiento;
    }

    public DyctSaldoIcepDTO getSaldoIcep() {
        return saldoIcep;
    }

    public void setSaldoIcep(DyctSaldoIcepDTO saldoIcep) {
        this.saldoIcep = saldoIcep;
    }
}
