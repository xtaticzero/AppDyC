package mx.gob.sat.mat.dyc.devolucionaut.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import mx.gob.sat.mat.dyc.controlsaldos.service.ControlSaldoImporte;

import mx.gob.sat.mat.dyc.devolucionaut.service.DyccDevolucionAutService;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.DycConstantesAutomaticasIva;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.CargarSaldoIcepServiceImpl;
import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.devolucionaut.DyccDevolucionAutDAO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpDatosSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;

import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "dyccDevolucionAutService")
public class DyccDevolucionAutServiceImpl implements DyccDevolucionAutService {

    private static final Logger LOG = Logger.getLogger(DyccDevolucionAutServiceImpl.class.getName());

    @Autowired
    private DyccDevolucionAutDAO dyccDevolucionAutDAO;

    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DyctDeclaracionDAO dyctDeclaracionDAO;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private DyctArchivoDAO dyctArchivoDAO;

    @Autowired(required = true)
    private AdmcUnidadAdmvaDAO daoUnidadAdm;

    @Autowired
    private DyccDictaminadorDAO daoDictaminador;

    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;

    @Autowired
    private IDycpServicioDAO dycpServicioDAO;

    @Autowired
    private ControlSaldoImporte controlSaldoImporteService;
    
    @Autowired
    private CargarSaldoIcepServiceImpl serviceCargarSaldoIcep;

    @Override
    public String getNumeroConsecutivoDevolucionAutomatica(String claveSir) {
        String numeroConsecutivo = "";
        try {
            numeroConsecutivo = dyccDevolucionAutDAO.getNumeroConsecutivoDevolucionAutomatica(claveSir);
        } catch (Exception error) {
            LOG.info(error.getMessage());
        }

        return numeroConsecutivo;
    }

