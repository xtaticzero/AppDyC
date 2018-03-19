/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.io.IOException;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.catalogo.service.DyccEjercicioService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccOrigenSaldoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccPeriodoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccSubtramiteService;

import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.servicio.service.diot.DiotService;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionRNFDC10;

import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import mx.gob.sat.siat.dyc.util.constante2.ConstantesValContribuyente;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;


import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;

import org.springframework.dao.DataAccessException;


/**
 * @author JEFG
 * */
@ManagedBean(name = "solicitudDevolicionUtilsMB")
@RequestScoped
public class SolicitudDevolicionUtilsMB {

    private static final Logger LOGGER = Logger.getLogger(SolicitudDevolicionUtilsMB.class);
    private static final String HIDEINSISTENCIACLABE = "seguirCLABE.hide();";

    @ManagedProperty(value = "#{dyccOrigenSaldoService}")
    private DyccOrigenSaldoService dyccOrigenSaldoService;
    @ManagedProperty("#{facesMessage}")
    private IFacesMessage messageSolicitud;
    @ManagedProperty("#{dyccEjercicioService}")
    private DyccEjercicioService dyccEjercicioService;
    @ManagedProperty("#{diotService}")
    private DiotService diotService;
    @ManagedProperty("#{dyccPeriodoService}")
    private DyccPeriodoService dyccPeriodoService;
    @ManagedProperty("#{dyccSubtramiteService}")
    private DyccSubtramiteService dyccSubtramiteService;
    
    

    @ManagedProperty(value = "#{adicionarSolicitudMB}")
    private AdicionarSolicitudMB solDevPage;
    @ManagedProperty(value = "#{datosContribuyenteMB}")
    private DatosContribuyenteMB datosContribuyentePage;
    

    public SolicitudDevolicionUtilsMB() {
    }

    public String validaDatos() {
        
        datosContribuyentePage.validarPeriodoInicio();
        
        if(datosContribuyentePage.isAsPeriodo())
        {
            return "datosContribuyente";
        }
        
        String page = null;
        if (datosContribuyentePage.getSonDatos().equals("1")) {
            solDevPage.setTramite(new TramiteDTO());
            solDevPage.getTramite().setRolesContribuyente(datosContribuyentePage.getRolesContribuyente());
            solDevPage.getTramite().setPersona(datosContribuyentePage.getContribuyente());
            try {
                solDevPage.inicializaVariables();
                solDevPage.getFlagsSolicitud().setStep("T1");
                origenDevolucion(datosContribuyentePage.getRolesContribuyente().getEsContribuyente());
                ejercicio();
                page = ValidaDatosSolicitud.PAGE_SOLICITUD;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                JSFUtils.messageGlobal("El sistema no pudo ejecutar correctamente este servicio, le sugerimos lo intente mas tarde!..",
                                       FacesMessage.SEVERITY_ERROR);
            }
        } else {
            showMessages(ConstantesIds.MSG_5, FacesMessage.SEVERITY_ERROR);
        }
        return page;
    }

    private void origenDevolucion(String registrador) throws SIATException {
        LOGGER.debug("registrador ->" + registrador);
        solDevPage.setCatOrigenSaldo(dyccOrigenSaldoService.obtieneOrigenesSaldo(registrador).getOutputs());
        solDevPage.getFlagsSolicitud().setRowSubOrigenSaldo(Boolean.TRUE);
    }

    private void ejercicio() throws SIATException {
        List<DyccEjercicioDTO> ejercicio = null;
        CatalogoTO ejercicioTo = null;
        try {
            ejercicio = dyccEjercicioService.obtieneEjercicio();
        } catch (DataAccessException e) {
            throw new SIATException(e);
        }
        if (null != ejercicio) {
            for (DyccEjercicioDTO item : ejercicio) {
                ejercicioTo = new CatalogoTO();
                ejercicioTo.setIdNum(item.getIdEjercicio());
                ejercicioTo.setIdString(String.valueOf(item.getPermiteSeleccion()));
                solDevPage.getCatEjercicio().add(ejercicioTo);
            }
        }
    }

    /**si  */
    public void seguirAInsistencia() {
        solDevPage.getFlagsSolicitud().setMessageGlobal(getMessage(ConstantesIds.MSG_48));
        RequestContext.getCurrentInstance().execute("dlgAddEdoCtaExp.show();");
    }

