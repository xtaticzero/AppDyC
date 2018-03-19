package mx.gob.sat.siat.dyc.gestionsol.service.consultarexpedienteac.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.concepto.DyccConceptoDAO;
import mx.gob.sat.siat.dyc.dao.concepto.DyccImpuestoDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.inconsistencia.DycaAvInconsistDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyccOrigenSaldoDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DyccPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DyccTipoPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.saldoicep.DyccSubOrigSaldoDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.DyccTipoDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDyccTipoServicioDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.dao.util.DyccEjercicioDAO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaAvInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroBean;
import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroDeclaracionBean;
import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroFinBean;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.CuadroBeanDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.CuadroDeclaracionDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.ICuadroDAO;
import mx.gob.sat.siat.dyc.gestionsol.service.consultarexpedienteac.IConsultasExpedienteService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * @author J. Isaac Carbajal Bernal
 */
@Service(value = "consultasExpedienteService")
public class ConsultasExpedienteServiceImpl implements IConsultasExpedienteService {
    public ConsultasExpedienteServiceImpl() {
        super();
    }

    private Logger log = Logger.getLogger(ConsultasExpedienteServiceImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;


    @Autowired
    private DyccEjercicioDAO dyccEjercicioDAO;
    @Autowired
    private DyccTipoPeriodoDAO dyccTipoPeriodoDAO;
    @Autowired
    private DyccPeriodoDAO dyccPeriodoDAO;
    @Autowired
    private DyccImpuestoDAO dyccImpuestoDAO;
    @Autowired
    private DyccConceptoDAO dyccConceptoDAO;
    @Autowired
    private DyccOrigenSaldoDAO dyccOrigenSaldoDAO;
    @Autowired
    private DyccSubOrigSaldoDAO dyccSubOrigSaldoDAO;
    @Autowired
    private DyccAprobadorDAO dyccAprobadorDAO;
    @Autowired
    private IDyccTipoServicioDAO dyccTipoServicioDAO;
    @Autowired
    private DyccTipoTramiteDAO dyccTipoTramiteDAO;
    @Autowired
    private IDycpCompensacionDAO dycpCompensacionDAO;
    @Autowired
    private DyccDictaminadorDAO dyccDictaminadorDAO;
    @Autowired
    private DyccTipoDeclaracionDAO dyccTipoDeclaracionDAO;
    @Autowired
    private DycaAvInconsistDAO dycaAvInconsistDAO;
    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;
    @Autowired
    private DyctArchivoDAO dyctArchivoDAO;
    @Autowired
    private CuadroBeanDAO cuadroBeanDAO;
    @Autowired
    private CuadroDeclaracionDAO daoCuadroDeclaracion;

    @Autowired
    private ICuadroDAO daoCuadro;


    @Override
    public DyccTipoDeclaraDTO buscarTipoDeclaracion(Integer idTipoDeclaracion) {
        return dyccTipoDeclaracionDAO.encontrar(idTipoDeclaracion);
    }

    @Override
    public DycpCompensacionDTO buscarOrigenSafCc(String numControl){
        DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
        try {
            compensacion = dycpCompensacionDAO.encontrar(numControl);
        } catch (SIATException e) {
            log.info(e.getMessage());
        }
        return compensacion;
    }

    @Override
    public DyccPeriodoDTO buscarPeriodo(Integer idPeriodo) {
        return dyccPeriodoDAO.encontrar(idPeriodo);
    }

    @Override
    public DyccEjercicioDTO buscarEjercicio(Integer idEjercicio) {
        return dyccEjercicioDAO.encontrar(idEjercicio);
    }

    @Override
    public DycpCompensacionDTO buscarCompensacion(String numControl) throws SIATException {
        return dycpCompensacionDAO.encontrar(numControl);
    }

    @Override
    public DyccTipoPeriodoDTO buscarTipoPeriodo(String idTipoPeriodo) {
        return dyccTipoPeriodoDAO.encontrar(idTipoPeriodo);
    }


    @Override
    public DyccImpuestoDTO buscarImpuesto(Integer idImpuesto) {
        return dyccImpuestoDAO.encontrar(idImpuesto);
    }

    @Override
    public DyccImpuestoDTO buscarImpuestoXConcepto(DyccConceptoDTO concepto) {
        return dyccImpuestoDAO.seleccionarXconcepto(concepto);
    }

    @Override
    public DyccConceptoDTO buscarConcepto(Integer idConcepto) {
        return dyccConceptoDAO.encontrar(idConcepto);
    }

    @Override
    public DyccSubOrigSaldoDTO buscarSubOrigSaldo(Integer idSubOrigenSaldo) {
        return dyccSubOrigSaldoDAO.encontrar(idSubOrigenSaldo);
    }

    @Override
    public DyccAprobadorDTO buscarAptobador(Integer numEmpleado) {
        return dyccAprobadorDAO.encontrar(numEmpleado);
    }

    @Override
    public DyccTipoServicioDTO buscarTipoServicio(Integer idTipoServicio) {
        return dyccTipoServicioDAO.encontrar(idTipoServicio);
    }

    @Override
    public DyccTipoTramiteDTO buscarTipoTramite(Integer idTipoTramite) {

        DyccTipoTramiteDTO objeto = null;
        try {
            objeto = dyccTipoTramiteDAO.encontrar(idTipoTramite);

        } catch (SIATException e) {
            log.error("buscarTipoTramite(), parametros: idTipoTramite=" + idTipoTramite + ", causa=" + e.getCause());
        }
        return objeto;
    }

    @Override
    public DyccDictaminadorDTO buscarDictaminador(Integer numEmpleado) {
        return dyccDictaminadorDAO.encontrar(numEmpleado);
    }


    @Override
    public DyccOrigenSaldoDTO buscarOrigenSaldo(Integer idOrigenSaldo) {
        return dyccOrigenSaldoDAO.encontrar(idOrigenSaldo);
    }

    @Override
    public List<DyccEjercicioDTO> seleccionarEjercicio() {
        return dyccEjercicioDAO.seleccionar();
    }

    @Override
    public List<DyccConceptoDTO> seleccionarConcepto() {
        return dyccConceptoDAO.seleccionar();
    }


    @Override
    public List<DyccTipoPeriodoDTO> seleccionarTipoPeriodo() {
        return dyccTipoPeriodoDAO.obtieneTipoPeriodo();
    }

    @Override
    public List<DyccPeriodoDTO> seleccionarPeriodoXTipoPeriodo(DyccTipoPeriodoDTO tipoPeriodo) {
        return dyccPeriodoDAO.selecXTipoPeriodo(tipoPeriodo);
    }

    @Override
    public List<DycaAvInconsistDTO> obtenerInconsistencias(String numControl) {
        return dycaAvInconsistDAO.selecXNumControl(numControl);
    }

    @Override
    public List<DyctDocumentoDTO> obtenerDocumentos(String numControl) {
        return dyctDocumentoDAO.selecXNumControl(numControl);
    }

    @Override
    public List<DyctArchivoDTO> obtyenerArchivosAdjuntos(String numControl) {
        return dyctArchivoDAO.buscarDocsAdjuntos(numControl);
    }

    @Override
    public List<CuadroBean> bucarDetCompuesto(String numControl) {
        return cuadroBeanDAO.selecXNumControl(numControl);
    }


    @Override
    public List<CuadroFinBean> buscarCuadroFin(List<CuadroBean> lCuadro) {
        List<CuadroFinBean> lCuadroFin = new ArrayList<CuadroFinBean>();
        if (lCuadro != null) {
            for (int i = 0; i < lCuadro.size(); i++) {
                Integer idDetalleaviso = lCuadro.get(i).getIdDetalleAviso();
                List<CuadroDeclaracionBean> lCuadroDeclaracion = daoCuadroDeclaracion.buscarCuadroDec(idDetalleaviso);
                CuadroFinBean cuadroFin = new CuadroFinBean();
                cuadroFin.setCuadro(lCuadro.get(i));

                if (lCuadroDeclaracion.size() == 2) {
                    cuadroFin.setCuadroDeclaracionN(lCuadroDeclaracion.get(0));
                    cuadroFin.setCuadroDeclaracionC(lCuadroDeclaracion.get(1));
                } else if (lCuadroDeclaracion.size() == 1) {
                    cuadroFin.setCuadroDeclaracionC(null);
                    cuadroFin.setCuadroDeclaracionN(lCuadroDeclaracion.get(0));
                }
                lCuadroFin.add(cuadroFin);
            }
        }
        return lCuadroFin;
    }


    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    public void setDyccEjercicioDAO(DyccEjercicioDAO dyccEjercicioDAO) {
        this.dyccEjercicioDAO = dyccEjercicioDAO;
    }

    public DyccEjercicioDAO getDyccEjercicioDAO() {
        return dyccEjercicioDAO;
    }

    public void setDyccTipoPeriodoDAO(DyccTipoPeriodoDAO dyccTipoPeriodoDAO) {
        this.dyccTipoPeriodoDAO = dyccTipoPeriodoDAO;
    }

    public DyccTipoPeriodoDAO getDyccTipoPeriodoDAO() {
        return dyccTipoPeriodoDAO;
    }

    public void setDyccPeriodoDAO(DyccPeriodoDAO dyccPeriodoDAO) {
        this.dyccPeriodoDAO = dyccPeriodoDAO;
    }

    public DyccPeriodoDAO getDyccPeriodoDAO() {
        return dyccPeriodoDAO;
    }

    public void setDyccImpuestoDAO(DyccImpuestoDAO dyccImpuestoDAO) {
        this.dyccImpuestoDAO = dyccImpuestoDAO;
    }

    public DyccImpuestoDAO getDyccImpuestoDAO() {
        return dyccImpuestoDAO;
    }

    public void setDyccConceptoDAO(DyccConceptoDAO dyccConceptoDAO) {
        this.dyccConceptoDAO = dyccConceptoDAO;
    }

    public DyccConceptoDAO getDyccConceptoDAO() {
        return dyccConceptoDAO;
    }

    public void setDyccOrigenSaldoDAO(DyccOrigenSaldoDAO dyccOrigenSaldoDAO) {
        this.dyccOrigenSaldoDAO = dyccOrigenSaldoDAO;
    }

    public DyccOrigenSaldoDAO getDyccOrigenSaldoDAO() {
        return dyccOrigenSaldoDAO;
    }

    public void setDyccSubOrigSaldoDAO(DyccSubOrigSaldoDAO dyccSubOrigSaldoDAO) {
        this.dyccSubOrigSaldoDAO = dyccSubOrigSaldoDAO;
    }

    public DyccSubOrigSaldoDAO getDyccSubOrigSaldoDAO() {
        return dyccSubOrigSaldoDAO;
    }

    public void setDyccAprobadorDAO(DyccAprobadorDAO dyccAprobadorDAO) {
        this.dyccAprobadorDAO = dyccAprobadorDAO;
    }

    public DyccAprobadorDAO getDyccAprobadorDAO() {
        return dyccAprobadorDAO;
    }

    public void setDyccTipoServicioDAO(IDyccTipoServicioDAO dyccTipoServicioDAO) {
        this.dyccTipoServicioDAO = dyccTipoServicioDAO;
    }

    public IDyccTipoServicioDAO getDyccTipoServicioDAO() {
        return dyccTipoServicioDAO;
    }

    public void setDyccTipoTramiteDAO(DyccTipoTramiteDAO dyccTipoTramiteDAO) {
        this.dyccTipoTramiteDAO = dyccTipoTramiteDAO;
    }

    public DyccTipoTramiteDAO getDyccTipoTramiteDAO() {
        return dyccTipoTramiteDAO;
    }

    public void setDycpCompensacionDAO(IDycpCompensacionDAO dycpCompensacionDAO) {
        this.dycpCompensacionDAO = dycpCompensacionDAO;
    }

    public IDycpCompensacionDAO getDycpCompensacionDAO() {
        return dycpCompensacionDAO;
    }

    public void setDyccDictaminadorDAO(DyccDictaminadorDAO dyccDictaminadorDAO) {
        this.dyccDictaminadorDAO = dyccDictaminadorDAO;
    }

    public DyccDictaminadorDAO getDyccDictaminadorDAO() {
        return dyccDictaminadorDAO;
    }

    public void setDyccTipoDeclaracionDAO(DyccTipoDeclaracionDAO dyccTipoDeclaracionDAO) {
        this.dyccTipoDeclaracionDAO = dyccTipoDeclaracionDAO;
    }

    public DyccTipoDeclaracionDAO getDyccTipoDeclaracionDAO() {
        return dyccTipoDeclaracionDAO;
    }

    public void setDycaAvInconsistDAO(DycaAvInconsistDAO dycaAvInconsistDAO) {
        this.dycaAvInconsistDAO = dycaAvInconsistDAO;
    }

    public DycaAvInconsistDAO getDycaAvInconsistDAO() {
        return dycaAvInconsistDAO;
    }

    public void setDaoCuadro(ICuadroDAO daoCuadro) {
        this.daoCuadro = daoCuadro;
    }

    public ICuadroDAO getDaoCuadro() {
        return daoCuadro;
    }


    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setDyctArchivoDAO(DyctArchivoDAO dyctArchivoDAO) {
        this.dyctArchivoDAO = dyctArchivoDAO;
    }

    public DyctArchivoDAO getDyctArchivoDAO() {
        return dyctArchivoDAO;
    }

    public void setDyctDocumentoDAO(DyctDocumentoDAO dyctDocumentoDAO) {
        this.dyctDocumentoDAO = dyctDocumentoDAO;
    }

    public DyctDocumentoDAO getDyctDocumentoDAO() {
        return dyctDocumentoDAO;
    }
}
