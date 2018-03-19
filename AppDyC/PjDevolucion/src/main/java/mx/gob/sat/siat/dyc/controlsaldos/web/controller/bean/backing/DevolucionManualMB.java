package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;

import mx.gob.sat.siat.dyc.controlsaldos.service.icep.CalcularActualizacionResDevoluService;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.RegistrosManualesServiceImpl;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;


@ManagedBean(name = "devolucionManualMB")
@ViewScoped
public class DevolucionManualMB
{
    private static final Logger LOG = Logger.getLogger(DevolucionManualMB.class.getName());
    
    private List<ItemComboBean> tiposResolucion;

    @ManagedProperty(value = "#{bdRegistrosManuales}")
    private RegistrosManualesServiceImpl bussinesDelegate;
    
    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;
    
    @ManagedProperty(value = "#{detalleIcepMB}")
    private DetalleIcepMB mbDetalleIcep;

    @ManagedProperty(value = "#{calcularActualizacionResDevoluService}")
    private CalcularActualizacionResDevoluService calcularActualizacionResDevoluService;

    private ItemComboBean tipoResolucionSelec;
    
    private Integer idTipoResActiva;
    private String numControl;
    private Date fechaResolucion;
    private Date fechaMinResolucion;
    private Double importeSolicitado;
    private Double importeAutorizado;
    private Double actualizacion;
    private Double intereses;
    private Double retIntereses;
    private Double importeCompensado;
    private Double importeNegado;
    private Double importeNetoDevolver;

    private Boolean mostrarDlg;
    private String estadoPantalla;

    @PostConstruct
    public void inicializar()
    {
        Map<String, Object> infoInicial = getBussinesDelegate().obtenerInfoInicial();
        tiposResolucion = (List<ItemComboBean>)infoInicial.get("tiposResolucion");
        idTipoResActiva = 0;
        mostrarDlg = Boolean.FALSE;
        estadoPantalla = "captDatosPrim";
        fechaMinResolucion = mbDetalleIcep.getFechaPrimerDecValidada();
    }
    
    public void insertar()
    {
      
        LOG.debug("INICIO insertar");
        numControl = numControl.toUpperCase();
        Map<String, Object> params = new HashMap<String, Object> ();
        params.put("idTipoResolucion", idTipoResActiva);
        params.put("idSaldoIcep", mbSession.getIdSaldoIcep());
        params.put("numControl", numControl);
        params.put("fechaResolucion", fechaResolucion);
        params.put("importeSolicitado", importeSolicitado);
        params.put("importeAutorizado", importeAutorizado);
        params.put("actualizacion", actualizacion);
        params.put("intereses", intereses);
        params.put("retIntereses", retIntereses);
        params.put("importeCompensado", importeCompensado);
        params.put("importeNetoDevolver", importeNetoDevolver);
        params.put("importeNegado", importeNegado);
        boolean inserto = false;
        try {
            Double remanente = mbDetalleIcep.getRemanenteFavCargo();
            String mensaje;
            boolean isNegadaDesistida = idTipoResActiva.equals(Constantes.TiposResolucion.NEGADA.getIdTipoResol()) || idTipoResActiva.equals(Constantes.TiposResolucion.DESISTIDA.getIdTipoResol());
            boolean evaluarImporteAutorizado = isNegadaDesistida ? isNegadaDesistida : importeAutorizado <= remanente;
            if (evaluarImporteAutorizado) {
                Map<String, Object> resp = bussinesDelegate.insertarDevolucion(params);
                inserto = (Boolean) resp.get("inserto");
                mensaje = !inserto ? (String) resp.get("mensaje") : null;
            } else {
                mensaje = "El importe autorizado (" + Utils.formatearMoneda(importeAutorizado) + ") no puede exceder el remanente del saldo a favor (" +
                                    Utils.formatearMoneda(remanente) + ")";
            }
            
            if (!inserto) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error -", mensaje));
                mostrarDlg = Boolean.TRUE;
            }
        }
        catch (SIATException e) {
            LOG.error(e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error -", e.getMessage()));
            mostrarDlg = Boolean.TRUE;
        }
        if(inserto){
            mbDetalleIcep.inicializar();
            mostrarDlg = Boolean.FALSE;
            limpiar();
        }
    }

    public void calcularImpNetoDev(){
        importeNetoDevolver = importeAutorizado + actualizacion - importeCompensado + intereses - retIntereses;
    }
    
    public void limpiar()
    {
        idTipoResActiva = null;
        numControl = null;
        fechaResolucion = null;
        importeSolicitado = null;
        importeAutorizado = null;
        actualizacion = null;
        intereses = null;
        retIntereses = null;
        importeCompensado = null;
        importeNegado = null;
        mostrarDlg = Boolean.FALSE;
        estadoPantalla = "captDatosPrim";
    }

