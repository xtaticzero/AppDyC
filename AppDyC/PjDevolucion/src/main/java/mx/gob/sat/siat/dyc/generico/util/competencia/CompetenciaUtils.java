package mx.gob.sat.siat.dyc.generico.util.competencia;

import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;


public final class CompetenciaUtils {

    private CompetenciaUtils() {
    }

    private static CompetenciaUtils instance = null;

    /**
     * Creador sincronizado para protegerse de posibles problemas multi-hilo
     */
    private static synchronized void createInstance() {
        if (instance == null) {
            instance = new CompetenciaUtils();
        }
    }

    /**
     * Obtiene instancia
     *
     * @return
     */
    public static CompetenciaUtils getInstance() {
        createInstance();
        return instance;
    }

    public static boolean esCompetenciaAGAFF(int claveAdm) {
        boolean esAgaff;
        switch (claveAdm) {
        case ConstantesDyCNumerico.VALOR_80:
        case ConstantesDyCNumerico.VALOR_81:
        case ConstantesDyCNumerico.VALOR_82:
        case ConstantesDyCNumerico.VALOR_90:
        case ConstantesDyCNumerico.VALOR_91:
        case ConstantesDyCNumerico.VALOR_97:
            esAgaff = Boolean.FALSE;
            break;
        default:
            esAgaff = Boolean.TRUE;
            break;
        }
        return esAgaff;
    }

    public static Integer identificaCompetencia(int claveAdm) {
        Integer competencia;
        switch (claveAdm) {
        case ConstantesDyCNumerico.VALOR_90:
        case ConstantesDyCNumerico.VALOR_91:
        case ConstantesDyCNumerico.VALOR_97:
            competencia = ConstantesIds.COMPETENCIA_AGGC;
            break;
        case ConstantesDyCNumerico.VALOR_80:
            competencia = ConstantesIds.COMPETENCIA_AGACE;
            break;
        case ConstantesDyCNumerico.VALOR_81:
        case ConstantesDyCNumerico.VALOR_82:
            competencia = ConstantesIds.COMPETENCIA_AGH;
            break;
        default:
            competencia = ConstantesIds.COMPETENCIA_AGGAF;
            break;
        }
        return competencia;
    }
}
