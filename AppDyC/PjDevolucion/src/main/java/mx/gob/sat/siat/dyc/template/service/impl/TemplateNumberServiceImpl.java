package mx.gob.sat.siat.dyc.template.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.generico.util.QRCodeUtil;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.ZipperUtil;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.template.dao.TemplateNumberDAO;
import mx.gob.sat.siat.dyc.template.dto.TemplateNumberDTO;
import mx.gob.sat.siat.dyc.template.dto.URLCompleteTemplateDTO;
import mx.gob.sat.siat.dyc.template.dto.URLTemplateDTO;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.template.web.util.LoadItems;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPlantillador;
import mx.gob.sat.siat.pjplantillador.dto.Items;
import mx.gob.sat.siat.pjplantillador.servicios.GeneradorArchivo;
import org.apache.commons.io.FileUtils;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "templateNumberService")
public class TemplateNumberServiceImpl implements TemplateNumberService, ConstantesPlantillador {
    private static final Logger LOGGER = Logger.getLogger(TemplateNumberServiceImpl.class);

    private static final Integer NUMERO_RENGLONES = 3;

    @Autowired
    private TemplateNumberDAO templateNumberDAO;


    @Autowired
    private PersonaIDCService personaIDCService;

    public Map<String, Object> templateCreated(Map<String, Object> paramsEntrada) throws SIATException {
        LOGGER.debug("INICIO TemplateNumberServiceImpl");
        List<Items> listItems;
        Map<String, Object> returnD = null;
        try {
            String idPlantilla = paramsEntrada.get(ID_TEMPLATE).toString();
            LOGGER.debug("idPlantilla ->" + idPlantilla + "<-");
            TemplateNumberDTO datosDocumento =
                    getTemplateNumber(Integer.valueOf(paramsEntrada.get(QUERY_TO).toString()), idPlantilla,
                                      paramsEntrada.get(NUM_CONTROL_LABEL).toString());
            LoadItems cargadorItems = new LoadItems();

            List<Items> itemsFromMap = cargadorItems.loadItemsFromMapAnyWhere(paramsEntrada);
            LOGGER.debug("numero de items cargados con el HASHMAP para generar la plantilla ->" + itemsFromMap.size() +
                         "<-");

            List<Items> itemsFromQuery = cargadorItems.loadItemsFromAnyWhere(idPlantilla, datosDocumento);
            itemsFromQuery = itemsFromQuery != null ? itemsFromQuery : new ArrayList<Items>();
            LOGGER.debug("numero de items cargados con el QUERY para generar la plantilla ->" + itemsFromQuery.size() +
                         "<-");

            listItems = new ArrayList<Items>(itemsFromQuery);
            listItems.addAll(itemsFromMap);

            String claveAdministracion = "";
            String rfc = "";
            String numControl = "";
            String numeroDocumento = "";

            claveAdministracion = datosDocumento.getClaveAdministracion();
            rfc = datosDocumento.getRfcContribuyente();
            numControl = datosDocumento.getNumControl();
            numeroDocumento = datosDocumento.getNumeroDocumento();

            try
            {
                returnD = this.toCreate(listItems, idPlantilla, claveAdministracion, rfc, numControl, numeroDocumento);
            }
            catch(Exception e)
            {
             LOGGER.debug(e.getMessage());
            }
            
            return returnD;
        } catch (RuntimeException e) {
            LOGGER.error(e);
            throw new SIATException(e);
        }
    }

