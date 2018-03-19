package mx.gob.sat.siat.dyc.registro.dto;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;

import org.primefaces.model.UploadedFile;


public class InfoCuentaClabeFieldDTO implements Serializable {


    @SuppressWarnings("compatibility:4719930891408929957")
    private static final long serialVersionUID = 1L;
    private DyctCuentaBancoDTO dyctCuentaBanco;
    private String opcionMani;
    private DyctArchivoDTO dyctArchivoDTO;
    private String rfc;
    private String adminD;
    private String nomCorrecto;
    private transient UploadedFile file;
    
    public InfoCuentaClabeFieldDTO() {
        super();
    }

    public void setDyctCuentaBanco(DyctCuentaBancoDTO dyctCuentaBanco) {
        this.dyctCuentaBanco = dyctCuentaBanco;
    }

    public DyctCuentaBancoDTO getDyctCuentaBanco() {
        return dyctCuentaBanco;
    }

    public void setOpcionMani(String opcionMani) {
        this.opcionMani = opcionMani;
    }

    public String getOpcionMani() {
        return opcionMani;
    }

    public void setDyctArchivoDTO(DyctArchivoDTO dyctArchivoDTO) {
        this.dyctArchivoDTO = dyctArchivoDTO;
    }

    public DyctArchivoDTO getDyctArchivoDTO() {
        return dyctArchivoDTO;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setAdminD(String adminD) {
        this.adminD = adminD;
    }

    public String getAdminD() {
        return adminD;
    }

    public void setNomCorrecto(String nomCorrecto) {
        this.nomCorrecto = nomCorrecto;
    }

    public String getNomCorrecto() {
        return nomCorrecto;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }
}
