/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.altex.impl;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.servicio.dao.altex.ConsultarContribuyenteAltamenteExportadorDAO;
import mx.gob.sat.siat.dyc.servicio.dao.altex.impl.mapper.ConsultarContribuyenteAltamenteExportadorDAOMapper;
import mx.gob.sat.siat.dyc.servicio.dao.altex.procedures.SpConsultarAltexStoredProcedure;
import mx.gob.sat.siat.dyc.servicio.dto.altex.ConsultarContribuyenteAltamenteExportadorDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constantebd.SQLInformixSIAT;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Esta clase reempla el Stored Procedure sp_sti_cdpcaeo1.sql
 * Consultar datos del padron de contribuyentes altamente exportadores.
 *
 * Los parametros de entrada estan descritos segun la siguiente informacion
 *
 * txtrfc varchar(13)     -- RFC parametro
 *
 * @author Israel Chavez
 * @since 24/07/2013
 *
 */
@Repository(value = "consultarContribuyenteAltamenteExportadorDAO")
public class ConsultarContribuyenteAltamenteExportadorDAOImpl implements ConsultarContribuyenteAltamenteExportadorDAO,
                                                                         SQLInformixSIAT {

    private Logger log = Logger.getLogger(ConsultarContribuyenteAltamenteExportadorDAOImpl.class.getName());

    public ConsultarContribuyenteAltamenteExportadorDAOImpl() {
        super();
    }

    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    /**
     * TODO
     * @param consultarContribuyenteAltamenteExportadorDTO
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public List<ConsultarContribuyenteAltamenteExportadorDTO> consultarContribuyenteAltamenteExportador(ConsultarContribuyenteAltamenteExportadorDTO consultarContribuyenteAltamenteExportadorDTO) {
        List<ConsultarContribuyenteAltamenteExportadorDTO> detalleContribuyenteALTEX =
            jdbcTemplateInformix.query(CONSULTA_CONTRIBUYENTES_ALTAMENTE_EXPORTADORES,
                                       new Object[] { consultarContribuyenteAltamenteExportadorDTO.getRfc(),
                                                      consultarContribuyenteAltamenteExportadorDTO.getRfc1(),
                                                      consultarContribuyenteAltamenteExportadorDTO.getRfc2(),
                                                      consultarContribuyenteAltamenteExportadorDTO.getNaltex() },
                                       new ConsultarContribuyenteAltamenteExportadorDAOMapper());
        return detalleContribuyenteALTEX;
    }

    public SpConsultarAltexDTO obtieneDatosAltexSP(SpConsultarAltexDTO spConsultarAltexDTO) throws SIATException {

        SpConsultarAltexStoredProcedure altexSP;

        altexSP = new SpConsultarAltexStoredProcedure(jdbcTemplateInformix, STORE_PROCEDURE_BUSCA_ALTEX);
        SpConsultarAltexDTO spConsultarAltex = new SpConsultarAltexDTO();
        log.info(spConsultarAltex);

        try {

            spConsultarAltex = altexSP.buscaAltex(spConsultarAltexDTO);

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                    STORE_PROCEDURE_BUSCA_ALTEX + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(spConsultarAltexDTO));
            throw dae;

        } catch (SQLException sqle) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sqle.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                    STORE_PROCEDURE_BUSCA_ALTEX + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(spConsultarAltexDTO));
            throw new SIATException(sqle);
        }

        return spConsultarAltex;
    }

}
