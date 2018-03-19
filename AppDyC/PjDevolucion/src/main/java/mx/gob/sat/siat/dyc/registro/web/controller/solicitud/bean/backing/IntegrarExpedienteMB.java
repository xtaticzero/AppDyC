/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.io.FileNotFoundException;
import java.io.Serializable;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import javax.xml.bind.JAXBException;

import mx.gob.sat.mat.dyc.integrarexpediente.service.IcepService;
import mx.gob.sat.siat.dyc.casocomp.service.emitirliquidacion.EmitirLiquidacionService;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.registro.service.expediente.IntegrarExpedienteService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DeterminarFlujoSolicitudDevolucionService;
import mx.gob.sat.siat.dyc.servicio.service.altex.ConsultarContribuyenteAltamenteExportadorService;
import mx.gob.sat.siat.dyc.servicio.service.cpr.CPRService;
import mx.gob.sat.siat.dyc.servicio.service.diot.DiotService;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesAsignarAuDic;

import org.xml.sax.SAXException;


/**
 * Intregracion de expediente
 * @author Israel Chàvez Chàvez
 * @since 29/07/2013
 * @version 0.2  J. Isaac Carbajal Bernal
 * */
@ManagedBean(name = "integrarExpedienteMB")
@ViewScoped
public class IntegrarExpedienteMB implements Serializable {

    @SuppressWarnings("compatibility:-4386761853901324824")
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{integrarExpedienteService}")
    private transient IntegrarExpedienteService integrarExpedienteService;

    @ManagedProperty(value = "#{diotService}")
    private transient DiotService diotService;

    @ManagedProperty(value = "#{cprService}")
    private transient CPRService cprService;

    @ManagedProperty(value = "#{consultarContribuyenteAltamenteExportadorService}")
    private transient ConsultarContribuyenteAltamenteExportadorService altexService;
    
    @ManagedProperty(value = "#{determinarFlujoSolicitudDevolucionService}")
    private transient DeterminarFlujoSolicitudDevolucionService determinarFlujoSolicitudDevolucionService;

    @ManagedProperty(value = "#{icepService}")
    private transient IcepService icepService;
    
    @ManagedProperty(value = "#{emitirliquidacionService}")
    private transient EmitirLiquidacionService emitirLiquidacion;

    public IntegrarExpedienteMB() {
        super();
    }

    @PostConstruct
    public void init() {
    }
    
    public void insertarExpediente() throws FileNotFoundException, ClassNotFoundException, SQLException, JAXBException,
                                            SAXException {

        DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
        compensacion.setNumControl("COM481400000041");
        
        integrarExpedienteService.validaNumControl(compensacion, ConstantesAsignarAuDic.SERVICIO_CASO_DE_COMPENSACION);
       
    }

    public void setIntegrarExpedienteService(IntegrarExpedienteService integrarExpedienteService) {
        this.integrarExpedienteService = integrarExpedienteService;
    }

    public IntegrarExpedienteService getIntegrarExpedienteService() {
        return integrarExpedienteService;
    }

    public void setDiotService(DiotService diotService) {
        this.diotService = diotService;
    }

    public DiotService getDiotService() {
        return diotService;
    }

    public void setCprService(CPRService cprService) {
        this.cprService = cprService;
    }

    public CPRService getCprService() {
        return cprService;
    }

    public void setAltexService(ConsultarContribuyenteAltamenteExportadorService altexService) {
        this.altexService = altexService;
    }

    public ConsultarContribuyenteAltamenteExportadorService getAltexService() {
        return altexService;
    }

    public void setIcepService(IcepService icepService) {
        this.icepService = icepService;
    }

    public IcepService getIcepService() {
        return icepService;
    }

    public void setDeterminarFlujoSolicitudDevolucionService(DeterminarFlujoSolicitudDevolucionService determinarFlujoSolicitudDevolucionService) {
        this.determinarFlujoSolicitudDevolucionService = determinarFlujoSolicitudDevolucionService;
    }

    public DeterminarFlujoSolicitudDevolucionService getDeterminarFlujoSolicitudDevolucionService() {
        return determinarFlujoSolicitudDevolucionService;
    }

    public void setEmitirLiquidacion(EmitirLiquidacionService emitirLiquidacion) {
        this.emitirLiquidacion = emitirLiquidacion;
    }

    public EmitirLiquidacionService getEmitirLiquidacion() {
        return emitirLiquidacion;
    }
}
