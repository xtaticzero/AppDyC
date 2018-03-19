package mx.gob.sat.siat.dyc.dao.saldoicep.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.saldoicep.DyccSubSaldoTramDAO;
import mx.gob.sat.siat.dyc.dao.saldoicep.impl.mapper.DyccSubSaldoTramMapper;
import mx.gob.sat.siat.dyc.dao.saldoicep.impl.mapper.DyccSubSaldoTramiteMapper;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.DyccTtSubTramiteDAOImpl;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dyccSubSaldoTramDAO")
public class DyccSubSaldoTramDAOImpl implements DyccSubSaldoTramDAO {
    
    private static final String ACTUALIZAR_FECHA_FIN = "UPDATE Dycc_SubSaldoTram SET FECHAFIN=NULL WHERE IDTIPOTRAMITE = ? AND IDSUBORIGENSALDO = ?";
    private static final String COLOCAR_FECHA_FIN    = "UPDATE Dycc_SubSaldoTram SET FECHAFIN=SYSDATE WHERE IDTIPOTRAMITE = ? AND IDSUBORIGENSALDO = ?";
    private static final String CONSULTA =  "SELECT A.IDSUBORIGENSALDO, A.IDTIPOTRAMITE, B.DESCRIPCION \n" + 
                                            "FROM Dycc_SubSaldoTram A \n" + 
                                            "INNER JOIN DYCC_SUBORIGSALDO B ON (A.IDSUBORIGENSALDO = B.IDSUBORIGENSALDO)\n" + 
                                            "WHERE A.IDTIPOTRAMITE=? AND A.FECHAFIN IS NULL";
    private static final String CONSULTAR_TIPOTRAMITE_SUBSALDOTRAM = "select IDSUBORIGENSALDO, IDTIPOTRAMITE, fechaInicio, fechaFin " +
                                            "from Dycc_SubSaldoTram where IDTIPOTRAMITE = ? and IDSUBORIGENSALDO = ?";
    private static final String INSERTAR = "insert into Dycc_SubSaldoTram (idSubOrigenSaldo, idTipoTramite, fechaInicio, fechaFin) values (?, ?, sysdate, null)";
    private static final String IDTIPOTRAMITE = "idTipoTramite";
    private static final Logger LOGGER = Logger.getLogger(DyccTtSubTramiteDAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    /**
     * Inserta los datos a partir de una lista
     *
     * @param subSaldo
     */
    @Override
    public void insertar(DyccSubSaldoTramDTO  subSaldo) throws SIATException {
        try {
            this.getJdbcTemplateDYC().update(INSERTAR,
                                             subSaldo.getDyccSuborigSaldoDTO().getIdSuborigenSaldo(), 
                                             subSaldo.getDyccTipoTramiteDTO().getIdTipoTramite()
                                            );
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(subSaldo));
            throw new SIATException(e);
        }
    }
    
    /**
     * Conulta los sub-saldos dados de alta a partir del tipo de tramite.
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccSubSaldoTramDTO> consultarXIdTipoTramite(Integer idTipoTramite) throws SIATException {
        
        List<DyccSubSaldoTramDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA, new Object[]{idTipoTramite}, new DyccSubSaldoTramMapper());
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA + ConstantesDyC1.TEXTO_3_ERROR_DAO + IDTIPOTRAMITE +idTipoTramite);
            throw new SIATException(e);
        }
        return lista;
    }
    
    /**
     * Consulta si hay un tipo de subsaldo del tramite asociado a un tramite en base de datos.
     *
     * @param idTipoTramite
     * @param idSubOrigenSaldo
     * @return
     */
    @Override
    public boolean consultarXTipoTramiteSubSaldoTram(Integer idTipoTramite, Integer idSubOrigenSaldo) {
        boolean bandera = Boolean.FALSE;
        try {
            jdbcTemplateDYC.queryForObject(CONSULTAR_TIPOTRAMITE_SUBSALDOTRAM, 
                                                    new Object[]{idTipoTramite, idSubOrigenSaldo}, 
                                                    new DyccSubSaldoTramiteMapper());
            bandera = Boolean.TRUE;
            
        } catch (Exception dae) {
            LOGGER.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTAR_TIPOTRAMITE_SUBSALDOTRAM + ConstantesDyC1.TEXTO_3_ERROR_DAO + IDTIPOTRAMITE + 
                         idTipoTramite + ", idSubOrigenSaldo: " + idSubOrigenSaldo);
        }
        return bandera;
    }
    
    /**
     * Actualiza la fecha fin a null para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idSubOrigenSaldo
     */
    @Override
    public void actualizarFechaFin(Integer idTipoTramite, Integer idSubOrigenSaldo) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_FECHA_FIN, new Object[]{idTipoTramite, idSubOrigenSaldo});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         ACTUALIZAR_FECHA_FIN+ ConstantesDyC1.TEXTO_3_ERROR_DAO + IDTIPOTRAMITE + 
                         idTipoTramite + ", idSubOrigenSaldo: " + idSubOrigenSaldo);
            throw new SIATException(e);            
        }
    }
    
    /**
     * Actualiza la fecha fin al dia de hoy para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idSubOrigenSaldo
     */
    @Override
    public void colocarFechaFin(Integer idTipoTramite, Integer idSubOrigenSaldo) throws SIATException {
        try {
            jdbcTemplateDYC.update(COLOCAR_FECHA_FIN, new Object[]{idTipoTramite, idSubOrigenSaldo});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         COLOCAR_FECHA_FIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + IDTIPOTRAMITE + 
                         idTipoTramite + ", idSubOrigenSaldo: " + idSubOrigenSaldo);
            throw new SIATException(e);            
        }
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
