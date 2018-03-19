package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumTipoSaldoICEP {
     SAF(1)
    ,CAF(2);
     
     private int id; 
     
     private EnumTipoSaldoICEP(int id){
         this.id = id;
     }
     
     public int getId(){
        return id;
     }    
}
