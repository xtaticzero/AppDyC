package mx.gob.sat.siat.dyc.dao.archivo.impl;

import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoInfReqDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctArchivoInfReqDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dyctArchivoInfReqDAO")
public class DyctArchivoInfReqDAOImpl implements DyctArchivoInfReqDAO {
    public DyctArchivoInfReqDAOImpl() {
        super();
    }
    private static final Logger LOGGER = Logger.getLogger(DyctArchivoInfReqDAOImpl.class);
    private static final String INSERTAR ="INSERT INTO Dyct_ArchivoInfReq (IDARCHIVOINFREQ, URL, NOMBREARCHIVO, IDTIPOTRAMITE, IDINFOAREQUERIR, NUMCONTROLDOC) VALUES((DYCQ_IDARCHINFOREQ.NEXTVAL),?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Inserta un nuevo registro en base de datos.
     *
     * @param dyctArchivoInfReqDTO
     * @throws SIATException
     */
    @Override
    public void insertar(DyctArchivoInfReqDTO dyctArchivoInfReqDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(INSERTAR, new Object[] { 
                                                 dyctArchivoInfReqDTO.getUrl(), 
                                                 dyctArchivoInfReqDTO.getNombreArchivo(),
                                                 dyctArchivoInfReqDTO.getDyctInfoRequeridaDTO().getDyccInfoTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                 dyctArchivoInfReqDTO.getDyctInfoRequeridaDTO().getDyccInfoTramiteDTO().getDyccInfoARequerirDTO().getIdInfoArequerir(),
                                                 dyctArchivoInfReqDTO.getDyctInfoRequeridaDTO().getDyctReqInfoDTO().getNumControlDoc() 
                                             }
                                  );
                                  
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "parametros: " + 
                         ExtractorUtil.ejecutar(dyctArchivoInfReqDTO));
            throw new SIATException(e);
        }
    }
}