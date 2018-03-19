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
 * DTO de la tabla DYCC_DICTAMINADOR
 * @author  Alfredo Ramirez
 * Actualizado por Luis Alberto Dominguez Palomino LADP
 * @since   02/04/2014
 */
public class DyccDictaminadorDTO implements Serializable {


    @SuppressWarnings("compatibility:-8292180990530521318")
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
    private Integer cargaTrabajo;
    private Integer centroCosto;
    private Integer claveAdm;

    public DyccDictaminadorDTO() {
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setCargaTrabajo(Integer cargaTrabajo) {
        this.cargaTrabajo = cargaTrabajo;
    }

    public Integer getCargaTrabajo() {
        return cargaTrabajo;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
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

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setCentroCosto(Integer centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Integer getCentroCosto() {
        return centroCosto;
    }
}
