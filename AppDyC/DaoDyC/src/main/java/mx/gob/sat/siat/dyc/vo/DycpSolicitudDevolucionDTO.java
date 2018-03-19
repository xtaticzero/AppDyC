/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


/**
 * Clase DTO para la tabla [DYCT_FACTURA]
 * @author J. Isaac Carbajal Bernal
 * @date Noviembre 20, 2013
 * @since 0.1
 *
 * */
public class DycpSolicitudDevolucionDTO implements Serializable{
    
    @SuppressWarnings("compatibility:")
    private static final long serialVersionUID = 1L;
    public static final String NOMBRE_TABLA = "DYCT_FACTURA";
    
    //NUMFACTURA                     NOT NULL VARCHAR2(20)
    private String numFactura;
    //RFCPROVEEDOR                   NOT NULL VARCHAR2(13)
    private String rfcProveedor;
    //FECHAFACTURA                   NOT NULL DATE
    private Date fechaFactura;
    //CONCEPTO                       NOT NULL VARCHAR2(50)
    private String concepto;
    //IMPORTE                        NOT NULL NUMBER(12,2)
    private BigDecimal importe;
    //IVATRASLADADO                  NOT NULL NUMBER(3,2)
    private BigDecimal ivaTrasladado;
    //IVATRASLADADOS
    private BigDecimal ivaTrasladadoF;   
    //NUMCONTROL                     NOT NULL VARCHAR2(15)
    private String numControl;
    //TOTAL                          NOT NULL NUMBER(12,2)
    private BigDecimal total;
    
    private Long idDocumentoReq;

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setRfcProveedor(String rfcProveedor) {
        this.rfcProveedor = rfcProveedor;
    }

    public String getRfcProveedor() {
        return rfcProveedor;
    }

    public void setFechaFactura(Date fechaFactura) {
        if (fechaFactura != null) {
            this.fechaFactura = (Date)fechaFactura.clone();
        } else {
            this.fechaFactura = null;
        }   
    }

    public Date getFechaFactura() {
        if (fechaFactura != null) {
            return (Date)fechaFactura.clone();
        } else {
            return null;
        }   
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }


    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
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

    public void setIvaTrasladadoF(BigDecimal ivaTrasladadoF) {
        this.ivaTrasladadoF = ivaTrasladadoF;
    }

    public BigDecimal getIvaTrasladadoF() {
        return ivaTrasladadoF;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }


    public Long getIdDocumentoReq() {
        return idDocumentoReq;
    }

    public void setIdDocumentoReq(Long idDocumentoReq) {
        this.idDocumentoReq = idDocumentoReq;
    }

    @Override
    public String toString() {
        return "DycpSolicitudDevolucionDTO{" + "numFactura=" + numFactura + ", rfcProveedor=" + rfcProveedor + ", fechaFactura=" + fechaFactura + ", concepto=" + concepto + ", importe=" + importe + ", ivaTrasladado=" + ivaTrasladado + ", ivaTrasladadoF=" + ivaTrasladadoF + ", numControl=" + numControl + ", total=" + total + ", idDocumentoReq=" + idDocumentoReq + '}';
    }
    
}
