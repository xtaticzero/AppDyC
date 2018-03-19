/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.SolventarRequerimientoDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper.CadenaCompensacionMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper.CadenaSolicitudMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper.ConsultaAnexoReqMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper.ConsultaDocumentoReqMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper.ConsultaEstatusReqMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper.IdDocConsecutivoMapper;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.AnexoRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaCompensacionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaSolicitudDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.DocRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.EstatusRequerimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.NotaExpedienteDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
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
 * @author David Guerrero Reyes
 * @since 19/11/2013
 */
@Repository(value = "solventarRequerimientoDAO")
public class SolventarRequerimientoDAOImpl implements SolventarRequerimientoDAO {

    private static final String APELLIDO_PATERNO
            = "       EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '|| \n";

    private static final String CONSULTAR_ESTADO_REQ_COMPENSACION
            = "SELECT reqinfo.fechanotificacion, req.idtipodocumento,\n"
            + "  req.idestadoreq,\n"
            + "  req.numcontrol,\n"
            + "  req.numcontroldoc,\n"
            + "  servicio.idtipotramite,\n"
            + "  estreq.descripcion\n"
            + "FROM DYCT_DOCUMENTO req\n"
            + "INNER JOIN dycc_estadoreq estreq  ON req.idestadoreq = estreq.idestadoreq\n"
            + "INNER JOIN dycp_compensacion sol  ON req.numcontrol = sol.numcontrol\n"
            + "INNER JOIN dyct_reqinfo reqinfo   ON req.numcontroldoc    = reqinfo.numcontroldoc\n"
            + "INNER JOIN dycp_servicio servicio ON sol.numcontrol = servicio.numcontrol\n"
            + "WHERE req.numcontroldoc    = ? \n"
            + "AND REQ.IDTIPODOCUMENTO =\n"
            + "  (SELECT MAX(IDTIPODOCUMENTO) tipoDoc\n"
            + "  FROM DYCT_DOCUMENTO\n"
            + "  WHERE numcontroldoc     = ? \n"
            + "  AND IDTIPODOCUMENTO IN (1,2)\n"
            + "  )";

    private static final String CONSULTAR_DATOS_COMPENSACION_PARA_CADENA
            = "SELECT d.descripcion AS descripcionTramiteICEPDestino,\n"
            + "F.descripcion AS descripcionConceptoICEPDestino,\n"
            + "H.descripcion AS descripcionPeriodoICEPDestino, \n"
            + "E.IDEJERCICIO, J.FECHASOLVENTACION, b.descripcion as tipoDeRequerimiento, A.NUMCONTROL,  \n"
            + "E.RFC, decode(EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||\n"
            + APELLIDO_PATERNO
            + "       EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno'), '  ',\n"
            + "       EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial'), \n"
            + "       EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||\n"
            + APELLIDO_PATERNO
            + "       EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as nombreORazonSocial \n"
            + "FROM DYCP_COMPENSACION A  \n"
            + "INNER JOIN DYCC_ESTADOCOMP    B ON (A.IDESTADOCOMP=B.IDESTADOCOMP) \n"
            + "inner join dycp_servicio      c on (a.numcontrol=c.numcontrol) \n"
            + "inner join dycc_tipoTramite   d on (c.idtipotramite=d.idtipotramite) \n"
            + "INNER JOIN DYCT_SALDOICEP     E ON (E.IDsaldoicep=A.IDSALDOICEPDESTINO) \n"
            + "INNER JOIN DYCC_CONCEPTO      F ON (F.IDCONCEPTO=E.IDCONCEPTO) \n"
            + "INNER JOIN DYCC_IMPUESTO      G ON (G.IDIMPUESTO=F.IDIMPUESTO) \n"
            + "INNER JOIN DYCC_PERIODO       H ON (H.IDPERIODO=E.IDPERIODO) \n"
            + "INNER join dyct_documento     I ON (i.numControl = a.numControl)\n"
            + "INNER JOIN DYCT_REQINFO       J ON (I.NUMCONTROLDOC = J.NUMCONTROLDOC)\n"
            + "INNER JOIN DYCT_contribuyente k ON (a.NUMCONTROL = k.NUMCONTROL)\n"
            + "INNER join dycc_tipodocumento L ON (i.idtipodocumento = l.idtipodocumento)\n"
            + "WHERE a.NUMCONTROL=? and i.numcontroldoc=?";

