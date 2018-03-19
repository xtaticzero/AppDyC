package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctPapelTrabajoDTO;

import org.springframework.jdbc.core.RowMapper;


public class PapelTrabajoMapper implements RowMapper<DyctPapelTrabajoDTO>
{
    private DyctExpedienteDTO expediente;

    public DyctPapelTrabajoDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DyctExpedienteDTO expedienteL;
        
        if (expediente != null)
        {
            expedienteL = expediente;
        }
        else
        {
            DycpServicioDTO servicio = new DycpServicioDTO();
            servicio.setNumControl(rs.getString("NUMCONTROL"));
            expedienteL = new DyctExpedienteDTO();
            expedienteL.setServicioDTO(servicio);
        }

        DyctPapelTrabajoDTO papelTrabajo = new DyctPapelTrabajoDTO();
        papelTrabajo.setNombreArchivo(rs.getString("NOMBREARCHIVO"));
        papelTrabajo.setDescripcion(rs.getString("DESCRIPCION"));
        papelTrabajo.setUrl(rs.getString("URL"));
        papelTrabajo.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        papelTrabajo.setDyctExpedienteDTO(expedienteL);
        papelTrabajo.setFechaBaja(rs.getDate("FECHABAJA"));
        papelTrabajo.setIdPapelTrabajo(rs.getInt("IDPAPELTRABAJO"));
        return papelTrabajo;
    }

    public DyctExpedienteDTO getExpediente() {
        return expediente;
    }

    public void setExpediente(DyctExpedienteDTO expediente) {
        this.expediente = expediente;
    }
}
