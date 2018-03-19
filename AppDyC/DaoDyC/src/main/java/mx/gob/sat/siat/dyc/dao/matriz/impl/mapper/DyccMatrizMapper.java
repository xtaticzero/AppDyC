package mx.gob.sat.siat.dyc.dao.matriz.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;

import org.springframework.jdbc.core.RowMapper;


public class DyccMatrizMapper implements RowMapper<DyccMatrizDocVO> {
    @Override
    public DyccMatrizDocVO mapRow(ResultSet resultSet, int i) throws SQLException {

        DyccMatrizDocVO dyccMatrizDocDTO = new DyccMatrizDocVO();

        dyccMatrizDocDTO.setIdPlantilla(resultSet.getInt("IDPLANTILLA"));
        dyccMatrizDocDTO.setNombreDocumento(resultSet.getString("NOMBREDOCUMENTO"));
        dyccMatrizDocDTO.setDescripcionDoc(resultSet.getString("DESCRIPCIONDOC"));
        dyccMatrizDocDTO.setFechaInicio(resultSet.getDate("FECHAINICIO"));
        dyccMatrizDocDTO.setFechaFin(resultSet.getDate("FECHAFIN"));
        dyccMatrizDocDTO.setIdTipo(resultSet.getInt("IDTIPO"));
        dyccMatrizDocDTO.setIdUnidad(resultSet.getInt("IDUNIDAD"));

        return dyccMatrizDocDTO;
    }

}
