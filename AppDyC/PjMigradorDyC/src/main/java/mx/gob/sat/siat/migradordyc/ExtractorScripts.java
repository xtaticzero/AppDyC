package mx.gob.sat.siat.migradordyc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.migradordyc.mapper.RegistroMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ExtractorScripts
{
    @Autowired
    private JdbcTemplate jdbcTemplateOrigen;

    @Autowired
    private CargadorInfoDB cargador;
    
    public static SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
    
    String[] nomsTablas = obtenerNombresTablas();

    public void generarArchivoInserts()
    {
        System.out.println("INICIO generarArchivoInserts");
        FileWriter archivoInserts;
        try {
            SimpleDateFormat formatSubFijoNA = new SimpleDateFormat("yyMMddHHmm");
            String nomArchivo = "insertsCatalogos_" + formatSubFijoNA.format(new Date()) + ".txt";
            archivoInserts = new FileWriter("/siat/scripts/" + nomArchivo);

            PrintWriter pw = obtenerPrintWriter(archivoInserts);

            for(int i = 0; i < nomsTablas.length; i++)
            {
                String nombreTabla = nomsTablas[i];
                System.out.println("se crearán los inserts de la tabla ->" + nombreTabla);
                List<String> sentsInsert = extraerScripts(nombreTabla);
                pw.println("-- Inserts tabla " + nombreTabla + " ------------------------------------------------------------------------------"); 
                for(String sentencia : sentsInsert)
                {     
                    pw.println(sentencia); 
                }
            }

            cerrarArchivo(archivoInserts);
        } 
        catch (IOException e) {
        }
    }

    public static PrintWriter obtenerPrintWriter(FileWriter archivo)
    {
        PrintWriter pw = null;
        try
        {
            pw = new PrintWriter(archivo);
        }
        catch (Exception e)
        {
            System.err.println("error al crear el PrintWriter");
        }
        return pw;
    }
    
    public static void cerrarArchivo(FileWriter archivo)
    {
        try
        {
            if (null != archivo)
                archivo.close();
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
        }
    }

    public List<String> extraerScripts(String nombreTabla)
    {
        System.out.println("extraerScripts tabla ->" + nombreTabla + "<-")    ;
        List<String> sentsInsert = new ArrayList<String> ();

        TablaDTO tabla = cargador.obtenerInfoTabla(nombreTabla, jdbcTemplateOrigen);
        if(tabla == null){
            System.err.println("La tabla no existe en el origen");
            return sentsInsert;
        }

        List<ColumnaDTO> columnas = tabla.getColumnas();

        StringBuilder strCampos = new StringBuilder();
        int numCols = columnas.size();
        int cont = 0;

        for(ColumnaDTO colDTO : columnas)
        {
            strCampos.append(colDTO.getCOLUMN_NAME());

            if(cont < (numCols - 1)){
                strCampos.append(", ");
            }
            cont++;
        }

        String query = " SELECT * FROM " + tabla.getNombre() + " WHERE ROWNUM < 20";
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
            System.err.println("error UncategorizedSQLException ->" + ue.getMessage());
            return sentsInsert;
        }
        
        StringBuilder sentInsert ;
        StringBuilder valores;
        
        for(RegistroDTO registro : registros)
        {
            sentInsert = new StringBuilder();
            valores = new StringBuilder();
            
            for(int i = 0; i < registro.getValores().length; i++)
            {
                //System.out.println("tabla ->" + tabla.getNombre() + " tabla.getTiposColumnas().length ->" + tabla.getTiposColumnas().length);
                //System.out.println("tipoColumna ->" + tabla.getTiposColumnas()[i]);
                if(registro.getValores()[i] != null)
                {
                   
                    switch (tabla.getTiposColumnas()[i])
                    {
                        case 4 :   // Integer
                            valores.append(registro.getValores()[i].toString());
                        break;
                        case -5:   // Integer
                            valores.append(registro.getValores()[i].toString());
                        break;
                        case 8:     //Double
                            valores.append(registro.getValores()[i].toString());
                        break;
                        case 12:  // String
                            valores.append("'" + registro.getValores()[i] + "'");
                        break;
                        case 91:   // Date
                            String fechaForm = formateadorFecha.format(registro.getValores()[i]);
                            valores.append(" TO_DATE('" + fechaForm  + "', 'dd/mm/yyyy')");
                        break;
                    }
                }
                else{
                    valores.append("null");
                }
                
                if(i < (numCols - 1)){
                    valores.append(", ");
                }
            }
        
            sentInsert.append("INSERT INTO SIAT_DYC_ADMIN.");
            sentInsert.append(nombreTabla);
            sentInsert.append(" (");
            sentInsert.append(strCampos);
            sentInsert.append(") VALUES (");
            sentInsert.append(valores);
            sentInsert.append(");");

            //System.out.println(sentInsert.toString());
            sentsInsert.add(sentInsert.toString());
        }

        return sentsInsert;
    }
    
    /*public static String[] obtenerNombresTablas()
    {
        String[] a = {
            "DYCC_MENSAJEUSR",
            "DYCC_MATDOCUMENTOS",
            "DYCA_ACTOADMTVOS"
    };
        return a;
    }*/
    
    public static String[] obtenerNombresTablas()
    {
        String[] a = {
            "DYCC_DICTAMINADOR",
            "DYCC_APROBADOR"
        };
        
        return a;
    }
    
    
