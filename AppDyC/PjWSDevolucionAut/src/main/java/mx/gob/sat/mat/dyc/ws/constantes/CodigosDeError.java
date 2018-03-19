/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.constantes;

/**
 *
 * @author CMEM
 */
public enum CodigosDeError {

    MOTIVO_TRAMITE_ASOCIADO("1", "TRAMITE ASOCIADO A LA SOLICITUD AUTOMATICA"),
    MOTIVO_ERROR_TRAMITE("2", "OCURRIO UN ERROR AL VALIDAR O PROCESAR EL TRAMITE"),
    MOTIVO_EXISTE_TRAMITE_CON_NUMERO_DECLARACION("3", "EXISTE UN TRÁMITE REGISTRADO CON EL NÚMERO DE DECLARACIÓN RECIBIDA"),
    MOTIVO_FA08_IMPORTE_AUTORIZADO_ES_IGUAL_O_MENOR_A_CERO("4", "IMPORTE AUTORIZADO ES IGUAL O MENOR A CERO"),
    MOTIVO_ERROR_PROBLEMA_REGISTO_O_DYC("5", "EXISTE UN PROBLEMA AL INTENTAR REGISTRAR EL TRÁMITE EN MAT DYC : "),
    MOTIVO_FA09_IMPORTE_REMANENTE_ES_IGUAL_O_MENOR_A_CERO("6", "IMPORTE REMANENTE ES IGUAL O MENOR A CERO"),
    MOTIVO_ERROR_PROBLEMA_APLICAR_CARGO("7", "EXISTE UN PROBLEMA AL APLICAR EL CARGO DEL TRÁMITE EN MAT DYC");

    private String codigo;
    private String descripcion;

    private CodigosDeError(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
