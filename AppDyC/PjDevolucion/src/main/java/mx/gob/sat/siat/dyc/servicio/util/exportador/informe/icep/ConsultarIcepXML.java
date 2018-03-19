package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.icep;
/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.domain.icep.IcepSioUrucple1DTO;
import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;


/**
 *
 * @author Israel Chavez
 * @since 12/09/2013
 *
 */
@XmlRootElement
public class ConsultarIcepXML extends Marshal implements Serializable {


    @SuppressWarnings("compatibility:6482481765155324116")
    private static final long serialVersionUID = 1L;
    
    private IcepSioUrucple1DTO icepSioUrucple1DTO = new IcepSioUrucple1DTO();


    public void setIcepSioUrucple1DTO(IcepSioUrucple1DTO icepSioUrucple1DTO) {
        this.icepSioUrucple1DTO = icepSioUrucple1DTO;
    }
    
    @XmlElement(name="consultarIcepSioUrucple1")
    public IcepSioUrucple1DTO getIcepSioUrucple1DTO() {
        return icepSioUrucple1DTO;
    }
}

