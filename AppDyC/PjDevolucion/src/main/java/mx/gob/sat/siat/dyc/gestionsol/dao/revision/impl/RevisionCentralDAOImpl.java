package mx.gob.sat.siat.dyc.gestionsol.dao.revision.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dao.revision.RevisionCentralDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.revision.impl.mapper.ResumenRevisorCentralMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.revision.impl.mapper.RevisionCentralMapper;
import mx.gob.sat.siat.dyc.gestionsol.vo.revision.ResumenRevisionVO;
import mx.gob.sat.siat.dyc.gestionsol.vo.revision.RevisionCentralVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "revisionCentralDAO")
public class RevisionCentralDAOImpl implements RevisionCentralDAO {
    
    private static final Logger LOGGER = Logger.getLogger(RevisionCentralDAO.class);
    
    private static final String CONSULTAR_LISTA_REVISOR_CENTRAL_MONTO1 = " select a.numcontrol, b.rfccontribuyente, DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS DICTAMINADO,   \n" + 
    "        d.descripcion as tipoTramite, a.fecharegistro, e.descripcion as nombreDocumento, sysdate as fechaVencimiento,   \n" + 
    "        f.nombre||' '||f.apellidoPaterno||' '||f.apellidoMaterno as dictaminador, a.numControlDoc, G.IMPORTESOLICITADO   \n" + 
    " from dyct_documento a   \n" + 
    " inner join dycp_servicio      b on (a.numcontrol=b.numcontrol and b.idorigensaldo!=4)   \n" + 
    " INNER JOIN DYCT_CONTRIBUYENTE C ON (C.NUMCONTROL = a.NUMCONTROL)  \n" + 
    " INNER JOIN DYCC_TIPOTRAMITE   D ON (D.IDTIPOTRAMITE = B.IDTIPOTRAMITE)   \n" + 
    " inner join dycc_tipodocumento e on (a.idtipodocumento = e.idtipodocumento)   \n" + 
    " inner join dycc_dictaminador  f on (b.numempleadoDict = f.numempleado)  \n" + 
    " INNER JOIN DYCT_RESOLUCION    G ON (G.NUMCONTROL = A.NUMCONTROL)  \n" + 
    " INNER JOIN DYCP_SOLICITUD     I ON (I.NUMCONTROL = A.NUMCONTROL)  \n" + 
    " where a.idestadodoc=9 \n" + 
        " UNION ALL \n" + 
    "select a.numcontrol, b.rfccontribuyente, DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS DICTAMINADO,   \n" + 
        "       d.descripcion as tipoTramite, a.fecharegistro, e.descripcion as nombreDocumento, sysdate as fechaVencimiento,   \n" + 
        "       f.nombre||' '||f.apellidoPaterno||' '||f.apellidoMaterno as dictaminador, a.numControlDoc, I.IMPORTECOMPENSADO AS IMPORTESOLICITADO   \n" + 
        "from dyct_documento a   \n" + 
        "inner join dycp_servicio      b on (a.numcontrol=b.numcontrol and b.idorigensaldo!=4)   \n" + 
        "INNER JOIN DYCT_CONTRIBUYENTE C ON (C.NUMCONTROL = a.NUMCONTROL)  \n" + 
        "INNER JOIN DYCC_TIPOTRAMITE   D ON (D.IDTIPOTRAMITE = B.IDTIPOTRAMITE)   \n" + 
        "inner join dycc_tipodocumento e on (a.idtipodocumento = e.idtipodocumento)   \n" + 
        "inner join dycc_dictaminador  f on (b.numempleadoDict = f.numempleado)  \n" + 
        "INNER JOIN DYCP_COMPENSACION  I ON (I.NUMCONTROL = A.NUMCONTROL)  \n" + 
        "where a.idestadodoc=9 "
        ;
    
