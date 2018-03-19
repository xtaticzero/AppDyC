package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.casocomp.service.EmitirResolucionService;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;


@ManagedBean(name = "pEmitirResolucionCCMB")
@ViewScoped
public class PEmitirResolucionMB {
    private Logger log = Logger.getLogger(PEmitirResolucionMB.class.getName());

    @ManagedProperty("#{serviceEmitirResolucion}")
    private EmitirResolucionService service;

    @ManagedProperty("#{serviceAdmCasosComp}")
    private IAdmCasosCompensacionService serviceACC;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;
    
    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;

    @PostConstruct
    public void cargarInfoInicial() {
    }
    
    public String registrarCompensacion (){
        log.debug("INICIO registrarCompensacion");
        mbSession.setSalida(null);
        
        if ( mbSession.getIdNumAprob() != ConstantesDyCNumerico.VALOR_0 ){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("numControl", mbSession.getNumControl());
            params.put("numEmpleadoAprobador", mbSession.getIdNumAprob());
            
            if ( !validacionAgs.aprobadorValido( mbSession.getIdNumAprob() ) /* || true */){
                validacionAgs.muestraMensajeAprobadorNoValido();

                return SatAgsEmpleadosMVService.NO_REDIRECCIONAMIENTO;
            }
            
            mbSession.setMensaje(service.registrarCompensacion(params));
            mbSession.setSalida("bandejaCompensaciones");
        } else {
            FacesContext.getCurrentInstance().addMessage("msgLiquidaA",
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un aprobador",
                                                                          null));
        }
        return mbSession.getSalida();
    }

    public void setService(EmitirResolucionService service) {
        this.service = service;
    }

    public EmitirResolucionService getService() {
        return service;
    }

    public void setServiceACC(IAdmCasosCompensacionService serviceACC) {
        this.serviceACC = serviceACC;
    }

    public IAdmCasosCompensacionService getServiceACC() {
        return serviceACC;
    }

    public void setMbSession(ManagerSesionAdmCasosCompMB mbSession) {
        this.mbSession = mbSession;
    }

    public ManagerSesionAdmCasosCompMB getMbSession() {
        return mbSession;
    }
    
    public void setValidacionAgs( SatAgsEmpleadosMVService validacionAgs ){
        this.validacionAgs = validacionAgs;
    }
    
    public SatAgsEmpleadosMVService getValidacionAgs (){
        return validacionAgs;
    }
}
