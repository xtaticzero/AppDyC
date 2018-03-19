package mx.gob.sat.siat.dyc.registro.util.validador;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.ValidaTramitesService;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccInstCreditoDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccConceptoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccEjercicioService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccImpuestoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccMatrizAnexosService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccParametrosAppService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccPeriodoService;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteArchivoTemp;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionRNFDC10;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoArchivo;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;


/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
/**
 * Validador para Registro
 *
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 05 Noviembre 2013
 */
@Component(value = "validadorRNRegistro")
public class ValidadorRNRegistro {

    private Logger log = Logger.getLogger(ValidadorRNRegistro.class.getName());
    private static final String REGEX_CLABE = "^([0-9]{18})$";

    public static final String EXISTE_EDO = "EXISTE_EDO";

    public static final String EDO_SOL = "EDO_SOL";

    public static final String ISR_PF_AUT = "ISR_PF_AUT";
    public static final String EDO_ISR_AUT = "EDO_ISR_AUT";
    public static final String EDO_PERMITIDO = "EDO_PERMITIDO";
    public static final String EXISTE_DEV_SIM = "Existe devolucion simultanea! la devolucion: ";
    public static final String ESTATUS_DEV = " esta en estatus: ";

    @Autowired(required = true)
    private DyccConceptoService dyccConceptoService;

    @Autowired
    private DyccPeriodoService dyccPeriodoService;

    @Autowired
    private DyccImpuestoService dyccImpuestoService;

    @Autowired
    private DyccEjercicioService dyccEjercicioService;

    @Autowired(required = true)
    private DycpSolicitudService dycpSolicitudService;

    @Autowired
    private DyccInstCreditoDAO dyccInstCreditoDAOImpl;

    @Autowired
    private DyccParametrosAppService dyccParametrosAppService;

    @Autowired
    private DyccMatrizAnexosService dyccMatrizAnexosService;

    @Autowired
    private ValidaTramitesService serviceValidaTramites;

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DycpSolicitudDAO daoSolicitud;

    private static final DyccEstadoSolDTO[] ESTADOS_DEVOL_SIMULTANEA = {
        Constantes.EstadosSolicitud.AUTORIZADA_TOTAL,
     /**   se quita el estado Negada a peticion del usuario, ya se permite otra solicitud por el mismo icep
        Constantes.EstadosSolicitud.NEGADA,*/
        Constantes.EstadosSolicitud.AUT_PARCIAL_REM_NEGADO,
        Constantes.EstadosSolicitud.EN_PROCESO,
        Constantes.EstadosSolicitud.REQUERIDA,
        Constantes.EstadosSolicitud.EN_REVISION,
        Constantes.EstadosSolicitud.PENDIENTE_RESOLVER,
        Constantes.EstadosSolicitud.EN_PROCESO_SIVAD,
        Constantes.EstadosSolicitud.EN_PROCESO_MORSA,
        Constantes.EstadosSolicitud.EN_PROCESO_SAD,
        Constantes.EstadosSolicitud.AUTORIZADA_TOTAL_SIVAD,
        Constantes.EstadosSolicitud.AUTORIZADA_TOTAL_MORSA,
        Constantes.EstadosSolicitud.RECIBIDA,
        Constantes.EstadosSolicitud.PREAUTORIZADA,
        Constantes.EstadosSolicitud.EN_PROCESO_PAGO
    };

    /**
     * Tipos Resolucion para permitir el registro de una nueva soliciutd para el
     * mismos ICEP ISR PF AUT
     */
    private static final DyccTipoResolDTO[] TIPOS_RESOLUCION_ISR_PF_AUT = {
        Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_DISP
    };
    /**
     * Estados para permitir el registro de una nueva soliciutd para el mismos
     * ICEP ISR PF AUT
     */
    private static final DyccEstadoSolDTO[] ESTADOS_DEVOL_ISR_PF_AUT = {
        Constantes.EstadosSolicitud.AUT_PARCIAL_REM_DISPONIBLE,
        Constantes.EstadosSolicitud.AUTORIZADA_SAD,
        Constantes.EstadosSolicitud.NEGADA_SAD,
        Constantes.EstadosSolicitud.PAGADA};

    private static final int ID_ISR_PF = 115;
    private static final String PREFIJO_FOLIO_AUTAMATICAS = "SA";
    private int idBancario;
    private String descripcion;
    private boolean valorCorrespondiente;

    private Integer montoCompensa;
    private String estadoRN6;

    /**
     * -- Importe en Saldos DYC o en RDO (Antes DWH) Nose permite el registro de
     * una solicitud de devolucion cuando el campo Importe solicitado en
     * devolucion sea: -Igual a cero o -Mayor al ultimo saldo disponible
     * registrado en Saldos DYC o -Cuando no existe ICEP en Saldos DYC
     * (tratandose de ICEP's registrados en Saldos DYC) o -Mayor al saldo a
     * favor o cantidad a favor registrado en la ultima declaracion registrada
     * en Datawarehouse. Esta regla no aplica cuando el origen de la devolucion
     * es "IMPAC a recuperar de ejercicios anteriores", "Misiones diplomaticas",
     * "Organismos internacionales", "Turistas extranjeros", "resolucion o
     * sentencia","Saldo a favor" o el origen es y el tipo de tramite es 109,119
     * y 120 de periodicidad del ejercicio ó 125 o 126, o el origen es "Pago de
     * lo indebido" y el tipo de tramite es 119 o 120 de periodicidad del
     * ejercicio ó 334 ó 347 de periodicidad del ejercicio, 349 al 374, 376 al
     * 381.
     *
     * Nota: Cuando se dice "tratandose de ICEP's registrados en Saldos DYC", se
     * refiere a que deben existir en los catalogos de Saldos DyC los diferentes
     * tipos de Impuestos, Conceptos, Ejercicio y Periodo para una combinacion
     * de ICEP especifica.
     *
     * @param tipoTramite --> Tipo de Tramite
     * @param saldoFavor --> Saldo a Favor de la Ultima Declaracion Presentada
     * @param importeSolicitado --> Importe Solicitado en Devolucion
     * @return
     */
    public boolean rn16(int importeSolicitado, int saldoFavor, int tipoTramite, int origenDev, int periodo) {
        boolean banderaSaldo = false;
        boolean banderaImporte = Boolean.TRUE;
        boolean banderaUltimoSald = false;
        boolean banderaResult = Boolean.TRUE;

        //Validación 1
        if (importeSolicitado == ConstantesDyC.INICIALIZADOR_CERO) {
            log.error("No se puede registrar la solicitud");
            banderaImporte = false;
        }

        //Validación 2
        if (banderaImporte) {
            log.info("Validar Contra Ulitmo Saldo DYC");
            banderaUltimoSald = Boolean.TRUE;
        }

        //rn16Continua
        boolean rn16Dev = rn16Continua(origenDev);

        //rn16final
        boolean rn16Conti = rn16final(importeSolicitado, saldoFavor, tipoTramite, periodo, rn16Dev);

        //rn16finalD
        boolean rn16ContiD = rn16finalD(importeSolicitado, saldoFavor, tipoTramite, origenDev, periodo, rn16Dev);

        if (rn16Conti) {
            banderaSaldo = Boolean.TRUE;
        } else if (rn16ContiD) {
            banderaSaldo = Boolean.TRUE;
        }

        //Validación Resultado
        if (banderaSaldo && banderaImporte && banderaUltimoSald) {
            banderaResult = Boolean.TRUE;
        } else {
            banderaResult = false;
        }
        //Fin validación Resultado
        log.info("Result = " + banderaResult);
        return banderaResult;
    }

    public boolean validaImporteSolEnRNFD16(int saldoSolicitado) {
        if (saldoSolicitado == ConstantesDyC1.CERO) {
            return Boolean.TRUE;
        }
        return false;
    }

    public boolean validaImporteSolvsSalosDyc(int importeSol) {
        if (importeSol == 1) {
            return Boolean.TRUE;
        }
        return false;
    }

    public boolean rn16Continua(int origenDev) {
        boolean banderaOrigen = false;

        //Validación 4
        if (origenDev == ConstantesDyC.IMPAC_RECUPERA_EJERCICIO || origenDev == ConstantesDyC.MISIONES_DIPLOMATICAS
                || origenDev == ConstantesDyC.ORGANISMOS_INTERNACIONALES) {
            banderaOrigen = Boolean.TRUE;
        } else if (origenDev == ConstantesDyC.TURISTAS_EXTRANJEROS
                || origenDev == ConstantesDyC.RESOLUCION_SENTENCIA || origenDev == ConstantesDyC.SALDO_A_FAVOR) {
            banderaOrigen = Boolean.TRUE;
        }
        //Validación 4 -->fin
        return banderaOrigen;
    }

