package principal.service.impl;

import java.io.File;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.DyctAnexoDAO;
import mx.gob.sat.siat.dyc.dao.DyctArchivoDAO;
import mx.gob.sat.siat.dyc.dao.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.DyctPapelTrabajoDAO;
import mx.gob.sat.siat.dyc.dao.DyctSolicitudTempDAO;
import mx.gob.sat.siat.dyc.domain.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.DyctSolicitudTempDTO;
import mx.gob.sat.siat.dyc.service.DyctAnexoReqService;
import mx.gob.sat.siat.dyc.service.DyctInfoRequeridaService;
import mx.gob.sat.siat.dyc.service.DyctOtraInfoReqService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.bean.ProcesoDocumentoBean;
import principal.service.AdminDocumentoService;
import principal.util.Utilerias;

@Service("adminDocumentoService")
public class AdminDocumentoServiceImpl implements AdminDocumentoService {
    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;

    @Autowired
    private DyctArchivoDAO dyctArchivoDAO;

    @Autowired
    private DyctAnexoDAO dyctAnexoDAO;

    @Autowired
    private DyctPapelTrabajoDAO dyctPapelTrabajoDAO;

    @Autowired
    private DyctInfoRequeridaService dyctInfoRequeridaService;

    @Autowired
    private DyctOtraInfoReqService dyctOtraInfoReqService;

    @Autowired
    private DyctAnexoReqService dyctAnexoReqService;

    @Autowired
    private DyctSolicitudTempDAO dyctSolicitudTempDAO;

    private Logger log = Logger.getLogger(AdminDocumentoServiceImpl.class.getName());

    public static final String SE_ELIMINO = "Se elimino correctamente";

    public AdminDocumentoServiceImpl() {
        super();
    }

    public void setDyctDocumentoDAO(DyctDocumentoDAO dyctDocumentoDAO) {
        this.dyctDocumentoDAO = dyctDocumentoDAO;
    }

    public DyctDocumentoDAO getDyctDocumentoDAO() {
        return dyctDocumentoDAO;
    }

    public void setDyctArchivoDAO(DyctArchivoDAO dyctArchivoDAO) {
        this.dyctArchivoDAO = dyctArchivoDAO;
    }

    public DyctArchivoDAO getDyctArchivoDAO() {
        return dyctArchivoDAO;
    }

    public void setDyctAnexoDAO(DyctAnexoDAO dyctAnexoDAO) {
        this.dyctAnexoDAO = dyctAnexoDAO;
    }

    public DyctAnexoDAO getDyctAnexoDAO() {
        return dyctAnexoDAO;
    }

    public void setDyctPapelTrabajoDAO(DyctPapelTrabajoDAO dyctPapelTrabajoDAO) {
        this.dyctPapelTrabajoDAO = dyctPapelTrabajoDAO;
    }

