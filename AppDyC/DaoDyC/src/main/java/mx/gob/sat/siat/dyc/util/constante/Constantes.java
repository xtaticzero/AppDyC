package mx.gob.sat.siat.dyc.util.constante;

import java.sql.Date;

import mx.gob.sat.siat.dyc.domain.DyccEstadoReqDTO;
import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;
import mx.gob.sat.siat.dyc.domain.movsaldo.DyctAccionPrivAjusDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccAccionSegDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccAfectaIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstResolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccEstadoDocDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoPlazoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;

public final class Constantes {

    public static final String AUTORIZADATOTAL = "Autorizada Total";

    private Constantes() {

    }

    public static class TiposServicio {

        private static java.sql.Date fechaIniTipoServicio = new java.sql.Date(new java.util.Date().getTime());
        public static final DyccTipoServicioDTO SOLICITUD_DEVOLUCION
                = new DyccTipoServicioDTO(1, "Solicitud de Devolución", fechaIniTipoServicio, null);
        public static final DyccTipoServicioDTO DEVOLUCION_AUTOMATICA
                = new DyccTipoServicioDTO(2, "Devolución Automática", fechaIniTipoServicio, null);
        public static final DyccTipoServicioDTO CASO_COMPENSACION
                = new DyccTipoServicioDTO(3, "Caso de Compensación", fechaIniTipoServicio, null);
        public static final DyccTipoServicioDTO AVISO_COMPENSACION
                = new DyccTipoServicioDTO(4, "Aviso de Compensación", fechaIniTipoServicio, null);

        public static void setFechaIniTipoServicio(Date fechaIniTipoServicio) {
            if (fechaIniTipoServicio != null) {
                Constantes.TiposServicio.fechaIniTipoServicio = (Date) fechaIniTipoServicio.clone();
            } else {
                Constantes.TiposServicio.fechaIniTipoServicio = null;
            }
        }

        public static Date getFechaIniTipoServicio() {
            if (null != fechaIniTipoServicio) {
                return (Date) fechaIniTipoServicio.clone();
            } else {
                return null;
            }
        }
    }

    public static class EstadosResolucion {

        public static final DyccEstResolDTO SIN_RESOL = new DyccEstResolDTO(0, "Sin Resolución");
        public static final DyccEstResolDTO EN_APROBACION = new DyccEstResolDTO(1, "En Aprobación");
        public static final DyccEstResolDTO APROBADA = new DyccEstResolDTO(2, "Aprobada");
        public static final DyccEstResolDTO NO_APROBADA = new DyccEstResolDTO(3, "No Aprobada");
    }

    public static class EstadosCompensacion {

        private static java.sql.Date fechaIniEstCompensacion = new java.sql.Date(new java.util.Date().getTime());
        public static final DyccEstadoCompDTO RECIBIDO
                = new DyccEstadoCompDTO(1, "Recibido", fechaIniEstCompensacion, null);
        public static final DyccEstadoCompDTO REGISTRO
                = new DyccEstadoCompDTO(2, "Registrada", fechaIniEstCompensacion, null);
        public static final DyccEstadoCompDTO EN_PROCESO
                = new DyccEstadoCompDTO(3, "En Proceso", fechaIniEstCompensacion, null);
        public static final DyccEstadoCompDTO PENDIENTE_RESOLVER
                = new DyccEstadoCompDTO(4, "Pendiente de Resolver", fechaIniEstCompensacion, null);
        public static final DyccEstadoCompDTO IMPROCEDENTE
                = new DyccEstadoCompDTO(5, "Improcedente", fechaIniEstCompensacion, null);
        public static final DyccEstadoCompDTO REQUERIDO
                = new DyccEstadoCompDTO(6, "Requerido", fechaIniEstCompensacion, null);
        public static final DyccEstadoCompDTO DESISTIDO
                = new DyccEstadoCompDTO(7, "Desistido", fechaIniEstCompensacion, null);
        public static final DyccEstadoCompDTO EN_REVISION
                = new DyccEstadoCompDTO(8, "En revisión", fechaIniEstCompensacion, null);

