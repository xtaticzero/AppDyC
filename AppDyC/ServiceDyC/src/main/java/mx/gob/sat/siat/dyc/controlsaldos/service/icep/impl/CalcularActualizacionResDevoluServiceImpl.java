/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
 */
package mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.mat.dyc.controlsaldos.service.impl.ObtenerFechaHistoricaServiceImpl;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.dto.ActualizacionDetalleDTO;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.facade.ActualizadorFacade;
import mx.gob.sat.siat.dyc.controlsaldos.dto.ParametroActDevDTO;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.CalcularActualizacionResDevoluService;
import mx.gob.sat.siat.dyc.controlsaldos.util.EnumCompBigDecimal;
import mx.gob.sat.siat.dyc.controlsaldos.vo.ActualizacionMontoParcialVO;
import mx.gob.sat.siat.dyc.controlsaldos.vo.DyctDeclaraIcepAuxVO;
import mx.gob.sat.siat.dyc.controlsaldos.vo.MontoParcialVO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.generico.util.FechaUtil;
import mx.gob.sat.siat.dyc.trabajo.util.DyctDeclaraICEPUtil;
import mx.gob.sat.siat.dyc.trabajo.util.constante.EnumErrorCalculoActDev;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;
import mx.gob.sat.siat.dyc.vo.DyctDeclaraIcepAuxDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para el calculo de actualizacion de resolucion de devoluciones en el
 * control de saldos.
 *
 * @author Kevin Mendez
 * @since Enero 20, 2014
 *
 */
@Service(value = "calcularActualizacionResDevoluService")
public class CalcularActualizacionResDevoluServiceImpl implements CalcularActualizacionResDevoluService {

    private static final Logger LOG = Logger.getLogger(CalcularActualizacionResDevoluServiceImpl.class);
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private DyctSaldoIcepDAO dyctSaldoIcepDAO;

    @Autowired
    private DyctDeclaraIcepDAO dyctDeclaraIcepDAO;

    @Autowired(required=false)
    private ActualizadorFacade actualizadorFacade;

    @Autowired
    private ObtenerFechaHistoricaServiceImpl serviceObtFechaHist;

    @Override
    public ActualizacionMontoParcialVO calcular(ParametroActDevDTO parametroActDevDTO) throws SIATException {
        ActualizacionMontoParcialVO actualizacionMontoParcialDTO = generarCalculo(parametroActDevDTO);

        if (actualizacionMontoParcialDTO != null && parametroActDevDTO.getModuloOrigen() != null && !parametroActDevDTO.getModuloOrigen().equals(ConstantesDyC3.MODULO_SALDOS)) {
            actualizacionMontoParcialDTO.setMontosParciales(null);
        }

        return actualizacionMontoParcialDTO;

    }

