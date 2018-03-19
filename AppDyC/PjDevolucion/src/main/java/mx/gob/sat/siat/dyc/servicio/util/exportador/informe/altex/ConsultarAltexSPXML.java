/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.altex;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;


/**
 *
 * @author Israel Chavez
 * @since 25/09/2013
 *
 */
@XmlRootElement
public class ConsultarAltexSPXML extends Marshal implements Serializable {


    @SuppressWarnings("compatibility:-834794164309130780")
    private static final long serialVersionUID = 1L;
    
    private SpConsultarAltexDTO spConsultarAltexDTO = new SpConsultarAltexDTO();


    public void setSpConsultarAltexDTO(SpConsultarAltexDTO spConsultarAltexDTO) {
        this.spConsultarAltexDTO = spConsultarAltexDTO;
    }
    @XmlElement(name="consultarAltexSP")
    public SpConsultarAltexDTO getSpConsultarAltexDTO() {
        return spConsultarAltexDTO;
    }
}
