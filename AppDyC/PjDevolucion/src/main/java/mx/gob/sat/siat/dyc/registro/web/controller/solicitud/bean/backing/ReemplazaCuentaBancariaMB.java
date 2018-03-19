/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SessionHandler;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.vo.RemplazaCuentaClabeVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


/**
 * @author Luis Alberto Dominguez Palomino [LADP]
 * MAnaged para el objeto de reemplazar una cuenta bancaria
 * */
@ManagedBean(name = "reemplazaCuentaBancariaMB")
@SessionScoped
public class ReemplazaCuentaBancariaMB extends AbstractPage {

    @ManagedProperty(value = "#{sessionHandler}")
    private SessionHandler sessionHandler;
    private RemplazaCuentaClabeVO remplazaCuentaClabeVO;
    private AccesoUsr usuario;

    public ReemplazaCuentaBancariaMB() {
    }

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
        this.usuario = (AccesoUsr)session.getAttribute(ConstantesDyC2.TIPO_ACCESO_CONT);
        remplazaCuentaClabeVO = new RemplazaCuentaClabeVO();
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

    public void setRemplazaCuentaClabeVO(RemplazaCuentaClabeVO remplazaCuentaClabeVO) {
        this.remplazaCuentaClabeVO = remplazaCuentaClabeVO;
    }

    public RemplazaCuentaClabeVO getRemplazaCuentaClabeVO() {
        return remplazaCuentaClabeVO;
    }

}
