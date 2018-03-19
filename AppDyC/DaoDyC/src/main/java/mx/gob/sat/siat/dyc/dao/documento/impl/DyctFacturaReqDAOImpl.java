/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.documento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyctFacturaReqDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctFacturaReqMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqConfProvDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @date Noviembre 29, 2013
 *
 * */
@Repository(value = "dyctFacturaReqDAO")
public class DyctFacturaReqDAOImpl implements DyctFacturaReqDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctFacturaReqDAOImpl.class.getName());

    public DyctFacturaReqDAOImpl() {
        super();
    }

    @Override
    public boolean existe(String numFactura, String rfcProveedor) throws SIATException {

        Integer countReg = 0;
        boolean existe = false;

        try {

            countReg =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.FACTURAREQ_EXISTE.toString(), new Object[] { rfcProveedor, numFactura },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.FACTURAREQ_EXISTE + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numFactura " + numFactura +
                      " rfcProveedor " + rfcProveedor);
            throw new SIATException("Error al ejecutar DyctFacturaReqDAOImpl.existe", dae);
        }


        if (countReg > 0) {
            existe = Boolean.TRUE;
        }

        return existe;
    }

    public boolean existeFacturasXNumControl(String numControl, String rfcProveedor) throws SIATException {

        Integer countReg = 0;
        boolean existe = Boolean.FALSE;


        try {

            countReg =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.FACTURAREQ_COUNT.toString(), new Object[] { numControl, rfcProveedor },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.FACTURAREQ_COUNT + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl " + numControl +
                      " rfcProveedor " + rfcProveedor);
            throw new SIATException("Error al ejecutar DyctFacturaReqDAOImpl.existeFacturasXNumControl", dae);
        }


        if (countReg > 0) {
            existe = Boolean.TRUE;
        }
        return existe;
    }

    @Override
    public List<DyctFacturaReqDTO> consultaFacturasXNumControl(String numControl,
                                                               String rfcProveedor) throws SIATException {

        try {

            List<DyctFacturaReqDTO> lDyctFacturaDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.FACTURAREQ_CONSULTA.toString(), new Object[] { numControl, rfcProveedor },
                                      new DyctFacturaReqMapper());


            return lDyctFacturaDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.FACTURAREQ_CONSULTA + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl " +
                      numControl + " rfcProveedor " + rfcProveedor);
            throw new SIATException("Error al ejecutar DyctFacturaReqDAOImpl.consultaFacturasXNumControl", dae);
        }

    }


    public void borrar(String numFactura) {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.FACTURAREQ_DELETE.toString(), new Object[] { numFactura });

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.FACTURAREQ_DELETEXNUMFACTURA + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numFactura " +
                      numFactura);
            throw dae;
        }


    }

    @Override
    public List<DyctFacturaReqDTO> selecXReqconf(DyctReqConfProvDTO req) {
        String query = " SELECT * FROM DYCT_FACTURAREQ WHERE NUMCONTROLDOC = ?";
        DyctFacturaReqMapper mapper = new DyctFacturaReqMapper();
        mapper.setReqConfProv(req);
        return jdbcTemplateDYC.query(query, new Object[] { req.getDyctDocumentoDTO().getNumControlDoc() },
                                     new DyctFacturaReqMapper());
    }

    @Override
    public void insertar(DyctFacturaReqDTO factura) throws SIATException {
        String sentInsert =
            "INSERT INTO DYCT_FACTURAREQ(NUMEROFACTURA, FECHAEMISION, CONCEPTO, IMPORTE, IVATRASLADADO, TOTAL, NUMCONTROLDOC) VALUES(?,?,?,?,?,?,?) ";

        try {


            jdbcTemplateDYC.update(sentInsert,
                                   new Object[] { factura.getNumeroFactura(), factura.getFechaEmision(), factura.getConcepto(),
                                                  factura.getImporte(), factura.getIvaTrasladado(), factura.getTotal(),
                                                  factura.getDyctReqConfProvDTO().getDyctDocumentoDTO().getNumControlDoc() });

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      sentInsert + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(factura));
            throw new SIATException("Error al ejecutar DyctFacturaReqDAOImpl.insertar", dae);
        }
    }
}
