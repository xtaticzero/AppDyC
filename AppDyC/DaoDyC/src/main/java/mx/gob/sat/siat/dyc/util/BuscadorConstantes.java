package mx.gob.sat.siat.dyc.util;

import mx.gob.sat.siat.dyc.domain.DyccEstadoReqDTO;
import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccAccionSegDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccAfectaIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstResolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccEstadoDocDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoPlazoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

public final class BuscadorConstantes {

    

    private BuscadorConstantes() {
    }

    public static DyccTipoServicioDTO obtenerTipoServicio(Integer idTipoServicio) {
        switch (idTipoServicio) {
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.TiposServicio.SOLICITUD_DEVOLUCION;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.TiposServicio.DEVOLUCION_AUTOMATICA;
        case ConstantesDyCNumerico.VALOR_3:
            return Constantes.TiposServicio.CASO_COMPENSACION;
        case ConstantesDyCNumerico.VALOR_4:
            return Constantes.TiposServicio.AVISO_COMPENSACION;
        }
        return null;
    }

    public static DyccEstResolDTO obtenerEstadoResolucion(Integer idEstResol) {
        switch (idEstResol) {
        case ConstantesDyCNumerico.VALOR_0:
            return Constantes.EstadosResolucion.SIN_RESOL;
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.EstadosResolucion.EN_APROBACION;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.EstadosResolucion.APROBADA;
        case ConstantesDyCNumerico.VALOR_3:
            return Constantes.EstadosResolucion.NO_APROBADA;
        }
        return null;
    }

    public static DyccEstadoCompDTO obtenerEstadoComp(Integer idEstadoComp) {
        DyccEstadoCompDTO estadoComp;
        switch (idEstadoComp) {
        case ConstantesDyCNumerico.VALOR_1:
            estadoComp = Constantes.EstadosCompensacion.RECIBIDO;
            break;
        case ConstantesDyCNumerico.VALOR_2:
            estadoComp = Constantes.EstadosCompensacion.REGISTRO;
            break;
        case ConstantesDyCNumerico.VALOR_3:
            estadoComp = Constantes.EstadosCompensacion.EN_PROCESO;
            break;
        case ConstantesDyCNumerico.VALOR_4:
            estadoComp = Constantes.EstadosCompensacion.PENDIENTE_RESOLVER;
            break;
        case ConstantesDyCNumerico.VALOR_5:
            estadoComp = Constantes.EstadosCompensacion.IMPROCEDENTE;
            break;
        case ConstantesDyCNumerico.VALOR_6:
            estadoComp = Constantes.EstadosCompensacion.REQUERIDO;
            break;
        case ConstantesDyCNumerico.VALOR_7:
            estadoComp = Constantes.EstadosCompensacion.DESISTIDO;
            break;
        case ConstantesDyCNumerico.VALOR_8:
            estadoComp = Constantes.EstadosCompensacion.EN_REVISION;
            break;
        default:
            estadoComp = null;
            break;
        }
        return estadoComp;
    }

    public static DyccOrigenSaldoDTO obtenerOrigenSaldo(Integer idOrigenSaldo) {
        switch (idOrigenSaldo) {
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.OrigenesSaldo.SALDO_FAVOR;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.OrigenesSaldo.PAGO_INDEBIDO;
        case ConstantesDyCNumerico.VALOR_3:
            return Constantes.OrigenesSaldo.IMPAC_ANTERIORES;
        case ConstantesDyCNumerico.VALOR_4:
            return Constantes.OrigenesSaldo.RESOLUCION_SENTENCIA;
        }
        return continuidadOrigenSaldo(idOrigenSaldo);
    }

    private static DyccOrigenSaldoDTO continuidadOrigenSaldo(Integer idOrigenSaldo) {
        switch (idOrigenSaldo) {
        case ConstantesDyCNumerico.VALOR_5:
            return Constantes.OrigenesSaldo.MISIONES_DIPLOMATICAS;
        case ConstantesDyCNumerico.VALOR_6:
            return Constantes.OrigenesSaldo.ORGANISMOS_INTERNACIONALES;
        case ConstantesDyCNumerico.VALOR_7:
            return Constantes.OrigenesSaldo.EXTRANJEROS_ESTABLECIMIENTO;
        case ConstantesDyCNumerico.VALOR_8:
            return Constantes.OrigenesSaldo.TURISTAS_EXTRANJEROS;
        }
        return null;
    }

