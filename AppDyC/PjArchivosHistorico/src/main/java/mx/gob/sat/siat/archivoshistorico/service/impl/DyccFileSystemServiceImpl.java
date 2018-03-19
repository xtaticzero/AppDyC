package mx.gob.sat.siat.archivoshistorico.service.impl;

import java.io.File;

import mx.gob.sat.siat.archivoshistorico.service.DyccFileSystemService;
import mx.gob.sat.siat.archivoshistorico.utils.Constantes;
import mx.gob.sat.siat.archivoshistorico.utils.Utils;
import mx.gob.sat.siat.dyc.dao.parametros.DyccParamentroAppDAO;
import mx.gob.sat.siat.dyc.dao.util.DyccFileSystemDAO;
import mx.gob.sat.siat.dyc.domain.DyccFileSystemDTO;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Jesús Alfredo Hernández Orozco
 */
@Service(value = "dyccFileSystemService")
public class DyccFileSystemServiceImpl implements DyccFileSystemService {
    public DyccFileSystemServiceImpl() {
        super();
    }

    private static final int ID_MAXIMO_FILESYSTEM = 34;
    private static final int LONGITUD = 2;

    @Autowired
    private DyccFileSystemDAO dyccFileSystemDAO;

    @Autowired
    private DyccParamentroAppDAO dyccParamentroAppDAO;

    /**
     * Determina que filesystem se utilizar&aacute; dentro del sistema. Primero verificar&aacute; si el actual sistema
     * de directorios tiene el espacio suficiente en disco para poder seguir escribiendo en el. De no ser as&acute;,
     * busca que si hay un seguno filesystem al cual pueda conectarse y si tiene espacio suficiente. Al momento de
     * de encontrar uno nuevo, el sistema utiliza el nuevo filesystem y lo deja como activo, deshabilitando el anterior.
     *
     * @return Retorna la ruta del punto de montaje del filesystem a utilizar.
     * @throws SIATException
     */
    @Override
    public String determinarFileSystemAUtilizar() throws SIATException {
        String puntoDeMontaje = "";

        DyccFileSystemDTO discoDuro = dyccFileSystemDAO.obtenerFileSystemActivo();
        DyccParametrosAppDTO objetoParametro = dyccParamentroAppDAO.encontrar(ID_MAXIMO_FILESYSTEM);

        if (Utils.obtenterEspacioUtilizadoEnDisco(discoDuro.getPuntoDeMontaje()).compareTo(Double.valueOf(objetoParametro.getValorStr())) >
            1) {
            buscarOtroFileSystem(discoDuro.getPuntoDeMontaje().substring(discoDuro.getPuntoDeMontaje().length() -
                                                                         ConstantesDyCNumerico.VALOR_3,
                                                                         discoDuro.getPuntoDeMontaje().length() - 1),
                                 discoDuro);
        } else {
            puntoDeMontaje = discoDuro.getPuntoDeMontaje();
        }

        return puntoDeMontaje;
    }

    /**
     * Busca si existe otro filesystem que se pueda utilizar para guardar archivos en disco duro y en cuanto encuentra
     * uno nuevo lo agrega a base de datos y deja de utilizar el anterior filesystem.
     *
     * @param numeroDeFileSystem
     * @return
     * @throws SIATException
     */
    private String buscarOtroFileSystem(String numeroDeFileSystem, DyccFileSystemDTO discoDuro) throws SIATException {

        String nuevoNumeroDeFileSystem =
            Utils.agregarCerosALaIzquierda(String.valueOf(Integer.valueOf(numeroDeFileSystem) + 1), LONGITUD);
        String puntoDeMontaje = Constantes.FILESYSTEM_BASE + nuevoNumeroDeFileSystem;

        File nuevoFileSystem = new File(puntoDeMontaje);
        if (!nuevoFileSystem.exists()) {
            puntoDeMontaje = Constantes.FILESYSTEM_BASE + numeroDeFileSystem;
        } else {
            dyccFileSystemDAO.insertar(puntoDeMontaje);
            dyccFileSystemDAO.activarDesactivarFileSystem(discoDuro.getIdFileSystem(), 0);
        }

        return puntoDeMontaje;
    }
}
