package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.MovimientosSaldoBussinesDel;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridPermisosAjusteBean;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


/**
 *
 * @author Softtek
 */
@ManagedBean(name = "mbPermisosAjustes")
@SessionScoped
public class PermisosAjusteMB {
    private static final Logger LOG = Logger.getLogger(PermisosAjusteMB.class.getName());

    @ManagedProperty(value = "#{delegateMovimientosSaldo}")
    private MovimientosSaldoBussinesDel delegate;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;

    private Boolean mostrarDlgDetallePermiso;

    private String palabraClave;

    private Integer numEmpleado;
    private String rfc;
    private String nombre;
    private String unidadAdministrativa;
    private String rolMatDyc;
    /*
     * buscandoUsuario, usuarioCargado
     */
    private String estatusDlgOtorgarPermiso;

    private List<FilaGridPermisosAjusteBean> filas;
    private FilaGridPermisosAjusteBean permisoSelec;

    private Boolean mostrarDlgConfirmRevocar;

    @PostConstruct
    public void cargarGridPermisosVigentes() {
        LOG.debug("INICIO cargarGridPermisosVigentes");
        if (getMbSession().getEsAdminCentral()) {
            filas = delegate.obtenerPermisosVigentes();
        }
    }

    public void mostrarDlgAgregarPermiso() {
        LOG.debug("INICIO mostrarDlgAgregarPermiso");
        estatusDlgOtorgarPermiso = "buscandoUsuario";
        mostrarDlgDetallePermiso = Boolean.TRUE;
        palabraClave = null;
    }

    public void cerrarDlgAgregarPermiso() {
        LOG.debug("INICIO cerrarDlgAgregarPermiso");
        mostrarDlgDetallePermiso = Boolean.FALSE;
    }

    public void otorgarPermiso() {
        LOG.debug("INICIO otorgarPermiso");
        AccesoUsr accesoUsrL = getServiceObtenerSesion().execute();

        delegate.insertarPermiso(numEmpleado, Integer.parseInt(accesoUsrL.getNumeroEmp()));
        mostrarDlgDetallePermiso = Boolean.FALSE;
        filas = delegate.obtenerPermisosVigentes();
    }

    public void buscarEmpleado() {
        LOG.debug("INICIO buscarEmpleado");
        Map<String, Object> respBusqEmpleado = delegate.obtenerInfoEmpleado(palabraClave);
        if ((Boolean) respBusqEmpleado.get("userValido")) {
            setNumEmpleado((Integer) respBusqEmpleado.get("numEmpleado"));
            rfc = (String) respBusqEmpleado.get("rfc");
            nombre = (String) respBusqEmpleado.get("nombre");
            unidadAdministrativa = (String) respBusqEmpleado.get("unidadAdministrativa");
            rolMatDyc = (String) respBusqEmpleado.get("rolMatDyc");
            estatusDlgOtorgarPermiso = "usuarioCargado";
        } else {
            LOG.debug("no se encontro un usuario valido");
            estatusDlgOtorgarPermiso = "buscandoUsuario";
            FacesMessage msjUserNoValido = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al buscar al usuario -", (String) respBusqEmpleado.get("mensaje"));

            FacesContext.getCurrentInstance().addMessage(null, msjUserNoValido);
        }
    }

    public void manejarBtnCambiarUsuario() {
        LOG.debug("INICIO manejarBtnCambiarUsuario");
        estatusDlgOtorgarPermiso = "buscandoUsuario";

    }

    public void mostrarDlgConfirmRevocar() {
        mostrarDlgConfirmRevocar = Boolean.TRUE;
    }

    public void cancelarRevocacion() {
        LOG.debug("INICIO cancelarRevocacion");
        mostrarDlgConfirmRevocar = Boolean.FALSE;
    }

    public void revocarPermiso() {
        LOG.debug("INICIO revocarPermiso");
        if (permisoSelec != null) {
            AccesoUsr accesoUsrL = getServiceObtenerSesion().execute();

            delegate.revocarGrantAjuste(permisoSelec.getNumEmpleado(),
                    Integer.parseInt(accesoUsrL.getNumeroEmp()));
            mostrarDlgConfirmRevocar = Boolean.FALSE;
            filas = delegate.obtenerPermisosVigentes();
        }
    }

    public List<FilaGridPermisosAjusteBean> getFilas() {
        return filas;
    }

    public void setFilas(List<FilaGridPermisosAjusteBean> filas) {
        this.filas = filas;
    }

    public Boolean getMostrarDlgDetallePermiso() {
        return mostrarDlgDetallePermiso;
    }

    public void setMostrarDlgDetallePermiso(Boolean mostrarDlgDetallePermiso) {
        this.mostrarDlgDetallePermiso = mostrarDlgDetallePermiso;
    }

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(String unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public String getRolMatDyc() {
        return rolMatDyc;
    }

    public void setRolMatDyc(String rolMatDyc) {
        this.rolMatDyc = rolMatDyc;
    }

    public MovimientosSaldoBussinesDel getDelegate() {
        return delegate;
    }

    public void setDelegate(MovimientosSaldoBussinesDel delegate) {
        this.delegate = delegate;
    }

    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getEstatusDlgOtorgarPermiso() {
        return estatusDlgOtorgarPermiso;
    }

    public void setEstatusDlgOtorgarPermiso(String estatusDlgOtorgarPermiso) {
        this.estatusDlgOtorgarPermiso = estatusDlgOtorgarPermiso;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public FilaGridPermisosAjusteBean getPermisoSelec() {
        return permisoSelec;
    }

    public void setPermisoSelec(FilaGridPermisosAjusteBean permisoSelec) {
        this.permisoSelec = permisoSelec;
    }

    public Boolean getMostrarDlgConfirmRevocar() {
        return mostrarDlgConfirmRevocar;
    }

    public void setMostrarDlgConfirmRevocar(Boolean mostrarDlgConfirmRevocar) {
        this.mostrarDlgConfirmRevocar = mostrarDlgConfirmRevocar;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }

}
