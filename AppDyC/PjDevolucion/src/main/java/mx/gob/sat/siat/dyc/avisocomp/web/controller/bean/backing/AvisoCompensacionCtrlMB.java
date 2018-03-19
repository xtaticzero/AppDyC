package mx.gob.sat.siat.dyc.avisocomp.web.controller.bean.backing;

import java.io.IOException;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.avisocomp.datamodel.PendienteDataModel;
import mx.gob.sat.siat.dyc.avisocomp.service.AvisoCompensacionService;
import static mx.gob.sat.siat.dyc.generico.util.Utils.muestraMensaje;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistraSolDevService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


@ManagedBean(name = "avisoCompensacionCtrlMB")
@ViewScoped
public class AvisoCompensacionCtrlMB {

    private Logger log = Logger.getLogger(AvisoCompensacionCtrlMB.class);

    @ManagedProperty(value = "#{avisoCompensacionService}")
    private AvisoCompensacionService avisoCompensacionService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{registraSolDevService}")
    private RegistraSolDevService registraSolDevService;
    
    
    private boolean muestraPaginadorAvisos;
    private PendienteDataModel pendienteDataModel;
    private int totalAvisosPendientes;


   @PostConstruct
    public void init() {
        totalAvisosPendientes = ConstantesDyCNumerico.VALOR_6;
        AccesoUsr au = serviceObtenerSesion.execute();
         if (registraSolDevService.desdeTramitesYNoEstaAmparado(au.getUsuario())) {
            muestraMensaje("Aviso de Compensaci\u00f3n");
        } else {
            redirecContribuyente();
        }
    }

    public void redirecContribuyente() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../avisocomp/datosContribuyente.jsf");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void setMuestraPaginadorAvisos(boolean muestraPaginadorAvisos) {
        this.muestraPaginadorAvisos = muestraPaginadorAvisos;
    }

    public boolean isMuestraPaginadorAvisos() {
        return muestraPaginadorAvisos;
    }

    public void setPendienteDataModel(PendienteDataModel pendienteDataModel) {
        this.pendienteDataModel = pendienteDataModel;
    }

    public PendienteDataModel getPendienteDataModel() {
        return pendienteDataModel;
    }

    public void setTotalAvisosPendientes(int totalAvisosPendientes) {
        this.totalAvisosPendientes = totalAvisosPendientes;
    }

    public int getTotalAvisosPendientes() {
        return totalAvisosPendientes;
    }

    public void setAvisoCompensacionService(AvisoCompensacionService avisoCompensacionService) {
        this.avisoCompensacionService = avisoCompensacionService;
    }

    public AvisoCompensacionService getAvisoCompensacionService() {
        return avisoCompensacionService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public RegistraSolDevService getRegistraSolDevService() {
        return registraSolDevService;
    }

    public void setRegistraSolDevService(RegistraSolDevService registraSolDevService) {
        this.registraSolDevService = registraSolDevService;
    }
    
    
}
