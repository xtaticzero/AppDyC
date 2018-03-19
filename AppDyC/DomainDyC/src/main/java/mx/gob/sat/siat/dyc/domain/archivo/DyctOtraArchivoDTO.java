/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.domain.archivo;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;


/**
 * DTO de la tabla DYCT_OTRAARCHIVO
 * @author  Alfredo Ramirez
 * @since   23/04/2014
 */
public class DyctOtraArchivoDTO implements Serializable {

    @SuppressWarnings("compatibility:4429485505456632075")
    private static final long serialVersionUID = 1L;

    private DyctOtraInfoReqDTO dyctOtraInfoReqDTO;
    private Integer numeroArchivo;
    private String url;
    private String nombreArchivo;

    public DyctOtraArchivoDTO() {
    }

    public DyctOtraArchivoDTO(Integer numeroArchivo, String url, String nombreArchivo) {
        this.numeroArchivo = numeroArchivo;
        this.url = url;
        this.nombreArchivo = nombreArchivo;
    }

    public void setNumeroArchivo(Integer numeroArchivo) {
        this.numeroArchivo = numeroArchivo;
    }

    public Integer getNumeroArchivo() {
        return numeroArchivo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setDyctOtraInfoReqDTO(DyctOtraInfoReqDTO dyctOtraInfoReqDTO) {
        this.dyctOtraInfoReqDTO = dyctOtraInfoReqDTO;
    }

    public DyctOtraInfoReqDTO getDyctOtraInfoReqDTO() {
        return dyctOtraInfoReqDTO;
    }
}