    /**
     * Servicio principal del caso de uso CALCULAR ACTUALIZACION DE RESOLUCION
     * DE DEVOLUCIONES.
     *
     * @modulo Control de Saldos
     * @since 23-Ene-2014
     * @param parametroActDevDTO
     */
    private ActualizacionMontoParcialVO generarCalculo(ParametroActDevDTO parametroActDevDTO) throws SIATException {
        List<DyctDeclaraIcepAuxVO> listaDeclaracionesAux;

        //Valida que el saldo exista: 
        validarPrimeraParte(parametroActDevDTO);        
        DyctSaldoIcepDTO saldoIcep;
        if(parametroActDevDTO.getIcepVO().getSaldoIcep() != null){
            saldoIcep = parametroActDevDTO.getIcepVO().getSaldoIcep();
        } else {
            saldoIcep = dyctSaldoIcepDAO.obtenerXId(parametroActDevDTO.getIcepVO().getIdSaldoIcep());
        }

        listaDeclaracionesAux = new ArrayList<DyctDeclaraIcepAuxVO>();
        LOG.debug("idSaldoIcep ->" + saldoIcep.getIdSaldoIcep() + "<-");
        List<DyctDeclaraIcepAuxDTO> listaDeclaracionesIcep;
        if(saldoIcep.getDyctDeclaraIcepList() != null){
            listaDeclaracionesIcep = new ArrayList<DyctDeclaraIcepAuxDTO>();
            for (DyctDeclaraIcepDTO declaraIcep : saldoIcep.getDyctDeclaraIcepList()) {
                DyctDeclaraIcepAuxDTO vo = new DyctDeclaraIcepAuxDTO();
                vo.setDyctSaldoIcepDTO(declaraIcep.getDyctSaldoIcepDTO());
                vo.setIdDeclaraIcep(declaraIcep.getIdDeclaraIcep());
                vo.setNumOperacion(declaraIcep.getNumOperacion());
                vo.setSaldoAfavor(declaraIcep.getSaldoAFavor());
                vo.setFechaPresentacion(declaraIcep.getFechaPresentacion());
                vo.setDyccTipoDeclaraDTO(declaraIcep.getDyccTipoDeclaraDTO());
                vo.setAltaSaldo(declaraIcep.getSaldoAFavor());
                vo.setValidacionDWH(declaraIcep.getValidacionDWH());
                listaDeclaracionesIcep.add(vo);
            }
        }else{
            listaDeclaracionesIcep = dyctDeclaraIcepDAO.obtenerRemanenteXIdSaldoIcep(saldoIcep.getIdSaldoIcep());
        }

        if (listaDeclaracionesIcep.isEmpty()) {
            LOG.info("listaDeclaracionesIcep esta vacia, NO existen declaraciones asociadas al ICEP " + saldoIcep.getIdSaldoIcep()
                    + " se calculará actualizacion basandose en la fecha de causacion");

            Date fechaHistorica = serviceObtFechaHist.execute(saldoIcep);

            LOG.info("fechaHistorica ->" + fechaHistorica + "<-");

            BigDecimal montoAutorizado = parametroActDevDTO.getMontoAutorizado();

            ActualizacionDetalleDTO resultadoCalculo = actualizadorFacade.calcularActualizacion(fechaHistorica, new Date(), montoAutorizado);

            BigDecimal importeResul = resultadoCalculo.getActualizacion();
            if (!resultadoCalculo.isActualizable()) {
                importeResul = BigDecimal.ZERO;
                resultadoCalculo.setFactorActzn(BigDecimal.ONE);
            }

            BigDecimal totalActualizacion = new BigDecimal(0d);
            totalActualizacion = totalActualizacion.add(importeResul);

            List<ActualizacionDetalleDTO> lInpcDetallado = new ArrayList<ActualizacionDetalleDTO>();
            lInpcDetallado.add(resultadoCalculo);

            List<MontoParcialVO> lDetalleCalculoSaldoDTO = new ArrayList<MontoParcialVO>();
            MontoParcialVO montoParcialVO = new MontoParcialVO();
            montoParcialVO.setIdSaldoIcep(saldoIcep.getIdSaldoIcep());
            montoParcialVO.setActualizacion(importeResul.doubleValue());
            lDetalleCalculoSaldoDTO.add(montoParcialVO);

            ActualizacionMontoParcialVO objActualizacion = new ActualizacionMontoParcialVO();
            objActualizacion.setTotalActualizacion(totalActualizacion);
            objActualizacion.setActualizacionDetalleDTO(lInpcDetallado);
            objActualizacion.setMontosParciales(lDetalleCalculoSaldoDTO);
            return objActualizacion;
        } else {
            for (DyctDeclaraIcepAuxDTO objDto : listaDeclaracionesIcep) {
                if (objDto.getValidacionDWH() != null) {
                    listaDeclaracionesAux.add(new DyctDeclaraIcepAuxVO(objDto));
                }
            }

            if (listaDeclaracionesAux.size() == ConstantesDyC3.TAMANIO_DECLARACION_NORMAL) {
                DyctDeclaraICEPUtil.identificarMontoDeclaracionesSimples(listaDeclaracionesAux, parametroActDevDTO.getMontoAutorizado().doubleValue());
            } else if (listaDeclaracionesAux.size() > ConstantesDyC3.TAMANIO_DECLARACION_NORMAL) {
                DyctDeclaraICEPUtil.identificarMontoDeclaracionesMultiples(listaDeclaracionesAux, parametroActDevDTO.getMontoAutorizado().doubleValue());
            }

            return obtenerActualizaciones(parametroActDevDTO.getFechaResolucion(), listaDeclaracionesAux);
        }
    }

