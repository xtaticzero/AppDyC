/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.plazostramite.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.plazostramite.vo.PlazoTramiteVO;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class PlazoTramiteMapper implements RowMapper<PlazoTramiteVO> {

    private static final Logger LOG = Logger.getLogger(PlazoTramiteMapper.class);

    @Override
    public PlazoTramiteVO mapRow(ResultSet rs, int i) throws SQLException {
        PlazoTramiteVO plazoTram = new PlazoTramiteVO();
        plazoTram.setFechaNotificacion(rs.getDate("FECHANOTIFICACION"));
        plazoTram.setFechaSolventacion(rs.getDate("FECHASOLVENTACION"));
        plazoTram.setNumControl(rs.getString("NUMCONTROL"));
        plazoTram.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
        plazoTram.setNumRequerimiento(rs.getInt("IDTIPODOCUMENTO"));
        try {
            plazoTram.setIdEstadoSol(rs.getInt("IDESTADOSOLICITUD"));
        } catch (Exception e) {
            LOG.info("no es solicitud:" + e.getMessage());
        }
        try {
            plazoTram.setIdEstadoComp(rs.getInt("IDESTADOCOMP"));
        } catch (Exception e) {
            LOG.info("no es compensacion:" + e.getMessage());
        }
        return plazoTram;
    }
}
