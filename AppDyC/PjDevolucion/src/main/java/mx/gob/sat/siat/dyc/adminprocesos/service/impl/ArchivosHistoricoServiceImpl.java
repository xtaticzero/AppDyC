package mx.gob.sat.siat.dyc.adminprocesos.service.impl;

import java.util.Date;

import mx.gob.sat.siat.dyc.adminprocesos.dao.ArchivosHistoricoDAO;
import mx.gob.sat.siat.dyc.adminprocesos.dto.ArchivosHistoricoDetalleDTO;
import mx.gob.sat.siat.dyc.adminprocesos.service.ArchivosHistoricoService;
import mx.gob.sat.siat.dyc.adminprocesos.web.controller.util.ImpresionArchivoUtil;
import mx.gob.sat.siat.dyc.dao.seguimiento.DycbSeguimientoFSDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "archivosHistoricoService")
public class ArchivosHistoricoServiceImpl implements ArchivosHistoricoService {
    public ArchivosHistoricoServiceImpl() {
        super();
    }
    
    @Autowired
    private DycbSeguimientoFSDAO dycbSeguimientoFSDAO;
    
    @Autowired
    private ArchivosHistoricoDAO archivosHistoricoDAO;

    /**
     * Busca la informaci&oacute;n de los tr&aacute;mites procesados e incluye los registros que se pudieron
     * mover exitosamente de un filesystem a otro, los que tuvieron alg&uacte;n error al moverlos de filesystem 
     * y el detalle de todos aquellos registros que fallaron. Los datos que se incluyen para todos aquellos casos
     * fallidos son los siguientes:<br />
     * - N&uacute;mero de control.<br />
     * - Causa de fallo.<br />
     * - Filesystem al cual se intenta hacer el copiado.<br />
     * 
     * @param fecha Fecha sobre la cual se hace la consulta.
     *
     * @throws SIATException
     */
    
    @Override
    public ArchivosHistoricoDetalleDTO prepararInformacionDeTramitesProcesados(Date fecha) throws SIATException {
        ArchivosHistoricoDetalleDTO objeto = buscarInformacion(fecha);
        ImpresionArchivoUtil.generarArchivo("fallidos.txt", "/siat/dyc/", objeto.getListaRegistroFallidos());
        return objeto;
    }
    
    /**
     * Busca la informaci&oacute;n de los tr&aacute;mites procesados e incluye los registros que se pudieron
     * mover exitosamente de un filesystem a otro.
     *
     * @param fecha Fecha sobre la cual se hace la consulta.
     * @return Objeto con toda la informaci&oactute;n obtenida en base de datos.
     * @throws SIATException
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    private ArchivosHistoricoDetalleDTO buscarInformacion(Date fecha) throws SIATException {
        ArchivosHistoricoDetalleDTO objeto = null;
        try {
            objeto = new ArchivosHistoricoDetalleDTO();
            objeto.setNoRegistrosExitosos(dycbSeguimientoFSDAO.consutarNoDeRegistrosProcesados(fecha, 1));
            objeto.setNoRegistrosFallidos(dycbSeguimientoFSDAO.consutarNoDeRegistrosProcesados(fecha, 0));
            objeto.setListaRegistroFallidos(archivosHistoricoDAO.listarDetalleDeFallo(fecha));
            
        } catch (Exception e) {
            throw new SIATException (e);
        }
        return objeto;
    }
}
