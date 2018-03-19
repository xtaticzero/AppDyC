package mx.gob.sat.mat.dyc.herramientas.analizador.dao;

import java.util.List;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.mapper.VPagosMapper;
import mx.gob.sat.siat.migradordyc.dto.VPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Huetzin Cruz Lozano
 */
@Repository
public class VPagosInformixDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateInformix;
    
    public List<VPagoDTO> selecLikeXRfc(String paramLike)
    {
        String query = " SELECT * FROM VPAGOS WHERE RFCEEOG1 LIKE ? ";
        VPagosMapper mapper = new VPagosMapper();
        return jdbcTemplateInformix.query(query, new Object[]{paramLike}, mapper);
    }
    
}
