/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.req.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.req.DyctOtraInfoReqDAO;
import mx.gob.sat.siat.dyc.dao.req.impl.mapper.OtraInfoReqMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 24, 2014
 *
 * */
@Repository(value = "dyctOtraInfoReqDAO")
public class DyctOtraInfoReqDAOImpl implements DyctOtraInfoReqDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctOtraInfoReqDAOImpl.class.getName());

    public DyctOtraInfoReqDAOImpl() {
        super();
    }

    @Override
    public void insertar(List<DyctOtraInfoReqDTO> lDyctOtraInfoReqDTO) throws SIATException {
        try {
            for (DyctOtraInfoReqDTO dyctOtraInfoReqDTO : lDyctOtraInfoReqDTO) {
                try {
                    String descr = dyctOtraInfoReqDTO.getDescripcion();
                    if (descr != null && descr.length() > ConstantesDyCNumerico.VALOR_3000) {
                        log.error("se modifica tamano de descripcon a max 3000 size:" + descr.length());
                        log.error("cadena QUE SOBREPASA ES:" + descr);
                        descr = descr.substring(0, ConstantesDyCNumerico.VALOR_3000);
                        log.error("se modifica tamano despues" + descr.length());
                    } else if (descr == null) {
                        log.error("ES NULO OTRAINFOREQ");
                    }
                    jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTAROTROSREQUERIMIENTOS.toString(),
                            new Object[]{descr,
                                dyctOtraInfoReqDTO.getDyctReqInfoDTO().getNumControlDoc()});
                } catch (Exception dae) {
                    log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                            + SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTAROTROSREQUERIMIENTOS
                            + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctOtraInfoReqDTO));
                    throw new SIATException(dae);
                }

            }
        } catch (Exception ex) {
            log.error("ERROR insertar--", ex);
            throw new SIATException(ex);
        }

    }

    @Override
    public void insertar(DyctOtraInfoReqDTO otraInfoRequerida) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTAROTROSREQUERIMIENTOS.toString(),
                                   new Object[] { otraInfoRequerida.getDescripcion(), otraInfoRequerida.getDyctReqInfoDTO().getNumControlDoc() });
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTAROTROSREQUERIMIENTOS.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(otraInfoRequerida));
            throw new SIATException(dae);
        }
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    @Override
    public List<DyctOtraInfoReqDTO> selecXReqinfo(DyctReqInfoDTO reqInfo) {
        log.info("reqInfo.getDyctDocumentoDTO().getNumControlDoc() ->" +
                 reqInfo.getNumControlDoc() + "<-");
        String query = " SELECT * FROM DYCT_OTRAINFOREQ WHERE NUMCONTROLDOC = ? ";

        OtraInfoReqMapper mapper = new OtraInfoReqMapper();
        return this.getJdbcTemplateDYC().query(query,
                                               new Object[] { reqInfo.getNumControlDoc() },
                                               mapper);
    }

    @Override
    public Integer buscaArchivoOtraInfoReq(String numControl, String archivo) {
        int resul = ConstantesDyCNumerico.VALOR_0;
        resul =
jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_OTRAINFOREQ_NUMCTRLYARCHIVO.toString(), new Object[] { numControl, archivo },
                               Integer.class);
        return resul;
    }

    @Override
    public void actualizarUrl(String numControl, String url) {
        String sql = SQLOracleDyC.UPDATE_OTRAINFOREQ.toString() + url + SQLOracleDyC.WHERE_OTRAINFOREQ.toString();
        jdbcTemplateDYC.update(sql, new Object[] { numControl });
    }
}
