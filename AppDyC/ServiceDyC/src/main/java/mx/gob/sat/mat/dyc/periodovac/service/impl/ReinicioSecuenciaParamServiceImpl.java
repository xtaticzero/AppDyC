/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.periodovac.service.impl;

import mx.gob.sat.mat.dyc.periodovac.ReinicioSecuenciaParamService;
import mx.gob.sat.siat.dyc.dao.periodovacacional.DyctReinicioSecParamDAO;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DyctReinicioSecParamDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Orlando Tepoz Z.
 */
@Service(value = "reinicioSecuenciaParamService")
public class ReinicioSecuenciaParamServiceImpl implements ReinicioSecuenciaParamService {

    @Autowired
    private DyctReinicioSecParamDAO dyctReinicioSecParamDAO;

    /**
     *
     * @return @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    public DyctReinicioSecParamDTO buscarFechaReinicioSecuenciaActiva() throws SIATException {
      
        return dyctReinicioSecParamDAO.buscarFechaReinicioActivo();
    }

    /**
     *
     * @param dTO
     * @throws SIATException
     */
    @Override
    public void inactivarFechaReinicioSecuencia(DyctReinicioSecParamDTO dTO) throws SIATException {

        dyctReinicioSecParamDAO.inactivarFechaReinicioSecuencia(dTO);
    }

    /**
     *
     * @param dTO
     * @return
     * @throws SIATException
     */
    @Override
    public DyctReinicioSecParamDTO insertarNuevaFechaReinicioSecuencia(DyctReinicioSecParamDTO dTO) throws SIATException {

        return dyctReinicioSecParamDAO.insertarNuevaFechaReinicioSecuencia(dTO);
    }
}
