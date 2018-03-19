package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;


/**
 * DTO de la tabla DYCT_SOLRFCCONTROL
 * @author  Luis Alberto Dominguez Palomino
 * @since   24/02/2015
 */
public class DyctSolRfcControlDTO implements Serializable {

    @SuppressWarnings("compatibility:-1664106845845654376")
    private static final long serialVersionUID = 1L;
    
    private String rfc;
    private DycpSolicitudDTO dycpSolicitudDTO;
    
    public DyctSolRfcControlDTO() {
    }
    
    public DyctSolRfcControlDTO(DycpSolicitudDTO dycpSolicitudDTO, String rfc) {
        this.dycpSolicitudDTO = dycpSolicitudDTO;
        this.rfc = rfc;
    }
    
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setDycpSolicitudDTO(DycpSolicitudDTO dycpSolicitudDTO) {
        this.dycpSolicitudDTO = dycpSolicitudDTO;
    }

    public DycpSolicitudDTO getDycpSolicitudDTO() {
        return dycpSolicitudDTO;
    }
}
