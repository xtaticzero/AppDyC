package cte.dyc.generico;


import cte.dyc.generico.util.constante.ConstantesDyC;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;

import org.apache.log4j.Logger;


/**
 * Utileria para extraer todos lo valores de las propiedades de un objeto.
 * @author Mario Bastida
 * @since Abril, 2014
 *
 */
public final class ExtractorUtil {
    /** Prefijo get. */
    private static final String PREFIJO_GET = "get";
    /** Prefijo set. */
    /**private final static String PREFIJO_SET = "set";*/

    /** Serial Version. */
    private static final String UID = "serialVersionUID";

    private static final String PREFIJO_PAQUETE = "mx.gob.sat.siat";

    private static final String CARACTER_TAB = "\t";
    private static final String CARACTER_SALTO_DE_LINEA = "\n";

    private static Logger log = Logger.getLogger(ExtractorUtil.class);
    private static final String TIPO_HASHMAP = "HashMap";

    private ExtractorUtil() {

    }

    public static String ejecutar(final Object origen) {
        String padreClase = origen.getClass().getName();
        String nombreClase = padreClase.substring(padreClase.lastIndexOf('.') + 1, padreClase.length());

        StringBuffer tabulador = new StringBuffer("");
        String result = "";

        try {
            if (nombreClase.equals(TIPO_HASHMAP)) {
                result = obtenerDatosMap(origen);
            } else {
                result = obtenerDatos(origen, tabulador);
            }
        } catch (Exception e) {
            log.error("Error al extraer datos del objeto " + e.toString());
        }

        return result;
    }

    private static boolean existeMetodo(final Method[] metodos, String metodoABuscar) {
        boolean encontrado = Boolean.FALSE;

        for (int x = 0; x < metodos.length; x++) {

            if (metodos[x].getName().equals(metodoABuscar.trim())) {
                encontrado = Boolean.TRUE;
                break;
            }
        }

        return encontrado;
    }

    private static String obtenerDatos(final Object origen, StringBuffer tabulador) throws ClassNotFoundException,
                                                                                           NoSuchMethodException,
                                                                                           IllegalAccessException,
                                                                                           InvocationTargetException {

        StringBuffer result = new StringBuffer();
        tabulador.append(CARACTER_TAB);

        String padreClase = origen.getClass().getName();
        String nombreClase = padreClase.substring(padreClase.lastIndexOf('.') + 1, padreClase.length());
        /**String nombreClase = padreClase.substring(padreClase.lastIndexOf(".") + 1, padreClase.length() ); */


        Class<?> clasePadre = Class.forName(padreClase);
        Object valorObjeto = null;
        Field[] campos = clasePadre.getDeclaredFields();
        Method[] metodos = clasePadre.getDeclaredMethods();

        result.append(tabulador.toString() + nombreClase + " = [ " + CARACTER_SALTO_DE_LINEA);

        for (Field field : campos) {

            String fieldName = field.getName();

            if (fieldName.equals(UID)) {
                continue;
            }
            final String nombre = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());


            if (field.getType().getName().contains("ArrayList") || field.getType().getName().contains("List")) {
                continue;
            }

            if (existeMetodo(metodos, PREFIJO_GET + nombre)) {
                final Method getter = clasePadre.getDeclaredMethod(PREFIJO_GET + nombre);

                valorObjeto = getter.invoke(origen, new Object[0]);

                if (field.getType().getName().startsWith(PREFIJO_PAQUETE)) {
                    if (valorObjeto != null) {
                        result.append(obtenerDatos(valorObjeto, new StringBuffer(tabulador.toString())));
                    }
                } else {
                    result.append(tabulador.toString() + CARACTER_TAB + fieldName + " = " + valorObjeto +
                                  CARACTER_SALTO_DE_LINEA);
                }
            }

        }

        result.append( tabulador.toString() + "]" + CARACTER_SALTO_DE_LINEA);

        return result.toString();
    }

    /**
     * Metodo que obtiene los datos de un Map
     * LADP
     * */
    private static String obtenerDatosMap(Object mapeo) {
        String result = "";
        Class[] mapeos = new Class[1];
        mapeos[0] = Map.class;

        try {
            Class<?> cls = Class.forName(ExtractorUtil.class.getName());
            Object obj = cls.newInstance();
            Method method = cls.getDeclaredMethod("pintaMap", mapeos);
            result = method.invoke(obj, mapeo).toString();
        } catch (Exception ex) {
            log.info(ConstantesDyC.TEXTO_1_ERROR_DAO + ex.getMessage());
        }
        return result;
    }

    protected String pintaMap(Map<Object, Object> mapeo) {
        String result = "";
        String nombreClase = mapeo.getClass().getName();
        result = nombreClase + " " + mapeo;
        return result;
    }

}