        public static void setFechaIniEstCompensacion(Date fechaIniEstCompensacion) {
            if (fechaIniEstCompensacion != null) {
                Constantes.EstadosCompensacion.fechaIniEstCompensacion = (Date) fechaIniEstCompensacion.clone();
            } else {
                Constantes.EstadosCompensacion.fechaIniEstCompensacion = null;
            }
        }

        public static Date getFechaIniEstCompensacion() {
            if (null != fechaIniEstCompensacion) {
                return (Date) fechaIniEstCompensacion.clone();
            } else {
                return null;
            }
        }
    }

    public static class OrigenesSaldo {

        private static java.util.Date fechaIniEstCompensacion = new java.util.Date();

        //public DyccOrigenSaldoDTO (String descripcion, java.util.Date fechaInicio, java.util.Date fechaFin, Integer idOrigenSaldo, Integer ordenSec)
        public static final DyccOrigenSaldoDTO SALDO_FAVOR
                = new DyccOrigenSaldoDTO("Saldo a favor", fechaIniEstCompensacion, null, 1, 10);
        public static final DyccOrigenSaldoDTO PAGO_INDEBIDO
                = new DyccOrigenSaldoDTO("Pago de lo Indebido", fechaIniEstCompensacion, null, 2, 20);
        public static final DyccOrigenSaldoDTO IMPAC_ANTERIORES
                = new DyccOrigenSaldoDTO("IMPAC por Recuperar de Ejercicios Anteriores", fechaIniEstCompensacion, null, 3,
                        30);
        public static final DyccOrigenSaldoDTO RESOLUCION_SENTENCIA
                = new DyccOrigenSaldoDTO("Resolucion o Sentencia", fechaIniEstCompensacion, null, 4, 40);
        public static final DyccOrigenSaldoDTO MISIONES_DIPLOMATICAS
                = new DyccOrigenSaldoDTO("Misiones Diplomaticas", fechaIniEstCompensacion, null, 5, 50);
        public static final DyccOrigenSaldoDTO ORGANISMOS_INTERNACIONALES
                = new DyccOrigenSaldoDTO("Organismos Internacionales", fechaIniEstCompensacion, null, 6, 60);
        public static final DyccOrigenSaldoDTO EXTRANJEROS_ESTABLECIMIENTO
                = new DyccOrigenSaldoDTO("Extranjeros sin Establecimiento Permanente en el Pais", fechaIniEstCompensacion,
                        null, 7, 70);
        public static final DyccOrigenSaldoDTO TURISTAS_EXTRANJEROS
                = new DyccOrigenSaldoDTO("Turistas Extranjeros", fechaIniEstCompensacion, null, 8, 80);

        public static void setFechaIniEstCompensacion(Date fechaIniEstCompensacion) {
            if (fechaIniEstCompensacion != null) {
                Constantes.OrigenesSaldo.fechaIniEstCompensacion = (Date) fechaIniEstCompensacion.clone();
            } else {
                Constantes.OrigenesSaldo.fechaIniEstCompensacion = null;
            }
        }

        public static Date getFechaIniEstCompensacion() {
            if (null != fechaIniEstCompensacion) {
                return (Date) fechaIniEstCompensacion.clone();
            } else {
                return null;
            }
        }
    }

    public static class TiposPeriodo {

