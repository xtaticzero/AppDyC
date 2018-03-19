package mx.gob.sat.siat.migradordyc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import mx.gob.sat.siat.migradordyc.mapper.ColumnaMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;


@Component
public class CargadorInfoDB
{
    public LinkedList<TablaDTO> obtenerInfoDataBase(JdbcTemplate jdbcTemplate)
    {
        String query =  " SELECT T.TABLE_NAME, COLUMN_NAME, DATA_LENGTH, DATA_TYPE, DATA_PRECISION, DATA_SCALE, NULLABLE \n" + 
                        " FROM user_tab_cols U INNER JOIN ALL_TABLES T ON (U.TABLE_NAME = T.TABLE_NAME) \n" + 
                        " WHERE OWNER = 'SIAT_DYC_ADMIN'";
        LinkedList<TablaDTO> tablas = new LinkedList<TablaDTO>();
        List<ColumnaDTO> columnas = jdbcTemplate.query(query, new ColumnaMapper());
        for(ColumnaDTO columna : columnas)
        {
            if(columna.getCOLUMN_NAME().contains("SYS_"))
            {
                System.out.println("La columna " + columna.getCOLUMN_NAME() + " pertenece al DBMS, NO será vinculada a ninguna tabla");
                continue;
            }

            if(columna.getTipoJava().intValue() == Types.SQLXML){
                System.out.println("LA COLUMNA ->" + columna.getCOLUMN_NAME() + "<- de la tabla -> " + columna.getTABLE_NAME() + "<-;  es de tipo XML, Se vinculará a la tabla.");
            }

            TablaDTO tablaAux = obtenerTabla(columna.getTABLE_NAME(), tablas);
            if(tablaAux != null){
                tablaAux.getColumnas().add(columna);
            }
            else
            {
                tablaAux = new TablaDTO();
                tablaAux.setNombre(columna.getTABLE_NAME());
                tablaAux.setColumnas(new ArrayList<ColumnaDTO>());
                tablaAux.getColumnas().add(columna);
                tablas.add(tablaAux);
            }
        }

        for(TablaDTO tabla : tablas)
        {
            tabla.setTiposColumnas(new int[tabla.getColumnas().size()]);
            int i = 0;
            for(ColumnaDTO columna : tabla.getColumnas())
            {
                tabla.getTiposColumnas()[i] = columna.getTipoJava().intValue();
                i++;
            }
        }
        return tablas;
    }

    private TablaDTO obtenerTabla(String nombreTabla, List<TablaDTO> tablas)
    {
        for(TablaDTO tabla : tablas) {
            if(tabla.getNombre().equals(nombreTabla))
                return tabla;
        }
        
        return null;
    }

    //busca en la tabla user_tab_cols de la base que representa el jdbctemplate, si encuentra registros (columnas) regresa la tabla en forma de objeto.
    public TablaDTO obtenerInfoTabla(String nombreTabla, JdbcTemplate jdbcTemplate)
    {
        String query =  " SELECT TABLE_NAME, COLUMN_NAME, DATA_LENGTH, DATA_TYPE, DATA_PRECISION, DATA_SCALE, NULLABLE " +
                        " FROM all_tab_cols U " + 
                        " WHERE TABLE_NAME = ?";
        
        List<ColumnaDTO> columnas = jdbcTemplate.query(query, new Object[]{ nombreTabla}, new ColumnaMapper());
        System.out.println("número de columnas econtradas para la tabla " + nombreTabla + ": ->" + columnas.size() + "<-");
        
        if(columnas.isEmpty())
        {
            System.err.println("NO se encontró ningun registro en ALL_TAB_COLS con TABLE_NAME = " + nombreTabla);
            return null;
        }
        
        TablaDTO tabla = new TablaDTO();
        tabla.setNombre(nombreTabla);
        tabla.setColumnas(new ArrayList<ColumnaDTO>());

        for(ColumnaDTO columna : columnas)
        {
            if(columna.getCOLUMN_NAME().contains("SYS_"))
            {
                System.out.println("La columna " + columna.getCOLUMN_NAME() + " al parecer pertence al sistema oracle, NO será vinculada a ninguna tabla");
                continue;
            }

            if(columna.getTipoJava().intValue() == Types.SQLXML){
                System.out.println("LA COLUMNA ->" + columna.getCOLUMN_NAME() + "<- de la tabla -> " + columna.getTABLE_NAME() + "<-;  es de tipo XML, aún así se vinculará a la tabla.");
            }

            tabla.getColumnas().add(columna);
        }

        tabla.setTiposColumnas(new int[tabla.getColumnas().size()]);
        int i = 0;

        for(ColumnaDTO columnaValidada : tabla.getColumnas())
        {
            tabla.getTiposColumnas()[i] = columnaValidada.getTipoJava().intValue();
            i++;
        }

        return tabla;
    }

    public String[] obtenerInfoResultSet(JdbcTemplate jdbcTemplate, String query)
    {
        System.out.println();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
        SqlRowSetMetaData metaData =  rowSet.getMetaData();
        String[] nomsColumns = metaData.getColumnNames();

        for(int i = 0; i < nomsColumns.length; i++)
        {
            StringBuilder sbInfoColumn = new StringBuilder(nomsColumns[i]);
            sbInfoColumn.append("|");
            sbInfoColumn.append(metaData.getColumnClassName(i + 1));
            sbInfoColumn.append("|");
            sbInfoColumn.append( metaData.getColumnDisplaySize(i + 1));
            System.out.println(sbInfoColumn);
        }

        return null;
    }
}
