package mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.controlsaldos.service.impl.ObtenerFechaHistoricaServiceImpl;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.CalcularSaldoRemanenteICEPService;
import mx.gob.sat.siat.dyc.controlsaldos.vo.MovimientoSaldoBean;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "calcularSaldoRemanenteICEPService")
public class CalcularSaldoRemanenteICEPServiceImpl implements CalcularSaldoRemanenteICEPService {

    private static final Logger LOG = Logger.getLogger(CalcularSaldoRemanenteICEPServiceImpl.class);

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;

    @Autowired
    private ConvertirSaldoServiceImpl convertidor;

    @Autowired
    private ObtenerMovsDiferidosServiceImpl serviceObtenerMovsDifs;

    @Autowired
    private ObtenerFechaHistoricaServiceImpl serviceObtenerFechaHistorica;

    private static final String REMANENTE_REAL = "remanenteReal";

    @Override
    public Map<String, Object> obtenerDatosRemanente(DyctSaldoIcepDTO saldoIcep) throws SIATException {
        Map<String, Object> datos = new HashMap<String, Object>();
        List<MovimientoSaldoBean> movsCalcular = obtenerMovsCalcular(saldoIcep);
        Date fechaHistorica;
        Date fechaPrimerDecValidada;
        try {
            fechaHistorica = serviceObtenerFechaHistorica.execute(saldoIcep);
            fechaPrimerDecValidada = serviceObtenerFechaHistorica.obtenerPrimerDeclaracionValida(saldoIcep);
        } catch (SIATException siatEx) {
            LOG.debug("siatEx:: " + siatEx);
            datos.put("fechaCalculo", null);
            datos.put("remanenteRazo", 0d);
            datos.put(REMANENTE_REAL, 0d);
            return datos;
        }

        LOG.debug("idSaldoIcep ->" + saldoIcep.getIdSaldoIcep() + "<- numero de movs a calcular ->" +
                  movsCalcular.size());
        LOG.debug("fechaHistorica del idSaldoIcep " + saldoIcep.getIdSaldoIcep() + " ->" + fechaHistorica);
        datos.put("fechaCalculo", fechaHistorica);
        datos.put("fechaPrimerDecValidada", fechaPrimerDecValidada);
        datos.put("remanenteRazo", calcularRazo2(movsCalcular));
        if (!saldoIcep.getDyctDeclaraIcepList().isEmpty()) {
            datos.put(REMANENTE_REAL, calcularRemanenteActualizando(movsCalcular, fechaHistorica));
        } else {
            datos.put(REMANENTE_REAL, datos.get("remanenteRazo"));
        }

        saldoIcep.setRemanente(new BigDecimal((Double)datos.get(REMANENTE_REAL)));
        saldoIcep.setFechaBaseCalculo(fechaHistorica);
        saldoIcep.setActRemanente(new Date());
        daoSaldoIcep.actualizarRemFebaseActrem(saldoIcep);

        return datos;
    }

    public Double execute(DyctSaldoIcepDTO saldoIcep, Date fechaCalculo) throws SIATException {
        List<MovimientoSaldoBean> movsCalcular = obtenerMovsCalcular(saldoIcep);
        Double rem = calcularRemanenteActualizando(movsCalcular, fechaCalculo);
        saldoIcep.setRemanente(new BigDecimal(rem));
        saldoIcep.setFechaBaseCalculo(fechaCalculo);
        saldoIcep.setActRemanente(new Date());
        daoSaldoIcep.actualizarRemFebaseActrem(saldoIcep);

        return rem;
    }

