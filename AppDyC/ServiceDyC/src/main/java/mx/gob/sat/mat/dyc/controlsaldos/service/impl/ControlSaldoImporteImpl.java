package mx.gob.sat.mat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.dyc.controlsaldos.service.ControlSaldoImporte;
import mx.gob.sat.siat.dyc.controlsaldos.util.DeclaracionUtil;
import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctCompHistoricaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovDevolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author gregorio.serapio
 */
@Service(value = "controlSaldoImporte")
public class ControlSaldoImporteImpl implements ControlSaldoImporte {

    private static final Logger LOG = Logger.getLogger(ControlSaldoImporteImpl.class);
    
    @Override
    public ControlSaldoImportesDTO calcularImportes(DyctSaldoIcepDTO saldoIcep) throws SIATException {
        List<DyctMovSaldoDTO> movSaldoList = new ArrayList<DyctMovSaldoDTO>(saldoIcep.getDyctMovSaldoList());

        try {
            ControlSaldoImportesDTO importesDTO = new ControlSaldoImportesDTO();
            importesDTO.setSaldoFavor(calcularSaldoFavor(saldoIcep.getDyctDeclaraIcepList(), movSaldoList));
            importesDTO.setDevuelto(calcularDevuelto(saldoIcep.getDycpSolicitudList(), movSaldoList));
            importesDTO.setCompensado(calcularCompensado(saldoIcep.getDycpCompensacionList(), movSaldoList));
            importesDTO.setEfectuado(calcularCargoExterno(movSaldoList, Constantes.MovsIcep.CARGO_DEVCOMPS_ANTERIORES.getIdMovimiento()));
            importesDTO.setAcreditamiento(calcularCargoExterno(movSaldoList, Constantes.MovsIcep.CARGO_ACREDITAMIENTO.getIdMovimiento()));
            importesDTO.setDevueltoHistorico(calcularDevueltoHistorico(saldoIcep.getDyctMovDevolucionList(), movSaldoList));
            importesDTO.setCompensadoHistorico(calcularCompensadoHistorico(saldoIcep.getDyctCompHistoricaList(), movSaldoList));

            BigDecimal resuelto = importesDTO.getDevuelto().add(importesDTO.getCompensado()).add(importesDTO.getEfectuado()).add(importesDTO.getAcreditamiento()).add(importesDTO.getDevueltoHistorico()).add(importesDTO.getCompensadoHistorico());
            importesDTO.setResuelto(resuelto);

            BigDecimal remanente = importesDTO.getSaldoFavor().subtract(importesDTO.getResuelto());
            importesDTO.setAjuste(calcularAjuste(movSaldoList));

            if (importesDTO.getAjuste().compareTo(BigDecimal.ZERO) < 0) {
                remanente = remanente.subtract(importesDTO.getAjuste().abs());
            } else {
                remanente = remanente.add(importesDTO.getAjuste());
            }

            importesDTO.setRemanente(remanente);

            return importesDTO;
        } catch (Exception e) {
            throw new SIATException("ERROR AL CALCULAR IMPORTES DEL ICEP " + saldoIcep, e);
        }
    }

    private BigDecimal calcularSaldoFavor(List<DyctDeclaraIcepDTO> dyctDeclaraIcep, List<DyctMovSaldoDTO> movSaldoList) {
        BigDecimal saldoFavor;

        if (!dyctDeclaraIcep.isEmpty()) {
            DyctDeclaraIcepDTO declaraIcepDTO = DeclaracionUtil.obtenerUtltimaDecEfectiva(dyctDeclaraIcep);
            if (declaraIcepDTO != null) {
                List<DyctMovSaldoDTO> movSaldoDTOs = obtenerMovSaldoXNumControl(movSaldoList, declaraIcepDTO.getNumOperacion().toString());
                saldoFavor = (!movSaldoDTOs.isEmpty()) ? movSaldoDTOs.get(movSaldoDTOs.size() - 1).getImporte() : BigDecimal.ZERO;                
            } else {
                saldoFavor = BigDecimal.ZERO;
            }
            
            for (DyctDeclaraIcepDTO declaraIcep : dyctDeclaraIcep) {
                List<DyctMovSaldoDTO> movSaldo = obtenerMovSaldoXNumControl(movSaldoList, declaraIcep.getNumOperacion().toString());
                LOG.info(movSaldo);
            }
        } else {
            saldoFavor = obtenerSaldoSinDeclaracion(movSaldoList);
        }

        return saldoFavor;
    }

