package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumEstadoCompensacion {
    REGISTRADA(2);
    
    private int id;
     
    private EnumEstadoCompensacion(int id){
        this.id = id;
    }


    public int getId(){
        return id;
    }

}