    @Transactional
    public TemplateNumberDTO getTemplateNumber(Integer queryByConsult, String idTemplate,
                                                     String numControl) throws SIATException {
        TemplateNumberDTO datosDocumento = null;
        List<TemplateNumberDTO> listaDocumentos = this.templateNumberDAO.itemsOfTemplateDAO(queryByConsult, idTemplate, numControl);
        if (!listaDocumentos.isEmpty()) {
            PersonaDTO persona = new PersonaDTO();
            persona.setBoId(listaDocumentos.get(0).getBoId());
            /**Se consulta domicilio actual y se sustituye por el del registro de la solicitud solo para presentarlo
             * en la plantilla*/
            PersonaUbicacionDTO ubicacion = personaIDCService.buscaUbicacion(persona);
            datosDocumento = listaDocumentos.get(0);
            if (null != ubicacion) {
                datosDocumento.setCalleNum(Utilerias.isNull(ubicacion.getCalle()) + " " + Utilerias.isNull(ubicacion.getNumeroExt()) +
                                      " " + Utilerias.isNull(ubicacion.getNumeroInt()));
                datosDocumento.setColonia(Utilerias.isNull(ubicacion.getColonia()));
                datosDocumento.setEntreCalles(getEntreCalles(ubicacion.getEntreCalle1(), ubicacion.getEntreCalle2()));
                datosDocumento.setDomicilio(getDomicilioActual(ubicacion));
                datosDocumento.setCp(Utilerias.isNull(ubicacion.getCodigoPostal()));
                datosDocumento.setEntidad(Utilerias.isNull(ubicacion.getEntFed()));
                datosDocumento.setMunicipio(Utilerias.isNull(ubicacion.getMunicipio()));
            }
        }
        return datosDocumento;
    }

    private String getEntreCalles(String c1, String c2) {
        String entreCalles = "ENTRE CALLES ";
        if (isNotNull(c1) && isNotNull(c2)) {
            entreCalles += c1.concat(" , " + c2);
        } else if (isNotNull(c1)) {
            entreCalles += c1.concat(",-");
        } else if (isNotNull(c2)) {
            entreCalles += "-, ".concat(c2);
        } else {
            entreCalles += "-";
        }
        return entreCalles;
    }

    private String getDomicilioActual(PersonaUbicacionDTO pUb) {
        StringBuilder domicilio = new StringBuilder();
        domicilio.append(Utilerias.isNull(pUb.getCalle()));
        domicilio.append(" ");
        domicilio.append(Utilerias.isNull(pUb.getNumeroExt()));
        domicilio.append(" ");
        domicilio.append(Utilerias.isNull(pUb.getNumeroInt()));
        domicilio.append(" ");
        domicilio.append(Utilerias.isNull(pUb.getColonia()));
        domicilio.append(" ");
        domicilio.append(getEntreCalles(pUb.getEntreCalle1(), pUb.getEntreCalle2()));
        domicilio.append(" ");
        domicilio.append(Utilerias.isNull(pUb.getCodigoPostal()));
        domicilio.append(" ");
        domicilio.append(Utilerias.isNull(pUb.getMunicipio()));
        domicilio.append(" ");
        domicilio.append(Utilerias.isNull(pUb.getEntFed()));
        return domicilio.toString();
    }

    private boolean isNotNull(Object object) {
        boolean res = false;
        if (null != object && !object.toString().trim().isEmpty()) {
            res = Boolean.TRUE;
        }
        return res;
    }

    @Override
    @Transactional
    public URLTemplateDTO getURLTemplate(Integer idTemplate) throws SIATException {
        URLTemplateDTO urlTemplateDTO;
        try {
            urlTemplateDTO = this.templateNumberDAO.itemsOfUrlTemplateDAO(idTemplate);

        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new SIATException(ex);
        }
        return urlTemplateDTO;
    }

