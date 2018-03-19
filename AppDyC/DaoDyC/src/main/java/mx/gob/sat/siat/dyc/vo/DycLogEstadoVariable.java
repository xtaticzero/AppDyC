/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.vo;

import java.util.Date;

/**
 *
 * @author GAER8674
 */
public class DycLogEstadoVariable {
    private String variable;
    private String estado;
    private Date fechaDeEstado;

    public DycLogEstadoVariable(String variable, String estado) {
        this.variable = variable;
        this.estado = estado;
    }
    
    
    /**
     * @return the variable
     */
    public String getVariable() {
        return variable;
    }

    /**
     * @param variable the variable to set
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the fechaDeEstado
     */
    public Date getFechaDeEstado() {
        return (Date)fechaDeEstado.clone();
    }

    /**
     * @param fechaDeEstado the fechaDeEstado to set
     */
    public void setFechaDeEstado(Date fechaDeEstado) {
        this.fechaDeEstado = (Date)fechaDeEstado.clone();
    }
}
