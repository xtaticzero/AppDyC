package mx.gob.sat.siat.dyc.trabajo.util.constantenum;



public enum EnumEstatusResol {
     EN_APROBACION (1)
    ,APROBADA      (2)
    ,NO_APROBADA   (3);

    private int id; 
    
    private EnumEstatusResol(int id){
        this.id = id;
    }
    
    public int getId(){
       return id;
    }   
}
