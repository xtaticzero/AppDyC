package mx.gob.sat.mat.dyc.batch.devautomaticas.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.dyc.batch.devautomaticas.dto.TramiteDTO;
import mx.gob.sat.mat.dyc.batch.devautomaticas.util.constante.EstatusEnum;
import org.apache.log4j.Logger;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public final class FileUtil {
  private static final Logger LOGGER = Logger.getLogger(FileUtil.class);
    private FileUtil() {
    }

    public static List<TramiteDTO> procesarArchivo(File file) throws  IOException {
        List<TramiteDTO> listTramites = new ArrayList<TramiteDTO>();

        String cadena;

        BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()));

        int id = 0;
        String st[];
        TramiteDTO tramite;
        while ((cadena = b.readLine()) != null) {
            if (!cadena.trim().isEmpty()) {
                st = cadena.split("\\|");
                if (st.length == 2) {
                    tramite = new TramiteDTO(++id, st[0].trim(), st[1].trim(), EstatusEnum.WAIT, "");
                } else {
                    tramite = new TramiteDTO(++id, st[0], null, EstatusEnum.ERROR, "");
                }
                listTramites.add(tramite);
            }
        }
        b.close();

        return listTramites;
    }

    public static void escribirArchivoSalida(File archivo, List<TramiteDTO> tramites) throws IOException {
        if (!archivo.getParentFile().exists()) {
            boolean mkdir = archivo.getParentFile().mkdir();
            if (mkdir) {
                LOGGER.info("Se creo la ruta " + archivo.getParent());
            }
        }

        if (!archivo.exists()) {
            boolean newFile = archivo.createNewFile();
            if (newFile) {
                LOGGER.info("Se creo el archivo " + archivo.getAbsolutePath());
            }
        }


        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo.getAbsoluteFile()), Charset.defaultCharset()));

        for (TramiteDTO tramite : tramites) {
            bw.write(tramite.getNumControl() + "|" + tramite.getRfc() + "|" + tramite.getObservacion());
            bw.newLine();
        }
        bw.close();
    }

    public static boolean renombrarArchivoProcesado(File archivo, File archivoRenombrado) {
        return archivo.renameTo(archivoRenombrado);
    }

    public static String getNombreArchivo(String archivo) {
        int index = archivo.lastIndexOf('.');
        return archivo.substring(0, index);
    }

    public static String getExtensionArchivo(String archivo) {
        int index = archivo.lastIndexOf('.');
        return archivo.substring(index + 1);
    }
}
