package mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cobranza.herramienta.actualizador.dto.ActualizacionDetalleDTO;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.facade.ActualizadorFacade;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.controlsaldos.service.DevueltoHelper;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.CargarSaldoIcepServiceImpl;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.CompensadoHistoricoHelper;
import mx.gob.sat.siat.dyc.controlsaldos.vo.MovimientoSaldoBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.CargoCuadroSAFBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.CuadroSaldoAFavorBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridCompensacionesBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridDevolucionesBean;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "serviceDesgloseSaldos")
public class DesgloseSaldosServiceImpl
{
    private static final Logger LOG = Logger.getLogger(DesgloseSaldosServiceImpl.class);
    
    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private ObtenerMovsDiferidosServiceImpl serviceObtSaldosFavor;

    @Autowired
    private CargarSaldoIcepServiceImpl serviceCargarSaldoIcep;

    @Autowired(required = false)
    private ActualizadorFacade actualizadorCbza;
    
    @Autowired
    private ConvertirSaldoServiceImpl convertidor;
    
    @Autowired
    private DevueltoHelper devueltoHelper;
    
    @Autowired
    private CompensadoHistoricoHelper helperCompHist;

    public Map<String, Object> obtenerCuadrosSAF(Integer idIcep)
    {
        Map<String, Object> infoDesglose = new HashMap<String, Object>();

        List<CuadroSaldoAFavorBean> cuadrosSAF = null;        
        List<MovimientoSaldoBean> saldosNegativos = new ArrayList<MovimientoSaldoBean>();

        try {
            DyctSaldoIcepDTO saldoIcep = serviceCargarSaldoIcep.execute(idIcep);
            cuadrosSAF = determinarSAFs(saldoIcep);                                    
            
            List<DyctMovSaldoDTO> cargos;
            List<DyctMovSaldoDTO> cargosValidados = new ArrayList<DyctMovSaldoDTO>();
            List<FilaGridDevolucionesBean> devoluciones = devueltoHelper.obtenerDevoluciones(saldoIcep);
            List<FilaGridCompensacionesBean> compensaciones = helperCompHist.obtenerCompensaciones(saldoIcep);
            Double devuelto;
            for (FilaGridDevolucionesBean devolucion : devoluciones) {
                cargos = obtenerMovSaldoXNumControl(saldoIcep.getDyctMovSaldoList(), devolucion.getNumControl());
                if (devolucion.getTipoResolucion() != null) {
                    devuelto = devolucion.getImporteAutorizado() + devolucion.getImporteNegado();
                    if (devuelto > 0) {                        
                        cargosValidados.addAll(cargos);
                    }
                }
            }
            
            for (FilaGridCompensacionesBean compensacion : compensaciones) {
                cargos = obtenerMovSaldoXNumControl(saldoIcep.getDyctMovSaldoList(), compensacion.getNumControl());
                if(compensacion.getImporteComphist().compareTo(BigDecimal.ZERO) > 0){                    
                    cargosValidados.addAll(cargos);
                }
            }
            
            cargos = obtenerMovSaldoXMovimiento(saldoIcep.getDyctMovSaldoList(), Constantes.MovsIcep.CARGO_DEVCOMPS_ANTERIORES.getIdMovimiento());
            cargosValidados.addAll(cargos);
            
            cargos = obtenerMovSaldoXMovimiento(saldoIcep.getDyctMovSaldoList(), Constantes.MovsIcep.CARGO_ACREDITAMIENTO.getIdMovimiento());
            cargosValidados.addAll(cargos);
            
            cargos = obtenerMovSaldoXAjuste(saldoIcep.getDyctMovSaldoList());
            cargosValidados.addAll(cargos);
            
            LOG.debug("total de cargos validados ->" + cargosValidados.size());
            for(DyctMovSaldoDTO cargo : cargosValidados)
            {
                LOG.debug("cargo ->" + cargo.getImporte() + "; origen ->" + cargo.getIdOrigen() + "<-; tipo " + cargo.getDyccMovIcepDTO().getDescripcion());
                // descontar cargo
                double saldoNoDescontado = descontarCargo(cargo, cuadrosSAF).doubleValue();
                if(saldoNoDescontado > 0d){
                    MovimientoSaldoBean movSaldoNegativo = new MovimientoSaldoBean();
                    movSaldoNegativo.setOrigen(cargo.getIdOrigen());
                    movSaldoNegativo.setImporte(saldoNoDescontado);
                    movSaldoNegativo.setFecha(cargo.getFechaOrigen());
                    saldosNegativos.add(movSaldoNegativo);
                }
            }

            for(CuadroSaldoAFavorBean cuadroSAF : cuadrosSAF)
            {
                if(saldosNegativos.isEmpty()){
                    cuadroSAF.setRemanenteTotal(obtenerRemanenteTotalAFecha(cuadrosSAF, cuadroSAF.getFecha()));
                }
                else{
                    cuadroSAF.setRemanenteTotal(obtenerRemanenteTotalNegativoAFecha(saldosNegativos, cuadroSAF.getFecha()));
                }
            }
        } 
        catch (SIATException e) {
            LOG.error("Ocurrio un error en obtenerCuadrosSAF, mensaje ->" + e.getMessage());
        }

        infoDesglose.put("cuadrosSAF", cuadrosSAF);
        
        infoDesglose.put("saldosNegativos", saldosNegativos);
        return infoDesglose;
    }
    
