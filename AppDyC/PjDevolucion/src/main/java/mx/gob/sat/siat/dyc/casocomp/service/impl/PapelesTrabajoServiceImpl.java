package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import mx.gob.sat.siat.dyc.casocomp.service.PapelesTrabajoService;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridPapelesTrabajoBean;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.util.DyctPapelTrabajoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctPapelTrabajoDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("servicePapelesTrabajo")
public class PapelesTrabajoServiceImpl implements PapelesTrabajoService {
    private Logger log = Logger.getLogger(PapelesTrabajoServiceImpl.class);

    @Autowired
    private DyctPapelTrabajoDAO daoPapelTrabajo;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Override
    public Map<String, Object> obtenerInfoIniPapelesTrabajo(String numControl) {
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl(numControl);
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("filasPapelesTrabajo", obtenerFilasGrid(servicio));
        return respuesta;
    }

    private List<FilaGridPapelesTrabajoBean> obtenerFilasGrid(DycpServicioDTO servicio) {
        List<DyctPapelTrabajoDTO> papelesTrabajo = daoPapelTrabajo.selecXServicioFechabajanula(servicio);
        List<FilaGridPapelesTrabajoBean> filasPapelesTrabajo = new ArrayList<FilaGridPapelesTrabajoBean>();
        for (DyctPapelTrabajoDTO papelTrabajo : papelesTrabajo) {
            FilaGridPapelesTrabajoBean fila = new FilaGridPapelesTrabajoBean();
            fila.setIdPapelTrabajo(papelTrabajo.getIdPapelTrabajo());
            fila.setNombreArchivo(papelTrabajo.getNombreArchivo());
            fila.setDescripcion(papelTrabajo.getDescripcion());
            filasPapelesTrabajo.add(fila);
        }
        return filasPapelesTrabajo;
    }

    @Override
    public Boolean eliminarPapelTrabajo(Integer idPapelTrabajo) {
        log.info("INICIO eliminarPapelTrabajo");
        log.info("idPapelTrabajo a eliminar->" + idPapelTrabajo + "<-");
        DyctPapelTrabajoDTO papelTrabajo = new DyctPapelTrabajoDTO();
        papelTrabajo.setFechaBaja(new java.util.Date());
        papelTrabajo.setIdPapelTrabajo(idPapelTrabajo);
        daoPapelTrabajo.actualizarFechaBaja(papelTrabajo);
        return Boolean.TRUE;
    }

    @Override
    public Map<String, Object> insertarPapelTrabajo(Map<String, Object> params) {
        log.info("INICIO insertarPapelTrabajo");
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl((String)params.get("numControl"));
        DyctExpedienteDTO expediente = new DyctExpedienteDTO();
        expediente.setServicioDTO(servicio);

        DyctPapelTrabajoDTO papelTrabajo = new DyctPapelTrabajoDTO();

        papelTrabajo.setNombreArchivo((String)params.get("nombreArchivo"));
        papelTrabajo.setDescripcion((String)params.get("descripcion"));
        papelTrabajo.setUrl((String)params.get("url"));
        papelTrabajo.setFechaRegistro(new java.util.Date());
        papelTrabajo.setDyctExpedienteDTO(expediente);
        papelTrabajo.setFechaBaja(null);
        papelTrabajo.setIdPapelTrabajo(daoPapelTrabajo.insertar(papelTrabajo));
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("idPapelTrabajo", papelTrabajo.getIdPapelTrabajo());
        return respuesta;
    }

