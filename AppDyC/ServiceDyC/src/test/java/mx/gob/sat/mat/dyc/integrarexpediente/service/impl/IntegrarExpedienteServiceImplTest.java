/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.integrarexpediente.service.impl;

import mx.gob.sat.mat.dyc.integrarexpediente.service.IntegrarExpedienteService;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
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
@ContextConfiguration("file:src/test/resources/applicationContext-DycService-IntegrarExpedienteTest.xml")
public class IntegrarExpedienteServiceImplTest {
    private final Logger log = Logger.getLogger(IntegrarExpedienteServiceImplTest.class.getName());
    
    @Autowired
    IntegrarExpedienteService integrarExpedienteServiceImpl;
    
    @Autowired
    private DycpSolicitudDAO daoSolicitud;
            
    
    @Ignore
    @Test
    public void integrarExp(){
        try {
            DycpSolicitudDTO solicitud = daoSolicitud.encontrar("DC111500000109");
            solicitud.setNumControl("DC111500000109");
            PersonaDTO personaRetenedor = null;//new PersonaDTO();
            DiotDTO diot = null;//new DiotDTO();
            SpConsultarAltexDTO altex = null;//new SpConsultarAltexDTO();
            integrarExpedienteServiceImpl.crearExpediente(solicitud, personaRetenedor, diot, altex);
            
        } catch (Exception e) {
            log.error("", e);
            Assert.assertTrue(false);
        }
    }
}
