package mx.gob.sat.siat.dyc.dao.movsaldo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.movsaldo.DyccOrigenSaldoDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.OrigenSaldoMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyccOrigenSaldoDAOImpl implements DyccOrigenSaldoDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccOrigenSaldoDAOImpl.class.getName());

    @Override
    public DyccOrigenSaldoDTO encontrar(Integer id) {
        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
        log.info(dyccOrigenSaldoDTO);
        try {
            dyccOrigenSaldoDTO =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.COLSUTALDYCC_ORIGENSALDO.toString(), new Object[] { id }, new OrigenSaldoMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.COLSUTALDYCC_ORIGENSALDO + ConstantesDyC1.TEXTO_3_ERROR_DAO + id);
        }
        return dyccOrigenSaldoDTO;
    }

    /**Metodo que obtiene todos los campos que existen en la tabla dycc_origensaldo
     * @return
     */
    @Override
    public List<DyccOrigenSaldoDTO> obtieneTodos() {
        List<DyccOrigenSaldoDTO> origenSaldo = new ArrayList<DyccOrigenSaldoDTO>();

        try {
            origenSaldo = jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_CONSULTAORIGENSALDO.toString(), new OrigenSaldoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_CONSULTAORIGENSALDO.toString());
        }
        return origenSaldo;
    }

    /**
     * Metodo que agrega un origen de saldo a la tabla dycc_origensaldo
     * @param origenSaldo
     */
    @Override
    public void agrega(DyccOrigenSaldoDTO origenSaldo) {
    }

    /**
     * Metodo que actualiza un origen de saldo a la tabla dycc_origensaldo
     * @param origenSaldo
     */
    @Override
    public void actualiza(DyccOrigenSaldoDTO origenSaldo) {
    }

    /**
     * Metodo que actualiza un origen de saldo en la tabla dycc_origensaldo
     * @param idOrigenSaldo
     */
    @Override
    public void elimina(int idOrigenSaldo) {
    }

    @Override
    public List<DyccOrigenSaldoDTO> obtieneOrigenesDeSaldosPorAvisoCompensacion() {
        List<DyccOrigenSaldoDTO> origenSaldo = new ArrayList<DyccOrigenSaldoDTO>();
        try {
            origenSaldo =
                    jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTENER_ORIGENES_SALDOS_POR_TIPO_SERVICIO.toString(), new OrigenSaldoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_ORIGENES_SALDOS_POR_TIPO_SERVICIO);
        }
        return origenSaldo;
    }
    
    
    /**
     * Este metodo consulta una lista de tipos de origen
     * @param cadena con los id a consultar separados por coma 1,2,3
     * @return Una lista de <object>DyccOrigenSaldoDTO</object>
     */
    public List<DyccOrigenSaldoDTO> obtieneOrigenesDeSaldosPorAvisoCompensacion(final String param1) 
    
    {
        List<DyccOrigenSaldoDTO> origenSaldo = null;
        try 
        {
            origenSaldo = jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTENER_ORIGENES_SALDOS.toString().replaceAll("@param1", param1), new OrigenSaldoMapper());
            
        } 
        catch (DataAccessException dae) 
        {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + SQLOracleDyC.SQL_OBTENER_ORIGENES_SALDOS_POR_TIPO_SERVICIO);
        }
        return origenSaldo;
    }
    
    

    @Override
    public List<DyccOrigenSaldoDTO> obtieneTodos(String idConsulta) {
        return jdbcTemplateDYC.query(SQLOracleDyC.GET_CATALOGO_ORIGEN.toString().concat(idConsulta), new OrigenSaldoMapper());
    }

    /**
     * Consulta una lista de los origenes del saldo de acuerdo al tipo de servicio.
     *
     * @param idTipoServicio
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccOrigenSaldoDTO> consultaXTipoServicio(Integer idTipoServicio) throws SIATException {
        List<DyccOrigenSaldoDTO> origenSaldo = new ArrayList<DyccOrigenSaldoDTO>();
        try {
            origenSaldo =
                    jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTENER_ORIGENES_SALDOS_X_TIPO_SERVICIO.toString(), new Object[] { idTipoServicio },
                                          new OrigenSaldoMapper());

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_ORIGENES_SALDOS_X_TIPO_SERVICIO);
        }
        return origenSaldo;
    }

    @Override
    public DyccOrigenSaldoDTO obtenerOrigenSaldoPorIdTipoTramite(Integer idTipoTramite) {
        DyccOrigenSaldoDTO origenSaldo = null;
        String query = 
            "SELECT OS.IDORIGENSALDO, OS.DESCRIPCION,OS.FECHAINICIO FROM DYCA_ORIGENTRAMITE OT\n" + 
            "INNER JOIN DYCC_ORIGENSALDO OS ON OS.IDORIGENSALDO = OT.IDORIGENSALDO \n" + 
            "WHERE IDTIPOTRAMITE = ? AND OT.FECHAFIN IS NULL\n";
        try {
            origenSaldo =
                    jdbcTemplateDYC.queryForObject(query, new Object[] { idTipoTramite },
                                          new OrigenSaldoMapper());

        } catch (Exception dae) {
            log.error(query + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query);
        }
        return origenSaldo;
    }

    @Override
    public DyccOrigenSaldoDTO getOrigenSaldo(int idOrigen) {
        return jdbcTemplateDYC.queryForObject(SQLOracleDyC.FIND_ORGIGEN_SALDO.toString(), new Object[] { idOrigen }, new OrigenSaldoMapper());
    }

    @Override
    public List<DyccOrigenSaldoDTO> obtenerOrigenSaldoTramitesSinOficio() {
        List<DyccOrigenSaldoDTO> origenSaldo = new ArrayList<DyccOrigenSaldoDTO>();

        try {
            origenSaldo = jdbcTemplateDYC.query(SQLOracleDyC.CONSULTAORIGENSALDO_RESOLSINOFICIO.toString(), new OrigenSaldoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTAORIGENSALDO_RESOLSINOFICIO.toString());
        }
        return origenSaldo;
    }

}
