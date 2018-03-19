package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ConceptoMapper;
import mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper.EjercicioMapper;
import mx.gob.sat.siat.dyc.dao.periodo.impl.mapper.PeriodoMapper;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


public class DyctSaldoIcepMapper implements RowMapper<DyctSaldoIcepDTO> {

    private DyccConceptoDTO concepto;
    private DyccEjercicioDTO ejercicio;
    private DyccPeriodoDTO periodo;
    private DyccOrigenSaldoDTO origenSaldo;

    private ConceptoMapper mapperConcepto;
    private EjercicioMapper mapperEjercicio;
    private PeriodoMapper mapperPeriodo;
    private OrigenSaldoMapper mapperOrigenSaldo;

    private Logger log = Logger.getLogger(DyctSaldoIcepMapper.class.getName());

    @Override
    public DyctSaldoIcepDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccConceptoDTO conceptoL = null;

        try {
            if (rs.getString("folioICEPS") != null && !rs.getString("folioICEPS").isEmpty()) {
                DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
                saldoIcep.setBoId(rs.getString("folioICEPS"));
                return saldoIcep;
            }
        } catch (Exception e) {
            log.debug("Consulta sin folio continua flujo");
        }

        if (concepto != null) {
            conceptoL = concepto;
        } else {
            if (mapperConcepto != null) {
                conceptoL = mapperConcepto.mapRow(rs, i);
            } else {
                conceptoL = new DyccConceptoDTO();
                conceptoL.setIdConcepto(rs.getInt("IDCONCEPTO"));
            }
        }

        DyccEjercicioDTO ejercicioL;
        if (ejercicio != null) {
            ejercicioL = ejercicio;
        } else {
            if (mapperEjercicio != null) {
                ejercicioL = mapperEjercicio.mapRow(rs, i);
            } else {
                ejercicioL = new DyccEjercicioDTO();
                ejercicioL.setIdEjercicio(rs.getInt("IDEJERCICIO"));
            }
        }

        DyccPeriodoDTO periodoL;
        if (periodo != null) {
            periodoL = periodo;
        } else {
            if (mapperPeriodo != null) {
                periodoL = mapperPeriodo.mapRow(rs, i);
            } else {
                periodoL = new DyccPeriodoDTO();
                periodoL.setIdPeriodo(rs.getInt("IDPERIODO"));
            }
        }

        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        saldoIcep.setIdSaldoIcep(rs.getInt("IDSALDOICEP"));
        saldoIcep.setRfc(rs.getString("RFC"));
        saldoIcep.setDyccConceptoDTO(conceptoL);
        saldoIcep.setDyccEjercicioDTO(ejercicioL);
        saldoIcep.setDyccPeriodoDTO(periodoL);
        saldoIcep.setRemanente(rs.getBigDecimal("REMANENTE"));
        saldoIcep.setDyccOrigenSaldoDTO(BuscadorConstantes.obtenerOrigenSaldo(rs.getInt("IDORIGENSALDO")));
        saldoIcep.setBloqueado(rs.getInt("BLOQUEADO"));
        saldoIcep.setFechaBaseCalculo(rs.getTimestamp("FECHABASECALCULO"));
        saldoIcep.setActRemanente(rs.getTimestamp("ACTREMANENTE"));

        return saldoIcep;
    }

    public ConceptoMapper getMapperConcepto() {
        return mapperConcepto;
    }

    public void setMapperConcepto(ConceptoMapper mapperConcepto) {
        this.mapperConcepto = mapperConcepto;
    }

    public void setMapperEjercicio(EjercicioMapper mapperEjercicio) {
        this.mapperEjercicio = mapperEjercicio;
    }

    public EjercicioMapper getMapperEjercicio() {
        return mapperEjercicio;
    }

    public void setMapperPeriodo(PeriodoMapper mapperPeriodo) {
        this.mapperPeriodo = mapperPeriodo;
    }

    public PeriodoMapper getMapperPeriodo() {
        return mapperPeriodo;
    }

    public DyccConceptoDTO getConcepto() {
        return concepto;
    }

    public void setConcepto(DyccConceptoDTO concepto) {
        this.concepto = concepto;
    }

    public DyccEjercicioDTO getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(DyccEjercicioDTO ejercicio) {
        this.ejercicio = ejercicio;
    }

    public DyccPeriodoDTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(DyccPeriodoDTO periodo) {
        this.periodo = periodo;
    }

    public DyccOrigenSaldoDTO getOrigenSaldo() {
        return origenSaldo;
    }

    public void setOrigenSaldo(DyccOrigenSaldoDTO origenSaldo) {
        this.origenSaldo = origenSaldo;
    }

    public OrigenSaldoMapper getMapperOrigenSaldo() {
        return mapperOrigenSaldo;
    }

    public void setMapperOrigenSaldo(OrigenSaldoMapper mapperOrigenSaldo) {
        this.mapperOrigenSaldo = mapperOrigenSaldo;
    }
}
