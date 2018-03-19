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
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2Forma2ADTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RegistroDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RenglonDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
@XmlRootElement
public class ConsultarCaratulaDeclaracionMoral2XML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:7971073188384216814")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarCaratulaDeclaracionMoral2Forma2ADTO> consultarCaratulaDeclaracionMoral2Forma2ADto = new ArrayList<ConsultarCaratulaDeclaracionMoral2Forma2ADTO>();
    
    private List<ConsultarCaratulaDeclaracionMoral2RenglonDTO> consultarCaratulaDeclaracionMoral2RenglonDto = new ArrayList<ConsultarCaratulaDeclaracionMoral2RenglonDTO>();
    
    private List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultarCaratulaDeclaracionMoral2Registro2002Dto = new ArrayList<ConsultarCaratulaDeclaracionMoral2RegistroDTO>();

    private List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultarCaratulaDeclaracionMoral2RegistroDto = new ArrayList<ConsultarCaratulaDeclaracionMoral2RegistroDTO>();


    public void setConsultarCaratulaDeclaracionMoral2Forma2ADto(List<ConsultarCaratulaDeclaracionMoral2Forma2ADTO> consultarCaratulaDeclaracionMoral2Forma2ADto) {
        this.consultarCaratulaDeclaracionMoral2Forma2ADto = consultarCaratulaDeclaracionMoral2Forma2ADto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionMoral2Forma2A")
    public List<ConsultarCaratulaDeclaracionMoral2Forma2ADTO> getConsultarCaratulaDeclaracionMoral2Forma2ADto() {
        return consultarCaratulaDeclaracionMoral2Forma2ADto;
    }

    public void setConsultarCaratulaDeclaracionMoral2RenglonDto(List<ConsultarCaratulaDeclaracionMoral2RenglonDTO> consultarCaratulaDeclaracionMoral2RenglonDto) {
        this.consultarCaratulaDeclaracionMoral2RenglonDto = consultarCaratulaDeclaracionMoral2RenglonDto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionMoral2Renglon")
    public List<ConsultarCaratulaDeclaracionMoral2RenglonDTO> getConsultarCaratulaDeclaracionMoral2RenglonDto() {
        return consultarCaratulaDeclaracionMoral2RenglonDto;
    }

    public void setConsultarCaratulaDeclaracionMoral2Registro2002Dto(List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultarCaratulaDeclaracionMoral2Registro2002Dto) {
        this.consultarCaratulaDeclaracionMoral2Registro2002Dto = consultarCaratulaDeclaracionMoral2Registro2002Dto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionMoral2Registro2002")
    public List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> getConsultarCaratulaDeclaracionMoral2Registro2002Dto() {
        return consultarCaratulaDeclaracionMoral2Registro2002Dto;
    }

    public void setConsultarCaratulaDeclaracionMoral2RegistroDto(List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultarCaratulaDeclaracionMoral2RegistroDto) {
        this.consultarCaratulaDeclaracionMoral2RegistroDto = consultarCaratulaDeclaracionMoral2RegistroDto;
    }

    @XmlElement(name = "consultarCaratulaDeclaracionMoral2Registro")
    public List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> getConsultarCaratulaDeclaracionMoral2RegistroDto() {
        return consultarCaratulaDeclaracionMoral2RegistroDto;
    }

}
