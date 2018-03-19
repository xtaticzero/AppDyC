/*
    *  Todos los Derechos Reservados 2013 SAT.
    *  Servicio de Administracion Tributaria (SAT).
    *
    *  Este software contiene informacion propiedad exclusiva del SAT considerada
    *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
    *  parcial o total.
    */
package mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion;

import java.io.Serializable;

import java.util.List;


/**
 *
 */

/**
 * Clase DTO para el manejo de elementos en el formulario para
 * solventar un requerimiento (Documentos)
 * @author David Guerrero Reyes
 * @date Noviembre 19, 2013
 * @since 0.1
 *
 * */
public class DocRequeridoDTO implements Serializable {

    @SuppressWarnings ("compatibility:-6399138236722159842")
    private static final long serialVersionUID = -8046242446634182869L;
    
    private Long idTabla;
    private String numControl;
    private String numControlDoc;
    private Integer idTipoTramite;
    private String descripcion;
    private Integer idInfoARequerir;
    private String tipoEntrega;
    private Integer orden;
    private List<ArchivoSolventadoDTO> archivoSol;


    public void setIdTabla(Long idTabla) {
        this.idTabla = idTabla;
    }

    public Long getIdTabla() {
        return idTabla;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdInfoARequerir(Integer idInfoARequerir) {
        this.idInfoARequerir = idInfoARequerir;
    }

    public Integer getIdInfoARequerir() {
        return idInfoARequerir;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setArchivoSol(List<ArchivoSolventadoDTO> archivoSol) {
        this.archivoSol = archivoSol;
    }

    public List<ArchivoSolventadoDTO> getArchivoSol() {
        return archivoSol;
    }
}
