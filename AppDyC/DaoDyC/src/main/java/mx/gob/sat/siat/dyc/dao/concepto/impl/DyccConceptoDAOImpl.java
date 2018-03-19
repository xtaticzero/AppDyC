package mx.gob.sat.siat.dyc.dao.concepto.impl;


import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.concepto.DyccConceptoDAO;
import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ConceptoMapper;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccConceptoDAO")
public class DyccConceptoDAOImpl implements DyccConceptoDAO{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private List<DyccConceptoDTO> listaConcepto;
    private DyccConceptoDTO dyccConcepto;

    private Logger log = Logger.getLogger(DyccConceptoDAOImpl.class.getName());

    public DyccConceptoDAOImpl() {
        super();
        listaConcepto = new ArrayList<DyccConceptoDTO>();
        dyccConcepto = new DyccConceptoDTO();
    }

    @Override
    public DyccConceptoDTO encontrar(Integer id) {
        DyccConceptoDTO dyccConceptoDTO = null;
        log.info(dyccConceptoDTO);
        try {
            dyccConceptoDTO =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTADYCC_CONCEPTO.toString(), new Object[] { id }, new ConceptoMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTADYCC_CONCEPTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + id);
        }

        return dyccConceptoDTO;
    }

    @Override
    public List<DyccConceptoDTO> seleccionar() {
        List<DyccConceptoDTO> lDyccConceptoDTO = new ArrayList<DyccConceptoDTO>();
        log.info(lDyccConceptoDTO);
        try {
            lDyccConceptoDTO = this.jdbcTemplateDYC.query(SQLOracleDyC.SELECCIONAR_DYCC_CONCEPTO.toString(), new ConceptoMapper());
        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.SELECCIONAR_DYCC_CONCEPTO);
        }
        return lDyccConceptoDTO;
    }

    /**
     * Metodo para obtener el concepto apartir de un idTipoTramite
     * @param idTipoTramite
     * @return
     */
    @Override
    public DyccConceptoDTO obtieneConceptoPorIdTramite(long idTipoTramite) {
        try {
            this.dyccConcepto =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENECONCEPTOPORTRAMITE.toString(), new Object[] { idTipoTramite },
                                                        new ConceptoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENECONCEPTOPORTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoTramite);
        }
        return this.dyccConcepto;
    }

    @Override
    public List<DyccConceptoDTO> obtieneConceptoPorImpuesto(int idImpuesto) {
        try {
            this.listaConcepto =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENECONCEPTOPORIMPUESTO.toString(), new Object[] { idImpuesto },
                                               new ConceptoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENECONCEPTOPORIMPUESTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idImpuesto);
            throw dae;
        }
        return this.listaConcepto;
    }

    @Override
    public DyccConceptoDTO obtieneIdConcepto(int idConcepto) {
        try {
            this.dyccConcepto =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTARCATALOGOCONCEPTO.toString(), new Object[] { idConcepto },
                                                        new ConceptoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOCONCEPTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idConcepto);
        }
        return this.dyccConcepto;
    }

    @Override
    public List<DyccConceptoDTO> obtenerConceptosPorTipoTramite(int tipoTramite) {
        try {
            this.listaConcepto =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTENER_CONCEPTOS_POR_TIPO_TRAMITE.toString(), new Object[] { tipoTramite },
                                               new ConceptoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_CONCEPTOS_POR_TIPO_TRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + tipoTramite);
            throw dae;
        }
        return this.listaConcepto;
    }

    @Override
    public List<DyccConceptoDTO> selecXImpuesto(DyccImpuestoDTO impuesto) {
        try {
            String query = " SELECT * FROM DYCC_CONCEPTO WHERE IDIMPUESTO = ? ";
            ConceptoMapper mapper = new ConceptoMapper();
            mapper.setImpuesto(impuesto);
            return jdbcTemplateDYC.query(query, new Object[] { impuesto.getIdImpuesto() }, mapper);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENECONCEPTOPORIMPUESTO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      impuesto.getIdImpuesto());
            throw dae;
        }
    }

    /**
     * Metodo encargado de obtener los conceptos que se necesitan para mostrar en aviso de compensacion por
     * tipo de rol con el que ingresen
     * @author Luis Alberto Dominguez Palomino [LADP]
     * @param tipoRol
     * @return
     */
    @Override
    public List<DyccConceptoDTO> obtenerConceptosPorTipoRol(int tipoRol) {
        try {
            this.listaConcepto =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTENER_CONCEPTOS_DESTINO_AVISO.toString(), new Object[] { tipoRol },
                                               new ConceptoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_CONCEPTOS_DESTINO_AVISO);
            throw dae;
        }
        return this.listaConcepto;
    }

    /**
     * Metodo encargado de obtener los conceptos que se necesitan para mostrar el origen en el aviso de compensacion
     * por idconceptodestino, origensaldo y provisional
     * @param idConceptoDestino
     * @param origenSaldo
     * @param provisional
     * @return
     */
    @Override
    public List<DyccConceptoDTO> obtenerConceptosOrigen(int tipoRol, int idConceptoDestino, int origenSaldo, int provisional) {
        try {
            this.listaConcepto =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTENER_CONCEPTOS_ORIGEN_AVISO.toString(), new Object[] { tipoRol,
                                                                                                  idConceptoDestino,
                                                                                                  origenSaldo,
                                                                                                  provisional },
                                               new ConceptoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTENER_CONCEPTOS_ORIGEN_AVISO);
        }
        return this.listaConcepto;
    }

    public void setListaConcepto(List<DyccConceptoDTO> listaConcepto) {
        this.listaConcepto = listaConcepto;
    }

    public List<DyccConceptoDTO> getListaConcepto() {
        return listaConcepto;
    }

    public void setDyccConcepto(DyccConceptoDTO dyccConcepto) {
        this.dyccConcepto = dyccConcepto;
    }

    public DyccConceptoDTO getDyccConcepto() {
        return dyccConcepto;
    }

    @Override
    public List<DyccConceptoDTO> selecOrderXImpuesto(DyccImpuestoDTO impuesto, String order)
    {
        try {
            String query = " SELECT * FROM DYCC_CONCEPTO WHERE IDIMPUESTO = ? AND FECHAFIN IS NULL " + order;
            ConceptoMapper mapper = new ConceptoMapper();
            mapper.setImpuesto(impuesto);
            return jdbcTemplateDYC.query(query, new Object[] { impuesto.getIdImpuesto() }, mapper);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENECONCEPTOPORIMPUESTO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      impuesto.getIdImpuesto());
            throw dae;
        }
    }

    @Override
    public DyccConceptoDTO obtieneConceptoPorIdConcepto(int tipotramite) {
          return  this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTARCATALOGOS_DYCC_CONCEPTO.toString(), new Object[] { tipotramite },
                                                        new ConceptoMapper());
    }
}
