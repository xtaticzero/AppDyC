package mx.gob.sat.siat.dyc.gestionsol.util.constante;

import java.math.BigDecimal;

public final class ConstantesGestionSol {
    private ConstantesGestionSol() {
        super();
    }
    
    
    public static final String ROL_REVISOR_CENTRAL = "SAT_DYC_REVISOR_CENT";
    public static final String ROL_REVISOR_DE_LEGALES = "SAT_DYC_APROB_LEGAL";
    
    
    public static final BigDecimal IMPORTE_MAYOR_REVISOR = new BigDecimal("10000000");
    public static final BigDecimal IMPORTE_MENOR_REVISOR = new BigDecimal("5000000");

    public static final String MSG_ACUSE_SIN_REACION = "No tiene Acuses Relacionados a su Número de Control";
    public static final String MSG_VERIFIQUE_DATOS = "Verifique!.. los datos de sesion del contribuyente no son correctos";
    
    
}
