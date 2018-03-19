package mx.gob.sat.siat.dyc.dao.anexo.impl;


import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.anexo.DyccMatrizAnexosDAO;
import mx.gob.sat.siat.dyc.dao.anexo.impl.mapper.DyccMatrizAnexosMapper;
import mx.gob.sat.siat.dyc.dao.anexo.impl.mapper.MatrizAnexosMapper;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccMatrizAnexosDAO")
public class DyccMatrizAnexosDAOImpl implements DyccMatrizAnexosDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private Logger log = Logger.getLogger(DyccMatrizAnexosDAOImpl.class);
    private static final String CONSULTA_X_IDTIPOTRAMITE = "select a.IDANEXO,a.NOMBREANEXO,a.DESCRIPCIONANEXO,a.FECHAINICIO,a.FECHAFIN,a.URL \n" + 
                                                           "from Dycc_MatrizAnexos a \n" + 
                                                           "inner join DYCC_ANEXOTRAMITE B ON (A.idAnexo = b.idANexo)\n" + 
                                                           "where b.idTipoTramite=?";
    
    private static final String CONSULTA_X_IDTIPOTRAMITE_FECHAFIN = "select a.IDANEXO,a.NOMBREANEXO,a.DESCRIPCIONANEXO,a.FECHAINICIO,a.FECHAFIN,a.URL \n" + 
                                                               "from Dycc_MatrizAnexos a \n" + 
                                                               "inner join DYCC_ANEXOTRAMITE B ON (A.idAnexo = b.idANexo)\n" + 
                                                               "where b.idTipoTramite=? and b.fechafin is null";
    
    /**
     * Consulta los anexos que estan asociados a un tramite (esto es para dar de alta los tipos de tramites).
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccMatrizAnexosDTO> consultarXIdTipoTramite(Integer idTipoTramite) throws SIATException {
        List<DyccMatrizAnexosDTO> lista = null;
        
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE, new Object[] { idTipoTramite }, 
                                          new MatrizAnexosMapper());
            
        } catch(Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoTramite);
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta los anexos que estan asociados a un tramite (esto es para dar de alta los tipos de tramites).
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccMatrizAnexosDTO> consultarXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException {
        List<DyccMatrizAnexosDTO> lista = null;
        
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE_FECHAFIN, new Object[] { idTipoTramite }, 
                                          new MatrizAnexosMapper());
            
        } catch(Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOTRAMITE_FECHAFIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + "idTipoTramite: " + 
                      idTipoTramite);
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    private List<DyccMatrizAnexosDTO> lDyccMatrizAnexosDTO;
    
    @Override
    public List<DyccMatrizAnexosDTO> seleccionar() {
        return this.jdbcTemplateDYC.query("SELECT * FROM DYCC_MATRIZANEXOS", new MatrizAnexosMapper());
    }

    @Override
    public DyccMatrizAnexosDTO getAnexo(int idAnexo) {
        return this.jdbcTemplateDYC.queryForObject("SELECT * FROM DYCC_MATRIZANEXOS where idanexo = ?",
                                                   new Object[] { idAnexo }, new MatrizAnexosMapper());
    }
    
    @Override
    public List<DyccMatrizAnexosDTO> consultarMatrizAnexos() {
        List<DyccMatrizAnexosDTO> matrizAnexos = new ArrayList<DyccMatrizAnexosDTO>();
        try {
            matrizAnexos =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEMATRIZANEXOS.toString(), new Object[] { }, new MatrizAnexosMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEMATRIZANEXOS);
        }
        return matrizAnexos;
    }

    @Override
    public int consultarExisteNombreAnexo(String nombre) {
        int existe = 0;
        try {
            existe =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTARCATALOGOS_CONSULTAREXISTENOMBREANEXO.toString(), new Object[] { nombre }, Integer.class);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_CONSULTAREXISTENOMBREANEXO + ConstantesDyC1.TEXTO_3_ERROR_DAO + nombre);
        }
        return existe;
    }

    @Override
    public void adicionarAnexo(DyccMatrizAnexosDTO dyccMatrizAnexosDto, DyccTipoTramiteDTO[] tramites,
                               DyccRolDTO[] roles) {
        try {
            int idAnexo = this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.OBTENER_ID_DYCC_MATRIZ_ANEXOS.toString(), Integer.class);
            this.jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_DYCC_MATRIZANEXOS.toString(),
                                        new Object[] { idAnexo, dyccMatrizAnexosDto.getNombreAnexo(),
                                                       dyccMatrizAnexosDto.getDescripcionAnexo(),
                                                       dyccMatrizAnexosDto.getFechaInicio(),
                                                       dyccMatrizAnexosDto.getFechaFin() });
            guardarAnexo(idAnexo, tramites, roles);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEMATRIZANEXOS + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyccMatrizAnexosDto));
        }
    }

    @Override
    public void modificarAnexo(DyccMatrizAnexosDTO dyccMatrizAnexosDto, DyccTipoTramiteDTO[] tramites,
                               DyccRolDTO[] roles){
        try {
            this.jdbcTemplateDYC.update(SQLOracleDyC.MODIFICAR_DYCC_MATRIZANEXOS.toString(),
                                        new Object[] { dyccMatrizAnexosDto.getDescripcionAnexo(),
                                                       dyccMatrizAnexosDto.getFechaInicio(),
                                                       dyccMatrizAnexosDto.getFechaFin(),
                                                       dyccMatrizAnexosDto.getIdAnexo() });
            this.jdbcTemplateDYC.update(SQLOracleDyC.ELIMINAR_ANEXOTRAMITE.toString(), new Object[] { dyccMatrizAnexosDto.getIdAnexo() });
            this.jdbcTemplateDYC.update(SQLOracleDyC.ELIMINAR_ANEXOROL.toString(), new Object[] { dyccMatrizAnexosDto.getIdAnexo() });
            guardarAnexo(dyccMatrizAnexosDto.getIdAnexo(), tramites, roles);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEMATRIZANEXOS + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyccMatrizAnexosDto));
        }
    }

    public void guardarAnexo(Integer idAnexo, DyccTipoTramiteDTO[] tramites,
                             DyccRolDTO[] roles) {
        try {
            if (tramites.length > 0) {
                for (int i = 0; i < tramites.length; i++) {
                    this.jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCATALOGOS_INSERTARANEXOTRAMITE.toString(),
                                                new Object[] { idAnexo, tramites[i].getIdTipoTramite() });
                }
            }
            if (roles.length > 0) {
                for (int i = 0; i < roles.length; i++) {
                    this.jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCATALOGOS_INSERTARANEXOROL.toString(),
                                                new Object[] { idAnexo, roles[i].getIdRol() });
                }
            }
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEMATRIZANEXOS.toString());
        }
    }

    @Override
    public void eliminarAnexo(int idAnexo) {
        try {
            this.jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCATALOGOS_ELIMINARANEXO.toString(), new Object[] { idAnexo });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_ELIMINARANEXO + " con el parametro : " + idAnexo);
        }
    }

    @Override
    public List<DyccMatrizAnexosDTO> buscarAnexosARequerir(int idTipoTramite) {
        try {
            this.lDyccMatrizAnexosDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARANEXOSREQUERIRPORNUMERODECONTROL.toString(),
                                          new Object[] { idTipoTramite }, new DyccMatrizAnexosMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARANEXOSREQUERIRPORNUMERODECONTROL.toString() +
                      " con los siguientes parametros " + " id tipo tramite " + idTipoTramite);
            throw dae;
        }
        return this.getLDyccMatrizAnexosDTO();
    }
    
    @Override
    public DyccMatrizAnexosDTO buscaAnexoPorId(Integer idAnexo) {
        String sql = "SELECT * from DYCC_MATRIZANEXOS where idanexo = ?";
        return jdbcTemplateDYC.queryForObject(sql, new Object[]{idAnexo}, new MatrizAnexosMapper());
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setLDyccMatrizAnexosDTO(List<DyccMatrizAnexosDTO> lDyccMatrizAnexosDTO) {
        this.lDyccMatrizAnexosDTO = lDyccMatrizAnexosDTO;
    }

    public List<DyccMatrizAnexosDTO> getLDyccMatrizAnexosDTO() {
        return lDyccMatrizAnexosDTO;
    }
}
