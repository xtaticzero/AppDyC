package mx.gob.sat.siat.dyc.casocomp.dao.districomp.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


public class CrearCasoCompMapper implements RowMapper<CasoCompensacionVO>
{
    @Override
    public CasoCompensacionVO mapRow(ResultSet rs, int i) throws SQLException {
        CasoCompensacionVO casoCompensacionVO = new CasoCompensacionVO();
        
        DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        DycaOrigenTramiteDTO dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();
        DycaServOrigenDTO dycaServOrigenDTO = new DycaServOrigenDTO();
        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();

        casoCompensacionVO.setEstadoTemporal(rs.getString("ESTADO"));
        dycpServicioDTO.setRfcContribuyente(rs.getString("RFC"));
        casoCompensacionVO.setIdDeclaracion(rs.getString("idDECLARACION"));
        compensacion.setNumOperacionDec(rs.getString("NUMEROOPERACION"));
        compensacion.setDyccTipoDeclaraDTO(BuscadorConstantes.obtenerTipoDeclaracion(rs.getInt("TIPODECLARACION")));
        compensacion.setFechaPresentaDec(rs.getDate("FECHAPRESENTACION"));
        compensacion.setDyctSaldoIcepDestinoDTO(saldoIcepDestino(rs));
        compensacion.setImporteCompensado(rs.getBigDecimal("IMPORTECOMPENSADO"));
        dyccOrigenSaldoDTO.setIdOrigenSaldo(rs.getInt("ORIGENSALDO"));
        dycaServOrigenDTO.setDyccOrigenSaldoDTO(dyccOrigenSaldoDTO);
        dycaOrigenTramiteDTO.setDycaServOrigenDTO(dycaServOrigenDTO);
        dycpServicioDTO.setDycaOrigenTramiteDTO(dycaOrigenTramiteDTO);

        compensacion.setDyctSaldoIcepOrigenDTO(saldoIcepOrigen(rs));

        casoCompensacionVO.setFechaCausacion(rs.getDate("FECHACAUSACION"));
        casoCompensacionVO.setMontoSaldoAFavor(rs.getBigDecimal("MONTOSALDOFAVOR"));
        casoCompensacionVO.setFechaPresentacionOrigen(rs.getDate("FECHAPRESENTDECLARACION"));
        compensacion.setRemanenteHistorico(rs.getBigDecimal("REMANENTEHISTORICO"));
        compensacion.setRemanenteAct(rs.getBigDecimal("REMANENTEACTUALIZADO"));
        compensacion.setDycpServicioDTO(dycpServicioDTO);
        casoCompensacionVO.setDycpCompensacionDTO(compensacion);
        return casoCompensacionVO;
    }

    private DyctSaldoIcepDTO saldoIcepDestino(ResultSet rs) throws SQLException {
        DyctSaldoIcepDTO dyctSaldoIcepDestino = new DyctSaldoIcepDTO();
        DyccEjercicioDTO dyccEjercicioDestino = new DyccEjercicioDTO();
        DyccPeriodoDTO dyccPeriodoDestino = new DyccPeriodoDTO();
        DyccConceptoDTO dyccConceptoDestino = new DyccConceptoDTO();
        DyccImpuestoDTO dyccImpuestoDestino = new DyccImpuestoDTO();
        dyccEjercicioDestino.setIdEjercicio(rs.getInt("EJERCICIO"));
        dyctSaldoIcepDestino.setDyccEjercicioDTO(dyccEjercicioDestino);
        dyccPeriodoDestino.setIdPeriodo(rs.getInt("PERIODO"));
        dyctSaldoIcepDestino.setDyccPeriodoDTO(dyccPeriodoDestino);
        dyccImpuestoDestino.setIdImpuesto(rs.getInt("IMPUESTO"));
        dyccConceptoDestino.setDyccImpuestoDTO(dyccImpuestoDestino);
        dyccConceptoDestino.setIdConcepto(rs.getInt("CONCEPTO"));
        dyctSaldoIcepDestino.setDyccConceptoDTO(dyccConceptoDestino);
        return dyctSaldoIcepDestino;
    }

    private DyctSaldoIcepDTO saldoIcepOrigen(ResultSet rs) throws SQLException {
        DyctSaldoIcepDTO dyctSaldoIcepOrigen = new DyctSaldoIcepDTO();
        DyccEjercicioDTO dyccEjercicioOrigen = new DyccEjercicioDTO();
        DyccPeriodoDTO dyccPeriodoOrigen = new DyccPeriodoDTO();
        DyccConceptoDTO dyccConceptoOrigen = new DyccConceptoDTO();
        dyccPeriodoOrigen.setIdPeriodo(rs.getInt("PERIOSALDOFAVOR"));
        dyctSaldoIcepOrigen.setDyccPeriodoDTO(dyccPeriodoOrigen);
        dyccEjercicioOrigen.setIdEjercicio(rs.getInt("EJERCSALDOFAVOR"));
        dyctSaldoIcepOrigen.setDyccEjercicioDTO(dyccEjercicioOrigen);
        dyccConceptoOrigen.setIdConcepto(rs.getInt("CONCESALDOFAVOR"));
        dyctSaldoIcepOrigen.setDyccConceptoDTO(dyccConceptoOrigen);
        return dyctSaldoIcepOrigen;
    }
}