    private static final String CONSULTAR_DATOS_SOLICITUD_PARA_CADENA
            = "select b.idTipoTramite, c.descripcion AS origenTramite, e.descripcion AS concepto, \n"
            + "       G.descripcion AS periodo,  D.IDEJERCICIO,  J.FECHASOLVENTACION, \n"
            + "       a.numControl, D.rfc, l.descripcion as TIPODEREQUERIMIENTO,  \n"
            + "       decode(EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||\n"
            + APELLIDO_PATERNO
            + "       EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno'), '  ',\n"
            + "       EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial'), \n"
            + "       EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||\n"
            + APELLIDO_PATERNO
            + "       EXTRACTVALUE(k.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as nombreORazonSocial \n"
            + "from dycp_solicitud a \n"
            + "inner join dycp_servicio      b on (a.numControl = b.numControl) \n"
            + "inner join dycc_tipoTramite   c on (c.idTipoTramite = b.idTipoTramite)\n"
            + "INNER JOIN DYCT_SALDOICEP     D ON (D.IDSALDOICEP = A.IDSALDOICEP)\n"
            + "INNER JOIN DYCC_CONCEPTO      E ON (E.IDCONCEPTO = D.IDCONCEPTO) \n"
            + "INNER JOIN dycc_impuesto      F ON (F.IDIMPUESTO = E.IDIMPUESTO) \n"
            + "INNER JOIN DYCC_PERIODO       G ON (G.IDPERIODO = D.IDPERIODO)   \n"
            + "INNER JOIN dycc_estadosol     H ON (H.idestadosolicitud = A.idestadosolicitud) \n"
            + "INNER join dyct_documento     i on (i.numControl = a.numControl) \n"
            + "INNER JOIN DYCT_REQINFO       J ON (I.NUMCONTROLDOC = J.NUMCONTROLDOC)\n"
            + "INNER JOIN DYCT_contribuyente k ON (a.NUMCONTROL = k.NUMCONTROL)\n"
            + "inner join dycc_tipodocumento l on (i.idtipodocumento=l.idtipodocumento) \n"
            + "WHERE a.numcontrol=? and i.numcontrolDoc=?";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(SolventarRequerimientoDAOImpl.class);

    public SolventarRequerimientoDAOImpl() {
        super();
    }

    private static final String ACTUALIZAR_STATUS_COMPENSACION
            = "UPDATE DYCP_COMPENSACION SET idestadocomp = ? WHERE numcontrol = ?";
    private static final String CONSULTA_NUMERO_DE_DOCUMENTO
            = "select max(numeroarchivo)+1 from DYCT_OTRAARCHIVO where idotrainforeq=?";

    @Override
    public EstatusRequerimientoDTO consultaEstatusReq(String numeroControlDoc, String numeroControl) {
        EstatusRequerimientoDTO estatusReq = new EstatusRequerimientoDTO();
        String query = "";
        try {
            if (numeroControl.startsWith("CC") || numeroControl.startsWith("AC")) {
                query = CONSULTAR_ESTADO_REQ_COMPENSACION;
            } else {
                query = SQLOracleDyC.CONSULTAESTADOREQ.toString();
            }
            estatusReq
                    = jdbcTemplateDYC.queryForObject(query, new Object[]{numeroControlDoc, numeroControlDoc}, new ConsultaEstatusReqMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTAESTADOREQ.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeroControlDoc);
        }

        return estatusReq;
    }

