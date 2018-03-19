package mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;

import org.springframework.jdbc.core.RowMapper;


public class InfoSaldoAFavorMapper implements RowMapper<InformacionSaldoFavorTO> {
    public InfoSaldoAFavorMapper() {
        super();
    }

    @Override
    public InformacionSaldoFavorTO mapRow(ResultSet rs, int i) throws SQLException {
        InformacionSaldoFavorTO infoSaldoFavor = new InformacionSaldoFavorTO();
        infoSaldoFavor.setFechaPresentacion(rs.getDate("FECHPRESAENTACION"));
        infoSaldoFavor.setFechaCaucion(rs.getDate("FECHCAUSACION"));
        infoSaldoFavor.setNumeroOperacion(rs.getString("NUMOPERACION") != null ? rs.getString("NUMOPERACION") : null);
        infoSaldoFavor.setNumeroDocumento(rs.getString("NUMDOC"));
        infoSaldoFavor.setImporteSaldoFavor(rs.getString("SALDOFAVOR") != null ? rs.getBigDecimal("SALDOFAVOR") :
                                            null);
        infoSaldoFavor.setImporteAcreditramientoEfectuado(rs.getString("DEVUELTOCOMPENSADO") != null ?
                                                          rs.getBigDecimal("DEVUELTOCOMPENSADO") : null);
        infoSaldoFavor.setImporteEfectuados(rs.getString("ACREDITAMIENTO") != null ?
                                            rs.getBigDecimal("ACREDITAMIENTO") : null);
        infoSaldoFavor.setImporteSolicitadoDevolucion(rs.getBigDecimal("IMPORTE"));
        infoSaldoFavor.setTipoDeclaracion(rs.getString("TIPODECLARACION"));
        infoSaldoFavor.setNormalFechaPresentacion(rs.getDate("NORMALFECH"));
        infoSaldoFavor.setNormalNumOperacion(rs.getString("NUMNORMAL"));
        infoSaldoFavor.setNormalSaldoFavor(rs.getString("NORMALIMPORTE") != null ? rs.getBigDecimal("NORMALIMPORTE") :
                                           null);
        infoSaldoFavor.setEtiquetaSaldo(rs.getString("ETIQUETASALDO"));
        return infoSaldoFavor;
    }
}
