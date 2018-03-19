/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.declaracion.impl;

import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.declaracion.DyctDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper.DeclaracionMapper;
import mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper.DyctDeclaracionMapper;
import mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper.InfoSaldoAFavorMapper;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctDeclaraTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.EDycDaoCodigoError;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesAsignarAuDic;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctDeclaracionDAOImpl implements DyctDeclaracionDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctDeclaracionDAOImpl.class.getName());

    private long secuenciaDeclaracion;

    private List<DyctDeclaracionDTO> lDyctDeclaracionDTO;

    public DyctDeclaracionDAOImpl() {
        super();
        lDyctDeclaracionDTO = new ArrayList<DyctDeclaracionDTO>();
    }

    public DyctDeclaracionDTO encontrar(Long id) {
        return jdbcTemplateDYC.queryForObject("SELECT * FROM DYCT_DECLARACION WHERE IDDECLARACION = ?",
                                              new Object[] { id }, new DeclaracionMapper());
    }

    private void obtenerSecuenciaDeclaracion(String secuencia) {
        secuenciaDeclaracion =
                jdbcTemplateDYC.queryForObject(SQLOracleDyC.OBTENER_SECUENCIA.replace(ConstantesAsignarAuDic.NOMBRE_SECUENCIA,
                                                                                      secuencia), Long.class);
    }

    @Override
    public void insertar(DyctDeclaracionDTO dyctDeclaracionDTO, String secuencia) {
        obtenerSecuenciaDeclaracion(secuencia);
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_DYCT_DECLARACION.toString(),
                                   new Object[] { secuenciaDeclaracion, dyctDeclaracionDTO.getFechaPresentacion(),
                                                  dyctDeclaracionDTO.getFechaCausacion(),
                                                  dyctDeclaracionDTO.getNumOperacion(),
                                                  dyctDeclaracionDTO.getNumDocumento(),
                                                  dyctDeclaracionDTO.getSaldoAfavor(),
                                                  dyctDeclaracionDTO.getDevueltoCompensado(),
                                                  dyctDeclaracionDTO.getAcreditamiento(),
                                                  dyctDeclaracionDTO.getImporte(),
                                                  dyctDeclaracionDTO.getDyccUsoDecDTO().getIdUsoDec(),
                                                  dyctDeclaracionDTO.getDyccTipoDeclaraDTO().getIdTipoDeclaracion(),
                                                  dyctDeclaracionDTO.getEtiquetaSaldo(),
                                                  dyctDeclaracionDTO.getNumControl() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAR_DYCT_DECLARACION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctDeclaracionDTO));
            throw dae;
        }
    }
    
    @Override
    public void insertar(DyctDeclaracionDTO dyctDeclaracionDTO) throws DycDaoExcepcion{
        int[] tipoDatos =
            new int[] { Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR, 
                Types.DECIMAL, Types.DECIMAL, Types.DECIMAL, Types.DECIMAL, Types.INTEGER, 
                Types.INTEGER, Types.VARCHAR, Types.VARCHAR};

        try {
            if (dyctDeclaracionDTO.getIdDeclaracion() == null) {
                dyctDeclaracionDTO.setIdDeclaracion(jdbcTemplateDYC.queryForObject(SQLOracleDyC.GETIDDECLARACION.toString(),
                                                                               Integer.class));
            }
            
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_DYCT_DECLARACION.toString(),
                                   new Object[] { dyctDeclaracionDTO.getIdDeclaracion(), 
                                                  dyctDeclaracionDTO.getFechaPresentacion(),
                                                  dyctDeclaracionDTO.getFechaCausacion(),
                                                  dyctDeclaracionDTO.getNumOperacion(),
                                                  dyctDeclaracionDTO.getNumDocumento(),
                                                  dyctDeclaracionDTO.getSaldoAfavor(),
                                                  dyctDeclaracionDTO.getDevueltoCompensado(),
                                                  dyctDeclaracionDTO.getAcreditamiento(),
                                                  dyctDeclaracionDTO.getImporte(),
                                                  dyctDeclaracionDTO.getDyccUsoDecDTO().getIdUsoDec(),
                                                  dyctDeclaracionDTO.getDyccTipoDeclaraDTO().getIdTipoDeclaracion(),
                                                  dyctDeclaracionDTO.getEtiquetaSaldo(),
                                                  dyctDeclaracionDTO.getNumControl() }, tipoDatos);
        } catch (DataAccessException dae) {
            throw new DycDaoExcepcion(EDycDaoCodigoError.BD_DYC_DECLARACION_INSERT_GENERAL, null, dae);
        }
    }


    @Override
    public List<DyctDeclaracionDTO> buscarDeclaracionesNumControl(String numControl) {
        try {

            String query =
                " SELECT A.*, B.* FROM DYCT_DECLARACION A " + " INNER JOIN DYCC_TIPODECLARA B ON A.IDTIPODECLARACION = B.IDTIPODECLARACION " +
                " WHERE A.NUMCONTROL = ? ORDER BY A.IDTIPODECLARACION DESC";

            lDyctDeclaracionDTO =
                    jdbcTemplateDYC.query(query, new Object[] { numControl }, new DyctDeclaracionMapper());
            return lDyctDeclaracionDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDECLARACIONPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }   
    }

    @Override
    public void createDeclaracion(DyctDeclaracionDTO input) {
        int[] types =
        { Types.INTEGER, Types.DATE, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.DECIMAL, Types.DECIMAL,
          Types.DECIMAL, Types.DECIMAL, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR };
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATEDECLARACIONDYCT.toString(),
                                   new Object[] { input.getIdDeclaracion(), input.getFechaPresentacion(),
                                                  input.getFechaCausacion(), input.getNumOperacion(),
                                                  input.getNumDocumento(), input.getSaldoAfavor(),
                                                  input.getDevueltoCompensado(), input.getAcreditamiento(),
                                                  input.getImporte(), input.getDyccUsoDecDTO().getIdUsoDec(),
                                                  input.getDyccTipoDeclaraDTO().getIdTipoDeclaracion(),
                                                  input.getEtiquetaSaldo(), input.getNumControl() }, types);
        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CREATEDECLARACIONDYCT + ConstantesDyC1.TEXTO_3_ERROR_DAO + input.getNumOperacion());
            throw e;
        }
    }

    @Override
    public void createRelacionDeclaracion(Integer idDeclaracion, String numControl) {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATERELACIONSOLDEC.toString(), idDeclaracion, numControl);
        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CREATERELACIONSOLDEC + ConstantesDyC1.TEXTO_3_ERROR_DAO + idDeclaracion +
                      numControl);
            throw e;
        }
    }

    @Override
    public void createDeclaracionTemp(DyctDeclaraTempDTO declaraTem) {


        int[] types =

        { Types.DATE, Types.DATE, Types.VARCHAR, Types.INTEGER, Types.DECIMAL, Types.DECIMAL, Types.DECIMAL,
          Types.DECIMAL, Types.INTEGER, Types.INTEGER, Types.DATE, Types.INTEGER, Types.DECIMAL, Types.INTEGER,
          Types.VARCHAR };


        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATE_DECLARACION_TEMP.toString(),
                                   new Object[] { declaraTem.getFechaPresentacion(), declaraTem.getFechaCausacion(),
                                                  declaraTem.getNumOperacion(), declaraTem.getNumDocumento(),
                                                  declaraTem.getSaldoAFavor(), declaraTem.getDevueltoCompensado(),
                                                  declaraTem.getAcreditamiento(), declaraTem.getImporte(),
                                                  declaraTem.getIdUsoDec(), declaraTem.getIdTipoDeclaracion(),
                                                  declaraTem.getNormalFechapres(), declaraTem.getNormalNumoperacion(),
                                                  declaraTem.getNormalImportesaf(), declaraTem.getFolioServtemp(),
                                                  declaraTem.getEtiquetaSaldo() }, types);
        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CREATE_DECLARACION_TEMP + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(declaraTem) + declaraTem.getFolioServtemp());
            throw e;
        }
    }

    @Override
    public InformacionSaldoFavorTO findDeclaracionTemp(int folio) throws SQLException {
        InformacionSaldoFavorTO declaracion = null;
        try {
            declaracion =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.FIND_DECLARACION_TEMP.toString(), new Object[] { folio }, new InfoSaldoAFavorMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.FIND_DECLARACION_TEMP + ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de Folio " +
                      folio);
            throw dae;
        }
        return declaracion;
    }

    public void setSecuenciaDeclaracion(long secuenciaDeclaracion) {
        this.secuenciaDeclaracion = secuenciaDeclaracion;
    }

    public long getSecuenciaDeclaracion() {
        return secuenciaDeclaracion;
    }

    @Override
    public List<DyctDeclaracionDTO> selecXServicio(DycpServicioDTO servicio) {
        try {
            String query = " SELECT * FROM DYCT_DECLARACION WHERE NUMCONTROL = ? ";
            DyctDeclaracionMapper mapper = new DyctDeclaracionMapper();
            mapper.setServicio(servicio);
            return jdbcTemplateDYC.query(query, new Object[] { servicio.getNumControl() }, mapper);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDECLARACIONPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + servicio.getNumControl());
            throw dae;
        }
    }
    
    @Override
    public int selecXServicioNumOperacion(final String numOperacion) {
        try {
            String query = " SELECT count(1) FROM DYCT_DECLARACION WHERE numoperacion = ? ";
            return jdbcTemplateDYC.queryForObject(query, new Object[] { numOperacion }, Integer.class);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARDECLARACIONPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de numoperacion " + numOperacion);
            throw dae;
        }
    }

    @Override
    public List<DyctDeclaracionDTO> selectXNumOperacion(String numOperacion, String rfc) {
        try {
            return jdbcTemplateDYC.query(SQLOracleDyC.FIND_DECLARACION_X_NUM_OPERACION.toString(), new Object[]{numOperacion, rfc}, new DyctDeclaracionMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.FIND_DECLARACION_X_NUM_OPERACION
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + " NUMOPERACION " + numOperacion + " RFC " + rfc);
            throw dae;
        }
    }
}
