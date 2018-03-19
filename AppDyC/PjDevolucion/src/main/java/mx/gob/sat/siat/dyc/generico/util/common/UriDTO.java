package mx.gob.sat.siat.dyc.generico.util.common;


public class UriDTO {

    private String id;
    private String nombre;
    private String uri;
    private String descripcion;


    public UriDTO(String nombre, String uri, String id, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.uri = uri;
        this.descripcion = descripcion;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
