/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.declaracion;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;


/**
 * DTO para la tabla DYCT_NOTA
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyctNotaDTO implements Serializable {

    @SuppressWarnings("compatibility:-4152700932340959443")
    private static final long serialVersionUID = 1L;

    private Date fecha;
    private Integer idNota;
    private String texto;
    private String usuario;
    private DycpServicioDTO dycpServicioDTO;

    public DyctNotaDTO() {
    }

    public DyctNotaDTO(Date fecha, Integer idNota, DycpServicioDTO dycpServicioDTO, String texto, String usuario) {
        this.fecha = fecha != null ? (Date)fecha.clone() : null;
        this.idNota = idNota;
        this.dycpServicioDTO = dycpServicioDTO;
        this.texto = texto;
        this.usuario = usuario;
    }

    public Date getFecha() {
        if (null != fecha) {
            return (Date)fecha.clone();
        } else {
            return null;
        }
    }

    public void setFecha(Date fecha) {
        if (null != fecha) {
            this.fecha = (Date)fecha.clone();
        } else {
            this.fecha = null;
        }
    }

    public void setIdNota(Integer idNota) {
        this.idNota = idNota;
    }

    public Integer getIdNota() {
        return idNota;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }
}
