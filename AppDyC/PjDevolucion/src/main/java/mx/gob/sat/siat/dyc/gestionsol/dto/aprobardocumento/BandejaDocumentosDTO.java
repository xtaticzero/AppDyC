package mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento;

import java.math.BigDecimal;

import java.util.Date;


/**
 * DTO para la aprobacion del documento
 * @author Ericka Janeth Ibarra Ponce
 * @since 13/01/2014
 */

public class BandejaDocumentosDTO {
    public BandejaDocumentosDTO() {
        super();
    }

    private Date   fechaFinalizacion;
    private Date   fechaPresentacion;
    private Date   fechaRegistro;
    
    private BigDecimal importeSolicitado;
    
    private int    amparado;
    private int    cveAdministracion;
    private int    cveNivel;
    private int    dias;
    private int    idEjercicio;
    private int    idInconsistencia;
    private int    idOrigenSaldo;
    private int    idPeriodo;    
    private int    idPlantilla;
    private int    idTipoDocumento;
    private int    idtiposervicio;
    private int    idtipotramite;
    private int    numEmpleadoAprob;
    private int    resolAutomatica;
    private int    tipoPlazo;  
    private int    idSaldoIcep;
    
    private String apellidoDictaminador;
    private String dictaminador;
    private String nombreDictaminador;
    private String numControl;
    private String numControlDoc;
    private String razonSocial;
    private String rfcContribuyente;
    private String rolDictaminado;
    private String tipoServicio;
    private String tipoTramite;
    private String nombreDocumento;
                    
    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setRolDictaminado(String rolDictaminado) {
        this.rolDictaminado = rolDictaminado;
    }

    public String getRolDictaminado() {
        return rolDictaminado;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (fechaPresentacion != null) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if(fechaPresentacion != null){
            return (Date)fechaPresentacion.clone();    
        }else{
            return null;
        }
        
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        if (fechaFinalizacion != null) {
            this.fechaFinalizacion = (Date)fechaFinalizacion.clone();
        } else {
            this.fechaFinalizacion = null;
        }
    }

    public Date getFechaFinalizacion() {
        if(fechaFinalizacion != null){
            return (Date)fechaFinalizacion.clone();    
        }else{
            return null;
        }
        
    }

    public void setDictaminador(String dictaminador) {
        this.dictaminador = dictaminador;
    }

    public String getDictaminador() {
        return dictaminador;
    }

    public void setIdtiposervicio(int idtiposervicio) {
        this.idtiposervicio = idtiposervicio;
    }

    public int getIdtiposervicio() {
        return idtiposervicio;
    }

    public void setResolAutomatica(int resolAutomatica) {
        this.resolAutomatica = resolAutomatica;
    }

    public int getResolAutomatica() {
        return resolAutomatica;
    }

    public void setNombreDictaminador(String nombreDictaminador) {
        this.nombreDictaminador = nombreDictaminador;
    }

    public String getNombreDictaminador() {
        return nombreDictaminador;
    }

    public void setApellidoDictaminador(String apellidoDictaminador) {
        this.apellidoDictaminador = apellidoDictaminador;
    }

    public String getApellidoDictaminador() {
        return apellidoDictaminador;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setIdInconsistencia(int idInconsistencia) {
        this.idInconsistencia = idInconsistencia;
    }

    public int getIdInconsistencia() {
        return idInconsistencia;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }

    public void setTipoPlazo(int tipoPlazo) {
        this.tipoPlazo = tipoPlazo;
    }

    public int getTipoPlazo() {
        return tipoPlazo;
    }

    public void setIdPlantilla(int idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public int getIdPlantilla() {
        return idPlantilla;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setCveAdministracion(int cveAdministracion) {
        this.cveAdministracion = cveAdministracion;
    }

    public int getCveAdministracion() {
        return cveAdministracion;
    }

    public void setIdtipotramite(int idtipotramite) {
        this.idtipotramite = idtipotramite;
    }

    public int getIdtipotramite() {
        return idtipotramite;
    }

    public void setIdOrigenSaldo(int idOrigenSaldo) {
        this.idOrigenSaldo = idOrigenSaldo;
    }

    public int getIdOrigenSaldo() {
        return idOrigenSaldo;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setNumEmpleadoAprob(int numEmpleadoAprob) {
        this.numEmpleadoAprob = numEmpleadoAprob;
    }

    public int getNumEmpleadoAprob() {
        return numEmpleadoAprob;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }
    
    public Date getFechaRegistro() {
        if(fechaRegistro != null){
            return (Date)fechaRegistro.clone();    
        }else{
            return null;
        }
        
    }

    public void setCveNivel(int cveNivel) {
        this.cveNivel = cveNivel;
    }

    public int getCveNivel() {
        return cveNivel;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setAmparado(int amparado) {
        this.amparado = amparado;
    }

    public int getAmparado() {
        return amparado;
    }

    public void setIdSaldoIcep(int idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public int getIdSaldoIcep() {
        return idSaldoIcep;
    }
}
