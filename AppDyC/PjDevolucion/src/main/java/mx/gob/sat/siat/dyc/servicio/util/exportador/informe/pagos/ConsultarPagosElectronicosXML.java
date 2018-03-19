/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.pagos;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.ConsultarPagosElectronicosAnioDTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf01DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf02DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoORADTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 22/07/2013
 *
 */
@XmlRootElement
public class ConsultarPagosElectronicosXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:144556128344720108")
    private static final long serialVersionUID = 1L;
    
    private List<ConsultarPagosElectronicosAnioDTO> consultarPagosElectronicosAnioDto = new ArrayList<ConsultarPagosElectronicosAnioDTO>();
    
    private List<InformacionDePagoInf01DTO> informacionDePagoInf01Dto = new ArrayList<InformacionDePagoInf01DTO>();

    private List<InformacionDePagoInf02DTO> informacionDePagoInf02Dto = new ArrayList<InformacionDePagoInf02DTO>();
    
    private List<InformacionDePagoORADTO> informacionDePagoORADto = new ArrayList<InformacionDePagoORADTO>();
    
    @XmlElement(name="consultarPagosElectronicosAnio")
    public List<ConsultarPagosElectronicosAnioDTO> getConsultarPagosElectronicosAnioDto() {
        return consultarPagosElectronicosAnioDto;
    }
    
    public void setInformacionDePagoInf01Dto(List<InformacionDePagoInf01DTO> informacionDePagoInf01Dto) {
        this.informacionDePagoInf01Dto = informacionDePagoInf01Dto;
    }

    @XmlElement(name="informacionDePagoInf01")
    public List<InformacionDePagoInf01DTO> getInformacionDePagoInf01Dto() {
        return informacionDePagoInf01Dto;
    }
    
    public void setInformacionDePagoInf02Dto(List<InformacionDePagoInf02DTO> informacionDePagoInf02Dto) {
        this.informacionDePagoInf02Dto = informacionDePagoInf02Dto;
    }

    @XmlElement(name="informacionDePagoInf02")
    public List<InformacionDePagoInf02DTO> getInformacionDePagoInf02Dto() {
        return informacionDePagoInf02Dto;
    }
    
    public void setConsultarPagosElectronicosAnioDto(List<ConsultarPagosElectronicosAnioDTO> consultarPagosElectronicosAnioDto) {
        this.consultarPagosElectronicosAnioDto = consultarPagosElectronicosAnioDto;
    }

    public void setInformacionDePagoORADto(List<InformacionDePagoORADTO> informacionDePagoORADto) {
        this.informacionDePagoORADto = informacionDePagoORADto;
    }

    @XmlElement(name="informacionDePagoORA")
    public List<InformacionDePagoORADTO> getInformacionDePagoORADto() {
        return informacionDePagoORADto;
    }
    
}
