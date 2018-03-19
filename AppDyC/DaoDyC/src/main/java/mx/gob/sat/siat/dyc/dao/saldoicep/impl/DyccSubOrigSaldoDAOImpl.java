package mx.gob.sat.siat.dyc.dao.saldoicep.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.saldoicep.DyccSubOrigSaldoDAO;
import mx.gob.sat.siat.dyc.dao.saldoicep.impl.mapper.ActividadEconomicaMapper;
import mx.gob.sat.siat.dyc.dao.saldoicep.impl.mapper.DyccSubSaldoTramiteMapper;
import mx.gob.sat.siat.dyc.dao.saldoicep.impl.mapper.SubOrigSaldoMapper;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 04, Dicimbre 2013
 */
@Repository
public class DyccSubOrigSaldoDAOImpl implements DyccSubOrigSaldoDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOG = Logger.getLogger(DyccSubOrigSaldoDAOImpl.class);
    private static final String SUCCESS = "La insercion de la relacion subOrigen Tramite se ha realizado con exito";
    private List<DyccSubOrigSaldoDTO> subOrigSaldo;
    private List<DyccSubSaldoTramDTO> listaSubSaldoTramite;
    private DyccTipoTramiteDTO currentTipoTramite;
    private DyccSubSaldoTramDTO currentSubSaldoTram;
    private List<DyccActividadDTO> actividades;

    public DyccSubOrigSaldoDAOImpl() {
        super();
        subOrigSaldo = new ArrayList<DyccSubOrigSaldoDTO>();
        listaSubSaldoTramite = new ArrayList<DyccSubSaldoTramDTO>();
        currentTipoTramite = new DyccTipoTramiteDTO();
        actividades = new ArrayList<DyccActividadDTO>();
    }

    @Override
    public DyccSubOrigSaldoDTO encontrar(Integer id) {
        DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO = new DyccSubOrigSaldoDTO();
        LOG.info(dyccSubOrigSaldoDTO);
        try {
            dyccSubOrigSaldoDTO =
                    this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.CONSULTADYCC_SUBORIGSALDO.toString(), new Object[] { id },
                                                             new SubOrigSaldoMapper());
        } catch (DataAccessException dae) {
            LOG.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTADYCC_SUBORIGSALDO + ConstantesDyC1.TEXTO_3_ERROR_DAO + id);
        }
        return dyccSubOrigSaldoDTO;
    }

    /**
     * @param idTipoTramite
     * @return
     */
    @Override
    public List<DyccSubOrigSaldoDTO> obtieneSubOrigSaldo(long idTipoTramite) {

        try {
            this.subOrigSaldo =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENESUBORIGSALDOPORTRAMITE.toString(), new Object[] { idTipoTramite },
                                          new SubOrigSaldoMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENESUBORIGSALDOPORTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      idTipoTramite);
        }
        return this.subOrigSaldo;
    }

    /**
     * TODO ISCC
     * @return
     */
    @Override
    public List<DyccSubOrigSaldoDTO> consultarSuborigenesDeSaldos() {

        try {

            this.subOrigSaldo =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENESUBORIGENESDESALDOS.toString(), new SubOrigSaldoMapper());

            LOG.debug("El query ha regresado " + this.subOrigSaldo.size() + " registros ");

        } catch (DataAccessException dae) {

            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENESUBORIGENESDESALDOS);
        }

        return this.subOrigSaldo;

    }

    @Override
    public int insertarSuborigeDelSaldo(DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO,
                                        List<DyccTipoTramiteDTO> selectedTramites) {

        Integer result = null;
        String query = null;

        try {
            query = SQLOracleDyC.INSERTARSUBORIGENESDELSALDO.toString();

            result =
                    jdbcTemplateDYC.update(query, dyccSubOrigSaldoDTO.getDescripcion(), dyccSubOrigSaldoDTO.getRequiereCaptura(),
                                           dyccSubOrigSaldoDTO.getLeyendaCaptura());

            LOG.debug("La insercion del subOrigen se ha realizado con exito");

            if (result == 1) {

                for (int i = 0; i < selectedTramites.size(); i++) {

                    this.currentTipoTramite = selectedTramites.get(i);
                    query = SQLOracleDyC.INSERTARTRAMITEASOCIADOALSUBORIGEN.toString();

                    result = jdbcTemplateDYC.update(query, this.currentTipoTramite.getIdTipoTramite());
                }
                LOG.debug(SUCCESS);
            }

        } catch (DataAccessException dae) {

            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyccSubOrigSaldoDTO));
            throw dae;
        }

        return null != result ? result : 0;
    }


    @Override
    public int actualizarSuborigeDelSaldo(DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO,
                                          List<DyccTipoTramiteDTO> selectedTramitesToShow) {
        Integer result = 0;
        String query = null;
        try {
            if (dyccSubOrigSaldoDTO.getDescripcion().equals(ConstantesDyC2.LBL_LIST_INACTIVO)) {
                query = SQLOracleDyC.ACTUALIZARSUBORIGENDELSALDOINACTIVO.toString();
                result =
                        jdbcTemplateDYC.update(query, dyccSubOrigSaldoDTO.getDescripcion(), dyccSubOrigSaldoDTO.getRequiereCaptura(),
                                               dyccSubOrigSaldoDTO.getLeyendaCaptura(),
                                               dyccSubOrigSaldoDTO.getIdSuborigenSaldo());
            } else {
                query = SQLOracleDyC.ACTUALIZARSUBORIGENDELSALDOACTIVO.toString();
                result =
                        jdbcTemplateDYC.update(query, dyccSubOrigSaldoDTO.getDescripcion(), dyccSubOrigSaldoDTO.getRequiereCaptura(),
                                               dyccSubOrigSaldoDTO.getLeyendaCaptura(),
                                               dyccSubOrigSaldoDTO.getIdSuborigenSaldo());
            }
            LOG.debug("La actualizacion del subOrigen se ha realizado con exito");
            if (null != result && !result.equals(0)) {
                query = SQLOracleDyC.ACTUALIZARSUBORIGENDELSALDOTRAMITEINACTIVO.toString();
                result = jdbcTemplateDYC.update(query, dyccSubOrigSaldoDTO.getIdSuborigenSaldo());
                for (int i = 0; i < selectedTramitesToShow.size(); i++) {
                    this.currentTipoTramite = selectedTramitesToShow.get(i);
                    query = SQLOracleDyC.VERIFICAREXISTENCIADELSUBORIGENTRAMITE.toString();
                    this.listaSubSaldoTramite =
                            jdbcTemplateDYC.query(query, new Object[] { dyccSubOrigSaldoDTO.getIdSuborigenSaldo(),
                                                                        this.getCurrentTipoTramite().getIdTipoTramite() },
                                                  new DyccSubSaldoTramiteMapper());
                    if (this.getListaSubSaldoTramite().isEmpty()) {
                        query = SQLOracleDyC.INSERTARNUEVOSUBSALDOTRAMITE.toString();
                        result =
                                jdbcTemplateDYC.update(query, dyccSubOrigSaldoDTO.getIdSuborigenSaldo(), this.getCurrentTipoTramite().getIdTipoTramite());
                    } else {
                        query = SQLOracleDyC.ACTUALIZARTRAMITEASOCIADOANIOSUBORIGEN.toString();
                        result =
                                jdbcTemplateDYC.update(query, dyccSubOrigSaldoDTO.getIdSuborigenSaldo(), this.getCurrentTipoTramite().getIdTipoTramite());
                    }
                }
                LOG.debug(SUCCESS);
            } else {
                result = 0;
            }

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query);
        }
        return result;
    }

    @Override
    public List<DyccSubOrigSaldoDTO> obtenerSubOrigenesDeSaldosPorTramite(int idOrigenSaldo,
                                                                          int idTipoTramite) {
        try {
            this.subOrigSaldo =
                    jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTENER_SUBORIGENES_DE_SALDO_POR_TRAMITE.toString(), new Object[] { idOrigenSaldo,
                                                                                                       idTipoTramite },
                                          new SubOrigSaldoMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_SUBORIGENES_DE_SALDO_POR_TRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoTramite);
        }
        return this.subOrigSaldo;
    }


    @Override
    public List<DyccActividadDTO> getActividadesEconomicas(int idSubOrigSaldo) throws SIATException {
        try {
            this.actividades =
                    jdbcTemplateDYC.query(SQLOracleDyC.GET_ACTIVIDADES_ECONOMICAS.toString(), new Object[] { idSubOrigSaldo }, new ActividadEconomicaMapper());
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.GET_ACTIVIDADES_ECONOMICAS);
            throw new SIATException(e);
        }

        return actividades;
    }

    @Override
    public int delete(DyccSubOrigSaldoDTO subOrigSaldo) {
        int result = 0;
        String query = null;
        try {
            query = SQLOracleDyC.DELETE_SUBSALDOTRAM.toString();
            result = this.jdbcTemplateDYC.update(query, subOrigSaldo.getIdSuborigenSaldo());

            query = SQLOracleDyC.DELETE_SUBORIGSALDO.toString();
            result = this.jdbcTemplateDYC.update(query, subOrigSaldo.getIdSuborigenSaldo());
            LOG.debug(SUCCESS);


        } catch (DataAccessException dae) {

            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO);
            throw dae;
        }
        return result;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public void setSubOrigSaldo(List<DyccSubOrigSaldoDTO> subOrigSaldo) {
        this.subOrigSaldo = subOrigSaldo;
    }

    public List<DyccSubOrigSaldoDTO> getSubOrigSaldo() {
        return subOrigSaldo;
    }

    public void setCurrentTipoTramite(DyccTipoTramiteDTO currentTipoTramite) {
        this.currentTipoTramite = currentTipoTramite;
    }

    public DyccTipoTramiteDTO getCurrentTipoTramite() {
        return currentTipoTramite;
    }

    public void setListaSubSaldoTramite(List<DyccSubSaldoTramDTO> listaSubSaldoTramite) {
        this.listaSubSaldoTramite = listaSubSaldoTramite;
    }

    public List<DyccSubSaldoTramDTO> getListaSubSaldoTramite() {
        return listaSubSaldoTramite;
    }


    public void setActividades(List<DyccActividadDTO> actividades) {
        this.actividades = actividades;
    }

    public List<DyccActividadDTO> getActividades() {
        return actividades;
    }

    public void setCurrentSubSaldoTram(DyccSubSaldoTramDTO currentSubSaldoTram) {
        this.currentSubSaldoTram = currentSubSaldoTram;
    }

    public DyccSubSaldoTramDTO getCurrentSubSaldoTram() {
        return currentSubSaldoTram;
    }
}
