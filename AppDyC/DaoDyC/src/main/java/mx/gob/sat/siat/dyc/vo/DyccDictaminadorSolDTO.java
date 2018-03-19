
/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;


/**
 * DTO para el catalogo de DYCC_DICTAMINADOR
 * @author Israel Chavez
 */
public class DyccDictaminadorSolDTO extends DyccDictaminadorDTO implements Serializable {

    @SuppressWarnings("compatibility:-5268860365275479252")
    private static final long serialVersionUID = 1L;

    public DyccDictaminadorSolDTO() {
        super();
    }

    private String numControl;
    private String tipoTramite;
    private String puntosAsignar;
    private Date fechaRegistro;
    private int idTipoTramite;

    public String getSeacrhParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("numControl:").append(this.getNumControl()).append(", ");
        sb.append("tipoTramite:").append(this.getTipoTramite()).append(", ");
        sb.append("puntosAsignar:").append(this.getPuntosAsignar()).append(", ");
        sb.append("idTipoTramite:").append(this.getIdTipoTramite()).append(", ");
        sb.append("fechaRegistro:").append(this.getFechaRegistro());

        return sb.toString();
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setPuntosAsignar(String puntosAsignar) {
        this.puntosAsignar = puntosAsignar;
    }

    public String getPuntosAsignar() {
        return puntosAsignar;
    }


    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if (fechaRegistro != null) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }
}
