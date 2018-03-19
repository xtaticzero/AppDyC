/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.dictamenes;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.servicio.dto.dictamenes.ConsultarDictamenesDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 26/07/2013
 *
 */
@XmlRootElement
public class ConsultarDictamenesXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:-3612704393237739927")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarDictamenesDTO> consultarDictamenesDto = new ArrayList<ConsultarDictamenesDTO>();

    public void setConsultarDictamenesDto(List<ConsultarDictamenesDTO> consultarDictamenesDto) {
        this.consultarDictamenesDto = consultarDictamenesDto;
    }

    @XmlElement(name = "consultarDictamenes")
    public List<ConsultarDictamenesDTO> getConsultarDictamenesDto() {
        return consultarDictamenesDto;
    }

}