    public Map<String, Object> toCreate(List<Items> entryLoad, String idTemplate, String claveAdministracion,
                                        String rfc, String numControl, String numeroDocumento) throws SIATException {

        Map<String, Object> resolution = new HashMap<String, Object>();

        try {
            List<Items> infoDocEnv = entryLoad != null ? entryLoad : new ArrayList<Items>();
            URLTemplateDTO urlTemplateDTO = this.getURLTemplate(Integer.valueOf(idTemplate));
            StringBuilder urlFileSystem = new StringBuilder();

            urlFileSystem.append(PATH_OF_TEMPLATE).append(claveAdministracion).append(SLASH).append(corregirRFC(rfc)).append(SLASH).append(numControl).append(SLASH).append(GESTION_DOC);
            File file = new File(urlFileSystem.toString());
            Boolean createDirectory = file.mkdirs();

            if (createDirectory) {
                LOGGER.info("se creo el directorio: " + file.getAbsolutePath());
                Utilerias.cambiarPermisosDirectorio(file.getAbsolutePath());
            }

            StringBuilder sbNumControlDoc;

            if (idTemplate.length() == ConstantesDyCNumerico.VALOR_2) {
                sbNumControlDoc = new StringBuilder("0").append(idTemplate).append(numeroDocumento);
            } else if (idTemplate.length() == ConstantesDyCNumerico.VALOR_1) {
                sbNumControlDoc = new StringBuilder("00").append(idTemplate).append(numeroDocumento);
            } else {
                sbNumControlDoc = new StringBuilder(idTemplate).append(numeroDocumento);
            }

            GeneradorArchivo app =
                new GeneradorArchivo(urlTemplateDTO.getUrlOfConfiguration(), urlTemplateDTO.getUrlOfTemplate(),
                                     new StringBuilder(urlFileSystem).append(SLASH).append(sbNumControlDoc).append(UNDERSCORE).append(urlTemplateDTO.getNameOfTemplate().trim()).append(END_OF_PATH).toString());
            app.setInfoDocEnv(infoDocEnv);

            int formaEjecucion = 0;
            // 0: cuando se le envían los tags en forma dinámica
            // 1: sustituye todos los tags por el texto que se le envía en nomDescrPruebas

            String nomDescrPruebas = "Info";
            // etiqueta con la cual se presentan dentro del documento (etiqueta abierta),
            // solo aplica cuando la forma de ejecucion es 1 en las tablas

            // numero de renglones a generar; solo aplica cuando la forma de ejecucion es 1 en las tablas

            int retorno = app.leerConfiguracion(formaEjecucion, nomDescrPruebas, NUMERO_RENGLONES);

            StringBuilder nameOfDocument =
                new StringBuilder(sbNumControlDoc).append(UNDERSCORE).append(urlTemplateDTO.getNameOfTemplate().trim()).append(END_OF_PATH);
            if (retorno >= 0) {

                if (app.generacion() == 0) {

                    resolution.put(SUCCESS, Boolean.TRUE);
                    resolution.put(URL, urlFileSystem.toString().trim());
                    resolution.put(NOMBRE_ARCHIVO, nameOfDocument.toString().trim());
                    resolution.put(NUMERO_DOCUMENTO, sbNumControlDoc.toString());


                }

            } else {

                resolution.put(SUCCESS, Boolean.FALSE);
                resolution.put(URL, null);
                resolution.put(NOMBRE_ARCHIVO, null);
            }
            Utilerias.cambiarPermisosAFichero(new StringBuilder(urlFileSystem).append(SLASH).append(sbNumControlDoc).append(UNDERSCORE).append(urlTemplateDTO.getNameOfTemplate().trim()).append(END_OF_PATH).toString());

        } catch (RuntimeException ex) {
            LOGGER.error(ex);
            ManejadorLogException.getTraceError(ex);
            throw new SIATException(ex);
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new SIATException(ex);
        }
        return resolution;
    }

    @Override
    public Map<String, Object> createTemplateOfJustMap(Map<String, Object> introMap) throws SIATException {
        Map<String, Object> templateCreated;

        TemplateNumberDTO datosDocumento;
        List<Items> listItems;

        try {
            datosDocumento =
                    this.getTemplateNumber(Integer.valueOf(introMap.get(QUERY_TO).toString()), introMap.get(ID_TEMPLATE).toString(),
                                           introMap.get(NUM_CONTROL_LABEL).toString());
            LoadItems loadFromMap = new LoadItems();

            List<Items> itemsFromQuery =
                loadFromMap.loadItemsFromAnyWhere(introMap.get(ID_TEMPLATE).toString(), datosDocumento);

            List<Items> itemsFromMap = loadFromMap.loadItemsFromMapAnyWhere(introMap);
            listItems = new ArrayList<Items>(itemsFromMap);
            listItems.addAll(itemsFromQuery);

            String claveAdministracion = "";
            String rfc = "";
            String numControl = "";
            String numeroDocumento = "";

            claveAdministracion = datosDocumento.getClaveAdministracion();
            rfc = datosDocumento.getRfcContribuyente();
            numControl = datosDocumento.getNumControl();
            numeroDocumento = datosDocumento.getNumeroDocumento();

            templateCreated =
                    toCreate(listItems, introMap.get(ID_TEMPLATE).toString(), claveAdministracion, rfc, numControl,
                             numeroDocumento);

        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new SIATException(ex);
        }
        return templateCreated;
    }

