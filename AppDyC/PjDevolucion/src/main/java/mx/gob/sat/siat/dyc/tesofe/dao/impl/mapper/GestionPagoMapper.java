package mx.gob.sat.siat.dyc.tesofe.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.sat.siat.dyc.tesofe.dto.DatosRetroTESOFE;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class GestionPagoMapper implements RowMapper<DatosRetroTESOFE> {

    private static final int ENTERO_DOS = 2;
    private static final int ENTERO_CUATRO = 4;

    private static final String PRIMER_ENVIO = "N";
    private static final String REENVIO = "R";

    private Date fechaInicioPeriodo;
    private String periodoInicioFin;
    private int cuentaValida;
    private String motivoRechazo;

    public GestionPagoMapper() {
        super();
    }

    @Override
    public DatosRetroTESOFE mapRow(ResultSet rs, int i) throws SQLException {
        DatosRetroTESOFE objeto = new DatosRetroTESOFE();
        objeto.setEjercicio(rs.getString("IDEJERCICIO"));
        objeto.setAnio(rs.getString("ANIO"));
        objeto.setMes(rs.getString("MES"));
        objeto.setNombre(rs.getString("NOMBRE"));
        objeto.setClaveADMOrigen(rs.getString("CLAVEADM_ORIGEN"));
        objeto.setAnioE(rs.getString("ANIO_E"));
        objeto.setImporteNeto(rs.getString("IMPORTENETO"));
        objeto.setIdTipoTramite(rs.getString("IDTIPOTRAMITE"));
        objeto.setClasificacion(rs.getString("CLASIFICACION"));
        objeto.setClaveDescripcion(rs.getString("CLAVEDESCRIPCION"));
        objeto.setClaveComputo(rs.getString("CLAVECOMPUTO"));
        objeto.setClase(rs.getString("IDORIGENSALDO"));
        objeto.setFechaEnvioATESOFE(rs.getString("FECHAENVIOATESOFE"));
        objeto.setStatusE(rs.getString("STATUS_E"));
        objeto.setStatusDescripcion(rs.getString("STATUSDESCRIPCION"));
        objeto.setFechaEmision(rs.getString("FECHAEMISION"));
        objeto.setFechaCancelacion(rs.getString("FECHACANCELACION"));
        objeto.setFechaAbono(rs.getString("FECHAABONO"));
        objeto.setTipoPersona(rs.getString("TIPOPERSONA"));
        setFechaInicioPeriodo(rs.getDate("FECHAINICIO"));
        setPeriodoInicioFin(rs.getString("PERIODOINICIOFIN"));
        setCuentaValida(rs.getInt("CUENTAVALIDA"));
        setMotivoRechazo(rs.getString("IDMOTIVORECHAZO"));

        if (getPeriodoInicioFin() != null && !getPeriodoInicioFin().isEmpty()) {
            SimpleDateFormat formatoAnio = new SimpleDateFormat("yyyy");
            objeto.setSaldoI(formatoAnio.format(getFechaInicioPeriodo()).concat(getPeriodoInicioFin().substring(0, ENTERO_DOS)));
            objeto.setSalfdoF(formatoAnio.format(getFechaInicioPeriodo()).concat(getPeriodoInicioFin().substring(ENTERO_DOS, ENTERO_CUATRO)));
        }

        if (getMotivoRechazo() == null && getCuentaValida() == 1) {
            objeto.setSucursal(PRIMER_ENVIO);
        } else {
            objeto.setSucursal(REENVIO);
        }

        return objeto;
    }

    public Date getFechaInicioPeriodo() {
        return (fechaInicioPeriodo != null) ? (Date) fechaInicioPeriodo.clone() : null;
    }

    public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo != null ? new Date(fechaInicioPeriodo.getTime()) : null;
    }

    public String getPeriodoInicioFin() {
        return periodoInicioFin;
    }

    public void setPeriodoInicioFin(String periodoInicioFin) {
        this.periodoInicioFin = periodoInicioFin;
    }

    public int getCuentaValida() {
        return cuentaValida;
    }

    public void setCuentaValida(int cuentaValida) {
        this.cuentaValida = cuentaValida;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }
}
