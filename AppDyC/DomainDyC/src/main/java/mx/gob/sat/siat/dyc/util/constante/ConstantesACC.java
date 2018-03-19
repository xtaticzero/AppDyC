package mx.gob.sat.siat.dyc.util.constante;

import mx.gob.sat.siat.dyc.util.common.ItemComboBean;

public final class ConstantesACC {

    private ConstantesACC() {

    }

    public static final Integer IDGRUPOOPERACION_PISTASAUDITORIA = 51;
    public static final Integer IDGRUPOOPERACION_EMITIRLIQUIDACION = 57;
    // TODO: validar si este es el valor correcto para pistas Auditoria Compensaciones
    public static final Integer MOVIMIENTO_PISTASA_APROBAR_RESOL = 774;
    public static final Integer MOVIMIENTO_PISTASA_EMITE_LIQUIDACION = 773;
    public static final Integer MOVIMIENTO_PISTASA_EMITE_RESOLUCION = 772;
    //Pistas Auditoria Control de Saldos
    public static final Integer IDGRUPOOPERACION_REGISTRA_INFO_ICEP = 91;
    public static final Integer IDGRUPOOPERACION_CONSULTA_MOV_ICEP = 92;
    public static final Integer MOVIMIENTO_PISTASA_REGISTRA_ICEP = 549;
    public static final Integer MOVIMIENTO_PISTASA_AGREGA_DECLARACION = 775;
    public static final Integer MOVIMIENTO_PISTASA_ELIMINA_DECLARACION = 776;
    public static final Integer MOVIMIENTO_PISTASA_AGREGA_COMPENSACION = 777;
    public static final Integer MOVIMIENTO_PISTASA_ELIMINA_COMPENSACION = 778;
    public static final Integer MOVIMIENTO_PISTASA_AGREGA_DEVOLUCION = 779;
    public static final Integer MOVIMIENTO_PISTASA_ELIMINA_DEVOLUCION = 780;
    public static final Integer PISTAS_IDMENSAJE_CREAR_ICEP = 2;
    public static final Integer PISTAS_IDMENSAJE_CREAR_DECLARACION = 16;
    public static final Integer PISTAS_IDMENSAJE_CREAR_COMPENSACION = 17;
    public static final Integer PISTAS_IDMENSAJE_CREAR_DEVOLUCION = 18;
    public static final Integer PISTAS_IDMENSAJE_ELIMINAR_DECLARACION = 19;
    public static final Integer PISTAS_IDMENSAJE_ELIMINAR_COMPENSACION = 20;
    public static final Integer PISTAS_IDMENSAJE_ELIMINAR_DEVOLUCION = 21;


    public static final int CLAVE_APROBAR_RESOLUCION = 1;
    public static final int CLAVE_NO_APROBAR_RESOLUCION = 2;

    public static final ItemComboBean OPCION_DICT_REGISTRAR_NOTA =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_1, "Registrar nota");
    public static final ItemComboBean OPCION_DICT_EMITIR_REQ =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_2, "Emitir requerimiento");
    public static final ItemComboBean OPCION_DICT_EMITIR_REQ_CONF_PROV =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_3,
                          "Emitir requerimiento para confirmación de operaciones con proveedores");
    public static final ItemComboBean OPCION_DICT_EMITIR_CARTA_PRES_CONT =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_4, "Emitir carta solicitando la presencia del contribuyente");
    public static final ItemComboBean OPCION_DICT_EMITIR_CARTA_INV =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_5, "Emitir carta invitación");
    public static final ItemComboBean OPCION_DICT_CARG_DESC_PAPS_TBAJO =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_6, "Cargar/descargar papeles de trabajo");
    public static final ItemComboBean OPCION_DICT_ENV_COMP_FISC =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_7, "Enviar caso de compensación a fiscalización");
    public static final ItemComboBean OPCION_DICT_EMITIR_RESOL_COMP =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_8, "Emitir resolución caso de compensación");
    public static final ItemComboBean OPCION_APROB_RESOL =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_9, "Aprobar resolución");
    public static final ItemComboBean OPCION_NO_APROB_RESOL =
        new ItemComboBean(ConstantesDyCNumerico.VALOR_10, "No aprobar resolución");

    public static final Integer PLANTILLA_REQ_PRESC_CONTTE = 77;
    public static final Integer PLANTILLA_CARTA_INVITACION_GC = 81;
    public static final Integer PLANTILLA_CARTA_INVITACION = 76;
    public static final int PLANTILLA_CARTA_INVITACION_HIDRO = 136;
    public static final Integer PLANTILLA_REQ_OPER_PROVS = 75;
    public static final Integer PLANTILLA_REQ_INFO = 74;
    public static final Integer PLANTILLA_REQ_INFO_GRAN_CONTTE = 79;
    public static final Integer PLANTILLA_LIQUIDACION = 78;
    public static final Integer PLANTILLA_LIQUIDACION_GRAN_CONTTE = 83;

    public static final Integer NUM_QUERY_CASOS_COMPENSACION = 74;

    public static final Integer TIPODOC_REQ_PRESC_CONTTE = 4;
    public static final Integer TIPODOC_CARTA_INVITACION = 7;

    public static final Integer HORA_VENCIMIENTO = 21;
    public static final Integer MINUTO_VENCIMIENTO = 59;
    public static final Integer SEGUNDO_VENCIMIENTO = 0;

    public static final Integer HORA_VIGENCIA_CARTA = 23;
    public static final Integer MINUTO_VIGENCIA_CARTA = 59;
    public static final Integer SEGUNDO_VIGENCIA_CARTA = 59;

    public static final Integer ID_ENERO = 1;
    public static final Integer ID_FEBRERO = 2;
    public static final Integer ID_MARZO = 3;
    public static final Integer ID_ABRIL = 4;
    public static final Integer ID_MAYO = 5;
    public static final Integer ID_JUNIO = 6;
    public static final Integer ID_JULIO = 7;
    public static final Integer ID_AGOSTO = 8;
    public static final Integer ID_SEPTIEMBRE = 9;
    public static final Integer ID_OCTUBRE = 10;
    public static final Integer ID_NOVIEMBRE = 11;
    public static final Integer ID_DICIEMBRE = 12;

    public static final Integer PISTAS_IDMENSAJE_APROB_VALIDA = 3;
    public static final Integer PISTAS_IDMENSAJE_RECHAZO_APROB = 4;
    
    public static final Integer LONGITUD_MAX_NOMBRE_ARCHIVO = 50;
    public static final Integer LONG_MAX_NOM_ARCHIVO_BORRADO = 42;

    public static final int NUM_ANIOS_ANTERIORES = 15;

    // TODO: Eliminar valor estatico, se agrega temporalmente por cuestiones de Sonar
    public static final Double DUMMY_IMPORTETOTAL = 456d;

    public static final String ESTADOSI = "Si";
    public static final String ESTADONO = "No";
    public static final String NUMEROCONTROL = "numControl";
    
    public static final String NOMBRE_ARCHIVO = "nombreArchivo";


}
