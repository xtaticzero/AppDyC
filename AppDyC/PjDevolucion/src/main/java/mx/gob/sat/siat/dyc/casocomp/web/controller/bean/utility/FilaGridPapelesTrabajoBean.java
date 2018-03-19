package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility;

public class FilaGridPapelesTrabajoBean
{
    private Integer idPapelTrabajo;
    private String nombreArchivo;
    private String descripcion;

    public Integer getIdPapelTrabajo() {
        return idPapelTrabajo;
    }

    public void setIdPapelTrabajo(Integer idPapelTrabajo) {
        this.idPapelTrabajo = idPapelTrabajo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
