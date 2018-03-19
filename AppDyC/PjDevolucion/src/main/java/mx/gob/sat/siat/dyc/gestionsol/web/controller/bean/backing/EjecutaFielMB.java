package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.avisocomp.service.AvisoCompensacionService;
import mx.gob.sat.siat.dyc.avisocomp.util.ConstantesAvisoComp;
import mx.gob.sat.siat.dyc.avisocomp.util.ValidadorAvisoCompensacion;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosAvisoFieldVO;
import mx.gob.sat.siat.dyc.casocomp.util.ValidadorCasoComp;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.generico.util.common.Selladora;
import mx.gob.sat.siat.dyc.generico.util.common.UsuarioFirmadoVO;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.AcuseReciboService;
import mx.gob.sat.siat.dyc.gestionsol.service.registrarinformacion.SolventarRequerimientoService;
import mx.gob.sat.siat.dyc.gestionsol.util.recurso.UtileriasGestionSol;
import mx.gob.sat.siat.dyc.gestionsol.vo.solventacion.SolventacionRequerimientoVO;
import mx.gob.sat.siat.dyc.registro.dto.InfoCuentaClabeFieldDTO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.InformacionCuentaClabeService;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.AdicionarSolicitudMB;
import mx.gob.sat.siat.dyc.service.DyccAprobadorService;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.AsignarException;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesFIEL;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;

/**
 * Managed Bean para la ejecución de la Firma Fiel
 *
 * @author José Adrian Luciano Borbonio
 * @author Jesus Alfredo Hernandez Orozco
 * @since 22/08/2013
 */
@ManagedBean(name = "ejecutaFIELMB")
@SessionScoped
public class EjecutaFielMB {

    private boolean banderaOTP;
    private boolean esPersonaFisica;

    private String ambiente;
    private String cadenaOriginal;
    private String indexContribuyente;
    private String logout;
    private String message;
    private String numControlDoc;
    private String urlFirmado;
    private String folio;
    private String mensajeDialogo;
    private String enlacePortalConsultaByqr;

    private boolean folioSecuenciaB;
    private String folioSecuencia;

    private FacesMessage msg;
    private UsuarioFirmadoVO usuarioFirmado;
    private SolventacionRequerimientoVO objetoSolventar;

    @ManagedProperty(value = "#{aprobarDocMB}")
    private AprobarDocumentoMB aprobar;

