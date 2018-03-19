/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
 */
package mx.gob.sat.siat.dyc.admcat.web.controller.bean.backing;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.sat.mat.dyc.asignartramite.service.DycaConfigDictaminadorService;
import mx.gob.sat.mat.dyc.asignartramite.service.DycaTipoServicioAsignarService;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.FrmMatrizDictaminadorVO;
import mx.gob.sat.siat.dyc.admcat.service.matrizaprobador.AprobadorService;
import mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.DictaminadorService;
import mx.gob.sat.siat.dyc.domain.ags.SatAgsEmpleadosMVDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.generico.util.UtilsValidaSesion;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;
import mx.gob.sat.siat.dyc.util.constante2.ConstatesMsgDictaminadores;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import mx.gob.sat.mat.dyc.catalogo.service.DycaOrigenTramiteService;
import mx.gob.sat.mat.dyc.catalogo.service.DyccMontoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoTramiteService;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaConfigDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaTipoServicioAsignarDTO;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

/**
 * ManagedBean pantalla MantenerMatrizDictaminadores.
 *
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * @date Agosto 7, 2013
 */
@ManagedBean(name = "mDictaminadorMB")
@SessionScoped
public class MDictaminadorMB {

    private static final Logger LOG = Logger.getLogger(MDictaminadorMB.class);

    @ManagedProperty(value = "#{dictaminadorService}")
    private DictaminadorService dictaminadorService;

    @ManagedProperty(value = "#{aprobadorService}")
    private AprobadorService aprobadorService;

    @ManagedProperty("#{registroPistasAuditoria}")
    private RegistroPistasAuditoria registroPistasAuditoria;

    @ManagedProperty(value = "#{admcUnidadAdmvaService}")
    private AdmcUnidadAdmvaService admcUnidadAdmvaService;

    @ManagedProperty(value = "#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService satAgsEmpleadosMVService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{dycaOrigenTramite}")
    private DycaOrigenTramiteService dycaOrigenTramite;

    @ManagedProperty(value = "#{dyccMontoService}")
    private DyccMontoService dyccMontoService;

    @ManagedProperty(value = "#{dycaTipoServicioAsignarService}")
    private DycaTipoServicioAsignarService dycaTipoServicioAsignarService;

    @ManagedProperty(value = "#{dycaConfigDictaminadorService}")
    private DycaConfigDictaminadorService dycaConfigDictaminadorService;

    @ManagedProperty(value = "#{dyccTipoTramiteService}")
    private DyccTipoTramiteService dyccTipoTramiteService;

    private SatAgsEmpleadosMVDTO empleadoAGS;

    private FrmMatrizDictaminadorVO frm;
    private DictaminadorVO dictaminador;
    private DictaminadorVO selectDictaminador;

    private List<DictaminadorVO> tblListaDictaminadores;
    private List<AdmcUnidadAdmvaDTO> tblListaAdministracion;
    private EmpleadoVO empleado;

    private AdmcUnidadAdmvaDTO admva;
    private FacesMessage message;
    private AdmcUnidadAdmvaDTO selectAdmva;
    private AccesoUsr accesoUsr;
    private Map<String, String> remplaceMensajes;

    private String expRFC = ConstantesDyC.EXP_REG_RFC;

    private static final String MENSAJE_ERROR_DICTAMINADOR_ACTIVO = "El empleado %s se encuentra activo como dictaminador en la administración %s, favor de verificar el dato o solicitar la inactivación en la citada Administración para ingresarlo nuevamente";

    private DualListModel<DycaOrigenTramiteDTO> infoARequerir;
    private List<ItemComboBean> tiposServicio;
    private String servicioSelect;
    private List<DyccMontoDTO> listaMontos;
    private Integer montoSelect;
    private List<DyccTipoServicioDTO> tipoServicioList;

    private DyccMontoDTO montoAsignado;
    private ItemComboBean servicioAsignado;
    private List<DyccTipoTramiteDTO> tipoTramiteList;

