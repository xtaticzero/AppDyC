package mx.gob.sat.siat.dyc.dao.util.impl;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DyctSaldoIcepMapper;
import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ConceptoMapper;
import mx.gob.sat.siat.dyc.dao.periodo.impl.mapper.PeriodoMapper;
import mx.gob.sat.siat.dyc.dao.util.DyctCompHistoricaDAO;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DyctCompHistoricaMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctCompHistoricaDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctCompHistoricaDAOImpl implements DyctCompHistoricaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctCompHistoricaDAOImpl.class);

    @Override
    public void actualizarMovCompensacion(DyctCompHistoricaDTO movCompensacion) throws SIATException {
        try {
            this.jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_MOVIMIENTOS_COMPENSACION.toString(),
                                        new Object[] { 
                                                       movCompensacion.getImporteCompensado(),                                                    
                                                        movCompensacion.getIdMovCompensacion() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZA_MOVIMIENTOS_COMPENSACION + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(movCompensacion));
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyctCompHistoricaDTO> obtenerXidSaldoIcep(int idSaldoIcep)
    {
        try {
            String query = " SELECT PD.DESCRIPCION DESCRIPCION_PERIODO, MC.*, SD.*, CD.*, PD.* " +
                            " FROM DYCT_COMPHISTORICA MC, DYCT_SALDOICEP SD, DYCC_CONCEPTO CD, DYCC_PERIODO PD " + 
                            " WHERE MC.IDSALDOICEPDESTINO = SD.IDSALDOICEP AND CD.IDCONCEPTO = SD.IDCONCEPTO " +
                            " AND SD.IDPERIODO = PD.IDPERIODO AND IDSALDOICEPORIGEN = ? AND MC.FECHAFIN IS NULL ";
            ConceptoMapper mapperConcepto = new ConceptoMapper();
            PeriodoMapper mapperPeriodo = new PeriodoMapper();
            DyctSaldoIcepMapper mapperIcepDestino = new DyctSaldoIcepMapper();
            mapperIcepDestino.setMapperConcepto(mapperConcepto);
            mapperIcepDestino.setMapperPeriodo(mapperPeriodo);
            DyctCompHistoricaMapper mapper = new DyctCompHistoricaMapper();
            mapper.setMapperIcepDestino(mapperIcepDestino);
            return jdbcTemplateDYC.query(query, new Object[] { idSaldoIcep }, mapper);
        } 
        catch (DataAccessException dae) {
            log.error(dae.getMessage());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.DYCT_MOVCOMPENSACION_OBTENER_POR_IDSALDOICEP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " idSaldoIcep " + idSaldoIcep );
            throw dae;
        }
    }

    @Override
    public void insertar(DyctCompHistoricaDTO compManual) throws SIATException
    {
        try{
            compManual.setIdMovCompensacion(jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDMOVCOMP.NEXTVAL FROM DUAL", Integer.class));
            
            String sentInsert = " INSERT INTO DYCT_COMPHISTORICA (NUMCONTROL, FECHADECLARADEST, IMPORTECOMPENSADO, IMPORTELIQUIDADO," +
                                " FECHARESOLUCION, IDTIPORESOL, IDMOVCOMPENSACION, IDSALDOICEPORIGEN, " +
                                " IDSALDOICEPDESTINO, NUMDOCDETERMINANTE, NOTAS) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";

            this.jdbcTemplateDYC.update(sentInsert, new Object[] {  compManual.getNumControl() ,
                                                                    compManual.getFechaDeclaraDest(),
                                                                    compManual.getImporteCompensado(),
                                                                    compManual.getImporteLiquidado(),
                                                                    compManual.getFechaResolucion(),
                                                                    compManual.getDyccTipoResolDTO().getIdTipoResol(),
                                                                    compManual.getIdMovCompensacion(),
                                                                    compManual.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep(),
                                                                    compManual.getDyctSaldoIcepDestinoDTO().getIdSaldoIcep(),
                                                                    compManual.getNumDocDeterminante(),
                                                                    compManual.getNotas()});
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    public DyctCompHistoricaDTO encontrarXNumcontrol(String numControl) throws SIATException
    {
        try{
            String query = " SELECT * FROM DYCT_COMPHISTORICA WHERE NUMCONTROL = ? AND FECHAFIN IS NULL";
            return jdbcTemplateDYC.queryForObject(query, new Object[] { numControl }, new DyctCompHistoricaMapper());
        }
        catch(EmptyResultDataAccessException erdae){
            return null;
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    public void borrar(Integer idMovCompensacion) throws SIATException
    {
        try{
            String sentencia = " UPDATE DYCT_COMPHISTORICA SET FECHAFIN = ? WHERE IDMOVCOMPENSACION = ?";
            jdbcTemplateDYC.update(sentencia, new Object[] {new Date(), idMovCompensacion});
        }
        catch(Exception e){
            log.error("Ocurrio un error en el borrado logico en DYCT_COMPHISTORICA; idMovCompensacion ->" + idMovCompensacion + "; mensaje: " + e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    public DyctCompHistoricaDTO encontrar(Integer idMovCompensacion)
    {
        String query = "SELECT * FROM DYCT_COMPHISTORICA WHERE IDMOVCOMPENSACION = ? AND FECHAFIN IS NULL";
        try {
            return this.jdbcTemplateDYC.queryForObject(query, new Object[] { idMovCompensacion }, new DyctCompHistoricaMapper());
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            log.error("NO se encontro DYCT_COMPHISTORICA con idMovCompensacion ->" + idMovCompensacion);
            return null;
        } 
    }
}