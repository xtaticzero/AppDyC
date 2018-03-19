package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.controlsaldos.service.BusquedaIcepsService;
import mx.gob.sat.siat.dyc.controlsaldos.util.ConstantesCS;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridBusquedaIceps;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.concepto.DyccConceptoDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovDevolucionDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DyccPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.util.DyctCompHistoricaDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.trabajo.util.constante.ConstantesDyCWeb;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "serviceBusquedaIceps")
public class BusquedaIcepsServiceImpl implements BusquedaIcepsService
{
    private static final Logger LOG = Logger.getLogger(BusquedaIcepsServiceImpl.class);

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private IDycpServicioDAO daoServicio;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DycpSolicitudDAO daoSolicitud;

    @Autowired
    private DyccConceptoDAO daoConcepto;

    @Autowired
    private DyccPeriodoDAO daoPeriodo;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private DyctMovDevolucionDAO daoMovDevolucion;

    @Autowired
    private DyctCompHistoricaDAO daoMovCompensacion;
    
    private static final String RFC = "rfc";

    @Override
    public List<FilaGridBusquedaIceps> buscarIceps(Map<String, Object> params) throws SIATException
    {
        List<FilaGridBusquedaIceps> filas = new ArrayList<FilaGridBusquedaIceps>();
        Integer tipoBusqueda = (Integer)params.get("tipoBusqueda");
        LOG.debug("tipoBusqueda ->" + tipoBusqueda + "<-");
        switch(tipoBusqueda)
        {
            case ConstantesCS.BUSQUEDA_X_RFC: 
                LOG.debug("rfc recibido en Service ->" + params.get(RFC) + "<-");
                List<DyctSaldoIcepDTO> iceps = daoSaldoIcep.selecXRfc((String)params.get(RFC));
                LOG.debug("numero de iceps encontrados ->" + iceps.size());
                for(DyctSaldoIcepDTO icep : iceps){
                    filas.add(crearFila(icep));
                }
            break;
            case ConstantesCS.BUSQUEDA_X_ICEP: 
                String rfc = (String)params.get(RFC);

                ItemComboBean itemConcepto = (ItemComboBean)params.get("itemConcepto");
                LOG.debug("itemConcepto ->" + itemConcepto + "<-");
                ItemComboBean itemImpuesto = itemConcepto.getItemPadre();
                DyccImpuestoDTO impuesto = new DyccImpuestoDTO();
                impuesto.setIdImpuesto(itemImpuesto.getId());
                impuesto.setDescripcion(itemImpuesto.getDescripcion());
                
                DyccConceptoDTO cpt = new DyccConceptoDTO();
                cpt.setIdConcepto(itemConcepto.getId());
                cpt.setDescripcion(itemConcepto.getDescripcion());
                cpt.setDyccImpuestoDTO(impuesto);

                DyccEjercicioDTO ejr = new DyccEjercicioDTO();
                ejr.setIdEjercicio((Integer)params.get("idEjercicio"));

                ItemComboBean itemPeriodo = (ItemComboBean)params.get("itemPeriodo");
                DyccPeriodoDTO per = new DyccPeriodoDTO();
                per.setIdPeriodo(itemPeriodo.getId());
                per.setDescripcion(itemPeriodo.getDescripcion());

                List<DyctSaldoIcepDTO> iceps2 = daoSaldoIcep.selecXRfcConceptoEjercicioPeriodo(rfc, cpt, ejr, per);
                for(DyctSaldoIcepDTO icep : iceps2){
                    filas.add(crearFila(icep));
                }

            break;
            case ConstantesCS.BUSQUEDA_X_NUMCONTROL:
                LOG.debug("numControl ->" + params.get("numControl") + "<-");
                DycpServicioDTO servicio = daoServicio.encontrar((String)params.get("numControl"));
                if(servicio != null)
                {
                    DyccTipoServicioDTO tipoServicio = servicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO();
                    DyctSaldoIcepDTO icep = null;
                    if(tipoServicio == Constantes.TiposServicio.CASO_COMPENSACION || tipoServicio == Constantes.TiposServicio.AVISO_COMPENSACION)
                    {
                        DycpCompensacionDTO compensacion = daoCompensacion.encontrar(servicio.getNumControl());
                        if(compensacion != null){
                            icep = daoSaldoIcep.encontrar(compensacion.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep());
                        }
                    }
                    else if(tipoServicio == Constantes.TiposServicio.SOLICITUD_DEVOLUCION)
                    {
                        DycpSolicitudDTO solicitud = daoSolicitud.encontrar(servicio.getNumControl());
                        if(solicitud != null){
                            icep = daoSaldoIcep.encontrar(solicitud.getDyctSaldoIcepDTO().getIdSaldoIcep());
                        }
                    }

                    if(icep != null){
                        filas.add(crearFila(icep));
                    }
                }
            break;
            case ConstantesDyCNumerico.VALOR_4:
                DyctSaldoIcepDTO icepUnico = daoSaldoIcep.encontrar((Integer)params.get("idSaldoIcep"));
                if(icepUnico != null){
                    filas.add(crearFila(icepUnico)); 
                }
            break;
            default:
                LOG.error("La opción de búsqueda no es válida, se regresará una lista vacía");
        }

        List<FilaGridBusquedaIceps> icepsSinMovs = new ArrayList<FilaGridBusquedaIceps>();
        DyctSaldoIcepDTO icepDTOAux;
        for(FilaGridBusquedaIceps icep : filas)
        {
            icepDTOAux = new DyctSaldoIcepDTO();
            icepDTOAux.setIdSaldoIcep(icep.getIdSaldoIcep());
            if(daoMovSaldo.selecXSaldoicep(icepDTOAux).isEmpty()){
                icepsSinMovs.add(icep) ;
            }
        }

        filas.removeAll(icepsSinMovs);

        return filas;
    }
    
