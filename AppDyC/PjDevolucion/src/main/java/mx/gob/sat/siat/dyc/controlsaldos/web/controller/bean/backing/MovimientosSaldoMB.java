package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.MovimientosSaldoBussinesDel;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridMovsSaldoBean;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;


/**
 *
 * @author Softtek
 */
@ManagedBean(name = "mbMovimientosSaldo")
@ViewScoped
public class MovimientosSaldoMB
{
    private static final Logger LOG = Logger.getLogger(MovimientosSaldoMB.class.getName());

    @ManagedProperty(value = "#{delegateMovimientosSaldo}")
    private MovimientosSaldoBussinesDel delegate;

    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;

    @ManagedProperty(value = "#{detalleIcepMB}")
    private DetalleIcepMB mbDetalleIcep;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private Boolean mostrarDialog;
    private Boolean mostrarDlgConfirmElim;
    private Boolean mostrarDlgAgregarMov;

    private List<FilaGridMovsSaldoBean> filas;
    private FilaGridMovsSaldoBean filaSelec;
    private FilaGridMovsSaldoBean movimientoNuevo;

    private Boolean habilitarBtnActivarMov;
    private Boolean habilitarBtnInactivarMov;

    public void inicializar()
    {
        LOG.debug("inicializar");
        Map<String, Object> infoInicial= delegate.obtenerInfoInicial(mbSession.getIdSaldoIcep());

        filas = (List<FilaGridMovsSaldoBean>)infoInicial.get("filasMovimientos");

        LOG.debug("numero de movs ->" + filas.size());
        movimientoNuevo = new FilaGridMovsSaldoBean();

        mostrarDialog = Boolean.TRUE;
        habilitarBtnActivarMov = Boolean.FALSE;
        habilitarBtnInactivarMov = Boolean.FALSE;
    }

    public void cerrar(){
        LOG.debug("INICIO cerrar");
        mostrarDialog = Boolean.FALSE;
        
        mbDetalleIcep.inicializar();
    }

    public void mostrarDialogConfirm(){
        LOG.debug("INICIO mostrarDialogConfirm");
        mostrarDlgConfirmElim = Boolean.TRUE;
    }

    public void manejarNoConfirm(){
        LOG.debug("INICIO manejarNoConfirm");
        mostrarDlgConfirmElim = Boolean.FALSE;
    }
    
    public void eliminarCargos(){
        LOG.debug("INICIO eliminarCargos");
        LOG.debug("idMovSaldo ->" + filaSelec.getIdMovSaldo());
    }
    
    public void mostrarDialogAgregarMov(){
        LOG.debug("INICIO mostrarDialogAgregarMov");
        mostrarDlgAgregarMov = Boolean.TRUE;
    }

    public void cerrarDialogAgregarMov(){
        LOG.debug("INICIO cerrarDialogAgregarMov");
        mostrarDlgAgregarMov = Boolean.FALSE;
    }

    public void agregarMovimiento(){
        LOG.debug("INICIO agregarMovimiento");
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put("idSaldoIcep", mbSession.getIdSaldoIcep());
        params.put("idTipoAfectacion", movimientoNuevo.getIdAfectacion());
        params.put("monto", new BigDecimal(movimientoNuevo.getMonto()));
        params.put("fechaOrigen", movimientoNuevo.getFechaOrigen());
        params.put("origen", movimientoNuevo.getOrigen());
        params.put("permisoAjustar", mbSession.getPermisoAjustar());
        params.put("justificacion", movimientoNuevo.getJustificacion());

        delegate.insertarMovimiento(params);

        mostrarDlgAgregarMov = Boolean.FALSE;
        filas = delegate.obtenerMovimientos(mbSession.getIdSaldoIcep());
    }
    
    public void manejarRowSelectMovimiento() {
        LOG.debug("manejarRowSelectMovimiento ->" + filaSelec + "<-");
        LOG.debug("idMovimiento ->" + filaSelec.getIdMovimiento() + "<-");
        if(filaSelec.getIdMovimiento() != Constantes.MovsIcep.ABONO_SAFCONTRIB.getIdMovimiento().intValue()){
            if("Vigente".equals(filaSelec.getEstatus())){
                habilitarBtnActivarMov = Boolean.FALSE;
                habilitarBtnInactivarMov = Boolean.TRUE;
            }
            else if("Inactivado".equals(filaSelec.getEstatus()))
            {
                habilitarBtnActivarMov = Boolean.TRUE;
                habilitarBtnInactivarMov = Boolean.FALSE;
            }
        }
        else{
            habilitarBtnActivarMov = Boolean.FALSE;
            habilitarBtnInactivarMov = Boolean.FALSE;
        }
    }


    public Boolean getMostrarDialog() {
        return mostrarDialog;
    }

    public void setMostrarDialog(Boolean mostrarDialog) {
        this.mostrarDialog = mostrarDialog;
    }

    public List<FilaGridMovsSaldoBean> getFilas() {
        return filas;
    }

    public void setFilas(List<FilaGridMovsSaldoBean> filas) {
        this.filas = filas;
    }

    public FilaGridMovsSaldoBean getFilaSelec() {
        return filaSelec;
    }

    public void setFilaSelec(FilaGridMovsSaldoBean filaSelec) {
        this.filaSelec = filaSelec;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }

    public MovimientosSaldoBussinesDel getDelegate() {
        return delegate;
    }

    public void setDelegate(MovimientosSaldoBussinesDel delegate) {
        this.delegate = delegate;
    }

    public Boolean getMostrarDlgConfirmElim() {
        return mostrarDlgConfirmElim;
    }

    public void setMostrarDlgConfirmElim(Boolean mostrarDlgConfirmElim) {
        this.mostrarDlgConfirmElim = mostrarDlgConfirmElim;
    }

    public Boolean getMostrarDlgAgregarMov() {
        return mostrarDlgAgregarMov;
    }

    public void setMostrarDlgAgregarMov(Boolean mostrarDlgAgregarMov) {
        this.mostrarDlgAgregarMov = mostrarDlgAgregarMov;
    }

    public FilaGridMovsSaldoBean getMovimientoNuevo() {
        return movimientoNuevo;
    }

    public void setMovimientoNuevo(FilaGridMovsSaldoBean movimientoNuevo) {
        this.movimientoNuevo = movimientoNuevo;
    }

    public DetalleIcepMB getMbDetalleIcep() {
        return mbDetalleIcep;
    }

    public void setMbDetalleIcep(DetalleIcepMB mbDetalleIcep) {
        this.mbDetalleIcep = mbDetalleIcep;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public Boolean getHabilitarBtnActivarMov() {
        return habilitarBtnActivarMov;
    }

    public void setHabilitarBtnActivarMov(Boolean habilitarBtnActivarMov) {
        this.habilitarBtnActivarMov = habilitarBtnActivarMov;
    }

    public Boolean getHabilitarBtnInactivarMov() {
        return habilitarBtnInactivarMov;
    }

    public void setHabilitarBtnInactivarMov(Boolean habilitarBtnInactivarMov) {
        this.habilitarBtnInactivarMov = habilitarBtnInactivarMov;
    }
}
