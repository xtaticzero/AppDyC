/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.diot;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;


/**
 *
 * @author Israel Chavez
 * @since 27/09/2013
 *
 */
@XmlRootElement
public class ConsultarSpDiotXML extends Marshal implements Serializable {


    @SuppressWarnings("compatibility:5708565425076274204")
    private static final long serialVersionUID = 1L;
    
    private DiotDTO consultarDiotSp = new DiotDTO();


    public void setConsultarDiotSp(DiotDTO consultarDiotSp) {
        this.consultarDiotSp = consultarDiotSp;
    }
    @XmlElement(name="consultarDiot")
    public DiotDTO getConsultarDiotSp() {
        return consultarDiotSp;
    }
}

