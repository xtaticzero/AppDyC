package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.io.IOException;
import java.math.BigDecimal;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.controlsaldos.service.DetalleIcepService;
import mx.gob.sat.siat.dyc.controlsaldos.util.ConstantesCS;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridCompensacionesBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridDeclaracionesBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridDevolucionesBean;
import mx.gob.sat.siat.dyc.domain.movsaldo.DyctAccionPrivAjusDTO;
import mx.gob.sat.siat.dyc.generico.util.Reporte;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;


/**
 * Managed Bean de la vista detalleIcep.
 * @author Huetzin Cruz Lozano
 * @since 08 Diciembre 2014
 */
@ManagedBean(name = "detalleIcepMB")
@ViewScoped
public class DetalleIcepMB {

    private static final Logger LOG = Logger.getLogger(DetalleIcepMB.class.getName());

    @ManagedProperty(value = "#{bdDetalleIcep}")
    private DetalleIcepService bussinesDelegate;

    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private String nombreRazonSocial;
    private String rfc;
    private String impuesto;
    private String concepto;
    private String periodo;
    private Integer ejercicio;
    private String tipoSaldo;

    private List<FilaGridDeclaracionesBean> declaraciones;
    private List<FilaGridCompensacionesBean> compensaciones;
    private List<FilaGridDevolucionesBean> devoluciones;

    private String notas;

    private Double ultimoImpDecl;
    private Double importeResuelto;
    private Double importeEfectuado;
    private Double importeAcreditacion;
    private Double remanenteFavCargo;

    private FilaGridCompensacionesBean compensacionSelec;
    private FilaGridDevolucionesBean devolucionSelec;
    private FilaGridDeclaracionesBean declaraNueva;
    private FilaGridDeclaracionesBean declaraSelec;

    private String msjRemanenteReal;

    // Estos 6 campos son los de la tabla de devoluciones
    private String origenSaldo;
    private Date fechaPresentacion;
    private Date fechaCausacion;
    private String numOperacion;
    private String numDocumento;
    private BigDecimal saldoAFavor;

    private Boolean mostrarTablaDecls;
    private Boolean habilitarElimDeclara;
    private Boolean habilitarElimComp;
    private Boolean habilitarElimDevol;
    private Boolean habilitarValidarDeclara;
    private Boolean mostrarDlgConfirElim;
    private String mensajeDlgConfirElim;
    private Boolean mostrarDlgConfirReact;
    private String mensajeDlgConfirReact;
    private Integer entidadEliminar;

    private Boolean permitirEdicion;
    private Boolean mostrarDlgDetDeclara;

    private Boolean mostrarBtnAjustarMovs;

    private Boolean existenAjustes;

    private String descripcionIcep;
    private Date fechaCalculo;
    private Date fechaPrimerDecValidada;

