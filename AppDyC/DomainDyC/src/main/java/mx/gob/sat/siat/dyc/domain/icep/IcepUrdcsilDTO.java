/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.domain.icep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;


/**
 * @author Israel Chavez
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "vd_estatus", "vd_tipdec", "vd_fecpre", "vd_n_numero_operacion", "vd_saldo" })
public class IcepUrdcsilDTO extends Marshal {
    /**Entradas**/
    private String rfc;
    private String rfc1;
    private String rfc2;
    private String periodo;
    private String ejercicio;
    private String concepto;
    private String tipoTramite;
    private String usuario;

    /**Salidas**/
    private String estatus;
    private String tipoDeclaracion;
    private String fechPresentacion;
    private String numOper;
    private String saldoFavor;

    public IcepUrdcsilDTO() {
        super();
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @XmlTransient
    public String getRfc() {
        return rfc;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @XmlTransient
    public String getPeriodo() {
        return periodo;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    @XmlTransient
    public String getEjercicio() {
        return ejercicio;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    @XmlTransient
    public String getConcepto() {
        return concepto;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    @XmlTransient
    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public String getUsuario() {
        return usuario;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setTipoDeclaracion(String tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public String getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setFechPresentacion(String fechPresentacion) {
        this.fechPresentacion = fechPresentacion;
    }

    public String getFechPresentacion() {
        return fechPresentacion;
    }

    public void setNumOper(String numOper) {
        this.numOper = numOper;
    }

    public String getNumOper() {
        return numOper;
    }

    public void setSaldoFavor(String saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public String getSaldoFavor() {
        return saldoFavor;
    }

    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("RFC: " + this.getRfc() + ", ");
        sb.append("Periodo: " + this.getPeriodo() + ", ");
        sb.append("Ejercicio: " + this.getEjercicio() + ", ");
        sb.append("Concepto: " + this.getConcepto() + ", ");
        sb.append("Tipo Tramite: " + this.getTipoTramite() + ", ");
        sb.append("Usuario: " + this.getUsuario() + ", ");
        return sb.toString();
    }

    public IcepUrdcsilDTO retrieveDTOFromSolicitud(DycpSolicitudDTO dYCPSolicitudDTO) {

        IcepUrdcsilDTO icepUrdcsilDTO = new IcepUrdcsilDTO();

        icepUrdcsilDTO.setRfc(String.valueOf(dYCPSolicitudDTO.getDycpServicioDTO().getRfcContribuyente()));
        icepUrdcsilDTO.setRfc1(ConstantesDyC2.CADENA_VACIA);
        icepUrdcsilDTO.setRfc2(ConstantesDyC2.CADENA_VACIA);
        icepUrdcsilDTO.setConcepto(String.valueOf(dYCPSolicitudDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDyccConceptoDTO().getIdConcepto()));
        icepUrdcsilDTO.setTipoTramite(String.valueOf(dYCPSolicitudDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite()));
        icepUrdcsilDTO.setPeriodo(String.valueOf(dYCPSolicitudDTO.getDyctSaldoIcepDTO().getDyccPeriodoDTO().getIdPeriodo()));
        icepUrdcsilDTO.setEjercicio(String.valueOf(dYCPSolicitudDTO.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio()));
        icepUrdcsilDTO.setUsuario(ConstantesDyC.USR_STORED_PROCEDURES);

        return icepUrdcsilDTO;

    }

    public void setRfc1(String rfc1) {
        this.rfc1 = rfc1;
    }

    @XmlTransient
    public String getRfc1() {
        return rfc1;
    }

    public void setRfc2(String rfc2) {
        this.rfc2 = rfc2;
    }

    @XmlTransient
    public String getRfc2() {
        return rfc2;
    }
}
