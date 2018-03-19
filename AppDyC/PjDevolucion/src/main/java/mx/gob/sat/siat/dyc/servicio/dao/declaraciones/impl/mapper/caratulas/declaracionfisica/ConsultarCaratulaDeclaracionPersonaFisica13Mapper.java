/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper.caratulas.declaracionfisica;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionfisica.ConsultarCaratulaDeclaracionPersonaFisica13DTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * ISCC
 * @author
 * 23/07/2013
 * TODO
 */
public class ConsultarCaratulaDeclaracionPersonaFisica13Mapper implements RowMapper<ConsultarCaratulaDeclaracionPersonaFisica13DTO> {
    public ConsultarCaratulaDeclaracionPersonaFisica13Mapper() {
        super();
    }

    public ConsultarCaratulaDeclaracionPersonaFisica13DTO mapRow(ResultSet resultado, int rowNum) throws SQLException {

        ConsultarCaratulaDeclaracionPersonaFisica13DTO consultaDatosDeclaracion =
            new ConsultarCaratulaDeclaracionPersonaFisica13DTO();

        consultaDatosDeclaracion.setNNumeroOperacion(resultado.getBigDecimal("n_numero_operacion"));
        consultaDatosDeclaracion.setVFechaCausacion(resultado.getString("v_fecha_causacion"));
        consultaDatosDeclaracion.setTdiepco1(resultado.getInt("tdiepco1"));
        consultaDatosDeclaracion.setFperceh1(resultado.getDate("fperceh1"));
        consultaDatosDeclaracion.setImporteAcargo(resultado.getBigDecimal("importe_acargo"));
        consultaDatosDeclaracion.setParteAct(resultado.getBigDecimal("parte_act"));
        consultaDatosDeclaracion.setRecargos(resultado.getBigDecimal("recargos"));
        consultaDatosDeclaracion.setMultaCorrec(resultado.getBigDecimal("multa_correc"));
        consultaDatosDeclaracion.setTotContribuc(resultado.getBigDecimal("tot_contribuc"));
        consultaDatosDeclaracion.setCreditoSal(resultado.getBigDecimal("n_numero_operacion"));
        consultaDatosDeclaracion.setCompensacion(resultado.getBigDecimal("compensacion"));
        consultaDatosDeclaracion.setCreditoDis(resultado.getBigDecimal("credito_dis"));
        consultaDatosDeclaracion.setDieselAuto(resultado.getBigDecimal("diesel_auto"));
        consultaDatosDeclaracion.setCarreteraCuo(resultado.getBigDecimal("carretera_cuo"));
        consultaDatosDeclaracion.setOtrosEstim(resultado.getBigDecimal("otros_estim"));
        consultaDatosDeclaracion.setTotAplica(resultado.getBigDecimal("tot_aplica"));
        consultaDatosDeclaracion.setIImptepagant(resultado.getBigDecimal("i_imptepagant"));
        consultaDatosDeclaracion.setCanAcargo(resultado.getBigDecimal("can_acargo"));
        consultaDatosDeclaracion.setImpPriPar(resultado.getBigDecimal("imp_pri_par"));
        consultaDatosDeclaracion.setImpSinPar(resultado.getBigDecimal("imp_sin_par"));
        consultaDatosDeclaracion.setCantiFavor(resultado.getBigDecimal("canti_favor"));
        consultaDatosDeclaracion.setCanPagar(resultado.getBigDecimal("can_pagar"));

        return consultaDatosDeclaracion;
    }
}
