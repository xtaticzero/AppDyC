/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl;


import java.io.FileNotFoundException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRsociedadIntegradoraDAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRsociedadIntegradoraDTO;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Esta clase reempla el Stored Procedure sp_sti_cddisrd1.sql
 * Los parametros de entrada estan descritos segun la siguiente informacion
 *
 * Consulta de datos de la Determinacisn del Impuesto Sobre la Renta
 * en la Declaracion del Ejercicio Presentada por Sociedades del
 * Regimen Simplificado. Interfaz 29
 *
 * @author Israel Chàvez.
 * @since 07/08/2013
 *
 */
@Repository(value = "consultarDeclaracionISRsociedadIntegradoraDAO")
@Transactional
public class ConsultarDeclaracionISRsociedadIntegradoraDAOimpl implements ConsultarDeclaracionISRsociedadIntegradoraDAO,
                                                                          SQLInformixSIAT {

    public ConsultarDeclaracionISRsociedadIntegradoraDAOimpl() {
        super();
    }

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     *
     */
    @Override
    public List<ConsultarDeclaracionISRsociedadIntegradoraDTO> consultarDeclaracionISRsociedadIntegradora(ConsultarDeclaracionISRsociedadIntegradoraDTO consultarDeclaracionISRsociedadIntegradoraDTO) throws FileNotFoundException,
                                                                                                                                                                                                              ClassNotFoundException,
                                                                                                                                                                                                              SQLException {
        List<ConsultarDeclaracionISRsociedadIntegradoraDTO> detalleDeclaracionISRsociedadIntegradoraList =
            new ArrayList<ConsultarDeclaracionISRsociedadIntegradoraDTO>();
        return detalleDeclaracionISRsociedadIntegradoraList;
    }

    public void setJdbcTemplateInformix(JdbcTemplate jdbcTemplateInformix) {
        this.jdbcTemplateInformix = jdbcTemplateInformix;
    }

    public JdbcTemplate getJdbcTemplateInformix() {
        return jdbcTemplateInformix;
    }
}
