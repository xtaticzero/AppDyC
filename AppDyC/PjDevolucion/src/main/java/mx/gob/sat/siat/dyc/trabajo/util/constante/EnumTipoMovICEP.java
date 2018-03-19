package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumTipoMovICEP {
     IMPORTE_AUTORIZADO          (1)
    ,IMPORTE_NEGADO              (2)
    ,COMPENSACION_OFICIO_CARGO   (7)
    ,SALDO_IMPROCEDENTE          (8)
    ,COMPENSACION_IMPROCEDENTE_ABONO   (9)
    ,ALTA_SALDO                        (10)
    ,ALTA_IMPORTE_COMPENSADO           (11)
    ,COMPENSACION_OFICIO_ABONO         (12)
    ,COMPENSACION_IMPROCEDENTE_CARGO   (13)
    ,ALTA_SALDO_DESISTIDO              (14)
    ,ALTA_IMPORTE_COMPENSADO_DESISTIDO (15)
    ,REVERSA_DE_COMPENSACION_ABONO     (16)
    ,REVERSA_DE_COMPENSACION_CARGO     (17)
    ,CANCELACION_REMANENTE             (22)                                            
    ,CANCELACION_DEVOLUCION            (25); 

     
     private int id;
      
     private EnumTipoMovICEP(int id){
         this.id = id;
     }


     public int getId(){
         return id;
     }

                        
}
