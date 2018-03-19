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
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
@XmlRootElement
public class ConsultarCaratulaDeclaracionSociedadControladora2AXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:-8463325791244954418")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO> consultarCaratulaDeclaracionSociedadControladora2ARenglonDto = new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO>();

    private List<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO> consultarCaratulaDeclaracionSociedadControladora2AForma2Dto = new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO>();

    private List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultarCaratulaDeclaracionSociedadControladora2AReg2002Dto = new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO>();

    private List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultarCaratulaDeclaracionSociedadControladora2ARegDto = new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO>();
         
    public void setConsultarCaratulaDeclaracionSociedadControladora2ARenglonDto(List<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO> consultarCaratulaDeclaracionSociedadControladora2ARenglonDto) {
        this.consultarCaratulaDeclaracionSociedadControladora2ARenglonDto = consultarCaratulaDeclaracionSociedadControladora2ARenglonDto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionSociedadControladora2ARenglon")
    public List<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO> getConsultarCaratulaDeclaracionSociedadControladora2ARenglonDto() {
        return consultarCaratulaDeclaracionSociedadControladora2ARenglonDto;
    }

    public void setConsultarCaratulaDeclaracionSociedadControladora2AForma2Dto(List<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO> consultarCaratulaDeclaracionSociedadControladora2AForma2Dto) {
        this.consultarCaratulaDeclaracionSociedadControladora2AForma2Dto = consultarCaratulaDeclaracionSociedadControladora2AForma2Dto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionSociedadControladora2AForma2")
    public List<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO> getConsultarCaratulaDeclaracionSociedadControladora2AForma2Dto() {
        return consultarCaratulaDeclaracionSociedadControladora2AForma2Dto;
    }

    public void setConsultarCaratulaDeclaracionSociedadControladora2AReg2002Dto(List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultarCaratulaDeclaracionSociedadControladora2AReg2002Dto) {
        this.consultarCaratulaDeclaracionSociedadControladora2AReg2002Dto = consultarCaratulaDeclaracionSociedadControladora2AReg2002Dto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionSociedadControladora2AReg2002")
    public List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> getConsultarCaratulaDeclaracionSociedadControladora2AReg2002Dto() {
        return consultarCaratulaDeclaracionSociedadControladora2AReg2002Dto;
    }

    public void setConsultarCaratulaDeclaracionSociedadControladora2ARegDto(List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultarCaratulaDeclaracionSociedadControladora2ARegDto) {
        this.consultarCaratulaDeclaracionSociedadControladora2ARegDto = consultarCaratulaDeclaracionSociedadControladora2ARegDto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionSociedadControladora2AReg")
    public List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> getConsultarCaratulaDeclaracionSociedadControladora2ARegDto() {
        return consultarCaratulaDeclaracionSociedadControladora2ARegDto;
    }

}
