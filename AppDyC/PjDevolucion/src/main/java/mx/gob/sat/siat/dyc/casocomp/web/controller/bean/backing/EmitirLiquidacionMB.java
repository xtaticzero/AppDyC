/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.casocomp.bean.ICEPBean;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.casocomp.service.emitirliquidacion.EmitirLiquidacionService;
import mx.gob.sat.siat.dyc.generico.util.ArchivoValida;
import mx.gob.sat.siat.dyc.generico.util.common.GetBigDecimal;
import mx.gob.sat.siat.dyc.generico.util.common.Messages;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


/**
 * Clase ManagedBean controlador para la emitir resoluciones por
 * Compensacion improcedente o Saldo a favor improcedente.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Julio 03, 2015
 * */
@ManagedBean(name = "emitirLiquidacionMB")
@ViewScoped
public class EmitirLiquidacionMB {
    private static final Logger LOG = Logger.getLogger(EmitirLiquidacionMB.class);

    @ManagedProperty(value = "#{emitirliquidacionService}")
    private EmitirLiquidacionService service;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;

    @ManagedProperty("#{serviceAdmCasosComp}")
    private IAdmCasosCompensacionService serviceACC;

    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;

    private String numControlDoc;

    /** edoInputs: char ; C: captura ; F: valida fechas ; A: aprobanción ; V: vista  */
    private char edoInputs;
    private String rfcContribuyente;
    private String tipoTramite;
    private String style;

    private String funResol;
    private Integer tipoResol;
    private Double importeActualizarD;
    private Double importeRecargoD;
    private Double importeMultaD;
    private String nuDeDocumentoL;
    private BigDecimal importeImprocedente;

    private Integer intMesINPC;
    private Integer anioInicialINPC;
    private Integer mesFinalINPC;
    private Integer anioFinalINPC;

    private Integer mesInicialTasaRec;
    private Integer anioInicialTasaRec;
    private Integer mesFinalTasaRec;
    private Integer anioFinalTasaRec;

    private List<Integer> anios;
    private List<ItemComboBean> meses;

    private ICEPBean icepCompensado;
    private ICEPBean icepOrigen;

    private StreamedContent streamedContArchGen;

    private List<ItemComboBean> conceptos;
    private List<ItemComboBean> ejercicios;
    private List<ItemComboBean> tiposPeriodo;
    private List<ItemComboBean> periodos;

    private Integer tipoPeriodoIcepComp;

    private BigDecimal importeCompensado;

    @PostConstruct
    public void inicializar() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("numControl", mbSession.getNumControl());
        Map<String, Object> infoInicial = service.obtenerInfoInicial(params);
        rfcContribuyente = (String)infoInicial.get("rfcContribuyente");
        tipoTramite = (String)infoInicial.get("tipoTramite");
        setMeses((List<ItemComboBean>)infoInicial.get("meses"));
        setEdoInputs('C');
        icepCompensado = (ICEPBean)infoInicial.get("icepCompensado");
        icepOrigen = (ICEPBean)infoInicial.get("icepOrigen");
        anios = (List<Integer>)infoInicial.get("anios");

