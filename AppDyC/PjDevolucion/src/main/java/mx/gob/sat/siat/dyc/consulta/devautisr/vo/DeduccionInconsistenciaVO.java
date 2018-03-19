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
public class DeduccionInconsistenciaVO implements Serializable {

    private static final long serialVersionUID = -3169851638923220540L;

    private String tipoDeduccion;
    private String rfcEmisorCFDI;
    private String nombreEmisorCFDI;
    private BigDecimal importeDeclaracionSAT;
    private BigDecimal importeDeclaracionContribuyente;
    private short numeroInconsistencia;
    private String descripcion;
    private String accionCorrectiva;
    private int ejercicioAccionCorrectiva;
    private int generaDevolucionManual;

    public String getTipoDeduccion() {
        return tipoDeduccion;
    }

    public void setTipoDeduccion(String tipoDeduccion) {
        this.tipoDeduccion = tipoDeduccion;
    }

    public String getRfcEmisorCFDI() {
        return rfcEmisorCFDI;
    }

    public void setRfcEmisorCFDI(String rfcEmisorCFDI) {
        this.rfcEmisorCFDI = rfcEmisorCFDI;
    }

    public String getNombreEmisorCFDI() {
        return nombreEmisorCFDI;
    }

    public void setNombreEmisorCFDI(String nombreEmisorCFDI) {
        this.nombreEmisorCFDI = nombreEmisorCFDI;
    }

    public BigDecimal getImporteDeclaracionSAT() {
        return importeDeclaracionSAT;
    }

    public void setImporteDeclaracionSAT(BigDecimal importeDeclaracionSAT) {
        this.importeDeclaracionSAT = importeDeclaracionSAT;
    }

    public BigDecimal getImporteDeclaracionContribuyente() {
        return importeDeclaracionContribuyente;
    }

    public void setImporteDeclaracionContribuyente(BigDecimal importeDeclaracionContribuyente) {
        this.importeDeclaracionContribuyente = importeDeclaracionContribuyente;
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
