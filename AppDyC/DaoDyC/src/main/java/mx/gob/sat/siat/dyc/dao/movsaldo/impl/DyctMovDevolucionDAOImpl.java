package mx.gob.sat.siat.dyc.dao.movsaldo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovDevolucionDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper.DyctMovDevolucionMapper;
import mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper.DyctMovDevolucionVarMapper;
import mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper.DyctMovResolucionAuxMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovDevolucionDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DyctResolucionAuxVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DyctMovDevolucionDAOImpl implements DyctMovDevolucionDAO
{
    private static final Logger LOG = Logger.getLogger(DyctMovDevolucionDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Obtiene los datos de los movimientos sobre una devolucion a traves de el numero de control
     *
     * @param numControl
     * @return
     */
    @Override
    public DyctMovDevolucionDTO consultar(String numControl) throws SIATException
    {
        try {
            LOG.debug("INICIO consultar");
            String query =  " SELECT * FROM DYCT_MOVDEVOLUCION WHERE NUMCONTROL = ? AND FECHAFIN IS NULL ";

            return jdbcTemplateDYC.queryForObject(query, new Object[] { numControl },
                                                   new DyctMovDevolucionMapper());
        } 
        catch(EmptyResultDataAccessException erdae){
            return null;
        }
        catch (Exception dae) {
            LOG.error("consultar(); parametros: numControl=" + numControl + "; causa:" + dae);
            throw new SIATException(dae);
        }
    }

    @Override
    public void actualizarMovDevolucion(DyctMovDevolucionDTO movDevolucion) {
        try {
            this.jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_MOVIMIENTOS_DEVOLUCION.toString(),
                                        new Object[] { movDevolucion.getImporteAutorizado(),
                                                       movDevolucion.getImporteNegado(),
                                                       movDevolucion.getActualizacion(), movDevolucion.getIntereses(),
                                                        movDevolucion.getIdMovDevolucion() });
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZA_MOVIMIENTOS_DEVOLUCION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(movDevolucion));
        }
    }

    @Override
    public List<DyctResolucionAuxVO> obtenerMovResolucion(String rfc) {
        List<DyctResolucionAuxVO> dyctMovResolucionList = new ArrayList<DyctResolucionAuxVO>();
        try {
            dyctMovResolucionList =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_RESOLUCION_DEV.toString(), new Object[] { rfc }, new DyctMovResolucionAuxMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.OBTENER_RESOLUCION_DEV + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc);
        }
        return dyctMovResolucionList;
    }

    @Override
    public List<DyctResolucionAuxVO> obtenerMovResDev(String rfc, int tipo) {
        List<DyctResolucionAuxVO> dyctMovDevResList = new ArrayList<DyctResolucionAuxVO>();
        try {
            if (tipo == 0) {
                //busqueda normal
                dyctMovDevResList =
                        this.jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_MOV_DEVOLUCION.toString(), new Object[] { rfc }, new DyctMovResolucionAuxMapper());
            } else {
                //busqueda por hist y normal
                dyctMovDevResList =
                        this.jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_MOV_DEV_RES.toString(), new Object[] { rfc, rfc }, new DyctMovResolucionAuxMapper());
            }
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.OBTENER_MOV_DEV_RES + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc);
        }
        return dyctMovDevResList;
    }

    public List<DyctMovDevolucionDTO> obtenerXidSaldoIcep(int idSaldoIcep) throws SIATException
    {
        try {
            String query = "SELECT * FROM DYCT_MOVDEVOLUCION WHERE IDSALDOICEP = ? AND FECHAFIN IS NULL";
            return jdbcTemplateDYC.query(query, new Object[] { idSaldoIcep },
                                          new DyctMovDevolucionVarMapper());
        } 
        catch (DataAccessException dae)
        {
            LOG.error(dae.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.CONSULTA_MOVIMIENTO_DE_RESOLUCION_DETALLE_ICEP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " idSaldoIcep " + idSaldoIcep);
            throw new SIATException(dae);
        }
    }

    @Override
    public void insertar(DyctMovDevolucionDTO devolucionManual) throws SIATException 
    {
        String sentInsert = " INSERT INTO DYCT_MOVDEVOLUCION (NUMCONTROL, FECHARESOLUCION, IMPORTESOLDEV, IMPORTEAUTORIZADO, IMPORTENEGADO, " +
        " ACTUALIZACION, INTERESES, IMPORTENETODEV, IDTIPORESOL, IDMOVDEVOLUCION, IDSALDOICEP, RETINTERESES, IMPCOMPENSADO)  VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
         
        try{                                                       
            devolucionManual.setIdMovDevolucion(this.jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDMOVDEV.NEXTVAL FROM DUAL", Integer.class));
            
            jdbcTemplateDYC.update(sentInsert,
                                   new Object[] {   devolucionManual.getNumControl(),
                                                    devolucionManual.getFechaResolucion(),
                                                    devolucionManual.getImporteSolDev(),
                                                    devolucionManual.getImporteAutorizado(),
                                                    devolucionManual.getImporteNegado(),
                                                    devolucionManual.getActualizacion(),
                                                    devolucionManual.getIntereses(),
                                                    devolucionManual.getImporteNetoDev(),
                                                    devolucionManual.getIdTipoResol().getIdTipoResol(),
                                                    devolucionManual.getIdMovDevolucion(),
                                                    devolucionManual.getDyctSaldoIcepDTO().getIdSaldoIcep(),
                                                    devolucionManual.getRetIntereses(),
                                                    devolucionManual.getImpCompensado()});
        }
        catch(Exception e){
            throw new SIATException(e.getMessage(), e);
        }
                                                                
    }

    @Override
    public void borrar(Integer idMovDevolucion) throws SIATException 
    {
        try{
            String sentencia = " UPDATE DYCT_MOVDEVOLUCION SET FECHAFIN = ? WHERE IDMOVDEVOLUCION = ?";
            jdbcTemplateDYC.update(sentencia, new Object[] {new Date(), idMovDevolucion});
        }
        catch(Exception e){
            LOG.error("Ocurrio un error en el borrado logico en DYCT_MOVDEVOLUCION; idMovDevolucion ->" + idMovDevolucion + "; mensaje: " + e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    public DyctMovDevolucionDTO encontrar(Integer idMovDevolucion)
    {
        String query = "SELECT * FROM DYCT_MOVDEVOLUCION WHERE IDMOVDEVOLUCION = ? AND FECHAFIN IS NULL";
        try {
            return this.jdbcTemplateDYC.queryForObject(query, new Object[] { idMovDevolucion }, new DyctMovDevolucionMapper());
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            LOG.error("NO se encontro DYCT_MOVDEVOLUCION con idMovDevolucion ->" + idMovDevolucion);
            return null;
        } 
    }
}
