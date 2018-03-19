package mx.gob.sat.siat.migradordyc.mapper;

import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Types;

import mx.gob.sat.siat.migradordyc.ColumnaDTO;

import oracle.sql.BLOB;

import org.springframework.jdbc.core.RowMapper;


public class ColumnaMapper implements RowMapper<ColumnaDTO>
{

    @Override
    public ColumnaDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        ColumnaDTO columna = new ColumnaDTO();
        columna.setTABLE_NAME(rs.getString("TABLE_NAME"));
        columna.setCOLUMN_NAME(rs.getString("COLUMN_NAME"));
        columna.setDATA_LENGTH(rs.getInt("DATA_LENGTH"));
        columna.setNULLABLE(rs.getString("NULLABLE").equals("Y"));
        settearTipoColumna(rs, columna);

        return columna;
    }

    private void settearTipoColumna(ResultSet rs, ColumnaDTO columna) throws SQLException
    {
        String tipo = rs.getString("DATA_TYPE");
        if(columna.getCOLUMN_NAME().equals("DATOSCREDFIS")){
            System.out.println("DATOSCREDFIS  tipo columna ->" + tipo + "<-");
        }

        if(tipo.equals("BLOB")){
            columna.setTipo(BLOB.class);
            columna.setTipoJava(Types.BLOB);
        }

        if(tipo.equals("CLOB")){
            columna.setTipo(Clob.class);
            columna.setTipoJava(Types.CLOB);
        }

        if(tipo.equals("DATE")){
            columna.setTipo(Date.class);
            columna.setTipoJava(Types.DATE);
        }

        if(tipo.equals("NUMBER"))
        {
            Integer numEnteros = rs.getInt("DATA_PRECISION");
            Integer numDecimales = rs.getInt("DATA_SCALE");
            if(numDecimales == 0)
            {
                if(numEnteros <= 9) {
                    columna.setTipo(Integer.class);
                    columna.setTipoJava(Types.INTEGER);
                }
                else
                {
                    columna.setTipo(Long.class);
                    columna.setTipoJava(Types.BIGINT);
                }
            }
            else
            {
                if(numEnteros <= 9) {
                    columna.setTipo(Float.class);
                    columna.setTipoJava(Types.FLOAT);
                }
                else
                {
                    columna.setTipo(Double.class);
                    columna.setTipoJava(Types.DOUBLE);
                }
            }
        }

        if(tipo.equals("VARCHAR2"))
        {
            columna.setTipo(String.class);
            columna.setTipoJava(Types.VARCHAR);
        }
                      
        if(tipo.equals("XMLTYPE"))
        {
            columna.setTipo(SQLXML.class);
            columna.setTipoJava(Types.CLOB);
        }
    } 
}
