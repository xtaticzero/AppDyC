/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.ags.impl;

import mx.gob.sat.siat.dyc.dao.ags.SatAgsEmpleadosMVDAO;
import mx.gob.sat.siat.dyc.dao.ags.impl.mapper.SatAgsEmpleadosMVMapper;
import mx.gob.sat.siat.dyc.domain.ags.SatAgsEmpleadosMVDTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author RRAL
 */
@Repository(value = "satAgsEmpleadosMVDAO")
public class SatAgsEmpleadosMVDAOImpl implements SatAgsEmpleadosMVDAO {

    private static final Logger LOG = Logger.getLogger(SatAgsEmpleadosMVDAOImpl.class);
    private static final String WHERE = " WHERE ";
    private static final String RFC_EMPLEADO = "  RFC = ?", NUM_EMPLEADO = "  NO_EMPLEADO = ? ";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public String getEstatusEmpleado(Object param, boolean isNumerico) {
        StringBuilder query = new StringBuilder(SQLOracleDyC.CONSULTA_ESTADO_EMPLEADO_AGS).append(WHERE);
        query.append(isNumerico ? NUM_EMPLEADO: RFC_EMPLEADO);
        try {
            return jdbcTemplateDYC.queryForObject( query.toString(),
                    new Object[]{param}, String.class);
        } catch (DataAccessException dae) {
            LOG.error("error al consultar el empleado: " + param);
            ManejadorLogException.getTraceError(dae);
        }
        return null;
    }
    
        @Override
       public String getCentroCostoEmpleado(Object param) {
        
           try {
            return jdbcTemplateDYC.queryForObject( SQLOracleDyC.CONSULTA_CENTRO_COSTO_EMPLEADO_AGS,
                    new Object[]{param}, String.class);
        } catch (DataAccessException dae) {
            LOG.error("error al consultar el empleado: " + param);
            ManejadorLogException.getTraceError(dae);
        }
        return null;
    }


    @Override
    public SatAgsEmpleadosMVDTO getEmpleadoAGS(Object param, boolean isNumerico) {
        StringBuilder query = new StringBuilder(SQLOracleDyC.CONSULTA_EMPLEADO_AGS).append(WHERE);
        query.append(isNumerico? NUM_EMPLEADO : RFC_EMPLEADO );
        {
            try {
                return jdbcTemplateDYC.queryForObject(query.toString(),
                        new Object[]{param}, new SatAgsEmpleadosMVMapper());
            } catch (EmptyResultDataAccessException emptyEx) {
                LOG.info("No se encontro ningun empleado con el numero/rfc de empleado ->" + param + "<-");
                return null;
            }
        }

    }

    public String getNombreEmpleado(Integer numEmpleado) {
        try {
            return jdbcTemplateDYC.queryForObject( SQLOracleDyC.CONSULTA_NOMBRE_EMPLEADO.toString(),
                    new Object[]{numEmpleado}, String.class);
        } catch (DataAccessException dae) {
            LOG.error("error al consultar el empleado: " + numEmpleado);
            ManejadorLogException.getTraceError(dae);
        }
        return null;
    }
}
