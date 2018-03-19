/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.vo;

import java.util.Date;

/**
 * @author Federico Chopin Gachuz
 * @date Abril 11, 2014
 * */
public class DocumentoAdministrarSolVO {

    public DocumentoAdministrarSolVO() {
        super();
    }

    private String rfcContribuyente;
    private String rolDictaminado;
    private String tipoTramite;
    private Date fechaPresentacion;
    private Date fechaLimite;
    private Double importeSolicitado;
    private int tipoDia;
    private int dias;
    private int claveAdm;
    private String numControl;
    private String nombreDocumento;
    private String estadoDesc;
    private int idEstadoDoc;
    private String url;
    private String numControlDoc;

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
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setFechaLimite(Date fechaLimite) {
        if (null != fechaLimite) {
            this.fechaLimite = (Date)fechaLimite.clone();
        } else {
            this.fechaLimite = null;
        }
    }

    public Date getFechaLimite() {
        if (null != fechaLimite) {
            return (Date)fechaLimite.clone();
        } else {
            return null;
        }
    }

    public void setImporteSolicitado(Double importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public Double getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }

    public void setTipoDia(int tipoDia) {
        this.tipoDia = tipoDia;
    }

    public int getTipoDia() {
        return tipoDia;
    }

    public void setClaveAdm(int claveAdm) {
        this.claveAdm = claveAdm;
    }

    public int getClaveAdm() {
        return claveAdm;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
    }

    public String getEstadoDesc() {
        return estadoDesc;
    }

    public void setIdEstadoDoc(int idEstadoDoc) {
        this.idEstadoDoc = idEstadoDoc;
    }

    public int getIdEstadoDoc() {
        return idEstadoDoc;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }
}
