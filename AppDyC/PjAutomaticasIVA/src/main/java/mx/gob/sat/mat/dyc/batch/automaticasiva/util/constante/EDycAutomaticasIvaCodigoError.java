/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.batch.automaticasiva.util.constante;

import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.IDycCodigoError;
import mx.gob.sat.siat.dyc.util.constante.EDycEntidadesFuncionales;

/**
 * 
 * @author GAER8674
 */

public enum EDycAutomaticasIvaCodigoError implements IDycCodigoError{
    // PROCESO DE DEVOLUCIONES AUTOMATICAS DE IVA
    AUTOMATICAS_GENERAL(EDycEntidadesFuncionales.DEV_AUTOMATICAS_IVA, "000", "Ocurrio un error durante la ejecucion del proceso de Devolucion Automatica de IVA, intente mas tarde."),
    AUTOMATICAS_DEVOLUCION_LOTE_ERRORPARCIAL(EDycEntidadesFuncionales.DEV_AUTOMATICAS_IVA, "001", "Ocurrio un error parcial al procesar el lote de devoluciones."),
    AUTOMATICAS_DEVOLUCION_LOTE_FINALIZADOCONERRORES(EDycEntidadesFuncionales.DEV_AUTOMATICAS_IVA, "002", "Se termino de procesar el lote de devoluciones, con errores."),
    AUTOMATICAS_DEVOLUCION_INDIVIDUAL(EDycEntidadesFuncionales.DEV_AUTOMATICAS_IVA, "003", "Ocurrio un error al procesar una devolucion individual, intente mas tarde."),
    AUTOMATICAS_NUMEROCONTROL_GENERAL(EDycEntidadesFuncionales.DEV_AUTOMATICAS_IVA, "004", "Ocurrio un error al generar el numero de control."),
    AUTOMATICAS_CASODEVOLUCION_GENERAR_GENERAL(EDycEntidadesFuncionales.DEV_AUTOMATICAS_IVA, "005", "Ocurrio un error al generar el caso de devolucion automatica de iva."),
    AUTOMATICAS_INTEGRAREXPEDIENTE_GENERAR_GENERAL(EDycEntidadesFuncionales.DEV_AUTOMATICAS_IVA, "006", "Ocurrio un error al generar la integracion de expediente."),
    
    // BD - MORSA
    BD_MORSA_GENERAL(EDycEntidadesFuncionales.DEV_AUTOMATICAS_IVA, "100", "Ocurrio un error al consultar los datos de MORSA, intente mas tarde."),
    
    // RFC AMPLIADO (IDC)
    RFCAMPLIADO_GENERAL(EDycEntidadesFuncionales.RFC_AMPLIADO, "200", "Ocurrio un error al consumir RFC Ampliado, intente mas tarde."),
    RFCAMPLIADO_RESPUESTA_NORECIBIDA(EDycEntidadesFuncionales.RFC_AMPLIADO, "201", "No se recibieron datos para el RFC indicado."),
    RFCAMPLIADO_RESPUESTA_UBICACION_NORECIBIDA(EDycEntidadesFuncionales.RFC_AMPLIADO, "202", "No se recibieron los datos de UBICACION para el RFC indicado."),
    RFCAMPLIADO_RESPUESTA_UBICACION_CLAVESIR_NORECIBIDA(EDycEntidadesFuncionales.RFC_AMPLIADO, "203", "No se recibio la CLAVE_SIR para el RFC indicado."),

    // BUZON NOTIFICACIONES
    BUZONNOTIF_GENERAL(EDycEntidadesFuncionales.BUZON_NOTIFICACIONES, "300", "Ocurrio un error al consumir Buzon Notificaciones, intente mas tarde."),
    BUZONNOTIF_MEDIO_NOTIENE(EDycEntidadesFuncionales.BUZON_NOTIFICACIONES, "301", "El usuario no cuenta con Medio de comunicacion."),

    // BUZON TRIBUTARIO
    BUZONTRIB_GENERAL(EDycEntidadesFuncionales.BUZON_TRIBUTARIO, "400", "Ocurrio un error al consumir Buzon Tributario, intente mas tarde."),
    BUZONTRIB_RESPUESTA_NORECIBIDA(EDycEntidadesFuncionales.BUZON_TRIBUTARIO, "401", "No se recibio respuesta del Servicio Buzon Tributario."),
    BUZONTRIB_RESPUESTA_RECIBIDA_NOEXITOSA(EDycEntidadesFuncionales.BUZON_TRIBUTARIO, "402", "La respuesta del Servicio Buzon Tributario no fue exitosa."),

    // BD - DYC
    BD_DYC_GENERAL(EDycEntidadesFuncionales.BD_DYC, "500", "Ocurrio un error al consultar DYC, intente mas tarde."),
    // BD - DYC - SALDOICEP
    BD_DYC_SALDOICEP_GENERAL(EDycEntidadesFuncionales.BD_DYC, "520", "Ocurrio un error al consultar SALDOICEP, intente mas tarde."),
    BD_DYC_SALDOICEP_QUERY_ICEP(EDycEntidadesFuncionales.BD_DYC, "521", "Ocurrio un error al consultar el SaldoIcep, intente mas tarde."),
    BD_DYC_SALDOICEP_INSERT_ICEP(EDycEntidadesFuncionales.BD_DYC, "522", "Ocurrio un error al insertar el ICEP, intente mas tarde."),
    // BD - DYC - CONTROLSALDOS - MOVSALDO
    BD_DYC_CONTROLSALDOS_INSERT_MOVSALDO(EDycEntidadesFuncionales.BD_DYC, "440", "Ocurrio un error al realizar la afectacion a control de saldos (MovSaldo)."),
    ;
        
    private final EDycEntidadesFuncionales entidadFuncional;
    private final String codigo;
    private final String descripcion;

    private EDycAutomaticasIvaCodigoError(EDycEntidadesFuncionales entidadFuncional, String codigo, String descripcion) {
        this.entidadFuncional = entidadFuncional;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    
    /**
     * @return the entidadFuncional
     */
    @Override
    public String getEntidadFuncional() {
        return entidadFuncional.getEntidad();
    }

    /**
     * @return the codigo
     */
    @Override
    public String getCodigo() {
        return ConstantesDyC.LOG_CODIGOERROR_PREFIX + entidadFuncional.getCodigo() + codigo;
    }

    /**
     * @return the mensaje
     */
    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
