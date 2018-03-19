package mx.gob.sat.siat.dyc.tesofe.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.tesofe.service.CambiosRetroalimentacionTESOFEService;
import mx.gob.sat.siat.dyc.tesofe.service.RetroalimentarTESOFEService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "retroalimentarTESOFEService")
public class RetroalimentarTESOFEServiceImpl implements RetroalimentarTESOFEService {


    private static final int CAMPOFECHA = 18;
    private static final int DATOS_RENGLON = 25;
    private static final int NUMEROCAMPOS = 27;
    private static final String PIPE = "\\|";
    private static final Logger LOGGER = Logger.getLogger(RetroalimentarTESOFEServiceImpl.class);

    private static final String TEXTO_ESPACIO = " ";

    @Autowired(required = true)
    private CambiosRetroalimentacionTESOFEService cambiosRetroalimentacionTESOFEService;


    /**
     * Lee el archivo que va a ser enviado
     */
    @Override
    public void retroalimentarTESOFE() { 
        LOGGER.debug("retroalimentarTESOFE");
        String ruta1="/siat/dyc/";
        String ruta2="TESOFE/recibir/";
        
        File directorio = new File(ruta1 + ruta2);
        File[] archivos = directorio.listFiles();

        for (int x = 0; x < archivos.length; x++) {
            if (archivos[x].exists()) {
                procesarArchivo(ConstantesDyCURL.URL_RECIBIDOS_TESOFE + archivos[x].getName());
            }
        }
    }

    /**
     * Inicia el caso de uso EnviarRetroalimentacionDeLaTESOFE
     */
    public void procesarArchivo(String rutaArchivo) {
        String renglon = "";
        String[] datosRenglon = null;
        BufferedReader bufferLectura = null;

        try {
            bufferLectura = new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"));

            // Leer el archivo linea por linea
            while ((renglon = bufferLectura.readLine()) != null) {
                if ( noSonCabeceras( renglon ) ){
                    
                    datosRenglon = renglon.split(PIPE);
                    if (validarRenglon(datosRenglon)) {
                        LOGGER.info(datosRenglon[DATOS_RENGLON]);
                        try {
                            cambiosRetroalimentacionTESOFEService.buscarFlujoAlterno(datosRenglon);
                        } catch (SIATException siate) {
                            LOGGER.error("retroalimentarTESOFE(), Hubo un error al leer el archivo: " + siate);
                        }
                    } else {
                        LOGGER.warn("Error, longitud del regnlon:  " + datosRenglon.length);
                    }

                }
            }
            if (bufferLectura != null) {
                bufferLectura.close();
            }
            moverArchivo(rutaArchivo);
        } catch (Exception ioe) {
            LOGGER.error("retroalimentarTESOFE(), Hubo un error al leer el archivo: " + ioe);
        }
    }
    
    private boolean noSonCabeceras ( String renglon ){
        return renglon.contains( TEXTO_ESPACIO );
    }

    /**
     * Valida que el renglon traiga todos los elementos
     *
     * @param datosRenglon arreglo que contiene todos los datos del renglon
     * @return verdadero si trae los 19 elementos, falso en caso contrario
     */
    private boolean validarRenglon(String[] datosRenglon) {
        boolean banderaValidarRenglon = false;

        if (datosRenglon.length == NUMEROCAMPOS) {
            banderaValidarRenglon = validarInformacionRenglon(datosRenglon);

        } else {
            banderaValidarRenglon = false;
        }
        return banderaValidarRenglon;
    }

    /**
     * Valida que los datos que se leen del archivo proporcionado de la TESOFE tengan los datos
     *
     * @param datosRenglon
     * @return
     */
    private boolean validarInformacionRenglon(String[] datosRenglon) {

        boolean banderaValidarInformacion = Boolean.TRUE;
        Date fechaSQL = null;
        try {
            crearFechaDeString(datosRenglon);

        } catch (Exception pe) {
            banderaValidarInformacion = false;
            LOGGER.error("FECHA ERROENA: " + datosRenglon[CAMPOFECHA] + ", fecha con formato=" + fechaSQL);
        }
        return banderaValidarInformacion;
    }

    /**
     * Devuelve un tipo de dato java.sql.Date a partir de un String
     *
     * @param datosRenglon
     * @return
     * @throws Exception
     */
    private Date crearFechaDeString(String[] datosRenglon) throws SIATException {
        Date fechaSQL = null;
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date fechaConFormato = formatoFecha.parse(datosRenglon[CAMPOFECHA]);
            fechaSQL = new Date(fechaConFormato.getTime());

        } catch (ParseException pe) {
            LOGGER.error("crearFechaDeString(): " + pe);
            throw new SIATException(pe);
        }
        return fechaSQL;
    }

    /**
     * Una vez leido el archivo que se trata en la retrolamentación, se se mueve a una carpeta de historico.
     *
     * @param rutaArchivo
     * @throws SIATException
     */
    private void moverArchivo(String rutaArchivo) throws SIATException {
        File archivoOrigen = new File(rutaArchivo);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
        String hora = dateFormat.format(System.currentTimeMillis());

        try {
            String ruta1 = "/siat/dyc/";
            String ruta2 = "historico/TESOFE/recibir";
            File archivoDestino = new File(rutaArchivo.split("\\.")[0] + "-" + hora + ".txt");
            if (archivoOrigen.renameTo(archivoDestino)) {
                File folder = new File(ruta1 + ruta2);
                if (!folder.exists() && folder.mkdir()) {
                    Utilerias.cambiarPermisosDirectorio(folder.getAbsolutePath());
                }
                File archivoFinal =
                    new File(ruta1 + ruta2 + archivoDestino.getAbsolutePath().substring(archivoDestino.getAbsolutePath().lastIndexOf("/"),
                                                                                        (archivoDestino.getAbsolutePath().length() -
                                                                                         1)));
                FileUtils.copyFile(archivoDestino, archivoFinal);
                if (archivoFinal.exists()) {
                    boolean a = archivoDestino.delete();
                    LOGGER.debug("valor" + a);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al mover el archivo: " + e);
            throw new SIATException(e);
        }
    }
}