    /**
     * Obtiene las actualizaciones utilizando la herramienta de calculo de
     * cobranza.
     *
     * @param fechaResolucion
     * @param listaDeclaracionesAux
     * @return
     * @throws SIATException
     */
    private ActualizacionMontoParcialVO obtenerActualizaciones(Date fechaResolucion,
            List<DyctDeclaraIcepAuxVO> listaDeclaracionesAux) throws SIATException {

        List<ActualizacionDetalleDTO> lInpcDetallado = new ArrayList<ActualizacionDetalleDTO>();
        BigDecimal totalActualizacion = BigDecimal.ZERO;
        List<MontoParcialVO> lDetalleCalculoSaldoDTO = new ArrayList<MontoParcialVO>();

        Date fechaPresentacion = null;
        Date fechaRes = null;
        BigDecimal montoUsado = BigDecimal.ZERO;

        ActualizacionMontoParcialVO actualizacionMontoParcialDTO = new ActualizacionMontoParcialVO();

        try {

            for (DyctDeclaraIcepAuxVO objDto : listaDeclaracionesAux) {

                MontoParcialVO montoParcialVO = new MontoParcialVO();

                montoParcialVO.setIdSaldoIcep(objDto.getDyctSaldoIcepDTO().getIdSaldoIcep());
                montoParcialVO.setIdDeclaraIcep(objDto.getIdDeclaraIcep());
                montoParcialVO.setMontoUsado(objDto.getMontoUsado());
                montoParcialVO.setMontoCargo(objDto.getMontoCargo());
                montoParcialVO.setSaldoAfavorAns(objDto.getSaldoAfavor().doubleValue());
                montoParcialVO.setSaldoAfavorDes(objDto.getSaldoAfavorNuevo());

                lDetalleCalculoSaldoDTO.add(montoParcialVO);

                //para una declaraciòn que el algoritmo de capas se ignoro
                //por tener un SAF menor al SAF anterior
                if (objDto.getMontoUsado().compareTo(BigDecimal.ZERO) == EnumCompBigDecimal.MAYOR.getId()) {
                    montoUsado = objDto.getMontoUsado();
                    fechaPresentacion = df.parse(df.format(objDto.getFechaPresentacion()));
                    fechaRes = df.parse(df.format(fechaResolucion));
                    LOG.debug("fechaPresentacion ->" + fechaPresentacion);
                    LOG.debug("fechaRes ->" + fechaRes);
                    //Para evitar errores en la tabla INPC (Plantilla).
                    if ((fechaRes.getTime() - fechaPresentacion.getTime()) < ConstantesDyC.UNDIA) {
                        fechaPresentacion = new Date(fechaPresentacion.getTime() - ConstantesDyC.UNDIA);
                    }
                    ActualizacionDetalleDTO resultadoCalculo
                            = actualizadorFacade.calcularActualizacion(fechaPresentacion, fechaRes,
                                    objDto.getMontoUsado());

                    if (resultadoCalculo != null) {
                        BigDecimal importeResul = resultadoCalculo.getActualizacion();
                        if (!resultadoCalculo.isActualizable()) {
                            importeResul = BigDecimal.ZERO;
                            resultadoCalculo.setFactorActzn(BigDecimal.ONE);
                        }
                        montoParcialVO.setActualizacion(importeResul.doubleValue());
                        totalActualizacion = totalActualizacion.add(importeResul);
                        lInpcDetallado.add(resultadoCalculo);

                    } else {
                        LOG.error(EnumErrorCalculoActDev.ERROR10.getId() + " "
                                + EnumErrorCalculoActDev.ERROR10.getDescripcion());
                        LOG.error("con lo parametros [fechaPresentacion, fechaResolucion, monto ] " + "[ "
                                + fechaPresentacion + ", " + fechaRes + ", "
                                + montoUsado + "]");
                        throw new SIATException(EnumErrorCalculoActDev.ERROR08.getId(),
                                EnumErrorCalculoActDev.ERROR08.getDescripcion());
                    }
                } else {
                    montoParcialVO.setActualizacion(new Double(0));
                }
            }
        } catch (SIATException se) {
            LOG.error(se.getMessage());
            LOG.error(EnumErrorCalculoActDev.ERROR08.getId() + " " + EnumErrorCalculoActDev.ERROR08.getDescripcion()
                    + " " + se.toString());
            LOG.error("con lo parametros [fechaPresentacion, fechaResolucion, monto ] " + "[ " + fechaPresentacion
                    + ", " + fechaRes + ", " + montoUsado + "]");
            throw new SIATException(EnumErrorCalculoActDev.ERROR08.getId(),
                    EnumErrorCalculoActDev.ERROR08.getDescripcion(), se);
        } catch (ParseException pe) {
            LOG.error(pe.getMessage());
            LOG.error(EnumErrorCalculoActDev.ERROR08.getId() + " " + EnumErrorCalculoActDev.ERROR08.getDescripcion()
                    + " " + pe.toString());
            LOG.error("con lo parametros [fechaPresentacion, fechaResolucion, monto ] "
                    + "[ " + fechaPresentacion + ", " + fechaRes
                    + ", " + montoUsado + "]");
            throw new SIATException(EnumErrorCalculoActDev.ERROR08.getId(),
                    EnumErrorCalculoActDev.ERROR08.getDescripcion(), pe);
        }

        actualizacionMontoParcialDTO.setTotalActualizacion(totalActualizacion);
        actualizacionMontoParcialDTO.setMontosParciales(lDetalleCalculoSaldoDTO);
        actualizacionMontoParcialDTO.setActualizacionDetalleDTO(lInpcDetallado);

        return actualizacionMontoParcialDTO;
    }

    /**
     * <pre>
     * Hace las siguientes validaciones:
     *  - La fecha de resolucion no sea nula y menor a la fecha actual
     *  - El monto autorizado sea mayor o igual a cero.
     *  - Que el saldo exista.
     * </pre>
     *
     * @param parametroActDevDTO
     * @throws SIATException
     */
    private void validarPrimeraParte(ParametroActDevDTO parametroActDevDTO) throws SIATException {
        if (parametroActDevDTO.getFechaResolucion() == null) {
            throw new SIATException(EnumErrorCalculoActDev.ERROR18.getId(),
                    EnumErrorCalculoActDev.ERROR18.getDescripcion());
        }

        if (parametroActDevDTO.getMontoAutorizado().doubleValue() <= 0) {
            throw new SIATException(EnumErrorCalculoActDev.ERROR19.getId(),
                    EnumErrorCalculoActDev.ERROR19.getDescripcion());
        }

        if (FechaUtil.comparar(parametroActDevDTO.getFechaResolucion(), new Date())
                == FechaUtil.Comparacion.MAYOR.getValor()) {
            throw new SIATException(EnumErrorCalculoActDev.ERROR17.getId(),
                    EnumErrorCalculoActDev.ERROR17.getDescripcion());
        }

    }
}