    @PostConstruct
    public void init() {
        this.frm = new FrmMatrizDictaminadorVO();
        this.frm.setFrmMensaje(new StringBuilder());

        this.tblListaDictaminadores = new ArrayList<DictaminadorVO>();
        this.tblListaAdministracion = new ArrayList<AdmcUnidadAdmvaDTO>();

        this.dictaminador = new DictaminadorVO();

        this.accesoUsr = new AccesoUsr();
        this.remplaceMensajes = new HashMap<String, String>();

        this.obtenerSession();
        this.frm.setTituloPagina(admva.getClaveSir() + " - " + admva.getNombre());

        UtilsValidaSesion.validarSesion(accesoUsr, Procesos.DYC00002);

        try {
            frm.setConfMensaje(this.dictaminadorService.obtenerMensaje(ConstatesMsgDictaminadores.MSG_MD_CFR_ELIM));
            listaMontos = dyccMontoService.obtenerMontos();
        } catch (Exception se) {
            LOG.error("Ocurrio un error al iniciar el ManageBean MDictaminadorMB; ->" + se.getMessage());
            ManejadorLogException.getTraceError(se);
        }
        this.verListaDictaminadores();
        montoSelect = null;
        infoARequerir = new DualListModel<DycaOrigenTramiteDTO>(new ArrayList<DycaOrigenTramiteDTO>(), new ArrayList<DycaOrigenTramiteDTO>());
        tipoServicioList = new ArrayList<DyccTipoServicioDTO>();
        servicioSelect = null;
        montoAsignado = new DyccMontoDTO();
        servicioAsignado = new ItemComboBean();
        tipoTramiteList = new ArrayList<DyccTipoTramiteDTO>();

        iniTipoSolAsignar();
    }

    private void iniTipoSolAsignar() {
        tiposServicio = new ArrayList<ItemComboBean>();
        ItemComboBean itemCombo = new ItemComboBean();
        itemCombo.setIdStr("1,3");
        itemCombo.setDescripcion("Devoluciones y Compensaciones");
        tiposServicio.add(itemCombo);

        itemCombo = new ItemComboBean();
        itemCombo.setIdStr("1");
        itemCombo.setDescripcion("Devolución");
        tiposServicio.add(itemCombo);

        itemCombo = new ItemComboBean();
        itemCombo.setIdStr("3");
        itemCombo.setDescripcion("Compensación");
        tiposServicio.add(itemCombo);
    }