    public DyctPapelTrabajoDAO getDyctPapelTrabajoDAO() {
        return dyctPapelTrabajoDAO;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    public void setDyctInfoRequeridaService(DyctInfoRequeridaService dyctInfoRequeridaService) {
        this.dyctInfoRequeridaService = dyctInfoRequeridaService;
    }

    public DyctInfoRequeridaService getDyctInfoRequeridaService() {
        return dyctInfoRequeridaService;
    }

    public void setDyctOtraInfoReqService(DyctOtraInfoReqService dyctOtraInfoReqService) {
        this.dyctOtraInfoReqService = dyctOtraInfoReqService;
    }

    public DyctOtraInfoReqService getDyctOtraInfoReqService() {
        return dyctOtraInfoReqService;
    }

    public void setDyctAnexoReqService(DyctAnexoReqService dyctAnexoReqService) {
        this.dyctAnexoReqService = dyctAnexoReqService;
    }

    public DyctAnexoReqService getDyctAnexoReqService() {
        return dyctAnexoReqService;
    }

    public void setDyctSolicitudTempDAO(DyctSolicitudTempDAO dyctSolicitudTempDAO) {
        this.dyctSolicitudTempDAO = dyctSolicitudTempDAO;
    }

    public DyctSolicitudTempDAO getDyctSolicitudTempDAO() {
        return dyctSolicitudTempDAO;
    }

    @Override
    public void eliminar() {

        File direcPrincipal = new File(ConstantesDyCURL.DOCUMENTOS_ADJUNTOS.toString());
        String[] directorio = direcPrincipal.list();
        Arrays.sort(directorio);

        if (directorio != null) {
            for (int i = ConstantesDyCNumerico.VALOR_0; i < directorio.length; i++) {
                File direcSecundario = new File(direcPrincipal + ConstantesDyCURL.DIAGONAL + directorio[i]);
                if(!direcSecundario.isDirectory()){
                    continue;
                }
                String[] rfc = direcSecundario.list();

                for (int k = ConstantesDyCNumerico.VALOR_0; k < rfc.length; k++) {
                    File direcTercero = new File(direcSecundario + ConstantesDyCURL.DIAGONAL + rfc[k]);
                    if(!direcTercero.isDirectory()){
                        continue;
                    }
                    String[] numcontrol = direcTercero.list();

                    for (int l = ConstantesDyCNumerico.VALOR_0; l < numcontrol.length; l++) {
                        File direcCuarto = new File(direcTercero + ConstantesDyCURL.DIAGONAL + numcontrol[l]);
                        if(!direcCuarto.isDirectory()){
                            continue;
                        }
                        String[] gestiondoc = direcCuarto.list();

                        //log.info("***********************************************************************");
                        log.info("Num. Control --> " + numcontrol[l]);
                        
                        for (int m = ConstantesDyCNumerico.VALOR_0; m < gestiondoc.length; m++) {
                            File direcQuinto = new File(direcCuarto + ConstantesDyCURL.DIAGONAL + gestiondoc[m]);
                            String[] contenido = direcQuinto.list();

                            log.info(direcQuinto.toString());
                            
                            eliminarDos(gestiondoc[m], contenido, numcontrol[l], direcQuinto, direcCuarto,
                                        direcTercero, direcSecundario);
                        }
                    }
                }
            }
        } else {
            log.info("No hay ficheros en el directorio especificado");
        }
    }

    private void eliminarDos(String gestiondoc, String[] contenido, String numcontrol, File direcQuinto,
                             File direcCuarto, File direcTercero, File direcSecundario) {
        if (gestiondoc.equals(ConstantesDyCURL.GESTIONDOC)) {
            if (contenido.length != ConstantesDyCNumerico.VALOR_0) {
                /**---------------ELIMINAR CONTENIDO DE GESTIONDOC --------------------**/
                eliminarContenidoGestionDoc(contenido, numcontrol, direcQuinto);
                eliminarDirectorios(direcCuarto);
                eliminarDirectorios(direcTercero);
                eliminarDirectorios(direcSecundario);
            } else {
                log.info("No existen documentos en esa carpeta");
                eliminarDirectorios(direcQuinto);
                eliminarDirectorios(direcCuarto);
                eliminarDirectorios(direcTercero);
                eliminarDirectorios(direcSecundario);

            }
        } else if (gestiondoc.equals(ConstantesDyCURL.ANEXOS)) {
            if (contenido.length != ConstantesDyCNumerico.VALOR_0) {
                /**---------------ELIMINAR CONTENIDO DE ANEXO --------------------**/
                eliminarContenidoAnexo(contenido, numcontrol, direcQuinto);
                eliminarDirectorios(direcCuarto);
                eliminarDirectorios(direcTercero);
                eliminarDirectorios(direcSecundario);
            } else {
                log.info("No existen anexos en esa carpeta");
                eliminarDirectorios(direcQuinto);
                eliminarDirectorios(direcCuarto);
                eliminarDirectorios(direcTercero);
                eliminarDirectorios(direcSecundario);
            }
        } else if (gestiondoc.equals(ConstantesDyCURL.PAPELTRABAJO)) {
            if (contenido.length != ConstantesDyCNumerico.VALOR_0) {
                /**---------------- ELIMINAR CONTENIDO PAPELTRABAJO --------------**/
                eliminarContenidoPapelTrabajo(contenido, numcontrol, direcQuinto);
                eliminarDirectorios(direcCuarto);
                eliminarDirectorios(direcTercero);
                eliminarDirectorios(direcSecundario);
            } else {
                log.info("No existen papeles de trabajo en esa carpeta");
                eliminarDirectorios(direcQuinto);
                eliminarDirectorios(direcCuarto);
                eliminarDirectorios(direcTercero);
                eliminarDirectorios(direcSecundario);
            }
        }

        eliminarTres(gestiondoc, contenido, numcontrol, direcQuinto, direcCuarto, direcTercero, direcSecundario);
    }

    private void eliminarTres(String gestiondoc, String[] contenido, String numcontrol, File direcQuinto,
                              File direcCuarto, File direcTercero, File direcSecundario) {
        if (gestiondoc.equals(ConstantesDyCURL.ARCHIVOS)) {
            if (contenido.length != ConstantesDyCNumerico.VALOR_0) {
                /**---------------- ELIMINAR CONTENIDO ARCHIVOS --------------**/
                eliminarContenidoArchivos(contenido, numcontrol, direcQuinto);
                eliminarDirectorios(direcCuarto);
                eliminarDirectorios(direcTercero);
                eliminarDirectorios(direcSecundario);
            } else {
                log.info("No existen archivos en esa carpeta");
                eliminarDirectorios(direcQuinto);
                eliminarDirectorios(direcCuarto);
                eliminarDirectorios(direcTercero);
                eliminarDirectorios(direcSecundario);
            }
        } else if (gestiondoc.equals(ConstantesDyCURL.INFOADICIONAL)) {
            /**---------------- ELIMINAR CONTENIDO INFOADICIONAL --------------**/
            eliminarContenidoInfoAdicional(direcCuarto, direcQuinto, direcTercero, direcSecundario, numcontrol,
                                           gestiondoc);
        }
    }

    private void eliminarContenidoGestionDoc(String[] contenido, String numcontrol, File direcQuinto) {
        Integer estatusDoc = ConstantesDyCNumerico.VALOR_0;
        for (int j = ConstantesDyCNumerico.VALOR_0; j < contenido.length; j++) {
            String documento = contenido[j];
            log.info("Documento --> " + documento);
            estatusDoc = dyctDocumentoDAO.buscarDocumento(documento, numcontrol);
            if (estatusDoc == ConstantesDyCNumerico.VALOR_0) {
                File eliminaDoc = new File(direcQuinto.toString() + ConstantesDyCURL.DIAGONAL + documento);
                log.info("Se elimina el documento de " + direcQuinto.toString() + documento);
                if (eliminaDoc.delete()) {
                    log.info(SE_ELIMINO);
                }

                eliminarDirectorios(direcQuinto);
            }
        }
    }

    private void eliminarContenidoAnexo(String[] contenido, String numcontrol, File direcQuinto) {
        Integer estatusDoc = ConstantesDyCNumerico.VALOR_0;
        for (int j = ConstantesDyCNumerico.VALOR_0; j < contenido.length; j++) {
            String anexos = contenido[j];
            log.info("Anexos --> " + anexos);
            estatusDoc = dyctAnexoDAO.buscaAnexos(numcontrol, anexos);
            if (estatusDoc == ConstantesDyCNumerico.VALOR_0) {
                File eliminaAnexo = new File(direcQuinto.toString() + ConstantesDyCURL.DIAGONAL + anexos);
                log.info("Se elimina el anexo de " + direcQuinto.toString() + anexos);
                if (eliminaAnexo.delete()) {
                    log.info(SE_ELIMINO);
                }
                eliminarDirectorios(direcQuinto);
            }
        }
    }

    private void eliminarContenidoPapelTrabajo(String[] contenido, String numcontrol, File direcQuinto) {
        Integer estatusDoc = ConstantesDyCNumerico.VALOR_0;
        for (int j = ConstantesDyCNumerico.VALOR_0; j < contenido.length; j++) {
            String papelTrabajo = contenido[j];
            log.info("Papel Trabajo --> " + papelTrabajo);
            estatusDoc = dyctPapelTrabajoDAO.buscaPapelTrabajo(numcontrol, papelTrabajo);
            if (estatusDoc == ConstantesDyCNumerico.VALOR_0) {
                File eliminaPapelTra = new File(direcQuinto.toString() + ConstantesDyCURL.DIAGONAL + papelTrabajo);
                log.info("Se elimina el papel de trabajo de " + direcQuinto.toString() + papelTrabajo);
                if (eliminaPapelTra.delete()) {
                    log.info(SE_ELIMINO);
                }

                eliminarDirectorios(direcQuinto);
            }
        }
    }

    private void eliminarContenidoArchivos(String[] contenido, String numcontrol, File direcQuinto) {
        Integer estatusDoc = ConstantesDyCNumerico.VALOR_0;
        for (int j = ConstantesDyCNumerico.VALOR_0; j < contenido.length; j++) {
            String archivo = contenido[j];
            log.info("Archivo --> " + archivo);
            estatusDoc = dyctArchivoDAO.buscarArchivo(numcontrol, archivo);
            if (estatusDoc == ConstantesDyCNumerico.VALOR_0) {
                File eliminaArchivo = new File(direcQuinto.toString() + ConstantesDyCURL.DIAGONAL + archivo);
                log.info("Se elimina el archivo de " + direcQuinto.toString() + archivo);
                if (eliminaArchivo.delete()) {
                    log.info(SE_ELIMINO);
                }

                eliminarDirectorios(direcQuinto);
            }
        }
    }

    private void eliminarContenidoInfoAdicional(File direcCuarto, File direcQuinto, File direcTercero,
                                                File direcSecundario, String numcontrol, String gestiondoc) {
        Integer estatusDoc = ConstantesDyCNumerico.VALOR_0;
        File infoRequerida = new File(direcCuarto.toString() + ConstantesDyCURL.DIAGONAL + gestiondoc);
        String[] infoDirectorios = infoRequerida.list();
        if (infoDirectorios.length != ConstantesDyCNumerico.VALOR_0) {
            for (int jI = ConstantesDyCNumerico.VALOR_0; jI < infoDirectorios.length; jI++) {
                String subCarpetaInfoAdicional = infoDirectorios[jI];
                File archivoSubCarpeta = new File(infoRequerida + ConstantesDyCURL.DIAGONAL + subCarpetaInfoAdicional);
                String[] archivos = archivoSubCarpeta.list();
                for (int arAdic = ConstantesDyCNumerico.VALOR_0; arAdic < archivos.length; arAdic++) {
                    String archivo = archivos[arAdic];
                    if (subCarpetaInfoAdicional.equals(ConstantesDyCURL.INFOREQUERIDA)) {
                        log.info("InfoRequerida --> " + archivo);
                        estatusDoc = dyctInfoRequeridaService.buscaArchivoInfoReq(numcontrol, archivo);
                        /**---------------ELIMINAR CONTENIDO DE INFOREQUERIDA --------------------**/
                        eliminarContenidoInfoRequerida(estatusDoc, archivoSubCarpeta, archivo, direcQuinto,
                                                       direcCuarto, direcTercero, direcSecundario);
                    } else if (subCarpetaInfoAdicional.equals(ConstantesDyCURL.OTRAINFOREQ)) {
                        log.info("OtraInfoRequerida -->" + archivo);
                        estatusDoc = dyctOtraInfoReqService.buscaArchivoOtraInfoReq(numcontrol, archivo);
                        /**---------------ELIMINAR CONTENIDO DE OTRAINFOREQUERIDA --------------------**/
                        eliminarContenidoOtraInfoRequerida(estatusDoc, archivoSubCarpeta, archivo, direcQuinto,
                                                           direcCuarto, direcTercero, direcSecundario);
                    } else if (subCarpetaInfoAdicional.equals(ConstantesDyCURL.ANEXOREQ)) {
                        log.info("AnexoRequerido --> " + archivo);
                        estatusDoc = dyctAnexoReqService.buscaArchivoEnAnexoReq(numcontrol, archivo);
                        if (estatusDoc == ConstantesDyCNumerico.VALOR_0) {
                            File eliminarArchAnexoReq =
                                new File(archivoSubCarpeta + ConstantesDyCURL.DIAGONAL + archivo);
                            log.info("Se elimininara el archivo de la ruta " + archivoSubCarpeta + archivo);
                            if (eliminarArchAnexoReq.delete()) {
                                log.info(SE_ELIMINO);
                            }

                            eliminarDirectorios(archivoSubCarpeta);
                            eliminarDirectorios(direcQuinto);
                            eliminarDirectorios(direcCuarto);
                            eliminarDirectorios(direcTercero);
                            eliminarDirectorios(direcSecundario);
                        }
                    }
                }
            }
        }
    }

    private void eliminarContenidoInfoRequerida(Integer estatusDoc, File archivoSubCarpeta, String archivo,
                                                File direcQuinto, File direcCuarto, File direcTercero,
                                                File direcSecundario) {
        if (estatusDoc == ConstantesDyCNumerico.VALOR_0) {
            File eliminarArchInfo = new File(archivoSubCarpeta + ConstantesDyCURL.DIAGONAL + archivo);
            log.info("Se elimininara el archivo de la ruta " + archivoSubCarpeta + archivo);
            if (eliminarArchInfo.delete()) {
                log.info(SE_ELIMINO);
            }
            eliminarDirectorios(archivoSubCarpeta);
            eliminarDirectorios(direcQuinto);
            eliminarDirectorios(direcCuarto);
            eliminarDirectorios(direcTercero);
            eliminarDirectorios(direcSecundario);
        }
    }

    private void eliminarContenidoOtraInfoRequerida(Integer estatusDoc, File archivoSubCarpeta, String archivo,
                                                    File direcQuinto, File direcCuarto, File direcTercero,
                                                    File direcSecundario) {
        if (estatusDoc == ConstantesDyCNumerico.VALOR_0) {
            File eliminarArchOtraInfo = new File(archivoSubCarpeta + ConstantesDyCURL.DIAGONAL + archivo);
            log.info("Se elimininara el archivo de la ruta " + archivoSubCarpeta + archivo);
            if (eliminarArchOtraInfo.delete()) {
                log.info(SE_ELIMINO);
            }
            eliminarDirectorios(archivoSubCarpeta);
            eliminarDirectorios(direcQuinto);
            eliminarDirectorios(direcCuarto);
            eliminarDirectorios(direcTercero);
            eliminarDirectorios(direcSecundario);
        }
    }

    @Override
    public void moverBloque() {
        List<ProcesoDocumentoBean> listaDocumento = mapeoDeConsulta();

        for (Iterator iterador = listaDocumento.iterator(); iterador.hasNext(); ) {
            ProcesoDocumentoBean procesoDoc = (ProcesoDocumentoBean)iterador.next();

            File destino = new File(ConstantesDyCURL.URL_HISTORICO.toString());
            Date dia = new Date();
            String fecha = new SimpleDateFormat(ConstantesDyC.YYYYMM).format(dia);
            File destinoFecha = new File(destino + ConstantesDyCURL.DIAGONAL + fecha);
            System.out.println("destino:: " + destino.getName() + " destinoFecha:: " + destinoFecha.getName());
            if (!destino.exists()) {
                if (destino.mkdir()) {
                    Utilerias.cambiarPermisosDirectorio(destino.getAbsolutePath());
                    if (destinoFecha.mkdir()) {
                        Utilerias.cambiarPermisosDirectorio(destinoFecha.getAbsolutePath());
                        log.info("Se creo correctamente");
                    }
                }
            } else {
                if (destinoFecha.mkdir()) {
                    Utilerias.cambiarPermisosDirectorio(destinoFecha.getAbsolutePath());
                    log.info("Se creo correctamente");
                }
            }
            continuidadMoverBloque(procesoDoc, destinoFecha);
        }
    }

    private void continuidadMoverBloque(ProcesoDocumentoBean procesoDoc, File destinoFecha) {

        File directorioAdmin = new File(ConstantesDyCURL.DOCUMENTOS_ADJUNTOS + procesoDoc.getAdmin());
        File directorioRfc = new File(directorioAdmin + ConstantesDyCURL.DIAGONAL + procesoDoc.getRfc());
        File directorioNumControl =
            new File(directorioRfc + ConstantesDyCURL.DIAGONAL + procesoDoc.getNumControl() + ConstantesDyCURL.DIAGONAL);

        File destinoAdm = new File(destinoFecha + ConstantesDyCURL.DIAGONAL + procesoDoc.getAdmin());
        File destinoRfc = new File(destinoAdm + ConstantesDyCURL.DIAGONAL + procesoDoc.getRfc());
        File destinoNumControl =
            new File(destinoRfc + ConstantesDyCURL.DIAGONAL + procesoDoc.getNumControl() + ConstantesDyCURL.DIAGONAL);

        /****Proceso carpeta****/
        if (!destinoAdm.exists()) {
            if (destinoAdm.mkdir()) {
                Utilerias.cambiarPermisosDirectorio(destinoAdm.getAbsolutePath());
                log.info("Lista carpeta admin");
            }
            if (destinoRfc.mkdir()) {
                Utilerias.cambiarPermisosDirectorio(destinoRfc.getAbsolutePath());
                log.info("Lista carpeta rfc");
            }
            if (directorioNumControl.renameTo(new File(destinoRfc + ConstantesDyCURL.DIAGONAL +
                                                       directorioNumControl.getName()))) {
                log.info(directorioNumControl);
                log.info("Se movio correctamente el paquete numero control");
                eliminarDirectorios(directorioRfc);
                eliminarDirectorios(directorioAdmin);
                log.info("Los archivos se encuentran en la nueva ruta que es --> " + destinoNumControl);
                String[] subcarpetas = destinoNumControl.list();
                for (int i = ConstantesDyCNumerico.VALOR_0; i < subcarpetas.length; i++) {
                    actualizarCarpetas(subcarpetas[i], procesoDoc.getNumControl(), destinoNumControl);
                    log.info("Se actualizara " + subcarpetas[i]);
                }
            }
        } else {
            continuidadFinalMoverBloque(destinoRfc, directorioNumControl, directorioRfc, directorioAdmin,
                                        destinoNumControl, procesoDoc);
        }
        /****FIN Proceso carpeta****/
    }

    private void continuidadFinalMoverBloque(File destinoRfc, File directorioNumControl, File directorioRfc,
                                             File directorioAdmin, File destinoNumControl,
                                             ProcesoDocumentoBean procesoDoc) {
        if (!destinoRfc.exists()) {
            if (destinoRfc.mkdir()) {
                Utilerias.cambiarPermisosDirectorio(destinoRfc.getAbsolutePath());
                log.info("Se creo correcto RFC");
            }
            if (directorioNumControl.renameTo(new File(destinoRfc + ConstantesDyCURL.DIAGONAL +
                                                       directorioNumControl.getName()))) {
                log.info(directorioNumControl);
                log.info("Se movio correctamente el paquete numero control");
                eliminarDirectorios(directorioRfc);
                eliminarDirectorios(directorioAdmin);
                log.info("Los archivos se encuentran en la nueva ruta que es --> " + destinoNumControl);
                String[] subcarpetas = destinoNumControl.list();
                for (int i = ConstantesDyCNumerico.VALOR_0; i < subcarpetas.length; i++) {
                    actualizarCarpetas(subcarpetas[i], procesoDoc.getNumControl(), destinoNumControl);
                    log.info("Se actualizara " + subcarpetas[i]);
                }
            }
        } else {
            if (directorioNumControl.renameTo(new File(destinoRfc + ConstantesDyCURL.DIAGONAL +
                                                       directorioNumControl.getName()))) {
                log.info(directorioNumControl);
                log.info("Se movio correctamente el paquete numero control");
                eliminarDirectorios(directorioRfc);
                eliminarDirectorios(directorioAdmin);
                log.info("Los archivos se encuentran en la nueva ruta que es --> " + destinoNumControl);
                String[] subcarpetas = destinoNumControl.list();
                for (int i = ConstantesDyCNumerico.VALOR_0; i < subcarpetas.length; i++) {
                    actualizarCarpetas(subcarpetas[i], procesoDoc.getNumControl(), destinoNumControl);
                    log.info("Se actualizara " + subcarpetas[i]);
                }
            }
        }
    }

    public void actualizarCarpetas(String subcarpetas, String numControl, File destinoNumControl) {
        String nuevaUrl = null;
        if (subcarpetas.equals(ConstantesDyCURL.GESTIONDOC)) {
            nuevaUrl = destinoNumControl + ConstantesDyCURL.DIAGONAL + subcarpetas + ConstantesDyCURL.DIAGONAL;
            dyctDocumentoDAO.actualizarUrl(numControl, nuevaUrl);
            log.info("Nueva URL gestionDoc --> " + nuevaUrl);
        } else if (subcarpetas.equals(ConstantesDyCURL.ARCHIVOS)) {
            nuevaUrl = destinoNumControl + ConstantesDyCURL.DIAGONAL + subcarpetas + ConstantesDyCURL.DIAGONAL;
            dyctArchivoDAO.actualizarUrl(numControl, nuevaUrl);
            log.info("Nueva URL archivos --> " + nuevaUrl);
        } else if (subcarpetas.equals(ConstantesDyCURL.ANEXOS)) {
            nuevaUrl = destinoNumControl + ConstantesDyCURL.DIAGONAL + subcarpetas + ConstantesDyCURL.DIAGONAL;
            dyctAnexoDAO.actualizarUrl(numControl, nuevaUrl);
            log.info("Nueva URL anexos --> " + nuevaUrl);
        } else if (subcarpetas.equals(ConstantesDyCURL.PAPELTRABAJO)) {
            nuevaUrl = destinoNumControl + ConstantesDyCURL.DIAGONAL + subcarpetas;
            dyctPapelTrabajoDAO.actualizarUrl(numControl, nuevaUrl);
            log.info("Nueva URL papelTrabajo --> " + nuevaUrl);
        } else if (subcarpetas.equals(ConstantesDyCURL.INFOADICIONAL)) {
            nuevaUrl = destinoNumControl + ConstantesDyCURL.DIAGONAL + subcarpetas;
            File subDirectorios = new File(nuevaUrl);
            String[] subDirec = subDirectorios.list();
            for (int j = ConstantesDyCNumerico.VALOR_0; j < subDirec.length; j++) {
                if (subDirec[j].equals(ConstantesDyCURL.INFOREQUERIDA)) {
                    String infoReqUrl = nuevaUrl + ConstantesDyCURL.DIAGONAL + subDirec[j] + ConstantesDyCURL.DIAGONAL;
                    dyctInfoRequeridaService.actualizarUrl(numControl, infoReqUrl);
                    log.info("Nueva URL infoRequerida --> " + infoReqUrl);
                } else if (subDirec[j].equals(ConstantesDyCURL.OTRAINFOREQ)) {
                    String otraInfoReq =
                        nuevaUrl + ConstantesDyCURL.DIAGONAL + subDirec[j] + ConstantesDyCURL.DIAGONAL;
                    dyctOtraInfoReqService.actualizarUrl(numControl, otraInfoReq);
                    log.info("Nueva URL otraInfoRequerida --> " + otraInfoReq);
                } else if (subDirec[j].equals(ConstantesDyCURL.ANEXOREQ)) {
                    String anexoReq = nuevaUrl + ConstantesDyCURL.DIAGONAL + subDirec[j] + ConstantesDyCURL.DIAGONAL;
                    dyctAnexoReqService.actualizarUrl(numControl, anexoReq);
                    log.info("Nueva URL anexoRequerida --> " + anexoReq);
                }
            }
        }
    }

    private List<ProcesoDocumentoBean> mapeoDeConsulta() {
        List<ProcesoDocumentoBean> listaProceso = new ArrayList<ProcesoDocumentoBean>();
        List<DycpCompensacionDTO> listaDocumento = dyctDocumentoDAO.buscarDocumentoXEstadoCompensacion();
        List<DycpServicioDTO> listaDocumenSol = dyctDocumentoDAO.buscarDocumentoXEstadoSolicitudes();

        for (Iterator iterador = listaDocumento.iterator(); iterador.hasNext(); ) {
            DycpCompensacionDTO compensacion = (DycpCompensacionDTO)iterador.next();
            ProcesoDocumentoBean procesoDoc = new ProcesoDocumentoBean();

            procesoDoc.setRfc(compensacion.getDycpServicioDTO().getRfcContribuyente());
            procesoDoc.setNumControl(compensacion.getNumControl());
            procesoDoc.setAdmin(compensacion.getDycpServicioDTO().getClaveAdm());

            listaProceso.add(procesoDoc);
        }

        for (Iterator iterador = listaDocumenSol.iterator(); iterador.hasNext(); ) {
            DycpServicioDTO solicitud = (DycpServicioDTO)iterador.next();
            ProcesoDocumentoBean procesoDoc = new ProcesoDocumentoBean();

            procesoDoc.setRfc(solicitud.getRfcContribuyente());
            procesoDoc.setNumControl(solicitud.getNumControl());
            procesoDoc.setAdmin(solicitud.getClaveAdm());

            listaProceso.add(procesoDoc);
        }
        return listaProceso;
    }

    @Override
    public void eliminarDirectorios(File directorios) {
        String[] registros = directorios.list();
        if (registros.length == ConstantesDyCNumerico.VALOR_0) {
            if (directorios.delete()) {
                log.info(SE_ELIMINO);
            }
        }
    }

    @Override
    public void eliminarArchivosTemp() {
        List<DyctSolicitudTempDTO> archivosTemp = dyctSolicitudTempDAO.listaSolicitudesTemp();

        if (archivosTemp.size() != ConstantesDyCNumerico.VALOR_0) {
            for (Iterator iterador = archivosTemp.iterator(); iterador.hasNext(); ) {
                DyctSolicitudTempDTO temporales = (DyctSolicitudTempDTO)iterador.next();

                String[] correcto = temporales.getClabeBancaria().split("/");
                String temporal = correcto[correcto.length - 2];

                File directorio =
                    new File(ConstantesDyCURL.URL_DOCUMENTOS + "/" + temporales.getClaveAdm() + "/" + temporales.getRfcContribuyente() +
                             "/" + temporal + "/" + correcto[correcto.length - 1]);
                if (directorio.exists()) {
                    String[] listaA = directorio.list();

                    for (int i = ConstantesDyCNumerico.VALOR_0; i < listaA.length; i++) {
                        log.info("El archivo es " + listaA[i]);
                        File archivo =
                            new File(ConstantesDyCURL.URL_DOCUMENTOS + "/" + temporales.getClaveAdm() + "/" +
                                     temporales.getRfcContribuyente() + "/" + temporal + "/" +
                                     correcto[correcto.length - 1] + ConstantesDyCURL.DIAGONAL + listaA[i]);
                        if (archivo.delete()) {
                            log.info(SE_ELIMINO);
                        }
                    }

                    log.info("Se eliminara el diretorio " + directorio);
                    eliminarDirectorios(directorio);
                } else {
                    log.info("No existe directorio en el Fyle System pero si en Base de Datos");
                }
            }
        } else {
            log.info("No existen archivos temporales");
        }    
    }

    @Override
    public List<ProcesoDocumentoBean> pruebaLista() {
        return mapeoDeConsulta();
    }

    @Override
    public void moverTesofe() {
        log.info(ConstantesDyCURL.URL_GENERADOS_TESOFE);
        log.info(ConstantesDyCURL.URL_HIST_GENERADOS_TESOFE);
        
        File pathTesofe = new File(ConstantesDyCURL.URL_GENERADOS_TESOFE);
        if(pathTesofe.exists()) {
            
            File pathHist = new File(ConstantesDyCURL.URL_HIST_GENERADOS_TESOFE);
            if(!pathHist.exists()){
                pathHist.mkdirs();
                Utilerias.cambiarPermisosDirectorio(pathHist.getAbsolutePath());
            }
            
            for(File f: pathTesofe.listFiles()){
                log.info(f.getName());
                f.renameTo(new File(pathHist, f.getName()));
            }            
            
        }
    }
}
