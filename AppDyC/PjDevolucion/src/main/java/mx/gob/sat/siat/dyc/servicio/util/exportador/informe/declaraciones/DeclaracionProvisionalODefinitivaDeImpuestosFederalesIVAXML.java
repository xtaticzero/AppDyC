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
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
@XmlRootElement
public class DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVAXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:-6679386506013008297")
    private static final long serialVersionUID = 1L;
    
    private List<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO> declaracionProvisionalODefinitivaDeImpuestosFederalesIVADto = new ArrayList<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO>();

    public void setDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto(List<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO> declaracionProvisionalODefinitivaDeImpuestosFederalesIVADto) {
        this.declaracionProvisionalODefinitivaDeImpuestosFederalesIVADto = declaracionProvisionalODefinitivaDeImpuestosFederalesIVADto;
    }

    @XmlElement(name = "declaracionProvisionalODefinitivaDeImpuestosFederalesIVA")
    public List<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO> getDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto() {
        return declaracionProvisionalODefinitivaDeImpuestosFederalesIVADto;
    }

}
