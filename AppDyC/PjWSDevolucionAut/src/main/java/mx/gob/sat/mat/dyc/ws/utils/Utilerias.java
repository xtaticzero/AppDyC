
package mx.gob.sat.mat.dyc.ws.utils;

import java.io.*;

import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


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
    private static final String ENCODING_UTF8 = "UTF-8";

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
                    boolean cambioPermisos = directorio.setWritable(Boolean.TRUE, false);
                    LOG.debug("cambiarPermisosDirectorio: --> " + cambioPermisos);
                    directorio.setExecutable(Boolean.TRUE, false);
                    directorio.setReadable(Boolean.TRUE, false);
                }
            }
        }
    }
    
    public static void cambiarPermisosAFichero(String rutaDelArchivo) {
        File archivo = new File(rutaDelArchivo);
        if (archivo.exists()) {
            boolean cambioPermisos = archivo.setWritable(Boolean.TRUE, false);
            LOG.debug("cambiarPermisosAFichero: --> " + cambioPermisos);
            archivo.setExecutable(Boolean.TRUE, false);
            archivo.setReadable(Boolean.TRUE, false);
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
          FileInputStream fileInputStream=null;
          byte[] bFile = new byte[(int) file.length()];
     try{
      
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(bFile);

     } finally{
                if(fileInputStream!=null){
                fileInputStream.close();
                }
                
        }
        return bFile;
    }

    public static ByteArrayOutputStream dtoToXMLStream(Object objectDTO){
        try {
            JAXBContext jxbCtx = JAXBContext.newInstance(objectDTO.getClass());
            Marshaller marshaller = jxbCtx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING_UTF8);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            marshaller.marshal(objectDTO, stream);
            return stream;

        } catch (JAXBException e) {
            LOG.error("Ocurrio un error en al convertir el dto a xml: " + e.getMessage());
            return null;
        }
    }

    public static String dtoToXMLString(Object objectDTO) {
        try {
            ByteArrayOutputStream stream = dtoToXMLStream(objectDTO);
            return (new String(stream.toByteArray(), ENCODING_UTF8));
        } catch (Exception e) {
            LOG.error("Ocurrio un error en al convertir el dto a xml: " + e.getMessage());
            return null;
        }
    }

}
