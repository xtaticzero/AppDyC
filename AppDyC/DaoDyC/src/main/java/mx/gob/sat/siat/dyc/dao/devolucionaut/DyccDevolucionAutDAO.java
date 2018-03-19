/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.devolucionaut;


import java.util.Date;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author itlocal
 */
public interface DyccDevolucionAutDAO {
    String getNumeroConsecutivoDevolucionAutomatica ( String claveSir );
    boolean actualizaEstadoBloqueoSaldoIcep ( DyctSaldoIcepDTO saldoIcep );
    boolean insertarInconsistencia ( DycaSolInconsistDTO inconsistencia );
    String consultaReglaRNFDC35AGAFF ( String rfc );
    boolean validarRegresoDictaminadorA ( Integer numeEmpleado, Date fechaRegistro );
    boolean valida4DiasInhabilesContinuosA ( Integer numeEmpleado, Date fechaRegistro );
    boolean validarDiaActualA ( Integer numeEmpleado, Date fechaRegistro );
    boolean existeRegistroSolicitud ( String numeroControl ) throws SIATException;
    boolean existeRegistroSolicitud ( String numeroControl, String rfc , Long icep ) throws SIATException;
    void insertaServicioSol(DycpSolicitudDTO input, Integer numEmpleado) throws SIATException;
}
