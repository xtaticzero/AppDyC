/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.declaraciontemp;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


/**
 * DTO de la tabla DYCT_DECLARATEMP
 * @author  Alfredo Ramirez
 * @since   03/04/2014
 */
public class DyctDeclaraTempDTO implements Serializable {

    @SuppressWarnings("compatibility:8990638500656045594")
    private static final long serialVersionUID = 1L;

    private BigDecimal acreditamiento;
    private BigDecimal devueltoCompensado;
    private Date fechaCausacion;
    private Date fechaPresentacion;
    private Integer folioServtemp;
    private Integer idTipoDeclaracion;
    private Integer idUsoDec;
    private BigDecimal importe;
    private Date normalFechapres;
    private BigDecimal normalImportesaf;
    private Long normalNumoperacion;
    private String numDocumento;
    private String numOperacion;
    private BigDecimal saldoAFavor;
    private DyctServicioTempDTO dyctServicioTempDTO; 
    private String etiquetaSaldo;

    public DyctDeclaraTempDTO() {
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

    public void setFolioServtemp(Integer folioServtemp) {
        this.folioServtemp = folioServtemp;
    }

    public Integer getFolioServtemp() {
        return folioServtemp;
    }

    public void setIdTipoDeclaracion(Integer idTipoDeclaracion) {
        this.idTipoDeclaracion = idTipoDeclaracion;
    }

    public Integer getIdTipoDeclaracion() {
        return idTipoDeclaracion;
    }

    public void setIdUsoDec(Integer idUsoDec) {
        this.idUsoDec = idUsoDec;
    }

    public Integer getIdUsoDec() {
        return idUsoDec;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setNormalFechapres(Date normalFechapres) {
        if (null != normalFechapres) {
            this.normalFechapres = (Date)normalFechapres.clone();
        } else {
            this.normalFechapres = null;
        }
    }

    public Date getNormalFechapres() {
        if (null != normalFechapres) {
            return (Date)normalFechapres.clone();
        } else {
            return null;
        }
    }

    public void setNormalImportesaf(BigDecimal normalImportesaf) {
        this.normalImportesaf = normalImportesaf;
    }

    public BigDecimal getNormalImportesaf() {
        return normalImportesaf;
    }

    public void setNormalNumoperacion(Long normalNumoperacion) {
        this.normalNumoperacion = normalNumoperacion;
    }

    public Long getNormalNumoperacion() {
        return normalNumoperacion;
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

  

    public void setDyctServicioTempDTO(DyctServicioTempDTO dyctServicioTempDTO) {
        this.dyctServicioTempDTO = dyctServicioTempDTO;
    }

    public DyctServicioTempDTO getDyctServicioTempDTO() {
        return dyctServicioTempDTO;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@" );
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("acreditamiento=");
        buffer.append(getAcreditamiento());
        buffer.append(',');
        buffer.append("devueltocompensado=");
        buffer.append(getDevueltoCompensado());
        buffer.append(',');
        buffer.append("fechacausacion=");
        buffer.append(getFechaCausacion());
        buffer.append(',');
        buffer.append("fechapresentacion=");
        buffer.append(getFechaPresentacion());
        buffer.append(',');
        buffer.append("folioservtemp=");
        buffer.append(getFolioServtemp());
        buffer.append(',');
        buffer.append("idtipodeclaracion=");
        buffer.append(getIdTipoDeclaracion());
        buffer.append(',');
        buffer.append("idusodec=");
        buffer.append(getIdUsoDec());
        buffer.append(',');
        buffer.append("importe=");
        buffer.append(getImporte());
        buffer.append(',');
        buffer.append("normalfechapres=");
        buffer.append(getNormalFechapres());
        buffer.append(',');
        buffer.append("normalimportesaf=");
        buffer.append(getNormalImportesaf());
        buffer.append(',');
        buffer.append("normalnumoperacion=");
        buffer.append(getNormalNumoperacion());
        buffer.append(',');
        buffer.append("numdocumento=");
        buffer.append(getNumDocumento());
        buffer.append(',');
        buffer.append("numoperacion=");
        buffer.append(getNumOperacion());
        buffer.append(',');
        buffer.append("saldoafavor=");
        buffer.append(getSaldoAFavor());
        buffer.append(']');
        return buffer.toString();
    }

    public void setSaldoAFavor(BigDecimal saldoAFavor) {
        this.saldoAFavor = saldoAFavor;
    }

    public BigDecimal getSaldoAFavor() {
        return saldoAFavor;
    }

    public void setEtiquetaSaldo(String etiquetaSaldo) {
        this.etiquetaSaldo = etiquetaSaldo;
    }

    public String getEtiquetaSaldo() {
        return etiquetaSaldo;
    }
}
