package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumErrorRegistroRes {
        ERROR01("NegRegistroRes_01", "El ICEP solicitado no es valido"),
        ERROR02("NegRegistroRes_02", "El ICEP no esta registrado en Saldos DYC"),
        ERROR03("NegRegistroRes_03", "Las reglas para calculo de actualizaciones de Cantidad a Favor son responsabilidad de DYC"),
        ERROR04("NegRegistroRes_04", "El monto solicitado es mayor al saldo a favor"),
        ERROR05("NegRegistroRes_05", "El monto autorizado no puede ser mayor al saldo de la ultima declaracion"),
        ERROR06("NegRegistroRes_06", "Error al calcular las actualizaciones, al ejecutar el servicio externo"),
        ERROR07("NegRegistroRes_07", "No existen declaraciones registradas en Saldos DYC"),
        ERROR08("NegRegistroRes_08", "Error al calcular actualizacion de devoluciòn con el servicio externo"),
        ERROR09("NegRegistroRes_09", "Error al calcular actualizacion de compensaciòn con el servicio externo"),
        ERROR10("NegRegistroRes_10", "Error al calcular actualizacion de compensaciòn con el servicio externo"),
        ERROR11("NegRegistroRes_11", "El ICEP Origen no esta registrado en el Saldos DYC"),
        ERROR12("NegRegistroRes_12", "El ICEP Destino no esta registrado en el Saldos DYC"),
        ERROR13("NegRegistroRes_13", "No existe una solicitud asociada al ICEP, con el numero de control especificado "),
        ERROR14("NegRegistroRes_14", "Las reglas para calculo de actualizaciones de Cantidad a Favor son responsabilidad de DYC, Saldos DYC no hace calculo de actualizaciones para Cantidad a Favor"),
        ERROR15("NegRegistroRes_15", "La afectaciòn enviada no es vàlida"),
        ERROR16("NegRegistroRes_16", "No se encuentra el Numero de Control registrado"),
        ERROR17("NegRegistroRes_17", "La Fecha de Resolucion es mayor a la fecha actual"),
        ERROR18("NegRegistroRes_18", "La Fecha del movimiento es requerida para el detalle ICEP"),
        ERROR19("NegRegistroRes_19", "El importe del movimiento es requerido para el detalle ICEP"),
        ERROR20("NegRegistroRes_20", "La tipo de movimiento es requerido para el detalle ICEP"),
        ERROR21("NegRegistroRes_21", "La tipo de afectaciòn es requerido para el detalle ICEP"),
        ERROR22("NegRegistroRes_21", "El numero de control debe ser proporcionado"),
        ERROR23("NegRegistroRes_23", "Las reglas para calculo de actualizaciones de Cantidad a Favor son responsabilidad de DYC, Saldos DYC no hace calculo de actualizaciones para Cantidad a Favor"),
        ERROR24("NegRegistroRes_24", "No existe uan resolucion con el nùmero de control especificado"),
        ERROR25("NegRegistroRes_25", "La resoluciòn no tiene un estado de aprobada"),
        ERROR26("NegRegistroRes_26", "No esta registrado un Importe Autorizado en la resoluciòn");
    
    private String descripcion;
    private String id;
    
    private EnumErrorRegistroRes(String id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }
    
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public String getId(){
        return id;
    }
}
