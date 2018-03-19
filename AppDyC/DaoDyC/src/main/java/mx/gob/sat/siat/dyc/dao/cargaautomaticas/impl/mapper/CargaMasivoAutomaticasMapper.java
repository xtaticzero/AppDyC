/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.cargaautomaticas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import mx.gob.sat.siat.dyc.vo.CargaMasivaAutomaticasRegistroVO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class CargaMasivoAutomaticasMapper implements RowMapper<CargaMasivaAutomaticasRegistroVO> {
       private static final String FORMATO_D_M_Y_H = "dd/MM/yyyy HH:mm:ss";
    public CargaMasivoAutomaticasMapper() {
        super();
    }

    @Override
    public CargaMasivaAutomaticasRegistroVO mapRow(ResultSet rs, int i) throws SQLException {
        CargaMasivaAutomaticasRegistroVO objeto = new CargaMasivaAutomaticasRegistroVO();
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_D_M_Y_H);
        objeto.setFechaCarga(formateador.format(rs.getTimestamp("FECHACARGA")));
        objeto.setFechaInicioProceso(formateador.format(rs.getTimestamp("FECHAINICIOPROCESO")));
        objeto.setFechaTerminoProceso(formateador.format(rs.getTimestamp("FECHATERMINOPROCESO")));
        objeto.setNumeroSolicitudesExitosas(rs.getInt("NUMEROSOLICITUDESEXITOSAS"));
        
        objeto.setNumeroSolicitudesFallidas(rs.getInt("NUMEROSOLICITUDESFALLIDAS"));
        objeto.setNumeroSolicitudesProcesar(rs.getInt("NUMEROSOLICITUDESPROCESAR"));
        objeto.setResponsable(rs.getString("RESPONSABLE"));
        objeto.setRutaArchivoExitoso(rs.getString("URLARCHIVOEXITOSA"));
        objeto.setRutaArchivoFallido(rs.getString("URLARCHIVOFALLIDA"));
        objeto.setRutaArchivoProcesar(rs.getString("URLARCHIVOPROCESAR"));
        
        return objeto;
    }
}