    @Override
    public BigDecimal execute(DyctSaldoIcepDTO saldoIcep) throws SIATException {
        //el remanente se regresa a la fecha de la ultima declaración efectuada
        if (saldoIcep.getDyccOrigenSaldoDTO() == null) {
            DyctSaldoIcepDTO saldoIcepAux = daoSaldoIcep.encontrar(saldoIcep.getIdSaldoIcep());
            saldoIcep.setRfc(saldoIcepAux.getRfc());
            saldoIcep.setDyccConceptoDTO(saldoIcepAux.getDyccConceptoDTO());
            saldoIcep.setDyccPeriodoDTO(saldoIcepAux.getDyccPeriodoDTO());
            saldoIcep.setDyccEjercicioDTO(saldoIcepAux.getDyccEjercicioDTO());
            saldoIcep.setDyccOrigenSaldoDTO(saldoIcepAux.getDyccOrigenSaldoDTO());
            saldoIcep.setFechaFin(saldoIcepAux.getFechaFin());
        }

        List<DyctMovSaldoDTO> movsTodos = daoMovSaldo.selecXSaldoicep(saldoIcep);
        List<DyctMovSaldoDTO> movsValidos = new ArrayList<DyctMovSaldoDTO>();
        for (DyctMovSaldoDTO movIt : movsTodos) {
            LOG.debug("idMovSaldo ->" + movIt.getIdMovSaldo());
            List<DyctAccionMovSalDTO> acciones = movIt.getDyctAccionMovSalDTOList();
            LOG.debug("acciones ->" + acciones);
            if (acciones == null || acciones.isEmpty()) {
                LOG.debug("No existen acciones para el ICEP ->" + saldoIcep.getIdSaldoIcep());
                movsValidos.add(movIt);
            } else {
                DyctAccionMovSalDTO ultimaAccion = acciones.get(acciones.size() - 1);
                if (ultimaAccion.getTipoAccionMovSal() == mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.VALIDAR ||
                    ultimaAccion.getTipoAccionMovSal() == mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.CREAR) {
                    movsValidos.add(movIt);
                }
            }
        }

        saldoIcep.setDyctMovSaldoList(movsValidos);

        LOG.debug("CalcularSaldoRemanenteICEPServiceImpl-numero de movimientos ->" +
                  saldoIcep.getDyctMovSaldoList().size() + "<-");
        
        if (saldoIcep.getDyctDeclaraIcepList() == null || saldoIcep.getDyctDeclaraIcepList().isEmpty()) {
            saldoIcep.setDyctDeclaraIcepList(daoDeclaraIcep.selecXSaldoicepOrder(saldoIcep,
                    " ORDER BY TRUNC(FECHAPRESENTACION) ASC, IDTIPODECLARACION ASC, FECHAREGISTRO ASC "));
        }

        if (!saldoIcep.getDyctDeclaraIcepList().isEmpty()) {
            List<MovimientoSaldoBean> movsDiferidos = serviceObtenerMovsDifs.execute(saldoIcep.getDyctDeclaraIcepList());
            if (movsDiferidos != null && !movsDiferidos.isEmpty()) {
                LOG.debug("numero de movsDiferidos ->" + movsDiferidos.size() + "<-");
                Date fechaActualizacion = movsDiferidos.get(movsDiferidos.size() - 1).getFecha();
                LOG.debug("fechaActualizacion ->" + fechaActualizacion + "<-");
                List<DyctMovSaldoDTO> movsYaAgregados = identificarMovsSaldoAfavor(saldoIcep);
                LOG.debug("numero de movsYaAgregados ->" + movsYaAgregados.size() + "<-");
                for (DyctMovSaldoDTO dto : saldoIcep.getDyctMovSaldoList()) {
                    if (!movsYaAgregados.contains(dto)) {
                        movsDiferidos.add(convertirDTOaBean(dto));
                    }
                }

                return new BigDecimal(calcularRemanenteActualizando(movsDiferidos, fechaActualizacion));
            }
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(calcularRazo(saldoIcep.getDyctMovSaldoList()));
        }
    }

    private double calcularRazo(List<DyctMovSaldoDTO> movimientos) {
        double totalAbonos = 0d;
        double totalCargos = 0d;

        for (DyctMovSaldoDTO movimiento : movimientos) {
            if (movimiento.getDyccMovIcepDTO().getDyccAfectaIcepDTO() == Constantes.AfectacionesIcep.ABONO) {
                totalAbonos += movimiento.getImporte().doubleValue();
            } else if (movimiento.getDyccMovIcepDTO().getDyccAfectaIcepDTO() == Constantes.AfectacionesIcep.CARGO) {
                totalCargos += movimiento.getImporte().doubleValue();
            }
        }

        LOG.debug("totalAbonos ->" + totalAbonos + "<-");
        LOG.debug("totalCargos ->" + totalCargos + "<-");

        return totalAbonos - totalCargos;
    }

