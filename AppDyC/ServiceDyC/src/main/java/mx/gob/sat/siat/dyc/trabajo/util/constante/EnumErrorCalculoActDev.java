package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumErrorCalculoActDev {
    ERROR01("NegCalcularActResDev_EX01", "El ICEP solicitado no es valido"),
    ERROR02("NegCalcularActResDev_EX02", "El ICEP no esta registrado en Saldos DYC"),
    ERROR03("NegCalcularActResDev_EX03", "Las reglas para calculo de actualizaciones de Cantidad a Favor son responsabilidad de DYC"),
    ERROR04("NegCalcularActResDev_EX04", "El monto solicitado es mayor al saldo remanente. El saldo no puede ser autorizado"),
    ERROR05("NegCalcularActResDev_EX05", "El monto autorizado no puede ser mayor al saldo de la ùltima declaraciòn"),
    ERROR06("NegCalcularActResDev_06", "Error al calcular las actualizaciones, al ejecutar el servicio externo"),
    ERROR07("NegCalcularActResDev_07", "No existen declaraciones registradas en Saldos DYC"),
    ERROR08("NegCalcularActResDev_08", "Error al calcular actualización de devolución, favor de intentarlo mas tarde"),
    ERROR09("NegCalcularActResDev_09", "Error al calcular actualización de devolución con el servicio externo"),
    ERROR10("NegCalcularActResDev_10", "Error al calcular actualización de devoluciòn con el servicio externo, el objeto devuelto es nulo "),
    ERROR11("NegCalcularActResDev_11", "El ICEP Origen no esta registrado en el Saldos DYC"),
    ERROR12("NegCalcularActResDev_12", "El ICEP Destino no esta registrado en el Saldos DYC"),
    ERROR13("NegCalcularActResDev_13", "No existe una Solicitud Saldo ICEP con el nùmero de control especificado "),
    ERROR14("NegCalcularActResDev_14", "Las reglas para calculo de actualizaciones de Cantidad a Favor son responsabilidad de DYC, Saldos DYC no hace calculo de actualizaciones para Cantidad a Favor"),
    ERROR15("NegCalcularActResDev_15", "La afectación enviada no es vàlida"),
    ERROR16("NegCalcularActResDev_16", "No se encuentra el Nùmero de Control especificado"),
    ERROR17("NegCalcularActResDev_17", "La Fecha de Resoluciòn es mayor a la fecha actual"),
    ERROR18("NegCalcularActResDev_18", "Debe proporcionar una Fecha de Resolución"),
    ERROR19("NegCalcularActResDev_19", "El monto autorizado debe ser mayor a cero "),
    ERROR20("NegCalcularActResDev_20", "El nùmero de control debe ser proporcionado");
    
    private String descripcion;
    private String id;
    
    private EnumErrorCalculoActDev(String id, String descripcion){
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
