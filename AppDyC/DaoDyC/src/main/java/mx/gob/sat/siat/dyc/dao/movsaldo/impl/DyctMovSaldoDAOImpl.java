package mx.gob.sat.siat.dyc.dao.movsaldo.impl;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper.DyctMovSaldoMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccAfectaIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.UtilsDominio;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctMovSaldoDAOImpl implements DyctMovSaldoDAO
{
    private static final Logger LOG = Logger.getLogger(DyctMovSaldoDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void insertar(DyctMovSaldoDTO movSaldo) throws SIATException
    {
        String sentInsert =
            " INSERT INTO DYCT_MOVSALDO (IDMOVSALDO, IDSALDOICEP, IMPORTE, FECHAREGISTRO, FECHAORIGEN, IDMOVIMIENTO, IDAFECTACION, IDORIGEN) VALUES (?,?,?,?,?,?,?,?)";
        try {
            movSaldo.setIdMovSaldo(jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDDETALLEICEP.NEXTVAL FROM DUAL",
                                                                  Integer.class));

            jdbcTemplateDYC.update(sentInsert,
                                   new Object[] { movSaldo.getIdMovSaldo(), movSaldo.getDyctSaldoIcepDTO().getIdSaldoIcep(),
                                                  movSaldo.getImporte(), movSaldo.getFechaRegistro(),
                                                  movSaldo.getFechaOrigen(),
                                                  movSaldo.getDyccMovIcepDTO().getIdMovimiento(),
                                                  movSaldo.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion(),
                                                  movSaldo.getIdOrigen() });
        } catch (DataAccessException dae) {
            LOG.info("el movimiento de la declaracion no se pudo insertar porque ya existe");
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyctMovSaldoDTO> selecXSaldoicep(final DyctSaldoIcepDTO saldoIcep)
    {
        LOG.debug("INICIO selecXSaldoicep saldoIcep->" + saldoIcep);
        try{
            String query =  " SELECT MOV.*,  " + 
                            UtilsDominio.obtenerAliasColumnas("DYCT_ACCIONMOVSAL", "ACC", jdbcTemplateDYC) +
                            " FROM DYCT_MOVSALDO MOV LEFT OUTER JOIN DYCT_ACCIONMOVSAL ACC ON MOV.IDMOVSALDO = ACC.IDMOVSALDO " + 
                            " WHERE IDSALDOICEP = ? AND FECHAFIN IS NULL ORDER BY MOV.IDMOVSALDO ASC, ACC.FECHAREGISTRO ASC ";
            LOG.debug("query ->" + query);
            DyctMovSaldoExtractorResultSet extractorResultSet = new DyctMovSaldoExtractorResultSet();
            extractorResultSet.setSaldoIcep(saldoIcep);

            return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, extractorResultSet);
        }
        catch(Exception e){
            //TODO: Una vez que se asegure que ya existen la tabla DYCT_ACCIONMOVSAL se debe quitar este catch
            LOG.error("ocurrio un error al obtener los movimientos de un ICEP; ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
            LOG.info("se ejecutará el query sin las tablas nuevas");
            String query =  " SELECT * FROM DYCT_MOVSALDO WHERE IDSALDOICEP = ? AND FECHAFIN IS NULL ";
            DyctMovSaldoMapper mapper = new DyctMovSaldoMapper();
            mapper.setSaldoIcep(saldoIcep);
            return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, mapper);
        }
    }

    @Override
    public List<DyctMovSaldoDTO> selecXMovsicep(DyccMovIcepDTO movIcep)
    {
        String query =  " SELECT * FROM DYCT_MOVSALDO WHERE IDMOVIMIENTO = ? AND IDAFECTACION = ? AND FECHAFIN IS NULL ";
        DyctMovSaldoMapper mapper = new DyctMovSaldoMapper();
        mapper.setTipoMovimiento(movIcep);
        return jdbcTemplateDYC.query(query, new Object[] { movIcep.getIdMovimiento(), movIcep.getDyccAfectaIcepDTO().getIdAfectacion() }, mapper);
    }

    @Override
    public List<DyctMovSaldoDTO> selecOrderXMovsicep(DyccMovIcepDTO movIcep, String orderBy)
    {
        String query =  " SELECT * FROM DYCT_MOVSALDO WHERE IDMOVIMIENTO = ? AND IDAFECTACION = ? AND FECHAFIN IS NULL " + orderBy;
        DyctMovSaldoMapper mapper = new DyctMovSaldoMapper();
        mapper.setTipoMovimiento(movIcep);
        return jdbcTemplateDYC.query(query, new Object[] { movIcep.getIdMovimiento(), movIcep.getDyccAfectaIcepDTO().getIdAfectacion() }, mapper);
    }

    @Override
    public List<DyctMovSaldoDTO> selecOrderXSaldoicepMovsicep(DyctSaldoIcepDTO saldoIcep, DyccMovIcepDTO movIcep,
                                                              String orderBy)
    {
        String query =  " SELECT * FROM DYCT_MOVSALDO WHERE IDSALDOICEP = ? AND IDMOVIMIENTO = ? AND IDAFECTACION = ? AND FECHAFIN IS NULL " + orderBy;
        DyctMovSaldoMapper mapper = new DyctMovSaldoMapper();
        mapper.setTipoMovimiento(movIcep);
        return jdbcTemplateDYC.query(query, new Object[] {  saldoIcep.getIdSaldoIcep(),
                                                            movIcep.getIdMovimiento(), 
                                                            movIcep.getDyccAfectaIcepDTO().getIdAfectacion() }, mapper);
    }
    
    @Override
    public List<DyctMovSaldoDTO> selecOrderXSaldoicepAfectacion(DyctSaldoIcepDTO saldoIcep, DyccAfectaIcepDTO afectacion,
                                                              String orderBy)
    {
        try{
            String query =  " SELECT * FROM DYCT_MOVSALDO MOV LEFT OUTER JOIN DYCT_ACCIONMOVSAL ACC ON ACC.IDMOVSALDO = MOV.IDMOVSALDO " + 
                            " WHERE IDSALDOICEP = ? AND IDAFECTACION = ? AND FECHAFIN IS NULL " + orderBy;

            DyctMovSaldoExtractorResultSet extractorResultSet = new DyctMovSaldoExtractorResultSet();
            extractorResultSet.setSaldoIcep(saldoIcep);

            return jdbcTemplateDYC.query(query, new Object[] {  saldoIcep.getIdSaldoIcep(),
                                                                afectacion.getIdAfectacion() }, extractorResultSet);
        }
        catch(Exception e){
            //TODO: una vez que se asegure que las tablas del modulo ajuste saldos estan listas en la bd.. debe retirarse este catch
            LOG.error("ocurrio un error al obtener los movimientos de un ICEP (order); ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
            LOG.info("se obtendrán los movimientos sin considerar las tablas del modulo ajustes");
            String query =  " SELECT * FROM DYCT_MOVSALDO WHERE IDSALDOICEP = ? AND IDAFECTACION = ? AND FECHAFIN IS NULL " + orderBy;
            DyctMovSaldoMapper mapper = new DyctMovSaldoMapper();
            mapper.setSaldoIcep(saldoIcep);
            return jdbcTemplateDYC.query(query, new Object[] {  saldoIcep.getIdSaldoIcep(),
                                                                afectacion.getIdAfectacion() }, mapper);
        }
    }

    @Override
    public List<DyctMovSaldoDTO> selecXSaldoicepIdorigen(DyctSaldoIcepDTO saldoIcep, String idOrigen)
    {
        String query =  " SELECT * FROM DYCT_MOVSALDO WHERE IDSALDOICEP = ? AND IDORIGEN = ? AND FECHAFIN IS NULL";
        DyctMovSaldoMapper mapper = new DyctMovSaldoMapper();
        mapper.setSaldoIcep(saldoIcep);
        return jdbcTemplateDYC.query(query, new Object[] {  saldoIcep.getIdSaldoIcep(), idOrigen }, mapper);
    }

    @Override
    public List<DyctMovSaldoDTO> selecXSaldoicepIdorigenConNulos(DyctSaldoIcepDTO saldoIcep, String idOrigen)
    {
        String query =  " SELECT * FROM DYCT_MOVSALDO WHERE IDSALDOICEP = ? AND IDORIGEN = ? ";
        DyctMovSaldoMapper mapper = new DyctMovSaldoMapper();
        mapper.setSaldoIcep(saldoIcep);
        return jdbcTemplateDYC.query(query, new Object[] {  saldoIcep.getIdSaldoIcep(), idOrigen }, mapper);
    }

    @Override
    public void borrar(Integer idMovSaldo) throws SIATException
    {
        try{
            String sentencia = " UPDATE DYCT_MOVSALDO SET FECHAFIN = ? WHERE IDMOVSALDO = ?";
            jdbcTemplateDYC.update(sentencia, new Object[] {new Date(), idMovSaldo});
        }
        catch(Exception e){
            LOG.error("Ocurrio un error al reactivar el saldo en DYCT_MOVSALDO; idMovSaldo ->" + idMovSaldo + "; mensaje: " + e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    public void reactivar(Integer idMovSaldo) throws SIATException
    {
        try{
            String sentencia = " UPDATE DYCT_MOVSALDO SET FECHAFIN = NULL WHERE IDMOVSALDO = ?";
            jdbcTemplateDYC.update(sentencia, new Object[] { idMovSaldo});
        }
        catch(Exception e){
            LOG.error("Ocurrio un error en el borrado logico en DYCT_MOVSALDO; idMovSaldo ->" + idMovSaldo + "; mensaje: " + e.getMessage());
            throw new SIATException(e);
        }
    }
}
