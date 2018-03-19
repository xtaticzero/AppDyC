package mx.gob.sat.siat.dyc.servicio.dto.declaraciones;

/**
 * Parametros de entrada para Interfaz 
 * ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales
 * [DWH_DFSD-36] 
 * @author J. Isaac Carbajal Bernal
 */
public class DeclaracionProvicionalIntVO {
    private String rfc1; 
    private String rfc2;
    private String rfc3;
    private int ejercicio;
    private int periodo;

    public void setRfc1(String rfc1) {
        this.rfc1 = rfc1;
    }

    public String getRfc1() {
        return rfc1;
    }

    public void setRfc2(String rfc2) {
        this.rfc2 = rfc2;
    }

    public String getRfc2() {
        return rfc2;
    }

    public void setRfc3(String rfc3) {
        this.rfc3 = rfc3;
    }

    public String getRfc3() {
        return rfc3;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getPeriodo() {
        return periodo;
    }
}
