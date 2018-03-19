package mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.avisocomp.vo.DatosDestinoAcuseVO;

import org.springframework.jdbc.core.RowMapper;


public class AvisoDestinoAcuseMapper implements RowMapper<DatosDestinoAcuseVO> {
    public AvisoDestinoAcuseMapper() {
        super();
    }

    @Override
    public DatosDestinoAcuseVO mapRow(ResultSet rs, int i) throws SQLException {
        DatosDestinoAcuseVO datosDestinoAcuse = new DatosDestinoAcuseVO();

        datosDestinoAcuse.setFechaInicioTramite(rs.getString("FECHAINICIOTRAMITE"));
        datosDestinoAcuse.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
        datosDestinoAcuse.setFolioAviso(rs.getString("FOLIOAVISO"));
        datosDestinoAcuse.setNombreAdm(rs.getString("NOMBRE"));
        datosDestinoAcuse.setFechaPresentacion(rs.getString("FECHAPRESENTACION"));
        datosDestinoAcuse.setTipoAviso(rs.getString("TIPOAVISO"));
        datosDestinoAcuse.setConcepto(rs.getString("CONCEPTO"));
        datosDestinoAcuse.setEjercicio(rs.getString("IDEJERCICIO"));
        datosDestinoAcuse.setNumOperacionDec(rs.getString("NUMOPERACIONDEC"));
        datosDestinoAcuse.setFechaPresentaDec(rs.getDate("FECHAPRESENTADEC"));
        datosDestinoAcuse.setPeriodo(rs.getString("PERIODO"));
        datosDestinoAcuse.setImporteCompensado(rs.getDouble("IMPORTECOMPENSADO"));

        if (rs.getString("FOLIOAVISONORMAL") != null) {
            datosDestinoAcuse.setFolioAvisoNormal(rs.getString("FOLIOAVISONORMAL"));
        } else {
            datosDestinoAcuse.setFolioAvisoNormal(" ");
        }

        datosDestinoAcuse.setTipoPersona(rs.getString("TIPOPERSONA"));
        if (datosDestinoAcuse.getTipoPersona().equals("F")) {
            datosDestinoAcuse.setPersonaFisica(rs.getString("NOMBREPERSONA") + " " + rs.getString("APPATERNO") + " " +
                                               rs.getString("APMATERNO"));
        } else {
            datosDestinoAcuse.setPersonaMoral(rs.getString("RAZONSOCIAL") + " " + rs.getString("TIPOSOCIEDAD"));
        }
        
        datosDestinoAcuse.setCadenaOriginal(rs.getString("CADENAORIGINAL"));
        datosDestinoAcuse.setSelloDigital(rs.getString("SELLODIGITAL"));

        return datosDestinoAcuse;
    }
}
