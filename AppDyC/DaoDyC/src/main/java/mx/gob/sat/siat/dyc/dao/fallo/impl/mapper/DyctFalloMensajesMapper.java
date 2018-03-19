package mx.gob.sat.siat.dyc.dao.fallo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloDetalleDTO;
import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * Clase mapper para el DTO DyctFalloMensajesDTO
 *
 * @author Softtek
 *
 */
public class DyctFalloMensajesMapper implements RowMapper<DyctFalloMensajesDTO> {

    private static final Logger LOGGER = Logger.getLogger(DyctFalloMensajesMapper.class);

    @Override
    public DyctFalloMensajesDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctFalloMensajesDTO dyctFalloMensajesDTO = new DyctFalloMensajesDTO();
        DyccFalloDetalleDTO dyccFalloDetalleDTO = new DyccFalloDetalleDTO();

        dyctFalloMensajesDTO.setIdFalloMensaje(rs.getInt("IDFALLOMENSAJE"));
        dyctFalloMensajesDTO.setMensaje(rs.getString("MENSAJE"));

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dyctFalloMensajesDTO.setHora(sdf.parse(rs.getString("HORA").toString()));
        } catch (ParseException e) {
            LOGGER.error(String.format("No se puedo convertir la fecha para el mensaje: %s",
                                       dyctFalloMensajesDTO.getMensaje()));
        }

        dyctFalloMensajesDTO.setTipoDocumento(rs.getString("TIPODOCUMENTO"));
        dyctFalloMensajesDTO.setActoAdministrativo(rs.getInt("ACTOADMINISTRATIVO"));
        dyctFalloMensajesDTO.setCveUnidadAdmtva(rs.getInt("CVEUNIDADADMTVA"));
        dyctFalloMensajesDTO.setNumControl(rs.getString("NUMCONTROL"));

        dyccFalloDetalleDTO.setIdFalloDetalle(rs.getInt("IDFALLODETALLE"));
        dyctFalloMensajesDTO.setIdFalloDetalle(dyccFalloDetalleDTO);

        return dyctFalloMensajesDTO;
    }

}
