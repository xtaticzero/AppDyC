package mx.gob.sat.siat.dyc.util.constante;

import java.math.BigDecimal;

public final class ConstantesCompetenciaAgaff {

    private ConstantesCompetenciaAgaff() {
    }

    public static final String COMPETENCIA_AGAFF = "90,91,97";

    //Determinar Flujo de solicitud ConstantesDyC 1. No existe 2. Inofrmativo 3. Confiable
    public static final int EDO_CCI_ADMON_DEL_RIESGO = 1;
    public static final String TRAMITE_RANGO_101_A_108 = "101,102,103,104,105,106,107,108";
    public static final String TRAMITE_RANGO_111_A_112 = "111,112";
    public static final String TRAMITE_RANGO_115_A_116 = "115,116";
    public static final String TRAMITE_119 = "119";
    public static final String TRAMITE_RANGO_111_A_114 = "111,112,113,114";
    public static final String TRAMITE_RANGO_116_A_118 = "116,117,118";
    public static final String TRAMITE_RANGO_121_A_122 = "121,122";
    public static final BigDecimal PORC_PERM_COMP_IA_ISR_IETU = new BigDecimal(50);
    public static final BigDecimal PORC_PERM_COMP_IA_ISR_IETU_LIMP_SUP = new BigDecimal(80);
    public static final BigDecimal NUEVO_BIGDECIMAL_CERO = BigDecimal.ZERO;
    public static final int INCONSISTENCIA_MSJ1 = 2;
    public static final int INCONSISTENCIA_MSJ2 = 3;
}