    private FilaGridBusquedaIceps crearFila(DyctSaldoIcepDTO icep)
    {
        FilaGridBusquedaIceps fila = new FilaGridBusquedaIceps();
        fila.setIdSaldoIcep(icep.getIdSaldoIcep());
        fila.setRfc(icep.getRfc());
        fila.setImpuesto(icep.getDyccConceptoDTO().getDyccImpuestoDTO().getDescripcion());
        fila.setConcepto(icep.getDyccConceptoDTO().getDescripcion());
        fila.setPeriodo(icep.getDyccPeriodoDTO().getDescripcion());
        fila.setEjercicio(icep.getDyccEjercicioDTO().getIdEjercicio());
        fila.setTipoSaldo(icep.getDyccOrigenSaldoDTO() != null ? icep.getDyccOrigenSaldoDTO().getDescripcion() : "");
        return fila;
    }

    @Override
    public List<ItemComboBean> obtenerConceptosXImpuesto(Integer idImpuesto)
    {
        List<ItemComboBean> itemsConceptos = new ArrayList<ItemComboBean>();
        DyccImpuestoDTO impuesto = new DyccImpuestoDTO();
        impuesto.setIdImpuesto(idImpuesto);
        List<DyccConceptoDTO> conceptos = daoConcepto.selecOrderXImpuesto(impuesto, "  ORDER BY IDCONCEPTO ASC");
        for(DyccConceptoDTO dto : conceptos)
        {
            ItemComboBean bean = new ItemComboBean();
            bean.setId(dto.getIdConcepto());
            bean.setDescripcion(dto.getDescripcion());
            itemsConceptos.add(bean);
        }

        return itemsConceptos;
    }

    @Override
    public List<ItemComboBean> obtenerPeriodosXTipo(String idTipoPeriodo)
    {
        List<ItemComboBean> itemsPeriodos = new ArrayList<ItemComboBean>();
        DyccTipoPeriodoDTO tipoPeriodo = BuscadorConstantes.obtenerTipoPeriodo(idTipoPeriodo);
        if(tipoPeriodo != null)
        {
            List<DyccPeriodoDTO> periodos = daoPeriodo.selecXTipoPeriodo(tipoPeriodo);
            for(DyccPeriodoDTO dto : periodos)
            {
                ItemComboBean bean = new ItemComboBean();
                bean.setId(dto.getIdPeriodo());
                bean.setDescripcion(dto.getDescripcion());
                itemsPeriodos.add(bean);
            }
        }
        return itemsPeriodos;
    }

