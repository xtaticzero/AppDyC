package mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroBean;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface ICuadroDAO {
    
    List<CuadroBean> obtenerCuadros(String numControlAc);
}
