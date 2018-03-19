/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.dao.expediente.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.ExpedienteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Implementaci&oacute;n DAO para insertar Expediente
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * */
public class IntegrarExpedienteDAOMapper implements RowMapper<ExpedienteDTO> {    

    public ExpedienteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        ExpedienteDTO expediente = new ExpedienteDTO();

        expediente.setNumControl(rs.getString("NUMCONTROL"));
        expediente.setPerfilDeRiesgo(rs.getString("PERFILDERIESGO"));

        if (null != rs.getClob("DATOSRETENEDORBANC")) {
            expediente.setDatosRetenedorBanc(rs.getClob("DATOSRETENEDORBANC").getAsciiStream());
        }
        if (null != rs.getClob("DATOSCPR")) {
            expediente.setDatosCPR(rs.getClob("DATOSCPR").getAsciiStream());
        }
        if (null != rs.getClob("DATOSDIOT")) {
            expediente.setDatosDIOT(rs.getClob("DATOSDIOT").getAsciiStream());
        }
        if (null != rs.getClob("DATOSALTEX")) {
            expediente.setDatosALTEX(rs.getClob("DATOSALTEX").getAsciiStream());
        }
        if (null != rs.getClob("DATOSPAGOS")) {
            expediente.setDatosPagos(rs.getClob("DATOSPAGOS").getAsciiStream());
        }
        if (null != rs.getClob("DATOSCOMPENSACION")) {
            expediente.setDatosCompensacion(rs.getClob("DATOSCOMPENSACION").getAsciiStream());
        }
        if (null != rs.getClob("DATOSPEDIMENTOS")) {
            expediente.setDatosPedimentos(rs.getClob("DATOSPEDIMENTOS").getAsciiStream());
        }
        if (null != rs.getClob("DATOSDEVOLUCIONES")) {
            expediente.setDatosDevoluciones(rs.getClob("DATOSDEVOLUCIONES").getAsciiStream());
        }
        if (null != rs.getClob("DATOSDICTAMENES")) {
            expediente.setDatosDictamenes(rs.getClob("DATOSDICTAMENES").getAsciiStream());
        }
        if (null != rs.getClob("DATOSDECLARACIONES")) {
            expediente.setDatosDeclaraciones(rs.getClob("DATOSDECLARACIONES").getAsciiStream());
        }
        if (null != rs.getClob("DATOSCONSOLIDACION")) {
            expediente.setDatosDictamenes(rs.getClob("DATOSCONSOLIDACION").getAsciiStream());
        }
        if (null != rs.getClob("DATOSPAGODERECHOS")) {
            expediente.setDatosPagoDerechos(rs.getClob("DATOSPAGODERECHOS").getAsciiStream());
        }
        if (null != rs.getClob("DATOSPAGOMULTAS")) {
            expediente.setDatosPagoMultas(rs.getClob("DATOSPAGOMULTAS").getAsciiStream());
        }
        if (null != rs.getClob("DATOSDETERMINAISR")) {
            expediente.setDatosDeterminaISR(rs.getClob("DATOSDETERMINAISR").getAsciiStream());
        }
        if (null != rs.getClob("DATOSDETERMINAIMP")) {
            expediente.setDatosDeterminaIMP(rs.getClob("DATOSDETERMINAIMP").getAsciiStream());
        }
        if (null != rs.getClob("DATOSSALDOICEP")) {
            expediente.setDatosSaldoICEP(rs.getClob("DATOSSALDOICEP").getAsciiStream());
        }
        if (null != rs.getClob("DATOSRETENEDORN")) {
            expediente.setDatosRetenedorN(rs.getClob("DATOSRETENEDORN").getAsciiStream());
        }
        
        expediente.setSaldoRetenedorN(rs.getDouble("SALDORETENEDORN"));
        
        expediente.setSaldoicep(rs.getDouble("SALDOICEP"));

        return expediente;
    }

}