/*
    public static String[] obtenerNombresTablas()
    {
        String[] a = {
    "DYCC_TIPOAVISO",
    "DYCC_ESTRESOL",
    "DYCC_ESTADOCOMP",
    "DYCC_MOTIVOCASO",
    "DYCC_PARAMETROSAPP",
    "DYCC_TIPODOCUMENTO",
    "DYCC_TIPOMENSAJE",
    "DYCC_ACCIONSEG",
    "DYCC_TIPOPERIODO",
    "DYCC_TIPOPLAZO",
    "DYCC_TIPOSERVICIO",
    "DYCC_MOTIVORES",
    "DYCC_ORIGENSALDO",
    "DYCC_ROL",
    "DYCC_EJERCICIO",
    "DYCC_ESTADOSOL", 
    "DYCC_GRUPOOPER",
    "DYCC_IMPUESTO",
    "DYCC_INCONSIST",
    "DYCC_INFOAREQUERIR",
    "DYCC_INSTCREDITO",
    "DYCC_MATRIZANEXOS",
    "DYCC_TIPODECLARA",
    "DYCC_USODEC",
    "DYCC_ESTADODOC",
    "DYCC_RELACIONINFO",
    "DYCC_DATOSANEXO",
    "DYCC_MOTIVORECHAZO",
    "DYCC_ESTADOPAGO",
    "DYCC_DICTAMINADOR",
    "DYCC_TIPOFIRMA",
    "DYCC_ESTADOREQ",
    "DYCC_TIPOSALDOICEP",
    "DYCC_AFECTAICEP",
    "DYCC_TIPOPLANTILLA",
    "DYCC_VALIDACION",
    "DYCC_EDOPADRONAGRO",
    "DYCC_APROBADOR",
    "DYCC_TIPOACCIONRFC",
    "DYCC_FORMAPAGO",
    "DYCC_SUBTRAMITE",
    "DYCC_CONCEPTO",
    "DYCC_SUBORIGSALDO",
    "DYCC_TIPORESOL",
    "DYCC_MENSAJEUSR",
    "DYCC_MOTIVOINHABIL",
    "DYCC_MOTIVOTIPORES",
    "DYCC_PERIODO",
    "DYCC_ANEXOROL",
    "DYCC_CALDICTAMIN",
    "DYCC_MATDOCUMENTOS",
    "DYCC_MOVICEP",
    "DYCC_ACTIVIDAD",
    "DYCC_MOTIVORFC",
    "DYCC_TIPOTRAMITE",
    "DYCC_TRAMITEROL",
    "DYCC_UNIDADTRAMITE",
    "DYCC_ANEXOTRAMITE",
    "DYCC_SUBSALDOTRAM",
    "DYCC_INFOTRAMITE",
    "DYCC_TTSUBTRAMITE",
    "DYCA_ACTOADMTVOS",
    "DYCA_CAMPOEDITABLE",
    "DYCA_SERVORIGEN",
    "DYCA_TIPOPERIODOTT",
    "DYCA_VALIDATRAMITE",
    "DYCA_ORIGENTRAMITE",
    "DYCC_MATRIZCOMP",
    "DYCC_OFICIOFOLIO",
    "DYCC_OFICIOINFO",
    "DYCC_UNIDADADMVA",
    "DYCC_UNIDADMVATIPO",
    "DYCC_OFICIOLEYENDA",
    "DYCC_UNIDADADMDOM",
    "DYCC_STATUSPROCESO",
    "DYCC_ADMINPROCESOS",
    "DYCC_PROCESOS",
    "DYCC_SEGPROCESO",
    "DYCC_TAREAS"
    };
        return a;
    }
*/
}
