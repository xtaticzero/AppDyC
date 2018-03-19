package mx.gob.sat.siat.dyc.domain.devolucionaut;

import java.util.Date;


/**
 * DTO de la tabla DYCT_WSAUTOMATICA
 * @author Mario Lizaola Ruiz
 */
public class DyctWsAutomaticaDTO {
    private Integer idWsAutomatica;
    private Integer idOperacion;
    private Date fechaRegistro;
    private String xmlRequest;
    private String xmlResponse;
    private String mensaje;

    public Integer getIdWsAutomatica() {
        return idWsAutomatica;
    }

    public void setIdWsAutomatica(Integer idWsAutomatica) {
        this.idWsAutomatica = idWsAutomatica;
    }

    public Integer getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public Date getFechaRegistro() {
        if (null != fechaRegistro){
            return (Date) fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro){
            this.fechaRegistro = (Date) fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public String getXmlRequest() {
        return xmlRequest;
    }

    public void setXmlRequest(String xmlRequest) {
        this.xmlRequest = xmlRequest;
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    public void setXmlResponse(String xmlResponse) {
        this.xmlResponse = xmlResponse;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
