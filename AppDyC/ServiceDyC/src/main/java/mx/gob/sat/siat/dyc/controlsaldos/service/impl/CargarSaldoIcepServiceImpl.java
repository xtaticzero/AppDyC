package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.util.List;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovDevolucionDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.dao.util.DyctCompHistoricaDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "serviceCargarSaldoIcep")
public class CargarSaldoIcepServiceImpl {

    private static final Logger LOG = Logger.getLogger(CargarSaldoIcepServiceImpl.class);
    
    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;
    
    @Autowired
    private DycpSolicitudDAO daoSolicitudes;
    
    @Autowired
    private IDycpCompensacionDAO daoCompensacion;
    
    @Autowired
    private DyctDeclaracionDAO daoDeclaracion;
    
    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;
    
    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;
    
    @Autowired
    private DyctCompHistoricaDAO daoMovCompensacion;
    
    @Autowired
    private DyctMovDevolucionDAO daoMovDevolucion;
    
    @Autowired
    private DyctResolCompDAO daoResolComp;
    
    public DyctSaldoIcepDTO execute(Integer idSaldoIcep) throws SIATException {
        LOG.debug("INICIO CargarSaldoIcepServiceImpl execute");
        DyctSaldoIcepDTO saldoIcep = daoSaldoIcep.encontrar(idSaldoIcep);
        
        saldoIcep.setDycpSolicitudList(daoSolicitudes.selecXSaldoicep(saldoIcep));
        LOG.debug("numero de solicitudes ->" + saldoIcep.getDycpSolicitudList().size());
        List<DyctDeclaracionDTO> listaDecalracion;
        for (DycpSolicitudDTO sol : saldoIcep.getDycpSolicitudList()) {
            listaDecalracion = daoDeclaracion.selecXServicio(sol.getDycpServicioDTO());
            sol.getDycpServicioDTO().setDyctDeclaracionList(listaDecalracion);
            sol.setDyctDeclaracionList(listaDecalracion);
        }
        
        saldoIcep.setDycpCompensacionList(daoCompensacion.selecXSaldoiceporigen(saldoIcep));
        for (DycpCompensacionDTO comp : saldoIcep.getDycpCompensacionList()) {
            listaDecalracion = daoDeclaracion.selecXServicio(comp.getDycpServicioDTO());
            comp.getDycpServicioDTO().setDyctDeclaracionList(listaDecalracion);
            comp.setDyctDeclaracionList(listaDecalracion);
            comp.setDyctSaldoIcepDestinoDTO(daoSaldoIcep.encontrar(comp.getDyctSaldoIcepDestinoDTO().getIdSaldoIcep()));
            comp.setDyctResolCompDTO(daoResolComp.encontrar(comp));
        }
        
        saldoIcep.setDyctDeclaraIcepList(daoDeclaraIcep.selecXSaldoicepOrder(saldoIcep, " ORDER BY TRUNC(FECHAPRESENTACION) ASC, IDTIPODECLARACION ASC, FECHAREGISTRO ASC "));
        saldoIcep.setDyctDeclaraIcepListCancel(daoDeclaraIcep.selecXSaldoicepOrderCancel(saldoIcep, " ORDER BY TRUNC(FECHAPRESENTACION) ASC, IDTIPODECLARACION ASC, FECHAREGISTRO ASC "));
        
        saldoIcep.setDyctMovSaldoList(daoMovSaldo.selecXSaldoicep(saldoIcep));
        
        saldoIcep.setDyctCompHistoricaList(daoMovCompensacion.obtenerXidSaldoIcep(saldoIcep.getIdSaldoIcep()));
        
        saldoIcep.setDyctMovDevolucionList(daoMovDevolucion.obtenerXidSaldoIcep(saldoIcep.getIdSaldoIcep()));
        
        return saldoIcep;
    }
}