    public boolean rn16final(int importeSolicitado, int saldoFavor, int tipoTramite, int periodo,
            boolean banderaOrigen) {
        boolean banderaSaldo = false;
        int constante = 0;

        String[] constantesE = ConstantesDyC.TIPOS_TRAMITE_RN16_EJER.split(",");
        String[] constantes = ConstantesDyC.TIPOS_TRAMITE_RN16.split(",");

        for (int j = ConstantesDyC.INICIALIZADOR_CERO; j < constantesE.length; j++) {
            String prueba = String.valueOf(tipoTramite);
            for (int i = ConstantesDyC.INICIALIZADOR_CERO; i < constantes.length; i++) {
                constante = Integer.parseInt(constantes[i]);
                if (banderaOrigen && prueba.equals(constantesE[j]) && periodo == ConstantesDyC.EJERCICIO
                        || tipoTramite == constante) {
                    banderaSaldo = Boolean.TRUE;
                    break;
                } else if (banderaOrigen && !banderaSaldo) {
                    if (importeSolicitado > saldoFavor) {
                        banderaSaldo = false;
                    } else {
                        banderaSaldo = Boolean.TRUE;
                    }
                }
            }
            continue;
        }
        return banderaSaldo;
    }

    public boolean rn16finalD(int importeSolicitado, int saldoFavor, int tipoTramite, int origenDev, int periodo,
            boolean banderaOrigen) {
        boolean banderaSaldo = false;
        int constanteD;
        int constanteDs;

        String[] constantesD = ConstantesDyC.TIPOS_TRAMITE_RN16_D_EJER.split(",");
        String[] constantesDs = ConstantesDyC.TIPOS_TRAMITE_RN16_D.split(",");

        for (int l = ConstantesDyC.INICIALIZADOR_CERO; l < constantesD.length; l++) {
            constanteD = Integer.parseInt(constantesD[l]);
            for (int k = ConstantesDyC.INICIALIZADOR_CERO; k < constantesDs.length; k++) {
                constanteDs = Integer.parseInt(constantesDs[k]);
                if (!banderaOrigen) {
                    if (origenDev == ConstantesDyC.PAGO_DE_LO_INDEBIDO && tipoTramite == constanteD
                            && periodo == ConstantesDyC.EJERCICIO) {
                        banderaSaldo = Boolean.TRUE;
                        break;
                    } else if (origenDev == ConstantesDyC.PAGO_DE_LO_INDEBIDO && tipoTramite == constanteDs) {
                        banderaSaldo = Boolean.TRUE;
                        break;
                    } else {
                        banderaSaldo = rn16finalT(importeSolicitado, saldoFavor, origenDev, periodo);
                    }
                }
            }
            continue;
        }
        return banderaSaldo;
    }

    public boolean rn16finalT(int importeSolicitado, int saldoFavor, int origenDev, int periodo) {
        boolean banderaSaldo = false;
        if (origenDev == ConstantesDyC.PAGO_DE_LO_INDEBIDO && periodo != ConstantesDyC.EJERCICIO) {
            if (importeSolicitado > saldoFavor) {
                banderaSaldo = false;
            } else {
                banderaSaldo = Boolean.TRUE;
            }
        }
        return banderaSaldo;
    }

