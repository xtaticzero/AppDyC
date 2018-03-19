/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccSubtramiteDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccSubtramiteMapper;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Clase dao para implementar los accesos a la BD para el DTO DyccSubTramiteDTO
 * @author Paola Rivera
 * @modifiedBy Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dyccSubtramiteDAO")
public class DyccSubtramiteDAOImpl implements DyccSubtramiteDAO {

    private Logger log = Logger.getLogger(DyccSubtramiteDAOImpl.class);
    private static final String CONSULTA_X_IDTIPOTRAMITE =
        "select a.IDSUBTIPOTRAMITE,a.DESCRIPCION,a.FECHAINICIO,a.FECHAFIN \n" +
        "from Dycc_Subtramite a\n" +
        "inner join Dycc_TtSubTramite b on (a.IDSUBTIPOTRAMITE = b.IDSUBTIPOTRAMITE)\n" +
        "where b.idTipoTramite=?";

    private static final String CONSULTA_X_IDTIPOTRAMITE_FECHAFIN =
        "select a.IDSUBTIPOTRAMITE,a.DESCRIPCION,a.FECHAINICIO,a.FECHAFIN \n" +
        "from Dycc_Subtramite a\n" +
        "inner join Dycc_TtSubTramite b on (a.IDSUBTIPOTRAMITE = b.IDSUBTIPOTRAMITE)\n" +
        "where b.idTipoTramite=? and b.fechaFin is null";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public DyccSubtramiteDAOImpl() {
        super();
    }

    @Override
    public List<DyccSubTramiteDTO> obtieneSubtramite() {
        List<DyccSubTramiteDTO> subtramite = new ArrayList<DyccSubTramiteDTO>();
        try {
            subtramite =
                    this.getJdbcTemplateDYC().query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENESUBTRAMITE.toString(), new DyccSubtramiteMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENESUBTRAMITE);
        }
        return subtramite;
    }

    /**
     * Consulta los subtramites asociados al tipo de tramite dado de alta (Esta consulta se realiza para administrar
     * los catalogos de tipo de tramite).
     *
     * @param idTipoTramite
     * @return
     */
    @Override
    public List<DyccSubTramiteDTO> consultaXIdTipoTramite(Integer idTipoTramite) throws SIATException {
        List<DyccSubTramiteDTO> lista = null;
        try {
            lista =
jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE, new Object[] { idTipoTramite }, new DyccSubtramiteMapper());

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idTipoTramite:" + idTipoTramite);
            throw new SIATException(e);
        }
        return lista;
    }

    /**
     * Consulta los subtramites asociados al tipo de tramite dado de alta (Esta consulta se realiza para administrar
     * los catalogos de tipo de tramite) considerando la fecha fin igual a null.
     *
     * @param idTipoTramite
     * @return
     */
    @Override
    public List<DyccSubTramiteDTO> consultaXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException {
        List<DyccSubTramiteDTO> lista = null;
        try {
            lista =
jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE_FECHAFIN, new Object[] { idTipoTramite }, new DyccSubtramiteMapper());

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOTRAMITE_FECHAFIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idTipoTramite:" +
                      idTipoTramite);
            throw new SIATException(e);
        }
        return lista;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
