/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.vo;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class InconsistenciaTramiteVO implements Serializable {

    private int numeroInconsistencia;
    private String descripcion;
    private String accionCorrectiva;
    private int ejercicio;
    private int generarDevolucionManual;

    //CC automaticas
    private long idDeduccion;
    private long idIngreso;

    public int getNumeroInconsistencia() {
        return numeroInconsistencia;
    }

    public void setNumeroInconsistencia(int numeroInconsistencia) {
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

    public int getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getGenerarDevolucionManual() {
        return generarDevolucionManual;
    }

    public void setGenerarDevolucionManual(int generarDevolucionManual) {
        this.generarDevolucionManual = generarDevolucionManual;
    }

    public long getIdDeduccion() {
        return idDeduccion;
    }

    public void setIdDeduccion(long idDeduccion) {
        this.idDeduccion = idDeduccion;
    }

    public long getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(long idIngreso) {
        this.idIngreso = idIngreso;
    }

    @Override
    public String toString() {
        return "InconsistenciaTramiteVO{" + "numeroInconsistencia=" + numeroInconsistencia + ", descripcion=" + descripcion + ", accionCorrectiva=" + accionCorrectiva + ", ejercicio=" + ejercicio + ", generarDevolucionManual=" + generarDevolucionManual + ", idDeduccion=" + idDeduccion + ", idIngreso=" + idIngreso + '}';
    }

}
