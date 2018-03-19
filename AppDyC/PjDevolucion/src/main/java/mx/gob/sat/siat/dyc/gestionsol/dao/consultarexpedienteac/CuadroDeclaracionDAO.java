package mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroDeclaracionBean;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface CuadroDeclaracionDAO {
    List<CuadroDeclaracionBean> buscarCuadroDec (Integer idDetalleaviso);
}
