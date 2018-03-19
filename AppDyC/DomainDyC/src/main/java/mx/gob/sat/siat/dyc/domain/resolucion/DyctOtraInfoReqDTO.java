/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;


/**
 * DTO de la tabla DYCT_OTRAINFOREQ
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctOtraInfoReqDTO implements Serializable {

    @SuppressWarnings("compatibility:-4835195421910063126")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Integer idOtraInfoReq;
    private DyctReqInfoDTO dyctReqInfoDTO;

    public DyctOtraInfoReqDTO() {
    }

    public DyctOtraInfoReqDTO(String descripcion, Integer idOtraInfoReq, DyctReqInfoDTO dyctReqInfoDTO) {
        this.descripcion = descripcion;
        this.idOtraInfoReq = idOtraInfoReq;
        this.dyctReqInfoDTO = dyctReqInfoDTO;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdOtraInfoReq(Integer idOtraInfoReq) {
        this.idOtraInfoReq = idOtraInfoReq;
    }

    public Integer getIdOtraInfoReq() {
        return idOtraInfoReq;
    }

    public void setDyctReqInfoDTO(DyctReqInfoDTO dyctReqInfoDTO) {
        this.dyctReqInfoDTO = dyctReqInfoDTO;
    }

    public DyctReqInfoDTO getDyctReqInfoDTO() {
        return dyctReqInfoDTO;
    }    
}