    @ManagedProperty(value = "#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty(value = "#{acuseReciboService}")
    private AcuseReciboService acuseReciboService;

    @ManagedProperty(value = "#{avisoCompensacionService}")
    private AvisoCompensacionService avisoCompensacionService;

    @ManagedProperty(value = "#{validadorAvisoCompensacion}")
    private ValidadorAvisoCompensacion validadorAvisoCompensacion;

    @ManagedProperty("#{solventarRequerimientoService}")
    private SolventarRequerimientoService solventarRequerimientoService;

    @ManagedProperty("#{informacionCuentaClabeService}")
    private InformacionCuentaClabeService informacionCuentaClabeService;

    @ManagedProperty(value = "#{adicionarSolicitudMB}")
    private AdicionarSolicitudMB solDevPage;

    @ManagedProperty("#{dyccAprobadorService}")
    private DyccAprobadorService aprobadorService;
    
    @ManagedProperty(value = "#{validadorCasoComp}")
    private ValidadorCasoComp validadorCasoComp;

    private static final Logger LOG = Logger.getLogger(EjecutaFielMB.class.getName());

    private static final int APROBAR_DOCUMENTO = 1;
    private static final int REGISTRO_DE_DEVOLUCION = 2;
    private static final int AVISO_DE_COMPENSACION = 4;
    private static final int COMUNICADO_ABONO_NO_EFECTUADO = 6;

    private static final String URL_APPLET = "/resources/applet/";
    private static final String APROBAR_DOCUMENTO_PAGE = "bandejaDocsAprobador";
    private static final String APROBAR_DOCUMENTO_ABONO_NO_EFECTUADO = "regresarBandejaAbonoNoEfectuado";
    private static final String SOLICITUD_PAGE = "solicitud";
    private static final long LIMITE_FOLIO = 999999999;

    private static final String ERROR = "DC-E";
    private static final String FOLIO = "Folio-->";

    private static final String DLG_ASIG = "dlgAsigError.show();";
    private static final String DLG_ERROR = "dialogEr.show();";

    private boolean firmaBandera = Boolean.FALSE;

    public EjecutaFielMB() {
        msg = new FacesMessage("");
    }

    @PostConstruct
    public void init() {
        usuarioFirmado = new UsuarioFirmadoVO();
        usuarioFirmado.setAccesoUsr(new AccesoUsr());
        usuarioFirmado.setTramiteDTO(new TramiteDTO());
        usuarioFirmado.setDatosAvisoFieldVO(new DatosAvisoFieldVO());
        usuarioFirmado.setPath(JSFUtils.getExternalContext().getRequestContextPath() + URL_APPLET);
        cargarDatosDeConfiguracion();
        message = null;
        logout = null;
        firmaBandera = Boolean.FALSE;
        folioSecuenciaB = Boolean.FALSE;
    }

    private void cargarDatosDeConfiguracion() {
        Properties propiedades = new Properties();
        FileInputStream archivo = null;
        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
            ambiente = propiedades.getProperty("ambiente");
            indexContribuyente = propiedades.getProperty("indexContribuyente");
            urlFirmado = propiedades.getProperty("urlFirmado");
            enlacePortalConsultaByqr = propiedades.getProperty("enlacePortalConsultaByqr");

        } catch (Exception e) {
            LOG.error("Error al cargar el archivo de configuracion: " + e);
        } finally {
            try {
                archivo.close();
            } catch (Exception e) {
                LOG.error("Error al cerrar el archivo de configuracion: " + e);
            }
        }
    }

    public String openView() throws SIATException, AsignarException {
        obtieneCadenaSelloFirma();

        if (usuarioFirmado.getProceso().equals(APROBAR_DOCUMENTO)) {
            aprobar.datosFirma(usuarioFirmado.getCadenaOriginal(), usuarioFirmado.getSelloDigital(),
                    usuarioFirmado.getFirmaDigital(), enlacePortalConsultaByqr);

            return APROBAR_DOCUMENTO_PAGE;
        }

        if (usuarioFirmado.getProceso().equals(REGISTRO_DE_DEVOLUCION)) {
            insertarSolicitud();
        }

        if (usuarioFirmado.getProceso().equals(AVISO_DE_COMPENSACION)) {
            setFolioSecuencia(insertarAvisoDeCompensacion());
           
        }

        if (usuarioFirmado.getProceso().equals(ConstantesDyCNumerico.VALOR_3)) {
            solventarRequerimiento();
        }

        if (usuarioFirmado.getProceso().equals(ConstantesDyCNumerico.VALOR_5)) {
            actualizarCuentaClabe();
        }

        if (usuarioFirmado.getProceso().equals(ConstantesDyCNumerico.VALOR_7)) {
            insertarSolicitudAutomaticas();
        }

        if (usuarioFirmado.getProceso().equals(COMUNICADO_ABONO_NO_EFECTUADO)) {
            apruebaComunicadoAbonoNoEfectuado();

            return APROBAR_DOCUMENTO_ABONO_NO_EFECTUADO;
        }

        return null;
    }

    private void apruebaComunicadoAbonoNoEfectuado() {
        aprobarDocumento();
        verificaAprobador();
    }

