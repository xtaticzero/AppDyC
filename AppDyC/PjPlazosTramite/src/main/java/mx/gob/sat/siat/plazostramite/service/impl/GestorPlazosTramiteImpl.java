/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.plazostramite.service.impl;

import mx.gob.sat.siat.plazostramite.service.GestorPlazosTramite;
import mx.gob.sat.siat.plazostramite.service.PlazosTramiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service(value = "gestorPlazosTramite")
public class GestorPlazosTramiteImpl implements GestorPlazosTramite {

    @Autowired
    private PlazosTramiteService plazosTramiteService;

    @Override
    public void iniciaProceso() {
        plazosTramiteService.actualizarTramitesPorPlazos();
    }
}
