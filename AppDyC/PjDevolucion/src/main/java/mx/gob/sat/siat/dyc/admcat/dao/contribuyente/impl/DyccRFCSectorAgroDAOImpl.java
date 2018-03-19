
package mx.gob.sat.siat.dyc.admcat.dao.contribuyente.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.contribuyente.DyccRFCSectorAgroDAO;
import mx.gob.sat.siat.dyc.admcat.dao.contribuyente.impl.mapper.DyccRfcSectorAgroMapper;
import mx.gob.sat.siat.dyc.admcat.vo.DyccRfcSectorAgroVO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccRFCSectorAgroDAO")
public class DyccRFCSectorAgroDAOImpl implements DyccRFCSectorAgroDAO{
    private static final Logger LOG = Logger.getLogger(DyccRFCSectorAgroDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public List<DyccRfcSectorAgroVO> consultar() throws SIATException {
        List<DyccRfcSectorAgroVO> lista = null;

        try {

            lista =
jdbcTemplateDYC.query(SQLOracleDyC.DYCC_RFCSECTORAGRO_CONSULTAR_TODOS.toString(), new Object[] { }, new DyccRfcSectorAgroMapper());

            return lista;

        } catch (DataAccessException dae) {
            LOG.error(dae.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.DYCC_RFCSECTORAGRO_CONSULTAR_TODOS.toString());
            throw new SIATException(dae);
        }
    }


    public List<DyccRfcSectorAgroVO> consultar(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException {
        List<DyccRfcSectorAgroVO> lista = null;

        String query =
            "SELECT IDCONTRIBUYENTE, RFC, NOMBRE, DECODE(FECHAFIN, NULL, 1, 0) AS ACTIVO FROM DYCC_RFCSECTORAGRO ";
        String condicional = "";

        if (dyccRfcSectorAgroVO.getRfc() != null && !dyccRfcSectorAgroVO.getRfc().trim().equals("")) {
            condicional += getOperador(condicional) + " RFC = '" + dyccRfcSectorAgroVO.getRfc() + "'";
        }
        if (dyccRfcSectorAgroVO.getNombre() != null && !dyccRfcSectorAgroVO.getNombre().trim().equals("")) {
            condicional += getOperador(condicional) + " NOMBRE LIKE '%" + dyccRfcSectorAgroVO.getNombre() + "%'";
        }

        if (dyccRfcSectorAgroVO.getActivo() != 2) {
            condicional +=
                    getOperador(condicional) + (dyccRfcSectorAgroVO.getActivo() == 1 ? " FECHAFIN IS NULL" : (dyccRfcSectorAgroVO.getActivo() ==
                                                                                                              0 ?
                                                                                                              " FECHAFIN IS NOT NULL" :
                                                                                                              ""));
        }

        query += (condicional.length() > 0 ? " WHERE " : "") + condicional;

        try {

            lista = jdbcTemplateDYC.query(query, new Object[] { }, new DyccRfcSectorAgroMapper());

            return lista;

        } catch (DataAccessException dae) {
            LOG.error(dae.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.DYCC_RFCSECTORAGRO_CONSULTAR_TODOS);
            throw new SIATException(dae);
        }
    }

    public void insert(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.DYCC_RFCSECTORAGRO_INSERT.toString(),
                                   new Object[] { dyccRfcSectorAgroVO.getRfc(), dyccRfcSectorAgroVO.getNombre(),
                                                  dyccRfcSectorAgroVO.getFechaFin() });


        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_RFCSECTORAGRO_INSERT.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyccRfcSectorAgroVO));
            throw new SIATException(dae);
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_RFCSECTORAGRO_INSERT.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyccRfcSectorAgroVO));
            throw new SIATException(e);
        }

    }

    public void update(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.DYCC_RFCSECTORAGRO_UPDATE.toString(),
                                   new Object[] { dyccRfcSectorAgroVO.getFechaFin(), dyccRfcSectorAgroVO.getRfc() });


        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_RFCSECTORAGRO_UPDATE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyccRfcSectorAgroVO));
            throw new SIATException(dae);
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_RFCSECTORAGRO_UPDATE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyccRfcSectorAgroVO));
            throw new SIATException(e);
        }

    }

    private String getOperador(String condicion) {
        if (condicion.length() > 0) {
            return " AND ";
        } else {
            return "";
        }
    }

    public boolean existe(String rfc) throws SIATException {
        int countReg = 0;

        try {

            countReg =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.DYCC_RFCSECTORAGRO_EXISTE.toString(), new Object[] { rfc }, Integer.class);

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.EMITIRRESOLUCION_EXISTERESOLUCION.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + " rfc: " +
                      rfc);
            throw new SIATException(dae);
        }

        return countReg > 0 ? Boolean.TRUE : Boolean.FALSE;

    }

    @Override
    public Integer findRfcSectorAgro(String rfc) throws SIATException {
        LOG.info("CONSULTA SECTOR AGROPECUARIO " + rfc);
        int activo = 0;
        try {
            activo = jdbcTemplateDYC.queryForObject(SQLOracleDyC.FIND_RFC_SECTOR_AGROPECUARIO.toString(), new Object[] { rfc }, Integer.class);
        }catch (EmptyResultDataAccessException edea){
            LOG.info("EL RFC NO EXISTE" + rfc);
            activo = ConstantesDyCNumerico.VALOR_3;
        }catch (DataAccessException dae) {
            LOG.info("EL RFC NO EXISTE" + rfc);
            throw new SIATException(dae);
            }catch (Exception e){
            LOG.info("Error en DyccRFCSectorAgroDAOImpl.findRfcSectorAgro, se capturo una excepcion de tipo: " + e.getMessage());
            }
        return activo;
    }

    @Override
    public void eliminar(String rfc) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleDyC.DYCC_RFCSECTORAGRO_ELIMINAR.toString(), new Object[] { rfc });


        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_RFCSECTORAGRO_ELIMINAR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + "rfc " + rfc);
            throw new SIATException(dae);
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_RFCSECTORAGRO_ELIMINAR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + "rfc " + rfc);
            throw new SIATException(e);
        }

    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
