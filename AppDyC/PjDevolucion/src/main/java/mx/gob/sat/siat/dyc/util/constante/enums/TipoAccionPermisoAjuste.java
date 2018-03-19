package mx.gob.sat.siat.dyc.util.constante.enums;

public enum TipoAccionPermisoAjuste {
    OTORGAR(1, "Se otorga permiso para realizar ajustes en control de saldos"),
    REVOCAR(2, "Se revoca permiso para realizar ajustes en control de saldos");

    private Integer id;
    private String descripcion;

    private TipoAccionPermisoAjuste(Integer i, String d) {
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
