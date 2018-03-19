package mx.gob.sat.mat.dyc.herramientas.analizador.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huetzin Cruz Lozano
 */
@Service
public class InsertarInfoExtractorBDelegate
{
    private static final String SEP_REGISTROS = "__sepregs__";
    private static final String SEPARADOR_TABLAS = "__septablas__";
    private static final String SEPARADOR_NOMTBL_INFO = "-------";
    private static final String PREFIJO_DATE = "_DATE_";
    
    public void insertarInfo(DataSource destino, String textoInfo)
    {
        textoInfo = new StringBuilder(textoInfo).reverse().toString();
        textoInfo = textoInfo.substring(3, textoInfo.length() - 3);
        System.out.println(textoInfo);
        System.out.println();
        System.out.println();
        
        String[] tablasInfo = textoInfo.split(SEPARADOR_TABLAS);
        for(String tablaInfo : tablasInfo)
        {
            //System.out.println(tablaInfo);
            String[] arrPrincipal = tablaInfo.split(SEPARADOR_NOMTBL_INFO);
            String nombreTabla = arrPrincipal[0].trim();
            System.out.println("------------- nombreTabla ->" + nombreTabla + " ------------------------------ ");
            if(arrPrincipal.length > 1)
            {
                String info = arrPrincipal[1].trim();
                System.out.println("info ->" + info);
                String[] regs = info.split(SEP_REGISTROS);
                for(String reg : regs)
                {
                    System.out.println("registro ->" + reg);
                    
                    StringBuilder nombresCampos = new StringBuilder(" (");
                    StringBuilder values = new StringBuilder(" VALUES(");
        
                    String[] paresCV = reg.split(";");

                    List<Object> params = new ArrayList<Object>();

                    for(String pcv : paresCV)
                    {
                        System.out.println("pcv ->" + pcv);
                        String[] cv = pcv.split("->");
                        String nombreCampo = cv[0].trim();
                        String valor = cv[1].trim();
                        valor = valor.substring(0, valor.length() - 2);
                        if(!valor.equals("null"))
                        {
                            nombresCampos.append(nombreCampo);
                            values.append("?");
                            
                            if(valor.startsWith(PREFIJO_DATE)){
                                params.add(new Date(Long.parseLong(valor.replaceFirst(PREFIJO_DATE, ""))));
                            }
                            else{
                                params.add(valor);
                            }

                            nombresCampos.append(", ");
                            values.append(", ");
                        }
                    }

                    nombresCampos.delete(nombresCampos.length() - 2, nombresCampos.length());

                    values.delete(values.length() - 2, values.length());

                    nombresCampos.append(")");
                    values.append(")");

                    String sentInsert = "INSERT INTO " + nombreTabla + nombresCampos.toString() + values.toString();
                    System.out.println("sentInsert ->" + sentInsert);
                    try{
                        JdbcTemplate jdbcTemplateDYC = new JdbcTemplate(destino);
                        jdbcTemplateDYC.update(sentInsert, params.toArray());
                    }
                    catch(org.springframework.dao.DuplicateKeyException dke){
                        System.out.println("se intentó insertar un registro repetido en ->" + nombreTabla);
                    }
                    catch(Exception e){
                        for(Object o : params){
                            System.out.println("obj ->" + o);
                        }

                        e.printStackTrace();
                        return;
                    }
                }
            }
            else{
                System.out.println("La tabla esta vacia");
            }
            System.out.println("------------- fin nombreTabla ->" + nombreTabla + " ------------------------------ ");
        }
    }
}