    private BigDecimal obtenerRemanenteTotalAFecha(List<CuadroSaldoAFavorBean> cuadrosSAF, Date fechaDestino)
    {
        BigDecimal remanenteTotal = BigDecimal.ZERO;
        for(CuadroSaldoAFavorBean cuadroSAF : cuadrosSAF){
            remanenteTotal = remanenteTotal.add(new BigDecimal(convertidor.execute(cuadroSAF.getFecha(), fechaDestino, cuadroSAF.getRemanente().doubleValue())));
        }
        LOG.debug("remanenteTotal ->" + remanenteTotal + "<-");
        return remanenteTotal;
    }
    
    private BigDecimal obtenerRemanenteTotalNegativoAFecha(List<MovimientoSaldoBean> saldosNegativos, Date fechaDestino)
    {
        BigDecimal remTotalNegativo = BigDecimal.ZERO;
        for(MovimientoSaldoBean saldoNeg : saldosNegativos){
            remTotalNegativo = remTotalNegativo.add(new BigDecimal(convertidor.execute(saldoNeg.getFecha(), fechaDestino, saldoNeg.getImporte())));
        }
        LOG.debug("remanenteTotalNegativo ->" + remTotalNegativo + "<-");
        return remTotalNegativo.negate();
    }

    private BigDecimal descontarCargo(DyctMovSaldoDTO cargo, List<CuadroSaldoAFavorBean> safs)
    {
        //importe del cargo que falta por cubrir 
        BigDecimal impCarFaltCubrir = cargo.getImporte();
        LOG.debug("impCarFaltCubrir ->" + impCarFaltCubrir + "<-");
        for(CuadroSaldoAFavorBean csaf : safs)
        {
            // tiene remanente?
            if(csaf.getRemanente().doubleValue() > 0d)
            {
                // si entra aquí por fuerza se agregará una filaCargo en este cuadro
                if(csaf.getCargos() == null){
                    csaf.setCargos(new ArrayList<CargoCuadroSAFBean>());
                }

                CargoCuadroSAFBean filaCargo = new CargoCuadroSAFBean();
                filaCargo.setFechaOrigen(cargo.getFechaOrigen());
                filaCargo.setOrigen(cargo.getIdOrigen());
                filaCargo.setDescrOrigen(cargo.getDyccMovIcepDTO().getDescripcionLarga());
                LOG.debug("descripcion larga cargo ->" + cargo.getDyccMovIcepDTO().getDescripcionLarga());
                csaf.getCargos().add(filaCargo);
                LOG.debug("csaf.getFecha() ->" + csaf.getFecha());
                LOG.debug("cargo.getFechaOrigen() ->" + cargo.getFechaOrigen());
                LOG.debug("impCarFaltCubrir ->" + impCarFaltCubrir);
                    
                ActualizacionDetalleDTO respDeflactacion = actualizadorCbza.calcularDeflactacion(csaf.getFecha(), cargo.getFechaOrigen(), impCarFaltCubrir);
                
                BigDecimal impCargoFaltCubrirFechaSAF;
                
                if(respDeflactacion.getFactorActzn() != null && respDeflactacion.getFactorActzn().doubleValue() > 0)
                {
                    LOG.debug("si hay factor de actualizacion por lo tanto se deflacto");
                    impCargoFaltCubrirFechaSAF = respDeflactacion.getImporteActualizado();   
                    // agregar descripcion INPC al cuadro SAF
                    try{
                        Date fechaInpcCuadro =null;
                        if(respDeflactacion.getFechaPublicacionAntiguo()!=null){
                            fechaInpcCuadro = new Date(respDeflactacion.getFechaPublicacionAntiguo().getTime() - (ConstantesDyCNumerico.MILISEGUNDOS_15DIAS));
                        }
                        if(fechaInpcCuadro!=null || respDeflactacion.getInpcAntiguo()!=null){
                            csaf.setDescrInpc(Utils.formatearFechaMesAnio(fechaInpcCuadro) + "  (" + respDeflactacion.getInpcAntiguo() + ")");
                        }else{
                            csaf.setDescrInpc("No aplica.");
                        }
                        
                        LOG.debug("DESCRIPCION INPC --->" + csaf.getDescrInpc());
                        Date fechaInpcCargo = new Date(respDeflactacion.getFechaPublicacionReciente().getTime() - (ConstantesDyCNumerico.MILISEGUNDOS_15DIAS));
                        filaCargo.setDescrInpc(Utils.formatearFechaMesAnio(fechaInpcCargo) + "  (" + respDeflactacion.getInpcReciente() + ")");
                        filaCargo.setFactorAct(respDeflactacion.getFactorActzn().floatValue());
                    } 
                    catch(Exception e){
                        LOG.error("Ocurrio un error al obtener la informacion para el cuadro en el desglose del control de saldos");
                        LOG.error(respDeflactacion);
                        ManejadorLogException.getTraceError(e);       
                    }
                }
                else{
                    LOG.debug("No hay factor de actualizacion por lo tanto NO se deflacto");
                    impCargoFaltCubrirFechaSAF = impCarFaltCubrir;
                    filaCargo.setDescrInpc("No aplica");
                    filaCargo.setFactorAct(1f);
                }

                LOG.debug("impCargoFaltCubrirFechaSAF ->" + impCargoFaltCubrirFechaSAF + "<-");

                // alcanza el remanente?
                if(csaf.getRemanente().doubleValue() >= impCargoFaltCubrirFechaSAF.doubleValue())
                {   
                    filaCargo.setCargoOrigen(impCarFaltCubrir);
                    filaCargo.setCargoReal(impCargoFaltCubrirFechaSAF);
                    // descontar cargo de remanente
                    csaf.setRemanente(csaf.getRemanente().subtract(impCargoFaltCubrirFechaSAF));
                    impCarFaltCubrir = BigDecimal.ZERO;
                    break;
                }
                else{ 
                    // el remanente no alcanzo
                    // encontrar importe maximo que puede cubirir el remanente de este cuadro

                    //llevar remanente a fecha del cargo (actualizar)
                    ActualizacionDetalleDTO respActualizacion = actualizadorCbza.calcularActualizacion(csaf.getFecha(), cargo.getFechaOrigen(), csaf.getRemanente());
                    if(respActualizacion.getFactorActzn() != null && respActualizacion.getFactorActzn().doubleValue() > 0){
                        BigDecimal remanenteActualizadoCSAFFechaCargo = respActualizacion.getImporteActualizado();
                        filaCargo.setCargoOrigen(remanenteActualizadoCSAFFechaCargo);
                        filaCargo.setCargoReal(csaf.getRemanente());
                        impCarFaltCubrir = impCarFaltCubrir.subtract(remanenteActualizadoCSAFFechaCargo);
                    }
                    else{
                        filaCargo.setCargoOrigen(csaf.getRemanente());
                        filaCargo.setCargoReal(csaf.getRemanente());
                        impCarFaltCubrir = impCarFaltCubrir.subtract(csaf.getRemanente());
                    }
                    csaf.setRemanente(BigDecimal.ZERO);
                }
                LOG.debug("descripcion INPC del cargo ->" + filaCargo.getDescrInpc() + "<-");
            }
        }
        LOG.debug("importe que falta por cubrir ->" + impCarFaltCubrir);
        return impCarFaltCubrir;
    }
    
