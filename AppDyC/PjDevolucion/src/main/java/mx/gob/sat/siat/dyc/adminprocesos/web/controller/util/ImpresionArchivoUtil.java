package mx.gob.sat.siat.dyc.adminprocesos.web.controller.util;

import java.io.File;
import java.io.PrintWriter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.List;

import mx.gob.sat.siat.dyc.tesofe.util.constante.ConstantesTESOFE;

import org.apache.log4j.Logger;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public final class ImpresionArchivoUtil {
    private ImpresionArchivoUtil() {
        super();
    }

    private static final String PIPE = "|";
    private static final Logger LOGGER = Logger.getLogger(ImpresionArchivoUtil.class);

    /**
     * Genera un archivo de texto con los datos especificados. Para utilizar este M&eacute;todo se requiere
     * que se le pase como par&acute;metro el nombre del mismo, la ruta donde se va a depositar y el tipo
     * de dato.
     *
     * @param nombreArchivo Nombre del documento.
     * @param ruta Ruta del filesystem donde se va a despositar el fichero.
     * @param listaDatos Informaci&oacute;n a despositar dentro del mismo.
     */
    public static void generarArchivo(String nombreArchivo, String ruta, List<?> listaDatos) {
        Method metodo = null;
        Class<?> clase = null;
        PrintWriter escritor = null;
        StringBuilder renglon = null;
        Field[] listaDeCampos = null;
        try {
            escritor = new PrintWriter(new File(ruta.concat(nombreArchivo)), ConstantesTESOFE.CODIFICACION);

            for (Object objeto : listaDatos) {
                clase = objeto.getClass();
                listaDeCampos = clase.getDeclaredFields();
                renglon = new StringBuilder();
                for (int i = 0; i < listaDeCampos.length; i++) {
                    metodo = clase.getDeclaredMethod("get" + regresarNombreDelCampo(listaDeCampos[i]));
                    renglon.append(metodo.invoke(objeto, new Object[0])).append(PIPE);
                }
                escritor.print(renglon.append("\r\n"));
                clase = null;
                metodo = null;
                renglon = null;
            }
            escritor.close();

        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    /**
     * Para el campo de una clase dado, se regresa el nombre de su m&eacute;todo con la primer letra
     * may&aacute;scula.
     *
     * @param campo nombre del campo.
     * @return nombre del m&eacute;todo get.
     */
    private static String regresarNombreDelCampo(Field campo) {
        String nombreCampo = campo.getName();
        return nombreCampo.substring(0, 1).toUpperCase() + nombreCampo.substring(1, nombreCampo.length());
    }
}
