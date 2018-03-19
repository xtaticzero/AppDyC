/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoPeriodoService;
import mx.gob.sat.siat.dyc.dao.periodo.DyccTipoPeriodoDAO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * @author Paola Rivera
 */
@Service(value = "dyccTipoPeriodoService")
public class DyccTipoPeriodoServiceImpl implements DyccTipoPeriodoService {

    private static final Logger LOG = Logger.getLogger(DyccTipoPeriodoServiceImpl.class.getName());

    @Autowired
    private DyccTipoPeriodoDAO dyccTipoPeriodoDAO;

    @Override
    public List<DyccTipoPeriodoDTO> obtieneTipoPeriodo() {
        LOG.debug("### Entra a método obtieneTipoPeriodo DyccTipoPeriodoServiceImpl");
        return this.dyccTipoPeriodoDAO.obtieneTipoPeriodo();
    }

    public ParamOutputTO<DyccTipoPeriodoDTO> tipoPeriodo(int tramite) throws SIATException {
        LOG.debug("### Entra a método TipoPeriodo DyccTipoPeriodoServiceImpl");
        try {
            return new ParamOutputTO<DyccTipoPeriodoDTO>(this.dyccTipoPeriodoDAO.tipoPeriodo(String.valueOf(tramite)));
        } catch (DataAccessException e) {
            throw new SIATException(e);
        }
    }

    @Override
    public ParamOutputTO<CatalogoTO> findTipoPeriodo(int idPeriodo) throws SIATException {
        LOG.info("INIT findTipoPeriodo " + idPeriodo);
        return new ParamOutputTO<CatalogoTO>(this.dyccTipoPeriodoDAO.findTipoPeriodo(idPeriodo));
    }
    
    @Override
    public DyccTipoPeriodoDTO encontrar(String id) {
        return dyccTipoPeriodoDAO.encontrar(id);
    }
}
