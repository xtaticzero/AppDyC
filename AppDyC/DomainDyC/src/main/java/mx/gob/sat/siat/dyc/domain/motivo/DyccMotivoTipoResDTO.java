/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.motivo;

import java.io.Serializable;


/**
 * DTO de la tabla DYCC_MOTIVOTIPORES
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyccMotivoTipoResDTO implements Serializable {

    @SuppressWarnings("compatibility:-8408113123413305755")
    private static final long serialVersionUID = 1L;

    private DyccMotivoResDTO dyccMotivoResDTO;

    public DyccMotivoTipoResDTO() {
    }

    public DyccMotivoTipoResDTO(DyccMotivoResDTO dyccMotivoResDTO) {
        this.dyccMotivoResDTO = dyccMotivoResDTO;
    }

    public void setDyccMotivoResDTO(DyccMotivoResDTO dyccMotivoResDTO) {
        this.dyccMotivoResDTO = dyccMotivoResDTO;
    }

    public DyccMotivoResDTO getDyccMotivoResDTO() {
        return dyccMotivoResDTO;
    }
}
