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
 * DTO de la tabla DYCC_TIPODOCUMENTO
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccTipoDocumentoDTO implements Serializable {

    @SuppressWarnings("compatibility:-7579824594439043611")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Integer idTipoDocumento;

    public DyccTipoDocumentoDTO() {
    }

    public DyccTipoDocumentoDTO(Integer idTipoDocumento, String descripcion) {
        this.descripcion = descripcion;
        this.idTipoDocumento = idTipoDocumento;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }
}
