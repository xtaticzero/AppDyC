package mx.gob.sat.siat.dyc.service;

import java.util.List;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.vo.ConsultarExpedienteVO;


public interface DyctAnexoService {

    void insertarAnexo(ArchivoVO input) throws SIATException;

    List<ConsultarExpedienteVO> buscarDocsAnexos(String numControl);
}