        private static java.sql.Date fechaIniTipoPeriodo = new java.sql.Date(new java.util.Date().getTime());
        public static final DyccTipoPeriodoDTO MENSUAL
                = new DyccTipoPeriodoDTO("M", "Mensual", fechaIniTipoPeriodo, null);
        public static final DyccTipoPeriodoDTO TRIMESTRAL
                = new DyccTipoPeriodoDTO("T", "Trimestral", fechaIniTipoPeriodo, null);
        public static final DyccTipoPeriodoDTO CUATRIMESTRAL
                = new DyccTipoPeriodoDTO("Q", "Cuatrimestral", fechaIniTipoPeriodo, null);
        public static final DyccTipoPeriodoDTO SEMESTRAL_A
                = new DyccTipoPeriodoDTO("S", "Semestral (A)", fechaIniTipoPeriodo, null);
        public static final DyccTipoPeriodoDTO SEMESTRAL_B
                = new DyccTipoPeriodoDTO("L", "Semestral (B)", fechaIniTipoPeriodo, null);
        public static final DyccTipoPeriodoDTO AJUSTE
                = new DyccTipoPeriodoDTO("J", "Ajuste", fechaIniTipoPeriodo, null);
        public static final DyccTipoPeriodoDTO DEL_EJERCICIO
                = new DyccTipoPeriodoDTO("Y", "Del Ejercicio", fechaIniTipoPeriodo, null);
        public static final DyccTipoPeriodoDTO BIMESTRAL
                = new DyccTipoPeriodoDTO("B", "Bimestral", fechaIniTipoPeriodo, null);
        public static final DyccTipoPeriodoDTO SIN_PERIODO
                = new DyccTipoPeriodoDTO("N", "Sin Periodo", fechaIniTipoPeriodo, null);

        public static void setFechaIniTipoPeriodo(Date fechaIniTipoPeriodo) {
            if (fechaIniTipoPeriodo != null) {
                Constantes.TiposPeriodo.fechaIniTipoPeriodo = (Date) fechaIniTipoPeriodo.clone();
            } else {
                Constantes.TiposPeriodo.fechaIniTipoPeriodo = null;
            }
        }

        public static Date getFechaIniTipoPeriodo() {
            if (null != fechaIniTipoPeriodo) {
                return (Date) fechaIniTipoPeriodo.clone();
            } else {
                return null;
            }
        }
    }

    public static class EstadosReq {

        private static java.sql.Date fechaIniEstadoReq = new java.sql.Date(new java.util.Date().getTime());
        public static final DyccEstadoReqDTO EMITIDO = new DyccEstadoReqDTO(1, "Emitido", fechaIniEstadoReq, null);
        public static final DyccEstadoReqDTO AUTORIZADO
                = new DyccEstadoReqDTO(2, "Autorizado", fechaIniEstadoReq, null);
        public static final DyccEstadoReqDTO RECHAZADO = new DyccEstadoReqDTO(3, "Rechazado", fechaIniEstadoReq, null);
        public static final DyccEstadoReqDTO VENCIDO = new DyccEstadoReqDTO(4, "Vencido", fechaIniEstadoReq, null);
        public static final DyccEstadoReqDTO SOLVENTADO
                = new DyccEstadoReqDTO(5, "Solventado", fechaIniEstadoReq, null);

        public static void setFechaIniEstadoReq(Date fechaIniEstadoReq) {
            if (fechaIniEstadoReq != null) {
                Constantes.EstadosReq.fechaIniEstadoReq = (Date) fechaIniEstadoReq.clone();
            } else {
                Constantes.EstadosReq.fechaIniEstadoReq = null;
            }
        }

        public static Date getFechaIniEstadoReq() {
            if (null != fechaIniEstadoReq) {
                return (Date) fechaIniEstadoReq.clone();
            } else {
                return null;
            }
        }
    }

    public static class EstadosDoc {

        private static java.sql.Date fechaIniEstadoDoc = new java.sql.Date(new java.util.Date().getTime());
        public static final DyccEstadoDocDTO ADJUNTADO = new DyccEstadoDocDTO(1, "Adjuntado", fechaIniEstadoDoc, null);
        public static final DyccEstadoDocDTO APROBADO = new DyccEstadoDocDTO(2, "Aprobado", fechaIniEstadoDoc, null);
        public static final DyccEstadoDocDTO GENERADO = new DyccEstadoDocDTO(3, "Generado", fechaIniEstadoDoc, null);
        public static final DyccEstadoDocDTO EN_MODIFICACION
                = new DyccEstadoDocDTO(4, "En Modificacion", fechaIniEstadoDoc, null);
        public static final DyccEstadoDocDTO EN_APROBACION
                = new DyccEstadoDocDTO(5, "En Aprobacion", fechaIniEstadoDoc, null);
        public static final DyccEstadoDocDTO RECHAZADO = new DyccEstadoDocDTO(6, "Rechazado", fechaIniEstadoDoc, null);
        public static final DyccEstadoDocDTO DILIGENCIADO
                = new DyccEstadoDocDTO(7, "Diligenciado", fechaIniEstadoDoc, null);
        public static final DyccEstadoDocDTO NOTIFICADO
                = new DyccEstadoDocDTO(8, "Notificado", fechaIniEstadoDoc, null);

