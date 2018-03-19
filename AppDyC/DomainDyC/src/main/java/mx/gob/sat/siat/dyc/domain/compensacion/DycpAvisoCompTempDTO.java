/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.compensacion;

import java.io.Serializable;

/**
 * DTO para el catalogo DYCP_AVISOCOMPTEMP
 * @author  Alfredo Ramirez
 * @since   26/06/2014
 */
public class DycpAvisoCompTempDTO implements Serializable {

    @SuppressWarnings("compatibility:-8846697021541364703")
    private static final long serialVersionUID = 1L;

    private String folioAvisoTemp;
    private String folioAvisoNormal;
    private Integer tipoAviso;

    public DycpAvisoCompTempDTO() {
    }

    public DycpAvisoCompTempDTO(String folioAvisoTemp, String folioAvisoNormal, Integer tipoAviso) {
        super();
        this.folioAvisoTemp = folioAvisoTemp;
        this.folioAvisoNormal = folioAvisoNormal;
        this.tipoAviso = tipoAviso;
    }

    public void setFolioAvisoTemp(String folioAvisoTemp) {
        this.folioAvisoTemp = folioAvisoTemp;
    }

    public String getFolioAvisoTemp() {
        return folioAvisoTemp;
    }

    public void setFolioAvisoNormal(String folioAvisoNormal) {
        this.folioAvisoNormal = folioAvisoNormal;
    }

    public String getFolioAvisoNormal() {
        return folioAvisoNormal;
    }

    public void setTipoAviso(Integer tipoAviso) {
        this.tipoAviso = tipoAviso;
    }

    public Integer getTipoAviso() {
        return tipoAviso;
    }
}
