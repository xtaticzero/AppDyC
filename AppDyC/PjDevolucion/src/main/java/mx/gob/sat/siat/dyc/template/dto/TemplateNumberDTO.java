/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.template.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;


/**
 * @author Yolanda Martínez Sánchez
 * @since 17/02/2014
 *
 */
@XmlType(propOrder = { "administracion" })
public class TemplateNumberDTO implements Serializable {
    public TemplateNumberDTO() {
        super();
    }

    @SuppressWarnings("compatibility:-442522290153171877777")
    private static final long serialVersionUID = 9L;
    private String administracion;
    private String concepto;
    private String impuestoOrigen;
    private String impuestoDestino;
    private String conceptoDestino;
    private String calleNum;
    private String cadena;
    private String colonia;
    private String entreCalles;
    private String cp;
    private String municipio;
    private String entidad;
    private String ejercicioOrigen;
    private String ejercicio;
    private String fechaNotificacion;
    private String fechaPresentacion;
    private String fechaComp;
    private String fechaSolventacion;
    private String importeTotalComp;
    private String importeSolicitado;
    private String razonSocial;
    private String numControl;
    private String numeroDocumento;
    private String origen;
    private String periodoOrigen;
    private String periodo;
    private String rfcContribuyente;
    private String ciudadAdmLocal;
    private String numOperacion;
    private String numControlDoc;
    private String fechaNotificacion2;
    private String claveAdministracion;
    private String motivoSinDeposito;
    private String curp;
    private String fraccion;
    private String nomFunAutorizado;
    private String domicilioAlaf;
    private String iniciales;
    private String leyenda;
    private String serieDocumental;
    private String domicilio;
    private String mesCompensa;
    private String anioCompensa;
    private String fundamentacion;
    private String importeActualizado;
    private String importeRecargo;
    private String importeNeto;
    private String boId;
    private String montoAplicar;
    private String tipoResolucion;

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public String getAdministracion() {
        return administracion;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setImpuestoOrigen(String impuestoOrigen) {
        this.impuestoOrigen = impuestoOrigen;
    }

    public String getImpuestoOrigen() {
        return impuestoOrigen;
    }

    public void setConceptoDestino(String conceptoDestino) {
        this.conceptoDestino = conceptoDestino;
    }

    public String getConceptoDestino() {
        return conceptoDestino;
    }


    public void setEjercicioOrigen(String ejercicioOrigen) {
        this.ejercicioOrigen = ejercicioOrigen;
    }

    public String getEjercicioOrigen() {
        return ejercicioOrigen;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setFechaNotificacion(String fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaPresentacion(String fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaComp(String fechaComp) {
        this.fechaComp = fechaComp;
    }

    public String getFechaComp() {
        return fechaComp;
    }

    public void setFechaSolventacion(String fechaSolventacion) {
        this.fechaSolventacion = fechaSolventacion;
    }

    public String getFechaSolventacion() {
        return fechaSolventacion;
    }

    public void setImporteTotalComp(String importeTotalComp) {
        this.importeTotalComp = importeTotalComp;
    }

    public String getImporteTotalComp() {
        return importeTotalComp;
    }

    public void setImporteSolicitado(String importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public String getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setPeriodoOrigen(String periodoOrigen) {
        this.periodoOrigen = periodoOrigen;
    }

    public String getPeriodoOrigen() {
        return periodoOrigen;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setCiudadAdmLocal(String ciudadAdmLocal) {
        this.ciudadAdmLocal = ciudadAdmLocal;
    }

    public String getCiudadAdmLocal() {
        return ciudadAdmLocal;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setFechaNotificacion2(String fechaNotificacion2) {
        this.fechaNotificacion2 = fechaNotificacion2;
    }

    public String getFechaNotificacion2() {
        return fechaNotificacion2;
    }

    public void setClaveAdministracion(String claveAdministracion) {
        this.claveAdministracion = claveAdministracion;
    }

    public String getClaveAdministracion() {
        return claveAdministracion;
    }

    public void setMotivoSinDeposito(String motivoSinDeposito) {
        this.motivoSinDeposito = motivoSinDeposito;
    }

    public String getMotivoSinDeposito() {
        return motivoSinDeposito;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCurp() {
        return curp;
    }


    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getColonia() {
        return colonia;
    }


    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCp() {
        return cp;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setCalleNum(String calleNum) {
        this.calleNum = calleNum;
    }

    public String getCalleNum() {
        return calleNum;
    }

    public void setEntreCalles(String entreCalles) {
        this.entreCalles = entreCalles;
    }

    public String getEntreCalles() {
        return entreCalles;
    }

    public void setFraccion(String fraccion) {
        this.fraccion = fraccion;
    }

    public String getFraccion() {
        return fraccion;
    }

    public void setNomFunAutorizado(String nomFunAutorizado) {
        this.nomFunAutorizado = nomFunAutorizado;
    }

    public String getNomFunAutorizado() {
        return nomFunAutorizado;
    }

    public void setDomicilioAlaf(String domicilioAlaf) {
        this.domicilioAlaf = domicilioAlaf;
    }

    public String getDomicilioAlaf() {
        return domicilioAlaf;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setSerieDocumental(String serieDocumental) {
        this.serieDocumental = serieDocumental;
    }

    public String getSerieDocumental() {
        return serieDocumental;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getCadena() {
        return cadena;
    }

    public void setMesCompensa(String mesCompensa) {
        this.mesCompensa = mesCompensa;
    }

    public String getMesCompensa() {
        return mesCompensa;
    }

    public void setAnioCompensa(String anioCompensa) {
        this.anioCompensa = anioCompensa;
    }

    public String getAnioCompensa() {
        return anioCompensa;
    }

    public void setFundamentacion(String fundamentacion) {
        this.fundamentacion = fundamentacion;
    }

    public String getFundamentacion() {
        return fundamentacion;
    }

    public void setImporteActualizado(String importeActualizado) {
        this.importeActualizado = importeActualizado;
    }

    public String getImporteActualizado() {
        return importeActualizado;
    }

    public void setImporteRecargo(String importeRecargo) {
        this.importeRecargo = importeRecargo;
    }

    public String getImporteRecargo() {
        return importeRecargo;
    }

    public void setImpuestoDestino(String impuestoDestino) {
        this.impuestoDestino = impuestoDestino;
    }

    public String getImpuestoDestino() {
        return impuestoDestino;
    }

    public void setBoId(String boId) {
        this.boId = boId;
    }

    public String getBoId() {
        return boId;
    }

    public void setImporteNeto(String importeNeto) {
        this.importeNeto = importeNeto;
    }

    public String getImporteNeto() {
        return importeNeto;
    }

    public void setMontoAplicar ( String montoAplicar ){
        this.montoAplicar = montoAplicar;
    }

    public String getMontoAplicar (){
        return montoAplicar;
    }

    public void setTipoResolucion ( String tipoResolucion ){
        this.tipoResolucion = tipoResolucion;
    }

    public String getTipoResolucion (){
        return tipoResolucion;
    }
}
