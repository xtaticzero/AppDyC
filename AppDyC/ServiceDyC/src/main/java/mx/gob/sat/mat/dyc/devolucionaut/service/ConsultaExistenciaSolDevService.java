/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.devolucionaut.service;

import java.math.BigInteger;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public interface ConsultaExistenciaSolDevService {
    
    String consultarSolCompIRSDevAut(String rfc, BigInteger impuesto, BigInteger concepto,
                                     BigInteger ejercicio, String periodo) throws SIATException;
    
}
