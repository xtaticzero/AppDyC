/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.batch.automaticasiva.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.dyc.batch.automaticasiva.util.constante.EDycAutomaticasIvaCodigoError;
import mx.gob.sat.mat.dyc.batch.automaticasiva.util.excepcion.DycAutomaticasIvaExcepcion;
import mx.gob.sat.siat.dyc.domain.DyctDevAutoIvaDTO;
import mx.gob.sat.siat.dyc.util.recurso.DycLogUtil;
import mx.gob.sat.siat.dyc.vo.DycAutomaticasIvaLoteProcesadoDTO;
import mx.gob.sat.siat.dyc.vo.DycAutomaticasIvaProcesadoDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author GAER8674
 */
@Component("dycAutomaticasIvaDel")
public class DycAutomaticasIvaDel {
    private final Logger log = Logger.getLogger(DycAutomaticasIvaDel.class);
    
    @Autowired
    @Qualifier("dycAutomaticasIvaService")
    private DycAutomaticasIvaServiceImpl dycAutomaticasIvaService;
    
    
    public DycAutomaticasIvaLoteProcesadoDTO procesarLoteDevolucionesAutomaticasIva() throws DycAutomaticasIvaExcepcion {
        log.info("procesarLoteDevolucionesAutomaticasIva - Inicio");
        DycAutomaticasIvaLoteProcesadoDTO dycAutomaticasIvaLoteProcesadoDTO = new DycAutomaticasIvaLoteProcesadoDTO();
        List<DycAutomaticasIvaProcesadoDTO> datosDevolucionesProcesadas = new ArrayList<DycAutomaticasIvaProcesadoDTO>();
        List<DycAutomaticasIvaProcesadoDTO> datosDevolucionesProcesadasDesistidas = new ArrayList<DycAutomaticasIvaProcesadoDTO>();
        List<DycAutomaticasIvaProcesadoDTO> datosDevolucionesNoProcesadas = new ArrayList<DycAutomaticasIvaProcesadoDTO>();
        int devolucionesProcesadas = 0;
        int devolucionesNoProcesadas = 0;
        
        List<DyctDevAutoIvaDTO> registrosMorsa = dycAutomaticasIvaService.consultarDevolucionesAutomaticasIVANuevas();
        
        for(DyctDevAutoIvaDTO registroMorsa : registrosMorsa){
            try {
                DycAutomaticasIvaProcesadoDTO procesadoDTO = dycAutomaticasIvaService.procesarDevolucionAutomaticaIva(registroMorsa);
                if(procesadoDTO.isDesistida()){
                    datosDevolucionesProcesadasDesistidas.add(procesadoDTO);
                }
                else{
                    datosDevolucionesProcesadas.add(procesadoDTO);
                }
                devolucionesProcesadas++;
            } catch (DycAutomaticasIvaExcepcion ex) {
                DycAutomaticasIvaProcesadoDTO procesadoDTO = ex.getDevolucionNoProcesada();
                datosDevolucionesNoProcesadas.add(procesadoDTO);
                devolucionesNoProcesadas++;
                log.error(DycLogUtil.generarMsgError(EDycAutomaticasIvaCodigoError.AUTOMATICAS_DEVOLUCION_LOTE_ERRORPARCIAL, ex.getEstadoVariables()), ex);
            }
        }
        
        dycAutomaticasIvaLoteProcesadoDTO.setDevolucionesProcesadas(devolucionesProcesadas);
        dycAutomaticasIvaLoteProcesadoDTO.setDevolucionesNoProcesadas(devolucionesNoProcesadas);
        dycAutomaticasIvaLoteProcesadoDTO.setDatosDevolucionesProcesadas(datosDevolucionesProcesadas);
        dycAutomaticasIvaLoteProcesadoDTO.setDatosDevolucionesProcesadasDesistidas(datosDevolucionesProcesadasDesistidas);
        dycAutomaticasIvaLoteProcesadoDTO.setDatosDevolucionesNoProcesadas(datosDevolucionesNoProcesadas);

        log.info("procesarLoteDevolucionesAutomaticasIva - Fin");
        return dycAutomaticasIvaLoteProcesadoDTO;
    }
}
