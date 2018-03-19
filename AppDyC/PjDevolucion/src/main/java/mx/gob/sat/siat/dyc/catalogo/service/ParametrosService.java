package mx.gob.sat.siat.dyc.catalogo.service;

public interface ParametrosService {
    
    Integer getObtenerMonto(int parametro);
    
    Integer getConcepto(int concepto);
    
    Integer getImpuesto(int impuesto);
    
    Integer getEjercicio(int ejercicio);
    
    Integer getPeriodo(int periodo);
    
    Integer getObtenMontoCompensacion(int parametro);
}
