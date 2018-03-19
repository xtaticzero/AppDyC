package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumErrorCalculoSaldoICEP {
    ERROR01("NegCalculoSaldoICEP_01", "El ICEP solicitado no es valido"),
    ERROR02("NegCalculoSaldoICEP_02", "El ICEP no esta registrado en Saldos DYC"),
    ERROR03("NegCalculoSaldoICEP_03", "Las reglas para calculo de actualizaciones de Cantidad a Favor son responsabilidad de DYC"),
    ERROR04("NegCalculoSaldoICEP_04", "El monto solicitado es mayor al saldo a favor"),
    ERROR05("NegCalculoSaldoICEP_05", "El monto autorizado no puede ser mayor al saldo de la ultima declaracion"),
    ERROR06("NegCalculoSaldoICEP_06", "Error al calcular las actualizaciones, al ejecutar el servicio externo"),
    ERROR07("NegCalculoSaldoICEP_07", "No existen declaraciones registradas en Saldos DYC"),
    ERROR08("NegCalculoSaldoICEP_08", "Error al calcular actualizacion de devoluciòn con el servicio externo"),
    ERROR09("NegCalculoSaldoICEP_09", "Error al calcular actualizacion de compensaciòn con el servicio externo"),
    ERROR10("NegCalculoSaldoICEP_10", "Error al calcular actualizacion de compensaciòn con el servicio externo"),
    ERROR11("NegCalculoSaldoICEP_11", "El ICEP Origen no esta registrado en el Saldos DYC"),
    ERROR12("NegCalculoSaldoICEP_12", "El ICEP Destino no esta registrado en el Saldos DYC"),
    ERROR13("NegCalculoSaldoICEP_13", "No existe una Solicitud Saldo ICEP con el numero de control especificado "),
    ERROR14("NegCalculoSaldoICEP_14", "Las reglas para calculo de actualizaciones de Cantidad a Favor son responsabilidad de DYC, Saldos DYC no hace calculo de actualizaciones para Cantidad a Favor"),
    ERROR15("NegCalculoSaldoICEP_15", "La afectaciòn enviada no es vàlida"),
    ERROR16("NegCalculoSaldoICEP_16", "No se encuentra el Numero de Control registrado"),
    ERROR17("NegCalculoSaldoICEP_17", "La Fecha de Resolucion es mayor a la fecha actual");
    
    private String descripcion;
    private String id;
    
    private EnumErrorCalculoSaldoICEP(String id, String descripcion){
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
