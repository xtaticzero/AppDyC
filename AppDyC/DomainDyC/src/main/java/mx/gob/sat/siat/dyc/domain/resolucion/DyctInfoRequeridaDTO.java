/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

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

import java.util.List;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;


/**
 * DTO de la tabla DYCT_INFOREQUERIDA
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctInfoRequeridaDTO implements Serializable {


    @SuppressWarnings("compatibility:-3361865791424617734")
    private static final long serialVersionUID = 1L;
    
    private DyctReqInfoDTO dyctReqInfoDTO;
    private DyccInfoTramiteDTO dyccInfoTramiteDTO;
    private transient List<DyctArchivoInfReqDTO> listaDyctArchivoInfReqDTO;

    public DyctInfoRequeridaDTO() {
    }

    public DyctInfoRequeridaDTO(DyccInfoTramiteDTO dyccInfoTramiteDTO, DyctReqInfoDTO dyctReqInfoDTO) {
        this.dyccInfoTramiteDTO = dyccInfoTramiteDTO;
        this.dyctReqInfoDTO = dyctReqInfoDTO;
    }

    public void setDyctReqInfoDTO(DyctReqInfoDTO dyctReqInfoDTO) {
        this.dyctReqInfoDTO = dyctReqInfoDTO;
    }

    public DyctReqInfoDTO getDyctReqInfoDTO() {
        return dyctReqInfoDTO;
    }

    public void setDyccInfoTramiteDTO(DyccInfoTramiteDTO dyccInfoTramiteDTO) {
        this.dyccInfoTramiteDTO = dyccInfoTramiteDTO;
    }

    public DyccInfoTramiteDTO getDyccInfoTramiteDTO() {
        return dyccInfoTramiteDTO;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@");
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append(']');
        return buffer.toString();
    }

    public void setListaDyctArchivoInfReqDTO(List<DyctArchivoInfReqDTO> listaDyctArchivoInfReqDTO) {
        this.listaDyctArchivoInfReqDTO = listaDyctArchivoInfReqDTO;
    }

    public List<DyctArchivoInfReqDTO> getListaDyctArchivoInfReqDTO() {
        return listaDyctArchivoInfReqDTO;
    }
}
