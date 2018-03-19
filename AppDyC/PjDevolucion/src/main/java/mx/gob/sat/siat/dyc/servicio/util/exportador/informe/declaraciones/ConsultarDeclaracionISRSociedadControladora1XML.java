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
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora1DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora2DTO;


/**
 *
 * @author  Alfredo Ramirez
 * @since   06/08/2013
 *
 */
@XmlRootElement
public class ConsultarDeclaracionISRSociedadControladora1XML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:7614907607974361117")
    private static final long serialVersionUID = 1L;

    private List<ConsultarDeclaracionISRSociedadControladora1DTO> consultarDeclaracionISRSociedadControladora1Dto =
        new ArrayList<ConsultarDeclaracionISRSociedadControladora1DTO>();

    private List<ConsultarDeclaracionISRSociedadControladora2DTO> consultarDeclaracionISRSociedadControladora2Dto =
        new ArrayList<ConsultarDeclaracionISRSociedadControladora2DTO>();

    public void setConsultarDeclaracionISRSociedadControladora1Dto(List<ConsultarDeclaracionISRSociedadControladora1DTO> consultarDeclaracionISRSociedadControladora1Dto) {
        this.consultarDeclaracionISRSociedadControladora1Dto = consultarDeclaracionISRSociedadControladora1Dto;
    }

    @XmlElement(name = "consultarDeclaracionISRSociedadControladora1")
    public List<ConsultarDeclaracionISRSociedadControladora1DTO> getConsultarDeclaracionISRSociedadControladora1Dto() {
        return consultarDeclaracionISRSociedadControladora1Dto;
    }

    public void setConsultarDeclaracionISRSociedadControladora2Dto(List<ConsultarDeclaracionISRSociedadControladora2DTO> consultarDeclaracionISRSociedadControladora2Dto) {
        this.consultarDeclaracionISRSociedadControladora2Dto = consultarDeclaracionISRSociedadControladora2Dto;
    }

    @XmlElement(name = "consultarDeclaracionISRSociedadControladora2")
    public List<ConsultarDeclaracionISRSociedadControladora2DTO> getConsultarDeclaracionISRSociedadControladora2Dto() {
        return consultarDeclaracionISRSociedadControladora2Dto;
    }

}