    /**
     * Consulta los documentos requeridos que son necesarios para solventar el
     * requerimiento.
     *
     * @param numeroControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    @Override
    public List<DocRequeridoDTO> consultaDocumentosReq(String numeroControl,
            String numControlDoc) throws SIATException {
        List<DocRequeridoDTO> lstDocRequerido = null;
        try {
            lstDocRequerido
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTADOCUMENTOSREQUERIDOSASOLVENTAR.toString(),
                            new Object[]{numeroControl, numControlDoc, numeroControl,
                                numControlDoc}, new ConsultaDocumentoReqMapper());
        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTADOCUMENTOSREQUERIDOSASOLVENTAR.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeroControl);
            throw new SIATException(e);
        }
        return lstDocRequerido;
    }

    /**
     * Consulta los anexos requeridos necesarios para solventar el
     * requerimiento.
     *
     * @param numeroControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    @Override
    public List<AnexoRequeridoDTO> consultaAnexoReq(String numeroControl, String numControlDoc) throws SIATException {
        List<AnexoRequeridoDTO> lstAnexoRequerido = null;
        try {
            lstAnexoRequerido
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTAANEXOSREQUERIDOSASOLVENTAR.toString(), new Object[]{numeroControl,
                numControlDoc},
                            new ConsultaAnexoReqMapper());
        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTAANEXOSREQUERIDOSASOLVENTAR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + numeroControl);
            throw new SIATException(e);
        }
        return lstAnexoRequerido;
    }

    /**
     * Genera un id de documento al momento de solventar el requerimiento por
     * cada uno de los documentos que se suben.
     *
     * @return
     */
    @Override
    public int getIdDocumento() throws SIATException {
        Integer idDocumento = null;
        try {
            idDocumento
                    = (Integer) jdbcTemplateDYC.queryForObject(SQLOracleDyC.GETIDDOCUMENTO.toString(), new Object[]{},
                            new IdDocConsecutivoMapper());
        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.GETIDDOCUMENTO.toString());
            throw new SIATException(e);
        }
        return idDocumento;
    }

    /**
     * Genera un ID de cocumento para cada documento que es anexado al momento
     * de solventar una devolución.
     *
     * @return
     * @throws SIATException
     */
    @Override
    public int getIdAnexo() throws SIATException {
        Integer idDocumento = null;
        try {
            idDocumento
                    = (Integer) jdbcTemplateDYC.queryForObject(SQLOracleDyC.GETIDANEXO.toString(), new Object[]{}, new IdDocConsecutivoMapper());
        } catch (Exception e) {
            e.getMessage();
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.GETIDANEXO.toString());
            throw new SIATException(e);
        }
        return idDocumento;
    }

    /**
     * Actualiza el nombre y la ruta del archivo anexo a partir de: - El número
     * de control. - El id del anexo. - El id del tipo de trámite.
     *
     * @param dyctAnexoReqDTO
     * @throws SIATException
     */
    @Override
    public void actualizaAnexo(DyctAnexoReqDTO dyctAnexoReqDTO) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAANEXOREQUERIMIENTO.toString(),
                    new Object[]{dyctAnexoReqDTO.getUrl(), dyctAnexoReqDTO.getNombreArchivo(),
                        dyctAnexoReqDTO.getDyctReqInfoDTO().getNumControlDoc(),
                        dyctAnexoReqDTO.getDyccAnexoTramiteDTO().getDyccMatrizAnexosDTO().getIdAnexo(),
                        dyctAnexoReqDTO.getDyccAnexoTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite()});

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAANEXOREQUERIMIENTO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(dyctAnexoReqDTO));
            throw new SIATException(dae);
        }
    }

    /**
     * Asigna la fecha de solventación
     *
     * @param numControlDoc
     * @throws SIATException
     */
    @Override
    public void actualizaReqInfo(String numControlDoc) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_REQINFO.toString(), new Object[]{numControlDoc});

        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZA_REQINFO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControlDoc);
            throw new SIATException(e);
        }
    }

    /**
     * Actualiza el estado de la solicitud
     *
     * @param isEstado
     * @param numControl
     * @throws SIATException
     */
    @Override
    public void actualizaSolicitud(Integer isEstado, String numControl) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZASOLICITUD.toString(), new Object[]{isEstado, numControl});

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZASOLICITUD.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + isEstado
                    + numControl);
            throw new SIATException(e);
        }

    }

    /**
     * Actualiza la compensación al momento de ser solventada.
     *
     * @param isEstado
     * @param numControl
     * @throws SIATException
     */
    @Override
    public void actualizaCompensacion(Integer isEstado, String numControl) throws SIATException {
        try {

            jdbcTemplateDYC.update(ACTUALIZAR_STATUS_COMPENSACION, new Object[]{isEstado, numControl});

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZASOLICITUD.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + isEstado
                    + numControl);
            throw new SIATException(e);
        }

    }

    /**
     * Inserta los registros en bitácora.
     *
     * @param seguimientoDTO
     * @throws SIATException
     */
    @Override
    public void insertaSeguimiento(SeguimientoDTO seguimientoDTO) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.INSERTASEGUIMIENTO.toString(),
                    new Object[]{seguimientoDTO.getIdAccionSeg(), seguimientoDTO.getResponsable(),
                        seguimientoDTO.getFecha(),
                        (seguimientoDTO.getComentarios() != null)
                                ? seguimientoDTO.getComentarios() : "",
                        seguimientoDTO.getNumControlDoc()});

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZASOLICITUD.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(seguimientoDTO));
            throw new SIATException(e);
        }
    }

    /**
     * Inserta las notas registradas por el contribuyente en BD
     *
     * @param notaExpediente
     * @throws SIATException
     */
    @Override
    public void insertaComentarioSolventacion(NotaExpedienteDTO notaExpediente) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.INSERTACOMENTARIO.toString(),
                    new Object[]{notaExpediente.getUsuario(), notaExpediente.getFechaRegistro(),
                        notaExpediente.getDescripcion(), notaExpediente.getNumControl()});

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.INSERTACOMENTARIO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(notaExpediente));
            throw new SIATException(dae);
        }
    }

    /**
     * Actualiza el estado del requerimiento que se encuentra en la tabla del
     * documento
     *
     * @param idEstadoReq
     * @param numControl
     * @param numControlDoc
     * @throws SIATException
     */
    @Override
    public void actualizaRequerimientoVencido(Integer idEstadoReq, String numControl,
            String numControlDoc) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAREQUERIMIENTOVENCIDA.toString(),
                    new Object[]{idEstadoReq, numControl, numControlDoc});

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAREQUERIMIENTOVENCIDA.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + idEstadoReq + numControl + numControlDoc);

            throw new SIATException(e);
        }
    }

    /**
     * Obtiene el numero maximo de documento que se debe de insertar en
     * dyct_otraarchivo
     *
     * @param idOtraInfoReq
     * @return
     * @throws SIATException
     */
    @Override
    public Integer retornarNumeroDeArchivo(Integer idOtraInfoReq) throws SIATException {
        Integer numero = 0;
        try {
            numero
                    = jdbcTemplateDYC.queryForObject(CONSULTA_NUMERO_DE_DOCUMENTO, new Object[]{idOtraInfoReq}, Integer.class);

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + CONSULTA_NUMERO_DE_DOCUMENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + "idOtraInfoReq: "
                    + idOtraInfoReq);
            throw new SIATException(e);
        }
        return numero;
    }

    /**
     * Consulta los datos de una compensacion para generar una cadena original
     * al solventar un requerimiento.
     *
     * @param numControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    @Override
    public CadenaCompensacionDTO consultarDatosDeCadenaDeUnaCompensacion(String numControl,
            String numControlDoc) throws SIATException {
        CadenaCompensacionDTO objeto = null;
        try {
            objeto
                    = jdbcTemplateDYC.queryForObject(CONSULTAR_DATOS_COMPENSACION_PARA_CADENA, new Object[]{numControl,
                numControlDoc},
                            new CadenaCompensacionMapper());

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + CONSULTAR_DATOS_COMPENSACION_PARA_CADENA + ConstantesDyC1.TEXTO_3_ERROR_DAO + "numControl: "
                    + numControl + ", numControlDoc: " + numControlDoc);
            throw new SIATException(e);
        }
        return objeto;
    }

    /**
     * Consulta los datos de una solicitud para generar una cadena original al
     * solventar un requerimiento.
     *
     * @param numControl
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    @Override
    public CadenaSolicitudDTO consultarDatosDeCadenaDeUnaSolicitud(String numControl,
            String numControlDoc) throws SIATException {
        CadenaSolicitudDTO objeto = null;
        try {
            objeto
                    = jdbcTemplateDYC.queryForObject(CONSULTAR_DATOS_SOLICITUD_PARA_CADENA, new Object[]{numControl, numControlDoc},
                            new CadenaSolicitudMapper());

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + CONSULTAR_DATOS_SOLICITUD_PARA_CADENA + ConstantesDyC1.TEXTO_3_ERROR_DAO + "numControl: "
                    + numControl + ", numControlDoc: " + numControlDoc);
            throw new SIATException(e);
        }
        return objeto;
    }

    @Override
    public void actualizaCadenaYSello(String numControlDoc, String cadenaOriginal, String selloDigital) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_CADENA_SELLO_SOLVENTACION.toString(),
                    new Object[]{cadenaOriginal, selloDigital, numControlDoc});

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ACTUALIZAR_CADENA_SELLO_SOLVENTACION.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + cadenaOriginal + selloDigital + numControlDoc);

            throw new SIATException(e);
        }
    }
}
