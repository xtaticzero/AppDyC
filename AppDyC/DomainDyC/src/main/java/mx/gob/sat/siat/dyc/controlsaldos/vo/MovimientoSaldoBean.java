package mx.gob.sat.siat.dyc.controlsaldos.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.apache.log4j.Logger;


public class MovimientoSaldoBean
{
    private static final Logger LOG = Logger.getLogger(MovimientoSaldoBean.class);
    private Integer tipo;
    private Double importe;
    private Date fecha;
    //TODO: quitar el formateador de aqui
    private SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
    private String origen;
    private Date validacionDWH;

    public MovimientoSaldoBean(){
    }

    public MovimientoSaldoBean(Integer t, Double i, String f)
    {
        tipo = t;
        importe = i;
        try {
            fecha = formateador.parse(f);
        } catch (ParseException e) {
            LOG.error("Ocurrio un error en MovimientoSaldoBean, mensaje ->" + e.getMessage());
        }
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Date getFecha() {
        return (fecha != null) ? (Date) fecha.clone() : null;
    }

    public void setFecha(Date fecha) {
        this.fecha = (fecha != null) ? (Date) fecha.clone() : null;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Date getValidacionDWH() {
        return validacionDWH != null ? (Date) validacionDWH.clone() : null;
    }

    public void setValidacionDWH(Date validacionDWH) {
        this.validacionDWH = validacionDWH != null ? (Date) validacionDWH.clone() : null;
    }
    
}
