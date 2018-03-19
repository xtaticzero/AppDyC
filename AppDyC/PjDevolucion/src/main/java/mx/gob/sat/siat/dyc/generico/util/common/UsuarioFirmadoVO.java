package mx.gob.sat.siat.dyc.generico.util.common;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.avisocomp.vo.DatosAvisoFieldVO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.registro.dto.InfoCuentaClabeFieldDTO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


public class UsuarioFirmadoVO implements Serializable {


    @SuppressWarnings("compatibility:2413945589973520597")
    private static final long serialVersionUID = 1L;
    private String cadenaOriginal;
    private String selloDigital;
    private String password;
    private String rfcFirma;
    private String firmaDigital;
    private AccesoUsr accesoUsr;
    private TramiteDTO tramiteDTO;
    private Integer proceso;
    private String path;
    private DatosAvisoFieldVO datosAvisoFieldVO;
    private InfoCuentaClabeFieldDTO infoCuentaClabeFieldDTO;

    public UsuarioFirmadoVO() {
        super();
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public void setRfcFirma(String rfcFirma) {
        this.rfcFirma = rfcFirma;
    }

    public String getRfcFirma() {
        return rfcFirma;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setTramiteDTO(TramiteDTO tramiteDTO) {
        this.tramiteDTO = tramiteDTO;
    }

    public TramiteDTO getTramiteDTO() {
        return tramiteDTO;
    }

    public void setProceso(Integer proceso) {
        this.proceso = proceso;
    }

    public Integer getProceso() {
        return proceso;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setDatosAvisoFieldVO(DatosAvisoFieldVO datosAvisoFieldVO) {
        this.datosAvisoFieldVO = datosAvisoFieldVO;
    }

    public DatosAvisoFieldVO getDatosAvisoFieldVO() {
        return datosAvisoFieldVO;
    }

    public void setInfoCuentaClabeFieldDTO(InfoCuentaClabeFieldDTO infoCuentaClabeFieldDTO) {
        this.infoCuentaClabeFieldDTO = infoCuentaClabeFieldDTO;
    }

    public InfoCuentaClabeFieldDTO getInfoCuentaClabeFieldDTO() {
        return infoCuentaClabeFieldDTO;
    }
    
    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }
    
}
