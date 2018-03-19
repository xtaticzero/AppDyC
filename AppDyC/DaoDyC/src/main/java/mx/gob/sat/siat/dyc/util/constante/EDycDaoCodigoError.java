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
public enum EDycDaoCodigoError implements IDycCodigoError{
    
    BD_DYC_QUERY_GENERAL(EDycEntidadesFuncionales.BD_DYC, "100", "Ocurrio un error al consultar DYC, intente mas tarde."),
    BD_DYC_QUERY_SECUENCIA(EDycEntidadesFuncionales.BD_DYC, "101", "Ocurrio un error al consultar una secuencia."),
    BD_DYC_SECUENCIA_REQUIEREREINICIAR(EDycEntidadesFuncionales.BD_DYC, "102", "La secuencia supero el valor maximo, requiere reiniciar."),
    
    //BD DYC - SaldoIcep
    BD_DYC_SALDOICEP_QUERY_GENERAL(EDycEntidadesFuncionales.BD_DYC, "120", "Ocurrio un error al consultar SALDOICEP, intente mas tarde."),
    //ECU_ConsultarSaldoDelICEP.Err2 (121)
    BD_DYC_SALDOICEP_NOENCONTRADO(EDycEntidadesFuncionales.BD_DYC, "Err2", "No se encuentra el ICEP registrado."),
    
    //BD DYC - Solicitud
    BD_DYC_SOLICITUD_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "150", "Ocurrio un error al insertar en SOLICITUD, intente mas tarde."),
    
    //BD DYC - Contribuyente
    BD_DYC_CONTRIBUYENTE_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "160", "Ocurrio un error al insertar en CONTRIBUYENTE, intente mas tarde."),

    //BD DYC - Declaracion
    BD_DYC_DECLARACION_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "170", "Ocurrio un error al insertar en DECLARACION, intente mas tarde."),

    //BD DYC - Cuenta banco
    BD_DYC_CUENTABANCO_INSERT_GENERAL(EDycEntidadesFuncionales.BD_DYC, "180", "Ocurrio un error al insertar en CUENTA BANCO, intente mas tarde."),
    ;
        
    private final EDycEntidadesFuncionales entidadFuncional;
    private final String codigo;
    private final String descripcion;

    private EDycDaoCodigoError(EDycEntidadesFuncionales entidadFuncional, String codigo, String descripcion) {
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
