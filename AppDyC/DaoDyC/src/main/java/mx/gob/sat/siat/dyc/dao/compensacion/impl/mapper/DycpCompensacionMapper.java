package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


public class DycpCompensacionMapper implements RowMapper<DycpCompensacionDTO> {


    public DycpCompensacionDTO mapRow(ResultSet rs, int i) throws SQLException {

        DycpServicioMapper mapperServicio;
        DycpServicioDTO servicio;
        DycpCompensacionDTO compensacion = new DycpCompensacionDTO();

        mapperServicio = new DycpServicioMapper();
        servicio = mapperServicio.mapRow(rs, i);

        DyctDeclaracionDTO declara = new DyctDeclaracionDTO();
        declara.setSaldoAfavor(rs.getBigDecimal("SALDOAFAVOR"));
        declara.setFechaCausacion(rs.getDate("FECHACAUSACION"));
        declara.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        List<DyctDeclaracionDTO> declaracionList = new ArrayList<DyctDeclaracionDTO>();
        declaracionList.add(declara);
        servicio.setDyctDeclaracionList(declaracionList);

        servicio.getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().setDescripcion(rs.getString("DESCRIPCION_DTT"));

        compensacion.setDycpServicioDTO(servicio);
        // ::::::::::::::::::::::::::: SALDO ICEP ORIGEN :::::::::::::::::::::::::::
        compensacion.setDyctSaldoIcepOrigenDTO(mapeoSaldoOrigen(rs));

        // ::::::::::::::::::::::::::: SALDO ICEP DESTINO :::::::::::::::::::::::::::
        DyctSaldoIcepDTO icepDestino = new DyctSaldoIcepDTO();
        icepDestino.setIdSaldoIcep(rs.getInt("IDSALDOICEPDESTINO"));

        icepDestino.setDyccConceptoDTO(new DyccConceptoDTO());
        icepDestino.getDyccConceptoDTO().setIdConcepto(rs.getInt("IDCONCEPTOD"));
        icepDestino.getDyccConceptoDTO().setDescripcion(rs.getString("DESCRIPCION_CPD"));

        icepDestino.getDyccConceptoDTO().setDyccImpuestoDTO(new DyccImpuestoDTO());
        icepDestino.getDyccConceptoDTO().getDyccImpuestoDTO().setIdImpuesto(rs.getInt("IDIMPUESTOD"));
        icepDestino.getDyccConceptoDTO().getDyccImpuestoDTO().setDescripcion(rs.getString("DESCRIPCION_IMP"));

        icepDestino.setDyccEjercicioDTO(new DyccEjercicioDTO());
        icepDestino.getDyccEjercicioDTO().setIdEjercicio(rs.getInt("IDEJERCICIOD"));

        icepDestino.setDyccPeriodoDTO(new DyccPeriodoDTO());
        icepDestino.getDyccPeriodoDTO().setIdPeriodo(rs.getInt("IDPERIODOD"));
        icepDestino.getDyccPeriodoDTO().setDescripcion(rs.getString("DESCRIPCION_PDD"));

        compensacion.setDyctSaldoIcepDestinoDTO(icepDestino);

        compensacion.setNumControl(rs.getString("NUMCONTROL"));
        compensacion.setImporteCompensado(rs.getBigDecimal("IMPORTECOMPENSADO"));
        compensacion.setRemanenteHistorico(rs.getBigDecimal("REMANENTEHISTORICO"));
        /** FECHAPRESENTADEC */
        compensacion.setFechaPresentaDec(rs.getDate("FECHAPRESENTACION"));

        compensacion.setDyccEstadoCompDTO(BuscadorConstantes.obtenerEstadoComp(rs.getInt("IDESTADOCOMP")));
        compensacion.setDyccTipoDeclaraDTO(BuscadorConstantes.obtenerTipoDeclaracion(rs.getInt("IDTIPODECLARACION")));

        compensacion.setDyctResolCompDTO(new DyctResolCompDTO());
        compensacion.getDyctResolCompDTO().setDyccEstResolDTO(BuscadorConstantes.obtenerEstadoResolucion(rs.getInt("IDESTRESOL")));
        /** PENDIENTE... compensacion.getDyctResolCompDTO().setFechaResolucion(rs.getDate("FECHARESOLUCION")); */

        return compensacion;
    }
    
    private DyctSaldoIcepDTO mapeoSaldoOrigen(ResultSet rs) throws SQLException {
        DyctSaldoIcepDTO icepOrigen = new DyctSaldoIcepDTO();
        icepOrigen.setIdSaldoIcep(rs.getInt("IDSALDOICEPORIGEN"));

        icepOrigen.setDyccConceptoDTO(new DyccConceptoDTO());
        icepOrigen.getDyccConceptoDTO().setIdConcepto(rs.getInt("IDCONCEPTOO"));
        icepOrigen.getDyccConceptoDTO().setDescripcion(rs.getString("DESCRIPCION_CPO"));
        icepOrigen.getDyccConceptoDTO().setDyccImpuestoDTO(new DyccImpuestoDTO());
        icepOrigen.getDyccConceptoDTO().getDyccImpuestoDTO().setIdImpuesto(rs.getInt("IDIMPUESTO"));
        icepOrigen.getDyccConceptoDTO().getDyccImpuestoDTO().setDescripcion(rs.getString("DESCRIPCION_IO"));

        icepOrigen.setDyccEjercicioDTO(new DyccEjercicioDTO());
        icepOrigen.getDyccEjercicioDTO().setIdEjercicio(rs.getInt("IDEJERCICIOO"));

        icepOrigen.setDyccPeriodoDTO(new DyccPeriodoDTO());
        icepOrigen.getDyccPeriodoDTO().setIdPeriodo(rs.getInt("IDPERIODOO"));
        icepOrigen.getDyccPeriodoDTO().setDescripcion(rs.getString("DESCRIPCION_PDO"));
        icepOrigen.getDyccPeriodoDTO().setDyccTipoPeriodoDTO(BuscadorConstantes.obtenerTipoPeriodo(rs.getString("IDTIPOPERIODO")));
        icepOrigen.setDyccOrigenSaldoDTO(BuscadorConstantes.obtenerOrigenSaldo(rs.getInt("IDORIGENSALDO")));

        /** icepOrigen.setRemanente(rs.getDouble("SALDOAFAVOR")); */
        icepOrigen.setRfc(rs.getString("RFCO"));
        return icepOrigen;
    }
}
