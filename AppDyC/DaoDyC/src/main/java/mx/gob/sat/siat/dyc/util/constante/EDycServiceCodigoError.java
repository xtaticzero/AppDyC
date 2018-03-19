/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.util.constante;

import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

/**
 *
 * @author GAER8674
 */
public enum EDycServiceCodigoError implements IDycCodigoError{
    //BD DYC
    BD_DYC_QUERY_GENERAL(EDycEntidadesFuncionales.BD_DYC, "100", "Ocurrio un error al consultar DYC, intente mas tarde."),
    
    //BD DYC - SaldoIcep
    BD_DYC_SALDOICEP_GENERAL(EDycEntidadesFuncionales.BD_DYC, "120", "Ocurrio un error al consultar SALDOICEP, intente mas tarde."),
    BD_DYC_SALDOICEP_NOENCONTRADO(EDycEntidadesFuncionales.BD_DYC, "Err2", "No se encuentra el ICEP registrado."),
    
    //BD DYC - Servicio
    BD_DYC_SERVICIO_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "140", "Ocurrio un error al insertar en SERVICIO, intente mas tarde."),
    
    //BD DYC - Solicitud
    BD_DYC_SOLICITUD_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "150", "Ocurrio un error al insertar en SOLICITUD, intente mas tarde."),

    //BD DYC - Contribuyente
    BD_DYC_CONTRIBUYENTE_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "160", "Ocurrio un error al insertar en CONTRIBUYENTE, intente mas tarde."),
    BD_DYC_CONTRIBUYENTE_DATOSCONTRIB_XML(EDycEntidadesFuncionales.BD_DYC, "161", "No se pudo conformar el xml para datos del contribuyente."),

    //BD DYC - Declaracion
    BD_DYC_DECLARACION_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "170", "Ocurrio un error al insertar en DECLARACION, intente mas tarde."),

    //BD DYC - Cuenta banco - Archivo
    BD_DYC_CUENTABANCO_ARCHIVO_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "180", "Ocurrio un error al insertar el archivo de validacion de Cuenta CLABE, intente mas tarde."),
    
    //BD DYC - Cuenta banco
    BD_DYC_CUENTABANCO_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "190", "Ocurrio un error al insertar en CUENTA BANCO, intente mas tarde."),

    //BD DYC - Resolucion
    BD_DYC_RESOLUCION_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "200", "Ocurrio un error al insertar en RESOLUCION, intente mas tarde."),
    ;
        
    private final EDycEntidadesFuncionales entidadFuncional;
    private final String codigo;
    private final String descripcion;

    private EDycServiceCodigoError(EDycEntidadesFuncionales entidadFuncional, String codigo, String descripcion) {
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
        return ConstantesDyC1.LOG_CODIGOERROR_PREFIX + entidadFuncional.getCodigo() + codigo;
    }

    /**
     * @return the mensaje
     */
    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
