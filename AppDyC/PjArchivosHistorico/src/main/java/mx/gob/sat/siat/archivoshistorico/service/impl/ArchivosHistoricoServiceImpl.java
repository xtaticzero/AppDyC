package mx.gob.sat.siat.archivoshistorico.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import mx.gob.sat.siat.archivoshistorico.dto.ArchivoHistoricoDto;
import mx.gob.sat.siat.archivoshistorico.service.AccesoADatosService;
import mx.gob.sat.siat.archivoshistorico.service.ArchivosHistoricoService;
import mx.gob.sat.siat.archivoshistorico.service.DiscoDuroService;
import mx.gob.sat.siat.archivoshistorico.utils.Constantes;
import mx.gob.sat.siat.dyc.dao.parametros.DyccParametrosAppDAO;
import mx.gob.sat.siat.dyc.domain.DyccFileSystemDTO;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "archivosHistoricoService")
public class ArchivosHistoricoServiceImpl implements ArchivosHistoricoService {

    public ArchivosHistoricoServiceImpl() {
        super();
    }

    @Autowired
    private AccesoADatosService accesoADatosService;

    @Autowired
    private DiscoDuroService discoDuroService;
    @Autowired
    private DyccParametrosAppDAO dyccParametrosAppDAO;
    
    private static final Logger LOGGER = Logger.getLogger(ArchivosHistoricoServiceImpl.class);
    private static final String NUMCONTROL="NUMCONTROL";
    private static final String IDFSSEGUIMIENTO="IDFSSEGUIMIENTO";
    private static final String MONTAJE_NYV="/mat/notificacion";
    private static final int TRES=3;
    private static final int DOS=2;
    private static final int CUATRO=4;
    private static final int TREINTA_SEIS=36;
    private static final int TREINTA_SIETE=37;
    /**
     * Mueve de un filesystem a otro, todos los tr&aacute;mites que estan como
     * desistidos, negados, o pagados.
     *
     */
    @Override
    public void acutalizarRegistrosDentroDelFileSystem() {
         FileInputStream   archivo =null;
        try {
            LOGGER.info("-1");
             Properties propiedades = new Properties();
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            propiedades.load(archivo);
            String montajeNYV;
            if(propiedades.getProperty("rutaFileSystemNyV")!=null){
                montajeNYV=propiedades.getProperty("rutaFileSystemNyV");
            }else{
                montajeNYV=MONTAJE_NYV;
            }
            DyccFileSystemDTO discoDuro = discoDuroService.determinarFileSystemAUtilizar();
            DyccParametrosAppDTO objeto = dyccParametrosAppDAO.encontrar(TREINTA_SIETE);
            List<Map<String, Object>> listaNYV=accesoADatosService.listarNumerosDeControNYVProcesados(objeto.getValor().intValue());
            LOGGER.error("ARCHIVOS ENCONTRADOS PARA CAMBIAR NYV:" + listaNYV.size());
              List<ArchivoHistoricoDto> archivosNYV= null; 
             for (Map<String, Object> mapeoNYV : listaNYV) {
                   LOGGER.error("procesandoNYV ... numcontrol:" + mapeoNYV.get(NUMCONTROL).toString());
                    try { 
                          archivosNYV = accesoADatosService.buscarDocumentosNYVProcesados(mapeoNYV.get(NUMCONTROL).toString());
                         discoDuroService.copiarRegistrosAlNuevoFileSystem(montajeNYV, archivosNYV);
                          accesoADatosService.actualizarURLs(montajeNYV, archivosNYV,discoDuro.getIdFileSystem(), mapeoNYV.get(NUMCONTROL).toString(), TRES, "",mapeoNYV.get(IDFSSEGUIMIENTO));
                   LOGGER.error("TRAMITE EXITOSO NYV:"+mapeoNYV.get(NUMCONTROL).toString());
                    } catch (Exception e) {
                        try {
                            discoDuroService.borrarRegistrosDelNuevoFileSystem(montajeNYV, archivosNYV);
                            String mensajeError="Ocurrio un error al pasar los archivos de NYV";
                            if(e.getCause()!=null){
                                mensajeError=e.getCause().toString();
                            }else if(e.getMessage()!=null){
                                 mensajeError=e.getMessage();
                            }
                            accesoADatosService.insertarRegistroEnBitacora(discoDuro.getIdFileSystem(), mapeoNYV.get(NUMCONTROL).toString(), CUATRO, mensajeError,mapeoNYV.get(IDFSSEGUIMIENTO));
                            LOGGER.error("TRAMITE FALLIDO NYV:"+mapeoNYV.get(NUMCONTROL).toString());
                            continue; 
                            } catch (Exception ex) {
                            LOGGER.error("NYV:ERROR DENTRO DE LA EXCEPCION --NUMCONTROL:"+mapeoNYV.get(NUMCONTROL).toString(), ex);
                            continue; 
                        }
                   }
                     try {
                        discoDuroService.borrarRegistrosDelFileSystemActual(archivosNYV);
                    } catch (Exception ex) {
                        LOGGER.error("NYV:ERROR al borrar los archivos del original "+mapeoNYV.get(NUMCONTROL).toString(), ex);
                    }
             }
           } catch (FileNotFoundException e) {
            LOGGER.error("ERROR leyyendo el archivo properties" , e);
           } catch (Exception ex) {
             LOGGER.error("ERROR DENTRO DEL FLUJO   A MOVER LOS ARCHIVOS A NYV: " , ex);
        }finally{
             try {
                if (archivo != null) {
                    archivo.close();
                }
            } catch (Exception e) {
                LOGGER.error("Error al cerrar el archivo de configuracion: " + e);
            }
           }
           try {
            DyccFileSystemDTO discoDuro = discoDuroService.determinarFileSystemAUtilizar();
            List<ArchivoHistoricoDto> listaDeRegistros = null;
            LOGGER.info("0");     
            if (!Constantes.FILESYSTEM_BASE.equals(discoDuro.getPuntoDeMontaje())) {
                  DyccParametrosAppDTO objeto2 = dyccParametrosAppDAO.encontrar(TREINTA_SEIS);
                List<Map<String, Object>> listaAProcesar = accesoADatosService.listarNumerosDeControlAProcesar(objeto2.getValor().intValue());
                LOGGER.error("ARCHIVOS ENCONTRADOS PARA CAMBIAR alt:" + listaAProcesar.size());
                for (Map<String, Object> mapeo : listaAProcesar) {
                    LOGGER.error("procesando alt... numcontrol:" + mapeo.get(NUMCONTROL).toString());
                    try {
                        listaDeRegistros = accesoADatosService.listarDatosParaMoverRegistrosDeFileSystem(mapeo.get(NUMCONTROL).toString());

                        discoDuroService.copiarRegistrosAlNuevoFileSystem(discoDuro.getPuntoDeMontaje(), listaDeRegistros);
                        accesoADatosService.actualizarURLs(discoDuro.getPuntoDeMontaje(), listaDeRegistros,discoDuro.getIdFileSystem(), mapeo.get(NUMCONTROL).toString(), DOS, "",mapeo.get(IDFSSEGUIMIENTO));
                        LOGGER.error("TRAMITE EXITOSO alt:"+mapeo.get(NUMCONTROL).toString());
                    } catch (Exception e) {
                        try {
                            discoDuroService.borrarRegistrosDelNuevoFileSystem(discoDuro.getPuntoDeMontaje(), listaDeRegistros);
                            String mensajeError="Ocurrio un error al pasar los archivos alt";
                            if(e.getCause()!=null){
                                mensajeError=e.getCause().toString();
                            }else if(e.getMessage()!=null){
                                 mensajeError=e.getMessage();
                            }
                            accesoADatosService.insertarRegistroEnBitacora(discoDuro.getIdFileSystem(), mapeo.get(NUMCONTROL).toString(), 0, mensajeError,mapeo.get(IDFSSEGUIMIENTO));
                            LOGGER.error("TRAMITE FALLIDO alt:"+mapeo.get(NUMCONTROL).toString());
                            continue; 
                        } catch (Exception ex) {
                            LOGGER.error("ERROR DENTRO DE LA EXCEPCION alt --NUMCONTROL:"+mapeo.get(NUMCONTROL).toString(), ex);
                            continue; 
                        }
                    }
                    try {
                        discoDuroService.borrarRegistrosDelFileSystemActual(listaDeRegistros);
                    } catch (Exception ex) {
                        LOGGER.error("ERROR al borrar los archivos del original alt "+mapeo.get(NUMCONTROL).toString(), ex);
                    }
                }

            } else {
                LOGGER.error("EL PUNTO DE MONTAJE ES EL ORIGINAL NO HAY CAMBIOS.. ");
            }
        } catch (Exception e) {
            LOGGER.error("ERROR DENTRO DEL FLUJO   A MOVER LOS ARCHIVOS ALT: " , e);
        }
    }
}
