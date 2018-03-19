/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.vo;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;


/**
 * DTO Caso de uso Mantener Matriz de Dictaminadores
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 15, 2013
 * @since 0.1
 *
 * */
public class DictaminadorVO extends DyccDictaminadorDTO {

    @SuppressWarnings("compatibility:-4414587843479732842")
    private static final long serialVersionUID = 1L;

    private String nombreCompleto;
    private String un;
    private String admonGral;
    private Integer activoPortal;

    private Integer ccComision;
    private Integer claveAdmComision;
    private String descComision;
    private String centroCostoDescr254;
    private Integer ccosto;
    

    public DictaminadorVO() {
        super();
    }


    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
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

    public void setActivoPortal(Integer activoPortal) {
        this.activoPortal = activoPortal;
    }

    public Integer getActivoPortal() {
        return activoPortal;
    }

    public void setCcComision(Integer ccComision) {
        this.ccComision = ccComision;
    }

    public Integer getCcComision() {
        return ccComision;
    }

    public void setClaveAdmComision(Integer claveAdmComision) {
        this.claveAdmComision = claveAdmComision;
    }

    public Integer getClaveAdmComision() {
        return claveAdmComision;
    }

    public void setDescComision(String descComision) {
        this.descComision = descComision;
    }

    public String getDescComision() {
        return descComision;
    }

    public void setCentroCostoDescr254(String centroCostoDescr254) {
        this.centroCostoDescr254 = centroCostoDescr254;
    }

    public String getCentroCostoDescr254() {
        return centroCostoDescr254;
    }

    public Integer getCcosto() {
        return ccosto;
    }

    public void setCcosto(Integer ccosto) {
        this.ccosto = ccosto;
    }
}
