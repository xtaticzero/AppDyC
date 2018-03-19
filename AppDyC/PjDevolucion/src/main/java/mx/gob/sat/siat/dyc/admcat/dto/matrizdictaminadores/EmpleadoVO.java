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

import mx.gob.sat.siat.dyc.vo.AprobadorVO;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;


/**
 * Empleado DTO para caso de uso Matriz de Dictaminadores PS_PERSON_NAME.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
public class EmpleadoVO implements Serializable {
    public static final String NOMBRE_TABLA = "PS_PERSON_NAME";

    @SuppressWarnings("compatibility:4582961485551168116")
    private static final long serialVersionUID = 1L;

    private String numEmpleado;
    private String un;
    private String admGral;
    private String rfcCorto;
    private String rfc;
    private String curp;
    private String nombre;
    private String paterno;
    private String materno;
    private String nombreCompleto;
    private Integer claveNivel;
    private String descClaveNivel;
    private String puesto;
    private String claveDepto;
    private String descDepto;
    private Integer ccosto;
    private String descCCosto;
    private String email;
    // ::::::::::::::::::::::::
    private Integer activo;
    private Integer claveAdm;
    private String claveComision;
    private String descComision;
    private Integer claveAdmOp;
    private String centroCostoDescr254;
    private Integer validacionEstado;
    private DictaminadorVO dictaminador;
    private AprobadorVO aprobador;

    public EmpleadoVO() {
        super();
    }

    // ACCESSOR'S *****************************************************

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getUn() {
        return un;
    }

    public void setAdmGral(String admGral) {
        this.admGral = admGral;
    }

    public String getAdmGral() {
        return admGral;
    }

    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
    }

    public String getRfcCorto() {
        return rfcCorto;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCurp() {
        return curp;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setClaveNivel(Integer claveNivel) {
        this.claveNivel = claveNivel;
    }

    public Integer getClaveNivel() {
        return claveNivel;
    }

    public void setDescClaveNivel(String descClaveNivel) {
        this.descClaveNivel = descClaveNivel;
    }

    public String getDescClaveNivel() {
        return descClaveNivel;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setClaveDepto(String claveDepto) {
        this.claveDepto = claveDepto;
    }

    public String getClaveDepto() {
        return claveDepto;
    }

    public void setDescDepto(String descDepto) {
        this.descDepto = descDepto;
    }

    public String getDescDepto() {
        return descDepto;
    }

    public void setCcosto(Integer ccosto) {
        this.ccosto = ccosto;
    }

    public Integer getCcosto() {
        return ccosto;
    }

    public void setDescCCosto(String descCCosto) {
        this.descCCosto = descCCosto;
    }

    public String getDescCCosto() {
        return descCCosto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setClaveComision(String claveComision) {
        this.claveComision = claveComision;
    }

    public String getClaveComision() {
        return claveComision;
    }

    public void setDescComision(String descComision) {
        this.descComision = descComision;
    }

    public String getDescComision() {
        return descComision;
    }

    public void setClaveAdmOp(Integer claveAdmOp) {
        this.claveAdmOp = claveAdmOp;
    }

    public Integer getClaveAdmOp() {
        return claveAdmOp;
    }

    public void setDictaminador(DictaminadorVO dictaminador) {
        this.dictaminador = dictaminador;
    }

    public DictaminadorVO getDictaminador() {
        return dictaminador;
    }

    public void setAprobador(AprobadorVO aprobador) {
        this.aprobador = aprobador;
    }

    public AprobadorVO getAprobador() {
        return aprobador;
    }

    public void setCentroCostoDescr254(String centroCostoDescr254) {
        this.centroCostoDescr254 = centroCostoDescr254;
    }

    public String getCentroCostoDescr254() {
        return centroCostoDescr254;
    }
    
    public void setValidacionEstado ( Integer validacionEstado ){
        this.validacionEstado = validacionEstado;
    }
    
    public Integer getValidacionEstado (){
        return validacionEstado;
    }
}
