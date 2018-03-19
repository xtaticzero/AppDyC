package mx.gob.sat.siat.dyc.controlsaldos.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;

/**
 *
 * @author Gregorio.Serapio
 */
public final class DeclaracionUtil {

    private DeclaracionUtil() {
    }

    public static List<DyctDeclaraIcepDTO> obtenerDeclsEfectivas(List<DyctDeclaraIcepDTO> declaraciones) {
        List<DyctDeclaraIcepDTO> declsEfectivas = new ArrayList<DyctDeclaraIcepDTO>();

        if (declaraciones != null) {
            for (DyctDeclaraIcepDTO declaIt : declaraciones) {
                if (declaIt.getFechaFin() == null && declaIt.getValidacionDWH() != null) {
                    declsEfectivas.add(declaIt);
                }
            }
        }
        return declsEfectivas;
    }

    public static DyctDeclaraIcepDTO obtenerUtltimaDecEfectiva(List<DyctDeclaraIcepDTO> declaraciones) {
        return obtenerDecEfectiva(declaraciones, false);
    }

    public static DyctDeclaraIcepDTO obtenerPrimerDecEfectiva(List<DyctDeclaraIcepDTO> declaraciones) {
        return obtenerDecEfectiva(declaraciones, true);
    }

    private static DyctDeclaraIcepDTO obtenerDecEfectiva(List<DyctDeclaraIcepDTO> declaraciones, boolean primera) {
        if (declaraciones != null) {
            List<DyctDeclaraIcepDTO> declaraIcepDTOs = new ArrayList<DyctDeclaraIcepDTO>(declaraciones);
            if (!primera) {
                Collections.reverse(declaraIcepDTOs);
            }

            for (DyctDeclaraIcepDTO decIcep : declaraIcepDTOs) {
                if (decIcep.getFechaFin() == null && decIcep.getValidacionDWH() != null) {
                    return decIcep;
                }
            }
        }
        return null;
    }
}
