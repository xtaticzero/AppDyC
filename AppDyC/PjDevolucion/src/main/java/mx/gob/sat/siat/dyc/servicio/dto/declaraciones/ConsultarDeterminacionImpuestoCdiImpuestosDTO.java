/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dto.declaraciones;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Israel Chavez
 * @since 29/07/2013
 *
 */
@XmlType(propOrder = { "cStiCcloanv1" })
public class ConsultarDeterminacionImpuestoCdiImpuestosDTO implements Serializable {

    @SuppressWarnings("compatibility:-4416878653496089935")
    private static final long serialVersionUID = 1L;

    //*****  Entradas  *****
    private int cStiCilmapv1;

    //*****  Salidas  *****
    private int cStiCcloanv1;

    public void setCStiCilmapv1(int cStiCilmapv1) {
        this.cStiCilmapv1 = cStiCilmapv1;
    }

    @XmlTransient
    public int getCStiCilmapv1() {
        return cStiCilmapv1;
    }

    public void setCStiCcloanv1(int cStiCcloanv1) {
        this.cStiCcloanv1 = cStiCcloanv1;
    }

    public int getCStiCcloanv1() {
        return cStiCcloanv1;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("cStiCilmapv1:");
        sb.append(this.getCStiCilmapv1());

        return sb.toString();
    }

}
