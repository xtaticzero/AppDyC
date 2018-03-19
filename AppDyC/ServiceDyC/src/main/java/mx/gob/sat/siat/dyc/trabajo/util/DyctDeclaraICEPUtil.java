package mx.gob.sat.siat.dyc.trabajo.util;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.controlsaldos.util.EnumCompBigDecimal;
import mx.gob.sat.siat.dyc.controlsaldos.vo.DyctDeclaraIcepAuxVO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public final class DyctDeclaraICEPUtil {
    
    private DyctDeclaraICEPUtil() {
        super();
    }
    
    public static DyctDeclaraIcepDTO crear(Integer idSaldoIcep, Long numOperacion, Date fechaPresentacion, Integer idTipoDeclaracion){
        
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
        dyctSaldoIcepDTO.setIdSaldoIcep              (idSaldoIcep);
        
        DyccTipoDeclaraDTO dyccTipoDeclaracionDTO = new DyccTipoDeclaraDTO();
        dyccTipoDeclaracionDTO.setIdTipoDeclaracion  (idTipoDeclaracion);
        
        DyctDeclaraIcepDTO dyctDeclaraICEPDTO = new DyctDeclaraIcepDTO();
        dyctDeclaraICEPDTO.setNumOperacion           (numOperacion);
        dyctDeclaraICEPDTO.setFechaPresentacion      (fechaPresentacion);
        dyctDeclaraICEPDTO.setDyctSaldoIcepDTO       (dyctSaldoIcepDTO);
        dyctDeclaraICEPDTO.setDyccTipoDeclaraDTO (dyccTipoDeclaracionDTO);
     
        return dyctDeclaraICEPDTO;                                               
    }
    
    /**
     * Metodo que calcula el monto parcial cuando solo existe una declaracion.
     * @since 23-Ene-2014
     * @param listaDeclaracionesAux
     * @param montoAut
     */
    public static void identificarMontoDeclaracionesSimples(List<DyctDeclaraIcepAuxVO> listaDeclaracionesAux,
                                                     double montoAut) {
        
        BigDecimal saf = null;
        BigDecimal safDesRes = null;
        BigDecimal montoAutorizado = new BigDecimal(String.valueOf(montoAut));
        
        listaDeclaracionesAux.get(0).setMontoUsado(montoAutorizado);
        listaDeclaracionesAux.get(0).setDeclaracionUsada(Boolean.TRUE);
        
        saf = listaDeclaracionesAux.get(0).getSaldoAfavor();
        safDesRes = saf.subtract(montoAutorizado);
        
        listaDeclaracionesAux.get(0).setSaldoAfavorNuevo(safDesRes.doubleValue());
        listaDeclaracionesAux.get(0).setMontoCargo(montoAutorizado.doubleValue());
    }

    /**
     * Metodo que calcula el monto parcial en caso de declaraciones multiples.
     * @since 23-Ene-2014
     * @param listaDeclaracionesAux
     * @param montoAut 
     */
    public static void identificarMontoDeclaracionesMultiplesVarianteDos(List<DyctDeclaraIcepAuxVO> listaDeclaracionesAux,
                                                       double montoAut) throws SIATException {
        BigDecimal montoParcial = BigDecimal.ZERO;
        BigDecimal montoAcumulado = BigDecimal.ZERO;
        BigDecimal montoFaltante = BigDecimal.ZERO;     
        BigDecimal saf = null;
        BigDecimal safDesRes = null;
        BigDecimal safSiguiente = BigDecimal.ZERO;
        BigDecimal saldoMayor =BigDecimal.ZERO;
        BigDecimal montoAutorizado = new BigDecimal(String.valueOf(montoAut));
        int i = 0;
        /*3.1.1*/

        /*SAF de la primera declara cion*/
        saldoMayor = listaDeclaracionesAux.get(i).getSaldoAfavor();
        listaDeclaracionesAux.get(i).setDeclaracionUsada(Boolean.TRUE);
        
        montoParcial = saldoMayor;

        listaDeclaracionesAux.get(i).setMontoUsado(montoParcial);

        montoAcumulado = montoParcial;
        saf = listaDeclaracionesAux.get(i).getSaldoAfavor();
        safDesRes = saf.subtract(montoAcumulado);
        
        listaDeclaracionesAux.get(i).setSaldoAfavorNuevo (safDesRes.doubleValue());
        listaDeclaracionesAux.get(i).setMontoCargo       (montoAcumulado.doubleValue());

        for (i=1; i < listaDeclaracionesAux.size(); i++ ) {

            safSiguiente = listaDeclaracionesAux.get(i).getSaldoAfavor();
               
            //SAFsiguiente es mayor al SAFanterior
            //se realziara un cargo por el total de su saf
            if (safSiguiente.compareTo(saldoMayor)  == EnumCompBigDecimal.MAYOR.getId()){
                listaDeclaracionesAux.get(i).setDeclaracionUsada(Boolean.TRUE); 
                saldoMayor = safSiguiente;
                
                if ( montoAcumulado.doubleValue() < montoAutorizado.doubleValue()) {
                    montoParcial  = safSiguiente.subtract(montoAcumulado);
                    montoFaltante = montoAutorizado.subtract(montoAcumulado);
                    
                    //si solo falta 100 de uan declaraciond e 200, se toma nada mas el faltamte :
                    if ((montoFaltante.compareTo(montoParcial) == EnumCompBigDecimal.MENOR.getId()) 
                        || (montoFaltante.compareTo(montoParcial) == EnumCompBigDecimal.IGUAL.getId()) )   {   
                        montoParcial = montoFaltante;
                    }
                    montoAcumulado = montoAcumulado.add(montoParcial);
    
                    listaDeclaracionesAux.get(i).setMontoUsado (montoParcial);
                    
                    //se calcula su nuevo saldo a favor:
                    saf = listaDeclaracionesAux.get(i).getSaldoAfavor();
                    safDesRes = saf.subtract (montoAcumulado);
                    
                    listaDeclaracionesAux.get(i).setSaldoAfavorNuevo (safDesRes.doubleValue());
                    listaDeclaracionesAux.get(i).setMontoCargo       (montoAcumulado.doubleValue());  
                
                //para las demas declaraciones de donde no se extrajo dinero
                } else { 
                    saf = listaDeclaracionesAux.get(i).getSaldoAfavor();
                    safDesRes = saf.subtract (montoAcumulado);
                    
                    listaDeclaracionesAux.get(i).setSaldoAfavorNuevo (safDesRes.doubleValue());
                    listaDeclaracionesAux.get(i).setSaldoAfavorNuevo (safDesRes.doubleValue());
                    listaDeclaracionesAux.get(i).setMontoCargo       (montoAcumulado.doubleValue());
                    listaDeclaracionesAux.get(i).setMontoUsado       (BigDecimal.ZERO);
                }
                
            //Si es una declaracion con SAF menor al saf anterior:
            } else {
                listaDeclaracionesAux.get(i).setSAFMenor     (Boolean.TRUE);
                listaDeclaracionesAux.get(i).setMontoUsado   (BigDecimal.ZERO);
                listaDeclaracionesAux.get(i).setSaldoAfavorNuevo (0d);
                listaDeclaracionesAux.get(i).setMontoCargo   (listaDeclaracionesAux.get(i).getSaldoAfavor().doubleValue());
            } 
        }
    }
    
    public static void identificarMontoDeclaracionesMultiples(List<DyctDeclaraIcepAuxVO> listaDeclaracionesAux,
                                                       double montoAut) throws SIATException {
       BigDecimal saf = null;
       BigDecimal safDesRes = null;
       BigDecimal montoAcumulado = BigDecimal.ZERO;
       BigDecimal montoAutorizado = new BigDecimal(String.valueOf(montoAut));
       
       /** (montoAutorizado.doubleValue() <= listaDeclaracionesAux.get(0).getSaldoAfavor().doubleValue() **/
       if ( (montoAutorizado.compareTo(listaDeclaracionesAux.get(0).getSaldoAfavor()) == EnumCompBigDecimal.MENOR.getId())
           || (montoAutorizado.compareTo(listaDeclaracionesAux.get(0).getSaldoAfavor()) == EnumCompBigDecimal.IGUAL.getId()) ) {
       
       
           listaDeclaracionesAux.get(0).setMontoUsado(montoAutorizado);
           listaDeclaracionesAux.get(0).setDeclaracionUsada(Boolean.TRUE);
           
           saf = listaDeclaracionesAux.get(0).getSaldoAfavor();
           safDesRes = saf.subtract(montoAutorizado);
           
           listaDeclaracionesAux.get(0).setSaldoAfavorNuevo(safDesRes.doubleValue());
           listaDeclaracionesAux.get(0).setMontoCargo(montoAutorizado.doubleValue());
           
           montoAcumulado = montoAcumulado.add( montoAutorizado);
            
            for (int i=1; i < listaDeclaracionesAux.size(); i++ ) { 
                
                saf = listaDeclaracionesAux.get(i).getSaldoAfavor();
                safDesRes = saf.subtract(montoAcumulado);
                
                listaDeclaracionesAux.get(i).setSaldoAfavorNuevo (safDesRes.doubleValue());
                listaDeclaracionesAux.get(i).setSaldoAfavorNuevo (safDesRes.doubleValue());
                listaDeclaracionesAux.get(i).setMontoCargo       (montoAcumulado.doubleValue());    
                listaDeclaracionesAux.get(i).setMontoUsado       (BigDecimal.ZERO);
            }
       }else{
           identificarMontoDeclaracionesMultiplesVarianteDos(listaDeclaracionesAux, montoAut);
       }
    
   }

}
