package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridCompensacionesBean;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctCompHistoricaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;


@Component
public class CompensadoHistoricoHelper
{
    private static final Logger LOG = Logger.getLogger(CompensadoHistoricoHelper.class);

    public List<FilaGridCompensacionesBean> obtenerCompensaciones(DyctSaldoIcepDTO saldoIcepOrigen) throws SIATException
    {
        List<FilaGridCompensacionesBean> filas = new ArrayList<FilaGridCompensacionesBean>();
        List<DyctMovSaldoDTO> movSaldoList = new ArrayList<DyctMovSaldoDTO>(saldoIcepOrigen.getDyctMovSaldoList());

        List<DyctCompHistoricaDTO> movsCompensacion = saldoIcepOrigen.getDyctCompHistoricaList();
        for(DyctCompHistoricaDTO movCompDTO : movsCompensacion)
        {
            FilaGridCompensacionesBean fila = new FilaGridCompensacionesBean();
            fila.setIdMovCompensacion(movCompDTO.getIdMovCompensacion());
            fila.setTipoCompensacion("HIST");
            fila.setNumControl(movCompDTO.getNumControl());
            fila.setFechaDeclaracionDestino(movCompDTO.getFechaDeclaraDest());
            fila.setPeriodoDestino(movCompDTO.getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO().getDescripcion());
            fila.setConceptoDestino(movCompDTO.getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getDescripcion());
            fila.setEjercicioDestino(movCompDTO.getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO().getIdEjercicio().toString());
            fila.setImporteCompensado(movCompDTO.getImporteCompensado());
            fila.setTipoResolucion(movCompDTO.getDyccTipoResolDTO() == Constantes.TiposResolucion.REGISTRAR_CASOCOMP ? "Registrada" : movCompDTO.getDyccTipoResolDTO().getDescripcion());
            fila.setFechaResolucion(movCompDTO.getFechaResolucion());
            fila.setFechaDeclaracionDestinoStr(Utils.formatearFecha(movCompDTO.getFechaDeclaraDest()));

            fila.setFechaResolucionStr(Utils.formatearFecha(movCompDTO.getFechaResolucion()));

            fila.setNotas(movCompDTO.getNotas());
            fila.setFechaFin(movCompDTO.getFechaFin());
            fila.setImporteComphist(calcularCompensado(fila, movSaldoList));
            filas.add(fila);
        }

        List<DycpCompensacionDTO> compensaciones = saldoIcepOrigen.getDycpCompensacionList();
        for(DycpCompensacionDTO compDTO : compensaciones)
        {
            DyctSaldoIcepDTO icepDestino = compDTO.getDyctSaldoIcepDestinoDTO();
            
            DyctResolCompDTO resolucion = compDTO.getDyctResolCompDTO();
            FilaGridCompensacionesBean fila = new FilaGridCompensacionesBean();
            fila.setTipoCompensacion("DYC");
            fila.setNumControl(compDTO.getDycpServicioDTO().getNumControl());
            fila.setFechaDeclaracionDestino(compDTO.getFechaPresentaDec());
            fila.setPeriodoDestino(icepDestino.getDyccPeriodoDTO().getDescripcion());
            fila.setConceptoDestino(icepDestino.getDyccConceptoDTO().getDescripcion());
            fila.setEjercicioDestino(icepDestino.getDyccEjercicioDTO().getIdEjercicio().toString());
            fila.setImporteCompensado(compDTO.getImporteCompensado());
            
            if(compDTO.getDyccEstadoCompDTO() == Constantes.EstadosCompensacion.DESISTIDO){
                fila.setTipoResolucion("Desistida");
            }
            else{
                fila.setTipoResolucion(obtenerStrTipoResolucion(resolucion));
                fila.setFechaResolucion(obtenerDateFechaResolucion(resolucion));
            }

            fila.setFechaResolucionStr(obtenerStrFechaResolucion(resolucion));
            fila.setFechaDeclaracionDestinoStr(Utils.formatearFecha(compDTO.getFechaPresentaDec()));
            fila.setImporteCompensadoStr(compDTO.getImporteCompensado().toString());
            fila.setFechaFin(null);
            fila.setImporteComphist(calcularCompensado(fila, movSaldoList));

            filas.add(fila);
        }        

        return filas;
    }
    
    private BigDecimal calcularCompensado(FilaGridCompensacionesBean compensacion, List<DyctMovSaldoDTO> movSaldoList) {
        BigDecimal compensado = BigDecimal.ZERO;
                List<DyctMovSaldoDTO> movSaldoDTOs = obtenerMovSaldoXNumControl(movSaldoList, compensacion.getNumControl());
                for (DyctMovSaldoDTO movSaldoDTO : movSaldoDTOs) {
                    if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.ABONO.getIdAfectacion())) {
                        compensado = compensado.subtract(movSaldoDTO.getImporte());
                    } else if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.CARGO.getIdAfectacion())) {
                        compensado = compensado.add(movSaldoDTO.getImporte());
                    }
                }        
        return compensado;
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
            if (!isNull && dyctMovSaldoDTO.getIdOrigen().trim().equals(numControl.trim())) {
                dyctMovSaldoDTO = movSaldoList.remove(i);
                movsSaldo.add(dyctMovSaldoDTO);
                --i;
            }
        }
        return movsSaldo;
    }

    private String obtenerStrFechaResolucion(DyctResolCompDTO resolucion)
    {
        LOG.debug("INICIO obtenerStrFechaResolucion");
        if(resolucion == null || resolucion.getDyccEstResolDTO() != Constantes.EstadosResolucion.APROBADA){
            return "";
        }
        return Utils.formatearFecha(resolucion.getFechaResolucion());
    }

    private String obtenerStrTipoResolucion(DyctResolCompDTO resolucion)
    {
        if(resolucion == null || resolucion.getDyccTipoResolDTO() == null || resolucion.getDyccEstResolDTO() != Constantes.EstadosResolucion.APROBADA){
            return "";
        }

        if(resolucion.getDyccTipoResolDTO() == Constantes.TiposResolucion.REGISTRAR_CASOCOMP){
            return "Registrada";
        }

        return resolucion.getDyccTipoResolDTO().getDescripcion();
    }        
    
    private Date obtenerDateFechaResolucion(DyctResolCompDTO resolucion)
    {
        if(resolucion == null || resolucion.getDyccEstResolDTO() != Constantes.EstadosResolucion.APROBADA){
            return null;
        }

        return resolucion.getFechaResolucion();
    }
}