    private List<CuadroSaldoAFavorBean> determinarSAFs(DyctSaldoIcepDTO saldoIcep) throws SIATException
    {        
        if(saldoIcep.getDyctDeclaraIcepList().isEmpty()){
            return determinarSAFCantidadFavor(saldoIcep);
        }

        List<MovimientoSaldoBean> movsSAF = serviceObtSaldosFavor.execute(saldoIcep.getDyctDeclaraIcepList());
        List<CuadroSaldoAFavorBean> cuadros = new ArrayList<CuadroSaldoAFavorBean>();
        for(MovimientoSaldoBean movSAF : movsSAF)
        {
            List<DyctMovSaldoDTO> movsSAFDTO = obtenerMovSaldoXNumControl(saldoIcep.getDyctMovSaldoList(), movSAF.getOrigen());
            String preOrigen = "";
            if(!movsSAFDTO.isEmpty()){
                preOrigen = movsSAFDTO.get(0).getDyccMovIcepDTO().getDescripcionLarga() + " ";
            }
            CuadroSaldoAFavorBean cuadro = new CuadroSaldoAFavorBean();
            cuadro.setSaldoAFavor(new BigDecimal(movSAF.getImporte()));
            cuadro.setOrigen(preOrigen + movSAF.getOrigen());
            cuadro.setFecha(movSAF.getFecha());
            cuadro.setFechaStr(Utils.formatearFecha(movSAF.getFecha()));
            cuadro.setRemanente(new BigDecimal(movSAF.getImporte()));
            cuadro.setSaldoAFavorStr(Utils.formatearMoneda(movSAF.getImporte()));
            cuadro.setValidacionDWH(movSAF.getValidacionDWH());
            cuadros.add(cuadro);
        }

        //cuadros de safs regresados 
        List<DyctMovSaldoDTO> safsExtras = daoMovSaldo.selecOrderXSaldoicepMovsicep(saldoIcep, Constantes.MovsIcep.ALTA_IMPOR_COMP_DESISTIDO, " ORDER BY FECHAORIGEN ASC ");
        for(DyctMovSaldoDTO movSAF : safsExtras)
        {
            CuadroSaldoAFavorBean cuadro = new CuadroSaldoAFavorBean();
            cuadro.setSaldoAFavor(movSAF.getImporte());
            cuadro.setOrigen("Saldo por desistimiento: " + movSAF.getIdOrigen());
            cuadro.setFecha(movSAF.getFechaOrigen());
            cuadro.setFechaStr(Utils.formatearFecha(movSAF.getFechaOrigen()));
            cuadro.setRemanente(movSAF.getImporte());
            cuadro.setSaldoAFavorStr(Utils.formatearMoneda(movSAF.getImporte().doubleValue()));
            cuadros.add(cuadro);                
        }

        return cuadros;
    }
    
