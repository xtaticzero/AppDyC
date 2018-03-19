package mx.gob.sat.siat.dyc.vistas.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmDomDTO;

import org.springframework.jdbc.core.RowMapper;


public class AdmcUnidadAdmDomMapper implements RowMapper<AdmcUnidadAdmDomDTO>
{

    @Override
    public AdmcUnidadAdmDomDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        AdmcUnidadAdmDomDTO domicilio = new AdmcUnidadAdmDomDTO();
        domicilio.setCalle(rs.getString("CALLE"));
        domicilio.setNumExterior(rs.getString("NUMEXTERIOR"));
        domicilio.setNumInterior(rs.getString("NUMINTERIOR"));
        domicilio.setColonia(rs.getString("COLONIA"));
        domicilio.setCodigoPostal(rs.getInt("CODIGOPOSTAL"));
        domicilio.setMunicipio(rs.getString("MUNICIPIO"));
        domicilio.setEntidadFed(rs.getString("ENTIDADFED"));
        domicilio.setReferencias(rs.getString("REFERENCIAS"));
        domicilio.setIdUnidadAdmDom(rs.getInt("IDUNIDADADMDOM"));
        return domicilio;
    }
}
