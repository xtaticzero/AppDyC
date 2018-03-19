package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.backing;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import javax.servlet.ServletContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.adminprocesos.dto.Cron;
import mx.gob.sat.siat.dyc.adminprocesos.dto.Dias;
import mx.gob.sat.siat.dyc.adminprocesos.dto.Lugares;
import mx.gob.sat.siat.dyc.adminprocesos.dto.Mes;
import mx.gob.sat.siat.dyc.adminprocesos.service.AdministrarProcesosService;
import mx.gob.sat.siat.dyc.adminprocesos.util.constante.Constantes;
import mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.bussiness.CronMaker;
import mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.bussiness.TraductorCron;
import mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.support.DatosPantalla;
import mx.gob.sat.siat.dyc.domain.adminproceso.DyccStatusProcesoDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.Procesos;

import org.apache.log4j.Logger;


@ManagedBean(name = "administrarProcesos")
@ViewScoped
public class AdministrarProcesos {
    
    private boolean banderaDialog;
    
    private List<Double>   camposNumericos;
    private List<String>   dias;
    private List<String>   horas;
    private List<Boolean>  listaBanderas;    
    private List<Dias>     listaDias;
    private List<String>   listaHoras;
    private List<Mes>      listaMeses;
    private List<String>   listaMinutos;
    private List<Lugares>  listaLugares;
    private List<String>   meses;
    private List<String>   minutos;
    private List<String>   posiciones;
    private List<Double>   radios;
    private List<String>   valorDias;
    private List<Procesos> listaProcesos;
    private List<DyccStatusProcesoDTO> listaStatusProceso;
    
    private String   fechaEjecucion;
    private String   statusProceso;
    private Procesos proceso;
    
    private static final int BANDERAAMARILLA = 2;
    private static final int BANDERAROJA  = 3;
    private static final int BANDERAVERDE = 1;
    private static final int EN_EJECUCION = 4;
    private static final int ELEMENTO1 = 0;
    private static final int ELEMENTO2 = 1;
    private static final int ELEMENTO3 = 2;
    private static final int ELEMENTO4 = 3;
    private static final int ELEMENTO5 = 4;
    private static final int ELEMENTO6 = 5;
    private static final int ELEMENTO7 = 6;
    private static final String STATUSPROCESO = "statusProceso";
    private static final String VALUE         = "value";
    
    @ManagedProperty(value = "#{administrarProcesosService}")
    private AdministrarProcesosService administrarProcesosService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private static final Logger LOGGER = Logger.getLogger(AdministrarProcesos.class);
    
    public AdministrarProcesos() {
        banderaDialog = false;
    }
    
    @PostConstruct
    public void init() {
        try {
            Utils.validarSesion(serviceObtenerSesion.execute(), mx.gob.sat.siat.dyc.util.constante.Procesos.DYC00018);
            generarDatos();
            listaProcesos = administrarProcesosService.consultar();
            fechaEjecucion = administrarProcesosService.consultarFechaEjecucion();
            asignarSemaforosyEstados();
        } catch (Exception e) {
            LOGGER.error("Error al cargar la informacion: "+e);
        }
    }
    
    /**
     * Asigna dependiendo de los estatus en los que se encuentran los proceso, las imagenes de los semaforos.
     */
    private void asignarSemaforosyEstados() {
        Iterator it = listaProcesos.iterator();
        Procesos procesoInterno = null;
        TraductorCron objetoTraductor = new TraductorCron();
    
        while (it.hasNext()) {
            procesoInterno = (Procesos)it.next();
            
            if(procesoInterno.getDyctSegProcesoDTO().getEjecucion().equals(Constantes.EJECUCIONCORRECTA)) {
                procesoInterno.setEjecucion("Correcta");
                
            } else if(procesoInterno.getDyctSegProcesoDTO().getEjecucion().equals(Constantes.EJECUCIONERRONEA)) {
                procesoInterno.setEjecucion("Incorrecta");
                
            } else {
                procesoInterno.setEjecucion("N/D");
            }  
            
            if(procesoInterno.getDyctSegProcesoDTO().getDyccStatusProcesoDTO().getIdStatusProceso()==BANDERAVERDE) {
                procesoInterno.setRuta(Constantes.SEMAFOROVERDE);
            
            } else if (procesoInterno.getDyctSegProcesoDTO().getDyccStatusProcesoDTO().getIdStatusProceso()==BANDERAAMARILLA){
                procesoInterno.setRuta(Constantes.SEMAFOROAMARILLO);
                
            } else if (procesoInterno.getDyctSegProcesoDTO().getDyccStatusProcesoDTO().getIdStatusProceso()==BANDERAROJA){
                procesoInterno.setRuta(Constantes.SEMAFOROROJO);
                
            } else if (procesoInterno.getDyctSegProcesoDTO().getDyccStatusProcesoDTO().getIdStatusProceso()==EN_EJECUCION){
                procesoInterno.setRuta(Constantes.EJECUTANDOSE);
                procesoInterno.setEjecucion("Ejecutandose...");
            }
            
            procesoInterno.setDescripcionHorarioEjecucion(objetoTraductor.generarDescripcionEjecucion(procesoInterno.getDyctTareasDTO().getHorarioEjecucion()));
        }
        
    }
    
