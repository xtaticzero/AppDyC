/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para el catalogo SEGB_MOVIMIENTO
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class SegbMovimientoDTO implements Serializable {

    @SuppressWarnings("compatibility:-8408056215209888851")
    private static final long serialVersionUID = 1L;

    private String deptId;
    private Date fechaPresentacion;
    private Date fechaRegistro;
    private Date fechaTramite;
    private Integer folio;
    private String fSession;
    private Integer idMovimiento;
    private Integer idProceso;
    private Integer idTipoPersona;
    private Integer idTmenu;
    private String observaciones;
    private String usuario;
    private String identificador;

    public SegbMovimientoDTO() {
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
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

    public void setFechaTramite(Date fechaTramite) {
        if (null != fechaTramite) {
            this.fechaTramite = (Date)fechaTramite.clone();
        } else {
            this.fechaTramite = null;
        }
    }

    public Date getFechaTramite() {
        if (null != fechaTramite) {
            return (Date)fechaTramite.clone();
        } else {
            return null;
        }
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFSession(String fSession) {
        this.fSession = fSession;
    }

    public String getFSession() {
        return fSession;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdTipoPersona(Integer idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }

    public Integer getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setIdTmenu(Integer idTmenu) {
        this.idTmenu = idTmenu;
    }

    public Integer getIdTmenu() {
        return idTmenu;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getParameterReport() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@" );
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("deptId=");
        buffer.append(getDeptId());
        buffer.append(',');
        buffer.append("fechaPresentacion=");
        buffer.append(getFechaPresentacion());
        buffer.append(',');
        buffer.append("fechaRegistro=");
        buffer.append(getFechaRegistro());
        buffer.append(',');
        buffer.append("fechaTramite=");
        buffer.append(getFechaTramite());
        buffer.append(',');
        buffer.append("folio=");
        buffer.append(getFolio());
        buffer.append(',');
        buffer.append("fSession=");
        buffer.append(getFSession());
        buffer.append(',');
        buffer.append("idMovimiento=");
        buffer.append(getIdMovimiento());
        buffer.append(',');
        buffer.append("idProceso=");
        buffer.append(getIdProceso());
        buffer.append(',');
        buffer.append("idTipoPersona=");
        buffer.append(getIdTipoPersona());
        buffer.append(',');
        buffer.append("idtMenu=");
        buffer.append(getIdTmenu());
        buffer.append(',');
        buffer.append("observaciones=");
        buffer.append(getObservaciones());
        buffer.append(',');
        buffer.append("usuario=");
        buffer.append(getUsuario());
        buffer.append(',');
        buffer.append("identificador=");
        buffer.append(getIdentificador());
        buffer.append(']');
        return buffer.toString();
    }
}
