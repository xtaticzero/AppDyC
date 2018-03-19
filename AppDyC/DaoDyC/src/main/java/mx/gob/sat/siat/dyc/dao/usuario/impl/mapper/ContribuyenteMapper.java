package mx.gob.sat.siat.dyc.dao.usuario.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.springframework.jdbc.core.RowMapper;


public class ContribuyenteMapper implements RowMapper<DyctContribuyenteDTO>
{
    private DycpServicioDTO servicio;
    public static final  String SUBFIJO = "_CONTRIBUYENTE";

    @Override
    public DyctContribuyenteDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyctContribuyenteDTO contribuyente = new DyctContribuyenteDTO();
        
        contribuyente.setFechaConsulta(rs.getDate(UtilsDominio.obtenerNombreColumna("FECHACONSULTA", SUBFIJO, rs)));
        //contribuyente.setDatosContribuyente(rs.getBinaryStream("DATOSCONTRIBUYENTE")); //TODO: obtener el XML de los datos del contribuyente
        contribuyente.setRolPf(rs.getInt(UtilsDominio.obtenerNombreColumna("ROLPF", SUBFIJO, rs)));
        
        contribuyente.setRolPm(rs.getInt(UtilsDominio.obtenerNombreColumna("ROLPM", SUBFIJO, rs)));
        contribuyente.setRolGranContrib(rs.getInt(UtilsDominio.obtenerNombreColumna("ROLGRANCONTRIB", SUBFIJO, rs)));
        contribuyente.setRolDictaminado(rs.getInt(UtilsDominio.obtenerNombreColumna("ROLDICTAMINADO", SUBFIJO, rs)));
        contribuyente.setRolSociedadControl(rs.getInt(UtilsDominio.obtenerNombreColumna("ROLSOCIEDADCONTROL", SUBFIJO, rs)));

        if(servicio != null){
            contribuyente.setDycpServicioDTO(servicio);
        }
        else
        {
            DycpServicioDTO servicioL = new DycpServicioDTO();
            servicioL.setNumControl(rs.getString(UtilsDominio.obtenerNombreColumna("NUMCONTROL", SUBFIJO, rs)));

            contribuyente.setDycpServicioDTO(servicioL);
        }

        return contribuyente;
    }

    public DycpServicioDTO getServicio() {
        return servicio;
    }

    public void setServicio(DycpServicioDTO servicio) {
        this.servicio = servicio;
    }
}
