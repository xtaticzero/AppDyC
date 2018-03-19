package mx.gob.sat.siat.dyc.dao.util.impl;

import java.util.Date;
import mx.gob.sat.siat.dyc.dao.util.DycpSolPagoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolPagoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository
public class DycpSolPagoDAOImpl implements DycpSolPagoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOGGER = Logger.getLogger(DycpSolPagoDAOImpl.class);


    /**
     * Inserta los datos a la tabla DYCP_SERVICIO, por el caso de uso EnviarRetroalimentacion
     *
     * @param dycpSolPagoDTO
     * @throws SIATException
     */
    @Override
    public void insertar(DycpSolPagoDTO dycpSolPagoDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_SOLPAGO.toString(),
                                   new Object[] { dycpSolPagoDTO.getDyccEstadoPagoDTO().getIdEstadoPago(),
                                                  dycpSolPagoDTO.getDyccFormaPagoDTO().getIdFormaPago(),
                                                  dycpSolPagoDTO.getDyccMotivoRechazoDTO().getIdMotivoRechazo(),
                                                  dycpSolPagoDTO.getFechaAbono(),
                                                  dycpSolPagoDTO.getNumeroTransaccion(),
                                                  dycpSolPagoDTO.getNumControl() 
                                                }
            );
        } catch (Exception dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.INSERTAR_SOLPAGO + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dycpSolPagoDTO));
            throw new SIATException(dae);
        }
    }
    
    /**
     * Busca si existe un registro de un numero de control en particular, esto se hace para que en vez de que trate de
     * insertar un registro con un mismo numero de control 2 veces, se verifique si existe o no para ver si se hace una 
     * insercion por primera vez o se actualize su registro actual en base de datos.
     *
     * @param numeroControl
     * @return
     */
    @Override
    public Integer buscarRegistroExistente(String numeroControl) throws SIATException {
        Integer numeroRegistros = 0;
        try {
            numeroRegistros = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_CUENTABANCO.toString(), new Object[] { numeroControl }, Integer.class);
            
        } catch (Exception dae) {
            LOGGER.error("buscarRegistroExistente(); parametros: numeroControl="+numeroControl+"; causa:"+dae);
            throw new SIATException(dae);
        }
        return numeroRegistros; 
    }
    
    /**
     * Actualiza el registro existente, pasando como parametro el numero de control.
     *
     * @param dycpSolPagoDTO
     */
    @Override
    public void actualizar ( DycpSolPagoDTO dycpSolPagoDTO ) throws SIATException {

        try {

            Date    fechaAbono        = dycpSolPagoDTO.getFechaAbono();
            String  numControl        = dycpSolPagoDTO.getNumControl();

            Integer idFormaPago       = dycpSolPagoDTO.getDyccFormaPagoDTO().getIdFormaPago();
            Integer idEstadoPago      = dycpSolPagoDTO.getDyccEstadoPagoDTO().getIdEstadoPago();
            Integer idMotivoRechazo   = dycpSolPagoDTO.getDyccMotivoRechazoDTO().getIdMotivoRechazo();
            Integer numeroTransaccion = dycpSolPagoDTO.getNumeroTransaccion();

            jdbcTemplateDYC.update(
                SQLOracleDyC.ACTUALIZAR_SOLPAGO.toString(),
                    new Object[] { idEstadoPago, idFormaPago, idMotivoRechazo, fechaAbono, numeroTransaccion, numControl }
            );

        } catch ( Exception dae ){

            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                        SQLOracleDyC.ACTUALIZAR_SOLPAGO + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dycpSolPagoDTO) );

            throw new SIATException(dae);
        }
    }

    @Override
    public DycpSolPagoDTO buscarXNumControl(String numeroControl) {
        String query = "SELECT * FROM DYCP_SOLPAGO WHERE NUMCONTROL = ? ";

        try {
            return (DycpSolPagoDTO) jdbcTemplateDYC.queryForObject(query, new Object[]{numeroControl}, new BeanPropertyRowMapper(DycpSolPagoDTO.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info(e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error(e);
            return null;
        }
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
