package mx.gob.sat.mat.dyc.herramientas.analizador.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.migradordyc.dto.VPagoDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Huetzin Cruz Lozano
 */
public class VPagosMapper implements RowMapper<VPagoDTO>
{
    @Override
    public VPagoDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        VPagoDTO pago = new VPagoDTO();
        pago.setRfceeog1(rs.getString("rfceeog1").trim());
        pago.setImpidee1(rs.getInt("impidee1"));
        pago.setIapidne1(rs.getInt("iapidne1"));
        pago.setImpfdee1(rs.getInt("impfdee1"));
        pago.setIapfdne1(rs.getInt("iapfdne1"));
        pago.setFperceh1(rs.getDate("fperceh1"));
        pago.setTdiepco1(rs.getString("tdiepco1"));
        pago.setLdleacv1(rs.getString("ldleacv1"));
        pago.setCalldoa1(rs.getInt("calldoa1"));
        pago.setTfiopro1(rs.getString("tfiopro1"));
        pago.setIatfdni1(rs.getInt("iatfdni1"));
        pago.setIacdnae1(rs.getLong("iacdnae1"));
        pago.setCan_pagar(rs.getBigDecimal("can_pagar"));
        pago.setN_numero_operacion(rs.getLong("n_numero_operacion"));
        pago.setImporte_afavor(rs.getBigDecimal("importe_afavor"));
        pago.setImporte_acargo(rs.getBigDecimal("importe_acargo"));
        pago.setC_ent_receptora(rs.getLong("c_ent_receptora"));
        pago.setC_llave_det(rs.getLong("c_llave_det"));
        pago.setN_materia(rs.getInt("n_materia"));
        pago.setN_medio_recepcion(rs.getInt("n_medio_recepcion"));
        pago.setF_fecharegistro(rs.getDate("f_fecharegistro"));
        pago.setCanti_favor(rs.getBigDecimal("canti_favor"));
        return pago;
    }
}