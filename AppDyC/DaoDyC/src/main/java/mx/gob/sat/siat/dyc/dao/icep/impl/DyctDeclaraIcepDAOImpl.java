package mx.gob.sat.siat.dyc.dao.icep.impl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.impl.mapper.DyctDeclaraIcepMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.DyctDeclaraIcepAuxDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "daoDeclaraIcepEst")
public class DyctDeclaraIcepDAOImpl implements DyctDeclaraIcepDAO
{
    private static final Logger LOG = Logger.getLogger(DyctDeclaraIcepDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void insertar(DyctDeclaraIcepDTO declaraIcep)
    {
        StringBuilder sentInsert;
        
        if(declaraIcep.getIdDeclaraIcep() == null){
            declaraIcep.setIdDeclaraIcep(jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDDECLARAICEP.NEXTVAL FROM dual", Integer.class));
        }

        int[] tiposDatos = new int[]{Types.BIGINT,
                                     Types.TIMESTAMP,
                                     Types.INTEGER,
                                     Types.INTEGER,
                                     Types.INTEGER,
                                     Types.DECIMAL,
                                     Types.TIMESTAMP, Types.SMALLINT, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR};

        sentInsert = new StringBuilder(" INSERT INTO DYCT_DECLARAICEP(NUMOPERACION, FECHAPRESENTACION, IDTIPODECLARACION, " )
                            .append(" IDDECLARAICEP, IDSALDOICEP, SALDOAFAVOR, VALIDACIONDWH, ORIGENDECLARA, FECHAREGISTRO, USRREGISTRO, NOTAS) " )
                            .append(" VALUES(?,?,?,?,?,?,?,?,?,?,?)");
        
        jdbcTemplateDYC.update (sentInsert.toString(),
                                new Object[] {  declaraIcep.getNumOperacion(), 
                                                declaraIcep.getFechaPresentacion(),
                                                declaraIcep.getDyccTipoDeclaraDTO().getIdTipoDeclaracion(),
                                                declaraIcep.getIdDeclaraIcep(),
                                                declaraIcep.getDyctSaldoIcepDTO().getIdSaldoIcep(),
                                                declaraIcep.getSaldoAFavor(),
                                                declaraIcep.getValidacionDWH(),
                                                declaraIcep.getOrigenDeclara(),
                                                declaraIcep.getFechaRegistro(),
                                                declaraIcep.getUsrRegistro(), 
                                                declaraIcep.getNotas()}, tiposDatos);
    }

    @Override
    public List<DyctDeclaraIcepDTO> selecXSaldoicep(DyctSaldoIcepDTO saldoIcep) throws SIATException
    {
        try
        {
            String query = "SELECT * FROM DYCT_DECLARAICEP WHERE IDSALDOICEP = ? AND FECHAFIN IS NULL ";
            DyctDeclaraIcepMapper mapper = new DyctDeclaraIcepMapper();
            mapper.setSaldoIcep(saldoIcep);
            return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, mapper);
        }
        catch (DataAccessException dae)
        {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + saldoIcep.getIdSaldoIcep());
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyctDeclaraIcepDTO> selecXSaldoicepOrder(DyctSaldoIcepDTO saldoIcep, String orderBy) throws SIATException
    {
        try
        {
            String query = "SELECT * FROM DYCT_DECLARAICEP WHERE IDSALDOICEP = ? AND FECHAFIN IS NULL "
            + orderBy;
            LOG.debug("query ->" + query + "<-");
            LOG.debug("idSaldoIcep ->" + saldoIcep.getIdSaldoIcep() + "<-");
            DyctDeclaraIcepMapper mapper = new DyctDeclaraIcepMapper();
            mapper.setSaldoIcep(saldoIcep);
            return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, mapper);
        }
        catch (DataAccessException dae)
        {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + saldoIcep.getIdSaldoIcep());
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyctDeclaraIcepDTO> selecXSaldoicepOrderCancel(DyctSaldoIcepDTO saldoIcep, String orderBy) throws SIATException
    {
        try
        {
            String query = "SELECT * FROM DYCT_DECLARAICEP WHERE IDSALDOICEP = ? AND FECHAFIN IS NOT NULL "
            + orderBy;
            LOG.debug("query ->" + query + "<-");
            LOG.debug("idSaldoIcep ->" + saldoIcep.getIdSaldoIcep() + "<-");
            DyctDeclaraIcepMapper mapper = new DyctDeclaraIcepMapper();
            mapper.setSaldoIcep(saldoIcep);
            return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, mapper);
        }
        catch (DataAccessException dae)
        {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + saldoIcep.getIdSaldoIcep());
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DyctDeclaraIcepDTO> selecXSaldoicepOrderCompleto(DyctSaldoIcepDTO saldoIcep, String orderBy) throws SIATException
    {
        StringBuilder query;
        try
        {
             query = new StringBuilder(" SELECT * FROM (SELECT * FROM DYCT_DECLARAICEP WHERE IDSALDOICEP = ? AND FECHAFIN IS NULL ")
            .append(" UNION ALL ")
            .append(" SELECT * FROM DYCT_DECLARAICEP WHERE IDSALDOICEP = ? AND ORIGENDECLARA = 2 AND FECHAFIN IS NOT NULL) ")
            .append(orderBy);
            LOG.debug("query    ->" + query + "<-  ");
            LOG.debug(" idSaldoIcep   ->" + saldoIcep.getIdSaldoIcep() + "  <-   ");
            DyctDeclaraIcepMapper mapper = new DyctDeclaraIcepMapper();
            mapper.setSaldoIcep(saldoIcep);
            return jdbcTemplateDYC.query(query.toString(), new Object[] { saldoIcep.getIdSaldoIcep(), saldoIcep.getIdSaldoIcep() }, mapper);
        }
        catch (DataAccessException dae)
        {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + saldoIcep.getIdSaldoIcep());
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DyctDeclaraIcepDTO> selecCancelXSaldoicepOrder(DyctSaldoIcepDTO saldoIcep, String orderBy) throws SIATException
    {
        try
        {
            String query = "SELECT * FROM DYCT_DECLARAICEP WHERE IDSALDOICEP = ? AND FECHAFIN IS NULL" + orderBy;
            LOG.debug("query  ->" + query + "<- ");
            LOG.debug("idSaldoIcep  ->" + saldoIcep.getIdSaldoIcep() + "<- ");
            DyctDeclaraIcepMapper mapper = new DyctDeclaraIcepMapper();
            mapper.setSaldoIcep(saldoIcep);
            return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, mapper);
        }
        catch (DataAccessException dae)
        {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + saldoIcep.getIdSaldoIcep());
            throw new SIATException(dae);
        }
    }
    
    /**
     * metodo para obtener la lista de declaraciones icep por
     * la llave foranea idSaldoIcep que representa la union entre
     * [DYCT_DECLARAICEP] y [DYCT_SALDOICEP].
     * @param idSaldoIcep
     * @return
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    public List<DyctDeclaraIcepAuxDTO> obtenerRemanenteXIdSaldoIcep(Integer idSaldoIcep) throws SIATException
    {
        LOG.debug("idSaldoIcep ->" + idSaldoIcep + "<-");
        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        saldoIcep.setIdSaldoIcep(idSaldoIcep);
        List<DyctDeclaraIcepDTO> declaraciones  = selecXSaldoicepOrder(saldoIcep, " ORDER BY TRUNC(FECHAPRESENTACION) ASC, IDTIPODECLARACION ASC, FECHAREGISTRO ASC ");
        List<DyctDeclaraIcepAuxDTO> vos = new ArrayList<DyctDeclaraIcepAuxDTO>();
        for(DyctDeclaraIcepDTO declaraIcep : declaraciones)
        {
            DyctDeclaraIcepAuxDTO vo = new DyctDeclaraIcepAuxDTO();
            vo.setDyctSaldoIcepDTO(declaraIcep.getDyctSaldoIcepDTO());
            vo.setIdDeclaraIcep(declaraIcep.getIdDeclaraIcep());
            vo.setNumOperacion(declaraIcep.getNumOperacion());
            vo.setSaldoAfavor(declaraIcep.getSaldoAFavor());
            vo.setFechaPresentacion(declaraIcep.getFechaPresentacion());
            vo.setDyccTipoDeclaraDTO(declaraIcep.getDyccTipoDeclaraDTO());
            vo.setAltaSaldo(declaraIcep.getSaldoAFavor());
            vo.setValidacionDWH(declaraIcep.getValidacionDWH());
            vos.add(vo);
        }

        return vos;
    }
    
    @Override
    public List<DyctDeclaraIcepAuxDTO> obtenerRemanenteActivasXIdSaldoIcep(Integer idSaldoIcep) throws SIATException {
        //TODO: (en refactor) optimizar codigo
        return obtenerRemanenteXIdSaldoIcep(idSaldoIcep);
    }

    @Override
    public void actualizarValidacionDWH(DyctDeclaraIcepDTO declaraIcep) 
    {
        String sentencia = " UPDATE DYCT_DECLARAICEP SET VALIDACIONDWH = ? WHERE IDDECLARAICEP = ?";
        jdbcTemplateDYC.update(sentencia,
                               new Object[] {   declaraIcep.getValidacionDWH(),
                                                declaraIcep.getIdDeclaraIcep()
                                              });
    }
    
    @Override
    public List<DyctDeclaraIcepDTO> buscaDeclaraOrigenXNumCntrol(String numControlRemanente, String rfcContribuyente) throws SIATException{
        List<DyctDeclaraIcepDTO> declaraExiste = null;
      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DT.* FROM DYCP_SERVICIO DS INNER JOIN DYCP_COMPENSACION DC ON DS.NUMCONTROL = DC.NUMCONTROL ");
        sql.append(" INNER JOIN DYCT_DECLARAICEP DT ON DC.IDSALDOICEPORIGEN = DT.IDSALDOICEP ");    
        sql.append(" Where DS.NUMCONTROL = ? AND DS.RFCCONTRIBUYENTE = ? ");
        sql.append(" ORDER BY DT.IDTIPODECLARACION DESC, DT.FECHAPRESENTACION DESC");
        
              
        try {
            declaraExiste =
                    jdbcTemplateDYC.query(sql.toString(), new Object[] { numControlRemanente, rfcContribuyente}, new DyctDeclaraIcepMapper());
        } catch (EmptyResultDataAccessException siatEmpty) {
            declaraExiste = null;
        } catch (Exception siatE) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControlRemanente + "RFC " +
                      rfcContribuyente);
            throw new SIATException(siatE);
        }
        return declaraExiste;
    }

    @Override
    public void borrar(Integer idDeclaraIcep) throws SIATException
    {
        try{
            String sentencia = " UPDATE DYCT_DECLARAICEP SET FECHAFIN = ? WHERE IDDECLARAICEP = ?";
            jdbcTemplateDYC.update(sentencia, new Object[] {new Date(), idDeclaraIcep });
        }
        catch(Exception e){
            LOG.error("Ocurrio un error en el borrado logico; idDeclaraIcep ->" + idDeclaraIcep + "; mensaje: " + e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    public void reactivar(Integer idDeclaraIcep) throws SIATException
    {
        try{
            String sentencia = " UPDATE DYCT_DECLARAICEP SET FECHAFIN = NULL WHERE IDDECLARAICEP = ?";
            jdbcTemplateDYC.update(sentencia, new Object[] { idDeclaraIcep });
        }
        catch(Exception e){
            LOG.error("Ocurrio un error al reactivar el trámite; idDeclaraIcep ->" + idDeclaraIcep + "; mensaje: " + e.getMessage());
            throw new SIATException(e);
        }
    }
    
    @Override
    public DyctDeclaraIcepDTO encontrar(Integer idDeclaraIcep)
    {
        try {
            String query = "SELECT * FROM DYCT_DECLARAICEP WHERE IDDECLARAICEP = ? AND FECHAFIN IS NULL ";
            DyctDeclaraIcepMapper mapper = new DyctDeclaraIcepMapper();
            return jdbcTemplateDYC.queryForObject(query, new Object[] {idDeclaraIcep}, mapper);
        }
        catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            LOG.info("NO se encontro ninguna DYCT_DECLARAICEP idDeclaraIcep ->" + idDeclaraIcep);
            return null;
        } 
    }
    
    @Override
    public DyctDeclaraIcepDTO encontrarConNulos(Integer idDeclaraIcep)
    {
        try {
            String query = "SELECT * FROM DYCT_DECLARAICEP WHERE IDDECLARAICEP = ?  ";
            DyctDeclaraIcepMapper mapper = new DyctDeclaraIcepMapper();
            return jdbcTemplateDYC.queryForObject(query, new Object[] {idDeclaraIcep}, mapper);
        }
        catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            LOG.info("NO se encontro ninguna DYCT_DECLARAICEP idDeclaraIcep ->" + idDeclaraIcep);
            return null;
        } 
    }
    //TODO: optimizar para todos los campos. Validar commas en sentencia update
    // por el momento solo contempla 4 campos: VALIDACIONDWH, ORIGENDECLARA, USRREGISTRO, NOTAS
    @Override
    public int actualizarSinNulos(DyctDeclaraIcepDTO declaraIcep)
    {
        List<Object> parametros = new ArrayList<Object>();
        StringBuilder sbSentencia = new StringBuilder();
        
        
        sbSentencia.append(" UPDATE DYCT_DECLARAICEP SET ");
        if(declaraIcep.getValidacionDWH() != null){
            sbSentencia.append(" VALIDACIONDWH = ?, ");
            parametros.add(declaraIcep.getValidacionDWH());
        }
        
        if(declaraIcep.getOrigenDeclara() != null){
            sbSentencia.append(" ORIGENDECLARA = ?, ");
            parametros.add(declaraIcep.getOrigenDeclara());
        }
        
        if(declaraIcep.getUsrRegistro() != null){
            sbSentencia.append(" USRREGISTRO = ?, ");
            parametros.add(declaraIcep.getUsrRegistro());
        }
        
        if(declaraIcep.getNotas() != null){
            sbSentencia.append(" NOTAS = ? ");
            parametros.add(declaraIcep.getNotas());
        }
        
        parametros.add(declaraIcep.getIdDeclaraIcep());
        parametros.add(declaraIcep.getDyctSaldoIcepDTO().getIdSaldoIcep());
        
        sbSentencia.append(" WHERE IDDECLARAICEP = ? AND IDSALDOICEP = ? " );
        
        return jdbcTemplateDYC.update(sbSentencia.toString(), parametros.toArray());
    }
}
