/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.vo;

import java.sql.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;


/**
 * @author Federico Chopin Gachuz
 * @date Abril 14, 2014
 *
 **/
public class SolicitudConsultarExpedienteVO extends DycpSolicitudDTO {

    @SuppressWarnings("compatibility:-2838078541537983290")
    private static final long serialVersionUID = 1L;

    public SolicitudConsultarExpedienteVO() {
        super();
    }
      
    private String descTramite;
    private String estadoSolicitud;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String descImpuesto;
    private String descConcepto;
    private String descPeriodo;
    private String clabe;
    private String descBanco;
    private String descSubOrigSaldo;
    private String descTipoServicio;
    private String descDeclaracion;
    private Integer idSaldoIcep;
    private String origenDevolucion;
    private Date fechaPresentaDec;


    public void setDescTramite(String descTramite) {
        this.descTramite = descTramite;
    }

    public String getDescTramite() {
        return descTramite;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setDescImpuesto(String descImpuesto) {
        this.descImpuesto = descImpuesto;
    }

    public String getDescImpuesto() {
        return descImpuesto;
    }

    public void setDescConcepto(String descConcepto) {
        this.descConcepto = descConcepto;
    }

    public String getDescConcepto() {
        return descConcepto;
    }

    public void setDescPeriodo(String descPeriodo) {
        this.descPeriodo = descPeriodo;
    }

    public String getDescPeriodo() {
        return descPeriodo;
    }

    public void setDescBanco(String descBanco) {
        this.descBanco = descBanco;
    }

    public String getDescBanco() {
        return descBanco;
    }

    public void setDescSubOrigSaldo(String descSubOrigSaldo) {
        this.descSubOrigSaldo = descSubOrigSaldo;
    }

    public String getDescSubOrigSaldo() {
        return descSubOrigSaldo;
    }

    public void setDescTipoServicio(String descTipoServicio) {
        this.descTipoServicio = descTipoServicio;
    }

    public String getDescTipoServicio() {
        return descTipoServicio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setDescDeclaracion(String descDeclaracion) {
        this.descDeclaracion = descDeclaracion;
    }

    public String getDescDeclaracion() {
        return descDeclaracion;
    }


    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public String getClabe() {
        return clabe;
    }

    public void setOrigenDevolucion(String origenDevolucion) {
        this.origenDevolucion = origenDevolucion;
    }

    public String getOrigenDevolucion() {
        return origenDevolucion;
    }

    public void setFechaPresentaDec(Date fechaPresentaDec) {
        if (null != fechaPresentaDec) {
            this.fechaPresentaDec = (Date)fechaPresentaDec.clone();
        } else {
            this.fechaPresentaDec = null;
        }
    }

    public java.util.Date getFechaPresentaDec() {
        if (null != fechaPresentaDec) {
            return (java.util.Date)fechaPresentaDec.clone();
        } else {
            return null;
        }
    }

}
