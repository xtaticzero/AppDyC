package mx.gob.sat.siat.dyc.dao.documento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyctFacturaCompDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.FacturaCompMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctFacturaCompDAOImpl implements DyctFacturaCompDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void insertar(DyctFacturaReqDTO factura) {
        String sentSQLInsert =
            " INSERT INTO DYCT_FACTURACOMP (NUMEROFACTURA, RFCPROVEEDOR, FECHAEMISION, CONCEPTO, IMPORTE, IVATRASLADADO, TOTAL, IDDOCUMENTOCOMP) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
        Object[] parametros = new Object[ConstantesDyCNumerico.VALOR_8];
        parametros[ConstantesDyCNumerico.VALOR_0] = factura.getNumeroFactura();
        parametros[ConstantesDyCNumerico.VALOR_1] = factura.getDyctReqConfProvDTO().getRfcProveedor();
        parametros[ConstantesDyCNumerico.VALOR_2] = factura.getFechaEmision();
        parametros[ConstantesDyCNumerico.VALOR_3] = factura.getConcepto();
        parametros[ConstantesDyCNumerico.VALOR_4] = factura.getImporte();
        parametros[ConstantesDyCNumerico.VALOR_5] = factura.getIvaTrasladado();
        parametros[ConstantesDyCNumerico.VALOR_6] = factura.getTotal();
        /**parametros[ConstantesDyCNumerico.VALOR_7] = factura.getDocumentoComp().getIdDocumentoComp();*/
        this.jdbcTemplateDYC.update(sentSQLInsert, parametros);
    }

    @Override
    public List<DyctFacturaReqDTO> selecXDocumentoRfcprov(DyctDocumentoDTO documento, String rfcProveedor) {
        return (List<DyctFacturaReqDTO>)this.jdbcTemplateDYC.query("SELECT * FROM DYCT_FACTURACOMP WHERE IDDOCUMENTOCOMP = ? AND RFCPROVEEDOR = ?",
                                                                   new Object[] { documento.getNumControlDoc(),
                                                                                  rfcProveedor },
                                                                   new FacturaCompMapper());
    }


}