        public static void setFechaIniEstadoDoc(Date fechaIniEstadoDoc) {
            if (fechaIniEstadoDoc != null) {
                Constantes.EstadosDoc.fechaIniEstadoDoc = (Date) fechaIniEstadoDoc.clone();
            } else {
                Constantes.EstadosDoc.fechaIniEstadoDoc = null;
            }
        }

        public static Date getFechaIniEstadoDoc() {
            if (null != fechaIniEstadoDoc) {
                return (Date) fechaIniEstadoDoc.clone();
            } else {
                return null;
            }
        }
    }

    public static class TiposDocumento {

        public static final DyccTipoDocumentoDTO PRIMER_REQUERIMIENTO
                = new DyccTipoDocumentoDTO(1, "Primer Requerimiento");
        public static final DyccTipoDocumentoDTO SEGUNDO_REQUERIMIENTO
                = new DyccTipoDocumentoDTO(2, "Segundo Requerimiento");
        public static final DyccTipoDocumentoDTO REQUERIMIENTO_FACTURAS
                = new DyccTipoDocumentoDTO(3, "Requerimiento  para confirmacion de operaciones con proveedores");
        public static final DyccTipoDocumentoDTO CARTA_SOLIC_PRESENCIA
                = new DyccTipoDocumentoDTO(4, "Carta solicitando la presencia del contribuyente");
        public static final DyccTipoDocumentoDTO RESOLUCION = new DyccTipoDocumentoDTO(5, "Resolucion");
        public static final DyccTipoDocumentoDTO LIQUIDACION = new DyccTipoDocumentoDTO(6, "Liquidacion");
        public static final DyccTipoDocumentoDTO CARTA_INVITACION
                = new DyccTipoDocumentoDTO(7, "Carta de invitacion al contribuyente");
    }

    public static class TiposPlazo {

        private static java.sql.Date fechaIniTipoPlazo = new java.sql.Date(new java.util.Date().getTime());
        public static final DyccTipoPlazoDTO DIAS_HABILES
                = new DyccTipoPlazoDTO(1, "Dias Habiles", fechaIniTipoPlazo, null);
        public static final DyccTipoPlazoDTO DIAS_NATURALES
                = new DyccTipoPlazoDTO(2, "Dias naturales", fechaIniTipoPlazo, null);

        public static void setFechaIniTipoPlazo(Date fechaIniTipoPlazo) {
            if (fechaIniTipoPlazo != null) {
                Constantes.TiposPlazo.fechaIniTipoPlazo = (Date) fechaIniTipoPlazo.clone();
            } else {
                Constantes.TiposPlazo.fechaIniTipoPlazo = null;
            }
        }
    }

    public static class AccionesSeg {

        public static final DyccAccionSegDTO APROBACION = new DyccAccionSegDTO("Aprobacion", 1);
        public static final DyccAccionSegDTO NOTIFICACION = new DyccAccionSegDTO("Notificacion", 2);
        public static final DyccAccionSegDTO SOLVENTACION = new DyccAccionSegDTO("Solventacion", 3);
        public static final DyccAccionSegDTO ESCALACION = new DyccAccionSegDTO("Escalacion", 4);
        public static final DyccAccionSegDTO RECHAZO = new DyccAccionSegDTO("Rechazo", 5);
        public static final DyccAccionSegDTO VENCIMIENTO = new DyccAccionSegDTO("Vencimiento", 6);
    }

    public static class TiposResolucion {