    public static DyccTipoPeriodoDTO obtenerTipoPeriodo(String idTipoPeriodo) {
        if ("M".equals(idTipoPeriodo)) {
            return Constantes.TiposPeriodo.MENSUAL;
        }

        if ("T".equals(idTipoPeriodo)) {
            return Constantes.TiposPeriodo.TRIMESTRAL;
        }

        if ("Q".equals(idTipoPeriodo)) {
            return Constantes.TiposPeriodo.CUATRIMESTRAL;
        }

        return continuidadObtenerTipoPeriodo(idTipoPeriodo);
    }

    private static DyccTipoPeriodoDTO continuidadObtenerTipoPeriodo(String idTipoPeriodo) {
        if ("S".equals(idTipoPeriodo)) {
            return Constantes.TiposPeriodo.SEMESTRAL_A;
        }

        if ("L".equals(idTipoPeriodo)) {
            return Constantes.TiposPeriodo.SEMESTRAL_B;
        }

        if ("J".equals(idTipoPeriodo)) {
            return Constantes.TiposPeriodo.AJUSTE;
        }

        return continuaObtenerTipoPeriodo(idTipoPeriodo);
    }

    private static DyccTipoPeriodoDTO continuaObtenerTipoPeriodo(String idTipoPeriodo) {
        if ("Y".equals(idTipoPeriodo)) {
            return Constantes.TiposPeriodo.DEL_EJERCICIO;
        }

        if ("B".equals(idTipoPeriodo)) {
            return Constantes.TiposPeriodo.BIMESTRAL;
        }

        if ("N".equals(idTipoPeriodo)) {
            return Constantes.TiposPeriodo.SIN_PERIODO;
        }
        return null;
    }

    public static DyccEstadoDocDTO obtenerEstadoDoc(Integer idEstadoDoc) {
        switch (idEstadoDoc) {
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.EstadosDoc.ADJUNTADO;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.EstadosDoc.APROBADO;
        case ConstantesDyCNumerico.VALOR_3:
            return Constantes.EstadosDoc.GENERADO;
        case ConstantesDyCNumerico.VALOR_4:
            return Constantes.EstadosDoc.EN_MODIFICACION;
        }
        return continuidadObtenerEstadoDoc(idEstadoDoc);
    }

    private static DyccEstadoDocDTO continuidadObtenerEstadoDoc(Integer idEstadoDoc) {
        switch (idEstadoDoc) {
        case ConstantesDyCNumerico.VALOR_5:
            return Constantes.EstadosDoc.EN_APROBACION;
        case ConstantesDyCNumerico.VALOR_6:
            return Constantes.EstadosDoc.RECHAZADO;
        case ConstantesDyCNumerico.VALOR_7:
            return Constantes.EstadosDoc.DILIGENCIADO;
        case ConstantesDyCNumerico.VALOR_8:
            return Constantes.EstadosDoc.NOTIFICADO;
        }
        return null;
    }

    public static DyccTipoPlazoDTO obtenerTipoPlazo(Integer idTipoPlazo) {
        switch (idTipoPlazo) {
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.TiposPlazo.DIAS_HABILES;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.TiposPlazo.DIAS_NATURALES;
        }
        return null;
    }

    public static DyccEstadoReqDTO obtenerEstadoReq(Integer idEstadoReq) {
        switch (idEstadoReq) {
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.EstadosReq.EMITIDO;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.EstadosReq.AUTORIZADO;
        }
        return continuidadObtenerEstadoReq(idEstadoReq);
    }

