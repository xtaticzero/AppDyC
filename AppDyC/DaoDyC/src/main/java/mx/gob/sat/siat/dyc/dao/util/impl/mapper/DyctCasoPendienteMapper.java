/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.caso.DyccMotivoCasoDTO;
import mx.gob.sat.siat.dyc.domain.caso.DyctCasoPendienteDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctCasoPendienteMapper implements RowMapper<DyctCasoPendienteDTO> {
    public DyctCasoPendienteMapper() {
        super();
    }

    @Override
    public DyctCasoPendienteDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctCasoPendienteDTO dyctCasoPenDTO = new DyctCasoPendienteDTO();
        DyccMotivoCasoDTO dyccMotivoCasoDTO = new DyccMotivoCasoDTO();

        dyctCasoPenDTO.setIdDeclaracion(rs.getInt("IDDECLARACION"));
        dyctCasoPenDTO.setFechaProcesamiento(rs.getDate("FECHAPROCESAMIENTO"));

        dyctCasoPenDTO.setIdImpuesto(rs.getInt("IDIMPUESTO"));
        dyctCasoPenDTO.setIdConcepto(rs.getInt("IDCONCEPTO"));
        dyctCasoPenDTO.setIdEjercicio(rs.getInt("IDEJERCICIO"));
        dyctCasoPenDTO.setIdPeriodo(rs.getInt("IDPERIODO"));
        dyctCasoPenDTO.setNumOperacion(rs.getInt("NUMOPERACION"));

        dyccMotivoCasoDTO.setIdMotivo(rs.getInt("IDMOTIVO"));
        dyctCasoPenDTO.setDyccMotivoCasoDTO(dyccMotivoCasoDTO);
        return dyctCasoPenDTO;
    }
}
