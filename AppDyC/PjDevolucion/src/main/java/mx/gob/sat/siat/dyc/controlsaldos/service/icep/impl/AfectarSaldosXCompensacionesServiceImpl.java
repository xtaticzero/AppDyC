package mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.controlsaldos.service.icep.AfectarSaldosXCompensacionesService;
import mx.gob.sat.siat.dyc.dao.accion.DyctAccionMovSalDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("serviceAfectarSaldosXCompensaciones")
public class AfectarSaldosXCompensacionesServiceImpl implements AfectarSaldosXCompensacionesService {

    private static final Logger LOG = Logger.getLogger(AfectarSaldosXCompensacionesServiceImpl.class);

    @Autowired
    private DyctDocumentoDAO daoDocumento;

    @Autowired
    private DyctResolCompDAO daoResolComp;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private DyctAccionMovSalDAO daoAccionMovSaldo;
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    public Map<String, Object> afectarXLiquidacion(String numCtrlDocResLiquidacion) throws SIATException {
        DyctDocumentoDTO documento = daoDocumento.encontrar(numCtrlDocResLiquidacion);
        DycpCompensacionDTO comp = daoCompensacion.encontrar(documento.getDycpServicioDTO().getNumControl());        
        DyctResolCompDTO resolComp = daoResolComp.encontrar(comp);        

        DyccMovIcepDTO tipoMov = null;
        if (resolComp.getDyccTipoResolDTO() == Constantes.TiposResolucion.SALDOAFAVOR_IMPROCEDENTE) {
            tipoMov = Constantes.MovsIcep.SALDO_IMPROCEDENTE;
        } else if (resolComp.getDyccTipoResolDTO() == Constantes.TiposResolucion.COMPENSACION_IMPROCEDENTE) {
            tipoMov = Constantes.MovsIcep.COMPENSACION_IMPROCEDENTE;
            
            DyctMovSaldoDTO movLiquidacion = new DyctMovSaldoDTO();
            movLiquidacion.setDyctSaldoIcepDTO(comp.getDyctSaldoIcepOrigenDTO());
            movLiquidacion.setImporte(comp.getImporteCompensado());
            movLiquidacion.setFechaRegistro(new Date());
            movLiquidacion.setFechaOrigen(resolComp.getFechaResolucion());
            movLiquidacion.setIdOrigen(numCtrlDocResLiquidacion);
            movLiquidacion.setDyccMovIcepDTO(tipoMov);
            daoMovSaldo.insertar(movLiquidacion);
        }

        Map<String, Object> respuesta = new HashMap<String, Object>();
        if (tipoMov != null) {            
            respuesta.put("seRealizoCargo", Boolean.TRUE);
        } else {
            LOG.error("el tipo de la resolucion NO es válido para una liquidacion de compensacion");
            respuesta.put("seRealizoCargo", Boolean.FALSE);
        }

        return respuesta;
    }

    @Override
    @Transactional(rollbackFor = SIATException.class)
    public Map<String, Object> afectarXDesistimiento(String numControl) throws SIATException
    {
        LOG.debug("INICIO numControl ->" + numControl);
        DycpCompensacionDTO compensacion = daoCompensacion.encontrar(numControl);
        List<DyctMovSaldoDTO> movs = daoMovSaldo.selecXSaldoicep(compensacion.getDyctSaldoIcepOrigenDTO());
        for (DyctMovSaldoDTO movAux : movs) 
        {
            LOG.debug("movAux ->" + movAux + "<-");
            boolean condA = numControl.equals(movAux.getIdOrigen());
            boolean condB = movAux.getDyccMovIcepDTO() == Constantes.MovsIcep.CARGO_ALTA_SALDO;

            if (condA && condB) {
                DyctAccionMovSalDTO accionMovSal = new DyctAccionMovSalDTO();
                accionMovSal.setDyctMovSaldoDTO(movAux);
                accionMovSal.setTipoAccionMovSal(mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.INVALIDAR);
                accionMovSal.setFechaRegistro(new Date());
                accionMovSal.setDyctAccionPrivAjusDTO(Constantes.PermisosEspecialesAjustarSaldo.MAT_DYC);
                accionMovSal.setJustificacion("Se desiste aviso de compensación que había generado el cargo - " + numControl + "; ajuste automático MAT-DYC Control de saldos");
                daoAccionMovSaldo.insertar(accionMovSal);

                break;
            }
        }

        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("seAplicoDesistimiento", Boolean.TRUE);
        return respuesta;
    }

 @Override
    @Transactional(rollbackFor = SIATException.class)
    public Map<String, Object> afectarXDesistimientoCasoCompensacion(String numControl) throws SIATException
    {
        LOG.debug("INICIO numControl ->" + numControl);
        DycpCompensacionDTO compensacion = daoCompensacion.encontrar(numControl);
        List<DyctMovSaldoDTO> movs = daoMovSaldo.selecXSaldoicep(compensacion.getDyctSaldoIcepOrigenDTO());
        for (DyctMovSaldoDTO movAux : movs) 
        {
            LOG.debug("movAux --->"+numControl + movAux + "<-");
            boolean condA = numControl.equals(movAux.getIdOrigen());
            boolean condB = movAux.getDyccMovIcepDTO() == Constantes.MovsIcep.CARGO_ALTA_SALDO;

            if (condA && condB) {
                LOG.debug("desistir --->"+numControl);
                DyctAccionMovSalDTO accionMovSal = new DyctAccionMovSalDTO();
                accionMovSal.setDyctMovSaldoDTO(movAux);
                accionMovSal.setTipoAccionMovSal(mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.INVALIDAR);
                accionMovSal.setFechaRegistro(new Date());
                accionMovSal.setDyctAccionPrivAjusDTO(Constantes.PermisosEspecialesAjustarSaldo.MAT_DYC);
                accionMovSal.setJustificacion("Se desiste aviso de compensación que había generado el cargo - " + numControl + "; ajuste automático MAT-DYC Control de saldos");
                daoAccionMovSaldo.insertar(accionMovSal);
                DyctMovSaldoDTO movLiquidacion = new DyctMovSaldoDTO();
                movLiquidacion.setDyctSaldoIcepDTO(compensacion.getDyctSaldoIcepOrigenDTO());
                movLiquidacion.setImporte(movAux.getImporte());
                movLiquidacion.setFechaRegistro(new Date());
                movLiquidacion.setFechaOrigen(movAux.getFechaOrigen());
                movLiquidacion.setIdOrigen(numControl);
                movLiquidacion.setDyccMovIcepDTO(Constantes.MovsIcep.ALTA_SALDO);
                daoMovSaldo.insertar(movLiquidacion);
                break;
            }
        }

        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("seAplicoDesistimiento", Boolean.TRUE);
        return respuesta;
    }
}