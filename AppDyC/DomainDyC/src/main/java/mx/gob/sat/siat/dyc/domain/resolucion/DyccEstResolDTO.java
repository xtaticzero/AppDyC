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
 * DTO de la tabla DYCC_ESTRESOL
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccEstResolDTO implements Serializable {

    @SuppressWarnings("compatibility:-5806812676352527225")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Integer idEstResol;

    public DyccEstResolDTO() {
    }

    public DyccEstResolDTO(Integer idEstResol, String descripcion) {
        this.descripcion = descripcion;
        this.idEstResol = idEstResol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdEstResol(Integer idEstResol) {
        this.idEstResol = idEstResol;
    }

    public Integer getIdEstResol() {
        return idEstResol;
    }
}
