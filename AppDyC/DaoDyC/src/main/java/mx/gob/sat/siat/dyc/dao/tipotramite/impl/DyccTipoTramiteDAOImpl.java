package mx.gob.sat.siat.dyc.dao.tipotramite.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.CatalogoTipoTramiteMapper;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.MantenerMatrizHabilitacionAnexosMapper;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.TipoTramiteMapper;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class DyccTipoTramiteDAOImpl implements DyccTipoTramiteDAO{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private List<DyccTipoTramiteDTO> tiposTramite;

    private static final Logger LOG = Logger.getLogger(DyccTipoTramiteDAOImpl.class.getName());

    private static final String ACTUALIZAR_TRAMITE =
        "update Dycc_TipoTramite set DESCRIPCION= ?, CLAVECONTABLE= ?, REQUIERECCI= ?, RESOLAUTOMATICA= ?, " +
        "PUNTOSASIGNACION= ?, IDCONCEPTO= ?, PLAZO= ?, IDTIPOPLAZO=? ,CLAVECOMPUTO = ? " +
        "where IDTIPOTRAMITE = ?";
    private static final String ENCONTRAR_TRAMITE =
        "SELECT COUNT(*) AS TOTAL FROM Dycc_TipoTramite WHERE IDTIPOTRAMITE=?";

    private static final String UPDATE_TIPOTRAMITE_FECHAFIN_NULL =
        "update dycc_tipotramite set fechaFin = null where idTipoTramite = ?";


    /**
     * Obtiene los datos del tipo de tramite a traves de el idTipoTramite y los aloja
     * en un objeto DyccTipoTramiteDTO
     *
     * @param idTipoTramite  es el identificador del tipo de tramite
     * @return objeto DyccTipoTramiteDTO
     * @throws SIATException Excepcion de ejecucion
     */
    @Override
    public DyccTipoTramiteDTO encontrar(Integer idTipoTramite) throws SIATException {
        try {
            return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTADYCC_TIPOTRAMITE.toString(), new Object[] { idTipoTramite },
                                                  new TipoTramiteMapper());
        } catch (EmptyResultDataAccessException dae) {
            return null;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTADYCC_TIPOTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoTramite);
            throw new SIATException(dae);
        }
    }

    /**
     * Registra un nuevo tipo de tramite en base de datos
     *
     * @param objeto
     * @throws SIATException
     */
    @Override
    public void insertar(DyccTipoTramiteDTO objeto) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_TIPO_TRAMITE.toString(),
                                   new Object[] { objeto.getIdTipoTramite(), objeto.getDescripcion(),
                                                  objeto.getClaveContable(), objeto.getRequiereCCI(),
                                                  objeto.getResolAutomatica(), objeto.getPuntosAsignacion(),
                                                  objeto.getDyccConceptoDTO().getIdConcepto(), objeto.getPlazo(),
                                                  objeto.getDyccTipoPlazoDTO().getIdTipoPlazo(),objeto.getClaveComputo() });

        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAR_TIPO_TRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO);
            throw new SIATException(e);
        }
    }


    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdOrigen(int idOrigenSaldo, int competencia, boolean isAgace, int... rol) {
        tiposTramite = new ArrayList<DyccTipoTramiteDTO>();
        StringBuilder query = new StringBuilder();
        Parametros parametros =
            new Parametros(String.valueOf(rol[0]), String.valueOf(idOrigenSaldo), String.valueOf(competencia));
        if(isAgace){
            String and = idOrigenSaldo == ConstantesDyCNumerico.VALOR_4?SQLOracleDyC.AND_TRAMITES_AGACE_RESOLUCION.toString(): SQLOracleDyC.AND_TRAMITES_AGACE_INDEBIDO.toString();
             
            getTramites(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORORIGEN.toString().concat(and),parametros);
        }
        else if (rol[1] == ConstantesTipoRol.SOC_CONTOLADORA) {
            query.append(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORORIGEN.toString());
            query.append(" AND tt.idconcepto not in(select idconcepto from dycc_concepto  where idimpuesto IN(1,2,19)) ");
            query.append(SQLOracleDyC.UNION_TRAMITES_SOC_CONTROLADORA.toString());
            parametros.setTramConsolida("37");
            parametros.setRolConsolicacion(String.valueOf(ConstantesTipoRol.SOC_CONTOLADORA));
            getTramites(query, parametros);
        } else if (rol[1] == ConstantesIds.INTEGRADORA_PM) {
            query = new StringBuilder(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORORIGEN.toString());
            query.append(" AND tt.idconcepto not in(select idconcepto from dycc_concepto  where idimpuesto IN(1,2,19)) ");
            query.append(SQLOracleDyC.UNION_TRAMITES_SOC_CONTROLADORA.toString());
            parametros.setTramConsolida("38");
            parametros.setRolConsolicacion(String.valueOf(ConstantesIds.INTEGRADORA_PM));
            getTramites(query, parametros);
        } else if (isSaldoFavor(rol[0], idOrigenSaldo)) 
        {
            getTramites(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORORIGEN.toString().concat(" AND tt.idconcepto in (301) ORDER BY idtipotramite ASC"), parametros);
           
            getTramites(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORORIGEN.toString().concat("AND tt.idconcepto not in (301) ORDER BY idtipotramite ASC"), parametros);
            
        } else {
            getTramites(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORORIGEN.toString().concat(" ORDER BY idtipotramite ASC"), parametros);
        }
        
        ordenaYQuitaDuplicados(tiposTramite);
        
        return tiposTramite;
    }

    private void ordenaYQuitaDuplicados(List<DyccTipoTramiteDTO> listaTramites){
            Set<DyccTipoTramiteDTO> tipoTram= new TreeSet<DyccTipoTramiteDTO>();
            for (DyccTipoTramiteDTO se : listaTramites){
                tipoTram.add(se);
                }
            listaTramites.clear();
            for (DyccTipoTramiteDTO se : tipoTram){
                listaTramites.add(se);
                }
        }
    
    private boolean isSaldoFavor(int rol, int idOrigenSaldo) {
        if (idOrigenSaldo == ConstantesDyCNumerico.VALOR_1 &&
            (rol == ConstantesTipoRol.PERSONA_FISICA || rol == ConstantesTipoRol.PERSONA_MORAL)) {

            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void getTramites(StringBuilder query, Parametros parametros)
    {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
        SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(parametros);
        try {
            tiposTramite.addAll(template.query(query.toString(), sqlNamedParameters, new TipoTramiteMapper()));
        } catch (DataAccessException dae) 
        {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORORIGEN.toString()+ ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      parametros.idOrigenSaldo + parametros.competencia + parametros.idOrigenSaldo);
            throw dae;
        }
    }

    /**
     * Obtiene una lista de todos los tramites que actualmente estan dados de alta en base de datos.
     *
     * @return Lista de los tramites dados de alta en base de datos.
     * @throws SIATException
     */
    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramite(long idOrigenSaldo) throws SIATException {
        tiposTramite = null;
        try {
            if(idOrigenSaldo<=ConstantesDyCNumerico.VALOR_0){
                tiposTramite = jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_TIPO_TRAMITE.toString(),new TipoTramiteMapper());
            } else {
                tiposTramite = jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_TIPO_TRAMITE_PARAM.toString(),new Object[]{idOrigenSaldo} ,new TipoTramiteMapper());
            }
        } catch (Exception dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.OBTENER_TIPO_TRAMITE.toString());
            throw new SIATException(dae);
        }
        return tiposTramite;
    }

    @Override
    public List<DyccTipoTramiteDTO> obtieneTodosTipoTramite(Integer tramite) {

        tiposTramite = null;

        try {

            tiposTramite =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOSDETRAMITE.toString(), new Object[] { tramite }, new TipoTramiteMapper());

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOSDETRAMITE);
        }
        return tiposTramite;
    }

    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdSubOrigenSaldo(int idOrigenSaldo) {

        tiposTramite = null;

        try {

            tiposTramite =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARTRAMITESASOCIADOSALSUBSALDO.toString(), new Object[] { idOrigenSaldo }, new TipoTramiteMapper());

        } catch (DataAccessException dae) {

            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARTRAMITESASOCIADOSALSUBSALDO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idOrigenSaldo);
        }

        return tiposTramite;
    }

    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramitePorAnexo(int anexo) {
        tiposTramite = null;
        try {
            tiposTramite =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORANEXO.toString(), new Object[] { anexo }, new MantenerMatrizHabilitacionAnexosMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORANEXO + ConstantesDyC1.TEXTO_3_ERROR_DAO + anexo);
        }
        return tiposTramite;
    }

    /**Selecciona el tipo de tramite por OrigenSaldo, Concepto y Tipo de servicio
     * @autor [LADP] Luis Alberto Dominguez Palomino
     * */
    @Override
    public Integer selecXTipoTramite(int concepto, int rol, int competencia, int origenSaldo, int tipoServicio) {
        Integer tramite = 0;
        Integer tramiR = 0;
        try {
            String result =
                jdbcTemplateDYC.queryForObject(SQLOracleDyC.BUSCA_TIPOTRAMITE_X_CONCEPTO.toString(), new Object[] { concepto, rol, competencia,
                                                                                            origenSaldo,
                                                                                            tipoServicio },
                                               String.class);
            tramiR = Integer.parseInt(result);
            return tramiR;
        } catch (EmptyResultDataAccessException edae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + edae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.BUSCA_TIPOTRAMITE_X_CONCEPTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + origenSaldo + ", " + concepto +
                      "," + tipoServicio);
            return tramite;
        }
    }

    /**
     * Verifica que exista el documento con un tipo de resolucion automatica.
     *
     * @param idTipoTramite el tipo de tramite que se utiliza para verificar si es una resolucion automatica o no.
     * @return verdadero si tiene resolucion automatica, falso en caso contrario.
     * @throws SIATException
     */
    @Override
    public boolean verificaSiEsResolucionAutomatica(int idTipoTramite) throws SIATException {
        boolean bandera = false;
        try {
            /**DyccTipoTramiteDTO dyccTipoTramiteDTO =
                    this.getJdbcTemplateDYC().queryForObject(CONSULTADYCC_TIPOTRAMITE, new Object[] { idTipoTramite },
                                                             new TipoTramiteMapper());*/
            bandera = Boolean.TRUE;

        } catch (Exception dae) {
            bandera = Boolean.FALSE;
        }
        return bandera;
    }

    @Override
    public List<DyccTipoTramiteDTO> selectXIdSuborigenSaldo(Integer idSuborigenSaldo) {
        return this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_TIPOTRAMITE_X_SUBORIGENSALDO.toString(), new Object[] { idSuborigenSaldo },
                                          new TipoTramiteMapper());

    }

    public void setTiposTramite(List<DyccTipoTramiteDTO> tiposTramite) {
        this.tiposTramite = tiposTramite;
    }

    public List<DyccTipoTramiteDTO> getTiposTramite() {
        return tiposTramite;
    }

    @Override
    public Integer obtenerTipoTramiteXConceptoAvisos(int concepto) {
        int valor = 0;
        String sql = "select dycc_tt.idtipotramite \n" +
            "        from dycc_tipotramite dycc_tt\n" +
            "        inner join dyca_origentramite dyca_ot on dyca_ot.idtipotramite = dycc_tt.idtipotramite \n" +
            "        and dyca_ot.idtiposervicio = 4 and dyca_ot.idorigensaldo = 1 \n" +
            "        where dycc_tt.idconcepto = ?";
        try {
            valor = jdbcTemplateDYC.queryForObject(sql, new Object[] { concepto }, Integer.class);
            return valor;
        } catch (EmptyResultDataAccessException edae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + edae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + concepto);
            return valor;
        }
    }

    @Override
    public List<DyccTipoTramiteDTO> tramitesPagoDeLoIndebido() {
        return jdbcTemplateDYC.query(SQLOracleDyC.TRAMITES_PAGO_DE_LO_INDEBIDO.toString(), new Object[] { }, new TipoTramiteMapper());
    }

    @Override
    public DyccTipoTramiteDTO obtieneTipoTramite(int idTipoTrmite) {
        return jdbcTemplateDYC.queryForObject(SQLOracleDyC.FIND_TRAMITE.toString(), new Object[] { idTipoTrmite }, new TipoTramiteMapper());
    }

    /**
     * Agrega el tipo del tramite poniendo fecha fin igual a nulo.
     *
     * @param idTipoTramite
     */
    @Override
    public DyccTipoTramiteDTO obtieneTipoTramiteID(int idTipoTrmite) {
        return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTARCATALOGOS_DYCC_TIPOTRAMITE.toString(), new Object[] { idTipoTrmite }, new TipoTramiteMapper());
    }

    /**
     * Agrega el tipo del tramite poniendo fecha fin igual a nulo.
     *
     * @param idTipoTramite
     */
    @Override
    public void eliminarTramite(Integer idTipoTramite, Integer bandera) throws SIATException {
        String query = null;
        if (bandera == 1) {
            query = UPDATE_TIPOTRAMITE_FECHAFIN_NULL;
        } else {
            query = SQLOracleDyC.UPDATE_TIPOTRAMITE_FECHAFIN.toString();
        }
        try {
            jdbcTemplateDYC.update(query, new Object[] { idTipoTramite });

        } catch (Exception dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.UPDATE_TIPOTRAMITE_FECHAFIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + "idTipoTramite: " +
                      idTipoTramite);
            throw new SIATException(dae);
        }
    }

    /**
     * Actualiza los datos del tipo de tramite a traves de su ID
     *
     * @param objeto
     * @throws SIATException
     */
    @Override
    public void actualizarTipoTramite(DyccTipoTramiteDTO objeto) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_TRAMITE,
                                   new Object[] { objeto.getDescripcion(), objeto.getClaveContable(),
                                                  objeto.getRequiereCCI(), objeto.getResolAutomatica(),
                                                  objeto.getPuntosAsignacion(),
                                                  objeto.getDyccConceptoDTO().getIdConcepto(), objeto.getPlazo(),
                                                  objeto.getDyccTipoPlazoDTO().getIdTipoPlazo(),
                                                  objeto.getClaveComputo(),
                                                  objeto.getIdTipoTramite() });

        } catch (Exception dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ACTUALIZAR_TRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + "DyccTipoTramiteDTO: " + objeto);
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdConcepto(int idConcepto) {

        tiposTramite = null;

        try {

            tiposTramite =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARTRAMITESPORIDCONCEPTO, new Object[] { idConcepto }, new CatalogoTipoTramiteMapper());

        } catch (DataAccessException dae) {

            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARTRAMITESPORIDCONCEPTO+ ConstantesDyC1.TEXTO_3_ERROR_DAO + idConcepto);
        }

        return tiposTramite;

    }

    /**
     * Obtiene el tipo de tramite por medio de idconceptoorigen y origen del saldo para aviso de compensacion
     * @author Luis Alberto Dominguez Palomino [LADP]
     * @param idConceptoOrigen
     * @param origenSaldo
     * @return
     */
    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramitePorConceptoOrigen(int idConceptoOrigen, int origenSaldo,
                                                                        int tipoRol) {
        tiposTramite = null;
        try {
            tiposTramite =
                    jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTIENE_TIPOTRAMITE_POR_CONCEPTOORIGEN_AVISO.toString(), new Object[] { idConceptoOrigen,
                                                                                                           origenSaldo,
                                                                                                           tipoRol },
                                          new CatalogoTipoTramiteMapper());

        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SQL_OBTIENE_TIPOTRAMITE_POR_CONCEPTOORIGEN_AVISO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      idConceptoOrigen + origenSaldo);
        }
        return tiposTramite;
    }

    /**
     * Valida si el ID del tramite ingresado en pantalla que se intenta dar de alta existe en base de datos
     *
     * @param idTramite
     * @return
     * @throws SIATException
     */
    @Override
    public Integer validarIdTramite(Integer idTramite) throws SIATException {
        Integer encontrado = 0;
        try {
            encontrado = jdbcTemplateDYC.queryForObject(ENCONTRAR_TRAMITE, new Object[] { idTramite }, Integer.class);
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ENCONTRAR_TRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + "idTipoTramite: " + idTramite);
            throw new SIATException(e);
        }

        return encontrado;
    }

    @Override
    public List<DyccTipoTramiteDTO> selecOrderXImpuesto(DyccImpuestoDTO impuesto, String orderBy)
    {
        String query =  " SELECT TT.* FROM DYCC_TIPOTRAMITE TT " +
                        " LEFT OUTER JOIN DYCC_CONCEPTO C ON TT.IDCONCEPTO = C.IDCONCEPTO " +
                        " WHERE IDIMPUESTO = ? " + orderBy;
        return jdbcTemplateDYC.query(query, new Object[] { impuesto.getIdImpuesto() }, new TipoTramiteMapper());
    }

    @Override
    public List<DyccTipoTramiteDTO> selecOrderXTiposervicio(DyccTipoServicioDTO tipoServicio, String orderBy)
    {
        String query =  " SELECT TT.* FROM DYCC_TIPOTRAMITE TT " +
                        " LEFT OUTER JOIN DYCA_ORIGENTRAMITE O ON TT.IDTIPOTRAMITE = O.IDTIPOTRAMITE " +
                        " WHERE IDTIPOSERVICIO = ? " + orderBy;
        return jdbcTemplateDYC.query(query, new Object[] { tipoServicio.getIdTipoServicio() }, new TipoTramiteMapper());
    }

    @Override
    public List<DyccTipoTramiteDTO> selecOrderXTiposervicioImpuesto(DyccTipoServicioDTO tipoServicio, DyccImpuestoDTO impuesto, String orderBy)
    {
        String query =  " SELECT TT.* FROM DYCC_TIPOTRAMITE TT " +
                        " LEFT OUTER JOIN DYCA_ORIGENTRAMITE O ON TT.IDTIPOTRAMITE = O.IDTIPOTRAMITE " +
                        " LEFT OUTER JOIN DYCC_CONCEPTO C ON TT.IDCONCEPTO = C.IDCONCEPTO " +
                        " WHERE IDTIPOSERVICIO = ? AND IDIMPUESTO = ? " + orderBy;

        return jdbcTemplateDYC.query(query, new Object[] { tipoServicio.getIdTipoServicio(),
                                                           impuesto.getIdImpuesto()}, new TipoTramiteMapper());
    }

    static class Parametros {

        private String rol;
        private String idOrigenSaldo;
        private String competencia;
        private String tramConsolida;
        private String rolConsolicacion;

        public Parametros(String rol, String idOrigenSaldo, String competencia) {
            this.rol = rol;
            this.idOrigenSaldo = idOrigenSaldo;
            this.competencia = competencia;
            this.tramConsolida = "0";
        }


        public void setRol(String rol) {
            this.rol = rol;
        }

        public String getRol() {
            return rol;
        }

        public void setIdOrigenSaldo(String idOrigenSaldo) {
            this.idOrigenSaldo = idOrigenSaldo;
        }

        public String getIdOrigenSaldo() {
            return idOrigenSaldo;
        }

        public void setCompetencia(String competencia) {
            this.competencia = competencia;
        }

        public String getCompetencia() {
            return competencia;
        }

        public void setTramConsolida(String tramConsolida) {
            this.tramConsolida = tramConsolida;
        }

        public String getTramConsolida() {
            return tramConsolida;
        }

        public void setRolConsolicacion(String rolConsolicacion) {
            this.rolConsolicacion = rolConsolicacion;
        }

        public String getRolConsolicacion() {
            return rolConsolicacion;
        }
    }
    
        private void getTramites(String query, Parametros parametros)
    {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
        SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(parametros);
        try {
            tiposTramite.addAll(template.query(query, sqlNamedParameters, new TipoTramiteMapper()));
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORORIGEN.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      parametros.idOrigenSaldo + parametros.competencia + parametros.idOrigenSaldo);
            throw dae;
        }
    }

    
}


