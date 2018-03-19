package mx.gob.sat.mat.dyc.ws.util.constante;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public enum TipoExcepcion {

    DATA_OPERATION("DATA-001", "OPERACION NO VALIDA"),
    DATA_PARAMETER("DATA-002", "PARAMETRO REQUERIDO"),
    DATA_ELEMENT("DATA-003", "ELEMENTO REQUERIDO"),
    DATA_VALUE("DATA-004", "CONTENIDO NO VALIDO"),
    DATA_REQUIRED("DATA-005", "VALOR REQUERIDO"),
    INTEGRITY_PK("INTEGRITY-001", "NUMERO DE CONTROL YA REGISTRADO"),
    INTEGRITY_FK("INTEGRITY-002", "NUMERO DE CONTROL NO VALIDO"),
    INTEGRITY_RIESGO("INTEGRITY-003", "MOTIVO DE RIESGO NO VALIDO"),
    CONNECTION("CONNECTION-001", "ERROR EN CONEXION");

    private String codigo;
    private String descripcion;

    private TipoExcepcion(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
