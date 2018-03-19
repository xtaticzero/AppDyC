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
public class RechazoTramiteVO implements Serializable {

    private int numeroRechazo;
    private String descripcion;
    private String accionCorrectiva;
    private int ejercicio;
    private int generarDevolucionManual;

    public int getNumeroRechazo() {
        return numeroRechazo;
    }

    public void setNumeroRechazo(int numeroRechazo) {
        this.numeroRechazo = numeroRechazo;
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

    @Override
    public String toString() {
        return "RechazoTramiteVO{" + "numeroRechazo=" + numeroRechazo + ", descripcion=" + descripcion + ", accionCorrectiva=" + accionCorrectiva + ", ejercicio=" + ejercicio + ", generarDevolucionManual=" + generarDevolucionManual + '}';
    }

}
