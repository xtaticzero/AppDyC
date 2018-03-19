package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;

import org.springframework.jdbc.core.RowMapper;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

/**
 * Registro Consulta Mapper
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 
 */

public class RegistroConsultaMapper implements RowMapper<TramiteDTO> {

    public RegistroConsultaMapper() {
        super();
    }

    @Override
    public TramiteDTO mapRow(ResultSet rs, int i) throws SQLException {
        TramiteDTO tramiteDTO = new TramiteDTO();
        InformacionSaldoFavorTO infoSaldoFavorTO = new InformacionSaldoFavorTO();

        /*--datos que se llenan de arriba*/
        infoSaldoFavorTO.setTipoDeclaracion(rs.getString("IDTIPODECLARACION"));
        infoSaldoFavorTO.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        infoSaldoFavorTO.setFechaCaucion(rs.getDate("FECHACAUSACION"));
        infoSaldoFavorTO.setNumeroOperacion(rs.getString("NUMOPERACION"));
        infoSaldoFavorTO.setNumeroDocumento(rs.getString("NUMDOCUMENTO"));
        infoSaldoFavorTO.setImporteSaldoFavor(rs.getBigDecimal("SALDOAFAVOR"));
        infoSaldoFavorTO.setImporteEfectuados(rs.getBigDecimal("DEVUELTOCOMPENSADO"));
        infoSaldoFavorTO.setImporteAcreditramientoEfectuado(rs.getBigDecimal("ACREDITAMIENTO"));
        infoSaldoFavorTO.setImporteSolicitadoDevolucion(rs.getBigDecimal("IMPORTE"));

        /*---Llena datos de abajo*/
        infoSaldoFavorTO.setNormalFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        infoSaldoFavorTO.setNormalNumOperacion(rs.getString("NUMOPERACION"));
        infoSaldoFavorTO.setNormalSaldoFavor(rs.getBigDecimal("SALDOAFAVOR"));

        tramiteDTO.setSaldoFavor(infoSaldoFavorTO);
        return tramiteDTO;
    }
}
