package mx.gob.sat.siat.dyc.dao.documento.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.CompensacionMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpServicioMapper;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DocsGestionMapper;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctDocumentoMapper;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctDocumentoVOMapper;
import mx.gob.sat.siat.dyc.dao.req.DyctReqInfoDAO;
import mx.gob.sat.siat.dyc.dao.seguimiento.DycaReasignacionDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.TipoTramiteMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.ContribuyenteMapper;
import mx.gob.sat.siat.dyc.domain.ReasignacionDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccEstadoDocDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.util.DyctDocumentoUtil;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleAprobarDoc;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DyctDocumentoVO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author J. Isaac Carbajal Bernal
 */
@Repository(value = "dyctDocumentoDAO")
public class DyctDocumentoDAOImpl implements DyctDocumentoDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    @Autowired(required = true)
    private DyctDocumentoUtil dyctDocumentoUtil;
    @Autowired
    private DyctReqInfoDAO dyctReqInfoDAO;
    @Autowired
    private DycaReasignacionDAO dycaReasignacionDAO;
    
    private static final String NUM_CONTROLDOC = " numControlDoc: ";
    private static final String ACTUALIZAR_CADENA_SELLO_FIRMA
            = "update dyct_documento set cadenaOriginal=?, selloDigital=?, firmaElectronica=? where NUMCONTROLDOC=?";
        private static final String ACTUALIZAR_FOLIONYV=
    "update dyct_documento set  FOLIONYV=? where NUMCONTROLDOC=?";
    
        private static final String ACTUALIZAR_FECHANOTI=
    "update DYCT_REQINFO set FECHANOTIFICACION = to_date(?,'dd/MM/yyyy') where NUMCONTROLDOC=?";
    
    private static final String CONTAR_X_IDPLANTILLA_ESTADODOC
            = "select COUNT (NUMCONTROL) from dyct_documento where idplantilla in (22,66,114,131,138) and idestadodoc=5 AND NUMCONTROL=?";
    private static final String LOG_FORMAT_GENERAL_STRING = "%s %s %s %s %s %s";
    private static final String CONSULTA_DOCUMENTOS_ACTIVOS="select NUMCONTROLDOC from DYCT_DOCUMENTO where idestadodoc=5 and NUMCONTROL=?";
    
    private static final String ORIGEN_FACULTADES = "fac";
    private static final String ORIGEN_DOCUMENTOS = "doc";
    private static final String ORIGEN_COMPENSACIONES = "comp";
    private static final String ORIGEN_RESOLSINDOCUMENTO = "nodoc";
    
    private static final String TABLA_FACULTADES = "DYCT_FACULTADES";
    private static final String TABLA_DOCUMENTOS = "DYCT_DOCUMENTO";
    private static final String TABLA_COMPENSACIONES = "DYCP_COMPENSACION";
    private static final String TABLA_RESOLSINDOCUMENTO = "DYCT_RESOLSINDOCUMENTO";
    private static final String FIN_LINEA = "<--";
    
    private Logger log = Logger.getLogger(DyctDocumentoDAOImpl.class.getName());
    
    @Override
    public List<DyctDocumentoDTO> selecXNumControl(String numControl) {
        List<DyctDocumentoDTO> lDyctDocumentoDTO = new ArrayList<DyctDocumentoDTO>();
        log.info(lDyctDocumentoDTO);
        try {
            lDyctDocumentoDTO
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_DYCT_DOCUMENTOREQ_X_NUMCONT.toString(),
                            new Object[]{numControl}, new DyctDocumentoMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_DYCT_DOCUMENTOREQ_X_NUMCONT + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + "numControl" + numControl);
        }
        
        return lDyctDocumentoDTO;
    }
    
    @Override
    public List<SolicitudAdministrarSolVO> selecXNumControlEstAprobado(String numControl) {
        List<SolicitudAdministrarSolVO> lSolicitudAdministrarSolVO = new ArrayList<SolicitudAdministrarSolVO>();
        log.info(lSolicitudAdministrarSolVO);
        try {
            lSolicitudAdministrarSolVO
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_X_NUMCONT_EST_APROBADO.toString(),
                            new Object[]{numControl}, new DocsGestionMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTARCONTRIBUYENTE_X_NUMCONT_EST_APROBADO + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + "numControl" + numControl);
        }
        
        return lSolicitudAdministrarSolVO;
    }
    
    @Override
    public void insertar(DyctDocumentoDTO documento) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARDOCUMENTO.toString(),
                    new Object[]{documento.getNumControlDoc(),
                        documento.getDyccTipoDocumentoDTO().getIdTipoDocumento(),
                        documento.getDycpServicioDTO().getNumControl(), documento.getUrl(),
                        documento.getNombreArchivo(),
                        documento.getDyccEstadoDocDTO().getIdEstadoDoc(),
                        documento.getDyccEstadoReqDTO().getIdEstadoReq(),
                        documento.getDyccMatDocumentosDTO().getIdPlantilla(),
                        documento.getDyccAprobadorDTO().getNumEmpleado()});
            
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARDOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(documento));
            throw new SIATException("Error al ejecutar DyctDocumentoDAOImpl.insertar", dae);
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARDOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(documento));
            throw new SIATException("Error al ejecutar DyctDocumentoDAOImpl.insertar", dae);
            
        }
    }
    
    @Override
    public void actualizarDocumentoAprobacion(Long numEmpleadoAprob, Long idDocumento) {
        try {
            
            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTO.toString(),
                    new Object[]{numEmpleadoAprob, idDocumento});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + " numEmpleadoAprob " + numEmpleadoAprob + " idDocumento " + idDocumento);
            throw dae;
        }
    }
    
    @Override
    public void actualizarEstadoDoc(Integer idEstadoDoc, String numControlDoc) {
        try {
            jdbcTemplateDYC.update("UPDATE DYCT_DOCUMENTO SET IDESTADODOC = ? WHERE NUMCONTROLDOC = ?",
                    new Object[]{idEstadoDoc, numControlDoc});
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + "UPDATE DYCT_DOCUMENTO SET IDESTADODOC = ? WHERE IDDOCUMENTOREQ = ?"
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + idEstadoDoc + " numControlDoc= " + numControlDoc);
        }
    }
    
    @Override
    public void insertarE(DyctDocumentoDTO documento) throws SIATException {
        try {
            int[] tipos
                    = new int[]{Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR,
                        Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP,
                        Types.INTEGER, Types.INTEGER, Types.VARCHAR};
            Integer numEmpleadoAprobador = null;
            if (documento.getDyccAprobadorDTO() != null) {
                numEmpleadoAprobador = documento.getDyccAprobadorDTO().getNumEmpleado();
            }
            
            Integer idTipoFirma = null;
            if (documento.getDyccTipoFirmaDTO() != null) {
                idTipoFirma = documento.getDyccTipoFirmaDTO().getIdTipoFirma();
            }
            
            Object[] parametros = new Object[ConstantesDyCNumerico.VALOR_14];
            parametros[ConstantesDyCNumerico.VALOR_0] = documento.getNumControlDoc();
            parametros[ConstantesDyCNumerico.VALOR_1] = documento.getDyccTipoDocumentoDTO().getIdTipoDocumento();
            parametros[ConstantesDyCNumerico.VALOR_2] = documento.getDycpServicioDTO().getNumControl();
            parametros[ConstantesDyCNumerico.VALOR_3] = documento.getUrl();
            parametros[ConstantesDyCNumerico.VALOR_4] = documento.getFechaRegistro();
            parametros[ConstantesDyCNumerico.VALOR_5] = documento.getNombreArchivo();
            parametros[ConstantesDyCNumerico.VALOR_6] = documento.getDyccEstadoDocDTO().getIdEstadoDoc();
            parametros[ConstantesDyCNumerico.VALOR_7] = documento.getDyccEstadoReqDTO().getIdEstadoReq();
            parametros[ConstantesDyCNumerico.VALOR_8] = documento.getDyccMatDocumentosDTO().getIdPlantilla();
            parametros[ConstantesDyCNumerico.VALOR_9] = documento.getFechaIniPlazoLegal();
            parametros[ConstantesDyCNumerico.VALOR_10] = documento.getFechaFinPlazoLegal();
            parametros[ConstantesDyCNumerico.VALOR_11] = idTipoFirma;
            parametros[ConstantesDyCNumerico.VALOR_12] = numEmpleadoAprobador;
            parametros[ConstantesDyCNumerico.VALOR_13] = documento.getFolioNyv();
            
            String sentSql
                    = " INSERT INTO DYCT_DOCUMENTO( NUMCONTROLDOC, IDTIPODOCUMENTO, NUMCONTROL, URL, FECHAREGISTRO, NOMBREARCHIVO, IDESTADODOC,"
                    + " IDESTADOREQ, IDPLANTILLA, FECHAINIPLAZOLEGAL, FECHAFINPLAZOLEGAL, IDTIPOFIRMA, NUMEMPLEADOAPROB, FOLIONYV)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            this.jdbcTemplateDYC.update(sentSql, parametros, tipos);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARDOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(documento));
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DyctDocumentoDTO> selecXServicioTipodocumento(DycpServicioDTO servicio,
            DyccTipoDocumentoDTO tipoDocumento) {
        String query = "SELECT * FROM DYCT_DOCUMENTO WHERE NUMCONTROL = ? AND IDTIPODOCUMENTO = ?";
        DyctDocumentoMapper mapper = new DyctDocumentoMapper();
        mapper.setServicio(servicio);
        mapper.setTipoDocumento(tipoDocumento);
        return this.jdbcTemplateDYC.query(query,
                new Object[]{servicio.getNumControl(), tipoDocumento.getIdTipoDocumento()},
                mapper);
    }
    
    @Override
    public List<DyctDocumentoDTO> ultimoDocumentoServicioTipoDoc(DyctDocumentoDTO documento) {
        
        DyctDocumentoMapper mapper = new DyctDocumentoMapper();
        mapper.setServicio(documento.getDycpServicioDTO());
        mapper.setTipoDocumento(documento.getDyccTipoDocumentoDTO());
        
        return jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_CC_TIPODOCUMENTO.toString(),
                new Object[]{documento.getDycpServicioDTO().getNumControl(),
                    documento.getDyccTipoDocumentoDTO().getIdTipoDocumento(),
                    documento.getDycpServicioDTO().getNumControl(),
                    documento.getDyccTipoDocumentoDTO().getIdTipoDocumento()},
                mapper);
    }
    
    @Override
    public List<DyctDocumentoDTO> seleccionar() {
        String query = " SELECT * FROM DYCT_DOCUMENTO";
        return this.jdbcTemplateDYC.query(query, new DyctDocumentoMapper());
    }
    
    @Override
    public List<DyctDocumentoDTO> selecUnionCompensacion() {
        String query
                = " SELECT * FROM DYCT_DOCUMENTO D, DYCP_COMPENSACION C, " + "               DYCT_CONTRIBUYENTE CONTE, DYCP_SERVICIO S "
                + " WHERE D.NUMCONTROL = C.NUMCONTROL " + " AND CONTE.NUMCONTROL = D.NUMCONTROL"
                + " AND S.NUMCONTROL = D.NUMCONTROL" + " AND D.IDESTADODOC = ? ";
        
        DyctDocumentoMapper mapper = new DyctDocumentoMapper();
        CompensacionMapper mapperCompensacion = new CompensacionMapper();
        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        ContribuyenteMapper mapperContribuyente = new ContribuyenteMapper();
        mapperServicio.setMapperContribuyente(mapperContribuyente);
        mapperServicio.setMapperCompensacion(mapperCompensacion);
        mapper.setMapperServicio(mapperServicio);
        return this.jdbcTemplateDYC.query(query, new Object[]{}, mapper);
    }

    /**
     * BUSCA EL DOCUMENTO A TRAVES DE SU NUMERO DE CONTROL Y LO DEPOSITA EN UN
     * OBJETO: DyctDocumentoDTO
     *
     * @param numControlDoc NUMERO DE CONTROL DEL DOCUMENTO
     * @return
     * @throws SIATException
     */
    @Override
    public DyctDocumentoDTO consultarXNumControlDoc(String numControlDoc) throws SIATException {
        DyctDocumentoDTO objetoDocumentoRequerimiento = null;
        try {
            objetoDocumentoRequerimiento
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DOCUMENTOXIDDOCUMENTO, new Object[]{numControlDoc},
                            new DyctDocumentoMapper());
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_DOCUMENTOXIDDOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControlDoc);
            throw new SIATException(dae);
        }
        return objetoDocumentoRequerimiento;
    }

    /**
     *
     * @param status
     * @param numControlDoc NUMERO DE CONTROL DEL DOCUMENTO
     * @throws SIATException
     */
    @Override
    public void actualizarStatusRequerimiento(Integer status, String numControlDoc) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_STATUSREQUERIMIENTO.toString(),
                    new Object[]{status, numControlDoc});
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAR_STATUSREQUERIMIENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + "status: "
                    + status + NUM_CONTROLDOC + numControlDoc + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }

    /**
     *
     * @param status
     * @param numControlDoc NUMERO DE CONTROL DEL DOCUMENTO
     * @throws SIATException
     */
    @Override
    public void actualizarStatusYFirmaXidDocumentoRequerimiento(Integer status, String numControlDoc,
            Integer idTipoFirma) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_STATUSDOCUMENTO.toString(),
                    new Object[]{status, idTipoFirma, numControlDoc});
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAR_STATUSDOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + "status: "
                    + status + "idTipoFirma: " + idTipoFirma + NUM_CONTROLDOC + numControlDoc
                    + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }

    /**
     *
     *
     * @param numControl NUMERO DE CONTROL DEL DOCUMENTO
     * @param idEstadoDoc ESTADO EN EL QUE SE ENCUENTRA EL DOCUMENTO
     * @param ids
     * @return
     * @throws SIATException
     */
    @Override
    public int buscarAutorizacionTotalOParcial(String numControl, Integer idEstadoDoc,
            String ids) throws SIATException {
        int numeroRegistros = 0;
        try {
            numeroRegistros
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_RESOLAUTORIZACIONPARCIALOTOTAL.toString()
                            + ids, new Object[]{numControl, idEstadoDoc}, Integer.class);
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_RESOLAUTORIZACIONPARCIALOTOTAL + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + "numControl: " + numControl + ", idEstadoDoc: " + idEstadoDoc + ", ids=" + ids
                    + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
        return numeroRegistros;
    }

    /**
     * DETERMINA SI EXISTE UN DOCUMENTO EN BASE A SU NUMERO DE CONTROL EN LOS
     * IDENTIFICADORES DE PLANTILLA ESPECIFICADOS
     *
     * @param numControl NUMERO DE CONTROL DEL DOCUMENTO
     * @param ids
     * @return
     * @throws SIATException
     */
    @Override
    public int buscarResolucionLiquidacionDevolucion(String numControl, String ids) throws SIATException {
        int numeroRegistros = 0;
        
        try {
            numeroRegistros
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_RESOLUCIONLIQUIDACION.toString() + ids,
                            new Object[]{numControl}, Integer.class);
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_RESOLUCIONLIQUIDACION + ConstantesDyC1.TEXTO_3_ERROR_DAO + "numControl: "
                    + numControl + ", ids: " + ids + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
        return numeroRegistros;
    }

    /**
     * BUSCA SI UN DOCUMENTO SE ENCUENTRA DECLARADO DENTRO DE LOS TIPOS DE
     * PLANTILLA ESPECIFICADOS
     *
     * @param numControlDoc NUMERO DE CONTROL DEL DOCUMENTO
     * @param ids IDENTIFICADORES DE PLANTILLA
     * @return
     * @throws SIATException
     */
    @Override
    public int buscarDocumentoEnPlantillas(String numControlDoc, String ids) throws SIATException {
        int numeroRegistros = 0;
        try {
            numeroRegistros
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DOCUMENTOENPLANTILLA.toString() + ids,
                            new Object[]{numControlDoc}, Integer.class);
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_DOCUMENTOENPLANTILLA + ConstantesDyC1.TEXTO_3_ERROR_DAO + NUM_CONTROLDOC
                    + numControlDoc + ", ids: " + ids + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
        log.info("JAHO - Con los ID: " + ids + ", y numControlDoc: " + numControlDoc + ". Encontró: "
                + numeroRegistros + " registros");
        
        return numeroRegistros;
    }

    /**
     * ACTUALIZA LA FECHA DE INICIO DEL PLAZO LEGAL AL DOCUMENTO
     *
     * @param numControlDoc NUMERO DE CONTROL DEL DOCUMENTO
     * @param fechaInicioPlazoLegal
     * @throws SIATException
     */
    @Override
    public void actualizarFechaInicioPlazoLegal(String numControlDoc,
            Date fechaInicioPlazoLegal) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_FECHAINICIOPLAZOLEGAL.toString(),
                    new Object[]{fechaInicioPlazoLegal, numControlDoc});
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAR_FECHAINICIOPLAZOLEGAL + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + NUM_CONTROLDOC + numControlDoc + ", fechaInicioPlazoLegal: " + fechaInicioPlazoLegal
                    + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }

    /**
     * COLOCA LA FECHA DE TERMINO DEL PLAZO LEGAL AL DOCUMENTO
     *
     * @param numControlDoc NUMERO DE CONTROL DEL DOCUMENTO
     * @param fechaFinPlazoLegal
     * @throws SIATException
     */
    @Override
    public void actualizarFechaFinPlazoLegal(String numControlDoc, Date fechaFinPlazoLegal) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_FECHAFINPLAZOLEGAL.toString(),
                    new Object[]{fechaFinPlazoLegal, numControlDoc});
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAR_FECHAFINPLAZOLEGAL + "; parametros=numControlDoc: " + numControlDoc
                    + ", fechaFinPlazoLegal: " + fechaFinPlazoLegal + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }

    /**
     * Regresa una lista de documentos de caso de compensacion con estado de
     * Desistida, Registrada e Improcedente *
     */
    @Override
    public List<DycpCompensacionDTO> buscarDocumentoXEstadoCompensacion() {
        return jdbcTemplateDYC.query(SQLOracleDyC.BUSCA_REG_ESTADOCOMPENSACION.toString(), new CompensacionMapper());
    }

    /**
     * Regresa una lista de documentos de solicitudes con estado de Desistida,
     * Negada, Autorizada y Pagada
     */
    @Override
    public List<DycpServicioDTO> buscarDocumentoXEstadoSolicitudes() {
        return jdbcTemplateDYC.query(SQLOracleDyC.BUSCA_REG_ESTADOSOLICITUD.toString(), new DycpServicioMapper());
    }

    /**
     * Busca si existe un documento por nombre de documento
     *
     * @param nombreDoc
     * @return
     */
    @Override
    public Integer buscarDocumento(String nombreDoc, String numControl) {
        return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DOCUMENTO_NUMCTRLYARCHIVO.toString(),
                new Object[]{numControl, nombreDoc}, Integer.class);
    }

    /**
     * Actualiza los atributos que contengan valor en el documentoDTO recibido.
     * NO actualiza a NULOS!
     *
     * @param documento : DyctDocumentoDTO
     * @throws SIATException
     * @return void
     */
    @Override
    public void actualizar(DyctDocumentoDTO documento) throws SIATException {
        try {
            dyctDocumentoUtil.setParams(new ArrayList<Object>());
            String sentencia
                    = " UPDATE DYCT_DOCUMENTO SET " + dyctDocumentoUtil.updateDocumento(documento) + " WHERE NUMCONTROLDOC = ?";
            log.info("sentencia ->\n \n \n " + sentencia);
            
            dyctDocumentoUtil.getParams().add(documento.getNumControlDoc());
            
            Object[] p = new Object[dyctDocumentoUtil.getParams().size()];
            int i = 0;
            
            for (Object o : dyctDocumentoUtil.getParams()) {
                log.debug("clase objecto params ->" + o.getClass().getName() + "<-");
                log.debug("objeto (valor) ->" + o + "<----" + i);
                p[i] = o;
                i++;
            }
            
            jdbcTemplateDYC.update(sentencia, p);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage());
            throw new SIATException(dae);
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage());
            throw new SIATException(dae);
        }
    }
    
    @Override
    public DyctDocumentoDTO encontrar(String numControlDoc) throws SIATException {
        try {
            return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DOCUMENTOXIDDOCUMENTO.toString(),
                    new Object[]{numControlDoc}, new DyctDocumentoMapper());
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_DOCUMENTOXIDDOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControlDoc);
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DyctDocumentoDTO> selecUnionCompensacionXEstadoDictaminador(DyccEstadoDocDTO estado,
            DyccDictaminadorDTO dictaminador) {
        
        ContribuyenteMapper mapperContribuyente = new ContribuyenteMapper();
        CompensacionMapper mapperCompensacion = new CompensacionMapper();
        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
        mapperServicio.setMapperContribuyente(mapperContribuyente);
        mapperServicio.setMapperCompensacion(mapperCompensacion);
        mapperServicio.setMapperTipoTramite(mapperTipoTramite);
        DyctDocumentoMapper mapper = new DyctDocumentoMapper();
        mapper.setMapperServicio(mapperServicio);
        return this.jdbcTemplateDYC.query(SQLOracleAprobarDoc.COMPENSACION_X_ESTADO,
                new Object[]{estado.getIdEstadoDoc(), dictaminador.getNumEmpleado()},
                mapper);
    }

    /**
     *
     */
    @Override
    public void actualizarUrl(String numControl, String url) {
        String sql = SQLOracleDyC.UPDATE_DOC.toString() + url + SQLOracleDyC.WHERE_DOC.toString();
        jdbcTemplateDYC.update(sql, new Object[]{numControl});
    }
    
    @Override
    public void actualizarDocumento(DyctDocumentoDTO documento) throws SIATException {
        
        try {
            final String query
                    = " UPDATE DYCT_DOCUMENTO SET NUMCONTROLDOC = ?, IDTIPODOCUMENTO = ?, URL = ?, FECHAREGISTRO = sysdate, NOMBREARCHIVO = ?, "
                    + " IDESTADODOC = ?, IDESTADOREQ = ?, IDPLANTILLA = ?, NUMEMPLEADOAPROB = ? WHERE NUMCONTROL = ? AND IDTIPODOCUMENTO = 5";
            
            jdbcTemplateDYC.update(query,
                    new Object[]{documento.getNumControlDoc(), documento.getDyccTipoDocumentoDTO().getIdTipoDocumento(),
                        documento.getUrl(), documento.getNombreArchivo(),
                        documento.getDyccEstadoDocDTO().getIdEstadoDoc(),
                        documento.getDyccEstadoReqDTO().getIdEstadoReq(),
                        documento.getDyccMatDocumentosDTO().getIdPlantilla(),
                        documento.getDyccAprobadorDTO().getNumEmpleado(),
                        documento.getDycpServicioDTO().getNumControl()});
            
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTORESOLUCION
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(documento));
            throw new SIATException("Error al ejecutar DyctDocumentoDAOImpl.actualizarDocumento", dae);
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTORESOLUCION
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(documento));
            throw new SIATException("Error al ejecutar DyctDocumentoDAOImpl.actualizarDocumento", dae);
            
        }
        
    }

    /**
     * <pre>
     * Busca todos los documentos que fueron enviados a NyV
     *
     * (Se buscan todos los documentos que esten aprobados y que tengan asignados un folio de NYV)
     * </pre>
     *
     * @param numeroRegistros es el n&uacute;mero de registros a ejecutar.
     * @param intervalo los tramites con fecha de resolucion dentro de los n-intervalo 
     * dias de intervalo anteriores a la fecha de ejecucion 
     *
     * @return Lista de documentos de la tabla DYCT_DOCUMENTO
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    public List<DyctDocumentoVO> buscarFolioNYV(int numeroRegistros, int intervalo) throws SIATException {
        log.error("buscarFolioNYV numeroRegistros-->" + numeroRegistros +FIN_LINEA);
        log.error("buscarFolioNYV intervalo-->" + intervalo +FIN_LINEA);
        List<DyctDocumentoVO> lista;
        try {
            lista = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_DATOSPARANYV.toString(), 
                    new Object[]{numeroRegistros, intervalo}, new DyctDocumentoVOMapper());
          log.error("numeroRegistros lista -->" + lista.size() + FIN_LINEA);
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_DATOSPARANYV);
            throw new SIATException(dae);
        }
        return lista;
    }

    /**
     * Actualiza el folio NYV cuando un documento es aprobado y el tipo de firma
     * es fiel.
     *
     * @param folioNYV
     * @param numControlDoc
     * @throws SIATException
     */
    @Override
    public void actualizarFolioNYV(String folioNYV, String numControlDoc) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_FOLIONYV.toString(),
                    new Object[]{folioNYV, numControlDoc});
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAR_FOLIONYV);
            throw new SIATException(dae);
        }
    }

    /**
     * Cambia el nombre del documento de *.docx a *.pdf
     *
     * @param nombreDocumentoPdf nombre del documento a actualizar (con
     * terminacion pdf)
     * @param numControlDoc Numero de control de documento
     */
    @Override
    public void actualizarDocumentoAPdf(String nombreDocumentoPdf, String numControlDoc) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_NOMBREDOCUMENTOAPDF.toString(),
                    new Object[]{nombreDocumentoPdf, numControlDoc});
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAR_NOMBREDOCUMENTOAPDF + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + "nombreDocumentoPdf: " + nombreDocumentoPdf + NUM_CONTROLDOC + numControlDoc);
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DyctDocumentoDTO> consultarDocumentoAprobador(DyccAprobadorDTO aprobador) throws SIATException {
        List<DyctDocumentoDTO> lista;
        try {
            lista
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_DOCUMENTO_APROBADORES, new Object[]{aprobador.getNumEmpleado()},
                            new DyctDocumentoMapper());
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_DOCUMENTO_APROBADORES + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(aprobador));
            throw new SIATException(dae);
        }
        return lista;
    }

    /**
     * Busca el ultimo documento aprobado para poder dar un seguimiento cuando
     * se da un rechazo por parte de la TESOFE, este paso permite asignar un
     * documento al ultimo aprobador involucrado dentro de la solicitud.
     *
     * @param numControl
     * @return
     */
    @Override
    public DyctDocumentoDTO encontrarUltimoRegistroXNumControl(String numControl) throws SIATException {
        DyctDocumentoDTO objetoDocumentoRequerimiento = null;
        try {
            objetoDocumentoRequerimiento
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTAR_ULTIMODOCUMENTOAPROBADO.toString(),
                            new Object[]{numControl}, new DyctDocumentoMapper());
            
        } catch (EmptyResultDataAccessException e) {
            log.info("No se obtiene informacion: " + e.getMessage());
            return null;
        } catch (DataAccessException ex) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTAR_ULTIMODOCUMENTOAPROBADO + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl, ex);
            throw new SIATException(null, ex.getMessage(), ex);
        }
        return objetoDocumentoRequerimiento;
    }

    /**
     * Actualiza dentro del registro del documento la cadena original, sello
     * digital y firma digital.
     *
     * @param numControlDoc N&uacute;mero de control de documento a acutalizar.
     * @param cadena Cadena Original
     * @param sello Sello digital
     * @param firma Firma ditigal
     * @throws SIATException
     */
    @Override
    public void actualizarCadenaSelloYFirma(String numControlDoc, String cadena, String sello,
            String firma) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_CADENA_SELLO_FIRMA,
                    new Object[]{cadena, sello, firma, numControlDoc});
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + ACTUALIZAR_CADENA_SELLO_FIRMA + ConstantesDyC1.TEXTO_3_ERROR_DAO + ". Cadena: " + cadena
                    + ". Sello: " + sello + ". Firma: " + firma + NUM_CONTROLDOC + numControlDoc);
            throw new SIATException(dae);
        }
    }
    @Override
    public void actualizarFolioNYVFechaNoti(String numControlDoc, String numControl, String folio, String fecha) throws SIATException {
        try {
            
        
            jdbcTemplateDYC.update(ACTUALIZAR_FOLIONYV,
                new Object[]{ folio,  numControlDoc});
            
        jdbcTemplateDYC.update(ACTUALIZAR_FECHANOTI ,
                new Object[]{fecha,   numControlDoc});
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + ACTUALIZAR_FOLIONYV + ACTUALIZAR_FECHANOTI + ConstantesDyC1.TEXTO_3_ERROR_DAO + ". Fecha Notificacion: " + fecha
                    + ". Folio NyV: " + folio + NUM_CONTROLDOC + numControlDoc);
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DyctDocumentoDTO> buscaDocumentosAprobador(Integer numempleadoAprob) {
        List<DyctDocumentoDTO> lista = null;
        String sql = "SELECT DOC.* FROM DYCT_DOCUMENTO DOC \n"
                + "INNER JOIN DYCP_SOLICITUD DS ON DS.NUMCONTROL = DOC.NUMCONTROL \n"
                + "WHERE DOC.IDESTADODOC IN (5, 10, 11) AND DOC.NUMEMPLEADOAPROB = ? ";
        try {
            lista = this.jdbcTemplateDYC.query(sql, new Object[]{numempleadoAprob}, new DyctDocumentoMapper());
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + numempleadoAprob);
        }
        return lista;
    }
    
    @Override
    public void actualizarNumEmpleadoAprob(Integer numEmpleadoAprob, String numControlDoc) throws SIATException {
        String sql = "UPDATE DYCT_DOCUMENTO SET NUMEMPLEADOAPROB = ? WHERE NUMCONTROLDOC = ?";
        try {
            this.jdbcTemplateDYC.update(sql, new Object[]{numEmpleadoAprob, numControlDoc});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + numEmpleadoAprob + "y" + numControlDoc);
            throw new SIATException(dae);
        }
    }
    
    public void setDyctDocumentoUtil(DyctDocumentoUtil dyctDocumentoUtil) {
        this.dyctDocumentoUtil = dyctDocumentoUtil;
    }
    
    public DyctDocumentoUtil getDyctDocumentoUtil() {
        return dyctDocumentoUtil;
    }

    /**
     * Cuenta las plantillas generadas anteriormente (ya sean 22 o 66) para
     * validar que no se envie la misma plantilla mas de una vez.
     *
     * @param numControl Numero de control sobre el cual se buscan las
     * plantillas.
     * @return
     * @throws SIATException
     */
    @Override
    public Integer contarPlantillasGeneradas22o66(String numControl) throws SIATException {
        Integer total = 0;
        
        try {
            total
                    = jdbcTemplateDYC.queryForObject(CONTAR_X_IDPLANTILLA_ESTADODOC, new Object[]{numControl}, Integer.class);
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + CONTAR_X_IDPLANTILLA_ESTADODOC + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl: "
                    + numControl);
            throw new SIATException(dae);
        }
        return total;
    }
    
    @Override
    public Integer actualizarEstadoreq(DyctDocumentoDTO documento) throws SIATException {
        String sql = "UPDATE DYCT_DOCUMENTO SET IDESTADOREQ = ? WHERE NUMCONTROLDOC = ? ";
        try {
            return this.jdbcTemplateDYC.update(sql,
                    new Object[]{documento.getDyccEstadoReqDTO().getIdEstadoReq(), documento.getNumControlDoc()});
        } catch (DataAccessException dae) {
            log.error("ocurrió un error al intentar actualizar el estadoReq del documento ->"
                    + documento.getNumControlDoc());
            ManejadorLogException.getTraceError(dae);
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DyctDocumentoDTO> buscaDocumentosParaHistorico(Integer maximo) throws SIATException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM dyct_documento doc ");
        sql.append("INNER JOIN dycp_servicio serv ON doc.numcontrol = serv.numcontrol ");
        sql.append("INNER JOIN dycp_solicitud sol ON sol.numcontrol = serv.numcontrol  ");
        sql.append("WHERE sol.idestadosolicitud in (5, 9, 13))");
        sql.append("WHERE rownum <= ?");
        
        try {
            return jdbcTemplateDYC.query(sql.toString(), new Object[]{maximo}, new DyctDocumentoMapper());
        } catch (DataAccessException dae) {
            log.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                    ConstantesDyC1.TEXTO_2_ERROR_DAO, sql.toString(), ConstantesDyC1.TEXTO_3_ERROR_DAO,
                    maximo));
            
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DyctDocumentoDTO> consultaNumeroDoc(String numControl) throws SIATException {
        List<DyctDocumentoDTO> listaTmp1;
        List<DyctDocumentoDTO> listaTmp2;
        List<DyctDocumentoDTO> lista = new ArrayList<DyctDocumentoDTO>();
        try {
            listaTmp1
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_NUMERO_DOCUMENTO2.toString(), new Object[]{numControl},
                            new DyctDocumentoMapper());
            
            listaTmp2
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_NUMERO_DOCUMENTO.toString(), new Object[]{numControl},
                            new DyctDocumentoMapper());
            lista.addAll(armaListaAcusesReimpresion(listaTmp2, listaTmp1));
            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_NUMERO_DOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(numControl));
            throw new SIATException(dae);
        }
        return lista;
    }
    
    @Override
    public void reasignacionManualAprobador(String numControl, Integer numEmpleado, Integer empleadoResponsable) throws SIATException {
        
        List<String> listaOrigenAprobadorTramite
                = jdbcTemplateDYC.query(getConsultaBusquedaOrigenAprobador(), new Object[]{numControl, numControl,
            numControl,numControl},
                        getProcesadorConsultaOrigenAprobador());
        
        procesaListaOrigenAprobadorTramite(listaOrigenAprobadorTramite, numControl, numEmpleado,empleadoResponsable);
    }
    
    private String getConsultaBusquedaOrigenAprobador() {
        StringBuilder consultaBusquedaAprobadorTramite = new StringBuilder();
        
        consultaBusquedaAprobadorTramite.append("SELECT 'fac' AS ori FROM DYCT_FACULTADES WHERE NUMCONTROL = ? ").append(" UNION ").append("SELECT 'doc' AS ori FROM DYCT_DOCUMENTO WHERE NUMCONTROL = ? ").append(" UNION ").append("SELECT 'comp' AS ori FROM DYCP_COMPENSACION WHERE NUMCONTROL = ? ")
                .append("union select 'nodoc' AS ori from dyct_resolsindocumento where numcontrol = ?");
        
        return consultaBusquedaAprobadorTramite.toString();
    }
    
    private String getMensajeTramiteSinOrigenAprobador(String numControl) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("El tramite ").append(numControl).append(" no tiene asociado ningun aprobador");
        
        return mensaje.toString();
    }
    
    private RowMapper<String> getProcesadorConsultaOrigenAprobador() {
        return new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("ori");
            }
        };
    }
    private RowMapper<String> obtenerNumeroControDoc() {
        return new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("NUMCONTROLDOC");
            }
        };
    }
    private String getTablaOrigenAprobador(String origen) {
        
        if (origen.equals(ORIGEN_FACULTADES)) {
            return TABLA_FACULTADES;
        }
        
        if (origen.equals(ORIGEN_DOCUMENTOS)) {
            return TABLA_DOCUMENTOS;
        }
        
        if (origen.equals(ORIGEN_COMPENSACIONES)) {
            return TABLA_COMPENSACIONES;
        }
        if (origen.equals(ORIGEN_RESOLSINDOCUMENTO)) {
            return TABLA_RESOLSINDOCUMENTO;
        }
        
        return null;
    }
    private String obtenerRefereciaReasignacion(String origen, String numeroControl){
        if (origen.equals(ORIGEN_FACULTADES)) {
            return numeroControl;
        }
        
        if (origen.equals(ORIGEN_DOCUMENTOS)) {
            String resultado=obtieneReferenciaDocumento(numeroControl);
            return (resultado!=null)?resultado:numeroControl;
        }
        
        if (origen.equals(ORIGEN_COMPENSACIONES)) {
            return numeroControl;
        }
        if (origen.equals(ORIGEN_RESOLSINDOCUMENTO)) {
            return numeroControl;
        }
        return null;
    }
    private void actualizaRegistroOrigenAprobador(String tablaOrigen, String numControl,
            Integer numEmpleado) throws SIATException {
        
        String sqlActualizaTramite = "UPDATE %s SET NUMEMPLEADOAPROB = ? WHERE NUMCONTROL = ?";
        try {
            sqlActualizaTramite = String.format(sqlActualizaTramite, tablaOrigen);
            
            this.jdbcTemplateDYC.update(sqlActualizaTramite, new Object[]{numEmpleado, numControl});
            
        } catch (DataAccessException dae) {
            log.error(getMensajeErrorActualizacionOrigenAprobador(dae, sqlActualizaTramite, numControl, numEmpleado));
            log.error(sqlActualizaTramite);
            throw new SIATException(dae);
        }
        
    }
    
    private String getMensajeTramiteActualizado(String numControl, Integer numEmpleado) {
        StringBuilder mensaje = new StringBuilder();
        
        mensaje.append("Tramite ").append(numControl).append(" asignado a ").append(numEmpleado);
        
        return mensaje.toString();
    }
    
    private String getMensajeErrorActualizacionOrigenAprobador(DataAccessException dae, String sqlActualizaTramite,
            String numControl, Integer numEmpleado) {
        StringBuilder mensaje = new StringBuilder();
        
        mensaje.append(ConstantesDyC1.TEXTO_1_ERROR_DAO).append(dae.getMessage()).append(ConstantesDyC1.TEXTO_2_ERROR_DAO).append(sqlActualizaTramite).append(ConstantesDyC1.TEXTO_3_ERROR_DAO).append(numEmpleado).append("y").append(numControl);
        
        return mensaje.toString();
    }
    
    private void procesaListaOrigenAprobadorTramite(List<String> listaOrigenAprobadorTramite, String numControl,
            Integer numEmpleado, Integer empleadoResponsable) throws SIATException {
        String tablaOrigen;
        String referencia;
        if (!listaOrigenAprobadorTramite.isEmpty()) {
            
            for (String origen : listaOrigenAprobadorTramite) {
                
                tablaOrigen = getTablaOrigenAprobador(origen);
                if (tablaOrigen != null) {
                    actualizaRegistroOrigenAprobador(tablaOrigen, numControl, numEmpleado);
                    referencia=obtenerRefereciaReasignacion(origen, numControl);
                    ReasignacionDTO reasignacion= new ReasignacionDTO();
                    reasignacion.setFechaFin(null);
                    reasignacion.setNumcontrol(numControl);
                    reasignacion.setOrigen(referencia);
                    reasignacion.setEmpleadoAsignado(numEmpleado);
                    reasignacion.setEmpleadoResponsable(empleadoResponsable);
                    try {
                        dycaReasignacionDAO.insertarReasignacion(reasignacion);
                    } catch(Exception e){
                         log.error("Error al guaradar la fecha de reasignacion en aprobador",e);
                    }
                }
            }
            log.info(getMensajeTramiteActualizado(numControl, numEmpleado));
            
        } else {
            log.info(getMensajeTramiteSinOrigenAprobador(numControl));
        }
    }
    
    @Override
    public DyctDocumentoDTO consultarDocumentoSolventarCtaClabe(String numControl) throws SIATException {
        
        DyctDocumentoDTO documentoCuentaClabe = null;
        
        try {
            documentoCuentaClabe
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DOC_SOLVENTAR_CTACLABE.toString(), new Object[]{numControl},
                            new DyctDocumentoMapper());
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTA_DOC_SOLVENTAR_CTACLABE + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
            throw new SIATException(dae);
        }
        
        return documentoCuentaClabe;
    }
    
    private List<DyctDocumentoDTO> armaListaAcusesReimpresion(List<DyctDocumentoDTO> lstOldAcuses, List<DyctDocumentoDTO> lstNuevosAcuses) {
        List<DyctDocumentoDTO> lstAcusesReimpresion = new ArrayList<DyctDocumentoDTO>();
        List<DyctDocumentoDTO> lstTemporal = new ArrayList<DyctDocumentoDTO>();
        
        for (DyctDocumentoDTO docNuevo : lstNuevosAcuses) {
            for (DyctDocumentoDTO docViejo : lstOldAcuses) {
                List<DyctReqInfoDTO> reqInfo = dyctReqInfoDAO.buscarReqInfoReimpresion(docViejo.getNumControlDoc());
                if (docNuevo.getNumControlDoc().equals(docViejo.getNumControlDoc()) || (reqInfo == null || reqInfo.isEmpty())) {
                    lstTemporal.add(docViejo);
                }
            }
        }
        
        lstOldAcuses.removeAll(lstTemporal);
        lstAcusesReimpresion.addAll(lstOldAcuses);
        lstAcusesReimpresion.addAll(lstNuevosAcuses);
        
        return lstAcusesReimpresion;
    }
    
    @Override
    public boolean agregaMarcaFechaNotif(String folioNyV, Integer marcaFechaNotif) throws SIATException {
        Integer result = 0;
        String sql = "UPDATE DYCT_DOCUMENTO SET MARCA_FECHANOTIF = ? WHERE FOLIONYV = ? ";
        try {
            this.jdbcTemplateDYC.update(sql,
                    new Object[]{marcaFechaNotif, folioNyV});
        } catch (DataAccessException dae) {
            log.error("ocurrió un error al intentar actualizar el estadoReq del documento con folio ->"
                    + folioNyV);
            ManejadorLogException.getTraceError(dae);
            throw new SIATException(dae);
        }
        return result==ConstantesDyCNumerico.VALOR_1;
    }
    
    @Override
    public void agregaMarcaFechaNotificacionByNumControlDoc(String numcontrolDoc, Integer marcaFechaNotif) throws SIATException {

        try {
            log.error("agregaMarcaFechaNotificacionByNumControlDoc: -->" + numcontrolDoc + "<--  marcaFechaNotif:-->" + marcaFechaNotif + FIN_LINEA);
            this.jdbcTemplateDYC.update(SQLOracleDyC.AGREGARMARCA_FECHANOTIF_BY_NUMCONTROLDOC.toString(),
                    new Object[]{marcaFechaNotif, numcontrolDoc});
        } catch (DataAccessException dae) {
            log.error("ocurrió un error al intentar actualizar la marca de fecha de notificacion del documento con folio ->"
                    + numcontrolDoc);
            ManejadorLogException.getTraceError(dae);
            throw new SIATException(dae);
        }
    }
    
    private String obtieneReferenciaDocumento(String numeroControl) {
        List<String> numerosDocumento;
        try{       
               numerosDocumento= jdbcTemplateDYC.query(CONSULTA_DOCUMENTOS_ACTIVOS, new Object[]{numeroControl},
                    obtenerNumeroControDoc());
        } catch( Exception e){
            log.error("error al obtener la referencia  numcontroldoc al reasignar",e);
            return null;
        }
       if(!numerosDocumento.isEmpty()){
            return numerosDocumento.get(ConstantesDyCNumerico.VALOR_0);
            
        }
        return null;
    }
    
       
    @Override
    public void dyctDocumentoupdateIdEstadoReq(final int idEstadoReq, final String numControl) throws SIATException {
        
        try {
                jdbcTemplateDYC.update(SQLOracleDyC.UPTADE_DYCP_DOCUMENTO_IDESTADOREQ_WHERE_NUMCONTROL, new Object[] { idEstadoReq, numControl});
        } catch (Exception dae) {
            log.error(SQLOracleDyC.UPTADE_DYCP_DOCUMENTO_IDESTADOREQ_WHERE_NUMCONTROL  + dae.getMessage() + " numControl: "
                    + numControl + "idEstadoReq " + idEstadoReq);
            throw new SIATException(dae);
        }
        
    }
    
    @Override
    public void dyctDocumentoupdateIdEstadoReqDic(final int idEstadoReq, final String numControl) throws SIATException {
        
        try {
                jdbcTemplateDYC.update(SQLOracleDyC.UPTADE_DYCP_DOCUMENTO_DIC_IDESTADOREQ_WHERE_NUMCONTROL, new Object[] { idEstadoReq, numControl});
        } catch (Exception dae) {
            log.error(SQLOracleDyC.UPTADE_DYCP_DOCUMENTO_IDESTADOREQ_WHERE_NUMCONTROL  + dae.getMessage() + " numControl: "
                    + numControl + "idEstadoReq " + idEstadoReq);
            throw new SIATException(dae);
        }
        
    }
    
}
