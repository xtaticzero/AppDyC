package mx.gob.sat.siat.dyc.resolucion.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import mx.gob.sat.mat.dyc.controlsaldos.service.ControlSaldoImporte;
import mx.gob.sat.siat.dyc.resolucion.service.ResolucionService;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.dto.CalculoActualizacionDTO;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.facade.ActualizadorFacade;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.DycConstantesAutomaticasIva;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.EDycAutomaticasIvaEstadoCasoDevolucion;
import mx.gob.sat.siat.dyc.controlsaldos.dto.ParametroActDevDTO;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.CalcularActualizacionResDevoluService;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.CargarSaldoIcepServiceImpl;
import mx.gob.sat.siat.dyc.controlsaldos.vo.ActualizacionMontoParcialVO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstResolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.IcepVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
@Service(value = "resolucionService")
public class ResolucionServiceImpl implements ResolucionService {

    @Autowired
    private CalcularActualizacionResDevoluService calcularActualizacionResDevoluService;

    @Autowired
    private CalcularFechasService calcularFechasService;

    @Autowired(required=false)
    private ActualizadorFacade actualizadorFacade;

    @Autowired
    private DyccTipoTramiteDAO dyccTipoTramiteDAO;

    @Autowired
    private CargarSaldoIcepServiceImpl serviceCargarSaldoIcep;

    @Autowired
    private ControlSaldoImporte controlSaldoImporteService;

    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;

    @Override
    public DyctResolucionDTO calcularActualizacionAutorizacionTotal(DycpSolicitudDTO dycpSolicitudDTO) throws SIATException {

        Integer idTipoTramite = dycpSolicitudDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite();
        BigDecimal actualizacion = BigDecimal.ZERO;
        BigDecimal valIntereses = BigDecimal.ZERO;
        if (!(idTipoTramite + "").matches(ConstantesDyC1.CALCULAR_ACTUALIZACION_E_INTERESES)) {
            ActualizacionMontoParcialVO montoParcialVO = calcularActualizacion(dycpSolicitudDTO);
            actualizacion = montoParcialVO.getTotalActualizacion();

            DyccTipoTramiteDTO dyccTipoTramiteDTO = dyccTipoTramiteDAO.encontrar(idTipoTramite);
            CalculoActualizacionDTO resultadoCalculo = calcularIntereses(dycpSolicitudDTO, dyccTipoTramiteDTO);
            valIntereses = (resultadoCalculo == null) ? BigDecimal.ZERO : resultadoCalculo.getRecargoDetalleDTO().getRecargo();
        }

        BigDecimal impNetoDevolver = dycpSolicitudDTO.getImporteSolicitado().add(actualizacion).add(valIntereses);

        DyctResolucionDTO dyctResolucionDTO = new DyctResolucionDTO();

        dyctResolucionDTO.setDycpSolicitudDTO(dycpSolicitudDTO);
        dyctResolucionDTO.setDyccTipoResolDTO(Constantes.TiposResolucion.AUTORIZADA_TOTAL);
        DyccEstResolDTO dyccEstreSolDTO = new DyccEstResolDTO();
        dyctResolucionDTO.setDyccEstreSolDTO(dyccEstreSolDTO);

        dycpSolicitudDTO.setNumControl(dycpSolicitudDTO.getDycpServicioDTO().getNumControl());
        dyctResolucionDTO.setFechaRegistro(new Date());
        dyctResolucionDTO.setImporteSolicitado(dycpSolicitudDTO.getImporteSolicitado());
        dyctResolucionDTO.setImpAutorizado(dycpSolicitudDTO.getImporteSolicitado());
        dyctResolucionDTO.setImpActualizacion(actualizacion);
        dyctResolucionDTO.setIntereses(valIntereses);
        dyctResolucionDTO.setRetIntereses(BigDecimal.ZERO);
        dyctResolucionDTO.setImpCompensado(BigDecimal.ZERO);
        dyctResolucionDTO.setImporteNeto(impNetoDevolver);
        dyccEstreSolDTO.setIdEstResol(EDycAutomaticasIvaEstadoCasoDevolucion.AUTORIZADA_TOTAL.getIdEstResol());
        dyctResolucionDTO.setSaldoAfavorAntRes(dycpSolicitudDTO.getImporteSolicitado());
        dyctResolucionDTO.setImpNegado(BigDecimal.ZERO);
        dyctResolucionDTO.setPagoEnviado(DycConstantesAutomaticasIva.DYCTRESOLUCION_PAGOENVIADO_BANDERA_NO);
        dyctResolucionDTO.setFechaAprobacion(new Date());
        dyctResolucionDTO.setFundamentacion("AUTORIZACION TOTAL PROCESO BATCH DEV AUTOMATICAS");

        return dyctResolucionDTO;
    }

