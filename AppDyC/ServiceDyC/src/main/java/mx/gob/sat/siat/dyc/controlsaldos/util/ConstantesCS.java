package mx.gob.sat.siat.dyc.controlsaldos.util;

import java.util.Arrays;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.util.constante.Constantes;


public final class ConstantesCS
{
    
    private ConstantesCS(){
    
    }

    public static final int BUSQUEDA_X_RFC = 1;
    public static final int BUSQUEDA_X_ICEP = 2;
    public static final int BUSQUEDA_X_NUMCONTROL = 3;

    public static final int MOVIMIENTO_PISTASAUDITORIA = 537;
    public static final int MOVIMIENTO_PISTASAUDITORIA_DEVOL = 548;
    
    public static final int NUM_TAB_DEVOLUCION = 0;
    public static final int NUM_TAB_COMPENSACION = 1;
    public static final int NUM_TAB = 4;

    public static final int EJERCICIO_2013 = 2013;
    
    public static final String LBL_DECLARA_MANUAL = "Manual";
    public static final String LBL_ORIGEN_DWH = "Data warehouse";
    public static final String LBL_VALIDADA_MANUALMENTE = "Validada manualmente";
    public static final String MSJ_DECL_NO_EFECTIVA = "Debido a que esta declaración no ha podido ser validada no es tomada en cuenta para calcular el saldo a favor del ICEP";
    
    private static final Integer[] DECLS_DOMS = new Integer[]{  Constantes.OrigenesDeclaracion.MANUAL,
                                                                Constantes.OrigenesDeclaracion.DATAWAREHOUSE,
                                                                Constantes.OrigenesDeclaracion.VALIDADA_EMPLEADO };
    
    private static final String[] LBLS_DECLS_DOMS = new String[] {  LBL_DECLARA_MANUAL,
                                                                    LBL_ORIGEN_DWH,
                                                                    LBL_VALIDADA_MANUALMENTE};
    
    public static Integer[] getDeclsDoms(){
        return DECLS_DOMS.clone();
    }

    public static String[] getLblsDeclsDoms(){
        return LBLS_DECLS_DOMS.clone();
    }

    public static boolean existenDeclsDominantes(List<DyctDeclaraIcepDTO> declsIcep){
        for(DyctDeclaraIcepDTO declaracion : declsIcep){
            if(Arrays.asList(DECLS_DOMS).contains(declaracion.getOrigenDeclara())
                || declaracion.getValidacionDWH() != null){
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    
}