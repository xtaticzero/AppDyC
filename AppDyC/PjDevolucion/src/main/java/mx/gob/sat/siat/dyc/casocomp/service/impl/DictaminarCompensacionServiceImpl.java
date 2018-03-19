package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.sql.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.dyc.controlsaldos.service.ControlSaldoImporte;

import mx.gob.sat.siat.dyc.casocomp.service.DictaminarCompensacionService;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridEstadoDocBean;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridIcepsOrigenSaldoBean;
import mx.gob.sat.siat.dyc.controlsaldos.service.impl.CargarSaldoIcepServiceImpl;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctNotaDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.dao.seguimiento.DycaReasignacionDAO;
import mx.gob.sat.siat.dyc.dao.seguimiento.DyctSeguimientoDAO;
import mx.gob.sat.siat.dyc.domain.ReasignacionDTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctNotaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSeguimientoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.vistas.EnumTipoUnidadAdmvaDyC;
import mx.gob.sat.siat.dyc.trabajo.util.constante.EnumRol;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("serviceDictaminarCompensacion")
public class DictaminarCompensacionServiceImpl implements DictaminarCompensacionService {

    private static final Logger LOG = Logger.getLogger(DictaminarCompensacionServiceImpl.class);

    @Autowired
    private DyctSeguimientoDAO daoSeguimiento;

    @Autowired
    private DyctNotaDAO daoNota;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DyctResolCompDAO daoResolComp;

    @Autowired
    private DycaReasignacionDAO dycaReasignacionDAO;

    @Autowired
    private ControlSaldoImporte controlSaldoImporteService;

    @Autowired
    private CargarSaldoIcepServiceImpl serviceCargarSaldoIcep;