    public Boolean isCompletarDocumento(String selloDigital, String cadenaOriginal, String firmaDigital, String idDocumentoReq,
            String employeeName, Map<String, Object> datosQr) throws SIATException {
        Boolean isModifed = false;

        LoadItems loadForCompleteTemplate = new LoadItems();
        try {
            List<Items> itemsOfComplete
                    = loadForCompleteTemplate.completeTemplate(selloDigital, cadenaOriginal, firmaDigital, employeeName);

            URLCompleteTemplateDTO urlCompleteTemplateDTO
                    = this.templateNumberDAO.itemsOfUrlOfCompleteTemplateDAO(idDocumentoReq);
            GeneradorArchivo app
                    = new GeneradorArchivo(urlCompleteTemplateDTO.getUrlOfConfiguration(), new StringBuilder(urlCompleteTemplateDTO.getUrlOfDestinity()).append(SLASH).append(urlCompleteTemplateDTO.getUrlName()).toString(),
                            new StringBuilder(urlCompleteTemplateDTO.getUrlOfDestinity()).append(SLASH).append(urlCompleteTemplateDTO.getUrlName()).toString());
            Utilerias.cambiarPermisosAFichero(new StringBuilder(urlCompleteTemplateDTO.getUrlOfDestinity()).append(SLASH).append(urlCompleteTemplateDTO.getUrlName()).toString());
            app.setInfoDocEnv(itemsOfComplete);
            int retorno = app.leerConfiguracion(0, "Info", LABEL_THREE);

            if (retorno >= 0) {
                if (app.generacion() == 0) {
                    isModifed = Boolean.TRUE;

                    Integer plantillaId = (Integer) datosQr.get(ID_TEMPLATE);
                    LOGGER.info("datosQr.get plantillaId: " + plantillaId);
                    if (plantillaId == ConstantesDyCNumerico.VALOR_121
                            || plantillaId == ConstantesDyCNumerico.VALOR_122) {
                        LOGGER.info("plantillaId == ->" + plantillaId + "<--- SE INSERTA QR AL DOCUMENTO");
                        LOGGER.info("datosQr: " + datosQr);
  
                        String ochoChars = Utils.getLastCharacters(datosQr.get("firmaElectronica").toString(), ConstantesDyCNumerico.VALOR_8);

                        StringBuilder qrContent = new StringBuilder();
                        qrContent.append(datosQr.get("enlacePortalConsultaByqr").toString())
                                .append(D1).append(AMPERSAND).append(D2).append(AMPERSAND);

                        StringBuilder contentD3 = new StringBuilder();
                        contentD3.append(D3).append(EQUAL_SIGN)
                                .append(corregirRFC(datosQr.get("rfcContribuyente").toString()))
                                .append(UNDERSCORE).append("199-"+datosQr.get("folio").toString())
                                .append(UNDERSCORE).append(ochoChars)
                                .append(UNDERSCORE).append("RESOLUCION")
                                .append(UNDERSCORE).append(CODIGO_QR);

                        qrContent.append(AMPERSAND).append(contentD3.toString());

                        insertQRCode(urlCompleteTemplateDTO.getUrlOfDestinity(), urlCompleteTemplateDTO.getUrlName(), qrContent.toString());
                    }
                }
            } else {
                isModifed = false;
            }
        } catch (IOException ex) {
            LOGGER.error(ex);
          throw new SIATException(ex);
        } catch (mx.gob.sat.siat.dyc.generico.util.common.SIATException ex) {
            LOGGER.error(ex);
             throw new SIATException(ex);
        } catch (SIATException ex) {
            LOGGER.error(ex);
             throw new SIATException(ex);
        }

            return isModifed;
        }

    /**
     * Modifica la estructura del RFC, eliminando de estos, las letras 'Ã‘' y
     * '&'.
     *
     * @param rfc
     * @return
     */
    private String corregirRFC(String rfc) {

        String rfcTemporal = "";
        if (rfc.contains("Ã‘") || rfc.contains("&")) {
            rfcTemporal = rfc.replaceAll("[Ã‘&]+", "");
            return rfcTemporal;
        } else {
            return rfc;
        }

    }