    @Override
    public Map<String, Object> subirPapelTrabajo(Map<String, Object> params) {
        log.debug("INICIO subirPapelTrabajo");

        String numControl = (String)params.get("numControl");

        InputStream secuenciaEntrada = (InputStream)params.get("secuenciaEntrada");
        String nombreArchivo = (String)params.get("nombreArchivo");
        String tipoArchivo = (String)params.get("tipoArchivo");

        Boolean success = Boolean.TRUE;
        String mensaje = "";

        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl(numControl);
        DyctExpedienteDTO expediente = new DyctExpedienteDTO();
        expediente.setServicioDTO(servicio);

        //validar
        Boolean esReemplazo = (Boolean)params.get("reemplazarArchivo");
        Map<String, Object> resValidacion =
            validarArchivo(secuenciaEntrada, nombreArchivo, tipoArchivo, expediente, esReemplazo);

        if ((Boolean)resValidacion.get("esValido")) {
            if (esReemplazo) {
                log.debug("dar de baja archivos a reemplazar");
                List<DyctPapelTrabajoDTO> papeles = daoPapelTrabajo.selecXExpediente(expediente);
                log.debug("papeles ->" + papeles + "<-");
                List<DyctPapelTrabajoDTO> papelesBaja = new ArrayList<DyctPapelTrabajoDTO>();
                for (DyctPapelTrabajoDTO papel : papeles) {
                    log.debug("fechaBaja ->" + papel.getFechaBaja() + "<-");
                    log.debug("nombreArchivo ->" + papel.getNombreArchivo() + "<-");
                    if (papel.getFechaBaja() == null && nombreArchivo.equals(papel.getNombreArchivo())) {
                        papelesBaja.add(papel);
                    }
                }
                for (DyctPapelTrabajoDTO papelBaja : papelesBaja) {
                    papelBaja.setFechaBaja(new Date());
                    daoPapelTrabajo.actualizarFechaBaja(papelBaja);
                }
            }

            guardarArchivo(expediente, secuenciaEntrada, nombreArchivo, (String)params.get("descripcion"));
            mensaje = "El archivo '" + nombreArchivo + "' se guardó exitosamente";
        } else {
            success = Boolean.FALSE;
            mensaje = (String)resValidacion.get("mensajeValidacion");

        }

        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("success", success);
        respuesta.put("mensaje", mensaje);
        respuesta.put("yaExiste", "yaExiste".equals(mensaje));
        respuesta.put("filasPapelesTrabajo", obtenerFilasGrid(servicio));
        return respuesta;
    }

