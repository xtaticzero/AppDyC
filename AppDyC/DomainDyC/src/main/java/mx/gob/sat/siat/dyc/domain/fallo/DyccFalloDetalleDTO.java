package mx.gob.sat.siat.dyc.domain.fallo;

import java.io.Serializable;


/**
 *
 * DTO para el catalogo DYCC_FALLODETALLE
 *
 * @author Softtek
 *
 */
public class DyccFalloDetalleDTO implements Serializable {

    private static final long serialVersionUID = -4710743818690817777L;

    private Integer idFalloDetalle;
    private String descripcion;
    private DyccFalloColasDTO idFalloColas;

    public DyccFalloDetalleDTO() {
        super();
    }

    public DyccFalloDetalleDTO(Integer idFalloDetalle) {
        super();
        this.idFalloDetalle = idFalloDetalle;
    }

    public DyccFalloDetalleDTO(String descripcion, DyccFalloColasDTO idFalloColas) {
        super();
        this.descripcion = descripcion;
        this.idFalloColas = idFalloColas;
    }

    public DyccFalloDetalleDTO(Integer idFalloDetalle, String descripcion, DyccFalloColasDTO idFalloColas) {
        super();
        this.idFalloDetalle = idFalloDetalle;
        this.descripcion = descripcion;
        this.idFalloColas = idFalloColas;
    }

    public Integer getIdFalloDetalle() {
        return idFalloDetalle;
    }

    public void setIdFalloDetalle(Integer idFalloDetalle) {
        this.idFalloDetalle = idFalloDetalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DyccFalloColasDTO getIdFalloColas() {
        return idFalloColas;
    }

    public void setIdFalloColas(DyccFalloColasDTO idFalloColas) {
        this.idFalloColas = idFalloColas;
    }
}