    private List<DyctMovSaldoDTO> obtenerMovSaldoXNumControl(List<DyctMovSaldoDTO> movSaldoList, String numControl) {
        List<DyctMovSaldoDTO> movsSaldo = new ArrayList<DyctMovSaldoDTO>();

        if (movSaldoList == null || movSaldoList.isEmpty()) {
            return movsSaldo;
        }

        DyctMovSaldoDTO dyctMovSaldoDTO;
        boolean isNull;
        for (int i = 0; i < movSaldoList.size(); i++) {
            dyctMovSaldoDTO = movSaldoList.get(i);
            isNull = (dyctMovSaldoDTO.getIdOrigen() == null || numControl == null);
            if (!isNull && dyctMovSaldoDTO.getIdOrigen().trim().equals(numControl.trim()) && validarMovimiento(dyctMovSaldoDTO)) {
                dyctMovSaldoDTO = movSaldoList.remove(i);
                movsSaldo.add(dyctMovSaldoDTO);
                --i;
            }
        }
        return movsSaldo;
    }
    
    private boolean validarMovimiento(DyctMovSaldoDTO dyctMovSaldoDTO) {
        if (dyctMovSaldoDTO.getFechaFin() == null) {
            List<DyctAccionMovSalDTO> acciones = dyctMovSaldoDTO.getDyctAccionMovSalDTOList();
            if (acciones == null || acciones.isEmpty()) {
                return true;
            } else {
                DyctAccionMovSalDTO ultimaAccion = acciones.get(acciones.size() - 1);
                if (ultimaAccion.getTipoAccionMovSal() == DyctAccionMovSalDTO.TipoAccionMovSaldo.VALIDAR
                        || ultimaAccion.getTipoAccionMovSal() == DyctAccionMovSalDTO.TipoAccionMovSaldo.CREAR) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private List<DyctMovSaldoDTO> obtenerMovSaldoXMovimiento(List<DyctMovSaldoDTO> movSaldoList, Integer idCargoExterno) {
        List<DyctMovSaldoDTO> movsSaldo = new ArrayList<DyctMovSaldoDTO>();

        if (movSaldoList == null || movSaldoList.isEmpty()) {
            return movsSaldo;
        }

        DyctMovSaldoDTO dyctMovSaldoDTO;
        for (int i = 0; i < movSaldoList.size(); i++) {
            dyctMovSaldoDTO = movSaldoList.get(i);
            if (dyctMovSaldoDTO.getDyccMovIcepDTO().getIdMovimiento().equals(idCargoExterno) && validarMovimiento(dyctMovSaldoDTO)) {
                dyctMovSaldoDTO = movSaldoList.remove(i);
                movsSaldo.add(dyctMovSaldoDTO);
                --i;
            }
        }
        return movsSaldo;
    }
    
    private List<DyctMovSaldoDTO> obtenerMovSaldoXAjuste(List<DyctMovSaldoDTO> movSaldoList) {
        List<DyctMovSaldoDTO> movsSaldo = new ArrayList<DyctMovSaldoDTO>();

        if (movSaldoList == null || movSaldoList.isEmpty()) {
            return movsSaldo;
        }

        DyctMovSaldoDTO dyctMovSaldoDTO;
        for (int i = 0; i < movSaldoList.size(); i++) {
            dyctMovSaldoDTO = movSaldoList.get(i);
            if (validarMovimiento(dyctMovSaldoDTO) && 
                    dyctMovSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.CARGO.getIdAfectacion())) {
                
                dyctMovSaldoDTO = movSaldoList.remove(i);
                movsSaldo.add(dyctMovSaldoDTO);
                --i;
            }
        }
        return movsSaldo;
    }
    
    private List<CuadroSaldoAFavorBean> determinarSAFCantidadFavor(DyctSaldoIcepDTO saldoIcep)
    {
        LOG.debug("INICIO determinarSAFCantidadFavor");
        List<DyctMovSaldoDTO> movsAbonoSAFs = daoMovSaldo.selecOrderXSaldoicepAfectacion(saldoIcep, Constantes.AfectacionesIcep.ABONO, "");
        LOG.debug("numero de abonos encontrados ->" + movsAbonoSAFs.size());
        List<CuadroSaldoAFavorBean> listaCuadrosSAF = new ArrayList<CuadroSaldoAFavorBean>();
        if(!movsAbonoSAFs.isEmpty())
        {
            DyctMovSaldoDTO movSAF = movsAbonoSAFs.get(0);
            LOG.debug("Desglose movSAF.getFechaOrigen() ->" + movSAF.getFechaOrigen());
            if(movSAF.getFechaOrigen() == null){
                LOG.error("El movSAF ->" + movSAF.getIdMovSaldo() + "<- NO tiene fecha origen, se usara la fecha actual");
                movSAF.setFechaOrigen(new Date());
            }

            CuadroSaldoAFavorBean cuadro = new CuadroSaldoAFavorBean();
            cuadro.setSaldoAFavor(movSAF.getImporte());
            cuadro.setOrigen("Saldo por desistimiento: " + movSAF.getIdOrigen());
            cuadro.setFecha(movSAF.getFechaOrigen());
            cuadro.setFechaStr(Utils.formatearFecha(movSAF.getFechaOrigen()));
            cuadro.setRemanente(movSAF.getImporte());
            cuadro.setSaldoAFavorStr(Utils.formatearMoneda(movSAF.getImporte().doubleValue()));
            listaCuadrosSAF.add(cuadro);
            
            if(movsAbonoSAFs.size() > 1){
                LOG.error("Existen inconsistencias en el saldo a favor del ICEP ->" + saldoIcep.getIdSaldoIcep() + 
                          "; existe mas de un movimiento abono para la CANTIDAD a favor; se tomará en cuenta solo el primero");
            }
        }
        return listaCuadrosSAF;    
    }
}
