package mx.gob.sat.siat.dyc.casocomp.service.districomp.impl;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.service.districomp.DistribuirCompService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.CalcularSaldoRemanenteICEPService;
import mx.gob.sat.siat.dyc.dao.compensacion.DycpAvisoCompDAO;
import mx.gob.sat.siat.dyc.dao.parametros.DyccParametrosAppDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.AsignacionAutomaticaDictaminadorService;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.enums.ClavesAdministraciones;
import mx.gob.sat.siat.dyc.util.constante.enums.IdsParametrosApp;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DistribuirCompServiceImpl implements DistribuirCompService {

    private static final Logger LOG = Logger.getLogger(DistribuirCompServiceImpl.class);

    @Autowired(required = true)
    private AsignacionAutomaticaDictaminadorService serviceAsigAutDict;

    @Autowired
    private DyctResolCompDAO daoResolComp;

    @Autowired
    private DyccParametrosAppDAO daoParametros;

    @Autowired
    private CalcularSaldoRemanenteICEPService serviceCalcRem;

    @Autowired
    private DycpAvisoCompDAO daoAvisoComp;

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_SALDOAFAVOR = "saldoAFavor";
    private static final String KEY_RESULT_REGAUT = "registrarCompAutomaticamente";
    private static final String KEY_MOTIVO_REGAUT = "motivo";


    private static final Integer[] ADMINS_GRANDES_CONTTES =
    { ClavesAdministraciones.CENTRAL_EMPR_CONSOLIDAN_FISC, ClavesAdministraciones.CENTRAL_FISC_SECTOR_FINANCIERO,
      ClavesAdministraciones.CENTRAL_GRANDES_CONTRIBUYENTES };

    public void distribuirCompensacion(DycpCompensacionDTO compensacion) {
        LOG.debug("INICIO distribuirCompensacion");
        Map<String, Object> respRegAut = registrarCompAutomaticamente(compensacion);
        LOG.debug(" registrar automaticamente compensacion ->" + (Boolean)respRegAut.get(KEY_RESULT_REGAUT));
        if ((Boolean)respRegAut.get(KEY_RESULT_REGAUT)) {
            /**    compensacion.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.REGISTRO);
            daoCompensacion.actualizarEstadocomp(Constantes.EstadosCompensacion.REGISTRO.getIdEstadoComp(),
                                                 compensacion.getDycpServicioDTO().getNumControl().trim());**/
            insertarResolRegistrada(compensacion, (String)respRegAut.get(KEY_MOTIVO_REGAUT));
        } else {
            try {
                serviceAsigAutDict.asignarDictaminador(compensacion,
                                                       Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio());

            } catch (SQLException e) {
                LOG.error("Ocurrio un error al asignar dictaminador ->" + e.getMessage());
                ManejadorLogException.getTraceError(e);
            }
        }
    }

    private Map<String, Object> registrarCompAutomaticamente(DycpCompensacionDTO compensacion) {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        double remanente =
            serviceCalcRem.calcularRemanenteOptimiRec(compensacion.getDyctSaldoIcepOrigenDTO()).doubleValue();
        int claveAdmComp = compensacion.getDycpServicioDTO().getClaveAdm();

        if (Arrays.asList(ADMINS_GRANDES_CONTTES).contains(claveAdmComp)) {
            LOG.debug("La compensación pertenece a Grandes Contribuyentes");
            Map<String, Object> respDeterSAF = determinarSaldoAFavor(compensacion);
            Double montoLimiteGrandesCont =
                daoParametros.encontrar(IdsParametrosApp.MONTOLIMITE_GRANDESCONT_REGCOMP).getValor().doubleValue();

            if (remanente < ConstantesDyCNumerico.VALOR_0) {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.FALSE);
                respuesta.put(KEY_MOTIVO_REGAUT,
                              "El saldo remanente es menor a Cero, no se registrará automáticamente");
                return respuesta;
            } else if ((Boolean)respDeterSAF.get(KEY_SUCCESS) &&
                       (Double)respDeterSAF.get(KEY_SALDOAFAVOR) <= montoLimiteGrandesCont) {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.TRUE);
                respuesta.put(KEY_MOTIVO_REGAUT,
                              "El monto de la compensación es menor al monto límite permitido (" + montoLimiteGrandesCont +
                              ")");
                return respuesta;
            } else {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.FALSE);
                if ((Boolean)respDeterSAF.get(KEY_SUCCESS)) {
                    respuesta.put(KEY_MOTIVO_REGAUT,
                                  "El saldo a favor de la compensación rebasa el monto límite, no se registrará automáticamente");
                } else {
                    respuesta.put(KEY_MOTIVO_REGAUT, "Ocurrió un error al intentar obtener el saldo a favor");
                }
                return respuesta;
            }

        } else if (compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio().intValue() ==
                   Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio()) {
            if (claveAdmComp == ClavesAdministraciones.CENTRAL_VERIF_HIDROCARBUROS ||
                claveAdmComp == ClavesAdministraciones.CENTRAL_FISC_HIDROCARBUROS) {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.FALSE);
                respuesta.put(KEY_MOTIVO_REGAUT,
                              "La compensación no se registrará automaticamente porque pertenece a AGHidrocarburos");
                LOG.info("La compensación no se registrará automaticamente porque pertenece a AGHidrocarburos");
                return respuesta;
            }
            if (remanente < 0) {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.FALSE);
                respuesta.put(KEY_MOTIVO_REGAUT,
                              "La compensación no se registrará automaticamente debido a que el remanente menor a 0");
                LOG.info("La compensación no se registrará automaticamente debido a que el remanente menor a 0");
                return respuesta;
            } else {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.TRUE);
                respuesta.put(KEY_MOTIVO_REGAUT,
                              "La compensación se registrará automaticamente debido a que el remanente menor a 0");
                LOG.info("La compensación se registrará automaticamente debido a que el remanente menor a 0");
                return respuesta;
            }
        } else if (ConstantesDyCNumerico.VALOR_1 ==
                   compensacion.getDycpAvisoCompDTO().getDyccTipoAvisoDTO().getIdTipoAviso()) {
            if (null != daoAvisoComp.selectEstatusXRFC(compensacion.getRfcContribuyente())) {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.FALSE);
                respuesta.put(KEY_MOTIVO_REGAUT,
                              "No se registrara automaticamente, por que existe un aviso en estatus por resolver");
                return respuesta;
            }
            if (compensacion.getDyctOrigenAvisoList().get(0).getEsRemanente() == 0) {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.TRUE);
                respuesta.put(KEY_MOTIVO_REGAUT,
                              "La compensación se registrará automaticamente debido a que No es de remanente");
                return respuesta;
            } else if (compensacion.getDycpAvisoCompDTO().getAvisoNormalExterno() != null &&
                       !"".equals(compensacion.getDycpAvisoCompDTO().getAvisoNormalExterno())) {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.FALSE);
                respuesta.put(KEY_MOTIVO_REGAUT,
                              "La compensación NO se registrará automaticamente por que el aviso normal es externo a MAT-DYC");
                return respuesta;
            } else {
                respuesta.put(KEY_RESULT_REGAUT, Boolean.TRUE);
                respuesta.put(KEY_MOTIVO_REGAUT,
                              "La compensación se registrará automaticamente por que el aviso normal es de MAT-DYC");
                return respuesta;
            }
        } else if (compensacion.getDycpAvisoCompDTO().getAvisoNormalExterno() != null &&
                   !"".equals(compensacion.getDycpAvisoCompDTO().getAvisoNormalExterno())) {
            respuesta.put(KEY_RESULT_REGAUT, Boolean.FALSE);
            respuesta.put(KEY_MOTIVO_REGAUT,
                          "La compensación NO se registrará automaticamente por que el aviso normal es externo a MAT-DYC");
            return respuesta;
        } else {
            respuesta.put(KEY_RESULT_REGAUT, Boolean.TRUE);
            respuesta.put(KEY_MOTIVO_REGAUT,
                          "La compensación se registrará automaticamente por que el aviso es diferente al aviso Normal");
            return respuesta;
        }
    }

    private Map<String, Object> determinarSaldoAFavor(DycpCompensacionDTO compensacion) {
        LOG.debug("INICIO determinarSaldoAFavor; num de Decls ->" +
                  compensacion.getDycpServicioDTO().getDyctDeclaracionList().size());
        Map<String, Object> respuesta = new HashMap<String, Object>();

        try {
            respuesta.put(KEY_SALDOAFAVOR,
                          compensacion.getDycpServicioDTO().getDyctDeclaracionList().get(0).getSaldoAfavor().doubleValue());
            respuesta.put(KEY_SUCCESS, Boolean.TRUE);
        } catch (Exception e) {
            LOG.info("ocurrio un error al intentar determinar el saldo a favor de la compensacion ->" +
                     compensacion.getNumControl() + " no se registrara automaticamente; ->" + e.getMessage());
            respuesta.put(KEY_SUCCESS, Boolean.FALSE);
        }
        return respuesta;
    }

    private void insertarResolRegistrada(DycpCompensacionDTO compensacion, String motivo) {
        DyctResolCompDTO resolComp = new DyctResolCompDTO();
        resolComp.setDyccAccionSegDTO(Constantes.AccionesSeg.APROBACION);
        resolComp.setDyccEstResolDTO(Constantes.EstadosResolucion.APROBADA);
        resolComp.setDyccTipoResolDTO(Constantes.TiposResolucion.REGISTRAR_CASOCOMP);
        resolComp.setDycpCompensacionDTO(compensacion);
        resolComp.setFechaResolucion(new Date());
        resolComp.setObservaciones("COMPENSACION AUTOMATICA - " + motivo);

        try {
            daoResolComp.insertar(resolComp);
        } catch (SIATException e) {
            LOG.error("Error al insertar resolucion de compensacion regitsrada; ->" + e.getMessage());
        }
    }

    public boolean isAutomatica(DycpCompensacionDTO compensacion) {
        LOG.debug("INICIO distribuirCompensacion");
        Map<String, Object> respRegAut = registrarCompAutomaticamente(compensacion);
        LOG.debug(" registrar automaticamente compensacion ->" + (Boolean)respRegAut.get(KEY_RESULT_REGAUT));
        if ((Boolean)respRegAut.get(KEY_RESULT_REGAUT)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

}
