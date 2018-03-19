/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.dao.acuserecibo.impl;

import java.io.UnsupportedEncodingException;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.dao.req.DyctReqInfoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.gestionsol.dao.acuserecibo.AcuseReciboDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.acuserecibo.impl.mapper.ConsultarDeclaracionesPorSolicitudMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.acuserecibo.impl.mapper.ConsultarDevolucionesContribuyenteMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.SolventarRequerimientoDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaContribuyente;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alfredo Ramirez
 * @since 20/09/2013
 *
 */
@Repository(value = "acuseReciboDAO")
public class AcuseReciboDAOImpl implements AcuseReciboDAO {

    private static final Logger LOGGER = Logger.getLogger(AcuseReciboDAOImpl.class);

    private static final DecimalFormat FORMATO_DECIMAL = new DecimalFormat("$ ###,###.##");
    private static final String CODIFICACION_UTF8 = "UTF-8";
    private static final String CODIFICACION_ISOLATIN1 = "ISO-8859-1";
    private static final String FORMATO_FECHA = "dd-MM-yyyy HH:mm:ss";
    private static final String SELLO_DIGITAL = "SELLODIGITAL";
    private static final String CADENAORIGINAL = "CADENAORIGINAL";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    @Autowired
    private SolventarRequerimientoDAO solventarRequerimientoDAO;
    @Autowired
    private DyctReqInfoDAO dyctReqInfoDAO;

    private List<DyctDeclaracionDTO> listaDeclaraciones;
    private List<String> anexos;
    private List<String> documentos;
    private ConsultarDevolucionesContribuyenteDTO solicitud;
    private Map<String, Object> datos;
    private Map<String, Object> datosB;

    public AcuseReciboDAOImpl() {
        super();
        listaDeclaraciones = new ArrayList<DyctDeclaracionDTO>();
        solicitud = new ConsultarDevolucionesContribuyenteDTO();
    }

    @Override
    public Map<String, Object> queryRequest(String numControl) throws ParseException {

        Map<String, Object> datas = new HashMap<String, Object>();
        Map<String, Object> datasB = new HashMap<String, Object>();

        try {
            if (this.jdbcTemplateDYC.queryForMap(SQLOracleDyC.TOQUERY_REQUEST.toString(), new Object[]{numControl}).size() > 0) {

                datasB = this.jdbcTemplateDYC.queryForMap(SQLOracleDyC.TOQUERY_REQUEST.toString(), new Object[]{numControl});
            }

            if (datasB != null) {
                datas.put(CLAVE_RFC, Utilerias.isNull(datasB.get(CLAVE_RFC)));
                datas.put(CONCEPTO, Utilerias.isNull(datasB.get(CONCEPTO)));
                datas.put(EJERCICIO, Utilerias.isNull(datasB.get(EJERCICIO)));
                datas.put(IMPORTE_SOLICITADO,
                        Utilerias.isNull(datasB.get(IMPORTE_SOLICITADO) != null ? datasB.get(IMPORTE_SOLICITADO)
                                : "0"));
                datas.put(ID_IMPUESTO, Utilerias.isNull(datasB.get(ID_IMPUESTO)));
                datas.put(ID_CONCEPTO, Utilerias.isNull(datasB.get(ID_CONCEPTO)));
                datas.put(ID_PERIODO, Utilerias.isNull(datasB.get(ID_PERIODO)));
                datas.put(TIPO_SALDO, Utilerias.isNull(datasB.get(TIPO_SALDO)));
                datas.put(CLAVE_ADM, Utilerias.isNull(datasB.get(CLAVE_ADM)));

            }
        } catch (DataAccessException dae) {
            LOGGER.error(dae);
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.TOQUERY_REQUEST + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
            throw dae;
        }
        return datas;

    }

