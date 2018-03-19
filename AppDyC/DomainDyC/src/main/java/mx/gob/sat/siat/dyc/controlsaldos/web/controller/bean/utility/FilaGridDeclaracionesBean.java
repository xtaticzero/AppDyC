package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.util.Date;


public class FilaGridDeclaracionesBean
{
    private Integer idDeclaraIcep;
    private Date fechaPresentacion;
    private Long numOperacion;
    private Integer idTipoDeclaracion;
    private String tipoDeclaracion;
    private Double importeDeclarado;
    private String validadaDWH;
    private String origenDeclara;
    private String notas;
    private String descrOrigen;
    private Boolean efectiva;
    private String infoAdicional;
    private Date fechaFin;

    //se ocupan para enviar el valor formateado al reporte
    private String fechaPresentacionStr;
    private String importeDeclaradoStr;

    public Date getFechaPresentacion() {
        if (fechaPresentacion != null) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (fechaPresentacion != null) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Long getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(Long numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setTipoDeclaracion(String tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public Double getImporteDeclarado() {
        return importeDeclarado;
    }

    public void setImporteDeclarado(Double importeDeclarado) {
        this.importeDeclarado = importeDeclarado;
    }

    public String getFechaPresentacionStr() {
        return fechaPresentacionStr;
    }

    public void setFechaPresentacionStr(String fechaPresentacionStr) {
        this.fechaPresentacionStr = fechaPresentacionStr;
    }

    public String getImporteDeclaradoStr() {
        return importeDeclaradoStr;
    }

    public void setImporteDeclaradoStr(String importeDeclaradoStr) {
        this.importeDeclaradoStr = importeDeclaradoStr;
    }

    public String getValidadaDWH() {
        return validadaDWH;
    }

    public void setValidadaDWH(String validadaDWH) {
        this.validadaDWH = validadaDWH;
    }

    public Integer getIdTipoDeclaracion() {
        return idTipoDeclaracion;
    }

    public void setIdTipoDeclaracion(Integer idTipoDeclaracion) {
        this.idTipoDeclaracion = idTipoDeclaracion;
    }

    public String getOrigenDeclara() {
        return origenDeclara;
    }

    public void setOrigenDeclara(String origenDeclara) {
        this.origenDeclara = origenDeclara;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getDescrOrigen() {
        return descrOrigen;
    }

    public void setDescrOrigen(String descrOrigen) {
        this.descrOrigen = descrOrigen;
    }

    public Integer getIdDeclaraIcep() {
        return idDeclaraIcep;
    }

    public void setIdDeclaraIcep(Integer idDeclaraIcep) {
        this.idDeclaraIcep = idDeclaraIcep;
    }

    public Boolean getEfectiva() {
        return efectiva;
    }

    public void setEfectiva(Boolean efectiva) {
        this.efectiva = efectiva;
    }

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin !=null){
            this.fechaFin = (Date)fechaFin.clone();
        }else{
            this.fechaFin = null;
            }
    }

    public Date getFechaFin() {
        if (fechaFin !=null){
            return (Date)fechaFin.clone();
            }
        return null;
    }
}
