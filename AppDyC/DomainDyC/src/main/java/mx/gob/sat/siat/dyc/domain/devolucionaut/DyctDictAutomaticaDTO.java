/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.domain.devolucionaut;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

/**
 *
 * @author MLIL
 */
public class DyctDictAutomaticaDTO implements Serializable{
    /**
     * Variables de la tabla 
     */
    @SuppressWarnings("compatibility:279465436847886487")
    private static final long serialVersionUID = 1L;
    
    private String numControl;
    private Integer numLote;
    private String rfc;
    private String impuesto;
    private Integer concepto;
    private String numOperacion;
    private BigDecimal importeSaldoF;
    private Date fechaProcModelo;
    private Date fechaRegistro;
    private Integer idModelo;
    private String idMarcResultado;
    private Integer motRiesgo;
    private Date fechaProceso;

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public Integer getNumLote() {
        return numLote;
    }

    public void setNumLote(Integer numLote) {
        this.numLote = numLote;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public Integer getConcepto() {
        return concepto;
    }

    public void setConcepto(Integer concepto) {
        this.concepto = concepto;
    }
        
    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public BigDecimal getImporteSaldoF() {
        return importeSaldoF;
    }

    public void setImporteSaldoF(BigDecimal importeSaldoF) {
        this.importeSaldoF = importeSaldoF;
    }

    public Date getFechaProcModelo() {
        if (null != fechaProcModelo) {
            return (Date)fechaProcModelo.clone();
        } else {
            return null;
        }
    }

    public void setFechaProcModelo(Date fechaProcModelo) {
        if (null != fechaProcModelo) {
            this.fechaProcModelo = (Date)fechaProcModelo.clone();
        } else {
            this.fechaProcModelo = null;
        }
    }

    public Date getFechaRegistro() {
        if (null != fechaRegistro){
            return (Date) fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro){
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }    
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getIdMarcResultado() {
        return idMarcResultado;
    }

    public void setIdMarcResultado(String idMarcResultado) {
        this.idMarcResultado = idMarcResultado;
    }

    public Integer getMotRiesgo() {
        return motRiesgo;
    }

    public void setMotRiesgo(Integer motRiesgo) {
        this.motRiesgo = motRiesgo;
    }

    public Date getFechaProceso() {
        if (null != fechaProceso){
            return (Date) fechaProceso.clone();
        }else {
            return null;
        }
    }

    public void setFechaProceso(Date fechaProceso) {
        if (null !=  fechaProceso){
            this.fechaProceso = (Date) fechaProceso.clone();
        }else {
            this.fechaProceso = null;
        }
    }
}