    private BigDecimal obtenerSaldoSinDeclaracion(List<DyctMovSaldoDTO> movSaldoList) {
        if (movSaldoList.isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            DyctMovSaldoDTO dyctMovSaldoDTO;
            for (int i = 0; i < movSaldoList.size(); i++) {
                dyctMovSaldoDTO = movSaldoList.get(i);
                if (dyctMovSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.ABONO.getIdAfectacion())
                        && validarMovimiento(dyctMovSaldoDTO)) {
                    dyctMovSaldoDTO = movSaldoList.remove(i);
                    return dyctMovSaldoDTO.getImporte();
                }
            }
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal calcularDevuelto(List<DycpSolicitudDTO> dycpSolicitudList, List<DyctMovSaldoDTO> movSaldoList) {
        BigDecimal devuelto = BigDecimal.ZERO;

        DyctResolucionDTO resolucionDTO;
        for (DycpSolicitudDTO dycpSolicitudDTO : dycpSolicitudList) {
            resolucionDTO = dycpSolicitudDTO.getDyctResolucionDTO();
            if (resolucionDTO != null && resolucionDTO.getDyccEstreSolDTO().getIdEstResol().equals(Constantes.EstadosResolucion.APROBADA.getIdEstResol())) {

                List<DyctMovSaldoDTO> movSaldoDTOs = obtenerMovSaldoXNumControl(movSaldoList, dycpSolicitudDTO.getNumControl());
                for (DyctMovSaldoDTO movSaldoDTO : movSaldoDTOs) {
                    if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.ABONO.getIdAfectacion())) {
                        devuelto = devuelto.subtract(movSaldoDTO.getImporte());
                    } else if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.CARGO.getIdAfectacion())) {
                        devuelto = devuelto.add(movSaldoDTO.getImporte());
                    }
                }

            }
        }

        return devuelto;

    }

    private BigDecimal calcularDevueltoHistorico(List<DyctMovDevolucionDTO> dyctMovDevolucionList, List<DyctMovSaldoDTO> movSaldoList) {

        BigDecimal devueltoHistorico = BigDecimal.ZERO;
        for (DyctMovDevolucionDTO movDevolucion : dyctMovDevolucionList) {
            if (movDevolucion.getFechaFin() == null) {
                List<DyctMovSaldoDTO> movSaldoDTOs = obtenerMovSaldoXNumControl(movSaldoList, movDevolucion.getNumControl());
                for (DyctMovSaldoDTO movSaldoDTO : movSaldoDTOs) {
                    if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.ABONO.getIdAfectacion())) {
                        devueltoHistorico = devueltoHistorico.subtract(movSaldoDTO.getImporte());
                    } else if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.CARGO.getIdAfectacion())) {
                        devueltoHistorico = devueltoHistorico.add(movSaldoDTO.getImporte());
                    }
                }
            }
        }
        return devueltoHistorico;
    }

    private BigDecimal calcularCompensado(List<DycpCompensacionDTO> dycpCompensacionList, List<DyctMovSaldoDTO> movSaldoList) {
        BigDecimal compensado = BigDecimal.ZERO;

        String estAfecDirecto = Constantes.EstadosCompensacion.EN_PROCESO.getIdEstadoComp() + "|" + Constantes.EstadosCompensacion.PENDIENTE_RESOLVER.getIdEstadoComp() + "|" + Constantes.EstadosCompensacion.REQUERIDO.getIdEstadoComp() + "|" + Constantes.EstadosCompensacion.EN_REVISION.getIdEstadoComp();
        String estadoComp;
        DyctResolCompDTO resolucionDTO;
        for (DycpCompensacionDTO compensacionDTO : dycpCompensacionList) {
            resolucionDTO = compensacionDTO.getDyctResolCompDTO();
            estadoComp = compensacionDTO.getDyccEstadoCompDTO().getIdEstadoComp().toString();
            if (estadoComp.matches(estAfecDirecto)) {
                compensado = compensado.add(compensacionDTO.getImporteCompensado());
            } else if ((!estadoComp.matches(estAfecDirecto)) && resolucionDTO != null && resolucionDTO.getDyccEstResolDTO().getIdEstResol().equals(Constantes.EstadosResolucion.APROBADA.getIdEstResol())) {

                List<DyctMovSaldoDTO> movSaldoDTOs = obtenerMovSaldoXNumControl(movSaldoList, compensacionDTO.getNumControl());
                for (DyctMovSaldoDTO movSaldoDTO : movSaldoDTOs) {
                    if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.ABONO.getIdAfectacion())) {
                        compensado = compensado.subtract(movSaldoDTO.getImporte());
                    } else if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.CARGO.getIdAfectacion())) {
                        compensado = compensado.add(movSaldoDTO.getImporte());
                    }
                }

            }
        }

        return compensado;
    }

    private BigDecimal calcularCompensadoHistorico(List<DyctCompHistoricaDTO> dyctCompHistoricaList, List<DyctMovSaldoDTO> movSaldoList) {

        BigDecimal compensadoHistorico = BigDecimal.ZERO;
        for (DyctCompHistoricaDTO compHistoricaDTO : dyctCompHistoricaList) {
            if (compHistoricaDTO.getFechaFin() == null) {

                List<DyctMovSaldoDTO> movSaldoDTOs = obtenerMovSaldoXNumControl(movSaldoList, compHistoricaDTO.getNumControl());
                for (DyctMovSaldoDTO movSaldoDTO : movSaldoDTOs) {
                    if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.ABONO.getIdAfectacion())) {
                        compensadoHistorico = compensadoHistorico.subtract(movSaldoDTO.getImporte());
                    } else if (movSaldoDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion().equals(Constantes.AfectacionesIcep.CARGO.getIdAfectacion())) {
                        compensadoHistorico = compensadoHistorico.add(movSaldoDTO.getImporte());
                    }
                }
            }
        }
        return compensadoHistorico;
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

    private BigDecimal calcularCargoExterno(List<DyctMovSaldoDTO> movSaldoList, Integer idCargoExterno) {
        BigDecimal cargo = BigDecimal.ZERO;
        DyctMovSaldoDTO dyctMovSaldoDTO;
        for (int i = 0; i < movSaldoList.size(); i++) {
            dyctMovSaldoDTO = movSaldoList.get(i);
            if (dyctMovSaldoDTO.getDyccMovIcepDTO().getIdMovimiento().equals(idCargoExterno) && validarMovimiento(dyctMovSaldoDTO)) {
                cargo = cargo.add(dyctMovSaldoDTO.getImporte());
                dyctMovSaldoDTO = movSaldoList.remove(i);
                LOG.info(dyctMovSaldoDTO);
                --i;
            }
        }

        return cargo;
    }

    private BigDecimal calcularAjuste(List<DyctMovSaldoDTO> movSaldoList) {
        BigDecimal ajuste = BigDecimal.ZERO;
        DyctMovSaldoDTO dyctMovSaldoDTO;
        for (int i = 0; i < movSaldoList.size(); i++) {
            dyctMovSaldoDTO = movSaldoList.get(i);
            if (validarMovimiento(dyctMovSaldoDTO)) {

                dyctMovSaldoDTO = movSaldoList.remove(i);
                LOG.info(dyctMovSaldoDTO);
                --i;

                if (dyctMovSaldoDTO.getDyccMovIcepDTO() == Constantes.MovsIcep.ABONO_AJUSTE) {
                    ajuste = ajuste.add(dyctMovSaldoDTO.getImporte());
                } else if (dyctMovSaldoDTO.getDyccMovIcepDTO() == Constantes.MovsIcep.CARGO_AJUSTE) {
                    ajuste = ajuste.subtract(dyctMovSaldoDTO.getImporte());
                }
                
            }
        }

        return ajuste;
    }
}
