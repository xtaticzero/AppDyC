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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.servicio.dto.altex.ConsultarContribuyenteAltamenteExportadorDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 25/07/2013
 *
 */
@XmlRootElement
public class ConsultarContribuyenteAltamenteExportadorXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:1231741389038642131")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarContribuyenteAltamenteExportadorDTO> contribuyenteAltamenteExportadorDto = new ArrayList<ConsultarContribuyenteAltamenteExportadorDTO>();
    
    public void setContribuyenteAltamenteExportadorDto(List<ConsultarContribuyenteAltamenteExportadorDTO> contribuyenteAltamenteExportadorDto) {
        this.contribuyenteAltamenteExportadorDto = contribuyenteAltamenteExportadorDto;
    }

    @XmlElement(name="contribuyenteAltamenteExportador")
    public List<ConsultarContribuyenteAltamenteExportadorDTO> getContribuyenteAltamenteExportadorDto() {
        return contribuyenteAltamenteExportadorDto;
    }

}
