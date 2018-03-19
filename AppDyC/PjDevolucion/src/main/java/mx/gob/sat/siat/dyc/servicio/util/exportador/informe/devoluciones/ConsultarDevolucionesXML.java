/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.devoluciones;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ConsultarDevolucionesDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 26/07/2013
 *
 */
@XmlRootElement
public class ConsultarDevolucionesXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:1069060842392460858")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarDevolucionesDTO> consultarDevolucionesDto = new ArrayList<ConsultarDevolucionesDTO>();
    
    public void setConsultarDevolucionesDto(List<ConsultarDevolucionesDTO> consultarDevolucionesDto) {
        this.consultarDevolucionesDto = consultarDevolucionesDto;
    }

    @XmlElement(name="consultarDevoluciones")
    public List<ConsultarDevolucionesDTO> getConsultarDevolucionesDto() {
        return consultarDevolucionesDto;
    }

}
