/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.archivo.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoDAO;
import mx.gob.sat.siat.dyc.dao.archivo.impl.mapper.DyctArchivoMapper;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctDocumentoMapper;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
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
 * @author Federico Chopin Gachuz
 * @date Enero 20, 2014
 *
 * */
@Repository(value = "dyctArchivoDAO")
public class DyctArchivoDAOImpl implements DyctArchivoDAO {
    
    private static final String CONSULTAR_DOCUMENTOS_X_NUMCONTROL = "select * from dyct_archivo where numcontrol=? and fechafin is null";
    
    private static final String CONSULTAR_DOCUMENTOS_X_NUMCONTROL_CONT = "select * from dyct_archivo where numcontrol=? and OCULTO_CONTRIBUYENTE = 0 and fechafin is null";
    

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctArchivoDAOImpl.class.getName());

    public DyctArchivoDAOImpl() {
        super();
    }

    @Override
    public List<DyctArchivoDTO> buscarDocsAdjuntos(String numControl) {

        try {

            List<DyctArchivoDTO> lDyctArchivoDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDOCSADJUNTOSPORNUMERODECONTROL.toString(),
                                      new Object[] { numControl, numControl }, new DyctArchivoMapper());

            return lDyctArchivoDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDOCSADJUNTOSPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

    }

    /**
     * 18-03-2016
     * @param dyctArchivoDTO
     * @return
     * @throws SIATException 
     */
    @Override
    public Integer insert(DyctArchivoDTO dyctArchivoDTO) throws SIATException{
        log.info("insert - Inicio");
        Integer idArchivo = jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDARCHIVO.NEXTVAL FROM DUAL", Integer.class);
        
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.DYCT_ARCHIVO_INSERT_GENERAL.toString(),
                                   new Object[] { idArchivo, 
                                                  dyctArchivoDTO.getNombreArchivo(), 
                                                  dyctArchivoDTO.getUrl(),
                                                  dyctArchivoDTO.getDescripcion(),
                                                  dyctArchivoDTO.getDycpServicioDTO().getNumControl(),
                                                  dyctArchivoDTO.getFechaRegistro(),
                                                  dyctArchivoDTO.getOcultoContribuyente()});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCT_ARCHIVO_INSERT_GENERAL + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctArchivoDTO));
            throw new SIATException(dae);
        }

        log.info("insert - Fin || idArchivo="+idArchivo);
        return idArchivo;
    }

    @Override
    public void insertarDocumento(DyctArchivoDTO dyctArchivoDTO) throws SIATException{

        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCONTRIBUYENTE_INSERTARDOCUMENTO,
                                   new Object[] { dyctArchivoDTO.getNombreArchivo(), dyctArchivoDTO.getUrl(),
                                                  dyctArchivoDTO.getDescripcion(),
                                                  dyctArchivoDTO.getDycpServicioDTO().getNumControl(),
                                                  dyctArchivoDTO.getOcultoContribuyente()
                                                  });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_INSERTARDOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctArchivoDTO));
            throw new SIATException(dae);
        }

    }

    @Override
    public void eliminarArchivo(DyctArchivoDTO dyctArchivoDTO) {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ELIMINAR_ARCHIVOS.toString(), new Object[] { dyctArchivoDTO.getIdArchivo() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ELIMINAR_ARCHIVOS +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctArchivoDTO));
            throw dae;
        }
    }
    
    @Override
    public void eliminarArchivoPorUrl(DyctArchivoDTO dyctArchivoDTO) {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ELIMINAR_ARCHIVOS_X_FECHAFIN.toString(), new Object[] { dyctArchivoDTO.getUrl(), dyctArchivoDTO.getNombreArchivo() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ELIMINAR_ARCHIVOS_X_FECHAFIN +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctArchivoDTO));
            throw dae;
        }
    }

    @Override
    public void insertarDocumento(final List<DyctArchivoDTO> archivos) {
        try {
            jdbcTemplateDYC.batchUpdate(SQLOracleDyC.CONSULTARCONTRIBUYENTE_INSERTARDOCUMENTO,
                                        new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        DyctArchivoDTO archivo = archivos.get(i);
                        ps.setString(ConstantesDyCNumerico.VALOR_1, archivo.getNombreArchivo());
                        ps.setString(ConstantesDyCNumerico.VALOR_2, archivo.getUrl());
                        ps.setString(ConstantesDyCNumerico.VALOR_3, archivo.getDescripcion());
                        ps.setString(ConstantesDyCNumerico.VALOR_4, archivo.getDycpServicioDTO().getNumControl());
                        ps.setDate(ConstantesDyCNumerico.VALOR_5, new java.sql.Date(archivo.getFechaRegistro().getTime()));
                        ps.setString(ConstantesDyCNumerico.VALOR_6, archivo.getOcultoContribuyente()+"");
                    }

                    @Override
                    public int getBatchSize() {
                        return archivos.size();
                    }
                });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.CONSULTARCONTRIBUYENTE_INSERTARDOCUMENTO);
            throw dae;
        }
    }

    @Override
    public Integer buscarArchivo(String numControl, String archivo) {
        return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_ARCHIVO_NUMCTRLYARCHIVO.toString(),
                                              new Object[] { numControl, archivo }, Integer.class);
    }

    @Override
    public void actualizarUrl(String numControl, String url) {
        String sql = SQLOracleDyC.UPDATE_ARCHIVO + url + SQLOracleDyC.WHERE_ARCHIVO;
        jdbcTemplateDYC.update(sql, new Object[] { numControl });
    }

    @Override
    public void createDocumentoEdoCta(DyctArchivoDTO dyctArchivoDTO) {

        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATE_DOCUMENTO_EDO_CTA,
                                   new Object[] { dyctArchivoDTO.getIdArchivo(), dyctArchivoDTO.getNombreArchivo(),
                                                  dyctArchivoDTO.getUrl(), dyctArchivoDTO.getDescripcion(),
                                                  dyctArchivoDTO.getDycpServicioDTO().getNumControl(),
                                                  dyctArchivoDTO.getOcultoContribuyente()});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CREATE_DOCUMENTO_EDO_CTA + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctArchivoDTO));
            throw dae;
        }

    }

    /**
     *Metodo que busca el archivo por numero de control
     * --LADP[Luis ALberto Dominguez Palomino]
     * @param numControl
     * @return
     * @throws SIATException
     */
    @Override
    public DyctArchivoDTO buscaArchivoXNumCtrl(String numControl) throws SIATException {
        DyctArchivoDTO archivo = null;
        try {
            archivo =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.BUSCA_ARCHIVO_X_NUMCTRL.toString(), new Object[] { numControl }, new DyctArchivoMapper());
        } catch (Exception siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.BUSCA_ARCHIVO_X_NUMCTRL + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
            throw new SIATException(siatE);
        }
        return archivo;
    }

    /**
     *Metodo que actualiza la informacion del archivo de estado de cuenta
     * --LADP[Luis Alberto Dominguez Palomino]
     * @param archivo
     * @param idArchivo
     */
    @Override
    public boolean actualizarArchivo(DyctArchivoDTO archivo, Integer idArchivo) throws SIATException{
        boolean correcto = false;
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_ARCHIVO_X_IDARCHIVO.toString(),
                                   new Object[] { archivo.getNombreArchivo(), archivo.getUrl(),
                                                  archivo.getDescripcion(), idArchivo });
            correcto = Boolean.TRUE;
        } catch (Exception siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZAR_ARCHIVO_X_IDARCHIVO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idArchivo);
            throw new SIATException(siatE);
        }
        return correcto;
    }

    @Override
    public List<DyctDocumentoDTO> listaDeArchivos() {
        String sql =
            "select dyct_d.* from dyct_documento dyct_d, dycp_solicitud dycp_s where dyct_d.numcontrol = dycp_s.numcontrol and dycp_s.rfccontribuyente = 'LURD470712C50'";
        return jdbcTemplateDYC.query(sql, new DyctDocumentoMapper());
    }

    @Override
    public void insertarDocumentoExpediente(DyctArchivoDTO dyctArchivoDTO) throws SIATException{

        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCONTRIBUYENTE_INSERTARDOCUMENTOEXPEDIENTE,
                                   new Object[] { dyctArchivoDTO.getNombreArchivo(), dyctArchivoDTO.getUrl(),
                                                  dyctArchivoDTO.getDescripcion(),
                                                  dyctArchivoDTO.getDycpServicioDTO().getNumControl(),
                                                  dyctArchivoDTO.getFechaRegistro(),
                                                  dyctArchivoDTO.getOcultoContribuyente()});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_INSERTARDOCUMENTOEXPEDIENTE + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctArchivoDTO));
            throw new SIATException(dae);
        }

    }

    @Override
    public void reactivaDocumentoExpediente(DyctArchivoDTO dyctArchivoDTO) throws SIATException{

        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCONTRIBUYENTE_REACTIVARDOCUMENTOEXPEDIENTE,
                                   new Object[] { dyctArchivoDTO.getUrl(),
                                                  dyctArchivoDTO.getNombreArchivo()});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_REACTIVARDOCUMENTOEXPEDIENTE + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctArchivoDTO));
            throw new SIATException(dae);
        }

    }
    /**
     * Obtiene los archivos adjuntos por el contribuyente a traves del numero de control
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyctArchivoDTO> getDocumentosXNumeroControl(String numControl) throws SIATException {
        List<DyctArchivoDTO> lDyctArchivoDTO = null;
       
        try {
            lDyctArchivoDTO = jdbcTemplateDYC.query(CONSULTAR_DOCUMENTOS_X_NUMCONTROL,
                                                    new Object[] { numControl }, 
                                                    new DyctArchivoMapper());
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + CONSULTAR_DOCUMENTOS_X_NUMCONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw new SIATException(dae);
        }
        return lDyctArchivoDTO;
    }
    
    @Override
    public List<DyctArchivoDTO> getDocumentosXNumeroControlCont(String numControl, boolean isEmpleado) throws SIATException {
        List<DyctArchivoDTO> lDyctArchivoDTO = null;
        String query = isEmpleado?CONSULTAR_DOCUMENTOS_X_NUMCONTROL:CONSULTAR_DOCUMENTOS_X_NUMCONTROL_CONT;
        
        try {
            lDyctArchivoDTO = jdbcTemplateDYC.query(query,
                                                    new Object[] { numControl }, 
                                                    new DyctArchivoMapper());
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + query +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw new SIATException(dae);
        }
        return lDyctArchivoDTO;
    }
}
