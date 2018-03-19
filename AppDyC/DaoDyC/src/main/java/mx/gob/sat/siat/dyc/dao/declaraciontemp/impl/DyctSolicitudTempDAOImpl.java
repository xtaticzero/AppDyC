package mx.gob.sat.siat.dyc.dao.declaraciontemp.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.declaraciontemp.DyctSolicitudTempDAO;
import mx.gob.sat.siat.dyc.dao.declaraciontemp.impl.mapper.DyctSolicitudTempMapper;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctSolicitudTempDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository("dyctSolicitudTempDAO")
public class DyctSolicitudTempDAOImpl implements DyctSolicitudTempDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public DyctSolicitudTempDAOImpl() {
        super();
    }

    @Override
    public List<DyctSolicitudTempDTO> listaSolicitudesTemp() {
        String sql =
            "SELECT ST.CLAVEADM AS ADMINISTRACION, ST.RFCCONTRIBUYENTE AS RFC, DA.URL AS URL, ST.FECHAPRESENTACION AS FECHA\n" +
            "                          FROM DYCT_SOLICITUDTEMP ST, DYCT_ARCHIVOTEMP DA\n" +
            "                          WHERE \n" +
            "                          ST.FOLIOSERVTEMP = DA.FOLIOSERVTEMP AND\n" +
            "                          ST.IDESTADOSOLICITUD = 1 AND\n" +
            "                          ST.FECHAPRESENTACION > TO_DATE(SYSDATE)-4 AND\n" +
            "                          ST.FECHAPRESENTACION < TO_DATE(SYSDATE)-3";
        return jdbcTemplateDYC.query(sql, new DyctSolicitudTempMapper());
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
