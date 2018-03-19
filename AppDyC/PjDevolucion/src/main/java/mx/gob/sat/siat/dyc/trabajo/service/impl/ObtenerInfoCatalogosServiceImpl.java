package mx.gob.sat.siat.dyc.trabajo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.dao.regsolicitud.DyccEstadoSolDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccTipoResolDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.DyccEstadoCompDAO;
import mx.gob.sat.siat.dyc.dao.concepto.DyccImpuestoDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyccOrigenSaldoDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DyccTipoPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDyccTipoServicioDAO;
import mx.gob.sat.siat.dyc.dao.util.DyccEjercicioDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "serviceObtnerInfoCatSaldos")
public class ObtenerInfoCatalogosServiceImpl {
    @Autowired
    private DyccImpuestoDAO daoImpuesto;

    @Autowired
    private DyccTipoPeriodoDAO daoTipoPeriodo;

    @Autowired
    private DyccEjercicioDAO daoEjercicio;

    @Autowired
    private DyccTipoResolDAO daoTipoResol;

    @Autowired
    private DyccOrigenSaldoDAO daoOrigenSaldo;

    @Autowired
    private IDyccTipoServicioDAO daoTipoServicio;

    @Autowired
    private DyccEstadoSolDAO daoEstadoSol;

    @Autowired
    private DyccEstadoCompDAO daoEstadoComp;

    @Autowired
    private AdmcUnidadAdmvaDAO daoUnidadAdmva;

    public Map<String, Object> execute() {
        Map<String, Object> infoInicial = new HashMap<String, Object>();
        infoInicial.put("impuestos", obtenerImpuestos());
        infoInicial.put("tiposPeriodo", obtenerTiposPeriodo());
        infoInicial.put("ejercicios", obtenerEjercicios());
        infoInicial.put("tiposResolComp", obtenerTiposResolComp());
        infoInicial.put("origenesSaldo", obtenerOrigenesSaldo());
        infoInicial.put("tiposServicio", obtenerTiposServicio());
        infoInicial.put("estadosSolicitud", obtenerEstadosSolicitud());
        infoInicial.put("estadosCompensacion", obtenerEstadosCompensacion());
        infoInicial.put("unidadesAdmvas", obtenerUnidadesAdmvas());
        infoInicial.put("tiposServVigentes", obtenerServVigentes());

        return infoInicial;
    }

    private List<ItemComboBean> obtenerImpuestos() {
        List<ItemComboBean> beansImpuestos = new ArrayList<ItemComboBean>();
        for (DyccImpuestoDTO impuesto : daoImpuesto.obtieneImpuestos()) {
            ItemComboBean bean = new ItemComboBean();
            bean.setId(impuesto.getIdImpuesto());
            bean.setDescripcion(impuesto.getDescripcion());
            beansImpuestos.add(bean);
        }

        return beansImpuestos;
    }

    private List<ItemComboBean> obtenerTiposPeriodo() {
        List<ItemComboBean> beans = new ArrayList<ItemComboBean>();
        for (DyccTipoPeriodoDTO tipoPeriodo : daoTipoPeriodo.obtieneTipoPeriodo()) {
            ItemComboBean bean = new ItemComboBean();
            bean.setIdStr(tipoPeriodo.getIdTipoPeriodo());
            bean.setDescripcion(tipoPeriodo.getDescripcion());
            beans.add(bean);
        }

        return beans;
    }

    private List<ItemComboBean> obtenerEjercicios() {
        List<ItemComboBean> beans = new ArrayList<ItemComboBean>();

        for (DyccEjercicioDTO ejercicio : daoEjercicio.obtieneEjercicio()) {
            ItemComboBean bean = new ItemComboBean();
            bean.setId(ejercicio.getIdEjercicio());
            beans.add(bean);
        }

        return beans;
    }