    @Override
    public Map<String, Object> consultarSolicitud(String rfc, String numControl, int servicio,
            Integer tipos, boolean isReimpresion) throws ParseException {
        datos = new HashMap<String, Object>();
        String condicionFinal = isReimpresion ? " and so.CADENAORIGINAL IS NOT NULL AND so.SELLODIGITAL IS NOT NULL" : "";
        try {
            datosB
                    = this.jdbcTemplateDYC.queryForMap(SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARSOLICTUD.toString() + condicionFinal, new Object[]{rfc, numControl});

            datos.put(FECHA, getFormatoFecha(datosB.get(FECHA_INICIO_TRAMITE), FORMATO_FECHA));
            datos.put(CLAVE_RFC, datosB.get(CLAVE_RFC));
            datos.put(NUMERO_FOLIO, datosB.get(NUMERO_FOLIO));
            datos.put(ADMIN_LOCAL, datosB.get(ADMIN_LOCAL));

            datos.put(ORIGEN_DEVOLUCUION, datosB.get(ORIGEN_DEVOLUCUION));
            datos.put(TIPO_TRAMITE, datosB.get(TIPO_TRAMITE));
            datos.put(IMPUESTO, datosB.get(IMPUESTO));
            datos.put(CONCEPTO, datosB.get(CONCEPTO));
            datos.put(TIPO_PERIODICIDAD, datosB.get(TIPO_PERIODICIDAD));
            datos.put(PERIODO, datosB.get(PERIODO));
            datos.put(EJERCICIO, String.valueOf(datosB.get(EJERCICIO)));

            datos.put(FECHA_PRESENTACION, getFormatoFecha(datosB.get(FECHA_PRESENTACION), FORMATO_FECHA));
            datos.put(MANIFIESTA, Utilerias.isNull(datosB.get(MANIFIESTA)));
            datos.put(LBL_PROTESTA, "");
            datos.put(PROTESTA, Utilerias.isNull(datosB.get(PROTESTA)));
            datos.put(LBL_NOTIFICACION, "");
            datos.put(NOTIFICACION_1, Utilerias.isNull(datosB.get(INFO_AGROPECUARIO)));
            datos.put(NOTIFICACION_2, Utilerias.isNull(datosB.get(APLICA_FACILIDAD)));
            listaDeclaraciones
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARDECLARACIONESPORSOLICITUD.toString(), new Object[]{rfc,
                numControl},
                            new ConsultarDeclaracionesPorSolicitudMapper());

            if (tipos == ConstantesDyCNumerico.VALOR_1) {

                String consultaDocumentosContribuyente = getConsultaTramitesAcuse();

                getDocumentosSolicitud(
                        SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARSOLICTUDANEXOS.toString(),
                        consultaDocumentosContribuyente, numControl, null);
            } else {
                getDocumentoCuentaClabe(SQLOracleDyC.CONSULTA_ARCHIVO_CUENTACLAB.toString(), numControl);
                datos.remove(FECHA);
                datos.put(FECHA, getFormatoFecha(new Date(), FORMATO_FECHA));
            }
            validaListasMap();
            datos.put(SELLO_ORIGINAL, Utilerias.isNull(datosB.get(SELLO_DIGITAL)));
            datos.put(CADENA_ORIGINAL, Utilerias.isNull(datosB.get(CADENAORIGINAL)));
            datos.put(ANEXO_SOLICITUD, anexos);
            datos.put(DOCUMENTO_SOLICITUD, documentos);
            datos.put(NUM_CONTROL, numControl);
        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARSOLICTUD + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc + ","
                    + numControl);
            throw dae;
        }
        return datos;
    }

    private String getConsultaTramitesAcuse() {
        String consulta = SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARSOLICTUDDOCUMENTOS.toString();
        Integer parametro = ConstantesValidaContribuyente.NO_OCULTO;

        return cambiaParametroOcultoContribuyente(consulta, parametro.toString());
    }

    private String cambiaParametroOcultoContribuyente(String consulta, String valorParametro) {

        StringBuilder textoCambio = new StringBuilder(consulta.replaceAll("\\?( )*$", valorParametro));
        textoCambio.append(" ");

        return textoCambio.toString();
    }

    @Override
    public ConsultarDevolucionesContribuyenteDTO solicitudPorNumControl(String numControl, String rfc) {
        try {
            this.solicitud
                    = (ConsultarDevolucionesContribuyenteDTO) this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTADYP_OBTENERSOLICITUDESPORNUMCONTROL.toString(),
                            new Object[]{numControl,
                                rfc},
                            new ConsultarDevolucionesContribuyenteMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTADYP_OBTENERSOLICITUDESPORNUMCONTROL.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
            throw dae;
        }
        return this.solicitud;
    }

    private void getDocumentosSolicitud(String queryAnexos, String queryDocumentos, String numControl,
            String queryOtranfo) {

        anexos = this.jdbcTemplateDYC.query(
                queryAnexos,
                new Object[]{numControl},
                new RowMapper() {

            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }

        }
        );

        documentos = this.jdbcTemplateDYC.query(
                queryDocumentos,
                new Object[]{numControl},
                new RowMapper() {

            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }

        }
        );

        if (null != queryOtranfo) {

            List<String> otraInfo = this.jdbcTemplateDYC.query(
                    queryOtranfo,
                    new Object[]{numControl},
                    new RowMapper() {

                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString(1);
                }

            }
            );

            documentos.addAll(otraInfo);
        }

    }

    private void getDocumentoCuentaClabe(String queryDocumentos, String numControl) {
        documentos = this.jdbcTemplateDYC.query(queryDocumentos, new Object[]{numControl}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }
        });
    }

    private void validaListasMap() {
        String segundaDeclaracion = "";
        if (listaDeclaraciones.size() > ConstantesDyCNumerico.VALOR_0) {
            segundaDeclaracion = "FALSE";
            if (listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getDyccTipoDeclaraDTO().getIdTipoDeclaracion()
                    == ConstantesDyCNumerico.VALOR_1) {
                datos.put(TIPO_DECLARACION, DECLARACION_NORMAL);
            } else if (listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getDyccTipoDeclaraDTO().getIdTipoDeclaracion()
                    == ConstantesDyCNumerico.VALOR_2) {
                datos.put(TIPO_DECLARACION, DECLARACION_COMPLEMENTARIA);
            } else {
                datos.put(TIPO_DECLARACION, "");
            }

            datos.put(FECHA_CAUSACION,
                    getFormatoFecha(listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getFechaCausacion(),
                            "dd/MM/yyyy"));

            datos.put(FECHA_PRESENTACION_DEV,
                    getFormatoFecha(listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getFechaPresentacion(),
                            "dd/MM/yyyy"));
            datos.put(NUMERO_OPERACION_DEV, String.valueOf(listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getNumOperacion()));
            if (null != listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getNumDocumento()) {
                datos.put(LBL_DOC_OPE, LBL_NUM_DOCUMENTO);
                datos.put(TXT_DOCOPE,
                        String.valueOf(listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getNumDocumento()));
            }
            if (null != listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getNumOperacion()) {
                datos.put(LBL_DOC_OPE, String.valueOf(LBL_NUM_OPERACION));
                datos.put(TXT_DOCOPE,
                        String.valueOf(listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getNumOperacion()));
            }
            getImportesMap();
        }
        datos.put(SEGUNDA_DECLARACION, segundaDeclaracion);
    }

    private void getImportesMap() {
        if (listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getSaldoAfavor() != null) {
            datos.put(LBL_REGLA, listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getEtiquetaSaldo() + ":");
            datos.put(SALDO_A_FAVOR,
                    FORMATO_DECIMAL.format(listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getSaldoAfavor()));
        } else {
            datos.put(LBL_REGLA, "");
            datos.put(SALDO_A_FAVOR, "");
        }

        if (listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getDevueltoCompensado() != null) {
            datos.put(IMPORE_DEVOLUCION,
                    FORMATO_DECIMAL.format(listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getDevueltoCompensado()));
        } else {
            datos.put(IMPORE_DEVOLUCION, "");
        }

        if (listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getAcreditamiento() != null) {
            datos.put(IMPORTE_ACREDITACION,
                    FORMATO_DECIMAL.format(listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getAcreditamiento()));
        } else {
            datos.put(IMPORTE_ACREDITACION, "");
        }

        if (listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getImporte() != null) {
            datos.put(IMPORTE_SOLICITADO,
                    FORMATO_DECIMAL.format(listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0).getImporte()));
        } else {
            datos.put(IMPORTE_SOLICITADO, "");
        }
    }

    @Override
    public Map<String, Object> getDatosAcuseReq(String numDocumento) throws SIATException {
        String query = "select numcontrol from dyct_documento where numcontroldoc = ?";

        try {
            String numcontrol = jdbcTemplateDYC.queryForObject(query, new Object[]{numDocumento},
                    String.class).toString();
            if (numcontrol.contains("DC")) {
                datosReqSolicitud(numDocumento);
            } else {
                datosReqAvisoComp(numDocumento);
            }
        } catch (Exception e) {
            LOGGER.error("error al buscar el documento: " + numDocumento);
            LOGGER.error("Query: " + query + ", error: " + e.getCause());
            throw new SIATException(e);
        }
        getDocumentosSolicitud(SQLOracleDyC.FIND_ANEXOS_ADJUNTOS_REQ.toString(), SQLOracleDyC.FIND_DOCUMENTOS_ADJUNTOS_REQ.toString(), numDocumento,
                SQLOracleDyC.FIND_OTROS_DOCUMENTOS.toString());
        datos.put(ANEXO_SOLICITUD, anexos);
        datos.put(DOCUMENTO_SOLICITUD, documentos);
        return datos;
    }

    @Override
    public Map<String, Object> getDatosAcuseReq(final String numDocumento, final String rfcContribuyente, boolean consideraRfc) throws SIATException {
        String condicionFinal = consideraRfc ? " and dycps.RFCCONTRIBUYENTE = '" + rfcContribuyente + "'" : "";
        String query = "select dyctd.numcontrol "
                + "  from dyct_documento dyctd "
                + "  left join dycp_servicio dycps on dycps.NUMCONTROL = dyctd.NUMCONTROL"
                + " where dyctd.numcontroldoc = ?"
                + condicionFinal;

        try {
            String numcontrol = jdbcTemplateDYC.queryForObject(query, new Object[]{numDocumento}, String.class);
            if (numcontrol.contains("DC")) {
                datosReqSolicitud(numDocumento);
            } else {
                datosReqAvisoComp(numDocumento);
            }
        } catch (Exception e) {
            LOGGER.error("error al buscar el documento: " + numDocumento);
            LOGGER.error("Query: " + query + ", error: " + e.getCause());
            throw new SIATException(e);
        }
        getDocumentosSolicitud(SQLOracleDyC.FIND_ANEXOS_ADJUNTOS_REQ.toString(), SQLOracleDyC.FIND_DOCUMENTOS_ADJUNTOS_REQ.toString(), numDocumento,
                SQLOracleDyC.FIND_OTROS_DOCUMENTOS.toString());
        datos.put(ANEXO_SOLICITUD, anexos);
        datos.put(DOCUMENTO_SOLICITUD, documentos);
        return datos;
    }

    private void datosReqSolicitud(String numDocumento) throws UnsupportedEncodingException {

        datos = new HashMap<String, Object>();
        datosB = new HashMap<String, Object>();
        String consulta = SQLOracleDyC.FIND_DATOS_SOLICITUD_REQ2.toString();
        datosB = jdbcTemplateDYC.queryForMap(consulta, new Object[]{numDocumento});

        if (datosB.get(CADENAORIGINAL) == null || datosB.get(SELLO_DIGITAL) == null) {
            datosB.clear();
            consulta = SQLOracleDyC.FIND_DATOS_SOLICITUD_REQ.toString();
            datosB = jdbcTemplateDYC.queryForMap(consulta, new Object[]{numDocumento});
            String selloDigital;
            try {
                selloDigital = datosB.get(SELLO_DIGITAL).toString();
            } catch (Exception ex) {
                LOGGER.error(ex);
                selloDigital = null;
            }
            
            try {
                
                solventarRequerimientoDAO.actualizaCadenaYSello(numDocumento, datosB.get(CADENAORIGINAL).toString(), selloDigital);
            } catch (SIATException ex) {
                LOGGER.error(ex);
            }
        }

        datos.put(FECHA_PRESENTACION, Utilerias.isNull(datosB.get("FECHAPRESENTACION")));
        datos.put(FECHA, Utilerias.isNull(datosB.get("FECHAINICIOTRAMITE")));
        datos.put(CLAVE_RFC, Utilerias.isNull(datosB.get("RFC")));
        datos.put(NUMERO_FOLIO, Utilerias.isNull(datosB.get("NUMCONTROL")));

        datos.put(NUMCONTROLDOC, Utilerias.isNull(datosB.get("NUMCONTROLDOC")));

        datos.put(ADMIN_LOCAL, Utilerias.isNull(datosB.get("ADMINISTRACION")));
        datos.put(ORIGEN_DEVOLUCUION, Utilerias.isNull(datosB.get("ORIGEN")));
        datos.put(TIPO_TRAMITE, Utilerias.isNull(datosB.get("TRAMITE")));
        datos.put(IMPUESTO, Utilerias.isNull(datosB.get("IMPUESTO")));
        datos.put(CONCEPTO, Utilerias.isNull(datosB.get("CONCEPTO")));
        datos.put(TIPO_PERIODICIDAD, Utilerias.isNull(datosB.get("TIPOPERIODO")));
        datos.put(TIPO_DECLARACION, Utilerias.isNull(datosB.get("TIPODECLARACION")));
        datos.put(EJERCICIO, Utilerias.isNull(datosB.get("IDEJERCICIO")));
        datos.put(FECHA_CAUSACION, Utilerias.isNull(datosB.get("FECHACAUSACION")));
        datos.put(FECHA_PRESENTACION_DEV, Utilerias.isNull(datosB.get("FECHAPRESENTACIONDEV")));
        datos.put(PERIODO, Utilerias.isNull(datosB.get("PERIODO")));
        datos.put(TIPO_DECLARACION, Utilerias.isNull(datosB.get("IDTIPODECLARACION")));
        datos.put(RUTA_IMAGEN, ConstantesDyCURL.URL_IMAGENES);
        datos.put(SELLO_ORIGINAL, Utilerias.isNull(datosB.get(SELLO_DIGITAL)));
        datos.put(CADENA_ORIGINAL, Utilerias.isNull(datosB.get(CADENAORIGINAL)));
        if (datosB.get(TIPOPERSONA).toString().trim().equals("M")) {
            datos.put(DENOMINACION, new String(Utilerias.isNull(datosB.get("RAZONSOCIAL")).getBytes(CODIFICACION_ISOLATIN1), CODIFICACION_UTF8));
        } else {
            datos.put(DENOMINACION, new String(Utilerias.isNull(datosB.get("NOMBRECOMPLETO")).getBytes(CODIFICACION_ISOLATIN1), CODIFICACION_UTF8));
        }
        if (null != datosB.get("NUMOPERACION")) {
            datos.put(LBL_DOC_OPE, String.valueOf(LBL_NUM_OPERACION));
            datos.put(TXT_DOCOPE, Utilerias.isNull(datosB.get("NUMOPERACION")));
        } else {
            datos.put(LBL_DOC_OPE, LBL_NUM_DOCUMENTO);
            datos.put(TXT_DOCOPE, Utilerias.isNull(datosB.get("NUMDOCUMENTO")));
        }
        if (null != datosB.get("SALDOAFAVOR")) {
            datos.put(SALDO_A_FAVOR, Utils.formatearMoneda(Double.valueOf(datosB.get("SALDOAFAVOR").toString())));
            datos.put(LBL_REGLA, datosB.get("ETIQUETASALDO"));
        } else {
            datos.put(LBL_REGLA, "");
            datos.put(SALDO_A_FAVOR, "");
        }
        if (!Utilerias.isNull(datosB.get("TIPOREQ")).equals("1")) {
            datos.put(LBL_2DO_REQ, "(2do)");
        } else {
            datos.put(LBL_2DO_REQ, "(1er)");
        }
        datos.put(IMPORE_DEVOLUCION,
                null != datosB.get("DEVUELTOCOMPENSADO") ? Utils.formatearMoneda(Double.valueOf(datosB.get("DEVUELTOCOMPENSADO").toString()))
                : "");
        datos.put(IMPORTE_ACREDITACION,
                null != datosB.get("ACREDITAMIENTO") ? Utils.formatearMoneda(Double.valueOf(datosB.get("ACREDITAMIENTO").toString()))
                : "");
        datos.put(IMPORTE_SOLICITADO, Utils.formatearMoneda(Double.valueOf(Utilerias.isNull(datosB.get("IMPORTE")))));
    }

    private void datosReqAvisoComp(String numDocumento) throws UnsupportedEncodingException {
        LOGGER.info("INIT ACUSE 2do REQUERIMIENTO: AVISO/CASO COMPENSACION");

        datos = new HashMap<String, Object>();
        datosB = new HashMap<String, Object>();
        String consultaEjecucion = "";

        boolean ejecutaConsulta = consultaXNumDocumento(numDocumento);

        consultaEjecucion = (ejecutaConsulta) ? SQLOracleDyC.FIND_DATOS_AVISO_COMP2.toString() : SQLOracleDyC.FIND_DATOS_AVISO_COMP.toString();
        datosB = jdbcTemplateDYC.queryForMap(consultaEjecucion, new Object[]{numDocumento});

        if (!ejecutaConsulta) {
            try {
                solventarRequerimientoDAO.actualizaCadenaYSello(numDocumento, datosB.get(CADENAORIGINAL).toString(), datosB.get(SELLO_DIGITAL).toString());
            } catch (SIATException ex) {
                LOGGER.error(ex);
            }
        }
        
        datos.put(FECHA, Utilerias.isNull(datosB.get("FECHAINICIOTRAMITE")));
        datos.put(FECHA_PRESENTACION, Utilerias.isNull(datosB.get("FECHAPRESENTACION")));
        datos.put(CLAVE_RFC, Utilerias.isNull(datosB.get("CLAVERFC")));
        datos.put(NUM_CONTROL, Utilerias.isNull(datosB.get("NUMCONTROL")));

        datos.put(NUMCONTROLDOC, Utilerias.isNull(datosB.get("NUMCONTROLDOC")));

        datos.put(ADMIN_LOCAL, Utilerias.isNull(datosB.get("ADMINLOCAL")));
        datos.put(CONCEPTO, Utilerias.isNull(datosB.get("CONCEPTO")));
        datos.put(EJERCICIO, Utilerias.isNull(datosB.get("IDEJERCICIO")));
        datos.put(IMPORTE_SOLICITADO,
                Utils.formatearMoneda(Double.valueOf(Utilerias.isNull(datosB.get("IMPORTECOMPENSADO")))));
        datos.put(PERIODO, Utilerias.isNull(datosB.get("PERIODO")));
        datos.put(FECHA_PRESENTACION_DEV, Utilerias.isNull(datosB.get("FECHAPRESENTACIONDEC")));
        datos.put(NUMERO_OPERACION, Utilerias.isNull(datosB.get("NUMOPERACION")));
        datos.put(TIPO_DECLARACION, Utilerias.isNull(datosB.get("TIPOAVISO")));
        datos.put(RUTA_IMAGEN, ConstantesDyCURL.URL_IMAGENES);
        datos.put(SELLO_ORIGINAL, Utilerias.isNull(datosB.get(SELLO_DIGITAL)));
        datos.put(CADENA_ORIGINAL, Utilerias.isNull(datosB.get(CADENAORIGINAL)));
        if (datosB.get(TIPOPERSONA).toString().trim().equals("M")) {
            datos.put(DENOMINACION, new String(Utilerias.isNull(datosB.get("RAZONSOCIAL")).getBytes(CODIFICACION_ISOLATIN1), CODIFICACION_UTF8));
        } else {
            datos.put(DENOMINACION, new String(Utilerias.isNull(datosB.get("NOMBRECOMPLETO")).getBytes(CODIFICACION_ISOLATIN1), CODIFICACION_UTF8));
        }

        datos.put(FOLIO, Utilerias.isNull(datosB.get("FOLIOAVISO")));
    }

    private String getFormatoFecha(Object date, String format) {
        String formatoFecha = "";
        if (null != date) {
            formatoFecha = new SimpleDateFormat(format).format((Date) date);
        }
        return formatoFecha;
    }

    public void setListaDeclaraciones(List<DyctDeclaracionDTO> listaDeclaraciones) {
        this.listaDeclaraciones = listaDeclaraciones;
    }

    public List<DyctDeclaracionDTO> getListaDeclaraciones() {
        return listaDeclaraciones;
    }

    public void setAnexos(List<String> anexos) {
        this.anexos = anexos;
    }

    public List<String> getAnexos() {
        return anexos;
    }

    public void setDocumentos(List<String> documentos) {
        this.documentos = documentos;
    }

    public List<String> getDocumentos() {
        return documentos;
    }

    public void setDatosB(Map<String, Object> datosB) {
        this.datosB = datosB;
    }

    public Map<String, Object> getDatosB() {
        return datosB;
    }

    public void setDatos(Map<String, Object> datos) {
        this.datos = datos;
    }

    public Map<String, Object> getDatos() {
        return datos;
    }

    @Override
    public Map<String, Object> consultarDatosAcuseSolventacion(String rfc, String numControl, int servicio, Integer tipos, boolean consideraRfc, String numControlDoc) throws ParseException {
        datos = new HashMap<String, Object>();
        String condicionFinal = consideraRfc ? " AND serv.RFCCONTRIBUYENTE = '" + rfc + "'" : "";

        try {
            datosB
                    = this.jdbcTemplateDYC.queryForMap(SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARACUSESOLVENTACION2.toString() + condicionFinal, new Object[]{numControl, numControlDoc});

            if ((datosB.get(SELLO_DIGITAL) == null) || (datosB.get(CADENAORIGINAL) == null)) {
                datosB.clear();
                datosB
                        = this.jdbcTemplateDYC.queryForMap(SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARACUSESOLVENTACION.toString() + condicionFinal, new Object[]{numControl, numControlDoc});
                solventarRequerimientoDAO.actualizaCadenaYSello(numControlDoc, datosB.get(CADENAORIGINAL).toString(), datosB.get(SELLO_DIGITAL).toString());
            }

            datos.put(FECHA, getFormatoFecha(datosB.get(FECHA_INICIO_TRAMITE), FORMATO_FECHA));
            datos.put(CLAVE_RFC, datosB.get(CLAVE_RFC));
            datos.put(NUMERO_FOLIO, datosB.get(NUMERO_FOLIO));
            datos.put(ADMIN_LOCAL, datosB.get(ADMIN_LOCAL));

            datos.put(ORIGEN_DEVOLUCUION, datosB.get(ORIGEN_DEVOLUCUION));
            datos.put(TIPO_TRAMITE, datosB.get(TIPO_TRAMITE));
            datos.put(IMPUESTO, datosB.get(IMPUESTO));
            datos.put(CONCEPTO, datosB.get(CONCEPTO));
            datos.put(TIPO_PERIODICIDAD, datosB.get(TIPO_PERIODICIDAD));
            datos.put(PERIODO, datosB.get(PERIODO));
            datos.put(EJERCICIO, String.valueOf(datosB.get(EJERCICIO)));

            datos.put(FECHA_PRESENTACION, getFormatoFecha(datosB.get(FECHA_PRESENTACION), FORMATO_FECHA));
            datos.put(MANIFIESTA, Utilerias.isNull(datosB.get(MANIFIESTA)));
            datos.put(LBL_PROTESTA, "");
            datos.put(PROTESTA, Utilerias.isNull(datosB.get(PROTESTA)));
            datos.put(LBL_NOTIFICACION, "");
            datos.put(NOTIFICACION_1, Utilerias.isNull(datosB.get(INFO_AGROPECUARIO)));
            datos.put(NOTIFICACION_2, Utilerias.isNull(datosB.get(APLICA_FACILIDAD)));
            listaDeclaraciones
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARDECLARACIONESPORSOLICITUD.toString(), new Object[]{rfc,
                numControl},
                            new ConsultarDeclaracionesPorSolicitudMapper());

            if (tipos == ConstantesDyCNumerico.VALOR_1) {

                String consultaDocumentosContribuyente = getConsultaTramitesAcuse();

                getDocumentosSolicitud(
                        SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARSOLICTUDANEXOS.toString(),
                        consultaDocumentosContribuyente, numControl, null);
            } else {
                getDocumentoCuentaClabe(SQLOracleDyC.CONSULTA_ARCHIVO_CUENTACLAB.toString(), numControl);
                datos.remove(FECHA);
                datos.put(FECHA, getFormatoFecha(new Date(), FORMATO_FECHA));
            }
            validaListasMap();
            datos.put(SELLO_ORIGINAL, Utilerias.isNull(datosB.get(SELLO_DIGITAL)));
            datos.put(CADENA_ORIGINAL, Utilerias.isNull(datosB.get(CADENAORIGINAL)));
            datos.put(ANEXO_SOLICITUD, anexos);
            datos.put(DOCUMENTO_SOLICITUD, documentos);
            datos.put(NUM_CONTROL, numControl);
        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.CONSULTARCATALOGOS_CONSULTARSOLICTUD + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc + ","
                    + numControl);
            throw dae;
        } catch (SIATException ex) {
            LOGGER.error(ex);
        }
        return datos;
    }

    private boolean consultaXNumDocumento(String numControlDoc) {
        boolean existeRegistro = false;

        List<DyctReqInfoDTO> lstReqInfo = dyctReqInfoDAO.buscarReqInfoReimpresion(numControlDoc);

        for (DyctReqInfoDTO requerimiento : lstReqInfo) {
            if (requerimiento.getCadenaOriginal() != null && requerimiento.getSelloDigital() != null) {
                existeRegistro = true;
            }
        }
        return existeRegistro;
    }
}
