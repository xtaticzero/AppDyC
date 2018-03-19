/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.periodovacacional.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.periodovacacional.DycpPeriodoVacDAO;
import mx.gob.sat.siat.dyc.dao.periodovacacional.impl.mapper.DycpPeriodoVacMapper;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DycpPeriodoVacDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("dycpPeriodoVacDAOImpl")
public class DycpPeriodoVacDAOImpl implements DycpPeriodoVacDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOG = Logger.getLogger(DycpPeriodoVacDAOImpl.class.getName());

    /**
     * Nuevo registro de periodo vacacional
     *
     * @param dycpPeriodoVacDTO
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    public void nuevoPeriodoVac(DycpPeriodoVacDTO dycpPeriodoVacDTO) throws SIATException {
        try {
            dycpPeriodoVacDTO.setIdPeriodo(this.getJdbcTemplateDYC().queryForObject("SELECT DYCQ_PERVACACIONAL.NEXTVAL FROM DUAL", Integer.class));
            this.getJdbcTemplateDYC().update(SQLOracleDyC.INSERTAR_PERIODOVACACIONAL.toString(),
                    dycpPeriodoVacDTO.getIdPeriodo(),
                    dycpPeriodoVacDTO.getInicioPeriodo(),
                    dycpPeriodoVacDTO.getFinPeriodo(),
                    dycpPeriodoVacDTO.isEstado(),
                    dycpPeriodoVacDTO.getMensaje(),
                    dycpPeriodoVacDTO.getInicioHoraInhabServ(),
                    dycpPeriodoVacDTO.getFinHoraInhabServ());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.INSERTAR_PERIODOVACACIONAL);
            throw new SIATException(dae);
        }
    }

    /**
     * Consulta el periodo vacacional
     *
     * @return objeto DycpPeriodoVacDTO
     * @throws SIATException
     */
    @Override
    public List<DycpPeriodoVacDTO> obtenerPeriodos() throws SIATException {
        List<DycpPeriodoVacDTO> dycpPeriodoVac = new ArrayList<DycpPeriodoVacDTO>();
        try {
            dycpPeriodoVac = this.getJdbcTemplateDYC().query(SQLOracleDyC.CONSULTAR_PERIODOVACACIONALES.toString(), new Object[]{}, new DycpPeriodoVacMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTAR_PERIODOVACACIONALES);
        }
        return dycpPeriodoVac;
    }

    /**
     * Consulta registro individual de periodo vacacional
     *
     * @param idPeriodoVac
     * @return objeto DycpPeriodoVacDTO
     * @throws SIATException
     */
    @Override
    public DycpPeriodoVacDTO buscaPeriodoVacXId(Integer idPeriodoVac) throws SIATException {
        DycpPeriodoVacDTO dycpPeriodoVacXId = null;
        try {
            dycpPeriodoVacXId = this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.CONSULTAR_PERIODOVACACIONALESXID.toString(), new Object[]{}, new DycpPeriodoVacMapper());
        } catch (EmptyResultDataAccessException e) {
            dycpPeriodoVacXId = null;
            LOG.error("No se ecnontraron registros asociados al id: " + idPeriodoVac);
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTAR_PERIODOVACACIONALES);
        }
        return dycpPeriodoVacXId;
    }
    
    @Override
    public DycpPeriodoVacDTO buscaPeriodoVacActivo() throws SIATException {
        DycpPeriodoVacDTO dycpPeriodoVacXId = null;
        try {
            dycpPeriodoVacXId = this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.CONSULTA_PERIODO_ACTIVO, new Object[]{}, new DycpPeriodoVacMapper());
        } catch (EmptyResultDataAccessException e) {
            dycpPeriodoVacXId = null;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_PERIODO_ACTIVO);
        }
        return dycpPeriodoVacXId;
    }
    

    /**
     * Actualiza el estatus del periodo vacacional
     *
     * @return
     */
    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    @Override
    public void actualizarEstadoPeriodoVac(DycpPeriodoVacDTO dycpPeriodoVacDTO) throws SIATException {
        try {

            this.jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_PERIODOVACACIONAL.toString(),
                    new Object[]{dycpPeriodoVacDTO.getInicioPeriodo(), 
                        dycpPeriodoVacDTO.getFinPeriodo(), 
                        dycpPeriodoVacDTO.isEstado(),
                        dycpPeriodoVacDTO.getMensaje(), 
                    dycpPeriodoVacDTO.getInicioHoraInhabServ(), 
                    dycpPeriodoVacDTO.getFinHoraInhabServ(), 
                    dycpPeriodoVacDTO.getIdPeriodo()});
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAR_PERIODOVACACIONAL);
        }
    }


}
