package mx.gob.sat.mat.dyc.herramientas.analizador.negocio;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huetzin Cruz Lozano
 */
@Service
public class ObtenerConexionesServiceImpl
{
    @Autowired
    private ApplicationContext appContext;

    public Map<String, javax.sql.DataSource> execute()
    {
        String[] nombresBeans = appContext.getBeanNamesForType(DataSource.class);
        Map<String, javax.sql.DataSource> conexiones = new HashMap<String, javax.sql.DataSource>();
        for(String nombreBean : nombresBeans){
            conexiones.put(nombreBean, (DataSource)appContext.getBean(nombreBean));
        }
        return conexiones;
    }
}