    private static final String CONSULTAR_LISTA_REVISOR_LEGALES = "select a.numcontrol, b.rfccontribuyente, DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS DICTAMINADO,   \n" + 
    "       d.descripcion as tipoTramite, a.fecharegistro, e.descripcion as nombreDocumento, sysdate as fechaVencimiento,   \n" + 
    "       f.nombre||' '||f.apellidoPaterno||' '||f.apellidoMaterno as dictaminador, a.numControlDoc, G.IMPORTESOLICITADO   \n" + 
    "from dyct_documento a   \n" + 
    "inner join dycp_servicio      b on (a.numcontrol=b.numcontrol)   \n" + 
    "INNER JOIN DYCT_CONTRIBUYENTE C ON (C.NUMCONTROL = a.NUMCONTROL)  \n" + 
    "INNER JOIN DYCC_TIPOTRAMITE   D ON (D.IDTIPOTRAMITE = B.IDTIPOTRAMITE)   \n" + 
    "inner join dycc_tipodocumento e on (a.idtipodocumento = e.idtipodocumento)   \n" + 
    "inner join dycc_dictaminador  f on (b.numempleadoDict = f.numempleado)  \n" + 
    "INNER JOIN DYCT_RESOLUCION    G ON (G.NUMCONTROL = A.NUMCONTROL)  \n" + 
    "INNER JOIN DYCP_SOLICITUD     I ON (I.NUMCONTROL = A.NUMCONTROL)  \n" + 
    "where a.idestadodoc=9 \n" +
    "AND b.idorigensaldo=4  \n" +
        "UNION ALL \n" + 
    "select a.numcontrol, b.rfccontribuyente, DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS DICTAMINADO,   \n" + 
        "       d.descripcion as tipoTramite, a.fecharegistro, e.descripcion as nombreDocumento, sysdate as fechaVencimiento,   \n" + 
        "       f.nombre||' '||f.apellidoPaterno||' '||f.apellidoMaterno as dictaminador, a.numControlDoc, I.IMPORTECOMPENSADO AS IMPORTESOLICITADO   \n" + 
        "from dyct_documento a   \n" + 
        "inner join dycp_servicio      b on (a.numcontrol=b.numcontrol)   \n" + 
        "INNER JOIN DYCT_CONTRIBUYENTE C ON (C.NUMCONTROL = a.NUMCONTROL)  \n" + 
        "INNER JOIN DYCC_TIPOTRAMITE   D ON (D.IDTIPOTRAMITE = B.IDTIPOTRAMITE)   \n" + 
        "inner join dycc_tipodocumento e on (a.idtipodocumento = e.idtipodocumento)   \n" + 
        "inner join dycc_dictaminador  f on (b.numempleadoDict = f.numempleado)  \n" + 
        "INNER JOIN DYCP_COMPENSACION  I ON (I.NUMCONTROL = A.NUMCONTROL)  \n" + 
        "where a.idestadodoc=9 \n" +
        "AND b.idorigensaldo=4  \n" 
        
        ;
    
    
    private static final String CONSULTAR_RESUMEN_X_NUMCONTROLDOC = "select B.RFCCONTRIBUYENTE, decode(EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '|| \n" + 
    "       EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '|| \n" + 
    "       EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno'), '  ', \n" + 
    "       EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial'),  \n" + 
    "       EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '|| \n" + 
    "       EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '|| \n" + 
    "       EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as nombreORazonSocial, \n" + 
    "       D.DESCRIPCION AS TIPOTRAMITE, E.DESCRIPCION AS TIPORESOLUCION, F.IMPORTESOLICITADO, F.IMPAUTORIZADO, F.IMPORTENETO, f.impnegado, a.numControlDoc , a.numControl \n" + 
    "from dyct_documento a \n" + 
    "inner join dycp_servicio b on (a.numcontrol=b.numcontrol) \n" + 
    "INNER JOIN dyct_contribuyente C ON (A.NUMCONTROL=C.NUMCONTROL) \n" + 
    "INNER JOIN DYCC_TIPOTRAMITE   D ON (B.IDTIPOTRAMITE=D.IDTIPOTRAMITE) \n" + 
    "INNER JOIN dycC_TIPODOCUMENTO E ON (A.IDTIPODOCUMENTO=E.IDTIPODOCUMENTO)\n" + 
    "INNER JOIN DYCT_RESOLUCION    F ON (A.NUMCONTROL = F.NUMCONTROL)\n" + 
    "where a.idestadodoc=9 AND A.NUMCONTROLDOC=?";
    
