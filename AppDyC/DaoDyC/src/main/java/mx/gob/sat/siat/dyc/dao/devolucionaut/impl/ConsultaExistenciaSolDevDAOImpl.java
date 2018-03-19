/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.devolucionaut.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.devolucionaut.ConsultaExistenciaSolDevDAO;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
@Repository(value = "consultaExistenciaSolDevDAO")
public class ConsultaExistenciaSolDevDAOImpl implements ConsultaExistenciaSolDevDAO {
    
    private static final Logger LOG = Logger.getLogger(ConsultaExistenciaSolDevDAOImpl.class);

    private static final String QUERY = "SELECT SERVICIO.NUMCONTROL " +
                                        "FROM DYCP_SERVICIO SERVICIO \n" +
                                        "INNER JOIN DYCP_SOLICITUD SOLICITUD ON SOLICITUD.NUMCONTROL = SERVICIO.NUMCONTROL \n" +
                                        "INNER JOIN DYCT_SALDOICEP SALDO ON SOLICITUD.IDSALDOICEP = SALDO.IDSALDOICEP \n" +
                                        "WHERE SALDO.RFC=? \n" +
                                        "AND SERVICIO.IDTIPOTRAMITE IN (115, 213) \n" +
                                        "AND SALDO.IDCONCEPTO=? \n" +
                                        "AND SALDO.IDEJERCICIO=? \n" +
                                        "AND SALDO.IDPERIODO=? \n" +
                                        "AND SALDO.IDORIGENSALDO=1 \n" +
                                        "ORDER BY " +
                                        "FECHAPRESENTACION desc ";
    
    
    @Autowired
    private JdbcTemplate jdbcTemplateADM;

    
    @Override
    public List<String> consultarSolCompIRSDevAut(String rfc, BigInteger impuesto, BigInteger concepto,
                                                  BigInteger ejercicio, String periodo) throws DAOException {
        String params = "";
        try {
            params = "   RFC--> "+ rfc + 
                     ",  Impuesto--> " + impuesto.intValue() + 
                     ",  Concepto--> " + concepto.intValue() + 
                     ",  Ejercicio--> " + ejercicio.intValue() +
                     ",  Periodo--> " + periodo;
            
            return jdbcTemplateADM.queryForList( 
                QUERY, 
                new Object[]{ rfc, concepto.intValue(), ejercicio.intValue(), Integer.parseInt( periodo )}, 
                String.class
            );

                                                        //new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, 
                                                                  //Types.INTEGER, Types.INTEGER},
            
        } catch (InvalidDataAccessApiUsageException iData){
            String mensajeError = "Error al no encontrar un Solicitud de Devolicion" + params;
            
            LOG.error(mensajeError, iData);
            return new ArrayList<String>();
        } catch (EmptyResultDataAccessException e) {
            LOG.info("consultarSolCompIRSDevAut no existen resultados ::: " +params+ " ::: ", e);
            return new ArrayList<String>();
        }   catch (DataAccessException ex) {
            String msgError = "ERROR AL CONSULTAR existencia de una Solicitud de devolución manual,  " + 
                              "Aviso de compensación o Caso de compensación ::: " + params + " ::: ";
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        } 
    }
    
}