    @PostConstruct
    public void inicializar() {
        LOG.debug("Se acaba de crear un nuevo ManageBean de scope view ->" + new Date());
        LOG.debug("idSaldoIcep ->" + mbSession.getIdSaldoIcep() + "<-");
        try {
            habilitarElimDeclara = Boolean.FALSE;
            AccesoUsr accesoUsrL = serviceObtenerSesion.execute();


            Map<String, Object> infoInicial =
                bussinesDelegate.obtenerInfoInicial(mbSession.getIdSaldoIcep(), accesoUsrL);
            nombreRazonSocial = (String)infoInicial.get("nombreRazonSocial");
            LOG.debug("nombreRazonSocial ->" + nombreRazonSocial + "<-");

            if (nombreRazonSocial == null || "".equals(nombreRazonSocial)) {
                nombreRazonSocial = mbSession.getNomRazonSocIcepActivo();
            }

            rfc = (String)infoInicial.get("rfc");
            impuesto = (String)infoInicial.get("impuesto");
            concepto = (String)infoInicial.get("concepto");
            periodo = (String)infoInicial.get("periodo");
            ejercicio = (Integer)infoInicial.get("ejercicio");
            tipoSaldo = (String)infoInicial.get("tipoSaldo");

            ///Aqui se llena este desma
            setOrigenSaldo((String)infoInicial.get("origenSaldo"));
            fechaPresentacion = (Date)infoInicial.get("fechaPresentacion");
            setFechaCausacion((Date)infoInicial.get("fechaCausacion"));
            setNumOperacion((String)infoInicial.get("numOperacion"));
            numDocumento = (String)infoInicial.get("numDocumento");
            saldoAFavor = (BigDecimal)infoInicial.get("saldoAFavor");
            fechaCalculo = (Date) infoInicial.get("fechaCalculo");
            fechaPrimerDecValidada = (Date) infoInicial.get("fechaPrimerDecValidada");

            setDeclaraciones((List<FilaGridDeclaracionesBean>)infoInicial.get("declaraciones"));
            setCompensaciones((List<FilaGridCompensacionesBean>)infoInicial.get("compensaciones"));
            setDevoluciones((List<FilaGridDevolucionesBean>)infoInicial.get("devoluciones"));

            notas = (String)infoInicial.get("notas");

            ultimoImpDecl = (Double)infoInicial.get("ultimoImpDecl");
            importeResuelto = (Double)infoInicial.get("importeResuelto");
            importeEfectuado = (Double)infoInicial.get("importeEfectuado");
            importeAcreditacion = (Double)infoInicial.get("importeAcreditacion");
            remanenteFavCargo = (Double)infoInicial.get("remanenteFavCargo");
            msjRemanenteReal = (String)infoInicial.get("msjRemanenteReal");
            LOG.debug("parametro a utilizar para ver si presento la tabla ->" + infoInicial.get("saldoAFavor"));
            declaraNueva = new FilaGridDeclaracionesBean();

            mostrarTablaDecls =
                    !getDeclaraciones().isEmpty() || (null == infoInicial.get("saldoAFavor") && infoInicial.get("numDocumento") ==
                                                      null);

            LOG.debug("mostrarTablaDecls ->" + mostrarTablaDecls);

            String roles = accesoUsrL.getRoles();
            LOG.debug("roles ->" + roles);
            permitirEdicion = roles.contains("SAT_SDC_ADMIN_SAL");
            mostrarDlgDetDeclara = Boolean.FALSE;

            habilitarValidarDeclara = Boolean.FALSE;

            DyctAccionPrivAjusDTO permisoAjustar = (DyctAccionPrivAjusDTO)infoInicial.get("permisoAjustar");
            mbSession.setPermisoAjustar(permisoAjustar);
            if (permisoAjustar != null) {
                if (Constantes.PermisosEspecialesAjustarSaldo.ADMIN_CENTRAL == permisoAjustar) {
                    mostrarBtnAjustarMovs = Boolean.TRUE;
                } else {
                    mostrarBtnAjustarMovs = Boolean.TRUE;
                }
            } else {
                mostrarBtnAjustarMovs = Boolean.FALSE;
            }

            existenAjustes = (Boolean)infoInicial.get("existenAjustes");
            LOG.debug("existenAjustes ->" + existenAjustes);
            descripcionIcep = (String)infoInicial.get("descripcionIcep");
        } catch (SIATException siate) {
            LOG.error(siate);
        } catch (Exception e) {
            LOG.error("Ocurrio un error al intentar obtener la información para la pantalla detalleIcep.jsf para el idIcep ->" +
                      mbSession.getIdSaldoIcep() + "<-; mensaje ->" + e.getMessage() + "<-");
            ManejadorLogException.getTraceError(e);
        }
    }


