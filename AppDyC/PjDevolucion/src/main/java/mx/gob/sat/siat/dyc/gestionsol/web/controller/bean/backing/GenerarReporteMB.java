/*
 *  Todos los Derechos Reservados 2016 SAT.
 *  Servicio de Administracion Tributaria (SAT).
 *
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;


import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.devolucionaut.service.DyccModeloService;
import mx.gob.sat.mat.dyc.devolucionaut.service.DyctDictAutomaticaService;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DetalleTramGenerarRepoDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccModelo;
import mx.gob.sat.siat.dyc.domain.devolucionaut.constante.MarcResultado;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.generico.util.FechaUtil;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.utility.GenerarReporteHelper;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import mx.gob.sat.siat.dyc.util.constante.Constantes;
import org.apache.log4j.Logger;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 *
 * @author Mauricio Lira López
 */
@ManagedBean(name = "generarReporteMB")
@SessionScoped
public class GenerarReporteMB {

    private static final Logger LOG = Logger.getLogger(GenerarReporteMB.class);

    private static final String RIESGO = "riesgo";
    private static final String ESTATUS = "estatus";
    private static final String TOTAL = "total";
    private static final float IMAGE_SCALE_PERCENT = 15.00f;
    /**
     * Variables de fechas
     *
     *
     */
    private Date fechaActual;   
    private Date fechaInicioGenerarReporte;
    private Date fechaFinGenerarReporte;

    private Integer idSeleccionarModelo;
    private Integer cuentaDeModelos;
    private Integer countMotivoConRiesgo;
    private Integer countMotivoSinRiesgo;
    private Integer countFechaRegistrosAutorizadas;
    private Integer countFechaRegistrosPendientes;

    private List<DyccModelo> modelos;
    private List<DetalleTramGenerarRepoDTO> listaDetalleGP;
    private List<GenerarReporteHelper> listaGenerarReporte;

    private LazyDataModel<DetalleTramGenerarRepoDTO> listaDetalleGPLazy;

    private DyccEstadoSolDTO dyccEstadoSolEnProceso;
    private DyccEstadoSolDTO dyccEstadoSolAutTotal;

    @ManagedProperty(value = "#{dyccModeloService}")
    private DyccModeloService dyccModeloService;

    @ManagedProperty(value = "#{dyctDictAutomaticaService}")
    private DyctDictAutomaticaService dyctDictAutomaticaService;

    @PostConstruct
    public void init() {
        seleccionarModelos();
        fechaActual = new Date();
        listaGenerarReporte = new ArrayList<GenerarReporteHelper>();
    }

