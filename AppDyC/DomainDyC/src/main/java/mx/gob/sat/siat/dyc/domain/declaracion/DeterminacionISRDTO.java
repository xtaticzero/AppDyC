/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.domain.declaracion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import mx.gob.sat.siat.dyc.domain.contribuyente.ContribuyenteDTO;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public class DeterminacionISRDTO implements Serializable {

    private static final long serialVersionUID = 1309846398547544233L;
    private Integer tipoDeclaracion;
    private Date fechaPresentacion;
    private Integer ejercicio;
    private Integer periodo;
    private String descripcionPeriodo;
    private String cuentaCLABE;
    private BigDecimal montoIsrAFavor;
    private String nombreInstitucionBancaria;
    private ContribuyenteDTO contribuyente;
    private Integer firmaDeclaracion;
    private Integer aceptoPropuestaRecibida;
    private BigDecimal ingresosAcumulables;
    private BigDecimal perdidas;
    private BigDecimal totalIngresosAcumulables;
    private BigDecimal deduccionesPersonales;
    private BigDecimal baseGravable;
    private BigDecimal isrConformeTarifaAnual;
    private BigDecimal subsidioEmpleo;
    private BigDecimal imptoSobreIngresosAcumulables;
    private BigDecimal imptoSobreIngresosNoAcumulables;
    private BigDecimal reduccionesDeISR;
    private BigDecimal imptoSobreLaRentaCausado;
    private BigDecimal pagosProvisionales;
    private BigDecimal imptoRetenidoAlcontribuyente;
    private BigDecimal imptoAcreditablePagadoEnExtranjero;
    private BigDecimal sectorPrimario;
    private BigDecimal imptoSobreInteresRealPorRetirosParciales;
    private BigDecimal isrAFavorDelEjercicio;
    private BigDecimal isrACargoDelEjercicio;

    public Integer getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setTipoDeclaracion(Integer tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public Date getFechaPresentacion() {
        return (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public String getDescripcionPeriodo() {
        return descripcionPeriodo;
    }

    public void setDescripcionPeriodo(String descripcionPeriodo) {
        this.descripcionPeriodo = descripcionPeriodo;
    }

    public String getCuentaCLABE() {
        return cuentaCLABE;
    }

    public void setCuentaCLABE(String cuentaCLABE) {
        this.cuentaCLABE = cuentaCLABE;
    }

    public BigDecimal getMontoIsrAFavor() {
        return montoIsrAFavor;
    }

    public void setMontoIsrAFavor(BigDecimal montoIsrAFavor) {
        this.montoIsrAFavor = montoIsrAFavor;
    }

    public String getNombreInstitucionBancaria() {
        return nombreInstitucionBancaria;
    }

    public void setNombreInstitucionBancaria(String nombreInstitucionBancaria) {
        this.nombreInstitucionBancaria = nombreInstitucionBancaria;
    }

    public ContribuyenteDTO getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(ContribuyenteDTO contribuyente) {
        this.contribuyente = contribuyente;
    }

    public Integer getFirmaDeclaracion() {
        return firmaDeclaracion;
    }

    public void setFirmaDeclaracion(Integer firmaDeclaracion) {
        this.firmaDeclaracion = firmaDeclaracion;
    }

    public Integer getAceptoPropuestaRecibida() {
        return aceptoPropuestaRecibida;
    }

    public void setAceptoPropuestaRecibida(Integer aceptoPropuestaRecibida) {
        this.aceptoPropuestaRecibida = aceptoPropuestaRecibida;
    }

    public BigDecimal getIngresosAcumulables() {
        return ingresosAcumulables;
    }

    public void setIngresosAcumulables(BigDecimal ingresosAcumulables) {
        this.ingresosAcumulables = ingresosAcumulables;
    }

    public BigDecimal getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(BigDecimal perdidas) {
        this.perdidas = perdidas;
    }

    public BigDecimal getTotalIngresosAcumulables() {
        return totalIngresosAcumulables;
    }

    public void setTotalIngresosAcumulables(BigDecimal totalIngresosAcumulables) {
        this.totalIngresosAcumulables = totalIngresosAcumulables;

    }

    public BigDecimal getDeduccionesPersonales() {
        return deduccionesPersonales;
    }

    public void setDeduccionesPersonales(BigDecimal deduccionesPersonales) {
        this.deduccionesPersonales = deduccionesPersonales;
    }

    public BigDecimal getBaseGravable() {
        return baseGravable;
    }

    public void setBaseGravable(BigDecimal baseGravable) {
        this.baseGravable = baseGravable;
    }

    public BigDecimal getIsrConformeTarifaAnual() {
        return isrConformeTarifaAnual;
    }

    public void setIsrConformeTarifaAnual(BigDecimal isrConformeTarifaAnual) {
        this.isrConformeTarifaAnual = isrConformeTarifaAnual;
    }

    public BigDecimal getSubsidioEmpleo() {
        return subsidioEmpleo;
    }

    public void setSubsidioEmpleo(BigDecimal subsidioEmpleo) {
        this.subsidioEmpleo = subsidioEmpleo;
    }

    public BigDecimal getImptoSobreIngresosAcumulables() {
        return imptoSobreIngresosAcumulables;
    }

    public void setImptoSobreIngresosAcumulables(BigDecimal imptoSobreIngresosAcumulables) {
        this.imptoSobreIngresosAcumulables = imptoSobreIngresosAcumulables;
    }

    public BigDecimal getImptoSobreIngresosNoAcumulables() {
        return imptoSobreIngresosNoAcumulables;
    }

    public void setImptoSobreIngresosNoAcumulables(BigDecimal imptoSobreIngresosNoAcumulables) {
        this.imptoSobreIngresosNoAcumulables = imptoSobreIngresosNoAcumulables;
    }

    public BigDecimal getReduccionesDeISR() {
        return reduccionesDeISR;
    }

    public void setReduccionesDeISR(BigDecimal reduccionesDeISR) {
        this.reduccionesDeISR = reduccionesDeISR;
    }

    public BigDecimal getImptoSobreLaRentaCausado() {
        return imptoSobreLaRentaCausado;
    }

    public void setImptoSobreLaRentaCausado(BigDecimal imptoSobreLaRentaCausado) {
        this.imptoSobreLaRentaCausado = imptoSobreLaRentaCausado;
    }

    public BigDecimal getPagosProvisionales() {
        return pagosProvisionales;
    }

    public void setPagosProvisionales(BigDecimal pagosProvisionales) {
        this.pagosProvisionales = pagosProvisionales;
    }

    public BigDecimal getImptoRetenidoAlcontribuyente() {
        return imptoRetenidoAlcontribuyente;
    }

    public void setImptoRetenidoAlcontribuyente(BigDecimal imptoRetenidoAlcontribuyente) {
        this.imptoRetenidoAlcontribuyente = imptoRetenidoAlcontribuyente;
    }

    public BigDecimal getImptoAcreditablePagadoEnExtranjero() {
        return imptoAcreditablePagadoEnExtranjero;
    }

    public void setImptoAcreditablePagadoEnExtranjero(BigDecimal imptoAcreditablePagadoEnExtranjero) {
        this.imptoAcreditablePagadoEnExtranjero = imptoAcreditablePagadoEnExtranjero;
    }

    public BigDecimal getSectorPrimario() {
        return sectorPrimario;
    }

    public void setSectorPrimario(BigDecimal sectorPrimario) {
        this.sectorPrimario = sectorPrimario;
    }

    public BigDecimal getImptoSobreInteresRealPorRetirosParciales() {
        return imptoSobreInteresRealPorRetirosParciales;
    }

    public void setImptoSobreInteresRealPorRetirosParciales(BigDecimal imptoSobreInteresRealPorRetirosParciales) {
        this.imptoSobreInteresRealPorRetirosParciales = imptoSobreInteresRealPorRetirosParciales;
    }

    public BigDecimal getIsrAFavorDelEjercicio() {
        return isrAFavorDelEjercicio;
    }

    public void setIsrAFavorDelEjercicio(BigDecimal isrAFavorDelEjercicio) {
        this.isrAFavorDelEjercicio = isrAFavorDelEjercicio;
    }

    public BigDecimal getIsrACargoDelEjercicio() {
        return isrACargoDelEjercicio;
    }

    public void setIsrACargoDelEjercicio(BigDecimal isrACargoDelEjercicio) {
        this.isrACargoDelEjercicio = isrACargoDelEjercicio;
    }

    @Override
    public String toString() {
        return "DeterminacionISRDTO{" + "tipoDeclaracion=" + tipoDeclaracion + ", fechaPresentacion=" + fechaPresentacion + ", ejercicio=" + ejercicio + ", periodo=" + periodo + ", descripcionPeriodo=" + descripcionPeriodo + ", cuentaCLABE=" + cuentaCLABE + ", montoIsrAFavor=" + montoIsrAFavor + ", nombreInstitucionBancaria=" + nombreInstitucionBancaria + ", contribuyente=" + contribuyente + ", firmaDeclaracion=" + firmaDeclaracion + ", aceptoPropuestaRecibida=" + aceptoPropuestaRecibida + ", ingresosAcumulables=" + ingresosAcumulables + ", perdidas=" + perdidas + ", totalIngresosAcumulables=" + totalIngresosAcumulables + ", deduccionesPersonales=" + deduccionesPersonales + ", baseGravable=" + baseGravable + ", isrConformeTarifaAnual=" + isrConformeTarifaAnual + ", subsidioEmpleo=" + subsidioEmpleo + ", imptoSobreIngresosAcumulables=" + imptoSobreIngresosAcumulables + ", imptoSobreIngresosNoAcumulables=" + imptoSobreIngresosNoAcumulables + ", reduccionesDeISR=" + reduccionesDeISR + ", imptoSobreLaRentaCausado=" + imptoSobreLaRentaCausado + ", pagosProvisionales=" + pagosProvisionales + ", imptoRetenidoAlcontribuyente=" + imptoRetenidoAlcontribuyente + ", imptoAcreditablePagadoEnExtranjero=" + imptoAcreditablePagadoEnExtranjero + ", sectorPrimario=" + sectorPrimario + ", imptoSobreInteresRealPorRetirosParciales=" + imptoSobreInteresRealPorRetirosParciales + ", isrAFavorDelEjercicio=" + isrAFavorDelEjercicio + ", isrACargoDelEjercicio=" + isrACargoDelEjercicio + '}';
    }

}
