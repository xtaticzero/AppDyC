package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import mx.gob.sat.siat.dyc.controlsaldos.service.impl.MovimientosSaldoBussinesDel;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridAccionesAjusteBean;

import org.apache.log4j.Logger;


/**
 *
 * @author Softtek
 */

@ManagedBean(name = "mbBitacoraAjustes")
@SessionScoped
public class BitacoraAjustesMB
{
    private static final Logger LOG = Logger.getLogger(BitacoraAjustesMB.class.getName());

    @ManagedProperty(value = "#{delegateMovimientosSaldo}")
    private MovimientosSaldoBussinesDel delegate;

    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;

    private Boolean mostrarDialog;
    private List<FilaGridAccionesAjusteBean> filasIcep;
    
    private List<FilaGridAccionesAjusteBean> filas;
    
    @PostConstruct
    public void cargarBitacoraGeneral(){
        LOG.debug("INICIO cargarBitacoraGeneral");
        if(mbSession.getEsAdminCentral()){
            filas = delegate.obtenerFilasAcciones();
        }
    }
    
    public void mostrarDialog(){
        LOG.debug("INICIO mostrarDialog");

        mostrarDialog = Boolean.TRUE;
        filasIcep = delegate.obtenerBitacoraAjustesSaldoIcep(mbSession.getIdSaldoIcep());
    }

    public void cerrar(){
        LOG.debug("INICIO cerrar");
        mostrarDialog = Boolean.FALSE;
    }

    public Boolean getMostrarDialog() {
        return mostrarDialog;
    }

    public void setMostrarDialog(Boolean mostrarDialog) {
        this.mostrarDialog = mostrarDialog;
    }

    public List<FilaGridAccionesAjusteBean> getFilas() {
        return filas;
    }

    public void setFilas(List<FilaGridAccionesAjusteBean> filas) {
        this.filas = filas;
    }

    public MovimientosSaldoBussinesDel getDelegate() {
        return delegate;
    }

    public void setDelegate(MovimientosSaldoBussinesDel delegate) {
        this.delegate = delegate;
    }

    public List<FilaGridAccionesAjusteBean> getFilasIcep() {
        return filasIcep;
    }

    public void setFilasIcep(List<FilaGridAccionesAjusteBean> filasIcep) {
        this.filasIcep = filasIcep;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }
}
