package mx.gob.sat.siat.dyc.dao.fallo.impl;

import mx.gob.sat.siat.dyc.dao.fallo.DycbCausaFalloFSDAO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import mx.gob.sat.siat.dyc.vo.DycbCausaFalloFSVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dycbConsultaFalloFSDAO")
public class DycbCausaFalloFSDAOImpl implements DycbCausaFalloFSDAO {
    public DycbCausaFalloFSDAOImpl() {
        super();
    }
    private static final String INSERTAR="INSERT INTO DYCB_CAUSAFALLOFS (IDFSSEGUIMIENTO, CAUSA) VALUES (?, ?)";
    private static final String SELECT="Select count(IDFSSEGUIMIENTO) from DYCB_CAUSAFALLOFS  where IDFSSEGUIMIENTO=?";
     private static final String UPDATE=" update DYCB_CAUSAFALLOFS set  CAUSA=? where IDFSSEGUIMIENTO=?";
    private static final Logger LOGGER = Logger.getLogger(DAOException.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Crea un nuevo registro que contiene el ID de movimiento la causa del fallo.
     *
     * @param objeto Objeto que aloja la informaci&oacute;n de la causa por la cual no se copia un documento 
     * de un filesystem a otro.
     * @throws DAOException Error durante tiempo de ejecuci&oacute;n.
     */
    @Override
    public void insertar(DycbCausaFalloFSVO objeto) throws DAOException {
        try {
            jdbcTemplateDYC.update(INSERTAR, new Object[] { objeto.getIdFSSeguimiento(), objeto.getCausa() });
        } catch (DataAccessException e) {
            LOGGER.error("Error al insertar la causa del fallo. Con el query: "+INSERTAR+". Con los parametros: "+ExtractorUtil.ejecutar(objeto)+". Error"+e);
            throw new DAOException(new Object[]{objeto},INSERTAR,e);
        }
    }
     public Integer select(int idSeguimiento) throws DAOException {
        try {
          return  jdbcTemplateDYC.queryForObject(SELECT, new Object[] { idSeguimiento},Integer.class);
        } catch (DataAccessException e) {
            LOGGER.error("Error al SELECT la causa del fallo. Con el query: "+SELECT+". Con los parametros: "+ExtractorUtil.ejecutar(idSeguimiento)+". Error"+e);
            throw new DAOException(new Object[]{idSeguimiento},SELECT,e);
        }
    }
     @Override
    public void update(DycbCausaFalloFSVO objeto) throws DAOException {
        try {
            jdbcTemplateDYC.update(UPDATE, new Object[] { objeto.getCausa(),objeto.getIdFSSeguimiento()  });
        } catch (DataAccessException e) {
            LOGGER.error("Error al actualizar la causa del fallo. Con el query: "+UPDATE+". Con los parametros: "+ExtractorUtil.ejecutar(objeto)+". Error"+e);
            throw new DAOException(new Object[]{objeto},UPDATE,e);
        }
    }
}
