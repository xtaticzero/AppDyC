/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.seguimiento;

import mx.gob.sat.siat.dyc.domain.ReasignacionDTO;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public interface DycaReasignacionDAO {
  /**
   * Metodo para insertarla fecha de reasignacion de un tramite a otro empledo
   * @throws DAOException 
   */
   void insertarReasignacion(ReasignacionDTO reasignacion)throws DAOException;
   /**
    * Secuencia para insertar la reasignacion del tramte en la tabal DycaReasignacion
    * @return id de secuencia
    * @throws DAOException 
    */
   int obtenerIdSecuenciaReasignacion()throws DAOException; 
   
   ReasignacionDTO obtenerUltimaReasignacion(String numeroControl);
   
   ReasignacionDTO obtenerReasignacion(String numeroControl);
}
