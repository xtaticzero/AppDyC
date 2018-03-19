/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.compensacion.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.DycpCompTempDAO;
import mx.gob.sat.siat.dyc.domain.compensacion.DycpCompTempDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author  Alfredo Ramirez
 * @since   16/07/2014
 */
@Repository
public class DycpCompTempDAOImpl implements DycpCompTempDAO{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DycpCompTempDAOImpl.class.getName());

    /**
     * Inserta una nueva Compensacion Temporal
     * @autor Alfredo Ramirez
     * @param dycpCompTempDTO
     */
    @Override
    public void insertar(DycpCompTempDTO dycpCompTempDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_COMPTEMP.toString(),
                                   new Object[] { dycpCompTempDTO.getDycpAvisoCompTempDTO().getFolioAvisoTemp(),
                                                  dycpCompTempDTO.getRfcContribuyente(),
                                                  dycpCompTempDTO.getImpCompDecla(),
                                                  dycpCompTempDTO.getFechaPresentaDec(),
                                                  dycpCompTempDTO.getNumOperacionDec(),
                                                  dycpCompTempDTO.getIdEjercicio(), dycpCompTempDTO.getIdPeriodo(),
                                                  dycpCompTempDTO.getIdTipoDeclaracion(),
                                                  dycpCompTempDTO.getClaveAdm(),
                                                  dycpCompTempDTO.getDyctServicioTempDTO().getFolioServTemp(),
                                                  dycpCompTempDTO.getIdConcepto() });
            log.info("Se guardo en dycp_compTemp : " + dycpCompTempDTO.getDycpAvisoCompTempDTO().getFolioAvisoTemp());
        } catch (DataAccessException siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAR_COMPTEMP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dycpCompTempDTO));
            ManejadorLogException.getTraceError(siatE);
            throw new SIATException(siatE);
        }
    }

    @Override
    public List<String> buscarPorFolioAvisoTemp(String folioAviso) throws SIATException {
        String folioAvisoNuevo = null;
        if(folioAviso.startsWith("F")){
            folioAvisoNuevo = folioAviso.replace("F-", "");
        }
        List<String> folioServTemp;
        try {
            folioServTemp =
                    jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTA_FOLIOSERVTEMP_POR_FOLIOAVISO.toString(), new Object[] { folioAvisoNuevo },
                                                 String.class);
        } catch (DataAccessException siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_FOLIOSERVTEMP_POR_FOLIOAVISO.toString()+
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + folioAvisoNuevo);
            ManejadorLogException.getTraceError(siatE);
            throw new SIATException(siatE);
        }
        return folioServTemp;
    }
}
