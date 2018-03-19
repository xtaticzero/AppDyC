package mx.gob.sat.siat.dyc.util.constante;

public final class ConstantesConsultaDBLink {

    private ConstantesConsultaDBLink() {
    }

    public static final String CONSULTA_DUAL = "SELECT COUNT(*) V FROM DUAL@AGS";
    public static final String DB_LINK_TABLE = "[TABLE]";
    public static final String DB_LINK_QUERY = "SIAT_DYC.SEGT_CAMBIOADSCRIPCION C, DYCV_EMPLEADO S";
    public static final Double IMPORTE_NUEVAS_INVERSIONES = 1000000.0;

}
