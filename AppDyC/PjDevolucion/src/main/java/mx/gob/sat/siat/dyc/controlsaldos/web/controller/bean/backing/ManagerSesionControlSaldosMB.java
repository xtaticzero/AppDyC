package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.domain.movsaldo.DyctAccionPrivAjusDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantePerfilUsr;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


@ManagedBean(name = "sesionControlSaldos")
@SessionScoped
public class ManagerSesionControlSaldosMB
{
    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private Integer tipoBusqueda;
    private String rfc;
    private Integer idSaldoIcep;
    private String nomRazonSocIcepActivo;
    private DyctAccionPrivAjusDTO permisoAjustar;
    private Boolean esAdminCentral;
    
    @PostConstruct
    public void inicializar() {
        AccesoUsr accesoUsrL = serviceObtenerSesion.execute();
        esAdminCentral = accesoUsrL.getRoles().contains(ConstantePerfilUsr.PERFIL_ADM_CENTRAL);
    }

    public Integer getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(Integer tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public String getNomRazonSocIcepActivo() {
        return nomRazonSocIcepActivo;
    }

    public void setNomRazonSocIcepActivo(String nomRazonSocIcepActivo) {
        this.nomRazonSocIcepActivo = nomRazonSocIcepActivo;
    }

    public DyctAccionPrivAjusDTO getPermisoAjustar() {
        return permisoAjustar;
    }

    public void setPermisoAjustar(DyctAccionPrivAjusDTO permisoAjustar) {
        this.permisoAjustar = permisoAjustar;
    }

    public Boolean getEsAdminCentral() {
        return esAdminCentral;
    }

    public void setEsAdminCentral(Boolean esAdminCentral) {
        this.esAdminCentral = esAdminCentral;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

}
