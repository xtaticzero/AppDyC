/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionfisica;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 10/07/2013
 *
 */
@XmlType(propOrder =
         { "nNumeroOperacion", "vFechaCausacion", "tdiepco1", "fperceh1", "importeAcargo", "parteAct", "recargos",
           "multaCorrec", "totContribuc", "creditoSal", "compensacion", "creditoDis", "dieselAuto", "carreteraCuo",
           "otrosEstim", "totAplica", "iImptepagant", "canAcargo", "impPriPar", "impSinPar", "cantiFavor",
           "canPagar" })
public class ConsultarCaratulaDeclaracionPersonaFisica13DTO implements Serializable {

    @SuppressWarnings("compatibility:4153173013432136220")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    private int cStiRpeangg1;
    private int cNPeriodo;
    private int nEjercicio;

    //*****  Salidas  *****
    private BigDecimal nNumeroOperacion;
    private String vFechaCausacion;
    private int tdiepco1;
    private Date fperceh1;
    private BigDecimal importeAcargo;
    private BigDecimal parteAct;
    private BigDecimal recargos;
    private BigDecimal multaCorrec;
    private BigDecimal totContribuc;
    private BigDecimal creditoSal;
    private BigDecimal compensacion;
    private BigDecimal creditoDis;
    private BigDecimal dieselAuto;
    private BigDecimal carreteraCuo;
    private BigDecimal otrosEstim;
    private BigDecimal totAplica;
    private BigDecimal iImptepagant;
    private BigDecimal canAcargo;
    private BigDecimal impPriPar;
    private BigDecimal impSinPar;
    private BigDecimal cantiFavor;
    private BigDecimal canPagar;

    public void setRfceeog1(String rfceeog1) {
        this.rfceeog1 = rfceeog1;
    }

    @XmlTransient
    public String getRfceeog1() {
        return rfceeog1;
    }

    public void setRfceeog2(String rfceeog2) {
        this.rfceeog2 = rfceeog2;
    }

    @XmlTransient
    public String getRfceeog2() {
        return rfceeog2;
    }

    public void setRfceeog3(String rfceeog3) {
        this.rfceeog3 = rfceeog3;
    }

    @XmlTransient
    public String getRfceeog3() {
        return rfceeog3;
    }

    public void setCStiRpeangg1(int cStiRpeangg1) {
        this.cStiRpeangg1 = cStiRpeangg1;
    }

    @XmlTransient
    public int getCStiRpeangg1() {
        return cStiRpeangg1;
    }

    public void setCNPeriodo(int cNPeriodo) {
        this.cNPeriodo = cNPeriodo;
    }

    @XmlTransient
    public int getCNPeriodo() {
        return cNPeriodo;
    }

    public void setNEjercicio(int nEjercicio) {
        this.nEjercicio = nEjercicio;
    }

    @XmlTransient
    public int getNEjercicio() {
        return nEjercicio;
    }

    public void setNNumeroOperacion(BigDecimal nNumeroOperacion) {
        this.nNumeroOperacion = nNumeroOperacion;
    }

    public BigDecimal getNNumeroOperacion() {
        return nNumeroOperacion;
    }

    public void setVFechaCausacion(String vFechaCausacion) {
        this.vFechaCausacion = vFechaCausacion;
    }

    public String getVFechaCausacion() {
        return vFechaCausacion;
    }

    public void setTdiepco1(int tdiepco1) {
        this.tdiepco1 = tdiepco1;
    }

    public int getTdiepco1() {
        return tdiepco1;
    }

    public void setFperceh1(Date fperceh1) {
        this.fperceh1 = new Date(fperceh1.getTime());
    }

    public Date getFperceh1() {
        return new Date(fperceh1.getTime());
    }

    public void setImporteAcargo(BigDecimal importeAcargo) {
        this.importeAcargo = importeAcargo;
    }

