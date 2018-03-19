/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


/**
 * DTO de la tabla DYCT_FACTURAREQ
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctFacturaReqDTO implements Serializable {

    @SuppressWarnings("compatibility:-4471134707546573541")
    private static final long serialVersionUID = 1L;

    private String concepto;
    private Date fechaEmision;
    private BigDecimal importe;
    private BigDecimal ivaTrasladado;
    private String numeroFactura;
    private BigDecimal total;
    private DyctReqConfProvDTO dyctReqConfProvDTO;

    public DyctFacturaReqDTO() {
    }

    public DyctFacturaReqDTO(String concepto, Date fechaEmision, BigDecimal importe, BigDecimal ivaTrasladado,
                             DyctReqConfProvDTO dyctReqConfProvDTO, String numeroFactura, BigDecimal total) {
        this.concepto = concepto;
        this.fechaEmision = fechaEmision != null ? (Date)fechaEmision.clone() : null;
        this.importe = importe;
        this.ivaTrasladado = ivaTrasladado;
        this.dyctReqConfProvDTO = dyctReqConfProvDTO;
        this.numeroFactura = numeroFactura;
        this.total = total;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setFechaEmision(Date fechaEmision) {
        if (null != fechaEmision) {
            this.fechaEmision = (Date)fechaEmision.clone();
        } else {
            this.fechaEmision = null;
        }
    }

    public Date getFechaEmision() {
        if (null != fechaEmision) {
            return (Date)fechaEmision.clone();
        } else {
            return null;
        }
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setIvaTrasladado(BigDecimal ivaTrasladado) {
        this.ivaTrasladado = ivaTrasladado;
    }

    public BigDecimal getIvaTrasladado() {
        return ivaTrasladado;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setDyctReqConfProvDTO(DyctReqConfProvDTO dyctReqConfProvDTO) {
        this.dyctReqConfProvDTO = dyctReqConfProvDTO;
    }

    public DyctReqConfProvDTO getDyctReqConfProvDTO() {
        return dyctReqConfProvDTO;
    }
}
