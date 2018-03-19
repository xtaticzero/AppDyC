package mx.gob.sat.siat.dyc.domain.asignartramite;

import java.io.Serializable;

/**
 *
 * @author Gregorio.Serapio
 */
public class DycaConfigDictaminadorDTO implements Serializable {

    private static final long serialVersionUID = -5447774148377927315L;
    
    private Integer idConfiguracion;
    private Integer idServicioAsignar;
    private Integer idTipoTramite;
    private Integer activo;

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public Integer getIdServicioAsignar() {
        return idServicioAsignar;
    }

    public void setIdServicioAsignar(Integer idServicioAsignar) {
        this.idServicioAsignar = idServicioAsignar;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "DycaConfigDictaminadorDTO{" + "idConfiguracion=" + idConfiguracion + ", idServicioAsignar=" + idServicioAsignar + ", idTipoTramite=" + idTipoTramite + ", activo=" + activo + '}';
    }
        
}
