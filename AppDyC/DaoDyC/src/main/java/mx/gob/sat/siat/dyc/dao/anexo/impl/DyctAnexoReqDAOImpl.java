/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.anexo.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.anexo.DyctAnexoReqDAO;
import mx.gob.sat.siat.dyc.dao.anexo.impl.mapper.AnexoReqMapper;
import mx.gob.sat.siat.dyc.dao.anexo.impl.mapper.DyctAnexoReqMapper;
import mx.gob.sat.siat.dyc.dao.anexo.impl.mapper.MatrizAnexosMapper;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
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
 * @author Federico Chopin Gachuz
 * @date Enero 24, 2014
 *
 * */
@Repository(value = "dyctAnexoReqDAO")
public class DyctAnexoReqDAOImpl implements DyctAnexoReqDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctAnexoReqDAOImpl.class.getName());

    public DyctAnexoReqDAOImpl() {
        super();
    }

    @Override
    public void insertar(List<DyctAnexoReqDTO> lDyctAnexoReqDTO) throws SIATException {

        try {

            for (DyctAnexoReqDTO dyctAnexoReqDTO : lDyctAnexoReqDTO) {

                jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARANEXOREQUERIR.toString(),
                                       new Object[] { dyctAnexoReqDTO.getDyctReqInfoDTO().getNumControlDoc(),
                                                      dyctAnexoReqDTO.getDyccAnexoTramiteDTO().getDyccMatrizAnexosDTO().getIdAnexo(),
                                                      dyctAnexoReqDTO.getDyccAnexoTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite() });

            }

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARANEXOREQUERIR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " lDyctAnexoReqDTO " + lDyctAnexoReqDTO);
            throw new SIATException(dae);
        }

    }

    @Override
    public void insertar(DyctAnexoReqDTO anexoReq) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_CC_INSERTARANEXOREQUERIR.toString(),
                                   new Object[] { anexoReq.getDyctReqInfoDTO().getNumControlDoc(),
                                                  anexoReq.getDyccAnexoTramiteDTO().getDyccMatrizAnexosDTO().getIdAnexo(),
                                                  anexoReq.getDyccAnexoTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                  anexoReq.getNombreArchivo(), anexoReq.getUrl() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_CC_INSERTARANEXOREQUERIR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(anexoReq));
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyctAnexoReqDTO> selecXReqinfo(DyctReqInfoDTO reqInfo) {
        String query =
            " SELECT * FROM DYCT_ANEXOREQ AR, DYCC_MATRIZANEXOS C WHERE NUMCONTROLDOC = ? AND C.IDANEXO = AR.IDANEXO ";
        AnexoReqMapper mapper = new AnexoReqMapper();
        mapper.setReqInfo(reqInfo);
        mapper.setMapperMatrizAnexos(new MatrizAnexosMapper());
        return this.jdbcTemplateDYC.query(query, new Object[] { reqInfo.getNumControlDoc() },
                                          mapper);
    }

    @Override
    public Integer buscaArchivoEnAnexoReq(String numControl, String archivo) {
        int resul = ConstantesDyCNumerico.VALOR_0;
        resul =
jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_ANEXO_REQUERIDA.toString(), new Object[] { numControl, archivo },
                               Integer.class);
        return resul;
    }

    @Override
    public void actualizarUrl(String numControl, String url) {
        String sql = SQLOracleDyC.UPDATE_ANEXOREQ + url + SQLOracleDyC.WHERE_ANEXOREQ;
        jdbcTemplateDYC.update(sql, new Object[] { numControl });
    }

    @Override
    public List<DyctAnexoReqDTO> buscarDocsAnexosRequeridos(String numControl) throws SIATException{

        try {

            List<DyctAnexoReqDTO> lDyctAnexoReqDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDOCSANEXOSREQUERIDOS.toString(),
                                      new Object[] { numControl }, new DyctAnexoReqMapper());

            return lDyctAnexoReqDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDOCSANEXOSREQUERIDOS +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw new SIATException(dae);
        }
    }

    /**
     * <html>
     * <body>
     * Se utiliza para realizar cualquier consulta, la ventaja de utilizar este método es que se puede ejecutar
     * cualquier query bajo cualquier criterio siempre y cuando no se necesite enviarle parámetros.
     * </body>
     * </html>
     *
     * @param query
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyctAnexoReqDTO> consultar(String query) throws SIATException {
        List<DyctAnexoReqDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(query, new AnexoReqMapper());
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +  query);
            throw new SIATException(dae);
        }
        return lista;
    }

    /**
     * Una vez que han sido leidos los registros se procede a marcarlos como procesados
     *
     * @param numControlDoc
     * @throws SIATException
     */
    @Override
    public void actualizarAProcesado(String numControlDoc) throws SIATException {
        String sql = "update DYCT_ANEXOREQ set procesado=1 where numcontroldoc=? and idanexo in (4, 6, 7)";
        try {
            jdbcTemplateDYC.update(sql, new Object[] { numControlDoc });
        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControlDoc: " + numControlDoc);
            throw new SIATException(e);
        }
    }
}