    /**si  */
    public void seguirAInsisEdoCont() {
        solDevPage.getFlagsSolicitud().setMessageSol(null);
    }


    /**Si  */
    public void select() {
        solDevPage.getTramite().setInstitucionFinanciera(null);
        solDevPage.getFlagsSolicitud().setMessageGlobal(null);
        solDevPage.getFlagsSolicitud().setShowEdoCta(Boolean.TRUE);
        RequestContext.getCurrentInstance().execute(HIDEINSISTENCIACLABE);
    }

    /**Si  */
    private boolean generaSolTemp() {
        if (solDevPage.getFlagsSolicitud().getBloqAnexos() || solDevPage.getFlagsSolicitud().isPresentaDoc()) {
            solDevPage.getFlagsSolicitud().setBloqAnexos(false);
            solDevPage.getFlagsSolicitud().setPresentaDoc(false);
            return true;
        }
        return false;
    }


    /**Si  */
    public String initSolicitud() {
        String page = null;
        if (generaSolTemp()) {
            solDevPage.getFlagsSolicitud().setFlagTemp(Boolean.TRUE);
            RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.CONFIRMETEMPORAL);
            return null;
        } else {
            if (null != solDevPage.getTramite().getDocumentos() &&
                !solDevPage.getTramite().getDocumentos().isEmpty()) {
                for (int i = 0; i < solDevPage.getTramite().getDocumentos().size(); i++) {
                    try {
                        ValidaDatosSolicitud.deleteDocumentos(solDevPage.getTramite().getDocumentos().get(i).getUrl() +
                                                              solDevPage.getTramite().getDocumentos().get(i).getNombreArchivo());
                    } catch (IOException e) {
                        LOGGER.info(e.getMessage());
                    }
                }
            }
            if (solDevPage.getTramite().getRolesContribuyente().getEsContribuyente().equals(ValidaDatosSolicitud.CONTRIBUYENTE)) {
                LOGGER.info("SE CANCELO EL FLUJO PRINCIPAL DEL REGISTRO DE SOLICITUD POR CONTRIBUYENTE");
                page =
datosContribuyentePage.isIsSolTem() ? ValidaDatosSolicitud.PAGE_SOLICITUDES_DEVOLUCION : "datosContribuyente";
            } else {
                LOGGER.info("SE CANCELO EL FLUJO PRINCIPAL DEL REGISTRO DE SOLICITUD POR ASESOR FISCAL");
                page = ValidaDatosSolicitud.PAGE_RFC;
            }
        }
        return page;
    }

    /*****************Si*************************/
    public void validaTipoDeclaracion() {
        LOGGER.info("TIPO DECLARACIONs " + solDevPage.getTramite().getSaldoFavor().getTipoDeclaracion());
        if (solDevPage.getTramite().getSaldoFavor().getTipoDeclaracion().equals("1")) {
            solDevPage.getFlagsSolicitud().setBloqDeclaracionNormal(Boolean.FALSE);
            solDevPage.getTramite().getSaldoFavor().setNormalFechaPresentacion(null);
            solDevPage.getTramite().getSaldoFavor().setNormalNumOperacion(null);
            solDevPage.getTramite().getSaldoFavor().setNormalSaldoFavor(null);
        } else {
            solDevPage.getFlagsSolicitud().setBloqDeclaracionNormal(Boolean.TRUE);
            showMessages(ConstantesIds.MSG_31, FacesMessage.SEVERITY_INFO);
        }
        LOGGER.info("BLOQUE DECLARACION NORMAL " + solDevPage.getFlagsSolicitud().isBloqDeclaracionNormal());
    }
    
    public void resetDiot() {
        solDevPage.getTramite().getInfoDeclarativa().setDiotFechPrecentacion(null);
        solDevPage.getTramite().getInfoDeclarativa().setDiotNumOperacion(null);
        solDevPage.getTramite().getInfoDeclarativa().setAltexNumConstancia(null);
       
    }

   /** public void consultaDIOT() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(ConstantesTipoArchivo.YYYYMMDD);
        String rfc = solDevPage.getTramite().getPersona().getRfcVigente();
        String numOperacion = solDevPage.getTramite().getInfoDeclarativa().getDiotNumOperacion();
        Date fechaPresentacion = solDevPage.getTramite().getInfoDeclarativa().getDiotFechPrecentacion();
        
        DiotDTO diotDTO = new DiotDTO();
        diotDTO.setTxtrfc(rfc);
        diotDTO.setTxtnumoperacion(numOperacion.toUpperCase());
        diotDTO.setTxtfechapresen(dateFormat.format(fechaPresentacion));
        diotDTO.setTxtusr(ConstantesDyC.USR_STOi_PROCEDURES);

        
        try {
            diotDTO = diotService.obtieneDiotSp(diotDTO);
        } catch (SIATException e) {
            LOGGER.error(e.getMessage());
            solDevPage.getFlagsSolicitud().setShowMessageError(Boolean.TRUE);
            return;
        }

        if (null == diotDTO.getVdRfceeog1()) {
            showMessages(ConstantesIds.MSG_13, FacesMessage.SEVERITY_ERROR);
            solDevPage.getTramite().getInfoDeclarativa().setDiotNumOperacion(null);
            solDevPage.getTramite().getInfoDeclarativa().setDiotFechPrecentacion(null);
            solDevPage.getFlagsSolicitud().setFlagFechDiot(Boolean.TRUE);
        } else {
            Integer valor = validaPeriodo(diotDTO);

            if (valor != ConstantesDyC1.CERO) {
                showMessages(ConstantesDyCNumerico.VALOR_44, FacesMessage.SEVERITY_ERROR);
                solDevPage.getTramite().getInfoDeclarativa().setDiotNumOperacion(null);
                solDevPage.getTramite().getInfoDeclarativa().setDiotFechPrecentacion(null);
                solDevPage.getFlagsSolicitud().setFlagFechDiot(Boolean.TRUE);
            } else {
                solDevPage.getTramite().setDiot(diotDTO);
            }

        }
    }*/
    
   /** private Integer validaPeriodo(DiotDTO diotDTO) throws ParseException {
        Integer ejercicio = solDevPage.getTramite().getEjercicio().getIdNum();
        String selectPeriodo = ConstantesDyC2.EMPTY_STRING;
        String periodoCapturado = ConstantesDyC2.EMPTY_STRING;
        Integer valor = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        if(ValidaDatosSolicitud.isPeriodoMensual(solDevPage.getTramite().getTipoPeriodo().getIdNum()))
        {
            selectPeriodo = solDevPage.getTramite().getPeriodo().getIdString().substring(ConstantesDyCNumerico.VALOR_2);
            periodoCapturado = ejercicio + ValidaDatosSolicitud.GUION + selectPeriodo + ValidaDatosSolicitud.INICIO;
            String periodoConsulta = diotDTO.getVdEjercic1() + ValidaDatosSolicitud.GUION + diotDTO.getVdCplearv1() + ValidaDatosSolicitud.INICIO;
            Date compare = df.parse(periodoConsulta);
            valor = compare.compareTo(df.parse(periodoCapturado));
        }else
        {
            selectPeriodo = dyccPeriodoService.obtenerPeriodoDiot(solDevPage.getTramite().getPeriodo().getIdNum());
            periodoCapturado = ejercicio + ValidaDatosSolicitud.GUION + selectPeriodo + ValidaDatosSolicitud.INICIO;
            Calendar fechaCaptura = Calendar.getInstance();
            fechaCaptura.setTime(df.parse(periodoCapturado));
            
            Calendar fechaDiot = Calendar.getInstance();
            fechaDiot.setTime(diotDTO.getVdFperceh1());
            
            valor = fechaDiot.compareTo(fechaCaptura)!=ConstantesDyC1.MENOS_UNO ? 0:1;            
        }
        return valor;
    }
*/
  
    /**
     * Llena el campo de Periodo y ejercicion de acuerdo al periodo seleccionado
     */
    public void buscaTipoPeriodo() {
        LOGGER.info("### buscaTipoPeriodo " + solDevPage.getTramite().getTipoPeriodo().getIdString());
        List<DyccPeriodoDTO> periodo = null;
        solDevPage.getCatPeriodo().clear();
        CatalogoTO periodoTo = null;
        String idTipoPeriodo = ConstantesDyC2.NULL;
        if (null != solDevPage.getTramite().getTipoPeriodo()) {
            idTipoPeriodo = solDevPage.getTramite().getTipoPeriodo().getIdString();
            solDevPage.getTramite().setEjercicio(null);
            try {
                periodo = dyccPeriodoService.obtienePeriodoPorTipoPeriodo(idTipoPeriodo);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                solDevPage.getFlagsSolicitud().setShowMessageError(Boolean.TRUE);
                return;
            }
            if (null != periodo) {
                if (periodo.size() == ConstantesDyCNumerico.VALOR_1) {
                    periodoTo = new CatalogoTO();
                    periodoTo.setIdNum(periodo.get(ConstantesDyC1.CERO).getIdPeriodo());
                    periodoTo.setDescripcion(periodo.get(ConstantesDyC1.CERO).getDescripcion());
                    periodoTo.setIdString(periodo.get(ConstantesDyC1.CERO).getPeriodoInicioFin());
                    solDevPage.getCatPeriodo().add(periodoTo);
                    solDevPage.getTramite().setPeriodo(periodoTo);
                } else {
                    for (DyccPeriodoDTO item : periodo) {
                        periodoTo = new CatalogoTO();
                        periodoTo.setIdNum(item.getIdPeriodo());
                        periodoTo.setDescripcion(null != item.getDescripcion() ? item.getDescripcion() :
                                                 ConstantesDyC2.EMPTY_STRING);
                        periodoTo.setIdString(null != item.getPeriodoInicioFin() ? item.getPeriodoInicioFin() :
                                              ConstantesDyC2.EMPTY_STRING);
                        solDevPage.getCatPeriodo().add(periodoTo);
                    }
                }
            }
        }
    }

    public void subTiposTramite() {
        LOGGER.debug("PERIODO " + solDevPage.getTramite().getPeriodo().getIdString());
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(ConstanteValidacionRNFDC10.DATE_FORMAT_YEAR);
        Integer actual = Integer.parseInt(dateFormat.format(date));
        Integer ejercicio = solDevPage.getTramite().getEjercicio().getIdNum();
        boolean noLocalizado =
            ValidaDatosSolicitud.isDomNoLocalizado(solDevPage.getTramite().getPersona().getPersonaIdentificacion().getClaveSitDomicilio());
        String sitContribuyente = solDevPage.getTramite().getPersona().getPersonaIdentificacion().getClaveDetSitCont();
        limpiarCampos();
        if (validaEjercicio(ejercicio, actual)) {
            solDevPage.getTramite().setTipoPeriodo(null);
            solDevPage.getTramite().setPeriodo(null);
            solDevPage.getTramite().setEjercicio(null);
            return;
        }
        if (solDevPage.getTramite().getRolesContribuyente().isGranContribuyente() && noLocalizado) {
            finalizarSolicitud(ConstantesIds.MSG_28);
        } else if (!solDevPage.getTramite().getRolesContribuyente().isGranContribuyente()) {
            estadoContribuyente(sitContribuyente, noLocalizado);
        }
    }

    private void estadoContribuyente(String sitContribuyente, boolean sitDomicilio) {
        if (sitContribuyente.equals(String.valueOf(ConstantesValContribuyente.CONTRIBUYENTE_SUSPENDIDO)) || sitDomicilio) {
            if (sitDomicilio) {
                CatalogoTO inconsistencia4 = new CatalogoTO();
                inconsistencia4.setIdNum(ConstantesDyCNumerico.VALOR_5);
                inconsistencia4.setDescripcion(ConstantesValContribuyente.EDO_DOMICILIO_NO_LOCALIZADO);
                solDevPage.getFlagsSolicitud().setMessageSol(getMessage(ConstantesIds.MSG_1));
                /**  JSFUtils.messageGlobal(solicitudUtils.getMessage(ConstantesDyC.MSG_1),
                                       FacesMessage.SEVERITY_ERROR); */
                addInconsistencias(inconsistencia4);
            }
            if ((sitContribuyente.equals(String.valueOf(ConstantesValContribuyente.CONTRIBUYENTE_SUSPENDIDO)) ||
                 sitContribuyente.equals(String.valueOf(ConstantesValContribuyente.CONTRIBUYENTE_CANCELADO)))) {
                CatalogoTO inconsistencia5 = new CatalogoTO();
                inconsistencia5.setDescripcion(ConstantesValContribuyente.EDO_CONTRIBUYENTE_INACTIVO);
                solDevPage.getFlagsSolicitud().setMessageSol(getMessage(ConstantesIds.MSG_2));
                /**   JSFUtils.messageGlobal(solicitudUtils.getMessage(ConstantesDyC.MSG_2),
                                       FacesMessage.SEVERITY_ERROR); */
                inconsistencia5.setIdNum(ConstantesDyCNumerico.VALOR_4);
                addInconsistencias(inconsistencia5);
            }
            RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.AINSISTENCIA);
        }
    }

    private void addInconsistencias(CatalogoTO inconsistenca) {
        for (CatalogoTO add : solDevPage.getTramite().getInconsistencias()) {
            if (add.getIdNum() == inconsistenca.getIdNum()) {
                return;
            }
        }
        solDevPage.getTramite().getInconsistencias().add(inconsistenca);
    }

    private void finalizarSolicitud(int idMessage) {
        solDevPage.getFlagsSolicitud().setMessageGlobal(getMessage(idMessage));
        solDevPage.getFlagsSolicitud().setEndSolicitud(Boolean.TRUE);
        RequestContext.getCurrentInstance().update("form:messageEndSolicitud");
        RequestContext.getCurrentInstance().execute("endSolicitud.show();");
    }

    private boolean validaEjercicio(int ejercicio, int actual) {
        CatalogoTO subTipoTramite = null;
        boolean correcto = false;
        solDevPage.getTramite().setSubTramite(null);
        if(ValidaDatosSolicitud.isSectorAgropecuario(solDevPage.getTramite().getTipoTramite().getIdNum()) && ejercicio < ConstanteValidacionRNFDC10.YEAR_2014){
                showMessages(ConstantesIds.MSG_71, FacesMessage.SEVERITY_ERROR);
                solDevPage.getTramite().setEjercicio(null);
        }
        if (ejercicio > actual) {
            showMessages(ConstantesIds.MSG_41, FacesMessage.SEVERITY_ERROR);
            solDevPage.getTramite().setPeriodo(null);
        } else if ((ejercicio <= ConstanteValidacionRNFDC10.YEAR_2004) &&
                   !solDevPage.getTramite().getRolesContribuyente().isSociedadControladora() &&
                   ValidaDatosSolicitud.subtipoTramites(solDevPage.getTramite().getTipoTramite().getIdNum())) {
            Integer tipoTramite = solDevPage.getTramite().getTipoTramite().getIdNum();
            solDevPage.getFlagsSolicitud().setShowSubtipoTramite(Boolean.TRUE);
            solDevPage.setSubtramite(new ArrayList<CatalogoTO>());
            solDevPage.getTramite().setSubTramite(new CatalogoTO());
            try {
                for (DyccSubTramiteDTO subTramite : dyccSubtramiteService.obtieneSubtramite()) {
                    subTipoTramite = new CatalogoTO();
                    subTipoTramite.setIdNum(subTramite.getIdSubTipoTramite());
                    subTipoTramite.setDescripcion(subTramite.getDescripcion());
                    solDevPage.getSubtramite().add(subTipoTramite);
                }
                presentaSubtiposTramite(tipoTramite);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                solDevPage.getFlagsSolicitud().setShowMessageError(Boolean.TRUE);
            }
        } else if (ejercicio >= actual) {
            correcto = validaPeriodo();
        }
        return correcto;
    }

    private boolean validaPeriodo() {
        LOGGER.info("ENTRO " + solDevPage.getTramite().getPeriodo().getIdString());
        if (solDevPage.getCatPeriodo().size() >= ConstantesDyCNumerico.VALOR_2) {
            int valor = solDevPage.getCatPeriodo().size();
            int idPeriodo = solDevPage.getTramite().getPeriodo().getIdNum();
            int selectPeriodo =
                Integer.parseInt(solDevPage.getTramite().getPeriodo().getIdString().substring(ConstantesDyCNumerico.VALOR_2));
            if (ValidaDatosSolicitud.isPeriodoValido(valor, idPeriodo, selectPeriodo)) {
                showMessages(ConstantesIds.MSG_41, FacesMessage.SEVERITY_ERROR);
                return Boolean.TRUE;
            }
        }
        return false;
    }

    private void presentaSubtiposTramite(int tipoTramite) {
        if (tipoTramite == ConstantesIds.TIPO_TRAMITE_NO114 || tipoTramite == ConstantesIds.TIPO_TRAMITE_NO117) {
            solDevPage.getSubtramite().remove(ConstantesDyCNumerico.VALOR_1);
        }
        if (tipoTramite == ConstantesIds.TIPO_TRAMITE_NO124) {
            solDevPage.getSubtramite().remove(ConstantesDyCNumerico.VALOR_2);
        }
    }

    private void limpiarCampos() {
        solDevPage.getTramite().getInfoDeclarativa().setDiotFechPrecentacion(null);
        solDevPage.getTramite().getInfoDeclarativa().setDiotNumOperacion(null);
        solDevPage.getTramite().setDiot(null);
        solDevPage.getTramite().setAltex(null);
        solDevPage.getFlagsSolicitud().setRowALTEX(Boolean.FALSE);
        solDevPage.getFlagsSolicitud().setBloqInfoSaldoFavor(Boolean.FALSE);
        solDevPage.getFlagsSolicitud().setBloqInfoDIOT(Boolean.FALSE);
        solDevPage.getFlagsSolicitud().setConfirmarICEP(Boolean.FALSE);
        solDevPage.getFlagsSolicitud().setShowSubtipoTramite(Boolean.FALSE);
        solDevPage.getFlagsSolicitud().setFlagDeclaracion(null);
        solDevPage.getFlagsSolicitud().setShowUploadFile(Boolean.TRUE);
        solDevPage.getTramite().setSaldoFavor(new InformacionSaldoFavorTO());
    }

    private String getMessage(int idMensaje) {
        return messageSolicitud.getMessage(idMensaje, ConstantesIds.CU_REGISTRO_SOLDEVLINEA);
    }

    private void showMessages(int idMensaje, Severity severity) {
        JSFUtils.messageGlobal(getMessage(idMensaje), severity);
    }

    /***************GET and SET*************/
    public void setSolDevPage(AdicionarSolicitudMB adicionarSolicitudPage) {
        this.solDevPage = adicionarSolicitudPage;
    }

    public AdicionarSolicitudMB getSolDevPage() {
        return solDevPage;
    }


    public void setMessageSolicitud(IFacesMessage messageSolicitud) {
        this.messageSolicitud = messageSolicitud;
    }

    public IFacesMessage getMessageSolicitud() {
        return messageSolicitud;
    }

    public void setDyccOrigenSaldoService(DyccOrigenSaldoService dyccOrigenSaldoService) {
        this.dyccOrigenSaldoService = dyccOrigenSaldoService;
    }

    public DyccOrigenSaldoService getDyccOrigenSaldoService() {
        return dyccOrigenSaldoService;
    }

    public void setDatosContribuyentePage(DatosContribuyenteMB datosContribuyentePage) {
        this.datosContribuyentePage = datosContribuyentePage;
    }

    public DatosContribuyenteMB getDatosContribuyentePage() {
        return datosContribuyentePage;
    }

    public void setDyccEjercicioService(DyccEjercicioService dyccEjercicioService) {
        this.dyccEjercicioService = dyccEjercicioService;
    }

    public DyccEjercicioService getDyccEjercicioService() {
        return dyccEjercicioService;
    }

    public void setDiotService(DiotService diotService) {
        this.diotService = diotService;
    }

    public DiotService getDiotService() {
        return diotService;
    }

    public void setDyccPeriodoService(DyccPeriodoService dyccPeriodoService) {
        this.dyccPeriodoService = dyccPeriodoService;
    }

    public DyccPeriodoService getDyccPeriodoService() {
        return dyccPeriodoService;
    }

    public void setDyccSubtramiteService(DyccSubtramiteService dyccSubtramiteService) {
        this.dyccSubtramiteService = dyccSubtramiteService;
    }

    public DyccSubtramiteService getDyccSubtramiteService() {
        return dyccSubtramiteService;
    }
    
    

}
