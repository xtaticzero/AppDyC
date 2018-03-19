package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cobranza.herramienta.actualizador.dto.ActualizacionDetalleDTO;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.facade.ActualizadorFacade;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridConversionesBean;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "bdConvertidorSaldos")
public class ConvertidorSaldosBussinesDel
{
    private static final Logger LOG = Logger.getLogger(ConvertidorSaldosBussinesDel.class);
    
    @Autowired
    private ActualizadorFacade actualizadorCbza;

    public List<FilaGridConversionesBean> convertir(Map<String, Object> params)
    {
        LOG.debug("INICIO");
        Date fechaOrigen = (Date)params.get("fechaOrigen");
        Date fechaDestino = (Date)params.get("fechaDestino");
        BigDecimal cantidadBase = (BigDecimal)params.get("cantidadBase");
        
        List<FilaGridConversionesBean> conversiones = new ArrayList<FilaGridConversionesBean>();
        
        if(!DateUtils.isSameDay(fechaOrigen, fechaDestino))
        {
            if(fechaOrigen.before(fechaDestino))
            {
                LOG.debug("la fecha origen es anterior a la fecha destino, se actualizará");
                ActualizacionDetalleDTO objRespActualizacion = actualizadorCbza.calcularActualizacion(fechaOrigen, fechaDestino, cantidadBase);
                if(objRespActualizacion.getFactorActzn() != null){

                    FilaGridConversionesBean conv = new FilaGridConversionesBean();
                    conv.setTipoConversion("Actualización");
                    conv.setImporteConvertido(objRespActualizacion.getImporteActualizado());
                    conv.setFechaDestino(fechaDestino);
                    conv.setFactorActualizacion(objRespActualizacion.getFactorActzn().floatValue());
                    conv.setInpcOrigen(objRespActualizacion.getInpcAntiguo().floatValue());
                    conv.setFechaPubInpcOrigen(objRespActualizacion.getFechaPublicacionAntiguo());
                    conv.setInpcDestino(objRespActualizacion.getInpcReciente().floatValue());
                    conv.setFechaPubInpcDestino(objRespActualizacion.getFechaPublicacionReciente());
                    conversiones.add(conv);
    
                }
                else{
                    LOG.error("Ocurrio un error en el servicio actualizador FactorActzn == null; parametros enviados -> " +
                        "fechaOrigen:" + fechaOrigen +";  fechaDestino:" + fechaDestino + "; importe:" + cantidadBase + ";");
                }
            }
            else
            {
                LOG.debug("la fecha origen NO   es anterior a la fecha destino, se deflactará");
                LOG.debug("fechaOrigen ->" + fechaOrigen + "<-");
                LOG.debug("fechaDestino ->" + fechaDestino + "<-");
                ActualizacionDetalleDTO objRespDeflactado = actualizadorCbza.calcularDeflactacion(fechaDestino, fechaOrigen, cantidadBase);
                if(objRespDeflactado.getFactorActzn() != null){
                    FilaGridConversionesBean conv = new FilaGridConversionesBean();
                    conv.setTipoConversion("Deflactación");
                    conv.setImporteConvertido(objRespDeflactado.getImporteActualizado());
                    conv.setFechaDestino(fechaDestino);
                    conv.setFactorActualizacion(objRespDeflactado.getFactorActzn().floatValue());
                    conv.setInpcOrigen(objRespDeflactado.getInpcAntiguo().floatValue());
                    conv.setFechaPubInpcOrigen(objRespDeflactado.getFechaPublicacionAntiguo());
                    conv.setInpcDestino(objRespDeflactado.getInpcReciente().floatValue());
                    conv.setFechaPubInpcDestino(objRespDeflactado.getFechaPublicacionReciente());
                    conversiones.add(conv);
                }
            }
        }

        return conversiones;
    }
}
