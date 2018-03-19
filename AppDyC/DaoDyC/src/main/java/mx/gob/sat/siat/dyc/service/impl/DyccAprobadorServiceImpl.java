package mx.gob.sat.siat.dyc.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctFacultadesDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.service.DyccAprobadorService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "dyccAprobadorService")
public class DyccAprobadorServiceImpl implements DyccAprobadorService {

    private static final Logger LOG = Logger.getLogger(DyccAprobadorServiceImpl.class);

    @Autowired
    private DyccAprobadorDAO daoAprobador;

    @Autowired
    private DyctDocumentoDAO daoDocumento;

    @Autowired
    private DyctFacultadesDAO daoFacultades;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Override
    public List<DyccAprobadorDTO> mostrarAprobadorConTrabajo(Integer claveAdm, Integer centroCostoOp, Integer opcion) {
        return daoAprobador.consultaAprobadorConCargaTrabajo(claveAdm, centroCostoOp, opcion);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = SIATException.class)
    public void actualizarCargaTrabajoAprob(Integer nuempleadoAprob, Integer nuevoEmpleado) throws SIATException {

        List<DyctDocumentoDTO> listaDocumentos = daoDocumento.buscaDocumentosAprobador(nuempleadoAprob);
        for (DyctDocumentoDTO doc : listaDocumentos) {
            daoDocumento.actualizarNumEmpleadoAprob(nuevoEmpleado, doc.getNumControlDoc());
        }

        List<DyctFacultadesDTO> listaFacultades = daoFacultades.buscaDocumentosFacultades(nuempleadoAprob);
        for (DyctFacultadesDTO facultad : listaFacultades) {
            daoFacultades.actualizarNumEmpleadoAprob(nuevoEmpleado, facultad.getIdFacultades());
        }

        List<DycpCompensacionDTO> listaCompensacion = daoCompensacion.buscaCompensacionAprobador(nuempleadoAprob);
        for (DycpCompensacionDTO compe : listaCompensacion) {
            daoCompensacion.actualizarNumEmpleadoAprob(nuevoEmpleado, compe.getDycpServicioDTO().getNumControl());
        }

    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = SIATException.class)
    public void actualizarAprobadorDocumento(Integer numeroEmpleadoAprobador,
                                             String numeroControlDocumento) throws SIATException {
        daoDocumento.actualizarNumEmpleadoAprob(numeroEmpleadoAprobador, numeroControlDocumento);
    }

    @Override
    public List<DictaminadorSolBean> consultarTramitesAsociadosAprobador(DyccAprobadorDTO aprobador) {

        LOG.info("Cargando la lista de los tramites del aprobador -  service");

        List<DictaminadorSolBean> listaTramites = new ArrayList<DictaminadorSolBean>();
        try {

            listaTramites = daoAprobador.consultarTramitesAsociadosAprobador(aprobador.getNumEmpleado());
        } catch (DataAccessException e) {
            LOG.info(e.getMessage());
        }

        return listaTramites;
    }

    @Override
    public List<DictaminadorSolBean> consultarTramitesAsociadosAprobadorPorFecha(DyccAprobadorDTO aprobador, Date fechaInicio,
                                                                         Date fechaFin) {
        LOG.info("Cargando la lista de los tramites del aprobador filtrado por fecha -  service");

        List<DictaminadorSolBean> listaTramites = new ArrayList<DictaminadorSolBean>();
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFin);
            cal.set(Calendar.HOUR, ConstantesDyCNumerico.VALOR_23);
            cal.set(Calendar.MINUTE, ConstantesDyCNumerico.VALOR_59);
            cal.set(Calendar.SECOND, ConstantesDyCNumerico.VALOR_59);
            cal.set(Calendar.MILLISECOND, ConstantesDyCNumerico.VALOR_999);

            listaTramites =
                    daoAprobador.consultarTramitesAsociadosAprobadorPorFecha(aprobador.getNumEmpleado(), fechaInicio, cal.getTime());
        } catch (DataAccessException e) {
            LOG.info(e.getMessage());
        }

        return listaTramites;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = SIATException.class)
    public void reasignacionAprobador(List<DictaminadorSolBean> listaSeleccionSolicitudes,
                                      DyccAprobadorDTO aprobadorDestinoReasignacion,Integer empleadoResponsable) throws SIATException {

        for (DictaminadorSolBean tramite : listaSeleccionSolicitudes) {
            daoDocumento.reasignacionManualAprobador(tramite.getNumControl(),
                                                     aprobadorDestinoReasignacion.getNumEmpleado(),empleadoResponsable);
        }

    }
}
