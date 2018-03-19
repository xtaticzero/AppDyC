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

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;


/**
 * DTO de la tabla DYCT_ARCHIVO
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyctArchivoDTO implements Serializable {

    @SuppressWarnings("compatibility:-4824081064016486238")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaRegistro;
    private Integer idArchivo;
    private int ocultoContribuyente;
    private String nombreArchivo;
    private String url;
    private DycpServicioDTO dycpServicioDTO;

    public DyctArchivoDTO() {
    }

    public DyctArchivoDTO(String descripcion, Date fechaRegistro, Integer idArchivo, String nombreArchivo,
                          DycpServicioDTO dycpServicioDTO, String url) {
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro != null ? (Date)fechaRegistro.clone() : null;
        this.idArchivo = idArchivo;
        this.nombreArchivo = nombreArchivo;
        this.dycpServicioDTO = dycpServicioDTO;
        this.url = url;
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

    public void setIdArchivo(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Integer getIdArchivo() {
        return idArchivo;
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }

    public void setOcultoContribuyente(int ocultoContribuyente) {
        this.ocultoContribuyente = ocultoContribuyente;
    }

    public int getOcultoContribuyente() {
        return ocultoContribuyente;
    }
}