   /**
     *
     * @param urlFileSystem carpeta donde se guarda el documento .docx
     * @param nameOfDocument nombre del docuemento a insertar el codigo QR con
     * extension .docx
     * @param qrContent contenido para el codigo QR
     * @throws java.io.IOException
     */
    public static void insertQRCode(String urlFileSystem, String nameOfDocument,
            String qrContent) throws IOException {

        LOGGER.info("insertQRCode");
        LOGGER.info("urlFileSy: " + urlFileSystem);
        LOGGER.info("nameOfDocument: " + nameOfDocument);
        LOGGER.info("qrContent: " + qrContent);

        String removedDocExtension = nameOfDocument.replaceAll(END_OF_PATH, "");
        File qrCodeImage = new File(urlFileSystem.concat(SLASH).concat(removedDocExtension.trim())
                .concat(PNG_EXTENSION));
        InputStream qr = QRCodeUtil.getQr(qrContent, QR_WIDTH, QR_HEIGHT, FORMATO_QR);
        QRCodeUtil.saveQrCodeToDirectory(qr, qrCodeImage);

        File fileZipped = new File(urlFileSystem.concat(SLASH).concat(removedDocExtension)
                .concat(ZIP_EXTENSION));
        File fileDocxToZipped = new File(urlFileSystem.concat(SLASH).concat(removedDocExtension).concat(END_OF_PATH));
        boolean hasBeenZipped = fileDocxToZipped.renameTo(fileZipped);

        File tempDirectory = new File(urlFileSystem.concat(SLASH).concat(removedDocExtension));

        boolean tempDirectoryCreated = tempDirectory.mkdir();

        if (tempDirectoryCreated && hasBeenZipped) {

            boolean unzipped = ZipperUtil.unzip(fileZipped.getPath(), tempDirectory.getPath());

            if (unzipped) {

                File fileQR = new File(urlFileSystem.concat(SLASH).concat(removedDocExtension).concat(PNG_EXTENSION));

                File pathWordMedia = new File(tempDirectory.getPath().concat(SLASH).concat(WORD_MEDIA_DIRECTORY));
                FileUtils.copyFileToDirectory(fileQR, pathWordMedia);
                String fullPathDefaultQRImage = tempDirectory.getPath()
                        .concat(SLASH).concat(WORD_MEDIA_DIRECTORY).concat(SLASH)
                        .concat(DEFAULT_QR_IMAGE_TEMPLATE).concat(PNG_EXTENSION);
                File deleteDefaultQRImage = new File(fullPathDefaultQRImage);
 
                if(deleteDefaultQRImage.delete())
                {
                    LOGGER.info("Se borro el documento para QR");
                }else
                {
                    LOGGER.info("No se borro el documento para QR");
                }

                File fileToRename = new File(tempDirectory.getPath()
                        .concat(SLASH).concat(WORD_MEDIA_DIRECTORY).concat(SLASH)
                        .concat(removedDocExtension).concat(PNG_EXTENSION));

                File fileRenamed = new File(tempDirectory.getPath()
                        .concat(SLASH).concat(WORD_MEDIA_DIRECTORY).concat(SLASH)
                        .concat(DEFAULT_QR_IMAGE_TEMPLATE).concat(PNG_EXTENSION));

                boolean renameTo1 = fileToRename.renameTo(fileRenamed);
                LOGGER.info("se RENOMBRo QR!!: " + renameTo1);

                String folderToZip = tempDirectory.getPath().concat(SLASH);
                String zippedFolder = urlFileSystem.concat(SLASH).concat(removedDocExtension)
                        .concat(ZIP_EXTENSION);
                LOGGER.info("se comprime!!!");
                ZipperUtil.zip(folderToZip, zippedFolder);

                File fileDocx = new File(urlFileSystem.concat(SLASH).concat(removedDocExtension)
                        .concat(END_OF_PATH));
                
                File fileZipToDocx = new File(urlFileSystem.concat(SLASH).concat(removedDocExtension).concat(ZIP_EXTENSION));
                LOGGER.info("se renombra!!");
                boolean renamedToDocx = fileZipToDocx.renameTo(fileDocx);
                LOGGER.info("renamedToDocx: " + renamedToDocx);

                FileUtils.deleteDirectory(new File(urlFileSystem.concat(SLASH).concat(removedDocExtension)));
                File qrImageDelete = new File(urlFileSystem.concat(SLASH).concat(removedDocExtension).concat(PNG_EXTENSION));
                boolean deletedQR = qrImageDelete.delete();
                LOGGER.info("qrImageDelete: " + deletedQR);
            }
        }
    }
}
