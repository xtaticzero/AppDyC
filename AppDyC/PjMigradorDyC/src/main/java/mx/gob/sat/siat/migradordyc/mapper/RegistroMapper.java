package mx.gob.sat.siat.migradordyc.mapper;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import mx.gob.sat.siat.migradordyc.ColumnaDTO;
import mx.gob.sat.siat.migradordyc.RegistroDTO;
import mx.gob.sat.siat.migradordyc.TablaDTO;

import org.apache.commons.io.IOUtils;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;


public class RegistroMapper implements RowMapper<RegistroDTO>
{
    private TablaDTO tabla;
    
    @Override
    public RegistroDTO mapRow(ResultSet rs, int i) throws SQLException {
        RegistroDTO registro = new RegistroDTO();
        Object[] valores = new Object[tabla.getColumnas().size()];
       
        int numCol = 0;
        for(ColumnaDTO columna : tabla.getColumnas())
        {
            Object valor;
            try {
                valor = recuperarValor(rs, columna);
            } catch (Exception e) {
                e.printStackTrace();
                throw new SQLException();
            }
            registro.put(columna.getCOLUMN_NAME(), valor);
            valores[numCol] = valor;
            numCol ++;
        }
        registro.setValores(valores);
        return registro;
    }
    
    private Object recuperarValor(ResultSet rs, ColumnaDTO columna) throws SQLException, Exception {
        Object o = null;
        try
        {
            if(columna.getTipoJava().intValue() == Types.CLOB)
            {
                Clob xml = rs.getClob(columna.getCOLUMN_NAME()) ;
                if(xml != null)
                {
                    byte[] bt = IOUtils.toByteArray(xml.getAsciiStream());
                    return  new SqlLobValue(new java.io.ByteArrayInputStream(bt), bt.length, new DefaultLobHandler());    
                }
            }
            else
                o =  rs.getObject(columna.getCOLUMN_NAME()) ;      
        }
        catch(Exception e)
        {
            System.err.println("Error al recuperar un valor del ResultSet, columna ->" + columna.getCOLUMN_NAME() + "<-; Mensaje de la excepción ->" + e.getMessage() + "<-");
            o = null;
            e.printStackTrace();
            throw e;
        }
        
        return o;
    }

    public TablaDTO getTabla() {
        return tabla;
    }

    public void setTabla(TablaDTO tabla) {
        this.tabla = tabla;
    }
}
