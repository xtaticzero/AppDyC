package mx.gob.sat.siat.dyc.domain.devolucionaut.constante;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public enum MarcResultado {

    SIN_RIESGO("01"),
    CON_RIESGO("02");

    private String codigo;

    private MarcResultado(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
