/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.devolucionaut;

import java.math.BigInteger;
import java.util.List;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public interface ConsultaExistenciaSolDevDAO {
    
        List<String> consultarSolCompIRSDevAut(String rfc, BigInteger impuesto, BigInteger concepto,
                                               BigInteger ejercicio, String periodo) throws DAOException;

}
