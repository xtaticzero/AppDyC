package mx.gob.sat.siat.dyc.admcat.service.casosverificar.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.service.casosverificar.RfcConfiablesService;
import mx.gob.sat.siat.dyc.dao.rfc.DycbEstadoRfcDAO;
import mx.gob.sat.siat.dyc.dao.rfc.DycpRfcDAO;
import mx.gob.sat.siat.dyc.domain.rfc.DycbEstadoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "rfcConfiablesService")
public class RfcConfiablesServiceImpl implements RfcConfiablesService {

    private Logger log = Logger.getLogger(RfcConfiablesServiceImpl.class);
    
    @Autowired
    private DycpRfcDAO dycpRfcDAO;

    @Autowired
    private DycbEstadoRfcDAO dycbEstadoRfcDAO;

    public RfcConfiablesServiceImpl() {
        super();
    }

    @Override
    public List<DycpRfcDTO> mostrarRfcConfiables(Integer activo, Integer inactivo, Integer padron) {
        return dycpRfcDAO.obtenerRfcConfiables(activo, inactivo, padron);
    }

    @Override
    public List<DycbEstadoRfcDTO> listaBitacoraCOnfiables(String rfc) {
        return dycbEstadoRfcDAO.buscaRegistros(rfc, ConstantesDyCNumerico.VALOR_1, ConstantesDyCNumerico.VALOR_2);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = SIATException.class)
    public void insertarConfiable(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException {
        try {
            DycpRfcDTO encontrar = dycpRfcDAO.encontrar(dycbEstadoRfcDTO.getDycpRfcDTO().getRfc().toUpperCase());
            if (encontrar != null && encontrar.getPadronConfiable() == ConstantesDyCNumerico.VALOR_0) {
                dycpRfcDAO.actualizarConfiable(dycbEstadoRfcDTO.getDycpRfcDTO());
                dycbEstadoRfcDAO.insertar(dycbEstadoRfcDTO);
            } else {
                dycpRfcDAO.insertar(dycbEstadoRfcDTO.getDycpRfcDTO());
                dycbEstadoRfcDAO.insertar(dycbEstadoRfcDTO);
            }
        } catch (SIATException e) {
            log.info("Se presento un error al insertar la informacion ");
            throw new SIATException(e);
        }
    }
    
    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = SIATException.class)
    public void modificarRfcConfiable(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException {
        try {
            dycpRfcDAO.actualizarConfiable(dycbEstadoRfcDTO.getDycpRfcDTO());
            dycbEstadoRfcDAO.insertar(dycbEstadoRfcDTO);
        } catch (SIATException e) {
            log.info("Ocurrio un error" + e.getMessage());
            throw new SIATException(e);
        }
    }

    public void setDycpRfcDAO(DycpRfcDAO dycpRfcDAO) {
        this.dycpRfcDAO = dycpRfcDAO;
    }

    public DycpRfcDAO getDycpRfcDAO() {
        return dycpRfcDAO;
    }

    public void setDycbEstadoRfcDAO(DycbEstadoRfcDAO dycbEstadoRfcDAO) {
        this.dycbEstadoRfcDAO = dycbEstadoRfcDAO;
    }

    public DycbEstadoRfcDAO getDycbEstadoRfcDAO() {
        return dycbEstadoRfcDAO;
    }


    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
}