        public static final DyccTipoResolDTO AUTORIZADA_TOTAL
                = new DyccTipoResolDTO(AUTORIZADATOTAL, null, new java.util.Date(), 1,
                        Constantes.TiposServicio.SOLICITUD_DEVOLUCION);
        public static final DyccTipoResolDTO AUTORIZADA_PARCIAL_REM_NEGADO
                = new DyccTipoResolDTO("Autorizada Parcial con remanente negado", null, new java.util.Date(), 2,
                        Constantes.TiposServicio.SOLICITUD_DEVOLUCION);
        public static final DyccTipoResolDTO AUTORIZADA_PARCIAL_REM_DISP
                = new DyccTipoResolDTO("Autorizada Parcial con remanente disponible", null, new java.util.Date(), 3,
                        Constantes.TiposServicio.SOLICITUD_DEVOLUCION);
        public static final DyccTipoResolDTO NEGADA
                = new DyccTipoResolDTO("Negada", null, new java.util.Date(), 4, Constantes.TiposServicio.SOLICITUD_DEVOLUCION);
        public static final DyccTipoResolDTO DESISTIDA
                = new DyccTipoResolDTO("Desistida", null, new java.util.Date(), 5,
                        Constantes.TiposServicio.SOLICITUD_DEVOLUCION);
        public static final DyccTipoResolDTO SALDOAFAVOR_IMPROCEDENTE
                = new DyccTipoResolDTO("Saldo a favor improcedente", null, new java.util.Date(), 6,
                        Constantes.TiposServicio.CASO_COMPENSACION);
        public static final DyccTipoResolDTO COMPENSACION_IMPROCEDENTE
                = new DyccTipoResolDTO("Compensacion Improcedente", null, new java.util.Date(), 7,
                        Constantes.TiposServicio.CASO_COMPENSACION);
        public static final DyccTipoResolDTO REGISTRAR_CASOCOMP
                = new DyccTipoResolDTO("Registrar Caso de Compensacion", null, new java.util.Date(), 8,
                        Constantes.TiposServicio.CASO_COMPENSACION);
        public static final DyccTipoResolDTO RECHAZOS_INCONS_DEV_AUTO
                = new DyccTipoResolDTO("Rechazos e Inconsistencias de Devoluciones Automaticas", null, new java.util.Date(),
                        10, Constantes.TiposServicio.DEVOLUCION_AUTOMATICA);
        public static final DyccTipoResolDTO AUTORIZADA_TOTAL_DEV_AUTO
                = new DyccTipoResolDTO(AUTORIZADATOTAL, null, new java.util.Date(), 11,
                        Constantes.TiposServicio.DEVOLUCION_AUTOMATICA);
        public static final DyccTipoResolDTO AUTORIZADA_PARCIAL_REM_DISP_DEV_AUTO
                = new DyccTipoResolDTO("Autorizada Parcial con remanente disponible", null, new java.util.Date(), 12,
                        Constantes.TiposServicio.DEVOLUCION_AUTOMATICA);
        public static final DyccTipoResolDTO DESISTIDA_PORSISTEMA_DEV_AUTO
                = new DyccTipoResolDTO("Desistida por sistema", null, new java.util.Date(), 13,
                        Constantes.TiposServicio.DEVOLUCION_AUTOMATICA);
        public static final DyccTipoResolDTO AUTORIZADA_PARCIAL
                = new DyccTipoResolDTO("Autorizada parcial", null, new java.util.Date(), 14,
                        Constantes.TiposServicio.DEVOLUCION_AUTOMATICA);
    }

    public static class TiposDeclaracion {

        private static java.sql.Date fechaIniTipoPlazo = new java.sql.Date(new java.util.Date().getTime());
        public static final DyccTipoDeclaraDTO NORMAL
                = new DyccTipoDeclaraDTO("Normal", null, fechaIniTipoPlazo, 1, Boolean.TRUE);
        public static final DyccTipoDeclaraDTO COMPLEMENTARIA
                = new DyccTipoDeclaraDTO("Complementaria", null, fechaIniTipoPlazo, 2, Boolean.TRUE);
        public static final DyccTipoDeclaraDTO SIN_DECLARACION
                = new DyccTipoDeclaraDTO("Sin declaración", null, fechaIniTipoPlazo, 3, Boolean.FALSE);
    }

