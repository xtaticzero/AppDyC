package mx.gob.sat.siat.dyc.util.constante.enums;

public enum TipoAccionMovSaldo {
    INVALIDAR(1, "Se realiza acción para que el movimiento del saldo deje afectar en control de saldos"),
    VALIDAR(2, "Se realiza acción para que el movimiento del saldo vuelva a afectar en control de saldos"),
    CREAR(3, "Se crea un nuevo movimiento (abono o cargo) para un ICEP");

    private Integer id;
    private String descripcion;

    private TipoAccionMovSaldo(Integer i, String d) {
        id = i;
        descripcion = d;
    }

    public Integer getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
