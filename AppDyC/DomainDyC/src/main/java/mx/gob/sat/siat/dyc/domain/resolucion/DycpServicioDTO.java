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

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;


/**
 * DTO de la tabla DYCP_SERVICIO
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DycpServicioDTO implements Serializable {


    @SuppressWarnings("compatibility:-2072255982442249130")
    private static final long serialVersionUID = 1L;

    private String numControl;
    private DyccDictaminadorDTO dyccDictaminadorDTO;
    private DycaOrigenTramiteDTO dycaOrigenTramiteDTO;
    private String rfcContribuyente;
    private Integer claveAdm;

    private DyctContribuyenteDTO dyctContribuyenteDTO;
    private DyctExpedienteDTO dyctExpedienteDTO;
    private DycpCompensacionDTO dycpCompensacionDTO;
    private DycpSolicitudDTO dycpSolicitudDTO;

    private List<DyctDeclaracionDTO> dyctDeclaracionList;
    private String boid;

    private AdmcUnidadAdmvaDTO dyccUnidadAdmvaDTO;

    public DycpServicioDTO() {
    }

    public DycpServicioDTO(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public void setDyctContribuyenteDTO(DyctContribuyenteDTO dyctContribuyenteDTO) {
        this.dyctContribuyenteDTO = dyctContribuyenteDTO;
    }

    public DyctContribuyenteDTO getDyctContribuyenteDTO() {
        return dyctContribuyenteDTO;
    }

    public void setDyctExpedienteDTO(DyctExpedienteDTO dyctExpedienteDTO) {
        this.dyctExpedienteDTO = dyctExpedienteDTO;
    }

    public DyctExpedienteDTO getDyctExpedienteDTO() {
        return dyctExpedienteDTO;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }

    public void setDyccDictaminadorDTO(DyccDictaminadorDTO dyccDictaminadorDTO) {
        this.dyccDictaminadorDTO = dyccDictaminadorDTO;
    }

    public DyccDictaminadorDTO getDyccDictaminadorDTO() {
        return dyccDictaminadorDTO;
    }

    public void setDycaOrigenTramiteDTO(DycaOrigenTramiteDTO dycaOrigenTramiteDTO) {
        this.dycaOrigenTramiteDTO = dycaOrigenTramiteDTO;
    }

    public DycaOrigenTramiteDTO getDycaOrigenTramiteDTO() {
        return dycaOrigenTramiteDTO;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setDycpSolicitudDTO(DycpSolicitudDTO dycpSolicitudDTO) {
        this.dycpSolicitudDTO = dycpSolicitudDTO;
    }

    public DycpSolicitudDTO getDycpSolicitudDTO() {
        return dycpSolicitudDTO;
    }

    public List<DyctDeclaracionDTO> getDyctDeclaracionList() {
        return dyctDeclaracionList;
    }

    public void setDyctDeclaracionList(List<DyctDeclaracionDTO> dyctDeclaracionList) {
        this.dyctDeclaracionList = dyctDeclaracionList;
    }

    public void setBoid(String boid) {
        this.boid = boid;
    }

    public String getBoid() {
        return boid;
    }

    public AdmcUnidadAdmvaDTO getDyccUnidadAdmvaDTO() {
        return dyccUnidadAdmvaDTO;
    }

    public void setDyccUnidadAdmvaDTO(AdmcUnidadAdmvaDTO dyccUnidadAdmvaDTO) {
        this.dyccUnidadAdmvaDTO = dyccUnidadAdmvaDTO;
    }

    //método que abrevía el obtener el tipoServicio de un tramite

    public DyccTipoServicioDTO getDyccTipoServicioDTO() {
        if (this.getDycaOrigenTramiteDTO() != null && this.getDycaOrigenTramiteDTO().getDycaServOrigenDTO() != null) {
            return this.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO();
        }

        return null;
    }

    @Override
    public String toString() {
        return "DycpServicioDTO{" + "numControl=" + numControl + ", dyccDictaminadorDTO=" + dyccDictaminadorDTO +
            ", dycaOrigenTramiteDTO=" + dycaOrigenTramiteDTO + ", rfcContribuyente=" + rfcContribuyente +
            ", claveAdm=" + claveAdm + ", dyctContribuyenteDTO=" + dyctContribuyenteDTO + ", dyctExpedienteDTO=" +
            dyctExpedienteDTO + ", dycpCompensacionDTO=" + dycpCompensacionDTO + ", dycpSolicitudDTO=" +
            dycpSolicitudDTO + ", dyctDeclaracionList=" + dyctDeclaracionList + ", boid=" + boid +
            ", dyccUnidadAdmvaDTO=" + dyccUnidadAdmvaDTO + '}';
    }

}
