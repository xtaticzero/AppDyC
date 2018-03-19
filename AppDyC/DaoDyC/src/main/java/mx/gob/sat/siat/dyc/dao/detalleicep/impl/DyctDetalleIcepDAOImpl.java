package mx.gob.sat.siat.dyc.dao.detalleicep.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.detalleicep.DyctDetalleIcepDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper.DyctDetalleIcepMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDetalleIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctDetalleIcepDAOImpl implements DyctDetalleIcepDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctDetalleIcepDAOImpl.class.getName());

    @Override
    public List<DyctDetalleIcepDTO> getMovimientoDeResolucionxIdSaldoIcep(Integer idSaldoICep) throws SIATException {

        try {

            List<DyctDetalleIcepDTO> lDyctDetalleIcepDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_MOVIMIENTO_DE_RESOLUCION_DETALLE_ICEP.toString(),
                                      new Object[] { idSaldoICep }, new DyctDetalleIcepMapper());

            return lDyctDetalleIcepDTO;

        } catch (DataAccessException dae) {
            log.error(dae.getMessage());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.CONSULTA_MOVIMIENTO_DE_RESOLUCION_DETALLE_ICEP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " idSaldoICep " + idSaldoICep);
            throw new SIATException("DyctDetalleIcepDAOImpl.getMovimientoByIdSaldoIcep", dae);
        }

    }

    @Override
    public void insertar(DyctDetalleIcepDTO dyctDetalleIcepDTO) throws SIATException
    {
        try {
            if(dyctDetalleIcepDTO.getIdDetalleIcep() == null){
                dyctDetalleIcepDTO.setIdDetalleIcep(jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDDETALLEICEP.NEXTVAL FROM DUAL", Integer.class));
            }

            jdbcTemplateDYC.update(SQLOracleDyC.DYCT_DETALLEICEP_INSERT.toString(),
                                   new Object[] { dyctDetalleIcepDTO.getIdDetalleIcep(),
                                                  dyctDetalleIcepDTO.getDyctSaldoIcepDTO().getIdSaldoIcep(),
                                                  dyctDetalleIcepDTO.getDyccMovIcepDTO().getIdMovimiento(),
                                                  dyctDetalleIcepDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion(),
                                                  dyctDetalleIcepDTO.getImporteMovimiento(),
                                                  dyctDetalleIcepDTO.getFechaMovimiento(),
                                                  dyctDetalleIcepDTO.getNumControl()
                                                  });
        } catch (Exception e) {
            log.error("ocurrió un error al insertar en DYCT_DETALLEICEP; mensaje de la excepción ->" + e.getMessage() + 
                      "; excepcion ->" + e.getClass().getName() + "<- ");
            ExtractorUtil.ejecutar(dyctDetalleIcepDTO);
            throw new SIATException(e.getMessage(), e);
        }

    }
    
    @Override
    public List<DyctDetalleIcepDTO> obtenerPorIdICepNumControl(Integer idSaldoICep, String numControl ) throws SIATException {

        List<DyctDetalleIcepDTO> lDyctDetalleIcepDTO = null;
        try {

            
                lDyctDetalleIcepDTO = jdbcTemplateDYC.query(SQLOracleDyC.DYCT_DETALLEICEP_OBTENER_POR_ICEP_NUMCONTROL.toString(),
                                      new Object[] { idSaldoICep, numControl }, new DyctDetalleIcepMapper());

            return lDyctDetalleIcepDTO;

        } catch (DataAccessException dae) {
            log.error(dae.getMessage());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.CONSULTA_MOVIMIENTO_DE_RESOLUCION_DETALLE_ICEP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl " + numControl +  " idSaldoICep " + idSaldoICep);
            throw new SIATException("DyctDetalleIcepDAOImpl.getMovimientoByIdSaldoIcep", dae);
        }

    }
    
    @Override
    public void actualizarNumControlDetall(String numControl, Integer idSaldoIcep, Integer idDetalleIcep) throws SIATException{
        String sql = "update dyct_detalleicep set numcontrol = ? where idsaldoicep = ? and iddetalleicep = ?";
        try{
        this.jdbcTemplateDYC.update(sql, new Object[]{numControl, idSaldoIcep, idDetalleIcep});
        }catch(Exception e){
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl " + numControl +  " idSaldoICep " + idSaldoIcep + "idDetalleIcep" + idDetalleIcep);
            throw new SIATException(e);
        }
    }

    @Override
    public List<DyctDetalleIcepDTO> selecXSaldoicep(DyctSaldoIcepDTO saldoIcep)
    {
        String query =  " SELECT * FROM DYCT_DETALLEICEP WHERE IDSALDOICEP = ? ";
        DyctDetalleIcepMapper mapper = new DyctDetalleIcepMapper();
        mapper.setSaldoIcep(saldoIcep);
        return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, mapper);

    }
}
