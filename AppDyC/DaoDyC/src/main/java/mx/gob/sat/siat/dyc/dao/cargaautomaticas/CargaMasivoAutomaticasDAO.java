/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.cargaautomaticas;

import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.dyc.vo.CargaMasivaAutomaticasRegistroVO;


/**
 *
 * @author root
 */
public interface CargaMasivoAutomaticasDAO {
         List<CargaMasivaAutomaticasRegistroVO> obtenerRegistrosAutomaticos(Date fechaCargaInicio,Date fechaCargaFin);
         List<CargaMasivaAutomaticasRegistroVO> obtenerUltimosRegistros();
         void updateCargaMasiva(CargaMasivaAutomaticasRegistroVO archivo);
         void updateCargaMasivaBorrado(String rfc);
         Integer consultaRegistroNoprocesado();
         void insertarCargaMasiva(String rfc);
}