        mbSession.setEstatus('D');
        mbSession.setAccButton('L');
    }

    public void emitirLiquidacion() {
        try {
            Map<String, Object> respuesta = service.emitirLiquidacion(this.obtenerDatos());
            File fileGenerado = (File)respuesta.get("archivo");

            InputStream stream = new FileInputStream(fileGenerado);
            setStreamedContArchGen(new DefaultStreamedContent(stream,
                                                              new MimetypesFileTypeMap().getContentType(fileGenerado),
                                                              fileGenerado.getName()));
            setEdoInputs('A');
            numControlDoc = (String)respuesta.get("numControlDoc");
        } catch (Exception e) {
            LOG.error("ERROR en service.emitirLiquidacion ->" + e.getMessage() + "<-");
        }
    }

    public Map<String, Object> obtenerDatos() {
        Map<String, Object> params = new HashMap<String, Object>();
        DecimalFormat formato = new DecimalFormat("$#,##0.00");

        params.put("numControl", mbSession.getNumControl());
        params.put("numControlDoc", numControlDoc != null ? numControlDoc : "");
        params.put("agaffImpTotAct", formato.format(importeActualizarD));
        params.put("impRecargo", formato.format(importeRecargoD));
        params.put("agaffImpMulta", formato.format(importeMultaD));
        params.put("totalAPagar", formato.format((importeMultaD != null ? importeMultaD : 0)+(importeRecargoD !=null ? importeRecargoD : 0)+(importeActualizarD !=null ? importeActualizarD : 0)+(importeImprocedente !=null ? importeImprocedente.longValue() : icepOrigen.getImporteCompensado().intValue())));
        params.put("importeActualizarD", importeActualizarD);
        params.put("importeRecargoD", importeRecargoD);
        params.put("importeMultaD", importeMultaD);
        params.put("agaffNumOficio", nuDeDocumentoL);
        params.put("lMesAnteriorA", intMesINPC);
        params.put("agaffINPCMAntMAntD", intMesINPC);
        params.put("lAnioAnteriorA", anioInicialINPC);
        params.put("lMesAnteriorR", mesFinalINPC);
        params.put("lMesAnteriorR2", mesFinalINPC);
        params.put("lAnioAnteriorR", anioFinalINPC);
        params.put("mesInicioTasaRec", mesInicialTasaRec);
        params.put("anioInicialTasaRec", anioInicialTasaRec);
        params.put("mesFinalTasaRec", mesFinalTasaRec);
        params.put("anioFinalTasaRec", anioFinalTasaRec);
        params.put("funMotivacion", funResol);
        params.put("tipoResolucion", mbSession.getTipoLiquidacion());
        params.put("importeCompensacion",
                   importeImprocedente != null ? formato.format(importeImprocedente) : formato.format(icepOrigen.getImporteCompensado()));
        params.put("importeIC", importeImprocedente != null ? importeImprocedente : icepOrigen.getImporteCompensado());
        params.put("tipoLiquidacion", mbSession.getTipoLiquidacion());
        params.put("esGranContribuyente", mbSession.getEsGranContribuyente());
        params.put("claveAdm", mbSession.getClaveAdm());

        mbSession.setParamsLiquida(params);
        return params;
    }

    public String enviarAAprobacion() {
        LOG.info("\n\nidSuperior ->" + mbSession.getIdNumAprob() + " || numControlDoc" + numControlDoc);
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());

        if (mbSession.getIdNumAprob() == ConstantesDyCNumerico.VALOR_0 && null == numControlDoc) {
            JSFUtils.messageGlobal("Se debe seleccionar el aprobador a escalar", FacesMessage.SEVERITY_WARN);
            return null;
        }

        if ( !validacionAgs.aprobadorValido( mbSession.getIdNumAprob() ) /* || true */ ){
            validacionAgs.muestraMensajeAprobadorNoValido( "msgAResol" );

            return SatAgsEmpleadosMVService.NO_REDIRECCIONAMIENTO;
        }
        
        /**validacionAgs.ejecutaJs( "dlgEnviarAprob.hide()" );*/
        RequestContext.getCurrentInstance().execute("dlgEnviarAprob.hide()");

        mbSession.setMensaje(null);
        mbSession.setSalida(null);

        ArchivoValida av = new ArchivoValida();
        try {
            av.validaciones(mbSession.getArchivoASubir(), request, false);

            Map<String, Object> params = mbSession.getParamsLiquida();
            params.put("idSuperior", mbSession.getIdNumAprob());
            params.put("numControlDoc", numControlDoc);

            InputStream secuenciaEntrada;
            secuenciaEntrada = mbSession.getArchivoASubir().getInputstream();
            if (null != secuenciaEntrada) {
                params.put("nombreArchivo", av.getNombre());
                params.put("secuenciaEntrada", secuenciaEntrada);

                mbSession.setMensaje(service.enviarAAprobacion(params));
                mbSession.setSalida("bandejaCompensaciones");
            } else {
                JSFUtils.messageGlobal("Error: debe seleccionar un archivo", FacesMessage.SEVERITY_ERROR);
            }
        } catch (IOException ioe) {
            LOG.error(ioe);
            JSFUtils.messageGlobal(ioe.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (SIATException e) {
            JSFUtils.messageGlobal(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

        return mbSession.getSalida();
    }

    public void validaFechas() {
        style = null;
        Calendar fechaInicial = Calendar.getInstance();
        Calendar fechaFinal = Calendar.getInstance();
        
        if(null != anioInicialTasaRec && null != mesInicialTasaRec ) {       
            fechaInicial.set(anioInicialTasaRec,mesInicialTasaRec,ConstantesDyCNumerico.VALOR_1);
        }
        
        if(null != anioFinalTasaRec && null != mesFinalTasaRec ) {       
            fechaFinal.set(anioFinalTasaRec,mesFinalTasaRec,ConstantesDyCNumerico.VALOR_1);
        }

        if (null != fechaInicial && null != fechaFinal) {
            if (fechaInicial.after(fechaFinal)) {
                JSFUtils.messageGlobal("La fecha de presentación de la declaración origen, no debe ser posterior " +
                    "a la fecha de presentación de la declaración donde se compensó",
                                       FacesMessage.SEVERITY_WARN);
                setEdoInputs('F');
                style = "ui-state-error";
            } else {
                setEdoInputs('C');
                style = "";
            }
        }
    }

    public void validaImporte(FacesContext facesContext, UIComponent component,
                              Object object){
        try {
            importeCompensado = GetBigDecimal.parse(icepOrigen.getImporteCompensado());
            setImporteImprocedente(GetBigDecimal.parse(object));
            /** importeImpro > importeCompensado  */
            if (getImporteImprocedente().compareTo(importeCompensado) > ConstantesDyCNumerico.VALOR_0) {
                FacesMessage message =
                    Messages.getMessage("El saldo a favor improcedente no puede ser mayor al saldo a aplicar",
                                        new Object[] { "SaldoFavor" });
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        } catch (Exception se) {
            LOG.error("\n###\n\n" +
                    se.getMessage());
        }
    }

    public void validaImporte() {
        try {
            importeCompensado = GetBigDecimal.parse(icepOrigen.getImporteCompensado());
            if (importeImprocedente.compareTo(importeCompensado) > ConstantesDyCNumerico.VALOR_0) {
                FacesContext.getCurrentInstance().addMessage("msgSaldoFavbor",
                                                             new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              "El saldo a favor improcedente no puede ser mayor al saldo a aplicar",
                                                                              null));
                setEdoInputs('F');
                style = "ui-state-error";
            }
        } catch (Exception se) {
            LOG.error("\n###\n\n" +
                    se.getMessage());
        }
    }

    public List<Integer> getAnios() {
        return anios;
    }

    public void setAnios(List<Integer> anios) {
        this.anios = anios;
    }

    public List<ItemComboBean> getMeses() {
        return meses;
    }

    public void setMeses(List<ItemComboBean> meses) {
        this.meses = meses;
    }

    public StreamedContent getStreamedContArchGen() {
        return streamedContArchGen;
    }

    public void setStreamedContArchGen(StreamedContent streamedContArchGen) {
        this.streamedContArchGen = streamedContArchGen;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoResol(Integer tipoResol) {
        this.tipoResol = tipoResol;
    }

    public Integer getTipoResol() {
        return tipoResol;
    }

    public void setFunResol(String funResol) {
        this.funResol = funResol;
    }

    public String getFunResol() {
        return funResol;
    }

    public void setImporteActualizarD(Double importeActualizarD) {
        this.importeActualizarD = importeActualizarD;
    }

    public Double getImporteActualizarD() {
        return importeActualizarD;
    }

    public void setImporteRecargoD(Double importeRecargoD) {
        this.importeRecargoD = importeRecargoD;
    }

    public Double getImporteRecargoD() {
        return importeRecargoD;
    }

    public void setImporteMultaD(Double importeMultaD) {
        this.importeMultaD = importeMultaD;
    }

    public Double getImporteMultaD() {
        return importeMultaD;
    }

    public void setIntMesINPC(Integer intMesINPC) {
        this.intMesINPC = intMesINPC;
    }

    public Integer getIntMesINPC() {
        return intMesINPC;
    }

    public void setService(EmitirLiquidacionService emitirLiquidacionService) {
        this.service = emitirLiquidacionService;
    }

    public EmitirLiquidacionService getService() {
        return service;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setMbSession(ManagerSesionAdmCasosCompMB mbSession) {
        this.mbSession = mbSession;
    }

    public ManagerSesionAdmCasosCompMB getMbSession() {
        return mbSession;
    }

    public String getNuDeDocumentoL() {
        return nuDeDocumentoL;
    }

    public void setNuDeDocumentoL(String nuDeDocumentoL) {
        this.nuDeDocumentoL = nuDeDocumentoL;
    }

    public Integer getAnioInicialINPC() {
        return anioInicialINPC;
    }

    public void setAnioInicialINPC(Integer anioInicialINPC) {
        this.anioInicialINPC = anioInicialINPC;
    }

    public Integer getMesFinalINPC() {
        return mesFinalINPC;
    }

    public void setMesFinalINPC(Integer mesFinalINPC) {
        this.mesFinalINPC = mesFinalINPC;
    }

    public Integer getAnioFinalINPC() {
        return anioFinalINPC;
    }

    public void setAnioFinalINPC(Integer anioFinalINPC) {
        this.anioFinalINPC = anioFinalINPC;
    }

    public Integer getAnioInicialTasaRec() {
        return anioInicialTasaRec;
    }

    public void setAnioInicialTasaRec(Integer anioInicialTasaRec) {
        this.anioInicialTasaRec = anioInicialTasaRec;
    }

    public Integer getMesFinalTasaRec() {
        return mesFinalTasaRec;
    }

    public void setMesFinalTasaRec(Integer mesFinalTasaRec) {
        this.mesFinalTasaRec = mesFinalTasaRec;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public Integer getMesInicialTasaRec() {
        return mesInicialTasaRec;
    }

    public void setMesInicialTasaRec(Integer mesInicialTasaRec) {
        this.mesInicialTasaRec = mesInicialTasaRec;
    }

    public ICEPBean getIcepCompensado() {
        return icepCompensado;
    }

    public void setIcepCompensado(ICEPBean icepCompensado) {
        this.icepCompensado = icepCompensado;
    }

    public ICEPBean getIcepOrigen() {
        return icepOrigen;
    }

    public void setIcepOrigen(ICEPBean icepOrigen) {
        this.icepOrigen = icepOrigen;
    }

    public List<ItemComboBean> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<ItemComboBean> conceptos) {
        this.conceptos = conceptos;
    }

    public List<ItemComboBean> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<ItemComboBean> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public List<ItemComboBean> getTiposPeriodo() {
        return tiposPeriodo;
    }

    public void setTiposPeriodo(List<ItemComboBean> tiposPeriodo) {
        this.tiposPeriodo = tiposPeriodo;
    }

    public void setTipoPeriodoIcepComp(Integer tipoPeriodoIcepComp) {
        this.tipoPeriodoIcepComp = tipoPeriodoIcepComp;
    }

    public Integer getTipoPeriodoIcepComp() {
        return tipoPeriodoIcepComp;
    }

    public void setPeriodos(List<ItemComboBean> periodos) {
        this.periodos = periodos;
    }

    public List<ItemComboBean> getPeriodos() {
        return periodos;
    }

    public IAdmCasosCompensacionService getServiceACC() {
        return serviceACC;
    }

    public void setServiceACC(IAdmCasosCompensacionService serviceACC) {
        this.serviceACC = serviceACC;
    }

    public Integer getAnioFinalTasaRec() {
        return anioFinalTasaRec;
    }

    public void setAnioFinalTasaRec(Integer anioFinalTasaRec) {
        this.anioFinalTasaRec = anioFinalTasaRec;
    }


    public void setEdoInputs(char edoInputs) {
        this.edoInputs = edoInputs;
    }

    public char getEdoInputs() {
        return edoInputs;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setImporteImprocedente(BigDecimal importeImprocedente) {
        this.importeImprocedente = importeImprocedente;
    }

    public BigDecimal getImporteImprocedente() {
        return importeImprocedente;
    }

    public void setValidacionAgs( SatAgsEmpleadosMVService validacionAgs ){
        this.validacionAgs = validacionAgs;
    }

    public SatAgsEmpleadosMVService getValidacionAgs (){
        return validacionAgs;
    }

}