    private List<ItemComboBean> obtenerTiposResolComp() {
        List<ItemComboBean> tiposResolComp = new ArrayList<ItemComboBean>();

        for (DyccTipoResolDTO tipoSaldo :
             daoTipoResol.buscarTiposResolucion(Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio())) {
            ItemComboBean tipoResol = new ItemComboBean();
            tipoResol.setId(tipoSaldo.getIdTipoResol());
            tipoResol.setDescripcion(tipoSaldo.getDescripcion());
            tiposResolComp.add(tipoResol);
        }

        return tiposResolComp;
    }

    private List<ItemComboBean> obtenerOrigenesSaldo() {
        List<ItemComboBean> origenesSaldo = new ArrayList<ItemComboBean>();

        for (DyccOrigenSaldoDTO origenSaldo : daoOrigenSaldo.obtieneTodos()) {
            ItemComboBean beanOrigenSaldo = new ItemComboBean();
            beanOrigenSaldo.setId(origenSaldo.getIdOrigenSaldo());
            beanOrigenSaldo.setDescripcion(origenSaldo.getDescripcion());
            origenesSaldo.add(beanOrigenSaldo);
        }

        return origenesSaldo;
    }

    private List<ItemComboBean> obtenerTiposServicio() {
        List<ItemComboBean> tiposServicio = new ArrayList<ItemComboBean>();

        for (DyccTipoServicioDTO tipoServicio : daoTipoServicio.seleccionar()) {
            ItemComboBean beanTipoServicio = new ItemComboBean();
            beanTipoServicio.setId(tipoServicio.getIdTipoServicio());
            beanTipoServicio.setDescripcion(tipoServicio.getDescripcion());
            tiposServicio.add(beanTipoServicio);
        }

        return tiposServicio;
    }

    private List<ItemComboBean> obtenerEstadosSolicitud() {
        List<ItemComboBean> estadosSolicitud = new ArrayList<ItemComboBean>();

        for (DyccEstadoSolDTO estadoSol : daoEstadoSol.obtenerLista()) {
            ItemComboBean beanEstadoSol = new ItemComboBean();
            beanEstadoSol.setId(estadoSol.getIdEstadoSolicitud());
            beanEstadoSol.setDescripcion(estadoSol.getDescripcion());
            estadosSolicitud.add(beanEstadoSol);
        }

        return estadosSolicitud;
    }

    private List<ItemComboBean> obtenerEstadosCompensacion() {
        List<ItemComboBean> estadosCompensacion = new ArrayList<ItemComboBean>();

        for (DyccEstadoCompDTO estadoComp : daoEstadoComp.obtenerLista()) {
            ItemComboBean beanEstadoComp = new ItemComboBean();
            beanEstadoComp.setId(estadoComp.getIdEstadoComp());
            beanEstadoComp.setDescripcion(estadoComp.getDescripcion());
            estadosCompensacion.add(beanEstadoComp);
        }

        return estadosCompensacion;
    }

    private List<ItemComboBean> obtenerUnidadesAdmvas() {
        List<ItemComboBean> unidadesAdmvas = new ArrayList<ItemComboBean>();

        for (AdmcUnidadAdmvaDTO unidadAdmva : daoUnidadAdmva.seleccionarOrderBy(" ORDER BY NOMBRE ASC ")) {
            ItemComboBean beanUnidadAdmva = new ItemComboBean();
            beanUnidadAdmva.setId(unidadAdmva.getClaveSir());
            beanUnidadAdmva.setDescripcion(unidadAdmva.getNombre());
            unidadesAdmvas.add(beanUnidadAdmva);
        }

        return unidadesAdmvas;
    }

    private List<ItemComboBean> obtenerServVigentes() {
        ItemComboBean[] servVigentes =
            new ItemComboBean[] { new ItemComboBean(ConstantesDyCNumerico.VALOR_1, "Devolución"),
                                  new ItemComboBean(ConstantesDyCNumerico.VALOR_2, "Devolución Automática"),
                                  new ItemComboBean(ConstantesDyCNumerico.VALOR_3, "Compensación") };
        return Arrays.asList(servVigentes);
    }
}
