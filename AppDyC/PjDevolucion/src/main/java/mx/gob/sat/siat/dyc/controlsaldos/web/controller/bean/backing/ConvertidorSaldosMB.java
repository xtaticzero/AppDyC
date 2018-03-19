package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sat.siat.dyc.controlsaldos.service.impl.ConvertidorSaldosBussinesDel;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridConversionesBean;

import org.apache.log4j.Logger;


@ManagedBean(name = "mbConvertidorSaldo")
@ViewScoped
public class ConvertidorSaldosMB
{
    private static final Logger LOG = Logger.getLogger(ConvertidorSaldosMB.class);

    @ManagedProperty("#{bdConvertidorSaldos}")
    private ConvertidorSaldosBussinesDel bussinesDel;

    private Date fechaOrigen;
    private BigDecimal cantidadBase;
    private Date fechaDestino;
    private Boolean mostrarDlg;

    private List<FilaGridConversionesBean> conversiones;

    public void abrirDialog(){
        mostrarDlg = Boolean.TRUE;
    }

    public void cerrarDialog(){
        mostrarDlg = Boolean.FALSE;
        limpiar();
    }

    public void limpiar(){
        fechaOrigen = null;
        cantidadBase = null;
        fechaDestino = null;
        conversiones = null;
    }

    public void convertir()
    {
        LOG.debug("INICIO convertir");
        Map<String, Object> paramsConvSaldos = new HashMap<String, Object> ();
        paramsConvSaldos.put("fechaOrigen", fechaOrigen);
        paramsConvSaldos.put("cantidadBase", cantidadBase);
        paramsConvSaldos.put("fechaDestino", fechaDestino);
        conversiones = bussinesDel.convertir(paramsConvSaldos);
    }

    public Date getFechaOrigen() {
        return (fechaOrigen != null) ? (Date) fechaOrigen.clone() : null;
    }

    public void setFechaOrigen(Date fechaOrigen) {
        this.fechaOrigen = (fechaOrigen != null) ? (Date) fechaOrigen.clone() : null;
    }

    public BigDecimal getCantidadBase() {
        return cantidadBase;
    }

    public void setCantidadBase(BigDecimal cantidadBase) {
        this.cantidadBase = cantidadBase;
    }

    public Date getFechaDestino() {
        return (fechaDestino != null) ? (Date) fechaDestino.clone() : null;
    }

    public void setFechaDestino(Date fechaDestino) {
        this.fechaDestino = (fechaDestino != null) ? (Date) fechaDestino.clone() : null;
    }

    public List<FilaGridConversionesBean> getConversiones() {
        return conversiones;
    }

    public void setConversiones(List<FilaGridConversionesBean> conversiones) {
        this.conversiones = conversiones;
    }

    public ConvertidorSaldosBussinesDel getBussinesDel() {
        return bussinesDel;
    }

    public void setBussinesDel(ConvertidorSaldosBussinesDel bussinesDel) {
        this.bussinesDel = bussinesDel;
    }

    public Boolean getMostrarDlg() {
        return mostrarDlg;
    }

    public void setMostrarDlg(Boolean mostrarDlg) {
        this.mostrarDlg = mostrarDlg;
    }
}
