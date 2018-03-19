/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;


/**
 * DTO de la tabla DYCT_DECLARAICEP
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 * Actualizado por Luis Alberto Dominguez Palomino LADP
 */
public class DyctDeclaraIcepDTO implements Serializable {
    @SuppressWarnings("compatibility:-7968911314180840476")
    private static final long serialVersionUID = 1L;

    private DyctSaldoIcepDTO dyctSaldoIcepDTO;
    private Integer idDeclaraIcep;
    private Long numOperacion;
    private Date fechaPresentacion;
    private DyccTipoDeclaraDTO dyccTipoDeclaraDTO;
    private BigDecimal saldoAFavor;
    private Date validacionDWH;
    private Integer origenDeclara;
    private Date fechaRegistro;
    private String usrRegistro; 
    private String notas;
    private Date fechaFin;

    public DyctDeclaraIcepDTO() {
    }

    public DyctDeclaraIcepDTO(Date fechaPresentacion, Long numOperacion, DyccTipoDeclaraDTO dyccTipoDeclaraDTO,
                              BigDecimal saldoAFavor) {
        this.fechaPresentacion = fechaPresentacion != null ? (Date)fechaPresentacion.clone() : null;
        this.numOperacion = numOperacion;
        this.dyccTipoDeclaraDTO = dyccTipoDeclaraDTO;
        this.saldoAFavor = saldoAFavor;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setNumOperacion(Long numOperacion) {
        this.numOperacion = numOperacion;
    }

    public Long getNumOperacion() {
        return numOperacion;
    }

    public void setDyctSaldoIcepDTO(DyctSaldoIcepDTO dyctSaldoIcepDTO) {
        this.dyctSaldoIcepDTO = dyctSaldoIcepDTO;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepDTO() {
        return dyctSaldoIcepDTO;
    }

    public void setIdDeclaraIcep(Integer idDeclaraIcep) {
        this.idDeclaraIcep = idDeclaraIcep;
    }

    public Integer getIdDeclaraIcep() {
        return idDeclaraIcep;
    }

    public void setSaldoAFavor(BigDecimal saldoAFavor) {
        this.saldoAFavor = saldoAFavor;
    }

    public BigDecimal getSaldoAFavor() {
        return saldoAFavor;
    }

    public Date getValidacionDWH() {
        if (null != validacionDWH) {
            return (Date)validacionDWH.clone();
        } else {
            return null;
        }
    }

    public void setValidacionDWH(Date validacionDWH) {
        if (null != validacionDWH) {
            this.validacionDWH = (Date)validacionDWH.clone();
        } else {
            this.validacionDWH = null;
        }
    }
    
    public Integer getOrigenDeclara() {
        return origenDeclara;
    }

    public void setOrigenDeclara(Integer origenDeclara) {
        this.origenDeclara = origenDeclara;
    }

    public Date getFechaRegistro() {
        return (fechaRegistro != null) ? (Date) fechaRegistro.clone() : null;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = (fechaRegistro != null) ? (Date) fechaRegistro.clone() : null;
    }

    public String getUsrRegistro() {
        return usrRegistro;
    }

    public void setUsrRegistro(String usrRegistro) {
        this.usrRegistro = usrRegistro;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public void setDyccTipoDeclaraDTO(DyccTipoDeclaraDTO dyccTipoDeclaraDTO) {
        this.dyccTipoDeclaraDTO = dyccTipoDeclaraDTO;
    }

    public DyccTipoDeclaraDTO getDyccTipoDeclaraDTO() {
        return dyccTipoDeclaraDTO;
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin!=null){
            this.fechaFin = (Date)fechaFin.clone();
        }else{
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (this.fechaFin!=null){
            return (Date)fechaFin.clone();
        }
        return null;
    }
}
