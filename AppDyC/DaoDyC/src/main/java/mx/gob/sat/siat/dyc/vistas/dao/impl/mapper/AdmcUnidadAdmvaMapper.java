package mx.gob.sat.siat.dyc.vistas.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmDomDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.domain.vistas.EnumTipoUnidadAdmvaDyC;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.springframework.jdbc.core.RowMapper;


public class AdmcUnidadAdmvaMapper implements RowMapper<AdmcUnidadAdmvaDTO>
{
    public static final  String SUBFIJO = "_UNIDADADMVA";

    private AdmcUnidadAdmDomMapper mapperAdmcUnidadAdmDom;

    @Override
    public AdmcUnidadAdmvaDTO mapRow(ResultSet rs, int i) throws SQLException {
        AdmcUnidadAdmvaDTO unidadAdmva = new AdmcUnidadAdmvaDTO();

        unidadAdmva.setIdUnidadAdmva(rs.getInt(UtilsDominio.obtenerNombreColumna("IDUNIDADADMVA", SUBFIJO, rs)));
        unidadAdmva.setIdUnidAdmvaTipo(rs.getInt(UtilsDominio.obtenerNombreColumna("IDUNIDADMVATIPO", SUBFIJO, rs)));
        unidadAdmva.setNombre(rs.getString(UtilsDominio.obtenerNombreColumna("NOMBRE", SUBFIJO, rs)));
        unidadAdmva.setNomAbreviado(rs.getString(UtilsDominio.obtenerNombreColumna("NOMABREVIADO", SUBFIJO, rs)));
        unidadAdmva.setIdUnidadmpadre(rs.getInt(UtilsDominio.obtenerNombreColumna("IDUNIDADMPADRE", SUBFIJO, rs)));
        unidadAdmva.setClaveSir(rs.getInt(UtilsDominio.obtenerNombreColumna("CLAVE_SIR", SUBFIJO, rs)));
        unidadAdmva.setClaveAgrs(rs.getString(UtilsDominio.obtenerNombreColumna("CLAVE_AGRS", SUBFIJO, rs)));

        if (unidadAdmva.getIdUnidAdmvaTipo() != null) {
            if (unidadAdmva.getIdUnidAdmvaTipo().intValue() ==
                EnumTipoUnidadAdmvaDyC.LOCAL.getIdTipoUnidadAdmva().intValue()) {
                unidadAdmva.setTipoUnidadAdmva(EnumTipoUnidadAdmvaDyC.LOCAL);
            }

            if (unidadAdmva.getIdUnidAdmvaTipo().intValue() ==
                EnumTipoUnidadAdmvaDyC.GRANDES_CONTRIBUYENTES.getIdTipoUnidadAdmva().intValue()) {
                unidadAdmva.setTipoUnidadAdmva(EnumTipoUnidadAdmvaDyC.GRANDES_CONTRIBUYENTES);
            }

            if (unidadAdmva.getIdUnidAdmvaTipo().intValue() ==
                EnumTipoUnidadAdmvaDyC.DEVOLUCIONES.getIdTipoUnidadAdmva().intValue()) {
                unidadAdmva.setTipoUnidadAdmva(EnumTipoUnidadAdmvaDyC.DEVOLUCIONES);
            }
        }

        if (mapperAdmcUnidadAdmDom != null) {
            unidadAdmva.setAdmcUnidadAdmDomDTO(mapperAdmcUnidadAdmDom.mapRow(rs, i));
        } else {
            try{
                AdmcUnidadAdmDomDTO domicilio = new AdmcUnidadAdmDomDTO();
                domicilio.setEntidadFed(rs.getString("ENTIDADFED"));
                domicilio.setIdUnidadAdmDom(rs.getInt("IDUNIDADADMDOM"));
                unidadAdmva.setAdmcUnidadAdmDomDTO(domicilio);
            }
            catch(java.sql.SQLException ex){
                ex.getMessage();
            }
        }

        return unidadAdmva;
    }

    public AdmcUnidadAdmDomMapper getMapperAdmcUnidadAdmDom() {
        return mapperAdmcUnidadAdmDom;
    }

    public void setMapperAdmcUnidadAdmDom(AdmcUnidadAdmDomMapper mapperAdmcUnidadAdmDom) {
        this.mapperAdmcUnidadAdmDom = mapperAdmcUnidadAdmDom;
    }
}