    private static DyccEstadoReqDTO continuidadObtenerEstadoReq(Integer idEstadoReq) {
        switch (idEstadoReq) {
        case ConstantesDyCNumerico.VALOR_3:
            return Constantes.EstadosReq.RECHAZADO;
        case ConstantesDyCNumerico.VALOR_4:
            return Constantes.EstadosReq.VENCIDO;
        case ConstantesDyCNumerico.VALOR_5:
            return Constantes.EstadosReq.SOLVENTADO;
        }
        return null;
    }

    public static DyccAccionSegDTO obtenerAccionSeg(Integer idAccionSeg) {
        switch (idAccionSeg) {
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.AccionesSeg.APROBACION;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.AccionesSeg.NOTIFICACION;
        case ConstantesDyCNumerico.VALOR_3:
            return Constantes.AccionesSeg.SOLVENTACION;
        }
        return continuidadObtenerAccionSeg(idAccionSeg);
    }

    private static DyccAccionSegDTO continuidadObtenerAccionSeg(Integer idAccionSeg) {
        switch (idAccionSeg) {
        
        case ConstantesDyCNumerico.VALOR_4:
            return Constantes.AccionesSeg.ESCALACION;
        case ConstantesDyCNumerico.VALOR_5:
            return Constantes.AccionesSeg.RECHAZO;
        case ConstantesDyCNumerico.VALOR_6:
            return Constantes.AccionesSeg.VENCIMIENTO;
        }
        return null;
    }

    public static DyccTipoDeclaraDTO obtenerTipoDeclaracion(Integer idTipoDeclaracion) {
        switch (idTipoDeclaracion) {
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.TiposDeclaracion.NORMAL;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.TiposDeclaracion.COMPLEMENTARIA;
        case ConstantesDyCNumerico.VALOR_3:
            return Constantes.TiposDeclaracion.SIN_DECLARACION;
        case ConstantesDyCNumerico.VALOR_4:
            return Constantes.TiposDeclaracion.COMPLEMENTARIA;
        case ConstantesDyCNumerico.VALOR_5:
            return Constantes.TiposDeclaracion.COMPLEMENTARIA;
        case ConstantesDyCNumerico.VALOR_6:
            return Constantes.TiposDeclaracion.SIN_DECLARACION;
        }
        return null;
    }

    public static DyccTipoDocumentoDTO obtenerTipoDocumento(Integer idTipoDocumento) {
        DyccTipoDocumentoDTO tdoc;
        switch (idTipoDocumento) {
        case ConstantesDyCNumerico.VALOR_1:
            tdoc = Constantes.TiposDocumento.PRIMER_REQUERIMIENTO;
            break;
        case ConstantesDyCNumerico.VALOR_2:
            tdoc = Constantes.TiposDocumento.SEGUNDO_REQUERIMIENTO;
            break;
        case ConstantesDyCNumerico.VALOR_3:
            tdoc = Constantes.TiposDocumento.REQUERIMIENTO_FACTURAS;
            break;
        case ConstantesDyCNumerico.VALOR_4:
            tdoc = Constantes.TiposDocumento.CARTA_SOLIC_PRESENCIA;
            break;
        case ConstantesDyCNumerico.VALOR_5:
            tdoc = Constantes.TiposDocumento.RESOLUCION;
            break;
        case ConstantesDyCNumerico.VALOR_6:
            tdoc = Constantes.TiposDocumento.LIQUIDACION;
            break;
        case ConstantesDyCNumerico.VALOR_7:
            tdoc = Constantes.TiposDocumento.CARTA_INVITACION;
            break;
        default:
            tdoc = null;
            break;
        }
        return tdoc;
    }

    public static DyccTipoResolDTO obtenerTipoResolucion(Integer idTipoResolucion) {
        switch (idTipoResolucion) {
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.TiposResolucion.AUTORIZADA_TOTAL;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_NEGADO;
        case ConstantesDyCNumerico.VALOR_3:
            return Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_DISP;
        case ConstantesDyCNumerico.VALOR_4:
            return Constantes.TiposResolucion.NEGADA;
        case ConstantesDyCNumerico.VALOR_14:
            return Constantes.TiposResolucion.AUTORIZADA_PARCIAL;
        }

        return obtenerTipoResolucionD(idTipoResolucion);
    }