    //Variables estaticas para estandarizar el nombre de los objetos contenidos en los HashMaps - Sonar
    public static class NombresParametros {

        public static final String NUMERO_CONTROL = "numControl";
        public static final String NOMBRE_ARCHIVO = "nombreArchivo";
    }

    public static class MovsIcep {

        public static final DyccMovIcepDTO COMPENSACION_IMPROCEDENTE
                = new DyccMovIcepDTO("COMIMP", 9, AfectacionesIcep.ABONO, "Compensación Improcedente");
        public static final DyccMovIcepDTO ABONO_CANC_REMANENTE_DECLARA
                = new DyccMovIcepDTO("CANCELREM", 22, AfectacionesIcep.ABONO, "Cancelacion Remanente Declaracion");
        public static final DyccMovIcepDTO CANCELACION_DEVLUCION
                = new DyccMovIcepDTO("DYCCANDEV", 25, AfectacionesIcep.ABONO, "CANCELACION DEVOLUCION");
        public static final DyccMovIcepDTO ABONO_COMPENSACION_OFICIO
                = new DyccMovIcepDTO("COMPOFI", 12, AfectacionesIcep.ABONO, "Compensación Oficio");
        public static final DyccMovIcepDTO ALTA_IMPOR_COMP_DESISTIDO
                = new DyccMovIcepDTO("DWHACTIMPD", 15, AfectacionesIcep.ABONO, "Alta de Importe Compensado Desistido");
        public static final DyccMovIcepDTO ABONO_COMP_OFICIO_DESISTIDO
                = new DyccMovIcepDTO("COMPOFID", 16, AfectacionesIcep.ABONO, "Compensación Oficio Desistido");
        public static final DyccMovIcepDTO ALTA_SALDO
                = new DyccMovIcepDTO("DWHACTSALDO", 10, AfectacionesIcep.ABONO, "Alta de Saldo");
        public static final DyccMovIcepDTO ABONO_DESISTIMIENTO
                = new DyccMovIcepDTO("ABONODESIST", 28, AfectacionesIcep.ABONO, "Abono por desistimiento");
        public static final DyccMovIcepDTO ABONO_PAGOINDEBIDO
                = new DyccMovIcepDTO("ABONOPAGOINDEB", 31, AfectacionesIcep.ABONO, "Abono diferente a saldo a favor manifestado en solicitud de devolución MAT generada por el contribuyente");
        public static final DyccMovIcepDTO ABONO_SAFDWH
                = new DyccMovIcepDTO("ABONOSAFDWH", 32, AfectacionesIcep.ABONO, "Abono por saldo a favor en declaración validada en Data Warehouse");
        public static final DyccMovIcepDTO ABONO_SAFCONTRIB
                = new DyccMovIcepDTO("ABONOSAFCONTRIB", 33, AfectacionesIcep.ABONO, "Abono por saldo a favor manifestado por el contribuyente en una solicitud de devolución MAT");
        public static final DyccMovIcepDTO ABONO_SAFCONTRIB_AVICOMP
                = new DyccMovIcepDTO("SAFCONTRIBAVICOMP", 34, AfectacionesIcep.ABONO, "Abono por saldo a favor manifestado por el contribuyente en un aviso de compensación MAT");
        public static final DyccMovIcepDTO ABONO_AJUSTE
                = new DyccMovIcepDTO("ABONOAJUSTE", 35, AfectacionesIcep.ABONO, "Abono agregado manualmente por personal de control del saldos para ajustar el saldo del ICEP");

