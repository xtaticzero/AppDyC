package mx.gob.sat.mat.dyc.batch.devautomaticas.service.impl;

import mx.gob.sat.mat.dyc.batch.devautomaticas.dto.TramiteDTO;
import mx.gob.sat.mat.dyc.batch.devautomaticas.exception.DevAutomaticasException;
import mx.gob.sat.mat.dyc.batch.devautomaticas.service.DycAutomaticasService;
import mx.gob.sat.mat.dyc.controlsaldos.service.AfectarSaldosXDevolucionesService;
import mx.gob.sat.siat.dyc.resolucion.service.ResolucionService;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.EDycAutomaticasIvaEstadoCasoDevolucion;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
@Service(value = "dycAutomaticasService")
public class DycAutomaticasServiceImpl implements DycAutomaticasService {

    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;

    @Autowired
    private ResolucionService resolucionService;

    @Autowired
    private AfectarSaldosXDevolucionesService afectarSaldosXDevolucionesService;

    @Autowired
    private DyctResolucionDAO dyctResolucionDAO;

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void procesarTramite(TramiteDTO tramite) throws DevAutomaticasException {
        procesar(tramite);
    }
    
    private void procesar(TramiteDTO tramite) throws DevAutomaticasException {
        try {
            DycpSolicitudDTO dycpSolicitudDTO = dycpSolicitudDAO.encontrar(tramite.getNumControl());

            if (dycpSolicitudDTO != null) {

<<<<<<< .mine
                if (dycpSolicitudDTO.getDycpServicioDTO().getRfcContribuyente().equalsIgnoreCase(tramite.getRfc())) {
=======
                String rfcContribuyente = dycpSolicitudDTO.getDycpServicioDTO().getRfcContribuyente().trim();
                if (rfcContribuyente.equalsIgnoreCase(tramite.getRfc())) {
>>>>>>> .r11377

<<<<<<< .mine
                    if (dycpSolicitudDTO.getDyccEstadoSolDTO() == Constantes.EstadosSolicitud.EN_PROCESO 
                            || dycpSolicitudDTO.getDyccEstadoSolDTO() == Constantes.EstadosSolicitud.EN_PROCESO_SIVAD 
                            || dycpSolicitudDTO.getDyccEstadoSolDTO() == Constantes.EstadosSolicitud.EN_PROCESO_MORSA) {
                                                                        
                        if(dycpSolicitudDTO.getDyctResolucionDTO() != null && 
                                dycpSolicitudDTO.getDyctResolucionDTO().getDyccEstreSolDTO() != Constantes.EstadosResolucion.NO_APROBADA) {
                            /**
                            * Se marca como error porque el estatus de la resolucion esta en EN_APROBACION o APROBADA
                            */
                           throw new DevAutomaticasException("Tiene una resolucion en estatus " 
                                   + dycpSolicitudDTO.getDyctResolucionDTO().getDyccEstreSolDTO().getDescripcion());
                            
                        }
                        
                        DyctResolucionDTO dyctResolucionDTO = resolucionService.calcularActualizacionAutorizacionTotal(dycpSolicitudDTO);
                        if(dycpSolicitudDTO.getDyctResolucionDTO() == null) {
                            dyctResolucionDAO.insertar(dyctResolucionDTO);
                            
                        } else {
                            dyctResolucionDAO.actualizarResolucion(dyctResolucionDTO);
                            
                        }
                                                        
                        afectarSaldosXDevolucionesService.afectarXResolucionDevAutomaticasIva(dycpSolicitudDTO, dyctResolucionDTO);
                        dycpSolicitudDAO.actualizarStatus(EDycAutomaticasIvaEstadoCasoDevolucion.AUTORIZADA_TOTAL.getIdEstadoSolicitud(), tramite.getNumControl());
                                                    
                    } else {
                        /**
                         * Se marca como error porque no esta en estatus EN PROCESO
                         */
                        throw new DevAutomaticasException("El tramite tiene estatus " + dycpSolicitudDTO.getDyccEstadoSolDTO().getDescripcion());
                    }
=======
                    if (dycpSolicitudDTO.getDyccEstadoSolDTO() == Constantes.EstadosSolicitud.EN_PROCESO 
                            || dycpSolicitudDTO.getDyccEstadoSolDTO() == Constantes.EstadosSolicitud.EN_PROCESO_SIVAD 
                            || dycpSolicitudDTO.getDyccEstadoSolDTO() == Constantes.EstadosSolicitud.EN_PROCESO_MORSA) {
                                                                        
                        if(dycpSolicitudDTO.getDyctResolucionDTO() != null && 
                                dycpSolicitudDTO.getDyctResolucionDTO().getDyccEstreSolDTO() != Constantes.EstadosResolucion.NO_APROBADA) {
                            /**
                            * Se marca como error porque el estatus de la resolucion esta en EN_APROBACION o APROBADA
                            */
                           throw new DevAutomaticasException("Tiene una resolucion en estatus " 
                                   + dycpSolicitudDTO.getDyctResolucionDTO().getDyccEstreSolDTO().getDescripcion());
                            
                        }
                        resolucionService.calcularImportesControlDeSaldos(dycpSolicitudDTO);
                        DyctResolucionDTO dyctResolucionDTO = resolucionService.calcularActualizacionAutorizacionTotal(dycpSolicitudDTO);
                        if(dycpSolicitudDTO.getDyctResolucionDTO() == null) {
                            dyctResolucionDAO.insertar(dyctResolucionDTO);
                            
                        } else {
                            dyctResolucionDAO.actualizarResolucion(dyctResolucionDTO);
                            
                        }
                                                        
                        afectarSaldosXDevolucionesService.afectarXResolucionDevAutomaticasIva(dycpSolicitudDTO, dyctResolucionDTO);
                        dycpSolicitudDAO.actualizarStatus(EDycAutomaticasIvaEstadoCasoDevolucion.AUTORIZADA_TOTAL.getIdEstadoSolicitud(), tramite.getNumControl());
                                                    
                    } else {
                        /**
                         * Se marca como error porque no esta en estatus EN PROCESO
                         */
                        throw new DevAutomaticasException("El tramite tiene estatus " + dycpSolicitudDTO.getDyccEstadoSolDTO().getDescripcion());
                    }
>>>>>>> .r11377

                } else {
                    /**
                     * Se marca como error porque no pertenece al RFC
                     */
                    throw new DevAutomaticasException("Pertenece a otro RFC (" + dycpSolicitudDTO.getDycpServicioDTO().getRfcContribuyente() + ")");
                }
            } else {
                /**
                 * Se marca como error porque no se encontro el numero de
                 * control
                 */
                throw new DevAutomaticasException("Numero de control no encontrado en MAT DyC");
            }

        } catch (SIATException ex) {
            /**
             * Se marca como error porque ocurrio una excepcion
             */
            throw new DevAutomaticasException((ex.getDescripcion() == null ? ex.getMessage() : ex.getDescripcion()), ex);
        }
    }
}
