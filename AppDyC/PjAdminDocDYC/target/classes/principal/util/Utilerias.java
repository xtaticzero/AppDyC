/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package principal.util;

import java.io.File;

/**
 * Clase de utileria para metodos genericos
 * @author  Alfredo Ramirez
 * @since   05/11/2013
 */
public final class Utilerias {

    /**
     * Constructor privado puesto que es una
     * clase Utils.
     */
    private Utilerias() {
    }

    private static final Integer LOGNITUD_MINIMA_RUTA = 20;
    private static final String DIAGONAL = "/";

    public static String isNull(Object objeto) {
        if (null == objeto) {
            return "";
        } else {
            return objeto.toString();
        }
    }

    public static void cambiarPermisosDirectorio(String ruta) {
        int indexPalabra = 0;
        String directorioParcial = "";
        String directorioSeparado[] = null;
        File directorio = null;
        File directorioAvalidar = new File(ruta);

        if (directorioAvalidar.isDirectory()) {
            directorioSeparado = ruta.split(DIAGONAL);
            for (int i = 0; i < directorioSeparado.length; i++) {
                indexPalabra = ruta.indexOf(directorioSeparado[i]);
                directorioParcial = ruta.substring(0, indexPalabra) + DIAGONAL + directorioSeparado[i];
                if (directorioParcial.contains("/siat/dyc/documentos") &&
                    directorioParcial.length() > LOGNITUD_MINIMA_RUTA) {
                    directorio = new File(directorioParcial);
                    directorio.setWritable(Boolean.TRUE);
                    directorio.setExecutable(Boolean.TRUE);
                    directorio.setReadable(Boolean.TRUE);
                }
            }
        }
    }

    /**
     * Modifica la estructura del RFC, eliminando de estos, las letras 'Ñ' y '&'.
     *
     * @param rfc
     * @return
     */
    public static String corregirRFC(String rfc) {

        String rfcTemporal = "";
        if (rfc.contains("Ñ") || rfc.contains("&")) {
            rfcTemporal = rfc.replaceAll("[Ñ&]+", "");
            return rfcTemporal;
        } else {
            return rfc;
        }
    }

}
