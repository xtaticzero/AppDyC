package mx.gob.sat.siat.dyc.dao.icep;

import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface  DyccMovIcepDAO {
     
     boolean existe(long idMovimiento, int idAfectacion) throws SIATException;

}
