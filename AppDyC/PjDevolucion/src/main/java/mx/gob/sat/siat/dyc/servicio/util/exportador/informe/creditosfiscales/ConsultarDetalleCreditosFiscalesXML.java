/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.creditosfiscales;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.servicio.dto.creditosfiscales.ConsultarDetalleCreditosFiscalesDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 24/07/2013
 *
 */
@XmlRootElement
public class ConsultarDetalleCreditosFiscalesXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:8621416860161842096")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarDetalleCreditosFiscalesDTO> creditosFiscalesDto = new ArrayList<ConsultarDetalleCreditosFiscalesDTO>();

    public void setCreditosFiscalesDto(List<ConsultarDetalleCreditosFiscalesDTO> creditosFiscalesDto) {
        this.creditosFiscalesDto = creditosFiscalesDto;
    }

    @XmlElement(name = "creditosFiscales")
    public List<ConsultarDetalleCreditosFiscalesDTO> getCreditosFiscalesDto() {
        return creditosFiscalesDto;
    }

}
