/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.compensaciones;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesFechasDTO;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesInformacionDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 24/07/2013
 *
 */
@XmlRootElement
public class ConsultarCompensacionesXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:-8330919194332446097")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarCompensacionesFechasDTO> compensacionesFechasDto = new ArrayList<ConsultarCompensacionesFechasDTO>();
    
    private List<ConsultarCompensacionesInformacionDTO> compensacionesInformacionDto = new ArrayList<ConsultarCompensacionesInformacionDTO>();


    public void setCompensacionesFechasDto(List<ConsultarCompensacionesFechasDTO> compensacionesFechasDto) {
        this.compensacionesFechasDto = compensacionesFechasDto;
    }

    @XmlElement(name="compensacionesFechas")
    public List<ConsultarCompensacionesFechasDTO> getCompensacionesFechasDto() {
        return compensacionesFechasDto;
    }

    public void setCompensacionesInformacionDto(List<ConsultarCompensacionesInformacionDTO> compensacionesInformacionDto) {
        this.compensacionesInformacionDto = compensacionesInformacionDto;
    }

    @XmlElement(name="compensacionesInformacion")
    public List<ConsultarCompensacionesInformacionDTO> getCompensacionesInformacionDto() {
        return compensacionesInformacionDto;
    }

}
