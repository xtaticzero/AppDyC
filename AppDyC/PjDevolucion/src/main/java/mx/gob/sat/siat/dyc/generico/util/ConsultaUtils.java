package mx.gob.sat.siat.dyc.generico.util;


public final class ConsultaUtils {

    private ConsultaUtils() {
    }

    private static ConsultaUtils instance = null;

    /**
     * Creador sincronizado para protegerse de posibles problemas multi-hilo
     */
    private static synchronized void createInstance() {
        if (instance == null) {
            instance = new ConsultaUtils();
        }
    }

    /**
     * Obtiene instancia
     *
     * @return
     */
    public static ConsultaUtils getInstance() {
        createInstance();
        return instance;
    }

    public static boolean consultaSiRfcAmparado(String rfc) {
        return true;
    }

    public static boolean desdeTramitesYNoEstaAmparado(String rfc) {
        return false;
    }
}
