package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.controlsaldos.service.DetalleIcepService;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.DeclaracionManualBussinesDel;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.MovimientosSaldoBussinesDel;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


/**
 *
 * @author Softtek
 */
@ManagedBean(name = "mbConfirmarAjuste")
@ViewScoped
public class ConfirmarAjusteMB {

    private static final Logger LOG = Logger.getLogger(ConfirmarAjusteMB.class.getName());

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;

    @ManagedProperty(value = "#{delegateDeclaManual}")
    private DeclaracionManualBussinesDel delegDeclaraManual;

    @ManagedProperty(value = "#{detalleIcepMB}")
    private DetalleIcepMB mbDetalleIcep;

    @ManagedProperty(value = "#{delegateMovimientosSaldo}")
    private MovimientosSaldoBussinesDel delegateMovsSaldo;

    @ManagedProperty(value = "#{mbMovimientosSaldo}")
    private MovimientosSaldoMB mbMovsSaldo;

    private Boolean mostrarDlg;
    private String justificacion;

    private String headerDialog;
    private String ayuda1;
    private String ayuda2;
    private String ayudaJustificacion;
    private String ayudaJustificacion2;

    private String btnInvocador;

    public void mostrarDialog(ActionEvent evento) {
        LOG.debug("INICIO mostrarDialog; evento ->" + evento);

        LOG.debug("evento ->" + evento + "<-");
        UIComponent c = evento.getComponent();
        LOG.debug("idButton ->" + c.getId() + "<-");

        btnInvocador = c.getId();

        if ("btnValidarDeclara".equals(btnInvocador)) {
            headerDialog = "Confirmar que la declaración es válida";
            ayuda1 =
                    "Validar la declaración significa que usted da fe de que esta se encuentra registrada debidamente en algún(os) sistema(s) alterno del SAT.";
            ayuda2 =
                    "Una vez validada la declaración será tomada en cuenta para determinar el saldo a favor del ICEP.";
            ayudaJustificacion = "Justifique el porque se valida la declaración.";
            ayudaJustificacion2 = "Por ejemplo: \"Se valida que la declaración se encuentra registrada en DYP\"";
        } else if ("btnActivarMovimiento".equals(btnInvocador)) {
            headerDialog = "Confirmar activación del movimiento";
            ayuda1 =
                    "Activar un movimiento significa que usted en base a un análisis determina que el movimiento debe ser tomado en cuenta para calcular el remanente del ICEP.";
            ayuda2 = "Si el movimiento se activa será tomado en cuenta para determinar el remanente del ICEP.";
            ayudaJustificacion = "Justifique porque se activa nuevamente el movimiento.";
            ayudaJustificacion2 = "Por ejemplo: 'El abono/cargo había sido inactivado erroneamente'.";
        } else if ("btnInactivarMovimiento".equals(btnInvocador)) {
            headerDialog = "Confirmar inactivación del movimiento";
            ayuda1 =
                    "Inactivar un movimiento significa que usted en base a un análisis determina que el abono/cargo no debe afectar el calculo de remanente del ICEP.";
            ayuda2 =
                    "Inactivar el movimiento es equivalente a eliminarlo en cuanto al calculo de remanente se refiere.";
            ayudaJustificacion =
                    "Describa el motivo por el cual el abono/cargo no debe ser tomado en cuenta para determinar el remanente.";
            ayudaJustificacion2 =
                    "Por ejemplo: 'El Contribuyente ingreso 2 veces el mismo aviso de compensación, por lo que se inactiva un cargo'.";
        }

        mostrarDlg = Boolean.TRUE;
        justificacion = null;
    }

    public void cancelar() {
        LOG.debug("INICIO cancelarValidaDeclaracion");
        setJustificacion(null);
        mostrarDlg = (Boolean.FALSE);
    }

