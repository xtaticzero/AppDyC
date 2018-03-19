/*
    *  Todos los Derechos Reservados 2013 SAT.
    *  Servicio de Administracion Tributaria (SAT).
    *
    *  Este software contiene informacion propiedad exclusiva del SAT considerada
    *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
    *  parcial o total.
    */
package mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion;

import java.io.Serializable;

import java.util.Date;


/**
 * Clase DTO para el manejo de elementos en el formulario para
 * guardar el seguimiento del requerimiento.
 * @author David Guerrero Reyes
 * @date Noviembre 26, 2013
 * @since 0.1
 *
 * */
public class SeguimientoDTO implements Serializable {

    @SuppressWarnings ("compatibility:-2133873058717725197")
    private static final long serialVersionUID = -5083854610487530181L;
    
    private int idSeguimiento;
    private int idAccionSeg;
    private String responsable;
    private Date fecha;
    private String comentarios;
    private String numControlDoc;

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setFecha(Date fecha) {
        if(null != fecha) {
            this.fecha = (Date)fecha.clone();
        } else {
            this.fecha = null;
        }
    }

    public Date getFecha() {
        if(null != fecha) {
            return (Date)fecha.clone();
        } else {
            return null;
        }
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public int getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdAccionSeg(int idAccionSeg) {
        this.idAccionSeg = idAccionSeg;
    }

    public int getIdAccionSeg() {
        return idAccionSeg;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }
}

