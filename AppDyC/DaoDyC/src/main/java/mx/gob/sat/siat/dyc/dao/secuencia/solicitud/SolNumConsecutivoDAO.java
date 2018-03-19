/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao.secuencia.solicitud;

import mx.gob.sat.siat.dyc.domain.secuencia.DycqNumControlDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;

/**
 *
 * @author GAER8674
 */
public interface SolNumConsecutivoDAO {

    DycqNumControlDTO getNumConsecutivo(String claveSir) throws SIATException;

    String getNumConsecutivoDevIva(String claveSir) throws DycDaoExcepcion;

    /**
     *
     * Antes de obtener el numero de secuencia se revisa si existe un registro
     * de fecha de reinicio de secuencia activo en la tabla
     * DYCT_REINICIOSECPARAM y se reinician las secuencias si es tiempo de
     * reiniciar, despues se inactiva la fecha de reinicio
     *
     * @param claveSir clave de la unidad administrativa
     * @return objeto DycqNumControlDTO con el numero de control generado por
     * DYCQ_NUMCTRLCOM
     */
    DycqNumControlDTO getNumConsecutivoCasoCom(String claveSir);

    DycqNumControlDTO getNumConsecutivoOrigenSafCC();

    Integer getNumConsecutivoSaldoIcep();

    Integer getNumConsecutivoDetalleIcep();

    Integer getFolioServicioTemp();

    Integer getIdArchivo();
    
    Integer getIdDeclaracion();    
    
    boolean existeNumeroControlSolicitud(String numeroControl);
    long obtenerValorSeqGuardadoMax(long claveADM, String filtroADM);
    void actualizarSecuenciaDeAdministracion(String claveSecuencia, long incremento);
    

}
