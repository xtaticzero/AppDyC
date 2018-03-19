package mx.gob.sat.siat.archivoshistorico.utils;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.archivoshistorico.dao.sql.Querys;

import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public final class Utils implements Querys {
    private Utils() {
        super();
    }
    
    private static final Logger LOGGER = Logger.getLogger(Utils.class);
    private static final int VALOR_CIEN = 100;
    
    /**
     * Agrega una determinada cantidad de ceros a la izquierda a una cadena.
     *
     * @param cadena Cadena a la cual se agregar&aacute; ceros a la izquierda.
     * @param largo La longitud de 
     * @return
     */
    public static String agregarCerosALaIzquierda(String cadena, int largo) {
        StringBuilder ceros = new StringBuilder();
        int cantidad = largo - cadena.length();
        for (int i = 0; i < cantidad; i++) {
            ceros.append("0");
        }
        return ceros.append(cadena).toString();
    }
    
    /**
     * Copia los archivos de una carpeta de un fileSystem  a otra.
     *
     * @param rutaOrigen Es la ruta donde se encuentra el archivo a copiar
     * @param rutaDestino Es la ruta donde se depostar&aacute; el archivo
     * @throws IOException Excepci&acute;n generada durante el copiado.
     */
    public static void copiarDocumento(String rutaOrigen, String rutaDestino) throws IOException {
        File archivoOrigen = new File(rutaOrigen);
        File archivoDestino = new File(rutaDestino);
        FileUtils.copyFile(archivoOrigen, archivoDestino);
        boolean a = archivoDestino.setReadable(Boolean.TRUE, Boolean.FALSE);
        boolean b = archivoDestino.setWritable(Boolean.TRUE, Boolean.FALSE);
        boolean c = archivoDestino.setExecutable(Boolean.TRUE, Boolean.FALSE);
        LOGGER.debug("a: " + a + " b:" + b + " c:" + c);
    }
    
    /**
     * Obtiene el espacio disponible en disco duro, el cual servir&aacute; como punto de partida para determinar si
     * se necesita de el uso de un nuevo filesystem o si se necesita acutalizar uno ya existente.
     *
     * @param puntoDeMontaje
     * @return
     */
    public static Double obtenterEspacioUtilizadoEnDisco(String puntoDeMontaje) {
        File disco = new File(puntoDeMontaje);
        LOGGER.error("sizelibre:"+disco.getFreeSpace()+" size total:"+disco.getTotalSpace());
        return Double.valueOf(VALOR_CIEN-(disco.getFreeSpace()*VALOR_CIEN)/disco.getTotalSpace());
    }
    
    /**
     * Genera una lista de los queries a utilizar para consultar todos los archivos adjuntos a un tr&aacute;mite.     *
     * 
     * @return
     */
    public static List<String> listarQueries() {
        List<String> s = new ArrayList<String>();
        s.add(ANEXOREQ);
        s.add(DOCUMENTO);
        s.add(OTROARCHIVO);
        s.add(ARCHIVOINFREQ);
        s.add(ARCHIVO);
        s.add(SOLAANEXOTRAM);
        return s;
    }
    
    /**
     * Obtiene el punto de montaje actual a partir de la URL donde se tiene actualmente guardado el archivo.
     *
     * @param url Ruta donde se tiene guardado el archivo a mover de filesystem
     * @return
     */
    public static String obtenerPuntoDeMontajeActual(String url) {
        String[] arreglo = url.split(Constantes.SLASH);
        return Constantes.SLASH+arreglo[1]+Constantes.SLASH+arreglo[ConstantesDyCNumerico.VALOR_2];
    }
}

