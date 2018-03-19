package mx.gob.sat.siat.dyc.catalogo.service.impl;

import mx.gob.sat.siat.dyc.catalogo.dao.ParametrosDAO;
import mx.gob.sat.siat.dyc.catalogo.service.ParametrosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value="parametrosService")
public class ParametrosServiceImpl implements ParametrosService {
    
    @Autowired
    private ParametrosDAO parametrosDAO;
    
    public ParametrosServiceImpl() {
        super();
    }
    
    public void setParametrosDAO(ParametrosDAO parametrosDAO) {
        this.parametrosDAO = parametrosDAO;
    }

    public ParametrosDAO getParametrosDAO() {
        return parametrosDAO;
    }
    

    @Override
    public Integer getObtenerMonto(int parametro) {
        return parametrosDAO.getObtenerMonto(parametro);
    }

    @Override
    public Integer getConcepto(int concepto) {
        String result = parametrosDAO.getConcepto(concepto);
        return Integer.parseInt(result);
    }


    @Override
    public Integer getImpuesto(int impuesto) {
        String result = parametrosDAO.getImpuesto(impuesto);
        return Integer.parseInt(result);
    }


    @Override
    public Integer getEjercicio(int ejercicio) {
        String result = parametrosDAO.getEjercicio(ejercicio);
        return Integer.parseInt(result);
    }

    @Override
    public Integer getPeriodo(int periodo) {
        String result = parametrosDAO.getPeriodo(periodo);
        return Integer.parseInt(result);
    }

    @Override
    public Integer getObtenMontoCompensacion(int parametro) {
        return parametrosDAO.getObtenerMonto(parametro);
    }
}
