/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;


/**
 * DTO de la tabla DYCT_SALDOICEP
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctSaldoIcepDTO implements Serializable {

    @SuppressWarnings("compatibility:-1826216287541961853")
    private static final long serialVersionUID = 1L;

    private Integer bloqueado;
    private Integer idSaldoIcep;
    private BigDecimal remanente;
    private String rfc;
    private DyccConceptoDTO dyccConceptoDTO;
    private DyccPeriodoDTO dyccPeriodoDTO;
    private DyccEjercicioDTO dyccEjercicioDTO;
    private DyccOrigenSaldoDTO dyccOrigenSaldoDTO;
    private Date fechaFin;
    private String boId;
    private Date fechaBaseCalculo;
    private Date actRemanente;

    private List<DyctDeclaraIcepDTO> dyctDeclaraIcepList;
    private List<DyctDeclaraIcepDTO> dyctDeclaraIcepListCancel;
    private List<DycpSolicitudDTO> dycpSolicitudList;
    private List<DyctMovSaldoDTO> dyctMovSaldoList;

    private List<DyctMovDevolucionDTO> dyctMovDevolucionList;
    private List<DyctCompHistoricaDTO> dyctCompHistoricaList;

    private List<DycpCompensacionDTO> dycpCompensacionList;

    public DyctSaldoIcepDTO() {
    }

    public DyctSaldoIcepDTO(Integer bloqueado, DyccConceptoDTO dyccConceptoDTO, DyccEjercicioDTO dyccEjercicioDTO,
                            DyccPeriodoDTO dyccPeriodoDTO, Integer idSaldoIcep, BigDecimal remanente, String rfc) {
        this.bloqueado = bloqueado;
        this.dyccConceptoDTO = dyccConceptoDTO;
        this.dyccEjercicioDTO = dyccEjercicioDTO;
        this.dyccPeriodoDTO = dyccPeriodoDTO;
        this.idSaldoIcep = idSaldoIcep;
        this.remanente = remanente;
        this.rfc = rfc;
    }

    public void setBloqueado(Integer bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Integer getBloqueado() {
        return bloqueado;
    }

    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setRemanente(BigDecimal remanente) {
        this.remanente = remanente;
    }

    public BigDecimal getRemanente() {
        return remanente;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setDyccConceptoDTO(DyccConceptoDTO dyccConceptoDTO) {
        this.dyccConceptoDTO = dyccConceptoDTO;
    }

    public DyccConceptoDTO getDyccConceptoDTO() {
        return dyccConceptoDTO;
    }

    public void setDyccPeriodoDTO(DyccPeriodoDTO dyccPeriodoDTO) {
        this.dyccPeriodoDTO = dyccPeriodoDTO;
    }

    public DyccPeriodoDTO getDyccPeriodoDTO() {
        return dyccPeriodoDTO;
    }

    public void setDyccEjercicioDTO(DyccEjercicioDTO dyccEjercicioDTO) {
        this.dyccEjercicioDTO = dyccEjercicioDTO;
    }

    public DyccEjercicioDTO getDyccEjercicioDTO() {
        return dyccEjercicioDTO;
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public void setDyctDeclaraIcepList(List<DyctDeclaraIcepDTO> dyctDeclaraIcepList) {
        this.dyctDeclaraIcepList = dyctDeclaraIcepList;
    }

    public List<DyctDeclaraIcepDTO> getDyctDeclaraIcepList() {
        return dyctDeclaraIcepList;
    }

    public void setDyctDeclaraIcepListCancel(List<DyctDeclaraIcepDTO> dyctDeclaraIcepListCancel) {
        this.dyctDeclaraIcepListCancel = dyctDeclaraIcepListCancel;
    }

    public List<DyctDeclaraIcepDTO> getDyctDeclaraIcepListCancel() {
        return dyctDeclaraIcepListCancel;
    }

    public List<DycpSolicitudDTO> getDycpSolicitudList() {
        return dycpSolicitudList;
    }

    public void setDycpSolicitudList(List<DycpSolicitudDTO> dycpSolicitudList) {
        this.dycpSolicitudList = dycpSolicitudList;
    }

    public List<DyctMovSaldoDTO> getDyctMovSaldoList() {
        return dyctMovSaldoList;
    }

    public void setDyctMovSaldoList(List<DyctMovSaldoDTO> dyctMovSaldoList) {
        this.dyctMovSaldoList = dyctMovSaldoList;
    }

    public List<DyctMovDevolucionDTO> getDyctMovDevolucionList() {
        return dyctMovDevolucionList;
    }

    public void setDyctMovDevolucionList(List<DyctMovDevolucionDTO> dyctMovDevolucionList) {
        this.dyctMovDevolucionList = dyctMovDevolucionList;
    }

    public List<DycpCompensacionDTO> getDycpCompensacionList() {
        return dycpCompensacionList;
    }

    public void setDycpCompensacionList(List<DycpCompensacionDTO> dycpCompensacionList) {
        this.dycpCompensacionList = dycpCompensacionList;
    }

    public String getBoId() {
        return boId;
    }

    public void setBoId(String boId) {
        this.boId = boId;
    }

    public Date getFechaBaseCalculo() {
        return (fechaBaseCalculo != null) ? (Date)fechaBaseCalculo.clone() : null;
    }

    public void setFechaBaseCalculo(Date fechaBaseCalculo) {
        this.fechaBaseCalculo = (fechaBaseCalculo != null) ? (Date)fechaBaseCalculo.clone() : null;
    }

    public Date getActRemanente() {
        return (actRemanente != null) ? (Date)actRemanente.clone() : null;
    }

    public void setActRemanente(Date actRemanente) {
        this.actRemanente = (actRemanente != null) ? (Date)actRemanente.clone() : null;
    }

    public void setDyctCompHistoricaList(List<DyctCompHistoricaDTO> dyctCompHistoricaList) {
        this.dyctCompHistoricaList = dyctCompHistoricaList;
    }

    public List<DyctCompHistoricaDTO> getDyctCompHistoricaList() {
        return dyctCompHistoricaList;
    }

    public DyccOrigenSaldoDTO getDyccOrigenSaldoDTO() {
        return dyccOrigenSaldoDTO;
    }

    public void setDyccOrigenSaldoDTO(DyccOrigenSaldoDTO dyccOrigenSaldoDTO) {
        this.dyccOrigenSaldoDTO = dyccOrigenSaldoDTO;
    }

    @Override
    public String toString() {
        String concepto =
            dyccConceptoDTO != null ? (dyccConceptoDTO.getIdConcepto() + "-" + dyccConceptoDTO.getDescripcion()) :
            null;
        Integer ejercicio = dyccEjercicioDTO != null ? dyccEjercicioDTO.getIdEjercicio() : null;
        String periodo =
            dyccPeriodoDTO != null ? (dyccPeriodoDTO.getIdPeriodo() + "-" + dyccPeriodoDTO.getDescripcion()) : null;
        return idSaldoIcep + "|" + rfc + "|" + concepto + "|" + ejercicio + "|" + periodo;
    }
}
