package mx.gob.sat.siat.dyc.dao.anexo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 */
public class ExpedienteMapper implements RowMapper<DyctExpedienteDTO> {

    private DyctExpedienteDTO expediente = new DyctExpedienteDTO();

    @Override
    public DyctExpedienteDTO mapRow(ResultSet rs, int i) throws SQLException {

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

        segundoMapper(rs, i);


        return expediente;
    }


    public void segundoMapper(ResultSet rs, int i) throws SQLException {
        
        DycpServicioDTO dycpServicio = new DycpServicioDTO();

        if (null != rs.getClob("DATOSDICTAMENES")) {
            expediente.setDatosDictamenes(rs.getClob("DATOSDICTAMENES").getAsciiStream());
        }
        if (null != rs.getClob("DATOSDECLARACIONES")) {
            expediente.setDatosDeclaraciones(rs.getClob("DATOSDECLARACIONES").getAsciiStream());
        }
        if (null != rs.getClob("DATOSCONSOLIDACION")) {
            expediente.setDatosConsolidacion(rs.getClob("DATOSCONSOLIDACION").getAsciiStream());
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
        
        dycpServicio.setNumControl(rs.getString("NUMCONTROL"));
        expediente.setServicioDTO(dycpServicio);

        if (null != rs.getClob("DATOSSALDOICEP")) {
            expediente.setDatosSaldoICEP(rs.getClob("DATOSSALDOICEP").getAsciiStream());
        }

        expediente.setSaldoIcep(rs.getBigDecimal("SALDOICEP"));
    }
}
