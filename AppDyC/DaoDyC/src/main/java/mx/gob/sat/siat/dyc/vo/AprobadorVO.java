/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.vo;

import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;

/**
 * Clase DTO para la tabla [DYCC_APROBADOR] + Descripcion Administracion Comision.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Octubre 24, 2013
 * */
public class AprobadorVO extends DyccAprobadorDTO {

    @SuppressWarnings("compatibility:-881330125597653641")
    private static final long serialVersionUID = 1L;

    private String nombreCompleto;
    private Integer noEmpleado;
    private String rfcCorto;
    private String curp;

    private Integer claveSir;
    private String un;
    private String admonGral;

    private String nombrePuesto;

    private String descClaveNivel;

    private Integer centroCosto;
    private String descCentroConsto;

    private String claveComision;
    private String descComision;

    private Integer activoPortal;
    private String centroCostoDescr254;

    public AprobadorVO() {
        super();
    }


    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
    }

    public String getRfcCorto() {
        return rfcCorto;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCurp() {
        return curp;
    }

    public void setClaveSir(Integer claveSir) {
        this.claveSir = claveSir;
    }

    public Integer getClaveSir() {
        return claveSir;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getUn() {
        return un;
    }

    public void setAdmonGral(String admonGral) {
        this.admonGral = admonGral;
    }

    public String getAdmonGral() {
        return admonGral;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setCentroCosto(Integer centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Integer getCentroCosto() {
        return centroCosto;
    }

    public void setDescCentroConsto(String descCentroConsto) {
        this.descCentroConsto = descCentroConsto;
    }

    public String getDescCentroConsto() {
        return descCentroConsto;
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

    public void setActivoPortal(Integer activoPortal) {
        this.activoPortal = activoPortal;
    }

    public Integer getActivoPortal() {
        return activoPortal;
    }

    public void setDescClaveNivel(String descClaveNivel) {
        this.descClaveNivel = descClaveNivel;
    }

    public String getDescClaveNivel() {
        return descClaveNivel;
    }

    public void setCentroCostoDescr254(String centroCostoDescr254) {
        this.centroCostoDescr254 = centroCostoDescr254;
    }

    public String getCentroCostoDescr254() {
        return centroCostoDescr254;
    }

    public void setNoEmpleado(Integer noEmpleado) {
        this.noEmpleado = noEmpleado;
    }

    public Integer getNoEmpleado() {
        return noEmpleado;
    }
}
