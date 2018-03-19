/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.icep.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcsilDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @since 09/10/2013
 * @author ISCC
 */
public class ObtieneIcepUrdcsilMapper implements RowMapper<IcepUrdcsilDTO>  {
    
    public ObtieneIcepUrdcsilMapper() {
        super();
    }
    
    public IcepUrdcsilDTO mapRow(ResultSet resultado, int rowNum) throws SQLException {
        
        IcepUrdcsilDTO icepUrdcsilDTO = new IcepUrdcsilDTO();

        icepUrdcsilDTO.setEstatus(String.valueOf(resultado.getInt("x")));
        icepUrdcsilDTO.setTipoDeclaracion(resultado.getString("c_dec_ctdliea1"));
        icepUrdcsilDTO.setFechPresentacion(String.valueOf(resultado.getDate("f_dec_fperceh1")));
        icepUrdcsilDTO.setNumOper(String.valueOf(resultado.getInt("n_dec_noupmee1")));
        icepUrdcsilDTO.setSaldoFavor(String.valueOf(resultado.getInt("i_dec_sfaadmi1")));
        
        return icepUrdcsilDTO;
    }
}