    @Override
    public Map<String, Object> buscarIcepsXpalabraclave(String palabraClave) throws SIATException
    {
        LOG.debug("palabraClave ->" + palabraClave + "<-");
        List<FilaGridBusquedaIceps> filas = new ArrayList<FilaGridBusquedaIceps>();
        Boolean busco = Boolean.FALSE;

        Map<String, Object> respuesta = new HashMap<String, Object>();

        if (StringUtils.isNumeric(palabraClave))
        {
            Integer idIcep;
            try{
                idIcep = Integer.parseInt(palabraClave);
            }
            catch(NumberFormatException nfe){
                respuesta.put("excedeInt", Boolean.TRUE);
                return respuesta;
            }
            DyctSaldoIcepDTO icepUnico = daoSaldoIcep.encontrar(idIcep);
            if(icepUnico != null){
                filas.add(crearFila(icepUnico)); 
            }
            busco = Boolean.TRUE;
        }

        if(palabraClave.matches(ConstantesDyCWeb.ExpresionesRegulares.RFC))
        {
            List<DyctSaldoIcepDTO> iceps = daoSaldoIcep.selecXRfc(palabraClave);
            for(DyctSaldoIcepDTO icep : iceps)
            {
                if(icep.getDyccOrigenSaldoDTO() != null){
                    filas.add(crearFila(icep));
                }else{
                    if(!daoSolicitud.selecXSaldoicep(icep).isEmpty()){
                        filas.add(crearFila(icep));
                        continue;
                    }

                    if(!daoCompensacion.selecXSaldoiceporigen(icep).isEmpty()){
                        filas.add(crearFila(icep));
                        continue;
                    }

                    if(!daoMovDevolucion.obtenerXidSaldoIcep(icep.getIdSaldoIcep()).isEmpty()){
                        filas.add(crearFila(icep));
                        continue;
                    }

                    if(!daoMovCompensacion.obtenerXidSaldoIcep(icep.getIdSaldoIcep()).isEmpty()){
                        filas.add(crearFila(icep));
                    }
                }
            }
            busco = Boolean.TRUE;
        }

        if(palabraClave.matches(ConstantesDyCWeb.ExpresionesRegulares.NUMCONTROL))
        {
            DycpServicioDTO servicio = daoServicio.encontrar(palabraClave);
            LOG.debug("servicio ->" + servicio);
            if(servicio != null)
            {
                DyccTipoServicioDTO tipoServicio = servicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO();
                DyctSaldoIcepDTO icep = null;
                if(tipoServicio == Constantes.TiposServicio.CASO_COMPENSACION || tipoServicio == Constantes.TiposServicio.AVISO_COMPENSACION)
                {
                    DycpCompensacionDTO compensacion = daoCompensacion.encontrar(servicio.getNumControl());
                    if(compensacion != null){
                        icep = daoSaldoIcep.encontrar(compensacion.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep());
                    }
                }
                else if(tipoServicio == Constantes.TiposServicio.SOLICITUD_DEVOLUCION || tipoServicio == Constantes.TiposServicio.DEVOLUCION_AUTOMATICA)
                {
                    DycpSolicitudDTO solicitud = daoSolicitud.encontrar(servicio.getNumControl());
                    if(solicitud != null){
                        icep = daoSaldoIcep.encontrar(solicitud.getDyctSaldoIcepDTO().getIdSaldoIcep());
                    }
                }

                if(icep != null){
                    filas.add(crearFila(icep));
                }
            }
            busco = Boolean.TRUE;
        }
        
        respuesta.put("filas", filas);
        respuesta.put("busco", busco);
        respuesta.put("excedeInt", Boolean.FALSE);

        return respuesta;
    }

    @Override
    public List<FilaGridBusquedaIceps> buscarIcep(Map<String, Object> params) throws SIATException
    {
        List<FilaGridBusquedaIceps> filas = new ArrayList<FilaGridBusquedaIceps>();
        String rfc = (String)params.get(RFC);

        ItemComboBean itemConcepto = (ItemComboBean)params.get("itemConcepto");
        LOG.debug("itemConcepto ->" + itemConcepto + "<-");
        ItemComboBean itemImpuesto = itemConcepto.getItemPadre();
        DyccImpuestoDTO impuesto = new DyccImpuestoDTO();
        impuesto.setIdImpuesto(itemImpuesto.getId());
        impuesto.setDescripcion(itemImpuesto.getDescripcion());
        
        DyccConceptoDTO cpt = new DyccConceptoDTO();
        cpt.setIdConcepto(itemConcepto.getId());
        cpt.setDescripcion(itemConcepto.getDescripcion());
        cpt.setDyccImpuestoDTO(impuesto);

        DyccEjercicioDTO ejr = new DyccEjercicioDTO();
        ejr.setIdEjercicio((Integer)params.get("idEjercicio"));

        ItemComboBean itemPeriodo = (ItemComboBean)params.get("itemPeriodo");
        DyccPeriodoDTO per = new DyccPeriodoDTO();
        per.setIdPeriodo(itemPeriodo.getId());
        per.setDescripcion(itemPeriodo.getDescripcion());

        //TODO: se elimino tabla DYCC_TIPOSALDOICEP, verificar si este método sigue aplicando y dependiendo eliminar o corregir
       
        List<DyctSaldoIcepDTO> iceps2 = daoSaldoIcep.selecXRfcConceptoEjercicioPeriodo(rfc, cpt, ejr, per);
        for(DyctSaldoIcepDTO icep : iceps2){
            filas.add(crearFila(icep));
        }
        
        return filas;
    }
}
