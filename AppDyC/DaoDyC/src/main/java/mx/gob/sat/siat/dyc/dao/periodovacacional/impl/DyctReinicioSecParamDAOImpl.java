/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.periodovacacional.impl;

import java.util.Date;
import mx.gob.sat.siat.dyc.dao.periodovacacional.DyctReinicioSecParamDAO;
import mx.gob.sat.siat.dyc.dao.periodovacacional.impl.mapper.DyctReinicioSecParamMapper;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DyctReinicioSecParamDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Orlando Tepoz Z.
 */
@Repository("dyctReinicioSecParamDAOImpl")
public class DyctReinicioSecParamDAOImpl implements DyctReinicioSecParamDAO {

    private static final Logger LOG = Logger.getLogger(DyctReinicioSecParamDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Registro almacenado en la tabla DYCT_REINICIOSECPARAM
     *
     * @return registro activo de fecha programada de reinicio de secuencias
     */
    @Override
    public DyctReinicioSecParamDTO buscarFechaReinicioActivo() {

        DyctReinicioSecParamDTO dyctReinicioSecParamDTO = null;

        try {
            LOG.info("SELECT_REINICIOSECPARAM_ACTIVO: " + SQLOracleDyC.SELECT_REINICIOSECPARAM_ACTIVO.toString());
            dyctReinicioSecParamDTO = jdbcTemplateDYC.queryForObject(SQLOracleDyC.SELECT_REINICIOSECPARAM_ACTIVO.toString(),
                    new Object[]{}, new DyctReinicioSecParamMapper());

        } catch (EmptyResultDataAccessException erde) {
            LOG.info("EmptyResultDataAccessException: " + erde);
            dyctReinicioSecParamDTO = null;
            LOG.error("No se encontraron registros asociados al query: "
                    + SQLOracleDyC.SELECT_REINICIOSECPARAM_ACTIVO.toString());
        } catch (DataAccessException dae) {
            LOG.info("DataAccessException ");
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage()
                    + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.SELECT_REINICIOSECPARAM_ACTIVO);

        }
        LOG.info("return dyctReinicioSecParamDTO: " + dyctReinicioSecParamDTO);
        return dyctReinicioSecParamDTO;
    }

    /**
     * Una vez que se han reiniciado las secuencias; se inactiva el registro
     *
     * @param dTO registro de la secuencia programada a eliminar (inactivar)
     */
    @Override
    public void inactivarFechaReinicioSecuencia(DyctReinicioSecParamDTO dTO) {

        LOG.info("inactivarFechaReinicioSecuencia getIdProgreInicio: " + dTO.getIdProgreInicio());
        try {
            LOG.info("inactivarFechaReinicioSecuencia INACTIVAR_REINICIOSECPARAM: " + SQLOracleDyC.INACTIVAR_REINICIOSECPARAM.toString());
            jdbcTemplateDYC.update(SQLOracleDyC.INACTIVAR_REINICIOSECPARAM.toString(),
                    dTO.getIdProgreInicio());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage()
                    + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.INACTIVAR_REINICIOSECPARAM);
        }
    }

    /**
     *
     * @param dTO
     * @return
     */
    @Override
    public DyctReinicioSecParamDTO insertarNuevaFechaReinicioSecuencia(DyctReinicioSecParamDTO dTO) {

        LOG.info("insertarNuevaFechaReinicioSecuencia: " + dTO);
        DyctReinicioSecParamDTO drspdto = null;
        try {

            LOG.info("insertarNuevaFechaReinicioSecuencia: " + SQLOracleDyC.INSERT_REINICIOSECPARAM.toString());
            jdbcTemplateDYC.update(SQLOracleDyC.INSERT_REINICIOSECPARAM.toString(),
                    new Object[]{dTO.getFechaProgramacion()});
            drspdto = new DyctReinicioSecParamDTO();
            drspdto.setFechaProgramacion(dTO.getFechaProgramacion());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage()
                    + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.INSERT_REINICIOSECPARAM);
        }
        return drspdto;
    }

    /**
     *
     * @param dTO egistro de fecha de reinicioF programado.
     * @return TRUE si hay que reiniciar las secuencias; FALSE en caso contrario
     */
    public boolean validarReinicio(DyctReinicioSecParamDTO dTO) {

        Date fecha = new Date();

        if (dTO == null) {
            LOG.info("fechaReinicioActivo: null");
            return false;
        } else {
            Date fechaProgramacion = dTO.getFechaProgramacion();
            if (fechaProgramacion.before(fecha) || fechaProgramacion.equals(fecha)) {
                LOG.info("SE REINICIAN SECUENCIAS  A LAS : " + fecha + " PROGRAMADAS EN LA FECHA: " + fechaProgramacion);
                LOG.info("fechaProgramacion.after(fecha))");
                return true;
            } else {
                LOG.info("NO SE REINICIAN SECUENCIAS ; PROGRAMADO PARA LA FECHA: " + fechaProgramacion);
                return false;
            }
        }
    }

    /**
     * Se reinician las siguientes secuencias: DYCQ_NUMCTRLDEVIVA,
     * DYCQ_NUMCTRLDEVISR, DYCQ_NUMCTRLCOM, DYCQ_NUMCONTROL, DYCQ_FOLIOAVISOCOMP
     *
     * @param dTO registro de fecha de reinicio de secuencias programado.
     * @return true si se han reiniciado las secuencias; false en caso contrario
     */
    public boolean seReinicianSecuencias(DyctReinicioSecParamDTO dTO) {

        boolean reinicioHecho = false;

        try {
            boolean validarReinicio = validarReinicio(dTO);
            if (validarReinicio) {
                LOG.info("se ejecuta --> REINICIO_SECUENCIAS_SCRIPT: " + SQLOracleDyC.REINICIO_SECUENCIAS_SCRIPT.toString());
                jdbcTemplateDYC.execute(SQLOracleDyC.REINICIO_SECUENCIAS_SCRIPT.toString());
                LOG.info("SE HAN REINICIADO LAS SECUENCIAS !!!!!! ");

                reinicioHecho = true;
            } else {
                reinicioHecho = false;
            }
        } catch (DataAccessException dae) {

            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage()
                    + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.REINICIO_SECUENCIAS_SCRIPT);
            reinicioHecho = false;
        }

        return reinicioHecho;
    }
}