    public void verListaDictaminadores() {
        try {
            this.dictaminador = new DictaminadorVO();
            this.dictaminador.setClaveAdm(this.getAdmva().getClaveSir());

            this.tblListaDictaminadores = this.dictaminadorService.obtenerDictaminadores(this.dictaminador);
            frm.setNumResultados(this.getTblListaDictaminadores().size());
            frm.setRowsPaginador(ConstantesDyC1.NO_COLS_PAGINA);
            frm.setPaginador(ConstantesDyC1.NO_COLS_PAGINA < this.getTblListaDictaminadores().size() ? Boolean.TRUE : Boolean.FALSE);
            if (ConstantesDyC1.NO_COLS_PAGINA < this.getTblListaDictaminadores().size()) {
                frm.setPaginador(Boolean.TRUE);
            } else {
                frm.setPaginador(Boolean.FALSE);
            }
        } catch (Exception e) {
            LOG.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
    }

    public void frmBuscarEmpleado() {
        empleado = new EmpleadoVO();
        empleado.setDictaminador(new DictaminadorVO());

        // DEFAULT SELECCION
        frm.setEmpleado("No. Empleado");
        frm.setInpEmpleado(Boolean.TRUE);

        // VISTA BUSQUEDA EMPLEADO
        // RENDER DIALOG *********************************************
        frm.setDlgVerBusquedaEmp(Boolean.TRUE);
        frm.setDlgVerAgregaEmp(Boolean.FALSE);
        frm.setDlgVerConsultaDict(Boolean.FALSE);
        frm.setDlgVerModificaDict(Boolean.FALSE);
        frm.setDlgVerComision(Boolean.FALSE);
        frm.setDlgComisionado(Boolean.FALSE);
        frm.setDlgEmpAGS(Boolean.FALSE);
        // RENDER DIALOG *********************************************
        frm.setIfrmAccion(ConstatesMsgDictaminadores.OPE_MD_BUSQUEDA);
        frm.setDlgTitulo(ConstatesMsgDictaminadores.CU_MD_BUSQUEDA);
    }

    public void abrirDlgAsignarTramite() {
        consultarTramiteAsignado(selectDictaminador.getNumEmpleado());
        if (servicioSelect != null) {
            buscarTramiteXServicio();
            List<DycaOrigenTramiteDTO> source = new ArrayList<DycaOrigenTramiteDTO>(infoARequerir.getSource());
            List<DycaOrigenTramiteDTO> target = new ArrayList<DycaOrigenTramiteDTO>();

            for (DyccTipoTramiteDTO tipoTramite : tipoTramiteList) {
                for (int i = 0; i < source.size(); i++) {
                    if (tipoTramite.getIdTipoTramite().equals(source.get(i).getDyccTipoTramiteDTO().getIdTipoTramite())) {
                        target.add(source.remove(i));
                        break;
                    }
                }
            }

            infoARequerir = new DualListModel<DycaOrigenTramiteDTO>(source, target);

        } else {
            infoARequerir = new DualListModel<DycaOrigenTramiteDTO>(new ArrayList<DycaOrigenTramiteDTO>(), new ArrayList<DycaOrigenTramiteDTO>());
            tipoServicioList = new ArrayList<DyccTipoServicioDTO>();
        }
    }

    public void buscarTramiteXServicio() {

        List<DycaOrigenTramiteDTO> tramitesSource = new ArrayList<DycaOrigenTramiteDTO>();
        tipoServicioList = new ArrayList<DyccTipoServicioDTO>();
        try {
            if (servicioSelect != null) {
                String[] ids = servicioSelect.split(",");
                DyccTipoServicioDTO dyccTipoServicioDTO;
                for (String id : ids) {
                    dyccTipoServicioDTO = new DyccTipoServicioDTO();
                    dyccTipoServicioDTO.setIdTipoServicio(Integer.valueOf(id));
                    tipoServicioList.add(dyccTipoServicioDTO);
                    tramitesSource.addAll(dycaOrigenTramite.selectXIdServicio(dyccTipoServicioDTO.getIdTipoServicio()));
                }
            }
        } catch (SIATException e) {
            LOG.error("buscarTramiteXServicio::", e);
        }
        infoARequerir = new DualListModel<DycaOrigenTramiteDTO>(tramitesSource, new ArrayList<DycaOrigenTramiteDTO>());
    }

    public void actualizarAsigarTramites() {
        try {
            dycaTipoServicioAsignarService.insertar(selectDictaminador.getNumEmpleado(), montoSelect, tipoServicioList, infoARequerir.getTarget());
            setDictaminador(selectDictaminador);
            modificaDictaminador();
        } catch (SIATException ex) {
            LOG.error("actualizarAsigarTramites::", ex);
        }
    }

    public void guardarAsigarTramites() {
        try {
            dycaTipoServicioAsignarService.insertar(Integer.parseInt(empleado.getNumEmpleado()), montoSelect, tipoServicioList, infoARequerir.getTarget());
        } catch (SIATException ex) {
            LOG.error("guardarAsigarTramites::", ex);
        }
    }

    public void ejecutaAccion() {
        if (null != frm.getFrmMensaje()) {
            frm.getFrmMensaje().setLength(ConstantesDyC1.CERO);
        }
        switch (frm.getIfrmAccion()) {
            case (int) ConstatesMsgDictaminadores.OPE_MD_BUSQUEDA:
                // >>> CONSULTA EMPLEADO " + this_getEmpleado[]getNumEmpleado[]
                consultaEmpleado();
                break;
            case (int) ConstatesMsgDictaminadores.OPE_MD_ADD:
                // >>> AGREGAR EMPLEADO A DICTAMINADOR
                agregaEmpleado();
                guardarAsigarTramites();
                break;
            case (int) ConstatesMsgDictaminadores.OPE_MD_CONSULTA:
                // >>> CONSULTA DICTAMINADOR
                break;
            case (int) ConstatesMsgDictaminadores.OPE_MD_MOD:
                // >>> MODIFICA DICTAMINADOR
                modificaDictaminador();
                break;
            default:
                // CASE DEFAULT
                break;
        }
    }

    public void frmSelectBuscar() {
        LOG.info(frm.getEmpleado());
        if (!(frm.getEmpleado().equals("No. Empleado"))) {
            frm.setInpEmpleado(Boolean.FALSE);
        } else {
            frm.setInpEmpleado(Boolean.TRUE);
        }
    }

    public void frmAccionConsultar() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (null != this.selectDictaminador) {
            setDictaminador(this.selectDictaminador);
            frm.setIfrmAccion(ConstatesMsgDictaminadores.OPE_MD_CONSULTA);
            frm.setDlgVerBusquedaEmp(Boolean.FALSE);
            frm.setDlgVerAgregaEmp(Boolean.FALSE);
            frm.setDlgVerConsultaDict(Boolean.TRUE);
            frm.setDlgVerModificaDict(Boolean.FALSE);
            frm.setDlgVerComision(Boolean.FALSE);
            if (this.getDictaminador().getActivo().equals(ConstantesDyCNumerico.VALOR_1)) {
                frm.setDlgComisionado(Boolean.TRUE);
            } else {
                frm.setDlgComisionado(Boolean.FALSE);
            }
            frm.setDlgTitulo(ConstatesMsgDictaminadores.CU_MD_CONSULTAR);
            this.consultaDictaminador();
            consultarTramiteAsignado(selectDictaminador.getNumEmpleado());
        } else {
            this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstatesMsgDictaminadores.CU_MD_SELECCION);
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.execute("dlgDetalleMD.hide()");
        }
    }

    private void consultarTramiteAsignado(Integer numEmpleado) {
        try {
            montoAsignado = new DyccMontoDTO();
            montoSelect = null;
            servicioAsignado = new ItemComboBean();
            servicioSelect = null;
            tipoTramiteList = new ArrayList<DyccTipoTramiteDTO>();

            List<DycaTipoServicioAsignarDTO> servicioAsignarList = dycaTipoServicioAsignarService.obtenerTramXDictaminador(numEmpleado, true);
            Integer idMontoAsig = 0;
            String idServicioAsig = "";
            StringBuilder idServAsigTmp = new StringBuilder();
            List<DycaConfigDictaminadorDTO> configDictaminadorList = new ArrayList<DycaConfigDictaminadorDTO>();
            for (int i = 0; i < servicioAsignarList.size(); i++) {
                idServAsigTmp.append(servicioAsignarList.get(i).getIdTipoServicio().toString()).append((i < (servicioAsignarList.size() - 1) ? "," : ""));
                idMontoAsig = servicioAsignarList.get(i).getIdMonto();
                configDictaminadorList.addAll(dycaConfigDictaminadorService.obtenerConfigDicXServicio(servicioAsignarList.get(i).getIdServicioAsignar(), true));
            }
            idServicioAsig = idServAsigTmp.toString();

            for (DyccMontoDTO monto : listaMontos) {
                if (monto.getIdMonto().equals(idMontoAsig)) {
                    montoAsignado = monto;
                    montoSelect = monto.getIdMonto();
                    break;
                }
            }

            for (ItemComboBean servicio : tiposServicio) {
                if (servicio.getIdStr().equals(idServicioAsig)) {
                    servicioAsignado = servicio;
                    servicioSelect = servicio.getIdStr();
                    break;
                }
            }

            for (DycaConfigDictaminadorDTO dcddto : configDictaminadorList) {
                tipoTramiteList.add(dyccTipoTramiteService.encontrar(dcddto.getIdTipoTramite()));
            }

        } catch (SIATException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void cargaInfoAGS() {
        if (null != selectDictaminador) {
            frm.setDlgEmpAGS(false);
            try {
                empleadoAGS = satAgsEmpleadosMVService.getEmpleadoAGS(selectDictaminador.getNumEmpleado());
            } catch (SIATException ex) {
                LOG.info("Error al obtener datos en AGS del empleado: " + selectDictaminador.getNumEmpleado() + ex);
            }
        }
    }

    public void frmAccionEditar() {
        if (null != this.selectDictaminador) {
            this.setDictaminador(this.selectDictaminador);
            frm.setIfrmAccion(ConstatesMsgDictaminadores.OPE_MD_MOD);
            // RENDER DIALOG *********************************************
            frm.setDlgVerBusquedaEmp(Boolean.FALSE);
            frm.setDlgVerAgregaEmp(Boolean.FALSE);
            frm.setDlgVerConsultaDict(Boolean.FALSE);
            frm.setDlgVerModificaDict(Boolean.TRUE);
            frm.setDlgVerComision(Boolean.FALSE);
            // RENDER DIALOG *********************************************
            frm.setDlgTitulo(ConstatesMsgDictaminadores.CU_MD_MODIFICA);
        } else {
            this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstatesMsgDictaminadores.CU_MD_SELECCION);
            FacesContext.getCurrentInstance().addMessage("No existe el empleado, intente nuevamente", message);
        }
    }

    public void consultaEmpleado() {

        empleado.setClaveAdm(admva.getClaveSir());
        empleado
                = dictaminadorService.consultarInformacionEmpleado(empleado, Integer.valueOf(accesoUsr.getCentroCosto()),
                        frm.isInpEmpleado(), admva.getClaveSir());

        switch (empleado.getValidacionEstado()) {
            case 0:
                mostrarMensajeErrorEmpleadoInexistente();
                break;
            case 1:
                mostrarMensajeErrorDictaminadorActivo();
                break;
            case 2:
                mostrarMenesajeErrorDictaminadorBaja();
                break;
            default:
                prepararAlta();
                break;
        }

    }

    private void prepararAlta() {
        empleado.setCcosto(Integer.valueOf(accesoUsr.getCentroCosto()));
        dictaminador.setNumEmpleado(Integer.parseInt(empleado.getNumEmpleado()));
        dictaminador.setObservaciones("");
        dictaminador.setCcosto(Integer.valueOf(accesoUsr.getCentroCosto()));
        dictaminador.setNombreCompleto(empleado.getNombreCompleto());

        frm.setIfrmAccion(ConstatesMsgDictaminadores.OPE_MD_ADD);
        frm.setDlgVerBusquedaEmp(Boolean.FALSE);
        frm.setDlgVerAgregaEmp(Boolean.TRUE);
        frm.setDlgVerConsultaDict(Boolean.FALSE);
        frm.setDlgVerModificaDict(Boolean.FALSE);
        frm.setDlgVerComision(Boolean.FALSE);
    }

    private void mostrarMensajeErrorEmpleadoInexistente() {
        LOG.info("null == getEmpleado().getNumEmpleado(): ");
        mostrarMensajeError("El empleado que ingresó no existe, ingresa el dato nuevamente", "msgDialiog");
    }

    private void mostrarMensajeError(String mensaje, String destino) {
        FacesContext.getCurrentInstance().addMessage(destino,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensaje));
    }

    private void mostrarMensajeErrorDictaminadorActivo() {
        mostrarMensajeError(getMensajeErrorDictaminadorActivo(), "msgDialiog");
    }

    private String getMensajeErrorDictaminadorActivo() {
        return String.format(MENSAJE_ERROR_DICTAMINADOR_ACTIVO, empleado.getNombreCompleto(), empleado.getCentroCostoDescr254());
    }

    private void mostrarMenesajeErrorDictaminadorBaja() {
        mostrarMensajeError(ConstantesDyC3.MENSAJE_MSG019, "msgDialiog");
    }

    public void modificaDictaminador() {
        if (dictaminador.getNumEmpleado() != ConstantesDyCNumerico.VALOR_0) {
            try {
                this.dictaminadorService.actualizarDictaminador(dictaminador);
                this.remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_EMPLEADO, dictaminador.getNombreCompleto());
                this.remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, this.admva.getNombre());
                this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                this.frm.setFrmMensaje(new StringBuilder(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_119,
                        ConstantesDyCNumerico.VALOR_1,
                        ConstantesDyCNumerico.VALOR_14)));
                this.message
                        = new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.dictaminadorService.obtenerMensaje()
                                + this.frm.getFrmMensaje().toString());
                this.verListaDictaminadores();
            } catch (Exception e) {
                this.message
                        = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstantesDyC1.TEXTO_ERROR + e.getMessage()
                                + this.frm.getFrmMensaje().toString());
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void agregaEmpleado() {
        try {

            if (null == this.empleado.getDictaminador()) {
                this.empleado.setDictaminador(new DictaminadorVO());
                this.empleado.getDictaminador().setObservaciones(this.getDictaminador().getObservaciones());
            }
            DyccAprobadorDTO aprobador
                    = aprobadorService.encontrarActivo(Integer.parseInt(this.empleado.getNumEmpleado()));
            if (aprobador != null && aprobador.getActivo() == ConstantesDyCNumerico.VALOR_1) {

                AdmcUnidadAdmvaDTO admvaAprobador = new AdmcUnidadAdmvaDTO();
                admvaAprobador.setClaveSir(aprobador.getClaveAdm());
                admvaAprobador = admcUnidadAdmvaService.consultarUnidadAdmvaList(admvaAprobador).get(0);
                remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, admvaAprobador.getNombre());
                registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                frm.getFrmMensaje().append(registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_83,
                        ConstantesDyCNumerico.VALOR_1,
                        ConstantesDyCNumerico.VALOR_98));
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.frm.getFrmMensaje().toString());
            } else {
                DyccDictaminadorDTO dictaminadores
                        = dictaminadorService.encontrarActivo(Integer.parseInt(this.empleado.getNumEmpleado()));
                if (dictaminadores != null && dictaminadores.getActivo() == ConstantesDyCNumerico.VALOR_1) {

                    AdmcUnidadAdmvaDTO admvaDictaminador = new AdmcUnidadAdmvaDTO();
                    admvaDictaminador.setClaveSir(dictaminadores.getClaveAdm());
                    admvaDictaminador = admcUnidadAdmvaService.consultarUnidadAdmvaList(admvaDictaminador).get(0);
                    this.remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, admvaDictaminador.getNombre());
                    this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                    this.frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_48,
                            ConstantesDyCNumerico.VALOR_1,
                            ConstantesDyCNumerico.VALOR_14));
                    this.message
                            = new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.frm.getFrmMensaje().toString());
                } else if (dictaminadores != null && dictaminadores.getActivo() == ConstantesDyCNumerico.VALOR_0) {
                    this.empleado.getDictaminador().setActivo(1);
                    this.empleado.getDictaminador().setClaveDepto(dictaminadores.getClaveDepto());
                    this.empleado.getDictaminador().setCargaTrabajo(dictaminadores.getCargaTrabajo());
                    this.empleado.getDictaminador().setCentroCosto(Integer.parseInt(admva.getClaveAgrs()));
                    this.empleado.getDictaminador().setClaveAdm(admva.getClaveSir());
                    this.empleado.getDictaminador().setNumEmpleado(Integer.parseInt(this.empleado.getNumEmpleado()));

                    this.dictaminadorService.actualizarDictaminador(this.empleado.getDictaminador());

                    this.remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_EMPLEADO, this.empleado.getNombreCompleto());
                    this.remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, this.admva.getNombre());

                    this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                    this.frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_49,
                            ConstantesDyCNumerico.VALOR_1,
                            ConstantesDyCNumerico.VALOR_14));

                    this.message
                            = new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.frm.getFrmMensaje().toString());
                } else {
                    this.empleado.setClaveAdm(admva.getClaveSir());
                    this.empleado.getDictaminador().setCentroCosto(Integer.parseInt(admva.getClaveAgrs()));
                    /**
                     * Para empleados de hidrocarburos se verifica si la
                     * CLAVEDEPTO es nula
                     */
                    if (this.empleado.getClaveDepto() == null) {
                        empleado.setClaveDepto("0000000000");
                        LOG.info("El empleado " + empleado.getNumEmpleado()
                                + " no tiene Clave de Departmento. Se asigna 0000000000.");
                    }
                    this.dictaminadorService.insertarDictaminador(this.empleado);

                    this.remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_EMPLEADO, this.empleado.getNombreCompleto());
                    this.remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, this.admva.getNombre());
                    this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                    this.frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_49,
                            ConstantesDyCNumerico.VALOR_1,
                            ConstantesDyCNumerico.VALOR_14));
                    this.message
                            = new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.frm.getFrmMensaje().toString());
                }
                this.verListaDictaminadores();
            }

        } catch (Exception e) {
            this.message
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstantesDyC1.TEXTO_ERROR + e.getMessage()
                            + this.frm.getFrmMensaje().toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void consultaDictaminador() {
        // Se ha consultado la información asociada en la Matriz de Dictaminadores del Dictaminador <No Empleado> y Nombre: <Nombre>
        remplaceMensajes.put(ConstantesDyC1.DB_NUM_EMPLEADO, this.dictaminador.getNumEmpleado().toString());
        remplaceMensajes.put(ConstantesDyC1.MD_NOMBRE_DICT, this.dictaminador.getNombreCompleto());
        dictaminadorService.consultaDictaminadorPA(remplaceMensajes);
    }

    public void mensajeBaja() {
        if (this.selectDictaminador != null) {
            setDictaminador(this.selectDictaminador);
            frm.setConfTitulo(frm.getStrOperElimina());
            // Realmente, ¿está seguro de eliminar el dictaminador <Nombre dictaminador>?.
            remplaceMensajes.put(ConstantesDyC1.MD_NOMBRE_DICT, this.getDictaminador().getNombreCompleto());
            registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
            frm.setConfMensaje(this.registroPistasAuditoria.obtenerMensaje(ConstatesMsgDictaminadores.MSG_MD_CFR_ELIM,
                    ConstantesDyC1.UNO,
                    ConstatesMsgDictaminadores.CU_MATRIZ_DICTAMINADOR));
        } else {
            this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstatesMsgDictaminadores.CU_MD_SELECCION);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void frmAccionBaja() {
        if (null != frm.getFrmMensaje()) {
            this.frm.getFrmMensaje().setLength(ConstantesDyC1.CERO);
        }
        if (null != this.selectDictaminador) {
            DyccDictaminadorDTO dic = new DyccDictaminadorDTO();
            dic.setNumEmpleado(this.dictaminador.getNumEmpleado());
            if (this.dictaminadorService.inactivarComisionarRNFDC615(dic, false)) {
                dic = dictaminadorService.encontrarActivo(this.selectDictaminador.getNumEmpleado());
                if (this.selectDictaminador.getClaveDepto() == null) {
                    this.selectDictaminador.setClaveDepto(dic.getClaveDepto());
                }
                this.setDictaminador(this.selectDictaminador);
                try {
                    this.getDictaminadorService().eliminarDictaminador(this.getDictaminador());

                    this.remplaceMensajes.put(ConstantesDyC1.MD_NOMBRE_DICT,
                            this.getDictaminador().getNombreCompleto());
                    this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                    this.frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_118,
                            ConstantesDyCNumerico.VALOR_3,
                            ConstantesDyCNumerico.VALOR_14));
                    this.message
                            = new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.frm.getFrmMensaje().toString());
                    this.verListaDictaminadores();
                    setSelectDictaminador(null);

                } catch (Exception e) {
                    this.message
                            = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstantesDyC1.TEXTO_ERROR + e.getMessage());
                    LOG.info(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
                }
            } else {
                this.remplaceMensajes.put(ConstantesDyC1.MD_NOMBRE_DICT, this.getDictaminador().getNombreCompleto());
                this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                this.frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_54,
                        ConstantesDyCNumerico.VALOR_1,
                        ConstantesDyCNumerico.VALOR_14));

                this.message
                        = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, this.frm.getFrmMensaje().toString());
                RequestContext.getCurrentInstance().execute("dlgDetalleMD.hide()");
            }
        }
        /**
         * else { this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
         * null, ConstantesDyC1.CU_MD_SELECCION); }
         */
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Recupera las variables de session y en su caso los envia al service.
     */
    public void obtenerSession() {
        try {
            this.accesoUsr = serviceObtenerSesion.execute();
            LOG.debug("accesoUsr.getClaveAdminCentral() ->" + accesoUsr.getClaveAdminCentral());
            if (null != accesoUsr.getClaveAdminCentral()) {
                this.admva = new AdmcUnidadAdmvaDTO();
                this.admva.setClaveSir(Integer.parseInt(this.accesoUsr.getClaveSir()));
                this.admva
                        = this.admcUnidadAdmvaService.consultarUnidadAdmvaList(admva).get(ConstantesDyCNumerico.VALOR_0);
            } else {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesDyC1.PAGINA_ERROR);
                } catch (IOException ioe) {
                    LOG.error("error al redireccionar a la pagina de error :$ mensaje ->" + ioe.getMessage());
                }
            }
        } catch (Exception e) {
            LOG.info(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesDyC1.PAGINA_ERROR);
            } catch (IOException ioe) {
                LOG.info(ConstantesDyC1.TEXTO_ERROR + ioe.getMessage());
            }
        }
    }

    public void setEmpleado(EmpleadoVO empleado) {
        this.empleado = empleado;
    }

    public EmpleadoVO getEmpleado() {
        return empleado;
    }

    public void setAdmva(AdmcUnidadAdmvaDTO admva) {
        this.admva = admva;
    }

    public AdmcUnidadAdmvaDTO getAdmva() {
        return admva;
    }

    public void setMessage(FacesMessage message) {
        this.message = message;
    }

    public FacesMessage getMessage() {
        return message;
    }

    public void setTblListaAdministracion(List<AdmcUnidadAdmvaDTO> tblListaAdministracion) {
        this.tblListaAdministracion = tblListaAdministracion;
    }

    public List<AdmcUnidadAdmvaDTO> getTblListaAdministracion() {
        return tblListaAdministracion;
    }

    public void setSelectAdmva(AdmcUnidadAdmvaDTO selectAdmva) {
        this.selectAdmva = selectAdmva;
    }

    public AdmcUnidadAdmvaDTO getSelectAdmva() {
        return selectAdmva;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setFrm(FrmMatrizDictaminadorVO frm) {
        this.frm = frm;
    }

    public FrmMatrizDictaminadorVO getFrm() {
        return frm;
    }

    public void setRemplaceMensajes(Map<String, String> remplaceMensajes) {
        this.remplaceMensajes = remplaceMensajes;
    }

    public Map<String, String> getRemplaceMensajes() {
        return remplaceMensajes;
    }

    public void setDictaminadorService(DictaminadorService dictaminadorService) {
        this.dictaminadorService = dictaminadorService;
    }

    public DictaminadorService getDictaminadorService() {
        return dictaminadorService;
    }

    public void setDictaminador(DictaminadorVO dictaminador) {
        this.dictaminador = dictaminador;
    }

    public DictaminadorVO getDictaminador() {
        return dictaminador;
    }

    public void setSelectDictaminador(DictaminadorVO selectDictaminador) {
        this.selectDictaminador = selectDictaminador;
    }

    public DictaminadorVO getSelectDictaminador() {
        return selectDictaminador;
    }

    public void setTblListaDictaminadores(List<DictaminadorVO> tblListaDictaminadores) {
        this.tblListaDictaminadores = tblListaDictaminadores;
    }

    public List<DictaminadorVO> getTblListaDictaminadores() {
        return tblListaDictaminadores;
    }

    public void setRegistroPistasAuditoria(RegistroPistasAuditoria registroPistasAuditoria) {
        this.registroPistasAuditoria = registroPistasAuditoria;
    }

    public RegistroPistasAuditoria getRegistroPistasAuditoria() {
        return registroPistasAuditoria;
    }

    public void setAdmcUnidadAdmvaService(AdmcUnidadAdmvaService admcUnidadAdmvaService) {
        this.admcUnidadAdmvaService = admcUnidadAdmvaService;
    }

    public AdmcUnidadAdmvaService getAdmcUnidadAdmvaService() {
        return admcUnidadAdmvaService;
    }

    public SatAgsEmpleadosMVService getSatAgsEmpleadosMVService() {
        return satAgsEmpleadosMVService;
    }

    public void setSatAgsEmpleadosMVService(SatAgsEmpleadosMVService satAgsEmpleadosMVService) {
        this.satAgsEmpleadosMVService = satAgsEmpleadosMVService;
    }

    public void setExpRFC(String expRFC) {
        this.expRFC = expRFC;
    }

    public String getExpRFC() {
        return expRFC;
    }

    public void setAprobadorService(AprobadorService aprobadorService) {
        this.aprobadorService = aprobadorService;
    }

    public AprobadorService getAprobadorService() {
        return aprobadorService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public SatAgsEmpleadosMVDTO getEmpleadoAGS() {
        return empleadoAGS;
    }

    public void setEmpleadoAGS(SatAgsEmpleadosMVDTO empleadoAGS) {
        this.empleadoAGS = empleadoAGS;
    }

    public DualListModel<DycaOrigenTramiteDTO> getInfoARequerir() {
        return infoARequerir;
    }

    public void setInfoARequerir(DualListModel<DycaOrigenTramiteDTO> infoARequerir) {
        this.infoARequerir = infoARequerir;
    }

    public List<ItemComboBean> getTiposServicio() {
        return tiposServicio;
    }

    public void setTiposServicio(List<ItemComboBean> tiposServicio) {
        this.tiposServicio = tiposServicio;
    }

    public String getServicioSelect() {
        return servicioSelect;
    }

    public void setServicioSelect(String servicioSelect) {
        this.servicioSelect = servicioSelect;
    }

    public void setDycaOrigenTramite(DycaOrigenTramiteService dycaOrigenTramite) {
        this.dycaOrigenTramite = dycaOrigenTramite;
    }

    public DyccMontoService getDyccMontoService() {
        return dyccMontoService;
    }

    public void setDyccMontoService(DyccMontoService dyccMontoService) {
        this.dyccMontoService = dyccMontoService;
    }

    public List<DyccMontoDTO> getListaMontos() {
        return listaMontos;
    }

    public void setListaMontos(List<DyccMontoDTO> listaMontos) {
        this.listaMontos = listaMontos;
    }

    public Integer getMontoSelect() {
        return montoSelect;
    }

    public void setMontoSelect(Integer montoSelect) {
        this.montoSelect = montoSelect;
    }

    public void setDycaTipoServicioAsignarService(DycaTipoServicioAsignarService dycaTipoServicioAsignarService) {
        this.dycaTipoServicioAsignarService = dycaTipoServicioAsignarService;
    }

    public DyccMontoDTO getMontoAsignado() {
        return montoAsignado;
    }

    public void setMontoAsignado(DyccMontoDTO montoAsignado) {
        this.montoAsignado = montoAsignado;
    }

    public ItemComboBean getServicioAsignado() {
        return servicioAsignado;
    }

    public void setServicioAsignado(ItemComboBean servicioAsignado) {
        this.servicioAsignado = servicioAsignado;
    }

    public void setDycaConfigDictaminadorService(DycaConfigDictaminadorService dycaConfigDictaminadorService) {
        this.dycaConfigDictaminadorService = dycaConfigDictaminadorService;
    }

    public void setDyccTipoTramiteService(DyccTipoTramiteService dyccTipoTramiteService) {
        this.dyccTipoTramiteService = dyccTipoTramiteService;
    }

    public List<DyccTipoTramiteDTO> getTipoTramiteList() {
        return tipoTramiteList;
    }

    public void setTipoTramiteList(List<DyccTipoTramiteDTO> tipoTramiteList) {
        this.tipoTramiteList = tipoTramiteList;
    }

}
