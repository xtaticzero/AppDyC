/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRMoralDAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRMoralDTO;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.springframework.stereotype.Repository;


/**
 * Esta clase reempla el Stored Procedure sp_sti_cddisrd2.sql
 * Los parametros de entrada estan descritos segun la siguiente informacion
 *
 * Consulta de Datos de la Determinacion del
 * Impuesto Sobre la Renta en la Declaracion
 * del Ejercicio Presentada por Personas Morales
 * del Rigimen General. Interfaz 25 (FORMA_18)
 *
 * @author Israel Chàvez
 * @since 07/08/2013
 * @see Modifica LuFerMX elimina
 *     private JdbcTemplate jdbcTemplateInformix;
 *
 */
@Repository(value = "consultarDeclaracionISRMoralDAO")
public class ConsultarDeclaracionISRMoralDAOImpl implements ConsultarDeclaracionISRMoralDAO, SQLInformixSIAT {

    public ConsultarDeclaracionISRMoralDAOImpl() {
        super();
    }

    /**
     * TODO
     * @param declaracionISRMoralDTO
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public List<ConsultarDeclaracionISRMoralDTO> consultarDeclaracionISRMoral(ConsultarDeclaracionISRMoralDTO declaracionISRMoralDTO) {
        List<ConsultarDeclaracionISRMoralDTO> detalleDeclaracionISRMoralDTO =
            new ArrayList<ConsultarDeclaracionISRMoralDTO>();
        return detalleDeclaracionISRMoralDTO;
    }
}
