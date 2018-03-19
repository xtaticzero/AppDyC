/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente;

import java.io.Serializable;

import java.math.BigDecimal;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;


/**
 *
 * @author  Alfredo Ramirez
 * @since   08/10/2013
 */
public class ConsultarDevolucionesContribuyenteDTO extends DycpSolicitudDTO implements Serializable {

    @SuppressWarnings("compatibility:-6033772478547756845")
    private static final long serialVersionUID = 1L;

    private String tipoTramite;
    private String impuesto;
    private String concepto;
    private String periodo;
    private String estadoSolicitud;
    private Integer idEjercicio;
    private Integer idEstadoSolicitud;
    private String numControlDoc;
    private Integer idEstadoDoc;
    private Integer idEstadoReq;
    private BigDecimal montoADevolver;
    private String fechaPresentacionCadena;

    public ConsultarDevolucionesContribuyenteDTO() {
        super();
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public Integer getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setIdEstadoDoc(Integer idEstadoDoc) {
        this.idEstadoDoc = idEstadoDoc;
    }

    public Integer getIdEstadoDoc() {
        return idEstadoDoc;
    }

    public void setIdEstadoReq(Integer idEstadoReq) {
        this.idEstadoReq = idEstadoReq;
    }

    public Integer getIdEstadoReq() {
        return idEstadoReq;
    }

    public void setMontoADevolver(BigDecimal montoADevolver) {
        this.montoADevolver = montoADevolver;
    }

    public BigDecimal getMontoADevolver() {
        return montoADevolver;
    }

    public String getFechaPresentacionCadena() {
        return fechaPresentacionCadena;
    }

    public void setFechaPresentacionCadena(String fechaPresentacionCadena) {
        this.fechaPresentacionCadena = fechaPresentacionCadena;
    }
}
