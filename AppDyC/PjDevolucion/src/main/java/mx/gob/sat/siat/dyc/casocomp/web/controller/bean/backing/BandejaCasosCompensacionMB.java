/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.siat.dyc.casocomp.service.BandejaCompensacionesService;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.model.CasoCompensacionDataModel;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.ContribuyenteBean;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridCasosCompBean;
import mx.gob.sat.siat.dyc.generico.util.common.FrmTemplateVO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPaginador;

import org.apache.log4j.Logger;


/**
 * Clase ManagedBean para la manejo de vista Bandeja de avisos y casos de compensacion .
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date aGOSTO 18, 2014
 * */
@ManagedBean(name = "bandCCMB")
@ViewScoped
public class BandejaCasosCompensacionMB {
    private static final Logger LOG = Logger.getLogger(BandejaCasosCompensacionMB.class);

    @ManagedProperty("#{serviceBandejaCompensaciones}")
    private BandejaCompensacionesService service;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;

    private CasoCompensacionDataModel casosCompensacion;
    private FilaGridCasosCompBean casoCompSeleccionado;
    private FrmTemplateVO frm;
    
    private int parametroRegresar;

    public BandejaCasosCompensacionMB() {
    }

    @PostConstruct
    public void cargarDefault() {
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());

        Map<String, Object> params = new HashMap<String, Object>();
        frm = new FrmTemplateVO();
        if (request.getRequestURL().indexOf("compensacionRegistrada") != -ConstantesDyCNumerico.VALOR_1 || mbSession.getParametroRegreso() == ConstantesDyCNumerico.VALOR_2) {
            frm.setTituloPagina("Compensación registrada");
            mbSession.setNRule("compensacionRegistrada");
            parametroRegresar = ConstantesDyCNumerico.VALOR_9;
        } else {
            frm.setTituloPagina("Bandeja casos de compensación");
            mbSession.setNRule("bandejaCompensaciones");
            parametroRegresar = ConstantesDyCNumerico.VALOR_8;
        }
        
        params.put("url", request.getRequestURL().indexOf("compensacionRegistrada") != -1 || mbSession.getParametroRegreso() == ConstantesDyCNumerico.VALOR_2 ? "R" : "B");
        params.put("rol", mbSession.getRol());
        params.put("numEmpleado", mbSession.getNumEmpleado());
        params.put("claveAdm", mbSession.getClaveAdm());
        params.put("claveAdm", mbSession.getClaveAdm());
        LOG.info("BandejaCasosCompensacionMB : NumEmpleado -" + mbSession.getNumEmpleado() + ", ClaveAdm - " +
                 mbSession.getClaveAdm());
        this.setCasosCompensacion(new CasoCompensacionDataModel(service.obtenerCompensaciones(params)));
        
        frm.setNumResultados(getCasosCompensacion().getRowCount());
        frm.setRowsPaginador(ConstantesPaginador.NO_COLS_PAGINA);
        frm.setPaginador((ConstantesPaginador.NO_COLS_PAGINA < getCasosCompensacion().getRowCount() ? Boolean.TRUE : Boolean.FALSE));

        if (null != mbSession.getMensaje()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mbSession.getMensaje(), null));
            mbSession.setMensaje(null);
        }

        mbSession.setParametroRegreso(ConstantesDyCNumerico.VALOR_0);

    }

    public String dictaminar() {
        mbSession.setSalida(null);
        if (null != casoCompSeleccionado) {
            mbSession.setNumControl(casoCompSeleccionado.getNumeroControl());
            ContribuyenteBean contribuyente = service.obtenerContribuyente(mbSession.getNumControl());
            mbSession.setEsGranContribuyente(contribuyente.getEsGranContribuyente());
            mbSession.setRfcContribuyente(casoCompSeleccionado.getRfc());
            
            mbSession.setDictaminador(casoCompSeleccionado.getDictaminador());
            mbSession.setNumDictaminador(casoCompSeleccionado.getNumDictaminador());
            mbSession.setNivelAprobador(casoCompSeleccionado.getNivel());
            
            mbSession.setParametroRegresar(parametroRegresar);
            
            mbSession.setSalida("dictaminar");
        } else {
            FacesContext.getCurrentInstance().addMessage("growl",
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe seleccionar un registro",
                                                                          null));
        }
        return mbSession.getSalida();
    }

    /**
     * se cometa por defecto MAT 12-dic-14 */
    public void mostrarMensaje(ComponentSystemEvent event) {
        if (null != mbSession.getMensaje()) {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, mbSession.getMensaje(),
                                                                          ""));
        }
    }

    public FilaGridCasosCompBean getCasoCompSeleccionado() {
        return casoCompSeleccionado;
    }

    public void setCasoCompSeleccionado(FilaGridCasosCompBean casoCompSeleccionado) {
        this.casoCompSeleccionado = casoCompSeleccionado;
    }

    public CasoCompensacionDataModel getCasosCompensacion() {
        return casosCompensacion;
    }

    public void setCasosCompensacion(CasoCompensacionDataModel casosCompensacion) {
        this.casosCompensacion = casosCompensacion;
    }

    public ManagerSesionAdmCasosCompMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionAdmCasosCompMB mbSession) {
        this.mbSession = mbSession;
    }

    public BandejaCompensacionesService getService() {
        return service;
    }

    public void setService(BandejaCompensacionesService service) {
        this.service = service;
    }

    public void setFrm(FrmTemplateVO frm) {
        this.frm = frm;
    }

    public FrmTemplateVO getFrm() {
        return frm;
    }

    public void setParametroRegresar(int parametroRegresar) {
        this.parametroRegresar = parametroRegresar;
    }

    public int getParametroRegresar() {
        return parametroRegresar;
    }
}
