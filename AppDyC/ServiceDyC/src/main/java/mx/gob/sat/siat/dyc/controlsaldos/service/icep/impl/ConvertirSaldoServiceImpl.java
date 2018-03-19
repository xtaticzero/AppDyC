package mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Date;

import mx.gob.sat.siat.cobranza.herramienta.actualizador.dto.ActualizacionDetalleDTO;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.facade.ActualizadorFacade;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "convertirSaldoService")
public class ConvertirSaldoServiceImpl
{
    private static final Logger LOG = Logger.getLogger(ConvertirSaldoServiceImpl.class);

    @Autowired(required = false)
    private ActualizadorFacade actualizadorCbza;

    public Double execute(Date fechaOrigen, Date fechaDestino, double importe)
    {
        double saldoConvertido = importe;

        if(!DateUtils.isSameDay(fechaOrigen, fechaDestino))
        {
            if(fechaOrigen.before(fechaDestino)){
                LOG.debug("la fecha origen es anterior a la fecha destino, se actualizará");
                ActualizacionDetalleDTO objRespActualizacion = actualizadorCbza.calcularActualizacion(fechaOrigen, fechaDestino,new BigDecimal(importe));
                if(objRespActualizacion.getFactorActzn() != null){
                        saldoConvertido = objRespActualizacion.getImporteActualizado().doubleValue();
                }
                else{
                    LOG.error("Ocurrio un error en el servicio actualizador FactorActzn == null; parametros enviados -> " +
                        "fechaOrigen:" + fechaOrigen +";  fechaDestino:" + fechaDestino + "; importe:" + importe + ";");
                }
            }
            else{
                LOG.debug("la fecha origen NO   es anterior a la fecha destino, se deflactará");
                LOG.debug("fechaOrigen ->" + fechaOrigen + "<-");
                LOG.debug("fechaDestino ->" + fechaDestino + "<-");
                ActualizacionDetalleDTO objRespDeflactado = actualizadorCbza.calcularDeflactacion(fechaDestino, fechaOrigen, new BigDecimal(importe));
                if(objRespDeflactado.getFactorActzn() != null){
                        saldoConvertido = objRespDeflactado.getImporteActualizado().doubleValue();
                }
            }
        }
        LOG.debug("" + importe + "  " + fechaOrigen  + " ========== " + saldoConvertido + "  " + fechaDestino);
        return saldoConvertido;
    }

    public Double convertirExacto(Date fechaOrigen, Date fechaDestino, double importe)
    {
        double saldoConvertido = importe;

        if(!DateUtils.isSameDay(fechaOrigen, fechaDestino))
        {
            LOG.debug("las fechas no son el mismo dia");
            if(fechaOrigen.before(fechaDestino)){
                LOG.debug("la fecha origen es anterior a la fecha destino, se actualizará");
                ActualizacionDetalleDTO objRespActualizacion = actualizadorCbza.calcularActualizacion(fechaOrigen, fechaDestino,new BigDecimal(importe));
                if(objRespActualizacion.getFactorActzn() != null){
                    if(objRespActualizacion.getFactorActzn().doubleValue() > 1d)
                    {
                        float factorActualizacion = objRespActualizacion.getInpcReciente().floatValue() / objRespActualizacion.getInpcAntiguo().floatValue();
                        saldoConvertido =   objRespActualizacion.getImporte().multiply(new BigDecimal(factorActualizacion)).doubleValue();
                        saldoConvertido = Math.round(saldoConvertido);
                        LOG.debug("factorActualizacion HCL->" + factorActualizacion + "<-");
                    }
                    else{
                        saldoConvertido = objRespActualizacion.getImporteActualizado().doubleValue();
                    }
                    LOG.debug("saldoConvertido->" + saldoConvertido + "<-");
                }
                else{
                    LOG.error("Ocurrio un error en el servicio actualizador FactorActzn == null; parametros enviados -> " +
                        "fechaOrigen:" + fechaOrigen +";  fechaDestino:" + fechaDestino + "; importe:" + importe + ";");
                }
            }
            else{
                LOG.debug("la fecha origen NO   es anterior a la fecha destino, se deflactará");
                LOG.debug("fechaOrigen ->" + fechaOrigen + "<-");
                LOG.debug("fechaDestino ->" + fechaDestino + "<-");
                ActualizacionDetalleDTO objRespDeflactado = actualizadorCbza.calcularDeflactacion(fechaDestino, fechaOrigen, new BigDecimal(importe));
                if(objRespDeflactado.getFactorActzn() != null){
                    if(objRespDeflactado.getFactorActzn().doubleValue() > 1d){
                        BigDecimal nFA = objRespDeflactado.getInpcReciente().divide(objRespDeflactado.getInpcAntiguo(), ConstantesDyCNumerico.VALOR_8, RoundingMode.HALF_UP);
                        LOG.debug("nuevo factor de actualizacion ->" + nFA + "<-");
                        saldoConvertido = objRespDeflactado.getImporte().divide(nFA, ConstantesDyCNumerico.VALOR_8, RoundingMode.HALF_UP).doubleValue();
                        saldoConvertido = Math.round(saldoConvertido);
                    }
                    else{
                        saldoConvertido = objRespDeflactado.getImporteActualizado().doubleValue();
                    }
                }
            }
        }
        LOG.debug("" + importe + "  " + fechaOrigen  + " ========== " + saldoConvertido + "  " + fechaDestino);
        return saldoConvertido;
    }

}
