
/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.web.controller.bean.backing;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.print.attribute.standard.Severity;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.dto.institucioncredito.FrmInstitucionCreditoDTO;
import mx.gob.sat.siat.dyc.admcat.dto.institucioncredito.ListaInstitucionCreditoDTO;
import mx.gob.sat.siat.dyc.admcat.service.institucioncredito.ListaInstCreditoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInstCreditoService;
import mx.gob.sat.siat.dyc.generico.util.UtilsValidaSesion;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.event.SelectEvent;


/**
 * ManagedBean Institucion Credito
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * Actualizo Luis Alberto Dominguez Palomino [LADP]
 * @date Septiembre 5, 2013
 * @since 0.1
 *
 * */
@ManagedBean(name = "institucionCreditoMB")
@ViewScoped
public class InstitucionCreditoMB {

    private static final Logger LOGGER = Logger.getLogger(InstitucionCreditoMB.class);

    @ManagedProperty(value = "#{dyccInstCreditoService}")
    private DyccInstCreditoService dyccInstCreditoService;
    @ManagedProperty(value = "#{listaInstCreditoService}")
    private ListaInstCreditoService listaInstCreditoService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private List<ListaInstitucionCreditoDTO> listaInstCredito;
    private FrmInstitucionCreditoDTO frmInstitucionCreditoDTO;

    private StringBuilder strSalida;
    private FacesMessage message;
    private ListaInstitucionCreditoDTO instCredito;
    private ListaInstitucionCreditoDTO selectInstitucion;
    private Severity typo;
    private Boolean btnValidar;
    private String idInstitucion;
    private AccesoUsr accesoUsr;

    public InstitucionCreditoMB() {
        super();
    }

    @PostConstruct
    public void init() {
        accesoUsr = serviceObtenerSesion.execute();
        UtilsValidaSesion.validarSesion(accesoUsr, Procesos.DYC00015);
        
        this.listaInstCredito = new ArrayList<ListaInstitucionCreditoDTO>();
        this.frmInstitucionCreditoDTO = new FrmInstitucionCreditoDTO();
        StringBuilder strMensajeForm = new StringBuilder();
        try {
            this.frmInstitucionCreditoDTO.setMensaje(this.listaInstCreditoService.obtenerMensaje("CONFIRMA"));
            strMensajeForm.append(this.listaInstCreditoService.obtenerMensaje("REQUERIDO"));
        } catch (Exception e) {
            LOGGER.error("ERROR EN MB " + e.getMessage());
        }

        this.getFrmInstitucionCreditoDTO().setStrMsgIdInstCredito(strMensajeForm.toString().replaceAll("Campo1",
                                                                                                       "ID INSTITUCION CREDITO"));
        this.getFrmInstitucionCreditoDTO().setStrMsgNombInstCredito(strMensajeForm.toString().replaceAll("Campo1",
                                                                                                         "NOMBRE INSTITUCION CREDITO").toUpperCase());

        this.strSalida = new StringBuilder();
        this.instCredito = new ListaInstitucionCreditoDTO();

        this.verListaInstituciones();
        this.btnValidar = Boolean.TRUE;

    }

    public void verListaInstituciones() {

        this.listaInstCredito = this.listaInstCreditoService.listaInstitucionesCredito();
        int numCols = this.listaInstCredito.size();
        if (numCols != 0) {
            this.frmInstitucionCreditoDTO.setTotalResultados(numCols);
        }
        if (numCols > ConstantesDyC1.NO_COLS_PAGINA) {
            this.frmInstitucionCreditoDTO.setPaginador(Boolean.TRUE);
            this.frmInstitucionCreditoDTO.setNColumnas(ConstantesDyC1.NO_COLS_PAGINA);
        } else {
            this.frmInstitucionCreditoDTO.setPaginador(Boolean.FALSE);
        }
        this.frmInstitucionCreditoDTO.setVerTabla(Boolean.TRUE);
    }

    public void activaBoton(SelectEvent event) {
        this.btnValidar = Boolean.FALSE;
    }

