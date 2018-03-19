/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.domain.anexo;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;


/**
 * DTO de la tabla DYCT_ANEXOREQ
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 * Actualizado por LADP Luis Alberto Dominguez Palomino
 */
public class DyctAnexoReqDTO implements Serializable {


    @SuppressWarnings("compatibility:7515177021012624469")
    private static final long serialVersionUID = 1L;
    private String nombreArchivo;
    private String url;
    private DyccAnexoTramiteDTO dyccAnexoTramiteDTO;
    private DyctReqInfoDTO dyctReqInfoDTO;
    private Integer procesado;

    public DyctAnexoReqDTO() {
    }

    public DyctAnexoReqDTO(DyccAnexoTramiteDTO dyccAnexoTramiteDTO, String nombreArchivo,
                           DyctReqInfoDTO dyctReqInfoDTO, String url, Integer procesado) {
        this.dyccAnexoTramiteDTO = dyccAnexoTramiteDTO;
        this.nombreArchivo = nombreArchivo;
        this.dyctReqInfoDTO = dyctReqInfoDTO;
        this.url = url;
        this.procesado = procesado;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDyccAnexoTramiteDTO(DyccAnexoTramiteDTO dyccAnexoTramiteDTO) {
        this.dyccAnexoTramiteDTO = dyccAnexoTramiteDTO;
    }

    public DyccAnexoTramiteDTO getDyccAnexoTramiteDTO() {
        return dyccAnexoTramiteDTO;
    }

    public void setDyctReqInfoDTO(DyctReqInfoDTO dyctReqInfoDTO) {
        this.dyctReqInfoDTO = dyctReqInfoDTO;
    }

    public DyctReqInfoDTO getDyctReqInfoDTO() {
        return dyctReqInfoDTO;
    }
    
    public void setProcesado(Integer procesado) {
        this.procesado = procesado;
    }

    public Integer getProcesado() {
        return procesado;
    }

}
