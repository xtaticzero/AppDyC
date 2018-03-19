package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SessionHandler;
import mx.gob.sat.siat.dyc.vo.ReqCuentaClabeVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


/**
 * @author JEFG
 * Actualizo Luis Alberto Dominguez Palomino [LADP]
 * @since 14/03/14
 */
@ManagedBean(name = "cuentaBancariaMB")
@SessionScoped
public class CuantaBancariaMB extends AbstractPage {

    @ManagedProperty(value = "#{sessionHandler}")
    private SessionHandler sessionHandler;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private ReqCuentaClabeVO reqCuentaClabeDTO;
    private DyctCuentaBancoDTO dyctCuentaBancoDTO;
    private AccesoUsr usuario;
    private String message;


    public CuantaBancariaMB() {

    }

    @PostConstruct
    public void init() {
        this.usuario = getServiceObtenerSesion().execute();
        reqCuentaClabeDTO = new ReqCuentaClabeVO();
    }

    public void setReqCuentaClabeDTO(ReqCuentaClabeVO reqCuentaClabeDTO) {
        this.reqCuentaClabeDTO = reqCuentaClabeDTO;
    }

    public ReqCuentaClabeVO getReqCuentaClabeDTO() {
        return reqCuentaClabeDTO;
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    public void setUsuario(AccesoUsr usuario) {
        this.usuario = usuario;
    }

    public AccesoUsr getUsuario() {
        return usuario;
    }

    public void setDyctCuentaBancoDTO(DyctCuentaBancoDTO dyctCuentaBancoDTO) {
        this.dyctCuentaBancoDTO = dyctCuentaBancoDTO;
    }

    public DyctCuentaBancoDTO getDyctCuentaBancoDTO() {
        return dyctCuentaBancoDTO;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
