package mx.gob.sat.siat.dyc.template.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.template.dto.TemplateNumberDTO;
import mx.gob.sat.siat.dyc.template.dto.URLCompleteTemplateDTO;
import mx.gob.sat.siat.dyc.template.dto.URLTemplateDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * DAO interface  for  table called matdocumentos
 * @author Yolanda Martínez Sánchez
 * @date March  12th,2014
 * @since 0.1
 * *
 * */

public interface TemplateNumberDAO {

    /**
     * @return List TemplateNumberDTO
     * @throws SIATException
     * @throws Exception
     */
    List<TemplateNumberDTO> itemsOfTemplateDAO(Integer queryByConsult, String idTemplate,
                                               String numControl) throws SIATException;

    URLTemplateDTO itemsOfUrlTemplateDAO(Integer idTemplate) throws SIATException;

    URLCompleteTemplateDTO itemsOfUrlOfCompleteTemplateDAO(String idDocumentoReq) throws SIATException;


}
