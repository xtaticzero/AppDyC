package mx.gob.sat.siat.dyc.comunica.util.recurso;

import java.util.Iterator;
import java.util.List;

public class MetodosGenerales {
    public MetodosGenerales() {
        super();
    }
    
    /**
     * Genera un string que contiene todos los id de plantilla
     *
     * @param listaIDsPlantilla lista de los id de plantilla
     * @return un string que contiene todos los id de plantilla separados por comas y encerrados por parentesis. 
     * Ejemplo: (1, 2, 3)
     */
    public String generarClausulaIN(List<Integer> listaIDsPlantilla) {
        int             i = 0;
        Iterator it1      = null;
        String mercancias = "";
        Integer idPlantilla = null;
        StringBuffer stringBuffer = new StringBuffer();
        it1               = listaIDsPlantilla.iterator();
        
        stringBuffer.append("(");
        
        while (it1.hasNext()) {
            if (i > 0) {
                stringBuffer.append(",");
            }
            idPlantilla = (Integer)it1.next();
            stringBuffer.append(String.valueOf(idPlantilla));
            i++;
        }
        stringBuffer.append(")");
        mercancias = stringBuffer.toString();
        
        return mercancias;
    }
}
