package mx.gob.sat.mat.dyc.asignartramite.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.dyc.asignartramite.service.DycaConfigDictaminadorService;
import mx.gob.sat.mat.dyc.asignartramite.service.DycaTipoServicioAsignarService;
import mx.gob.sat.siat.dyc.dao.asignartramite.DycaTipoServicioAsignarDAO;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaConfigDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaTipoServicioAsignarDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gregorio.Serapio
 */
@Service(value = "dycaTipoServicioAsignarService")
public class DycaTipoServicioAsignarServiceImpl implements DycaTipoServicioAsignarService {

    @Autowired
    private DycaTipoServicioAsignarDAO dycaTipoServicioAsignarDAO;

    @Autowired
    private DycaConfigDictaminadorService dycaConfigDictaminadorService;

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void insertar(Integer idEmpleado, Integer idMonto, List<DyccTipoServicioDTO> tipoServicioList, List<DycaOrigenTramiteDTO> tramitesList) throws SIATException {
        try {
            List<DycaTipoServicioAsignarDTO> serviciosAsignados = dycaTipoServicioAsignarDAO.obtenerTramXDictaminador(idEmpleado);
            List<DycaOrigenTramiteDTO> tramites = new ArrayList<DycaOrigenTramiteDTO>(tramitesList);
            DycaConfigDictaminadorDTO configDictaminadorDTO;
            DycaTipoServicioAsignarDTO asignarDTO;
            for (DyccTipoServicioDTO dTO : tipoServicioList) {
                asignarDTO = validarExisteTipoServicio(serviciosAsignados, dTO);

                if(asignarDTO == null) {
                    asignarDTO = new DycaTipoServicioAsignarDTO();
                    asignarDTO.setIdDictaminador(idEmpleado);
                    asignarDTO.setIdMonto(idMonto);
                    asignarDTO.setIdTipoServicio(dTO.getIdTipoServicio());
                    asignarDTO.setActivo(1);
                    asignarDTO = dycaTipoServicioAsignarDAO.insertar(asignarDTO);
                } else {
                    asignarDTO.setActivo(1);
                    asignarDTO.setIdMonto(idMonto);
                    dycaTipoServicioAsignarDAO.actualizar(asignarDTO);
                }

                List<DycaConfigDictaminadorDTO> tramitesAsignados = dycaConfigDictaminadorService.obtenerConfigDicXServicio(asignarDTO.getIdServicioAsignar());
                for (int i = 0; i < tramites.size(); i++) {
                    DycaOrigenTramiteDTO dycaOrigenTramite = tramites.get(i);

                    if (asignarDTO.getIdTipoServicio().equals(dycaOrigenTramite.getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio())) {
                        configDictaminadorDTO = validarExisteTramite(tramitesAsignados, dycaOrigenTramite.getDyccTipoTramiteDTO());

                        if(configDictaminadorDTO == null) {
                            configDictaminadorDTO = new DycaConfigDictaminadorDTO();
                            configDictaminadorDTO.setIdServicioAsignar(asignarDTO.getIdServicioAsignar());
                            configDictaminadorDTO.setIdTipoTramite(dycaOrigenTramite.getDyccTipoTramiteDTO().getIdTipoTramite());
                            configDictaminadorDTO.setActivo(1);
                            dycaConfigDictaminadorService.insertar(configDictaminadorDTO);
                        } else {
                            configDictaminadorDTO.setActivo(1);
                            dycaConfigDictaminadorService.actualizar(configDictaminadorDTO);
                        }

                        tramites.remove(i);
                        i--;
                    }
                }

                for (int i = 0; i < tramitesAsignados.size(); i++) {
                    configDictaminadorDTO = tramitesAsignados.get(i);
                    configDictaminadorDTO.setActivo(0);
                    dycaConfigDictaminadorService.actualizar(configDictaminadorDTO);
                }
            }

            for (DycaTipoServicioAsignarDTO servicioAsignar : serviciosAsignados) {
                servicioAsignar.setIdMonto(idMonto);
                servicioAsignar.setActivo(0);
                dycaTipoServicioAsignarDAO.actualizar(servicioAsignar);
                List<DycaConfigDictaminadorDTO> tramitesAsignados = dycaConfigDictaminadorService.obtenerConfigDicXServicio(servicioAsignar.getIdServicioAsignar());
                for (DycaConfigDictaminadorDTO configDictaminador : tramitesAsignados) {
                    configDictaminador.setActivo(0);
                    dycaConfigDictaminadorService.actualizar(configDictaminador);
                }
            }

        } catch (DAOException e) {
            throw new SIATException(e.getMensaje(), e);
        }
    }

    private DycaTipoServicioAsignarDTO validarExisteTipoServicio(List<DycaTipoServicioAsignarDTO> serviciosAsignados, DyccTipoServicioDTO servicioPorAsignar){
        for (int i = 0; i< serviciosAsignados.size(); i++) {
            if(serviciosAsignados.get(i).getIdTipoServicio().equals(servicioPorAsignar.getIdTipoServicio())){
                return serviciosAsignados.remove(i);
            }
        }
        return null;
    }

    private DycaConfigDictaminadorDTO validarExisteTramite(List<DycaConfigDictaminadorDTO> tramitesAsignados, DyccTipoTramiteDTO tramitePorAsignar){
        for (int i = 0; i< tramitesAsignados.size(); i++) {
            if(tramitesAsignados.get(i).getIdTipoTramite().equals(tramitePorAsignar.getIdTipoTramite())){
                return tramitesAsignados.remove(i);
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DycaTipoServicioAsignarDTO> obtenerTramXDictaminador(Integer idEmpleado) throws SIATException {
        try {
            return dycaTipoServicioAsignarDAO.obtenerTramXDictaminador(idEmpleado);
        } catch (DAOException e) {
            throw new SIATException(e.getMensaje(), e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<DycaTipoServicioAsignarDTO> obtenerTramXDictaminador(Integer idEmpleado, boolean activo) throws SIATException {
        try {
            return dycaTipoServicioAsignarDAO.obtenerTramXDictaminador(idEmpleado, activo);
        } catch (DAOException e) {
            throw new SIATException(e.getMensaje(), e);
        }
    }
}
