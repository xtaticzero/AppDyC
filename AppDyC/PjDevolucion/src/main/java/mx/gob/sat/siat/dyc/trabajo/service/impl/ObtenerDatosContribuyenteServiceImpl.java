package mx.gob.sat.siat.dyc.trabajo.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.trabajo.vo.DatosContribuyenteVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


@Service(value = "serviceObtenerDatosContte")
public class ObtenerDatosContribuyenteServiceImpl {
    private static final Logger LOG = Logger.getLogger(ObtenerDatosContribuyenteServiceImpl.class);

    @Autowired
    @Qualifier("jdbcTemplateDYC")
    private JdbcTemplate jdbcTemplateDYC;

    public DatosContribuyenteVO execute(String numControl) {
        String query =
            "SELECT  EXTRACTVALUE(DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') AS NOMBRE,\n" +
            "        EXTRACTVALUE(DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') AS APPATERNO,\n" +
            "        EXTRACTVALUE(DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') AS APMATERNO,\n" +
            "        EXTRACTVALUE(DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') AS RAZONSOCIAL\n" +
            " FROM DYCT_CONTRIBUYENTE WHERE NUMCONTROL = ? ";

        try {
            DatosContribuyenteVO datosContte =
                jdbcTemplateDYC.queryForObject(query, new Object[] { numControl }, new RowMapper<DatosContribuyenteVO>() {
                    @Override
                    public DatosContribuyenteVO mapRow(ResultSet rs, int i) throws SQLException {
                        DatosContribuyenteVO datosContte = new DatosContribuyenteVO();

                        datosContte.setEmpresa(rs.getString("RAZONSOCIAL"));
                        String nombre = rs.getString("NOMBRE");
                        if (nombre != null) {
                            datosContte.setNombre(nombre + " " + rs.getString("APPATERNO") + " " +
                                                  rs.getString("APMATERNO"));
                        }

                        return datosContte;
                    }
                });

            return datosContte;
        } catch (EmptyResultDataAccessException erdae) {
            LOG.info("NO se encontró datos del contribuyente para el servicio ->" + numControl + "<-");
            return null;
        }
    }
}
