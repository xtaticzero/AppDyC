package mx.gob.sat.siat.dyc.adminprocesos.service.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.adminprocesos.service.AdministrarProcesosService;
import mx.gob.sat.siat.dyc.dao.adminproceso.DyctAdminProcesosDAO;
import mx.gob.sat.siat.dyc.dao.adminproceso.DyctProcesosDAO;
import mx.gob.sat.siat.dyc.dao.adminproceso.DyctSegProcesoDAO;
import mx.gob.sat.siat.dyc.dao.adminproceso.DyctTareasDAO;
import mx.gob.sat.siat.dyc.dao.util.DyccStatusProcesoDAO;
import mx.gob.sat.siat.dyc.domain.adminproceso.DyccStatusProcesoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.Procesos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "administrarProcesosService")
public class AdministrarProcesosServiceImpl implements AdministrarProcesosService {
    
    @Autowired
    private DyctProcesosDAO dyctProcesosDAO;
    
    @Autowired
    private DyccStatusProcesoDAO dyccStatusProcesoDAO;
    
    @Autowired
    private DyctSegProcesoDAO dyctSegProcesoDAO;
    
    @Autowired
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
    
    @Autowired
    private DyctTareasDAO dyctTareasDAO;
    
    /**
     * Lista todos los procesos existentes dados de alta en base de datos
     *
     * @return Lista con los procesos existentes.
     * @throws SIATException
     */
    @Override
    public List<Procesos> consultar() throws SIATException {
        return dyctProcesosDAO.consultar();
    }
    
    /**
     * Guarda el nuevo calendario de ejecucion que se registra en la pantalla de administrarProcesos.
     *
     * @param proceso datos completos con el nuevo proceso.
     * @throws SIATException
     */
    @Override
    public void guardarCron(Procesos proceso) throws SIATException {
        dyctTareasDAO.guardarCron(proceso);
    }
    
    /**
     * Lista los estatus de los procesos que estab en BD.
     *
     * @return lista de estuatus de procesos.
     * @throws SIATException
     */
    @Override
    public List<DyccStatusProcesoDTO> listarStatusProceso() throws SIATException {
        return dyccStatusProcesoDAO.listarStatusProceso(SQLOracleDyC.CONSULTA_STATUSPROCESOS.toString());
    }
    
    /**
     * Actualiza el estatus de un proceso de activo a pausado y vicerverza
     *
     * @param proceso Objeto con los datos del proceso
     * @throws SIATException
     */
    @Override
    public void actualizarStatusProceso(Procesos proceso) throws SIATException {
        dyctSegProcesoDAO.actualizarStatusProceso(proceso);
    }
    
    /**
     * Retorna la ultima fecha de ejecucion del administrador de procesos
     *
     * @return
     * @throws SIATException
     */
    @Override
    public String consultarFechaEjecucion() throws SIATException {
        return dyctAdminProcesosDAO.consultarFechaEjecucion();
    }
}
