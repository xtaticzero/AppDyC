package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;


public class FilaGridFacturasProvBean
{
    private String numFactura;
    private String fecha;
    private String concepto;
    private String importe;
    private String ivaTrasladado;
    private String total;

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getIvaTrasladado() {
        return ivaTrasladado;
    }

    public void setIvaTrasladado(String ivaTrasladado) {
        this.ivaTrasladado = ivaTrasladado;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void cargar(DyctFacturaReqDTO dto)
    {
        this.setNumFactura(dto.getNumeroFactura());
        this.setFecha(dto.getFechaEmision().toString());
        this.setConcepto(dto.getConcepto());
        this.setImporte(dto.getImporte().toString());
        this.setIvaTrasladado(dto.getIvaTrasladado().toString());
        this.setTotal(dto.getTotal().toString());
    }
}