/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;


/**
 * DTO para el catalogo DYCP_AVISOCOMP
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DycpAvisoCompDTO implements Serializable {

    @SuppressWarnings("compatibility:1509890052525980827")
    private static final long serialVersionUID = 1L;

    private DycpCompensacionDTO dycpCompensacionDTO;
    private String folioAviso;
    private DycpAvisoCompDTO dycpAvisoCompNormalDTO;
    private DyccTipoAvisoDTO dyccTipoAvisoDTO;
    private String cadenaOriginal;
    private String selloDigital;
    private String avisoNormalExterno;

    private List<DycpCompensacionDTO> dycpCompensacionDTOList;

    public DycpAvisoCompDTO() {
    }

    public DycpAvisoCompDTO(DyccTipoAvisoDTO dyccTipoAvisoDTO, DycpCompensacionDTO dycpCompensacionDTO) {
        this.dyccTipoAvisoDTO = dyccTipoAvisoDTO;
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public void setDyccTipoAvisoDTO(DyccTipoAvisoDTO dyccTipoAvisoDTO) {
        this.dyccTipoAvisoDTO = dyccTipoAvisoDTO;
    }

    public DyccTipoAvisoDTO getDyccTipoAvisoDTO() {
        return dyccTipoAvisoDTO;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }

    public void setFolioAviso(String folioAviso) {
        this.folioAviso = folioAviso;
    }

    public String getFolioAviso() {
        return folioAviso;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getSelloDigital() {
        return selloDigital;
    }


    public List<DycpCompensacionDTO> getDycpCompensacionDTOList() {
        return dycpCompensacionDTOList;
    }

    public void setDycpCompensacionDTOList(List<DycpCompensacionDTO> dycpCompensacionDTOList) {
        this.dycpCompensacionDTOList = dycpCompensacionDTOList;
    }

    public String getAvisoNormalExterno() {
        return avisoNormalExterno;
    }

    public void setAvisoNormalExterno(String avisoNormalExterno) {
        this.avisoNormalExterno = avisoNormalExterno;
    }

    public DycpAvisoCompDTO getDycpAvisoCompNormalDTO() {
        return dycpAvisoCompNormalDTO;
    }

    public void setDycpAvisoCompNormalDTO(DycpAvisoCompDTO dycpAvisoCompNormalDTO) {
        this.dycpAvisoCompNormalDTO = dycpAvisoCompNormalDTO;
    }
}
