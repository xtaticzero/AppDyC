/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.domain;

import java.util.Date;

/**
 *
 * @author GAER8674
 */
public class DyctDevAutoIvaDTO {
    public static final String LOG_TOSTRING_SEPARADOR_VALOR = "=";
    public static final String LOG_TOSTRING_SEPARADOR_TUPLA = " || ";

    private Long numeroLote;
    private String nombreContribuyente;
    private String rfc;
    private String impuesto;
    private Integer concepto;
    private Integer ejercicio;
    private Integer periodo;
    private String nombreBanco;
    private String numeroCuentaClabe;
    private String tipoDeclaracion;
    private Date fechaHoraPresentacion;
    private Long numeroOperacion;
    private String importeSaldoFavor;
    private Date fechaHoraProcesamiento;
    private String marcaResultado;
    private String motivoRechazo;

    public DyctDevAutoIvaDTO() {}
    
    
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder("[");
        toString.append("numeroLote").append(LOG_TOSTRING_SEPARADOR_VALOR).append(this.getNumeroLote())
                .append(LOG_TOSTRING_SEPARADOR_TUPLA);
        toString.append("nombreContribuyente").append(LOG_TOSTRING_SEPARADOR_VALOR).append(this.getNombreContribuyente())
                .append(LOG_TOSTRING_SEPARADOR_TUPLA);
        toString.append("rfc").append(LOG_TOSTRING_SEPARADOR_VALOR).append(this.getRfc())
                .append(LOG_TOSTRING_SEPARADOR_TUPLA);
        toString.append("impuesto").append(LOG_TOSTRING_SEPARADOR_VALOR).append(this.getImpuesto())
                .append(LOG_TOSTRING_SEPARADOR_TUPLA);
        toString.append("concepto").append(LOG_TOSTRING_SEPARADOR_VALOR).append(this.getConcepto())
                .append(LOG_TOSTRING_SEPARADOR_TUPLA);
        toString.append("ejercicio").append(LOG_TOSTRING_SEPARADOR_VALOR).append(this.getEjercicio())
                .append(LOG_TOSTRING_SEPARADOR_TUPLA);
        toString.append("periodo").append(LOG_TOSTRING_SEPARADOR_VALOR).append(this.getPeriodo())
                .append(']');
        return toString.toString();
    }

    /**
     * @return the numeroLote
     */
    public Long getNumeroLote() {
        return numeroLote;
    }

    /**
     * @param numeroLote the numeroLote to set
     */
    public void setNumeroLote(Long numeroLote) {
        this.numeroLote = numeroLote;
    }

    /**
     * Obtiene el nombre o la razon social (Dependiendo del tipo de contribuyente)
     * @return the nombreContribuyente
     */
    public String getNombreContribuyente() {
        return nombreContribuyente;
    }

    /**
     * @param nombreContribuyente the nombreContribuyente to set
     */
    public void setNombreContribuyente(String nombreContribuyente) {
        this.nombreContribuyente = nombreContribuyente;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the impuesto
     */
    public String getImpuesto() {
        return impuesto;
    }

    /**
     * @param impuesto the impuesto to set
     */
    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    /**
     * @return the concepto
     */
    public Integer getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(Integer concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the ejercicio
     */
    public Integer getEjercicio() {
        return ejercicio;
    }

    /**
     * @param ejercicio the ejercicio to set
     */
    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    /**
     * @return the periodo
     */
    public Integer getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the nombreBanco
     */
    public String getNombreBanco() {
        return nombreBanco;
    }

    /**
     * @param nombreBanco the nombreBanco to set
     */
    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    /**
     * @return the numeroCuentaClabe
     */
    public String getNumeroCuentaClabe() {
        return numeroCuentaClabe;
    }

    /**
     * @param numeroCuentaClabe the numeroCuentaClabe to set
     */
    public void setNumeroCuentaClabe(String numeroCuentaClabe) {
        this.numeroCuentaClabe = numeroCuentaClabe;
    }

    /**
     * @return the tipoDeclaracion
     */
    public String getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    /**
     * @param tipoDeclaracion the tipoDeclaracion to set
     */
    public void setTipoDeclaracion(String tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    /**
     * @return the fechaHoraPresentacion
     */
    public Date getFechaHoraPresentacion() {
        if(null!=fechaHoraPresentacion){
            return (Date)fechaHoraPresentacion.clone();
        }
        else{
            return null;
        }
    }

    /**
     * @param fechaHoraPresentacion the fechaHoraPresentacion to set
     */
    public void setFechaHoraPresentacion(Date fechaHoraPresentacion) {
        if(null!=fechaHoraPresentacion){
            this.fechaHoraPresentacion = (Date)fechaHoraPresentacion.clone();
        }
        else{
            this.fechaHoraPresentacion = null;
        }
    }

    /**
     * @return the numeroOperacion
     */
    public Long getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * @param numeroOperacion the numeroOperacion to set
     */
    public void setNumeroOperacion(Long numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    /**
     * @return the fechaHoraProcesamiento
     */
    public Date getFechaHoraProcesamiento() {
        if(null!=fechaHoraProcesamiento){
            return (Date)fechaHoraProcesamiento.clone();
        }
        else{
            return null;
        }
    }

    /**
     * @param fechaHoraProcesamiento the fechaHoraProcesamiento to set
     */
    public void setFechaHoraProcesamiento(Date fechaHoraProcesamiento) {
        if(null!=fechaHoraProcesamiento){
            this.fechaHoraProcesamiento = (Date)fechaHoraProcesamiento.clone();
        }
        else{
            this.fechaHoraProcesamiento = null;
        }
    }

    /**
     * @return the marcaResultado
     */
    public String getMarcaResultado() {
        return marcaResultado;
    }

    /**
     * @param marcaResultado the marcaResultado to set
     */
    public void setMarcaResultado(String marcaResultado) {
        this.marcaResultado = marcaResultado;
    }

    /**
     * @return the motivoRechazo
     */
    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    /**
     * @param motivoRechazo the motivoRechazo to set
     */
    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    /**
     * @return the importeSaldoFavor
     */
    public String getImporteSaldoFavor() {
        return importeSaldoFavor;
    }

    /**
     * @param importeSaldoFavor the importeSaldoFavor to set
     */
    public void setImporteSaldoFavor(String importeSaldoFavor) {
        this.importeSaldoFavor = importeSaldoFavor;
    }
}