    private static DyccTipoResolDTO obtenerTipoResolucionD(Integer idTipoResolucion) {
        switch (idTipoResolucion) {
        case ConstantesDyCNumerico.VALOR_5:
            return Constantes.TiposResolucion.DESISTIDA;
        case ConstantesDyCNumerico.VALOR_6:
            return Constantes.TiposResolucion.SALDOAFAVOR_IMPROCEDENTE;
        case ConstantesDyCNumerico.VALOR_7:
            return Constantes.TiposResolucion.COMPENSACION_IMPROCEDENTE;
        case ConstantesDyCNumerico.VALOR_8:
            return Constantes.TiposResolucion.REGISTRAR_CASOCOMP;
        }

        return obtenerTipoResolucionT(idTipoResolucion);
    }

    private static DyccTipoResolDTO obtenerTipoResolucionT(Integer idTipoResolucion) {
        switch (idTipoResolucion) {
        case ConstantesDyCNumerico.VALOR_10:
            return Constantes.TiposResolucion.RECHAZOS_INCONS_DEV_AUTO;
        case ConstantesDyCNumerico.VALOR_11:
            return Constantes.TiposResolucion.AUTORIZADA_TOTAL_DEV_AUTO;
        case ConstantesDyCNumerico.VALOR_12:
            return Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_DISP_DEV_AUTO;
        case ConstantesDyCNumerico.VALOR_13:
            return Constantes.TiposResolucion.DESISTIDA_PORSISTEMA_DEV_AUTO;
        }
        return null;
    }

    public static DyccAfectaIcepDTO obtenerTipoAfectacionIcep(Integer idTipoAfectacion) {
        switch (idTipoAfectacion) {
        case ConstantesDyCNumerico.VALOR_1:
            return Constantes.AfectacionesIcep.ABONO;
        case ConstantesDyCNumerico.VALOR_2:
            return Constantes.AfectacionesIcep.CARGO;
        }
        return null;
    }

    public static Integer obtenerOrigenDeclaracion(Integer origenDeclaracion) {
        switch (origenDeclaracion)
        {
            case ConstantesDyCNumerico.VALOR_1:
                return Constantes.OrigenesDeclaracion.CONTRIBUYENTE;
            case ConstantesDyCNumerico.VALOR_2:
                return Constantes.OrigenesDeclaracion.MANUAL;
            case ConstantesDyCNumerico.VALOR_3:
                return Constantes.OrigenesDeclaracion.DATAWAREHOUSE;
            case ConstantesDyCNumerico.VALOR_4:
                return Constantes.OrigenesDeclaracion.VALIDADA_EMPLEADO;
        }
        return null;
    }

    public static DyccMovIcepDTO obtenerMovIcep(Integer idMovimiento, Integer idAfectacion){
        if(idAfectacion.intValue() == Constantes.AfectacionesIcep.ABONO.getIdAfectacion().intValue()){
            if(idMovimiento.intValue() == ConstantesDyCNumerico.VALOR_9){
                return Constantes.MovsIcep.COMPENSACION_IMPROCEDENTE;
            }
            if(idMovimiento.intValue() == ConstantesDyCNumerico.VALOR_22){
                return Constantes.MovsIcep.ABONO_CANC_REMANENTE_DECLARA;
            }
            if(idMovimiento.intValue() == ConstantesDyCNumerico.VALOR_25){
            return Constantes.MovsIcep.CANCELACION_DEVLUCION;
            }
            if(idMovimiento.intValue() == ConstantesDyCNumerico.VALOR_12){
            return Constantes.MovsIcep.ABONO_COMPENSACION_OFICIO;
            }
            if(idMovimiento.intValue() == ConstantesDyCNumerico.VALOR_15){
            return Constantes.MovsIcep.ALTA_IMPOR_COMP_DESISTIDO;
            }
            if(idMovimiento.intValue() == ConstantesDyCNumerico.VALOR_16){
                return Constantes.MovsIcep.ABONO_COMP_OFICIO_DESISTIDO;
            }
            if(idMovimiento.intValue() == ConstantesDyCNumerico.VALOR_10){
                return Constantes.MovsIcep.ALTA_SALDO;
            }
            if(idMovimiento.intValue() == ConstantesDyCNumerico.VALOR_28){
                return Constantes.MovsIcep.ABONO_DESISTIMIENTO;
            }
            
            switch(idMovimiento){
                case ConstantesDyCNumerico.VALOR_31: return Constantes.MovsIcep.ABONO_PAGOINDEBIDO ;
                case ConstantesDyCNumerico.VALOR_32: return Constantes.MovsIcep.ABONO_SAFDWH ;
                case ConstantesDyCNumerico.VALOR_33: return Constantes.MovsIcep.ABONO_SAFCONTRIB ;
                case ConstantesDyCNumerico.VALOR_34: return Constantes.MovsIcep.ABONO_SAFCONTRIB_AVICOMP ;
                case ConstantesDyCNumerico.VALOR_35: return Constantes.MovsIcep.ABONO_AJUSTE ;
            }
        }
        else if(idAfectacion.intValue() == Constantes.AfectacionesIcep.CARGO.getIdAfectacion().intValue())
        {
            return obtenerMovIcepCargo(idMovimiento);
         
        }
        return null;
    }
    
