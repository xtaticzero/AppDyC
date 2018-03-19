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


/**
 * @author Israel Chavez
 * @version 0.2 J. Isaac Carbajal Bernal
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
         { "vd_estatus", "vd_tipdec", "vd_fecpre", "vd_n_numero_operacion", "vd_saldo"})
public class IcepUrdcFatDTO extends Marshal{
    
    /**Entradas*/
    private String rfc;
    private String boId;
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
    
    public IcepUrdcFatDTO() {
        super();
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    @XmlTransient
    public String getRfc() {
        return rfc;
    }
    
    public void setBoId(String boId) {
        this.boId = boId;
    }

    @XmlTransient
    public String getBoId() {
        return boId;
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
    
    public String getParameterReport(){
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("RFC: " + this.getRfc() + ", ");
        sb.append("Periodo: " + this.getPeriodo() + ", ");
        sb.append("Ejercicio: " + this.getEjercicio() + ", ");
        sb.append("Concepto: " + this.getConcepto() + ", ");
        sb.append("Tipo Tramite: " + this.getTipoTramite() + ", ");
        sb.append("Usuario: " + this.getUsuario() + ", ");
        return sb.toString();
    }
    
    public IcepUrdcFatDTO retrieveDTOFromSolicitud(DycpSolicitudDTO dYCPSolicitudDTO){
        
        IcepUrdcFatDTO icepUrdcFatDTO =  new IcepUrdcFatDTO();
        
        icepUrdcFatDTO.setRfc(String.valueOf(dYCPSolicitudDTO.getDycpServicioDTO().getRfcContribuyente()));
        icepUrdcFatDTO.setConcepto(String.valueOf(dYCPSolicitudDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDyccConceptoDTO().getIdConcepto()));
        icepUrdcFatDTO.setTipoTramite(String.valueOf(dYCPSolicitudDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite()));
        icepUrdcFatDTO.setPeriodo(String.valueOf(dYCPSolicitudDTO.getDyctSaldoIcepDTO().getDyccPeriodoDTO().getIdPeriodo()));
        icepUrdcFatDTO.setEjercicio(String.valueOf(dYCPSolicitudDTO.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio()));
        
        icepUrdcFatDTO.setUsuario(ConstantesDyC.USR_STORED_PROCEDURES);
        
        return icepUrdcFatDTO;
        
    }

}
