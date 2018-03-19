package mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.avisocomp.vo.DataUploadVO;

import org.springframework.jdbc.core.RowMapper;


public class DataUploadMapper implements RowMapper<DataUploadVO> {
    public DataUploadMapper() {
        super();
    }


    @Override
    public DataUploadVO mapRow(ResultSet rs, int i) throws SQLException {


        DataUploadVO dataUpload = new DataUploadVO();
        dataUpload.setFolioAvisoNormal(rs.getString("folioAvisoNormal"));
        dataUpload.setIdTipoDeclaracion(rs.getInt("idTipoDeclaracion"));
        dataUpload.setIdConcepto(rs.getInt("idConcepto"));
        dataUpload.setIdPeriodo(rs.getInt("idPeriodo"));
        dataUpload.setIdEjercicio(rs.getInt("idEjercicio"));
        dataUpload.setFechaPresentacionDec(rs.getDate("fechaPresentaDec"));
        dataUpload.setNumOperacionDec(rs.getString("numOperacionDec"));
        dataUpload.setImporteComDeclarado(rs.getBigDecimal("impCompDecla"));
        dataUpload.setIdTipoAviso(rs.getInt("idTipoAviso"));
        dataUpload.setIdTipoPeriodo(rs.getString("idTipoPeriodo"));
        /*********Tabla ori*****/
        dataUpload.setSafIdPeriodo(rs.getInt("safIdPeriodo"));
        dataUpload.setSafIdEjercicio(rs.getInt("safIdEjercicio"));
        dataUpload.setSaldoAplicar(rs.getBigDecimal("saldoAplicar"));
        dataUpload.setRemanenteHistorico(rs.getBigDecimal("remanenteHistorico"));
        dataUpload.setFechaCausacion(rs.getDate("fechaCausacion"));
        dataUpload.setRemanenteAct(rs.getBigDecimal("remanenteAct"));
        dataUpload.setIdOrigenSaldo(rs.getInt("idOrigenSaldo"));
        dataUpload.setIdTipoTramite(rs.getInt("idTipoTramite"));
        dataUpload.setImporteSolicitado(rs.getBigDecimal("importeSolicitado"));
        dataUpload.setImpActualizado(rs.getBigDecimal("impActualizado"));
        dataUpload.setImpRemanente(rs.getBigDecimal("impRemanente"));
        dataUpload.setDiotNumOperacion(rs.getString("diotNumOperacion"));
        dataUpload.setDiotFechaPresenta(rs.getDate("diotFechaPresenta"));
        dataUpload.setSafNumOperacionDec(rs.getInt("safNumOperacionDec"));
        dataUpload.setTipoSaldo(rs.getInt("tipoSaldo"));
        dataUpload.setEspSubOrigen(rs.getString("espSubOrigen"));
        dataUpload.setPresentoDiot(rs.getInt("presentoDiot"));
        dataUpload.setNumControlRem(rs.getInt("numControlRem"));
        dataUpload.setEsRemanente(rs.getInt("esRemanente"));
        /**  Upload   from  dyct_declaratemp **/

        dataUpload.setFechaPresentacion(rs.getDate("fechaPresentacion"));
        dataUpload.setNumOperacion(rs.getString("numOperacion"));
        dataUpload.setSaldoAFavor(rs.getBigDecimal("saldoAFavor"));
        dataUpload.setNormalFechaPres(rs.getDate("normalFechaPres"));
        dataUpload.setNormalNumOperacion(rs.getLong("normalNumOperacion"));
        dataUpload.setNormalImporteSaf(rs.getBigDecimal("normalImporteSaf"));
        dataUpload.setIdTipoDeclaracionTemp(rs.getInt("idTipoDeclaracionTemp"));
        dataUpload.setDevueltoCompensado(rs.getBigDecimal("devueltoCompensado"));
        dataUpload.setAcreditamiento(rs.getBigDecimal("acreditamiento"));
        /**It is  the Key  for  the tables */
        dataUpload.setFolioServTemp(rs.getInt("folioServTemp"));


        return dataUpload;
    }
}
