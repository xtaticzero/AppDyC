/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.domain.ags;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;

/**
 *
 * @author root
 */
public class SatAgsEmpleadosMVDTO implements Serializable {

    @SuppressWarnings("compatibility:-8408056215209888851")
    private static final long serialVersionUID = 1L;

    private String empleado;
    private String rfcCorto;
    private String deptid;
    private String estatus;
    private Integer noEmpleado;
    private String jobcode;
    private String location;
    private String un;
    private String admonGral;
    private String nombre;
    private String paterno;
    private String materno;
    private String nombreCompleto;
    private String domicilio;
    private String colonia;
    private String cp;
    private String municipio;
    private String ciudad;
    private String estado;
    private String edoDescripcion;
    private String rfc;
    private String curp;
    private String nombrePuesto;
    private String claveNivelDireccion;
    private String descrDepto;
    private String nivelDireccion;
    private String centroCosto;
    private String centroCostoDescr;
    private String centroCostoDescr254;
    private String email;
    private String generalDeptid;
    private String descrGeneral;
    private String emplStatusGeneral;
    private String centralDeptid;
    private String descrCentral;
    private String emplStatusCentral;
    private String adminDeptid;
    private String descrAdministrador;
    private String emplStatusAdmin;
    private String subadminDeptid;
    private String descrSubadministrador;
    private String emplStatusSubadmin;
    private String jefeDeptoDeptId;
    private String descrJefeDepto;
    private String emplStatusJefedepto;
    private String enlaceDeptid;
    private String descrEnlace;
    private String emplStatusEnlace;
    
    private AdmcUnidadAdmvaDTO admcUnidadAdmvaDTO;

    public SatAgsEmpleadosMVDTO() {
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getRfcCorto() {
        return rfcCorto;
    }

    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Integer getNoEmpleado() {
        return noEmpleado;
    }

    public void setNoEmpleado(Integer noEmpleado) {
        this.noEmpleado = noEmpleado;
    }

    public String getJobcode() {
        return jobcode;
    }

    public void setJobcode(String jobcode) {
        this.jobcode = jobcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getAdmonGral() {
        return admonGral;
    }

    public void setAdmonGral(String admonGral) {
        this.admonGral = admonGral;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEdoDescripcion() {
        return edoDescripcion;
    }

    public void setEdoDescripcion(String edoDescripcion) {
        this.edoDescripcion = edoDescripcion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getClaveNivelDireccion() {
        return claveNivelDireccion;
    }

    public void setClaveNivelDireccion(String claveNivelDireccion) {
        this.claveNivelDireccion = claveNivelDireccion;
    }

    public String getDescrDepto() {
        return descrDepto;
    }

    public void setDescrDepto(String descrDepto) {
        this.descrDepto = descrDepto;
    }

    public String getNivelDireccion() {
        return nivelDireccion;
    }

    public void setNivelDireccion(String nivelDireccion) {
        this.nivelDireccion = nivelDireccion;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public String getCentroCostoDescr() {
        return centroCostoDescr;
    }

    public void setCentroCostoDescr(String centroCostoDescr) {
        this.centroCostoDescr = centroCostoDescr;
    }

    public String getCentroCostoDescr254() {
        return centroCostoDescr254;
    }

    public void setCentroCostoDescr254(String centroCostoDescr254) {
        this.centroCostoDescr254 = centroCostoDescr254;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeneralDeptid() {
        return generalDeptid;
    }

    public void setGeneralDeptid(String generalDeptid) {
        this.generalDeptid = generalDeptid;
    }

    public String getDescrGeneral() {
        return descrGeneral;
    }

    public void setDescrGeneral(String descrGeneral) {
        this.descrGeneral = descrGeneral;
    }

    public String getEmplStatusGeneral() {
        return emplStatusGeneral;
    }

    public void setEmplStatusGeneral(String emplStatusGeneral) {
        this.emplStatusGeneral = emplStatusGeneral;
    }

    public String getCentralDeptid() {
        return centralDeptid;
    }

    public void setCentralDeptid(String centralDeptid) {
        this.centralDeptid = centralDeptid;
    }

    public String getDescrCentral() {
        return descrCentral;
    }

    public void setDescrCentral(String descrCentral) {
        this.descrCentral = descrCentral;
    }

    public String getEmplStatusCentral() {
        return emplStatusCentral;
    }

    public void setEmplStatusCentral(String emplStatusCentral) {
        this.emplStatusCentral = emplStatusCentral;
    }

    public String getAdminDeptid() {
        return adminDeptid;
    }

    public void setAdminDeptid(String adminDeptid) {
        this.adminDeptid = adminDeptid;
    }

    public String getDescrAdministrador() {
        return descrAdministrador;
    }

    public void setDescrAdministrador(String descrAdministrador) {
        this.descrAdministrador = descrAdministrador;
    }

    public String getEmplStatusAdmin() {
        return emplStatusAdmin;
    }

    public void setEmplStatusAdmin(String emplStatusAdmin) {
        this.emplStatusAdmin = emplStatusAdmin;
    }

    public String getSubadminDeptid() {
        return subadminDeptid;
    }

    public void setSubadminDeptid(String subadminDeptid) {
        this.subadminDeptid = subadminDeptid;
    }

    public String getDescrSubadministrador() {
        return descrSubadministrador;
    }

    public void setDescrSubadministrador(String descrSubadministrador) {
        this.descrSubadministrador = descrSubadministrador;
    }

    public String getEmplStatusSubadmin() {
        return emplStatusSubadmin;
    }

    public void setEmplStatusSubadmin(String emplStatusSubadmin) {
        this.emplStatusSubadmin = emplStatusSubadmin;
    }

    public String getJefeDeptoDeptId() {
        return jefeDeptoDeptId;
    }

    public void setJefeDeptoDeptId(String jefeDeptoDeptId) {
        this.jefeDeptoDeptId = jefeDeptoDeptId;
    }

    public String getDescrJefeDepto() {
        return descrJefeDepto;
    }

    public void setDescrJefeDepto(String descrJefeDepto) {
        this.descrJefeDepto = descrJefeDepto;
    }

    public String getEmplStatusJefedepto() {
        return emplStatusJefedepto;
    }

    public void setEmplStatusJefedepto(String emplStatusJefedepto) {
        this.emplStatusJefedepto = emplStatusJefedepto;
    }

    public String getEnlaceDeptid() {
        return enlaceDeptid;
    }

    public void setEnlaceDeptid(String enlaceDeptid) {
        this.enlaceDeptid = enlaceDeptid;
    }

    public String getDescrEnlace() {
        return descrEnlace;
    }

    public void setDescrEnlace(String descrEnlace) {
        this.descrEnlace = descrEnlace;
    }

    public String getEmplStatusEnlace() {
        return emplStatusEnlace;
    }

    public void setEmplStatusEnlace(String emplStatusEnlace) {
        this.emplStatusEnlace = emplStatusEnlace;
    }
    
    public AdmcUnidadAdmvaDTO getAdmcUnidadAdmvaDTO() {
        return admcUnidadAdmvaDTO;
    }

    public void setAdmcUnidadAdmvaDTO(AdmcUnidadAdmvaDTO admcUnidadAdmvaDTO) {
        this.admcUnidadAdmvaDTO = admcUnidadAdmvaDTO;
    }
    
}
