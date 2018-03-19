/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.tipotramite;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;


/**
 * DTO para el catalogo DYCC_TIPOTRAMITE
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccTipoTramiteDTO implements Comparable, Serializable {

    @SuppressWarnings("compatibility:4166603225493513637")
    private static final long serialVersionUID = 1L;

    private String claveContable;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idTipoTramite;
    private Integer plazo;
    private String claveComputo; 
    private Integer puntosAsignacion;
    private Boolean requiereCCI;
    private Integer resolAutomatica;
    private List<DycaOrigenTramiteDTO> dycaOrigentramiteList;
    private DyccConceptoDTO dyccConceptoDTO;
    private List<DycaValidaTramiteDTO> dycaValidatramiteList;
    private DyccTipoPlazoDTO dyccTipoPlazoDTO;

    public DyccTipoTramiteDTO() {
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setClaveContable(String claveContable) {
        this.claveContable = claveContable;
    }

    public String getClaveContable() {
        return claveContable;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPuntosAsignacion(Integer puntosAsignacion) {
        this.puntosAsignacion = puntosAsignacion;
    }

    public Integer getPuntosAsignacion() {
        return puntosAsignacion;
    }

    public void setRequiereCCI(Boolean requiereCCI) {
        this.requiereCCI = requiereCCI;
    }

    public Boolean getRequiereCCI() {
        return requiereCCI;
    }

    public void setResolAutomatica(Integer resolAutomatica) {
        this.resolAutomatica = resolAutomatica;
    }

    public Integer getResolAutomatica() {
        return resolAutomatica;
    }

    public void setDycaOrigentramiteList(List<DycaOrigenTramiteDTO> dycaOrigentramiteList) {
        this.dycaOrigentramiteList = dycaOrigentramiteList;
    }

    public List<DycaOrigenTramiteDTO> getDycaOrigentramiteList() {
        return dycaOrigentramiteList;
    }

    public void setDyccConceptoDTO(DyccConceptoDTO dyccConceptoDTO) {
        this.dyccConceptoDTO = dyccConceptoDTO;
    }

    public DyccConceptoDTO getDyccConceptoDTO() {
        return dyccConceptoDTO;
    }

    public void setDycaValidatramiteList(List<DycaValidaTramiteDTO> dycaValidatramiteList) {
        this.dycaValidatramiteList = dycaValidatramiteList;
    }

    public List<DycaValidaTramiteDTO> getDycaValidatramiteList() {
        return dycaValidatramiteList;
    }

    public void setDyccTipoPlazoDTO(DyccTipoPlazoDTO dyccTipoPlazoDTO) {
        this.dyccTipoPlazoDTO = dyccTipoPlazoDTO;
    }

    public DyccTipoPlazoDTO getDyccTipoPlazoDTO() {
        return dyccTipoPlazoDTO;
    }
    
    public void setClaveComputo(String claveComputo) {
        this.claveComputo = claveComputo;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof DyccTipoTramiteDTO){
            if (this.idTipoTramite < ((DyccTipoTramiteDTO)o).getIdTipoTramite()){
                return -1;
                }
            else if (this.idTipoTramite > ((DyccTipoTramiteDTO)o).getIdTipoTramite()){
                return 1;
                }
        return 0;
        }
        return -1;
    }

    public String getClaveComputo() {
        return claveComputo;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return Boolean.TRUE;
        }
        if (!(object instanceof DyccTipoTramiteDTO)) {
            return Boolean.FALSE;
        }
        final DyccTipoTramiteDTO other = (DyccTipoTramiteDTO)object;
        if (!(idTipoTramite == null ? other.idTipoTramite == null : idTipoTramite.equals(other.idTipoTramite))) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + ((idTipoTramite == null) ? 0 : idTipoTramite.hashCode());
        return result;
    }
}
