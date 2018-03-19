package mx.gob.sat.siat.dyc.casocomp.dao.districomp.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.casocomp.dao.districomp.CreaCasoCompenDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.CompensacionMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpServicioMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.SQLOracleSIAT;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


/**
 * CrearCasoCompenDAOImpl
 * @author [LADP] Luis Alberto Dominguez Palomino
 *
 */
@Repository(value = "creaCasoCompenDAO")
public class CreaCasoCompenDAOImpl implements CreaCasoCompenDAO, SQLOracleSIAT {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(CreaCasoCompenDAOImpl.class.getName());

    @Override
    public List<DycpCompensacionDTO> buscaCompensacion(DycpCompensacionDTO casoCompensacionVO) {
        String query = "SELECT DYCPC.*, DYCPS.*  FROM DYCP_COMPENSACION DYCPC\n" +
            "INNER JOIN DYCP_SERVICIO DYCPS ON DYCPC.NUMCONTROL = DYCPS.NUMCONTROL\n" +
            "INNER JOIN DYCT_SALDOICEP DYCT_DESTINO ON DYCT_DESTINO.IDSALDOICEP = DYCPC.IDSALDOICEPDESTINO\n" +
            "INNER JOIN DYCT_SALDOICEP DYCT_ORIGEN ON DYCT_ORIGEN.IDSALDOICEP = DYCPC.IDSALDOICEPORIGEN\n" +
            "WHERE 1 = 1 AND\n" +
           // "DYCT_DESTINO.IDCONCEPTO = ? AND\n" +
           // "DYCT_DESTINO.IDEJERCICIO = ? AND \n" +
           // "DYCT_DESTINO.IDPERIODO = ? AND\n" +
            "DYCT_ORIGEN.IDCONCEPTO = ? AND\n" +
            "DYCT_ORIGEN.IDEJERCICIO = ? AND \n" +
            "DYCT_ORIGEN.IDPERIODO = ? AND\n" +
            "DYCPC.FECHAPRESENTADEC = ? AND\n" +
            "DYCPC.NUMOPERACIONDEC = ? AND \n" +
            "DYCPS.RFCCONTRIBUYENTE = ? ";
        try {
            log.info(" buscar caso compensciones");
            CompensacionMapper mapper = new CompensacionMapper();
            mapper.setMapperServicio(new DycpServicioMapper());
log.info(" idconceptodes"+casoCompensacionVO.getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getIdConcepto()+" ejerciciodesti "+
                                                            casoCompensacionVO.getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO().getIdEjercicio()+" periododesti "+
                                                            casoCompensacionVO.getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO().getIdPeriodo()+" conceptoorigne "+
                                                            casoCompensacionVO.getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO().getIdConcepto()+" ejercicioorigen "+
                                                            casoCompensacionVO.getDyctSaldoIcepOrigenDTO().getDyccEjercicioDTO().getIdEjercicio()+" periodoorige "+
                                                            casoCompensacionVO.getDyctSaldoIcepOrigenDTO().getDyccPeriodoDTO().getIdPeriodo()+" fechapresedec "+
                                                            casoCompensacionVO.getFechaPresentaDec()+" numeroopera"+
                                                            casoCompensacionVO.getNumOperacionDec()+" rfccont "+
                                                            casoCompensacionVO.getDycpServicioDTO().getRfcContribuyente().trim() );
            List<DycpCompensacionDTO> lista =
                jdbcTemplateDYC.query(query, new Object[] { 
                    //casoCompensacionVO.getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getIdConcepto(),
                                                            //casoCompensacionVO.getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO().getIdEjercicio(),
                                                            //casoCompensacionVO.getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO().getIdPeriodo(),
                                                            casoCompensacionVO.getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO().getIdConcepto(),
                                                            casoCompensacionVO.getDyctSaldoIcepOrigenDTO().getDyccEjercicioDTO().getIdEjercicio(),
                                                            casoCompensacionVO.getDyctSaldoIcepOrigenDTO().getDyccPeriodoDTO().getIdPeriodo(),
                                                            casoCompensacionVO.getFechaPresentaDec(),
                                                            casoCompensacionVO.getNumOperacionDec(),
                                                            casoCompensacionVO.getDycpServicioDTO().getRfcContribuyente().trim() },
                                      mapper);
            log.info("lista.size()" + lista.size());
            if (!lista.isEmpty() && lista.size() >= 1) {
                return lista;
            } else {
                return null;
            }
        } catch (EmptyResultDataAccessException siatEmpty) {
            log.error("error controlado:" + siatEmpty.getMessage());
            return null;
        } catch (DataAccessException siatData) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatData.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     query + ConstantesDyC1.TEXTO_3_ERROR_DAO);
            return null;
        }
    }

     @Override
    public int buscaCompensacionExistente(String numeroOperacion) {
   
             return  jdbcTemplateDYC.queryForObject(SQLOracleDyC.BUSCA_COMPENSACION_EXISTENTE.toString(), new Object[] {  numeroOperacion  },
                                                   Integer.class);

    }
    @Override
    public DycpCompensacionDTO buscaCompensacionDiferente(DycpCompensacionDTO dycpCompensacionDTO) {
        DycpCompensacionDTO compensacion = null;
        try {
            compensacion =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.BUSCA_COMPENSACION_DIFERENTE.toString(), new Object[] { dycpCompensacionDTO.getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getIdConcepto(),
                                                                                                                        dycpCompensacionDTO.getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO().getIdEjercicio(),
                                                                                                                        dycpCompensacionDTO.getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO().getIdPeriodo(),
                                                                                                                        dycpCompensacionDTO.getFechaPresentaDec(),
                                                                                                                        dycpCompensacionDTO.getNumOperacionDec(),
                                                                                                                        dycpCompensacionDTO.getDycpServicioDTO().getRfcContribuyente() },
                                                   new CompensacionMapper());
        } catch (EmptyResultDataAccessException siatEmpty) {
            log.error("error controlado:" + siatEmpty.getMessage());
            compensacion = null;
        } catch (DataAccessException siatData) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatData.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.BUSCA_COMPENSACION_DIFERENTE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                     ExtractorUtil.ejecutar(dycpCompensacionDTO));
        }
        return compensacion;
    }
}
