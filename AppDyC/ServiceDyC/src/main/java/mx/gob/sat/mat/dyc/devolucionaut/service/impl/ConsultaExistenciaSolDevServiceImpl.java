/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.devolucionaut.service.impl;

import java.math.BigInteger;
import java.util.List;
import mx.gob.sat.mat.dyc.devolucionaut.service.ConsultaExistenciaSolDevService;
import mx.gob.sat.siat.dyc.dao.devolucionaut.ConsultaExistenciaSolDevDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
@Service(value = "consultaExistenciaSolDevService")
public class ConsultaExistenciaSolDevServiceImpl implements ConsultaExistenciaSolDevService {
    
    private static final Logger LOG = Logger.getLogger(ConsultaExistenciaSolDevServiceImpl.class);
    private static final String ERROR_EN_CONEXION = "Error en la conexion";

    @Autowired
    private ConsultaExistenciaSolDevDAO consultaExistenciaSolDevDAO;

    @Override
    public String consultarSolCompIRSDevAut(String rfc, BigInteger impuesto, BigInteger concepto,
                                            BigInteger ejercicio, String periodo) throws SIATException {
        try {
            List<String> resultadoConsulta = consultaExistenciaSolDevDAO.consultarSolCompIRSDevAut(
                                                rfc, impuesto, concepto, ejercicio, periodo);
            
            if(resultadoConsulta.size() > 0){
                return resultadoConsulta.get(0);
            } else {
                return null;
            }
            
        } catch (DAOException e) {
            LOG.error("AutomaticasServiceImpl.consultarSolCompIRSDevAut ::: ", e);
            throw new SIATException(ERROR_EN_CONEXION, e);
        }
    }
    
}
