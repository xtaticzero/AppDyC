package mx.gob.sat.siat.dyc.vistas.service.impl;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vistas.dao.SegbMovimientoDAO;
import mx.gob.sat.siat.dyc.vistas.service.SegbMovimientoService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Implementacion service para la tabla SEGB_MOVIMIENTO.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * @date Abril 16, 2014
 */
@Service(value = "segbMovimientoService")
public class SegbMovimientoServiceImpl implements SegbMovimientoService {
    private static final Logger LOG = Logger.getLogger("loggerDyC");

    @Autowired
    private SegbMovimientoDAO segbMovimientoDAO;


    public SegbMovimientoServiceImpl() {
        super();
    }

    @Override
    public void agregaIdentificador(mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO segbMovimiento) throws SIATException {
        try {
            this.segbMovimientoDAO.agregaIdentificador(segbMovimiento);
        } catch (SIATException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage());
            throw new SIATException(dae);
        }
    }

}