    public void manejarChangeTxtImporteAutorizado()
    {
        LOG.debug("INICIO manejarChangeTxtImporteAutorizado");
        LOG.debug("idTipoResActiva ->" + idTipoResActiva + "<-");
        if(idTipoResActiva == 2){
            importeNegado = importeSolicitado - importeAutorizado;
        }
        calcularImpNetoDev();
    }

    public void mostrarDialog()
    {
        mostrarDlg = Boolean.TRUE;
    }

    public void avanzarRegistro()
    {
        LOG.debug("INICIO avanzarRegistro");
        estadoPantalla = "captDatosFinales";
        actualizacion = 0.0;
        intereses = 0.0;
        retIntereses = 0.0;
        importeCompensado = 0.0;
        boolean isNegadaDesistida = idTipoResActiva.equals(Constantes.TiposResolucion.NEGADA.getIdTipoResol()) || idTipoResActiva.equals(Constantes.TiposResolucion.DESISTIDA.getIdTipoResol());
        importeAutorizado = isNegadaDesistida ? new Double(0) : importeSolicitado;
        importeNegado = idTipoResActiva.equals(Constantes.TiposResolucion.NEGADA.getIdTipoResol()) ? importeSolicitado : 0.0;
    }

    public List<ItemComboBean> getTiposResolucion() {
        return tiposResolucion;
    }

    public void setTiposResolucion(List<ItemComboBean> tiposResolucion) {
        this.tiposResolucion = tiposResolucion;
    }

    public RegistrosManualesServiceImpl getBussinesDelegate() {
        return bussinesDelegate;
    }

    public void setBussinesDelegate(RegistrosManualesServiceImpl bussinesDelegate) {
        this.bussinesDelegate = bussinesDelegate;
    }

    public ItemComboBean getTipoResolucionSelec() {
        return tipoResolucionSelec;
    }

    public void setTipoResolucionSelec(ItemComboBean tipoResolucionSelec) {
        this.tipoResolucionSelec = tipoResolucionSelec;
    }

    public Integer getIdTipoResActiva() {
        return idTipoResActiva;
    }

    public void setIdTipoResActiva(Integer idTipoResActiva) {
        this.idTipoResActiva = idTipoResActiva;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public Date getFechaResolucion() {
        return (fechaResolucion != null) ? (Date) fechaResolucion.clone() : null;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = (fechaResolucion != null) ? (Date) fechaResolucion.clone() : null;
    }

    public Date getFechaMinResolucion() {
        return (fechaMinResolucion != null) ? (Date) fechaMinResolucion.clone() : null;
    }

    public void setFechaMinResolucion(Date fechaMinResolucion) {
        this.fechaMinResolucion = (fechaMinResolucion != null) ? (Date) fechaMinResolucion.clone() : null;
    }

    public Double getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setImporteSolicitado(Double importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public Double getImporteAutorizado() {
        return importeAutorizado;
    }

    public void setImporteAutorizado(Double importeAutorizado) {
        this.importeAutorizado = importeAutorizado;
    }

    public Double getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Double actualizacion) {
        this.actualizacion = actualizacion;
    }

    public Double getIntereses() {
        return intereses;
    }

    public void setIntereses(Double intereses) {
        this.intereses = intereses;
    }

    public Double getRetIntereses() {
        return retIntereses;
    }

    public void setRetIntereses(Double retIntereses) {
        this.retIntereses = retIntereses;
    }

    public Double getImporteCompensado() {
        return importeCompensado;
    }

    public void setImporteCompensado(Double importeCompensado) {
        this.importeCompensado = importeCompensado;
    }

    public Double getImporteNegado() {
        return importeNegado;
    }

    public void setImporteNegado(Double importeNegado) {
        this.importeNegado = importeNegado;
    }

    public DetalleIcepMB getMbDetalleIcep() {
        return mbDetalleIcep;
    }

    public void setMbDetalleIcep(DetalleIcepMB mbDetalleIcep) {
        this.mbDetalleIcep = mbDetalleIcep;
    }

    public Boolean getMostrarDlg() {
        return mostrarDlg;
    }

    public void setMostrarDlg(Boolean mostrarDlg) {
        this.mostrarDlg = mostrarDlg;
    }

    public CalcularActualizacionResDevoluService getCalcularActualizacionResDevoluService() {
        return calcularActualizacionResDevoluService;
    }

    public void setCalcularActualizacionResDevoluService(CalcularActualizacionResDevoluService calcularActualizacionResDevoluService) {
        this.calcularActualizacionResDevoluService = calcularActualizacionResDevoluService;
    }

    public String getEstadoPantalla() {
        return estadoPantalla;
    }

    public void setEstadoPantalla(String estadoPantalla) {
        this.estadoPantalla = estadoPantalla;
    }
    
    public Double getImporteNetoDevolver() {
        return importeNetoDevolver;
    }

    public void setImporteNetoDevolver(Double importeNetoDevolver) {
        this.importeNetoDevolver = importeNetoDevolver;
    }
}
