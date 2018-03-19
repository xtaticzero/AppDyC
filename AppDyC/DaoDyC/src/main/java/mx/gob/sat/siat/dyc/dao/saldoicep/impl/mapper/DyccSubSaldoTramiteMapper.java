/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.saldoicep.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccTipoTramiteDTO
 * @author Paola Rivera
 */
public class DyccSubSaldoTramiteMapper implements RowMapper<DyccSubSaldoTramDTO> {
    public DyccSubSaldoTramiteMapper() {
        super();
    }

    @Override
    public DyccSubSaldoTramDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccSubSaldoTramDTO dyccSubSaldoTramiteDTO = new DyccSubSaldoTramDTO();
        DyccSubOrigSaldoDTO dyccSuborigSaldoDTO= new DyccSubOrigSaldoDTO();
        DyccTipoTramiteDTO dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        DycaOrigenTramiteDTO dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();
        
        dyccSuborigSaldoDTO.setIdSuborigenSaldo(rs.getInt("iDsuborigensaldo"));
        dyccSubSaldoTramiteDTO.setDyccSuborigSaldoDTO(dyccSuborigSaldoDTO);
        
        dyccTipoTramiteDTO.setIdTipoTramite(rs.getInt("idTipoTramite"));
        dycaOrigenTramiteDTO.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);
        dyccSubSaldoTramiteDTO.setFechaInicio(rs.getDate("fechaInicio"));
        dyccSubSaldoTramiteDTO.setFechaFin(rs.getDate("fechaFin"));

        return dyccSubSaldoTramiteDTO;
    }

}
