/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


package mx.gob.sat.siat.dyc.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.banco.DyctCuentaBancoDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.service.DyctCuentaBancoService;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.RemplazaCuentaClabeVO;
import mx.gob.sat.siat.dyc.vo.ReqCuentaClabeVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * Servicio cuenta banco
 * @author JEFG
 *
 */
@Service("dyctCuentaBancoService")
public class DyctCuentaBancoServiceImpl implements DyctCuentaBancoService {

    private static Logger logger = Logger.getLogger(DyctCuentaBancoServiceImpl.class.getName());
    @Autowired
    private DyctCuentaBancoDAO dyctCuentaBancoDAO;
    @Autowired
    private DyctResolucionDAO dyctResolucionDAO;


    public DyctCuentaBancoServiceImpl() {
        super();
    }

    @Override
    public void createCuentaBanco(DyctCuentaBancoDTO input) throws SIATException {
        dyctCuentaBancoDAO.createCuentaBanco(input);
    }

    /**
     *Obtiene todas la cuentas clabes de un contribuyente por rfc y que no sean vaildas por tesofe
     * --LADP[Luis ALberto Dominguez Palomino]
     * @param rfc
     * @return
     * @throws SIATException
     */
    @Override
    public ParamOutputTO<ReqCuentaClabeVO> getReqCuentasCLABE(String rfc) throws SIATException {
        try {
            return new ParamOutputTO<ReqCuentaClabeVO>(dyctCuentaBancoDAO.getReqCuentasCLABE(rfc));
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new SIATException(e);
        }

    }

    /**
     *Actualiza la cuenta clabe y el campo de pago enviado de la resolucion por numero de control
     *  --LADP[Luis ALberto Dominguez Palomino]
     * @param dyctCuentaBancoDTO
     */
    @Override
    public void actualizaCuenta(DyctCuentaBancoDTO dyctCuentaBancoDTO) {
        try {
            dyctCuentaBancoDAO.update(dyctCuentaBancoDTO);
            dyctResolucionDAO.actualizarPagoEnviado(ConstantesDyCNumerico.VALOR_0,
                                                    dyctCuentaBancoDTO.getDycpSolicitudDTO().getNumControl());
        } catch (SIATException siatE) {
            logger.info(siatE.getMessage());
        }
    }

    /**
     *Remplaza la cuenta clabe y el campo de pago enviado de la resolucion por numero de control
     *  --LADP[Luis ALberto Dominguez Palomino]
     * @param dyctCuentaBancoDTO
     */
    @Override
    public void remplazaCuenta(DyctCuentaBancoDTO dyctCuentaBancoDTO) {
        try {
            dyctCuentaBancoDAO.remplaza(dyctCuentaBancoDTO);
            dyctResolucionDAO.actualizarPagoEnviado(ConstantesDyCNumerico.VALOR_0,
                                                    dyctCuentaBancoDTO.getDycpSolicitudDTO().getNumControl());
        } catch (SIATException siatE) {
            logger.info(siatE.getMessage());
        }
    }

    /**
     *Consulta por numero de control la cuenta clabe
     *  --LADP[Luis ALberto Dominguez Palomino]
     * @param dyctCuentaBancoDTO
     * @return
     */
    @Override
    public DyctCuentaBancoDTO consultaXNumCtrl(DyctCuentaBancoDTO dyctCuentaBancoDTO) {
        DyctCuentaBancoDTO cuentaBancoDTO = null;
        try {
            cuentaBancoDTO = dyctCuentaBancoDAO.consultaXNumCtrl(dyctCuentaBancoDTO);
        } catch (SIATException siatE) {
            logger.info(siatE.getMessage());
        }
        return cuentaBancoDTO;
    }

    /**
     *Obtiene la cuenta clabe actual por numero de control
     *  --LADP[Luis ALberto Dominguez Palomino]
     * @param numControl
     * @return
     */
    @Override
    public RemplazaCuentaClabeVO consultaNumCtrlTramite(String numControl) {
        RemplazaCuentaClabeVO cuentaClabeVO = null;
        try {
            cuentaClabeVO = dyctCuentaBancoDAO.consultaNumCtrlTramite(numControl);
        } catch (SIATException siatE) {
            logger.info(siatE.getMessage());
        }
        return cuentaClabeVO;
    }

    /**
     *Obtiene todos los registros de cuenta clabes por rfc del contribuyente y que tesofe haya aprobado esa cuenta
     *  --LADP[Luis ALberto Dominguez Palomino]
     * @param rfc
     * @return
     */
    @Override
    public List<RemplazaCuentaClabeVO> consultaCuentaClabeXPagoTesofe(String rfc) {
        List<RemplazaCuentaClabeVO> cuenta = new ArrayList<RemplazaCuentaClabeVO>();
        try {
            cuenta = dyctCuentaBancoDAO.consultaCuentaClabeXPagoTesofe(rfc);
        } catch (SIATException e) {
            logger.info(e.getMessage());
        }
        return cuenta;
    }

    public static void setLogger(Logger logger) {
        DyctCuentaBancoServiceImpl.logger = logger;
    }

    public static Logger getLogger() {
        return logger;
    }

    public void setDyctCuentaBancoDAO(DyctCuentaBancoDAO dyctCuentaBancoDAO) {
        this.dyctCuentaBancoDAO = dyctCuentaBancoDAO;
    }

    public DyctCuentaBancoDAO getDyctCuentaBancoDAO() {
        return dyctCuentaBancoDAO;
    }

    public void setDyctResolucionDAO(DyctResolucionDAO dyctResolucionDAO) {
        this.dyctResolucionDAO = dyctResolucionDAO;
    }

    public DyctResolucionDAO getDyctResolucionDAO() {
        return dyctResolucionDAO;
    }

}
