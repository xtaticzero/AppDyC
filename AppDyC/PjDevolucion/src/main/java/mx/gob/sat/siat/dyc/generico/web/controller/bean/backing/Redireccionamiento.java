package mx.gob.sat.siat.dyc.generico.web.controller.bean.backing;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import static mx.gob.sat.siat.dyc.generico.util.Utils.obtenerPropiedadStringDelMenu;
import static mx.gob.sat.siat.dyc.generico.util.Utils.redirecBuzonContribuyente;

import org.apache.log4j.Logger;


@ManagedBean(name = "redireccionamientoMB")
@ViewScoped
public class Redireccionamiento {
    public Redireccionamiento() {
        super();
    }
    
    private String url;
    
    private static final Logger LOG = Logger.getLogger(Redireccionamiento.class.getName());
    
    @PostConstruct
    public void init() 
    {
        String mensajeRequest = obtenerPropiedadStringDelMenu("pantalla");
        
        LOG.error(">> Redireccionamiento. Mensaje recuperad : " + mensajeRequest);
        
        if(mensajeRequest.trim().equals("Seguimiento de trÃ¡mites y requerimientos"))
        {
            mensajeRequest = "Seguimiento de trámites y requerimientos";
        }
        else if(mensajeRequest.trim().equals("Solicitud de devoluciÃ³n"))
        {
            mensajeRequest = "Solicitud de devolución";
        }
        
         LOG.error(">> Redireccionamiento. Mensaje final : " + mensajeRequest);
        url="Para poder realizar el trámite de " + mensajeRequest + " debes ingresar a través del Buzón tributario";
    }
    
    public void redireccionaBuzon(){
            redirecBuzonContribuyente();
        }
    
/**    public void redireccionar(String pagina) {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("idRedireccionar.click();");
        Button boton = new Button();
        boton.findComponent("idRedireccionar");
        ActionEvent evento = new ActionEvent(boton);
        //evento.queue();
        
        Button b = new Button();
        b.getFacet("nombre");
        b.getId();
        
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        try {
            Process process = Runtime.getRuntime().exec("redireccionar()");
        } catch (IOException e) {
        }


    }
*/
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
