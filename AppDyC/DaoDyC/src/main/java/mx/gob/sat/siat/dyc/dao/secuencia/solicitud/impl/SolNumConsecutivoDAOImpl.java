/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao.secuencia.solicitud.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.secuencia.solicitud.SolNumConsecutivoDAO;
import mx.gob.sat.siat.dyc.dao.secuencia.solicitud.impl.mapper.DycqNumControlMapper;
import mx.gob.sat.siat.dyc.domain.secuencia.DycqNumControlDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.EDycDaoCodigoError;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 *
 * @author GAER8674
 */
@Repository
public class SolNumConsecutivoDAOImpl implements SolNumConsecutivoDAO {

    private static final Logger LOGGER = Logger.getLogger(SolNumConsecutivoDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public SolNumConsecutivoDAOImpl() {
        super();
    }

    /**
     *
     * @param claveSir clave de la unidad administrativa
     * @return objeto DycqNumControlDTO con el numero de control generado por
     * DYCQ_NUMCONTROL
     * @throws SIATException
     */
    @Override
    public DycqNumControlDTO getNumConsecutivo(String claveSir) throws SIATException {
        String num = null;
        DycqNumControlDTO numControl = null;

        try {
            numControl =
                    (DycqNumControlDTO)jdbcTemplateDYC.queryForObject(SQLOracleDyC.SELECT_DYCQ_NUMCONTROL_NEXTVAL + claveSir + SQLOracleDyC.FROM_DUAL,
                                                                      new Object[] { }, new DycqNumControlMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.SELECT_DYCQ_NUMCONTROL_NEXTVAL + claveSir + SQLOracleDyC.FROM_DUAL + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                         " claveSir ");
            throw new SIATException(dae);
        }
        if (null != numControl) {
            num = ConstantesTipoRol.CONSECUTIVO.substring((numControl.getSecuencia().length()));
            num = num + numControl.getSecuencia();
            numControl.setSecuencia(num);
        }

        return numControl;
    }

    /**
     *
     * @param claveSir clave de la unidad administrativa
     * @return objeto DycqNumControlDTO con el numero de control generado por
     * DYCQ_NUMCTRLDEVIVA
     *
     * @throws DycDaoExcepcion
     */
    @Override
    public String getNumConsecutivoDevIva(String claveSir) throws DycDaoExcepcion{
        return getNumConsecutivo("DYCQ_NUMCTRLDEVIVA", claveSir, ConstantesTipoRol.CONSECUTIVO_AUTOMATICASIVA);
    }

    /**
     * Para PPMC 100238 CC107199; se reinician secuencias DYCQ_NUMCTRLDEVIVA,
     * DYCQ_NUMCTRLDEVISR, DYCQ_NUMCTRLCOM, DYCQ_NUMCONTROL,
     * DYCQ_FOLIOAVISOCOMP. Se almacena una un registro de fecha de reinicio en
     * la tabla DYCT_REINICIOSECPARAM el cual si esta activo se reinician cuando
     * sea tiempo de reiniciar
     *
     * @param nombreBaseSecuencia; nombre de la secuencia
     * @param claveSir; unidad administrativa
     * @param mascaraSecuencia
     * @return numero de secuencia generado por: nombreBaseSecuencia
     * @throws DycDaoExcepcion
     */
    private String getNumConsecutivo(String nombreBaseSecuencia, String claveSir, String mascaraSecuencia) throws DycDaoExcepcion{
        String num = null;
        DycqNumControlDTO numControl = null;
        final String nombreSecuencia = nombreBaseSecuencia + claveSir;
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("nombreSecuencia", nombreSecuencia) );

        try {
            numControl =
                    (DycqNumControlDTO)jdbcTemplateDYC.queryForObject(SQLOracleDyC.SELECT + nombreSecuencia + SQLOracleDyC.FROM_DUAL,
                                                                      new Object[] { }, new DycqNumControlMapper());
        } catch (DataAccessException dae) {
            throw new DycDaoExcepcion(EDycDaoCodigoError.BD_DYC_QUERY_SECUENCIA, estadoVariables, dae);
        }
        
        if (null != numControl) {
            if(numControl.getSecuencia().length() > mascaraSecuencia.length()){
                estadoVariables.add( new DycLogEstadoVariable("valorSecuencia", numControl.getSecuencia()) );
                throw new DycDaoExcepcion(EDycDaoCodigoError.BD_DYC_SECUENCIA_REQUIEREREINICIAR, estadoVariables);
            }
            
            num = mascaraSecuencia.substring((numControl.getSecuencia().length()));
            num = num + numControl.getSecuencia();
        }

        return num;
    }

