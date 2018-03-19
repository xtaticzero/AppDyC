package mx.gob.sat.siat.dyc.dao.util.impl;

import mx.gob.sat.siat.dyc.dao.util.DyccFileSystemDAO;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DyccFileSystemMapper;
import mx.gob.sat.siat.dyc.domain.DyccFileSystemDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dyccFileSystemDAO")
public class DyccFileSystemDAOImpl implements DyccFileSystemDAO {
    public DyccFileSystemDAOImpl() {
        super();
    }
    
    private static final Logger LOGGER = Logger.getLogger(DyccFileSystemDAOImpl.class);
    private static final String OBTENER_FILESYSTEM_ACTIVO = "SELECT * FROM DYCC_FILESYSTEM WHERE ACTIVO=1";
    private static final String INSERTAR = "INSERT INTO DYCC_FILESYSTEM(IDFILESYSTEM, PUNTODEMONTAJE, ESPACIOENDISCO,ACTIVO) VALUES (DYCQ_IDFILESYSTEM.NEXTVAL, ?, 0, 1)";
    private static final String ACTIVAR_DESACTIVAR_FS="update DYCC_FILESYSTEM set ACTIVO = ? where IDFILESYSTEM = ?";
    
    @Autowired 
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Obtiene los datos del filesystem que se est&aacute; utilizando actualmente.
     *
     * @return
     * @throws SIATException
     */
    @Override
    public DyccFileSystemDTO obtenerFileSystemActivo() throws SIATException {
        DyccFileSystemDTO objeto = null;
        try {
            objeto = jdbcTemplateDYC.queryForObject(OBTENER_FILESYSTEM_ACTIVO, new DyccFileSystemMapper());
            
        } catch(Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         OBTENER_FILESYSTEM_ACTIVO+". Causa: "+e);
            throw new SIATException (e);
        }
        return objeto;
    }

    /**
     * Actualiza el espacio en disco del fileSystem que se est&aacute; utilizando.
     *
     * @param espacioOcupadoEnDisco Cantidad de espacio disponible en disco.
     * @throws SIATException
     */
    @Override
    public void actualizarEspacioOcupadoEnDisco(double espacioOcupadoEnDisco) throws SIATException {
    }

    /**
     * Actualiza el fileSystem que se encuentra activo dentro del sistema.
     *
     * @param idFileSystem ID del fileSystem que se esta utilizando.
     * @param activoInactivo Bandera que indica si se activar&aacute; (1) o inactivar&aacute; (0) un fileSystem
     * @throws SIATException
     */
    @Override
    public void activarDesactivarFileSystem(int idFileSystem, int activoInactivo) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTIVAR_DESACTIVAR_FS, new Object []{activoInactivo , idFileSystem});
            
        } catch(Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         INSERTAR+". Causa: "+e);
            throw new SIATException (e);
        }
    }


    /**
     * Agrega un nuevo FileSystem a base de datos 
     *
     * @param puntoDeMontaje Es la ruta donde estar&aacute; montado el fileSystem a agregar.
     * @throws SIATException
     */
    @Override
    public void insertar(String puntoDeMontaje) throws SIATException {
        try {
            jdbcTemplateDYC.update(INSERTAR, new Object []{puntoDeMontaje});
            
        } catch(Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         INSERTAR+". Causa: "+e);
            throw new SIATException (e);
        }
    }
}
