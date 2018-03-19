package mx.gob.sat.siat.migradordyc;

import java.sql.Types;

import java.util.LinkedList;
import java.util.List;

import mx.gob.sat.siat.migradordyc.mapper.RegistroMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class InsertadorInfo
{
    @Autowired
    private JdbcTemplate jdbcTemplateOrigen;
    
    @Autowired
    private JdbcTemplate jdbcTemplateDestino;
    
    @Autowired
    private CargadorInfoDB cargador;
    
    public void copiarInfoTablas(LinkedList<TablaDTO> tablas)
    {
        System.out.println("InsertadorInfo INICIO copiarInfoTablas");
        boolean salir = false;
        while(!tablas.isEmpty() || salir)
        {
            TablaDTO tabla = tablas.pop();

            switch(copiarInfoTabla(tabla, 1))
            {
                case 1: System.out.println("se copio correctamente los registros de la tabla ->" + tabla.getNombre() + "<-"); break;
                case 2: 
                    System.out.println("NO se copiaron los registros de la tabla ->" + tabla.getNombre() + "<-; se pondra en cola para su posterior copiado"  ); 
                    tablas.add(tabla);
                break;
                case 3: System.err.println("Ocurrió un error desconocido al copiar la tabla ->" + tabla.getNombre() + "<-, se detendra el proceso"); break;
            }
        }
    }
    
    public void copiarInfoTabla(String nombreTabla)
    {
        System.out.println("InsertadorInfo INICIO copiarInfoTabla");
        TablaDTO tabla = cargador.obtenerInfoTabla(nombreTabla, jdbcTemplateOrigen);
        if(tabla == null){
            System.err.println("La tabla no existe en el origen");
        }
        else
        {
            copiarInfoTabla(tabla, 2);
        }
    }

    // modo = 1: si existe un error de dependencias salir del proceso -- ESCRICTO
    // modo = 2: aunque exista un error de dependencias seguir intentando insertar los registros faltantes -- FLEXIBLE
    private int copiarInfoTabla(TablaDTO tabla, Integer modo)  // la tabla ya debe de tener cargados sus metadatos
    {
        String camposXML = "";
        for(ColumnaDTO columna : tabla.getColumnas())
        {
            if(columna.getTipoJava().intValue() == Types.CLOB){
                camposXML += " TABLA." + columna.getCOLUMN_NAME() + ".getCLOBVal() AS " + columna.getCOLUMN_NAME() + ", ";
            }
        }

        String query = " SELECT " + camposXML + " TABLA.* FROM " + tabla.getNombre() + " TABLA";
        System.out.println("query para obtener todos les registrso de la tabla origen ->" + query + "<-");

        RegistroMapper mapperRegistros = new RegistroMapper();
        mapperRegistros.setTabla(tabla);
        List<RegistroDTO> registros;
        try{
             registros = jdbcTemplateOrigen.query(query, mapperRegistros);
        }
        catch (org.springframework.jdbc.BadSqlGrammarException bs){
            System.err.println("error en el query ->" + query);
            throw bs;
        }
        catch(org.springframework.jdbc.UncategorizedSQLException ue) {
            //TODO: mejorar el tratamiento de la excepcion
            return 3;
        }

        System.out.println("Copiando informacion de la tabla " + tabla.getNombre() + " numRegistros ->" + registros.size() + "<-");
        String sentenciaInsert = generarSentenciaInsert(tabla);
        System.out.println("sentenciaInsert ->" + sentenciaInsert + "<-");
        int x = 1;
        for(RegistroDTO registro : registros)
        {
            System.out.println("insertando el registro " + x++ + " de la tabla " + tabla.getNombre());

            try{
                jdbcTemplateDestino.update(sentenciaInsert,  registro.getValores(), tabla.getTiposColumnas());
            }
            catch(DataIntegrityViolationException dive)
            {
                System.out.println("Violación de Intedridad de datos en la tabla ->" + tabla.getNombre() + "<-; " + dive.getMessage());
                if(dive.getMessage().contains("unique constraint")){
                    System.out.println("el registro ya existe en la BD Destino");
                }
                else{
                    if(modo.intValue() == 1)
                        return 2; // error por dependencias
                    else
                        System.out.println("ocurrio un error por dependencias al intentar insertar el regitro " + x + " de la tabla ->" + tabla.getNombre() + "; pero como el modo es NO es ESTRICTO se seguirá intentando insertar los siguientes regitros");
                }
            }
            catch(Exception e)
            {
                System.err.println("error al insertar registro ->" + e.getMessage() + "   ->" + sentenciaInsert + "");
                
                for(int i = 0; i < registro.getValores().length; i++){
                    System.out.println("valor ->" + registro.getValores()[i] + "<-; tipoColumna ->" + tabla.getTiposColumnas()[i] + "<-");
                }
                System.out.println("fin valores ------------------------------------------------------------------");
                return 3; // error desconocido
            }
        }
        return 1; //exito
    }

    private static String generarSentenciaInsert(TablaDTO tabla)
    {
        String strCampos = "";
        String strSignos = "";
        boolean quitarComma = false;

        for(ColumnaDTO columna : tabla.getColumnas())
        {
            quitarComma = Boolean.TRUE;
            strCampos += columna.getCOLUMN_NAME();
            strCampos += ",";
            if(columna.getTipoJava().intValue() == Types.CLOB)
                strSignos += " XmlType.createxml(?),";
            else
                strSignos += "?,";
        }

        if(quitarComma)
        {
            strCampos = strCampos.substring(0, strCampos.length() - 1);
            strSignos = strSignos.substring(0, strSignos.length() - 1);
        }

        return "INSERT INTO " + tabla.getNombre() + " (" + strCampos + ")" + " VALUES (" + strSignos + ")";
    }
}
