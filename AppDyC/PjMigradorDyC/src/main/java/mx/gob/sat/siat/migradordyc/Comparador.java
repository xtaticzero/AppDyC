package mx.gob.sat.siat.migradordyc;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.migradordyc.mapper.ColumnaMapper;
import mx.gob.sat.siat.migradordyc.mapper.TablaMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class Comparador {
    
    private static int contCamposErr = 0;
    
    @Autowired
    private JdbcTemplate jdbcTemplateOrigen;
    
    @Autowired
    private JdbcTemplate jdbcTemplateDestino;
    
    public Comparador() {
        super();
    }
    
    public boolean compararEstructurasBasesDatos(List<TablaDTO> tablasOrigen, List<TablaDTO> tablasDestino)
    {
        System.out.println("Número de tablas en la BD Origen ->" + tablasOrigen.size() + "<-;  Número de tablas en BD Destino ->" + tablasDestino.size() + "<-");
        
        compararTablas(tablasOrigen, tablasDestino);
        
        for(TablaDTO tablaOrigen : tablasOrigen){
            compararCampos(tablaOrigen);
        }
        System.out.println(" ----- FIN compararEstructurasBasesDatos");
        return false;
    }

    private void compararTablas(List<TablaDTO> tablasOrigen, List<TablaDTO> tablasDestino)
    {
        List<String> nombresTablasOrigen = new ArrayList<String>();
        List<String> nombresTablasDestino = new ArrayList<String>();

        for(TablaDTO tablaOrigen : tablasOrigen){
            nombresTablasOrigen.add(tablaOrigen.getNombre());
        }

        for(TablaDTO tablaDestino : tablasDestino){
            nombresTablasDestino.add(tablaDestino.getNombre());
        }

        for(String nombreTablaOrigen : nombresTablasOrigen)
        {
            if(!existeEnLista(nombreTablaOrigen, nombresTablasDestino)){
                System.err.println("La tabla ->" + nombreTablaOrigen + "<- NO existe en la base de datos destino");
            }
        }
        
        for(String nombreTablaDestino : nombresTablasDestino)
        {
            if(!existeEnLista(nombreTablaDestino, nombresTablasOrigen)){
                System.err.println("La tabla ->" + nombreTablaDestino + "<- esta en la BD destino PERO NO existe en la BD Origen");
            }
        }
    }

    private void compararCampos(TablaDTO tabla)
    {
        String query =  " SELECT TABLE_NAME, COLUMN_NAME, DATA_LENGTH, NULLABLE, DATA_TYPE, DATA_PRECISION, DATA_SCALE " +
                        " FROM user_tab_cols WHERE table_name = ?";
        List<ColumnaDTO> colsOrigen = jdbcTemplateOrigen.query(query, new Object[]{tabla.getNombre()}, new ColumnaMapper());
        List<ColumnaDTO> colsDestino = jdbcTemplateDestino.query(query, new Object[]{tabla.getNombre()}, new ColumnaMapper());

        for(ColumnaDTO colOrigen : colsOrigen){
            if(!existeColumnaIgual(colOrigen, colsDestino)){
                System.err.println("TABLA -" + tabla.getNombre() + "- LA COLUMNA (DE LA BD ORIGEN) ->" + colOrigen.getCOLUMN_NAME() + "<- NO TIENE PAR EN LA BD DESTINO");
                contCamposErr ++;
            }
        }

        for(ColumnaDTO colDestino : colsDestino){
            if(!existeColumnaIgual(colDestino, colsOrigen)){
                System.err.println("TABLA -" + tabla.getNombre() + "- LA COLUMNA (DE LA BD DESTINO) ->" + colDestino.getCOLUMN_NAME() + "<- NO TIENE PAR EN LA BD ORIGEN");
                contCamposErr ++;
            }
        }
    }

    private boolean existeColumnaIgual(ColumnaDTO col, List<ColumnaDTO> columnas)
    {
        for(ColumnaDTO colAux : columnas)
        {
            if(sonIguales(colAux, col))
                return Boolean.TRUE;
        }
        return false;
    }

    private boolean sonIguales(ColumnaDTO col1, ColumnaDTO col2)
    {
        if(col1.getCOLUMN_NAME().equals(col2.getCOLUMN_NAME()))
        {
            if(col1.getDATA_LENGTH().intValue() == col2.getDATA_LENGTH().intValue())
            {
                if(col1.getNULLABLE().booleanValue() == col2.getNULLABLE().booleanValue()){
                    return Boolean.TRUE;
                }
                else
                    System.out.println("hay una diferencia en el NULLABLE. col1 ->" + col1.getNULLABLE().booleanValue() + "; col2 ->" + col2.getNULLABLE().booleanValue());
            }
        }
        //System.out.println("Las columnas no son iguales ->" + col1.getCOLUMN_NAME() + "-" + col2.getCOLUMN_NAME() + "-" + col1.getDATA_LENGTH().intValue() + "-" + col2.getDATA_LENGTH().intValue());
        
        return false;
    }

    private boolean existeEnLista(String tabla, List<String> tablas)
    {
        for(String it : tablas)
        {
            if(it.equals(tabla))
                return Boolean.TRUE;
        }
        return false;
    }
    
    public void buscarTablasSinRegistros(JdbcTemplate jdbcTemplate)
    {
        String query =  " SELECT * FROM ALL_TABLES " + 
                        " WHERE OWNER = 'SIAT_DYC_ADMIN'";

        List<TablaDTO> tablas = jdbcTemplate.query(query, new TablaMapper());

        for(TablaDTO tabla : tablas)
        {
            String queryCount = "SELECT COUNT(*) FROM " + tabla.getNombre();
            Integer numRegs = jdbcTemplate.queryForObject(queryCount, Integer.class);
            if(numRegs.intValue() == 0)
                System.out.println("La tabla ->" + tabla.getNombre() +  "<- esta Vacía");
        }
        
        System.out.println("---- Fin buscarTablasSinRegistros ------------------------");
    }
}
