/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.sindocumento.impl.DyctResolSinDocumentoDAOImpl;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.BandejaSinDocumentosDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl.mapper.AprobarDocumentoMapper;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class BandejaSinDocumentosDAOImpl implements BandejaSinDocumentosDAO {

    private static final Logger LOG = Logger.getLogger(DyctResolSinDocumentoDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final String NOMBREDOCUMENTO = "H.DESCRIPCION AS NOMBREDOCUMENTO,  \n";
    private static final String NOMBREDICTAMINADOR = "M.NOMBRE ||' '|| M.APELLIDOPATERNO ||' '|| M.APELLIDOMATERNO AS NOMBREDICTAMINADOR,  \n";
    private static final String IDTIPOSERVICIO = "J.IDTIPOSERVICIO,  \n";
    private static final String TIPOSERVICIO = "J.DESCRIPCION AS TIPOSERVICIO,  \n";
    private static final String INCONSISTENCIA = "COUNT (K.IDINCONSISTENCIA) AS INCONSISTENCIA,  \n";
    private static final String IDTIPOPLAZO = "D.PLAZO AS DIAS,D.IDTIPOPLAZO,    \n";
    private static final String IDTIPODOCUMENTO = "H.IDTIPODOCUMENTO,  \n";
    private static final String CLAVEADM = "F.CLAVEADM,  \n";
    private static final String IDORIGENSALDO = "B.IDORIGENSALDO,  \n";
    private static final String NUMEMPLEADOAPROB = "G.NUMEMPLEADOAPROB,  \n";
    private static final String FECHAREGISTRO = "G.FECHAREGISTRO,  \n";
    private static final String CLAVENIVEL = "F.CLAVENIVEL, \n";
    private static final String DYCP_SERVICIO = "INNER JOIN SIAT_DYC_ADMIN.DYCP_SERVICIO      B ON (A.NUMCONTROL = B.NUMCONTROL)  \n";
    private static final String DYCT_CONTRIBUYENTE = "INNER JOIN SIAT_DYC_ADMIN.DYCT_CONTRIBUYENTE C ON (C.NUMCONTROL = B.NUMCONTROL)  \n";
    private static final String DYCC_TIPOTRAMITE = "INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPOTRAMITE   D ON (D.IDTIPOTRAMITE = B.IDTIPOTRAMITE)  \n";
    private static final String DYCC_APROBADOR = "INNER JOIN SIAT_DYC_ADMIN.DYCC_APROBADOR     F ON (F.NUMEMPLEADO = G.NUMEMPLEADOAPROB)  \n";
    private static final String DYCC_TIPODOCUMENTO = "INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPODOCUMENTO H ON (H.IDTIPODOCUMENTO = G.IDTIPODOCUMENTO)  \n";
    private static final String DYCC_TIPOSERVICIO = "INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPOSERVICIO  J ON (J.IDTIPOSERVICIO = b.IDTIPOSERVICIO)  \n";
    private static final String DYCC_DICTAMINADOR = "inner join SIAT_DYC_ADMIN.dycc_dictaminador  M on (M.NUMEMPLEADO = b.numEmpleadoDict)  \n";
    private static final String GROUP_BY = "GROUP BY (A.NUMCONTROL), B.RFCCONTRIBUYENTE, DECODE(C.ROLDICTAMINADO,1,'SI','NO'),  \n";
    private static final String CLAUSULA_DINAMICA_PARAM = "<clausula_dinamica>";

    private static final String CONSULTAR_SINDOC_X_APROBADOR = "select * from (\n"
            + "            select NUMCONTROL, RFC, ROLDICTAMINADO, IDTIPOTRAMITE, TIPOTRAMITE, FECHAPRESENTACION, NOMBREDOCUMENTO, NOMBREDICTAMINADOR, \n"
            + "                DICTAMINADOR, IMPORTESOLICITADO, IDTIPOSERVICIO, TIPOSERVICIO, INCONSISTENCIA, DIAS, IDTIPOPLAZO, \n"
            + "                IDTIPODOCUMENTO, RESOLAUTOMATICA, CLAVEADM, IDORIGENSALDO, NUMEMPLEADOAPROB, FECHAREGISTRO, CLAVENIVEL, AMPARADO, RAZONSOCIAL, \n"
            + "                SALDOAPLICAR, TIPOREQUERIMIENTO, IDSALDOICEP, ROW_NUMBER() OVER(ORDER BY NUMCONTROL ASC) AS rn from (\n"
            + "              (SELECT DISTINCT (A.NUMCONTROL) AS NUMCONTROL,  \n"
            + "    B.RFCCONTRIBUYENTE AS RFC,  \n"
            + "    DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO,  \n"
            + "    D.IDTIPOTRAMITE,  \n"
            + "    D.DESCRIPCION AS TIPOTRAMITE,  \n"
            + "    A.FECHAPRESENTACION,  \n"
            + NOMBREDOCUMENTO
            + NOMBREDICTAMINADOR
            + "DECODE(A.RESOLAUTOMATICA,1,'Preautorizada','') AS DICTAMINADOR,  \n"
            + "A.IMPORTESOLICITADO,  \n"
            + IDTIPOSERVICIO
            + TIPOSERVICIO
            + INCONSISTENCIA
            + IDTIPOPLAZO
            + IDTIPODOCUMENTO
            + "A.RESOLAUTOMATICA,  \n"
            + CLAVEADM
            + IDORIGENSALDO
            + NUMEMPLEADOAPROB
            + FECHAREGISTRO
            + CLAVENIVEL
            + "C.AMPARADO,  \n"
            + "EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') as RAZONSOCIAL, \n"
            + "0 AS SALDOAPLICAR, \n"
            + "'' AS TIPOREQUERIMIENTO, \n"
            + "0 AS IDSALDOICEP \n"
            + "FROM SIAT_DYC_ADMIN.dycp_solicitud A  \n"
            + DYCP_SERVICIO
            + DYCT_CONTRIBUYENTE
            + DYCC_TIPOTRAMITE
            + "INNER JOIN SIAT_DYC_ADMIN.DYCT_RESOLSINDOCUMENTO     G ON (G.NUMCONTROL = B.NUMCONTROL)\n"
            + DYCC_APROBADOR
            + DYCC_TIPODOCUMENTO
            + DYCC_TIPOSERVICIO
            + "LEFT JOIN SIAT_DYC_ADMIN.DYCA_SOLINCONSIST   K ON (K.NUMCONTROL = A.NUMCONTROL)  \n"
            + DYCC_DICTAMINADOR
            + "WHERE G.NUMEMPLEADOAPROB = ? \n"
            + CLAUSULA_DINAMICA_PARAM
            + "and G.idestadoreq = 1\n"
            + GROUP_BY
            + "        D.IDTIPOTRAMITE, D.DESCRIPCION, A.FECHAPRESENTACION, H.DESCRIPCION, M.NOMBRE ||' '|| M.APELLIDOPATERNO ||' '|| M.APELLIDOMATERNO,  \n"
            + "        DECODE(A.RESOLAUTOMATICA,1,'Preautorizada',''), A.IMPORTESOLICITADO, J.IDTIPOSERVICIO, J.DESCRIPCION, D.PLAZO, D.IDTIPOPLAZO,  \n"
            + "        H.IDTIPODOCUMENTO, A.RESOLAUTOMATICA, F.CLAVEADM, B.IDORIGENSALDO, G.NUMEMPLEADOAPROB,  \n"
            + "        G.FECHAREGISTRO, F.CLAVENIVEL, C.AMPARADO, EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') )\n"
            + "union all\n"
            + "(SELECT DISTINCT (A.NUMCONTROL) AS NUMCONTROL,\n"
            + "B.RFCCONTRIBUYENTE AS RFC,  \n"
            + "DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO,  \n"
            + "D.IDTIPOTRAMITE,  \n"
            + "D.DESCRIPCION AS TIPOTRAMITE,  \n"
            + "A.FECHAINICIOTRAMITE AS FECHAREGISTRO,  \n"
            + NOMBREDOCUMENTO
            + NOMBREDICTAMINADOR
            + "'' AS DICTAMINADOR,  \n"
            + "A.IMPORTECOMPENSADO,  \n"
            + IDTIPOSERVICIO
            + TIPOSERVICIO
            + INCONSISTENCIA
            + IDTIPOPLAZO
            + IDTIPODOCUMENTO
            + "0 AS RESOLAUTOMATICA,  \n"
            + CLAVEADM
            + IDORIGENSALDO
            + NUMEMPLEADOAPROB
            + FECHAREGISTRO
            + CLAVENIVEL
            + "C.AMPARADO, \n"
            + "EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') as RAZONSOCIAL, \n"
            + "N.IMPORTECOMPENSADO AS SALDOAPLICAR, \n"
            + "DECODE(P.DESCRIPCION, NULL, 'NO APLICA', P.DESCRIPCION) AS TIPOREQUERIMIENTO, \n"
            + "N.IDSALDOICEPORIGEN AS IDSALDOICEP \n"
            + "FROM SIAT_DYC_ADMIN.dycp_compensacion A  \n"
            + DYCP_SERVICIO
            + DYCT_CONTRIBUYENTE
            + DYCC_TIPOTRAMITE
            + "INNER JOIN SIAT_DYC_ADMIN.DYCT_RESOLSINDOCUMENTO     G ON (G.NUMCONTROL = B.NUMCONTROL)  \n"
            + DYCC_APROBADOR
            + DYCC_TIPODOCUMENTO
            + DYCC_TIPOSERVICIO
            + "LEFT  JOIN SIAT_DYC_ADMIN.DYCA_SOLINCONSIST  K ON (K.NUMCONTROL = A.NUMCONTROL)  \n"
            + DYCC_DICTAMINADOR
            + "INNER JOIN SIAT_DYC_ADMIN.DYCP_COMPENSACION  N ON (N.NUMCONTROL = B.NUMCONTROL) \n"
            + "LEFT  JOIN SIAT_DYC_ADMIN.DYCT_RESOLCOMP     O ON (O.NUMCONTROL = B.NUMCONTROL) \n"
            + "LEFT  JOIN SIAT_DYC_ADMIN.DYCC_TIPORESOL     P ON (O.IDTIPORESOL = P.IDTIPORESOL) \n"
            + "WHERE G.NUMEMPLEADOAPROB = ?\n"
            + CLAUSULA_DINAMICA_PARAM
            + "AND a.idestadocomp=8 and G.idestadoreq = 1 \n"
            + GROUP_BY
            + "        D.IDTIPOTRAMITE, D.DESCRIPCION, A.FECHAINICIOTRAMITE, H.DESCRIPCION, M.NOMBRE ||' '|| M.APELLIDOPATERNO ||' '|| M.APELLIDOMATERNO,  \n"
            + "        A.IMPORTECOMPENSADO, J.IDTIPOSERVICIO, J.DESCRIPCION, D.PLAZO, D.IDTIPOPLAZO,  \n"
            + "        H.IDTIPODOCUMENTO, RESOLAUTOMATICA, F.CLAVEADM, B.IDORIGENSALDO, G.NUMEMPLEADOAPROB,  \n"
            + "        G.FECHAREGISTRO, F.CLAVENIVEL, C.AMPARADO, EXTRACTVALUE(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial'), N.IMPORTECOMPENSADO, P.DESCRIPCION, N.IDSALDOICEPORIGEN\n"
            + "  ))) WHERE rn between ? and ? ";

    private static final String CONTAR_SINDOC_X_APROBADOR = "select count(*) from (select NUMCONTROL, RFC, ROLDICTAMINADO, IDTIPOTRAMITE, TIPOTRAMITE, FECHAPRESENTACION, NOMBREDOCUMENTO, NOMBREDICTAMINADOR, \n"
            + "                DICTAMINADOR, IMPORTESOLICITADO, IDTIPOSERVICIO, TIPOSERVICIO, INCONSISTENCIA, DIAS, IDTIPOPLAZO, \n"
            + "                IDTIPODOCUMENTO, RESOLAUTOMATICA, CLAVEADM, IDORIGENSALDO, NUMEMPLEADOAPROB, FECHAREGISTRO, CLAVENIVEL, AMPARADO, \n"
            + "                SALDOAPLICAR, TIPOREQUERIMIENTO, IDSALDOICEP from ( \n"
            + "              (SELECT DISTINCT (A.NUMCONTROL) AS NUMCONTROL,  \n"
            + "    B.RFCCONTRIBUYENTE AS RFC,  \n"
            + "    DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO,  \n"
            + "    D.IDTIPOTRAMITE,  \n"
            + "    D.DESCRIPCION AS TIPOTRAMITE,  \n"
            + "    A.FECHAPRESENTACION,  \n"
            + NOMBREDOCUMENTO
            + NOMBREDICTAMINADOR
            + "DECODE(A.RESOLAUTOMATICA,1,'Preautorizada','') AS DICTAMINADOR,  \n"
            + "A.IMPORTESOLICITADO,  \n"
            + IDTIPOSERVICIO
            + TIPOSERVICIO
            + INCONSISTENCIA
            + IDTIPOPLAZO
            + IDTIPODOCUMENTO
            + "A.RESOLAUTOMATICA,  \n"
            + CLAVEADM
            + IDORIGENSALDO
            + NUMEMPLEADOAPROB
            + FECHAREGISTRO
            + CLAVENIVEL
            + "C.AMPARADO,  \n"
            + "0 AS SALDOAPLICAR, \n"
            + "'' AS TIPOREQUERIMIENTO, \n"
            + "0 AS IDSALDOICEP \n"
            + "FROM SIAT_DYC_ADMIN.dycp_solicitud A  \n"
            + DYCP_SERVICIO
            + DYCT_CONTRIBUYENTE
            + DYCC_TIPOTRAMITE
            + "INNER JOIN SIAT_DYC_ADMIN.DYCT_RESOLSINDOCUMENTO     G ON (G.NUMCONTROL = B.NUMCONTROL)\n"
            + DYCC_APROBADOR
            + DYCC_TIPODOCUMENTO
            + DYCC_TIPOSERVICIO
            + "LEFT JOIN SIAT_DYC_ADMIN.DYCA_SOLINCONSIST   K ON (K.NUMCONTROL = A.NUMCONTROL)  \n"
            + DYCC_DICTAMINADOR
            + "WHERE G.NUMEMPLEADOAPROB = ? and G.idestadoreq = 1 \n"
            + CLAUSULA_DINAMICA_PARAM
            + GROUP_BY
            + "        D.IDTIPOTRAMITE, D.DESCRIPCION, A.FECHAPRESENTACION, H.DESCRIPCION, M.NOMBRE ||' '|| M.APELLIDOPATERNO ||' '|| M.APELLIDOMATERNO,  \n"
            + "        DECODE(A.RESOLAUTOMATICA,1,'Preautorizada',''), A.IMPORTESOLICITADO, J.IDTIPOSERVICIO, J.DESCRIPCION, D.PLAZO, D.IDTIPOPLAZO,  \n"
            + "        H.IDTIPODOCUMENTO, A.RESOLAUTOMATICA, F.CLAVEADM, B.IDORIGENSALDO, G.NUMEMPLEADOAPROB,  \n"
            + "        G.FECHAREGISTRO, F.CLAVENIVEL, C.AMPARADO )\n"
            + "union \n"
            + "(SELECT DISTINCT (A.NUMCONTROL) AS NUMCONTROL,\n"
            + "B.RFCCONTRIBUYENTE AS RFC,  \n"
            + "DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO,  \n"
            + "D.IDTIPOTRAMITE,  \n"
            + "D.DESCRIPCION AS TIPOTRAMITE,  \n"
            + "A.FECHAINICIOTRAMITE AS FECHAREGISTRO,  \n"
            + NOMBREDOCUMENTO
            + NOMBREDICTAMINADOR
            + "'' AS DICTAMINADOR,  \n"
            + "A.IMPORTECOMPENSADO,  \n"
            + IDTIPOSERVICIO
            + TIPOSERVICIO
            + INCONSISTENCIA
            + IDTIPOPLAZO
            + IDTIPODOCUMENTO
            + "0 AS RESOLAUTOMATICA,  \n"
            + CLAVEADM
            + IDORIGENSALDO
            + NUMEMPLEADOAPROB
            + FECHAREGISTRO
            + CLAVENIVEL
            + "C.AMPARADO, \n"
            + "N.IMPORTECOMPENSADO AS SALDOAPLICAR, \n"
            + "DECODE(P.DESCRIPCION, NULL, 'NO APLICA', P.DESCRIPCION) AS TIPOREQUERIMIENTO, \n"
            + "N.IDSALDOICEPORIGEN AS IDSALDOICEP \n"
            + "FROM SIAT_DYC_ADMIN.dycp_compensacion A  \n"
            + DYCP_SERVICIO
            + DYCT_CONTRIBUYENTE
            + DYCC_TIPOTRAMITE
            + "INNER JOIN SIAT_DYC_ADMIN.DYCT_RESOLSINDOCUMENTO     G ON (G.NUMCONTROL = B.NUMCONTROL)  \n"
            + DYCC_APROBADOR
            + DYCC_TIPODOCUMENTO
            + DYCC_TIPOSERVICIO
            + "LEFT  JOIN SIAT_DYC_ADMIN.DYCA_SOLINCONSIST  K ON (K.NUMCONTROL = A.NUMCONTROL)  \n"
            + DYCC_DICTAMINADOR
            + "INNER JOIN SIAT_DYC_ADMIN.DYCP_COMPENSACION  N ON (N.NUMCONTROL = B.NUMCONTROL) \n"
            + "LEFT  JOIN SIAT_DYC_ADMIN.DYCT_RESOLCOMP     O ON (O.NUMCONTROL = B.NUMCONTROL) \n"
            + "LEFT  JOIN SIAT_DYC_ADMIN.DYCC_TIPORESOL     P ON (O.IDTIPORESOL = P.IDTIPORESOL) \n"
            + "WHERE G.NUMEMPLEADOAPROB = ?\n"
            + CLAUSULA_DINAMICA_PARAM
            + "AND a.idestadocomp=8 and G.idestadoreq = 1 \n"
            + GROUP_BY
            + "        D.IDTIPOTRAMITE, D.DESCRIPCION, A.FECHAINICIOTRAMITE, H.DESCRIPCION, M.NOMBRE ||' '|| M.APELLIDOPATERNO ||' '|| M.APELLIDOMATERNO,  \n"
            + "        A.IMPORTECOMPENSADO, J.IDTIPOSERVICIO, J.DESCRIPCION, D.PLAZO, D.IDTIPOPLAZO,  \n"
            + "        H.IDTIPODOCUMENTO, RESOLAUTOMATICA, F.CLAVEADM, B.IDORIGENSALDO, G.NUMEMPLEADOAPROB,  \n"
            + "        G.FECHAREGISTRO, F.CLAVENIVEL, C.AMPARADO, N.IMPORTECOMPENSADO, P.DESCRIPCION, N.IDSALDOICEPORIGEN\n"
            + "  )))";

    @Override
    public List<BandejaDocumentosDTO> consultarTramitesBandeja(Object[] parametros, String clausula) throws DAOException {
        try {
            String queryDatos = CONSULTAR_SINDOC_X_APROBADOR;
            String query = queryDatos.replace(CLAUSULA_DINAMICA_PARAM, clausula);
            StringBuilder paramsRecibidos = new StringBuilder();
            for (Object parametro : parametros) {
                paramsRecibidos.append(parametro);
                paramsRecibidos.append("|||");
            }
            LOG.error("parametros recibidos:" + paramsRecibidos.toString());
            return (List<BandejaDocumentosDTO>) jdbcTemplateDYC.query(query, parametros, new AprobarDocumentoMapper());
        } catch (EmptyResultDataAccessException e) {
            LOG.info(e);
            return new ArrayList<BandejaDocumentosDTO>();
        } catch (DataAccessException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public Long countBuscarBandejaDoc(Object[] parametros, String clausula) throws SIATException {
        Long count = 0L;

        try {
            String queryConteo = CONTAR_SINDOC_X_APROBADOR;
            String query = queryConteo.replace(CLAUSULA_DINAMICA_PARAM, clausula);
            StringBuilder paramsRecibidos = new StringBuilder();
            for (Object parametro : parametros) {
                paramsRecibidos.append(parametro);
                paramsRecibidos.append("|||");
            }
            LOG.error("parametros recibidos:" + paramsRecibidos.toString());
            count = jdbcTemplateDYC.queryForObject(query, parametros, Long.class);

        } catch (DataAccessException dae) {
            LOG.error(dae);
            throw new SIATException(dae);
        }

        return count;
    }
}
