package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.ResumenDevolucionDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl.mapper.ResumenDevolucionMapper;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.ResumenDevolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleAprobarDoc;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Ericka Janth Ibarra Ponce
 * @date 02/2014
 *
 *
 */
@Repository(value = "resumenDevolucionDAO")
public class ResumenDevolucionDAOImpl implements ResumenDevolucionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public ResumenDevolucionDAOImpl() {
        super();
    }
    private Logger log = Logger.getLogger(ResumenDevolucionDAOImpl.class.getName());

    /**
     * Obtiene la información de los datos que aparecerán en pantalla en el
     * resumen de la resolución.
     *
     * @param numControl
     * @param nombreDoc
     * @param sinDoc si es true; es un Trámite sin oficio de resolución
     * @return
     * @throws SIATException
     */
    @Override
    public List<ResumenDevolucionDTO> buscarResumen(String numControl, String nombreDoc, boolean sinDoc) throws SIATException {

        try {

            String queryResumen = (!sinDoc) ? (SQLOracleAprobarDoc.RESUMENDOCUMENTO_PARTE1 + SQLOracleAprobarDoc.RESUMENDOCUMENTO_PARTE2)
                    : (SQLOracleAprobarDoc.RESUMENSINDOCUMENTO_PARTE1 + SQLOracleAprobarDoc.RESUMENSINDOCUMENTO_PARTE2);

            List<ResumenDevolucionDTO> lResumenDevSolDTO
                    = jdbcTemplateDYC.query(queryResumen,
                            new Object[]{numControl, nombreDoc, numControl, nombreDoc},
                            new ResumenDevolucionMapper());
            if (lResumenDevSolDTO.isEmpty()) {
                log.error("No existen registros");
            }
            return lResumenDevSolDTO;

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleAprobarDoc.RESUMENDOCUMENTO_PARTE1
                    + SQLOracleAprobarDoc.RESUMENDOCUMENTO_PARTE2 + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + " numero Control " + numControl + " nombre Documento"
                    + nombreDoc);
            throw new SIATException(dae);
        }
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
