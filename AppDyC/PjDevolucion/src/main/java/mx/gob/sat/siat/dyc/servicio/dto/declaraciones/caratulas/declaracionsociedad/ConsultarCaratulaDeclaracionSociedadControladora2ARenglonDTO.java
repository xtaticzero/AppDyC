/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 10/07/2013
 *
 */
 @XmlType(propOrder = { "cStiRpeangg1" })
public class ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO implements Serializable {
    
    @SuppressWarnings("compatibility:-1499481981585627785")
    private static final long serialVersionUID = 1L;
    
    //*****  Entradas  *****
    private int cStiCcloanv1;
    
    //*****  Salidas  *****
    private int cStiRpeangg1;

    public void setCStiCcloanv1(int cStiCcloanv1) {
        this.cStiCcloanv1 = cStiCcloanv1;
    }

    @XmlTransient
    public int getCStiCcloanv1() {
        return cStiCcloanv1;
    }

    public void setCStiRpeangg1(int cStiRpeangg1) {
        this.cStiRpeangg1 = cStiRpeangg1;
    }

    public int getCStiRpeangg1() {
        return cStiRpeangg1;
    }
    
    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("cStiCcloanv1:" + this.getCStiCcloanv1() + ", ");
                
        return sb.toString();
    }

}