    public void imprimirReporte() {
        LOG.debug("INICIO imprimirReporte");
        Map<String, Object> datosReporte = new HashMap<String, Object>();
        datosReporte.put("nombreRazonSocial", getNombreRazonSocial());
        datosReporte.put("rfc", rfc);
        datosReporte.put("impuesto", impuesto);
        datosReporte.put("concepto", concepto);
        datosReporte.put("periodo", periodo);
        datosReporte.put("ejercicio", ejercicio);
        datosReporte.put("tipoSaldo", tipoSaldo);

        datosReporte.put("remanente", Utils.formatearMoneda(remanenteFavCargo));
        datosReporte.put("estatus", "");
        datosReporte.put("RUTA_IMAGEN", ConstantesDyCURL.URL_IMAGENES);

        datosReporte.put("listaDeclaraciones", getDeclaraciones());

        if (getCompensaciones() != null) {
            for (FilaGridCompensacionesBean comp : getCompensaciones()) {
                comp.setImporteCompensadoStr(Utils.formatearMoneda(comp.getImporteCompensado().doubleValue()));
                comp.setImporteCompHistStr(Utils.formatearMoneda(comp.getImporteComphist().doubleValue()));
            }
        }
        datosReporte.put("listaCompensaciones", getCompensaciones());

        if (devoluciones != null) {
            for (FilaGridDevolucionesBean dev : devoluciones) {
                dev.setImporteSolDevStr(Utils.formatearMoneda(dev.getImporteSolDev()));
                dev.setFechaResolucionStr(Utils.formatearFecha(dev.getFechaResolucion()));
                dev.setImporteAutorizadoStr(Utils.formatearMoneda(dev.getImporteAutorizado()));
                dev.setImporteNegadoStr(Utils.formatearMoneda(dev.getImporteNegado()));
                dev.setActualizacionStr(Utils.formatearMoneda(dev.getActualizacion()));
                dev.setInteresesStr(Utils.formatearMoneda(dev.getIntereses()));
                dev.setRetInteresesStr(Utils.formatearMoneda(dev.getRetIntereses()));
                dev.setImpCompensadoStr(Utils.formatearMoneda(dev.getImpCompensado()));
                dev.setImporteNetoDevStr(Utils.formatearMoneda(dev.getImporteNetoDev()));
            }
        }
        datosReporte.put("listaResolDevol", getDevoluciones());

        datosReporte.put("ultimoImporteDeclarado", Utils.formatearMoneda(ultimoImpDecl));
        datosReporte.put("importeResuelto", Utils.formatearMoneda(importeResuelto));
        datosReporte.put("remanenteFavCar", Utils.formatearMoneda(remanenteFavCargo));
        datosReporte.put("importeEfectuado", Utils.formatearMoneda(importeEfectuado));
        datosReporte.put("importeAcreditacion", Utils.formatearMoneda(importeAcreditacion));
        datosReporte.put("importeDeclarado", Utils.formatearMoneda(ultimoImpDecl));
        datosReporte.put("importeCompHistorico", Utils.formatearMoneda(importeResuelto));
        datosReporte.put("idIcep", mbSession.getIdSaldoIcep());
        datosReporte.put("fechaReporte", "Fecha impresión: " + Utils.formatearFechaCompleta(new Date()));

        LOG.debug("ultimoImporteDeclarado ->" + datosReporte.get("ultimoImporteDeclarado") + "<-");
        LOG.debug("importeResuelto ->" + datosReporte.get("importeResuelto") + "<-");

        try {
            new Reporte().imprimirReporte("/siat/dyc/reportes/detalleIcep.jasper", datosReporte, "detalleIcep");
        } catch (SIATException e) {
            LOG.error(e);
        }
    }

    public void regresar() {
        FacesContext temp = FacesContext.getCurrentInstance();
        
        try {
            temp.getExternalContext().redirect("criteriosBusqueda.jsf");            
            /** esto estaba antes y en String en vez de void: return "busquedaSaldos"; */
        } catch (IOException ex) {
            LOG.debug("Error en redirect",ex);
        }
    }

    public String irADesglose() {
        return "desgloseSaldos";
    }

    public String insertarDeclaracion() {
        LOG.debug("INICIO insertarDeclaracion");

        LOG.debug("numOperacion ->" + declaraNueva.getNumOperacion() + "<-");
        if (declaraNueva.getNumOperacion() == null) {
            declaraNueva.setNumOperacion(0L);
        }

        AccesoUsr accesoUsrL = serviceObtenerSesion.execute();
        LOG.debug("nombre completo usuario loggeado ->" + accesoUsrL.getNombreCompleto());
        Map<String, Object> result =
            bussinesDelegate.insertarDeclaracion(declaraNueva, mbSession.getIdSaldoIcep(), accesoUsrL.getNombreCompleto());

        if ((Boolean)result.get(DetalleIcepService.KEY_SUCCESS)) {
            LOG.debug("la declaración se inserto satisfactoriamente");
            limpiarDeclaracion();
            return "detalleIcep";
        } else {
            LOG.debug("Ocurrio un error al insertar la declaracion");
            FacesMessage msjParamNoValido =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar la declaración manual -",
                                 (String)result.get(DetalleIcepService.KEY_MENSAJE));

            FacesContext.getCurrentInstance().addMessage(null, msjParamNoValido);
        }

        LOG.debug("FIN insertarDeclaracion");

