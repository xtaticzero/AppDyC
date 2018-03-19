package mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaCompensacionDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class CadenaCompensacionMapper implements RowMapper<CadenaCompensacionDTO> {
    public CadenaCompensacionMapper() {
        super();
    }

    @Override
    public CadenaCompensacionDTO mapRow(ResultSet rs, int i) throws SQLException {
        CadenaCompensacionDTO objetoCadenaCompensacion = new CadenaCompensacionDTO();
        objetoCadenaCompensacion.setDescripcionTramiteICEPDestino(rs.getString("DESCRIPCIONTRAMITEICEPDESTINO"));
        objetoCadenaCompensacion.setDescripcionConceptoICEPDestino(rs.getString("DESCRIPCIONCONCEPTOICEPDESTINO"));
        objetoCadenaCompensacion.setDescripcionPeriodoICEPDestino(rs.getString("DESCRIPCIONPERIODOICEPDESTINO"));
        objetoCadenaCompensacion.setEjercicioICEPDestino(rs.getString("IDEJERCICIO"));
        objetoCadenaCompensacion.setFechaSolventacion(rs.getString("FECHASOLVENTACION"));
        objetoCadenaCompensacion.setTipoDeRequerimiento(rs.getString("TIPODEREQUERIMIENTO"));
        objetoCadenaCompensacion.setNumeroControl(rs.getString("NUMCONTROL"));
        objetoCadenaCompensacion.setRfc(rs.getString("RFC"));
        objetoCadenaCompensacion.setNombreORazonSocial(rs.getString("NOMBREORAZONSOCIAL"));
        return objetoCadenaCompensacion;
    }
}