    @Override
    public Map<String, Object> obtenerInfoIniDictaminarCC(String numControlComp, EnumRol rol,
            EnumTipoUnidadAdmvaDyC tipoAdm) {
        LOG.debug("###numControlComp ->" + numControlComp + "<-");
        HashMap<String, Object> infoInicial = new HashMap<String, Object>();
        DycpCompensacionDTO compensacion;
        try {
            compensacion = daoCompensacion.encontrar(numControlComp);
            compensacion.setDyctSaldoIcepDestinoDTO(daoSaldoIcep.encontrar(compensacion.getDyctSaldoIcepDestinoDTO().getIdSaldoIcep()));
            compensacion.setDyctSaldoIcepOrigenDTO(daoSaldoIcep.encontrar(compensacion.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep()));

            infoInicial.put("tipoTramite",
                    compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDescripcion());
            infoInicial.put("estadoCompensacion", compensacion.getDyccEstadoCompDTO().getDescripcion());

            DyctResolCompDTO resolComp = daoResolComp.encontrar(compensacion);
            LOG.debug("resolComp ->" + resolComp);

            if (resolComp != null) {
                infoInicial.put("estadoResolucion", resolComp.getDyccEstResolDTO().getDescripcion());
                infoInicial.put("fechaAprobacionResolucion", Utils.formatearFecha(resolComp.getFechaResolucion()));
            } else {
                infoInicial.put("estadoResolucion", "Sin Resolución");
                infoInicial.put("fechaAprobacionResolucion", "No aplica");
            }

            String folioAviso
                    = compensacion.getDycpAvisoCompDTO() != null ? compensacion.getDycpAvisoCompDTO().getFolioAviso() : null;

            infoInicial.put("importeCompensado", compensacion.getImporteCompensado());
            infoInicial.put("impuesto",
                    compensacion.getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getDyccImpuestoDTO().getDescripcion());
            infoInicial.put("concepto",
                    compensacion.getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getDescripcion());
            infoInicial.put("ejercicio",
                    compensacion.getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO().getIdEjercicio().toString());
            infoInicial.put("periodo", compensacion.getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO().getDescripcion());
            infoInicial.put("tipoDeclaracion", compensacion.getDyccTipoDeclaraDTO().getDescripcion());
            infoInicial.put("fechaPresentacion", Utils.formatearFecha(compensacion.getFechaPresentaDec()));
            infoInicial.put("icepOrigenSaldo", crearFilaGridIcepsOrigenSaldo(compensacion));
            infoInicial.put("documentos", obtenerDocumentos(compensacion));
            infoInicial.put("accionesPermitidas", obtenerAccionesPermitidas(compensacion, rol, tipoAdm));
            infoInicial.put("folioAviso", folioAviso);
        } catch (SIATException e) {
            LOG.error("error al obtener info inicial de la compensacion: " + numControlComp + "; mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }
        return infoInicial;
    }

    private List<ItemComboBean> obtenerAccionesPermitidas(DycpCompensacionDTO compensacion, EnumRol rol,
            EnumTipoUnidadAdmvaDyC tipoAdm) {
        LOG.debug("rol ->" + tipoAdm + "<-");
        List<ItemComboBean> opciones = new LinkedList<ItemComboBean>();

        if (rol == EnumRol.USUARIO_FISCALIZACION) {
            opciones.add(ConstantesACC.OPCION_DICT_CARG_DESC_PAPS_TBAJO);
        }

        if (rol == EnumRol.DICTAMINADOR) {
            if (compensacion.getDyccEstadoCompDTO() == Constantes.EstadosCompensacion.EN_PROCESO) {
                opciones.add(ConstantesACC.OPCION_DICT_EMITIR_REQ);
            }
            opciones.add(ConstantesACC.OPCION_DICT_EMITIR_CARTA_INV);
            /**
             * opciones.add(ConstantesACC.OPCION_DICT_EMITIR_CARTA_PRES_CONT);
             */
            if (tipoAdm != EnumTipoUnidadAdmvaDyC.GRANDES_CONTRIBUYENTES) {
                opciones.add(ConstantesACC.OPCION_DICT_EMITIR_REQ_CONF_PROV);
            }
            opciones.add(ConstantesACC.OPCION_DICT_CARG_DESC_PAPS_TBAJO);
            if (compensacion.getDyccEstadoCompDTO() == Constantes.EstadosCompensacion.EN_PROCESO) {
                opciones.add(ConstantesACC.OPCION_DICT_EMITIR_RESOL_COMP);
            }
            if (compensacion.getDyccEstadoCompDTO() == Constantes.EstadosCompensacion.REQUERIDO) {
                LOG.info("\n###\n  ::::: REQUERIDO : NO TIENE DEFINIDAS ACCIONES :::::");
            }
        }

        if (compensacion.getDyctResolCompDTO() != null) {
            LOG.debug("##\nestado resolucion ->"
                    + compensacion.getDyctResolCompDTO().getDyccEstResolDTO().getDescripcion() + "<-");

            if (rol == EnumRol.ADMINISTRADOR
                    && compensacion.getDyctResolCompDTO().getDyccEstResolDTO() == Constantes.EstadosResolucion.EN_APROBACION) {
                opciones.add(ConstantesACC.OPCION_APROB_RESOL);
                opciones.add(ConstantesACC.OPCION_NO_APROB_RESOL);
            }
        }

        return opciones;
    }

    private FilaGridIcepsOrigenSaldoBean crearFilaGridIcepsOrigenSaldo(DycpCompensacionDTO compensacion) throws SIATException {
        FilaGridIcepsOrigenSaldoBean beanIcepOrigenSaldo = new FilaGridIcepsOrigenSaldoBean();
        beanIcepOrigenSaldo.setIdSaldoIcep(compensacion.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep());
        beanIcepOrigenSaldo.setOrigenSaldo(compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getDescripcion());

        beanIcepOrigenSaldo.setPeriodicidad(compensacion.getDyctSaldoIcepOrigenDTO().getDyccPeriodoDTO().getDyccTipoPeriodoDTO().getDescripcion());

        beanIcepOrigenSaldo.setPeriodo(compensacion.getDyctSaldoIcepOrigenDTO().getDyccPeriodoDTO().getDescripcion());
        beanIcepOrigenSaldo.setEjercicio(compensacion.getDyctSaldoIcepOrigenDTO().getDyccEjercicioDTO().getIdEjercicio().toString());
        beanIcepOrigenSaldo.setConcepto(compensacion.getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO().getDescripcion());
        beanIcepOrigenSaldo.setSaldoAplicar(compensacion.getImporteCompensado());
        DyctSaldoIcepDTO saldoIcep = serviceCargarSaldoIcep.execute(beanIcepOrigenSaldo.getIdSaldoIcep());
        ControlSaldoImportesDTO controlSaldoImportes = controlSaldoImporteService.calcularImportes(saldoIcep);
        beanIcepOrigenSaldo.setRemanenteHistorico(controlSaldoImportes.getRemanente());

        return beanIcepOrigenSaldo;
    }

    private List<FilaGridEstadoDocBean> obtenerDocumentos(DycpCompensacionDTO compensacion) {
        LOG.debug("INICIO obtenerDocumentos - compensacion ->" + compensacion);
        List<FilaGridEstadoDocBean> estadosDocsBeans = new ArrayList<FilaGridEstadoDocBean>();
        List<DyctSeguimientoDTO> seguimientos = daoSeguimiento.selecXCompensacion(compensacion);
        LOG.debug("num de seguimientos encontrados ->" + seguimientos.size() + "<-");
        for (DyctSeguimientoDTO seguimiento : seguimientos) {
            FilaGridEstadoDocBean docBean = new FilaGridEstadoDocBean();
            docBean.setTipoDocumento(seguimiento.getDyctDocumentoDTO().getDyccTipoDocumentoDTO().getDescripcion());
            docBean.setAccionSeguimiento(seguimiento.getDyccAccionSegDTO().getDescripcion());
            docBean.setResponsable(seguimiento.getResponsable());
            docBean.setFechaReasignacion(seguimiento.getFecha());
            docBean.setComentarios(seguimiento.getComentarios());
            estadosDocsBeans.add(docBean);
        }
        ReasignacionDTO reasignacion = dycaReasignacionDAO.obtenerReasignacion(compensacion.getDycpServicioDTO().getNumControl());
        if (null != reasignacion) {
            FilaGridEstadoDocBean docBean = new FilaGridEstadoDocBean();
            docBean.setTipoDocumento("No aplica");
            docBean.setAccionSeguimiento("Reasignado");
            docBean.setResponsable(reasignacion.getNombreResponsable());
            docBean.setFechaReasignacion(reasignacion.getFechaReasignacion());
            docBean.setComentarios("No aplica");
            estadosDocsBeans.add(docBean);
        }
        return estadosDocsBeans;
    }

    @Override
    public void insertarNota(Map<String, Object> params) {
        DyctNotaDTO dto = new DyctNotaDTO();
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl((String) params.get("numControlComp"));
        dto.setDycpServicioDTO(servicio);
        dto.setTexto((String) params.get("texto"));
        dto.setFecha(new Date(new java.util.Date().getTime()));
        dto.setUsuario((String) params.get("nomCompUsuario"));
        daoNota.insertar(dto);
    }
}
