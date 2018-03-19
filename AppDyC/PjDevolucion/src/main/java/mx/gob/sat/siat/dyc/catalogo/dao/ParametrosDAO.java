package mx.gob.sat.siat.dyc.catalogo.dao;


public interface ParametrosDAO {
    
    Integer getObtenerMonto(int parametro);
    
    String getConcepto(int concepto);
    
    String getImpuesto(int impuesto);
    
    String getEjercicio(int ejercicio);
    
    String getPeriodo(int periodo);
    
    Integer getObtenMontoCompensacion(int parametro);
}
