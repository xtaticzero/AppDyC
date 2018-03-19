/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.service.solicitud.impl;


import java.util.List;
import mx.gob.sat.siat.dyc.dao.seguimiento.DycaReasignacionDAO;

import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.domain.ReasignacionDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.ReasignarManSolicDevolucionyCasosCompService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Israel Chavez
 */
@Service(value = "reasignarManSolicDevolucionyCasosCompService")
public class ReasignarManSolicDevolucionyCasosCompServiceImpl implements ReasignarManSolicDevolucionyCasosCompService {

    private static Logger log = Logger.getLogger(ReasignarManSolicDevolucionyCasosCompServiceImpl.class.getName());

    @Autowired
    private IDycpServicioDAO dycpServicioDAO;
    @Autowired
    private DycaReasignacionDAO dycaReasignacionDAO;
    
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public String reasignacionManualSolicitud(List<DictaminadorSolBean> selectedSolicitudesArrList,
                                              DyccDictaminadorDTO selectedDictaminador,
                                              DyccDictaminadorDTO selectedDictaminadorOnDlg, String numControl ){
        DictaminadorSolBean current;
        String paramNumControl;

        for (int i = 0; i < selectedSolicitudesArrList.size(); i++) {

            current = selectedSolicitudesArrList.get(i);
            try {
                /**dyccDictaminadorDAO.restarPuntosReasiganacion(selectedDictaminador.getNumEmpleado(),
                                                              current.getIdTipoTramite());
                dyccDictaminadorDAO.actualizaCargaDeTrabajo(selectedDictaminadorOnDlg.getNumEmpleado(),
                                                            current.getIdTipoTramite());*/
               paramNumControl = StringUtils.isNotBlank(current.getNumControl() )? current.getNumControl() :numControl;
                
                dycpServicioDAO.updateDictaminador(selectedDictaminadorOnDlg.getNumEmpleado(), paramNumControl);

            } catch (Exception e) {

                log.error("Se ha presentado un error" + e.getMessage());
            }
        }

        return null;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public String reasignacionManualResponsable(List<DictaminadorSolBean> selectedSolicitudesArrList, 
                                                   DyccDictaminadorDTO selectedDictaminador, 
                                                   DyccDictaminadorDTO selectedDictaminadorOnDlg, Integer numeroEmpleadoResponsable) {
        
        for (DictaminadorSolBean current:selectedSolicitudesArrList) {           
            try {                           
                dycpServicioDAO.updateDictaminador(selectedDictaminadorOnDlg.getNumEmpleado(), current.getNumControl());
                ReasignacionDTO reasignacion= new ReasignacionDTO();
                reasignacion.setFechaFin(null);
                reasignacion.setNumcontrol(current.getNumControl());
                reasignacion.setEmpleadoResponsable(numeroEmpleadoResponsable);
                reasignacion.setEmpleadoAsignado(current.getNumEmpleado());
                reasignacion.setOrigen(current.getNumControl());
                dycaReasignacionDAO.insertarReasignacion(reasignacion);  

            } catch (Exception e) {

                log.error("Se ha presentado un error" + e.getMessage());
            }
        }

        return null;    
    
    }

}
