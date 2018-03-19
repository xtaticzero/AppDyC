package mx.gob.sat.siat.archivoshistorico.dao.impl;


import java.util.List;


import mx.gob.sat.siat.archivoshistorico.dao.ConsultasHistoricoDao;
import mx.gob.sat.siat.archivoshistorico.dao.impl.mapper.ArchivoHistoricoMapper;
import mx.gob.sat.siat.archivoshistorico.dao.sql.Querys;
import mx.gob.sat.siat.archivoshistorico.dto.ArchivoHistoricoDto;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "consultasHistoricoDao")
public class ConsultasHistoricoDaoImpl implements ConsultasHistoricoDao,Querys {

    public ConsultasHistoricoDaoImpl() {
        super();
    }

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOGGER = Logger.getLogger(ConsultasHistoricoDaoImpl.class);
    
    /**
     * Obtiene una relaci&oacute;n de todas las tablas en donde se encuentran guardados los archivos adjuntos a 
     * un tr&aacute;mite; al cual se adjunta la URL, nombre de archivo y campos que los alojan.
     *
     * @param query Consulta con la cual se extrae la informaci&oacute;n.
     * @param numeroControl N&uacute;mero de tr&aacute;mite.
     * @return Listado con relaci&oacute;n de todas las tablas en donde se encuentran guardados los archivos adjuntos a 
     * un tr&aacute;mite; al cual se adjunta la URL, nombre de archivo y campos que los alojan.
     * @throws DAOException Error generado en tiempo de ejecuci&oacute;n.
     */
    @Override
    public List<ArchivoHistoricoDto> consultasParaHistorico(String query, String numeroControl) throws DAOException {
        List<ArchivoHistoricoDto> archivoHistoricoDto = null;
        try {
            archivoHistoricoDto = this.jdbcTemplateDYC.query(query, new Object[]{numeroControl}, new ArchivoHistoricoMapper());
            
        } catch (Exception e) {
            LOGGER.error("Error al ejecutar el query: " + query + ". Error: " + e + ". numeroControl: "+numeroControl);
            throw new DAOException(new Object[]{query, numeroControl}, "method: consultasParaHistorico", e);
        }
        return archivoHistoricoDto;
    }
    
    /**
     * Actualiza la URL de cada tabla de BD que contiene un archivo adjunto a un tr&aacute;mite .
     *
     * @param sql Query generado din&aacute;micamente
     * @param url URL a actualizar
     * @throws DAOException Error generado en tiempo de ejecuci&oacute;n.
     */
     @Override
    public List<ArchivoHistoricoDto> buscarDocumentosNYVProcesados(String numControl) throws DAOException {
        try {
            return jdbcTemplateDYC.query(Querys.OBTENERDOCUMENTOSNYVPROCESADOS.toString() , new Object[]{numControl}, new ArchivoHistoricoMapper());
       } catch (Exception e) {
            LOGGER.error("Error al ejecutar el query nyv: " + Querys.OBTENERDOCUMENTOSNYVPROCESADOS.toString() + ". Nyv Error: " + e + ". numeroControl: "+numControl);
            throw new DAOException(new Object[]{Querys.OBTENERDOCUMENTOSNYVPROCESADOS.toString(), numControl}, "method: buscarDocumentosNYVProcesados", e);
        }

    }
    /**
     * Actualiza la URL de cada tabla de BD que contiene un archivo adjunto a un tr&aacute;mite .
     *
     * @param sql Query generado din&aacute;micamente
     * @param url URL a actualizar
     * @throws DAOException Error generado en tiempo de ejecuci&oacute;n.
     */
    @Override
    public void actualizarURL(String sql, String url) throws DAOException {
        try {
            jdbcTemplateDYC.update(sql, url);
        } catch (DataAccessException e) {
            LOGGER.error("Error al ejecutar el query: " + sql + ". Error: " + e + ". sql: "+sql+". url:"+url);
            throw new DAOException(new Object[]{sql, url}, "method: queryDinamico", e);
        }
    }
    
    /**
     * Inserta un registro en BD.
     * 
     * @param query Es el query para la inserci&oacute;n de datos.
     * @throws DAOException Error generado en tiempo de ejecuci&oacute;n
     */
    @Override
    public void insertar (String query) throws DAOException {
        jdbcTemplateDYC.update(query);
        try {
            jdbcTemplateDYC.update(query);
        } catch (DataAccessException e) {
            LOGGER.error("Error al ejecutar el query: " + query + ". Error: " + e );
            throw new DAOException(new Object[]{query}, "method: insertar", e);
        }
    }
}
