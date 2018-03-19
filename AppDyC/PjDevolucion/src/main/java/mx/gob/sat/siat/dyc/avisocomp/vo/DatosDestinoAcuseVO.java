package mx.gob.sat.siat.dyc.avisocomp.vo;

import java.io.Serializable;

import java.sql.Date;

import java.util.List;


public class DatosDestinoAcuseVO implements Serializable {

    @SuppressWarnings("compatibility:-7479505431729038843")
    private static final long serialVersionUID = 1L;

    private String fechaInicioTramite;
    private String fechaPresentacion;
    private String rfcContribuyente;
    private String folioAviso;
    private String nombreAdm;
    private String tipoAviso;
    private String concepto;
    private String ejercicio;
    private String numOperacionDec;
    private String periodo;
    private Date fechaPresentaDec;
    private Double importeCompensado;
    private String folioAvisoNormal;
    private String tipoPersona;
    private String personaMoral;
    private String personaFisica;
    private String cadenaOriginal;
    private String selloDigital;

    private List<DatosOrigenAcuseVO> listaDatosOrigen;

    public DatosDestinoAcuseVO() {
        super();
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setFolioAviso(String folioAviso) {
        this.folioAviso = folioAviso;
    }

    public String getFolioAviso() {
        return folioAviso;
    }

    public void setNombreAdm(String nombreAdm) {
        this.nombreAdm = nombreAdm;
    }

    public String getNombreAdm() {
        return nombreAdm;
    }

    public void setTipoAviso(String tipoAviso) {
        this.tipoAviso = tipoAviso;
    }

    public String getTipoAviso() {
        return tipoAviso;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setNumOperacionDec(String numOperacionDec) {
        this.numOperacionDec = numOperacionDec;
    }

    public String getNumOperacionDec() {
        return numOperacionDec;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setListaDatosOrigen(List<DatosOrigenAcuseVO> listaDatosOrigen) {
        this.listaDatosOrigen = listaDatosOrigen;
    }

    public List<DatosOrigenAcuseVO> getListaDatosOrigen() {
        return listaDatosOrigen;
    }

    public void setFolioAvisoNormal(String folioAvisoNormal) {
        this.folioAvisoNormal = folioAvisoNormal;
    }

    public String getFolioAvisoNormal() {
        return folioAvisoNormal;
    }


    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setPersonaMoral(String personaMoral) {
        this.personaMoral = personaMoral;
    }

    public String getPersonaMoral() {
        return personaMoral;
    }

    public void setPersonaFisica(String personaFisica) {
        this.personaFisica = personaFisica;
    }

    public String getPersonaFisica() {
        return personaFisica;
    }

    public void setFechaPresentaDec(Date fechaPresentaDec) {
        if (fechaPresentaDec != null) {
            this.fechaPresentaDec = (Date)fechaPresentaDec.clone();
        } else {
            this.fechaPresentaDec = null;
        }
    }

    public Date getFechaPresentaDec() {
        if (fechaPresentaDec != null) {
            return (Date)fechaPresentaDec.clone();
        } else {
            return null;
        }
    }

    public void setImporteCompensado(Double importeCompensado) {
        this.importeCompensado = importeCompensado;
    }

    public Double getImporteCompensado() {
        return importeCompensado;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getSelloDigital() {
        return selloDigital;
    }

    public void setFechaPresentacion(String fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaInicioTramite(String fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    public String getFechaInicioTramite() {
        return fechaInicioTramite;
    }
}
