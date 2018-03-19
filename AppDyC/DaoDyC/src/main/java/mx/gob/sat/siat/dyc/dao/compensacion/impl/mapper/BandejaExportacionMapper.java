package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.dyc.domain.resolucion.FilaGridTramitesBean;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import org.springframework.jdbc.core.RowMapper;

public class BandejaExportacionMapper implements RowMapper<FilaGridTramitesBean> {

    public BandejaExportacionMapper() {
    }

    @Override
    public FilaGridTramitesBean mapRow(ResultSet rs, int i) throws SQLException {

        FilaGridTramitesBean registro = new FilaGridTramitesBean();

        Integer idTipoServicio = rs.getInt("IDTIPOSERVICIO_SERVICIO");
        boolean isAvisoComp = (idTipoServicio.equals(Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio())
                || idTipoServicio.equals(Constantes.TiposServicio.AVISO_COMPENSACION.getIdTipoServicio()));
        
        registro.setAdministracion(rs.getString("NOMABREVIADO_UNIDADADMVA"));
        
        String nombreDic;
        Integer numDic = rs.getInt("NUMEMPLEADO_DIC");
        if (!rs.wasNull()) {
            nombreDic = numDic + " - " + rs.getString("NOMBRE_DIC") + " " + rs.getString("APELLIDOPATERNO_DIC") + " " + rs.getString("APELLIDOMATERNO_DIC");
        } else {
            nombreDic = "";
        }
        
        registro.setDictaminador(nombreDic);
        registro.setNumControl(rs.getString("NUMCONTROL_SERVICIO"));
        registro.setRfc(rs.getString("RFCCONTRIBUYENTE_SERVICIO"));
        registro.setTramite(rs.getString("DESCRIPCION_TSERV"));
        registro.setTipoTramite(rs.getString("DESCRIPCION_TIPOTRAMITE"));

        String fechaPresentacion = !isAvisoComp ? "FECHAPRESENTACION_SOL" : "FECHAPRESENTACION_COMP";
        registro.setFechaPresentacion(rs.getDate(fechaPresentacion));
        registro.setPeriodo(rs.getString("DESCRIPCION_PERIODO"));
        registro.setEjercicio(rs.getString("IDEJERCICIO_SALDO"));
        String estadoTramite = !isAvisoComp ? "DESCRIPCION_ESTADOSOL" : "DESCRIPCION_ESTADOCOMP";
        registro.setEstado(rs.getString(estadoTramite));

        String fechaResolucion = !isAvisoComp ? "FECHAREGISTRO_RESOL" : "FECHARESOLUCION_RESC";
        registro.setFechaResolucion(rs.getDate(fechaResolucion));

        String importe = !isAvisoComp ? "IMPORTESOLICITADO_SOL" : "IMPORTECOMPENSADO_COMP";
        registro.setImporte(rs.getDouble(importe));
        if (!isAvisoComp) {
            registro.setImporteAutorizado(rs.getDouble("IMPAUTORIZADO_RESOL"));
            registro.setFechaPago(rs.getDate("FECHAABONO_PAGO"));
            registro.setImportePagado(rs.getDouble("IMPORTENETO_RESOL"));
        }
        registro.setImpuestos(rs.getString("DESCRIPCION_IMPUESTO"));
        registro.setConceptoImpuestos(rs.getString("DESCRIPCION_CONCEPTO"));
        registro.setDias(rs.getInt("PLAZO"));
        registro.setTipoDia(rs.getInt("IDTIPOPLAZO"));

        return registro;
    }
}