    /**
     *
     * @param claveSir clave de la unidad administrativa
     * @return objeto DycqNumControlDTO con el numero de control generado por
     * DYCQ_NUMCTRLCOM
     */
    @Override
    public DycqNumControlDTO getNumConsecutivoCasoCom(String claveSir) {
        LOGGER.debug("getNumConsecutivoCasoCom ".toUpperCase() + " " + claveSir);
        String num = null;
        DycqNumControlDTO numControl = null;

        try {
            numControl =
                    (DycqNumControlDTO)jdbcTemplateDYC.queryForObject(SQLOracleDyC.SELECT_DYCQ_NUMCTRLCOM_NEXTVAL + claveSir + SQLOracleDyC.FROM_DUALCOM,
                                                                      new Object[] { }, new DycqNumControlMapper());
            LOGGER.debug("SEC_COM" + numControl.getSecuencia());
        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.SELECT_DYCQ_NUMCTRLCOM_NEXTVAL + claveSir + SQLOracleDyC.FROM_DUALCOM + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                         " claveSir ");
        }
        if (null != numControl) {
            num = ConstantesTipoRol.CONSECUTIVO.substring(numControl.getSecuencia().length());
            num = num + numControl.getSecuencia();
            numControl.setSecuencia(num);
        }

        return numControl;
    }

    @Override
    public DycqNumControlDTO getNumConsecutivoOrigenSafCC() {
        LOGGER.debug("getNumConsecutivoOrigenSafCC".toUpperCase());
        String num = null;
        DycqNumControlDTO numControl = null;
        String sql = "select DYCQ_IDORIGENSAFCC.nextval as SECUENCIA from dual";

        try {
            numControl =
                    (DycqNumControlDTO)jdbcTemplateDYC.queryForObject(sql, new Object[] { }, new DycqNumControlMapper());
        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql);
        }
        if (null != numControl) {
            num = ConstantesTipoRol.CONSECUTIVO.substring(numControl.getSecuencia().length());
            num = num + numControl.getSecuencia();
            numControl.setSecuencia(num);
        }
        return numControl;
    }

    @Override
    public Integer getFolioServicioTemp() {
        Integer idDeclaracion = null;
        try {
            idDeclaracion = jdbcTemplateDYC.queryForObject(SQLOracleDyC.GET_FOLIO_SERVICIO_TEMP.toString(), Integer.class);
            if (null != idDeclaracion) {
                LOGGER.debug("INIT ID_DECLARACION => " + idDeclaracion.intValue());
                createServicioTemp(idDeclaracion.intValue());
            }

        } catch (EmptyResultDataAccessException exc) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + exc.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                         "Consulta Saldo ICEP");
        }
        return idDeclaracion;
    }

    @Override
    public Integer getNumConsecutivoSaldoIcep() {
        LOGGER.debug("getNumConsecutivoSaldoICEP".toUpperCase());
        /**
         * String num = null;
         */
        Integer idSaldoIcep = null;

        String sql = "select DYCQ_IDSALDOICEP.nextval as SECUENCIA from dual";

        try {

            idSaldoIcep = jdbcTemplateDYC.queryForObject(sql, new Object[] { }, Integer.class);

        } catch (EmptyResultDataAccessException exc) {
            idSaldoIcep = null;
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + exc.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                         "Consulta SDYCQ_IDSALDOICEP");
        }

        return idSaldoIcep;
    }


    @Override
    public Integer getNumConsecutivoDetalleIcep() {
        LOGGER.debug("getNumConsecutivoDetalleICEP".toUpperCase());
        Integer idDetalleIcep = null;
        String sql = "select DYCQ_IDDETALLEICEP.nextval as SECUENCIA from dual";

        try {
            idDetalleIcep = jdbcTemplateDYC.queryForObject(sql, new Object[] { }, Integer.class);

        } catch (EmptyResultDataAccessException exc) {
            idDetalleIcep = null;
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + exc.getMessage() + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                         "Consulta DYCQ_IDDETALLEICEP");
        }

        return idDetalleIcep;
    }

    @Override
    public Integer getIdArchivo() {
        Integer idArchivo = null;
        String query = "SELECT DYCQ_IDARCHIVO.NEXTVAL as SECUENCIA FROM DUAL";
        try {
            idArchivo = jdbcTemplateDYC.queryForObject(query, Integer.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return idArchivo;
    }

    @Override
    public Integer getIdDeclaracion() {
        try {
            return jdbcTemplateDYC.queryForObject(SQLOracleDyC.GETIDDECLARACION.toString(), Integer.class);
        } catch (DataAccessException e) {
            e.getMessage();
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.GETIDDECLARACION);
            throw e;
        }
    }

    private void createServicioTemp(int folio) {
        LOGGER.info("INIT CREATE RELACION SOLTEMP, FOLIO " + folio);
        jdbcTemplateDYC.update(SQLOracleDyC.CREATE_SERVICIO_TEMP.toString(), new Object[] { folio, 1 });
    }
    /**
     * Funcion para validar si un numero de control ya ha sido guardado en BD
     * @param numeroControl cadena que contiene un numero de control a validar
     * @return true si ya existe en bd, false si no existe en bd
     */
    @Override
    public boolean existeNumeroControlSolicitud(String numeroControl){
        String resultado=null;
        boolean existeNumControl=Boolean.TRUE;
        try{
            resultado=(String)jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_NUMERO_CONTROL.toString(),new Object[]{numeroControl.trim()}, getNumeroControlServicio());
            LOGGER.error("Si existe el numcontrol en DYCP_SERVICIO: " + resultado);
        
        }catch(EmptyResultDataAccessException e){
            existeNumControl=Boolean.FALSE;
            LOGGER.error("No existe num ctrl: " + numeroControl.trim()+" en DYCP_SERVICIO, debe permitirse la insercion de este");
        }catch(DataAccessException e){
            LOGGER.error("Error al verificar en DYCP_SERCICIO si el num de control: "+numeroControl+" existe");
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.CONSULTA_NUMERO_CONTROL,e);            
        }      
        return existeNumControl;
    }
    /**
     * Funcion para obtener el valor maximo con que se guardo un numero de
     * control de una ADM
     *
     * @param claveADM calve de la ADM
     * @param filtroADM Cadena que contine un filtro para hacer mas rapido la
     * busqueda
     * @return Long que contiene el valor maximo guardado respecto al numero de
     * control de una adm
     */
    @Override
    public long obtenerValorSeqGuardadoMax(long claveADM, String filtroADM){
        String resultado= null;
        long valorSecuenciaMaxGuardado=-1;
        try{
        resultado=(String)jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_NUMERO_CONTROL_MAX_BY_ADM.toString(),new Object[]{claveADM,filtroADM}, String.class);
        LOGGER.info(" Consulta result max num ctrl adm: " + resultado);
        if(resultado!=null && !resultado.isEmpty()){
            valorSecuenciaMaxGuardado=Long.parseLong(resultado.substring(SQLOracleDyC.INDEX_NUM_CONTROL));
        }
        
        }catch(DataAccessException e){
             e.getMessage();
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.CONSULTA_NUMERO_CONTROL,e);
            throw e;
        } 
        
        
        return valorSecuenciaMaxGuardado;
    }
    /**
     * Metodo para incremetar el valor de una secuencia de numero de control de
     * una adm
     *
     * @param claveSecuencia Cadena que contiene la clave de ADM
     * @param incremento Long que contiene el incremento para hacer el salto en
     * la secuencia
     * @throws
     */
    @Override
    public void actualizarSecuenciaDeAdministracion(String claveSecuencia, long incremento){
        
        try{
            LOGGER.info("Modificando valor de incremeto de secuencia:******************** ");
        jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_SECUENCIA_NUM_CONTROL.toString()+
                claveSecuencia+SQLOracleDyC.INCREMENTO_SECUENCIA_NUM_CONTROL+" "+incremento);
        
        String resultado=(String)jdbcTemplateDYC.queryForObject(SQLOracleDyC.SELECT_DYCQ_NUMCONTROL_NEXTVAL.toString()+
                claveSecuencia+SQLOracleDyC.FROM_DUAL,String.class);
        LOGGER.info("Salto de secuencia: "+resultado);
        jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_SECUENCIA_NUM_CONTROL.toString()+
                claveSecuencia+SQLOracleDyC.INCREMENTO_SECUENCIA_NUM_CONTROL+" "+SQLOracleDyC.VALOR_INCREMENTO_UNO);
        } catch(DataAccessException e){
             e.getMessage();
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.ACTUALIZA_SECUENCIA_NUM_CONTROL.toString()+
                claveSecuencia+SQLOracleDyC.INCREMENTO_SECUENCIA_NUM_CONTROL,e);
            throw e;
        }
    }

    private RowMapper<String> getNumeroControlServicio() {
        return new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("NUMCONTROL");
            }
        };
    }
}
