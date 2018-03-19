package mx.gob.sat.siat.archivoshistorico.service.impl;

import java.io.File;

import java.io.IOException;

import java.util.List;

import mx.gob.sat.siat.archivoshistorico.dto.ArchivoHistoricoDto;
import mx.gob.sat.siat.archivoshistorico.service.DiscoDuroService;

import mx.gob.sat.siat.archivoshistorico.utils.Constantes;
import mx.gob.sat.siat.archivoshistorico.utils.Utils;
import mx.gob.sat.siat.dyc.dao.parametros.DyccParamentroAppDAO;
import mx.gob.sat.siat.dyc.dao.util.DyccFileSystemDAO;

import mx.gob.sat.siat.dyc.domain.DyccFileSystemDTO;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.commons.io.FileUtils;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "discoDuroService")
public class DiscoDuroServiceImpl implements DiscoDuroService {

    public DiscoDuroServiceImpl() {
        super();
    }

    private static final int ID_MAXIMO_FILESYSTEM = 34;
    private static final int LONGITUD = 2;
    private static final Logger LOGGER = Logger.getLogger(DiscoDuroServiceImpl.class);
    private static final String PUNTO_MONTAJE_CERO = "00";

    @Autowired
    private DyccFileSystemDAO dyccFileSystemDAO;

    @Autowired
    private DyccParamentroAppDAO dyccParamentroAppDAO;

    /**
     * Determina que filesystem se utilizar&aacute; dentro del sistema. Primero
     * verificar&aacute; si el actual sistema de directorios tiene el espacio
     * suficiente en disco para poder seguir escribiendo en el. De no ser
     * as&iacute;, busca que si hay un seguno filesystem al cual pueda
     * conectarse y si tiene espacio suficiente. Al momento de de encontrar uno
     * nuevo, el sistema utiliza el nuevo filesystem y lo deja como activo,
     * deshabilitando el anterior.
     *
     * @return Retorna la ruta del punto de montaje del filesystem a utilizar.
     * @throws SIATException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, isolation = Isolation.DEFAULT, rollbackFor = SIATException.class)
    @Override
    public DyccFileSystemDTO determinarFileSystemAUtilizar() throws SIATException {

        String numeroDeFileSystem = null;
        DyccFileSystemDTO discoDuroActivo = dyccFileSystemDAO.obtenerFileSystemActivo();
        DyccParametrosAppDTO limiteEnDisco = dyccParamentroAppDAO.encontrar(ID_MAXIMO_FILESYSTEM);

        if (Utils.obtenterEspacioUtilizadoEnDisco(discoDuroActivo.getPuntoDeMontaje()).compareTo(Double.valueOf(limiteEnDisco.getValorStr())) == 1) {
            numeroDeFileSystem = !(Constantes.FILESYSTEM_BASE.equals(discoDuroActivo.getPuntoDeMontaje())) ? discoDuroActivo.getPuntoDeMontaje().substring(discoDuroActivo.getPuntoDeMontaje().length() - ConstantesDyCNumerico.VALOR_2, discoDuroActivo.getPuntoDeMontaje().length()) : PUNTO_MONTAJE_CERO;
            if (!buscarOtroFileSystem(numeroDeFileSystem, discoDuroActivo)) {
                return discoDuroActivo;
            }
        } else {
            return discoDuroActivo;
        }
        return dyccFileSystemDAO.obtenerFileSystemActivo();
    }

    /**
     * Busca si existe otro filesystem que se pueda utilizar para guardar
     * archivos en disco duro y en cuanto encuentra uno nuevo lo agrega a base
     * de datos y deja de utilizar el anterior filesystem.
     *
     * @param numeroDeFileSystem
     * @throws SIATException
     */
    private boolean buscarOtroFileSystem(String numeroDeFileSystem, DyccFileSystemDTO discoDuro) throws SIATException {
        boolean bandera = Boolean.FALSE;
        String nuevoNumeroDeFileSystem
                = Utils.agregarCerosALaIzquierda(String.valueOf(Integer.valueOf(numeroDeFileSystem) + 1), LONGITUD);
        String puntoDeMontaje = Constantes.FILESYSTEM_BASE + nuevoNumeroDeFileSystem;

        File nuevoFileSystem = new File(puntoDeMontaje);
        if (nuevoFileSystem.exists()) {
            dyccFileSystemDAO.insertar(puntoDeMontaje);
            dyccFileSystemDAO.activarDesactivarFileSystem(discoDuro.getIdFileSystem(), 0);
            bandera = Boolean.TRUE;
        }
        return bandera;
    }

