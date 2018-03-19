package mx.gob.sat.siat.dyc.dao.tipotramite.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.tipotramite.DycaOrigenTramiteDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.DycaOrigenTramiteMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
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
 * @author J. Isaac Carbajal Bernal
 * @modifuedBy Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dycaOrigenTramiteDAO")
public class DycaOrigenTramiteDAOImpl implements DycaOrigenTramiteDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final String INSERTAR = "insert into Dyca_OrigenTramite (idTipoServicio, idOrigenSaldo, idTipoTramite) values (?, ?, ?)";
    private static final String ACTUALIZAR = "update Dyca_OrigenTramite set IDTIPOSERVICIO=?, IDORIGENSALDO=? where IDTIPOTRAMITE=?";
    private static final Logger LOG = Logger.getLogger(DycaOrigenTramiteDAOImpl.class);

    @Override
    public List<DycaOrigenTramiteDTO> selectXTipoTramite(Integer idTipoTramite) throws SIATException{
        List<DycaOrigenTramiteDTO> origenTramite = null;
        String query = "SELECT ORT.*, TRA.* FROM DYCA_ORIGENTRAMITE ORT INNER JOIN DYCC_TIPOTRAMITE TRA ON ORT.IDTIPOTRAMITE = TRA.IDTIPOTRAMITE WHERE ORT.IDTIPOTRAMITE = ? AND ORT.FECHAFIN IS NULL";
        try{
            origenTramite = this.jdbcTemplateDYC.query(query, new Object[]{idTipoTramite}, new DycaOrigenTramiteMapper());
            } catch (DataAccessException dae) {
                LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                          ConstantesDyC1.TEXTO_3_ERROR_DAO + " IDTIPOTRAMITE= " + idTipoTramite);
                throw new SIATException(dae);
            }
        return origenTramite;
    }
    
    /**
     * Inserta un registro en base de datos
     *
     * @param objeto
     * @throws SIATException
     */
    @Override
    public void insertar(DycaOrigenTramiteDTO objeto) throws SIATException {
        try {
            jdbcTemplateDYC.update(INSERTAR, new Object[] { objeto.getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio(),
                                                            objeto.getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo(), 
                                                            objeto.getDyccTipoTramiteDTO().getIdTipoTramite()});
            
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + INSERTAR +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO);
            throw new SIATException(dae);
        }
    }
    
    /**
     * Actualiza los datos que se necesitan para el origen del tramite a partir del identificador del tramite.
     *
     * @param objeto
     * @throws SIATException
     */
    @Override
    public void actualizar(DycaOrigenTramiteDTO objeto) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR, new Object[] { 
                objeto.getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio(),
                objeto.getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo(),
                objeto.getDyccTipoTramiteDTO().getIdTipoTramite()
            });
            
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + ACTUALIZAR +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO);
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DycaOrigenTramiteDTO> selectXIdServicio(Integer idServicio) throws DAOException {
        String query =  "SELECT ORT.*, TRA.* FROM DYCA_ORIGENTRAMITE ORT INNER JOIN DYCC_TIPOTRAMITE TRA ON ORT.IDTIPOTRAMITE = TRA.IDTIPOTRAMITE WHERE ORT.IDTIPOSERVICIO = ?";
        try {
            return (List<DycaOrigenTramiteDTO>) jdbcTemplateDYC.query(query, new Object[]{idServicio}, new DycaOrigenTramiteMapper());
        } catch (EmptyResultDataAccessException dae) {
            LOG.info(dae.getMessage());
            return new ArrayList<DycaOrigenTramiteDTO>();
        } catch (DataAccessException dae) {
            throw new DAOException(dae.getMessage(), dae);
        }
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
