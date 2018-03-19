package mx.gob.sat.siat.dyc.dao.fallo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.fallo.DyctFalloMensajesDAO;
import mx.gob.sat.siat.dyc.dao.fallo.impl.mapper.DyctFalloMensajesMapper;
import mx.gob.sat.siat.dyc.dao.fallo.impl.mapper.NotificacionMapper;
import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.NotificacionVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Softtek
 *
 */
@Repository(value = "dyccFalloMensajesDAO")
public class DyctFalloMensajesDAOImpl implements DyctFalloMensajesDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOGGER = Logger.getLogger(DyctFalloMensajesDAOImpl.class);

    private static final String LOG_FORMAT_GENERAL_STRING = "%s %s %s %s %s %s";
    private static final String FORMAT_SEARCH_STRING = "%%%s%%";
    private static final String FIND_DYCT_FALLOMENSAJES = "SELECT * FROM DYCT_FALLOMENSAJES WHERE IDFALLOMENSAJE=?";
    private static final String COUNTBYIDFALLODETALLE_DYCT_FALLOMENSAJES =
            "SELECT COUNT(fa.IDFALLOMENSAJE) FROM DYCT_FALLOMENSAJES fa INNER JOIN dyct_documento doc ON (doc.NUMCONTROLDOC=fa.MENSAJE)  WHERE  doc.nombrearchivo not like '%.pdf' AND fa.IDFALLODETALLE=? AND fa.CVEUNIDADADMTVA LIKE ? AND fa.NUMCONTROL LIKE ?";
    private static final String FINDBYIDFALLODETALLE_DYCT_FALLOMENSAJES =
        "SELECT * FROM DYCT_FALLOMENSAJES WHERE IDFALLODETALLE=? ORDER BY CVEUNIDADADMTVA";
    private static final String FINDBYIDFALLODETALLE_DYCT_FALLOMENSAJES_PAGINADOR =
             "SELECT * FROM (SELECT fa.IDFALLOMENSAJE, fa.MENSAJE, TO_CHAR(fa.HORA, 'DD/MM/YYYY HH24:MI:SS') AS HORA, fa.IDFALLODETALLE, fa.TIPODOCUMENTO, fa.ACTOADMINISTRATIVO, fa.CVEUNIDADADMTVA, fa.NUMCONTROL, row_number() OVER (ORDER BY fa.CVEUNIDADADMTVA, fa.NUMCONTROL) rn FROM DYCT_FALLOMENSAJES fa INNER JOIN dyct_documento doc ON (doc.NUMCONTROLDOC=fa.MENSAJE)  WHERE  doc.nombrearchivo not like '%.pdf' AND fa.IDFALLODETALLE=? AND fa.CVEUNIDADADMTVA LIKE ? AND fa.NUMCONTROL LIKE ? ORDER BY fa.CVEUNIDADADMTVA) WHERE rn BETWEEN ? AND ? ORDER BY rn";
    private static final String FINDBYMENSAJE_DYCT_FALLOMENSAJES =
        "SELECT * FROM DYCT_FALLOMENSAJES WHERE MENSAJE LIKE ?";
    private static final String GETALL_DYCT_FALLOMENSAJES = "SELECT * FROM DYCT_FALLOMENSAJES";
    private static final String INSERT_DYCT_FALLOMENSAJES =
        "INSERT INTO DYCT_FALLOMENSAJES(IDFALLOMENSAJE, MENSAJE, HORA, TIPODOCUMENTO, ACTOADMINISTRATIVO, CVEUNIDADADMTVA, NUMCONTROL, IDFALLODETALLE) VALUES(?, ?, sysdate, ?, ?, ?, ?, ?)";
    private static final String DELETE_DYCT_FALLOMENSAJES = "DELETE FROM DYCT_FALLOMENSAJES WHERE IDFALLOMENSAJE=?";
    private static final String NEXTID_DYCC_FALLOMENSAJES = "SELECT DYCQ_IDFALLOMENSAJE.NEXTVAL FROM DUAL";
    private static final String GETALL_NOTIFICACIONES =
        "SELECT DYCC_FALLODETALLE.IDFALLODETALLE AS IDFALLODETALLE, DYCC_FALLOCOLAS.DESCRIPCION AS ORIGEN, DYCC_FALLODETALLE.DESCRIPCION AS MENSAJE, COUNT(DYCC_FALLODETALLE.DESCRIPCION) AS TOTAL FROM DYCT_FALLOMENSAJES INNER JOIN dyct_documento doc  ON (doc.NUMCONTROLDOC=DYCT_FALLOMENSAJES.MENSAJE) LEFT JOIN DYCC_FALLODETALLE ON DYCT_FALLOMENSAJES.IDFALLODETALLE = DYCC_FALLODETALLE.IDFALLODETALLE LEFT JOIN DYCC_FALLOCOLAS ON DYCC_FALLODETALLE.IDFALLOCOLAS = DYCC_FALLOCOLAS.IDFALLOCOLAS WHERE  doc.nombrearchivo not like '%.pdf'  GROUP BY DYCC_FALLODETALLE.IDFALLODETALLE, DYCC_FALLOCOLAS.DESCRIPCION, DYCC_FALLODETALLE.DESCRIPCION ORDER BY ORIGEN, MENSAJE";
    @Override
    public DyctFalloMensajesDTO find(Integer id) {
        DyctFalloMensajesDTO dyctFalloMensajesDTO = null;

        try {
            dyctFalloMensajesDTO =
                    jdbcTemplateDYC.queryForObject(FIND_DYCT_FALLOMENSAJES, new Object[] { id }, new DyctFalloMensajesMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s %s %d", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, FIND_DYCT_FALLOMENSAJES,
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO, id));
        }

        return dyctFalloMensajesDTO;
    }

    @Override
    public Long countByIdFalloDetalle(Integer idFalloDetalle, String cveUnidadAdmtva, String numControl) {
        Long count = 0L;
        try {
            count =
jdbcTemplateDYC.queryForObject(COUNTBYIDFALLODETALLE_DYCT_FALLOMENSAJES, new Object[] { idFalloDetalle,
                                                                                        String.format(FORMAT_SEARCH_STRING,
                                                                                                      cveUnidadAdmtva),
                                                                                        String.format(FORMAT_SEARCH_STRING,
                                                                                                      numControl) },
                               Long.class);
        } catch (DataAccessException dae) {
            LOGGER.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, COUNTBYIDFALLODETALLE_DYCT_FALLOMENSAJES,
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO,
                                       new StringBuilder().append(idFalloDetalle).append(ConstantesDyC1.CARACTER_COMA).append(cveUnidadAdmtva).append(ConstantesDyC1.CARACTER_COMA).append(numControl).toString()));
        }

        return count;
    }

    @Override
    public List<DyctFalloMensajesDTO> findByIdFalloDetalle(Integer idFalloDetalle) {
        List<DyctFalloMensajesDTO> dyctFalloMensajesDTOList = new ArrayList<DyctFalloMensajesDTO>();

        try {
            dyctFalloMensajesDTOList =
                    jdbcTemplateDYC.query(FINDBYIDFALLODETALLE_DYCT_FALLOMENSAJES, new Object[] { idFalloDetalle },
                                          new DyctFalloMensajesMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, FINDBYMENSAJE_DYCT_FALLOMENSAJES,
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO, idFalloDetalle));
        }

        return dyctFalloMensajesDTOList;
    }

    @Override
    public List<DyctFalloMensajesDTO> findByIdFalloDetallePaginador(Integer idFalloDetalle, String cveUnidadAdmtva,
                                                                    String numControl, Paginador paginador) {
        List<DyctFalloMensajesDTO> dyctFalloMensajesDTOList = new ArrayList<DyctFalloMensajesDTO>();

        try {
            dyctFalloMensajesDTOList =
                    jdbcTemplateDYC.query(FINDBYIDFALLODETALLE_DYCT_FALLOMENSAJES_PAGINADOR, new Object[] { idFalloDetalle,
                                                                                                            String.format(FORMAT_SEARCH_STRING,
                                                                                                                          cveUnidadAdmtva),
                                                                                                            String.format(FORMAT_SEARCH_STRING,
                                                                                                                          numControl),
                                                                                                            paginador.getFrom(),
                                                                                                            paginador.getTo() },
                                          new DyctFalloMensajesMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO,
                                       FINDBYIDFALLODETALLE_DYCT_FALLOMENSAJES_PAGINADOR,
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO,
                                       new StringBuilder().append(idFalloDetalle).append(ConstantesDyC1.CARACTER_COMA).append(cveUnidadAdmtva).append(ConstantesDyC1.CARACTER_COMA).append(numControl).toString()));
        }

        return dyctFalloMensajesDTOList;
    }

    @Override
    public DyctFalloMensajesDTO findByMensaje(String mensaje) {
        DyctFalloMensajesDTO dyccFalloDetalleDTO = null;

        try {
            dyccFalloDetalleDTO =
                    jdbcTemplateDYC.queryForObject(FINDBYMENSAJE_DYCT_FALLOMENSAJES, new Object[] { mensaje },
                                                   new DyctFalloMensajesMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, FINDBYMENSAJE_DYCT_FALLOMENSAJES,
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO, mensaje));
        }

        return dyccFalloDetalleDTO;
    }

    @Override
    public void insertFalloMensajes(DyctFalloMensajesDTO dyctFalloMensajesDTO) {
        try {
            dyctFalloMensajesDTO.setIdFalloMensaje(jdbcTemplateDYC.queryForObject(NEXTID_DYCC_FALLOMENSAJES,
                                                                                  Integer.class));
            jdbcTemplateDYC.update(INSERT_DYCT_FALLOMENSAJES,
                                   new Object[] { dyctFalloMensajesDTO.getIdFalloMensaje(), dyctFalloMensajesDTO.getMensaje(),
                                                  dyctFalloMensajesDTO.getTipoDocumento(),
                                                  dyctFalloMensajesDTO.getActoAdministrativo(),
                                                  dyctFalloMensajesDTO.getCveUnidadAdmtva(),
                                                  dyctFalloMensajesDTO.getNumControl(),
                                                  dyctFalloMensajesDTO.getIdFalloDetalle().getIdFalloDetalle() });
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, INSERT_DYCT_FALLOMENSAJES));
        }
    }

    @Override
    public void deleteFalloMensajes(Integer id) {
        try {
            jdbcTemplateDYC.update(DELETE_DYCT_FALLOMENSAJES, new Object[] { id });
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s %s %d", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, DELETE_DYCT_FALLOMENSAJES,
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO, id));
        }
    }

    @Override
    public List<DyctFalloMensajesDTO> getAllFalloMensajes() {
        List<DyctFalloMensajesDTO> dyctFalloMensajesDTOList = new ArrayList<DyctFalloMensajesDTO>();

        try {
            dyctFalloMensajesDTOList = jdbcTemplateDYC.query(GETALL_DYCT_FALLOMENSAJES, new DyctFalloMensajesMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, GETALL_DYCT_FALLOMENSAJES));
        }

        return dyctFalloMensajesDTOList;
    }


    @Override
    public List<NotificacionVO> getAllNotificaciones() {
        List<NotificacionVO> notificacionesVOList = new ArrayList<NotificacionVO>();

        try {
            notificacionesVOList = jdbcTemplateDYC.query(GETALL_NOTIFICACIONES, new NotificacionMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format("%s %s %s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, GETALL_NOTIFICACIONES));
        }

        return notificacionesVOList;
    }

    @Override
    public Long countDocumentosRezagados(String cveUnidadAdmtva, String numControl, Integer maxRezagados) {
        Long count = 0L;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(doc.numcontroldoc) FROM dyct_documento doc ");
        sql.append("INNER JOIN dyca_actoadmtvos actoadm ON actoadm.idplantilla = doc.idplantilla  ");
        sql.append("WHERE idestadodoc=2 AND nombrearchivo LIKE '%.docx' AND cveunidadadmtva LIKE ? AND numcontrol LIKE ? AND ROWNUM<=? ");

        try {
            count =
jdbcTemplateDYC.queryForObject(sql.toString(), new Object[] { String.format(FORMAT_SEARCH_STRING, cveUnidadAdmtva),
                                                              String.format(FORMAT_SEARCH_STRING, numControl),
                                                              maxRezagados }, Long.class);
        } catch (DataAccessException dae) {
            LOGGER.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, sql.toString(),
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO, maxRezagados));
        }

        return count;
    }

    @Override
    public List<DyctFalloMensajesDTO> obtenDocumentosRezagados(String cveUnidadAdmtva, String numControl,
                                                               Integer maxRezagados, Paginador paginador) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM( ");
        sql.append("SELECT 0 AS idfallomensaje, doc.numcontroldoc AS mensaje, 0 AS idfallodetalle, actoadm.cveunidadadmtva, actoadm.cveunidadadmtva AS actoadministrativo, doc.numcontrol, TO_CHAR(doc.fecharegistro, 'DD/MM/YYYY HH24:MI:SS') AS hora, actoadm.cveactoadmtvo, tipodoc.descripcion AS tipodocumento, row_number() OVER (ORDER BY fecharegistro) rn ");
        sql.append("FROM dyct_documento doc ");
        sql.append("INNER JOIN dyca_actoadmtvos actoadm ON actoadm.idplantilla = doc.idplantilla ");
        sql.append("INNER JOIN dycc_tipodocumento tipodoc ON tipodoc.idtipodocumento = doc.idtipodocumento ");
        sql.append("WHERE idestadodoc=2 AND nombrearchivo LIKE '%.docx' AND cveunidadadmtva LIKE ? AND numcontrol LIKE ? AND ROWNUM <= ? ");
        sql.append("ORDER BY fecharegistro ");
        sql.append(") WHERE rn BETWEEN ? AND ? ORDER BY rn");

        List<DyctFalloMensajesDTO> dyctFalloMensajesDTOList = new ArrayList<DyctFalloMensajesDTO>();

        try {
            dyctFalloMensajesDTOList =
                    jdbcTemplateDYC.query(sql.toString(), new Object[] { String.format(FORMAT_SEARCH_STRING,
                                                                                       cveUnidadAdmtva),
                                                                         String.format(FORMAT_SEARCH_STRING,
                                                                                       numControl), maxRezagados,
                                                                         paginador.getFrom(), paginador.getTo() },
                                          new DyctFalloMensajesMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(String.format(LOG_FORMAT_GENERAL_STRING, ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(),
                                       ConstantesDyC1.TEXTO_2_ERROR_DAO, sql.toString(),
                                       ConstantesDyC1.TEXTO_3_ERROR_DAO, cveUnidadAdmtva));
        }

        return dyctFalloMensajesDTOList;
    }
}