    @Override
    public void calcularImportesControlDeSaldos(DycpSolicitudDTO dycpSolicitudDTO) throws SIATException {
        DyctSaldoIcepDTO saldoIcep = serviceCargarSaldoIcep.execute(dycpSolicitudDTO.getDyctSaldoIcepDTO().getIdSaldoIcep());

        DyctDeclaracionDTO declaracion = null;
        for (DycpSolicitudDTO sol : saldoIcep.getDycpSolicitudList()) {
            if (sol.getNumControl().equals(dycpSolicitudDTO.getNumControl())
                    && (sol.getDyctDeclaracionList() != null && !sol.getDyctDeclaracionList().isEmpty())) {
                declaracion = sol.getDyctDeclaracionList().get(0);
                break;
            }
        }

        boolean validoDeclaracion = false;
        if (declaracion != null) {
            if (!saldoIcep.getDyctDeclaraIcepList().isEmpty()
                    || (null == declaracion.getSaldoAfavor() && declaracion.getNumDocumento() == null)) {
                
                for (DyctDeclaraIcepDTO decicep : saldoIcep.getDyctDeclaraIcepList()) {
                    if (decicep.getFechaFin() == null && decicep.getValidacionDWH() == null) {
                        decicep.setValidacionDWH(new Date());
                        decicep.setOrigenDeclara(Constantes.OrigenesDeclaracion.DATAWAREHOUSE);
                        decicep.setNotas("VALIDACION PROCESO BATCH DEV AUTOMATICAS");
                        daoDeclaraIcep.actualizarSinNulos(decicep);
                        validoDeclaracion = true;
                    } else if (decicep.getFechaFin() == null && decicep.getValidacionDWH() != null) {
                        validoDeclaracion = true;
                    }
                }

            } else {
                validoDeclaracion = true;
            }
        }

        if (!validoDeclaracion) {
            throw new SIATException("No tiene declaraciones registradas o activas");
        }

        ControlSaldoImportesDTO controlSaldoImportes = controlSaldoImporteService.calcularImportes(saldoIcep);
        if (controlSaldoImportes.getRemanente().compareTo(dycpSolicitudDTO.getImporteSolicitado()) < ConstantesDyCNumerico.VALOR_0) {
            throw new SIATException("Remanente insuficiente " + controlSaldoImportes.getRemanente());
        }
        dycpSolicitudDTO.setDyctSaldoIcepDTO(saldoIcep);
    }

    private ActualizacionMontoParcialVO calcularActualizacion(DycpSolicitudDTO dycpSolicitudDTO) throws SIATException {

        IcepVO icepVO1 = new IcepVO();
        icepVO1.setIdSaldoIcep(dycpSolicitudDTO.getDyctSaldoIcepDTO().getIdSaldoIcep());
        icepVO1.setSaldoIcep(dycpSolicitudDTO.getDyctSaldoIcepDTO());

        ParametroActDevDTO parametroActDevDTO = new ParametroActDevDTO();
        parametroActDevDTO.setIcepVO(icepVO1);
        parametroActDevDTO.setNumControl(dycpSolicitudDTO.getDycpServicioDTO().getNumControl());
        parametroActDevDTO.setRfc(dycpSolicitudDTO.getDycpServicioDTO().getRfcContribuyente());
        parametroActDevDTO.setFechaResolucion(new Date());
        parametroActDevDTO.setMontoAutorizado(dycpSolicitudDTO.getImporteSolicitado());

        ActualizacionMontoParcialVO actualizacionMontoParcialVO = calcularActualizacionResDevoluService.calcular(parametroActDevDTO);

        return actualizacionMontoParcialVO;

    }

    private CalculoActualizacionDTO calcularIntereses(DycpSolicitudDTO dycpSolicitudDTO, DyccTipoTramiteDTO dyccTipoTramiteDTO) throws SIATException {
        Date fechaEmisionResol = new Date();
        Date fechaVencimiento = calcularFechasService.calcularFechaFinal(dycpSolicitudDTO.getFechaPresentacion(), dyccTipoTramiteDTO.getPlazo(), dyccTipoTramiteDTO.getDyccTipoPlazoDTO().getIdTipoPlazo());

        CalculoActualizacionDTO resultadoCalculo = null;
        if (fechaEmisionResol.after(fechaVencimiento)) {
            try {
                resultadoCalculo = actualizadorFacade.calcular(fechaVencimiento, fechaEmisionResol, dycpSolicitudDTO.getImporteSolicitado());

            } catch (Exception e) {
                throw new SIATException("Error al calcular los interes " + dycpSolicitudDTO.getNumControl(), e);
            }
        }
        return resultadoCalculo;
    }

}
