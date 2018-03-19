package mx.gob.sat.siat.dyc.registro.util;

public class RespuestaDTO {
    private String mensaje;
    private String redirecciona;
    private boolean esValido;
    
    public RespuestaDTO() {
        super();
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setRedirecciona(String redirecciona) {
        this.redirecciona = redirecciona;
    }

    public String getRedirecciona() {
        return redirecciona;
    }

    public void setEsValido(boolean esValido) {
        this.esValido = esValido;
    }

    public boolean isEsValido() {
        return esValido;
    }
}
