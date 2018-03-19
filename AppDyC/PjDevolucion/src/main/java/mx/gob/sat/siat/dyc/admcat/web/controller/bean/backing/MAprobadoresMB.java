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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.dyc.admcat.dto.matrizaprobador.FrmMAprobadorVO;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;
import mx.gob.sat.siat.dyc.admcat.service.matrizaprobador.AprobadorService;
import mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.DictaminadorService;
import mx.gob.sat.siat.dyc.domain.ags.SatAgsEmpleadosMVDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.generico.util.UtilsValidaSesion;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesExpresionesRegulares;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesMatrizAprobadores;
import mx.gob.sat.siat.dyc.util.constante2.ConstatesMsgDictaminadores;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.dyc.vo.AprobadorVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;


/**
 * Clase ManagedBean para caso de uso <b>Mantener Matriz Aprobadores</b>, tabla <b>[DYCC_APROBADOR]</b>.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Octubre 14, 2013
 * @version 0.1.1
 *
 * */
@ManagedBean(name = "mAprobadoresMB")
@ViewScoped
public class MAprobadoresMB {

    private static final Logger LOG = Logger.getLogger(MAprobadoresMB.class);

    @ManagedProperty(value = "#{aprobadorService}")
    private AprobadorService aprobadorService;

    @ManagedProperty(value = "#{dictaminadorService}")
    private DictaminadorService dictaminadorService;

    @ManagedProperty(value = "#{admcUnidadAdmvaService}")
    private AdmcUnidadAdmvaService admcUnidadAdmvaService;
    
    @ManagedProperty(value = "#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService satAgsEmpleadosMVService;

    @ManagedProperty("#{registroPistasAuditoria}")
    private RegistroPistasAuditoria registroPistasAuditoria;
    
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private List<AdmcUnidadAdmvaDTO> tblListaAdmin;
    private AdmcUnidadAdmvaDTO admva;
    private AdmcUnidadAdmvaDTO selectAdmin;

    private AdmcUnidadAdmvaDTO admvaGral;
    private AdmcUnidadAdmvaDTO adm;
    private AdmcUnidadAdmvaDTO admLocal;

    private List<AprobadorVO> listaAprobador;
    private List<EmpleadoVO> listaNivel;
    private AprobadorVO maprobador;
    private AprobadorVO selectAprobador;
    private EmpleadoVO empleado;
    private SatAgsEmpleadosMVDTO empleadoAGS;

    private FacesMessage message;
    private AccesoUsr accesoUsr;
    private AccesoProceso accesoProceso;
    private FrmMAprobadorVO frm;

    private Map<String, String> remplaceMensajes;
    private Boolean banderamMsj = Boolean.FALSE;
    
    private String valRFC = ConstantesExpresionesRegulares.EXP_REG_RFC;
    
    private static final String MENSAJE_ERROR_APROBADOR_ACTIVO = "El empleado %s se encuentra activo como aprobador en la administración %s, favor de verificar el dato o solicitar la inactivación en la citada Administración para ingresarlo nuevamente";

    @PostConstruct
    public void init() {
        frm = new FrmMAprobadorVO();
        frm.setFrmMensaje(new StringBuilder());
        frm.setAdmCentral("");

        accesoUsr = new AccesoUsr();
        remplaceMensajes = new HashMap<String, String>();
        adm = new AdmcUnidadAdmvaDTO();
        admLocal = new AdmcUnidadAdmvaDTO();
        empleado = new EmpleadoVO();


        this.obtenerSession();
        UtilsValidaSesion.validarSesion(accesoUsr, Procesos.DYC00008);

        try {
            admvaGral = admcUnidadAdmvaService.consultarUnidadAdmvaCentral(admva).get(ConstantesDyC1.CERO);
            frm.setAdmCentral(admva.getClaveSir() + " - " + admva.getNombre());
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + " consultarUnidadAdmvaCentral; mensaje ->" + e.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(admva));
            ManejadorLogException.getTraceError(e);
        }

        if (getAdmva().getClaveSir() == ConstantesIds.ClavesAdministraciones.CENTRAL_DYC) {
            adm.setIdUnidadmpadre(getAdmva().getIdUnidadmpadre());
            this.verAdministraciones(adm);

            frm.setPanelAdmin(Boolean.TRUE);
            frm.setPanelAprobador(Boolean.FALSE);
            frm.setTituloPagina(ConstantesMatrizAprobadores.CU_MA_NOMBRE);
            frm.setDlgVerBusquedaEmp(Boolean.FALSE);

            banderamMsj = Boolean.TRUE;

        } else {
            selectAdmin = new AdmcUnidadAdmvaDTO();

            adm.setClaveSir(getAdmva().getClaveSir());
            adm.setIdUnidadmpadre(getAdmva().getIdUnidadmpadre());
            selectAdmin.setClaveSir(getAdmva().getClaveSir());
            selectAdmin.setIdUnidadmpadre(getAdmva().getIdUnidadmpadre());
            selectAdmin.setNomAbreviado(getAdmva().getNomAbreviado());
            selectAdmin.setNombre(getAdmva().getNombre());

            frm.setTituloPagina(selectAdmin.getNombre());
            frm.setAdmCentral(selectAdmin.getClaveSir() +" - "+ selectAdmin.getNombre());
            frm.setPanelAdmin(Boolean.FALSE);
            frm.setPanelAprobador(Boolean.TRUE);
            frm.setDlgVerBusquedaEmp(Boolean.TRUE);

            banderamMsj = Boolean.FALSE;
            this.vistaPanelAprobador();
        }
        try {
            listaNivel = aprobadorService.obtenerListaNivelDireccion();
        } catch (Exception e) {
            LOG.error("\nError" + e.getMessage());
        }
    }