    private Boolean yaExisteArchivo(DyctExpedienteDTO expediente, String nombreArchivo) {
        List<DyctPapelTrabajoDTO> papeles = daoPapelTrabajo.selecXExpediente(expediente);
        for (DyctPapelTrabajoDTO papelDTO : papeles) {
            if (papelDTO.getFechaBaja() == null && nombreArchivo.equals(papelDTO.getNombreArchivo())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private void guardarArchivo(DyctExpedienteDTO expediente, InputStream secuenciaEntrada, String nombreArchivo,
                                String descripcion) {
        DycpCompensacionDTO compensacion;
        try {
            compensacion = daoCompensacion.encontrar(expediente.getServicioDTO().getNumControl());
            String claveAdm = String.format("%02d", compensacion.getDycpServicioDTO().getClaveAdm());
            // guardar Archivo en file System
            StringBuilder ruta = new StringBuilder();
            ruta.append("/siat/dyc/documentos/");
            ruta.append(claveAdm + "/");
            /**ruta.append(compensacion.getDycpServicioDTO().getRfcContribuyente() + "/");*/
            ruta.append(Utilerias.corregirRFC(compensacion.getDycpServicioDTO().getRfcContribuyente()) + "/");
            ruta.append(compensacion.getDycpServicioDTO().getNumControl() + "/");
            ruta.append("papelesTrabajo/");
            ArchivoCargaDescarga utileriaArchivos = new ArchivoCargaDescarga();

            utileriaArchivos.escribirArchivo(nombreArchivo, secuenciaEntrada, ruta.toString(), ConstantesDyCNumerico.VALOR_4096);

            // guardar datos en BADA
            DyctPapelTrabajoDTO papelTrabajo = new DyctPapelTrabajoDTO();
            papelTrabajo.setNombreArchivo(nombreArchivo);
            papelTrabajo.setDescripcion(descripcion);
            papelTrabajo.setUrl(ruta.toString());
            papelTrabajo.setFechaRegistro(new java.util.Date());
            papelTrabajo.setDyctExpedienteDTO(expediente);
            papelTrabajo.setFechaBaja(null);
            papelTrabajo.setIdPapelTrabajo(daoPapelTrabajo.insertar(papelTrabajo));
        } catch (SIATException e) {
            JSFUtils.messageGlobal(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    private Map<String, Object> validarArchivo(InputStream secuenciaEntrada, String nombreArchivo, String tipoArchivo,
                                               DyctExpedienteDTO expediente, Boolean esReemplazo) {
        log.debug("INICIO validarArchivo");

        log.info("tipoArchivo ->" + tipoArchivo + "; nombreArchivo ->" + nombreArchivo);

        String mensajeValidacion = "mensajeValidacion";
        int numBytesDisponibles;

        int tamanioMaximo = obtenerTamanioMaximo(tipoArchivo);
        log.info("tamanioMaximo ->" + tamanioMaximo);
        Boolean esValido = Boolean.TRUE;
        try {
            if (secuenciaEntrada == null) {
                mensajeValidacion = "El archivo es nulo";
                esValido = Boolean.FALSE;
            } else {
                numBytesDisponibles = secuenciaEntrada.available();
                log.info("numBytesDisponibles ->" + numBytesDisponibles);
                
                if (nombreArchivo.length()<=ConstantesDyCNumerico.VALOR_80) {
                    if (numBytesDisponibles > tamanioMaximo) {
                        log.info("El tamaño del archivo (" + numBytesDisponibles + " bytes) excede el tamaño máximo permitido(" + tamanioMaximo +" bytes); tipoArchivo ->" + tipoArchivo + "<-");
                        mensajeValidacion = "El tamaño máximo en la carga de papeles de trabajo será menor a 4MB en formato ZIP en cualquier otro caso menor a 1";
                        esValido = Boolean.FALSE;
                    } else {
                        if (!esReemplazo && yaExisteArchivo(expediente, nombreArchivo)) {
                            mensajeValidacion = "yaExiste";
                            esValido = Boolean.FALSE;
                        }
                    }
                } else {
                    log.error("El tamaño nombre del archivo, excede el tamaño máximo permitido(" + nombreArchivo.length() +"); tipoArchivo ->" + tipoArchivo + "<-");
                    mensajeValidacion = "El nombre del archivo no debe tener mas de 80 caracteres.";
                    esValido = Boolean.FALSE;
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        Map<String, Object> respuesta = new HashMap<String, Object>();
        log.info("esValido ->" + esValido + "<-");
        log.info("mensajeValidacion ->" + mensajeValidacion + "<-");
        respuesta.put("esValido", esValido);
        respuesta.put("mensajeValidacion", mensajeValidacion);
        return respuesta;
    }

    private int obtenerTamanioMaximo(String tipoArchivo)
    {
        String[] extensionesZip = {"application/zip", 
                                   "application/x-zip", 
                                   "application/x-zip-compressed", 
                                   "application/octet-stream", 
                                   "application/x-compress", 
                                   "application/x-compressed", 
                                   "multipart/x-zip"};
        
        if(Arrays.asList(extensionesZip).contains(tipoArchivo) || tipoArchivo.contains("zip")){
            return ConstantesDyCNumerico.VALOR_4194304;
        }
        else {
            return ConstantesDyCNumerico.VALOR_1048576;
        }
    }

    @Override
    public Map<String, Object> descargarPapelTrabajo(Integer idPapelTrabajo) {
        log.info("INICIO descargarPapelTrabajo");
        Map<String, Object> respuesta = new HashMap<String, Object>();
        DyctPapelTrabajoDTO papelTrabajo = daoPapelTrabajo.encontrar(idPapelTrabajo);

        StringBuilder ruta = new StringBuilder();
        ruta.append(papelTrabajo.getUrl());
        ruta.append(papelTrabajo.getNombreArchivo());
        String rutaS = ruta.toString();
        log.info("ruta ->" + rutaS);
        File archivoPapelTrabajo = new File(rutaS);
        log.info("archivoPapelTrabajo ->" + archivoPapelTrabajo);
        respuesta.put("archivoPapelTrabajo", archivoPapelTrabajo);
        return respuesta;
    }
}
