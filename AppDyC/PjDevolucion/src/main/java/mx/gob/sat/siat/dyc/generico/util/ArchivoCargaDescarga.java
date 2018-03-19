/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.generico.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.MimetypesFileTypeMap;

import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.primefaces.model.DefaultStreamedContent;


/**
 * Clase generica para descargar archivos
 * @author  Alfredo Ramirez
 * @since   06/01/2014
 */
public class ArchivoCargaDescarga {

    private Logger log = Logger.getLogger(ArchivoCargaDescarga.class);

    private Mensaje mensaje = new Mensaje();

    /**
     *
     * @param descargarArchivo
     * @return
     */
    public DefaultStreamedContent descargarArchivo(String descargarArchivo) {

        DefaultStreamedContent archivo = null;
        File tempFile = new File(descargarArchivo);
        byte[] contenido = null;

        try {
            contenido = Utilerias.toByteArray(tempFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        archivo =
                new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                                           tempFile.getName());

        return archivo;
    }
 public DefaultStreamedContent descargarArchivoTipoExcel(String descargarArchivo) {

        DefaultStreamedContent archivo = null;
        File tempFile = new File(descargarArchivo);
        byte[] contenido = null;

        try {
            contenido = Utilerias.toByteArray(tempFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        archivo =
                new DefaultStreamedContent(new ByteArrayInputStream(contenido), "application/xls",
                                           tempFile.getName());

        return archivo;
    }
    /**
     *
     * @param msgComponente
     * @param eliminarArchivo
     */
    public void eliminarArchivo(String msgComponente, String eliminarArchivo) {
        File file = new File(eliminarArchivo);
        if (file.delete()) {
            mensaje.addInfo(msgComponente, "El archivo ha sido borrado exitosamente.");
        } else {
            mensaje.addInfo(msgComponente, "El archivo no puedo ser borrado.");
        }
    }

    /**
     *
     * @param nomArchivo
     * @param archivo
     * @param url
     * @param tamanio
     * @throws SIATException
     */
    public void escribirArchivo(String nomArchivo, InputStream archivo, String url, int tamanio) throws SIATException {
        OutputStream out = null;
        try {
            if (url != null) {
                String[] carpetasArray = url.split("/");
                File urlTemp = new File("" + carpetasArray[0]);
                if (urlTemp.mkdir()) {
                    Utilerias.cambiarPermisosDirectorio(urlTemp.getAbsolutePath());
                    log.info("Se creo correctamente");
                }
                for (int i = 1; i < carpetasArray.length; i++) {
                    urlTemp = new File(urlTemp + "/" + carpetasArray[i]);
                    if (urlTemp.mkdir()) {
                        Utilerias.cambiarPermisosDirectorio(urlTemp.getAbsolutePath());
                        log.info("Se creo correctamente");
                    }
                }

                File file;
                file = new File(url + "/");
                out = new FileOutputStream(new File(file, nomArchivo));
                int read = 0;
                byte[] bytes = new byte[tamanio];
                while ((read = archivo.read(bytes)) != -1) {
                    out.write(bytes, ConstantesDyCNumerico.VALOR_0, read);
                }
                archivo.close();
                out.flush();
                out.close();
            } else {
                throw new IOException("El campo de URL en escribirArchivo es null");
            }

        } catch (IOException e) {
            throw new SIATException("ERROR AL ESCRIBIR ARCHIVO EN DISCO", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ie) {
                log.error(ie.getMessage());
            }
        }
    }


}
