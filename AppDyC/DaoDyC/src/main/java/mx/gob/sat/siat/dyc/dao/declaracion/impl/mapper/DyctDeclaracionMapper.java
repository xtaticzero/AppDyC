/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUsoDecDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla DYCT_DECLARACION
 * @author Federico Chopin Gachuz
 *
 * */
public class DyctDeclaracionMapper implements RowMapper<DyctDeclaracionDTO>
{
    private DycpServicioDTO servicio;
    
    @Override
    public DyctDeclaracionDTO mapRow(ResultSet resultSet, int i) throws SQLException
    {
        DycpServicioDTO servicioL;
        if(servicio != null){
            servicioL = servicio;
        }
        else
        {
            servicioL = new DycpServicioDTO();
            servicioL.setNumControl(resultSet.getString("NUMCONTROL"));
        }

        DyccUsoDecDTO usoDec = new DyccUsoDecDTO();
        usoDec.setIdUsoDec(resultSet.getInt("IDUSODEC"));
        
        DyctDeclaracionDTO declaracion = new DyctDeclaracionDTO();
        declaracion.setNumControl(resultSet.getString("NUMCONTROL"));
        declaracion.setIdDeclaracion(resultSet.getInt("IDDECLARACION"));
        declaracion.setFechaPresentacion(resultSet.getDate("FECHAPRESENTACION"));
        declaracion.setFechaCausacion(resultSet.getDate("FECHACAUSACION"));
        declaracion.setNumOperacion(resultSet.getString("NUMOPERACION"));
        declaracion.setNumDocumento(resultSet.getString("NUMDOCUMENTO"));
        declaracion.setSaldoAfavor(resultSet.getBigDecimal("SALDOAFAVOR"));
        declaracion.setDevueltoCompensado(resultSet.getBigDecimal("DEVUELTOCOMPENSADO"));
        declaracion.setAcreditamiento(resultSet.getBigDecimal("ACREDITAMIENTO"));
        declaracion.setImporte(resultSet.getBigDecimal("IMPORTE"));
        declaracion.setDyccUsoDecDTO(usoDec);
        declaracion.setDyccTipoDeclaraDTO(BuscadorConstantes.obtenerTipoDeclaracion(resultSet.getInt("IDTIPODECLARACION")));
        declaracion.setEtiquetaSaldo(resultSet.getString("ETIQUETASALDO"));
        declaracion.setDycpServicioDTO(servicioL);

        return declaracion;
    }

    public DycpServicioDTO getServicio() {
        return servicio;
    }

    public void setServicio(DycpServicioDTO servicio) {
        this.servicio = servicio;
    }
}
