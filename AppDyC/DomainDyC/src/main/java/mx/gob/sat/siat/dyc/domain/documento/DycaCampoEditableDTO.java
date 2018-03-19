/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.documento;

import java.io.Serializable;


/**
 * DTO de la tabla DYCA_CAMPOEDITABLE
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DycaCampoEditableDTO implements Serializable {

    @SuppressWarnings("compatibility:279465436847886487")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Integer idCampo;
    private Integer idPlantilla;
    private String idTag;

    public DycaCampoEditableDTO() {
    }

    public DycaCampoEditableDTO(String descripcion, Integer idCampo, String idTag) {
        this.descripcion = descripcion;
        this.idCampo = idCampo;
        this.idTag = idTag;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdCampo(Integer idCampo) {
        this.idCampo = idCampo;
    }

    public Integer getIdCampo() {
        return idCampo;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

    public String getIdTag() {
        return idTag;
    }
}
