/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;


/**
 * @author JEFG
 * @since 12/04/14
 */
public class ArchivoVO implements Serializable {


    @SuppressWarnings("compatibility:-1860887878943757851")
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nombre;
    private String url;
    private String urlPlantillaAnexo;
    private String estado;
    private String nombreArchivo;
    private String descripcion;
    private Integer tramite;
    private String numControl;

    public ArchivoVO() {
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setUrlPlantillaAnexo(String urlPlantillaAnexo) {
        this.urlPlantillaAnexo = urlPlantillaAnexo;
    }

    public String getUrlPlantillaAnexo() {
        return urlPlantillaAnexo;
    }

    public void setTramite(Integer tramite) {
        this.tramite = tramite;
    }

    public Integer getTramite() {
        return tramite;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }
}
