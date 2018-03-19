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
 * Clase DTO para el manejo de elementos en el formulario para
 * solventar un requerimiento (Documentos)
 * @author David Guerrero Reyes
 * @date Noviembre 19, 2013
 * @since 0.1
 *
 * */
public class AnexoRequeridoDTO implements Serializable {

    @SuppressWarnings ("compatibility:-6734762238066611006")
    private static final long serialVersionUID = 8807086775028267131L;
    
    private Long idTabla;
    private String numControl;
    private String numRequerimiento;
    private String descripcionAnexo;
    private Integer idAnexo;
    private Integer idTipoTramite;
    private String url;
    private String tipoEntrega;
    private ArchivoSolventadoDTO archivoSol;
    private List<ArchivoSolventadoDTO> listaArchivosSolventados;

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumRequerimiento(String numRequerimiento) {
        this.numRequerimiento = numRequerimiento;
    }

    public String getNumRequerimiento() {
        return numRequerimiento;
    }

    public void setDescripcionAnexo(String descripcionAnexo) {
        this.descripcionAnexo = descripcionAnexo;
    }

    public String getDescripcionAnexo() {
        return descripcionAnexo;
    }

    public void setIdAnexo(Integer idAnexo) {
        this.idAnexo = idAnexo;
    }

    public Integer getIdAnexo() {
        return idAnexo;
    }

    public void setIdTabla(Long idTabla) {
        this.idTabla = idTabla;
    }

    public Long getIdTabla() {
        return idTabla;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setArchivoSol(ArchivoSolventadoDTO archivoSol) {
        this.archivoSol = archivoSol;
    }

    public ArchivoSolventadoDTO getArchivoSol() {
        return archivoSol;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setListaArchivosSolventados(List<ArchivoSolventadoDTO> listaArchivosSolventados) {
        this.listaArchivosSolventados = listaArchivosSolventados;
    }

    public List<ArchivoSolventadoDTO> getListaArchivosSolventados() {
        return listaArchivosSolventados;
    }
}