    private double calcularRazo2(List<MovimientoSaldoBean> movimientos) {
        double totalAbonos = 0d;
        double totalCargos = 0d;

        for (MovimientoSaldoBean movimiento : movimientos) {
            if (movimiento.getTipo().intValue() == Constantes.AfectacionesIcep.ABONO.getIdAfectacion().intValue()) {
                totalAbonos += movimiento.getImporte();
            } else if (movimiento.getTipo().intValue() ==
                       Constantes.AfectacionesIcep.CARGO.getIdAfectacion().intValue()) {
                totalCargos += movimiento.getImporte();
            }
        }

        LOG.debug("totalAbonos ->" + totalAbonos + "<-");
        LOG.debug("totalCargos ->" + totalCargos + "<-");

        return totalAbonos - totalCargos;
    }

    private double calcularRemanenteActualizando(List<MovimientoSaldoBean> movimientos, Date fechaActualizacion) {
        LOG.debug("numero de movimientos ->" + movimientos.size() + "<-");
        LOG.debug("fechaActualizacion ->" + fechaActualizacion + "<-");
        double remanente = 0d;
        for (MovimientoSaldoBean mov : movimientos) {
            LOG.debug("fecha movimiento ---->" + mov.getFecha() + " importeMovimiento ->" + mov.getImporte() + "<-");
            Date origenNoNull = (mov.getFecha() == null)? fechaActualizacion :  mov.getFecha();
            /**if (mov.getValidacionDWH() != null) {*/
                double saldoConvertido = convertidor.execute(origenNoNull, fechaActualizacion, mov.getImporte());
                LOG.debug("saldoConvertido ->" + saldoConvertido + "<-");
                LOG.debug("mov.getTipo() ->" + mov.getTipo() + "<-");
                if (mov.getTipo() == 1) {
                    remanente += saldoConvertido;
                } else if (mov.getTipo() == 2) {
                    remanente -= saldoConvertido;
                }
            /**}*/
            LOG.debug("remanente parcial ->" + remanente + "<-");
        }
        return remanente;
    }

    private MovimientoSaldoBean convertirDTOaBean(DyctMovSaldoDTO dto) {
        MovimientoSaldoBean bean = new MovimientoSaldoBean();
        bean.setTipo(dto.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion());
        bean.setImporte(dto.getImporte().doubleValue());
        bean.setFecha(dto.getFechaOrigen());
        return bean;
    }

    private List<DyctMovSaldoDTO> identificarMovsSaldoAfavor(DyctSaldoIcepDTO saldoIcep) {
        LOG.debug("INICIO identificarMovsSaldoAfavor");
        List<DyctMovSaldoDTO> movsDeclaracion = new ArrayList<DyctMovSaldoDTO>();

        for (DyctMovSaldoDTO movSaldoDTO : saldoIcep.getDyctMovSaldoList()) {
            if (esSaldoAFavor(movSaldoDTO)) {
                movsDeclaracion.add(movSaldoDTO);
            }
        }
        return movsDeclaracion;
    }

    private boolean esSaldoAFavor(DyctMovSaldoDTO movimiento) {
        if (movimiento.getDyccMovIcepDTO().getDyccAfectaIcepDTO() == Constantes.AfectacionesIcep.ABONO) {
            int tipoMov = movimiento.getDyccMovIcepDTO().getIdMovimiento();
            LOG.debug("tipoMov ->" + tipoMov);
            return (tipoMov == Constantes.MovsIcep.ALTA_SALDO.getIdMovimiento().intValue() ||
                    tipoMov == Constantes.MovsIcep.ABONO_SAFDWH.getIdMovimiento().intValue() ||
                    tipoMov == Constantes.MovsIcep.ABONO_SAFCONTRIB.getIdMovimiento().intValue() ||
                    tipoMov == Constantes.MovsIcep.ABONO_SAFCONTRIB_AVICOMP.getIdMovimiento().intValue());
        }

        return false;
    }

