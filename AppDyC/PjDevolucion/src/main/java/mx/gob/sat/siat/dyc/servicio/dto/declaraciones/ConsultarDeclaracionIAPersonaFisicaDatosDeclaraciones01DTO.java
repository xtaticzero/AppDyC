package mx.gob.sat.siat.dyc.servicio.dto.declaraciones;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;


public class ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO implements Serializable{
    
    @SuppressWarnings("compatibility:-1458093601766042979")
    private static final long serialVersionUID = 1L;

    public ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO() {
        super();
    }
    
    /**Entradas**/
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    private BigDecimal nNumeroOperacion;
    
    /**I/O**/    
    private int cNPeriodo;
    private int nEjercicio;
    
    /**Salidas**/
    private int tdiepco1;
    private Date fperceh1;
    private BigDecimal i121026;
    private BigDecimal i121003;
    private BigDecimal i121004;
    private BigDecimal i121006;
    private BigDecimal a121102;
    private BigDecimal a121101;
    private BigDecimal i121039;
    private BigDecimal i121007;
    private BigDecimal i121008;
    private BigDecimal i121009;
    private BigDecimal i121012;
    private BigDecimal i121013;
    private BigDecimal i121014;
    private BigDecimal i121015;
    private BigDecimal i121860;
    private BigDecimal i121017;
    private BigDecimal i121016;
    private BigDecimal i121019;
    private BigDecimal i111923;
    private BigDecimal a121048;
    private BigDecimal a121049;
    private BigDecimal i121021;


    public void setRfceeog1(String rfceeog1) {
        this.rfceeog1 = rfceeog1;
    }

    public String getRfceeog1() {
        return rfceeog1;
    }

    public void setCNPeriodo(int cNPeriodo) {
        this.cNPeriodo = cNPeriodo;
    }

    public int getCNPeriodo() {
        return cNPeriodo;
    }

    public void setNEjercicio(int nEjercicio) {
        this.nEjercicio = nEjercicio;
    }

    public int getNEjercicio() {
        return nEjercicio;
    }

    public void setNNumeroOperacion(BigDecimal nNumeroOperacion) {
        this.nNumeroOperacion = nNumeroOperacion;
    }

    public BigDecimal getNNumeroOperacion() {
        return nNumeroOperacion;
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

    public void setI121026(BigDecimal i121026) {
        this.i121026 = i121026;
    }

    public BigDecimal getI121026() {
        return i121026;
    }

    public void setI121003(BigDecimal i121003) {
        this.i121003 = i121003;
    }

    public BigDecimal getI121003() {
        return i121003;
    }

    public void setI121004(BigDecimal i121004) {
        this.i121004 = i121004;
    }

    public BigDecimal getI121004() {
        return i121004;
    }

    public void setI121006(BigDecimal i121006) {
        this.i121006 = i121006;
    }

    public BigDecimal getI121006() {
        return i121006;
    }

    public void setA121102(BigDecimal a121102) {
        this.a121102 = a121102;
    }

    public BigDecimal getA121102() {
        return a121102;
    }

    public void setA121101(BigDecimal a121101) {
        this.a121101 = a121101;
    }

    public BigDecimal getA121101() {
        return a121101;
    }

    public void setI121039(BigDecimal i121039) {
        this.i121039 = i121039;
    }

    public BigDecimal getI121039() {
        return i121039;
    }

    public void setI121007(BigDecimal i121007) {
        this.i121007 = i121007;
    }

    public BigDecimal getI121007() {
        return i121007;
    }

    public void setI121008(BigDecimal i121008) {
        this.i121008 = i121008;
    }

    public BigDecimal getI121008() {
        return i121008;
    }

    public void setI121009(BigDecimal i121009) {
        this.i121009 = i121009;
    }

    public BigDecimal getI121009() {
        return i121009;
    }

    public void setI121012(BigDecimal i121012) {
        this.i121012 = i121012;
    }

    public BigDecimal getI121012() {
        return i121012;
    }

    public void setI121013(BigDecimal i121013) {
        this.i121013 = i121013;
    }

    public BigDecimal getI121013() {
        return i121013;
    }

    public void setI121014(BigDecimal i121014) {
        this.i121014 = i121014;
    }

    public BigDecimal getI121014() {
        return i121014;
    }

    public void setI121015(BigDecimal i121015) {
        this.i121015 = i121015;
    }

    public BigDecimal getI121015() {
        return i121015;
    }

    public void setI121860(BigDecimal i121860) {
        this.i121860 = i121860;
    }

    public BigDecimal getI121860() {
        return i121860;
    }

    public void setI121017(BigDecimal i121017) {
        this.i121017 = i121017;
    }

    public BigDecimal getI121017() {
        return i121017;
    }

    public void setI121016(BigDecimal i121016) {
        this.i121016 = i121016;
    }

    public BigDecimal getI121016() {
        return i121016;
    }

    public void setI121019(BigDecimal i121019) {
        this.i121019 = i121019;
    }

    public BigDecimal getI121019() {
        return i121019;
    }

    public void setI111923(BigDecimal i111923) {
        this.i111923 = i111923;
    }

    public BigDecimal getI111923() {
        return i111923;
    }

    public void setA121048(BigDecimal a121048) {
        this.a121048 = a121048;
    }

    public BigDecimal getA121048() {
        return a121048;
    }

    public void setA121049(BigDecimal a121049) {
        this.a121049 = a121049;
    }

    public BigDecimal getA121049() {
        return a121049;
    }

    public void setI121021(BigDecimal i121021) {
        this.i121021 = i121021;
    }

    public BigDecimal getI121021() {
        return i121021;
    }

    public void setRfceeog2(String rfceeog2) {
        this.rfceeog2 = rfceeog2;
    }

    public String getRfceeog2() {
        return rfceeog2;
    }

    public void setRfceeog3(String rfceeog3) {
        this.rfceeog3 = rfceeog3;
    }

    public String getRfceeog3() {
        return rfceeog3;
    }
    
    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append(ConstantesDyC.C_STI_CCLOANV1);
        sb.append(this.getRfceeog1());
        sb.append(", ");
        
        sb.append(ConstantesDyC.C_STI_CCLOANV1);
        sb.append(this.getRfceeog2());
        sb.append(", ");
        
        sb.append(ConstantesDyC.C_STI_CCLOANV1);
        sb.append(this.getRfceeog3());
        sb.append(", ");
        
        sb.append(ConstantesDyC.C_STI_CCLOANV1);
        sb.append(this.getCNPeriodo());
        sb.append(", ");
        
        sb.append(ConstantesDyC.C_STI_CCLOANV1);
        sb.append(this.getNEjercicio());
                       
        return sb.toString();
    }
}