    public String confirmarAjuste() {
        LOG.debug("INICIO confirmarAjuste");
        LOG.debug("justificacionValidDeclara ->" + getJustificacion());


        Map<String, Object> params = new HashMap<String, Object>();

        AccesoUsr accesoUsrL = getServiceObtenerSesion().execute();
        params.put("usuarioValida", accesoUsrL.getNombreCompleto());
        params.put("justificacion", getJustificacion());
        params.put("idSaldoIcep", getMbSession().getIdSaldoIcep());

        Map<String, Object> result = null;

        if ("btnValidarDeclara".equals(btnInvocador)) {
            LOG.debug("se tiene que validar la declaracion");

            params.put("idDeclaraIcep", mbDetalleIcep.getDeclaraSelec().getIdDeclaraIcep());
            if(mbDetalleIcep.getDeclaraSelec().getOrigenDeclara().equalsIgnoreCase("Manual")){
                params.put("origenDeclara", Constantes.OrigenesDeclaracion.MANUAL);
            }
            result = delegDeclaraManual.validarDeclaracion(params);

            if ((Boolean)result.get("success")) {
                mostrarDlg = Boolean.FALSE;
                mbDetalleIcep.inicializar();
                /**
                 * FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se validó correctamente la declaración -", "refresque la página para ver los cambios");
                 * FacesContext.getCurrentInstance().addMessage(null, mensaje);
                */

                return null;
            } else {
                FacesMessage msjErrorValidarDeclara =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar el estatus de la declaración a validada -",
                                     (String)result.get(DetalleIcepService.KEY_MENSAJE));

                FacesContext.getCurrentInstance().addMessage(null, msjErrorValidarDeclara);
                return null;
            }
        } else {
            params.put("idMovSaldo", mbMovsSaldo.getFilaSelec().getIdMovSaldo());
            params.put("permisoAjustar", mbSession.getPermisoAjustar());

            if ("btnActivarMovimiento".equals(btnInvocador)) {
                LOG.debug("activar movimiento .....");
                params.put("tipoAccion",
                           mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.VALIDAR);
            } else if ("btnInactivarMovimiento".equals(btnInvocador)) {
                params.put("tipoAccion",
                           mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.INVALIDAR);
            }

            delegateMovsSaldo.actualizarMovimiento(params);

            mostrarDlg = Boolean.FALSE;
            justificacion = null;
            mbMovsSaldo.inicializar();
        }

        return null;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }

    public DeclaracionManualBussinesDel getDelegDeclaraManual() {
        return delegDeclaraManual;
    }

    public void setDelegDeclaraManual(DeclaracionManualBussinesDel delegDeclaraManual) {
        this.delegDeclaraManual = delegDeclaraManual;
    }

    public String getHeaderDialog() {
        return headerDialog;
    }

    public void setHeaderDialog(String headerDialog) {
        this.headerDialog = headerDialog;
    }

    public Boolean getMostrarDlg() {
        return mostrarDlg;
    }

    public void setMostrarDlg(Boolean mostrarDlg) {
        this.mostrarDlg = mostrarDlg;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getAyuda1() {
        return ayuda1;
    }

    public void setAyuda1(String ayuda1) {
        this.ayuda1 = ayuda1;
    }

    public String getAyuda2() {
        return ayuda2;
    }

    public void setAyuda2(String ayuda2) {
        this.ayuda2 = ayuda2;
    }

    public String getAyudaJustificacion() {
        return ayudaJustificacion;
    }

    public void setAyudaJustificacion(String ayudaJustificacion) {
        this.ayudaJustificacion = ayudaJustificacion;
    }

    public String getAyudaJustificacion2() {
        return ayudaJustificacion2;
    }

    public void setAyudaJustificacion2(String ayudaJustificacion2) {
        this.ayudaJustificacion2 = ayudaJustificacion2;
    }

    public DetalleIcepMB getMbDetalleIcep() {
        return mbDetalleIcep;
    }

    public void setMbDetalleIcep(DetalleIcepMB mbDetalleIcep) {
        this.mbDetalleIcep = mbDetalleIcep;
    }

    public MovimientosSaldoBussinesDel getDelegateMovsSaldo() {
        return delegateMovsSaldo;
    }

    public void setDelegateMovsSaldo(MovimientosSaldoBussinesDel delegateMovsSaldo) {
        this.delegateMovsSaldo = delegateMovsSaldo;
    }

    public MovimientosSaldoMB getMbMovsSaldo() {
        return mbMovsSaldo;
    }

    public void setMbMovsSaldo(MovimientosSaldoMB mbMovsSaldo) {
        this.mbMovsSaldo = mbMovsSaldo;
    }
}