        public static final DyccMovIcepDTO IMPORTE_NEGADO
                = new DyccMovIcepDTO("DYCNEG", 2, AfectacionesIcep.CARGO, "Importe Negado");
        public static final DyccMovIcepDTO COMPENSACION_OFICIO
                = new DyccMovIcepDTO("COMOFI", 7, AfectacionesIcep.CARGO, "Compensación Oficio");
        public static final DyccMovIcepDTO SALDO_IMPROCEDENTE
                = new DyccMovIcepDTO("SALIMP", 8, AfectacionesIcep.CARGO, "Saldo Improcedente");
        public static final DyccMovIcepDTO ALTA_IMPORTE_COMPENSADO
                = new DyccMovIcepDTO("DWHACTIMP", 11, AfectacionesIcep.CARGO, "Alta de Importe Compensado");
        public static final DyccMovIcepDTO ALTA_SALDO_DESISITIDO
                = new DyccMovIcepDTO("DWHACTSALDOD", 14, AfectacionesIcep.CARGO, "Alta de Saldo Desistido");
        public static final DyccMovIcepDTO CARGO_CANC_REMANENTE_DECLARA
                = new DyccMovIcepDTO("CANCELREM", 22, AfectacionesIcep.CARGO, "Cancelacion Remanente Declaracion");
        public static final DyccMovIcepDTO IMPORTE_AUTORIZADO
                = new DyccMovIcepDTO("DYCAUT", 1, AfectacionesIcep.CARGO, "Importe Autorizado");
        public static final DyccMovIcepDTO CARGO_COMPENSACION_IMPROCEDENTE
                = new DyccMovIcepDTO("COMPIMP", 13, AfectacionesIcep.CARGO, "Compensación Improcedente");
        public static final DyccMovIcepDTO CARGO_COMP_OFICIO_DESISTIDO
                = new DyccMovIcepDTO("COMOFID", 17, AfectacionesIcep.CARGO, "Compensación Oficio Desistido");
        public static final DyccMovIcepDTO CARGO_ALTA_SALDO
                = new DyccMovIcepDTO("DWHACTSALDO", 10, AfectacionesIcep.CARGO, "Alta de Saldo");
        public static final DyccMovIcepDTO CARGO_DEVCOMPS_ANTERIORES
                = new DyccMovIcepDTO("CARGODEVCOMPANT", 26, AfectacionesIcep.CARGO, "Devoluciones y/o compensaciones anteriores");
        public static final DyccMovIcepDTO CARGO_ACREDITAMIENTO
                = new DyccMovIcepDTO("CARGOACREDITA", 27, AfectacionesIcep.CARGO, "Acreditamiento");
        public static final DyccMovIcepDTO CARGO_DEV_HIST
                = new DyccMovIcepDTO("CARDEVHIST", 28, AfectacionesIcep.CARGO, "Cargo por saldo autorizado en devolución histórica");
        public static final DyccMovIcepDTO CARGO_COMP_HIST
                = new DyccMovIcepDTO("CARCOMPHIST", 29, AfectacionesIcep.CARGO, "Cargo por compensación histórica");
        public static final DyccMovIcepDTO CARGO_SALDONEG_DEV_HIST
                = new DyccMovIcepDTO("CARNEGDEVHIST", 30, AfectacionesIcep.CARGO, "Cargo por saldo negado en devolución histórica");
        public static final DyccMovIcepDTO CARGO_AJUSTE
                = new DyccMovIcepDTO("CARGOAJUSTE", 31, AfectacionesIcep.CARGO, "Cargo agregado manualmente por personal de control del saldos para ajustar el saldo del ICEP");
    }

    public static class AfectacionesIcep {

        private static java.util.Date fechaInicio = new java.util.Date();
        public static final DyccAfectaIcepDTO ABONO = new DyccAfectaIcepDTO("Abono", null, fechaInicio, 1);
        public static final DyccAfectaIcepDTO CARGO = new DyccAfectaIcepDTO("Cargo", null, fechaInicio, 2);
    }

    public static class OrigenesDeclaracion {

        public static final int CONTRIBUYENTE = 1;
        public static final int MANUAL = 2;
        public static final int DATAWAREHOUSE = 3;
        public static final int VALIDADA_EMPLEADO = 4;
    }

    public static class TiposAviso {

        public static final DyccTipoAvisoDTO NORMAL = new DyccTipoAvisoDTO(1, "Normal");
        public static final DyccTipoAvisoDTO COMPLEMENTARIO = new DyccTipoAvisoDTO(2, "Complementario");
    }

    public static class EstadosSolicitud {