    private static DyccMovIcepDTO obtenerMovIcepCargo(Integer idMovimiento) {
          switch(idMovimiento){
                case 2:
                    return Constantes.MovsIcep.IMPORTE_NEGADO;
                case ConstantesDyCNumerico.VALOR_7:    
                    return Constantes.MovsIcep.COMPENSACION_OFICIO;
                case ConstantesDyCNumerico.VALOR_8:
                    return Constantes.MovsIcep.SALDO_IMPROCEDENTE;
                case ConstantesDyCNumerico.VALOR_11:
                    return Constantes.MovsIcep.ALTA_IMPORTE_COMPENSADO;
                case ConstantesDyCNumerico.VALOR_14:
                    return Constantes.MovsIcep.ALTA_SALDO_DESISITIDO;
                case ConstantesDyCNumerico.VALOR_22:
                   return Constantes.MovsIcep.CARGO_CANC_REMANENTE_DECLARA;
                case 1:
                    return Constantes.MovsIcep.IMPORTE_AUTORIZADO;
                case ConstantesDyCNumerico.VALOR_13:
                    return Constantes.MovsIcep.CARGO_COMPENSACION_IMPROCEDENTE;
                case ConstantesDyCNumerico.VALOR_17:
                    return Constantes.MovsIcep.CARGO_COMP_OFICIO_DESISTIDO;
                case ConstantesDyCNumerico.VALOR_10:
                    return Constantes.MovsIcep.CARGO_ALTA_SALDO;
                case ConstantesDyCNumerico.VALOR_26:
                    return Constantes.MovsIcep.CARGO_DEVCOMPS_ANTERIORES;
                case ConstantesDyCNumerico.VALOR_27:
                    return Constantes.MovsIcep.CARGO_ACREDITAMIENTO;
                case ConstantesDyCNumerico.VALOR_28:
                    return Constantes.MovsIcep.CARGO_DEV_HIST;
                case ConstantesDyCNumerico.VALOR_29:
                    return Constantes.MovsIcep.CARGO_COMP_HIST;
                case ConstantesDyCNumerico.VALOR_30:
                    return Constantes.MovsIcep.CARGO_SALDONEG_DEV_HIST;
                case ConstantesDyCNumerico.VALOR_31:
                    return Constantes.MovsIcep.CARGO_AJUSTE;
            }
          return null;
    }

    public static DyccTipoAvisoDTO obtenerTipoAviso(Integer idTipoAviso)
    {
        switch (idTipoAviso) 
        {
            case ConstantesDyCNumerico.VALOR_1:
                return Constantes.TiposAviso.NORMAL;
            case ConstantesDyCNumerico.VALOR_2:
                return Constantes.TiposAviso.COMPLEMENTARIO;
        }
        return null;
    }

