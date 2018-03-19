/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.web.controller.bean.backing;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.consulta.devautisr.service.CalculosConsultaDevAutISRService;
import org.apache.log4j.Logger;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author root
 */
@ManagedBean(name = "consultaDevISRDetalleNuevoMB")
@ViewScoped
public class DetalleConsultaDevautisrNuevoMB {
    
    @ManagedProperty(value = "#{consultaDevautisrMB}")
    private ConsultaDevautisrMB consultaDevautisrMB;
    
    @ManagedProperty(value = "#{consultaDevISRDetalleMB}")
    private DetalleConsultaDevautisrMB consultaDevISRDetalleMB;
    
    @ManagedProperty(value = "#{calculosConsultaDevAutISRService}")
    private CalculosConsultaDevAutISRService calculosConsultaDevAutISRService;
    
    private boolean impuestoSobreLaRenta;
    private boolean inconsDeduccion;
    private boolean inconsIngreso;
    private boolean inconsRechazo;
    private String contenidoDetalle;
    private String contenidoDetalleRechazo;
    private String contenidoDetalleDeduccion;
    private String contenidoDetalleIngreso;

    private TramiteDevAutISRBO tramiteDevAutISRBO;
    
    private Logger log = Logger.getLogger(DetalleConsultaDevautisrNuevoMB.class);
    
    @PostConstruct
    public void init() {
        impuestoSobreLaRenta = Boolean.TRUE;
        inconsDeduccion = Boolean.FALSE;
        inconsIngreso = Boolean.FALSE;
        inconsRechazo = Boolean.FALSE;
        contenidoDetalle = consultaDevautisrMB.getContenidoDetalle();
        contenidoDetalleRechazo = consultaDevautisrMB.getContenidoDetalleRechazo();
        contenidoDetalleDeduccion = consultaDevautisrMB.getContenidoDetalleDeduccion();
        contenidoDetalleIngreso = consultaDevautisrMB.getContenidoDetalleIngreso();
        tramiteDevAutISRBO = calculosConsultaDevAutISRService.determinarDiferenciasDeclaraciones(consultaDevautisrMB.getSelectedDatosTramiteISRDetalleVOs());
    }
    
    public void onTabChange(TabChangeEvent event) {
        impuestoSobreLaRenta = Boolean.FALSE;
        inconsDeduccion = Boolean.FALSE ;
        inconsIngreso = Boolean.FALSE;
        inconsRechazo = Boolean.FALSE; 
        
        
        String tabSelect = event.getTab().getId();
        
        if(tabSelect.equals("tabImpuestoSobreRenta")){
            impuestoSobreLaRenta = Boolean.TRUE;
        }
        if(tabSelect.equals("tabInconsDeducciones")){
            inconsDeduccion = Boolean.TRUE ;
        }
        if(tabSelect.equals("tabInconsIngresos")){
            inconsIngreso = Boolean.TRUE;
        }
        if(tabSelect.equals("tabInconsRechazos")){
            inconsRechazo = Boolean.TRUE; 
        }        

    }
    
    public void verPantallaAdjuntarDocumentos() 
    {
        
        consultaDevISRDetalleMB.setMortrarAcciones(Boolean.FALSE);
        consultaDevISRDetalleMB.retroPantallaCuentaBancaria();
        consultaDevautisrMB.setInstanciado(Boolean.TRUE);
        redireccionar();
    
    }
    
    public void redireccionar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try { 
            ServletContext sc = (ServletContext)facesContext.getExternalContext().getContext();
            facesContext.getExternalContext().redirect(sc.getContextPath() + "/faces/resources/pages/registro/solicitudesDevolucion.jsf");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    
    public String goToConsultaDevAutISR(){
       
        return "goConsultaDevolucionesContribuyente";
    }

    public boolean isImpuestoSobreLaRenta() {
        return impuestoSobreLaRenta;
    }

    public void setImpuestoSobreLaRenta(boolean impuestoSobreLaRenta) {
        this.impuestoSobreLaRenta = impuestoSobreLaRenta;
    }

    public boolean isInconsDeduccion() {
        return inconsDeduccion;
    }

    public void setInconsDeduccion(boolean inconsDeduccion) {
        this.inconsDeduccion = inconsDeduccion;
    }

    public boolean isInconsIngreso() {
        return inconsIngreso;
    }

    public void setInconsIngreso(boolean inconsIngreso) {
        this.inconsIngreso = inconsIngreso;
    }

    public boolean isInconsRechazo() {
        return inconsRechazo;
    }

    public void setInconsRechazo(boolean inconsRechazo) {
        this.inconsRechazo = inconsRechazo;
    }

    public ConsultaDevautisrMB getConsultaDevautisrMB() {
        return consultaDevautisrMB;
    }

    public void setConsultaDevautisrMB(ConsultaDevautisrMB consultaDevautisrMB) {
        this.consultaDevautisrMB = consultaDevautisrMB;
    }

    public String getContenidoDetalle() {
        return contenidoDetalle;
    }

    public void setContenidoDetalle(String contenidoDetalle) {
        this.contenidoDetalle = contenidoDetalle;
    }

    public CalculosConsultaDevAutISRService getCalculosConsultaDevAutISRService() {
        return calculosConsultaDevAutISRService;
    }

    public void setCalculosConsultaDevAutISRService(CalculosConsultaDevAutISRService calculosConsultaDevAutISRService) {
        this.calculosConsultaDevAutISRService = calculosConsultaDevAutISRService;
    }

    public TramiteDevAutISRBO getTramiteDevAutISRBO() {
        return tramiteDevAutISRBO;
    }

    public void setTramiteDevAutISRBO(TramiteDevAutISRBO tramiteDevAutISRBO) {
        this.tramiteDevAutISRBO = tramiteDevAutISRBO;
    }

    public String getContenidoDetalleRechazo() {
        return contenidoDetalleRechazo;
    }

    public void setContenidoDetalleRechazo(String contenidoDetalleRechazo) {
        this.contenidoDetalleRechazo = contenidoDetalleRechazo;
    }

    public String getContenidoDetalleDeduccion() {
        return contenidoDetalleDeduccion;
    }

    public void setContenidoDetalleDeduccion(String contenidoDetalleDeduccion) {
        this.contenidoDetalleDeduccion = contenidoDetalleDeduccion;
    }

    public String getContenidoDetalleIngreso() {
        return contenidoDetalleIngreso;
    }

    public void setContenidoDetalleIngreso(String contenidoDetalleIngreso) {
        this.contenidoDetalleIngreso = contenidoDetalleIngreso;
    }

    public DetalleConsultaDevautisrMB getConsultaDevISRDetalleMB() {
        return consultaDevISRDetalleMB;
    }

    public void setConsultaDevISRDetalleMB(DetalleConsultaDevautisrMB consultaDevISRDetalleMB) {
        this.consultaDevISRDetalleMB = consultaDevISRDetalleMB;
    }
    
    

}
