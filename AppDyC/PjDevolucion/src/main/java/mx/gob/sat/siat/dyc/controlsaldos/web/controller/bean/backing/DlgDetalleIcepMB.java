package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.registrarinformaciondeicep.service.impl.DlgDetalleIcepServiceImpl;
import mx.gob.sat.siat.dyc.controlsaldos.service.BusquedaIcepsService;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;


@ManagedBean(name = "mbDlgDetalleIcep")
@SessionScoped
public class DlgDetalleIcepMB
{
    private static final Logger LOG = Logger.getLogger(DlgDetalleIcepMB.class);

    @ManagedProperty(value = "#{bdDlgDetalleIcep}")
    private DlgDetalleIcepServiceImpl delegate;

    @ManagedProperty(value = "#{serviceBusquedaIceps}")
    private BusquedaIcepsService serviceBusqueda;

    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;

    @ManagedProperty(value = "#{personaIDCService}")
    private PersonaIDCService serviceIdentPersona;

    private String rfc;
    private Integer idImpuesto;
    private Integer idConcepto;
    private String idTipoPeriodo;
    private Integer idPeriodo;
    private Integer ejercicio;
    private Integer tipoSaldo;
    private String nombreRazonSocial;
    
    private List<ItemComboBean> conceptos;
    private List<ItemComboBean> periodos;
    
    // (abajo) sinValidar rfcValidado rfcIncorrecto
    private String estado; 
    private String etiquetaNomRazonsoc;
    private Boolean mostrarDlgMensaje;
    private String mensajeSuccess;

    private Boolean mostrarse;

    public void registrarIcep()
    {
        LOG.debug("INICIO registrarIcep ..." + rfc);
        rfc = rfc.toUpperCase();
        Map<String, Object> params = new HashMap<String, Object> ();
        params.put("rfc", rfc);
        params.put("idConcepto", idConcepto);
        params.put("idPeriodo", idPeriodo);
        params.put("ejercicio", ejercicio);
        params.put("tipoSaldo", tipoSaldo);
        
        Map<String, Object> resultInsert = delegate.insertar(params);    
        
        if((Boolean)resultInsert.get("success"))
        {
            mbSession.setIdSaldoIcep((Integer)resultInsert.get("idSaldoIcep"));
            mostrarDlgMensaje = Boolean.TRUE;
            mensajeSuccess = "El ICEP " + mbSession.getIdSaldoIcep() + " se generó satisfactoriamente";
            mbSession.setNomRazonSocIcepActivo(nombreRazonSocial);
        }
        else{
            FacesContext context = FacesContext.getCurrentInstance();  
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al insertar el ICEP -", (String)resultInsert.get("mensaje")));
        }
    }

    public void actualizarConceptos()
    {
        if(idImpuesto > 0){
            setConceptos(getServiceBusqueda().obtenerConceptosXImpuesto(idImpuesto));
        }
        else{
            setConceptos(null); 
        }
    }

    public void actualizarPeriodos()
    {
        if(idTipoPeriodo != null && !"".equals(idTipoPeriodo)){
            periodos = serviceBusqueda.obtenerPeriodosXTipo(idTipoPeriodo);
            if(periodos.size() == 1){
                idPeriodo = periodos.get(0).getId();
            }
        }
        else{
            periodos = null; 
        }
    }

    public void obtenerNombreRazonSocial()
    {
        LOG.debug("INICIO obtenerNombrerazonsocial");

        try {
            ParamOutputTO<PersonaDTO> paramPersonaDTO = serviceIdentPersona.findContribuyente(rfc);
            PersonaDTO contribuyente = paramPersonaDTO.getOutput();
            if(contribuyente != null)
            {
                PersonaIdentificacionDTO ident = contribuyente.getPersonaIdentificacion();
                if(ident.getNombre() != null){
                    nombreRazonSocial = ident.getNombre() + " " + ident.getApPaterno() + " " + ident.getApMaterno();
                    etiquetaNomRazonsoc = "Nombre:";
                }
                else{
                    nombreRazonSocial = ident.getRazonSocial() + " " + ident.getTipoSociedad();
                    etiquetaNomRazonsoc = "Razón social:";
                }
                estado= "rfcValidado";
            }
            else{
                LOG.debug("No se encontro el RFC ->" + rfc + "<-");
                nombreRazonSocial = null;
                estado = "rfcIncorrecto";
                FacesContext context = FacesContext.getCurrentInstance();  
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"RFC no válido -", "el RFC '" + rfc + "' no se encuentra registrado"));
            }
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
    }

    public void manejarBtnCambiarRfc()
    {
        LOG.debug("INICIO manejarBtnCambiarRfc");
        estado = "sinValidar";
    }

    public void cancelar()
    {
        LOG.debug("cancelar");
        mostrarse = Boolean.FALSE;
        estado = "sinValidar";
        rfc = null;
        idImpuesto = null;
        idConcepto = null;
        idTipoPeriodo = null;
        idPeriodo = null;
        ejercicio = null;
        tipoSaldo = null;
        mensajeSuccess = null;
    }

    public String irADetalle(){
        LOG.debug("INICIO irADetalle");
        cancelar();
        mostrarDlgMensaje = Boolean.FALSE;
        return "detalleIcep";
    }
    
    public void cerrarDialogs(){
        cancelar();
        mostrarDlgMensaje = Boolean.FALSE;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Integer getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(Integer idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getIdTipoPeriodo() {
        return idTipoPeriodo;
    }

    public void setIdTipoPeriodo(String idTipoPeriodo) {
        this.idTipoPeriodo = idTipoPeriodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getTipoSaldo() {
        return tipoSaldo;
    }

    public void setTipoSaldo(Integer tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public List<ItemComboBean> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<ItemComboBean> conceptos) {
        this.conceptos = conceptos;
    }

    public List<ItemComboBean> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<ItemComboBean> periodos) {
        this.periodos = periodos;
    }

    public BusquedaIcepsService getServiceBusqueda() {
        return serviceBusqueda;
    }

    public void setServiceBusqueda(BusquedaIcepsService serviceBusqueda) {
        this.serviceBusqueda = serviceBusqueda;
    }

    public DlgDetalleIcepServiceImpl getDelegate() {
        return delegate;
    }

    public void setDelegate(DlgDetalleIcepServiceImpl delegate) {
        this.delegate = delegate;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public PersonaIDCService getServiceIdentPersona() {
        return serviceIdentPersona;
    }

    public void setServiceIdentPersona(PersonaIDCService serviceIdentPersona) {
        this.serviceIdentPersona = serviceIdentPersona;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEtiquetaNomRazonsoc() {
        return etiquetaNomRazonsoc;
    }

    public void setEtiquetaNomRazonsoc(String etiquetaNomRazonsoc) {
        this.etiquetaNomRazonsoc = etiquetaNomRazonsoc;
    }

    public Boolean getMostrarDlgMensaje() {
        return mostrarDlgMensaje;
    }

    public void setMostrarDlgMensaje(Boolean mostrarDlgMensaje) {
        this.mostrarDlgMensaje = mostrarDlgMensaje;
    }

    public Boolean getMostrarse() {
        return mostrarse;
    }

    public void setMostrarse(Boolean mostrarse) {
        this.mostrarse = mostrarse;
    }

    public String getMensajeSuccess() {
        return mensajeSuccess;
    }

    public void setMensajeSuccess(String mensajeSuccess) {
        this.mensajeSuccess = mensajeSuccess;
    }
}
