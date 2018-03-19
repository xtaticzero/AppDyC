package mx.gob.sat.siat.dyc.dao.usuario.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;

import org.springframework.jdbc.core.RowMapper;


public class DictaminadorSolMapper implements RowMapper<DictaminadorSolBean> {

    public DictaminadorSolMapper() {
        super();
    }

    @Override
    public DictaminadorSolBean mapRow(ResultSet rs, int i) throws SQLException {

        DictaminadorSolBean dictaminadorSol = new DictaminadorSolBean();

        dictaminadorSol.setNumControl(rs.getString("NUMCONTROL"));
        dictaminadorSol.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
        dictaminadorSol.setImporteTramite(rs.getDouble("IMPORTETRAMITE"));
        dictaminadorSol.setTipoTramite(rs.getString("TIPOTRAMITE"));
        dictaminadorSol.setPuntosAsignar(rs.getString("PUNTOSASIGNACION"));
        dictaminadorSol.setFechaRegistro(rs.getDate("FECHAPRESENTACION"));
        dictaminadorSol.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        dictaminadorSol.setNumEmpleado(rs.getInt("NUMEMPLEADODICT"));

        return dictaminadorSol;

    }

}
