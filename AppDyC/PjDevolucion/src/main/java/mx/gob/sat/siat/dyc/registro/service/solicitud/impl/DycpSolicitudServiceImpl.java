/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
package mx.gob.sat.siat.dyc.registro.service.solicitud.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedProperty;

import javax.xml.bind.JAXBException;

import mx.gob.sat.mat.dyc.controlsaldos.service.AfectarSaldosXDevolucionesService;
import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.MontoSaldoAFavorService;
import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.TramiteDictaminacionAutomaticaService;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInconsistenciaService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccPeriodoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyctDeclaracionService;
import mx.gob.sat.siat.dyc.controlsaldos.service.DetalleIcepService;
import mx.gob.sat.siat.dyc.dao.anexo.DyccMatrizAnexosDAO;
import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoTempDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctFacturaReqDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctReqConfProvDAO;
import mx.gob.sat.siat.dyc.dao.rfc.DycpRfcDAO;
import mx.gob.sat.siat.dyc.dao.secuencia.solicitud.SolNumConsecutivoDAO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoTempDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctServicioTempDTO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctSolicitudTempDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqConfProvDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaInconsistTempDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevTempVO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevolucionRegistroVO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSolRfcControlDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.secuencia.DycqNumControlDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUsoDecDTO;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.generico.util.competencia.CompetenciaUtils;
import mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.AcuseReciboService;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ContribuyenteService;
import mx.gob.sat.siat.dyc.registro.service.expediente.IntegrarExpedienteService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.AsignacionAutomaticaDictaminadorService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DeterminarFlujoSolicitudDevolucionService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistroConsultaService;
import mx.gob.sat.siat.dyc.service.DyctAnexoService;
import mx.gob.sat.siat.dyc.service.DyctArchivoService;
import mx.gob.sat.siat.dyc.service.DyctCuentaBancoService;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.AsignarException;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDictaminacionAutomatica;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante.enums.IdsContribuyentesConfiables;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteCompetenciaAGAFF;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesAsignarAuDic;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.vo.DycpSolicitudDevolucionDTO;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SolicitudConsultarExpedienteVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.xml.sax.SAXException;

@Service("dycpSolicitudService")
public class DycpSolicitudServiceImpl implements DycpSolicitudService {

    private static final Logger LOG = Logger.getLogger(DycpSolicitudServiceImpl.class);
    private static final String ERROR = "SIN FOLIO";
    private static final String HORAINICIAL = "00:00:00";
    private static final String HORALIMITE = "18:00:00";
    private static final String HORA_INICIAL_LABORAL = "09:00:00";
    private static final String FORMATO_HORA = "HH:mm:ss";
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String FORMATO_ANIO_YY = "yy";
    private static final String FORMATO_D_M_Y_H = "dd/MM/yyyy HH:mm:ss";

    @Autowired(required = true)
    private DycpSolicitudDAO daoSolicitud;
    @Autowired(required = false)
    private AsignacionAutomaticaDictaminadorService asignacionAutomaticaDictaminadorService;
    @Autowired
    private SolNumConsecutivoDAO solNumConsecutivoDAO;
    @Autowired
    private IntegrarExpedienteService integrarExpedienteService;
    @Autowired
    private DyctDeclaracionService dyctDeclaracionService;
    @Autowired
    private ContribuyenteService contribuyenteService;
    @Autowired
    private DyctAnexoService dyctAnexoService;
    @Autowired
    private DyccInconsistenciaService dyccInconsistenciaService;
    @Autowired
    private DeterminarFlujoSolicitudDevolucionService serviceDeterminarFlujo;
    @Autowired
    private DyctCuentaBancoService dyctCuentaBancoService;
    @Autowired
    private DyctArchivoService dycaDocumentoService;
    @Autowired(required = false)
    private AcuseReciboService acuseReciboService;
    @Autowired
    private DiaHabilService diaHabilService;
    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;
    @Autowired
    private DyctReqConfProvDAO dyctReqConfProvDAO;
    @Autowired
    private DyctFacturaReqDAO dyctFacturaReqDAO;
    @Autowired
    private DyctArchivoTempDAO daoArchivoTemp;
    @Autowired
    private DyccMatrizAnexosDAO dyccMatrizAnexosDAO;
    @Autowired
    private RegistroConsultaService registroConsultaService;
    @Autowired
    private AfectarSaldosXDevolucionesService serviceSaldos;
    @Autowired
    private RegistroPistasAuditoria servicePistasAuditoria;
    @Autowired
    private DyctDeclaracionDAO daoDeclaracion;
    @Autowired
    private DycpRfcDAO dycpRfcDao;
    @Autowired(required = false)
    private DyccPeriodoService dyccPeriodoService;
    @Autowired(required = false)
    private TramiteDictaminacionAutomaticaService tramiteDictaminacionAutomaticaService;
    @Autowired(required = false)
    private MontoSaldoAFavorService montoSaldoAFavorService;

    @ManagedProperty(value = "#{bdDetalleIcep}")
    private DetalleIcepService bussinesDelegate;
    
    @Autowired
    private DyctDocumentoDAO daoDocumento;

    private final DateFormat formateadorAnio = new SimpleDateFormat(FORMATO_ANIO_YY);

