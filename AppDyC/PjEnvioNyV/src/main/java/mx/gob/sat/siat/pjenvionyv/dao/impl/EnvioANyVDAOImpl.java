package mx.gob.sat.siat.pjenvionyv.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.impl.mapper.DyctDocumentoMapper;
import mx.gob.sat.siat.dyc.domain.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.pjenvionyv.dao.EnvioANyVDAO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "envioANyVDAO")
public class EnvioANyVDAOImpl implements EnvioANyVDAO {

    private static final String QUERY="select /*+ index(dyd IDX_DOCUMENTO_IDESTADODOC) */ "
            + "dyd.NUMCONTROLDOC,dyd.IDTIPODOCUMENTO,dyd.NUMCONTROL,dyd.URL,dyd.FECHAREGISTRO,dyd.NOMBREARCHIVO,"
            + "dyd.IDESTADODOC,dyd.IDESTADOREQ,dyd.IDPLANTILLA,dyd.FECHAINIPLAZOLEGAL,dyd.FECHAFINPLAZOLEGAL,"
            + "dyd.IDTIPOFIRMA,dyd.NUMEMPLEADOAPROB,dyd.FOLIONYV "
            + "from dyct_documento dyd where dyd.idestadodoc=2 AND dyd.idtipofirma=2 and dyd.folionyv is null";
    private static final Logger LOGGER = Logger.getLogger(EnvioANyVDAOImpl.class);
    private static final String CAMBIAR_URL   = "UPDATE DYCT_DOCUMENTO SET URL = ? WHERE NUMCONTROLDOC= ?";
    
    @Autowired
    @Qualifier(value = "jdbcTemplateDYC")
    private JdbcTemplate jdbcTamplateDYC;

    public EnvioANyVDAOImpl() {
        super();
    }

    /**
     * Lista todos los documentos a enviar para NyV (son todos aquellos documentos que estan en estatus 2 de aprobado, 
     * que tienen firma autógrafa y que no tienen un folio NyV asignado).
     *
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyctDocumentoDTO> listarDocumentosAEnviar() throws SIATException {
        List<DyctDocumentoDTO> listaDeDocumentosAEnviar = null;
        try {
            listaDeDocumentosAEnviar = jdbcTamplateDYC.query(QUERY, new DyctDocumentoMapper());
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC.TEXTO_2_ERROR_DAO +
                     QUERY + ConstantesDyC.TEXTO_3_ERROR_DAO);
            throw new SIATException(e);
        }
        return listaDeDocumentosAEnviar;
    }
    
    @Override
     public void actualizarRutaDocumentoPdf(String rutaDocumento, String numControlDoc) throws SIATException {
         try {
             
             
             jdbcTamplateDYC.update(CAMBIAR_URL,
                                    new Object[] { rutaDocumento, numControlDoc });
         } catch (Exception dae) {
             LOGGER.error(ConstantesDyC.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC.TEXTO_2_ERROR_DAO +
                       CAMBIAR_URL + ConstantesDyC.TEXTO_3_ERROR_DAO + "rutaDocumento: " +
                          rutaDocumento + ", numControlDoc: " + numControlDoc);
             throw new SIATException(dae);
         }
     }    
}
