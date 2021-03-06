package mx.gob.sat.mat.dyc.batch.devautomaticas.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Date;
import mx.gob.sat.mat.dyc.batch.devautomaticas.dto.TramiteDTO;
import mx.gob.sat.mat.dyc.batch.devautomaticas.exception.DevAutomaticasException;
import mx.gob.sat.mat.dyc.batch.devautomaticas.service.DycAutomaticasDel;
import mx.gob.sat.mat.dyc.batch.devautomaticas.service.DycAutomaticasService;
import mx.gob.sat.mat.dyc.batch.devautomaticas.util.FechaUtil;
import mx.gob.sat.mat.dyc.batch.devautomaticas.util.FileUtil;
import mx.gob.sat.mat.dyc.batch.devautomaticas.util.constante.EstatusEnum;
import mx.gob.sat.mat.dyc.cargaautomaticas.CargaMasivoAutomaticasService;
import mx.gob.sat.siat.dyc.vo.CargaMasivaAutomaticasRegistroVO;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
@Component(value = "dycAutomaticasDel")
public class DycAutomaticasDelImpl implements DycAutomaticasDel {

    private static final Logger LOGGER = Logger.getLogger(DycAutomaticasDelImpl.class);
    private static final String TXT = ".txt";
    @Value("${pathFile}")
    private String path;

    @Value("${fileName}")
    private String fileName;

    @Autowired
    private DycAutomaticasService dycAutomaticasService;
    @Autowired
    private CargaMasivoAutomaticasService cargaMasivoAutomaticasService;

    @Override
    public void exec() {
        LOGGER.info("INICIA AUTOMATICAS... ");
        
        Charset charset = Charset.availableCharsets().get("UTF-8");
        if(charset == null){
            charset = Charset.defaultCharset();
        }
        
        File archivoProcesar = new File(path, fileName);
        CargaMasivaAutomaticasRegistroVO archivo = procesarTramites(archivoProcesar, charset);
        FileUtil.renombrarArchivoProcesado(archivoProcesar, new File(archivo.getRutaArchivoProcesar()));
        convertirSalidaExcel(archivo, charset);
        LOGGER.info("FINAL AUTOMATICAS... ");
    }

    private CargaMasivaAutomaticasRegistroVO procesarTramites(File archivoProcesar, Charset charset) {
        String cadena;
        BufferedReader b = null;
        BufferedWriter bwSuccess = null;
        BufferedWriter bwError = null;

        try {
            Date inicio = new Date();
            b = new BufferedReader(new InputStreamReader(new FileInputStream(archivoProcesar), charset));

            File pathOut = new File(path, FechaUtil.getFechaHoraSinFormato());
            boolean create = pathOut.mkdir();
            if (create) {
                LOGGER.info("Se creo la ruta " + pathOut.getAbsolutePath());
            }

<<<<<<< .mine
            File fileSuccess = new File(pathOut, EstatusEnum.SUCCESS.getValue() + TXT);
            create = fileSuccess.createNewFile();
            if (create) {
                LOGGER.info("Se creo el archivo " + fileSuccess.getAbsolutePath());
            }
            bwSuccess = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileSuccess), charset));

            File fileError = new File(pathOut, EstatusEnum.ERROR.getValue() + TXT);
            create = fileError.createNewFile();
            if (create) {
                LOGGER.info("Se creo el archivo " + fileError.getAbsolutePath());
            }
            bwError = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileError), charset));

            int id = 0;
            int totalSuccess = 0;
            int totalError = 0;
            String st[];
            TramiteDTO tramite;

            while ((cadena = b.readLine()) != null) {
                if (!cadena.trim().isEmpty()) {
                    st = cadena.split("\\|");

                    if (st.length == 2) {
                        tramite = new TramiteDTO(++id, st[0].trim(), st[1].trim(), EstatusEnum.WAIT, "");
                        try {

                            if (tramite.getNumControl() != null && tramite.getNumControl().startsWith("\uFEFF")) {
                                tramite.setNumControl(tramite.getNumControl().substring(1));
                            }

                            dycAutomaticasService.procesarTramite(tramite);
                            tramite.setEstatus(EstatusEnum.SUCCESS);
                            tramite.setObservacion("Procesado correctamente");
                            bwSuccess.write(tramite.getNumControl() + "|" + tramite.getRfc() + "|" + tramite.getObservacion());
                            bwSuccess.newLine();
                            totalSuccess++;

                        } catch (DevAutomaticasException ex) {
                            tramite.setEstatus(EstatusEnum.ERROR);
                            tramite.setObservacion(ex.getMessage());
                            bwError.write(tramite.getNumControl() + "|" + tramite.getRfc() + "|" + tramite.getObservacion());
                            bwError.newLine();
                            totalError++;
=======
            File fileSuccess = new File(pathOut, EstatusEnum.SUCCESS.getValue() + TXT);
            create = fileSuccess.createNewFile();
            if (create) {
                LOGGER.info("Se creo el archivo " + fileSuccess.getAbsolutePath());
            }
            bwSuccess = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileSuccess), charset));

            File fileError = new File(pathOut, EstatusEnum.ERROR.getValue() + TXT);
            create = fileError.createNewFile();
            if (create) {
                LOGGER.info("Se creo el archivo " + fileError.getAbsolutePath());
            }
            bwError = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileError), charset));

            int id = 0;
            int totalProcesar = 0;
            int totalSuccess = 0;
            int totalError = 0;
            String st[];
            TramiteDTO tramite;

            while ((cadena = b.readLine()) != null) {
                if (!cadena.trim().isEmpty()) {
                    st = cadena.split("\\|");
                    totalProcesar++;

                    if (st.length == 2) {
                        tramite = new TramiteDTO(++id, st[0].trim(), st[1].trim(), EstatusEnum.WAIT, "");
                        try {

                            if (tramite.getNumControl() != null && tramite.getNumControl().startsWith("\uFEFF")) {
                                tramite.setNumControl(tramite.getNumControl().substring(1));
                            }

                            dycAutomaticasService.procesarTramite(tramite);
                            tramite.setEstatus(EstatusEnum.SUCCESS);
                            tramite.setObservacion("Procesado correctamente");
                            bwSuccess.write(tramite.getNumControl() + "|" + tramite.getRfc() + "|" + tramite.getObservacion());
                            bwSuccess.newLine();
                            totalSuccess++;

                        } catch (DevAutomaticasException ex) {
                            tramite.setEstatus(EstatusEnum.ERROR);
                            tramite.setObservacion(ex.getMessage());
                            bwError.write(tramite.getNumControl() + "|" + tramite.getRfc() + "|" + tramite.getObservacion());
                            bwError.newLine();
                            totalError++;
>>>>>>> .r11377

                        }
                    } else {
                        bwError.write(cadena + "|No cumple con los parametros");
                        bwError.newLine();
                        totalError++;

                    }

                }
<<<<<<< .mine
            }

            File archivoProcesado = new File(pathOut, fileName);

            CargaMasivaAutomaticasRegistroVO archivo = new CargaMasivaAutomaticasRegistroVO();
            archivo.setRutaArchivoProcesar(archivoProcesado.getAbsolutePath());
            archivo.setRutaArchivoExitoso(fileSuccess.getAbsolutePath());
            archivo.setRutaArchivoFallido(fileError.getAbsolutePath());
            archivo.setNumeroSolicitudesProcesar(id);
            archivo.setNumeroSolicitudesExitosas(totalSuccess);
            archivo.setNumeroSolicitudesFallidas(totalError);
            archivo.setFechaInicioProcesoDate(inicio);
            archivo.setFechaTerminoProcesoDate(new Date());
