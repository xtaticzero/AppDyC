/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.pagos.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.pagos.ConsultarPagosElectronicosDAO;
import mx.gob.sat.siat.dyc.servicio.dao.pagos.impl.mapper.ConsultarPagosElectronicosAnioMapper;
import mx.gob.sat.siat.dyc.servicio.dao.pagos.impl.mapper.InformacionDePagoInf01Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.pagos.impl.mapper.InformacionDePagoInf02Mapper;
import mx.gob.sat.siat.dyc.servicio.dao.pagos.impl.mapper.InformacionDePagoORAMapper;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.ConsultarPagosElectronicosAnioDTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf01DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf02DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoORADTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.SQLOracleSIAT;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Israel Chavez
 * @since 02/07/2013
 *
 */
@Repository(value = "consultarPagosElectronicosDAO")
@Transactional
public class ConsultarPagosElectronicosDAOImpl implements ConsultarPagosElectronicosDAO, SQLInformixSIAT,
                                                          SQLOracleSIAT {

    private Logger logger = Logger.getLogger(ConsultarPagosElectronicosDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateSIAT;

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    @Override
    public List<ConsultarPagosElectronicosAnioDTO> consultaRegistrosPorAnio(ConsultarPagosElectronicosAnioDTO consultarPagosElectronicosAnioDTO) {
        List<ConsultarPagosElectronicosAnioDTO> pagosAnnoArray = new ArrayList<ConsultarPagosElectronicosAnioDTO>();
        try {
            pagosAnnoArray =
                    jdbcTemplateInformix.query(CONSULTA_PAGOS_ANUALES.replace("{0}", String.valueOf(consultarPagosElectronicosAnioDTO.getNumeje())),
                                               new Object[] { consultarPagosElectronicosAnioDTO.getTxtrfc(),
                                                              consultarPagosElectronicosAnioDTO.getTxtrfc2(),
                                                              consultarPagosElectronicosAnioDTO.getTxtrfc3(),
                                                              consultarPagosElectronicosAnioDTO.getNumper(),
                                                              consultarPagosElectronicosAnioDTO.getNumeje(),
                                                              consultarPagosElectronicosAnioDTO.getCStiCcloanv1() },
                                               new ConsultarPagosElectronicosAnioMapper());
        } catch (DataAccessException dae) {
            logger.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTA_PAGOS_ANUALES + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarPagosElectronicosAnioDTO));
        }
        return pagosAnnoArray;
    }

    @Override
    public List<InformacionDePagoInf01DTO> informacionDePagoInf01(InformacionDePagoInf01DTO consultarInformacionDePagoInf01DTO) {
        List<InformacionDePagoInf01DTO> pagosInf01Array = new ArrayList<InformacionDePagoInf01DTO>();
        try {
            pagosInf01Array =
                    jdbcTemplateInformix.query(CONSLUTAR_PAGOS_ELECTRONICOSDAO_INFORMACION_PAGO_INF01, new Object[] { consultarInformacionDePagoInf01DTO.getCStiCcloanv1() },
                                               new InformacionDePagoInf01Mapper());

        } catch (DataAccessException e) {
            logger.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSLUTAR_PAGOS_ELECTRONICOSDAO_INFORMACION_PAGO_INF01 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarInformacionDePagoInf01DTO));
        }
        return pagosInf01Array;
    }

    @Override
    public List<InformacionDePagoInf02DTO> informacionDePagoInf02(InformacionDePagoInf02DTO consultarInformacionDePagoInf02DTO) {
        List<InformacionDePagoInf02DTO> pagosInf02Array = new ArrayList<InformacionDePagoInf02DTO>();
        try {
            pagosInf02Array =
                    jdbcTemplateInformix.query(CONSLUTAR_PAGOS_ELECTRONICOSDAO_INFORMACION_PAGO_INF02, new Object[] { consultarInformacionDePagoInf02DTO.getCDecCplearv1() },
                                               new InformacionDePagoInf02Mapper());
        } catch (DataAccessException e) {
            logger.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSLUTAR_PAGOS_ELECTRONICOSDAO_INFORMACION_PAGO_INF02 + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarInformacionDePagoInf02DTO));
        }
        return pagosInf02Array;
    }

    @Override
    public List<InformacionDePagoORADTO> informacionDePagoOra(InformacionDePagoORADTO consultarInformacionDePagoORADTO) {
        List<InformacionDePagoORADTO> pagosOraArray = new ArrayList<InformacionDePagoORADTO>();
        try {
            pagosOraArray =
                    jdbcTemplateSIAT.query(CONSULTAR_PAGOS_ELECTRONICOSDAO_INFORMACION_PAGO, new Object[] { consultarInformacionDePagoORADTO.getCIdeRfceeog1(),
                                                                                                            consultarInformacionDePagoORADTO.getCIdeRfceeog2(),
                                                                                                            consultarInformacionDePagoORADTO.getCIdeRfceeog3(),
                                                                                                            consultarInformacionDePagoORADTO.getCDecCrleanv1(),
                                                                                                            consultarInformacionDePagoORADTO.getCDecCplearv1(),
                                                                                                            consultarInformacionDePagoORADTO.getNDecEjercic1(), },
                                           new InformacionDePagoORAMapper());
        } catch (DataAccessException e) {
            logger.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesErrorTextos.TEXTO_2_ERROR_DAO +
                    CONSULTAR_PAGOS_ELECTRONICOSDAO_INFORMACION_PAGO + ConstantesErrorTextos.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(consultarInformacionDePagoORADTO));
        }
        return pagosOraArray;
    }

}
