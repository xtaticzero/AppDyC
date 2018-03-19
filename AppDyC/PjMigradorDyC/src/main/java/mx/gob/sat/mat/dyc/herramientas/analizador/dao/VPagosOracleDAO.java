package mx.gob.sat.mat.dyc.herramientas.analizador.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.siat.migradordyc.dto.VPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Huetzin Cruz Lozano
 */
@Repository
public class VPagosOracleDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private final static String SENT_INSERT = " INSERT INTO VPAGOS (RFCEEOG1, IMPIDEE1, IAPIDNE1, IMPFDEE1, IAPFDNE1, FPERCEH1, TDIEPCO1," +
                                            " LDLEACV1, CALLDOA1, TFIOPRO1, IATFDNI1, IACDNAE1, CAN_PAGAR, N_NUMERO_OPERACION, " + 
                                            " IMPORTE_AFAVOR, IMPORTE_ACARGO, C_ENT_RECEPTORA, C_LLAVE_DET, N_MATERIA, " +
                                            " N_MEDIO_RECEPCION, F_FECHAREGISTRO, CANTI_FAVOR) " + 
                                            " VALUES(?, ?, ?, ?, ?," + 
                                                    "?, ?, ?, ?, ?," + 
                                                    "?, ?, ?, ?, ?," + 
                                                    "?, ?, ?, ?, ?,"+ 
                                                    "?, ? ) ";

    public Integer insertar(VPagoDTO pago)
    {
        
        
        Object[] params = new Object[]{ pago.getRfceeog1(),
                                        pago.getImpidee1(),
                                        pago.getIapidne1(),
                                        pago.getImpfdee1(),
                                        pago.getIapfdne1(),
                                        pago.getFperceh1(),
                                        pago.getTdiepco1(),
                                        pago.getLdleacv1(),
                                        pago.getCalldoa1(),
                                        pago.getTfiopro1(),
                                        pago.getIatfdni1(),
                                        pago.getIacdnae1(),
                                        pago.getCan_pagar(),
                                        pago.getN_numero_operacion(),
                                        pago.getImporte_afavor(),
                                        pago.getImporte_acargo(),
                                        pago.getC_ent_receptora(),
                                        pago.getC_llave_det(),
                                        pago.getN_materia(),
                                        pago.getN_medio_recepcion(),
                                        pago.getF_fecharegistro(),
                                        pago.getCanti_favor()};

        return jdbcTemplateDYC.update(SENT_INSERT, params);
    }
    
    public void insertarLote(final List<VPagoDTO> pagos){
        
        jdbcTemplateDYC.batchUpdate(SENT_INSERT, new BatchPreparedStatementSetter() {
			
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                    VPagoDTO pago = pagos.get(i);
                    ps.setString(1, pago.getRfceeog1());
                    ps.setInt(2, pago.getImpidee1());
                    ps.setInt(3, pago.getIapidne1());
                    ps.setInt(4, pago.getImpfdee1());
                    ps.setInt(5, pago.getIapfdne1());
                    ps.setDate(6, new java.sql.Date(pago.getFperceh1().getTime()));
                    ps.setString(7, pago.getTdiepco1());
                    ps.setString(8, pago.getLdleacv1());
                    ps.setInt(9, pago.getCalldoa1());
                    ps.setString(10, pago.getTfiopro1());
                    ps.setInt(11, pago.getIatfdni1());
                    ps.setLong(12, pago.getIacdnae1());
                    ps.setBigDecimal(13, pago.getCan_pagar());
                    ps.setLong(14, pago.getN_numero_operacion());
                    ps.setBigDecimal(15, pago.getImporte_afavor());
                    ps.setBigDecimal(16, pago.getImporte_acargo());
                    ps.setLong(17, pago.getC_ent_receptora());
                    ps.setLong(18, pago.getC_llave_det());
                    ps.setInt(19, pago.getN_materia());
                    ps.setInt(20, pago.getN_medio_recepcion());
                    ps.setDate(21, new java.sql.Date(pago.getF_fecharegistro().getTime()));
                    ps.setBigDecimal(22, pago.getCanti_favor());
            }
			
            @Override
            public int getBatchSize() {
                    return pagos.size();
            }
        });
    }
}
