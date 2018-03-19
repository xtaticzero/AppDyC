package mx.gob.sat.siat.dyc.domain.asignartramite;

import java.io.Serializable;

/**
 *
 * @author Gregorio.Serapio
 */
public class DycaTipoServicioAsignarDTO implements Serializable {

    private static final long serialVersionUID = 1186115268143996271L;
    private Integer idServicioAsignar;
    private Integer idDictaminador;
    private Integer idTipoServicio;
    private Integer idMonto;
    private Integer activo;

    public Integer getIdServicioAsignar() {
        return idServicioAsignar;
    }

    public void setIdServicioAsignar(Integer idServicioAsignar) {
        this.idServicioAsignar = idServicioAsignar;
    }

    public Integer getIdDictaminador() {
        return idDictaminador;
    }

    public void setIdDictaminador(Integer idDictaminador) {
        this.idDictaminador = idDictaminador;
    }

    public Integer getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(Integer idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Integer getIdMonto() {
        return idMonto;
    }

    public void setIdMonto(Integer idMonto) {
        this.idMonto = idMonto;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "dycaTipoServicioAsignarDTO{" + "idServicioAsignar=" + idServicioAsignar + ", idDictaminador=" + idDictaminador + ", idTipoServicio=" + idTipoServicio + ", idMonto=" + idMonto + ", activo=" + activo + '}';
    }

}
