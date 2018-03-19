/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.catalogo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccUnidadTramiteDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccUnidadTramiteMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;
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
 * Implementacion DAO catalogo DYCC_UNIDADTRAMITE
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @modifiedBy Jesus Alfredo Hernandez Orozco
 * @date Octubre 9, 2013
 * @since 0.1
 *
 * */
@Repository(value = "dyccUnidadTramiteDAO")
public class DyccUnidadTramiteDAOImpl implements DyccUnidadTramiteDAO{
    private static final Logger LOG = Logger.getLogger(DyccUnidadTramiteDAOImpl.class);
    
    private static final String ACTUALIZAR_FECHA_FIN = "UPDATE dycc_UnidadTramite SET FECHAFIN=NULL WHERE IDTIPOTRAMITE=? AND IDTIPOUNIDADADMVA = ?";
    private static final String COLOCAR_FECHA_FIN    = "UPDATE dycc_UnidadTramite SET FECHAFIN=SYSDATE WHERE IDTIPOTRAMITE=? AND IDTIPOUNIDADADMVA = ?";
    public static final  String CONSULTA_UNIDADTRAMITE_X_TIPOTRAMITE_UNIDADADMVA_FECHAFIN = 
        "SELECT * from dycc_UnidadTramite WHERE IDTIPOTRAMITE = ? AND IDTIPOUNIDADADMVA = ? and fechafin is null";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private List<DyccUnidadTramiteDTO> listaUnidad;

    public DyccUnidadTramiteDAOImpl() {
        super();
        listaUnidad = new ArrayList<DyccUnidadTramiteDTO>();
    }

    @Override
    public List<DyccUnidadTramiteDTO> obtieneLista() {
        try {
            this.listaUnidad =
                    this.getJdbcTemplateDYC().query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEUNIDADTRAMITE.toString(), new DyccUnidadTramiteMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEUNIDADTRAMITE.toString());
            throw dae;
        }

