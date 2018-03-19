/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dto.dycadministracion;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;


/**
 * Clase DTO para caso de uso administracion devoluciones y compensaciones.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1 , Enero 30, 2014
 *
 * */
public class AdmSolicitudesdycVO extends SolicitudAdministrarSolVO implements Serializable {


    @SuppressWarnings("compatibility:3197318510289946230")
    private static final long serialVersionUID = 5067342230521961064L;

    private long numEmpleado;
    private String estadoDyC;
    private String admNombre;
    private Integer claveAdm;
    private String rfcContribuyente;
    private Integer idTipoServicio;
    private String entidadFed;

    public AdmSolicitudesdycVO() {
        super();
    }


    public void setNumEmpleado(long numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public long getNumEmpleado() {
        return numEmpleado;
    }

    public void setEstadoDyC(String estadoDyC) {
        this.estadoDyC = estadoDyC;
    }

    public String getEstadoDyC() {
        return estadoDyC;
    }

    public void setAdmNombre(String admNombre) {
        this.admNombre = admNombre;
    }

    public String getAdmNombre() {
        return admNombre;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setIdTipoServicio(Integer idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Integer getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setEntidadFed(String entidadFed) {
        this.entidadFed = entidadFed;
    }

    public String getEntidadFed() {
        return entidadFed;
    }
}
