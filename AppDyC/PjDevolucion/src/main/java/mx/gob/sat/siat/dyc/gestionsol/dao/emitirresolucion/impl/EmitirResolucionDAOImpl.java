/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.dao.emitirresolucion.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.sql.Types;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpCredFisDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.emitirresolucion.EmitirResolucionDAO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;


/**
 * @author Federico Chopin Gachuz
 * @date Abril 25, 2014
 *
 * */
@Repository(value = "emitirResolucionDAO")
public class EmitirResolucionDAOImpl implements EmitirResolucionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(EmitirResolucionDAOImpl.class.getName());

    public EmitirResolucionDAOImpl() {
        super();
    }

    @Override
    public void insertarResolucion(DyctResolucionDTO dyctResolucionDTO) throws SIATException {

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.EMITIRRESOLUCION_INSERTARRESOLUCION.toString(),
                                   new Object[] { dyctResolucionDTO.getDycpSolicitudDTO().getNumControl(),
                                                  dyctResolucionDTO.getDyccTipoResolDTO().getIdTipoResol(),
                                                  dyctResolucionDTO.getImporteSolicitado(),
                                                  dyctResolucionDTO.getImpAutorizado(),
                                                  dyctResolucionDTO.getImpActualizacion(),
                                                  dyctResolucionDTO.getIntereses(),
                                                  dyctResolucionDTO.getRetIntereses(),
                                                  dyctResolucionDTO.getImpCompensado(),
                                                  dyctResolucionDTO.getImporteNeto(),
                                                  dyctResolucionDTO.getFundamentacion(),
                                                  dyctResolucionDTO.getDyccEstreSolDTO().getIdEstResol(),
                                                  dyctResolucionDTO.getSaldoAfavorAntRes(),
                                                  dyctResolucionDTO.getImpNegado(),
                                                  dyctResolucionDTO.getPagoEnviado() });

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_INSERTARRESOLUCION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(dyctResolucionDTO));
            throw new SIATException(dae);
        }

    }

    @Override
    public void actualizarResolucion(DyctResolucionDTO dyctResolucionDTO) throws SIATException {

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.EMITIRRESOLUCION_ACTUALIZARRESOLUCION.toString(),
                                   new Object[] { dyctResolucionDTO.getDyccTipoResolDTO().getIdTipoResol(),
                                                  dyctResolucionDTO.getImporteSolicitado(),
                                                  dyctResolucionDTO.getImpAutorizado(),
                                                  dyctResolucionDTO.getImpActualizacion(),
                                                  dyctResolucionDTO.getIntereses(),
                                                  dyctResolucionDTO.getRetIntereses(),
                                                  dyctResolucionDTO.getImpCompensado(),
                                                  dyctResolucionDTO.getImporteNeto(),
                                                  dyctResolucionDTO.getFundamentacion(),
                                                  dyctResolucionDTO.getDyccEstreSolDTO().getIdEstResol(),
                                                  dyctResolucionDTO.getSaldoAfavorAntRes(),
                                                  dyctResolucionDTO.getImpNegado(), dyctResolucionDTO.getPagoEnviado(),
                                                  dyctResolucionDTO.getDycpSolicitudDTO().getNumControl() });

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_ACTUALIZARRESOLUCION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(dyctResolucionDTO));
            throw new SIATException(dae);
        }

    }

    @Override
    public Integer buscarMotivoPadre(Integer idMotivo) throws SIATException {

        Integer idMotivoPadre = 0;

        try {

            idMotivoPadre =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.EMITIRRESOLUCION_BUSCARMOTIVOPADRE.toString(), new Object[] { idMotivo },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_BUSCARMOTIVOPADRE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    "idMotivo: " + idMotivo);
            throw new SIATException(dae);
        }

        return idMotivoPadre;

    }
    
    
    
    
    @Override
    public String buscarDescripcionMotivoPadre(Integer idMotivo) throws SIATException {

        String descMotivoPadreD = null;

        try {

            descMotivoPadreD =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.EMITIRRESOLUCION_BUSCARDESCRIPCIONMOTIVOPADRE.toString(), new Object[] { idMotivo },
                                                   String.class);

        } catch(EmptyResultDataAccessException erdae) {
            
            log.info("NO se encontraron registros en la tabla DYCC_MOTIVORES para el IDMOTIVO ->" + idMotivo);
            return null;
            
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_BUSCARDESCRIPCIONMOTIVOPADRE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    "idMotivo: " + idMotivo);
            throw new SIATException(dae);
        }

        return descMotivoPadreD;

    }
    
    @Override
    public String buscarDescripcionMotivoDesistida(Integer idMotivo) throws SIATException {

        String descMotivoDesistida = null;

        try {

            descMotivoDesistida =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.EMITIRRESOLUCION_BUSCARDESCRIPCIONMOTIVODESISTIDA.toString(), new Object[] { idMotivo },
                                                   String.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_BUSCARDESCRIPCIONMOTIVODESISTIDA.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    "idMotivo: " + idMotivo);
            throw new SIATException(dae);
        }

        return descMotivoDesistida;

    }
    

    @Override
    public void actualizarExpediente(DyctExpCredFisDTO expediente) throws SIATException {
        
        if (expediente != null) {

            Object[] objParam = getPaseParametros3(expediente);
            int[] typ = getTipoDatos3();

            try {
                jdbcTemplateDYC.update(SQLOracleDyC.EMITIRRESOLUCION_ACTUALIZAR.toString(), objParam, typ);
            } catch (DataAccessException dae) {
                log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                        SQLOracleDyC.EMITIRRESOLUCION_ACTUALIZAR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                        ExtractorUtil.ejecutar(expediente));
                throw new SIATException(dae);
            }
        }

    }
    
    private Object[] getPaseParametros2(DyctExpCredFisDTO expediente) {
        LobHandler lobHandler = new DefaultLobHandler();
        SqlLobValue sqlLVDatosExpediente = null;
        
        if (null != expediente.getDatosCredFis()) {
            try {
                byte[] rest = IOUtils.toByteArray(expediente.getDatosCredFis());
                sqlLVDatosExpediente = new SqlLobValue(new ByteArrayInputStream(rest), rest.length, lobHandler);
            } catch (IOException e) {
                log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
                log.error(e);
            }
        } else {
            sqlLVDatosExpediente = new SqlLobValue("", lobHandler);
        }

        Object[] params =
            new Object[] { expediente.getDyctExpedienteDTO().getServicioDTO().getNumControl(), sqlLVDatosExpediente };


        return params;
    }
    
    private Object[] getPaseParametros3(DyctExpCredFisDTO expediente) {
        LobHandler lobHandler = new DefaultLobHandler();
        SqlLobValue sqlLVDatosExpediente = null;
        
        if (null != expediente.getDatosCredFis()) {
            try {
                byte[] rest = IOUtils.toByteArray(expediente.getDatosCredFis());
                sqlLVDatosExpediente = new SqlLobValue(new ByteArrayInputStream(rest), rest.length, lobHandler);
            } catch (IOException e) {
                log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
                log.error(e);
            }
        } else {
            sqlLVDatosExpediente = new SqlLobValue("", lobHandler);
        }

        Object[] params =
            new Object[] { sqlLVDatosExpediente, expediente.getDyctExpedienteDTO().getServicioDTO().getNumControl() };


        return params;
    }
    
    private int[] getTipoDatos2() {

        int[] tipoDatos = new int[] { Types.VARCHAR, Types.CLOB };

        return tipoDatos;
    }
    
    private int[] getTipoDatos3() {

        int[] tipoDatos = new int[] { Types.CLOB, Types.VARCHAR };

        return tipoDatos;
    }

    @Override
    public void insertarExpediente(DyctExpCredFisDTO expediente) throws SIATException {
        
        if (expediente != null) {

            Object[] objParam = getPaseParametros2(expediente);
            int[] typ = getTipoDatos2();

            try {
                jdbcTemplateDYC.update(SQLOracleDyC.EMITIRRESOLUCION_INSERTAR.toString(), objParam, typ);
            } catch (DataAccessException dae) {
                log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                        SQLOracleDyC.EMITIRRESOLUCION_INSERTAR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                        ExtractorUtil.ejecutar(expediente));
                throw new SIATException(dae);
            }
        }
         
    }

    public Object[] getPaseParametros(DyctExpedienteDTO expediente) {

        LobHandler lobHandler = new DefaultLobHandler();

        SqlLobValue sqlLVDatosCreditosFiscales = new SqlLobValue("", lobHandler);

         /**try {

            if (null != expediente.getDatosCredFis()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosCredFis());
                sqlLVDatosCreditosFiscales = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            } 

          } catch (IOException e) {
            log.error(ConstantesDyC.TEXTO_1_ERROR_DAO + e.getMessage());
        }*/

        return new Object[] { sqlLVDatosCreditosFiscales, expediente.getServicioDTO().getNumControl() };
    }

    public int[] getTipoDatos() {

        int[] tipoDatos = new int[] { Types.CLOB, Types.VARCHAR };

        return tipoDatos;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
