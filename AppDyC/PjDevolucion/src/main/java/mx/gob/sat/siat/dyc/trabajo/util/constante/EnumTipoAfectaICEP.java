package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumTipoAfectaICEP {
     ABONO(1)
    ,CARGO(2);
     
     private int id; 
     
     private EnumTipoAfectaICEP(int id){
         this.id = id;
     }
     
     public int getId(){
        return id;
     }    
}