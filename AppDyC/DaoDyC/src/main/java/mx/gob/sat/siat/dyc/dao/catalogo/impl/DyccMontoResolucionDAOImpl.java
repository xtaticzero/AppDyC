/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.catalogo.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.catalogo.DyccMontoResolucionDAO;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper.DyccMontoResolucionMapper;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoResolucionDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository("dyccMontoResolucionDAOImpl")
public class DyccMontoResolucionDAOImpl implements DyccMontoResolucionDAO {

    private static final Logger LOG = Logger.getLogger(DyccMontoResolucionDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final String ADD_MONTO = "INSERT INTO DYCC_MONTORESOLUCION VALUES(?,?,?,?,?)";

    private static final String UPDATE_MONTO = "UPDATE DYCC_MONTORESOLUCION SET IDORIGENDEV = ?, IDTIPOTRAMITE = ?, MONTOAUTORIZADO = ?, ESTADO = ? WHERE IDMONTORESOL = ?";

    private static final String EXISTE_MONTO_ACTIVO = "SELECT MSA.IDMONTORESOL, MSA.IDMONTORESOL, MSA.IDORIGENDEV, OS.DESCRIPCION AS ORIGENDEV, TT.IDTIPOTRAMITE  || ' ' ||  TT.DESCRIPCION AS TIPOTRAMITE, TT.IDTIPOTRAMITE, MSA.MONTOAUTORIZADO,"
            + " MSA.ESTADO FROM DYCC_MONTORESOLUCION MSA"
            + " INNER JOIN DYCC_ORIGENSALDO OS ON (MSA.IDORIGENDEV = OS.IDORIGENSALDO)"
            + " INNER JOIN DYCC_TIPOTRAMITE TT ON (MSA.IDTIPOTRAMITE = TT.IDTIPOTRAMITE)"
            + " WHERE MSA.IDTIPOTRAMITE = ? AND MSA.ESTADO = 1 ORDER BY MSA.IDMONTORESOL ASC";

    private static final String ELIMINAR_MONTO = "UPDATE DYCC_MONTORESOLUCION SET ESTADO = 0 WHERE IDMONTORESOL = ?";

    private static final String FIND_ALL_MONTOS = "SELECT MSA.IDMONTORESOL, MSA.IDMONTORESOL, MSA.IDORIGENDEV, OS.DESCRIPCION AS ORIGENDEV, TT.IDTIPOTRAMITE  || ' ' ||  TT.DESCRIPCION AS TIPOTRAMITE, TT.IDTIPOTRAMITE, MSA.MONTOAUTORIZADO,"
            + " MSA.ESTADO FROM DYCC_MONTORESOLUCION MSA"
            + " INNER JOIN DYCC_ORIGENSALDO OS ON (MSA.IDORIGENDEV = OS.IDORIGENSALDO)"
            + " INNER JOIN DYCC_TIPOTRAMITE TT ON (MSA.IDTIPOTRAMITE = TT.IDTIPOTRAMITE)";

    private static final String EXISTE_MONTO = "SELECT MSA.IDMONTORESOL, MSA.IDMONTORESOL, MSA.IDORIGENDEV, OS.DESCRIPCION AS ORIGENDEV, TT.IDTIPOTRAMITE  || ' ' ||  TT.DESCRIPCION AS TIPOTRAMITE, TT.IDTIPOTRAMITE, MSA.MONTOAUTORIZADO,"
            + " MSA.ESTADO FROM DYCC_MONTORESOLUCION MSA"
            + " INNER JOIN DYCC_ORIGENSALDO OS ON (MSA.IDORIGENDEV = OS.IDORIGENSALDO)"
            + " INNER JOIN DYCC_TIPOTRAMITE TT ON (MSA.IDTIPOTRAMITE = TT.IDTIPOTRAMITE)"
            + " WHERE MSA.IDTIPOTRAMITE = ? ORDER BY MSA.IDMONTORESOL ASC";

    private static final String OBTENER_SECUENCIA = "SELECT DYCQ_MONTORESOLUCION.NEXTVAL FROM DUAL";

    @Override
    public void insertarMonto(DyccMontoResolucionDTO montoResolucion) throws SIATException {

        Integer idMontoResolucion = null;
        try {
            idMontoResolucion = this.jdbcTemplateDYC.queryForObject(OBTENER_SECUENCIA, Integer.class);
            jdbcTemplateDYC.update(ADD_MONTO,
                    new Object[]{idMontoResolucion,
                        montoResolucion.getIdOrigenDevolucion(),
                        montoResolucion.getIdTipoTramite(),
                        montoResolucion.getMontoAutorizado(),
                        montoResolucion.getEstado()});
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + ADD_MONTO + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(montoResolucion));
            throw new SIATException(dae);
        }

    }

    @Override
    public void updateMonto(DyccMontoResolucionDTO montoResolucion) throws SIATException {
        try {
            jdbcTemplateDYC.update(UPDATE_MONTO,
                    new Object[]{montoResolucion.getIdOrigenDevolucion(),
                        montoResolucion.getIdTipoTramite(), montoResolucion.getMontoAutorizado(),
                        montoResolucion.getEstado(), montoResolucion.getIdMontoResolucion()});
        } catch (Exception siatE) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + UPDATE_MONTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + montoResolucion.getIdMontoResolucion());
            throw new SIATException(siatE);
        }
    }

    @Override
    public void eliminarMonto(DyccMontoResolucionDTO montoResolucion) throws SIATException {
        try {
            jdbcTemplateDYC.update(ELIMINAR_MONTO,
                    new Object[]{montoResolucion.getIdMontoResolucion()});
        } catch (Exception siatE) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + ELIMINAR_MONTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + montoResolucion.getIdMontoResolucion());
            throw new SIATException(siatE);
        }
    }

    @Override
    public DyccMontoResolucionDTO buscarMontoActivoXId(Integer idTipoTramite) {
        try {
            return (DyccMontoResolucionDTO) jdbcTemplateDYC.queryForObject(EXISTE_MONTO_ACTIVO, new Object[]{idTipoTramite}, new DyccMontoResolucionMapper());
        } catch (EmptyResultDataAccessException e) {
            LOG.info(e);
            return null;
        } catch (DataAccessException e) {
            LOG.info(e);
            return null;
        }
    }

    @Override
    public List<DyccMontoResolucionDTO> buscarAllMontos() throws DAOException {
        try {
            return (List<DyccMontoResolucionDTO>) jdbcTemplateDYC.query(FIND_ALL_MONTOS, new DyccMontoResolucionMapper());
        } catch (EmptyResultDataAccessException e) {
            LOG.info(e);
            return new ArrayList<DyccMontoResolucionDTO>();
        } catch (DataAccessException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public DyccMontoResolucionDTO existeMontoActivo(DyccMontoResolucionDTO montoResolucion) throws DAOException {
        try {
            return (DyccMontoResolucionDTO) jdbcTemplateDYC.queryForObject(EXISTE_MONTO, new Object[]{montoResolucion.getIdTipoTramite()}, new DyccMontoResolucionMapper());
        } catch (EmptyResultDataAccessException e) {
            LOG.info(e);
            return null;
        } catch (DataAccessException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
