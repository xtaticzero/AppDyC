package mx.gob.sat.siat.dyc.dao.compensacion.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.DycpAvisoCompDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpAvisoCompMapper;
import mx.gob.sat.siat.dyc.dao.tiposerv.impl.DyccTipoAvisoDAOImpl;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 03,Diciembre 2013
 */
@Repository
public class DycpAvisoCompDAOImpl implements DycpAvisoCompDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccTipoAvisoDAOImpl.class);

    @Override
    public DycpAvisoCompDTO encontrar(String folioAviso)  throws SIATException
    {
        String query = "SELECT * FROM DYCP_AVISOCOMP WHERE FOLIOAVISO = ?";
        try {
            return jdbcTemplateDYC.queryForObject(query, new Object[] { folioAviso },
                                                new DycpAvisoCompMapper());
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            log.debug("NO se encontro aviso de compensación con el folio ->" + folioAviso);
            return null;
        }
        catch (DataAccessException dae)
        {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      query + ConstantesDyC1.TEXTO_3_ERROR_DAO + folioAviso);
            throw new SIATException(dae);
        }
    }

    @Override
    public Integer obtenerSecuencia() {
        Integer secuencia;
        try {
            secuencia =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.OBTENER_SECUENCIA_DYCP_AVISOCOMP.toString(), new Object[] { }, Integer.class);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.OBTENER_SECUENCIA_DYCP_AVISOCOMP);
            throw dae;
        }
        return secuencia;
    }

    @Override
    public void insertar(DycpAvisoCompDTO avisoComp) throws SIATException
    {
        String sentInsert =
            " INSERT INTO DYCP_AVISOCOMP(FOLIOAVISO, FOLIOAVISONORMAL, IDTIPOAVISO, CADENAORIGINAL,  SELLODIGITAL, AVISONORMALEXTERNO) VALUES (?, ?, ?, ?, ?, ?)";
            
        
        try {
            String folioAvisoNormal = avisoComp.getDycpAvisoCompNormalDTO() != null ? avisoComp.getDycpAvisoCompNormalDTO().getFolioAviso() : null;

            jdbcTemplateDYC.update(sentInsert,
                                   new Object[] { avisoComp.getFolioAviso(),
                                                  folioAvisoNormal,
                                                  avisoComp.getDyccTipoAvisoDTO().getIdTipoAviso(),
                                                  avisoComp.getCadenaOriginal(),
                                                  avisoComp.getSelloDigital(),
                                                  avisoComp.getAvisoNormalExterno()});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sentInsert +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(avisoComp));
            throw dae;
        }
    }

    @Override
    public DycpAvisoCompDTO buscaFolioAvisoXFolioNormalYRFC(String folioAviso, String rfcContribuyente) {
        DycpAvisoCompDTO dycpAvisoCompDTO = new DycpAvisoCompDTO();

        try {

            List<DycpAvisoCompDTO> dycpAviso =
                jdbcTemplateDYC.query(SQLOracleDyC.BUSCAFOLIOX_FOLIONORMALYRFC.toString(), new Object[] { folioAviso, rfcContribuyente},
                                                new DycpAvisoCompMapper());
            if (!dycpAviso.isEmpty()) {
                dycpAvisoCompDTO = dycpAviso.get(ConstantesDyCNumerico.VALOR_0);

            }
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.BUSCAFOLIOX_FOLIONORMALYRFC + ConstantesDyC1.TEXTO_3_ERROR_DAO + folioAviso);
        }
        return dycpAvisoCompDTO;
    }

    @Override
    public List<DycpAvisoCompDTO> selecXAvisonormal(DycpAvisoCompDTO avisoNormal)
    {
        String query = " SELECT * FROM DYCP_AVISOCOMP WHERE FOLIOAVISONORMAL = ?" ;

        DycpAvisoCompMapper mapper = new DycpAvisoCompMapper();
        mapper.setAvisoNormal(avisoNormal);

        return jdbcTemplateDYC.query(query, new Object[] { avisoNormal.getFolioAviso() }, mapper);
    }
    
    @Override
        public DycpAvisoCompDTO selectEstatusXRFC(String rfc) {
            DycpAvisoCompDTO dycpAvisoDTO = null;
            List<DycpAvisoCompDTO> lstDycpAviso = null;
            final String query ="SELECT a.* FROM DYCP_SERVICIO p \n" + 
            " INNER JOIN DYCP_COMPENSACION c ON c.numControl =  p.numControl\n" + 
            " INNER JOIN DYCP_AVISOCOMP a ON a.FOLIOAVISO = c.FOLIOAVISO\n" + 
            " WHERE p.RFCCONTRIBUYENTE =? \n" + 
            " AND c.IDESTADOCOMP in (3,6,8)";
            try{
                lstDycpAviso =
                jdbcTemplateDYC.query(query, new Object[] { rfc },
                                                new DycpAvisoCompMapper());
            }catch (DataAccessException dae) {
                log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                          query + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc);
                throw dae;
            }
            if(!lstDycpAviso.isEmpty()){
                dycpAvisoDTO = lstDycpAviso.get(ConstantesDyC1.CERO);
            }
            return dycpAvisoDTO;
        }
    
}
