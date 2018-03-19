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
 * guardar las notas de un requerimiento.
 * @author David Guerrero Reyes
 * @date Noviembre 26, 2013
 * @since 0.1
 *
 * */
public class NotaExpedienteDTO implements Serializable {


    @SuppressWarnings("compatibility:-5576045702963635543")
    private static final long serialVersionUID = 1572347128839783234L;
    
    private String usuario;
    private Date fechaRegistro;
    private String descripcion;
    private String numControl;

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if(null != fechaRegistro) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if(null != fechaRegistro) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }
}
