/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.seguimiento.impl;

import mx.gob.sat.siat.dyc.dao.seguimiento.DycaReasignacionDAO;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DycaReasignacionMapper;
import mx.gob.sat.siat.dyc.domain.ReasignacionDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;

import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
@Repository(value = "dycaReasignacionDAO")
public class DycaReasignacionDAOImpl implements DycaReasignacionDAO {
    
    private static final Logger LOG = Logger.getLogger(DycaReasignacionDAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final String INSERTAR_REASIGNACION="INSERT INTO DYCA_REASIGNACIONEMP(IDREASIGNACION,NUMCONTROL,RESPONSABLEDEASIGNAR,EMPLEADOASIGNADO,FECHAREASIGNACION,ORIGEN) VALUES(?,?,?,?,SYSDATE,?)";
    public static final StringBuilder CONSULTAR_SECUENCIA_REASIGNANCION
            = new StringBuilder("SELECT DYCQ_REASIGNACIONEMP.NEXTVAL FROM DUAL");
     public static final StringBuilder CONSULTAR_ULTIMA_REASIGNACION= new StringBuilder("SELECT * FROM DYCA_REASIGNACIONEMP")
             .append(" WHERE FECHAREASIGNACION  = (SELECT MAX(FECHAREASIGNACION) FROM DYCA_REASIGNACIONEMP WHERE NUMCONTROL=?)")
             .append(" AND NUMCONTROL=? AND  rownum=1");
     public static final StringBuilder CONSULTAR_NOMBRE_RESPONSABLE= new StringBuilder( "SELECT NOMBRE_COMPLETO from SIAT_DYC.SAT_AGS_EMPLEADOS_MV where no_empleado=?");        
    public static final String ACTUALIZA_FECHA_FIN="UPDATE DYCA_REASIGNACIONEMP SET FECHAFIN=SYSDATE WHERE IDREASIGNACION=?";
     
     
    @Override
    public void insertarReasignacion(ReasignacionDTO reasignacion) throws DAOException {
    try {
            Integer secuencia;
            secuencia=obtenerIdSecuenciaReasignacion();
            LOG.debug("secuencia idReasignacion ->" + secuencia);
            reasignacion.setIdReasignacion(secuencia);
    
            jdbcTemplateDYC.update(INSERTAR_REASIGNACION,
                                   new Object[] { reasignacion.getIdReasignacion(),
                                                  reasignacion.getNumcontrol(),
                                                  reasignacion.getEmpleadoResponsable(),
                                                  reasignacion.getEmpleadoAsignado(),
                                                  reasignacion.getOrigen()});
        } catch (Exception dae) {
            LOG.error("Error al guardar "+dae.getCause()+"\n"+ExtractorUtil.ejecutar(reasignacion));
            throw new DAOException(new Object[] {reasignacion}, "Error con el query: "+INSERTAR_REASIGNACION, dae);
        }    
    
    }

    @Override
    public int obtenerIdSecuenciaReasignacion() throws DAOException {
     try {
            return jdbcTemplateDYC.queryForObject(CONSULTAR_SECUENCIA_REASIGNANCION.toString(), Integer.class);
        } catch (DataAccessException dae) {
            LOG.error("Error al ejecutar en bd"+dae.getCause()+"\n");
            throw new DAOException(null, "Error con el query: "+CONSULTAR_SECUENCIA_REASIGNANCION.toString(), dae);
        }
    }

    @Override
    public ReasignacionDTO obtenerUltimaReasignacion(String numeroControl) {
           ReasignacionDTO reasignacion = null;
           
           try{
              reasignacion=jdbcTemplateDYC.queryForObject(CONSULTAR_ULTIMA_REASIGNACION.toString(),new Object[]{numeroControl,numeroControl},new DycaReasignacionMapper()); 
           } catch (EmptyResultDataAccessException ex) { 
              reasignacion = null;
           } catch(DataAccessException e) {
               LOG.error("Error al ejecutar la consulta ", e);
               reasignacion=null;
           }
        
        return reasignacion;
    }

    @Override
    public ReasignacionDTO obtenerReasignacion(String numeroControl) {
        LOG.info("Consultando si el tramite: "+numeroControl+" tiene reasignacion DAO!!!!!!!!!!!!!!!!!!!!!!");
        ReasignacionDTO reasignacion=obtenerUltimaReasignacion(numeroControl);
        if(null!=reasignacion) {
            String nombre=obtenerNombreEmplado(reasignacion.getEmpleadoResponsable());
            reasignacion.setNombreResponsable((nombre!=null)?nombre:"");  
        }      
        
        return reasignacion;
    }
    
    private String obtenerNombreEmplado(Integer numEmpleado){        
       try {
            return jdbcTemplateDYC.queryForObject(CONSULTAR_NOMBRE_RESPONSABLE.toString(),
                    new Object[]{numEmpleado}, String.class);
        } catch (DataAccessException dae) {
            LOG.error("error al consultar el nombre del empleado empleado: " + numEmpleado,dae);            
        }
        return null;
    } 
    
  public void actualizarFechaFin(ReasignacionDTO reasignacion){
      try{
               jdbcTemplateDYC.update(ACTUALIZA_FECHA_FIN,new Object[]{reasignacion.getIdReasignacion()});
      }catch(Exception e){
          LOG.error("Error al actulizar la fecha fin de reasignacion de tramite", e);
      }
  }
}