    /**
     * Inserta los datos de la solicitud y genera el acuse de recibo.
     */
    private void insertarSolicitud() throws SIATException, AsignarException {
        try {
            Map<String, Object> datos = ValidaDatosSolicitud.generaCadenaySello(this.usuarioFirmado.getTramiteDTO());
            dycpSolicitudService.insertaSolicitud(this.usuarioFirmado.getTramiteDTO(),
                    Utilerias.isNull(datos.get("sello").toString()),
                    Utilerias.isNull(datos.get("cadena").toString())).getOutput();

        } catch (IOException e) {
            folio = ERROR + (int) (Math.random() * LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
            mensajeDialogo = "Ocurrió un error al generar la cadena original y/o el sello digital.";
            LOG.error(FOLIO + folio + ConstantesErrorTextos.TEXTO_1_ERROR_DAO + mensajeDialogo);
            ManejadorLogException.getTraceError(e);
            solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute(DLG_ERROR);
        } catch (AsignarException ae) {

            mensajeDialogo = "No existen dictaminadores disponibles para asignar en esta administración.";

            ManejadorLogException.getTraceError(ae);

            if (usuarioFirmado.getProceso().equals(ConstantesDyCNumerico.VALOR_7)) {
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.FALSE);
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsigFolio(Boolean.FALSE);
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsigConsulta(Boolean.TRUE);
            } else {
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.FALSE);
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsigFolio(Boolean.TRUE);
            }
            RequestContext.getCurrentInstance().execute(DLG_ASIG);
        } catch (SIATException e) {
            folio = ERROR + (int) (Math.random() * LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
            mensajeDialogo = "Ocurrió un error al generar el acuse de su solicitud.";
            LOG.error(FOLIO + folio + ConstantesErrorTextos.TEXTO_1_ERROR_DAO + mensajeDialogo);
            ManejadorLogException.getTraceError(e);
            solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute(DLG_ERROR);
        }
    }

    /**
     * Inserta los datos de la solicitud y genera el acuse de recibo.
     */
    private void insertarSolicitudAutomaticas() throws SIATException, AsignarException {
        try {
            Map<String, Object> datos = ValidaDatosSolicitud.generaCadenaySello(this.usuarioFirmado.getTramiteDTO());
            dycpSolicitudService.insertaSolicitudISR(this.usuarioFirmado.getTramiteDTO(),
                    Utilerias.isNull(datos.get("sello").toString()),
                    Utilerias.isNull(datos.get("cadena").toString())).getOutput();

        } catch (IOException e) {
            folio = ERROR + (int) (Math.random() * LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
            mensajeDialogo = "Ocurrió un error al generar la cadena original y/o el sello digital.";
            LOG.error(FOLIO + folio + ConstantesErrorTextos.TEXTO_1_ERROR_DAO + mensajeDialogo);
            ManejadorLogException.getTraceError(e);
            solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute(DLG_ERROR);
        } catch (AsignarException ae) {

            mensajeDialogo = "No existen dictaminadores disponibles para asignar en esta administración.";

            ManejadorLogException.getTraceError(ae);

            if (usuarioFirmado.getProceso().equals(ConstantesDyCNumerico.VALOR_7)) {
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.FALSE);
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsigFolio(Boolean.FALSE);
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsigConsulta(Boolean.TRUE);
            } else {
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.FALSE);
                solDevPage.getFlagsSolicitud().setShowMessageErrorAsigFolio(Boolean.TRUE);
            }
            RequestContext.getCurrentInstance().execute(DLG_ASIG);
        } catch (SIATException e) {
            folio = ERROR + (int) (Math.random() * LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
            mensajeDialogo = "Ocurrió un error al generar el acuse de su solicitud.";
            LOG.error(FOLIO + folio + ConstantesErrorTextos.TEXTO_1_ERROR_DAO + mensajeDialogo);
            ManejadorLogException.getTraceError(e);
            solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute(DLG_ERROR);
        }
    }

