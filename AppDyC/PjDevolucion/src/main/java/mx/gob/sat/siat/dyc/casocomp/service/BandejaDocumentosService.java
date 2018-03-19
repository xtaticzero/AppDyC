package mx.gob.sat.siat.dyc.casocomp.service;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridDocumentosBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface BandejaDocumentosService
{
    List<FilaGridDocumentosBean> obtenerDocumentos(Map<String, Object> params);
    
    Map<String, Object> obtenerInfoDescargarDocumento(String numControlDoc) throws SIATException;
    
    Map<String, Object> enviarAAprobacion(String numControlDoc, Integer numEmpleadoAprob) throws SIATException;
}
