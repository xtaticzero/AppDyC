package mx.gob.sat.siat.dyc.dao.mensajes.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.mensajes.DyccGrupoOperDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccTipoMensajeDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 */
public class DyccMensajeUsrMapper implements RowMapper<DyccMensajeUsrDTO>{

    @Override
    public DyccMensajeUsrDTO mapRow(ResultSet rs, int i) throws SQLException{
        DyccMensajeUsrDTO mensajeUsr = new DyccMensajeUsrDTO();
        mensajeUsr.setIdMensaje(rs.getInt("IDMENSAJE"));
        DyccGrupoOperDTO grupoOper = new DyccGrupoOperDTO();
        grupoOper.setIdGrupoOperacion(rs.getInt("IDGRUPOOPERACION"));
        mensajeUsr.setDyccGrupoOperDTO(grupoOper);
        mensajeUsr.setDescripcion(rs.getString("DESCRIPCION"));
        mensajeUsr.setFechaInicio(rs.getDate("FECHAINICIO"));
        mensajeUsr.setFechaFin(rs.getDate("FECHAFIN"));
        DyccTipoMensajeDTO tipoMensaje = new DyccTipoMensajeDTO();
        tipoMensaje.setIdTipoMensaje(rs.getInt("IDTIPOMENSAJE"));
        mensajeUsr.setDyccTipoMensajeDTO(tipoMensaje);
        return mensajeUsr;
    }
}
