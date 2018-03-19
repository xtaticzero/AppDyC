package mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroBean;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface CuadroBeanDAO {
    List<CuadroBean> selecXNumControl(String numControl);
}