    public void verAdministraciones(AdmcUnidadAdmvaDTO admin) {
        try {
            setTblListaAdmin(getAdmcUnidadAdmvaService().consultarUnidadAdmvaList(admin));
            getFrm().setNumResultados(getTblListaAdmin().size());
            getFrm().setRowsPaginador(ConstantesDyC1.NO_COLS_PAGINA);
            getFrm().setPaginador(ConstantesDyC1.NO_COLS_PAGINA < getTblListaAdmin().size() ? Boolean.TRUE : Boolean.FALSE);
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage(), e);
            getTblListaAdmin().clear();
            frm.getFrmMensaje().append(e.getMessage());
        }
    }

    public void verAprobadores() {
        try {   
            maprobador = new AprobadorVO();
            maprobador.setClaveSir(selectAdmin.getClaveSir());
            maprobador.setClaveAdm(selectAdmin.getClaveSir());

            this.setListaAprobador(aprobadorService.obtenerAprobadores(maprobador, Boolean.TRUE));
            frm.setNumResulApd(getListaAprobador().size());
            frm.setRowsPagAprd(ConstantesDyC1.NO_COLS_PAGINA);
            frm.setPagAprd((ConstantesDyC1.NO_COLS_PAGINA < getListaAprobador().size() ? Boolean.TRUE : Boolean.FALSE));
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage(), e);
            frm.getFrmMensaje().append(e.getMessage());
        }
    }

    public void vistaPanelAdministracion() {
        banderamMsj = Boolean.TRUE;
        frm.setPanelAdmin(Boolean.TRUE);
        frm.setPanelAprobador(Boolean.FALSE);
        frm.setAdmCentral(admvaGral.getNomAbreviado() + ConstantesMatrizAprobadores.MA_SIGNO + getAdmva().getNomAbreviado());
        adm.setIdUnidadmpadre(getAdmva().getIdUnidadmpadre());
        this.verAdministraciones(adm);
    }

    public void vistaPanelAprobador() {
        banderamMsj = Boolean.FALSE;
        if (LOG.isInfoEnabled()) {
            LOG.info("\n>>> VER PANEL APROBADORES  " + ExtractorUtil.ejecutar(selectAdmin));
        }
        if (null != selectAdmin) {
            admLocal = selectAdmin;
            frm.setPanelAdmin(Boolean.FALSE);
            frm.setPanelAprobador(Boolean.TRUE);
            frm.setAdmCentral(selectAdmin.getClaveSir() +" - "+ selectAdmin.getNombre());
            this.verAprobadores();
        }
    }

    public void ejecutaAccion() {
        if (LOG.isInfoEnabled()) {
            LOG.info(ConstantesDyC1.SALTO_LINEA_1 + ">>> EJECUTA ACCION " + getFrm().getIfrmAccion());
        }
        frm.getFrmMensaje().setLength(ConstantesDyC1.CERO);
        switch (getFrm().getIfrmAccion()) {
        case ConstantesMatrizAprobadores.OPE_MA_BUSCA:
            /** CONSULTA EMPLEADO ************  this . getEmpleado() . getIdEmpleado() );*/
            this.consultaEmpleado();
            break;
        case ConstantesMatrizAprobadores.OPE_MA_ADICIONA:
            LOG.info(ConstantesDyC1.SALTO_LINEA_1 + ">>> ADICIONAR APROBADOR ************ " +
                     ConstantesDyC1.SALTO_LINEA_1 + ">>> APROBADOR " + this.getMaprobador() +
                     ConstantesDyC1.SALTO_LINEA_1 + "\n>>> EMPLEADO " + this.getEmpleado());
            this.adicionarAprobador();
            break;
        case ConstantesMatrizAprobadores.OPE_MA_CONSULTA:
            LOG.info("\nCONSULTA APROBADOR");
            break;
        case ConstantesMatrizAprobadores.OPE_MA_MODIFICA:
            LOG.info(ConstantesDyC1.SALTO_LINEA_1 + "MODIFICAR APROBADOR");
            if (this.selectAprobador.getNumEmpleado() != ConstantesDyC1.CERO) {
                try {
                    this.aprobadorService.actualizarAprobador(this.selectAprobador);

                    //REGISTRO PISTAS AUDITORIA
                    this.remplaceMensajes.put(ConstantesDyC1.DB_NUM_EMPLEADO,
                                              this.selectAprobador.getNumEmpleado().toString());
                    this.remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, this.selectAprobador.getNombreCompleto());
                    this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                    this.frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesMatrizAprobadores.MSG_MA_16,
                                                                                                ConstantesDyC1.TRES,
                                                                                                ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR));
                    
                    this.message =
                            new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.frm.getFrmMensaje().toString());

                    this.vistaPanelAprobador();
                } catch (Exception e) {
                    this.message =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstantesDyC1.TEXTO_ERROR + e.getMessage() +
                                             this.frm.getFrmMensaje().toString());
                }
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            break;
        default:
            LOG.info(">>> Default case ");
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

    public void frmBuscarEmpleado() {
        empleado = new EmpleadoVO();
        // DEFAULT SELECCION
        frm.setEmpleado("No. Empleado");
        frm.setInpEmpleado(Boolean.TRUE);
        frm.setDlgVerBusquedaEmp(Boolean.TRUE);
        frm.setDlgVerAgregaEmp(Boolean.FALSE);
        frm.setDlgVerConsultaAprd(Boolean.FALSE);
        frm.setDlgVerModificaAprd(Boolean.FALSE);
        frm.setIfrmAccion(ConstantesMatrizAprobadores.OPE_MA_BUSCA);
        frm.setDlgTitulo(ConstantesMatrizAprobadores.CU_MA_CONSULTAR);
        frm.setDlgEmpAGS(Boolean.FALSE);
    }

    public void frmAccionConsultar() {
        if (null != this.selectAprobador) {
            this.setMaprobador(this.selectAprobador);
            admLocal.setNombre(selectAprobador.getDescCentroConsto());
            frm.setIfrmAccion(ConstantesMatrizAprobadores.OPE_MA_CONSULTA);
            frm.setDlgVerBusquedaEmp(Boolean.FALSE);
            frm.setDlgVerAgregaEmp(Boolean.FALSE);
            frm.setDlgVerConsultaAprd(Boolean.TRUE);
            frm.setDlgVerModificaAprd(Boolean.FALSE);
            frm.setCentroCosto(selectAprobador.getCentroCosto());
            empleado.setCcosto(selectAprobador.getCentroCosto());

            for (EmpleadoVO e : this.listaNivel) {
                if (e.getClaveNivel().equals(maprobador.getClaveNivel())) {
                    maprobador.setDescClaveNivel(e.getDescClaveNivel());
                    break;
                }
            }

            frm.setDlgTitulo(ConstantesMatrizAprobadores.CU_MA_CONSULTAR);

            // Se ha consultado la información asociada en la Matriz de Aprobadores del Aprobador <numero de empleado> y Nombre: <Nombre>.
            remplaceMensajes.put(ConstantesDyC1.DB_NUM_EMPLEADO, maprobador.getNumEmpleado().toString());
            remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, maprobador.getNombreCompleto());
            aprobadorService.consultaAprobadorPA(remplaceMensajes);

        } else {
            this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstatesMsgDictaminadores.CU_MD_SELECCION);
            FacesContext.getCurrentInstance().addMessage(null, message);

            RequestContext.getCurrentInstance().execute("dlgDetalleMD.hide()");
        }
    }

    public void frmAccionEditar() {
        if (LOG.isInfoEnabled()) {
            LOG.info("\n>>> FORM MODIFICAR APROBADOR " + this.selectAprobador);
        }
        if (null != this.selectAprobador) {
            maprobador = this.selectAprobador;
            frm.setIfrmAccion(ConstantesMatrizAprobadores.OPE_MA_MODIFICA);
            frm.setDlgVerBusquedaEmp(Boolean.FALSE);
            frm.setDlgVerAgregaEmp(Boolean.FALSE);
            frm.setDlgVerConsultaAprd(Boolean.FALSE);
            frm.setDlgVerModificaAprd(Boolean.TRUE);
            /* RENDER DIALOG ********************************************* */
            frm.setDlgTitulo(ConstantesMatrizAprobadores.CU_MA_MODIFICAR);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstatesMsgDictaminadores.CU_MD_SELECCION);
            FacesContext.getCurrentInstance().addMessage("No existe el empleado, intente nuevamente!", message);
            RequestContext.getCurrentInstance().execute("dlgDetalleMD.hide()");
        }
    }

    public void consultaEmpleado() {
        empleado.setClaveAdm( getUnidadAdmvaConsulta() );
        empleado = getInformacionEmpleado();
        
        switch (empleado.getValidacionEstado()) {
        case 0:
            mostrarMensajeErrorEmpleadoInexistente();
            break;
        case 1:
            mostrarMensajeErrorEmpleadoActivo();
            break;
        case 2:
            mostrarMenesajeErrorEmpleadoBaja();
            break;
        default:
            prepararAlta();
            break;
        }
    }
    
    private void mostrarMensajeErrorEmpleadoInexistente() {
        mostrarMensajeError("El empleado que ingresó no existe, ingresa el dato nuevamente", "msgDialiog");
    }

    private void mostrarMensajeError(String mensaje, String destino) {
        FacesContext.getCurrentInstance().addMessage(destino,
                                                     new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensaje));
    }
    
    private void mostrarMensajeErrorEmpleadoActivo() {
        mostrarMensajeError(getMensajeErrorEmpleadoActivo(), "msgDialiog");
    }

    private String getMensajeErrorEmpleadoActivo() {
        return String.format( MENSAJE_ERROR_APROBADOR_ACTIVO, empleado.getNombreCompleto(), empleado.getCentroCostoDescr254() );
    }
    
    private Integer getUnidadAdmvaConsulta (){
        return getAdmva().getClaveSir() == ConstantesDyCNumerico.VALOR_100 ? 
            admLocal.getClaveSir() : 
            adm.getClaveSir();
    }
    
    private EmpleadoVO getInformacionEmpleado (){
        return aprobadorService.consultarInformacionEmpleado(
            empleado, 
            Integer.valueOf(accesoUsr.getCentroCosto()),
            frm.isInpEmpleado(), 
            admva.getClaveSir()
        );
    }
    
    private void mostrarMenesajeErrorEmpleadoBaja() {
        mostrarMensajeError( ConstantesDyC3.MENSAJE_MSG019, "msgDialiog");
    }
    
    private void prepararAlta (){
        if (null == this.empleado.getAprobador()) {
            this.empleado.setAprobador(new AprobadorVO());
        }
        empleado.getAprobador().setNumEmpleado(Integer.parseInt(this.empleado.getNumEmpleado()));
        empleado.getAprobador().setNombreCompleto(this.empleado.getNombreCompleto());
        empleado.getAprobador().setNombrePuesto(this.empleado.getPuesto());
        frm.setIfrmAccion(ConstantesMatrizAprobadores.OPE_MA_ADICIONA);
        admLocal.setClaveAgrs((this.getAdmva().getClaveSir() == ConstantesDyCNumerico.VALOR_100)?admLocal.getClaveAgrs():this.admva.getClaveAgrs());

        frm.setDlgVerBusquedaEmp(Boolean.FALSE);
        frm.setDlgVerAgregaEmp(Boolean.TRUE);
        frm.setDlgVerConsultaAprd(Boolean.FALSE);
        frm.setDlgVerModificaAprd(Boolean.FALSE);
    }

    public void adicionarAprobador() {
        try {
            String nombreAdm = this.admva.getNombre();
            if (this.getAdmva().getClaveSir() == ConstantesDyCNumerico.VALOR_100) {
                nombreAdm = this.selectAdmin.getNombre();
            }

            DyccDictaminadorDTO dictaminador =
                dictaminadorService.encontrarActivo(Integer.parseInt(this.empleado.getNumEmpleado()));
            if (dictaminador != null && dictaminador.getActivo() == ConstantesDyCNumerico.VALOR_1) {
                AdmcUnidadAdmvaDTO admvaDictaminador = new AdmcUnidadAdmvaDTO();
                admvaDictaminador.setClaveSir(dictaminador.getClaveAdm());
                admvaDictaminador = admcUnidadAdmvaService.consultarUnidadAdmvaList(admvaDictaminador).get(0);
                remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, admvaDictaminador.getNombre());
                registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesIds.MSG_48,
                                                                                       ConstantesDyC1.UNO,
                                                                                       ConstantesDyCNumerico.VALOR_14));
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, frm.getFrmMensaje().toString());
            } else {
                DyccAprobadorDTO aprobador =
                    aprobadorService.encontrarActivo(Integer.parseInt(this.empleado.getNumEmpleado()));
                if (aprobador != null && aprobador.getActivo() == ConstantesDyCNumerico.VALOR_1) {
                    AdmcUnidadAdmvaDTO admvaAprobador = new AdmcUnidadAdmvaDTO();
                    admvaAprobador.setClaveSir(aprobador.getClaveAdm());
                    admvaAprobador = admcUnidadAdmvaService.consultarUnidadAdmvaList(admvaAprobador).get(0);
                    remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, admvaAprobador.getNombre());
                    registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);

                    frm.getFrmMensaje().append(
                        String.format( 
                            "El empleado %s %s %s se encuentra activo como aprobador en la administración %s, favor de verificar el dato o solicitar la inactivación en la citada Administración para ingresarlo nuevamente"
                            , aprobador.getNombre()
                            , aprobador.getApellidoPaterno()
                            , aprobador.getApellidoPaterno()
                            , dictaminadorService.obtenerNombreUnidadAdmva( aprobador.getClaveAdm() ) 
                        )
                    );
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, frm.getFrmMensaje().toString());
                } else {
                    if (aprobador != null && aprobador.getActivo() == ConstantesDyCNumerico.VALOR_0) {
                        empleado.getAprobador().setActivo(ConstantesDyCNumerico.VALOR_1);
                        empleado.getAprobador().setClaveNivel(empleado.getClaveNivel());
                        empleado.getAprobador().setNumEmpleado(Integer.parseInt(this.empleado.getNumEmpleado()));
                        empleado.getAprobador().setClaveAdm(this.selectAdmin.getClaveSir());
                        empleado.getAprobador().setCentroCosto(Integer.parseInt((this.getAdmva().getClaveSir() == ConstantesDyCNumerico.VALOR_100)?
                                this.selectAdmin.getClaveAgrs():
                                this.getAdmva().getClaveAgrs()));
                        
                        aprobadorService.actualizarAprobador(this.empleado.getAprobador());

                        remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, this.empleado.getNombreCompleto());
                        remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, nombreAdm);
                        registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                        frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesMatrizAprobadores.MSG_MA_3,
                                                                                               ConstantesDyC1.UNO,
                                                                                               ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR));
                        message =
                                new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.frm.getFrmMensaje().toString());
                    } else {
                        /**Para empleados de hidrocarburos se verifica si la CLAVEDEPTO es nula*/
                        if(this.empleado.getClaveDepto() == null){
                            empleado.setClaveDepto("0000000000");
                            LOG.info("El empleado "+empleado.getNumEmpleado()+" no tiene Clave de Departmento. Se asigna 0000000000.");
                        }
                        
                        empleado.setCcosto(Integer.parseInt((this.getAdmva().getClaveSir() == ConstantesDyCNumerico.VALOR_100)?
                                this.selectAdmin.getClaveAgrs():
                                this.getAdmva().getClaveAgrs()));
                        empleado.setClaveAdm(this.selectAdmin.getClaveSir());
                        aprobadorService.insertarAprobador(this.empleado);
                        remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, this.empleado.getNombreCompleto());
                        remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, nombreAdm);
                        registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                        frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesMatrizAprobadores.MSG_MA_3,
                                                                                               ConstantesDyC1.UNO,
                                                                                               ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR));
                        message =
                                new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.frm.getFrmMensaje().toString());
                    }
                    this.vistaPanelAprobador();
                }
            }
        } catch (Exception e) {
            LOG.error("OCURRIO UN ERROR AL DAR DE ALTA AL APROBADOR:"+e);
            this.message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstantesDyC1.TEXTO_ERROR + e.getMessage() +
                                     this.frm.getFrmMensaje().toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mensajeBaja() {
        if (this.selectAprobador != null) {
            this.setMaprobador(this.selectAprobador);
            frm.setConfTitulo(frm.getStrOperElimina());

            // Realmente, ¿está seguro de eliminar el dictaminador <Nombre dictaminador>?.
            remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, this.getMaprobador().getNombreCompleto());
            registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
            frm.setConfMensaje(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_95,
                                                                           ConstantesDyC1.UNO,
                                                                           ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR));
        } else {
            this.message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstatesMsgDictaminadores.CU_MD_SELECCION);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void frmAccionBaja() {
        LOG.info("\n>>> BAJA APROBADOR ");
        frm.getFrmMensaje().setLength(ConstantesDyC1.CERO);
        if (null != this.selectAprobador) {
            DyccAprobadorDTO apro = new DyccAprobadorDTO();
            apro.setNumEmpleado(this.maprobador.getNumEmpleado());
            apro.setNombre(this.maprobador.getNombreCompleto());
            apro.setClaveDepto(this.admva.getNombre());

            if (this.aprobadorService.inactivarComisionarRNFDC615(apro, false)) {
                apro = aprobadorService.encontrarActivo(this.selectAprobador.getNumEmpleado());
                if(this.selectAprobador.getClaveDepto() == null){
                    this.selectAprobador.setClaveDepto(apro.getClaveDepto());
                }
                this.setMaprobador(this.selectAprobador);
                try {
                    this.getAprobadorService().eliminarAprobador(this.getMaprobador());

                    remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, this.getMaprobador().getNombreCompleto());
                    registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                    frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesMatrizAprobadores.MSG_MA_17,
                                                                                           ConstantesDyC1.TRES,
                                                                                           ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR));

                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, null, this.frm.getFrmMensaje().toString());
                    this.setSelectAprobador(null);
                    this.verAprobadores();
                } catch (Exception e) {
                    message =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstantesDyC1.TEXTO_ERROR + e.getMessage());
                    LOG.info(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
                }
            } else {
                remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, this.getMaprobador().getNombreCompleto());
                registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                frm.getFrmMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_90,
                                                                                       ConstantesDyCNumerico.VALOR_1,
                                                                                       ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR));

                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, this.frm.getFrmMensaje().toString());
                RequestContext.getCurrentInstance().execute("dlgDetalleMD.hide()");
            }
        } /**else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ConstantesDyC1.CU_MD_SELECCION);
        }*/

        if(message == null){
            LOG.error("Se entro en un caso en donde el mensaje que se le pretende mostrar al usuario es nulo, se mostrara uno generico.");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Ocurrió un error inesperado en el sistema, por favor " +
                "intentelo de nuevo y en caso de persistir el error comuniquese con el administrador del sistema");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void cargaInfoAGS(){
        if(null != selectAprobador){
            frm.setDlgEmpAGS(false);
            try {
                empleadoAGS = satAgsEmpleadosMVService.getEmpleadoAGS(selectAprobador.getNumEmpleado());
            } catch (SIATException ex) {
                 LOG.info("Error al obtener datos en AGS del empleado: "
                         +selectAprobador.getNumEmpleado()+ ex);
            }
        }
    }

    /**
     * Recupera las variables de session y en su caso los envia al service.
     */
    public void obtenerSession() {
        try {
            accesoUsr = serviceObtenerSesion.execute();
            if (null != accesoUsr.getClaveSir()) {
                admva = new AdmcUnidadAdmvaDTO();
                admva.setClaveSir(Integer.parseInt(accesoUsr.getClaveSir()));
                admva = admcUnidadAdmvaService.consultarUnidadAdmvaList(admva).get(ConstantesDyC1.CERO);
            }
        } catch (Exception e) {
            LOG.error("\nERROR: " + e.getMessage());
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesDyC1.PAGINA_ERROR);
            } catch (IOException ioe) {
                LOG.error("\nERROR: " + ioe.getMessage());
            }
        }
    }

    public void setTblListaAdmin(List<AdmcUnidadAdmvaDTO> tblListaAdmin) {
        this.tblListaAdmin = tblListaAdmin;
    }

    public List<AdmcUnidadAdmvaDTO> getTblListaAdmin() {
        return tblListaAdmin;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAccesoProceso(AccesoProceso accesoProceso) {
        this.accesoProceso = accesoProceso;
    }

    public AccesoProceso getAccesoProceso() {
        return accesoProceso;
    }

    public void setMessage(FacesMessage message) {
        this.message = message;
    }

    public FacesMessage getMessage() {
        return message;
    }

    public void setSelectAdmin(AdmcUnidadAdmvaDTO selectAdmin) {
        this.selectAdmin = selectAdmin;
    }

    public AdmcUnidadAdmvaDTO getSelectAdmin() {
        return selectAdmin;
    }

    public void setAdmva(AdmcUnidadAdmvaDTO admva) {
        this.admva = admva;
    }

    public AdmcUnidadAdmvaDTO getAdmva() {
        return admva;
    }

    public void setAdmLocal(AdmcUnidadAdmvaDTO admLocal) {
        this.admLocal = admLocal;
    }

    public AdmcUnidadAdmvaDTO getAdmLocal() {
        return admLocal;
    }

    public void setEmpleado(EmpleadoVO empleado) {
        this.empleado = empleado;
    }

    public EmpleadoVO getEmpleado() {
        return empleado;
    }

    public void setAdm(AdmcUnidadAdmvaDTO adm) {
        this.adm = adm;
    }

    public AdmcUnidadAdmvaDTO getAdm() {
        return adm;
    }

    public void setSelectAprobador(AprobadorVO selectAprobador) {
        this.selectAprobador = selectAprobador;
    }

    public AprobadorVO getSelectAprobador() {
        return selectAprobador;
    }

    public void setRemplaceMensajes(Map<String, String> remplaceMensajes) {
        this.remplaceMensajes = remplaceMensajes;
    }

    public Map<String, String> getRemplaceMensajes() {
        return remplaceMensajes;
    }

    public void setAdmvaGral(AdmcUnidadAdmvaDTO admvaGral) {
        this.admvaGral = admvaGral;
    }

    public AdmcUnidadAdmvaDTO getAdmvaGral() {
        return admvaGral;
    }

    public void setFrm(FrmMAprobadorVO frm) {
        this.frm = frm;
    }

    public FrmMAprobadorVO getFrm() {
        return frm;
    }

    public void setMaprobador(AprobadorVO maprobador) {
        this.maprobador = maprobador;
    }

    public AprobadorVO getMaprobador() {
        return maprobador;
    }

    public void setListaAprobador(List<AprobadorVO> listaAprobador) {
        this.listaAprobador = listaAprobador;
    }

    public List<AprobadorVO> getListaAprobador() {
        return listaAprobador;
    }

    public void setAprobadorService(AprobadorService aprobadorService) {
        this.aprobadorService = aprobadorService;
    }

    public AprobadorService getAprobadorService() {
        return aprobadorService;
    }

    public void setRegistroPistasAuditoria(RegistroPistasAuditoria registroPistasAuditoria) {
        this.registroPistasAuditoria = registroPistasAuditoria;
    }

    public RegistroPistasAuditoria getRegistroPistasAuditoria() {
        return registroPistasAuditoria;
    }

    public void setListaNivel(List<EmpleadoVO> listaNivel) {
        this.listaNivel = listaNivel;
    }

    public List<EmpleadoVO> getListaNivel() {
        return listaNivel;
    }

    public void setAdmcUnidadAdmvaService(AdmcUnidadAdmvaService admcUnidadAdmvaService) {
        this.admcUnidadAdmvaService = admcUnidadAdmvaService;
    }

    public AdmcUnidadAdmvaService getAdmcUnidadAdmvaService() {
        return admcUnidadAdmvaService;
    }

    public void setDictaminadorService(DictaminadorService dictaminadorService) {
        this.dictaminadorService = dictaminadorService;
    }

    public DictaminadorService getDictaminadorService() {
        return dictaminadorService;
    }

    public void setBanderamMsj(Boolean banderamMsj) {
        this.banderamMsj = banderamMsj;
    }

    public Boolean getBanderamMsj() {
        return banderamMsj;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
    
    public SatAgsEmpleadosMVService getSatAgsEmpleadosMVService() {
        return satAgsEmpleadosMVService;
    }

    public void setSatAgsEmpleadosMVService(SatAgsEmpleadosMVService satAgsEmpleadosMVService) {
        this.satAgsEmpleadosMVService = satAgsEmpleadosMVService;
    }
    
    public SatAgsEmpleadosMVDTO getEmpleadoAGS() {
        return empleadoAGS;
    }

    public void setEmpleadoAGS(SatAgsEmpleadosMVDTO empleadoAGS) {
        this.empleadoAGS = empleadoAGS;
    }

    public String getValRFC() {
        return valRFC;
    }

    public void setValRFC(String valRFC) {
        this.valRFC = valRFC;
    }
    
}
