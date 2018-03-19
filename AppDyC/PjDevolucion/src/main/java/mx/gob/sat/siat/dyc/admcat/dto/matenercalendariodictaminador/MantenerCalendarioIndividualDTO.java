package mx.gob.sat.siat.dyc.admcat.dto.matenercalendariodictaminador;

import mx.gob.sat.siat.dyc.domain.DyccCalDictaminDTO;

/**
 * Clase complementaria del DTO DyccCalDictaMinDTO
 * @author Alfredo Ramirez
 * @since 16/08/2013
 */
public class MantenerCalendarioIndividualDTO extends DyccCalDictaminDTO {

    @SuppressWarnings("compatibility:1172629265832058493")
    private static final long serialVersionUID = 1L;

    private String motivoDescripcion;
    private int idUnidad;
    private Long numCalendario;

    public void setMotivoDescripcion(String motivoDescripcion) {
        this.motivoDescripcion = motivoDescripcion;
    }

    public String getMotivoDescripcion() {
        return motivoDescripcion;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setNumCalendario(Long numCalendario) {
        this.numCalendario = numCalendario;
    }

    public Long getNumCalendario() {
        return numCalendario;
    }

}
