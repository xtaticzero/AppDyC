package mx.gob.sat.siat.dyc.adminprocesos.dto;

public class RegistroFallidoDTO {
    public RegistroFallidoDTO() {
        super();
    }
    private String numControl;
    private String causaDeFallo;
    private String puntoDeMontaje;

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setCausaDeFallo(String causaDeFallo) {
        this.causaDeFallo = causaDeFallo;
    }

    public String getCausaDeFallo() {
        return causaDeFallo;
    }

    public void setPuntoDeMontaje(String puntoDeMontaje) {
        this.puntoDeMontaje = puntoDeMontaje;
    }

    public String getPuntoDeMontaje() {
        return puntoDeMontaje;
    }
}
