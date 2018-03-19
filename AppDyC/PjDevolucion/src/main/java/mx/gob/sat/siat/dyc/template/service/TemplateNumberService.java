package mx.gob.sat.siat.dyc.template.service;


import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.template.dto.TemplateNumberDTO;
import mx.gob.sat.siat.dyc.template.dto.URLTemplateDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.pjplantillador.dto.Items;


public interface TemplateNumberService {

    TemplateNumberDTO getTemplateNumber(Integer queryByConsult, String idTemplate, String numControl) throws SIATException;

    URLTemplateDTO getURLTemplate(Integer idTemplate) throws SIATException;
    Map<String, Object> templateCreated(Map<String, Object> in) throws SIATException;

    Map<String, Object> toCreate(List<Items> entryLoad, String idTemplate, String claveAdministracion, String rfc,
                             String numControl, String numeroDocumento) throws SIATException;

    Map<String, Object> createTemplateOfJustMap(Map<String, Object> introMap) throws SIATException;

    Boolean isCompletarDocumento(String selloDigital, String cadenaOriginal, String firmaDigital, String idDocumentoReq,String employeeName, Map<String, Object>  datosQr) throws SIATException;

}
