/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.compensaciones;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 10/07/2013
 *
 */
@XmlType(propOrder = { "n_dec_impidee1","n_dec_impfdee1" })
public class ConsultarCompensacionesFechasDTO implements Serializable {

    @SuppressWarnings("compatibility:6244801753852661189")
    private static final long serialVersionUID = 1L;
    
    //*****  Entradas  *****
    private int cDecCplearv1;
    
    //*****  Salidas  *****
    private int nDecImpidee1;
    private int nDecImpfdee1;

    public void setCDecCplearv1(int cDecCplearv1) {
        this.cDecCplearv1 = cDecCplearv1;
    }

    @XmlTransient
    public int getCDecCplearv1() {
        return cDecCplearv1;
    }

    public void setNDecImpidee1(int nDecImpidee1) {
        this.nDecImpidee1 = nDecImpidee1;
    }

    public int getNDecImpidee1() {
        return nDecImpidee1;
    }

    public void setNDecImpfdee1(int nDecImpfdee1) {
        this.nDecImpfdee1 = nDecImpfdee1;
    }

    public int getNDecImpfdee1() {
        return nDecImpfdee1;
    }
    
    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("cDecCplearv1:" + this.getCDecCplearv1() + ", ");
                
        return sb.toString();
    }
    
}
