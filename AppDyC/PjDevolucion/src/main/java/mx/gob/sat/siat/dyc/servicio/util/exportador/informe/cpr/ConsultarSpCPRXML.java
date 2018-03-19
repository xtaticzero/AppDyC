/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.cpr;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.domain.cpr.CPRDTO;
import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;


/**
 *
 * @author Israel Chavez
 * @since 25/09/2013
 *
 */
@XmlRootElement
public class ConsultarSpCPRXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:3706941384322882979")
    private static final long serialVersionUID = 1L;
    
    private CPRDTO consultarCprxSp = new CPRDTO();


    public void setConsultarCprxSp(CPRDTO consultarCprxSp) {
        this.consultarCprxSp = consultarCprxSp;
    }
    
    @XmlElement(name="consultarCprx")
    public CPRDTO getConsultarCprxSp() {
        return consultarCprxSp;
    }
}