    private static final String CONSULTAR_RESUMENCONT_X_NUMCONTROLDOC = " select B.RFCCONTRIBUYENTE, decode(EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '|| \n" + 
    "        EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '|| \n" + 
    "        EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno'), '  ', \n" + 
    "        EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial'),  \n" + 
    "        EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '|| \n" + 
    "        EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '|| \n" + 
    "        EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as nombreORazonSocial, \n" + 
    "        D.DESCRIPCION AS TIPOTRAMITE, E.DESCRIPCION AS TIPORESOLUCION, F.IMPORTECOMPENSADO AS IMPORTESOLICITADO,  F.IMPORTECOMPENSADO AS IMPAUTORIZADO, F.IMPORTECOMPENSADO AS IMPORTENETO, 0 AS impnegado, a.numControlDoc , a.numControl \n" + 
    " from dyct_documento a \n" + 
    " inner join dycp_servicio b on (a.numcontrol=b.numcontrol) \n" + 
    " INNER JOIN dyct_contribuyente C ON (A.NUMCONTROL=C.NUMCONTROL) \n" + 
    " INNER JOIN DYCC_TIPOTRAMITE   D ON (B.IDTIPOTRAMITE=D.IDTIPOTRAMITE) \n" + 
    " INNER JOIN dycC_TIPODOCUMENTO E ON (A.IDTIPODOCUMENTO=E.IDTIPODOCUMENTO)\n" + 
    " INNER JOIN DYCP_COMPENSACION  F ON (A.NUMCONTROL = F.NUMCONTROL)\n" + 
    " where a.idestadodoc=9 AND A.NUMCONTROLDOC=?";
    
    private static final String CONSULTAR_DEL_PADRON_NO_CONFIABLE_LEGALES = "select a.numcontrol, b.rfccontribuyente, DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS DICTAMINADO,    \n" + 
    "       d.descripcion as tipoTramite, a.fecharegistro, e.descripcion as nombreDocumento, sysdate as fechaVencimiento,    \n" + 
    "       f.nombre||' '||f.apellidoPaterno||' '||f.apellidoMaterno as dictaminador, a.numControlDoc, G.IMPORTESOLICITADO    \n" + 
    "from dyct_documento a    \n" + 
    "inner join dycp_servicio      b on (a.numcontrol=b.numcontrol and b.idorigensaldo=4) \n" + 
    "INNER JOIN DYCT_CONTRIBUYENTE C ON (C.NUMCONTROL = a.NUMCONTROL)   \n" + 
    "INNER JOIN DYCC_TIPOTRAMITE   D ON (D.IDTIPOTRAMITE = B.IDTIPOTRAMITE)    \n" + 
    "inner join dycc_tipodocumento e on (a.idtipodocumento = e.idtipodocumento)    \n" + 
    "inner join dycc_dictaminador  f on (b.numempleadoDict = f.numempleado)   \n" + 
    "INNER JOIN DYCT_RESOLUCION    G ON (G.NUMCONTROL = A.NUMCONTROL)   \n" + 
    "INNER JOIN DYCP_SOLICITUD     I ON (I.NUMCONTROL = A.NUMCONTROL) \n" + 
    "INNER JOIN DYCP_RFC           J ON (b.rfccontribuyente=J.RFC AND J.ESNOCONFIABLE=1 AND J.PADRONNOCONFIABLE=1) \n" + 
    "where a.idestadodoc=9 and a.idtipodocumento=5";
    
    private static final String CONSULTAR_DEL_PADRON_NO_CONFIABLE_RCENTRAL = "select a.numcontrol, b.rfccontribuyente, DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS DICTAMINADO,    \n" + 
    "       d.descripcion as tipoTramite, a.fecharegistro, e.descripcion as nombreDocumento, sysdate as fechaVencimiento,    \n" + 
    "       f.nombre||' '||f.apellidoPaterno||' '||f.apellidoMaterno as dictaminador, a.numControlDoc, G.IMPORTESOLICITADO    \n" + 
    "from dyct_documento a    \n" + 
    "inner join dycp_servicio      b on (a.numcontrol=b.numcontrol and b.idorigensaldo!=4) \n" + 
    "INNER JOIN DYCT_CONTRIBUYENTE C ON (C.NUMCONTROL = a.NUMCONTROL)   \n" + 
    "INNER JOIN DYCC_TIPOTRAMITE   D ON (D.IDTIPOTRAMITE = B.IDTIPOTRAMITE)    \n" + 
    "inner join dycc_tipodocumento e on (a.idtipodocumento = e.idtipodocumento)    \n" + 
    "inner join dycc_dictaminador  f on (b.numempleadoDict = f.numempleado)   \n" + 
    "INNER JOIN DYCT_RESOLUCION    G ON (G.NUMCONTROL = A.NUMCONTROL)   \n" + 
    "INNER JOIN DYCP_SOLICITUD     I ON (I.NUMCONTROL = A.NUMCONTROL) \n" + 
    "INNER JOIN DYCP_RFC           J ON (b.rfccontribuyente=J.RFC AND J.ESNOCONFIABLE=1 AND J.PADRONNOCONFIABLE=1) \n" + 
    "where a.idestadodoc=9 and a.idtipodocumento=5";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    public RevisionCentralDAOImpl() {
        super();
    }

