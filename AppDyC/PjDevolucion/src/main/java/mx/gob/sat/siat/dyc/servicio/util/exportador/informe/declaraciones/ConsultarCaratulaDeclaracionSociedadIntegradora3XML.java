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
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
@XmlRootElement
public class ConsultarCaratulaDeclaracionSociedadIntegradora3XML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:3972751782592703177")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO> consultarCaratulaDeclaracionSociedadIntegradora3RenglonDto = new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO>();

    private List<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO> consultarCaratulaDeclaracionSociedadIntegradora3Forma3Dto = new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO>();
    
    private List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultarCaratulaDeclaracionSociedadIntegradora3Reg2002Dto = new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO>();
    
    private List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultarCaratulaDeclaracionSociedadIntegradora3RegDto = new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO>();
    
    public void setConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDto(List<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO> consultarCaratulaDeclaracionSociedadIntegradora3RenglonDto) {
        this.consultarCaratulaDeclaracionSociedadIntegradora3RenglonDto = consultarCaratulaDeclaracionSociedadIntegradora3RenglonDto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionSociedadIntegradora3Renglon")
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO> getConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDto() {
        return consultarCaratulaDeclaracionSociedadIntegradora3RenglonDto;
    }

    public void setConsultarCaratulaDeclaracionSociedadIntegradora3Forma3Dto(List<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO> consultarCaratulaDeclaracionSociedadIntegradora3Forma3Dto) {
        this.consultarCaratulaDeclaracionSociedadIntegradora3Forma3Dto = consultarCaratulaDeclaracionSociedadIntegradora3Forma3Dto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionSociedadIntegradora3Forma3")
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO> getConsultarCaratulaDeclaracionSociedadIntegradora3Forma3Dto() {
        return consultarCaratulaDeclaracionSociedadIntegradora3Forma3Dto;
    }

    public void setConsultarCaratulaDeclaracionSociedadIntegradora3Reg2002Dto(List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultarCaratulaDeclaracionSociedadIntegradora3Reg2002Dto) {
        this.consultarCaratulaDeclaracionSociedadIntegradora3Reg2002Dto = consultarCaratulaDeclaracionSociedadIntegradora3Reg2002Dto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionSociedadIntegradora3Reg2002")
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> getConsultarCaratulaDeclaracionSociedadIntegradora3Reg2002Dto() {
        return consultarCaratulaDeclaracionSociedadIntegradora3Reg2002Dto;
    }

    public void setConsultarCaratulaDeclaracionSociedadIntegradora3RegDto(List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultarCaratulaDeclaracionSociedadIntegradora3RegDto) {
        this.consultarCaratulaDeclaracionSociedadIntegradora3RegDto = consultarCaratulaDeclaracionSociedadIntegradora3RegDto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionSociedadIntegradora3Reg")
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> getConsultarCaratulaDeclaracionSociedadIntegradora3RegDto() {
        return consultarCaratulaDeclaracionSociedadIntegradora3RegDto;
    }

}
