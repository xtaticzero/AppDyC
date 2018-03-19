/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.casocomp.service.districomp.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.casocomp.dao.districomp.RecuperarCasosCompenDAO;
import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.casocomp.service.districomp.RecuperarCasosCompenService;
import mx.gob.sat.siat.dyc.dao.util.DyctCasoPendienteDAO;
import mx.gob.sat.siat.dyc.domain.caso.DyctCasoPendienteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value="recuperarCasosCompenService")
public class RecuperarCasosCompenServiceImpl implements RecuperarCasosCompenService{
    
    @Autowired
    private DyctCasoPendienteDAO dyctCasoPendienteDAO;
    
    @Autowired
    private RecuperarCasosCompenDAO recuperarCasosCompenDAO;
    
    private Logger log = Logger.getLogger(RecuperarCasosCompenServiceImpl.class.getName());
    
    public RecuperarCasosCompenServiceImpl() {
        super();
    }
    
    public void setDyctCasoPendienteDAO(DyctCasoPendienteDAO dyctCasoPendienteDAO) {
        this.dyctCasoPendienteDAO = dyctCasoPendienteDAO;
    }

    public DyctCasoPendienteDAO getDyctCasoPendienteDAO() {
        return dyctCasoPendienteDAO;
    }
    
    public void setRecuperarCasosCompenDAO(RecuperarCasosCompenDAO recuperarCasosCompenDAO) {
        this.recuperarCasosCompenDAO = recuperarCasosCompenDAO;
    }

    public RecuperarCasosCompenDAO getRecuperarCasosCompenDAO() {
        return recuperarCasosCompenDAO;
    }
    
    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    @Override
    public List<CasoCompensacionVO> obtenDeclaracionesPorIdDeclaracion() {
        List<CasoCompensacionVO> declaraciones = new ArrayList();
        List<DyctCasoPendienteDTO> declaracionesPendientes = dyctCasoPendienteDAO.obtenerDeclaraciones();
        
        for(DyctCasoPendienteDTO dyctCasoPen : declaracionesPendientes){
            DyctCasoPendienteDTO dyctCasoPenDTO = new DyctCasoPendienteDTO();
            
            dyctCasoPenDTO.setIdDeclaracion(dyctCasoPen.getIdDeclaracion());
            
            dyctCasoPenDTO.setIdImpuesto(dyctCasoPen.getIdImpuesto());
            dyctCasoPenDTO.setIdConcepto(dyctCasoPen.getIdConcepto());
            
            dyctCasoPenDTO.setIdEjercicio(dyctCasoPen.getIdEjercicio());
            dyctCasoPenDTO.setIdPeriodo(dyctCasoPen.getIdPeriodo());
            
            dyctCasoPenDTO.setNumOperacion(dyctCasoPen.getNumOperacion());
            
            List<CasoCompensacionVO> declaracionPorId = recuperarCasosCompenDAO.obtenDeclaracionesPorIdDeclaracion(dyctCasoPenDTO);
            declaraciones.addAll(declaracionPorId);
        }        
        return declaraciones;
    }


    @Override
    @Transactional(readOnly = true)
    public void validaInsertarCasoPen(DyctCasoPendienteDTO dyctCasoPenDTO)throws SIATException {
        Integer registros = dyctCasoPendienteDAO.buscarCasoPendiente(dyctCasoPenDTO);
        if(registros == ConstantesDyCNumerico.VALOR_0){
            dyctCasoPendienteDAO.insertar(dyctCasoPenDTO);
        }else{
           log.info("No se puede insertar Caso Pendiente ya Existente");    
        }        
    }


    
}


