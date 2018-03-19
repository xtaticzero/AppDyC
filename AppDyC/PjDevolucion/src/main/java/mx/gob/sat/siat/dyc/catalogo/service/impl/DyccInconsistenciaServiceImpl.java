/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccInconsistenciaDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInconsistenciaService;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaInconsistTempDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para consulta de inconsistencias
 * @author Federico Chopin Gachuz
 *
 */
@Service("dyccInconsistenciaService")
public class DyccInconsistenciaServiceImpl implements DyccInconsistenciaService {
    public DyccInconsistenciaServiceImpl() {
        super();
    }

    @Autowired
    private DyccInconsistenciaDAO dyccInconsistenciaDAO;

    private static final Logger LOG = Logger.getLogger(DyccInconsistenciaServiceImpl.class);

    @Transactional(readOnly = true)
    public DyccInconsistDTO buscarInconsistencia(int idInconsistencia) {
        DyccInconsistDTO dyccInconsistenciaDTO = new DyccInconsistDTO();
        try {
            dyccInconsistenciaDTO = dyccInconsistenciaDAO.buscarInconsistencia(idInconsistencia);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dyccInconsistenciaDTO;
    }

    /**
     * Metodo de servicio para consulta de inconsistencias
     * @param String messageSol
     * @return Objeto <DycaSolInconsistDTO>
     *
     */
    @Transactional(readOnly = true)
    public List<DycaSolInconsistDTO> buscarSolicitudDev(String numControl) {
        List<DycaSolInconsistDTO> dycaSolInconsistDTO = new ArrayList<DycaSolInconsistDTO>();
        try {
            dycaSolInconsistDTO = dyccInconsistenciaDAO.buscarSolicitudDev(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dycaSolInconsistDTO;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<DyctDictAutomaticaDTO> buscarMotivosRiesgo(String numControl) {
        List<DyctDictAutomaticaDTO> dycMotivoRiesgoDTO = new ArrayList<DyctDictAutomaticaDTO>();
        try {
            dycMotivoRiesgoDTO = dyccInconsistenciaDAO.buscarMotivosRiesgo(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dycMotivoRiesgoDTO;
    }

    @Override
    public void insertarInconsistencia(DycaSolInconsistDTO dycaSolInconsistDTO) {
        try {
            dyccInconsistenciaDAO.insertarInconsistencia(dycaSolInconsistDTO);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }


    @Override
    public ParamOutputTO<CatalogoTO> findInconsistenciasTemp(int folio) throws SIATException {
        CatalogoTO inconsistenciaTemp = null;
        List<CatalogoTO> inconsistenciasTemp = new ArrayList<CatalogoTO>();
        try {
            for (DycaSolInconsistDTO outPut : dyccInconsistenciaDAO.findInconsistenciasTemp(folio)) {
                inconsistenciaTemp = new CatalogoTO();
                inconsistenciaTemp.setIdNum(outPut.getDyccInconsistDTO().getIdInconsistencia());
                inconsistenciaTemp.setDescripcion(outPut.getDescripcion());
                inconsistenciasTemp.add(inconsistenciaTemp);
            }
        } catch (DataAccessException dae) {
            LOG.error(dae.getMessage());
        }

        return new ParamOutputTO<CatalogoTO>(inconsistenciasTemp);
    }

    @Override
    public void createInconsitenciasTemp(DycaInconsistTempDTO inputSolTemp) throws SIATException {
        try {
            dyccInconsistenciaDAO.createInconsitenciasTemp(inputSolTemp);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
    }
}
