package mx.gob.sat.siat.dyc.controlsaldos.util;


public enum EnumCompBigDecimal {
     MENOR(-1)
    ,IGUAL(0)
    ,MAYOR(1);


    
    private int id; 
    
    private EnumCompBigDecimal(int id){
        this.id = id;
    }
    
    public int getId(){
       return id;
    }   
    
    
}