    public static DyccEstadoSolDTO obtenerEstadoSolicitud(Integer idEstadoSolicitud)
    {
        switch (idEstadoSolicitud) 
        {
            case ConstantesDyCNumerico.VALOR_1: return Constantes.EstadosSolicitud.PENDIENTE_SUBIR_ANEXOS;
            case ConstantesDyCNumerico.VALOR_2: return Constantes.EstadosSolicitud.RECIBIDA;
            case ConstantesDyCNumerico.VALOR_3: return Constantes.EstadosSolicitud.EN_PROCESO;
            case ConstantesDyCNumerico.VALOR_4: return Constantes.EstadosSolicitud.REQUERIDA;
            case ConstantesDyCNumerico.VALOR_5: return Constantes.EstadosSolicitud.DESISTIDA;
            case ConstantesDyCNumerico.VALOR_6: return Constantes.EstadosSolicitud.PENDIENTE_RESOLVER;
            case ConstantesDyCNumerico.VALOR_7: return Constantes.EstadosSolicitud.EN_REVISION;
            case ConstantesDyCNumerico.VALOR_8: return Constantes.EstadosSolicitud.INCONSISTENTE;
            case ConstantesDyCNumerico.VALOR_9: return Constantes.EstadosSolicitud.NEGADA;
            default:                            return obtenerEstadoSolicitud2(idEstadoSolicitud);
        }
    }
    public static DyccEstadoSolDTO obtenerEstadoSolicitud2(Integer idEstadoSolicitud){
        switch(idEstadoSolicitud){
            
            case ConstantesDyCNumerico.VALOR_10: return Constantes.EstadosSolicitud.AUT_PARCIAL_REM_DISPONIBLE;
            case ConstantesDyCNumerico.VALOR_11: return Constantes.EstadosSolicitud.AUT_PARCIAL_REM_NEGADO;
            case ConstantesDyCNumerico.VALOR_12: return Constantes.EstadosSolicitud.AUTORIZADA_TOTAL;
            case ConstantesDyCNumerico.VALOR_13: return Constantes.EstadosSolicitud.PAGADA;
            case ConstantesDyCNumerico.VALOR_14: return Constantes.EstadosSolicitud.RECIBIDA_INSIST_CONTTE;
            case ConstantesDyCNumerico.VALOR_15: return Constantes.EstadosSolicitud.DESISTIDA_SISTEMA;
            case ConstantesDyCNumerico.VALOR_16: return Constantes.EstadosSolicitud.PREAUTORIZADA;
            case ConstantesDyCNumerico.VALOR_17: return Constantes.EstadosSolicitud.EN_PROCESO_SIVAD;
            case ConstantesDyCNumerico.VALOR_18: return Constantes.EstadosSolicitud.EN_PROCESO_MORSA;
            case ConstantesDyCNumerico.VALOR_19: return Constantes.EstadosSolicitud.AUTORIZADA_TOTAL_SIVAD;
            case ConstantesDyCNumerico.VALOR_20: return Constantes.EstadosSolicitud.AUTORIZADA_TOTAL_MORSA;
            case ConstantesDyCNumerico.VALOR_21: return Constantes.EstadosSolicitud.EN_PROCESO_SAD;
            case ConstantesDyCNumerico.VALOR_22: return Constantes.EstadosSolicitud.AUTORIZADA_SAD;
            case ConstantesDyCNumerico.VALOR_23: return Constantes.EstadosSolicitud.NEGADA_SAD;
            case ConstantesDyCNumerico.VALOR_24: return Constantes.EstadosSolicitud.EN_PROCESO_PAGO;  
            case ConstantesDyCNumerico.VALOR_1718: return Constantes.EstadosSolicitud.PREASIGNADA;
        }
        return null;
    }

    public static DyctAccionMovSalDTO.TipoAccionMovSaldo obtenerTipoAccionMovSaldo(Integer idTipoAccion){
        for(DyctAccionMovSalDTO.TipoAccionMovSaldo tipoAccion : mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.values()){
            if(tipoAccion.getId().intValue() == idTipoAccion){
                return tipoAccion;
            }
        }
        return null;
    }
    
}
