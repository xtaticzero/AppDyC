package mx.gob.sat.siat.dyc.trabajo.web.controller.bean.backing;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import mx.gob.sat.siat.dyc.trabajo.service.impl.ObtenerInfoCatalogosServiceImpl;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;


@ManagedBean(name = "mbCatalogosEstaticos")
@ApplicationScoped
public class CatalogosEstaticosMB {
    private List<ItemComboBean> impuestos;
    private List<ItemComboBean> tiposPeriodo;
    private List<ItemComboBean> ejercicios;
    private List<ItemComboBean> tiposResolComp;
    private List<ItemComboBean> origenesSaldo;
    private List<ItemComboBean> tiposServicio;
    private List<ItemComboBean> estadosSolicitud;
    private List<ItemComboBean> estadosCompensacion;
    private List<ItemComboBean> unidadesAdmvas;
    private List<ItemComboBean> tiposServVigentes;

    @ManagedProperty(value = "#{serviceObtnerInfoCatSaldos}")
    private ObtenerInfoCatalogosServiceImpl serviceCatalogos;

    @PostConstruct
    public void init()
    {
        Map<String, Object> infoCatalogos = serviceCatalogos.execute();
        setImpuestos((List<ItemComboBean>)infoCatalogos.get("impuestos"));
        setTiposPeriodo((List<ItemComboBean>)infoCatalogos.get("tiposPeriodo"));
        setEjercicios((List<ItemComboBean>)infoCatalogos.get("ejercicios"));
        setTiposResolComp((List<ItemComboBean>)infoCatalogos.get("tiposResolComp"));
        origenesSaldo = (List<ItemComboBean>)infoCatalogos.get("origenesSaldo");
        setTiposServicio((List<ItemComboBean>)infoCatalogos.get("tiposServicio"));
        estadosSolicitud = (List<ItemComboBean>)infoCatalogos.get("estadosSolicitud");
        estadosCompensacion = (List<ItemComboBean>)infoCatalogos.get("estadosCompensacion");
        unidadesAdmvas = (List<ItemComboBean>)infoCatalogos.get("unidadesAdmvas");
        tiposServVigentes = (List<ItemComboBean>)infoCatalogos.get("tiposServVigentes");
    }

    public List<ItemComboBean> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ItemComboBean> impuestos) {
        this.impuestos = impuestos;
    }

    public List<ItemComboBean> getTiposPeriodo() {
        return tiposPeriodo;
    }

    public void setTiposPeriodo(List<ItemComboBean> tiposPeriodo) {
        this.tiposPeriodo = tiposPeriodo;
    }

    public List<ItemComboBean> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<ItemComboBean> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public ObtenerInfoCatalogosServiceImpl getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ObtenerInfoCatalogosServiceImpl serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public List<ItemComboBean> getTiposResolComp() {
        return tiposResolComp;
    }

    public void setTiposResolComp(List<ItemComboBean> tiposResolComp) {
        this.tiposResolComp = tiposResolComp;
    }

    public List<ItemComboBean> getOrigenesSaldo() {
        return origenesSaldo;
    }

    public void setOrigenesSaldo(List<ItemComboBean> origenesSaldo) {
        this.origenesSaldo = origenesSaldo;
    }

    public List<ItemComboBean> getTiposServicio() {
        return tiposServicio;
    }

    public void setTiposServicio(List<ItemComboBean> tiposServicio) {
        this.tiposServicio = tiposServicio;
    }

    public List<ItemComboBean> getEstadosSolicitud() {
        return estadosSolicitud;
    }

    public void setEstadosSolicitud(List<ItemComboBean> estadosSolicitud) {
        this.estadosSolicitud = estadosSolicitud;
    }

    public List<ItemComboBean> getEstadosCompensacion() {
        return estadosCompensacion;
    }

    public void setEstadosCompensacion(List<ItemComboBean> estadosCompensacion) {
        this.estadosCompensacion = estadosCompensacion;
    }

    public List<ItemComboBean> getUnidadesAdmvas() {
        return unidadesAdmvas;
    }

    public void setUnidadesAdmvas(List<ItemComboBean> unidadesAdmvas) {
        this.unidadesAdmvas = unidadesAdmvas;
    }

    public List<ItemComboBean> getTiposServVigentes() {
        return tiposServVigentes;
    }

    public void setTiposServVigentes(List<ItemComboBean> tiposServVigentes) {
        this.tiposServVigentes = tiposServVigentes;
    }
}
