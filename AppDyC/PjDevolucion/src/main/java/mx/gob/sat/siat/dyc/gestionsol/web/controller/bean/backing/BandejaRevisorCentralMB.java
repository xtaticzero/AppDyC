package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.service.revision.RevisionCentralService;
import mx.gob.sat.siat.dyc.gestionsol.util.constante.ConstantesGestionSol;
import mx.gob.sat.siat.dyc.gestionsol.vo.revision.RevisionCentralVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@ManagedBean(name = "bandejaRevisorCentralMB")
@ViewScoped
public class BandejaRevisorCentralMB extends AbstractPage {
    public BandejaRevisorCentralMB() {
        super();
    }
    private int p;
    private RevisionCentralVO documento;
    private List<RevisionCentralVO> listaDocumentos;
    private static final Logger LOGGER = Logger.getLogger(BandejaRevisorCentralMB.class);
    
    @ManagedProperty("#{revisionCentralService}")
    private RevisionCentralService revisionCentralService;
    
    @ManagedProperty("#{resumenRevisorCentralMB}")
    private ResumenRevisorCentralMB resumenRevisorCentralMB;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @PostConstruct
    public void init() {
        try {
            AccesoUsr accesoUsr = serviceObtenerSesion.execute();
            elegirCorrectamenteLaSesionAValidar(accesoUsr);
            
            if(accesoUsr.getRoles().contains(ConstantesGestionSol.ROL_REVISOR_CENTRAL) && obtenerOpcionDelMenu().equals(ConstantesDyCNumerico.VALOR_1)) {
                listaDocumentos = revisionCentralService.consultar(0);
                consultarPadronDeLegales(0);
            }
            if (accesoUsr.getRoles().contains(ConstantesGestionSol.ROL_REVISOR_DE_LEGALES) && obtenerOpcionDelMenu().equals(ConstantesDyCNumerico.VALOR_2)) {
                listaDocumentos = revisionCentralService.consultar(1);    
                consultarPadronDeLegales(1);
            } 
            
            setDataModel(new SIATDataModel());
            getDataModel().setWrappedData(listaDocumentos);
            obtenerResultado();
            
        } catch (SIATException e) {
            LOGGER.error("Hubo un error al cargar la lista de documentos."+e);
        }
    }
    
    /**
     * Agrega a la lista de datos consultados, los registros que pertenecen al padron de legales.
     *
     * @param bandera
     * @throws SIATException
     */
    private void consultarPadronDeLegales(Integer bandera) throws SIATException {
        if(listaDocumentos != null) {
            listaDocumentos.addAll(depurarLista(listaDocumentos, revisionCentralService.consultarDelPadronDeNoConfiables(bandera)));
            
        } else {
            listaDocumentos = revisionCentralService.consultarDelPadronDeNoConfiables(bandera);
        }
    }
    
    /**
     * Obtiene la opcion de menu de la cual da clic.
     */
    private Integer obtenerOpcionDelMenu() {
        Integer resultado=null;
        if ((FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("p"))!=null) {
            resultado=Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("p"));
            p=resultado;
        }
        return resultado;
    }
    
    /**
     * Valida que si viene un revisor central o de legales tenga asociado el proceso correcto.
     *
     * @param accesoUsr Variable con datos de sesion.
     */
    private void elegirCorrectamenteLaSesionAValidar(AccesoUsr accesoUsr) {
        if(accesoUsr.getRoles().contains(ConstantesGestionSol.ROL_REVISOR_CENTRAL)) {
            Utils.validarSesion(accesoUsr, Procesos.DYC00020);
            
        } else if (accesoUsr.getRoles().contains(ConstantesGestionSol.ROL_REVISOR_DE_LEGALES)) {
            Utils.validarSesion(accesoUsr, Procesos.DYC00023);
            
        } else {
            Utils.validarSesion(accesoUsr, Procesos.DYC00000);
        }
    }
    
    /**
     * Obtiene el valor del resultado de la aprobacion o rechazo por parte del revisor central
     */
    private void obtenerResultado() {
        Integer resultado=null;
        FacesMessage facesMessage = new FacesMessage("");
        if ((FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("resultado"))!=null) {
            resultado=Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("resultado"));
        }
        
        if (resultado!=null) {
            if (resultado.equals(1)) {
                facesMessage.setDetail("Se aprobó el documento.");
                facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
                facesMessage.rendered();
                
            } else if (resultado.equals(ConstantesDyCNumerico.VALOR_2)) {
                facesMessage.setDetail("Se rechazó el documento.");
                facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
                facesMessage.rendered();
            }
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
    }
    
    /**
     * Una vez que el usuario a seleccionado un documento para ser revisado, se redirecciona a la pantalla de resumen.
     * Si no ha seleccionado nada le muestra en pantalla un mensaje de error.
     */
    public void goToResumen() {
        FacesMessage facesMessage = new FacesMessage("");
        
        if (documento== null) {
            facesMessage.setDetail("Debe de seleccionar un documento.");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
        } else {
            resumenRevisorCentralMB.setNumControlDoc(documento.getNumControlDoc());
            resumenRevisorCentralMB.setComentarios("");
            resumenRevisorCentralMB.init();
            resumenRevisorCentralMB.setP(p);
            
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+
                                                                                "/faces/resources/pages/gestionsol/resumenRevisorCentral.jsf");
                
            } catch (Exception e) {
                LOGGER.error("Hubo un error al tratar de modificar el tipo de trámite, intente mas tarde.");
            }
        }
    }
    
    /**
     * Busca si dentro de la lista de los valores a retornar si el valor está repetido y lo depura.
     *
     * @param listaDeConsultaEnBase
     * @param listaDePadron
     * @return
     */
    private List<RevisionCentralVO> depurarLista(List<RevisionCentralVO> listaDeConsultaEnBase, List<RevisionCentralVO> listaDePadron) {
        List<RevisionCentralVO> listaResultante = new ArrayList<RevisionCentralVO>();   
        boolean bandera=false;
        for(RevisionCentralVO obj1 : listaDePadron) {
            for(RevisionCentralVO obj2 : listaDeConsultaEnBase) {
                if(obj1.getNumControl().equals(obj2.getNumControl())) {
                    bandera=Boolean.TRUE;
                    break;
                }
            }
            if(!bandera) {
                listaResultante.add(obj1);
            }
            bandera=false;
        }
        return listaResultante;
    }

    public void setRevisionCentralService(RevisionCentralService revisionCentralService) {
        this.revisionCentralService = revisionCentralService;
    }

    public RevisionCentralService getRevisionCentralService() {
        return revisionCentralService;
    }

    public void setListaDocumentos(List<RevisionCentralVO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public List<RevisionCentralVO> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setDocumento(RevisionCentralVO documento) {
        this.documento = documento;
    }

    public RevisionCentralVO getDocumento() {
        return documento;
    }

    public void setResumenRevisorCentralMB(ResumenRevisorCentralMB resumenRevisorCentralMB) {
        this.resumenRevisorCentralMB = resumenRevisorCentralMB;
    }

    public ResumenRevisorCentralMB getResumenRevisorCentralMB() {
        return resumenRevisorCentralMB;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
