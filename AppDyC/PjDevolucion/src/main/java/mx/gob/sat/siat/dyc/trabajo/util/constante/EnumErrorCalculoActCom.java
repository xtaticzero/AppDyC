package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumErrorCalculoActCom {
    ERROR01("NegCalcularActResCom_01", "El ICEP Origen debe ser proporcionado"),
    ERROR02("NegCalcularActResCom_02", "La Compensaciòn Històrica debe ser proporcionada"),
    ERROR03("NegCalcularActResCom_03", "El ICEP Origen no esta registrado en saldos DYC"),
    ERROR04("NegCalcularActResCom_04", "El Numero de Control debe ser proporcionado"),
    ERROR05("NegCalcularActResCom_05", "La compensaciòn no tiene un estado de Registrada");
    
    private String descripcion;
    private String id;
    
    private EnumErrorCalculoActCom(String id, String descripcion){
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
