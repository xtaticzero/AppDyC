/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.sindocumento.impl;

import mx.gob.sat.siat.dyc.dao.sindocumento.DyctResolSinDocumentoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolSinDocumentoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository("dyctResolSinDocumentoDAOImpl")
public class DyctResolSinDocumentoDAOImpl implements DyctResolSinDocumentoDAO {

    private static final Logger LOG = Logger.getLogger(DyctResolSinDocumentoDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final String SQL_RESOL_SINDOC = " INSERT INTO DYCT_RESOLSINDOCUMENTO( IDRESOLSINDOCUMENTO, NUMCONTROL, FECHAREGISTRO, "
            + "IDTIPODOCUMENTO, IDESTADOREQ, NUMEMPLEADOAPROB)"
            + " VALUES (?,?,sysdate,?,?,?)";

    private static final String APROBAR_RESOL_SINDOC = "UPDATE DYCT_RESOLSINDOCUMENTO SET IDESTADOREQ = ? WHERE NUMCONTROL = ? AND IDESTADOREQ = 1";

    private static final String OBTENER_SECUENCIA = "SELECT DYCQ_RESOLSINDOCUMENTO.NEXTVAL FROM DUAL";
    
    private static final String ACTUALIZADOCUMNTOREQ = "UPDATE DYCT_RESOLSINDOCUMENTO SET NUMEMPLEADOAPROB = ? WHERE NUMCONTROL = ? AND IDESTADOREQ = ?";
    
    @Override
    public void insertarResolucionSinDoc(DyctResolSinDocumentoDTO resolucionSinDoc) throws SIATException {

        Integer idResolSinDoc = null;

        try {
            idResolSinDoc = this.jdbcTemplateDYC.queryForObject(OBTENER_SECUENCIA, Integer.class);
            this.jdbcTemplateDYC.update(SQL_RESOL_SINDOC,
                    new Object[]{idResolSinDoc,
                        resolucionSinDoc.getNumControl(),
                        resolucionSinDoc.getIdTipoDocumento(),
                        resolucionSinDoc.getIdEstadoReq(),
                        resolucionSinDoc.getIdAprobador()});

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQL_RESOL_SINDOC + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(resolucionSinDoc));
            throw new SIATException("Error al ejecutar DyctResolSinDocumentoDAOImpl.insertarResolucionSinDoc", dae);
        } catch (Exception dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQL_RESOL_SINDOC + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(resolucionSinDoc));
            throw new SIATException("Error al ejecutar DyctResolSinDocumentoDAOImpl.insertarResolucionSinDoc", dae);

        }
    }

    @Override
    public void aprobarResolucionSinDoc(String numControl, Integer idEstadoReq) throws SIATException {
        try {
            jdbcTemplateDYC.update(APROBAR_RESOL_SINDOC,
                    new Object[]{idEstadoReq, numControl});
        } catch (Exception siatE) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + APROBAR_RESOL_SINDOC + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
            throw new SIATException(siatE);
        }
    }

    @Override
    public void updateSinDocumento(Integer numAprobador, String numControl, Integer idEstadoDocumento) throws SIATException {
        try {
            
            jdbcTemplateDYC.update(ACTUALIZADOCUMNTOREQ,
                    new Object[]{numAprobador, numControl, idEstadoDocumento});

        } catch (Exception dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + ACTUALIZADOCUMNTOREQ + ConstantesDyC1.TEXTO_3_ERROR_DAO + numAprobador
                    + numControl);
            throw new SIATException(dae);
        }
    }

}
