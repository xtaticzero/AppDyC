package mx.gob.sat.mat.dyc.background.declscomple.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.mat.dyc.background.declscomple.vo.DeclaracionComplementariaVO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;


import org.springframework.jdbc.core.RowMapper;


public class ProcesoDeclaracionComplemenMapper implements RowMapper<DeclaracionComplementariaVO> {
    
    public ProcesoDeclaracionComplemenMapper() {
        super();
    }

    @Override
    public DeclaracionComplementariaVO mapRow(ResultSet rs, int i) throws SQLException
    {
        DeclaracionComplementariaVO declaracionComple = new DeclaracionComplementariaVO();
        DyccPeriodoDTO dyccPeriodoDTO = new DyccPeriodoDTO();
        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
        DyccConceptoDTO dyccConceptoDTO = new DyccConceptoDTO();
        DyccImpuestoDTO dyccImpuestoDTO = new DyccImpuestoDTO();
        DyccTipoDeclaraDTO dyccTipoDeclaracionDTO = new DyccTipoDeclaraDTO();
        DycpCompensacionDTO dycpCompensacion = new DycpCompensacionDTO();
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();

        declaracionComple.setIdDeclaracion(rs.getString("idDECLARACION"));
        declaracionComple.setCantidadAFavor(rs.getDouble("CANTIDADAFAVOR"));
        declaracionComple.setSaldoAFavor(rs.getDouble("SALDOAFAVOR"));
        
        dyccConceptoDTO.setIdConcepto(rs.getInt("CONCEPTO"));
        dyccImpuestoDTO.setIdImpuesto(rs.getInt("IMPUESTO"));
        dyccConceptoDTO.setDyccImpuestoDTO(dyccImpuestoDTO);
        dyctSaldoIcepDTO.setDyccConceptoDTO(dyccConceptoDTO);
        dyccPeriodoDTO.setIdPeriodo(rs.getInt("PERIODO"));
        dyctSaldoIcepDTO.setDyccPeriodoDTO(dyccPeriodoDTO);
        dyccEjercicioDTO.setIdEjercicio(rs.getInt("EJERCICIO"));
        dyctSaldoIcepDTO.setDyccEjercicioDTO(dyccEjercicioDTO);
        dycpCompensacion.setDyctSaldoIcepOrigenDTO(dyctSaldoIcepDTO);
        
        dycpServicioDTO.setRfcContribuyente(rs.getString("RFC"));
        dycpCompensacion.setDycpServicioDTO(dycpServicioDTO);
            
        dyccTipoDeclaracionDTO.setIdTipoDeclaracion(rs.getInt("TIPODECLARACION"));
        dycpCompensacion.setDyccTipoDeclaraDTO(dyccTipoDeclaracionDTO);
        
        dycpCompensacion.setFechaPresentaDec(rs.getDate("FECHAPRESENTACION"));
        dycpCompensacion.setFechaInicioTramite(rs.getDate("FECHACARGA"));
        dycpCompensacion.setNumOperacionDec(rs.getString("NUMEROOPERACION"));
    
        declaracionComple.setDycpCompensacionDTO(dycpCompensacion);
        
        return declaracionComple;
    }
}