    @Override
    public String obtenerRfcAmpliadoClaveSir(IdCInterno rfcAmpliado) {
        StringBuilder claveSirEnmascarada = new StringBuilder("");

        if (rfcAmpliado != null
                && rfcAmpliado.getUbicacion() != null
                && rfcAmpliado.getUbicacion().getCALR() != null) {

            String claveSir = rfcAmpliado.getUbicacion().getCALR();
            claveSirEnmascarada = new StringBuilder();

            claveSirEnmascarada
                    .append(ConstantesTipoRol.MASCARA_CEROS_CLAVESIR.substring(claveSir.length()))
                    .append(claveSirEnmascarada.toString())
                    .append(claveSir);
        }

        return claveSirEnmascarada.toString();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void guardarRegistroSaldoIcep(DyctSaldoIcepDTO registroSaldoIcep) throws SIATException {
        daoSaldoIcep.insert(registroSaldoIcep);
    }

    /**
     * Método para actualizar estado de la Devolucion Automática
     *
     * @param estatus recibe el numero de estatus a actualizar
     * @param folioMATDyC Numero de control
     * @return numero
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     *
     *
     */
    @Override
    public int actualizaEstatus(Integer estatus, String folioMATDyC) throws SIATException {
        int valor = 0;

        try {
            valor = dycpSolicitudDAO.actualizarStatus(estatus, folioMATDyC);
        } catch (SIATException error) {
            LOG.info(error.getMessage());
            throw new SIATException(error);
        }

        return valor;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY,
            rollbackFor = {Exception.class})
    public void guardarDeclaracion(DyctDeclaracionDTO declaracion) throws DycDaoExcepcion {
        dyctDeclaracionDAO.insertar(declaracion);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY,
            rollbackFor = {Exception.class})
    public void guardarMovientoSaldoIcep(DyctMovSaldoDTO movimientoSaldoIcep) throws SIATException {
        daoMovSaldo.insertar(movimientoSaldoIcep);
    }

    @Override
    public boolean bloqueaRegistroSaldoIcep(DyctSaldoIcepDTO saldoIcep) {
        saldoIcep.setBloqueado(ConstantesDyC1.SALDO_ICEP_ESTADO_BLOQUEADO);

        return dyccDevolucionAutDAO.actualizaEstadoBloqueoSaldoIcep(saldoIcep);
    }

    @Override
    public boolean insertarInconsistencia(DycaSolInconsistDTO inconsistencia) throws SIATException {
        try {
            dyccDevolucionAutDAO.insertarInconsistencia(inconsistencia);

            return Boolean.TRUE;
        } catch (DataAccessException error) {
            LOG.info(error.getMessage());
            throw new SIATException(error);
        }
    }

    @Override
    public Integer insertarCuentaBancoArchivoDevAut(String numeroControl, Date fechaPresentacion) {
        Integer idArchivo = null;

        try {
            DyctArchivoDTO dyctArchivoDTO = new DyctArchivoDTO();
            DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();

            dyctArchivoDTO.setDycpServicioDTO(dycpServicioDTO);

            dyctArchivoDTO.setNombreArchivo(DycConstantesAutomaticasIva.DYCTARCHIVO_NOMBREARCHIVO_GENERADOPORSISTEMA);
            dyctArchivoDTO.setUrl(ConstantesDyCURL.URL_DOCUMENTOS);
            dyctArchivoDTO.setDescripcion(DycConstantesAutomaticasIva.DYCTARCHIVO_DESCRIPCION_GENERADOPORSISTEMA);
            dycpServicioDTO.setNumControl(numeroControl);
            dyctArchivoDTO.setFechaRegistro(fechaPresentacion);

            idArchivo = dyctArchivoDAO.insert(dyctArchivoDTO);

        } catch (SIATException error) {
            LOG.info(error.getMessage());
        }

        return idArchivo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int asignarDictaminador(DycpSolicitudDTO solicitud) throws SQLException {

        return asignarDictaminadorSolicitudDevolucion(solicitud);
    }

    private int asignarDictaminadorSolicitudDevolucion(DycpSolicitudDTO solicitud) throws SQLException {

        String datosRNFD35;
        AdmcUnidadAdmvaDTO cbzUnidad = new AdmcUnidadAdmvaDTO();
        cbzUnidad.setClaveSir(solicitud.getDycpServicioDTO().getClaveAdm());
        cbzUnidad = daoUnidadAdm.consultarUnidadAdmvaList(cbzUnidad).get(ConstantesDyCNumerico.VALOR_0);

        Integer competencia = identificaCompetencia(solicitud.getDycpServicioDTO().getClaveAdm());
        switch (competencia) {
            case ConstantesIds.COMPETENCIA_AGGC:
            case ConstantesIds.COMPETENCIA_AGACE:
            case ConstantesIds.COMPETENCIA_AGH:
                return asignacionDictaminadoresRNFDC29(solicitud, cbzUnidad);
            default:
                /*AGACE*/
                datosRNFD35 = dyccDevolucionAutDAO.consultaReglaRNFDC35AGAFF(solicitud.getDycpServicioDTO().getRfcContribuyente());

                if (StringUtils.isNotBlank(datosRNFD35)) {

                    return validaAdmContribuyente(datosRNFD35, solicitud, cbzUnidad);
                }

                return asignacionDictaminadoresRNFDC29(solicitud, cbzUnidad);
        }
    }

    public static Integer identificaCompetencia(int claveAdm) {

        switch (claveAdm) {
            case ConstantesDyCNumerico.VALOR_90:
            case ConstantesDyCNumerico.VALOR_91:
            case ConstantesDyCNumerico.VALOR_97:
                return ConstantesIds.COMPETENCIA_AGGC;
            case ConstantesDyCNumerico.VALOR_80:
                return ConstantesIds.COMPETENCIA_AGACE;
            case ConstantesDyCNumerico.VALOR_81:
            case ConstantesDyCNumerico.VALOR_82:
                return ConstantesIds.COMPETENCIA_AGH;
        }

        return ConstantesIds.COMPETENCIA_AGGAF;
    }

    private Integer asignacionDictaminadoresRNFDC29(DycpSolicitudDTO solicitudDev, AdmcUnidadAdmvaDTO cbzUnidad) {

        List<DictaminadorVO> dictaminadores = getDictaminadores(solicitudDev, cbzUnidad);

        if (listaVacia(dictaminadores)) {

            LOG.error("No encontro dictaminador con clave ADM -->"
                    + solicitudDev.getDycpServicioDTO().getClaveAdm() + " Clave AGR--> " + cbzUnidad.getClaveAgrs());

            return ConstantesDyC2.ZERO_VALUE;
        }

        return seleccionaDictaminador(dictaminadores, solicitudDev);
    }

    private boolean listaVacia(List<DictaminadorVO> dictaminadores) {
        return dictaminadores.size() == ConstantesDyC2.SIZE_DE_LISTA_VACIA;
    }

    private Integer seleccionaDictaminador(List<DictaminadorVO> dictaminadores, DycpSolicitudDTO solicitudDev) {

        boolean esInasistente;
        boolean esDisponible;

        Iterator it = dictaminadores.iterator();

        while (it.hasNext()) {

            DictaminadorVO dyccDictaminadorAleatorio = dictaminadores.get(0);
            dictaminadores.remove(dyccDictaminadorAleatorio);

            esInasistente = getEsInasistenteDictaminador(dyccDictaminadorAleatorio, solicitudDev);

            if (esInasistente) {

                esDisponible = dyccDevolucionAutDAO.validarRegresoDictaminadorA(
                        dyccDictaminadorAleatorio.getNumEmpleado(),
                        solicitudDev.getFechaPresentacion()
                );

            } else {
                esDisponible = dyccDevolucionAutDAO.valida4DiasInhabilesContinuosA(
                        dyccDictaminadorAleatorio.getNumEmpleado(),
                        solicitudDev.getFechaPresentacion()
                );

            }

            if (esDisponible) {
                return dyccDictaminadorAleatorio.getNumEmpleado();
            }

        }

        return 0;
    }

    private boolean getEsInasistenteDictaminador(DictaminadorVO dyccDictaminadorAleatorio, DycpSolicitudDTO solicitudDev) {

        return dyccDevolucionAutDAO.validarDiaActualA(
                dyccDictaminadorAleatorio.getNumEmpleado(),
                solicitudDev.getFechaPresentacion()
        );
    }

    private List<DictaminadorVO> getDictaminadores(DycpSolicitudDTO solicitudDev, AdmcUnidadAdmvaDTO cbzUnidad) {

        return daoDictaminador.consultarDictaminadorPorCargaAleatorio(
                solicitudDev.getDycpServicioDTO().getClaveAdm(),
                cbzUnidad.getClaveAgrs()
        );
    }

    private Integer validaAdmContribuyente(String datosRNFD35, DycpSolicitudDTO solicitudDev,
            AdmcUnidadAdmvaDTO claveAdmActual) {

        String[] campos = getCampos(datosRNFD35);

        if (campoContieneAdministracion(campos, claveAdmActual)) {

            return Integer.valueOf(campos[0]);
        }

        return asignacionDictaminadoresRNFDC29(solicitudDev, claveAdmActual);
    }

    private String[] getCampos(String datosRNFD35) {
        return datosRNFD35.split("-");
    }

    private boolean campoContieneAdministracion(String[] campos, AdmcUnidadAdmvaDTO claveAdmActual) {
        Integer campo = Integer.valueOf(campos[1]);
        Integer claveSir = claveAdmActual.getClaveSir();

        return campo.equals(claveSir);
    }

    @Override
    public void generarServicioSolicitud(DycpSolicitudDTO solicitudDev, Integer numEmpleado) throws SIATException {
        try {
            dyccDevolucionAutDAO.insertaServicioSol(solicitudDev, numEmpleado);
        } catch (Exception error) {
            LOG.error(error.getMessage());
            throw new SIATException(error);
        }
    }

    @Override
    public DycpSolicitudDTO existeRegistroSolicitud(String numeroControl, String rfc, Integer icep) throws SIATException {

        DycpSolicitudDTO solicitudDTO = dycpSolicitudDAO.encontrar(numeroControl, rfc, icep);
        if (solicitudDTO == null) {
            throw new SIATException("NO EXISTE SOLICITUD CON NUMERO DE CONTROL:" + numeroControl + ", ICEP:" + icep + " Y RFC:" + rfc);
        }
        return solicitudDTO;
    }

    @Override
    public DyctSaldoIcepDTO encontrarRegistroIcepContribuyente(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo, Integer origenSaldo) throws DycDaoExcepcion {

        return daoSaldoIcep.encontrar(rfc, concepto, ejercicio, periodo, origenSaldo);
    }

    @Override
    public List<DycpDatosSolicitudDTO> obtenerDatosSolicitud(Integer idSaldoIcep) throws DycDaoExcepcion {

        return daoSaldoIcep.getSolicitud(idSaldoIcep);
    }

    @Override
    public String getCompensacionPorIcep(Integer idSaldoIcep) {

        return daoSaldoIcep.getCompensacionPorIcep(idSaldoIcep);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY,
            rollbackFor = {Exception.class})
    public void insertaServicioSol(DycpSolicitudDTO solicitud, Integer numEmpleado) throws SIATException {
        dyccDevolucionAutDAO.insertaServicioSol(solicitud, numEmpleado);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY, rollbackFor = {Exception.class})
    public void guardarDeclaracionIcep(DyctDeclaraIcepDTO declaracion) throws SIATException {
        daoDeclaraIcep.insertar(declaracion);
    }

    @Override
    public BigDecimal obtenerRemante(Integer idSaldoIcep) throws SIATException {
        DyctSaldoIcepDTO saldoIcep = serviceCargarSaldoIcep.execute(idSaldoIcep);
        ControlSaldoImportesDTO controlSaldoImportes = controlSaldoImporteService.calcularImportes(saldoIcep);
        return controlSaldoImportes.getRemanente();
    }

    @Override
    public List<DyctDeclaracionDTO> obtenerDeclaracion(String numeroOperacion, String rfc) {
        return dyctDeclaracionDAO.selectXNumOperacion(numeroOperacion, rfc);
    }

    @Override
    public DycpServicioDTO consultarTramiteSinDictaminador(String numeroControl) throws SIATException {

        return dycpServicioDAO.obtenerServicioSinDictaminador(numeroControl);

    }

    @Override
    public DycpSolicitudDTO consultarSolicitud(String numeroControl) throws SIATException {
        return dycpSolicitudDAO.consultarSolicitud(numeroControl);
    }

    @Override
    public BigDecimal obtenerImporteResuelto(Integer idSaldoIcep) throws SIATException {
        DyctSaldoIcepDTO saldoIcep = serviceCargarSaldoIcep.execute(idSaldoIcep);
        ControlSaldoImportesDTO controlSaldoImportes = controlSaldoImporteService.calcularImportes(saldoIcep);
        return controlSaldoImportes.getResuelto();
    }

    @Override
    public ControlSaldoImportesDTO obtenerSaldoImportes(Integer idSaldoIcep) throws SIATException {
        DyctSaldoIcepDTO saldoIcep = serviceCargarSaldoIcep.execute(idSaldoIcep);
        return controlSaldoImporteService.calcularImportes(saldoIcep);
        
    }
}