    /**
     * Copia los registros de un fileSystem a otro.
     *
     * @param puntoDeMontajeNuevo Es el filesystem sobre el cual se va a hacer
     * el copiado
     * @param listaDeRegistros Son todos los registros a actualizar que
     * pertenecen a un n&uacute;mero de control
     * @return Verdadero si todos los registros fueron copiados
     * satisfactoriamente. Falso en caso contrario.
     */
    @Override
    public void copiarRegistrosAlNuevoFileSystem(String puntoDeMontajeNuevo,
            List<ArchivoHistoricoDto> listaDeRegistros) throws SIATException {
        File archivoNuevo = null;
        File archivoOriginal = null;
        StringBuilder rutaArchivoNuevo = null;
        StringBuilder rutaArchivoOriginal = null;

        for (ArchivoHistoricoDto objeto : listaDeRegistros) {
            rutaArchivoOriginal = new StringBuilder();
            rutaArchivoNuevo = new StringBuilder();
            archivoOriginal
                    = new File(rutaArchivoOriginal.append(objeto.getUrl()).append(Constantes.SLASH).append(objeto.getNombreArchivo()).toString());
            archivoNuevo
                    = new File(rutaArchivoNuevo.append(objeto.getUrl().replace(Utils.obtenerPuntoDeMontajeActual(objeto.getUrl()),
                            puntoDeMontajeNuevo)).append(Constantes.SLASH).append(objeto.getNombreArchivo()).toString());

            try {
                FileUtils.copyFile(archivoOriginal, archivoNuevo);
                if(archivoNuevo.exists()){
                     LOGGER.error("SE COPIO EXITOSO:"+archivoNuevo.getAbsolutePath());
                }else{
                   String msgError="NO SE COPIO CON SEGUNDA VERIFICACION:"+archivoNuevo.getAbsolutePath();
                   LOGGER.error(msgError); 
                   throw new SIATException(msgError);
                }
            } catch (IOException e) {
                String error = "Error al copiar el archivo: " + rutaArchivoOriginal.toString()
                        + " Campo1: " + objeto.getCampo1() + ". Valor1= " + objeto.getId1() + " Campo2: "
                        + objeto.getCampo1() + ". Valor2= " + objeto.getId2() + " ruta nueva: " + rutaArchivoNuevo + " ruta original:" + objeto.getUrl();
                LOGGER.error(error, e);
                throw new SIATException(error, e);
            }
            rutaArchivoNuevo = null;
            rutaArchivoOriginal = null;
        }
    }

    /**
     * En caso de fallo borra todos los archivos adjuntos asociados de un
     * n&uacute;mero de control del filesystem nuevo.
     *
     * @param puntoDeMontajeNuevo Es el filesystem sobre el cual se va a hacer
     * el copiado
     * @param listaDeRegistros Son todos los registros que se intentaron copiar
     * que pertenecen a un n&uacute;mero de control
     */
    @Override
    public void borrarRegistrosDelNuevoFileSystem(String puntoDeMontajeNuevo,
            List<ArchivoHistoricoDto> listaDeRegistros) {

        String ruta = null;
        File archivoCompleto = null;
        File archivoRuta = null;
        StringBuilder rutaArchivoCompleto = null;
        try {
            for (ArchivoHistoricoDto objeto : listaDeRegistros) {

                try {
                    rutaArchivoCompleto = new StringBuilder();
                    ruta = objeto.getUrl().replace(Utils.obtenerPuntoDeMontajeActual(objeto.getUrl()), puntoDeMontajeNuevo);
                    archivoRuta = new File(ruta);
                    archivoCompleto = new File(rutaArchivoCompleto.append(ruta).append(Constantes.SLASH).append(objeto.getNombreArchivo()).toString());

                    boolean a = archivoCompleto.delete();
                    boolean b = archivoRuta.delete();
                    LOGGER.error("RUTA BORRADA NUEVO POR FALLO: " + b + ". ARCHIVO BORRADO: " + a+ "ruta borra nuevo archivo:"+archivoCompleto.getAbsolutePath());

                } catch (Exception e) {
                    LOGGER.error("Error al borrar el archivo: url orignal:" + objeto.getUrl() + ". Causa: " + e, e);

                }
                rutaArchivoCompleto = null;
            }
        } catch (Exception e) {
            LOGGER.error("Error al borrar  ", e);

        }
    }

    /**
     * Borra todos los archivos adjuntos asociados de un n&uacute;mero de
     * control del filesystem actual.
     *
     * @param listaDeRegistros Son todos los registros que se copiaron que
     * pertenecen a un n&uacute;mero de control
     */
    @Override
    public void borrarRegistrosDelFileSystemActual(List<ArchivoHistoricoDto> listaDeRegistros) throws SIATException {

        File archivoCompleto = null;
        File archivoRuta = null;
        StringBuilder rutaArchivoCompleto = null;

        for (ArchivoHistoricoDto objeto : listaDeRegistros) {

            rutaArchivoCompleto = new StringBuilder();
            try {
                archivoRuta = new File(objeto.getUrl());
                archivoCompleto
                        = new File(rutaArchivoCompleto.append(objeto.getUrl()).append(Constantes.SLASH).append(objeto.getNombreArchivo()).toString());

                boolean a = archivoCompleto.delete();
                boolean b = archivoRuta.delete();
                LOGGER.error("RUTA BORRADA ORIGINAL: " + b + ". ARCHIVO BORRADO: " + a+" ruta archivo original:"+archivoCompleto.getAbsolutePath());

            } catch (Exception e) {
                String error = "Error al borrar el archivo: " + rutaArchivoCompleto.toString();
                LOGGER.error(error, e);

            }
            rutaArchivoCompleto = null;
        }
    }
}
