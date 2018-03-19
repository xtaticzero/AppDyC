package mx.gob.sat.siat.dyc.gestionsol.vo.emitirresolucion;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DocumentosActualizados")
public class ResultadosFirmezaVO extends Marshal implements Serializable {
    
    @SuppressWarnings("compatibility:-5192538171573562320")
    private static final long serialVersionUID = -4701991781481311174L;
    
    @XmlElement(name = "DocumentosDyC", type = FirmezaVO.class)
    private List<FirmezaVO> firmeza;
    

    public ResultadosFirmezaVO() {
        super();
    }


    public void setFirmeza(List<FirmezaVO> firmeza) {
        this.firmeza = firmeza;
    }

    public List<FirmezaVO> getFirmeza() {
        return firmeza;
    }
}
