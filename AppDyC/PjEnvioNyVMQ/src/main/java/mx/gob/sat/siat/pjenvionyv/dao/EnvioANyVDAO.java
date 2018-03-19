package mx.gob.sat.siat.pjenvionyv.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface EnvioANyVDAO {
    List<DyctDocumentoDTO> listarDocumentosAEnviar() throws SIATException;
    void actualizarRutaDocumentoPdf(String rutaDocumento, String numControlDoc) throws SIATException;
}