    /**
     * Metodo de servicio para consulta de un tramite
     *
     * @param numControl messageSol
     * @return Objeto < SolicitudConsultarExpedienteVO>
     *
     */
    @Transactional(readOnly = true)
    public SolicitudConsultarExpedienteVO buscarNumcontrol(String numControl) {
        SolicitudConsultarExpedienteVO dycpSolicitudDTO = new SolicitudConsultarExpedienteVO();

        try {
            dycpSolicitudDTO = daoSolicitud.buscarNumcontrol(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dycpSolicitudDTO;
    }

    @Transactional(readOnly = true)
    public SolicitudConsultarExpedienteVO buscarNumcontrolComp(String numControl) {

        SolicitudConsultarExpedienteVO dycpSolicitudDTO = new SolicitudConsultarExpedienteVO();

        try {
            dycpSolicitudDTO = daoSolicitud.buscarNumcontrolComp(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dycpSolicitudDTO;

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ParamOutputTO<DycpSolicitudDTO> insertaSolicitud(TramiteDTO input, String sello,
            String cadena) throws SIATException, AsignarException {
        boolean existeNumControl = true;
        Integer numEmpleado = null;
        String claveLocalidad = input.getRolesContribuyente().getClaveLocalidad();
        DecimalFormat nf = new DecimalFormat("#00");
        String strClaveLocalidad = nf.format(Long.parseLong(claveLocalidad));
        DycqNumControlDTO numConsecutivo;
        StringBuilder sbNumControl = new StringBuilder(ConstantesTipoRol.DC);
        //Cambio para validar que el num de control no existe en bd
        while (existeNumControl) {
            numConsecutivo = solNumConsecutivoDAO.getNumConsecutivo(strClaveLocalidad);
            sbNumControl = new StringBuilder(ConstantesTipoRol.DC);
            sbNumControl.append(claveLocalidad);
            sbNumControl.append(formateadorAnio.format(new Date()));
            sbNumControl.append(numConsecutivo.getSecuencia().trim());
            LOG.error("Buscando num de control: " + sbNumControl.toString());
            existeNumControl = solNumConsecutivoDAO.existeNumeroControlSolicitud(sbNumControl.toString());
        }

        DycpSolicitudDTO solicitud = llenarSolicitud(input, sbNumControl.toString());
        DyctContribuyenteDTO contribuyente = generaDatosContibXML(input.getRolesContribuyente(), input.getPersona());
        String urlGlobal = "";
        try {
            Map<String, Object> respCreateIcep = createICEP(input.getSaldoFavor(), input.getSaldoIcep(), solicitud);
            Boolean isInconsis = (Boolean) respCreateIcep.get("encontroDeclaDWH");
            solicitud.getDyctSaldoIcepDTO().setIdSaldoIcep((Integer) respCreateIcep.get("idSaldoIcep"));
            solicitud.setCadenaOriginal(cadena);
            solicitud.setSelloDigital(sello);
            numEmpleado
                    = asignacionAutomaticaDictaminadorService.asignarDictaminador(solicitud, ConstantesAsignarAuDic.SERVICIO_SOLICITUD_DEVOLUCION);
            if (numEmpleado == ConstantesDyCNumerico.VALOR_0) {
                throw new AsignarException();
            }
            daoSolicitud.insertaServicioSol(solicitud, numEmpleado);
            urlGlobal
                    = renameFileContribuyente(input.getInstitucionFinanciera().getDyctArchivoDTO().getUrl(), solicitud);
            createDeclaracion(solicitud);
            contribuyente.setNumControl(solicitud.getNumControl());
            contribuyenteService.createContribuyenteDYCT(SQLOracleDyC.CREATECONTRIBUYENTE.toString(), contribuyente);
            if (null != isInconsis && !isInconsis) {
                input.getInconsistencias().add(new CatalogoTO(ConstantesDyCNumerico.VALOR_8,
                        "INCONCISTENCIA EN LAS DECLARACIONES",
                        ConstantesDyC2.EMPTY_STRING));
            }
            createInconsistencias(input.getInconsistencias(), solicitud);
            createRfcsControlador(input.getRfcControladora(), solicitud);
            createCuentaBanco(input.getInstitucionFinanciera(), solicitud, urlGlobal);
            createAnexos(input.getAnexos(), solicitud, urlGlobal);
            createDocumentos(input.getDocumentos(), solicitud, urlGlobal);
            daoSolicitud.deleteSolicitudTep(input.getSolTemp());

            serviceDeterminarFlujo.determinarFlujoSolicitudDevolucion(solicitud);
            integrarExpedienteService.crearExpediente(solicitud, input.getRetenedor(), input.getDiot(),
                    input.getAltex());

            if (sbNumControl.toString().equals(solicitud.getNumControl())) {
                acuseReciboService.generarAcuseRecibo(ConstantesDyCNumerico.VALOR_1, solicitud.getNumControl(),
                        input.getPersona().getRfcVigente(), ConstantesDyC1.NO_ADMON, false);
            } else {
                throw new SIATException();
            }

        } catch (AsignarException ae) {
            LOG.error("Al asignar dictaminador: " + ae);
            throw new AsignarException(ae);
        } catch (Exception e) {
            ManejadorLogException.getTraceError(e);
            try {
                ValidaDatosSolicitud.deleteDocumentos(urlGlobal);
            } catch (IOException io) {
                LOG.error(io.getMessage());
            }
            throw new SIATException(e);
        }

        return new ParamOutputTO<DycpSolicitudDTO>(solicitud);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ParamOutputTO<DycpSolicitudDTO> insertaSolicitudSinIcept(TramiteDTO input, String sello,
            String cadena) throws SIATException, AsignarException {
        boolean existeNumControl = true;
        Integer numEmpleado = null;
        String claveLocalidad = input.getRolesContribuyente().getClaveLocalidad();
        DecimalFormat nf = new DecimalFormat("#00");
        String strClaveLocalidad = nf.format(Long.parseLong(claveLocalidad));
        DycqNumControlDTO numConsecutivo;
        StringBuilder sbNumControl = new StringBuilder(ConstantesTipoRol.DC);
        //Cambio para validar que el num de control no existe en bd
        while (existeNumControl) {
            numConsecutivo = solNumConsecutivoDAO.getNumConsecutivo(strClaveLocalidad);
            sbNumControl = new StringBuilder(ConstantesTipoRol.DC);
            sbNumControl.append(claveLocalidad);
            sbNumControl.append(formateadorAnio.format(new Date()));
            sbNumControl.append(numConsecutivo.getSecuencia().trim());
            LOG.error("Buscando num de control: " + sbNumControl.toString());
            existeNumControl = solNumConsecutivoDAO.existeNumeroControlSolicitud(sbNumControl.toString());
        }

        DycpSolicitudDTO solicitud = llenarSolicitud(input, sbNumControl.toString());
        DyctContribuyenteDTO contribuyente = generaDatosContibXML(input.getRolesContribuyente(), input.getPersona());
        String urlGlobal = "";
        try {
            Map<String, Object> respCreateIcep = createICEP(input.getSaldoFavor(), input.getSaldoIcep(), solicitud);
            Boolean isInconsis = (Boolean) respCreateIcep.get("encontroDeclaDWH");
            solicitud.getDyctSaldoIcepDTO().setIdSaldoIcep((Integer) respCreateIcep.get("idSaldoIcep"));
            solicitud.setCadenaOriginal(cadena);
            solicitud.setSelloDigital(sello);
            numEmpleado
                    = asignacionAutomaticaDictaminadorService.asignarDictaminador(solicitud, ConstantesAsignarAuDic.SERVICIO_SOLICITUD_DEVOLUCION);
            if (numEmpleado == ConstantesDyCNumerico.VALOR_0) {
                throw new AsignarException();
            }
            daoSolicitud.insertaServicioSol(solicitud, numEmpleado);
            urlGlobal
                    = renameFileContribuyente(input.getInstitucionFinanciera().getDyctArchivoDTO().getUrl(), solicitud);
            createDeclaracion(solicitud);
            contribuyente.setNumControl(solicitud.getNumControl());
            contribuyenteService.createContribuyenteDYCT(SQLOracleDyC.CREATECONTRIBUYENTE.toString(), contribuyente);
            if (null != isInconsis && !isInconsis) {
                input.getInconsistencias().add(new CatalogoTO(ConstantesDyCNumerico.VALOR_8,
                        "INCONCISTENCIA EN LAS DECLARACIONES",
                        ConstantesDyC2.EMPTY_STRING));
            }

            cargarArchivos(urlGlobal, input);

            createInconsistencias(input.getInconsistencias(), solicitud);
            createRfcsControlador(input.getRfcControladora(), solicitud);
            createCuentaBanco(input.getInstitucionFinanciera(), solicitud, urlGlobal);
            createAnexos(input.getAnexos(), solicitud, urlGlobal);
            createDocumentos(input.getDocumentos(), solicitud, urlGlobal);
            daoSolicitud.deleteSolicitudTep(input.getSolTemp());

            serviceDeterminarFlujo.determinarFlujoSolicitudDevolucion(solicitud);
            integrarExpedienteService.crearExpediente(solicitud, input.getRetenedor(), input.getDiot(),
                    input.getAltex());

            if (sbNumControl.toString().equals(solicitud.getNumControl())) {
                acuseReciboService.generarAcuseRecibo(ConstantesDyCNumerico.VALOR_1, solicitud.getNumControl(),
                        input.getPersona().getRfcVigente(), ConstantesDyC1.NO_ADMON, false);
            } else {
                throw new SIATException();
            }

        } catch (AsignarException ae) {
            LOG.error("Al asignar dictaminador: " + ae);
            throw new AsignarException(ae);
        } catch (Exception e) {
            ManejadorLogException.getTraceError(e);
            try {
                ValidaDatosSolicitud.deleteDocumentos(urlGlobal);
            } catch (IOException io) {
                LOG.error(io.getMessage());
            }
            throw new SIATException(e);
        }

        return new ParamOutputTO<DycpSolicitudDTO>(solicitud);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ParamOutputTO<DycpSolicitudDTO> insertaSolicitudISR(TramiteDTO input, String sello,
            String cadena) throws SIATException, AsignarException {
        boolean existeNumControl = true;
        Integer numEmpleado = null;
        String claveLocalidad = input.getRolesContribuyente().getClaveLocalidad();
        DecimalFormat nf = new DecimalFormat("#00");
        String strClaveLocalidad = nf.format(Long.parseLong(claveLocalidad));
        DycqNumControlDTO numConsecutivo;
        DycpSolicitudDTO solicitudISR = new DycpSolicitudDTO();
        StringBuilder sbNumControl = new StringBuilder(ConstantesTipoRol.DC);

        //Cambio para validar que el num de control no existe en bd
        while (existeNumControl) {
            numConsecutivo = solNumConsecutivoDAO.getNumConsecutivo(strClaveLocalidad);
            sbNumControl = new StringBuilder(ConstantesTipoRol.DC);
            sbNumControl.append(claveLocalidad);
            sbNumControl.append(formateadorAnio.format(new Date()));
            sbNumControl.append(numConsecutivo.getSecuencia().trim());
            LOG.error("Buscando num de control: " + sbNumControl.toString());
            existeNumControl = solNumConsecutivoDAO.existeNumeroControlSolicitud(sbNumControl.toString());
        }

        DycpSolicitudDTO solicitud = llenarSolicitud(input, sbNumControl.toString());
        DyctContribuyenteDTO contribuyente = generaDatosContibXML(input.getRolesContribuyente(), input.getPersona());
        String urlGlobal = "";
        try {
            DyctSaldoIcepDTO icepT = null;
            DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
            DyccPeriodoDTO periodo = new DyccPeriodoDTO();
            DyccOrigenSaldoDTO saldoOrigen = new DyccOrigenSaldoDTO();
            ejercicio.setIdEjercicio(input.getEjercicio().getIdNum());
            periodo.setIdPeriodo(input.getPeriodo().getIdNum());
            saldoOrigen.setIdOrigenSaldo(input.getOrigenSaldo().getIdNum());

            icepT = serviceSaldos.encontrarISR(input.getPersona().getRfc(), input.getConcepto(), ejercicio, periodo, saldoOrigen);

            if (icepT != null) {
                int ifNumOperacion;
                solicitudISR.setNumControl(input.getNumControl());
                input.setSaldoIcep(icepT.getIdSaldoIcep());
                solicitud.getDyctSaldoIcepDTO().setIdSaldoIcep(icepT.getIdSaldoIcep());
                solicitud.setCadenaOriginal(cadena);
                solicitud.setSelloDigital(sello);
                solicitud.setNumControl(sbNumControl.toString());

                ifNumOperacion = daoDeclaracion.selecXServicioNumOperacion(input.getNumControl());

                numEmpleado = asignacionAutomaticaDictaminadorService.asignarDictaminador(solicitud, ConstantesAsignarAuDic.SERVICIO_SOLICITUD_DEVOLUCION);
                urlGlobal = renameFileContribuyente(input.getInstitucionFinanciera().getDyctArchivoDTO().getUrl(), solicitud);

                if (numEmpleado == ConstantesDyCNumerico.VALOR_0) {
                    throw new AsignarException();
                }

                daoSolicitud.insertaServicioSol(solicitud, numEmpleado);
                solicitud.setResolAutomatica(0);

                /**
                 * TODO: Revisar y hacer la implementacion correcta este es un
                 * parche *
                 */
                createDeclaracionTemporal(input.getSaldoFavor(), solicitud.getNumControl());

                if (ifNumOperacion == 0) {

                    createICEPISR(input.getSaldoFavor(), solicitud);
                }

            } else {
                return insertaSolicitudSinIcept(input, sello, cadena);
            }

            contribuyente.setNumControl(solicitud.getNumControl());
            contribuyenteService.createContribuyenteDYCT(SQLOracleDyC.CREATECONTRIBUYENTE.toString(), contribuyente);

            cargarArchivos(urlGlobal, input);

            createRfcsControlador(input.getRfcControladora(), solicitud);
            createCuentaBanco(input.getInstitucionFinanciera(), solicitud, urlGlobal);
            createAnexos(input.getAnexos(), solicitud, urlGlobal);
            createDocumentos(input.getDocumentos(), solicitud, urlGlobal);

            daoSolicitud.deleteSolicitudTep(input.getSolTemp());

            serviceDeterminarFlujo.determinarFlujoSolicitudDevolucion(solicitud);
            integrarExpedienteService.crearExpediente(solicitud, input.getRetenedor(), input.getDiot(),
                    input.getAltex());

            if (sbNumControl.toString().equals(solicitud.getNumControl())) {
                acuseReciboService.generarAcuseRecibo(ConstantesDyCNumerico.VALOR_1, solicitud.getNumControl(),
                        input.getPersona().getRfcVigente(), ConstantesDyC1.NO_ADMON, false);
            } else {
                throw new SIATException();
            }

        } catch (AsignarException ae) {
            LOG.error("Al asignar dictaminador: " + ae);
            throw new AsignarException(ae);
        } catch (Exception e) {
            ManejadorLogException.getTraceError(e);
            try {
                ValidaDatosSolicitud.deleteDocumentos(urlGlobal);
            } catch (IOException io) {
                LOG.error(io.getMessage());
            }
            throw new SIATException(e);
        }

        return new ParamOutputTO<DycpSolicitudDTO>(solicitud);
    }

    public void cargarArchivos(String urlGlobal, TramiteDTO input) {

        copiarArchivo(urlGlobal + "/" + input.getInstitucionFinanciera().getDyctArchivoDTO().getNombreArchivo(), urlGlobal + ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS, input.getInstitucionFinanciera().getDyctArchivoDTO().getNombreArchivo());

        for (ArchivoVO documento : input.getDocumentos()) {
            copiarArchivo(urlGlobal + "/" + documento.getNombreArchivo(), urlGlobal + ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS, documento.getNombreArchivo());

        }

    }

    public void copiarArchivo(String urlOrigen, String urlDestino, String nombreArchivo) {

        
        File archivoOrigen = new File(urlOrigen);
        OutputStream out = null;
        InputStream in = null;

        if (archivoOrigen.exists()) {
            try {
                if (new File(urlDestino).mkdir()) {
                    LOG.error("Se creo el directorio correctamente");
                }

                File archivoDestino = new File(urlDestino + "/", nombreArchivo);

                in = new FileInputStream(archivoOrigen);
                out = new FileOutputStream(archivoDestino);

                byte[] buf = new byte[ConstantesDyCNumerico.VALOR_4096];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.flush();
                out.close();

            } catch (IOException e) {
                LOG.error("Error al copiar el archivo." + e.getMessage());
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException ex) {
                    LOG.error("Error al cerrar el archivo." + ex.getMessage());
                }
                
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                    LOG.error("Error al cerrar el archivo." + ex.getMessage());
                }
            }

        } else {
            LOG.error("Error no existe el archivo especificado.");
            return;
        }

        File archivo = new File(urlOrigen);
        if (archivo.delete()) {
            LOG.error("Se borro el archivo de forma correcta ISR");
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void insertaSolicitudTemporal(TramiteDTO input) throws SIATException {
        DyctSolicitudTempDTO solTemp = new DyctSolicitudTempDTO();
        Integer folio = null;
        DyctContribuyenteDTO contribuyente = null;

        folio = solNumConsecutivoDAO.getFolioServicioTemp();
        contribuyente = generaDatosContibXML(input.getRolesContribuyente(), input.getPersona());
        String urlGlobal = "";
        if (ConstantesDyCNumerico.VALOR_0 != folio.intValue()) {
            llenarSolicitudTemporal(input, solTemp);
            solTemp.setFolioServtemp(folio);
            daoSolicitud.createSolicitudTemp(solTemp);
            contribuyente.setNumControl(folio.toString());
            contribuyenteService.createContribuyenteDYCT(SQLOracleDyC.CREATE_CONTRIBUYENTE_TEMP.toString(), contribuyente);
            dyctDeclaracionService.createDeclaracionTemp(input.getSaldoFavor(), folio.toString());
            urlGlobal = createCtaBancoTemp(input.getInstitucionFinanciera().getDyctArchivoDTO(), solTemp);
            createArchivosTemp(input.getDocumentos(), ConstantesDyCNumerico.VALOR_2, urlGlobal, solTemp);
            createArchivosTemp(input.getAnexos(), ConstantesDyCNumerico.VALOR_3, urlGlobal, solTemp);
            createInconsistenciaTemp(input.getInconsistencias(), solTemp);
        } else {
            throw new SIATException(ERROR);
        }
    }

    private DycpSolicitudDTO llenarSolicitud(TramiteDTO input, String numControl) throws SIATException {
        DycpSolicitudDTO solicitudDTO = new DycpSolicitudDTO();
        solicitudDTO.setNumControl(numControl);
        DyccTipoTramiteDTO tramite = new DyccTipoTramiteDTO();
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        tramite.setIdTipoTramite(input.getTipoTramite().getIdNum());

        /**
         * Manifiesta estado cuenta
         */
        tramite.setPlazo(input.getInstitucionFinanciera().getCuentaValida());
        /**
         * Protesta
         */
        tramite.setPuntosAsignacion(null != input.getTipoTramite().getDescripcion()
                ? Integer.parseInt(input.getTipoTramite().getDescripcion()) : null);
        /**
         * sector agropecuario
         */
        tramite.setDescripcion(null != input.getSecAgp() ? input.getSecAgp().getIdString() : null);
        tramite.setClaveContable(null != input.getSecAgp() ? input.getSecAgp().getDescripcion() : null);
        tramite.setResolAutomatica(null != input.getSecAgp() ? input.getSecAgp().getIdNum() : null);
        tramite.setDyccConceptoDTO(ValidaDatosSolicitud.getConcepto(input.getImpuesto().getIdImpuesto(),
                input.getConcepto().getIdConcepto()));
        DycaOrigenTramiteDTO origenTramite = new DycaOrigenTramiteDTO();
        DycaServOrigenDTO dycaServOrigenDTO = new DycaServOrigenDTO();
        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
        dyccOrigenSaldoDTO.setIdOrigenSaldo(input.getOrigenSaldo().getIdNum());
        dycaServOrigenDTO.setDyccOrigenSaldoDTO(dyccOrigenSaldoDTO);
        dycaServOrigenDTO.setDyccTipoServicioDTO(new DyccTipoServicioDTO(1, null, null, null));
        origenTramite.setDycaServOrigenDTO(dycaServOrigenDTO);

        DyccSubTramiteDTO subTramite = new DyccSubTramiteDTO();
        /**
         * Estado Actual
         */

        subTramite.setIdSubTipoTramite(ValidaDatosSolicitud.isCatalogo(input.getSubTramite()));
        validaHoraRegSol(solicitudDTO);
        solicitudDTO.setImporteSolicitado(null != input.getSaldoFavor().getImporteSolicitadoDevolucion()
                ? input.getSaldoFavor().getImporteSolicitadoDevolucion()
                : ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO);
        solicitudDTO.setInfoAdicional(input.getInfoAdicional());
        solicitudDTO.setDiotNumOperacion(null != input.getInfoDeclarativa().getDiotFechPrecentacion()
                ? input.getInfoDeclarativa().getDiotNumOperacion().toUpperCase() : null);
        solicitudDTO.setDiotFechaPresenta(input.getInfoDeclarativa().getDiotFechPrecentacion());
        solicitudDTO.setRetenedorRfc(null != input.getRetenedor() ? input.getRetenedor().getRfc()
                : ConstantesDyC2.NULL);
        solicitudDTO.setAltexConstancia(null != input.getInfoDeclarativa().getAltexNumConstancia()
                ? input.getInfoDeclarativa().getAltexNumConstancia().toString() : null);
        solicitudDTO.setEsCertificado(false);
        dycpServicioDTO.setRfcContribuyente(input.getPersona().getRfcVigente());
        dycpServicioDTO.setBoid(Utilerias.isNull(input.getPersona().getBoId()));
        dycpServicioDTO.setClaveAdm(input.getRolesContribuyente().getClaveAdmon());
        origenTramite.setDyccTipoTramiteDTO(tramite);
        dycpServicioDTO.setDycaOrigenTramiteDTO(origenTramite);
        dycpServicioDTO.setNumControl(numControl);
        solicitudDTO.setDycpServicioDTO(dycpServicioDTO);
        solicitudDTO.setDyccEstadoSolDTO(getEstadoSolicitud(input));
        solicitudDTO.setDyctSaldoIcepDTO(ValidaDatosSolicitud.getSaldoIcep(input.getEjercicio().getIdNum(),
                input.getPeriodo().getIdNum(),
                input.getOrigenSaldo().getIdNum()));
        solicitudDTO.getDyctSaldoIcepDTO().setBloqueado(input.getEstadoActual());
        solicitudDTO.setDyccSubtramiteDTO(subTramite);
        solicitudDTO.setDyccActividadDTO(ValidaDatosSolicitud.getActividad(ValidaDatosSolicitud.isCatalogo(input.getActividadEconomica()),
                ValidaDatosSolicitud.isCatalogo(input.getSubOrigenSaldo())));
        return solicitudDTO;
    }

    public DyccEstadoSolDTO getEstadoSolicitud(TramiteDTO input) {
        DyccEstadoSolDTO edoSolicitud = new DyccEstadoSolDTO();
        Integer estadoDictAut = estadoDictAutomatica(input);
        if (estadoDictAut != ConstantesDyCNumerico.VALOR_0) {
            edoSolicitud.setIdEstadoSolicitud(estadoDictAut);
        } else {
            DycpRfcDTO contConfiable = dycpRfcDao.encontrar(input.getPersona().getRfcVigente().trim());
            edoSolicitud.setIdEstadoSolicitud(null != contConfiable
                    && contConfiable.getEsConfiable().equals(IdsContribuyentesConfiables.ES_CONFIABLE)
                    ? ConstantesDyCNumerico.VALOR_16 : ConstantesDyCNumerico.VALOR_3);
        }
        return edoSolicitud;
    }

    private Integer estadoDictAutomatica(TramiteDTO input) {
        int estatus = 0;
        DycTramiteDictaminacionAutomaticaDTO dyccTramiteDicAuDTO;
        if (CompetenciaUtils.esCompetenciaAGAFF(input.getRolesContribuyente().getClaveAdmon())) {
            dyccTramiteDicAuDTO = tramiteDictaminacionAutomaticaService.perteneceDictAutomatica(
                    input.getTipoTramite().getIdNum(), input.getConcepto().getIdConcepto());
            if (dyccTramiteDicAuDTO != null && validaSaldoFavor(input.getSaldoFavor().getImporteSolicitadoDevolucion(),
                    input.getTipoTramite().getIdNum()) && validaVigencia(input)) {
                estatus = dyccTramiteDicAuDTO.getModelo().equals(ConstantesDictaminacionAutomatica.ID_MODELO_SIVAD)
                        ? ConstantesDyCNumerico.VALOR_17 : ConstantesDyCNumerico.VALOR_18;
            }
        }
        return estatus;
    }

    private boolean validaSaldoFavor(BigDecimal importeSolicitado, int idTipoTrmite) {
        boolean bandera = false;
        BigDecimal limiteSaldo = montoSaldoAFavorService.getLimiteSaldoFavor(idTipoTrmite);
        if (limiteSaldo.compareTo(BigDecimal.ZERO) != ConstantesDyCNumerico.VALOR_0
                && importeSolicitado.compareTo(limiteSaldo) != ConstantesDyCNumerico.VALOR_1) {
            bandera = Boolean.TRUE;
        }
        return bandera;
    }

    private boolean validaVigencia(TramiteDTO input) {
        boolean bandera = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int mesActual = cal.get(Calendar.MONTH) + ConstantesDyCNumerico.VALOR_1;
        int anioValido = cal.get(Calendar.YEAR) - ConstantesDyCNumerico.VALOR_5;
        if (input.getEjercicio().getIdNum() >= anioValido) {
            bandera = Boolean.TRUE;
        } else if (input.getEjercicio().getIdNum() < anioValido) {
            bandera = false;
        } else {
            return dyccPeriodoService.obtenerFinPeriodo(input.getPeriodo().getIdNum()) < mesActual;

        }
        return bandera;

    }

    private DyctContribuyenteDTO generaDatosContibXML(RolesContribuyenteDTO input,
            PersonaDTO personaContrib) throws SIATException {
        DyctContribuyenteDTO contribuyente = new DyctContribuyenteDTO();
        contribuyente.setRolPm(ValidaDatosSolicitud.isRolPersona(input.isPersonaMoral()));
        contribuyente.setRolPf(ValidaDatosSolicitud.isRolPersona(input.isPersonaFisica()));
        contribuyente.setRolDictaminado(ValidaDatosSolicitud.isRolPersona(input.isDictaminado()));
        contribuyente.setRolGranContrib(ValidaDatosSolicitud.isRolPersona(input.isGranContribuyente()));
        contribuyente.setRolSociedadControl(ValidaDatosSolicitud.isRolPersona(input.isSociedadControladora()));
        try {
            contribuyente.setDatosContribuyente(new ByteArrayInputStream(personaContrib.generateXML(ConstantesDyC2.CONTRIBUYENTE_XSD)));
        } catch (JAXBException e) {
            throw new SIATException("ERROR EN EL GUARDADO DE CONTRIBUYENTE", e);
        } catch (SAXException e) {
            throw new SIATException("ERROR EN EL GUARDADO DE CONTRIBUYENTE", e);
        }
        return contribuyente;
    }

    private void llenarSolicitudTemporal(TramiteDTO tramite, DyctSolicitudTempDTO solTemp) {
        solTemp.setTipoSaldo(tramite.getOrigenSaldo().getIdString());
        solTemp.setImporteSolicitado(tramite.getSaldoFavor().getImporteSolicitadoDevolucion());
        solTemp.setRfcContribuyente(tramite.getPersona().getRfcVigente());
        solTemp.setInfoAdicional(tramite.getInfoAdicional());
        solTemp.setDiotNumOperacion(tramite.getInfoDeclarativa().getDiotNumOperacion());
        solTemp.setDiotFechaPresenta(tramite.getInfoDeclarativa().getDiotFechPrecentacion());
        solTemp.setRetenedorRfc(null != tramite.getRetenedor() ? tramite.getRetenedor().getRfc() : ConstantesDyC2.NULL);
        solTemp.setAltexConstancia(null != tramite.getInfoDeclarativa().getAltexNumConstancia()
                ? tramite.getInfoDeclarativa().getAltexNumConstancia().toString() : null);
        solTemp.setClaveAdm(tramite.getRolesContribuyente().getClaveAdmon());
        solTemp.setIdEstadosolicitud(ConstantesDyCNumerico.VALOR_1);
        solTemp.setIdPeriodo(tramite.getPeriodo().getIdNum());
        solTemp.setIdTipotramite(tramite.getTipoTramite().getIdNum());
        solTemp.setIdSuborigensaldo(null != tramite.getSubOrigenSaldo() ? tramite.getSubOrigenSaldo().getIdNum() : 0);
        solTemp.setIdOrigensaldo(tramite.getOrigenSaldo().getIdNum());
        solTemp.setIdSubtipotramite(null != tramite.getSubTramite() ? tramite.getSubTramite().getIdNum() : null);
        solTemp.setIdImpuesto(tramite.getImpuesto().getIdImpuesto());
        solTemp.setIdConcepto(tramite.getConcepto().getIdConcepto());
        solTemp.setIdEjercicio(tramite.getEjercicio().getIdNum());
        solTemp.setClabeBancaria(null != tramite.getInstitucionFinanciera().getClabe()
                ? tramite.getInstitucionFinanciera().getClabe() : null);
        solTemp.setIdActividad(null != tramite.getActividadEconomica() ? tramite.getActividadEconomica().getIdNum()
                : null);
        /**
         * Datos Correctos
         */
        if (null != tramite.getSaldoFavor().getDatosCorrectos()) {
            solTemp.setDatosCorrectos(tramite.getSaldoFavor().getDatosCorrectos().equals("Si") ? "1" : "0");
        }
        /**
         * Manifiesta estado cuenta
         */
        solTemp.setManifiestaEdocta(tramite.getInstitucionFinanciera().getCuentaValida());
        /**
         * Protesta
         */
        solTemp.setProtesta(null != tramite.getTipoTramite().getDescripcion()
                ? Integer.parseInt(tramite.getTipoTramite().getDescripcion()) : null);

        /**
         * Aplicafacilidad,InfoAgro,EdoPadron
         */
        isSecAgro(tramite.getSecAgp(), solTemp);
        /**
         * saldo ICEP
         */
        solTemp.setSaldoIcep(tramite.getSaldoIcep());
    }

    private Map<String, Object> createICEP(InformacionSaldoFavorTO saldoFavor, Integer idSaldoIcep,
            DycpSolicitudDTO solicitudDTO) throws SIATException {
        LOG.debug("INICIO createICEP");
        solicitudDTO.getDycpServicioDTO().setDyctDeclaracionList(new ArrayList<DyctDeclaracionDTO>());
        DyctDeclaracionDTO declaracionNormal = new DyctDeclaracionDTO();
        int idtipoDec
                = ValidaDatosSolicitud.validaTipoDec(saldoFavor.getFechaPresentacion(), saldoFavor.getFechaCaucion())
                ? ConstantesDyCNumerico.VALOR_3 : Integer.valueOf(saldoFavor.getTipoDeclaracion());

        declaracionNormal.setNumOperacion(saldoFavor.getNumeroOperacion());
        declaracionNormal.setEtiquetaSaldo(!saldoFavor.getEtiquetaSaldo().endsWith("SF")
                ? saldoFavor.getEtiquetaSaldo() : null);
        declaracionNormal.setNumDocumento(saldoFavor.getNumeroDocumento());
        declaracionNormal.setSaldoAfavor(saldoFavor.getImporteSaldoFavor());
        declaracionNormal.setAcreditamiento(saldoFavor.getImporteEfectuados());
        declaracionNormal.setDevueltoCompensado(saldoFavor.getImporteAcreditramientoEfectuado());
        declaracionNormal.setImporte(saldoFavor.getImporteSolicitadoDevolucion());
        declaracionNormal.setDyccUsoDecDTO(new DyccUsoDecDTO(null, null, null, ConstantesDyCNumerico.VALOR_1));
        declaracionNormal.setFechaPresentacion(saldoFavor.getFechaPresentacion());
        declaracionNormal.setFechaCausacion(saldoFavor.getFechaCaucion());
        declaracionNormal.setDyccTipoDeclaraDTO(new DyccTipoDeclaraDTO(idtipoDec));
        solicitudDTO.getDycpServicioDTO().getDyctDeclaracionList().add(declaracionNormal);
        solicitudDTO.getDycpServicioDTO().setDycpSolicitudDTO(solicitudDTO);

        if (null != saldoFavor.getNormalFechaPresentacion()) {
            DyctDeclaracionDTO declaracionCom = new DyctDeclaracionDTO();
            declaracionCom.setFechaPresentacion(saldoFavor.getNormalFechaPresentacion());
            declaracionCom.setNumOperacion(saldoFavor.getNormalNumOperacion());
            declaracionCom.setSaldoAfavor(saldoFavor.getNormalSaldoFavor());
            declaracionCom.setImporte(saldoFavor.getNormalSaldoFavor());
            declaracionCom.setDyccUsoDecDTO(new DyccUsoDecDTO(null, null, null, ConstantesDyCNumerico.VALOR_2));
            declaracionCom.setDyccTipoDeclaraDTO(new DyccTipoDeclaraDTO(ConstantesDyCNumerico.VALOR_1));
            solicitudDTO.getDycpServicioDTO().getDyctDeclaracionList().add(declaracionCom);
        }

        if (null != idSaldoIcep) {
            LOG.info("REGISTRO DEL ICEP CON SALDOS");
            Map<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("idSaldoIcep", idSaldoIcep);
            return respuesta;

        } else {
            return serviceSaldos.afectarXRegistro(solicitudDTO);
        }
    }

    private Map<String, Object> createICEPISR(InformacionSaldoFavorTO saldoFavor,
            DycpSolicitudDTO solicitudDTO) throws SIATException {
        LOG.debug("INICIO createICEP");
        solicitudDTO.getDycpServicioDTO().setDyctDeclaracionList(new ArrayList<DyctDeclaracionDTO>());
        DyctDeclaracionDTO declaracionNormal = new DyctDeclaracionDTO();
        int idtipoDec
                = ValidaDatosSolicitud.validaTipoDec(saldoFavor.getFechaPresentacion(), saldoFavor.getFechaCaucion())
                ? ConstantesDyCNumerico.VALOR_3 : Integer.valueOf(saldoFavor.getTipoDeclaracion());

        declaracionNormal.setNumOperacion(saldoFavor.getNumeroOperacion());
        declaracionNormal.setEtiquetaSaldo(!saldoFavor.getEtiquetaSaldo().endsWith("SF")
                ? saldoFavor.getEtiquetaSaldo() : null);
        declaracionNormal.setNumDocumento(saldoFavor.getNumeroDocumento());
        declaracionNormal.setSaldoAfavor(saldoFavor.getImporteSaldoFavor());
        declaracionNormal.setAcreditamiento(saldoFavor.getImporteEfectuados());
        declaracionNormal.setDevueltoCompensado(saldoFavor.getImporteAcreditramientoEfectuado());
        declaracionNormal.setImporte(saldoFavor.getImporteSolicitadoDevolucion());
        declaracionNormal.setDyccUsoDecDTO(new DyccUsoDecDTO(null, null, null, ConstantesDyCNumerico.VALOR_1));
        declaracionNormal.setFechaPresentacion(saldoFavor.getFechaPresentacion());
        declaracionNormal.setFechaCausacion(saldoFavor.getFechaCaucion());
        declaracionNormal.setDyccTipoDeclaraDTO(new DyccTipoDeclaraDTO(idtipoDec));
        solicitudDTO.getDycpServicioDTO().getDyctDeclaracionList().add(declaracionNormal);
        solicitudDTO.getDycpServicioDTO().setDycpSolicitudDTO(solicitudDTO);

        if (null != saldoFavor.getNormalFechaPresentacion()) {
            DyctDeclaracionDTO declaracionCom = new DyctDeclaracionDTO();
            declaracionCom.setFechaPresentacion(saldoFavor.getNormalFechaPresentacion());
            declaracionCom.setNumOperacion(saldoFavor.getNormalNumOperacion());
            declaracionCom.setSaldoAfavor(saldoFavor.getNormalSaldoFavor());
            declaracionCom.setImporte(saldoFavor.getNormalSaldoFavor());
            declaracionCom.setDyccUsoDecDTO(new DyccUsoDecDTO(null, null, null, ConstantesDyCNumerico.VALOR_2));
            declaracionCom.setDyccTipoDeclaraDTO(new DyccTipoDeclaraDTO(ConstantesDyCNumerico.VALOR_1));
            solicitudDTO.getDycpServicioDTO().getDyctDeclaracionList().add(declaracionCom);
        }

        return serviceSaldos.afectarXRegistroISR(solicitudDTO);
    }

    private void createDeclaracion(DycpSolicitudDTO solicitudDTO) throws SIATException {
        DyctDeclaracionDTO declaracion = null;
        if (!solicitudDTO.getDycpServicioDTO().getDyctDeclaracionList().isEmpty()) {
            Iterator<DyctDeclaracionDTO> it = solicitudDTO.getDycpServicioDTO().getDyctDeclaracionList().iterator();
            while (it.hasNext()) {
                declaracion = (DyctDeclaracionDTO) it.next();
                declaracion.setNumControl(solicitudDTO.getNumControl());
                dyctDeclaracionService.createDeclaracion(declaracion);
            }
        }
    }

    private void createDeclaracionTemporal(InformacionSaldoFavorTO saldoFavor, String numControl) throws SIATException {
        DyctDeclaracionDTO declaracionNormal = new DyctDeclaracionDTO();
        int idtipoDec
                = ValidaDatosSolicitud.validaTipoDec(saldoFavor.getFechaPresentacion(), saldoFavor.getFechaCaucion())
                ? ConstantesDyCNumerico.VALOR_3 : Integer.valueOf(saldoFavor.getTipoDeclaracion());

        declaracionNormal.setNumOperacion(saldoFavor.getNumeroOperacion());
        declaracionNormal.setEtiquetaSaldo(!saldoFavor.getEtiquetaSaldo().endsWith("SF")
                ? saldoFavor.getEtiquetaSaldo() : null);
        declaracionNormal.setNumDocumento(saldoFavor.getNumeroDocumento());
        declaracionNormal.setSaldoAfavor(saldoFavor.getImporteSaldoFavor());
        declaracionNormal.setAcreditamiento(saldoFavor.getImporteEfectuados());
        declaracionNormal.setDevueltoCompensado(saldoFavor.getImporteAcreditramientoEfectuado());
        declaracionNormal.setImporte(saldoFavor.getImporteSolicitadoDevolucion());
        declaracionNormal.setDyccUsoDecDTO(new DyccUsoDecDTO(null, null, null, ConstantesDyCNumerico.VALOR_1));
        declaracionNormal.setFechaPresentacion(saldoFavor.getFechaPresentacion());
        declaracionNormal.setFechaCausacion(saldoFavor.getFechaCaucion());
        declaracionNormal.setDyccTipoDeclaraDTO(new DyccTipoDeclaraDTO(idtipoDec));

        declaracionNormal.setNumControl(numControl);
        dyctDeclaracionService.createDeclaracion(declaracionNormal);

    }

    private String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_HORA);
        return formateador.format(ahora);
    }

    private void validaHoraRegSol(DycpSolicitudDTO solicitudDTO) {

        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMATO_HORA);
            SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_FECHA);

            String horaActual = getHoraActual();
            Date hrInicial, hrLimete, hrTramite;
            hrInicial = dateFormat.parse(HORAINICIAL);
            hrLimete = dateFormat.parse(HORALIMITE);
            hrTramite = dateFormat.parse(horaActual);

            boolean consultarDiaHabilSig = Boolean.TRUE;
            if (diaHabilService.esHabil(new Date())) {

                Date horaInicioLab = dateFormat.parse(HORA_INICIAL_LABORAL);
                if (hrTramite.before(horaInicioLab)) {
                    String ha = formateador.format(new Date());
                    formateador = new SimpleDateFormat(FORMATO_D_M_Y_H);
                    solicitudDTO.setFechaPresentacion(formateador.parse(ha + " " + HORA_INICIAL_LABORAL));
                    solicitudDTO.setFechaInicioTramite(formateador.parse(ha + " " + HORA_INICIAL_LABORAL));

                    consultarDiaHabilSig = false;
                } else if ((hrInicial.compareTo(hrTramite) <= ConstantesDyCNumerico.VALOR_0)
                        && (hrLimete.compareTo(hrTramite) >= ConstantesDyCNumerico.VALOR_0)) {
                    solicitudDTO.setFechaPresentacion(new Date());
                    solicitudDTO.setFechaInicioTramite(null);
                    consultarDiaHabilSig = false;
                }
            }

            if (consultarDiaHabilSig) {
                String ha = null;
                Date fecha = null;

                try {
                    fecha = diaHabilService.buscarDiaFederalRecaudacion(new Date(), ConstantesDyCNumerico.VALOR_1);
                    ha = formateador.format(fecha);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
                formateador = new SimpleDateFormat(FORMATO_D_M_Y_H);
                solicitudDTO.setFechaPresentacion(formateador.parse(ha + " " + HORA_INICIAL_LABORAL));
                solicitudDTO.setFechaInicioTramite(formateador.parse(ha + " " + HORA_INICIAL_LABORAL));

            }
        } catch (ParseException pe) {
            LOG.error(pe.getMessage());
        }
    }

    private void createRfcsControlador(List<String> rfcs, DycpSolicitudDTO solicitudDTO) throws SIATException {
        DyctSolRfcControlDTO rfcCtrl = null;
        if (null != rfcs && !rfcs.isEmpty()) {
            for (String s : rfcs) {
                rfcCtrl = new DyctSolRfcControlDTO();
                rfcCtrl.setDycpSolicitudDTO(solicitudDTO);
                rfcCtrl.setRfc(s);
                registroConsultaService.createRfcControlador(rfcCtrl);
            }
        }
    }

    private void createAnexos(List<ArchivoVO> fileAnexos, DycpSolicitudDTO solicitudDTO,
            String urlGlobal) throws SIATException {
        if (fileAnexos != null && !fileAnexos.isEmpty()) {
            int tramite
                    = solicitudDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite();
            String ruta = urlGlobal + ConstantesDyCURL.TIPO_ARCHIVO_ANEXOS;
            for (int i = 0; i < fileAnexos.size(); i++) {
                fileAnexos.get(i).setTramite(tramite);
                fileAnexos.get(i).setNumControl(solicitudDTO.getNumControl());
                fileAnexos.get(i).setUrl(ruta);
                dyctAnexoService.insertarAnexo(fileAnexos.get(i));
            }
        }
    }

    private void createArchivosTemp(List<ArchivoVO> archivos, Integer tipoDoc, String urlGlobal,
            DyctSolicitudTempDTO solTemp) throws SIATException {
        /**
         * ConstantesDyC.TIPO_ARCHIVO_ARCHIVOS ConstantesDyC.TIPO_ARCHIVO_ANEXOS
         */
        String ruta = null;
        String folioServTemp = null;
        if (!archivos.isEmpty()) {
            folioServTemp = solTemp.getFolioServtemp().toString();
            ruta
                    = urlGlobal + (tipoDoc.equals(2) ? ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS : ConstantesDyCURL.TIPO_ARCHIVO_ANEXOS);
            for (int i = 0; i < archivos.size(); i++) {
                archivos.get(i).setNumControl(folioServTemp);
                archivos.get(i).setUrl(ruta);
                createArchivoTemp(archivos.get(i));
            }
        }
    }

    private String createCtaBancoTemp(DyctArchivoDTO ctaBanco, DyctSolicitudTempDTO solTemp) throws SIATException {
        File carpetaNumCtrl = null;
        StringBuilder ruta = new StringBuilder();
        ruta.append(ctaBanco.getUrl().substring(0, ctaBanco.getUrl().lastIndexOf("/temporal")));
        ruta.append("/temporal_" + solTemp.getFolioServtemp());
        carpetaNumCtrl = new File(ctaBanco.getUrl());
        if (carpetaNumCtrl.renameTo(new File(ruta.toString()))) {
            LOG.info("SE CREO CARPETA SOLICITUD TEMPORAL: " + ruta);
        } else {
            throw new SIATException("NO SE RENOMBRO LA CARPETA DE SOLICITUD TEMPORAL:" + ruta);
        }
        ArchivoVO cuenta = new ArchivoVO();
        cuenta.setNumControl(solTemp.getFolioServtemp().toString());
        cuenta.setNombreArchivo(ctaBanco.getNombreArchivo());
        cuenta.setId(0);
        cuenta.setDescripcion(ctaBanco.getDescripcion());
        cuenta.setUrl(ruta.toString().concat(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS));
        createArchivoTemp(cuenta);
        return ruta.toString();
    }

    private String renameFileContribuyente(String url, DycpSolicitudDTO solicitudDTO) throws SIATException {
        String carpetaCont = url.substring(0, url.lastIndexOf("/temporal"));
        File carpetaNumCtrl = new File(url);
        carpetaCont += "/".concat(solicitudDTO.getNumControl());
        if (carpetaNumCtrl.renameTo(new File(carpetaCont))) {
            LOG.info("SE REALIZO CORRECTAMENTE RENOMBRADO DE CARPETA " + carpetaCont);
        } else {
            throw new SIATException("NO SE RENOMBRO LA CARPETA " + carpetaCont + " YA QUE NO SE ENCONTRO: "
                    + carpetaNumCtrl.getPath());
        }
        return carpetaCont;
    }

    private void isSecAgro(CatalogoTO secAgro, DyctSolicitudTempDTO solTemp) {
        if (null != secAgro) {
            solTemp.setAplicaFacilidad(null != secAgro.getIdString() ? Integer.valueOf(secAgro.getIdString()) : null);
            solTemp.setInfoAgropecuario(null != secAgro.getDescripcion() ? Integer.valueOf(secAgro.getDescripcion())
                    : null);
            solTemp.setEstadoPatron(secAgro.getIdNum());
        }
    }

    private void createDocumentos(List<ArchivoVO> documentos, DycpSolicitudDTO solicitudDTO,
            String urlGlobal) throws SIATException {
        if (documentos != null && !documentos.isEmpty()) {
            DyctArchivoDTO archivo = null;
            DycpServicioDTO servicio = new DycpServicioDTO();
            servicio.setNumControl(solicitudDTO.getNumControl());
            String ruta = urlGlobal + ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS;
            for (ArchivoVO documento : documentos) {
                archivo = new DyctArchivoDTO();
                archivo.setNombreArchivo(documento.getNombreArchivo());
                archivo.setDescripcion(documento.getDescripcion());
                archivo.setUrl(ruta);
                archivo.setDycpServicioDTO(servicio);
                dycaDocumentoService.insertarDocumento(archivo);
            }
        }
    }

    private void createInconsistencias(List<CatalogoTO> paramInputTO,
            DycpSolicitudDTO solicitudDTO) throws SIATException {
        if (paramInputTO.isEmpty()) {
            return;
        }

        DyccInconsistDTO idInconsistencia = null;
        DycaSolInconsistDTO inconsistencia = null;
        for (CatalogoTO item : paramInputTO) {
            inconsistencia = new DycaSolInconsistDTO();
            idInconsistencia = new DyccInconsistDTO();
            idInconsistencia.setIdInconsistencia(item.getIdNum());
            inconsistencia.setDycpSolicitudDTO(solicitudDTO);
            inconsistencia.setDyccInconsistDTO(idInconsistencia);
            inconsistencia.setDescripcion(null != item.getDescripcion() ? item.getDescripcion() : "INCONSISTENCIA");
            dyccInconsistenciaService.insertarInconsistencia(inconsistencia);
        }
    }

    private void createInconsistenciaTemp(List<CatalogoTO> paramInputTO,
            DyctSolicitudTempDTO solTemp) throws SIATException {
        if (paramInputTO.isEmpty()) {
            return;
        }

        DyccInconsistDTO idInconsistencia = null;

        DyctServicioTempDTO folio = new DyctServicioTempDTO();
        folio.setFolioServTemp(solTemp.getFolioServtemp());
        DycaInconsistTempDTO inconsis = null;

        for (CatalogoTO item : paramInputTO) {
            inconsis = new DycaInconsistTempDTO();
            idInconsistencia = new DyccInconsistDTO();
            idInconsistencia.setIdInconsistencia(item.getIdNum());
            inconsis.setDyctServicioTempDTO(null);
            inconsis.setDyccInconsistDTO(idInconsistencia);
            inconsis.setDescripcion(null != item.getDescripcion() ? item.getDescripcion() : "INCONSISTENCIA");
            inconsis.setDyctServicioTempDTO(folio);
            dyccInconsistenciaService.createInconsitenciasTemp(inconsis);
        }
    }

    private void createCuentaBanco(DyctCuentaBancoDTO instCredit, DycpSolicitudDTO solicitudDTO,
            String urlGlobal) throws SIATException {
        DyctArchivoDTO archivo = new DyctArchivoDTO();
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl(solicitudDTO.getNumControl());
        archivo.setIdArchivo(solNumConsecutivoDAO.getIdArchivo());
        archivo.setNombreArchivo(instCredit.getDyctArchivoDTO().getNombreArchivo());
        archivo.setDescripcion(instCredit.getDyctArchivoDTO().getDescripcion());
        archivo.setUrl(urlGlobal + ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS);
        archivo.setDycpServicioDTO(servicio);
        dycaDocumentoService.createDocumentoEdoCta(archivo);
        instCredit.setDyctArchivoDTO(archivo);
        instCredit.setDycpSolicitudDTO(solicitudDTO);
        dyctCuentaBancoService.createCuentaBanco(instCredit);
    }

    @Override
    public Long countSolicitudesDictaminador(String numEmpleado, String estado, String numControl, String rfc) {
        return daoSolicitud.countSolicitudesDictaminador(numEmpleado, estado, numControl, rfc);
    }

    /**
     * Metodo de servicio para consulta de solicitudes de devolucion
     *
     * @return Lista dycpSolicitudAdministrarSolDTO
     *
     *
     */
    @Transactional(readOnly = true)
    public List<SolicitudAdministrarSolVO> buscarSolicitudesDictaminador(String numEmpleado, int estado) {
        List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO = new ArrayList<SolicitudAdministrarSolVO>();
        try {
            lSolicitudAdministrarSolVO = daoSolicitud.buscarSolicitudesDictaminador(numEmpleado, estado);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lSolicitudAdministrarSolVO;
    }

    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesDictaminadorPaginador(String numEmpleado, String estado,
            String numControl, String rfc,
            Paginador paginador) {
        List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO = new ArrayList<SolicitudAdministrarSolVO>();

        try {
            lSolicitudAdministrarSolVO
                    = daoSolicitud.buscarSolicitudesDictaminadorPaginador(numEmpleado, estado, numControl, rfc,
                            paginador);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }

        return lSolicitudAdministrarSolVO;
    }

    @Override
    public Long countSolicitudesAdministrador(int unidad, String numControl, String rfc) {
        return daoSolicitud.countSolicitudesAdministrador(unidad, numControl, rfc);
    }

    /**
     * Metodo de servicio para consulta de solicitudes de devolucion
     *
     * @return Lista dycpSolicitudAdministrarSolDTO
     *
     *
     */
    public List<SolicitudAdministrarSolVO> buscarSolicitudesAdministrador(int unidad) {

        List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO = new ArrayList<SolicitudAdministrarSolVO>();
        try {
            lSolicitudAdministrarSolVO = daoSolicitud.buscarSolicitudesAdministrador(unidad);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lSolicitudAdministrarSolVO;

    }

    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesAdministradorPaginador(int unidad, String numControl,
            String rfc, Paginador paginador) {
        List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO = new ArrayList<SolicitudAdministrarSolVO>();

        try {
            lSolicitudAdministrarSolVO
                    = daoSolicitud.buscarSolicitudesAdministradorPaginador(unidad, numControl, rfc, paginador);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }

        return lSolicitudAdministrarSolVO;
    }

    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesAdministradorFacultades(int unidad, String numempleado) {

        List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO = new ArrayList<SolicitudAdministrarSolVO>();
        try {
            lSolicitudAdministrarSolVO = daoSolicitud.buscarSolicitudesAdministradorFacultades(unidad, numempleado);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lSolicitudAdministrarSolVO;

    }

    /**
     * Metodo de servicio para consulta de solicitudes de devolucion
     *
     * @return Lista dycpSolicitudAdministrarSolDTO
     *
     *
     */
    @Override
    public List<SolicitudAdministrarSolVO> buscarSolicitudesFiscalizador(int unidad) {

        List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO = new ArrayList<SolicitudAdministrarSolVO>();
        try {
            lSolicitudAdministrarSolVO = daoSolicitud.buscarSolicitudesFiscalizador(unidad);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lSolicitudAdministrarSolVO;

    }

    /**
     * LADP--> Consulta Devoluciones Simultaneas
     */
    @Override
    public String consultaDevolucionSimultaneas(String rfc, int idImpuesto, int idConcepto, int idEjercicio,
            int idPeriodo) {
        return daoSolicitud.consultaDevolucionSimultaneas(rfc, idConcepto, idEjercicio, idPeriodo);
    }

    /**
     * Metodo de servicio para consulta de Facturas
     *
     * @return Lista ldycpSolicitudDevolucionDTO
     *
     *
     */
    @Override
    public List<DycpSolicitudDevolucionDTO> buscarFacturas(String rfcProveedor) {
        List<DycpSolicitudDevolucionDTO> ldycpSolicitudDevolucionDTO = new ArrayList<DycpSolicitudDevolucionDTO>();
        try {
            ldycpSolicitudDevolucionDTO = daoSolicitud.buscarFacturas(rfcProveedor);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return ldycpSolicitudDevolucionDTO;
    }

    @Override
    public boolean buscarNumFactura(String numFactura, String rfcProveedor) throws SIATException {
        boolean existe = false;
        try {
            existe = dyctFacturaReqDAO.existe(numFactura, rfcProveedor);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }

        return existe;
    }

    @Override
    public boolean insertaFactura(DycpSolicitudDevolucionDTO dycpSolicitudDevolucionDTO) {
        boolean inserto = false;
        try {
            inserto = daoSolicitud.insertaFactura(dycpSolicitudDevolucionDTO);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return inserto;
    }

    /**
     * Metodo que devuelve los rfc con la misma cuenta cable
     *
     * @param cuentaBancaria
     * @return
     */
    @Override
    public Integer obtenRFCBancario(String cuentaBancaria, String rfc) {
        Integer listaRFC = ConstantesDyCNumerico.VALOR_0;
        try {
            listaRFC = daoSolicitud.obtenRFCBancario(cuentaBancaria, rfc);
        } catch (DataAccessException dae) {
            LOG.error(dae.getMessage());
        }
        return listaRFC;
    }

    /**
     * mabc
     */
    @Override
    public boolean existenFacturas(String numControl, String rfcProveedor) throws SIATException {
        boolean existFac = false;
        try {
            existFac = dyctFacturaReqDAO.existeFacturasXNumControl(numControl, rfcProveedor);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }

        return existFac;
    }

    /**
     * mabc
     */
    /**
     * Metodo de servicio para consulta de Facturas
     *
     * @return Lista ldycpSolicitudDevolucionDTO
     *
     *
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public List<DyctFacturaReqDTO> buscarFacturas(String numControl, String rfcProveedor) throws SIATException {
        List<DyctFacturaReqDTO> ldyctFacturaReqDTO = new ArrayList<DyctFacturaReqDTO>();
        try {
            ldyctFacturaReqDTO = dyctFacturaReqDAO.consultaFacturasXNumControl(numControl, rfcProveedor);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return ldyctFacturaReqDTO;
    }

    /**
     * mabc
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean borrarFactura(String numFactura) {
        boolean exitooperacion = false;

        try {
            dyctFacturaReqDAO.borrar(numFactura);

            exitooperacion = Boolean.TRUE;

        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return exitooperacion;

    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {SIATException.class})
    public boolean insertarFacturas(DyctDocumentoDTO dyctDocumentoDTO, String usuarioSolvento,
            List<DyctFacturaReqDTO> facturas, String rfcProveedor,
            String nombreCompleto) throws SIATException {
        boolean inserto = false;

        try {

            DyctDocumentoDTO dyctDocumentoDTO1 = new DyctDocumentoDTO();
            dyctDocumentoDTO1.setNumControlDoc(dyctDocumentoDTO.getNumControlDoc());

            DyctReqConfProvDTO dyctReqConfProvDTO = new DyctReqConfProvDTO();
            dyctReqConfProvDTO.setDyctDocumentoDTO(dyctDocumentoDTO1);
            dyctReqConfProvDTO.setRfcProveedor(rfcProveedor);

            dyctDocumentoDAO.insertar(dyctDocumentoDTO);
            dyctReqConfProvDAO.insertar(dyctReqConfProvDTO);

            for (DyctFacturaReqDTO dyctFacturaReqDTO : facturas) {
                dyctFacturaReqDAO.insertar(dyctFacturaReqDTO);
            }

            daoSolicitud.actualizarStatus(ConstantesDyCNumerico.VALOR_7,
                    dyctDocumentoDTO.getDycpServicioDTO().getNumControl());

            registrarPistaAuditoria(dyctDocumentoDTO, nombreCompleto);

            inserto = true;

        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException("Error al guardar las facturas", e);
        }

        return inserto;
    }

    @Override
    @Transactional(readOnly = true)
    public ParamOutputTO<SolicitudDevTempVO> completarSolicitudTemp(SolicitudDevolucionRegistroVO paramInput) throws SIATException {
        SolicitudDevTempVO solicitudTemp = new SolicitudDevTempVO();
        Integer folio = null;
        try {
            folio = Integer.parseInt(paramInput.getIdDev());
        } catch (NumberFormatException e) {
            LOG.error(e.getMessage());
        }

        try {
            solicitudTemp.setSolContribTemp(contribuyenteService.findContrinbuyenteTemp(folio).getOutput());
            solicitudTemp.setSolDeclaracionTemp(dyctDeclaracionService.findDeclaracionTemp(folio).getOutput());
            solicitudTemp.setSolicitudTemp(daoSolicitud.findSolicitudTemp(folio));
            solicitudTemp.setInconsistTemp(dyccInconsistenciaService.findInconsistenciasTemp(folio).getOutputs());
            solicitudTemp.setArchivos(getArchivosTemp(folio));
        } catch (Exception e) {
            throw new SIATException("ERROR AL RECUPERAR LA SOLICITUD TEMPORAL FOLIOTEMP " + folio, e);
        }
        return new ParamOutputTO<SolicitudDevTempVO>(solicitudTemp);
    }

    private List<ArchivoVO> getArchivosTemp(int folio) throws SIATException {
        List<ArchivoVO> documentosTemp = new ArrayList<ArchivoVO>();
        List<DyctArchivoTempDTO> findArchivoSolTemp = daoArchivoTemp.findArchivoSolTemp(folio);
        ArchivoVO documento = null;
        for (DyctArchivoTempDTO dyctArchivoTempDTO : findArchivoSolTemp) {
            documento = new ArchivoVO();
            documento.setNombreArchivo(dyctArchivoTempDTO.getNombreArchivo());
            documento.setUrl(dyctArchivoTempDTO.getUrl());
            documento.setNombre(dyctArchivoTempDTO.getDescripcion());
            documento.setDescripcion(dyctArchivoTempDTO.getDescripcion());
            documento.setId(dyctArchivoTempDTO.getTipoArchivo());
            documento.setUrlPlantillaAnexo(getUrlPlantilla(dyctArchivoTempDTO.getTipoArchivo()));
            documentosTemp.add(documento);
        }
        return documentosTemp;
    }

    private String getUrlPlantilla(int idAnexo) throws SIATException {
        DyccMatrizAnexosDTO anexo = null;
        if (idAnexo != ConstantesDyCNumerico.VALOR_0) {
            try {
                anexo = dyccMatrizAnexosDAO.getAnexo(idAnexo);
            } catch (SQLException e) {
                throw new SIATException(e);
            }
            return anexo.getUrl();
        }
        return null;
    }

    public void registrarPistaAuditoria(DyctDocumentoDTO dyctDocumentoDTO,
            String nombreCompleto) throws SIATException {

        Map<String, String> reemplaceMensajes = new HashMap<String, String>();

        String fechaHora = "";

        Date fechaActual = new Date();

        DateFormat fechaHoraF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaHora = fechaHoraF.format(fechaActual);

        reemplaceMensajes.put("<nombreDeLaPlantilla>",
                "Requerimiento para confirmación de operaciones con proveedores");
        reemplaceMensajes.put("<numeroDeControlDeLaSolicitud>", dyctDocumentoDTO.getDycpServicioDTO().getNumControl());
        reemplaceMensajes.put("<nombreDelEmpleado>", nombreCompleto);
        reemplaceMensajes.put("<fechaDelSistema>", fechaHora);

        PistaAuditoriaVO pistaAuditoria = new PistaAuditoriaVO();

        pistaAuditoria.setRemplaceMensajes(reemplaceMensajes);
        pistaAuditoria.setIdGrupoOperacion(ConstantesDyCNumerico.VALOR_32);
        pistaAuditoria.setIdProceso(Procesos.DYC00005);
        pistaAuditoria.setIdMensaje(ConstantesDyCNumerico.VALOR_110);
        pistaAuditoria.setMovimiento(ConstantesDyCNumerico.VALOR_533);
        pistaAuditoria.setIdentificador(dyctDocumentoDTO.getDycpServicioDTO().getNumControl());

        servicePistasAuditoria.registrarPistaAuditoria(pistaAuditoria);

    }

    private void createArchivoTemp(ArchivoVO input) throws SIATException {
        DyctArchivoTempDTO dyctArchivoTempDTO = new DyctArchivoTempDTO();
        DyctServicioTempDTO folioTemp = new DyctServicioTempDTO();
        folioTemp.setFolioServTemp(Integer.valueOf(input.getNumControl()));
        dyctArchivoTempDTO.setDyctServicioTempDTO(folioTemp);
        dyctArchivoTempDTO.setNombreArchivo(input.getNombreArchivo());
        dyctArchivoTempDTO.setTipoArchivo(input.getId());
        dyctArchivoTempDTO.setDescripcion(null != input.getDescripcion() ? input.getDescripcion() : input.getNombre());
        dyctArchivoTempDTO.setUrl(input.getUrl());
        try {
            daoArchivoTemp.createArchivoTemp(dyctArchivoTempDTO);
        } catch (DataAccessException e) {
            throw new SIATException("ERROR AL INSERTAR EN DYCT_ARCHIVOTEMP: " + e);
        }
    }

    @Override
    public int actualizarStatus(Integer status, String numControl) throws SIATException {
        return daoSolicitud.actualizarStatus(status, numControl);
    }

    @Override
    public int actualizarStatusPago(Integer status, String numControl) throws SIATException {
        return daoSolicitud.actualizarStatusPago(status, numControl);
    }
    
     @Override
     @Transactional(rollbackFor = SIATException.class, readOnly = false)
    public void actualizarIdEstadoSol(final int idEstadoSol, final String numControl, final String numDoc, final int idEstadoReq)throws SIATException {
         daoSolicitud.actualizarIdEstadoSol(idEstadoSol, numControl);
         daoDocumento.dyctDocumentoupdateIdEstadoReq(idEstadoReq, numDoc);
    }
    
    @Override
    @Transactional(rollbackFor = SIATException.class, readOnly = false)
    public void actualizarIdEstadoSolDic(final int idEstadoSol, final String numControlDictaminar,final int idEstadoReq)throws SIATException
    {
         daoSolicitud.actualizarIdEstadoSolDic(idEstadoSol, numControlDictaminar);
         daoDocumento.dyctDocumentoupdateIdEstadoReqDic(idEstadoReq, numControlDictaminar);
    }

    public DetalleIcepService getBussinesDelegate() {
        return bussinesDelegate;
    }

    public void setBussinesDelegate(DetalleIcepService bussinesDelegate) {
        this.bussinesDelegate = bussinesDelegate;
    }

    public DycpSolicitudDAO getDaoSolicitud() {
        return daoSolicitud;
    }

    public void setDaoSolicitud(DycpSolicitudDAO daoSolicitud) {
        this.daoSolicitud = daoSolicitud;
    }

}
