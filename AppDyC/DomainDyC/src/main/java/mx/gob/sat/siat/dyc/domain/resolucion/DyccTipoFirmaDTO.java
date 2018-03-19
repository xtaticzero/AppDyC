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
 * DTO para la tabla DYCC_TIPOFIRMA
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccTipoFirmaDTO implements Serializable {

    @SuppressWarnings("compatibility:1617276027773959676")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Integer idTipoFirma;

    public DyccTipoFirmaDTO() {
    }

    public DyccTipoFirmaDTO(String descripcion, Integer idTipoFirma) {
        this.descripcion = descripcion;
        this.idTipoFirma = idTipoFirma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdTipoFirma(Integer idTipoFirma) {
        this.idTipoFirma = idTipoFirma;
    }

    public Integer getIdTipoFirma() {
        return idTipoFirma;
    }
}
