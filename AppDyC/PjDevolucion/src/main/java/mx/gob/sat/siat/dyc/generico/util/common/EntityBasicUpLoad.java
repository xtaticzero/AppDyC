package mx.gob.sat.siat.dyc.generico.util.common;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;


public class EntityBasicUpLoad implements Serializable{


    @SuppressWarnings("compatibility:-8225203169566720743")
    private static final long serialVersionUID = -6281401019535039383L;

    public EntityBasicUpLoad() {
        super();
    }
    
    private int idConsecutivoDoc;
    private String nombre="";
    private long tamano;
    private String descripcion="";
    private boolean correcto;
    private transient UploadedFile archivoFisico;
    private String estatus;



    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setTamano(long tamano) {
        this.tamano = tamano;
    }

    public long getTamano() {
        return tamano;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    public boolean isCorrecto() {
        return correcto;
    }

    public void setArchivoFisico(UploadedFile archivoFisico) {
        this.archivoFisico = archivoFisico;
    }

    public UploadedFile getArchivoFisico() {
        return archivoFisico;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setIdConsecutivoDoc(int idConsecutivoDoc) {
        this.idConsecutivoDoc = idConsecutivoDoc;
    }

    public int getIdConsecutivoDoc() {
        return idConsecutivoDoc;
    }
}
