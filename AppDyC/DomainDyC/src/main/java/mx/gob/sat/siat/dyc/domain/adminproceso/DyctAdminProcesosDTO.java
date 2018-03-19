/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.adminproceso;

import java.util.Date;

/**
 * DTO para el catalogo [DYCT_ADMINPROCESOS]
 * @author  Alfredo Ramirez
 * @since   25/04/2014
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 *
 * @date Agosto 19, 2015
 */
public class DyctAdminProcesosDTO {

    private Integer idAdministrador;
    private Date horaEjecucion;
    private Integer statusAdmin;

    public DyctAdminProcesosDTO() {
    }

    public DyctAdminProcesosDTO(Integer idAdministrador, Date horaEjecucion, Integer statusAdministrador) {
        this.idAdministrador = idAdministrador;
        this.horaEjecucion = horaEjecucion != null ? (Date)horaEjecucion.clone() : null;
        this.statusAdmin = statusAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
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

    public void setStatusAdmin(Integer statusAdmin) {
        this.statusAdmin = statusAdmin;
    }

    public Integer getStatusAdmin() {
        return statusAdmin;
    }

}