        return null;
    }

    public void buscarDeclaraciones() {
        LOG.info("INICIO buscarDeclaraciones");
        try {
            bussinesDelegate.buscarDeclaraciones(mbSession.getIdSaldoIcep());
        } catch (SIATException siate) {
            LOG.error(siate);
        }
    }

    public void limpiarDeclaracion() {
        LOG.debug("limpiarDeclaracion");
        RequestContext.getCurrentInstance().reset("formDeclaracion:pnlDetalleDeclaracion");

        declaraNueva.setFechaPresentacion(null);
        declaraNueva.setNumOperacion(null);
        declaraNueva.setIdTipoDeclaracion(null);
        declaraNueva.setTipoDeclaracion(null);
        declaraNueva.setImporteDeclarado(null);
        declaraNueva.setValidadaDWH(null);
        declaraNueva.setFechaPresentacionStr(null);
        declaraNueva.setImporteDeclaradoStr(null);
        declaraNueva.setNotas(null);

        mostrarDlgDetDeclara = Boolean.FALSE;
    }

    public void manejarRowSelectDeclara() {
        LOG.debug("declaraSelec ->" + declaraSelec.getOrigenDeclara() + "<-");
        habilitarElimDeclara = ConstantesCS.LBL_DECLARA_MANUAL.equals(declaraSelec.getOrigenDeclara());
        habilitarValidarDeclara = declaraSelec.getValidadaDWH().equalsIgnoreCase("No");
    }

    public void manejarRowSelectComp() {
        LOG.debug("compensacionSelec ->" + compensacionSelec + "<-");
        LOG.debug("tipoCompensacion ->" + compensacionSelec.getTipoCompensacion() + "<-");
        habilitarElimComp = "HIST".equals(compensacionSelec.getTipoCompensacion());
    }

    public void manejarRowSelectDevol() {
        LOG.debug("devolucionSelec ->" + devolucionSelec + "<-");
        habilitarElimDevol = "HIST".equals(devolucionSelec.getTipoDevolucion());
    }

    public void mostrarDlgConfirElim(ActionEvent evento) {
        LOG.debug("INICIO mostrarDlgConfirElim");
        LOG.debug("evento ->" + evento + "<-");
        UIComponent c = evento.getComponent();
        LOG.debug("idButton ->" + c.getId() + "<-");
        mostrarDlgConfirElim = Boolean.TRUE;

        if ("btnElimDeclara".equals(c.getId())) {
            mensajeDlgConfirElim =
                    "¿Está seguro que desea eliminar la declaración con número de operación " + declaraSelec.getNumOperacion() +
                    "?";
            entidadEliminar = 1;
        } else if ("btnElimComp".equals(c.getId())) {
            mensajeDlgConfirElim =
                    "¿Está seguro que desea eliminar la compensación con número de control  " + compensacionSelec.getNumControl() +
                    "?";
            entidadEliminar = 2;
        } else if ("btnElimDevol".equals(c.getId())) {
            mensajeDlgConfirElim =
                    "¿Está seguro que desea eliminar la devolución con número de control " + devolucionSelec.getNumControl() +
                    "?";
            entidadEliminar = ConstantesDyCNumerico.VALOR_3;
        }
    }

    public void mostrarDlgConfirReact(ActionEvent evento) {
        LOG.debug("INICIO mostrarDlgConfirReact");
        LOG.debug("evento ->" + evento + "<-");
        mostrarDlgConfirReact = Boolean.TRUE;

        mensajeDlgConfirReact =
                "¿Está seguro que desea reactivar la declaración con número de operación " + declaraSelec.getNumOperacion() +
                "?";
    }

    public void eliminarEntidadHist() {
        LOG.debug("INICIO eliminarEntidadHist");
        LOG.debug("entidadEliminar ->" + entidadEliminar);
        switch (entidadEliminar) {
        case 1:
            LOG.debug("INICIO eliminarDeclaraHist; idDeclaraIcep ->" + declaraSelec.getIdDeclaraIcep());
            bussinesDelegate.eliminarDeclaraHist(declaraSelec.getIdDeclaraIcep());
            break;
        case 2:
            bussinesDelegate.eliminarCompensacionHist(compensacionSelec.getIdMovCompensacion());
            break;
        case ConstantesDyCNumerico.VALOR_3:
            bussinesDelegate.eliminarDevolucionHist(devolucionSelec.getIdMovDevolucion());
            break;
        default:
            break;
        }
        inicializar();
        mostrarDlgConfirElim = Boolean.FALSE;
        mensajeDlgConfirElim = null;
        entidadEliminar = null;
    }

    public void reactivarEntidadHist() {
        LOG.debug("INICIO reactivarEntidadHist");
        LOG.debug("INICIO reactivarEntidadHist; idDeclaraIcep ->" + declaraSelec.getIdDeclaraIcep());
        bussinesDelegate.reactivarDeclaraHist(declaraSelec.getIdDeclaraIcep());
        inicializar();
        mostrarDlgConfirReact = Boolean.FALSE;
        mensajeDlgConfirReact = null;
    }

    public void mostrarDialogDetDeclara() {
        LOG.debug("INICIO mostrarDialogDetDeclara");
        mostrarDlgDetDeclara = Boolean.TRUE;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setTipoSaldo(String tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public String getTipoSaldo() {
        return tipoSaldo;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getNotas() {
        return notas;
    }

    public void setImporteResuelto(Double importeResuelto) {
        this.importeResuelto = importeResuelto;
    }

    public Double getImporteResuelto() {
        return importeResuelto;
    }

    public void setRemanenteFavCargo(Double remanenteFavCargo) {
        this.remanenteFavCargo = remanenteFavCargo;
    }

    public Double getRemanenteFavCargo() {
        return remanenteFavCargo;
    }

    public void setUltimoImpDecl(Double ultimoImpDecl) {
        this.ultimoImpDecl = ultimoImpDecl;
    }

    public Double getUltimoImpDecl() {
        return ultimoImpDecl;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public Double getImporteEfectuado() {
        return importeEfectuado;
    }

    public void setImporteEfectuado(Double importeEfectuado) {
        this.importeEfectuado = importeEfectuado;
    }

    public Double getImporteAcreditacion() {
        return importeAcreditacion;
    }

    public void setImporteAcreditacion(Double importeAcreditacion) {
        this.importeAcreditacion = importeAcreditacion;
    }

    public DetalleIcepService getBussinesDelegate() {
        return bussinesDelegate;
    }

    public void setBussinesDelegate(DetalleIcepService bussinesDelegate) {
        this.bussinesDelegate = bussinesDelegate;
    }

    public List<FilaGridDeclaracionesBean> getDeclaraciones() {
        return declaraciones;
    }

    public void setDeclaraciones(List<FilaGridDeclaracionesBean> declaraciones) {
        this.declaraciones = declaraciones;
    }

    public List<FilaGridCompensacionesBean> getCompensaciones() {
        return compensaciones;
    }

    public void setCompensaciones(List<FilaGridCompensacionesBean> compensaciones) {
        this.compensaciones = compensaciones;
    }

    public List<FilaGridDevolucionesBean> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(List<FilaGridDevolucionesBean> devoluciones) {
        this.devoluciones = devoluciones;
    }

    public FilaGridCompensacionesBean getCompensacionSelec() {
        return compensacionSelec;
    }

    public void setCompensacionSelec(FilaGridCompensacionesBean selectCompensacion) {
        this.compensacionSelec = selectCompensacion;
    }

    public FilaGridDevolucionesBean getDevolucionSelec() {
        return devolucionSelec;
    }

    public void setDevolucionSelec(FilaGridDevolucionesBean selectDevolucion) {
        this.devolucionSelec = selectDevolucion;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }

    public String getMsjRemanenteReal() {
        return msjRemanenteReal;
    }

    public void setMsjRemanenteReal(String msjRemanenteReal) {
        this.msjRemanenteReal = msjRemanenteReal;
    }

    public String getOrigenSaldo() {
        return origenSaldo;
    }

    public void setOrigenSaldo(String origenSaldo) {
        this.origenSaldo = origenSaldo;
    }

    public Date getFechaPresentacion() {
        return (fechaPresentacion != null) ? (Date)fechaPresentacion.clone() : null;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = (fechaPresentacion != null) ? (Date)fechaPresentacion.clone() : null;
    }

    public Date getFechaCausacion() {
        return (fechaCausacion != null) ? (Date)fechaCausacion.clone() : null;
    }

    public void setFechaCausacion(Date fechaCausacion) {
        this.fechaCausacion = (fechaCausacion != null) ? (Date)fechaCausacion.clone() : null;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public BigDecimal getSaldoAFavor() {
        return saldoAFavor;
    }

    public void setSaldoAFavor(BigDecimal saldoAFavor) {
        this.saldoAFavor = saldoAFavor;
    }

    public Boolean getMostrarTablaDecls() {
        return mostrarTablaDecls;
    }

    public void setMostrarTablaDecls(Boolean mostrarTablaDecls) {
        this.mostrarTablaDecls = mostrarTablaDecls;
    }

    public Boolean getHabilitarElimDeclara() {
        return habilitarElimDeclara;
    }

    public void setHabilitarElimDeclara(Boolean habilitarElimDeclara) {
        this.habilitarElimDeclara = habilitarElimDeclara;
    }

    public FilaGridDeclaracionesBean getDeclaraNueva() {
        return declaraNueva;
    }

    public void setDeclaraNueva(FilaGridDeclaracionesBean declaraNueva) {
        this.declaraNueva = declaraNueva;
    }

    public FilaGridDeclaracionesBean getDeclaraSelec() {
        return declaraSelec;
    }

    public void setDeclaraSelec(FilaGridDeclaracionesBean declaraSelec) {
        this.declaraSelec = declaraSelec;
    }

    public Boolean getHabilitarElimComp() {
        return habilitarElimComp;
    }

    public void setHabilitarElimComp(Boolean habilitarElimComp) {
        this.habilitarElimComp = habilitarElimComp;
    }

    public Boolean getHabilitarElimDevol() {
        return habilitarElimDevol;
    }

    public void setHabilitarElimDevol(Boolean habilitarElimDevol) {
        this.habilitarElimDevol = habilitarElimDevol;
    }

    public Boolean getMostrarDlgConfirElim() {
        return mostrarDlgConfirElim;
    }

    public void setMostrarDlgConfirElim(Boolean mostrarDlgConfirElim) {
        this.mostrarDlgConfirElim = mostrarDlgConfirElim;
    }

    public String getMensajeDlgConfirElim() {
        return mensajeDlgConfirElim;
    }

    public void setMensajeDlgConfirElim(String mensajeDlgConfirElim) {
        this.mensajeDlgConfirElim = mensajeDlgConfirElim;
    }

    public Integer getEntidadEliminar() {
        return entidadEliminar;
    }

    public void setEntidadEliminar(Integer entidadEliminar) {
        this.entidadEliminar = entidadEliminar;
    }

    public Boolean getPermitirEdicion() {
        return permitirEdicion;
    }

    public void setPermitirEdicion(Boolean permitirEdicion) {
        this.permitirEdicion = permitirEdicion;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public Boolean getMostrarDlgDetDeclara() {
        return mostrarDlgDetDeclara;
    }

    public void setMostrarDlgDetDeclara(Boolean mostrarDlgDetDeclara) {
        this.mostrarDlgDetDeclara = mostrarDlgDetDeclara;
    }

    public Boolean getHabilitarValidarDeclara() {
        return habilitarValidarDeclara;
    }

    public void setHabilitarValidarDeclara(Boolean habilitarValidarDeclara) {
        this.habilitarValidarDeclara = habilitarValidarDeclara;
    }

    public Boolean getMostrarBtnAjustarMovs() {
        return mostrarBtnAjustarMovs;
    }

    public void setMostrarBtnAjustarMovs(Boolean mostrarBtnAjustarMovs) {
        this.mostrarBtnAjustarMovs = mostrarBtnAjustarMovs;
    }

    public Boolean getExistenAjustes() {
        return existenAjustes;
    }

    public void setExistenAjustes(Boolean existenAjustes) {
        this.existenAjustes = existenAjustes;
    }

    public String getDescripcionIcep() {
        return descripcionIcep;
    }

    public void setDescripcionIcep(String descripcionIcep) {
        this.descripcionIcep = descripcionIcep;
    }

    public void setMostrarDlgConfirReact(Boolean mostrarDlgConfirReact) {
        this.mostrarDlgConfirReact = mostrarDlgConfirReact;
    }

    public Boolean getMostrarDlgConfirReact() {
        return mostrarDlgConfirReact;
    }

    public void setMensajeDlgConfirReact(String mensajeDlgConfirReact) {
        this.mensajeDlgConfirReact = mensajeDlgConfirReact;
    }

    public String getMensajeDlgConfirReact() {
        return mensajeDlgConfirReact;
    }

    public Boolean getHabilitarReactDeclara() {
        return habilitarElimDeclara && declaraSelec.getFechaFin() != null;
    }

    public Boolean getHabilitarElimDeclaraFechaFin() {
        return habilitarElimDeclara && declaraSelec.getFechaFin() == null;
    }

    public Date getFechaCalculo() {
        return fechaCalculo != null ? (Date) fechaCalculo.clone() : null;
    }

    public void setFechaCalculo(Date fechaCalculo) {
        this.fechaCalculo = fechaCalculo != null ? (Date) fechaCalculo.clone() : null;
    }

    public Date getFechaPrimerDecValidada() {
        return fechaPrimerDecValidada != null ? (Date) fechaPrimerDecValidada.clone() : null;
}

    public void setFechaPrimerDecValidada(Date fechaPrimerDecValidada) {
        this.fechaPrimerDecValidada = fechaPrimerDecValidada != null ? (Date) fechaPrimerDecValidada.clone() : null;
    }

}
