/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.generico.util.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;


/**
 * Clase de utileria para metodos genericos
 * @author  Alfredo Ramirez
 * @since   05/11/2013
 */
public final class Utilerias {
    

    private static final Logger LOG = Logger.getLogger(Utilerias.class);
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
                    boolean cambioPermisos = directorio.setWritable( Boolean.TRUE,  Boolean.FALSE);
                    LOG.debug("cambiarPermisosDirectorio: --> " + cambioPermisos);
                    directorio.setExecutable( Boolean.TRUE,  Boolean.FALSE);
                    directorio.setReadable( Boolean.TRUE,  Boolean.FALSE);
                }
            }
        }
    }
    
    public static void cambiarPermisosAFichero(String rutaDelArchivo) {
        File archivo = new File(rutaDelArchivo);
        if (archivo.exists()) {
            boolean cambioPermisos = archivo.setWritable( Boolean.TRUE,  Boolean.FALSE);
            LOG.debug("cambiarPermisosAFichero: --> " + cambioPermisos);
            archivo.setExecutable( Boolean.TRUE,  Boolean.FALSE);
            archivo.setReadable( Boolean.TRUE,  Boolean.FALSE);
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
    
    /**
     * metodo que recibe un objto tipo File y regresa un array de bytes
     * @param file
     * @return 
     * @throws java.io.IOException 
     */
    public static byte[] toByteArray(File file) throws IOException{
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(bFile);
        fileInputStream.close();
        return bFile;
    }


}