    private List<MovimientoSaldoBean> obtenerMovsCalcular(DyctSaldoIcepDTO saldoIcep) throws SIATException {
        LOG.debug("INICIO obtenerMovsCalcular");
        if (saldoIcep.getDyctMovSaldoList() == null) {
            saldoIcep.setDyctMovSaldoList(daoMovSaldo.selecXSaldoicep(saldoIcep));
        }

        List<DyctMovSaldoDTO> movsValidos = new ArrayList<DyctMovSaldoDTO>();
        LOG.debug("INICIO pruebas MOVIMIENTO INVALIDADOS ------------------------");
        List<DyctMovSaldoDTO> movs = saldoIcep.getDyctMovSaldoList();
        for (DyctMovSaldoDTO movIt : movs) {
            LOG.debug("idMovSaldo ->" + movIt.getIdMovSaldo());
            List<DyctAccionMovSalDTO> acciones = movIt.getDyctAccionMovSalDTOList();
            LOG.debug("acciones ->" + acciones);
            if (acciones == null || acciones.isEmpty()) {
                LOG.debug("No existen acciones para el ICEP ->" + saldoIcep.getIdSaldoIcep());
                movsValidos.add(movIt);
            } else {
                DyctAccionMovSalDTO ultimaAccion = acciones.get(acciones.size() - 1);
                if (ultimaAccion.getTipoAccionMovSal() == mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.VALIDAR ||
                    ultimaAccion.getTipoAccionMovSal() == mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.CREAR) {
                    movsValidos.add(movIt);
                }
            }
        }
        LOG.debug("FIN pruebas MOVIMIENTO INVALIDADOS ------------------------");
        LOG.debug("movsValidos ->" + movsValidos);
        saldoIcep.setDyctMovSaldoList(movsValidos);

        if (saldoIcep.getDyctDeclaraIcepList() == null) {
            LOG.debug("el icep no trae declaraciones, se buscaran");
            saldoIcep.setDyctDeclaraIcepList(daoDeclaraIcep.selecXSaldoicepOrder(saldoIcep,
                                                                                 " ORDER BY FECHAPRESENTACION ASC, IDTIPODECLARACION ASC "));
        }

        List<MovimientoSaldoBean> movsDiferidos;
        if (!saldoIcep.getDyctDeclaraIcepList().isEmpty()) {
            movsDiferidos = serviceObtenerMovsDifs.execute(saldoIcep.getDyctDeclaraIcepList());
            LOG.debug("num de movs diferidos ---->" + movsDiferidos.size());
            List<DyctMovSaldoDTO> movsYaAgregados = identificarMovsSaldoAfavor(saldoIcep);
            LOG.debug("movsYaAgregados.size() ->" + movsYaAgregados.size());
            for (DyctMovSaldoDTO dto : saldoIcep.getDyctMovSaldoList()) {
                if (!movsYaAgregados.contains(dto)) {
                    movsDiferidos.add(convertirDTOaBean(dto));
                }
            }
        } else {
            movsDiferidos = new ArrayList<MovimientoSaldoBean>();
            for (DyctMovSaldoDTO dto : saldoIcep.getDyctMovSaldoList()) {
                movsDiferidos.add(convertirDTOaBean(dto));
            }
        }

        LOG.debug("FIN obtenerMovsCalcular; movsDiferidos.size() ->" + movsDiferidos.size());
        return movsDiferidos;
    }

    @Override
    public BigDecimal calcularRemanenteOptimiRec(DyctSaldoIcepDTO saldoIcep) {
        try {
            Map<String, Object> datosRemanente = obtenerDatosRemanente(saldoIcep);
            return new BigDecimal((Double)datosRemanente.get(REMANENTE_REAL));
        } catch (SIATException e) {
            LOG.error("Ocurrio un error en calcularRemanenteOptimiRec ->" + e.getMessage());
            return null;
        }
    }
}
