package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumErrorDeclaracionIcep {
    ERROR01("EnumErrorDeclaracionIcep_01", "El ICEP solicitado ya existe"),
    ERROR02("EnumErrorDeclaracionIcep_02", "El ICEP solicitado no existe"),
    ERROR03("EnumErrorDeclaracionIcep_03", "Debe proporcioanr el parametro de Ejercicio"),
    ERROR04("EnumErrorDeclaracionIcep_04", "Debe proporcioanr el parametro de Periodo"),
    ERROR05("EnumErrorDeclaracionIcep_05", "Debe proporcioanr el parametro de Concepto"),
    ERROR06("EnumErrorDeclaracionIcep_06", "Debe proporcioanr el parametro de RFC"),
    ERROR07("EnumErrorDeclaracionIcep_07", "Debe proporcioanr el parametro de Origen del Saldo");
   
    private String descripcion;
    private String id;
    
    private EnumErrorDeclaracionIcep(String id, String descripcion){
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
