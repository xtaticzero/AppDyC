/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.caso;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para el catalogo DYCT_CASOPENDIENTE
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */

public class DyctCasoPendienteDTO implements Serializable {

    @SuppressWarnings("compatibility:-7346687788944385202")
    private static final long serialVersionUID = 1L;

    private Date fechaProcesamiento;
    private Date fechaFin;
    private Integer idConcepto;
    private Integer idDeclaracion;
    private Integer idEjercicio;
    private Integer idImpuesto;
    private Integer idPeriodo;
    private Integer numOperacion;
    private DyccMotivoCasoDTO dyccMotivoCasoDTO;

    public DyctCasoPendienteDTO() {
    }

    public void setFechaProcesamiento(Date fechaProcesamiento) {
        if (null != fechaProcesamiento) {
            this.fechaProcesamiento = (Date)fechaProcesamiento.clone();
        } else {
            this.fechaProcesamiento = null;
        }
    }

    public Date getFechaProcesamiento() {
        if (null != fechaProcesamiento) {
            return (Date)fechaProcesamiento.clone();
        } else {
            return null;
        }
    }
    
    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdDeclaracion(Integer idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public Integer getIdDeclaracion() {
        return idDeclaracion;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdImpuesto(Integer idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public Integer getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setNumOperacion(Integer numOperacion) {
        this.numOperacion = numOperacion;
    }

    public Integer getNumOperacion() {
        return numOperacion;
    }

    public void setDyccMotivoCasoDTO(DyccMotivoCasoDTO dyccMotivoCasoDTO) {
        this.dyccMotivoCasoDTO = dyccMotivoCasoDTO;
    }

    public DyccMotivoCasoDTO getDyccMotivoCasoDTO() {
        return dyccMotivoCasoDTO;
    }
}