=======
            }

            File archivoProcesado = new File(pathOut, fileName);

            CargaMasivaAutomaticasRegistroVO archivo = new CargaMasivaAutomaticasRegistroVO();
            archivo.setRutaArchivoProcesar(archivoProcesado.getAbsolutePath());
            archivo.setRutaArchivoExitoso(fileSuccess.getAbsolutePath());
            archivo.setRutaArchivoFallido(fileError.getAbsolutePath());
            archivo.setNumeroSolicitudesProcesar(totalProcesar);
            archivo.setNumeroSolicitudesExitosas(totalSuccess);
            archivo.setNumeroSolicitudesFallidas(totalError);
            archivo.setFechaInicioProcesoDate(inicio);
            archivo.setFechaTerminoProcesoDate(new Date());
>>>>>>> .r11377

            return archivo;
        } catch (RuntimeException ex) {
            LOGGER.error("Error en la lectura o escritura del archivo RuntimeException:: ", ex);
            return null;
        } catch (IOException ex) {
            LOGGER.error("Error en la lectura o escritura del archivo IOException:: ", ex);
            return null;
        } finally {
            if (b != null) {
                try {
                    b.close();
                } catch (IOException ex) {
                    LOGGER.error("Error al cerrar el archivo de lectura ", ex);
                }
            }
            if (bwSuccess != null) {
                try {
                    bwSuccess.close();
                } catch (IOException ex) {
                    LOGGER.error("Error al cerrar el archivo de exitosos ", ex);
                }
            }
            if (bwError != null) {
                try {
                    bwError.close();
                } catch (IOException ex) {
                    LOGGER.error("Error al cerrar el archivo de errores ", ex);
                }
            }

        }
    }

    private void convertirSalidaExcel(CargaMasivaAutomaticasRegistroVO archivo, Charset charset) {
        if (archivo != null) {
            convertirExcel(archivo.getRutaArchivoProcesar(), charset);
            convertirExcel(archivo.getRutaArchivoExitoso(), charset);
            convertirExcel(archivo.getRutaArchivoFallido(), charset);
            cargaMasivoAutomaticasService.updateCargaMasiva(archivo);
        }
    }

    private void convertirExcel(String inputFile, Charset charset) {
        BufferedReader reader = null;
        InputStream in = null;
        FileOutputStream out = null;
        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), charset));
            String[] dataArray;
            String delimiter = "\\|";
            String text;

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("overlayReport");

            int i = 0;
            while ((text = reader.readLine()) != null) {
                dataArray = text.split(delimiter);
                Row row = sheet.createRow(i);
                int cellnum = 0;
                for (String value : dataArray) {
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellValue(value);
                }
                i++;
            }

            out = new FileOutputStream(new File(inputFile.replace(TXT, ".xls")));
            workbook.write(out);

        } catch (FileNotFoundException ex) {
            LOGGER.error("error FileNotFoundException", ex);
        } catch (IOException ex) {
            LOGGER.error("error IOException", ex);
        } catch (Exception e) {
            LOGGER.error("error general", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                LOGGER.error("error al cerrar archivos XLS", ex);
            }
            try {
                if (reader != null) {
                    reader.close();

                }
            } catch (IOException ex) {
                LOGGER.error("error al cerrar archivos reader", ex);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                LOGGER.error("error al cerrar archivos in", ex);
            }

        }
    }
}
