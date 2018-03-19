/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;


/**
 * DTO para el tabla DYCC_ACCIONSEG
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccAccionSegDTO implements Serializable {

    @SuppressWarnings("compatibility:6823457903532739124")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Integer idAccionSeg;

    public DyccAccionSegDTO() {
    }

    public DyccAccionSegDTO(String descripcion, Integer idAccionSeg) {
        this.descripcion = descripcion;
        this.idAccionSeg = idAccionSeg;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdAccionSeg(Integer idAccionSeg) {
        this.idAccionSeg = idAccionSeg;
    }

    public Integer getIdAccionSeg() {
        return idAccionSeg;
    }
}
