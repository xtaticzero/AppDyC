package mx.gob.sat.siat.dyc.domain.fallo;

import java.io.Serializable;


/**
 *
 * DTO para el catalogo DYCC_FALLOCOLAS
 *
 * @author Softtek
 *
 */
public class DyccFalloColasDTO implements Serializable {

    private static final long serialVersionUID = -3092547223167302497L;

    private Integer idFalloColas;
    private String descripcion;

    public DyccFalloColasDTO() {
        super();
    }

    public DyccFalloColasDTO(Integer idFalloColas) {
        super();
        this.idFalloColas = idFalloColas;
    }

    public DyccFalloColasDTO(Integer idFalloColas, String descripcion) {
        super();
        this.idFalloColas = idFalloColas;
        this.descripcion = descripcion;
    }

    public Integer getIdFalloColas() {
        return idFalloColas;
    }

    public void setIdFalloColas(Integer idFalloColas) {
        this.idFalloColas = idFalloColas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
