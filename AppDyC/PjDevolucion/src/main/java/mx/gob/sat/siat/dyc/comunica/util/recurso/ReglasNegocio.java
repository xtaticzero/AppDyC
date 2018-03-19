package mx.gob.sat.siat.dyc.comunica.util.recurso;

import java.util.List;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class ReglasNegocio {
    public ReglasNegocio() {
        super();
    }
    
    private static final int CASOCOMPENSACION6    = 6;
    private static final int CASOCOMPENSACION7    = 7;
    private static final int CASOCOMPENSACION8    = 8;
    private static final int ESTADOTRAMITE2       = 2;
    private static final int ESTADOTRAMITE5       = 5;
    private static final int ESTADOTRAMITE9       = 9;
    private static final int ESTADOTRAMITE10      = 10;
    private static final int ESTADOTRAMITE11      = 11;
    private static final int ESTADOTRAMITE12      = 12;
    private static final int SOLICITUDDEVOLUCION1 = 1;
    private static final int SOLICITUDDEVOLUCION2 = 2;
    private static final int SOLICITUDDEVOLUCION3 = 3;
    private static final int SOLICITUDDEVOLUCION4 = 4;
    private static final int SOLICITUDDEVOLUCION5 = 5;
    
    
    /**
     *  Valida la regla de negocio RNFDC957
     *  
     *  Esta regla consiste en que a partir de el tipo de resolucion actualizara estado del tramite.
     *  
     *  Tramite                         Tipo de resolución                                              Estado  del trámite
     * Solicitud de Devolución          1 - Autorizada Total                                            12- Autorizada Total
     *                                  2 - Autorizada Parcial con remanente negado                     11- Autorizada Parcial con remanente negado
     *                                  3 - Autorizada Parcial con remanente disponible                 10- Autorizada Parcial con remanente disponible
     *                                  4 - Negada                                                      9 - Negada
     *                                  5 - Desistida                                                   5 - Desistida
     *  Devolución automática           11- Autorizada Total                                            Autorizada Total
     *                                  12- Autorizada Parcial con Remante Disponible                   Autorizada Parcial con remanente disponible
     *                                  10- Rechazos e Inconsistencias de Devoluciones Automáticas      Desistida
     *
     * @param idTipoResolucion es el id que identifica al tipo de resolucion que tiene el servicio
     * @return id de estado de tramite.
     */
    public Integer validarReglaNegocioRNFDC957 (Integer idTipoResolucion) {
        Integer estadoTramite = 0;
        
        if (idTipoResolucion == SOLICITUDDEVOLUCION1) {
            estadoTramite = ESTADOTRAMITE12;
            
        } else if (idTipoResolucion == SOLICITUDDEVOLUCION2) {
            estadoTramite = ESTADOTRAMITE11;
            
        } else if (idTipoResolucion == SOLICITUDDEVOLUCION3) {
            estadoTramite = ESTADOTRAMITE10;
            
        } else if (idTipoResolucion == SOLICITUDDEVOLUCION4) {
            estadoTramite = ESTADOTRAMITE9;
            
        } else if (idTipoResolucion == SOLICITUDDEVOLUCION5) {
            estadoTramite = ESTADOTRAMITE5;
        }
        
        return estadoTramite;
    }
    
    /**
     *  Valida la regla de negocio RNFDC957
     *  
     *  Esta regla consiste en que a partir de el tipo de resolucion actualizara estado del tramite.
     *  
     *  Tramite                         Tipo de resolución                                              Estado  del trámite
     * Caso Compensación                6 - Saldo a favor improcedente                                  5 - Improcedente
     *                                  7 - Compensación Improcedente                                   5 - Improcedente
     *                                  8 - Registrar Caso de Compensación                              2 - Registrada
     *
     * @param idTipoResolucion es el id que identifica al tipo de resolucion que tiene el servicio
     * @return id de estado de tramite.
     */
    public Integer validarReglaNegocioCompRNFDC957 (Integer idTipoResolucion) {
        Integer estadoTramite = 0;
        
        if (idTipoResolucion == CASOCOMPENSACION6) {
            estadoTramite = ESTADOTRAMITE5;
            
        } else if (idTipoResolucion == CASOCOMPENSACION7) {
            estadoTramite = ESTADOTRAMITE5;
            
        } else if (idTipoResolucion == CASOCOMPENSACION8) {
            estadoTramite = ESTADOTRAMITE2;   
        }
        return estadoTramite;
    }
    
    
    /**
     *  Valida la regla de negocio RNFDC955
     *  
     *  Esta regla clasifica los documentos en requerimientos y resoluciones
     *  
     *
     * @param lista Elementos a validar en la regla
     * @return id de estado de tramite.
     */
    public boolean validarReglaNegocioCompRNFDC955 (List<Integer> lista, int id) {
        boolean banderaTramite = false;
        
        for (Integer elemento : lista) {
            if(id == elemento) {
                banderaTramite = Boolean.TRUE;
                break;
            }
        }
        return banderaTramite;
    }
}
