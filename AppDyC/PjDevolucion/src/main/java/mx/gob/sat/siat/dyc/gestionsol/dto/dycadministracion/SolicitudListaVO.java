/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dto.dycadministracion;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.util.constante.ConstantesCaracteres;


/**
 * Clase DTO para CASO DE USO ADMINISTRACION SOLICITUDES DEVOLUCION.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 * <BR/>
 * <TABLE BORDER='1'>
 * <TR><TH> COLUMNA </TD><TD>NOMBRE</TH></TR>
 * <TR><TD>S.CLAVEADM </TD><TD> CLAVEADM</TD></TR>
 * <TR><TD>U.NOMBRE </TD><TD> ADMINISTRACION</TD></TR>
 * <TR><TD>D.NUMEMPLEADO  </TD><TD> NUMEMPLEADO</TD></TR>
 * <TR><TD>D.NOMBRECOMPLETO </TD><TD> DICTAMINADOR</TD></TR>
 * <TR><TD>S.NUMCONTROL </TD><TD> NUMCONTROL</TD></TR>
 * <TR><TD>S.RFCCONTRIBUYENTE </TD><TD> RFCCONTRIBUYENTE</TD></TR>
 * <TR><TD>'Solicitud de Devolución' </TD><TD> TRAMITE</TD></TR>
 * <TR><TD>S.IDTIPOTRAMITE||' - '||T.DESCRIPCION </TD><TD> TIPOTRAMITE</TD></TR>
 * <TR><TD>ES.IDESTADOSOLICITUD </TD><TD> IDESTADOSOLICITUD</TD></TR>
 * <TR><TD>ES.DESCRIPCION </TD><TD> ESTADO</TD></TR>
 * <TR><TD>S.FECHAPRESENTACION </TD><TD> FECHAPRESENTACION</TD></TR>
 * <TR><TD>0 </TD><TD> MONTO</TD></TR>
 * <TR><TD>S.FECHAPRESENTACION </TD><TD> FECHAPRESENTACION</TD></TR>
 * </TABLE>
 *
 * @date Septimbre 25, 2013
 */
public class SolicitudListaVO implements Serializable {
    @SuppressWarnings("compatibility:3404920619446391811")
    private static final long serialVersionUID = 1L;

    private int claveAdm;
    private String unidadAdmva;
    private int numEmpleado;
    private String dictaminador;
    private String numControl;
    private String rfcContribuyente;
    private String tipoServicio;
    private int idTipoTramite;
    private String tipoTramite;
    private int idEstadoSolicitud;
    private String estado;
    private Date fechaPresentacion;
    private long monto;
    private Date fechaFin;

    public SolicitudListaVO() {
        super();
    }

    /** ACCESSOR'S **************************************** */
    public String getParameterReport() {
        StringBuffer sb = new StringBuffer();
        sb.append("CLAVEADM:").append(this.getClaveAdm()).append(ConstantesCaracteres.SALTO_LINEA_1);
        sb.append("unidadAdmva:").append(this.getUnidadAdmva()).append(ConstantesCaracteres.SALTO_LINEA_1);
        sb.append("dictaminador:").append(this.getDictaminador()).append(ConstantesCaracteres.SALTO_LINEA_1);
        sb.append("numControl:").append(this.getNumControl()).append(ConstantesCaracteres.SALTO_LINEA_1);
        sb.append("rfcContribuyente:").append(this.getRfcContribuyente()).append(ConstantesCaracteres.SALTO_LINEA_1);
        sb.append("tipoServicio:").append(this.getTipoServicio()).append(ConstantesCaracteres.SALTO_LINEA_1);
        sb.append("tipoTramite:").append(this.getTipoTramite()).append(ConstantesCaracteres.SALTO_LINEA_1);
        sb.append("estado:").append(this.getEstado()).append(ConstantesCaracteres.SALTO_LINEA_1);
        sb.append("fechaPresentacion:").append(this.getFechaPresentacion()).append(ConstantesCaracteres.SALTO_LINEA_1);
        sb.append("monto:").append(this.getMonto());
        return sb.toString();
    }

    public void setClaveAdm(int claveAdm) {
        this.claveAdm = claveAdm;
    }

    public int getClaveAdm() {
        return claveAdm;
    }

    public void setUnidadAdmva(String unidadAdmva) {
        this.unidadAdmva = unidadAdmva;
    }

    public String getUnidadAdmva() {
        return unidadAdmva;
    }

    public void setNumEmpleado(int numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public int getNumEmpleado() {
        return numEmpleado;
    }

    public void setDictaminador(String dictaminador) {
        this.dictaminador = dictaminador;
    }

    public String getDictaminador() {
        return dictaminador;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setIdEstadoSolicitud(int idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public int getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if(null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if(null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public long getMonto() {
        return monto;
    }

    public void setFechaFin(Date fechaFin) {
        if(null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if(null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }
}
