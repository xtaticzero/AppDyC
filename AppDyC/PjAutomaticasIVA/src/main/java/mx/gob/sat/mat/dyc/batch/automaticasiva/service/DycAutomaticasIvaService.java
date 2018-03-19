/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.batch.automaticasiva.service;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.dyc.batch.automaticasiva.util.excepcion.DycAutomaticasIvaExcepcion;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.EDycAutomaticasIvaEstadoCasoDevolucion;
import mx.gob.sat.siat.dyc.domain.DyctDevAutoIvaDTO;
import mx.gob.sat.siat.dyc.vo.DycAutomaticasIvaProcesadoDTO;

/**
 *
 * @author GAER8674
 */
public interface DycAutomaticasIvaService {
    List<DyctDevAutoIvaDTO> consultarDevolucionesAutomaticasIVANuevas() throws DycAutomaticasIvaExcepcion;
    DycAutomaticasIvaProcesadoDTO procesarDevolucionAutomaticaIva(DyctDevAutoIvaDTO registroMorsa) throws DycAutomaticasIvaExcepcion;
    Integer guardarCasoDevolucionDatosIcep(String numeroControl, DyctDevAutoIvaDTO registroMorsa) throws DycAutomaticasIvaExcepcion;
    void cambiarDeclaracionMorsaEstado(DyctDevAutoIvaDTO registroMorsa, Integer estado);
    void guardarCasoDevolucionSolicitud(String numeroControl, DyctDevAutoIvaDTO registroMora, 
            String claveSir, IdCInterno rfcAmpliado, Integer idSaldoIcep, 
            Date fechaConsultaARFCAmpliado, EDycAutomaticasIvaEstadoCasoDevolucion estadoCasoDevolucion) throws DycAutomaticasIvaExcepcion;
    void afectacionControlSaldos(String numeroControl) throws DycAutomaticasIvaExcepcion;
    void integrarExpediente(String numeroControl, String rfc) throws DycAutomaticasIvaExcepcion;
}
