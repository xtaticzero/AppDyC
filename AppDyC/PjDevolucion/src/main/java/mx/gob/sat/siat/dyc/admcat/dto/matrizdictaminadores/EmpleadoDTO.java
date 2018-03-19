/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores;

import java.io.Serializable;

import java.util.Date;


/**
 * Empleado DTO para caso de uso Matriz de Dictaminadores PS_PERSON_NAME.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
public class EmpleadoDTO implements Serializable {
    public static final String NOMBRE_TABLA = "PS_PERSON_NAME";

    @SuppressWarnings("compatibility:-5345526437976390291")
    private static final long serialVersionUID = 1L;

    private String idEmpleado;
    private String ciudad;
    private String nombreCompleto;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fecha;

    public EmpleadoDTO() {
        super();
    }

    public String getParameterReport() {
        StringBuffer sb = new StringBuffer();

        sb.append("\n idEmpleado:").append(this.getIdEmpleado());
        sb.append("\n ciudad:").append(this.getCiudad());
        sb.append("\n nombreCompleto:").append(this.getNombreCompleto());
        sb.append("\n nombre:").append(this.getNombre());
        sb.append("\n apellidoPaterno:").append(this.getApellidoPaterno());
        sb.append("\n apellidoMaterno:").append(this.getApellidoMaterno());
        sb.append("\n fecha:").append(this.getFecha());

        return sb.toString();
    }

    // TODO: ACCESSOR'S *****************************************************

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setFecha(Date fecha) {
        if (null != fecha) {
            this.fecha = (Date)fecha.clone();
        } else {
            this.fecha = null;
        }
    }

    public Date getFecha() {
        if (null != fecha) {
            return (Date)fecha.clone();
        } else {
            return null;
        }
    }

}
