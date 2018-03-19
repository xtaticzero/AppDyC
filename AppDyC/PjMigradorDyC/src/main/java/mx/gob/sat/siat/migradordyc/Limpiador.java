package mx.gob.sat.siat.migradordyc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mx.gob.sat.siat.migradordyc.mapper.StringMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class Limpiador
{
    @Autowired
    private JdbcTemplate jdbcTemplateDestino;

    public void limpiarTablas(LinkedList<TablaDTO> tablas)
    {
        while(!tablas.isEmpty())
        {
            TablaDTO tabla = null;
            try
            {
                tabla = tablas.pop();
                System.out.println("eliminando datos de la tabla ->" + tabla.getNombre());
                jdbcTemplateDestino.update("DELETE FROM " + tabla.getNombre());
            }
            catch(org.springframework.jdbc.BadSqlGrammarException bsge){
                System.err.println("error BadSqlGrammarException al borrar la tabla ->  " + tabla.getNombre() + "- se omitirá el borrado de esta tabla; " + bsge.getMessage());
            }
            catch(org.springframework.dao.DataIntegrityViolationException e)
            {
                System.out.println("NO se pudierón borrar los datos de la tabla " + tabla.getNombre() + "<- se pondrá en cola pra su posterior borrado");
                tablas.add(tabla);
            }
            catch(org.springframework.jdbc.UncategorizedSQLException u){
                System.out.println("UncategorizedSQLException -> NO se pudierón borrar los datos de la tabla " + tabla.getNombre() + "<- se pondrá en cola pra su posterior borrado");
                tablas.add(tabla);
                
            }
        }
    }

    public void dropearTablas() 
    {
        String owner = "SIAT_DYC_ADMIN";
        String query = "SELECT * FROM ALL_TABLES WHERE OWNER = '" + owner + "'";

        StringMapper mapperString = new StringMapper();
        mapperString.setNombreColumna("TABLE_NAME");

        List<String> listTablas = jdbcTemplateDestino.query(query, mapperString);
        List<String> sentsDropOrd = new ArrayList<String>();
        
        LinkedList<String> tablas = new LinkedList<String>();
        for(String t : listTablas){
            tablas.add(t);
        }

        while(!tablas.isEmpty())
        {
            String tabla = null;
            try
            {
                tabla = tablas.pop();
                System.out.println("eliminando datos de la tabla ->" + tabla);
                jdbcTemplateDestino.update("DROP TABLE " + tabla);
                sentsDropOrd.add("DROP TABLE " + owner + "." + tabla + ";");
            }
            catch(Exception e)
            {
                System.out.println("NO se pudo eliminar la tabla " + tabla + "<- se pondrá en cola para su posterior eliminación");
                System.out.println("mensaje: " + e.getMessage());
                tablas.add(tabla);
            }
        }
        System.out.println("--- las sentencias para eliminar todas las tablas de ejecutarón en el siguiente orden : ------------------------");
        for(String sentDrop : sentsDropOrd){
            System.out.println(sentDrop);
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println(" FIN DROPEAR TABLAS");
    }
    
    public void dropearSecuencias() 
    {
        String query = "SELECT * FROM ALL_SEQUENCES WHERE SEQUENCE_OWNER = 'SIAT_DYC_ADMIN'";

        StringMapper mapperString = new StringMapper();
        mapperString.setNombreColumna("SEQUENCE_NAME");

        List<String> nombresSecuencias = jdbcTemplateDestino.query(query, mapperString);
        System.out.println("se eliminarán " + nombresSecuencias.size() + " secuencias");

        for(String t : nombresSecuencias){
            jdbcTemplateDestino.update("DROP SEQUENCE " + t);
        }

        System.out.println(" FIN DROPEAR SECUENCIAS");
    }


}