        return this.getListaUnidad();
    }

    /**
     * Inserta la unidad del tramite a partir del objeto: DyccUnidadTramiteDTO
     *
     * @param dyccUnidadTramiteDTO
     * @throws SIATException
     */
    @Override
    public void insertarUnidadTramite(DyccUnidadTramiteDTO dyccUnidadTramiteDTO) throws SIATException {
        try {
            this.getJdbcTemplateDYC().update(SQLOracleDyC.CONSULTARCATALOGOS_INSERTARUNIDADTRAMITE.toString(),
                                             dyccUnidadTramiteDTO.getDyccTipoTramiteDTO().getIdTipoTramite(),
                                             dyccUnidadTramiteDTO.getIdTipoUnidadAdmva());
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTARCATALOGOS_INSERTARUNIDADTRAMITE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(dyccUnidadTramiteDTO));
            throw new SIATException(e);
        }
    }
    
    
    /**
     * Consulta las unidades de tramite a traves del idTipoTramite
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccUnidadTramiteDTO> consultarUnidadTramiteXIdTipoTramite(Integer idTipoTramite) throws SIATException {
        
        List<DyccUnidadTramiteDTO> listaTipoTramite = null;
        String query = "SELECT IDTIPOTRAMITE, IDTIPOUNIDADADMVA FROM DYCC_UNIDADTRAMITE WHERE IDTIPOTRAMITE = ? AND FECHAFIN IS NULL";
        try {
            /**listaTipoTramite = jdbcTemplateDYC.query(OBTIENE_UNIDADTRAMITEXIDTIPOTRAMITE, new Object[] { idTipoTramite }, new DyccUnidadTramiteMapper());*/
            listaTipoTramite = jdbcTemplateDYC.query(query, new Object[] { idTipoTramite }, new DyccUnidadTramiteMapper());
            
        } catch (Exception dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.OBTIENE_UNIDADTRAMITEXIDTIPOTRAMITE);
            throw new SIATException(dae);
        }

        return listaTipoTramite;
    }


    /**
     * Inserta las unidades de tramite a traves de una lista.
     *
     * @param listaUnidadt
     * @throws SIATException
     */
    @Override
    public void insertarUnidadTramite(List<DyccUnidadTramiteDTO> listaUnidadt) throws SIATException {
        for (DyccUnidadTramiteDTO dyccUnidadTramiteDTO : listaUnidadt) {
            try {
                this.getJdbcTemplateDYC().update(SQLOracleDyC.CONSULTARCATALOGOS_INSERTARUNIDADTRAMITE.toString(),
                                                 dyccUnidadTramiteDTO.getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                 dyccUnidadTramiteDTO.getIdTipoUnidadAdmva());
            } catch (Exception e) {
                LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                        SQLOracleDyC.CONSULTARCATALOGOS_INSERTARUNIDADTRAMITE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                        ExtractorUtil.ejecutar(dyccUnidadTramiteDTO));
                throw new SIATException(e);
            }
        }
    }
    
    /**
     * Consulta si existe algun tramite dado de alta con una unidad administrativa en base de datos sin tomar en cuenta 
     * la fecha fin.
     *
     * @param idTipoTramite 
     * @param idTipoUnidadAdmva
     * @return
     */
    @Override
    public boolean consultarXTipoTramiteUnidadAdmva(Integer idTipoTramite, Integer idTipoUnidadAdmva) {
        
        boolean bandera = Boolean.FALSE;
        try {
            jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_UNIDADTRAMITE_X_TIPOTRAMITE_UNIDADADMVA.toString(), 
                                            new Object[]{idTipoTramite, idTipoUnidadAdmva}, 
                                            new DyccUnidadTramiteMapper());
            bandera = Boolean.TRUE;
            
        } catch (Exception dae) {
            LOG.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTA_UNIDADTRAMITE_X_TIPOTRAMITE_UNIDADADMVA.toString());
        }
        return bandera;
    }
    
    /**
     * Consulta si existe algun tramite dado de alta con una unidad administrativa en base de datos tomando en cuenta 
     * la fecha fin en null
     *
     * @param idTipoTramite 
     * @param idTipoUnidadAdmva
     * @return
     */
    @Override
    public boolean consultarXTipoTramiteUnidadAdmvaFechaFin(Integer idTipoTramite, Integer idTipoUnidadAdmva) {
        
        boolean bandera = Boolean.FALSE;
        try {
            jdbcTemplateDYC.queryForObject(CONSULTA_UNIDADTRAMITE_X_TIPOTRAMITE_UNIDADADMVA_FECHAFIN, 
                                            new Object[]{idTipoTramite, idTipoUnidadAdmva}, 
                                            new DyccUnidadTramiteMapper());
            bandera = Boolean.TRUE;
            
        } catch (Exception dae) {
            LOG.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTA_UNIDADTRAMITE_X_TIPOTRAMITE_UNIDADADMVA_FECHAFIN + "Parametros: idTipoTramite: "+
                     idTipoTramite+", idTipoUnidadAdmva:"+idTipoUnidadAdmva);
        }
        return bandera;
    }
    
    /**
     * Cuando se encuentra que existe un una unidad administrativa asociada a un tramite con 
     * consultarXTipoTramiteUnidadAdmva() se procede a actualizar su fechaFin a null
     *
     * @param idTipoTramite
     * @param idTipoUnidadAdmva
     */
    @Override
    public void actualizarFechaFin(Integer idTipoTramite, Integer idTipoUnidadAdmva) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_FECHA_FIN, new Object[]{idTipoTramite, idTipoUnidadAdmva});
            
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    ACTUALIZAR_FECHA_FIN);
            throw new SIATException(e);            
        }
    }
    
    /**
     * Cuando se deja de asociar una unidad administrativa a un tramite se coloca la fecha de fin
     *
     * @param idTipoTramite
     * @param idTipoUnidadAdmva
     */
    @Override
    public void colocarFechaFin(Integer idTipoTramite, Integer idTipoUnidadAdmva) throws SIATException {
        try {
            jdbcTemplateDYC.update(COLOCAR_FECHA_FIN, new Object[]{idTipoTramite, idTipoUnidadAdmva});
            
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    COLOCAR_FECHA_FIN);
            throw new SIATException(e);            
        }
    }

    // TODO: ACCESSOR'S ***************************************************************************************

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setListaUnidad(List<DyccUnidadTramiteDTO> listaUnidad) {
        this.listaUnidad = listaUnidad;
    }

    public List<DyccUnidadTramiteDTO> getListaUnidad() {
        return listaUnidad;
    }
}
