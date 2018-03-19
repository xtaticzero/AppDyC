package mx.gob.sat.siat.migradordyc.mapper;

import java.io.InputStream;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class DyctContribuyenteMapper implements RowMapper<InputStream>
{
    @Override
    public InputStream mapRow(ResultSet rs, int i) throws SQLException
    {
        InputStream x = null;
        /*try{
        x = rs.getAsciiStream("DATOSCONTRIBUYENTE");
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            }*/
        
        try{
            Clob c = rs.getClob("DATOSCONTRIBUYENTE");
            x = c.getAsciiStream();
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            }
        
        
        return x;
    }

}
