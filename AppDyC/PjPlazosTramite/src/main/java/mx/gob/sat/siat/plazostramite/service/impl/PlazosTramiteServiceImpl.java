/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.plazostramite.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.plazostramite.dao.PlazosTramiteDao;
import mx.gob.sat.siat.plazostramite.service.PlazosTramiteService;
import mx.gob.sat.siat.plazostramite.vo.PlazoTramiteVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service(value = "plazosTramiteService")
public class PlazosTramiteServiceImpl implements PlazosTramiteService {

    private static final Logger LOG = Logger.getLogger(PlazosTramiteServiceImpl.class);
    @Autowired
    private DiaHabilService diaHabilService;
    @Autowired
    private PlazosTramiteDao plazosTramiteDao;
    

    public void actualizarTramitesPorPlazos() {
        List<PlazoTramiteVO>  listaTramites = buscarTramitesPlazos();
        for (PlazoTramiteVO plazoTram : listaTramites) {
            try {
                reglaPlazoReq(plazoTram);
            } catch (Exception e) {
                LOG.error("ocurrio un error en el proceso solicitudes:numcontrol:" + plazoTram.getNumControl() + " numdoc:" + plazoTram.getNumControlDoc() + " f. noti"
                        + plazoTram.getFechaNotificacion() + " f.solv:" + plazoTram.getFechaSolventacion(), e);
            }
        }
        listaTramites = buscarTramitesCompensacionesPlazos();
        for (PlazoTramiteVO plazoTram : listaTramites) {
            try {
                reglaPlazoReqComp(plazoTram);
            } catch (Exception e) {
                LOG.error("ocurrio un error en el proceso compensaciones:numcontrol:" + plazoTram.getNumControl() + " numdoc:" + plazoTram.getNumControlDoc() + " f. noti"
                        + plazoTram.getFechaNotificacion() + " f.solv:" + plazoTram.getFechaSolventacion(), e);
            }
        }
    }

    private void reglaPlazoReq(PlazoTramiteVO plazoTram) {

        Date fechaResultado = null;
        Date fechaHoy = null;
        boolean fechaMayor = false;

        if (null != plazoTram.getFechaNotificacion() &&  null == plazoTram.getFechaSolventacion()) {

            if (plazoTram.getNumRequerimiento() == ConstantesDyCNumerico.VALOR_1) {
                fechaResultado
                        = getDiaHabilService().buscarDiaFederalRecaudacion(plazoTram.getFechaNotificacion(),
                                ConstantesDyCNumerico.VALOR_21);
            } else if (plazoTram.getNumRequerimiento() == ConstantesDyCNumerico.VALOR_2) {
                fechaResultado
                        = getDiaHabilService().buscarDiaFederalRecaudacion(plazoTram.getFechaNotificacion(),
                                ConstantesDyCNumerico.VALOR_11);
            }

            fechaHoy = new Date();
            if (null != fechaResultado && fechaHoy.after(fechaResultado)) {
                fechaMayor = Boolean.TRUE;
            }

        }

        if (fechaMayor) {
            actualizarEstados(plazoTram.getNumControl(), plazoTram.getNumControlDoc());
        }
    }

    private void reglaPlazoReqComp(PlazoTramiteVO plazoTram) {

        Date fechaResultado = null;
        Date fechaHoy = null;
        boolean fechaMayor = false;

        if (null != plazoTram.getFechaNotificacion() && null == plazoTram.getFechaSolventacion()) {

            if (plazoTram.getNumRequerimiento() == ConstantesDyCNumerico.VALOR_1 ) {
                fechaResultado
                        = getDiaHabilService().buscarDiaFederalRecaudacion(plazoTram.getFechaNotificacion(),
                                ConstantesDyCNumerico.VALOR_16);
            }

            fechaHoy = new Date();
            if (null != fechaResultado && fechaHoy.after(fechaResultado)) {
                fechaMayor = Boolean.TRUE;
            }

        }

        if (fechaMayor) {
            actualizarEstadosComp(plazoTram.getNumControl(), plazoTram.getNumControlDoc());
        }
    }

    private List<PlazoTramiteVO> buscarTramitesPlazos() {

        List<PlazoTramiteVO> lSolicitudAdministrarSolVO = new ArrayList<PlazoTramiteVO>();
        try {
            lSolicitudAdministrarSolVO = plazosTramiteDao.buscarTramitesPlazos();
        } catch (DataAccessException e) {
            LOG.error(e.getMessage(),e);
        }
        return lSolicitudAdministrarSolVO;

    }

    private List<PlazoTramiteVO> buscarTramitesCompensacionesPlazos() {
        List<PlazoTramiteVO> lSolicitudAdministrarSolVO = new ArrayList<PlazoTramiteVO>();
        try {
            lSolicitudAdministrarSolVO = plazosTramiteDao.buscarTramitesCompensacionesPlazos();
        } catch (DataAccessException e) {
            LOG.error(e.getMessage(),e);
        }
        return lSolicitudAdministrarSolVO;

    }

    @Override
    public void actualizarEstados(String numControl, String numControlDoc) {

        try {
            plazosTramiteDao.actualizarEstados(numControl, numControlDoc);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage(),e);
        }

    }

    @Override
    public void actualizarEstadosComp(String numControl, String numControlDoc) {

        try {
            plazosTramiteDao.actualizarEstadosComp(numControl, numControlDoc);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage(),e);
        }

    }

    public DiaHabilService getDiaHabilService() {
        return diaHabilService;
    }

    public void setDiaHabilService(DiaHabilService diaHabilService) {
        this.diaHabilService = diaHabilService;
    }

}
