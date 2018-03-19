package mx.gob.sat.siat.dyc.template.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.template.dao.TemplateNumberDAO;
import mx.gob.sat.siat.dyc.template.dao.impl.mapper.TemplateNumberMapper;
import mx.gob.sat.siat.dyc.template.dao.impl.mapper.URLCompleteTemplateMapper;
import mx.gob.sat.siat.dyc.template.dao.impl.mapper.URLTemplateMapper;
import mx.gob.sat.siat.dyc.template.dto.TemplateNumberDTO;
import mx.gob.sat.siat.dyc.template.dto.URLCompleteTemplateDTO;
import mx.gob.sat.siat.dyc.template.dto.URLTemplateDTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPlantillador;
import mx.gob.sat.siat.dyc.util.constante.SQLOracleDyCPlantillas;
import mx.gob.sat.siat.dyc.util.constante.SQLOracleDyCPlantillasComp;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository(value = "templateNumberDAO")
@Transactional
public class TemplateNumberDAOImpl implements TemplateNumberDAO, SQLOracleDyCPlantillas, ConstantesPlantillador {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    private static final Logger LOGGER_OF_SERVICE = Logger.getLogger(TemplateNumberDAOImpl.class);
    private URLTemplateDTO urlTemplateDTO;
    private URLCompleteTemplateDTO urlCompleteTemplateDTO;

    @Override
    public List<TemplateNumberDTO> itemsOfTemplateDAO(Integer queryByConsult, String idTemplate, String numControl) throws SIATException
    {
        try {
            String query = "";

            switch (queryByConsult) {
            case LABEL_ONE:    
                query = CONSULTA_PLANTILLA_1_PARTE1 + CONSULTA_PLANTILLA_1_PARTE2;
                break;
            case LABEL_TWO:
                query = CONSULTA_PLANTILLA_2_PARTE1 + CONSULTA_PLANTILLA_2_PARTE2;
                break;
            case LABEL_TWENTY_TWO:
                query = CONSULTA_PLANTILLA_22;
                break;
            case LABEL_ONE_HUNDRED_AND_THIRTY_ONE:
                query = SQLOracleDyCPlantillasComp.CONSULTA_PLANTILLA_131;
                break;
            case LABEL_SIXTY_SIX:
            case LABEL_ONE_HUNDRED_AND_FOURTEEN:
                query = CONSULTA_PLANTILLA_66;
                break;
            
            case LABEL_SEVENTY_FOUR:
            case LABEL_SEVENTY_FIVE:
            case LABEL_SEVENTY_SIX:
            case LABEL_SEVENTY_SEVEN:
            case LABEL_SEVENTY_NINE:
            case LABEL_EIGHTY_ONE:
            case LABEL_ONE_HUNDRED_AND_THIRTY_FOUR:
            case LABEL_ONE_HUNDRED_AND_THIRTY_SIX:
        
                query =
CONSULTA_PLANTILLA_76.replace("[PERSONA_IDENTIFICACION]", PERSONA_IDENTIFICACION).replace("[DOMICILIO]",
                                                                                          DOMICILIO_PARTES).replace("[PERSONA_UBICACION]",
                                                                                                                      PERSONA_UBICACION).replace("[COLS]",
                                                                                                                                                 "").replace("[JOIN]",
                                                                                                                                                             "");
                break;
            case LABEL_SEVENTY_EIGHT:
            case LABEL_EIGHTY_THREE:
            case LABEL_ONE_HUNDRED_AND_THIRTY_SEVEN:    
                query =
CONSULTA_PLANTILLA_76.replace("[PERSONA_IDENTIFICACION]", PERSONA_IDENTIFICACION).replace("[DOMICILIO]",
                                                                                          DOMICILIO_PARTES).replace("[PERSONA_UBICACION]",
                                                                                                                      PERSONA_UBICACION).replace("[COLS]",
                                                                                                                                                 COLS_JOIN_LIQUIDA).replace("[JOIN]",
                                                                                                                                                                            JOIN_LIQUIDACION);
                break;
            case LABEL_ONE_HUNDRED_AND_THIRTY_EIGHT:
                query = CONSULTA_PLANTILLA_138;
                break;
            default:
                break;

            }
            if (queryByConsult == LABEL_TWO) {
                return jdbcTemplateDYC.query(query, new Object[] { numControl, numControl }, new TemplateNumberMapper(Integer.valueOf(idTemplate)));

            } else {

                return jdbcTemplateDYC.query(query, new Object[] { numControl },
                                                                              new TemplateNumberMapper(Integer.valueOf(idTemplate)));
            }

        }
        catch (RuntimeException e) {
            LOGGER_OF_SERVICE.error(e);
            ManejadorLogException.getTraceError(e);
            throw new SIATException(e);
        }
    }


    @Override
    public URLTemplateDTO itemsOfUrlTemplateDAO(Integer idTemplate) throws SIATException{
        try {
            this.setUrlTemplateDTO(jdbcTemplateDYC.queryForObject(LOAD_URL_FROM_TEMPLATE_AND_CONFIGURATION,
                                                                            new Object[] { idTemplate },
                                                                            new URLTemplateMapper()));

        } catch (Exception ex) {
            LOGGER_OF_SERVICE.error("Ocurrio un error al ejecutar itemsOfUrlTemplateDAO; idTemplate ->" + idTemplate);
            ManejadorLogException.getTraceError(ex);
            throw new SIATException(ex);
        }
        return this.getUrlTemplateDTO();
    }

    @Override
    public URLCompleteTemplateDTO itemsOfUrlOfCompleteTemplateDAO(String idDocumentoReq) throws SIATException{
        try {

            this.setUrlCompleteTemplateDTO(jdbcTemplateDYC.queryForObject(CHANGE_TWO_ITEMS_FROM_TEMPLATE,
                                                                                    new Object[] { idDocumentoReq },
                                                                                    new URLCompleteTemplateMapper()));


        } catch (Exception ex) {

            LOGGER_OF_SERVICE.error(ex);
            throw new SIATException(ex);

        }
        return this.getUrlCompleteTemplateDTO();
    }

    public void setUrlTemplateDTO(URLTemplateDTO urlTemplateDTO) {
        this.urlTemplateDTO = urlTemplateDTO;
    }

    public URLTemplateDTO getUrlTemplateDTO() {
        return urlTemplateDTO;
    }

    public void setUrlCompleteTemplateDTO(URLCompleteTemplateDTO urlCompleteTemplateDTO) {
        this.urlCompleteTemplateDTO = urlCompleteTemplateDTO;
    }

    public URLCompleteTemplateDTO getUrlCompleteTemplateDTO() {
        return urlCompleteTemplateDTO;
    }
}

