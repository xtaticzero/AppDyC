package mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.controlsaldos.service.RegistrarDeclaracionesService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.AfectarSaldosXAvisosCompService;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("serviceAfectarSaldosXAvisosComp")
public class AfectarSaldosXAvisosCompServiceImpl implements AfectarSaldosXAvisosCompService
{
    private static final Logger LOG = Logger.getLogger(AfectarSaldosXAvisosCompServiceImpl.class);

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private RegistrarDeclaracionesService serviceRegistrarDecls;

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = Exception.class)
    public Map<String, Object> afectarXRegistro(DycpCompensacionDTO compensacion) throws SIATException
    {
        DyctSaldoIcepDTO icepDestino = compensacion.getDyctSaldoIcepDestinoDTO();
        persistirSaldoIcep(icepDestino, Boolean.TRUE);
        LOG.debug("idSaldoIcepOrigen ->" + icepDestino.getIdSaldoIcep() + "<-");
        DyctSaldoIcepDTO icepOrigen = compensacion.getDyctSaldoIcepOrigenDTO();
        icepOrigen.setRfc(icepDestino.getRfc());
        boolean icepNuevo = persistirSaldoIcep(icepOrigen, Boolean.FALSE);
        LOG.debug("idSaldoIcepOrigen ->" + icepOrigen.getIdSaldoIcep() + "<-");
        if(icepNuevo){
            serviceRegistrarDecls.execute(compensacion.getDycpServicioDTO());
        }

        DyctMovSaldoDTO cargoCompensacion = new DyctMovSaldoDTO();
        cargoCompensacion.setDyctSaldoIcepDTO(icepOrigen);
        cargoCompensacion.setImporte(compensacion.getImporteCompensado());
        cargoCompensacion.setFechaRegistro(new Date());
        cargoCompensacion.setFechaOrigen(compensacion.getFechaPresentaDec());
        cargoCompensacion.setDyccMovIcepDTO(Constantes.MovsIcep.CARGO_ALTA_SALDO);
        cargoCompensacion.setIdOrigen(compensacion.getDycpServicioDTO().getNumControl());
        daoMovSaldo.insertar(cargoCompensacion);

        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("idSaldoIcepOrigen", icepOrigen.getIdSaldoIcep());
        respuesta.put("idSaldoIcepDestino", icepDestino.getIdSaldoIcep());
        return respuesta;
    }

    /*
     * true: si el icep se persistio
     * false: el icep no se persistio porque ya existia
     */
    private boolean persistirSaldoIcep(DyctSaldoIcepDTO saldoIcep, boolean esIcepDestino) throws SIATException
    {        
        DyctSaldoIcepDTO saldoIcepAux = null;
        if(esIcepDestino){
            List<DyctSaldoIcepDTO> listTemp = daoSaldoIcep.selecXRfcConceptoEjercicioPeriodo(saldoIcep.getRfc(), saldoIcep.getDyccConceptoDTO(), 
                                    saldoIcep.getDyccEjercicioDTO(), saldoIcep.getDyccPeriodoDTO());
            
            if(listTemp != null && !listTemp.isEmpty()){
                for (DyctSaldoIcepDTO icepTemp : listTemp) {
                    if(icepTemp.getDyccOrigenSaldoDTO() == null){
                        saldoIcepAux = icepTemp;
                        break;
                    }
                }
            }
            
        }else{
            
            saldoIcepAux = daoSaldoIcep.encontrar(saldoIcep.getRfc(), saldoIcep.getDyccConceptoDTO(), 
                                    saldoIcep.getDyccEjercicioDTO(), saldoIcep.getDyccPeriodoDTO(), saldoIcep.getDyccOrigenSaldoDTO());
        }
        
                
        if(saldoIcepAux == null){
            daoSaldoIcep.insert(saldoIcep);
            return Boolean.TRUE;
        }
        else{
            saldoIcep.setIdSaldoIcep(saldoIcepAux.getIdSaldoIcep());
            return Boolean.FALSE;
        }
    }
}