    public void execAccion() {
        LOGGER.info(">>>" + this.frmInstitucionCreditoDTO.getAccion());
        this.strSalida.setLength(0);
        switch (this.frmInstitucionCreditoDTO.getAccion()) {
        case ConstantesDyCNumerico.VALOR_1475369:
            instCredito.setIdInstCredito(Integer.parseInt(idInstitucion));
            this.listaInstCreditoService.insertarInstitucionesCredito(instCredito);
            this.strSalida.append(this.listaInstCreditoService.obtieneMensaje());
            this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.strSalida.toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
            this.verListaInstituciones();
            this.setIdInstitucion("");
            break;
        case ConstantesDyCNumerico.VALOR_1475963:
            this.listaInstCreditoService.actualizaInstitucionesCredito(instCredito);
            this.strSalida.append(this.listaInstCreditoService.obtieneMensaje());
            this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.strSalida.toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
            this.verListaInstituciones();
            break;
        default:
            LOGGER.info("Case Default");
            break;
        }
    }

    public void frmAdicionaIC() {
        LOGGER.info(">>>Adiciona");
        this.frmInstitucionCreditoDTO.setNuevaIC(Boolean.TRUE);
        this.frmInstitucionCreditoDTO.setEditaIC(Boolean.FALSE);
        this.frmInstitucionCreditoDTO.setAccion(ConstantesDyCNumerico.VALOR_1475369);
        this.frmInstitucionCreditoDTO.setTituloDialog(ConstantesDyC2.IC_ADD);
        this.instCredito.setDescripcion("");
        this.instCredito.setIdInstCredito(ConstantesDyCNumerico.VALOR_0);
    }

    public void frmModificaIC() {
        LOGGER.info(">>>Modifica");
        if (this.selectInstitucion != null) {
            this.instCredito = this.selectInstitucion;

            if (this.instCredito.getIdInstCredito() != ConstantesDyCNumerico.VALOR_0) {
                this.frmInstitucionCreditoDTO.setNuevaIC(Boolean.FALSE);
                this.frmInstitucionCreditoDTO.setEditaIC(Boolean.TRUE);
                this.frmInstitucionCreditoDTO.setAccion(ConstantesDyCNumerico.VALOR_1475963);
                this.frmInstitucionCreditoDTO.setTituloDialog(ConstantesDyC2.IC_MOD);
            }
        }
    }

    // TODO:  ACCESSORS **********************************************************************************

    public void setDyccInstCreditoService(DyccInstCreditoService dyccInstCreditoService) {
        this.dyccInstCreditoService = dyccInstCreditoService;
    }

    public DyccInstCreditoService getDyccInstCreditoService() {
        return dyccInstCreditoService;
    }

    public void setListaInstCreditoService(ListaInstCreditoService listaInstCreditoService) {
        this.listaInstCreditoService = listaInstCreditoService;
    }

    public ListaInstCreditoService getListaInstCreditoService() {
        return listaInstCreditoService;
    }

    public void setListaInstCredito(List<ListaInstitucionCreditoDTO> listaInstCredito) {
        this.listaInstCredito = listaInstCredito;
    }

    public List<ListaInstitucionCreditoDTO> getListaInstCredito() {
        return listaInstCredito;
    }

    public void setFrmInstitucionCreditoDTO(FrmInstitucionCreditoDTO frmInstitucionCreditoDTO) {
        this.frmInstitucionCreditoDTO = frmInstitucionCreditoDTO;
    }

    public FrmInstitucionCreditoDTO getFrmInstitucionCreditoDTO() {
        return frmInstitucionCreditoDTO;
    }

    public void setStrSalida(StringBuilder strSalida) {
        this.strSalida = strSalida;
    }

    public StringBuilder getStrSalida() {
        return strSalida;
    }

    public void setMessage(FacesMessage message) {
        this.message = message;
    }

    public FacesMessage getMessage() {
        return message;
    }

    public void setInstCredito(ListaInstitucionCreditoDTO instCredito) {
        this.instCredito = instCredito;
    }

    public ListaInstitucionCreditoDTO getInstCredito() {
        return instCredito;
    }

    public void setSelectInstitucion(ListaInstitucionCreditoDTO selectInstitucion) {
        this.selectInstitucion = selectInstitucion;
    }

    public ListaInstitucionCreditoDTO getSelectInstitucion() {
        return selectInstitucion;
    }

    public void setTypo(Severity typo) {
        this.typo = typo;
    }

    public Severity getTypo() {
        return typo;
    }

    public void setBtnValidar(Boolean btnValidar) {
        this.btnValidar = btnValidar;
    }

    public Boolean getBtnValidar() {
        return btnValidar;
    }

    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getIdInstitucion() {
        return idInstitucion;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
