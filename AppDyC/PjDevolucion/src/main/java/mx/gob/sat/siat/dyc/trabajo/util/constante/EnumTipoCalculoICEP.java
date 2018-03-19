package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumTipoCalculoICEP {
     NINGUNO(0)   
    ,ACTUALIZACION(1)
    ,DEFLACTACION (2);
     
    private int id; 
    
    private EnumTipoCalculoICEP(int id){
        this.id = id;
    }


    public int getId() {
        return id;
    }
}
