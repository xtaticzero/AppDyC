package mx.gob.sat.siat.dyc.domain.icep;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


/**
 * @author Israel Chavez
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
         { "rfc", "periodo", "ejercicio", "concepto", "tipoTramite", "usuario", "estatus", "tipoDeclaracion",
           "fechPresentacion", "numOper", "saldoFavor" })
@XmlRootElement(name = "icepSioUrucple1DTO")
public class IcepSioUrucple1DTO extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:-8342971318538718974")
    private static final long serialVersionUID = 1L;

    /**Entradas*/
    @XmlElement
    private String rfc;
    @XmlElement
    private String periodo;
    @XmlElement
    private String ejercicio;
    @XmlElement
    private String concepto;
    @XmlElement
    private String tipoTramite;
    @XmlElement
    private String usuario;

    /**Salidas**/
    @XmlElement
    private String estatus;
    @XmlElement
    private String tipoDeclaracion;
    @XmlElement
    private String fechPresentacion;
    @XmlElement
    private String numOper;
    @XmlElement
    private String saldoFavor;

    public IcepSioUrucple1DTO() {
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

    public IcepSioUrucple1DTO retrieveDTOFromSolicitud(DycpSolicitudDTO dYCPSolicitudDTO) {

        IcepSioUrucple1DTO icepSioUrucple1DTO = new IcepSioUrucple1DTO();

        icepSioUrucple1DTO.setRfc(String.valueOf(dYCPSolicitudDTO.getDycpServicioDTO().getRfcContribuyente()));
        icepSioUrucple1DTO.setConcepto(String.valueOf(dYCPSolicitudDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDyccConceptoDTO().getIdConcepto()));
        icepSioUrucple1DTO.setTipoTramite(String.valueOf(dYCPSolicitudDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite()));
        icepSioUrucple1DTO.setPeriodo(String.valueOf(dYCPSolicitudDTO.getDyctSaldoIcepDTO().getDyccPeriodoDTO().getIdPeriodo()));
        icepSioUrucple1DTO.setEjercicio(String.valueOf(dYCPSolicitudDTO.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio()));
        icepSioUrucple1DTO.setUsuario(ConstantesDyC.USR_STORED_PROCEDURES);

        return icepSioUrucple1DTO;

    }

}

