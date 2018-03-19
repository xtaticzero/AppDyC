/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.pagos;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 10/07/2013
 * @version 0.2 24/03/2014
 * @author J. Isaac Carbajal Bernal
 * DTO para la interfaz ConsultarPagosElectronicos[DWH_DFSD-9]
 */
@XmlType(propOrder =
         { "v_fecha_causacion", "v_fperceh1", "v_iapidne1", "v_iapfdne1", "v_importe_acargo", "v_compensacion",
            "v_importe_afavor", "v_n_numero_operacion", "v_tdiepco1" })
public class ConsultarPagosElectronicosAnioDTO implements Serializable {

    @SuppressWarnings("compatibility:-3858665642172958655")
    private static final long serialVersionUID = 1L;
        
    //*****  Entradas  *****
    private String txtrfc;
    private String txtrfc2;
    private String txtrfc3;
    private int numper;
    private int numeje;
    private int numcon;
    //TODO verificar que el tipo de dato de esta propiedad sea el correcto
    private int cStiCcloanv1;

    //*****  Salidas  *****
    private String vFechaCausacion;
    private Date vFperceh1;
    private int vIapidne1;
    private int vIapfdne1;
    private BigDecimal vImporteAcargo;    
    private BigDecimal vNNumeroOperacion;
    private BigDecimal vCompensacion;    
    private BigDecimal vImporteAfavor;
    private int vTdiepco1;
    
    public void setTxtrfc(String txtrfc) {
        this.txtrfc = txtrfc;
    }

    @XmlTransient
    public String getTxtrfc() {
        return txtrfc;
    }

    public void setTxtrfc2(String txtrfc2) {
        this.txtrfc2 = txtrfc2;
    }

    @XmlTransient
    public String getTxtrfc2() {
        return txtrfc2;
    }

    public void setTxtrfc3(String txtrfc3) {
        this.txtrfc3 = txtrfc3;
    }

    @XmlTransient
    public String getTxtrfc3() {
        return txtrfc3;
    }

    public void setNumper(int numper) {
        this.numper = numper;
    }

    @XmlTransient
    public int getNumper() {
        return numper;
    }

    public void setNumeje(int numeje) {
        this.numeje = numeje;
    }

    @XmlTransient
    public int getNumeje() {
        return numeje;
    }

    public void setNumcon(int numcon) {
        this.numcon = numcon;
    }

    @XmlTransient
    public int getNumcon() {
        return numcon;
    }

    public void setCStiCcloanv1(int cStiCcloanv1) {
        this.cStiCcloanv1 = cStiCcloanv1;
    }
    
    @XmlTransient
    public int getCStiCcloanv1() {
        return cStiCcloanv1;
    }
    
    public void setVFechaCausacion(String vFechaCausacion) {
        this.vFechaCausacion = vFechaCausacion;
    }

    public String getVFechaCausacion() {
        return vFechaCausacion;
    }

    public void setVFperceh1(Date vFperceh1) {
        if(vFperceh1!=null){
            this.vFperceh1 = (Date)vFperceh1.clone();
        } else {
            this.vFperceh1 = null;
            }
    }

    public Date getVFperceh1() {
        if(vFperceh1!= null){
            return (Date)vFperceh1.clone();
        }else{
            return null;
            }
    }

    public void setVIapidne1(int vIapidne1) {
        this.vIapidne1 = vIapidne1;
    }

    public int getVIapidne1() {
        return vIapidne1;
    }

    public void setVIapfdne1(int vIapfdne1) {
        this.vIapfdne1 = vIapfdne1;
    }

    public int getVIapfdne1() {
        return vIapfdne1;
    }

    public void setVImporteAcargo(BigDecimal vImporteAcargo) {
        this.vImporteAcargo = vImporteAcargo;
    }

    public BigDecimal getVImporteAcargo() {
        return vImporteAcargo;
    }

    public void setVNNumeroOperacion(BigDecimal vNNumeroOperacion) {
        this.vNNumeroOperacion = vNNumeroOperacion;
    }

    public BigDecimal getVNNumeroOperacion() {
        return vNNumeroOperacion;
    }
    
    public void setVCompensacion(BigDecimal vCompensacion) {
        this.vCompensacion = vCompensacion;
    }

    public BigDecimal getVCompensacion() {
        return vCompensacion;
    }
    
    public void setVImporteAfavor(BigDecimal vImporteAfavor) {
        this.vImporteAfavor = vImporteAfavor;
    }

    public BigDecimal getVImporteAfavor() {
        return vImporteAfavor;
    }
    
    public void setVTdiepco1(int vTdiepco1) {
        this.vTdiepco1 = vTdiepco1;
    }

    public int getVTdiepco1() {
        return vTdiepco1;
    }

    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("txtrfc:").append(this.getTxtrfc()).append(", ");
        sb.append("txtrfc2:").append(this.getTxtrfc2()).append(", ");
        sb.append("txtrfc3:").append(this.getTxtrfc3()).append(", ");
        sb.append("numper:").append(this.getNumper()).append(", ");
        sb.append("numeje:").append(this.getNumeje()).append(", ");
        sb.append("numcon:").append(this.getNumcon()).append(", ");
        sb.append("c_sti_ccloanv1:").append(this.getCStiCcloanv1());
        
        return sb.toString();
    }

}
