package mx.gob.sat.siat.dyc.dao.seguimiento.impl;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import mx.gob.sat.siat.dyc.dao.seguimiento.DycbSeguimientoFSDAO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import mx.gob.sat.siat.dyc.vo.DycbSeguimientoFSVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dycbSeguimientoFSDAO")
public class DycbSeguimientoFSDAOImpl implements DycbSeguimientoFSDAO {
    public DycbSeguimientoFSDAOImpl() {
        super();
    }
    private static final String INSERTAR="INSERT INTO DYCB_SEGUIMIENTOFS (IDFSSEGUIMIENTO, IDFILESYSTEM, NUMCONTROL, EXITO, FECHA) VALUES (?, ?, ?, ?, SYSDATE)";
    private static final String UPDATE="update DYCB_SEGUIMIENTOFS set IDFILESYSTEM=?,  EXITO=?, FECHA=SYSDATE WHERE IDFSSEGUIMIENTO=?";
    private static final String CONSULTAR_POR_FECHA = "SELECT COUNT(*) \n" + 
                                                      "FROM Dycb_SeguimientoFS A \n" + 
                                                      "INNER JOIN DYCB_CAUSAFALLOFS B ON (A.IDFSSEGUIMIENTO = b.IDFSSEGUIMIENTO)\n" + 
                                                      "WHERE A.FECHA BETWEEN ? AND ? AND A.EXITO = ?";
    private static final String CONSULTAR_SECUENCIA = "SELECT DYCQ_SEGUIMIENTOFS.nextval FROM DUAL";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DycbSeguimientoFSDAOImpl.class);

    /**
     * Crea un nuevo registro en base de datos a partir de se muevan todos los documentos de un tr&aacute;mite; ya
     * sea que se haya creado exitosamente o no.
     *
     * @param objeto Objeto de la clase DycbSeguimientoFSVO.
     * @throws DycDaoExcepcion
     */
    @Override
    public void insertar(DycbSeguimientoFSVO objeto) throws DAOException {
        try {
            jdbcTemplateDYC.update(INSERTAR,
                                   new Object[] {objeto.getIdFSSeguimiento(), objeto.getIdFileSystem(), objeto.getNumControl(), objeto.getExito()});
        } catch (DataAccessException dae) {
            LOGGER.error("Error al guardar"+dae.getCause()+"\n"+ExtractorUtil.ejecutar(objeto));
            throw new DAOException(new Object[] {objeto}, "Error con el query: "+INSERTAR, dae);
        }
    }
  @Override
    public void update(DycbSeguimientoFSVO objeto) throws DAOException {
        try {
            jdbcTemplateDYC.update(UPDATE,
                                   new Object[] {objeto.getIdFileSystem(),   objeto.getExito(),objeto.getIdFSSeguimiento()});
        } catch (DataAccessException dae) {
            LOGGER.error("Error al guardar"+dae.getCause()+"\n"+ExtractorUtil.ejecutar(objeto));
            throw new DAOException(new Object[] {objeto}, "Error con el query: "+UPDATE, dae);
        }
    }
    /**
     * Busca todos los tr&aacute;mites que fueron procesados por el proceso de PjArchivosHistorico para mover 
     * los documentos adjuntos que posseen de un filesystem a otro y los cuenta.
     *
     * @param fecha Fecha de consulta de procesamiento.
     * @param identificador 1 Para contar los registros que fueron movidos exitosamente. 0 En caso contrario.
     * @return
     * @throws DycDaoExcepcion
     */
    @Override
    public int consutarNoDeRegistrosProcesados(Date fecha, int identificador) throws DAOException {
        int noRegistros  = 0;
        Date fechaInicio = null;
        Date fechaFin    = null;
        Locale localeMexico = new Locale("es", "MX");
        SimpleDateFormat formatoSimple   = new SimpleDateFormat("dd/MM/yyyy", localeMexico);
        SimpleDateFormat formatoCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", localeMexico);
        
        try {
            fechaInicio = formatoCompleto.parse(formatoSimple.format(fecha)+" 00:00:01");
            fechaFin    = formatoCompleto.parse(formatoSimple.format(fecha)+" 23:59:59");
            
            noRegistros = jdbcTemplateDYC.queryForObject(CONSULTAR_POR_FECHA, 
                                                         new Object[]{fechaInicio, fechaFin, identificador}, 
                                                         Integer.class);
        } catch (Exception e) {
            LOGGER.error("consutarNoDeRegistrosProcesados(): "+e.getCause()+"\n. fecha: "+fecha+". identificador: "+identificador);
            throw new DAOException(new Object[] {fecha, identificador}, "consutarNoDeRegistrosProcesados()", e);
        }
        return noRegistros;
    }

    /**
     * Extrae el n&uacute;mero de secuecia para insertar en base de datos.
     *
     * @return N&uacute;mero de secuencia.
     * @throws SIATException
     */
    @Override
    public int consultarNoDeSecuencia() throws DAOException {
        int secuencia=0;
        try {
            secuencia = jdbcTemplateDYC.queryForObject(CONSULTAR_SECUENCIA, Integer.class);
        } catch (DataAccessException dae) {
            LOGGER.error("Error al consultarNoDeSecuencia"+dae.getCause());
            throw new DAOException(null, "Error con el query: "+INSERTAR, dae);
        }
        return secuencia;
    }
}
