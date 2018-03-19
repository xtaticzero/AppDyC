/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.controlsaldos.service;

import java.util.HashMap;
import java.util.Map;
import mx.gob.sat.mat.dyc.controlsaldos.service.impl.ProcesarDeclaracionCompBussinesDelTestImpl;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.DycControlSaldosObtenerIcepDTO;
import mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author GAER8674
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/applicationContext-DycService-ProcesarDeclaracionTst.xml")
public class ProcesarDeclaracionCompBussinesDelTest {
    private final Logger log = Logger.getLogger(ProcesarDeclaracionCompBussinesDelTest.class.getName());
    
    @Autowired
    ProcesarDeclaracionCompBussinesDelTestImpl procesarDeclaracionCompBussinesDelTest;
    
    @Ignore
    @Test
    public void obtenerIcep(){
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            DycControlSaldosObtenerIcepDTO dycControlSaldosObtenerIcepDTO = new DycControlSaldosObtenerIcepDTO();
            dycControlSaldosObtenerIcepDTO.setRfc("BMC990112UU7");
            dycControlSaldosObtenerIcepDTO.setIdConcepto(101);
            dycControlSaldosObtenerIcepDTO.setIdEjercicio(2014);
            dycControlSaldosObtenerIcepDTO.setIdPeriodo(35);
            dycControlSaldosObtenerIcepDTO.setIdOrigenSaldo(1);
            DyctSaldoIcepDTO rs = procesarDeclaracionCompBussinesDelTest.obtenerIcep(dycControlSaldosObtenerIcepDTO);
            
            if(rs!=null){
                log.info(rs.getFechaBaseCalculo());
                log.info(rs.getActRemanente());
                log.info(rs.getRemanente());
            }
        } catch (DycServiceExcepcion e) {
            log.error("", e);
            Assert.assertTrue(false);
        }
    }
}
