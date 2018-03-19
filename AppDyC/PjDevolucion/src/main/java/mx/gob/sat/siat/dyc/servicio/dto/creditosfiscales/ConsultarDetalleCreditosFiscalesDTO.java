/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.creditosfiscales;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 24/07/2013
 *
 */
 @XmlType(propOrder = { "ncurmee1","ipmapgo1" })
public class ConsultarDetalleCreditosFiscalesDTO implements Serializable{

    @SuppressWarnings("compatibility:7933953186624941045")
    private static final long serialVersionUID = 1L;
    
    //*****  Entradas  *****
    private String rfceeog1;
    private String rfceeog2;
    private String rfceeog3;
    
    //*****  Salidas  *****
    private int ncurmee1;
    private BigDecimal ipmapgo1;

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

    public void setNcurmee1(int ncurmee1) {
        this.ncurmee1 = ncurmee1;
    }

    public int getNcurmee1() {
        return ncurmee1;
    }

    public void setIpmapgo1(BigDecimal ipmapgo1) {
        this.ipmapgo1 = ipmapgo1;
    }

    public BigDecimal getIpmapgo1() {
        return ipmapgo1;
    }
    
    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("rfceeog1:").append(this.getRfceeog1()).append(", ");
        sb.append("rfceeog2:").append(this.getRfceeog2()).append(", ");
        sb.append("rfceeog3:").append(this.getRfceeog3());
                
        return sb.toString();
    }
    
}
