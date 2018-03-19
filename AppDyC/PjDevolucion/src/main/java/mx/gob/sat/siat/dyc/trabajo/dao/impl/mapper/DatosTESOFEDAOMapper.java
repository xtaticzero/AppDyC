package mx.gob.sat.siat.dyc.trabajo.dao.impl.mapper;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.tesofe.dto.DatosEMP;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class DatosTESOFEDAOMapper implements RowMapper<DatosEMP> {
    
    private static final String COLUMN_BANCO = "BANCO";
    private static final String COLUMN_CLABE = "CLABE";
    private static final String COLUMN_IMPORTE = "IMPORTE";
    private static final String COLUMN_BENEFICIARIO = "BENEFICIARIO";
    private static final String COLUMN_RFC = "RFC";
    private static final String COLUMN_CLAVECOMPUTO = "CLAVECOMPUTO";
    private static final String COLUMN_IDTIPOTRAMITE = "IDTIPOTRAMITE";
    private static final String COLUMN_NUMCONTROL = "NUMCONTROL";
    private static final String COLUMN_RECHAZO = "RECHAZO";

    @Override
    public DatosEMP mapRow(ResultSet resultSet, int i) throws SQLException {
        DatosEMP objeto = new DatosEMP();
        
        if (existeColumna(resultSet, COLUMN_BANCO)) {
            objeto.setBanco(resultSet.getString(COLUMN_BANCO));
        }
        
        if (existeColumna(resultSet, COLUMN_CLABE)) {
           objeto.setClabe(resultSet.getString(COLUMN_CLABE));  
        }
        
        if (existeColumna(resultSet, COLUMN_IMPORTE)) {
           objeto.setImporte(resultSet.getString(COLUMN_IMPORTE)); 
        }
        
        if (existeColumna(resultSet, COLUMN_BENEFICIARIO)) {
            objeto.setBeneficiario(resultSet.getString(COLUMN_BENEFICIARIO));
        }
        
        if (existeColumna(resultSet, COLUMN_RFC)) {
            objeto.setRfc(resultSet.getString(COLUMN_RFC));
        }
        
        if (existeColumna(resultSet, COLUMN_CLAVECOMPUTO)) {
            objeto.setClaveComputo(resultSet.getString(COLUMN_CLAVECOMPUTO));
        }
        
        if (existeColumna(resultSet, COLUMN_IDTIPOTRAMITE)) {
            objeto.setTipoTramite(resultSet.getInt(COLUMN_IDTIPOTRAMITE)); 
        }
        
        if (existeColumna(resultSet, COLUMN_NUMCONTROL)) {
            objeto.setNumControl(resultSet.getString(COLUMN_NUMCONTROL));
        }
        
        if (existeColumna(resultSet, COLUMN_RECHAZO)) {
            objeto.setRechazo(resultSet.getInt(COLUMN_RECHAZO));
        }
        
        return objeto;
    }
    
    private static boolean existeColumna(ResultSet rs, String columnName) {

        try {
            ResultSetMetaData metaData = rs.getMetaData();
            for (int columna = 1; columna <= metaData.getColumnCount(); columna++) {
                if (columnName.equalsIgnoreCase(metaData.getColumnName(columna))) {
                    return Boolean.TRUE;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}