    /**
     * <pre>
     * Consulta los documentos que se tienen pendientes por revisar por parte de un revisor central.
     * </pre>
     *
     * @return lista de documentos.
     * @throws SIATException
     */
    @Override
    public List<RevisionCentralVO> consultar(Integer bandera) throws SIATException {
        List<RevisionCentralVO> listaDeDocumentos = null;
        String query = "";
        try {
            if (bandera.equals(0)) {
                query=CONSULTAR_LISTA_REVISOR_CENTRAL_MONTO1;
            } else {
                query=CONSULTAR_LISTA_REVISOR_LEGALES;
            }
            listaDeDocumentos = jdbcTemplateDYC.query(query, new RevisionCentralMapper());
            
        } catch (Exception dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    query);
            throw new SIATException(dae);
        }
        return listaDeDocumentos;
    }

    /**
     * Consulta los datos del documento a revisar por parte del revisor central a traves del número de control
     *
     * @param numControlDoc Número de control de documento que identifica al revisor.
     * @return Objeto con RFC, nombre o razon social, tipo de requerimiento, tipo de resolucion y los siguientes importes: 
     * solicitado, autorizado, nneto y saldo negado.
     * 
     * @throws SIATException
     */
    @Override
    public ResumenRevisionVO consultarResumen(String numControlDoc) throws SIATException {
        ResumenRevisionVO objeto = null;
        try {
            objeto=jdbcTemplateDYC.queryForObject(CONSULTAR_RESUMEN_X_NUMCONTROLDOC, new Object[]{numControlDoc} ,new ResumenRevisorCentralMapper());
            
        } catch (Exception dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR_RESUMEN_X_NUMCONTROLDOC + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControlDoc: "+numControlDoc);
            throw new SIATException(dae);
        }
        return objeto;
    }
    
    @Override
    public ResumenRevisionVO consultarResumenCont(String numControlDoc) throws SIATException {
        ResumenRevisionVO objeto = null;
        try {
            objeto=jdbcTemplateDYC.queryForObject(CONSULTAR_RESUMENCONT_X_NUMCONTROLDOC, new Object[]{numControlDoc} ,new ResumenRevisorCentralMapper());
            
        } catch (Exception dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR_RESUMENCONT_X_NUMCONTROLDOC + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    " numControlDoc: "+numControlDoc);
            throw new SIATException(dae);
        }
        return objeto;
    }
    /**
     * Consulta todas aquellas solicitudes que estan pendientes por revisar por parte del revisor central y que se 
     * encuentran en el  padrón de RFC no confiables y los agrega en el listado de revision central, 
     *
     * @return
     * @throws SIATException
     */
    @Override
    public List<RevisionCentralVO> consultarDelPadronDeNoConfiables(Integer bandera) throws SIATException {
        String query = "";
        List<RevisionCentralVO> listaDeDocumentos = null;
        try {
            if (bandera.equals(0)) {
                query=CONSULTAR_DEL_PADRON_NO_CONFIABLE_RCENTRAL;
            } else {
                query=CONSULTAR_DEL_PADRON_NO_CONFIABLE_LEGALES;
            }
            listaDeDocumentos = jdbcTemplateDYC.query(query, new RevisionCentralMapper());
            
        } catch (Exception dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    query);
            throw new SIATException(dae);
        }
        return listaDeDocumentos;
    }
}
