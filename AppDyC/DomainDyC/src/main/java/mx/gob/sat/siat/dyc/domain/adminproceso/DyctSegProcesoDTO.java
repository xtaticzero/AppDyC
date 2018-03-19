/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.adminproceso;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para el catalogo DYCT_SEGPROCESO
 * @author  Alfredo Ramirez
 * @since   25/04/2014
 */
public class DyctSegProcesoDTO implements Serializable {


    @SuppressWarnings("compatibility:-6093959440280529436")
    private static final long serialVersionUID = 1L;
    
    private Integer intentos;
    private Integer numMaxIntentos;
    private Integer prioridad;
    private Date horaEjecucion;
    private Integer ejecucion;
    private transient DyctProcesosDTO dyctProcesosDTO;
    private transient DyccStatusProcesoDTO dyccStatusProcesoDTO;

    public DyctSegProcesoDTO() {
    }

    public DyctSegProcesoDTO(Integer intentos, Integer numMaxIntentos, Integer prioridad, Date horaEjecucion,
                             Integer ejecucion) {
        this.intentos = intentos;
        this.numMaxIntentos = numMaxIntentos;
        this.prioridad = prioridad;
        this.horaEjecucion = horaEjecucion != null ? (Date)horaEjecucion.clone() : null;
        this.ejecucion = ejecucion;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setNumMaxIntentos(Integer numMaxIntentos) {
        this.numMaxIntentos = numMaxIntentos;
    }

    public Integer getNumMaxIntentos() {
        return numMaxIntentos;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public Date getHoraEjecucion() {
        if (horaEjecucion != null) {
            return (Date)horaEjecucion.clone();
        } else {
            return null;
        }
    }

    public void setHoraEjecucion(Date horaEjecucion) {
        if (horaEjecucion != null) {
            this.horaEjecucion = (Date)horaEjecucion.clone();
        } else {
            this.horaEjecucion = null;
        }
    }

    public void setDyctProcesosDTO(DyctProcesosDTO dyctProcesosDTO) {
        this.dyctProcesosDTO = dyctProcesosDTO;
    }

    public DyctProcesosDTO getDyctProcesosDTO() {
        return dyctProcesosDTO;
    }

    public void setDyccStatusProcesoDTO(DyccStatusProcesoDTO dyccStatusProcesoDTO) {
        this.dyccStatusProcesoDTO = dyccStatusProcesoDTO;
    }

    public DyccStatusProcesoDTO getDyccStatusProcesoDTO() {
        return dyccStatusProcesoDTO;
    }

    public void setEjecucion(Integer ejecucion) {
        this.ejecucion = ejecucion;
    }

    public Integer getEjecucion() {
        return ejecucion;
    }
}
