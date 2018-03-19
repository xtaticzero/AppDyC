/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.batch.dictautomaticas.service.impl;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.batch.dictautomaticas.exception.DictAutomaticasException;
import mx.gob.sat.mat.batch.dictautomaticas.service.DyctDictAutomaticasService;
import mx.gob.sat.mat.dyc.automaticasiva.service.CrearCasoAutomaticasIvaService;
import mx.gob.sat.mat.dyc.controlsaldos.service.AfectarSaldosXDevolucionesService;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.EDycAutomaticasIvaEstadoCasoDevolucion;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.devolucionaut.DyctDicAutomaticaDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service(value = "dycDictAutomaticasService")
public class DyctDictAutomaticasServiceImpl implements DyctDictAutomaticasService {

    @Autowired
    private DyctDicAutomaticaDAO dyctDictAutomaticaDAO;

    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;

    @Autowired
    private CrearCasoAutomaticasIvaService crearCasoAutomaticasIvaService;

    @Autowired
    private AfectarSaldosXDevolucionesService afectarSaldosXDevolucionesService;

    @Override
    public List<DyctDictAutomaticaDTO> getTramitesDictaminados() {
        return dyctDictAutomaticaDAO.getTramitesDictaminados();
    }

    @Override
    public List<DyctDictAutomaticaDTO> getAllTramitesNoProcesados() {

        return dyctDictAutomaticaDAO.getAllTramitesNoProcesados();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DictAutomaticasException.class)
    public void procesarTramite ( DyctDictAutomaticaDTO tramite ) throws DictAutomaticasException {
        if ( tramiteSinRiesgo( tramite ) ) {
            generaResolucionTramite( tramite );
        } else {
            actualizaEstados( tramite, Boolean.TRUE );
        }
    }

    private boolean tramiteSinRiesgo ( DyctDictAutomaticaDTO tramite ){
        return tramite.getIdMarcResultado().equals( ConstantesDyC.CVE_SIN_RIESGO );
    }

    private void generaResolucionTramite ( DyctDictAutomaticaDTO tramite ) throws DictAutomaticasException {
        try {

            DycpSolicitudDTO solicitud = dycpSolicitudDAO.encontrar( tramite.getNumControl() );
            if ( solicitud != null ){
                aplicaResolucionSolicitud( solicitud, tramite );
            }

        } catch ( Exception ex ){
            String mensajeError = getMensajeErrorProcesamientoTramite( tramite );
            throw new DictAutomaticasException( mensajeError, ex );
        }
    }

    private String getMensajeErrorProcesamientoTramite ( DyctDictAutomaticaDTO tramite ){
        StringBuilder mensajeError = new StringBuilder();

        mensajeError
            .append( "Error al procesar el tramite:: " )
            .append( tramite.getNumControl() )
            .append( " " )
            .append( tramite.getRfc() );

        return mensajeError.toString();
    }

    private void aplicaResolucionSolicitud ( DycpSolicitudDTO solicitud, DyctDictAutomaticaDTO tramite ) 
                                                throws DycServiceExcepcion, SIATException, DictAutomaticasException {

        if ( rfcSolicitudTramiteIgual( solicitud, tramite ) && solicitudEstadoEnProceso( solicitud ) ){

            registraResolucion( solicitud, tramite );
            afectarSaldos( tramite );
            actualizaEstados( tramite, false );

        } else {
            String mensajeError = getMensajeErrorValidacionSolicitudTramite( solicitud, tramite );
            throw new DictAutomaticasException( mensajeError );
        }
    }

    private void registraResolucion ( DycpSolicitudDTO solicitud, DyctDictAutomaticaDTO tramite ) throws DycServiceExcepcion {
        Date fecha = new Date();

        crearCasoAutomaticasIvaService.insertarResolucion(
            tramite.getNumControl(),
            fecha, fecha,
            solicitud.getImporteSolicitado(), 
            EDycAutomaticasIvaEstadoCasoDevolucion.AUTORIZADA_TOTAL
        );
    }

    private void afectarSaldos ( DyctDictAutomaticaDTO tramite ) throws SIATException {
        afectarSaldosXDevolucionesService
            .afectarXResolucionDevAutomaticasIva( tramite.getNumControl() );
    }

    private String getMensajeErrorValidacionSolicitudTramite ( DycpSolicitudDTO solicitud, DyctDictAutomaticaDTO tramite ){
        /**
         * Se marca como error porque no pertenece al RFC o no esta
         * en estatus EN PROCESO
         */
        StringBuilder mensajeError = new StringBuilder();

        mensajeError
            .append( "No pertenece al RFC o no esta en estatus de EN PROCESO SIVAD/MORSA:: " )
            .append( tramite.getNumControl() ).append( " " )
            .append( tramite.getRfc() ).append( ":" )
            .append( solicitud.getDycpServicioDTO().getRfcContribuyente() ).append( " " )
            .append( solicitud.getDyccEstadoSolDTO().getDescripcion() );

        return mensajeError.toString();
    }

    private boolean rfcSolicitudTramiteIgual ( DycpSolicitudDTO solicitud, DyctDictAutomaticaDTO tramite ){
        String rfcSolicitud = solicitud.getDycpServicioDTO().getRfcContribuyente();
        String rfcTramite = tramite.getRfc();

        return rfcSolicitud.equalsIgnoreCase( rfcTramite );
    }

    private boolean solicitudEstadoEnProceso ( DycpSolicitudDTO solicitud ){
        return solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().equals( Constantes.EstadosSolicitud.EN_PROCESO_SIVAD.getIdEstadoSolicitud() ) || 
                solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().equals( Constantes.EstadosSolicitud.EN_PROCESO_MORSA.getIdEstadoSolicitud() );
    }

    private void actualizaEstados ( DyctDictAutomaticaDTO tramite, boolean isDesbloqueo ) throws DictAutomaticasException {
        try {
            int estado = getEstadoTramite( tramite, isDesbloqueo );

            dycpSolicitudDAO.actualizarStatus( estado, tramite.getNumControl() );
            dyctDictAutomaticaDAO.actualizarMarca( tramite.getNumControl() );

        } catch (SIATException ex) {
            String mensajeError = getMensajeErrorActualizacionEstadosTramite( tramite );
            throw new DictAutomaticasException( mensajeError, ex );
        }
    }

    private String getMensajeErrorActualizacionEstadosTramite ( DyctDictAutomaticaDTO tramite ){
        StringBuilder mensajeError = new StringBuilder();

        mensajeError
            .append( "Error al actualizar el tramite:: " )
            .append( tramite.getNumControl() );

        return mensajeError.toString();
    }

    private int getEstadoTramite ( DyctDictAutomaticaDTO tramite, boolean isDesbloqueo ){
        if ( isDesbloqueo ){
            return Constantes.EstadosSolicitud.EN_PROCESO.getIdEstadoSolicitud();
        }

        if ( tramite.getIdModelo() == ConstantesDyCNumerico.VALOR_1 ){
            return Constantes.EstadosSolicitud.AUTORIZADA_TOTAL_SIVAD.getIdEstadoSolicitud();
        }

        return Constantes.EstadosSolicitud.AUTORIZADA_TOTAL_MORSA.getIdEstadoSolicitud();
    }

}
