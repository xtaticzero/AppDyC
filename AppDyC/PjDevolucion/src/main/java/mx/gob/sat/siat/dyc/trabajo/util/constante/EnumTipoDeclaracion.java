package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumTipoDeclaracion {
     NORMAL(1)
    ,COMPLEMENTARIA(2);
     
     private int id;
     
     private  EnumTipoDeclaracion(int id){
         this.id = id;
     }
        
     public int getId(){
         return id;
     }
             
     
}