    public BigDecimal getImporteAcargo() {
        return importeAcargo;
    }

    public void setParteAct(BigDecimal parteAct) {
        this.parteAct = parteAct;
    }

    public BigDecimal getParteAct() {
        return parteAct;
    }

    public void setRecargos(BigDecimal recargos) {
        this.recargos = recargos;
    }

    public BigDecimal getRecargos() {
        return recargos;
    }

    public void setMultaCorrec(BigDecimal multaCorrec) {
        this.multaCorrec = multaCorrec;
    }

    public BigDecimal getMultaCorrec() {
        return multaCorrec;
    }

    public void setTotContribuc(BigDecimal totContribuc) {
        this.totContribuc = totContribuc;
    }

    public BigDecimal getTotContribuc() {
        return totContribuc;
    }

    public void setCreditoSal(BigDecimal creditoSal) {
        this.creditoSal = creditoSal;
    }

    public BigDecimal getCreditoSal() {
        return creditoSal;
    }

    public void setCompensacion(BigDecimal compensacion) {
        this.compensacion = compensacion;
    }

    public BigDecimal getCompensacion() {
        return compensacion;
    }

    public void setCreditoDis(BigDecimal creditoDis) {
        this.creditoDis = creditoDis;
    }

    public BigDecimal getCreditoDis() {
        return creditoDis;
    }

    public void setDieselAuto(BigDecimal dieselAuto) {
        this.dieselAuto = dieselAuto;
    }

    public BigDecimal getDieselAuto() {
        return dieselAuto;
    }

    public void setCarreteraCuo(BigDecimal carreteraCuo) {
        this.carreteraCuo = carreteraCuo;
    }

    public BigDecimal getCarreteraCuo() {
        return carreteraCuo;
    }

    public void setOtrosEstim(BigDecimal otrosEstim) {
        this.otrosEstim = otrosEstim;
    }

    public BigDecimal getOtrosEstim() {
        return otrosEstim;
    }

    public void setTotAplica(BigDecimal totAplica) {
        this.totAplica = totAplica;
    }

    public BigDecimal getTotAplica() {
        return totAplica;
    }

    public void setIImptepagant(BigDecimal iImptepagant) {
        this.iImptepagant = iImptepagant;
    }

    public BigDecimal getIImptepagant() {
        return iImptepagant;
    }

    public void setCanAcargo(BigDecimal canAcargo) {
        this.canAcargo = canAcargo;
    }

    public BigDecimal getCanAcargo() {
        return canAcargo;
    }

    public void setImpPriPar(BigDecimal impPriPar) {
        this.impPriPar = impPriPar;
    }

    public BigDecimal getImpPriPar() {
        return impPriPar;
    }

    public void setImpSinPar(BigDecimal impSinPar) {
        this.impSinPar = impSinPar;
    }

    public BigDecimal getImpSinPar() {
        return impSinPar;
    }

    public void setCantiFavor(BigDecimal cantiFavor) {
        this.cantiFavor = cantiFavor;
    }

    public BigDecimal getCantiFavor() {
        return cantiFavor;
    }

    public void setCanPagar(BigDecimal canPagar) {
        this.canPagar = canPagar;
    }

    public BigDecimal getCanPagar() {
        return canPagar;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("rfceeog1:");
        sb.append(this.getRfceeog1());
        sb.append(", ");
        sb.append("rfceeog2:");
        sb.append(this.getRfceeog2());
        sb.append(", ");
        sb.append("rfceeog3:");
        sb.append(this.getRfceeog3());
        sb.append(", ");
        sb.append("c_sti_rpeangg1:");
        sb.append(this.getCStiRpeangg1());
        sb.append(", ");
        sb.append("c_n_periodo:");
        sb.append(this.getCNPeriodo());
        sb.append(", ");
        sb.append("n_ejercicio:");
        sb.append(this.getNEjercicio());

        return sb.toString();
    }

}
