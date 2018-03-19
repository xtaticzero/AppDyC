/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.util.constante;

/**
 *
 * @author GAER8674
 */
public enum DyctDevAutoIvaConstantes {
    NUMERO_LOTE("NUMLOTE"),
    NOMBRE_CONTRIBUYENTE("NOMBRE"),
    RFC("RFC"),
    IMPUESTO("IMPUESTO"),
    CONCEPTO("CONCEPTO"),
    EJERCICIO("EJERCICIO"),
    PERIODO("PERIODO"),
    NOMBRE_BANCO("NOMBBANCO"),
    NUMERO_CUENTA_CLABE("NUMCLABE"),
    TIPO_DECLARACION("TIPODECLARA"),
    FECHAHORA_PRESENTACION("FECHAPRESENT"),
    NUMERO_OPERACION("NUMOPERACION"),
    IMPORTE_SALDO_FAVOR("IMPORTESALDOF"),
    FECHAHORA_PROCESAMIENTO("FECHAPROCMORSA"),
    MARCA_RESULTADO("MARCRESULTADO"),
    MOTIVO_RECHAZO("MOTRECHAZO"),
    ;
    
    private String columna;

    private DyctDevAutoIvaConstantes(String columna) {
        this.columna = columna;
    }

    /**
     * @return the columna
     */
    public String getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(String columna) {
        this.columna = columna;
    }
}
