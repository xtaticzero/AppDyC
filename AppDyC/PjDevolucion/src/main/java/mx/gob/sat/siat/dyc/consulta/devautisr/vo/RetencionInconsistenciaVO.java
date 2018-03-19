/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class RetencionInconsistenciaVO implements Serializable {

    private static final long serialVersionUID = -4022325889829603418L;

    private String tipoIngreso;
    private String rfcRetenedor;
    private String razonSocial;
    private BigDecimal totalIngresoSat;
    private BigDecimal imptoRetenidoSat;
    private BigDecimal totalIngresoContribuyente;
    private BigDecimal imptoRetenidoContribuyente;
    private short numeroInconsistencia;
    private String descripcion;
    private String accionCorrectiva;
    private int ejercicioAccionCorrectiva;
    private int generaDevolucionManual;

    public String getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(String tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public String getRfcRetenedor() {
        return rfcRetenedor;
    }

    public void setRfcRetenedor(String rfcRetenedor) {
        this.rfcRetenedor = rfcRetenedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public BigDecimal getTotalIngresoSat() {
        return totalIngresoSat;
    }

    public void setTotalIngresoSat(BigDecimal totalIngresoSat) {
        this.totalIngresoSat = totalIngresoSat;
    }

    public BigDecimal getImptoRetenidoSat() {
        return imptoRetenidoSat;
    }

    public void setImptoRetenidoSat(BigDecimal imptoRetenidoSat) {
        this.imptoRetenidoSat = imptoRetenidoSat;
    }

    public BigDecimal getTotalIngresoContribuyente() {
        return totalIngresoContribuyente;
    }

    public void setTotalIngresoContribuyente(BigDecimal totalIngresoContribuyente) {
        this.totalIngresoContribuyente = totalIngresoContribuyente;
    }

    public BigDecimal getImptoRetenidoContribuyente() {
        return imptoRetenidoContribuyente;
    }

    public void setImptoRetenidoContribuyente(BigDecimal imptoRetenidoContribuyente) {
        this.imptoRetenidoContribuyente = imptoRetenidoContribuyente;
    }

    public short getNumeroInconsistencia() {
        return numeroInconsistencia;
    }

    public void setNumeroInconsistencia(short numeroInconsistencia) {
        this.numeroInconsistencia = numeroInconsistencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAccionCorrectiva() {
        return accionCorrectiva;
    }

    public void setAccionCorrectiva(String accionCorrectiva) {
        this.accionCorrectiva = accionCorrectiva;
    }

    public int getEjercicioAccionCorrectiva() {
        return ejercicioAccionCorrectiva;
    }

    public void setEjercicioAccionCorrectiva(int ejercicioAccionCorrectiva) {
        this.ejercicioAccionCorrectiva = ejercicioAccionCorrectiva;
    }

    public int getGeneraDevolucionManual() {
        return generaDevolucionManual;
    }

    public void setGeneraDevolucionManual(int generaDevolucionManual) {
        this.generaDevolucionManual = generaDevolucionManual;
    }

}
