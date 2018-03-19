/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoTipoResDTO;


/**
 * DTO para el catalogo DYCA_RESOLMOTIVO
 * @author  Alfredo Ramirez
 * @since   29/04/2014
 */
public class DycaResolMotivoDTO implements Serializable {


    @SuppressWarnings("compatibility:6554837065650237418")
    private static final long serialVersionUID = 1L;
    
    private DyctResolucionDTO dyctResolucionDTO;
    private DyccMotivoTipoResDTO dyccMotivoTipoResDTO;
    private Integer idResolMotivo;
    private String descripcionOtros;
    private Date fechaFin;

    public DycaResolMotivoDTO() {
    }

    public void setDyctResolucionDTO(DyctResolucionDTO dyctResolucionDTO) {
        this.dyctResolucionDTO = dyctResolucionDTO;
    }

    public DyctResolucionDTO getDyctResolucionDTO() {
        return dyctResolucionDTO;
    }

    public void setDyccMotivoTipoResDTO(DyccMotivoTipoResDTO dyccMotivoTipoResDTO) {
        this.dyccMotivoTipoResDTO = dyccMotivoTipoResDTO;
    }

    public DyccMotivoTipoResDTO getDyccMotivoTipoResDTO() {
        return dyccMotivoTipoResDTO;
    }

    public void setDescripcionOtros(String descripcionOtros) {
        this.descripcionOtros = descripcionOtros;
    }

    public String getDescripcionOtros() {
        return descripcionOtros;
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setIdResolMotivo(Integer idResolMotivo) {
        this.idResolMotivo = idResolMotivo;
    }

    public Integer getIdResolMotivo() {
        return idResolMotivo;
    }
}
