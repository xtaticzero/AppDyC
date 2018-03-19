/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.catalogo.dao.impl;


import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccInstCreditoDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccInstCreditoMapper;
import mx.gob.sat.siat.dyc.dao.banco.impl.mapper.DyctCuentaBancoMapper;
import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Implementacion DAO catalogo DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
@Repository(value = "dyccInstCreditoDAO")
public class DyccInstCreditoDAOImpl implements DyccInstCreditoDAO {

    private Logger log = Logger.getLogger(DyccInstCreditoDAOImpl.class.getName());

    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;

    private List<DyccInstCreditoDTO> listaInstCredito;

    private List<DyctCuentaBancoDTO> cuentasBancos;


    public DyccInstCreditoDAOImpl() {
        super();

        listaInstCredito = new ArrayList<DyccInstCreditoDTO>();
    }
    
    /**
     *Obtiene todas las instituciones disponibles en el catalogo 
     *  --LADP[Luis ALberto Dominguez Palomino]
     * @return
     * @throws SQLException
     */
    @Override
    public List<DyccInstCreditoDTO> listaInstCredito() throws SQLException {
        try {
            listaInstCredito = jdbcTemplateDYC.query(SQLOracleDyC.INSTITUCIONCREDITO_LISTARIC.toString(), new DyccInstCreditoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.INSTITUCIONCREDITO_LISTARIC);
            throw dae;
        }
        return listaInstCredito;
    }

    @Override
    public void insertaInstCredito(DyccInstCreditoDTO instCredito) throws SQLException {
        try {
            // TODO: IDINSTCREDITO, DESCRIPCION, ORDENSEC, FECHAINICIO, FECHAFIN
            jdbcTemplateDYC.update(SQLOracleDyC.INSTITUCIONCREDITO_INSERTARIC.toString(),
                                   new Object[] { instCredito.getIdInstCredito(), instCredito.getDescripcion().toUpperCase(),
                                                  instCredito.getOrdenSec(), instCredito.getFechaInicio(),
                                                  instCredito.getFechaFin() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.INSTITUCIONCREDITO_INSERTARIC + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(instCredito));
            throw dae;
        }
    }

    @Override
    public void actualizaInstCredito(DyccInstCreditoDTO instCredito) throws SQLException {
        try {
            // IDINSTCREDITO, DESCRIPCION, ORDENSEC, FECHAINICIO, FECHAFIN
            jdbcTemplateDYC.update(SQLOracleDyC.INSTITUCIONCREDITO_ACTUALIZARIC.toString(), instCredito.getDescripcion(),
                                   instCredito.getOrdenSec(), instCredito.getFechaInicio(), instCredito.getFechaFin(),
                                   instCredito.getIdInstCredito());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.INSTITUCIONCREDITO_ACTUALIZARIC + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(instCredito));
            throw dae;
        }
    }

    @Override
    public DyccInstCreditoDTO getInstitucion(int id) {
        try {
            listaInstCredito =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTAINSTITUCIONFINANCIERA.toString(), new Object[] { id }, new DyccInstCreditoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTAINSTITUCIONFINANCIERA.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    id);
            throw dae;
        }
        if (!listaInstCredito.isEmpty()) {
            return listaInstCredito.get(0);
        }

        return new DyccInstCreditoDTO();
    }

    @Override
    public List<DyctCuentaBancoDTO> getCunetaBancosPorRFC(String rfc) throws SIATException {
        try {
            cuentasBancos =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTACUNETASCLABE.toString(), new Object[] { rfc }, new DyctCuentaBancoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTACUNETASCLABE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc);
            throw new SIATException(dae);
        }
        return cuentasBancos;
    }
    
    /**
     *Obtiene el nombre de la institucion por el campo descripcion
     *  --LADP[Luis ALberto Dominguez Palomino]
     * @param institucion
     * @return
     * @throws SIATException
     */
    @Override
    public DyccInstCreditoDTO institucionXDescripcion(String institucion) throws SIATException {
        DyccInstCreditoDTO instCredito = null;
        try {
            instCredito =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.INSTITUCION_X_DESCRIPCION.toString(), new Object[] { institucion }, new DyccInstCreditoMapper());
        } catch (Exception siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.INSTITUCION_X_DESCRIPCION.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    institucion);
            throw new SIATException(siatE);
        }
        return instCredito;
    }
    // TODO: ACCESSORS *********************************************************

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setListaInstCredito(List<DyccInstCreditoDTO> listaInstCredito) {
        this.listaInstCredito = listaInstCredito;
    }

    public List<DyccInstCreditoDTO> getListaInstCredito() {
        return listaInstCredito;
    }

    public void setCuentasBancos(List<DyctCuentaBancoDTO> cuentasBancos) {
        this.cuentasBancos = cuentasBancos;
    }

    public List<DyctCuentaBancoDTO> getCuentasBancos() {
        return cuentasBancos;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
}
