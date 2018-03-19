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

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;


/**
 * DTO de la tabla DYCT_ARCHIVOAVISO
 * @author  Alfredo Ramirez
 * @since   17/07/2014
 */
public class DyctArchivoAvisoDTO implements Serializable {

    @SuppressWarnings("compatibility:-3177648027068796133")
    private static final long serialVersionUID = 1L;

    private Integer idArchivoAviso;
    private String nombreArchivo;
    private String url;
    private String descripcion;
    private Date fechaRegistro;
    private DycpAvisoCompDTO dycpAvisoCompDTO;

    public void setIdArchivoAviso(Integer idArchivoAviso) {
        this.idArchivoAviso = idArchivoAviso;
    }

    public Integer getIdArchivoAviso() {
        return idArchivoAviso;
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if (null != fechaRegistro) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setDycpAvisoCompDTO(DycpAvisoCompDTO dycpAvisoCompDTO) {
        this.dycpAvisoCompDTO = dycpAvisoCompDTO;
    }

    public DycpAvisoCompDTO getDycpAvisoCompDTO() {
        return dycpAvisoCompDTO;
    }
}
