package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.controlsaldos.service.BusquedaIcepsService;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.RegistrosManualesServiceImpl;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;


@ManagedBean(name = "mbCompManual")
@ViewScoped
public class CompensacionManualMB
{
    private static final Logger LOG = Logger.getLogger(CompensacionManualMB.class.getName());

    @ManagedProperty(value = "#{serviceBusquedaIceps}")
    private BusquedaIcepsService serviceBusqueda;

    @ManagedProperty(value = "#{bdRegistrosManuales}")
    private RegistrosManualesServiceImpl serviceRegManual;

    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;

    @ManagedProperty(value = "#{detalleIcepMB}")
    private DetalleIcepMB mbDetalleIcep;

    private String numControl;
    private Date fechaDeclDest;
    private Integer idImpuestoDest;
    private Integer idConceptoDest;
    private String idTipoPeriodoDest;
    private Integer idPeriodoDest;
    private Integer idEjercicioDest;
    private Double importeComp;
    private Date fechaResol;
    private Integer idTipoResol;
    private BigDecimal importeLiquidado;
    private String numDocDeterminante;
    private String notas;

    private Boolean mostrarDlgComp;

    private List<ItemComboBean> conceptos;
    private List<ItemComboBean> periodos;

    public void insertar()
    {
        LOG.debug("INICIO insertar");

        numControl = numControl.toUpperCase();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("numControl", numControl);
        params.put("fechaDeclDest", fechaDeclDest);
        LOG.debug("idConceptoDest ->" + idConceptoDest + "<-");
        params.put("idConceptoDest", idConceptoDest);
        params.put("idPeriodoDest", idPeriodoDest);
        params.put("idEjercicioDest", idEjercicioDest);
        params.put("importeComp", importeComp);
        params.put("fechaResol", fechaResol);
        params.put("idTipoResol", idTipoResol);
        params.put("importeLiquidado", importeLiquidado);
        params.put("numDocDeterminante", numDocDeterminante);
        params.put("notas", notas);
        params.put("idSaldoIcep", mbSession.getIdSaldoIcep());
        params.put("rfc", mbSession.getRfc());
        try {
            Map<String, Object> resp = serviceRegManual.insertarCompensacion(params);
            if((Boolean)resp.get("inserto")){
                getMbDetalleIcep().inicializar();
                mostrarDlgComp = Boolean.FALSE;
                limpiar();
            }
            else{
                LOG.debug("mensaje ->" + (String)resp.get("mensaje"));
                
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error -", (String)resp.get("mensaje")));
                mostrarDlgComp = Boolean.TRUE;
            }
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
    }

    public void limpiar()
    {
        numControl = null;
        fechaDeclDest = null;
        idImpuestoDest = null;
        idConceptoDest = null;
        idTipoPeriodoDest = null;
        idPeriodoDest = null;
        idEjercicioDest = null;
        importeComp = null;
        fechaResol = null;
        idTipoResol = null;
        setImporteLiquidado(null);
        numDocDeterminante = null;
        notas = null;

        conceptos = null;
        periodos = null;
        
        mostrarDlgComp = Boolean.FALSE;
    }

    public void actualizarConceptos()
    {
        LOG.debug("INICIO actualizarConceptos; idImpuestoDest ->" + idImpuestoDest);
        if(idImpuestoDest > 0){
            setConceptos(getServiceBusqueda().obtenerConceptosXImpuesto(idImpuestoDest));
        }
        else{
            setConceptos(null); 
        }
    }

    public void actualizarPeriodos()
    {
        if(idTipoPeriodoDest != null && !"".equals(idTipoPeriodoDest)){
            periodos = serviceBusqueda.obtenerPeriodosXTipo(idTipoPeriodoDest);
            if(periodos.size() == 1){
                idPeriodoDest = periodos.get(0).getId();
            }
        }
        else{
            periodos = null; 
        }
    }
    
    public void manejarChangeFechaDeclaDestino()
    {
        LOG.debug("INICIO manejarChangeFechaDeclaDestino");
        LOG.debug("fecha declaracion destino ->" + fechaDeclDest + "<-");
    }
    
    public void validarNumControl()
    {
        LOG.debug("INICIO manejarChangeFechaDeclaDestino");
        LOG.debug("existe numcontrol ->" + numControl);
    }
    
    public void mostrarDialog()
    {
        mostrarDlgComp = Boolean.TRUE;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public Date getFechaDeclDest() {
        return (fechaDeclDest != null) ? (Date) fechaDeclDest.clone() : null;
    }

    public void setFechaDeclDest(Date fechaDeclDest) {
        this.fechaDeclDest = (fechaDeclDest != null) ? (Date) fechaDeclDest.clone() : null;
    }

    public Integer getIdConceptoDest() {
        return idConceptoDest;
    }

    public void setIdConceptoDest(Integer idConceptoDest) {
        this.idConceptoDest = idConceptoDest;
    }

    public Integer getIdPeriodoDest() {
        return idPeriodoDest;
    }

    public void setIdPeriodoDest(Integer idPeriodoDest) {
        this.idPeriodoDest = idPeriodoDest;
    }

    public Integer getIdEjercicioDest() {
        return idEjercicioDest;
    }

    public void setIdEjercicioDest(Integer idEjercicioDest) {
        this.idEjercicioDest = idEjercicioDest;
    }

    public Double getImporteComp() {
        return importeComp;
    }

    public void setImporteComp(Double importeComp) {
        this.importeComp = importeComp;
    }

    public Date getFechaResol() {
        return (fechaResol != null) ? (Date) fechaResol.clone() : null;
    }

    public void setFechaResol(Date fechaResol) {
        this.fechaResol = (fechaResol != null) ? (Date) fechaResol.clone() : null;
    }

    public Integer getIdTipoResol() {
        return idTipoResol;
    }

    public void setIdTipoResol(Integer idTipoResol) {
        this.idTipoResol = idTipoResol;
    }

    public Integer getIdImpuestoDest() {
        return idImpuestoDest;
    }

    public void setIdImpuestoDest(Integer idImpuestoDest) {
        this.idImpuestoDest = idImpuestoDest;
    }

    public String getIdTipoPeriodoDest() {
        return idTipoPeriodoDest;
    }

    public void setIdTipoPeriodoDest(String idTipoPeriodoDest) {
        this.idTipoPeriodoDest = idTipoPeriodoDest;
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

    public RegistrosManualesServiceImpl getServiceRegManual() {
        return serviceRegManual;
    }

    public void setServiceRegManual(RegistrosManualesServiceImpl serviceRegManual) {
        this.serviceRegManual = serviceRegManual;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }

    public String getNumDocDeterminante() {
        return numDocDeterminante;
    }

    public void setNumDocDeterminante(String numDocDeterminante) {
        this.numDocDeterminante = numDocDeterminante;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public DetalleIcepMB getMbDetalleIcep() {
        return mbDetalleIcep;
    }

    public void setMbDetalleIcep(DetalleIcepMB mbDetalleIcep) {
        this.mbDetalleIcep = mbDetalleIcep;
    }

    public BigDecimal getImporteLiquidado() {
        return importeLiquidado;
    }

    public void setImporteLiquidado(BigDecimal importeLiquidado) {
        this.importeLiquidado = importeLiquidado;
    }

    public Boolean getMostrarDlgComp() {
        return mostrarDlgComp;
    }

    public void setMostrarDlgComp(Boolean mostrarDlgComp) {
        this.mostrarDlgComp = mostrarDlgComp;
    }
}