    /**
     * Muestra el aviso de compensacion y genera el reporte de avisos
     */
    private String insertarAvisoDeCompensacion() throws SIATException {
        String secuencia = null;
        DyctSaldoIcepDTO dTO = null;

        try {
            existeACRegistrado();
            dTO
                    = avisoCompensacionService.encontrarIcep(this.usuarioFirmado.getAccesoUsr().getUsuario(), this.usuarioFirmado.getDatosAvisoFieldVO().getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getIdConcepto(),
                            this.usuarioFirmado.getDatosAvisoFieldVO().getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO().getIdEjercicio(),
                            this.usuarioFirmado.getDatosAvisoFieldVO().getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO().getIdPeriodo());

            if (dTO != null && !dTO.getBoId().isEmpty()
                    && this.usuarioFirmado.getDatosAvisoFieldVO().getDycpCompensacionDTO().getDycpAvisoCompDTO().getDyccTipoAvisoDTO().getIdTipoAviso()
                    == ConstantesAvisoComp.TipoAviso.NORMAL) {
                secuencia = dTO.getBoId();
                setFolioSecuenciaB(Boolean.TRUE); 
                return secuencia;
            }

            secuencia
                    = avisoCompensacionService.insertarCompensacion(this.usuarioFirmado.getDatosAvisoFieldVO(), Utilerias.isNull(usuarioFirmado.getCadenaOriginal()),
                            Utilerias.isNull(usuarioFirmado.getSelloDigital()));
          setFolioSecuenciaB(Boolean.TRUE);   
        }catch(SIATException e) {
             setFolioSecuenciaB(Boolean.FALSE);
            folio = ERROR + (int) (Math.random() * LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
            mensajeDialogo = e.getMessage();
            LOG.error(FOLIO + folio + ConstantesErrorTextos.TEXTO_1_ERROR_DAO + mensajeDialogo);
            ManejadorLogException.getTraceError(e);
            solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute(DLG_ERROR);
            
        } catch (Exception e) {
            folio = ERROR + (int) (Math.random() * LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
            mensajeDialogo = "Ocurrió un error al generar el Aviso  de Compensación";
            LOG.error(FOLIO + folio + ConstantesErrorTextos.TEXTO_1_ERROR_DAO + mensajeDialogo);
            ManejadorLogException.getTraceError(e);
            solDevPage.getFlagsSolicitud().setShowMessageErrorAsig(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute(DLG_ERROR);
        }

        return secuencia;
    }

    /**
     * Inserta y actualiza los datos para solventar el requerimiento.
     */
    private void solventarRequerimiento() {
        try {
            solventarRequerimientoService.actualizarInformacion(objetoSolventar);

        } catch (Exception e) {
            msg.setDetail("Ocurrió un error al solventar el requerimiento, intente más tarde.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            LOG.error("Error al solventar el requerimiento: " + e);
        }
    }

    /**
     * Inserta los registros para hacer el proceso de actualizacion de cuenta
     * clabe.
     */
    private void actualizarCuentaClabe() {
        try {
            informacionCuentaClabeService.actualizarCuentaClabe(usuarioFirmado, objetoSolventar);
            acuseReciboService.generarAcuseReciboReimpresion(ConstantesDyCNumerico.VALOR_2, objetoSolventar.getNumControl(), usuarioFirmado.getInfoCuentaClabeFieldDTO().getRfc(),
                    ConstantesDyC.NO_ADMON, Boolean.TRUE, objetoSolventar.getNumControlDoc());
        } catch (Exception e) {
            msg.setDetail("Ocurrió un error al modificar la cuenta clabe, intente más tarde.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            LOG.error("Error al generar el acuse de recibo de la cuenta clabe: " + e);
        }
    }

    private void aprobarDocumento() {
        aprobar.datosFirma(
                usuarioFirmado.getCadenaOriginal(),
                usuarioFirmado.getSelloDigital(),
                usuarioFirmado.getFirmaDigital(), enlacePortalConsultaByqr
        );
    }

    private void verificaAprobador() {
        int numEmpleadoAprob = 0;
        String numControlDocumento = "";
        if (aprobar != null && aprobar.getBandejaDocumentosSolDTO() != null) {

            numEmpleadoAprob = aprobar.getBandejaDocumentosSolDTO().getNumEmpleadoAprob();
            numControlDocumento = aprobar.getNumControlDoc();

            if (numEmpleadoAprob == 0) {
                LOG.info(" num de cntrol doc1 : " + numControlDocumento);
                numEmpleadoAprob = Integer.parseInt(usuarioFirmado.getAccesoUsr().getNumeroEmp());
            }
        }
        LOG.info(" aprobador del doc  : " + numEmpleadoAprob);
        LOG.info(" num de cntrol doc2 : " + numControlDocumento);

        try {
            aprobadorService.actualizarAprobadorDocumento(numEmpleadoAprob, numControlDocumento);

        } catch (Exception e) {
            LOG.error(String.format(
                    " No pudo asignar el aprobador al documento - [ numEmp : %d ][ numCtrlDoc : %s ] ", numEmpleadoAprob, numControlDocumento
            )
            );
            LOG.error(e.getCause());
        }
    }

    public void obtieneCadenaSelloFirma() {
        Selladora selladora = new Selladora();
        try {
            usuarioFirmado.setCadenaOriginal(new String((JSFUtils.getExternalContext().getRequestParameterMap().get(ConstantesFIEL.PARAMETRO_CADENA_ORIGINAL)).getBytes("ISO-8859-1"),
                    "UTF-8"));
            usuarioFirmado.setFirmaDigital(JSFUtils.getExternalContext().getRequestParameterMap().get(ConstantesFIEL.PARAMETRO_FIRMA_DIGITAL));
            usuarioFirmado.setSelloDigital(Utilerias.isNull(selladora.retornarParametros("1",
                    usuarioFirmado.getCadenaOriginal())));
        } catch (IOException e) {
            LOG.error("Error al obtener sello digital ó cadena" + e);
        }
    }

    public void cerrarFolio() {
        try {
            acuseReciboService.mostrarReporteAvisosDownload(getFolioSecuencia(),
                    this.usuarioFirmado.getDatosAvisoFieldVO().getDycpCompensacionDTO().getDycpServicioDTO().getRfcContribuyente(),
                    ConstantesDyC.NO_ADMON);
        } catch (SIATException ex) {
            LOG.error("Error al mostrar el archivo" + ex);
        } catch (Exception e) {
            LOG.error("Error al mostrar el archivo");
            LOG.error(e);
        }
        setFolioSecuenciaB(Boolean.FALSE);
        setFirmaBandera(Boolean.TRUE);
    }

    public String regrersar() {
        String regreso = "";
        switch (usuarioFirmado.getProceso()) {
            case APROBAR_DOCUMENTO:
                regreso = "regresarAComentarios";
                break;
            case REGISTRO_DE_DEVOLUCION:
                regreso = SOLICITUD_PAGE;
                break;
            case ConstantesDyCNumerico.VALOR_3:
                regreso = "regresarASolventar";
                break;
            case AVISO_DE_COMPENSACION:
                regreso = "avisoRegresoFiel";
                break;
            case ConstantesDyCNumerico.VALOR_5:
                regreso = "regresarACuentaBancaria";
                break;
            case ConstantesDyCNumerico.VALOR_7:
                regreso = "regresarConsultarDevCont";
                break;
            default:
                break;
        }
        return regreso;
    }

    /**
     * Este metodo se utiliza cuando se intenta hacer un firmado para el caso de
     * uso de aviso de compensación.
     *
     * @param proceso
     * @param datosAvisoField
     */
    public void setDatosAvisoCompensacion(Integer proceso, DatosAvisoFieldVO datosAvisoField,
            AccesoUsr accesoUsr) throws SIATException {
        UtileriasGestionSol obj = new UtileriasGestionSol();
        folioSecuenciaB = Boolean.FALSE;
        usuarioFirmado.setAccesoUsr(accesoUsr);
        usuarioFirmado.setProceso(proceso);
        usuarioFirmado.setDatosAvisoFieldVO(datosAvisoField);

        cadenaOriginal
                = obj.crearCadenaOriginal(validadorAvisoCompensacion.datosCadenaOriginal(this.usuarioFirmado.getDatosAvisoFieldVO().getDycpCompensacionDTO(),
                        this.usuarioFirmado.getDatosAvisoFieldVO().getPersona(),
                        this.usuarioFirmado.getDatosAvisoFieldVO().getClaveAdm()),
                        usuarioFirmado);

        if (usuarioFirmado.getDatosAvisoFieldVO().getPersona().getPersona().getPersonaIdentificacion().getTipoPersona().equals("F")) {
            esPersonaFisica = Boolean.TRUE;
        }
        setCadenaOriginal(cadenaOriginal);
        if (accesoUsr.getMenu().equals("1") && esPersonaFisica) {
            banderaOTP = Boolean.TRUE;
        }
    }

    /**
     * Este metodo se utiliza cuando se intenta hacer un firmado para el caso de
     * uso de solicitud de devolucion del contribuyente.
     *
     * @param proceso
     * @param tramite
     * @param cadenaOriginal
     */
    public void setDatosSolicitud(Integer proceso, TramiteDTO tramite, String cadenaOriginal,
            AccesoUsr accesoUsr) throws SIATException {
        usuarioFirmado.setAccesoUsr(accesoUsr);
        usuarioFirmado.setProceso(proceso);
        usuarioFirmado.setTramiteDTO(tramite);
        usuarioFirmado.setCadenaOriginal(cadenaOriginal);

        if (usuarioFirmado.getTramiteDTO().getPersona().getPersonaIdentificacion().getTipoPersona().equals("F")) {
            esPersonaFisica = Boolean.TRUE;
        }
        setCadenaOriginal(cadenaOriginal);
        if (accesoUsr.getMenu().equals("1") && esPersonaFisica) {
            banderaOTP = Boolean.TRUE;
        }
    }

    /**
     * Este metodo se utiliza cuando se intenta hacer un firmado para el caso de
     * uso de aprobar documento.
     *
     * @param accesoUsr
     * @param parametrosCadena
     */
    public void setDatosAprobarDocumento(Map<String, Object> parametrosCadena, AccesoUsr accesoUsr) {
        UtileriasGestionSol obj = new UtileriasGestionSol();
        String cadena = obj.crearCadenaOriginal(parametrosCadena, usuarioFirmado);
        usuarioFirmado.setAccesoUsr(accesoUsr);
        aprobar.setAccesoUsr(accesoUsr);
        usuarioFirmado.setProceso(APROBAR_DOCUMENTO);
        usuarioFirmado.setCadenaOriginal(cadena);
        setCadenaOriginal(cadena);
    }

    /**
     * Este metodo se utiliza cuando se intenta aprobar la emisión de un
     * comunicado por Abono no efectuado.
     *
     * @param accesoUsr
     * @param parametrosCadena
     */
    public void setDatosComunicadoAbonoNoEfectuado(Map<String, Object> parametrosCadena, AccesoUsr accesoUsr) {
        UtileriasGestionSol obj = new UtileriasGestionSol();
        String cadena = obj.crearCadenaOriginal(parametrosCadena, usuarioFirmado);
        usuarioFirmado.setAccesoUsr(accesoUsr);
        aprobar.setAccesoUsr(accesoUsr);
        usuarioFirmado.setProceso(COMUNICADO_ABONO_NO_EFECTUADO);
        usuarioFirmado.setCadenaOriginal(cadena);
        setCadenaOriginal(cadena);
    }

    /**
     * Método para solventar el requerimiento
     *
     * @param accesoUsr
     */
    public void setDatosSolventarRequerimiento(AccesoUsr accesoUsr, SolventacionRequerimientoVO objetoASolventar,
            String numControlDocumento) throws SIATException {
        this.objetoSolventar = objetoASolventar;
        this.numControlDoc = numControlDocumento;
        usuarioFirmado.setAccesoUsr(accesoUsr);
        aprobar.setAccesoUsr(accesoUsr);
        usuarioFirmado.setProceso(ConstantesDyCNumerico.VALOR_3);
        verificarOTP(accesoUsr, objetoASolventar.getNumControl());
    }

    /**
     * Metodo para actualizar cuenta clabe
     *
     * @param proceso
     * @param accesoUsr
     */
    public void setDatosCuentaClabe(Integer proceso, InfoCuentaClabeFieldDTO infoCuentaClabeFieldDTO,
            AccesoUsr accesoUsr) throws SIATException {
        usuarioFirmado.setProceso(proceso);
        usuarioFirmado.setAccesoUsr(accesoUsr);
        usuarioFirmado.setInfoCuentaClabeFieldDTO(infoCuentaClabeFieldDTO);
        verificarOTP(accesoUsr, infoCuentaClabeFieldDTO.getDyctCuentaBanco().getDycpSolicitudDTO().getNumControl());
    }

    /**
     * Valida SI un contribuyente ha ingresado a través del portal de
     * contribuyentes y es persona física para mostrar el tipo de firmado por
     * OTP.
     *
     * @param accesoUsr
     */
    private void verificarOTP(AccesoUsr accesoUsr, String numControl) throws SIATException {
        LOG.info("JAHO - MENU: " + accesoUsr.getMenu() + ", Tipo de persona: " + accesoUsr.getIdTipoPersona());
        if (accesoUsr.getMenu().equals("1")) {
            if (solventarRequerimientoService.encontrarContribuyente(numControl).getRolPf().equals(1)) {
                banderaOTP = Boolean.TRUE;
            } else {
                banderaOTP = Boolean.FALSE;
            }
        } else {
            banderaOTP = Boolean.FALSE;
        }
    }
    private void existeACRegistrado() throws SIATException{
        LOG.error("Validando si ya existe en bd el aviso de compensacion----------");
        DatosAvisoFieldVO datosAvisoField=usuarioFirmado.getDatosAvisoFieldVO();
        List<DycpCompensacionDTO> listaCompensacion =
            validadorCasoComp.buscaICEPDestinoAvisoOCaso(datosAvisoField.getDycpCompensacionDTO().getDycpServicioDTO().getRfcContribuyente(), 
                                                         datosAvisoField.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO(),
                                                         datosAvisoField.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO(), 
                                                         datosAvisoField.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO());
        if (listaCompensacion != null && listaCompensacion.size() > 0) {
            for (DycpCompensacionDTO compensacion : listaCompensacion) {

                if (compensacion.getNumControl().startsWith("AC")) {
                    DycpAvisoCompDTO avisoCompensacion = null;
                    try {
                        avisoCompensacion =
                                avisoCompensacionService.buscaAvisoCompensacion(compensacion.getDycpAvisoCompDTO().getFolioAviso());
                    } catch (SIATException e) {
                        LOG.error(e.getMessage());
                        throw new SIATException(e);

                    }
                    if (avisoCompensacion.getDyccTipoAvisoDTO().getIdTipoAviso() ==
                        ConstantesAvisoComp.TipoAviso.NORMAL &&
                        datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO().getDyccTipoAvisoDTO().getIdTipoAviso() == ConstantesAvisoComp.TipoAviso.NORMAL) {
                        throw new SIATException("No es posible presentar el aviso de compensación de tipo normal porque ya existe " +
                                                "un aviso registrado para el mismo concepto, debes presentarlo como complementario");
                    }
                } else {
                    throw new SIATException("No es posible presentar el aviso de compensación, porque ya existe una compensación " +
                                            "registrada para el mismo concepto en el periodo indicado");
                }
            }
        }
        
    }
    public void setAprobar(AprobarDocumentoMB aprobar) {
        this.aprobar = aprobar;
    }

    public AprobarDocumentoMB getAprobar() {
        return aprobar;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setUsuarioFirmado(UsuarioFirmadoVO usuarioFirmado) {
        this.usuarioFirmado = usuarioFirmado;
    }

    public UsuarioFirmadoVO getUsuarioFirmado() {
        return usuarioFirmado;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public String getLogout() {
        return logout;
    }

    public void setAvisoCompensacionService(AvisoCompensacionService avisoCompensacionService) {
        this.avisoCompensacionService = avisoCompensacionService;
    }

    public AvisoCompensacionService getAvisoCompensacionService() {
        return avisoCompensacionService;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        //TODO para la version 3 y posteriores de commons-lang se usa el metodo escapeEcmaScript
        return StringEscapeUtils.escapeEcmaScript(cadenaOriginal);
    }

    public void setValidadorAvisoCompensacion(ValidadorAvisoCompensacion validadorAvisoCompensacion) {
        this.validadorAvisoCompensacion = validadorAvisoCompensacion;
    }

    public ValidadorAvisoCompensacion getValidadorAvisoCompensacion() {
        return validadorAvisoCompensacion;
    }

    public void setAcuseReciboService(AcuseReciboService acuseReciboService) {
        this.acuseReciboService = acuseReciboService;
    }

    public AcuseReciboService getAcuseReciboService() {
        return acuseReciboService;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setIndexContribuyente(String indexContribuyente) {
        this.indexContribuyente = indexContribuyente;
    }

    public String getIndexContribuyente() {
        return indexContribuyente;
    }

    public void setUrlFirmado(String urlFirmado) {
        this.urlFirmado = urlFirmado;
    }

    public String getUrlFirmado() {
        return urlFirmado;
    }

    public void setSolventarRequerimientoService(SolventarRequerimientoService solventarRequerimientoService) {
        this.solventarRequerimientoService = solventarRequerimientoService;
    }

    public SolventarRequerimientoService getSolventarRequerimientoService() {
        return solventarRequerimientoService;
    }

    public void setInformacionCuentaClabeService(InformacionCuentaClabeService informacionCuentaClabeService) {
        this.informacionCuentaClabeService = informacionCuentaClabeService;
    }

    public InformacionCuentaClabeService getInformacionCuentaClabeService() {
        return informacionCuentaClabeService;
    }

    public void setBanderaOTP(boolean banderaOTP) {
        this.banderaOTP = banderaOTP;
    }

    public boolean isBanderaOTP() {
        return banderaOTP;
    }

    public void setMsg(FacesMessage msg) {
        this.msg = msg;
    }

    public FacesMessage getMsg() {
        return msg;
    }

    public void setSolDevPage(AdicionarSolicitudMB solDevPage) {
        this.solDevPage = solDevPage;
    }

    public AdicionarSolicitudMB getSolDevPage() {
        return solDevPage;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
    }

    public void setMensajeDialogo(String mensajeDialogo) {
        this.mensajeDialogo = mensajeDialogo;
    }

    public String getMensajeDialogo() {
        return mensajeDialogo;
    }

    public boolean isFolioSecuenciaB() {
        return folioSecuenciaB;
    }

    public void setFolioSecuenciaB(boolean folioSecuenciaB) {
        this.folioSecuenciaB = folioSecuenciaB;
    }

    public String getFolioSecuencia() {
        return folioSecuencia;
    }

    public void setFolioSecuencia(String folioSecuencia) {
        this.folioSecuencia = folioSecuencia;

    }

    public boolean isFirmaBandera() {
        return firmaBandera;
    }

    public void setFirmaBandera(boolean firmaBandera) {
        this.firmaBandera = firmaBandera;
    }

    public void setAprobadorService(DyccAprobadorService aprobadorService) {
        this.aprobadorService = aprobadorService;
    }

    public DyccAprobadorService getAprobadorService() {
        return aprobadorService;
    }
    public void setValidadorCasoComp(ValidadorCasoComp validadorCasoComp) {
        this.validadorCasoComp = validadorCasoComp;
    }

    public ValidadorCasoComp getValidadorCasoComp() {
        return validadorCasoComp;
    }
}
