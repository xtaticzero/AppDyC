package mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


public class DyctResolCompMapper implements RowMapper<DyctResolCompDTO>
{
    private Logger logger = Logger.getLogger(DyctResolCompMapper.class.getName());
    
    private DycpCompensacionDTO compensacion;

    @Override
    public DyctResolCompDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        logger.debug("compensacion ->" + compensacion + "<-");
        DycpCompensacionDTO compensacionL;

        if (compensacion != null) {
            compensacionL = compensacion;
        } else {
            DycpServicioDTO servicio = new DycpServicioDTO();
            servicio.setNumControl(rs.getString("NUMCONTROL"));
            compensacionL = new DycpCompensacionDTO();
            compensacionL.setDycpServicioDTO(servicio);
        }

        DyctResolCompDTO resolComp = new DyctResolCompDTO();
        resolComp.setDycpCompensacionDTO(compensacionL);
        compensacionL.setDyctResolCompDTO(resolComp);
        resolComp.setDyccAccionSegDTO(BuscadorConstantes.obtenerAccionSeg(rs.getInt("IDACCIONSEG")));
        resolComp.setFechaResolucion(rs.getDate("FECHARESOLUCION"));
        resolComp.setObservaciones(rs.getString("OBSERVACIONES"));
        resolComp.setDyccEstResolDTO(BuscadorConstantes.obtenerEstadoResolucion(rs.getInt("IDESTRESOL")));
        resolComp.setDyccTipoResolDTO(BuscadorConstantes.obtenerTipoResolucion(rs.getInt("IDTIPORESOL")));

        return resolComp;
    }

    public DycpCompensacionDTO getCompensacion() {
        return compensacion;
    }

    public void setCompensacion(DycpCompensacionDTO compensacion) {
        this.compensacion = compensacion;
    }
}
