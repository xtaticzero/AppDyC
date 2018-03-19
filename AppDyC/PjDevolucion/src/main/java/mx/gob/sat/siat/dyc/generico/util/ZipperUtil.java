/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.generico.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

/**
 *
 * @author root
 */
public final class ZipperUtil {

    private static final Logger LOG = Logger.getLogger(QRCodeUtil.class.getName());

    private ZipperUtil() {

    }

    /**
     *
     * @param zipFilePath
     * @param destDir
     * @return
     */
    public static boolean unzip(String zipFilePath, String destDir) {

        File dir = new File(destDir);
        FileOutputStream fos = null;
        ZipInputStream zis = null;
        ZipEntry ze = null;
        FileInputStream fis = null;

        if (!dir.exists()) {
            if (dir.mkdirs()) {
                LOG.info("Se creo carpeta QR");
            } else {
                LOG.info("No se creo carpeta QR");
            }
        }

        byte[] buffer = new byte[ConstantesDyCNumerico.VALOR_1024];
        try {
            fis = new FileInputStream(zipFilePath);
            zis = new ZipInputStream(fis);
            ze = zis.getNextEntry();
            File newFile = null;
            String fileName = null;
            while (ze != null) {
                fileName = ze.getName();
                newFile = new File(destDir + File.separator + fileName);

                if (new File(newFile.getParent()).mkdirs()) {
                    LOG.info("Se creo carpeta QR " + destDir + File.separator + fileName);
                } else {
                    LOG.info("No se creo carpeta QR " + destDir + File.separator + fileName);
                }

                try {
                    fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                } catch (IOException ioe) {
                    LOG.error(ioe.getMessage(), ioe);
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }

                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return false;
        } finally {
            try {

                if (zis != null) {
                    zis.close();
                }

                if (fis != null) {
                    fis.close();
                }

            } catch (IOException e) {
                LOG.error(e.getMessage(), e);

            }
        }
        return true;
    }

    /**
     *
     * @param path
     * @param outputFile
     */
    public static void zip(String path, String outputFile) {

        int buffer = ConstantesDyCNumerico.VALOR_2048;
        boolean isEntry = false;
        BufferedInputStream bis = null;
        ZipOutputStream zos = null;
        ArrayList<String> directoryList = new ArrayList<String>();
        File f = new File(path);
        if (f.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(outputFile);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                byte data[] = new byte[buffer];

                if (f.isDirectory()) {

                    do {
                        String directoryName = "";
                        if (directoryList.size() > 0) {
                            directoryName = directoryList.get(0);

                        }
                        String fullPath = path + directoryName;
                        File fileList = null;
                        if (directoryList.size() == 0) {

                            fileList = f;
                        } else {

                            fileList = new File(fullPath);
                        }
                        String[] filesName = fileList.list();

                        int totalFiles = filesName.length;
                        for (int i = 0; i < totalFiles; i++) {
                            String name = filesName[i];
                            File filesOrDir = new File(fullPath + name);
                            if (filesOrDir.isDirectory()) {

                                ZipEntry entry = new ZipEntry(directoryName + name + "/");
                                zos.putNextEntry(entry);
                                isEntry = true;
                                directoryList.add(directoryName + name + "/");
                            } else {

                                ZipEntry entry = new ZipEntry(directoryName + name);
                                zos.putNextEntry(entry);
                                isEntry = true;
                                FileInputStream fileInputStream = new FileInputStream(filesOrDir);
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, buffer);
                                int size = -1;
                                while ((size = bufferedInputStream.read(data, 0, buffer)) != -1) {
                                    zos.write(data, 0, size);
                                }
                                bufferedInputStream.close();
                            }
                        }
                        if (directoryList.size() > 0 && directoryName.trim().length() > 0) {

                            directoryList.remove(0);
                        }

                    } while (directoryList.size() > 0);
                } else {
                    FileInputStream fis = new FileInputStream(f);
                    bis = new BufferedInputStream(fis, buffer);
                    ZipEntry entry = new ZipEntry(f.getName());
                    zos.putNextEntry(entry);
                    isEntry = true;
                    int size = -1;
                    while ((size = bis.read(data, 0, buffer)) != -1) {
                        zos.write(data, 0, size);
                    }
                }

                if (isEntry) {
                    zos.close();
                } else {
                    zos = null;
                    LOG.error("No Entry Found in Zip");
                }

                LOG.error("CHECK IS THERE ANY ENTRY IN ZIP ? ----START");
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException ex) {
                        LOG.error(ex.getMessage(), ex);
                    }
                }

                if (zos != null) {
                    try {
                        zos.close();
                    } catch (IOException ex) {
                        LOG.error(ex.getMessage(), ex);
                    }
                }
            }
        } else {
            LOG.error("File or Directory not found");
        }
    }
}
