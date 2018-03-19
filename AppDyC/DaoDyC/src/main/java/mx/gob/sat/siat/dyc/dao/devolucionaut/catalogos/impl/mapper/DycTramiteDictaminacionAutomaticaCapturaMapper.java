package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Jose Luis Aguilar
 */
public class DycTramiteDictaminacionAutomaticaCapturaMapper implements RowMapper<DycTramiteDictaminacionAutomaticaDTO> {

    public DycTramiteDictaminacionAutomaticaCapturaMapper() {
        super();
    }
    
    @Override
    public DycTramiteDictaminacionAutomaticaDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica = new DycTramiteDictaminacionAutomaticaDTO();

        tramiteDictaminacionAutomatica.setIdTramiteDicAut(    rs.getInt( "IDTRAMITEDICAU" ) );
        tramiteDictaminacionAutomatica.setOrigenSaldo(        rs.getString( "IDORIGENSALDO" ) );
        tramiteDictaminacionAutomatica.setIdTipoTramite(      rs.getInt( "IDTIPOTRAMITE" ) );
        tramiteDictaminacionAutomatica.setModelo(             rs.getString( "IDMODELO" ) );
        tramiteDictaminacionAutomatica.setIdConcepto(         rs.getInt( "IDCONCEPTO" ) );
        tramiteDictaminacionAutomatica.setIdImpuesto(         rs.getInt( "IDIMPUESTO" ) );
        tramiteDictaminacionAutomatica.setDictamenAutomatico( rs.getString( "DICTAMENAUT" ) );

        return tramiteDictaminacionAutomatica;
    }
    
}