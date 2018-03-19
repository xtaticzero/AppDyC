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
 * DTO de la tabla DYCT_REQCONFPROV
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctReqConfProvDTO implements Serializable {

    @SuppressWarnings("compatibility:-4135641697708597599")
    private static final long serialVersionUID = 1L;

    private String rfcProveedor;
    private DyctDocumentoDTO dyctDocumentoDTO;

    public DyctReqConfProvDTO() {
    }

    public DyctReqConfProvDTO(DyctDocumentoDTO dyctDocumentoDTO, String rfcProveedor) {
        this.dyctDocumentoDTO = dyctDocumentoDTO;
        this.rfcProveedor = rfcProveedor;
    }

    public void setRfcProveedor(String rfcProveedor) {
        this.rfcProveedor = rfcProveedor;
    }

    public String getRfcProveedor() {
        return rfcProveedor;
    }

    public void setDyctDocumentoDTO(DyctDocumentoDTO dyctDocumentoDTO) {
        this.dyctDocumentoDTO = dyctDocumentoDTO;
    }

    public DyctDocumentoDTO getDyctDocumentoDTO() {
        return dyctDocumentoDTO;
    }
}
