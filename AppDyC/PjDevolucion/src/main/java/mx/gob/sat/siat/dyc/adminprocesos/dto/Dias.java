package mx.gob.sat.siat.dyc.adminprocesos.dto;

public class Dias {
    private int numeroDia;
    private String valorDia;
    private String nombreDia;
    
    public Dias (int numeroDia, String valorDia, String nombreDia) {
            this.numeroDia = numeroDia;
            this.valorDia  = valorDia;
            this.nombreDia = nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNumeroDia(int numeroDia) {
        this.numeroDia = numeroDia;
    }

    public int getNumeroDia() {
        return numeroDia;
    }

    public void setValorDia(String valorDia) {
        this.valorDia = valorDia;
    }

    public String getValorDia() {
        return valorDia;
    }
}
