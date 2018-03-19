/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.generico.util;

import java.io.IOException;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.primefaces.model.UploadedFile;


/**
 * Clase para validaci&oacute;n de archivo a subir.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Mayo 8, 2015
 * */
public class ArchivoValida extends ArchivoCargaDescarga {

    private static final Logger LOG = Logger.getLogger(ArchivoValida.class);
    private static String[] tipoArchivo;
    private UploadedFile archivo;
    private String nombre;
    private String ext;
    private StringBuilder ruta;

    static {
        tipoArchivo = new String[ConstantesDyCNumerico.VALOR_8];
        tipoArchivo[ConstantesDyCNumerico.VALOR_0] = ".zip";
        tipoArchivo[ConstantesDyCNumerico.VALOR_1] = ".jpg";
        tipoArchivo[ConstantesDyCNumerico.VALOR_2] = ".doc";
        tipoArchivo[ConstantesDyCNumerico.VALOR_3] = ".docx";
        tipoArchivo[ConstantesDyCNumerico.VALOR_4] = ".xls";
        tipoArchivo[ConstantesDyCNumerico.VALOR_5] = ".xlsx";
        tipoArchivo[ConstantesDyCNumerico.VALOR_6] = ".pdf";
        tipoArchivo[ConstantesDyCNumerico.VALOR_7] = ".jpeg";
    }

    public ArchivoValida() {
        super();
    }

    /**
     * Cosntructor con parametros
     * @param ruta : StringBuilder : path a escribir el archivo cargado
     */
    public ArchivoValida(StringBuilder ruta) {
        super();
        this.ruta = ruta;
    }

    private void validaNombre() {
        this.nombre = null;
        if (null != archivo) {
            this.nombre = archivo.getFileName();
            this.nombre = this.nombre.substring(this.nombre.lastIndexOf('\\') + 1, this.nombre.length());
        }
    }

    private void validaTipo() throws SIATException {
        boolean tip = false;
        for (int a = 0; a < tipoArchivo.length; a++) {
            if (nombre.lastIndexOf(tipoArchivo[a]) != -1) {
                tip = Boolean.TRUE;
                ext = tipoArchivo[a];
                break;
            }
        }
        if (!tip) {
            throw new SIATException("Error: el tipo del archivo no es correcto, favor de verificar");
        }
    }


    /**
     * Valida el archivo recibido desde PrimeFaces, por tipo de archivo, tama&ntilde;o (MB),
     * nombre, logitud de nombre.
     * @param archivo : UploadedFile : Archivo recibido desde PrimeFaces.
     * @param upload : boolean : Verdadero si se requiere escribir el archivo en el servidor
     * al final de la validaci&oacute;n.
     * @throws SIATException : regresa una excepci&oacute;n con el mensaje de la
     * validaci&oacute;n que fallo.
     */
    public void validaciones(UploadedFile archivo, HttpServletRequest request, boolean upload) throws SIATException {
        this.validaciones(archivo, request, null, upload);
    }

    /**
     * Valida el archivo recibido desde PrimeFaces, por tipo de archivo, tama&ntilde;o (MB),
     * nombre, logitud de nombre.
     * @param archivo : UploadedFile : Archivo recibido desde PrimeFaces.
     * @param upload : boolean : Verdadero si se requiere escribir el archivo en el servidor
     * al final de la validaci&oacute;n.
     * @throws SIATException : regresa una excepci&oacute;n con el mensaje de la
     * validaci&oacute;n que fallo.
     */
    public void validaciones(UploadedFile archivo, HttpServletRequest request, String nombrePlantillador,
                             boolean upload) throws SIATException {
        this.archivo = archivo;
        if (null == request.getHeader("X-Content-Scanning")) {
            if (null == this.archivo) {
                throw new SIATException("Error: debe seleccionar un archivo");
            } else {
                validaNombre();
                validaTipo();
                if (nombre.length() > ConstantesDyCNumerico.VALOR_50) {
                    throw new SIATException("Error: tamaño de nombre de documento inválido");
                } else if (ext.equals(tipoArchivo[ConstantesDyCNumerico.VALOR_0]) &&
                           this.archivo.getSize() > ConstantesDyCNumerico.VALOR_4194304) {

                    throw new SIATException("Error: el archivo " + nombre + " sobrepasa el peso permitido (4Mb)");
                } else if (!ext.equals(tipoArchivo[ConstantesDyCNumerico.VALOR_0]) &&
                           this.archivo.getSize() > ConstantesDyCNumerico.VALOR_1048576) {

                    throw new SIATException("Error: el archivo " + nombre + " sobrepasa el peso permitido (1Mb)");
                } else if ((null != nombrePlantillador) && (!nombrePlantillador.equals(nombre))) {
                    throw new SIATException("Error: el archivo " + nombre + " no corresponde con el generado");
                }

                this.guardar(upload);
            }
        } else {
            imprimirNombresCabeceras(request);
            throw new SIATException("Error: Se detecto virus en el archivo " + nombre + " ");
        }
    }

    private void guardar(boolean upload) throws SIATException {
        if (upload) {
            try {
                escribirArchivo(nombre, this.archivo.getInputstream(), ruta.toString(),
                                ConstantesDyCNumerico.VALOR_4096);
            } catch (IOException ioe) {
                throw new SIATException(ioe);
            } catch (Exception se) {
                throw new SIATException(se);
            }
        }
    }

    private void imprimirNombresCabeceras(HttpServletRequest request) {
        LOG.info("\n\n::::::::::::::::::  VIRUS DETECTADO :::::::::::::::::: \n :::::::::::::::::::::::::::::::::::: \n" +
                request + "\n::::::::::::::::::::::::::::::::::::");
        Enumeration names = request.getHeaderNames();
        int contCabeceras = 1;
        while (names.hasMoreElements()) {
            String nombreCabecera = (String)names.nextElement();
            LOG.info("\n NombreCabecera " + contCabeceras + " : " + nombreCabecera);
            Enumeration values = request.getHeaders(nombreCabecera);
            if (values != null) {
                while (values.hasMoreElements()) {
                    String value = (String)values.nextElement();
                    LOG.info("\n    valor : ->" + value + "<-");
                }
            }
            contCabeceras++;
        }
        LOG.info("\n:::::::::::::::::: FIN CABECERA VIRUS ::::::::::::::::::");
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setRuta(StringBuilder ruta) {
        this.ruta = ruta;
    }

    public StringBuilder getRuta() {
        return ruta;
    }
}
