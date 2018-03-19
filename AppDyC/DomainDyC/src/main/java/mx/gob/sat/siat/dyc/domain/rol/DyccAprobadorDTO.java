/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.rol;

import java.io.Serializable;


/**
 * DTO de la tabla DYCC_APROBADOR
 * @author  Alfredo Ramirez
 * Actualizado por Luis Alberto Dominguez Palomino LADP
 * @since   02/04/2014
 */
public class DyccAprobadorDTO implements Serializable {


    @SuppressWarnings("compatibility:3202736028293823266")
    private static final long serialVersionUID = 1L;

    private Integer numEmpleado;
    private Integer activo;
    private String observaciones;
    private String claveDepto;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String rfc;
    private Integer numEmpleadoJefe;
    private Integer claveNivel;
    private Integer centroCosto;
    private Integer claveAdm;

    public DyccAprobadorDTO() {
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setNumEmpleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setClaveDepto(String claveDepto) {
        this.claveDepto = claveDepto;
    }

    public String getClaveDepto() {
        return claveDepto;
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

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setNumEmpleadoJefe(Integer numEmpleadoJefe) {
        this.numEmpleadoJefe = numEmpleadoJefe;
    }

    public Integer getNumEmpleadoJefe() {
        return numEmpleadoJefe;
    }

    public void setClaveNivel(Integer claveNivel) {
        this.claveNivel = claveNivel;
    }

    public Integer getClaveNivel() {
        return claveNivel;
    }

    public void setCentroCosto(Integer centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Integer getCentroCosto() {
        return centroCosto;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@");
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');

        buffer.append("numempleado=");
        buffer.append(getNumEmpleado());
        buffer.append(',');
        buffer.append("activo=");
        buffer.append(getActivo());
        buffer.append(',');
        buffer.append("observaciones=");
        buffer.append(getObservaciones());
        buffer.append(',');
        buffer.append("clavdepto=");
        buffer.append(getClaveDepto());
        buffer.append(',');
        buffer.append("nombre=");
        buffer.append(getNombre());
        buffer.append(',');
        buffer.append("apellidopaterno=");
        buffer.append(getApellidoPaterno());
        buffer.append(',');
        buffer.append("apellidomaterno=");
        buffer.append(getApellidoMaterno());
        buffer.append(',');
        buffer.append("email=");
        buffer.append(getEmail());
        buffer.append(',');
        buffer.append("rfc=");
        buffer.append(getRfc());
        buffer.append(',');
        buffer.append("numempleadojefe=");
        buffer.append(getNumEmpleadoJefe());
        buffer.append(',');
        buffer.append("clavenivel=");
        buffer.append(getClaveNivel());
        buffer.append(',');
        buffer.append("centrocosto=");
        buffer.append(getCentroCosto());
        buffer.append(',');
        buffer.append("claveAdm=");
        buffer.append(getClaveAdm());
        buffer.append(']');
        return buffer.toString();
    }

}
