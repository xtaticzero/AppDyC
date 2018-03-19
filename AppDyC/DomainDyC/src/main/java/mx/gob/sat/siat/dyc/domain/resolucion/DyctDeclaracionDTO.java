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

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUsoDecDTO;


/**
 * DTO para la tabla DYCT_DECLARACION
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyctDeclaracionDTO implements Serializable {


    @SuppressWarnings("compatibility:-6771492901498213845")
    private static final long serialVersionUID = 1L;
    
    private Integer idDeclaracion;
    private Date fechaPresentacion;
    private Date fechaCausacion;
    private String numOperacion;
    private String numDocumento;
    private BigDecimal saldoAfavor;
    private BigDecimal devueltoCompensado;
    private BigDecimal acreditamiento;
    private BigDecimal importe;
    private DyccTipoDeclaraDTO dyccTipoDeclaraDTO;
    private DyccUsoDecDTO dyccUsoDecDTO;
    private String etiquetaSaldo;
    private DycpServicioDTO dycpServicioDTO;

    //TODO: quitar la referencia al numControl como String, ya esta como DTO, porque el campo es FK
    private String numControl;

    public DyctDeclaracionDTO() {
    }

    public void setAcreditamiento(BigDecimal acreditamiento) {
        this.acreditamiento = acreditamiento;
    }

    public BigDecimal getAcreditamiento() {
        return acreditamiento;
    }

    public void setDevueltoCompensado(BigDecimal devueltoCompensado) {
        this.devueltoCompensado = devueltoCompensado;
    }

    public BigDecimal getDevueltoCompensado() {
        return devueltoCompensado;
    }

    public void setFechaCausacion(Date fechaCausacion) {
        if (null != fechaCausacion) {
            this.fechaCausacion = (Date)fechaCausacion.clone();
        } else {
            this.fechaCausacion = null;
        }
    }

    public Date getFechaCausacion() {
        if (null != fechaCausacion) {
            return (Date)fechaCausacion.clone();
        } else {
            return null;
        }
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setIdDeclaracion(Integer idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public Integer getIdDeclaracion() {
        return idDeclaracion;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setSaldoAfavor(BigDecimal saldoAfavor) {
        this.saldoAfavor = saldoAfavor;
    }

    public BigDecimal getSaldoAfavor() {
        return saldoAfavor;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public void setDyccTipoDeclaraDTO(DyccTipoDeclaraDTO dyccTipoDeclaraDTO) {
        this.dyccTipoDeclaraDTO = dyccTipoDeclaraDTO;
    }

    public DyccTipoDeclaraDTO getDyccTipoDeclaraDTO() {
        return dyccTipoDeclaraDTO;
    }

    public void setDyccUsoDecDTO(DyccUsoDecDTO dyccUsoDecDTO) {
        this.dyccUsoDecDTO = dyccUsoDecDTO;
    }

    public DyccUsoDecDTO getDyccUsoDecDTO() {
        return dyccUsoDecDTO;
    }


    public void setEtiquetaSaldo(String etiquetaSaldo) {
        this.etiquetaSaldo = etiquetaSaldo;
    }

    public String getEtiquetaSaldo() {
        return etiquetaSaldo;
    }
    
    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }
    
    public String getParameterRequest() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@" );
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("acreditamiento=");
        buffer.append(getAcreditamiento());
        buffer.append(',');
        buffer.append("devueltoCompensado=");
        buffer.append(getDevueltoCompensado());
        buffer.append(',');
        buffer.append("fechaCausacion=");
        buffer.append(getFechaCausacion());
        buffer.append(',');
        buffer.append("fechaPresentacion=");
        buffer.append(getFechaPresentacion());
        buffer.append(',');
        buffer.append("idDeclaracion=");
        buffer.append(getIdDeclaracion());
        buffer.append(',');
        buffer.append("importe=");
        buffer.append(getImporte());
        buffer.append(',');
        buffer.append("numDocumento=");
        buffer.append(getNumDocumento());
        buffer.append(',');
        buffer.append("numOperacion=");
        buffer.append(getNumOperacion());
        buffer.append(',');
        buffer.append("saldoaFavor=");
        buffer.append(getSaldoAfavor());
        buffer.append(',');
        buffer.append("etiquetaSaldo=");
        buffer.append(getEtiquetaSaldo());
        buffer.append("numControl=");
        buffer.append(getNumControl());
        buffer.append(']');
        return buffer.toString();
    }

}
