package mx.gob.sat.siat.dyc.servicio.dto.declaraciones;

import java.io.Serializable;

import java.math.BigDecimal;


public class ConsultarDeclaracionIEPS4DTO implements Serializable {
    
    @SuppressWarnings("compatibility:-6921320791353539390")
    private static final long serialVersionUID = 1L;

    public ConsultarDeclaracionIEPS4DTO() {
        super();
    }
    
    /**Entradas**/
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    private int iapidne1;
    private int iapfdne1;
    private int cDecCplearv1;
    
    /**Salidas**/
    private String ldleacv1;
    private BigDecimal vP2141906;
    private BigDecimal vP2141907;
    private BigDecimal vP2141908;
    private BigDecimal vP2141917;
    private BigDecimal vP2141918;
    private BigDecimal vP2141910;
    private BigDecimal vP2141911;
    private BigDecimal vP2141521;
    private BigDecimal vP2141912;
    private BigDecimal vP2141919;
    private BigDecimal vP2141920;
    private BigDecimal vP2141914;
    private BigDecimal vP2141915;
    private BigDecimal vP2141916;

    public void setRfceeog1(String rfceeog1) {
        this.rfceeog1 = rfceeog1;
    }

    public String getRfceeog1() {
        return rfceeog1;
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

    public void setIapidne1(int iapidne1) {
        this.iapidne1 = iapidne1;
    }

    public int getIapidne1() {
        return iapidne1;
    }

    public void setIapfdne1(int iapfdne1) {
        this.iapfdne1 = iapfdne1;
    }

    public int getIapfdne1() {
        return iapfdne1;
    }

    public void setCDecCplearv1(int cDecCplearv1) {
        this.cDecCplearv1 = cDecCplearv1;
    }

    public int getCDecCplearv1() {
        return cDecCplearv1;
    }

    public void setLdleacv1(String ldleacv1) {
        this.ldleacv1 = ldleacv1;
    }

    public String getLdleacv1() {
        return ldleacv1;
    }

    public void setVP2141906(BigDecimal vP2141906) {
        this.vP2141906 = vP2141906;
    }

    public BigDecimal getVP2141906() {
        return vP2141906;
    }

    public void setVP2141907(BigDecimal vP2141907) {
        this.vP2141907 = vP2141907;
    }

    public BigDecimal getVP2141907() {
        return vP2141907;
    }

    public void setVP2141908(BigDecimal vP2141908) {
        this.vP2141908 = vP2141908;
    }

    public BigDecimal getVP2141908() {
        return vP2141908;
    }

    public void setVP2141917(BigDecimal vP2141917) {
        this.vP2141917 = vP2141917;
    }

    public BigDecimal getVP2141917() {
        return vP2141917;
    }

    public void setVP2141918(BigDecimal vP2141918) {
        this.vP2141918 = vP2141918;
    }

    public BigDecimal getVP2141918() {
        return vP2141918;
    }

    public void setVP2141910(BigDecimal vP2141910) {
        this.vP2141910 = vP2141910;
    }

    public BigDecimal getVP2141910() {
        return vP2141910;
    }

    public void setVP2141911(BigDecimal vP2141911) {
        this.vP2141911 = vP2141911;
    }

    public BigDecimal getVP2141911() {
        return vP2141911;
    }

    public void setVP2141521(BigDecimal vP2141521) {
        this.vP2141521 = vP2141521;
    }

    public BigDecimal getVP2141521() {
        return vP2141521;
    }

    public void setVP2141912(BigDecimal vP2141912) {
        this.vP2141912 = vP2141912;
    }

    public BigDecimal getVP2141912() {
        return vP2141912;
    }

    public void setVP2141919(BigDecimal vP2141919) {
        this.vP2141919 = vP2141919;
    }

    public BigDecimal getVP2141919() {
        return vP2141919;
    }

    public void setVP2141920(BigDecimal vP2141920) {
        this.vP2141920 = vP2141920;
    }

    public BigDecimal getVP2141920() {
        return vP2141920;
    }

    public void setVP2141914(BigDecimal vP2141914) {
        this.vP2141914 = vP2141914;
    }

    public BigDecimal getVP2141914() {
        return vP2141914;
    }

    public void setVP2141915(BigDecimal vP2141915) {
        this.vP2141915 = vP2141915;
    }

    public BigDecimal getVP2141915() {
        return vP2141915;
    }

    public void setVP2141916(BigDecimal vP2141916) {
        this.vP2141916 = vP2141916;
    }

    public BigDecimal getVP2141916() {
        return vP2141916;
    }
    
    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("rfceeog1:");
        sb.append(this.getRfceeog1());
        
        sb.append("rfceeog2:");
        sb.append(this.getRfceeog2());
        
        sb.append("rfceeog3:");
        sb.append(this.getRfceeog3());
        
        sb.append("iapidne1:");
        sb.append(this.getIapidne1());
        
        sb.append("iapfdne1:");
        sb.append(this.getIapfdne1());
        
        sb.append("cDecCplearv1:");
        sb.append(this.getCDecCplearv1());
        
        
        return sb.toString();
    }
}
