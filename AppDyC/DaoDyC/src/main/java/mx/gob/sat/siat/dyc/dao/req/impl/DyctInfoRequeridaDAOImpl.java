/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.req.impl;

import java.sql.Types;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.req.DyctInfoRequeridaDAO;
import mx.gob.sat.siat.dyc.dao.req.impl.mapper.DocsAdjuntosRequeridosMapper;
import mx.gob.sat.siat.dyc.dao.req.impl.mapper.DyctInfoRequeridaMapper;
import mx.gob.sat.siat.dyc.dao.req.impl.mapper.InfoARequerirMapper;
import mx.gob.sat.siat.dyc.dao.req.impl.mapper.InfoRequeridaMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
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
@Repository(value = "dyctInfoRequeridaDAO")
public class DyctInfoRequeridaDAOImpl implements DyctInfoRequeridaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctInfoRequeridaDAOImpl.class.getName());

    public DyctInfoRequeridaDAOImpl() {
        super();
    }
    
    private static final String CONSULTAR_X_TODOS_LOS_VALORES="select * from dyct_inforequerida WHERE NUMCONTROLDOC=? AND IDTIPOTRAMITE=? AND IDINFOAREQUERIR=?";

    @Override
    public void insertar(List<DyctInfoRequeridaDTO> lDyctInfoRequeridaDTO) throws SIATException{

        try {

            for (DyctInfoRequeridaDTO dyctInfoRequeridaDTO : lDyctInfoRequeridaDTO) {

                jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARDOCUMENTACIONREQUERIR.toString(),
                                       new Object[] { dyctInfoRequeridaDTO.getDyctReqInfoDTO().getNumControlDoc(),
                                                      dyctInfoRequeridaDTO.getDyccInfoTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                      dyctInfoRequeridaDTO.getDyccInfoTramiteDTO().getDyccInfoARequerirDTO().getIdInfoArequerir() });
            }

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARDOCUMENTACIONREQUERIR +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " lDyctInfoRequeridaDTO " + lDyctInfoRequeridaDTO);
            throw new SIATException(dae);
        }

    }

    @Override
    public void insertar(DyctInfoRequeridaDTO infoRequerida) throws SIATException {
        String sentencia =
            " INSERT INTO DYCT_INFOREQUERIDA(NUMCONTROLDOC, IDTIPOTRAMITE, IDINFOAREQUERIR) VALUES(?, ?, ?)";
        try {
            log.info("idDocumentoReq ->" + infoRequerida.getDyctReqInfoDTO().getNumControlDoc());
            log.info("idTipoTramite ->" +
                     infoRequerida.getDyccInfoTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite());
            log.info("idInfoARequerir ->" +
                     infoRequerida.getDyccInfoTramiteDTO().getDyccInfoARequerirDTO().getIdInfoArequerir());
            int[] tipos = new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER };

            jdbcTemplateDYC.update(sentencia,
                                   new Object[] { infoRequerida.getDyctReqInfoDTO().getNumControlDoc(),
                                                  infoRequerida.getDyccInfoTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                  infoRequerida.getDyccInfoTramiteDTO().getDyccInfoARequerirDTO().getIdInfoArequerir() }, tipos);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      sentencia + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(infoRequerida));
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyctInfoRequeridaDTO> selecXReqinfo(DyctReqInfoDTO reqInfo) {
        String query =
            " SELECT * FROM DYCT_INFOREQUERIDA IR, DYCC_INFOAREQUERIR C WHERE NUMCONTROLDOC = ? AND C.IDINFOAREQUERIR = IR.IDINFOAREQUERIR";
        InfoRequeridaMapper mapper = new InfoRequeridaMapper();
        mapper.setMapperInfoARequerir(new InfoARequerirMapper());
        mapper.setReqInfo(reqInfo);
        return this.jdbcTemplateDYC.query(query, new Object[] { reqInfo.getNumControlDoc() },
                                          mapper);
    }

    /**
     * Metodo que consulta por numero de control y nombre de carpeta para obtener un resultado entero
     * realiza la consulta sobre la tabla DYCT_INFOREQUERIDA
     * autor = LADP--> Luis Alberto Dominguez Palomino
     * @param numControl
     * @param archivo
     * @return
     */
    @Override
    public Integer buscaArchivoInfoReq(String numControl, String archivo) {
        int result = 0;
        result =
                jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_INFOREQUERIDA_NUMCTRLYARCHIVO.toString(), new Object[] { numControl,
                                                                                                                   archivo },
                                               Integer.class);
        return result;
    }

    @Override
    public void actualizarUrl(String numControl, String url) {
        String sql = SQLOracleDyC.UPDATE_INFOREQ + url + SQLOracleDyC.WHERE_INFOREQ;
        jdbcTemplateDYC.update(sql, new Object[] { numControl });
    }

    @Override
    public List<DyctInfoRequeridaDTO> buscarDocsAdjuntosRequeridos(String numControl) throws SIATException{
        
        try {

            List<DyctInfoRequeridaDTO> lDyctInfoRequeridaDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDOCSADJUNTOSREQUERIDOS.toString(),
                                      new Object[] { numControl, numControl },
                                      new DocsAdjuntosRequeridosMapper());

            return lDyctInfoRequeridaDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDOCSADJUNTOSREQUERIDOS +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw new SIATException(dae);
        }
        
    }
    
    @Override
    public DyctInfoRequeridaDTO buscar(DyctInfoRequeridaDTO objeto) {
        DyctInfoRequeridaDTO objeto2 =  null;
        
        try {
            objeto2 =
                    jdbcTemplateDYC.queryForObject(CONSULTAR_X_TODOS_LOS_VALORES, 
                                                   new Object[] { objeto.getDyctReqInfoDTO().getNumControlDoc(),
                                                                  objeto.getDyccInfoTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                                  objeto.getDyccInfoTramiteDTO().getDyccInfoARequerirDTO().getIdInfoArequerir() },
                                                   new DyctInfoRequeridaMapper() );
        } catch (Exception e) {
            log.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + 
                     ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(objeto));
        }
        
        return objeto2;
    }
}
