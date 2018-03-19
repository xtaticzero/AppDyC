/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.regsolicitud;

import java.io.Serializable;

/**
 * Clase para los objetos de vista que se renderean, (se muestran y ocultan) de solventarRequerimiento.jsf
 * @author David Guerrero Reyes
 */

public class DocumentoReqDTO implements Serializable  {

    @SuppressWarnings("compatibility:-699588147320685685")
    private static final long serialVersionUID = -1784721573751887129L;
    private String nombreDocumento;
    private String tipoEntrega;
    private String archivo;
    private String estado;
    private String observaciones;
    
    public DocumentoReqDTO() {
        super();
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }
}