        public static final DyccEstadoSolDTO PENDIENTE_SUBIR_ANEXOS = new DyccEstadoSolDTO(" Pendiente de Subir Anexos", null, null, 1);
        public static final DyccEstadoSolDTO RECIBIDA = new DyccEstadoSolDTO("Recibida", null, null, 2);
        public static final DyccEstadoSolDTO EN_PROCESO = new DyccEstadoSolDTO("En Proceso", null, null, 3);
        public static final DyccEstadoSolDTO REQUERIDA = new DyccEstadoSolDTO("Requerida", null, null, 4);
        public static final DyccEstadoSolDTO DESISTIDA = new DyccEstadoSolDTO("Desistida", null, null, 5);
        public static final DyccEstadoSolDTO PENDIENTE_RESOLVER = new DyccEstadoSolDTO("Pendiente de Resolver", null, null, 6);
        public static final DyccEstadoSolDTO EN_REVISION = new DyccEstadoSolDTO("En Revisión", null, null, 7);
        public static final DyccEstadoSolDTO INCONSISTENTE = new DyccEstadoSolDTO("Inconsistente", null, null, 8);
        public static final DyccEstadoSolDTO NEGADA = new DyccEstadoSolDTO("Negada", null, null, 9);
        public static final DyccEstadoSolDTO AUT_PARCIAL_REM_DISPONIBLE = new DyccEstadoSolDTO("Autorizada Parcial con Remanente Disponible", null, null, 10);
        public static final DyccEstadoSolDTO AUT_PARCIAL_REM_NEGADO = new DyccEstadoSolDTO("Autorizada Parcial con Remanente Negado", null, null, 11);
        public static final DyccEstadoSolDTO AUTORIZADA_TOTAL = new DyccEstadoSolDTO(AUTORIZADATOTAL, null, null, 12);
        public static final DyccEstadoSolDTO PAGADA = new DyccEstadoSolDTO("Pagada", null, null, 13);
        public static final DyccEstadoSolDTO RECIBIDA_INSIST_CONTTE = new DyccEstadoSolDTO("Recibida a Insistencia del Contribuyente", null, null, 14);
        public static final DyccEstadoSolDTO DESISTIDA_SISTEMA = new DyccEstadoSolDTO("Desistida por sistema", null, null, 15);
        public static final DyccEstadoSolDTO PREAUTORIZADA = new DyccEstadoSolDTO("Preautorizada", null, null, 16);
        public static final DyccEstadoSolDTO EN_PROCESO_SIVAD = new DyccEstadoSolDTO("En proceso SIVAD", null, null, 17);
        public static final DyccEstadoSolDTO EN_PROCESO_MORSA = new DyccEstadoSolDTO("En proceso MORSA", null, null, 18);
        public static final DyccEstadoSolDTO AUTORIZADA_TOTAL_SIVAD = new DyccEstadoSolDTO("Autorizada total SIVAD", null, null, 19);
        public static final DyccEstadoSolDTO AUTORIZADA_TOTAL_MORSA = new DyccEstadoSolDTO("Autorizada total MORSA", null, null, 20);
        public static final DyccEstadoSolDTO EN_PROCESO_SAD = new DyccEstadoSolDTO("En Proceso SAD", null, null, 21);
        public static final DyccEstadoSolDTO AUTORIZADA_SAD = new DyccEstadoSolDTO("Autorizada SAD", null, null, 22);
        public static final DyccEstadoSolDTO NEGADA_SAD = new DyccEstadoSolDTO("Negada SAD", null, null, 23);
        public static final DyccEstadoSolDTO EN_PROCESO_PAGO = new DyccEstadoSolDTO("En Proceso de Pago ", null, null, 24);
        public static final DyccEstadoSolDTO PREASIGNADA = new DyccEstadoSolDTO("Preasignada", null, null, 1718);
    }

    public static class PermisosEspecialesAjustarSaldo {

        public static final DyctAccionPrivAjusDTO MAT_DYC = new DyctAccionPrivAjusDTO(1, 1);
        public static final DyctAccionPrivAjusDTO ADMIN_CENTRAL = new DyctAccionPrivAjusDTO(2, 1);
    }

}