    /**
     * Genera todos los datos necesarios al cargar la pantalla.
     */
    private void generarDatos() {
        DatosPantalla objetoDatosPantalla = new DatosPantalla();
        
        try {
            camposNumericos = objetoDatosPantalla.listarCamposNumericos();
            dias = objetoDatosPantalla.listarCamposDias();
            horas = objetoDatosPantalla.listarCamposHoras();
            listaBanderas = objetoDatosPantalla.listarBanderas();
            listaDias = objetoDatosPantalla.listarDias();
            listaHoras = objetoDatosPantalla.listarHoras();
            listaLugares = objetoDatosPantalla.listarLugares();
            listaMeses = objetoDatosPantalla.listarMeses();
            listaMinutos = objetoDatosPantalla.listarMinutos();
            listaStatusProceso = administrarProcesosService.listarStatusProceso();
            meses = objetoDatosPantalla.listarCamposMeses();
            minutos = objetoDatosPantalla.listarCamposMinutos();
            posiciones = objetoDatosPantalla.listarCamposPosiciones();
            radios = objetoDatosPantalla.listarCamposRadios();
        } catch (SIATException siate) {
            LOGGER.error(siate);
        }
        
        objetoDatosPantalla = null;
    }
    
    /**
     * Redirecciona a la Misma pantalla para volver a pintar todos los elementos en ella
     */
    public void redireccionar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            ServletContext sc = (ServletContext)facesContext.getExternalContext().getContext();
            facesContext.getExternalContext().redirect(sc.getContextPath()+"/faces/resources/pages/adminprocesos/administrarProcesos.jsf");

        } catch (IOException e) {
            LOGGER.error("Error en redireccionar() de AdministrarProcesos.java: " + e);
        }
    }
    
    /**
     * Genera una expresion cron de acuerdo a los datos ingresados a traves del Dialog.
     *
     * @param e
     */
    public void guardarCalendarizacion(ActionEvent e) {
        CronMaker objetoCronMaker = new CronMaker();
        String idBoton = e.getComponent().getAttributes().get("id").toString();
        Cron cron = crearCron(idBoton);

        try {
            if(idBoton.equals(STATUSPROCESO)) {
                LOGGER.info("Proceso: "+proceso.getDyctSegProcesoDTO().getDyctProcesosDTO().getIdProceso()+"Nombre: "+ proceso.getDyctTareasDTO().getNombreJOB());
                LOGGER.info("Status - proceso: "+statusProceso);
                proceso.getDyctSegProcesoDTO().getDyccStatusProcesoDTO().setIdStatusProceso(Integer.valueOf(statusProceso));
                administrarProcesosService.actualizarStatusProceso(proceso);
            } else {
                proceso.getDyctTareasDTO().setHorarioEjecucion(objetoCronMaker.generarCron(idBoton, cron));
                administrarProcesosService.guardarCron(proceso);
            }
            listaProcesos = administrarProcesosService.consultar();
            redireccionar();
            
        } catch (Exception e1) {
            LOGGER.error("ERROR AL GENERAR LA EXPRESION CRON: "+e1);
        } 
        objetoCronMaker = null;
    }
    
    /**
     * Dependiendo del boton al cual se le haya dado click, ya sea si es el boton que genera la secuencia en minutos,
     * horas, dias, semanas, etc. Asigna los valores de la pantalla adecuados para que se genera la expresion cron.
     *
     * @param idBoton
     * @return
     */
    private Cron crearCron(String idBoton) {
        
        Cron cron = new Cron();
        if(idBoton.equals(Constantes.MINUTO)) {
            cron.setMinuto(camposNumericos.get(ELEMENTO1).intValue());
            
        } else if (idBoton.equals(Constantes.HORA)) {
            cron.setBanderaHora(radios.get(ELEMENTO1).intValue());
            if(radios.get(ELEMENTO1).intValue()==1) {
                cron.setHora(camposNumericos.get(ELEMENTO2).intValue());
            } else {
                cron.setHora(Integer.valueOf(horas.get(ELEMENTO1)));
                cron.setMinuto(Integer.valueOf(minutos.get(ELEMENTO1)));
            }
                
        } else if (idBoton.equals(Constantes.DIA)) {
            cron.setBanderaDia(radios.get(ELEMENTO2).intValue());
            cron.setNumeroDia(camposNumericos.get(ELEMENTO3)!=null?camposNumericos.get(ELEMENTO3).intValue():0);
            cron.setHora(Integer.valueOf(horas.get(ELEMENTO2)));
            cron.setMinuto(Integer.valueOf(minutos.get(ELEMENTO2)));
            
        } else if (idBoton.equals(Constantes.SEMANA)) {
            cron.setDia(valorDias.toString().replace("[", "").replace("]", "").replace(" ", ""));
            cron.setHora(Integer.valueOf(horas.get(ELEMENTO3)));
            cron.setMinuto(Integer.valueOf(minutos.get(ELEMENTO3)));
            
        } else {
            cron = crearCron2 (idBoton, cron);
        }
        banderaDialog = false;
        
        return cron;
    }
    
    /**
     * Continua coin el flujo del metodo anterior
     *
     * @param idBoton
     * @param cron
     * @return
     */
    private Cron crearCron2 (String idBoton, Cron cron) {
        Cron cron2 = new Cron();
        if (idBoton.equals(Constantes.MES)) {
           cron.setBanderaMes(radios.get(ELEMENTO4).intValue());
           cron.setNumeroDia(camposNumericos.get(ELEMENTO4)!=null?camposNumericos.get(ELEMENTO4).intValue():null);
           
           if(radios.get(ELEMENTO4)==1){
               cron.setNumeroMes(camposNumericos.get(ELEMENTO5)!=null?camposNumericos.get(ELEMENTO5).intValue():null);
           } else {
               cron.setNumeroMes(camposNumericos.get(ELEMENTO6)!=null?camposNumericos.get(ELEMENTO6).intValue():null);
           }
           
           cron.setPosicion(Integer.valueOf(posiciones.get(ELEMENTO1)));
           cron.setDia(dias.get(ELEMENTO1));
           cron.setHora(Integer.valueOf(horas.get(ELEMENTO4)));
           cron.setMinuto(Integer.valueOf(minutos.get(ELEMENTO4)));
           
        } else {
            cron2 = crearCron3 (idBoton, cron);   
        }
        return cron2;
    }
    
    /**
     * Continua coin el flujo del metodo anterior
     *
     * @param idBoton
     * @param cron
     * @return
     */
    private Cron crearCron3 (String idBoton, Cron cron) {
        if (idBoton.equals(Constantes.ANIO_SIN_TILDE)) {
           cron.setBanderaAnual(radios.get(ELEMENTO5).intValue());
           cron.setNumeroDia(camposNumericos.get(ELEMENTO7)!=null?camposNumericos.get(ELEMENTO7).intValue():null);
           if(radios.get(ELEMENTO5)==1) {
               cron.setNumeroMes(meses.get(ELEMENTO1)!=null?Integer.valueOf(meses.get(ELEMENTO1)):null);
           } else {
               cron.setNumeroMes(meses.get(ELEMENTO2)!=null?Integer.valueOf(meses.get(ELEMENTO2)):null);
           }
           cron.setPosicion(Integer.valueOf(posiciones.get(ELEMENTO2)));
           cron.setDia(dias.get(ELEMENTO2));
           cron.setHora(Integer.valueOf(horas.get(ELEMENTO5)));
           cron.setMinuto(Integer.valueOf(minutos.get(ELEMENTO5)));
       }
        return cron;
    }
    
    public void seleccionarProceso() {
        banderaDialog = Boolean.TRUE;
    }
    
    public void actualizar1(AjaxBehaviorEvent e) {
        radios.set(ELEMENTO1, Double.valueOf(e.getComponent().getAttributes().get(VALUE).toString()));
        
        if(radios.get(ELEMENTO1)==1) {
            listaBanderas.set(ELEMENTO2,Boolean.TRUE);
        } else {
            listaBanderas.set(ELEMENTO2,Boolean.FALSE);
            camposNumericos.set(ELEMENTO2,null);
        }
    }
    
    public void actualizar2(AjaxBehaviorEvent e) {
        radios.set(ELEMENTO2, Double.valueOf(e.getComponent().getAttributes().get(VALUE).toString()));
        
        if(radios.get(ELEMENTO2)==1) {
            listaBanderas.set(ELEMENTO3, Boolean.TRUE);
        } else {
            listaBanderas.set(ELEMENTO3, Boolean.FALSE);
            camposNumericos.set(ELEMENTO3, null);
        }
    }
    
    public void actualizar3(AjaxBehaviorEvent e) {
        radios.set(ELEMENTO4, Double.valueOf(e.getComponent().getAttributes().get(VALUE).toString()));
        
        if(radios.get(ELEMENTO4)==1) {
            listaBanderas.set(ELEMENTO4, Boolean.TRUE);
            listaBanderas.set(ELEMENTO5, Boolean.TRUE);
            listaBanderas.set(ELEMENTO6, Boolean.FALSE);
            camposNumericos.set(ELEMENTO6,null);
            
        } else {
            listaBanderas.set(ELEMENTO4, Boolean.FALSE);
            listaBanderas.set(ELEMENTO5, Boolean.FALSE);
            listaBanderas.set(ELEMENTO6, Boolean.TRUE);
            camposNumericos.set(ELEMENTO4, null);
            camposNumericos.set(ELEMENTO5, null);
        }
    }
    
    public void actualizar4(AjaxBehaviorEvent e) {
        radios.set(ELEMENTO5, Double.valueOf(e.getComponent().getAttributes().get(VALUE).toString()));

        if(radios.get(ELEMENTO5)==1) {
            listaBanderas.set(ELEMENTO7, Boolean.TRUE);
    
        } else {
            listaBanderas.set(ELEMENTO7, Boolean.FALSE);
            camposNumericos.set(ELEMENTO7,null);
        }
    }
  
    public void setListaDias(List<Dias> listaDias) {
        this.listaDias = listaDias;
    }

    public List<Dias> getListaDias() {
        return listaDias;
    }

    public void setListaHoras(List<String> listaHoras) {
        this.listaHoras = listaHoras;
    }

    public List<String> getListaHoras() {
        return listaHoras;
    }

    public void setListaMeses(List<Mes> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public List<Mes> getListaMeses() {
        return listaMeses;
    }

    public void setListaMinutos(List<String> listaMinutos) {
        this.listaMinutos = listaMinutos;
    }

    public List<String> getListaMinutos() {
        return listaMinutos;
    }

    public void setListaLugares(List<Lugares> listaLugares) {
        this.listaLugares = listaLugares;
    }

    public List<Lugares> getListaLugares() {
        return listaLugares;
    }
    
    public void setListaBanderas(List<Boolean> listaBanderas) {
        this.listaBanderas = listaBanderas;
    }

    public List<Boolean> getListaBanderas() {
        return listaBanderas;
    }

    public void setCamposNumericos(List<Double> camposNumericos) {
        this.camposNumericos = camposNumericos;
    }

    public List<Double> getCamposNumericos() {
        return camposNumericos;
    }

    public void setDias(List<String> dias) {
        this.dias = dias;
    }

    public List<String> getDias() {
        return dias;
    }

    public void setHoras(List<String> horas) {
        this.horas = horas;
    }

    public List<String> getHoras() {
        return horas;
    }

    public void setMeses(List<String> meses) {
        this.meses = meses;
    }

    public List<String> getMeses() {
        return meses;
    }

    public void setMinutos(List<String> minutos) {
        this.minutos = minutos;
    }

    public List<String> getMinutos() {
        return minutos;
    }

    public void setPosiciones(List<String> posiciones) {
        this.posiciones = posiciones;
    }

    public List<String> getPosiciones() {
        return posiciones;
    }

    public void setRadios(List<Double> radios) {
        this.radios = radios;
    }

    public List<Double> getRadios() {
        return radios;
    }

    public void setValorDias(List<String> valorDias) {
        this.valorDias = valorDias;
    }

    public List<String> getValorDias() {
        return valorDias;
    }

    public void setBanderaDialog(boolean banderaDialog) {
        this.banderaDialog = banderaDialog;
}

    public boolean isBanderaDialog() {
        return banderaDialog;
    }

    public void setAdministrarProcesosService(AdministrarProcesosService administrarProcesosService) {
        this.administrarProcesosService = administrarProcesosService;
    }

    public AdministrarProcesosService getAdministrarProcesosService() {
        return administrarProcesosService;
    }

    public void setListaProcesos(List<Procesos> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }

    public List<Procesos> getListaProcesos() {
        return listaProcesos;
    }
    
    public void setProceso(Procesos proceso) {
        this.proceso = proceso;
    }

    public Procesos getProceso() {
        return proceso;
    }

    public void setListaStatusProceso(List<DyccStatusProcesoDTO> listaStatusProceso) {
        this.listaStatusProceso = listaStatusProceso;
    }

    public List<DyccStatusProcesoDTO> getListaStatusProceso() {
        return listaStatusProceso;
    }

    public void setStatusProceso(String statusProceso) {
        this.statusProceso = statusProceso;
    }

    public String getStatusProceso() {
        return statusProceso;
    }

    public void setFechaEjecucion(String fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getFechaEjecucion() {
        return fechaEjecucion;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
