/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.matenercalendariodictaminador.MantenerCalendarioIndividualDTO;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccCalDictaMinDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccCalDictaMinService;
import mx.gob.sat.siat.dyc.domain.DyccCalDictaminDTO;
import mx.gob.sat.siat.dyc.generico.util.common.SessionHandler;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPistasAuditoria;
import mx.gob.sat.siat.dyc.util.constante.ConstantesReasignarSolicitudManual;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Alfredo Ramirez
 * @since 14/08/2013
 *
 */
@Service(value = "dyccCalDictaMinService")
public class DyccCalDictaMinServiceImpl implements DyccCalDictaMinService {

    private Logger log = Logger.getLogger(DyccCalDictaMinServiceImpl.class);

    @Autowired(required = true)
    private SessionHandler sessionHandler;

    @Autowired(required = true)
    private SegbMovimientosDAO segbMovimientosDAO;

    private AccesoUsr accesoUsr;

    @Autowired
    private DyccCalDictaMinDAO dyccCalDictaMinDAO;

    @Override
    public List<MantenerCalendarioIndividualDTO> consultarDictaminador(long numEmpDictaminador) {
        List<MantenerCalendarioIndividualDTO> dyccCalDictaMinArray = new ArrayList<MantenerCalendarioIndividualDTO>();
        try {
            dyccCalDictaMinArray = dyccCalDictaMinDAO.consultarDictaminador(numEmpDictaminador);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return dyccCalDictaMinArray;
    }

    @Override
    public List<DyccCalDictaminDTO> existenInhabiles(DyccCalDictaminDTO dyccCalDictaminDto) {
        List<DyccCalDictaminDTO> dyccCalDictaminArray = new ArrayList<DyccCalDictaminDTO>();
        try {
            dyccCalDictaminArray = dyccCalDictaMinDAO.existenInhabiles(dyccCalDictaminDto);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return dyccCalDictaminArray;
    }

    @Override
    public void adicionarDiaInhabil(DyccCalDictaminDTO dyccCalDictaminDto) {
        try {
            dyccCalDictaMinDAO.adicionarDiaInhabil(dyccCalDictaminDto);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void modificarDiaIndividual(DyccCalDictaminDTO dyccCalDictaminDto) {
        try {
            dyccCalDictaMinDAO.modificarDiaIndividual(dyccCalDictaminDto);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void eliminarDiaIndividual(DyccCalDictaminDTO dyccCalDictaminDto) {
        try {
            dyccCalDictaMinDAO.eliminarDiaIndividual(dyccCalDictaminDto);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<MantenerCalendarioIndividualDTO> consultarCalendarioGeneral(int idunidadadmva) {
        List<MantenerCalendarioIndividualDTO> dyccDictaminadoresGenArray =
            new ArrayList<MantenerCalendarioIndividualDTO>();
        try {
            dyccDictaminadoresGenArray = dyccCalDictaMinDAO.consultarCalendarioGeneral(idunidadadmva);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return dyccDictaminadoresGenArray;
    }

    @Override
    public void eliminarDiaGeneral(MantenerCalendarioIndividualDTO mantenerCalendarioIndividualDto) {
        try {
            dyccCalDictaMinDAO.eliminarDiaGeneral(mantenerCalendarioIndividualDto);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
    }

    @Transactional(readOnly = false)
    @Override
    public int validarReasiganacion(Integer numeEmpleado) {

        boolean esReasignable = false;
        boolean esInhabilEnElDictamin = false;
        boolean esElegibleASuRegreso = false;
        int resultadoValidacion = ConstantesDyCNumerico.VALOR_0;

        try {

            esInhabilEnElDictamin = dyccCalDictaMinDAO.validarDiaActual(numeEmpleado);

            if (!esInhabilEnElDictamin) {
                esReasignable = dyccCalDictaMinDAO.valida4DiasInhabilesContinuos(numeEmpleado);
                if (!esReasignable) {
                    resultadoValidacion = ConstantesReasignarSolicitudManual.NO_ES_REASIGANABLE_CASO_1;
                } else {
                    resultadoValidacion = ConstantesReasignarSolicitudManual.ES_REASIGANABLE;
                }
            } else {

                esElegibleASuRegreso = dyccCalDictaMinDAO.validarRegresoDictaminador(numeEmpleado);

                if (esElegibleASuRegreso) {
                    resultadoValidacion = ConstantesReasignarSolicitudManual.ES_REASIGANABLE;
                } else {
                    resultadoValidacion = ConstantesReasignarSolicitudManual.NO_ES_REASIGANABLE_CASO_2;
                }
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }

        return resultadoValidacion;
    }

    @Transactional
    public void registraPAuditoria(int movimiento, String observacion) {
        accesoUsr = this.getSessionHandler().obtenerSession();
        try {
            SegbMovimientoDTO movimientoDTO =
                MovimientoUtil.addMovimiento(this.getSessionHandler().obtenerSessionID(), ConstantesPistasAuditoria.CLAVE_SYS,
                                             Procesos.DYC00002, new Date(), new Date(), movimiento, observacion);
            BigInteger folio = segbMovimientosDAO.insert(movimientoDTO);
            log.info("\n>>> REGISTRA PISTA EN BITACORA " + folio);
        } catch (DaoException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        } catch (Exception ex) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex.getMessage());
        }
    }

    @Transactional
    public void registraAcceso(AccesoUsr accesoUsr) {
        this.setAccesoUsr(accesoUsr);
    }

    public void setDyccCalDictaMinDAO(DyccCalDictaMinDAO dyccCalDictaMinDAO) {
        this.dyccCalDictaMinDAO = dyccCalDictaMinDAO;
    }

    public DyccCalDictaMinDAO getDyccCalDictaMinDAO() {
        return dyccCalDictaMinDAO;
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setSegbMovimientosDAO(SegbMovimientosDAO segbMovimientosDAO) {
        this.segbMovimientosDAO = segbMovimientosDAO;
    }

    public SegbMovimientosDAO getSegbMovimientosDAO() {
        return segbMovimientosDAO;
    }

    public void testMejora(Integer numeEmpleado) {
        dyccCalDictaMinDAO.valida4DiasInhabilesContinuos(numeEmpleado);
    }
}
