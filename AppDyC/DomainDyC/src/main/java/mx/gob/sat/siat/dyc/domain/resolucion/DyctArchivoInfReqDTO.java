package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;


/**
 * DTO de la tabla DYCT_ARCHIVOINFREQ
 * @author  Luis Alberto Dominguez Palomino
 * @since   20/05/2015
 */
public class DyctArchivoInfReqDTO implements Serializable {

    @SuppressWarnings("compatibility:-7394453553395354268")
    private static final long serialVersionUID = 1L;
    private Integer idArchivoInfReq;
    private String nombreArchivo;
    private String url;
    private DyctInfoRequeridaDTO dyctInfoRequeridaDTO;
    
    public DyctArchivoInfReqDTO() {
        super();
    }
    
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setIdArchivoInfReq(Integer idArchivoInfReq) {
        this.idArchivoInfReq = idArchivoInfReq;
    }

    public Integer getIdArchivoInfReq() {
        return idArchivoInfReq;
    }

    public void setDyctInfoRequeridaDTO(DyctInfoRequeridaDTO dyctInfoRequeridaDTO) {
        this.dyctInfoRequeridaDTO = dyctInfoRequeridaDTO;
    }

    public DyctInfoRequeridaDTO getDyctInfoRequeridaDTO() {
        return dyctInfoRequeridaDTO;
    }
}
