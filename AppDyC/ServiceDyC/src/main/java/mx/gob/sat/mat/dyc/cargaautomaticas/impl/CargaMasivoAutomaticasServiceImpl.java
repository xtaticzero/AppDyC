/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.cargaautomaticas.impl;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.dyc.cargaautomaticas.CargaMasivoAutomaticasService;
import mx.gob.sat.siat.dyc.dao.cargaautomaticas.CargaMasivoAutomaticasDAO;
import mx.gob.sat.siat.dyc.vo.CargaMasivaAutomaticasRegistroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service("cargaMasivoAutomaticasService")
public class CargaMasivoAutomaticasServiceImpl implements CargaMasivoAutomaticasService{
    @Autowired
    private CargaMasivoAutomaticasDAO cargaMasivoAutomaticasDAO;
    @Override
        public  List<CargaMasivaAutomaticasRegistroVO> obtenerRegistrosAutomaticos(Date fechaCargaInicio,Date fechaCargaFin){
           return  cargaMasivoAutomaticasDAO.obtenerRegistrosAutomaticos(fechaCargaInicio, fechaCargaFin);
         }
        
    /**
     *
     * @return
     */
    @Override
          public  List<CargaMasivaAutomaticasRegistroVO> obtenerUltimosRegistros(){
           return  cargaMasivoAutomaticasDAO.obtenerUltimosRegistros();
         }
         
    @Override
     @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public  void updateCargaMasiva(CargaMasivaAutomaticasRegistroVO archivo){
             cargaMasivoAutomaticasDAO.updateCargaMasiva(archivo);
       }
        
          @Override
     @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public  void crearRegistroMasivas(String rfc){
           Integer count= cargaMasivoAutomaticasDAO.consultaRegistroNoprocesado();
           if( count!=null && count>0 ){
               cargaMasivoAutomaticasDAO.updateCargaMasivaBorrado(rfc);
           }else{
               cargaMasivoAutomaticasDAO.insertarCargaMasiva(rfc);
           }
       }
}
