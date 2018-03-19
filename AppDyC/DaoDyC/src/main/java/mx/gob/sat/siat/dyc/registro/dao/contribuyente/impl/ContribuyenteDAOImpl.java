/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.sql.Types;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctContribTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.registro.dao.contribuyente.ContribuyenteDAO;
import mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl.mapper.FindContribuyenteTempMapper;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.EDycDaoCodigoError;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;


/**
 *
 * @author GAER8674
 */
@Repository(value = "insertaContribuyenteDAOImpl")
public class ContribuyenteDAOImpl implements ContribuyenteDAO {

    private static final Logger LOG = Logger.getLogger(ContribuyenteDAOImpl.class.getName());


    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void createContribuyenteDYCT(String query, DyctContribuyenteDTO input) {
        LOG.debug("INSERT CONTRIBUYENTE");

        if (input != null) {

            Object[] objParam = getPaseParametros(input);
            int[] typ = getTipoDatos();

            try {
                jdbcTemplateDYC.update(query, objParam, typ);
            } catch (DataAccessException e) {
                LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                          ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(input));
                throw e;
            }
        }
    }

    private Object[] getPaseParametros(DyctContribuyenteDTO contribuyente) {
        LobHandler lobHandler = new DefaultLobHandler();
        SqlLobValue sqlLVDatosContribuyente = null;
        // DATOSRETENEDORBANC
        if (null != contribuyente.getDatosContribuyente()) {
            try {
                byte[] rest = IOUtils.toByteArray(contribuyente.getDatosContribuyente());
                sqlLVDatosContribuyente = new SqlLobValue(new ByteArrayInputStream(rest), rest.length, lobHandler);
            } catch (IOException e) {
                LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
            }
        } else {
            sqlLVDatosContribuyente = new SqlLobValue("", lobHandler);
        }

        Object[] params =
            new Object[] { sqlLVDatosContribuyente, contribuyente.getRolPf(), contribuyente.getRolPm(), contribuyente.getRolGranContrib(),
                           contribuyente.getRolDictaminado(), contribuyente.getRolSociedadControl(),
                           contribuyente.getNumControl() };


        return params;
    }

    private int[] getTipoDatos() {

        int[] tipoDatos =
            new int[] { Types.CLOB, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                        Types.VARCHAR };

        return tipoDatos;
    }

    @Override
    public DyctContribTempDTO findContrinbuyenteTemp(int folio) {
        try {

            return jdbcTemplateDYC.queryForObject(SQLOracleDyC.FIND_CONTRIBUYENTE_TEMP.toString(), new Object[] { folio },
                                                  new FindContribuyenteTempMapper());

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.FIND_CONTRIBUYENTE_TEMP + ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de folio " +
                      folio);
            throw dae;
        }
    }

    @Override
    public void createContribuyente(DyctContribuyenteDTO contribuyente) throws DycDaoExcepcion{
        LobHandler lobHandler = new DefaultLobHandler();
        SqlLobValue xmlContribuyente = null;

        if (null != contribuyente.getXmlContribuyente()) {
            xmlContribuyente = new SqlLobValue(contribuyente.getXmlContribuyente(), lobHandler);
        } else {
            xmlContribuyente = new SqlLobValue("", lobHandler);
        }
        
        Object[] params =
            new Object[] { contribuyente.getFechaConsulta(), xmlContribuyente, contribuyente.getRolPf(), 
                           contribuyente.getRolPm(), contribuyente.getRolGranContrib(), contribuyente.getRolDictaminado(), 
                           contribuyente.getRolSociedadControl(), contribuyente.getNumControl() };
        int[] tipoDatos = 
            new int[] { Types.TIMESTAMP, Types.CLOB, Types.INTEGER, 
                        Types.INTEGER, Types.INTEGER, Types.INTEGER, 
                        Types.INTEGER, Types.VARCHAR };

        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATECONTRIBUYENTE_DI.toString(), params, tipoDatos);
        } catch (DataAccessException e) {
            throw new DycDaoExcepcion(EDycDaoCodigoError.BD_DYC_CONTRIBUYENTE_INSERT_GENERAL, null, e);
        }
    }
}

