package mx.gob.sat.mat.dyc.background.declscomple.vo;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;





public class DeclaracionComplementariaVO 
{
    private Double cantidadAFavor;
    private Double saldoAFavor;
    private String idDeclaracion;
    private DycpCompensacionDTO dycpCompensacionDTO;

    public void setCantidadAFavor(Double cantidadAFavor) {
        this.cantidadAFavor = cantidadAFavor;
    }

    public Double getCantidadAFavor() {
        return cantidadAFavor;
    }

    public void setSaldoAFavor(Double saldoAFavor) {
        this.saldoAFavor = saldoAFavor;
    }

    public Double getSaldoAFavor() {
        return saldoAFavor;
    }

    public void setIdDeclaracion(String idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public String getIdDeclaracion() {
        return idDeclaracion;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }
}
