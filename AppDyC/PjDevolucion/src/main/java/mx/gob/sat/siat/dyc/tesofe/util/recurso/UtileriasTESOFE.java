package mx.gob.sat.siat.dyc.tesofe.util.recurso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.gob.sat.siat.dyc.tesofe.util.constante.ConstantesTESOFE;

import org.apache.log4j.Logger;


/**
 * Esta clase se utiliza para agregar soporte a la clase de GestionPagosTESOFEServiceImpl *
 *
 * @author Jesus Alfredo Hernandez Orozco
 *
 */
public final class UtileriasTESOFE {
    private UtileriasTESOFE() {
        super();
    }
    public static final Logger LOGGER = Logger.getLogger(UtileriasTESOFE.class);
    
    /**
     * Quita el punto decimal al importe recolectado en base de datos.
     *
     * @param importe
     * @return
     */
    public static String convertirImporteEnString(String importe) {
        String importeADevolver  = importe;
        String[] importeDividido = importe.split("\\.");
        if(importeDividido.length>1) {
            importeADevolver  = importeDividido[0];
            if(importeDividido[1].length()==1) {
                importeADevolver+=importeDividido[1]+"0";
                
            } else if(importeDividido[1].length()==0) {
                importeADevolver+=importeDividido[1]+"00";
                
            } else if(importeDividido[1].length()>ConstantesTESOFE.LONGITUD_MAXIMA_DECIMALES) {
                importeADevolver+=importeDividido[1].substring(0, ConstantesTESOFE.LONGITUD_MAXIMA_DECIMALES);
            }
        } else if(importeDividido.length==1) {
            importeADevolver+="00";
        }
        return importeADevolver;
    }
    
    
    /**
     * Agrega una determinada cantidad de ceros a la izquierda a una cadena.
     *
     * @param cantidadSinCeros
     * @param largo
     * @return
     */
    public static String agregarCeros(String cantidadSinCeros, int largo) {
        StringBuilder ceros = new StringBuilder();
        int cantidad = largo - cantidadSinCeros.length();
        for (int i = 0; i < cantidad; i++) {
            ceros.append("0");
        }
        return ceros.append(cantidadSinCeros).toString();
    }

    /**
     * Agrega una determinada cantidad de ceros a la izquierda a una cadena.
     *
     * @param cadena
     * @param largo
     * @return
     */
    public static String agregarEspaciosALaDerecha(String cadena, int largo) {
        StringBuilder espacios = new StringBuilder();
        int cantidadEspacios = largo - cadena.length();
        for (int i = 0; i < cantidadEspacios; i++) {
            espacios.append(" ");
        }
        return cadena.concat(espacios.toString());
    }
    
    /**
     * Valida si la cadena se encuentra dentro de la tabla de caracteres validos.
     *
     * @param texto
     * @return
     */
    public static String validarYCorregirTexto(String texto) {
        String textoADevolver="";
        String temporal = texto;
        String patron ="[A-Za-z0-9\\s!\"#$%&'()*+,-./:;?@\\\\_áéíóúñÑ¡¿]+";   
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(texto);
        if(!matcher.matches()) {
            String textoTemporal=temporal.replaceAll(patron, "");
            textoADevolver = texto.replaceAll("["+textoTemporal+"]", "");
        } else {
            textoADevolver = texto;
        }
        return textoADevolver;
    }
    
    public static String generarClavesDeRastreo(List<String> lista) {
        StringBuilder clavesDeRastreo = new StringBuilder();
        clavesDeRastreo.append("('");
        int i = 0;
        for (String clave : lista) {
            if (i>0) {
                clavesDeRastreo.append("','");
            }
            clavesDeRastreo.append(clave);
            i++;
        }
        clavesDeRastreo.append("')");
        return clavesDeRastreo.toString();
    }
    
        public static String generarParamNumControl(List<String> lista) {
        StringBuilder numerosControl = new StringBuilder();
        numerosControl.append("'");
        int i = 0;
        for (String numeroControl : lista) {
            if (i > 0) {
                numerosControl.append("','");
            }
            numerosControl.append(numeroControl);
            i++;
        }
        numerosControl.append("'");
        return numerosControl.toString();
    }
    
    /**
     * Retorna los titulos del reporte de diario que se genera en excel
     *
     * @return Lista con el nombre de los titulos
     */
    public static List<String> retornarTitulosDeExcel() {
        List<String> lista = new ArrayList<String>();
        lista.add("Ejercicio");
        lista.add("año_");
        lista.add("mes_");
        lista.add("Nombre");
        lista.add("ALR_Origen");
        lista.add("Año_E");
        lista.add("SumaDeImporte");
        lista.add("Sucursal");
        lista.add("Tipo_I");
        lista.add("Clasificacion");
        lista.add("Claves.Descripcion");
        lista.add("Computo");
        lista.add("Clase");
        lista.add("Saldo_I");
        lista.add("Saldo_F");
        lista.add("_Fecha de Envio a TESOFE");
        lista.add("Status_E");
        lista.add("Status.Descripcion");
        lista.add("Emisión");
        lista.add("Fecha_Cancelacion");
        lista.add("Fecha_Abono");
        lista.add("TPersona");
        return lista;
    }
}