    public void seleccionarModelos() {
        try {
            modelos = dyccModeloService.consultarPorEstado(Boolean.TRUE);
        } catch (SIATException ex) {
            java.util.logging.Logger.getLogger(GenerarReporteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redireccionar() {
        try {
           if (fechaInicioGenerarReporte != null && fechaFinGenerarReporte != null && idSeleccionarModelo != null){
            fechaInicioGenerarReporte = FechaUtil.lowDate(fechaInicioGenerarReporte);
            fechaFinGenerarReporte = FechaUtil.upDate(fechaFinGenerarReporte);

               DyccModelo modelo = new DyccModelo();
               for (DyccModelo m : modelos) {
                   if (m.getIdModelo().equals(idSeleccionarModelo)) {
                       modelo = m;
                   }
               }

               if(modelo.getDescripcion().equalsIgnoreCase("SIVAD")){
                   dyccEstadoSolEnProceso = Constantes.EstadosSolicitud.EN_PROCESO_SIVAD;
                   dyccEstadoSolAutTotal = Constantes.EstadosSolicitud.AUTORIZADA_TOTAL_SIVAD;
               }else {
                   dyccEstadoSolEnProceso = Constantes.EstadosSolicitud.EN_PROCESO_MORSA;
                   dyccEstadoSolAutTotal = Constantes.EstadosSolicitud.AUTORIZADA_TOTAL_MORSA;
               }

            cuentaDeModelos = dyctDictAutomaticaService.buscarGenerarReportePorFiltro(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte);
            countMotivoConRiesgo = dyctDictAutomaticaService.buscarGenerarReportePorFiltroMotivoRiesgo(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, MarcResultado.CON_RIESGO.getCodigo());
            countMotivoSinRiesgo = dyctDictAutomaticaService.buscarGenerarReportePorFiltroMotivoRiesgo(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, MarcResultado.SIN_RIESGO.getCodigo());
            countFechaRegistrosAutorizadas = dyctDictAutomaticaService.buscarGenerarReportePorFiltroFechaRegistro(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, dyccEstadoSolAutTotal.getIdEstadoSolicitud());
            countFechaRegistrosPendientes = dyctDictAutomaticaService.buscarPendientesDictaminar(dyccEstadoSolEnProceso.getIdEstadoSolicitud(), fechaInicioGenerarReporte, fechaFinGenerarReporte);

            FacesContext context = FacesContext.getCurrentInstance();
            Application app = context.getApplication();
            ResourceBundle backendText = app.getResourceBundle(context, "msgValidacion");

            String solicitudesDictPor = backendText.getString("solicitudesDictPor");
            String solicitudesEmitidasConRiesgo = backendText.getString("solicitudesEmitidasConRiesgo");
            String solicitudesEmitidasSinRiesgo = backendText.getString("solicitudesEmitidasSinRiesgo");
            String solicitudesAutorizadasTotal = backendText.getString("solicitudesAutorizadasTotal");
            String pendientesDict = backendText.getString("pendientesDict");

            GenerarReporteHelper generarReporteHelper = new GenerarReporteHelper();
            generarReporteHelper.setEtiquetas(solicitudesDictPor + " " + modelo.getDescripcion());
            generarReporteHelper.setTotalRegistros(cuentaDeModelos);
            generarReporteHelper.setOpcion(TOTAL);
            generarReporteHelper.setValor(TOTAL);

            GenerarReporteHelper generarReporteHelperConRiesgo = new GenerarReporteHelper();
            generarReporteHelperConRiesgo.setEtiquetas(solicitudesEmitidasConRiesgo);
            generarReporteHelperConRiesgo.setTotalRegistros(countMotivoConRiesgo);
            generarReporteHelperConRiesgo.setOpcion(RIESGO);
            generarReporteHelperConRiesgo.setValor("02");

            GenerarReporteHelper generarReporteHelperSinRiesgo = new GenerarReporteHelper();
            generarReporteHelperSinRiesgo.setEtiquetas(solicitudesEmitidasSinRiesgo);
            generarReporteHelperSinRiesgo.setTotalRegistros(countMotivoSinRiesgo);
            generarReporteHelperSinRiesgo.setOpcion(RIESGO);
            generarReporteHelperSinRiesgo.setValor("01");

            GenerarReporteHelper generarReporteHelperAutorizadas = new GenerarReporteHelper();
            generarReporteHelperAutorizadas.setEtiquetas(solicitudesAutorizadasTotal);
            generarReporteHelperAutorizadas.setTotalRegistros(countFechaRegistrosAutorizadas);
            generarReporteHelperAutorizadas.setOpcion(ESTATUS);
            generarReporteHelperAutorizadas.setValor("true");

            GenerarReporteHelper generarReporteHelperPendientes = new GenerarReporteHelper();
            generarReporteHelperPendientes.setEtiquetas(pendientesDict);
            generarReporteHelperPendientes.setTotalRegistros(countFechaRegistrosPendientes);
            generarReporteHelperPendientes.setOpcion(ESTATUS);
            generarReporteHelperPendientes.setValor("false");

            listaGenerarReporte.clear();
            listaGenerarReporte.add(generarReporteHelper);
            listaGenerarReporte.add(generarReporteHelperConRiesgo);
            listaGenerarReporte.add(generarReporteHelperSinRiesgo);
            listaGenerarReporte.add(generarReporteHelperAutorizadas);
            listaGenerarReporte.add(generarReporteHelperPendientes);

            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                       + "/faces/resources/pages/gestionsol/generarreporte/generarReporte.jsf");
           }else{            
            if(fechaInicioGenerarReporte==null){
                   FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Llenar la fecha inicial",
                                                                              null));
            }
               if(fechaFinGenerarReporte == null){
                   FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Llenar la feche final",
                                                                              null));
               }
               if(idSeleccionarModelo==null){
                   FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccionar un modelo",
                                                                             null));
               }
           }
        } catch (SIATException e) {
            LOG.error("Hubo dasdasdatipo de trámite, intente mas tarde." + e.getDescripcion());
        } catch (IOException e) {
            LOG.error("IOException_Redireccionar." + e.getMessage());
        }
    }
    
    public void redireccionarDetalleTramiteGenerarReporte() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        final String opcion = params.get("opcion");
        final String parametroMetodo = params.get("variableAux");
        final boolean var = Boolean.parseBoolean(parametroMetodo);

        listaDetalleGPLazy = new LazyDataModel<DetalleTramGenerarRepoDTO>() {
            private static final long serialVersionUID = 3109256773218160485L;

            @Override
            public List<DetalleTramGenerarRepoDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
                try {
                    Paginador paginador = new Paginador();
                    paginador.setNPaginaActual((first + pageSize) / pageSize);
                    paginador.setNRegPorPagina(pageSize);

                    int nReg = 0;
                    if (opcion.equals(TOTAL)) {
                        nReg = dyctDictAutomaticaService.buscarGenerarReportePorFiltro(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte);
                        listaDetalleGP = dyctDictAutomaticaService.consultarDetalleTramGenRepoPorFiltro(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, paginador);

                    } else if (opcion.equals(RIESGO)) {
                        nReg = dyctDictAutomaticaService.buscarGenerarReportePorFiltroMotivoRiesgo(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, parametroMetodo);
                        listaDetalleGP = dyctDictAutomaticaService.consultarDetalleTramGenRepoPorFiltroMotivoRiesgo(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, parametroMetodo, paginador);

                    } else if (opcion.equals(ESTATUS)) {
                        if(var) {
                            nReg = dyctDictAutomaticaService.buscarGenerarReportePorFiltroFechaRegistro(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, dyccEstadoSolAutTotal.getIdEstadoSolicitud());
                            listaDetalleGP = dyctDictAutomaticaService.consultarDetalleTramGenRepoPorFiltroFechaRegistro(idSeleccionarModelo, fechaInicioGenerarReporte, fechaFinGenerarReporte, dyccEstadoSolAutTotal.getIdEstadoSolicitud(), paginador);

                        } else {
                            nReg = dyctDictAutomaticaService.buscarPendientesDictaminar(dyccEstadoSolEnProceso.getIdEstadoSolicitud(), fechaInicioGenerarReporte, fechaFinGenerarReporte);
                            listaDetalleGP = dyctDictAutomaticaService.consultarPendientesDictaminar(dyccEstadoSolEnProceso.getIdEstadoSolicitud(), fechaInicioGenerarReporte, fechaFinGenerarReporte, paginador);

                        }
                    }
                    setRowCount(nReg);
                    return listaDetalleGP;
                } catch (SIATException ex) {
                    LOG.error("Error al mostra la lista de detalle en reportes:: " + ex.getDescripcion());
                }
                return null;
            }

            @Override
            public Object getRowKey(DetalleTramGenerarRepoDTO object) {
                return object.getNumControl();
            }

            @Override
            public DetalleTramGenerarRepoDTO getRowData(String rowKey) {
                for (DetalleTramGenerarRepoDTO detalleTramGenerarRepoDTO : listaDetalleGP) {
                    if (detalleTramGenerarRepoDTO.getNumControl().equals(rowKey)) {
                        return detalleTramGenerarRepoDTO;
                    }
                }
                return null;
            }

        };

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                    + "/faces/resources/pages/gestionsol/generarreporte/detalleTramitesGenerarReporte.jsf");
        } catch (IOException e) {
            LOG.error("IOException_redireccionarDetalleTramiteGenerarReporte." + e.getMessage());
        }

    }

    public void regresar() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String regresar = params.get("regresar");
            
        try {            
            if (regresar.equals("dycAdministracion")){                
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                    + "/faces/resources/pages/gestionsol/" + regresar + ".jsf");                
            }else if (regresar.equals("generarReporte")){
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                    + "/faces/resources/pages/gestionsol/generarreporte/" + regresar + ".jsf");
                listaDetalleGP.clear();   
            }            

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GenerarReporteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void preProcessPDF(Object document) {
        Document pdf = (Document)document;
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();
        try {
            Image im = Image.getInstance("/siat/imagenes/logoSAT.jpg");
            im.scalePercent(IMAGE_SCALE_PERCENT);
            pdf.add(im);
            pdf.add(new Paragraph("\n"));
        } catch (Exception e) {
            LOG.error("No se encontro el logo: " + e.getMessage());
        }
    }

    public void reset() {        
        fechaInicioGenerarReporte = null;
        fechaFinGenerarReporte = null;
        idSeleccionarModelo = null;
    }

    public Date getFechaActual() {
        if (null != fechaActual) {
            return (Date) fechaActual.clone();
        } else {
            return null;
        }
    }

    public void setFechaActual(Date fechaActual) {
        if (null != fechaActual) {
            this.fechaActual = (Date) fechaActual.clone();
        } else {
            this.fechaActual = null;
        }
    }

    public Date getFechaInicioGenerarReporte() {
        if (null != fechaInicioGenerarReporte) {
            return (Date) fechaInicioGenerarReporte.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicioGenerarReporte(Date fechaInicioGenerarReporte) {
        if (null != fechaInicioGenerarReporte) {
            this.fechaInicioGenerarReporte = (Date) fechaInicioGenerarReporte.clone();
        } else {
            this.fechaInicioGenerarReporte = null;
        }
    }

    public Date getFechaFinGenerarReporte() {
        if (null != fechaFinGenerarReporte) {
            return (Date) fechaFinGenerarReporte.clone();
        } else {
            return null;
        }
    }

    public void setFechaFinGenerarReporte(Date fechaFinGenerarReporte) {
        if (null != fechaFinGenerarReporte) {
            this.fechaFinGenerarReporte = (Date) fechaFinGenerarReporte.clone();
        } else {
            this.fechaFinGenerarReporte = null;
        }
    }

    public DyccModeloService getDyccModeloService() {
        return dyccModeloService;
    }

    public void setDyccModeloService(DyccModeloService dyccModeloService) {
        this.dyccModeloService = dyccModeloService;
    }

    public Integer getIdSeleccionarModelo() {
        return idSeleccionarModelo;
    }

    public void setIdSeleccionarModelo(Integer idSeleccionarModelo) {
        this.idSeleccionarModelo = idSeleccionarModelo;
    }

    public List<DyccModelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<DyccModelo> modelos) {
        this.modelos = modelos;
    }

    public DyctDictAutomaticaService getDyctDictAutomaticaService() {
        return dyctDictAutomaticaService;
    }

    public void setDyctDictAutomaticaService(DyctDictAutomaticaService dyctDictAutomaticaService) {
        this.dyctDictAutomaticaService = dyctDictAutomaticaService;
    }

    public Integer getCuentaDeModelos() {
        return cuentaDeModelos;
    }

    public void setCuentaDeModelos(Integer cuentaDeModelos) {
        this.cuentaDeModelos = cuentaDeModelos;
    }

    public Integer getCountMotivoConRiesgo() {
        return countMotivoConRiesgo;
    }

    public void setCountMotivoConRiesgo(Integer countMotivoRiesgo) {
        this.countMotivoConRiesgo = countMotivoRiesgo;
    }

    public Integer getCountFechaRegistrosAutorizadas() {
        return countFechaRegistrosAutorizadas;
    }

    public void setCountFechaRegistrosAutorizadas(Integer countFechaRegistros) {
        this.countFechaRegistrosAutorizadas = countFechaRegistros;
    }

    public Integer getCountMotivoSinRiesgo() {
        return countMotivoSinRiesgo;
    }

    public void setCountMotivoSinRiesgo(Integer countMotivoSinRiesgo) {
        this.countMotivoSinRiesgo = countMotivoSinRiesgo;
    }

    public Integer getCountFechaRegistrosPendientes() {
        return countFechaRegistrosPendientes;
    }

    public void setCountFechaRegistrosPendientes(Integer countFechaRegistrosPendientes) {
        this.countFechaRegistrosPendientes = countFechaRegistrosPendientes;
    }

    public List<DetalleTramGenerarRepoDTO> getListaDetalleGP() {
        return listaDetalleGP;
    }

    public void setListaDetalleGP(List<DetalleTramGenerarRepoDTO> listaDetalleGP) {
        this.listaDetalleGP = listaDetalleGP;
    }

    public LazyDataModel<DetalleTramGenerarRepoDTO> getListaDetalleGPLazy() {
        return listaDetalleGPLazy;
    }

    public void setListaDetalleGPLazy(LazyDataModel<DetalleTramGenerarRepoDTO> listaDetalleGPLazy) {
        this.listaDetalleGPLazy = listaDetalleGPLazy;
    }

    public List<GenerarReporteHelper> getListaGenerarReporte() {
        return listaGenerarReporte;
    }

    public void setListaGenerarReporte(List<GenerarReporteHelper> listaGenerarReporte) {
        this.listaGenerarReporte = listaGenerarReporte;
    }

}
