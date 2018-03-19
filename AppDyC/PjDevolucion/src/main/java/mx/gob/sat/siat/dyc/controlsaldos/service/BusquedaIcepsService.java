package mx.gob.sat.siat.dyc.controlsaldos.service;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridBusquedaIceps;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface BusquedaIcepsService
{
    List<FilaGridBusquedaIceps> buscarIceps(Map<String, Object> params)throws SIATException;

    List<ItemComboBean> obtenerConceptosXImpuesto(Integer idImpuesto);

    List<ItemComboBean> obtenerPeriodosXTipo(String idTipoPeriodo);

    Map<String, Object> buscarIcepsXpalabraclave(String palabraClave)throws SIATException;
    
    List<FilaGridBusquedaIceps> buscarIcep(Map<String, Object> params)throws SIATException;

   
}
