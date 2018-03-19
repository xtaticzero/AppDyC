package mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion;

import java.io.Serializable;

import org.primefaces.model.UploadedFile;


/**
 *
 * @author David Guerrero Reyes
 * @version 1.0
 * @see
 */

public class ArchivoSolventadoDTO implements Serializable{


    @SuppressWarnings("compatibility:-3638514457995555435")
    private static final long serialVersionUID = -8346857323915565492L;
    
    private long idConsecutivoDoc;
    private String nombre="";
    private long tamano;
    private String descripcion="";
    private boolean correcto;
    private transient UploadedFile archivoFisico;
    private String estatus;


    public void setIdConsecutivoDoc(long idConsecutivoDoc) {
        this.idConsecutivoDoc = idConsecutivoDoc;
    }

    public long getIdConsecutivoDoc() {
        return idConsecutivoDoc;
    }

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

    public void setTamano(long tamano) {
        this.tamano = tamano;
    }

    public long getTamano() {
        return tamano;
    }
}
