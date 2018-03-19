package mx.gob.sat.mat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import mx.gob.sat.mat.dyc.controlsaldos.service.AfectarSaldosXDevolucionesService;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

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
 * @author AdrianAguilar
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/applicationContext-DycService-AfectarSaldosXDevolucionesTest.xml")
public class AfectarSaldosXDevolucionesServiceImplTest
{
 
    private final Logger log = Logger.getLogger(AfectarSaldosXDevolucionesServiceImplTest.class.getName());
    
    @Autowired
    AfectarSaldosXDevolucionesService afectarSaldosXDevolucionesService;
    
    
    @Ignore
    @Test
    public void afectarSaldo(){
        try {
            int idTramite = 500;
            DycpSolicitudDTO solicitud = new DycpSolicitudDTO();
            solicitud.setNumControl("DC111600000146");
            solicitud.setDyctSaldoIcepDTO(new DyctSaldoIcepDTO(null, new DyccConceptoDTO(null, null, null, 104, null), new DyccEjercicioDTO(null, null, 2014, Integer.SIZE), new DyccPeriodoDTO(null, null, null, 1, null, null), idTramite, BigDecimal.ZERO, null));
            solicitud.setDycpServicioDTO(new DycpServicioDTO("DC111600000146"));
            solicitud.getDycpServicioDTO().setDyctDeclaracionList(new ArrayList<DyctDeclaracionDTO>());
            solicitud.getDycpServicioDTO().getDyctDeclaracionList().add(new DyctDeclaracionDTO());
            solicitud.getDycpServicioDTO().setRfcContribuyente("BFO720726E58");
            solicitud.getDycpServicioDTO().setDycaOrigenTramiteDTO(new DycaOrigenTramiteDTO(new DycaServOrigenDTO(),new DyccTipoTramiteDTO()));
            solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().setDyccConceptoDTO(new DyccConceptoDTO("Descripcion", new Date(), new Date(), 104, new DyccImpuestoDTO()));
            afectarSaldosXDevolucionesService.afectarXRegistro(solicitud);
        } catch (Exception e) {
            log.error("", e);
            Assert.assertTrue(false);
        }
    }
}