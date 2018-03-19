/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.dao.ArchivoDAO;
import mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper.ArchivoMapper;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Alfredo Ramirez
 * @since  03/07/2014
 *
 */
@Repository(value = "archivoDAO")
public class ArchivoDAOImpl implements ArchivoDAO{

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(ArchivoDAOImpl.class);

    public ArchivoDAOImpl() {
        super();
    }

    @Override
    public void insertar(final List<ArchivoVO> archivos){
        try {
            jdbcTemplateDYC.batchUpdate(SQLOracleDyC.INSERTAR_DYCT_ARCHIVOAVISO, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ArchivoVO archivo = archivos.get(i);
                        ps.setString(ConstantesDyCNumerico.VALOR_1, archivo.getNombreArchivo());
                        ps.setString(ConstantesDyCNumerico.VALOR_2, archivo.getUrl());
                        ps.setString(ConstantesDyCNumerico.VALOR_3, archivo.getDescripcion());
                        ps.setDate(ConstantesDyCNumerico.VALOR_4, new java.sql.Date(archivo.getFechaRegistro().getTime()));
                        ps.setString(ConstantesDyCNumerico.VALOR_5, archivo.getDycpAvisoCompDTO().getFolioAviso());
                    }

                    @Override
                    public int getBatchSize() {
                        return archivos.size();
                    }
                });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.INSERTAR_DYCT_ARCHIVOAVISO);
            throw dae;
        }
    }
    
    @Override
    public List<ArchivoVO> buscaArchivosPorFolioAviso(String folioAviso) {
        List<ArchivoVO> archivo = null;
        String sql = "SELECT * FROM DYCT_ARCHIVOAVISO WHERE FOLIOAVISO = ?";
        try{
            archivo = jdbcTemplateDYC.query(sql, new Object[]{folioAviso} , new ArchivoMapper());
        }catch(DataAccessException dae){
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.INSERTAR_DYCT_ARCHIVOAVISO);
            throw dae;
        }
        return archivo;
    }
    
    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
}
