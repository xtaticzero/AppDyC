/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.domain.resolucion;

import java.util.Date;

/**
 *
 * @author root
 */
public class DyctResolSinDocumentoDTO {

    private int idResolSinDoc;
    private String numControl;
    private Date fechaRegistro;
    private int idTipoDocumento;
    private int idEstadoReq;
    private int idAprobador;
    private int idTipoFirma;
    private String cadenaOriginal;
    private String selloDigital;
    private String firma;

    public int getIdResolSinDoc() {
        return idResolSinDoc;
    }

    public void setIdResolSinDoc(int idResolSinDoc) {
        this.idResolSinDoc = idResolSinDoc;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public Date getFechaRegistro() {
        if (null != fechaRegistro) {
            return (Date) fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro) {
            this.fechaRegistro = (Date) fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public int getIdEstadoReq() {
        return idEstadoReq;
    }

    public void setIdEstadoReq(int idEstadoReq) {
        this.idEstadoReq = idEstadoReq;
    }

    public int getIdAprobador() {
        return idAprobador;
    }

    public void setIdAprobador(int idAprobador) {
        this.idAprobador = idAprobador;
    }

    public int getIdTipoFirma() {
        return idTipoFirma;
    }

    public void setIdTipoFirma(int idTipoFirma) {
        this.idTipoFirma = idTipoFirma;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getSelloDigital() {
        return selloDigital;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

}