    /**
     * -- Presentacion importe solicitado en devolucion -- Si el tipo de tramite
     * es 101 al 108, 111 al 118, 121 al 124, 301, 304 al 343 o 345 y 346
     * entonces Si Importe [devoluciones y/o compensaciones efectuadas sin
     * incluir actualizacion] ES MAYOR a "0" ENTONCES El sistema calcula el
     * imoprte solicitado en devolucion asi: "importe solicitado en devoulcion"
     * = "saldo a favor" - "devoulciones y/o compensacion efectuadas sin incluir
     * actualizacion" De lo contrario Importe Solicitado en Devolucion = Campo
     * "Saldo a Favor" Fin Si De lo contrario Si el tipo de tramite es 119 o 120
     * 0 344 o 347 entonces Si importe [devoluciones y/o compensaciones
     * efectuadas sin incluir actualizacion] O [importe del acreditamiento
     * efectuado] ES MAYOR a CERO ENTONCES El sistema calcula el importe
     * solicitado en devolucion asi: "importe solicitado en devolucion" = "saldo
     * a favor" - "importe del acreditamiento efectuado" - "importe de las
     * devoluciones y/o compensaciones efectuadas sin incluir actualizacion" De
     * lo contrario Impor Solicitado en Devolucion = Campo "Saldo Favor" Fin Si
     * Fin Si
     *
     * @param tipoTramite --> Tipo de Tramite
     * @param importDevoCompe --> Importe de las Devoluciones y/o Compensaciones
     * Efectuadas
     * @param saldoFavor --> Saldo a Favor
     * @param importAcredEfectua --> Importe del Acreditamiento Efectuado
     * @return importSoliciDevo --> Importe Solicitado en Devolucion
     */
    public static BigDecimal rn20(BigDecimal importDevoCompe, BigDecimal saldoFavor) {
        /**
         * int importDevoCompe, int importAcredEfectua, int saldoFavor if
         * (importDevoCompe > ConstantesDyC.INICIALIZADOR_CERO &&
         * importAcredEfectua > ConstantesDyC.INICIALIZADOR_CERO) {
         * importSoliciDevo = saldoFavor - importAcredEfectua - importDevoCompe;
         * } else if (importDevoCompe > ConstantesDyC.INICIALIZADOR_CERO) {
         * importSoliciDevo = saldoFavor - importDevoCompe; } else if
         * (importAcredEfectua > ConstantesDyC.INICIALIZADOR_CERO) {
         * importSoliciDevo = saldoFavor - importAcredEfectua; }
         */

        return saldoFavor.subtract(importDevoCompe).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static Double rn20D(int tipoTramite, double importDevoCompe, double saldoFavor, double importAcredEfectua) {
        double importSoliciDevo = ConstantesDyC.INICIALIZADOR_DOUBLE;
        int constanteD;

        String[] constantesD = ConstantesDyC.VALIDA_TIPO_TRAMITE_D.split(",");
        for (int j = ConstantesDyC.INICIALIZADOR_CERO; j < constantesD.length; j++) {
            constanteD = Integer.parseInt(constantesD[j]);
            if (tipoTramite == constanteD
                    && (importDevoCompe > ConstantesDyC.INICIALIZADOR_CERO || importAcredEfectua
                    > ConstantesDyC.INICIALIZADOR_CERO)) {
                importSoliciDevo = saldoFavor - importAcredEfectua - importDevoCompe;
                return importSoliciDevo;
            }
        }
        return importSoliciDevo;
    }

    /**
     * --Importe de la cantidad a favor o pago de lo indebido Si el Asesor
     * Fiscal pertenece a una Administracion Desconcentrada de Servicios al
     * Contribuyente y el origen de la devolucion es "Pago de lo indebido" y los
     * tipos de tramite son 358, 359, 360, 373 solo puede recibir devoluciones
     * menores a [MONTO_PERMITIDO_DEVOLUCIONES_PERSONAS_FISICAS_SIN_FIELD] Pesos
     * mexicanos para personas fisicas (Ver RNFDC12) Delo contrario Si el origen
     * del saldo es "Resolucion o Sentencia" puede recibir devoluciones sin
     * validar el [MONTO_PERMITIDO_DEVOLUCIONES_PERSONAS_FISICAS_SIN_FIELD]
     * Pesos mexicanos para personas fisicas (Ver RNFDC12)
     *
     * @param asesorFiscal
     * @param origenDev
     * @param tipoTramite
     * @param montoPer
     * @return
     */
    public boolean rn3(boolean asesorFiscal, int origenDev, int tipoTramite, int montoPer) {
        boolean asesor = Boolean.TRUE;
        int idTipoTramite;
        boolean rnResult = false;

        String[] arrIdsTiposTramite = ConstantesDyC.TIPOS_TRAMITE_RN3.split(",");

        if (!asesorFiscal) {
            log.error("Asesor Fiscal no existe");
            asesor = false;
        } else {
            for (int i = ConstantesDyC.INICIALIZADOR_CERO; i < arrIdsTiposTramite.length; i++) {
                idTipoTramite = Integer.parseInt(arrIdsTiposTramite[i]);
                if (origenDev == ConstantesDyC.PAGO_DE_LO_INDEBIDO && tipoTramite == idTipoTramite) {
                    rnResult = rn3Consulta(montoPer);
                    return rnResult;
                }
            }
        }
        return asesor;
    }

    public boolean rn3Consulta(int montoPer) {
        boolean resultado = false;
        String parametro = ConstantesDyC.ID_PARAMETROS;
        List<DyccParametrosAppDTO> result = null;
        try {
            result = dyccParametrosAppService.obtieneParametroDto(parametro);
        } catch (SQLException e) {
            log.error("No se realizo la consulta correctamente");
        }
        if (result != null) {
            if (montoPer < result.get(ConstantesDyCNumerico.VALOR_0).getValor().intValue()) {
                log.info("Es permitido el monto");
                resultado = Boolean.TRUE;
            } else {
                log.error("Solo se permite devoluciones menores a $13,970.00 M/N");
                resultado = false;
            }
        }
        return resultado;
    }

    /**
     * -- Devoluciones Simultaneas -- No se puede presentar una nueva solicitud
     * de devolución cuando se haya presentado otra para el mismo ICEP o su
     * estado sea uno de los siguiente
     *
     * @param rfc
     * @param idConcepto
     * @param idEjercicio
     * @param idPeriodo
     * @param idOrigenSaldo
     * @return
     */
    public Map<String, Object> rn6(String rfc, int idConcepto, int idEjercicio, int idPeriodo, int idOrigenSaldo) {
        log.error("rfc->" + rfc + "|idConcepto->" + idConcepto + "|idEjercicio->" + idEjercicio + "|idPeriodo->" + idPeriodo + "|idOrigenSaldo->" + idOrigenSaldo);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        DyccEstadoSolDTO estado = null;
        try {
            DyccConceptoDTO concepto = new DyccConceptoDTO();
            concepto.setIdConcepto(idConcepto);
            DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
            ejercicio.setIdEjercicio(idEjercicio);
            DyccPeriodoDTO periodo = new DyccPeriodoDTO();
            periodo.setIdPeriodo(idPeriodo);
            DyccOrigenSaldoDTO origen = new DyccOrigenSaldoDTO();
            origen.setIdOrigenSaldo(idOrigenSaldo);

            List<DyctSaldoIcepDTO> saldosIcep = daoSaldoIcep.selecXRfcConceptoEjercicioPeriodoOrigen(rfc, concepto, ejercicio, periodo, origen);

            for (DyctSaldoIcepDTO saldoIcep : saldosIcep) {
                if (saldoIcep.getDyccOrigenSaldoDTO() != null && saldoIcep.getDyccOrigenSaldoDTO().getIdOrigenSaldo().equals(idOrigenSaldo)) {
                    List<DycpSolicitudDTO> solicitudes = daoSolicitud.selecXSaldoicep(saldoIcep);
                    for (DycpSolicitudDTO solicitud : solicitudes) {
                        if (Arrays.asList(ESTADOS_DEVOL_SIMULTANEA).contains(solicitud.getDyccEstadoSolDTO())) {
                            if (solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().compareTo(Constantes.EstadosSolicitud.AUT_PARCIAL_REM_NEGADO.getIdEstadoSolicitud()) == 0) {
                                if (solicitud.getDyctResolucionDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO().getIdTipoResol().compareTo(Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_NEGADO.getIdTipoResol()) == 0) {
                                    log.error(EXISTE_DEV_SIM + solicitud.getDycpServicioDTO().getNumControl() + ESTATUS_DEV + solicitud.getDyccEstadoSolDTO().getDescripcion() + " y con tipo resolucion AUTORIZADA_PARCIAL_REM_NEGADO");
                                    respuesta.put(EXISTE_EDO, Boolean.TRUE);
                                    respuesta.put(EDO_SOL, solicitud.getDyccEstadoSolDTO());
                                    return respuesta;
                                }

                            } else if (solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().compareTo(Constantes.EstadosSolicitud.AUTORIZADA_TOTAL.getIdEstadoSolicitud()) == 0) {
                                if (solicitud.getDyctResolucionDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO().getIdTipoResol().compareTo(Constantes.TiposResolucion.AUTORIZADA_TOTAL.getIdTipoResol()) == 0) {
                                    log.error(EXISTE_DEV_SIM + solicitud.getDycpServicioDTO().getNumControl() + ESTATUS_DEV + solicitud.getDyccEstadoSolDTO().getDescripcion() + " y con tipo resolucion AUTORIZADA_TOTAL");
                                    respuesta.put(EXISTE_EDO, Boolean.TRUE);
                                    respuesta.put(EDO_SOL, solicitud.getDyccEstadoSolDTO());
                                    return respuesta;
                                }
                            }/** se quita Negada por peticion usuario, ya se puede registrar otra solicitud por el mismo icep
                             * else if (solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().compareTo(Constantes.EstadosSolicitud.NEGADA.getIdEstadoSolicitud()) == 0) {
                                if (solicitud.getDyctResolucionDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO().getIdTipoResol().compareTo(Constantes.TiposResolucion.NEGADA.getIdTipoResol()) == 0) {
                                    log.error(EXISTE_DEV_SIM + solicitud.getDycpServicioDTO().getNumControl() + ESTATUS_DEV + solicitud.getDyccEstadoSolDTO().getDescripcion() + " y con tipo resolucion NEGADA");
                                    respuesta.put(EXISTE_EDO, Boolean.TRUE);
                                    respuesta.put(EDO_SOL, solicitud.getDyccEstadoSolDTO());
                                    return respuesta;
                                }
                            }*/ else {
                                log.error(EXISTE_DEV_SIM + solicitud.getDycpServicioDTO().getNumControl() + ESTATUS_DEV + solicitud.getDyccEstadoSolDTO().getDescripcion());
                                respuesta.put(EXISTE_EDO, Boolean.TRUE);
                                respuesta.put(EDO_SOL, solicitud.getDyccEstadoSolDTO());
                                return respuesta;
                            }
                        }
                    }
                }
            }
            log.error("No se encontro  EDO de SOL simultanea, se deberia permitir el registro ");
            respuesta.put(EXISTE_EDO, Boolean.FALSE);
            respuesta.put(EDO_SOL, estado);
            return respuesta;
        } catch (SIATException e) {
            log.error("ocurrio un error al validar si existen devoluciones simultaneas ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
            respuesta.put(EXISTE_EDO, Boolean.TRUE);
            respuesta.put(EDO_SOL, estado);
            return respuesta;
        }
    }

    /**
     * Funcion para preguntar por si las automaticas ISR PF en el icep tienen
     * asocido una solicitud Manual con estado no permitido
     *
     * @param rfc
     * @param idConcepto
     * @param idEjercicio
     * @param idPeriodo
     * @param idOrigenSaldo
     * @return
     */
    public Map<String, Object> rn6Isr(String rfc, int idConcepto, int idEjercicio, int idPeriodo, int idOrigenSaldo) {
        log.error("rfc->" + rfc + "|idConcepto->" + idConcepto + "|idEjercicio->" + idEjercicio + "|idPeriodo->" + idPeriodo + "|idOrigenSaldo->" + idOrigenSaldo);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        DyccEstadoSolDTO estado = null;
        try {
            DyccConceptoDTO concepto = new DyccConceptoDTO();
            concepto.setIdConcepto(idConcepto);
            DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
            ejercicio.setIdEjercicio(idEjercicio);
            DyccPeriodoDTO periodo = new DyccPeriodoDTO();
            periodo.setIdPeriodo(idPeriodo);
            List<DyctSaldoIcepDTO> saldosIcep = daoSaldoIcep.selecXRfcConceptoEjercicioPeriodo(rfc, concepto, ejercicio, periodo);

            for (DyctSaldoIcepDTO saldoIcep : saldosIcep) {
                if (saldoIcep.getDyccOrigenSaldoDTO() != null && saldoIcep.getDyccOrigenSaldoDTO().getIdOrigenSaldo().equals(idOrigenSaldo)) {
                    List<DycpSolicitudDTO> solicitudes = daoSolicitud.selecXSaldoicep(saldoIcep);
                    for (DycpSolicitudDTO solicitud : solicitudes) {
                        if (!solicitud.getDycpServicioDTO().getNumControl().startsWith(PREFIJO_FOLIO_AUTAMATICAS) && Arrays.asList(ESTADOS_DEVOL_SIMULTANEA).contains(solicitud.getDyccEstadoSolDTO())) {
                            if (solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().compareTo(Constantes.EstadosSolicitud.AUT_PARCIAL_REM_NEGADO.getIdEstadoSolicitud()) == 0) {
                                if (solicitud.getDyctResolucionDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO().getIdTipoResol().compareTo(Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_NEGADO.getIdTipoResol()) == 0) {
                                    log.error(EXISTE_DEV_SIM + solicitud.getDycpServicioDTO().getNumControl() + ESTATUS_DEV + solicitud.getDyccEstadoSolDTO().getDescripcion() + " y con tipo resolucion AUTORIZADA_PARCIAL_REM_NEGADO");
                                    respuesta.put(EXISTE_EDO, Boolean.TRUE);
                                    respuesta.put(EDO_SOL, solicitud.getDyccEstadoSolDTO());
                                    return respuesta;
                                }

                            } else if (solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().compareTo(Constantes.EstadosSolicitud.AUTORIZADA_TOTAL.getIdEstadoSolicitud()) == 0) {
                                if (solicitud.getDyctResolucionDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO().getIdTipoResol().compareTo(Constantes.TiposResolucion.AUTORIZADA_TOTAL.getIdTipoResol()) == 0) {
                                    log.error(EXISTE_DEV_SIM + solicitud.getDycpServicioDTO().getNumControl() + ESTATUS_DEV + solicitud.getDyccEstadoSolDTO().getDescripcion() + " y con tipo resolucion AUTORIZADA_TOTAL");
                                    respuesta.put(EXISTE_EDO, Boolean.TRUE);
                                    respuesta.put(EDO_SOL, solicitud.getDyccEstadoSolDTO());
                                    return respuesta;
                                }
                            } /** se quita Negada por peticion usuario, ya se puede registrar otra solicitud por el mismo icep 23-01-2018
                             * else if (solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().compareTo(Constantes.EstadosSolicitud.NEGADA.getIdEstadoSolicitud()) == 0) {
                                if (solicitud.getDyctResolucionDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO().getIdTipoResol().compareTo(Constantes.TiposResolucion.NEGADA.getIdTipoResol()) == 0) {
                                    log.error(EXISTE_DEV_SIM + solicitud.getDycpServicioDTO().getNumControl() + ESTATUS_DEV + solicitud.getDyccEstadoSolDTO().getDescripcion() + " y con tipo resolucion NEGADA");
                                    respuesta.put(EXISTE_EDO, Boolean.TRUE);
                                    respuesta.put(EDO_SOL, solicitud.getDyccEstadoSolDTO());
                                    return respuesta;
                                }
                            }*/ else {
                                log.error(EXISTE_DEV_SIM + solicitud.getDycpServicioDTO().getNumControl() + ESTATUS_DEV + solicitud.getDyccEstadoSolDTO().getDescripcion());
                                respuesta.put(EXISTE_EDO, Boolean.TRUE);
                                respuesta.put(EDO_SOL, solicitud.getDyccEstadoSolDTO());
                                return respuesta;
                            }

                        }
                    }
                }
            }
            log.error("No se encontro  EDO de SOL simultanea, se deberia permitir el registro ");
            respuesta.put(EXISTE_EDO, false);
            respuesta.put(EDO_SOL, estado);
            return respuesta;
        } catch (SIATException e) {
            log.error("ocurrio un error al validar si existen devoluciones simultaneas ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
            respuesta.put(EXISTE_EDO, Boolean.TRUE);
            respuesta.put(EDO_SOL, estado);
            return respuesta;
        }
    }

    /**
     *
     * @param rfc
     * @param idConcepto
     * @param idEjercicio
     * @param idPeriodo
     * @param idOrigenSaldo
     * @param idTipoTramite
     * @return
     */
    public Map<String, Object> esTramiteISRConEstadoYTipoResolucionRn6Rn7(String rfc, int idConcepto, int idEjercicio, int idPeriodo, int idOrigenSaldo, int idTipoTramite) {
        log.error("rfc->" + rfc + "|idConcepto->" + idConcepto + "|idEjercicio->" + idEjercicio + "|idPeriodo->" + idPeriodo + "|idOrigenSaldo->" + idOrigenSaldo);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        DyccEstadoSolDTO estadoAUT = null;
        boolean isrpfaut = false;
        try {
            if (idTipoTramite == ID_ISR_PF) {
                DyccConceptoDTO concepto = new DyccConceptoDTO();
                concepto.setIdConcepto(idConcepto);
                DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
                ejercicio.setIdEjercicio(idEjercicio);
                DyccPeriodoDTO periodo = new DyccPeriodoDTO();
                periodo.setIdPeriodo(idPeriodo);
                List<DyctSaldoIcepDTO> saldosIcep = daoSaldoIcep.selecXRfcConceptoEjercicioPeriodo(rfc, concepto, ejercicio, periodo);

                for (DyctSaldoIcepDTO saldoIcep : saldosIcep) {
                    if (saldoIcep.getDyccOrigenSaldoDTO() != null && saldoIcep.getDyccOrigenSaldoDTO().getIdOrigenSaldo().equals(idOrigenSaldo)) {
                        List<DycpSolicitudDTO> solicitudes = daoSolicitud.selecXSaldoicep(saldoIcep);
                        for (DycpSolicitudDTO solicitud : solicitudes) {
                            if (solicitud.getDycpServicioDTO().getNumControl().startsWith(PREFIJO_FOLIO_AUTAMATICAS)) {
                                isrpfaut = Boolean.TRUE;
                                estadoAUT = solicitud.getDyccEstadoSolDTO();
                                log.error("Validando devolucion ISR PF AUT con Estado y Tipo Resolucion!! la devolucion:" + solicitud.getDycpServicioDTO().getNumControl() );
                                if (Arrays.asList(ESTADOS_DEVOL_ISR_PF_AUT).contains(solicitud.getDyccEstadoSolDTO())) {
                                    if (solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().compareTo(Constantes.EstadosSolicitud.AUT_PARCIAL_REM_DISPONIBLE.getIdEstadoSolicitud()) == 0) {
                                        if (solicitud.getDyctResolucionDTO() != null && solicitud.getDyctResolucionDTO().getDyccTipoResolDTO() != null && Arrays.asList(TIPOS_RESOLUCION_ISR_PF_AUT).contains(solicitud.getDyctResolucionDTO().getDyccTipoResolDTO())) {
                                            log.error("Existe devolucion ISR PF AUT con Estado y Tipo Resolucion!! la devolucion:" + solicitud.getDycpServicioDTO().getNumControl() + " esta con tipo Resolucion" + solicitud.getDyctResolucionDTO().getDyccTipoResolDTO().getDescripcion());
                                            respuesta.put(ISR_PF_AUT, isrpfaut);
                                            respuesta.put(EDO_ISR_AUT, solicitud.getDyccEstadoSolDTO());
                                            respuesta.put(EDO_PERMITIDO, Boolean.TRUE);
                                            return respuesta;

                                        } else {
                                            log.error("Existe devolucion ISR PF AUT con Tipo Resolucion!! la devolucion:" + solicitud.getDycpServicioDTO().getNumControl() + "con estado " + solicitud.getDyccEstadoSolDTO().getDescripcion() + "pero no con tipo Resolucion valida");
                                            respuesta.put(ISR_PF_AUT, isrpfaut);
                                            respuesta.put(EDO_ISR_AUT, solicitud.getDyccEstadoSolDTO());
                                            respuesta.put(EDO_PERMITIDO, false);
                                            return respuesta;
                                        }
                                    } else {
                                        log.error("Existe devolucion ISR PF AUT con estado, la devolucion:" + solicitud.getDycpServicioDTO().getNumControl() + " esta con Estado" + solicitud.getDyccEstadoSolDTO().getDescripcion());
                                        respuesta.put(ISR_PF_AUT, isrpfaut);
                                        respuesta.put(EDO_ISR_AUT, solicitud.getDyccEstadoSolDTO());
                                        respuesta.put(EDO_PERMITIDO, Boolean.TRUE);
                                        return respuesta;
                                    }
                                }
                            }
                        }
                    }
                }
                respuesta.put(ISR_PF_AUT, isrpfaut);
                respuesta.put(EDO_ISR_AUT, estadoAUT);
                respuesta.put(EDO_PERMITIDO, false);
                return respuesta;
            } else {
                respuesta.put(ISR_PF_AUT, isrpfaut);
                respuesta.put(EDO_ISR_AUT, estadoAUT);
                respuesta.put(EDO_PERMITIDO, false);
                return respuesta;
            }
        } catch (SIATException e) {
            log.error("ocurrio un error al validar si existen devoluciones ISR PF AUT con  Tipo Resolucion->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
            respuesta.put(ISR_PF_AUT, isrpfaut);
            respuesta.put(EDO_ISR_AUT, estadoAUT);
            respuesta.put(EDO_PERMITIDO, false);
            return respuesta;
        }
    }

    /**
     * Metodo para validar si el saldo de la declaracion complementaria es mayor
     * a la declaracion normal para la regla RN6
     *
     * @param saldoFavorCom Saldo declaracion complementaria
     * @param saldoFavorNormal Saldo declaracion normal
     * @return true si saldoFavorCom es mayor que saldoFavorNormal de lo
     * contrario false
     */
    public boolean esSaldoMayorDeclaracionComplementariaRN6(BigDecimal saldoFavorCom, BigDecimal saldoFavorNormal) {
        log.info("Saldo com:" + saldoFavorCom + " y normal:" + saldoFavorNormal);

        BigDecimal saldoFavorComTmp = saldoFavorCom;
        BigDecimal saldoFavorNormalTmp = saldoFavorNormal;

        if (saldoFavorComTmp == null) {
            saldoFavorComTmp = BigDecimal.ZERO;
        }
        if (saldoFavorNormalTmp == null) {
            saldoFavorNormalTmp = BigDecimal.ZERO;
        }

        return saldoFavorComTmp.compareTo(saldoFavorNormalTmp) == 1 ? Boolean.TRUE : Boolean.FALSE;

    }

    /**
     * -- Devoluciones Simultaneas -- No se puede presentar una nueva solicitud
     * de devolución cuando se haya presentado otra para el mismo ICEP o su
     * estado sea uno de los siguiente
     *
     * @param rfc
     * @param idImpuesto
     * @param idConcepto
     * @param idEjercicio
     * @param idPeriodo
     * @return
     */
    public String getEstado(String rfc, int idConcepto, int idEjercicio, int idPeriodo) {
        String estado = null;
        try {
            DyccConceptoDTO concepto = new DyccConceptoDTO();
            concepto.setIdConcepto(idConcepto);
            DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
            ejercicio.setIdEjercicio(idEjercicio);
            DyccPeriodoDTO periodo = new DyccPeriodoDTO();
            periodo.setIdPeriodo(idPeriodo);
            List<DyctSaldoIcepDTO> saldosIcep = daoSaldoIcep.selecXRfcConceptoEjercicioPeriodo(rfc, concepto, ejercicio, periodo);

            for (DyctSaldoIcepDTO saldoIcep : saldosIcep) {
                List<DycpSolicitudDTO> solicitudes = daoSolicitud.selecXSaldoicep(saldoIcep);
                for (DycpSolicitudDTO solicitud : solicitudes) {
                    if (Arrays.asList(ESTADOS_DEVOL_SIMULTANEA).contains(solicitud.getDyccEstadoSolDTO())) {
                        estado = solicitud.getDyccEstadoSolDTO().getDescripcion();
                    }
                }
            }
            return estado;
        } catch (SIATException e) {
            log.error("ocurrio un error al validar si existen devoluciones simultaneas ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
            return "";
        }

    }

    /**
     * -- Validacion Cuenta Clabe -- Siempre debe cumplir para validar la cuenta
     * bancaria CLABE (18 posiciones), utilizando el modulo 10, peso 3-7-1 para
     * el calculo aplicado de izquierda a derecha. Valida el codigo de 3 digios
     * registrado en su CLABE corresponda a un codigo debanco. Valida
     * regla[Validacion de Banco versus CLABE][RNFDC805]. Esta Regla hace
     * referencia ala regla de negocio RNFDC805 del Paquete Funcional
     * PF_GESTION_PAGO_Y_DEVOLUCIONES_INDEBIDAS.
     *
     * @param cuentaBancaria
     * @return regresa un valor verdadero o falso
     */
    public boolean rn470(String cuentaBancaria) {
        if (cuentaBancaria.matches(REGEX_CLABE)) {
            log.info("Son 18 posiciones");
            String banco = cuentaBancaria.substring(0, ConstantesDyCNumerico.VALOR_3);
            log.info("Banco " + banco);

            char[] posicion = banco.toCharArray();
            String uno = String.valueOf(posicion[0]);
            Integer unoI = Integer.valueOf(uno);
            String dos = String.valueOf(posicion[1]);
            Integer dosI = Integer.valueOf(dos);
            String tres = String.valueOf(posicion[2]);
            Integer tresI = Integer.valueOf(tres);

            String plaza = cuentaBancaria.substring(ConstantesDyCNumerico.VALOR_3, ConstantesDyCNumerico.VALOR_6);
            log.info("Plaza " + plaza);

            char[] posicionP = plaza.toCharArray();
            String unop = String.valueOf(posicionP[0]);
            Integer unoP = Integer.valueOf(unop);
            String dosp = String.valueOf(posicionP[1]);
            Integer dosP = Integer.valueOf(dosp);
            String tresp = String.valueOf(posicionP[2]);
            Integer tresP = Integer.valueOf(tresp);

            String cuenta = cuentaBancaria.substring(ConstantesDyCNumerico.VALOR_6, ConstantesDyCNumerico.VALOR_17);
            log.info("Cuenta " + cuenta);

            char[] posicionC = cuenta.toCharArray();
            String unoc = String.valueOf(posicionC[0]);
            Integer unoC = Integer.valueOf(unoc);
            String dosc = String.valueOf(posicionC[1]);
            Integer dosC = Integer.valueOf(dosc);
            String tresc = String.valueOf(posicionC[2]);
            Integer tresC = Integer.valueOf(tresc);
            String cuac = String.valueOf(posicionC[ConstantesDyCNumerico.VALOR_3]);
            Integer cuaC = Integer.valueOf(cuac);
            String quic = String.valueOf(posicionC[ConstantesDyCNumerico.VALOR_4]);
            Integer quiC = Integer.valueOf(quic);
            String sexc = String.valueOf(posicionC[ConstantesDyCNumerico.VALOR_5]);
            Integer sexC = Integer.valueOf(sexc);
            String setc = String.valueOf(posicionC[ConstantesDyCNumerico.VALOR_6]);
            Integer setC = Integer.valueOf(setc);
            String ochoc = String.valueOf(posicionC[ConstantesDyCNumerico.VALOR_7]);
            Integer ochoC = Integer.valueOf(ochoc);
            String nuevc = String.valueOf(posicionC[ConstantesDyCNumerico.VALOR_8]);
            Integer nuevC = Integer.valueOf(nuevc);
            String diec = String.valueOf(posicionC[ConstantesDyCNumerico.VALOR_9]);
            Integer dieC = Integer.valueOf(diec);
            String onc = String.valueOf(posicionC[ConstantesDyCNumerico.VALOR_10]);
            Integer onC = Integer.valueOf(onc);

            String resulBU = String.valueOf(unoI * ConstantesDyC.PESOS_TRES);
            String resulBI = String.valueOf(dosI * ConstantesDyC.PESOS_SIETE);
            String resulBT = String.valueOf(tresI * ConstantesDyC.PESOS_UNO);
            String resulPU = String.valueOf(unoP * ConstantesDyC.PESOS_TRES);
            String resulPD = String.valueOf(dosP * ConstantesDyC.PESOS_SIETE);
            String resulPT = String.valueOf(tresP * ConstantesDyC.PESOS_UNO);
            String resulCU = String.valueOf(unoC * ConstantesDyC.PESOS_TRES);
            String resulCD = String.valueOf(dosC * ConstantesDyC.PESOS_SIETE);
            String resulCT = String.valueOf(tresC * ConstantesDyC.PESOS_UNO);
            String resulCC = String.valueOf(cuaC * ConstantesDyC.PESOS_TRES);
            String resulCQ = String.valueOf(quiC * ConstantesDyC.PESOS_SIETE);
            String resulCS = String.valueOf(sexC * ConstantesDyC.PESOS_UNO);
            String resulCSe = String.valueOf(setC * ConstantesDyC.PESOS_TRES);
            String resulCO = String.valueOf(ochoC * ConstantesDyC.PESOS_SIETE);
            String resulCN = String.valueOf(nuevC * ConstantesDyC.PESOS_UNO);
            String resulCDi = String.valueOf(dieC * ConstantesDyC.PESOS_TRES);
            String resulCOn = String.valueOf(onC * ConstantesDyC.PESOS_SIETE);

            String resultobtenBU = obtenerPosicionGral(resulBU);
            String resultobtenBD = obtenerPosicionGral(resulBI);
            String resultobtenBT = obtenerPosicionGral(resulBT);
            String resultobtenPU = obtenerPosicionGral(resulPU);
            String resultobtenPD = obtenerPosicionGral(resulPD);
            String resultobtenPT = obtenerPosicionGral(resulPT);
            String resultobtenCU = obtenerPosicionGral(resulCU);
            String resultobtenCD = obtenerPosicionGral(resulCD);
            String resultobtenCT = obtenerPosicionGral(resulCT);
            String resultobtenCC = obtenerPosicionGral(resulCC);
            String resultobtenCQ = obtenerPosicionGral(resulCQ);
            String resultobtenCS = obtenerPosicionGral(resulCS);
            String resultobtenCSe = obtenerPosicionGral(resulCSe);
            String resultobtenCO = obtenerPosicionGral(resulCO);
            String resultobtenCN = obtenerPosicionGral(resulCN);
            String resultobtenCDi = obtenerPosicionGral(resulCDi);
            String resultobtenCOn = obtenerPosicionGral(resulCOn);

            Integer suma
                    = (Integer.parseInt(resultobtenBU) + Integer.parseInt(resultobtenBD) + Integer.parseInt(resultobtenBT)
                    + Integer.parseInt(resultobtenPU) + Integer.parseInt(resultobtenPD) + Integer.parseInt(resultobtenPT)
                    + Integer.parseInt(resultobtenCU) + Integer.parseInt(resultobtenCD) + Integer.parseInt(resultobtenCT)
                    + Integer.parseInt(resultobtenCC) + Integer.parseInt(resultobtenCQ) + Integer.parseInt(resultobtenCS)
                    + Integer.parseInt(resultobtenCSe) + Integer.parseInt(resultobtenCO) + Integer.parseInt(resultobtenCN)
                    + Integer.parseInt(resultobtenCDi) + Integer.parseInt(resultobtenCOn));

            log.info("Total de la suma = " + suma);
            String obtenSuma = String.valueOf(suma);
            char[] obtenerValorSuma = obtenSuma.toCharArray();
            String valorObtenidoSuma = String.valueOf(obtenerValorSuma[obtenerValorSuma.length - 1]);

            Integer numControl = (ConstantesDyC2.MODULO - Integer.parseInt(valorObtenidoSuma));

            Integer numControlFinal = numControl;
            if (numControl == ConstantesDyC2.MODULO) {
                numControlFinal = ConstantesDyCNumerico.VALOR_0;
            }

            log.info("Numero Control = " + numControlFinal);

            String valorD
                    = (uno + dos + tres + unop + dosp + tresp + unoc + dosc + tresc + cuac + quic + sexc + setc + ochoc
                    + nuevc + diec + onc + numControlFinal);

            log.info("Cuenta bancaria = " + valorD);

            valorCorrespondiente = rn470Continua(cuentaBancaria, valorD, banco);
            return valorCorrespondiente;
        } else {
            log.info("Minimo 18 posiciones");
            valorCorrespondiente = false;
            return valorCorrespondiente;
        }
    }

    public boolean rn470Continua(String cuentaBancaria, String valorD, String banco) {
        DyccInstCreditoDTO result = dyccInstCreditoDAOImpl.getInstitucion(Integer.parseInt(banco));
        if (result.getIdInstCredito() != null) {
            if (cuentaBancaria.equals(valorD)) {
                this.setIdBancario(result.getIdInstCredito());
                this.setDescripcion(result.getDescripcion());
                log.info("El id es: " + idBancario);
                log.info("El banco es: " + descripcion);
                log.info("Es correcto regresaria un true");
                valorCorrespondiente = Boolean.TRUE;
            } else {
                log.info("Es incorrecto regresaria un false");
                valorCorrespondiente = false;
            }
        } else {
            log.info("No existe banco con ese id");
            valorCorrespondiente = false;
        }
        return valorCorrespondiente;
    }

    /**
     * *
     * Metodo encargar de obtener la pocision de cada valor para cuenta bancaria
     */
    private String obtenerPosicionGral(String resultado) {
        char[] obtener = resultado.toCharArray();
        String obtenido;
        if (resultado.length() == ConstantesDyC.POSICION_GENERAL) {
            obtenido = String.valueOf(obtener[0]);
        } else {
            obtenido = String.valueOf(obtener[1]);
        }
        return obtenido;
    }

    /**
     * -- Estado del CPR Cuando el CPR esta en estado de "Sancionado" en el
     * ejercicio y periodo indicado en la declaracion o "cancelado por
     * solicitud" o "cancelado" o "baja por defuncion" o "baja temporal" o "no
     * localizado" o "Cancelacion por actuacion" o "Suspensión por actuación" o
     * "Suspension por acumulación de amonestaciones" se debe guardar en el
     * sistema la inconsistencia, marcar la solicitud como recibida a
     * insistencia del contribuyente y permitirle seguir con el registro de la
     * solicitud.
     *
     * @return
     */
    public boolean rn10(String estado) {
        boolean resultEstado = rn10Continua(estado);
        if (resultEstado) {
            return Boolean.TRUE;
        } else if (estado.equals(ConstanteValidacionRNFDC10.CANCELACION_ACTUACION)
                || estado.equals(ConstanteValidacionRNFDC10.SUSPENSION_ACTUACION)
                || estado.equals(ConstanteValidacionRNFDC10.SUSPENSION_ACUMULACION)) {
            log.info("Correcta validacion D");
            return Boolean.TRUE;
        } else {
            log.info("El estado no es valido");
            return false;
        }
    }

    public boolean rn10Continua(String estado) {
        if (estado.equals(ConstanteValidacionRNFDC10.SANCIONADO) || estado.equals(ConstanteValidacionRNFDC10.CANCELADO_SOLICITUD)
                || estado.equals(ConstanteValidacionRNFDC10.CANCELADO)) {
            log.info("Correcta validacion D");
            return Boolean.TRUE;
        } else if (estado.equals(ConstanteValidacionRNFDC10.BAJA_DEFUNCION) || estado.equals(ConstanteValidacionRNFDC10.BAJA_TEMPORAL)
                || estado.equals(ConstanteValidacionRNFDC10.NO_LOCALIZADO)) {
            log.info("Correcta validacion D");
            return Boolean.TRUE;
        } else {
            return false;
        }
    }

    /**
     * -- Validacion para Identificar la Administracion Correspondiente Recibe
     * tres parametros que son origenDev --> Origen de Devolucion rolesContri
     * --> Obejto que contiene la lista adminContri --> Administracion del
     * Contribuyente Correspondiente
     */
    public Map<String, Object> identificarAdministracion(int origenDev, RolesContribuyenteDTO rolesContri, int adminContri, int tramite) {
        boolean esGranContribuyente = false;
        boolean rolAlaf = false;
        boolean rolSocMer = false;

        List<PersonaRolDTO> listaRol = rolesContri.getRoles();
        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            if (serviceValidaTramites.validaTramite(ConstanteArchivoTemp.ID_REGLA_TRAMS_AGACE, tramite)) {

                respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, "" + ConstantesIds.ClavesAdministraciones.GENERAL_COMERCIO_EXTERIOR);
                respuesta.put(ConstantesIds.CLAVE_ADMON, ConstantesIds.ClavesAdministraciones.GENERAL_COMERCIO_EXTERIOR);
                return respuesta;
            } else {
                for (int i = ConstantesDyC.INICIALIZADOR_CERO; i < listaRol.size(); i++) {
                    int claveRol = listaRol.get(i).getClaveRol();
                    if (claveRol == ConstantesTipoRol.GRAN_CONTRIBUYENTE_PF || claveRol == ConstantesTipoRol.GRAN_CONTRIBUYENTE_PM) {
                        log.info("Rol Gran  COntribuyente");
                        esGranContribuyente = Boolean.TRUE;
                    }

                    if (claveRol == ConstantesIds.GC_ALAF_PF || claveRol == ConstantesIds.GC_ALAF_PM) {
                        log.info("Rol Alaf");
                        rolAlaf = Boolean.TRUE;
                    }

                    boolean validarRol = claveRol == ConstantesIds.INTEGRADA_PM || claveRol == ConstantesIds.INTEGRADORA_PM || claveRol == ConstantesIds.GC_ACFECF_PM;
                    if (claveRol == ConstantesIds.SOC_MERCAN_CONTROL || claveRol == ConstantesIds.SOC_MERCAN_CONTROLA || validarRol) {
                        log.info("Rol Soc. Mercantil");
                        rolSocMer = Boolean.TRUE;
                    }

                    if (claveRol == ConstantesIds.AGH_FISCALIZACION_PM || claveRol == ConstantesIds.AGH_FISCALIZACION_PF) {
                        respuesta.put(ConstantesIds.CLAVE_ADMON, ConstantesIds.ClavesAdministraciones.CENTRAL_FISC_HIDROCARBUROS);
                        respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, String.valueOf(ConstantesIds.ClavesAdministraciones.CENTRAL_FISC_HIDROCARBUROS));
                        log.info("Administración Central de Fiscalización de Hidrocarburos : "
                                + ConstantesIds.ClavesAdministraciones.CENTRAL_FISC_HIDROCARBUROS);
                        return respuesta;
                    }

                    if (claveRol == ConstantesIds.AGH_VERIFICACION_PM || claveRol == ConstantesIds.AGH_VERIFICACION_PF) {
                        respuesta.put(ConstantesIds.CLAVE_ADMON, ConstantesIds.ClavesAdministraciones.CENTRAL_VERIF_HIDROCARBUROS);
                        respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, String.valueOf(ConstantesIds.ClavesAdministraciones.CENTRAL_VERIF_HIDROCARBUROS));
                        log.info("Administración Central de Verificación de Hidrocarburos : "
                                + ConstantesIds.ClavesAdministraciones.CENTRAL_VERIF_HIDROCARBUROS);
                        return respuesta;
                    }
                }
                return identAdminConti(esGranContribuyente, rolAlaf, rolSocMer, perteneceSectorFinanciero(listaRol), adminContri, origenDev);
            }
        } catch (Exception e) {
            log.error("Error al identificar administración " + e);
            ManejadorLogException.getTraceError(e);
        }

        return null;
    }

    private Map<String, Object> identAdminConti(boolean esGranContribuyente, boolean rolAlaf, boolean rolSocMer,
            boolean perteneceSectorFinanciero, int adminContri, int origenDev) {
        Map<String, Object> respuesta = new HashMap<String, Object>();

        if (esGranContribuyente) {
            if (rolAlaf) {
                respuesta.put(ConstantesIds.CLAVE_ADMON, adminContri);
                if (adminContri < ConstantesDyCNumerico.VALOR_10) {
                    respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, "0" + adminContri);
                } else {
                    respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, String.valueOf(adminContri));
                }
                log.debug("Administracion Desconcentrada de Auditoria Fiscal : " + respuesta.get(ConstantesIds.CLAVE_SIR_NUM_CTRL));
            } else if (rolSocMer) {
                respuesta.put(ConstantesIds.CLAVE_ADMON, ConstantesIds.ACFECF);
                respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, String.valueOf(ConstantesIds.ACFECF));
                log.info("Administración Central de Fiscalización a Empresas que Consolidan Fiscalmente : "
                        + ConstantesIds.ACFECF);
            } else if (perteneceSectorFinanciero) {
                respuesta.put(ConstantesIds.CLAVE_ADMON, ConstantesIds.ACFSF);
                respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, String.valueOf(ConstantesIds.ACFSF));
                log.info("Administración Central de Fiscalización al Sector Financiero : " + ConstantesIds.ACFSF);
            } else {
                respuesta.put(ConstantesIds.CLAVE_ADMON, ConstantesIds.ACFGCD);
                respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, String.valueOf(ConstantesIds.ACFGCD));
                log.info("Administración Central de Fiscalización a Grandes Contribuyentes Diversos : "
                        + ConstantesIds.ACFGCD);
            }
        } else if (origenDev == ConstantesDyC.TURISTAS_EXTRANJEROS) {
            respuesta.put(ConstantesIds.CLAVE_ADMON, ConstantesIds.ACDC);
            respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, String.valueOf(ConstantesIds.ACDC_NUMCONTROL));

            log.info("Administracion Central de Devoluciones y Compensaciones");
        } else {
            respuesta.put(ConstantesIds.CLAVE_ADMON, adminContri);

            if (adminContri < ConstantesDyCNumerico.VALOR_10) {
                respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, "0" + adminContri);
            } else {
                respuesta.put(ConstantesIds.CLAVE_SIR_NUM_CTRL, String.valueOf(adminContri));
            }
            log.info("Administracion Desconcentrada de Auditoria Fiscal : " + respuesta.get(ConstantesIds.CLAVE_SIR_NUM_CTRL));
        }
        return respuesta;
    }

    /**
     * Metodo rolSF
     */
    private boolean perteneceSectorFinanciero(List<PersonaRolDTO> roles) {
        boolean rol = false;
        int claveRol = 0;
        for (int i = ConstantesDyC.INICIALIZADOR_CERO; i < roles.size(); i++) {
            claveRol = roles.get(i).getClaveRol();
            if (rolSFU(claveRol)) {
                rol = Boolean.TRUE;
                break;
            }
            if (rolSFD(claveRol)) {
                rol = Boolean.TRUE;
                break;
            }
            if (rolSFT(claveRol)) {
                rol = Boolean.TRUE;
                break;
            }
            if (rolSFC(claveRol)) {
                rol = Boolean.TRUE;
                break;
            }
            if (rolSFQ(claveRol)) {
                rol = Boolean.TRUE;
                break;
            }
        }
        return rol;
    }

    private boolean rolSFU(int claveRol) {
        boolean rol = false;
        if (claveRol == ConstantesIds.SOC_MUTUAL_Q_NOPERE || claveRol == ConstantesIds.SOC_CONTROL_GRUPOS
                || claveRol == ConstantesIds.SF_ARRENDADORA_FINAN || claveRol == ConstantesIds.SF_BANCA_DESARROLLO) {
            rol = Boolean.TRUE;
        } else if (claveRol == ConstantesIds.SF_BANCA_MULTIPLE || claveRol == ConstantesIds.SF_BANCO_DE_MEXICO
                || claveRol == ConstantesIds.SF_BOLSA_DE_VALORES || claveRol == ConstantesIds.SF_BOLSA_MEXI_VALORES) {
            rol = Boolean.TRUE;
        }
        return rol;
    }

    private boolean rolSFD(int claveRol) {
        boolean rol = false;
        if (claveRol == ConstantesIds.SF_INMOBILIA_FINANCIERO || claveRol == ConstantesIds.SF_INSTITU_FIANZAS
                || claveRol == ConstantesIds.SF_INSTITU_SEGUROS || claveRol == ConstantesIds.SF_SOCIEDAD_AHORRO_PRES) {
            rol = Boolean.TRUE;
        } else if (claveRol == ConstantesIds.SF_SOCIEDAD_INVER || claveRol == ConstantesIds.SF_SOCIEDAD_INVER_ESP
                || claveRol == ConstantesIds.SF_INST_DEPOS_VALORES
                || claveRol == ConstantesIds.SF_ADM_FONDOS_PEL_RETIRO) {
            rol = Boolean.TRUE;
        }
        return rol;
    }

    private boolean rolSFT(int claveRol) {
        boolean rol = false;
        if (claveRol == ConstantesIds.SF_ALMACEN_GRAL_DEPO || claveRol == ConstantesIds.SF_ASOC_MEX_INST_SEGUROS
                || claveRol == ConstantesIds.SF_COM_NAC_BANCARIA_VALORES
                || claveRol == ConstantesIds.SF_COM_NAC_SEGUROS_FIANZA) {
            rol = Boolean.TRUE;
        } else if (claveRol == ConstantesIds.SF_EMPRESAS_FACTORAJE
                || claveRol == ConstantesIds.SF_FILIALES_BANCOS_EXTRAN
                || claveRol == ConstantesIds.SF_OPERADORAS_SOC_INVERSION
                || claveRol == ConstantesIds.SF_REP_BANCA_EXTRANJERA) {
            rol = Boolean.TRUE;
        }
        return rol;
    }

    private boolean rolSFC(int claveRol) {
        boolean rol = false;
        if (claveRol == ConstantesIds.SF_SOC_INVER_INST_ADEUDA
                || claveRol == ConstantesIds.SF_SOC_INVER_RENTA_FIJA_COM
                || claveRol == ConstantesIds.SF_SOC_INVERSI_CAPITAL || claveRol == ConstantesIds.SF_SOCIEDAD_INVER_COMUN) {
            rol = Boolean.TRUE;
        } else if (claveRol == ConstantesIds.SF_COM_NAC_SIS_AHORRO_RETIRO
                || claveRol == ConstantesIds.SF_SOC_FINANCIE_OBJ_LTDO || claveRol == ConstantesIds.SF_SOFOM_ER
                || claveRol == ConstantesIds.SF_SOFOM_ENR) {
            rol = Boolean.TRUE;
        }
        return rol;
    }

    private boolean rolSFQ(int claveRol) {
        boolean rol = false;
        if (claveRol == ConstantesIds.SOCIEDAD_FINANCIERA_POPULAR
                || claveRol == ConstantesIds.SOCIEDAD_INVERSION_SALUD
                || claveRol == ConstantesIds.SF_OTRA_ENTIDAD_INTER_FINAN || claveRol == ConstantesIds.SECTOR_FINANCIERO) {
            rol = Boolean.TRUE;
        } else if (claveRol == ConstantesIds.SF_CASA_BOLSA || claveRol == ConstantesIds.SF_CASA_CAMBIO
                || claveRol == ConstantesIds.SF_COMPA_AFIANZADOR || claveRol == ConstantesIds.SF_GRUPOS_FINANCIERO) {
            rol = Boolean.TRUE;
        }
        return rol;
    }

    /**
     * ----Validacion de Anexos Electronicos----------------- Si el
     * contribuyente es "Gran Contribuyente" (RNFDC53) o el Contribuyente es
     * "Dictaminador" (RNFDC54) entonces El tramite no debe presentar Anexos
     * Electronicos De lo contrario Si el Contribuyente es "No Gran
     * Contribuyente" y no es "Distaminador" entonces El tramite requiere
     * presentar Anexos Electronicos segun la matriz de anexos junto con
     * regla(RNFDC53), a excepcion de tramite 130 este presentara ficha siempre
     * y cuando el tramite se cumpla.
     *
     * @param granContribuyente
     * @param disctaminador
     * @param tipoTramite
     * @return
     */
    public List<ArchivoVO> rn55(int idTipoTramite) {
        List<ArchivoVO> dyctAnexoDTO = new ArrayList<ArchivoVO>();
        ArchivoVO dyctAnexo = null;
        List<DyccMatrizAnexosDTO> obtenAnexos = dyccMatrizAnexosService.buscarAnexosARequerir(idTipoTramite);

        for (DyccMatrizAnexosDTO dyccMatrizAnexosDTO : obtenAnexos) {
            dyctAnexo = new ArchivoVO();
            dyctAnexo.setId(dyccMatrizAnexosDTO.getIdAnexo());
            dyctAnexo.setUrlPlantillaAnexo(dyccMatrizAnexosDTO.getUrl());
            dyctAnexo.setEstado(ConstantesTipoArchivo.EDO_ANEXO);
            dyctAnexo.setNombre(dyccMatrizAnexosDTO.getNombreAnexo());
            dyctAnexoDTO.add(dyctAnexo);
        }

        return dyctAnexoDTO;
    }

    /**
     * Para el tramite 130 siempre presentara ficha sector agropecuario
     * ignorando regla RNFDC53.
     *
     */
    public ArchivoVO getFichaSecAgro() throws SIATException {
        DyccMatrizAnexosDTO matrizAnexosDTO = null;
        try {
            matrizAnexosDTO = dyccMatrizAnexosService.buscaAnexoPorId(1);
        } catch (DataAccessException e) {
            throw new SIATException("Error, Consulta de Matriz Anexos SectorAgro: ", e);
        }
        ArchivoVO fichaSecAgro = new ArchivoVO();
        fichaSecAgro.setId(matrizAnexosDTO.getIdAnexo());
        fichaSecAgro.setUrlPlantillaAnexo(matrizAnexosDTO.getUrl());
        fichaSecAgro.setEstado(ConstantesTipoArchivo.EDO_ANEXO);
        fichaSecAgro.setNombre(matrizAnexosDTO.getNombreAnexo() + " " + matrizAnexosDTO.getDescripcionAnexo());
        return fichaSecAgro;
    }

    /**
     * Metodo que valida la cuenta cable y el rfc donde si la cuenta clabe biene
     * correcta consulta a la base de datos DYC para verificar que la cuenta
     * clabe no exista en otros rfc si la cuenta cable se encuentra en otros rfc
     * y la cuenta es verdadera regresa el valor 1 = "Cuenta clabe correcta,
     * Existe en otros RFC" else si la cuenta clabe es verdadera y no se
     * encuentra en otros rfc regresa el valor 2 = "Cuenta clabe correcta, NO
     * existe en otros RFC" el si la cuenta clabe no es verdades regresa el
     * valor 0 = "Cuenta clabe Incorrecta"
     *
     * @param cuentaClabe
     * @param rfc
     * @return
     */
    public boolean validaClabeRFC(String cuentaClabe, String rfc) {
        boolean respuesta = false;
        Integer obtenRFCs = dycpSolicitudService.obtenRFCBancario(cuentaClabe, rfc);
        if (obtenRFCs != ConstantesDyC.INICIALIZADOR_CERO) {
            respuesta = false;
            log.info("Cuenta Clabe Incorrecta, Existe en otros RFC");
        } else {
            respuesta = Boolean.TRUE;
            log.info("Cuenta Clabe Correcta, NO existe en otros RFC");
        }
        return respuesta;
    }

    /**
     * Este metodo es de la regla 101 del paquete de saldos consulta base de
     * datos DYC para verificar que el ICEP sea existente y el resultado es un
     * valor booelano
     *
     * @param impuesto
     * @param concepto
     * @param ejercicio
     * @param periodo
     * @return
     */
    public boolean verificaICEP(int impuesto, int concepto, int ejercicio, int periodo) {
        DyccEjercicioDTO obtenEjercicio = null;
        DyccEjercicioDTO ejerDTO = new DyccEjercicioDTO();
        ejerDTO.setIdEjercicio(ejercicio);

        DyccPeriodoDTO obtenPeriodo = null;
        DyccPeriodoDTO perioDTO = new DyccPeriodoDTO();
        perioDTO.setIdPeriodo(periodo);
        DyccConceptoDTO dtoConcepto = null;
        List<DyccImpuestoDTO> obtenImpuesto = dyccImpuestoService.obtieneImpuestos(impuesto);
        try {
            dtoConcepto = dyccConceptoService.obtieneIdConcepto(concepto);
            obtenEjercicio = dyccEjercicioService.obtieneEjercicioPorId(ejerDTO);
            obtenPeriodo = dyccPeriodoService.obtienePeriodoPorIdPeriodo(perioDTO);
        } catch (Exception e) {
            log.error("Error al realizar la consulta" + e.getMessage());
        }

        if (dtoConcepto != null && !obtenImpuesto.isEmpty() && obtenEjercicio != null && obtenPeriodo != null) {
            log.info("Existe ICEP");
            return Boolean.TRUE;
        } else {
            log.info("No existe ICPE");
            return false;
        }
    }

    public void setIdBancario(int idBancario) {
        this.idBancario = idBancario;
    }

    public int getIdBancario() {
        return idBancario;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setValorCorrespondiente(boolean valorCorrespondiente) {
        this.valorCorrespondiente = valorCorrespondiente;
    }

    public boolean isValorCorrespondiente() {
        return valorCorrespondiente;
    }

    public void setMontoCompensa(Integer montoCompensa) {
        this.montoCompensa = montoCompensa;
    }

    public Integer getMontoCompensa() {
        return montoCompensa;
    }

    public void setEstadoRN6(String estadoRN6) {
        this.estadoRN6 = estadoRN6;
    }

    public String getEstadoRN6() {
        return estadoRN6;
    }

}
