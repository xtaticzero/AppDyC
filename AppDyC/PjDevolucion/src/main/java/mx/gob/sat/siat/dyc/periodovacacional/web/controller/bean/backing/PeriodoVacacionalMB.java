/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.periodovacacional.web.controller.bean.backing;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.sat.mat.dyc.periodovac.PeriodoVacService;
import mx.gob.sat.mat.dyc.periodovac.ReinicioSecuenciaParamService;
import mx.gob.sat.siat.dyc.dao.periodovacacional.impl.DycpPeriodoVacDAOImpl;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DycpPeriodoVacDTO;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DyctReinicioSecParamDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import org.apache.log4j.Logger;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Ing. I. Ismael Castillo Justo
 */
@ManagedBean(name = "periodoVacacionalMB")
@ViewScoped
public class PeriodoVacacionalMB {

    private List<DycpPeriodoVacDTO> listaPeriodosVacacionales;

    private static final Logger LOG = Logger.getLogger(DycpPeriodoVacDAOImpl.class.getName());

    private boolean deshabilitarCmdModifElim = Boolean.TRUE;

    private boolean btnConsultarPV = Boolean.FALSE;
    private boolean btnModificarPV = Boolean.FALSE;
    private boolean btnHabilitarPV = Boolean.TRUE;
    private String horas;
    private Map<String, String> horasM = new LinkedHashMap<String, String>();
    private Map<String, String> minutosM = new LinkedHashMap<String, String>();

    private DycpPeriodoVacDTO objetoPeriodoVacacional;
    private DycpPeriodoVacDTO objetoPeriodoVacacional2;

    private String fechaInicio;
    private String horaInicio;
    private String minutoInicio;
    private String fechaFin;
    private String horaFin;
    private String minutoFin;
    private Date fechaIniMsj;
    private Date fechaFinMsj;
    private Date fechaActIniMsj;
    private Date fechaActFinMsj;

    private DycpPeriodoVacDTO habilitarPeriodoVacacionalObjeto;
    private DycpPeriodoVacDTO modificarPeriodoVacacionalObjeto;

    @ManagedProperty("#{periodoVacService}")
    private PeriodoVacService periodoVacService;

    @ManagedProperty("#{reinicioSecuenciaParamService}")
    private ReinicioSecuenciaParamService reinicioSecuenciaParamService;

    private Date fechaReinicioSecuencia = null;
    private String horaReinicioSecuencia = "";
    private String minutoReinicioSecuencia = "";
    private String fechaDeReinicioProgramada = "";
    private boolean existeFechaReinicio = false;
    private static final String NOMBRETABREINICIO = "tabReinicioSecuencia";

    public static final String OBJETO_PERIODO = "objetoPeriodo";
    
    public static final String FORMA = "frmPeriodoVac:inputid";

    public static final String LISTAPERIODO = "listPeriodos.jsf";
    
    public static final String HABILITAR_P = "habilitarPeriodo.jsf";
    
    public static final String FECHA_FORMATO = "dd/MM/yyyy";
    
