package mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento;

import java.util.Date;

import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;


public class AprobarDocumentoDTO {
    
    private Boolean          banderaEAAD;
    private Integer          firma;
    private Integer          idEstado;
    private Integer          idEstadoReq;
    private Integer          idEstadoResol;
    private Integer          idTipoPlantilla;
    private String           numControlDoc;
    private String           numControlTramite;
    private Integer          numEmpleadoAprobador;
    private Integer          resolAutomatica;
    private Integer          cveAdministracion;
    private Date             fecPresentacion;
    private Integer          idTipoServicio;
    private String           sello;
    private String           cadenaOriginal;
    private String           firmaDigital;
    private SeguimientoDTO   seguimientoDTO;
    private PistaAuditoriaVO rPistaAutitoria;

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstadoResol(Integer idEstadoResol) {
        this.idEstadoResol = idEstadoResol;
    }

    public Integer getIdEstadoResol() {
        return idEstadoResol;
    }

    public void setIdEstadoReq(Integer idEstadoReq) {
        this.idEstadoReq = idEstadoReq;
    }

    public Integer getIdEstadoReq() {
        return idEstadoReq;
    }

    public void setSeguimientoDTO(SeguimientoDTO seguimientoDTO) {
        this.seguimientoDTO = seguimientoDTO;
    }

    public SeguimientoDTO getSeguimientoDTO() {
        return seguimientoDTO;
    }

    public void setRPistaAutitoria(PistaAuditoriaVO rPistaAutitoria) {
        this.rPistaAutitoria = rPistaAutitoria;
    }

    public PistaAuditoriaVO getRPistaAutitoria() {
        return rPistaAutitoria;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setIdTipoPlantilla(Integer idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public Integer getIdTipoPlantilla() {
        return idTipoPlantilla;
    }

    public void setNumControlTramite(String numControlTramite) {
        this.numControlTramite = numControlTramite;
    }

    public String getNumControlTramite() {
        return numControlTramite;
    }

    public void setBanderaEAAD(Boolean banderaEAAD) {
        this.banderaEAAD = banderaEAAD;
    }

    public Boolean getBanderaEAAD() {
        return banderaEAAD;
    }

    public void setFirma(Integer firma) {
        this.firma = firma;
    }

    public Integer getFirma() {
        return firma;
    }

    public void setNumEmpleadoAprobador(Integer numEmpleadoAprobador) {
        this.numEmpleadoAprobador = numEmpleadoAprobador;
    }

    public Integer getNumEmpleadoAprobador() {
        return numEmpleadoAprobador;
    }

    public void setResolAutomatica(Integer resolAutomatica) {
        this.resolAutomatica = resolAutomatica;
    }

    public Integer getResolAutomatica() {
        return resolAutomatica;
    }

    public void setCveAdministracion(Integer cveAdministracion) {
        this.cveAdministracion = cveAdministracion;
    }

    public Integer getCveAdministracion() {
        return cveAdministracion;
    }

    public void setIdTipoServicio(Integer idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Integer getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    public String getSello() {
        return sello;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setFecPresentacion(Date fecPresentacion) {
        this.fecPresentacion = (Date)fecPresentacion.clone();
    }

    public Date getFecPresentacion() {
        if(null != fecPresentacion){
        return (Date)fecPresentacion.clone();
        } else{
            return null;
        }
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }
}
