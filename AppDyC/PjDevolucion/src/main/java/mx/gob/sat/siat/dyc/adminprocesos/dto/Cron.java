package mx.gob.sat.siat.dyc.adminprocesos.dto;

public class Cron {
    private Integer minuto;
    private Integer hora;
    private Integer numeroDia;
    private String  dia;
    private Integer numeroMes;
    private Integer posicion;
    private Integer banderaHora;
    private Integer banderaDia;
    private Integer banderaMes;
    private Integer banderaAnual;

    public void setMinuto(Integer minuto) {
        this.minuto = minuto;
    }

    public Integer getMinuto() {
        return minuto;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getHora() {
        return hora;
    }

    public void setNumeroDia(Integer numeroDia) {
        this.numeroDia = numeroDia;
    }

    public Integer getNumeroDia() {
        return numeroDia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDia() {
        return dia;
    }

    public void setNumeroMes(Integer numeroMes) {
        this.numeroMes = numeroMes;
    }

    public Integer getNumeroMes() {
        return numeroMes;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setBanderaHora(Integer banderaHora) {
        this.banderaHora = banderaHora;
    }

    public Integer getBanderaHora() {
        return banderaHora;
    }

    public void setBanderaDia(Integer banderaDia) {
        this.banderaDia = banderaDia;
    }

    public Integer getBanderaDia() {
        return banderaDia;
    }

    public void setBanderaMes(Integer banderaMes) {
        this.banderaMes = banderaMes;
    }

    public Integer getBanderaMes() {
        return banderaMes;
    }

    public void setBanderaAnual(Integer banderaAnual) {
        this.banderaAnual = banderaAnual;
    }

    public Integer getBanderaAnual() {
        return banderaAnual;
    }
}