    @PostConstruct
    public void init() {
        listaPeriodosVacacionales = new ArrayList<DycpPeriodoVacDTO>();
        habilitarPeriodoVacacionalObjeto = new DycpPeriodoVacDTO();
        habilitarPeriodoVacacionalObjeto.setEstado(Boolean.TRUE);
        modificarPeriodoVacacionalObjeto = new DycpPeriodoVacDTO();

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
        if (session.getAttribute(OBJETO_PERIODO) != null) {
            setObjetoPeriodoVacacional((DycpPeriodoVacDTO) (session.getAttribute(OBJETO_PERIODO)));
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH");
            SimpleDateFormat formatoMinutos = new SimpleDateFormat("mm");
            setHoraInicio(formatoHora.format(getObjetoPeriodoVacacional().getInicioHoraInhabServ()));
            setMinutoInicio(formatoMinutos.format(getObjetoPeriodoVacacional().getInicioHoraInhabServ()));
            setHoraFin(formatoHora.format(getObjetoPeriodoVacacional().getFinHoraInhabServ()));
            setMinutoFin(formatoMinutos.format(getObjetoPeriodoVacacional().getFinHoraInhabServ()));
        } else {
            objetoPeriodoVacacional = new DycpPeriodoVacDTO();
        }
        try {
            listaPeriodosVacacionales = periodoVacService.obtenerPeriodos();
            opcionesHorasMinutosCombo();
        } catch (SIATException ex) {
            java.util.logging.Logger.getLogger(PeriodoVacacionalMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            java.util.logging.Logger.getLogger(PeriodoVacacionalMB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean isBtnConsultarPV() {
        return btnConsultarPV;
    }

    public void setBtnConsultarPV(boolean btnConsultarPV) {
        this.btnConsultarPV = btnConsultarPV;
    }

    public boolean isBtnModificarPV() {
        return btnModificarPV;
    }

    public void setBtnModificarPV(boolean btnModificarPV) {
        this.btnModificarPV = btnModificarPV;
    }

    /*Seleccion de fila */
    public void onRowSelected(SelectEvent event) {
        DycpPeriodoVacDTO dycpPeriodoVacDTO = (DycpPeriodoVacDTO) event.getObject();
        deshabilitarCmdModifElim = false;
        
        dycpPeriodoVacDTO.toString();

    }

    public void onTabChange(TabChangeEvent event) {
        String tabSeleccionada = event.getTab().getId();

        if (tabSeleccionada.equals(NOMBRETABREINICIO)) {

            try {
                DyctReinicioSecParamDTO dTO = reinicioSecuenciaParamService.buscarFechaReinicioSecuenciaActiva();
                LOG.info("tabReinicioSecuencia reinicio activo: " + dTO);
                if (dTO != null) {
                    existeFechaReinicio = true;
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date fecha = dTO.getFechaProgramacion();

                    fechaDeReinicioProgramada = df.format(fecha).concat(" Hrs.");
                }

            } catch (SIATException ex) {
                LOG.info("SIATException: onTabChange() " + ex);
            }
        }
    }

    public void guardarReinicioSecuencia() {

        try {
            LOG.info("guardarReinicioSecuencia");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().getFlash().setKeepMessages(true);

            if (getFechaReinicioSecuencia() != null && !getHoraReinicioSecuencia().equals("")
                    && !getMinutoReinicioSecuencia().equals("")) {
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date dateFromString = dateFromString(getFechaReinicioSecuencia(),
                        getHoraReinicioSecuencia(), getMinutoReinicioSecuencia(), formatoFecha);
                DyctReinicioSecParamDTO nuevo = new DyctReinicioSecParamDTO();
                nuevo.setFechaFin(null);
                nuevo.setFechaProgramacion(dateFromString);
                DyctReinicioSecParamDTO dTO = reinicioSecuenciaParamService.buscarFechaReinicioSecuenciaActiva();

                if (dTO == null) {
                    LOG.info("No existe una fecha de reinicio.");
                    DyctReinicioSecParamDTO nuevoReinicio = reinicioSecuenciaParamService.insertarNuevaFechaReinicioSecuencia(nuevo);
                    if (nuevoReinicio != null) {
                        LOG.info("Se ha guardado fecha de reinicio con éxito.");
                        existeFechaReinicio = true;

                        fechaDeReinicioProgramada = formatoFecha.format(nuevoReinicio.getFechaProgramacion()).concat(" Hrs.");
                        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha guardado fecha de reinicio con éxito.", "");
                        facesContext.addMessage(null, facesMessage);
                    }
                } else {
                    LOG.info("Ya existe una fecha de reinicio.");
                    FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya existe una fecha de reinicio.", "");
                    facesContext.addMessage(null, facesMessage);
                }
            } else {
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Todos los campos son obligatorios.*", "");
                facesContext.addMessage(null, facesMessage);
            }
        } catch (Exception e) {
            LOG.info("Exception: guardarReinicioSecuencia()");
        }
    }

    public Date dateFromString(Date fecha, String hora, String minuto, SimpleDateFormat formatoFecha) {
        Date date = null;
        LOG.info(" fecha: " + fecha);
        LOG.info("  hora: " + hora);
        LOG.info("minuto: " + minuto);
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
        String fechaFormateada = dateString + " " + hora + ":" + minuto + ":" + "00";
        LOG.info("fechaFormateada --> " + fechaFormateada);

        try {
            date = formatoFecha.parse(fechaFormateada);
        } catch (ParseException ex) {
            LOG.info("ParseException: al convertir String  a Date(): " + fechaFormateada);
        }

        return date;
    }

    public void cancelarReinicioSecuencia() throws IOException {

        FacesContext cancelar = FacesContext.getCurrentInstance();
        cancelar.getExternalContext().redirect(LISTAPERIODO);
    }

    public void changeValueListener() {

    }

    public void handleDateSelect(SelectEvent event) {

    }

    public void onRowSelectGestion() {

    }

    public boolean isBtnHabilitarPV() {

        return btnHabilitarPV;
    }

    public void setBtnHabilitarPV(boolean btnHabilitarPV) {
        this.btnHabilitarPV = btnHabilitarPV;
    }

    /*Habilitar nuevo registro de periodo vacacional */
    public DycpPeriodoVacDTO getHabilitarPeriodoVacacionalObjeto() {
        return habilitarPeriodoVacacionalObjeto;
    }

    public void setHabilitarPeriodoVacacionalObjeto(DycpPeriodoVacDTO habilitarPeriodoVacacionalObjeto) {
        this.habilitarPeriodoVacacionalObjeto = habilitarPeriodoVacacionalObjeto;
    }

    public DycpPeriodoVacDTO getModificarPeriodoVacacionalObjeto() {
        return modificarPeriodoVacacionalObjeto;
    }

    public void setModificarPeriodoVacacionalObjeto(DycpPeriodoVacDTO modificarPeriodoVacacionalObjeto) {
        this.modificarPeriodoVacacionalObjeto = modificarPeriodoVacacionalObjeto;
    }

    public List<DycpPeriodoVacDTO> getListaPeriodosVacacionales() {
        return listaPeriodosVacacionales;
    }

    public void setListaPeriodosVacacionales(List<DycpPeriodoVacDTO> listaPeriodosVacacionales) {
        this.listaPeriodosVacacionales = listaPeriodosVacacionales;
    }

    public PeriodoVacService getPeriodoVacService() {
        return periodoVacService;
    }

    public void setPeriodoVacService(PeriodoVacService periodoVacService) {
        this.periodoVacService = periodoVacService;
    }

    /*Habilitar y deshabilita los botones de modificar y consultar  periodo  vacacional*/
    public void setDeshabilitarCmdModifElim(boolean deshabilitarCmdModifElim) {
        this.deshabilitarCmdModifElim = deshabilitarCmdModifElim;
    }

    public boolean isDeshabilitarCmdModifElim() {
        return deshabilitarCmdModifElim;
    }

    public DycpPeriodoVacDTO getObjetoPeriodoVacacional() {
        return objetoPeriodoVacacional;
    }

    public void setObjetoPeriodoVacacional(DycpPeriodoVacDTO objetoPeriodoVacacional) {
        this.objetoPeriodoVacacional = objetoPeriodoVacacional;
    }

    public void consultarPeriodoVac() {
        try {
            HttpSession session;
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
            session.setAttribute(OBJETO_PERIODO, getObjetoPeriodoVacacional());
            FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.getExternalContext().redirect("consultarPeriodo.jsf");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(PeriodoVacacionalMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modificarPeriodoVac() {
        try {
            HttpSession session;
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
            session.setAttribute(OBJETO_PERIODO, getObjetoPeriodoVacacional());
            FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.getExternalContext().redirect("modificarPeriodo.jsf");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(PeriodoVacacionalMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DycpPeriodoVacDTO getObjetoPeriodoVacacional2() {
        return objetoPeriodoVacacional2;
    }

    public void setObjetoPeriodoVacacional2(DycpPeriodoVacDTO objetoPeriodoVacacional2) {
        this.objetoPeriodoVacacional2 = objetoPeriodoVacacional2;
    }

    public void btnPVRegresar() throws IOException {
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.getExternalContext().redirect(LISTAPERIODO);
    }

    public void btnPVCancelar() throws IOException {
       
        FacesContext cancelar = FacesContext.getCurrentInstance();
        cancelar.getExternalContext().redirect(LISTAPERIODO);
    }

    public void habilitarPeriodoVac() throws IOException {
        FacesContext habilitar = FacesContext.getCurrentInstance();
        habilitar.getExternalContext().redirect(HABILITAR_P);
    }

    public void opcionesHorasMinutosCombo() throws IOException {
        for (int h = 0; h <= ConstantesDyCNumerico.VALOR_23; h++) {
            if (h <= ConstantesDyCNumerico.VALOR_9) {
                horasM.put("0" + h, "0" + h);
            } else {
                horasM.put("" + h, "" + h);
            }
        }
        for (int minutos = 0; minutos <= ConstantesDyCNumerico.VALOR_59; minutos++) {
            if (minutos <= ConstantesDyCNumerico.VALOR_9) {
                minutosM.put("0" + minutos, "0" + minutos);
            } else {
                minutosM.put("" + minutos, "" + minutos);
            }
        }
    }

    public Map<String, String> getHorasM() {
        return horasM;
    }

    public void setHorasM(Map<String, String> horasM) {
        this.horasM = horasM;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public Date getFechaActIniMsj() {
        return new Date(fechaActIniMsj.getTime());
    }

    public void setFechaActIniMsj(Date fechaActIniMsj) {
        this.fechaActIniMsj = new Date(fechaActIniMsj.getTime());
    }

    public Date getFechaActFinMsj() {
        return new Date(fechaActFinMsj.getTime());
    }

    public void setFechaActFinMsj(Date fechaActFinMsj) {
        this.fechaActFinMsj = new Date(fechaActFinMsj.getTime());
    }

    public void habilitarNuevoPeriodoVac() {
        try {
            //Pasos necesarios para Inicio-Fin
             DycpPeriodoVacDTO fechas = null;
            String fechaFormateadaInicio;
            String fechaFormateadaFin;
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            int fechaA;
            int fechaB;
            
            setFechaInicio(new SimpleDateFormat(FECHA_FORMATO).format(getFechaIniMsj()));
            setFechaFin(new SimpleDateFormat(FECHA_FORMATO).format(getFechaFinMsj()));
           
            fechaFormateadaInicio = getFechaInicio() + " " + getHoraInicio() + ":" + getMinutoInicio() + ":" + "00";
            getHabilitarPeriodoVacacionalObjeto().setInicioHoraInhabServ(formatoFecha.parse(fechaFormateadaInicio));

            fechaFormateadaFin = getFechaFin() + " " + getHoraFin() + ":" + getMinutoFin() + ":" + "00";
            getHabilitarPeriodoVacacionalObjeto().setFinHoraInhabServ(formatoFecha.parse(fechaFormateadaFin));

            fechas = getHabilitarPeriodoVacacionalObjeto();
            fechaA = fechas.getInicioPeriodo().compareTo(fechas.getFinPeriodo());   
            fechaB = fechas.getInicioHoraInhabServ().compareTo(fechas.getFinHoraInhabServ());
       
             if(fechaA>0)
             {
                 FacesContext facesContext = FacesContext.getCurrentInstance();
                 facesContext.getExternalContext().getFlash().setKeepMessages(true);
                 FacesMessage facesMessage = new FacesMessage("La fecha de fin del periodo vacacional debe ser posterior o igual a la fecha de inicio del periodo vacacional, favor de revisar e ingresar nuevamente los datos correctos.");
                 facesContext.addMessage(FORMA, facesMessage);
            
                 return;
             }
             if(fechaB>0)
             {
                 FacesContext facesContext = FacesContext.getCurrentInstance();
                 facesContext.getExternalContext().getFlash().setKeepMessages(true);
                 FacesMessage facesMessage = new FacesMessage("La fecha y hora de inhabilitación del servicio debe ser posterior a la fecha y hora de inicio de la inhabilitación, favor de revisar e ingresar nuevamente los datos correctos.");
                 facesContext.addMessage(FORMA, facesMessage);
                 
                 return;
             }
            
            LOG.error("Inicio: " + getHabilitarPeriodoVacacionalObjeto().getInicioHoraInhabServ());
            LOG.error("Fin: " + getHabilitarPeriodoVacacionalObjeto().getFinHoraInhabServ());
            
           
            periodoVacService.nuevoPeriodoVac(getHabilitarPeriodoVacacionalObjeto());
                        
            FacesContext habilitar = FacesContext.getCurrentInstance();
            habilitar.getExternalContext().getFlash().setKeepMessages(true);
            habilitar.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Se ha habilitado el periodo vacacional con éxito."));
            
            
            habilitar.getExternalContext().redirect(LISTAPERIODO);
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().getFlash().setKeepMessages(true);
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "","Se ha modificado el periodo vacacional con éxito");
            facesContext.addMessage(FORMA, facesMessage);
            
            
        } catch (SIATException ex) {
            LOG.error(ex);
        } catch (ParseException e) {
            LOG.error("Info: " + e);
        } catch (IOException ex) {
            LOG.error("Info: " + ex);
        }
    }

    public void actualizarPeriodoVac() {
            DycpPeriodoVacDTO fechas = null;
            int fechaA;
            int fechaB;
            
        try {
           

            getModificarPeriodoVacacionalObjeto().setIdPeriodo(getObjetoPeriodoVacacional().getIdPeriodo());
            getModificarPeriodoVacacionalObjeto().setInicioPeriodo(getObjetoPeriodoVacacional().getInicioPeriodo());
            getModificarPeriodoVacacionalObjeto().setFinPeriodo(getObjetoPeriodoVacacional().getFinPeriodo());
            getModificarPeriodoVacacionalObjeto().setEstado(getObjetoPeriodoVacacional().isEstado());
            getModificarPeriodoVacacionalObjeto().setMensaje(getObjetoPeriodoVacacional().getMensaje());
            getModificarPeriodoVacacionalObjeto().setInicioHoraInhabServ(getObjetoPeriodoVacacional().getInicioHoraInhabServ());
            getModificarPeriodoVacacionalObjeto().setFinHoraInhabServ(getObjetoPeriodoVacacional().getFinHoraInhabServ());

            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            setFechaInicio(new SimpleDateFormat(FECHA_FORMATO).format(getModificarPeriodoVacacionalObjeto().getInicioHoraInhabServ()));
            setFechaFin(new SimpleDateFormat(FECHA_FORMATO).format(getModificarPeriodoVacacionalObjeto().getFinHoraInhabServ()));

            String fechaFormateadaInicio = getFechaInicio() + " " + getHoraInicio() + ":" + getMinutoInicio() + ":" + "00";
            getModificarPeriodoVacacionalObjeto().setInicioHoraInhabServ(formatoFecha.parse(fechaFormateadaInicio));
            LOG.info("" + fechaFormateadaInicio);

            String fechaFormateadaFin = getFechaFin() + " " + getHoraFin() + ":" + getMinutoFin() + ":" + "00";
            getModificarPeriodoVacacionalObjeto().setFinHoraInhabServ(formatoFecha.parse(fechaFormateadaFin));
            LOG.info("" + fechaFormateadaFin);
                      
            
            fechas = getModificarPeriodoVacacionalObjeto();
            fechaA = fechas.getInicioPeriodo().compareTo(fechas.getFinPeriodo());   
            fechaB = fechas.getInicioHoraInhabServ().compareTo(fechas.getFinHoraInhabServ());
       
             if(fechaA>0)
             {
                 FacesContext facesContext = FacesContext.getCurrentInstance();
                 facesContext.getExternalContext().getFlash().setKeepMessages(true);
                 FacesMessage facesMessage = new FacesMessage("La fecha de fin del periodo vacacional debe ser posterior o igual a la fecha de inicio del periodo vacacional, favor de revisar e ingresar nuevamente los datos correctos.");
                 facesContext.addMessage(FORMA, facesMessage);
                 
            
                 return;
             }   
             if(fechaB>0)
             {
                 FacesContext facesContext = FacesContext.getCurrentInstance();
                 facesContext.getExternalContext().getFlash().setKeepMessages(true);
                 FacesMessage facesMessage = new FacesMessage("La fecha y hora de inhabilitación del servicio debe ser posterior a la fecha y hora de inicio de la inhabilitación, favor de revisar e ingresar nuevamente los datos correctos.");
                 facesContext.addMessage(FORMA, facesMessage);
                 
                 return;
             }
            
            periodoVacService.actualizarEstadoPeriodoVac(getModificarPeriodoVacacionalObjeto());
            LOG.info(""+getModificarPeriodoVacacionalObjeto());
          

            
            FacesContext actualizar = FacesContext.getCurrentInstance();
            actualizar.getExternalContext().getFlash().setKeepMessages(true);
            actualizar.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Se ha modificado el periodo vacacional con éxito."));
            
            
            actualizar.getExternalContext().redirect(LISTAPERIODO);
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().getFlash().setKeepMessages(true);
            FacesMessage facesMessage = new FacesMessage("","Se ha modificado el periodo vacacional con éxito");
            facesContext.addMessage(FORMA, facesMessage);
            
            


        } catch (IOException ex) {
            LOG.error("IOException: " + ex);
        } catch (SIATException exception) {
             LOG.error("SIATException: " + exception);
        } catch (ParseException parseException) {
            LOG.error("ParseException: " + parseException);
        }
    }

    public Map<String, String> getMinutosM() {
        return minutosM;
    }

    public void setMinutosM(Map<String, String> minutosM) {
        this.minutosM = minutosM;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMinutoInicio() {
        return minutoInicio;
    }

    public void setMinutoInicio(String minutoInicio) {
        this.minutoInicio = minutoInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getMinutoFin() {
        return minutoFin;
    }

    public void setMinutoFin(String minutoFin) {
        this.minutoFin = minutoFin;
    }

    public Date getFechaIniMsj() {
        
        if (fechaIniMsj != null) {
            return (Date)fechaIniMsj.clone();
        } else {
            return null;
        }
    }

    public void setFechaIniMsj(Date fechaIniMsj) {
        if (fechaIniMsj != null) {
            this.fechaIniMsj = (Date)fechaIniMsj.clone();
        } else {
            this.fechaIniMsj = null;
        }
    }

    public Date getFechaFinMsj() {
        
         if (fechaFinMsj != null) {
            return (Date)fechaFinMsj.clone();
        } else {
            return null;
        }
    }

    public void setFechaFinMsj(Date fechaFinMsj) {
        if (fechaFinMsj != null) {
            this.fechaFinMsj = (Date)fechaFinMsj.clone();
        } else {
            this.fechaFinMsj = null;
        }
    }

    public Date getFechaReinicioSecuencia() {
        return fechaReinicioSecuencia != null ? (Date) fechaReinicioSecuencia.clone() : null;
    }

    public void setFechaReinicioSecuencia(Date fechaReinicioSecuencia) {
        this.fechaReinicioSecuencia = fechaReinicioSecuencia != null ? (Date) fechaReinicioSecuencia.clone() : null;
    }

    public String getHoraReinicioSecuencia() {

        return horaReinicioSecuencia;
    }

    public void setHoraReinicioSecuencia(String horaReinicioSecuencia) {

        this.horaReinicioSecuencia = horaReinicioSecuencia;
    }

    public String getMinutoReinicioSecuencia() {

        return minutoReinicioSecuencia;
    }

    public void setMinutoReinicioSecuencia(String minutoReinicioSecuencia) {

        this.minutoReinicioSecuencia = minutoReinicioSecuencia;
    }

    public ReinicioSecuenciaParamService getReinicioSecuenciaParamService() {
        return reinicioSecuenciaParamService;
    }

    public void setReinicioSecuenciaParamService(ReinicioSecuenciaParamService reinicioSecuenciaParamService) {
        this.reinicioSecuenciaParamService = reinicioSecuenciaParamService;
    }

    public String getFechaDeReinicioProgramada() {
        return fechaDeReinicioProgramada;
    }

    public void setFechaDeReinicioProgramada(String fechaDeReinicioProgramada) {
        this.fechaDeReinicioProgramada = fechaDeReinicioProgramada;
    }

    public boolean isExisteFechaReinicio() {
        return existeFechaReinicio;
    }

    public void setExisteFechaReinicio(boolean existeFechaReinicio) {
        this.existeFechaReinicio = existeFechaReinicio;
    }

}
