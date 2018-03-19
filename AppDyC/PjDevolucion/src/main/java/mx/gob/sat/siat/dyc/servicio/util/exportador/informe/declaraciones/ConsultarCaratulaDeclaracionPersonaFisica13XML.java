/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.declaraciones;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionfisica.ConsultarCaratulaDeclaracionPersonaFisica13DTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
@XmlRootElement
public class ConsultarCaratulaDeclaracionPersonaFisica13XML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:-1648468132296365001")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarCaratulaDeclaracionPersonaFisica13DTO> consultarCaratulaDeclaracionPersonaFisica13Dto = new ArrayList<ConsultarCaratulaDeclaracionPersonaFisica13DTO>();

    public void setConsultarCaratulaDeclaracionPersonaFisica13Dto(List<ConsultarCaratulaDeclaracionPersonaFisica13DTO> consultarCaratulaDeclaracionPersonaFisica13Dto) {
        this.consultarCaratulaDeclaracionPersonaFisica13Dto = consultarCaratulaDeclaracionPersonaFisica13Dto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionPersonaFisica13")
    public List<ConsultarCaratulaDeclaracionPersonaFisica13DTO> getConsultarCaratulaDeclaracionPersonaFisica13Dto() {
        return consultarCaratulaDeclaracionPersonaFisica13Dto;
    }

}
