package mx.gob.sat.siat.dyc.dao.seguimiento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.seguimiento.DyctSeguimientoDAO;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DyctSeguimientoMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSeguimientoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleAprobarDoc;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyctSeguimientoDAO")
public class DyctSeguimientoDAOImpl implements DyctSeguimientoDAO {
    private Integer secuencia;

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctSeguimientoDAOImpl.class.getName());

    @Override
    public DyctSeguimientoDTO encontrar(Integer id) {
        return null;
    }

    @Override
    public List<DyctSeguimientoDTO> selecXDocumentocc(DyctDocumentoDTO documentoCc) {
        String sentSql = " SELECT * FROM DYCT_SEGUIMIENTO WHERE IDDOCUMENTOCOMP = ?";
        DyctSeguimientoMapper mapper = new DyctSeguimientoMapper();
        /**mapper.setDocumentoCc(documentoCc);*/
        return jdbcTemplateDYC.query(sentSql, new Object[] { documentoCc.getNumControlDoc() }, mapper);
    }

    @Override
    public List<DyctSeguimientoDTO> selecXCompensacion(DycpCompensacionDTO compensacion) {
        return jdbcTemplateDYC.query(SQLOracleAprobarDoc.SEGUIMIENTO_X_COMPENSACION,
                                               new Object[] { compensacion.getDycpServicioDTO().getNumControl() },
                                               new DyctSeguimientoMapper());
    }

    @Override
    public List<DyctSeguimientoDTO> selecXServicio(DycpServicioDTO servicio) {

        try {
            /**
            List<DyctSeguimientoDTO> lDyctSeguimientoDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.BUSCARSEGUIMIENTOSPORSERVICIO,
                                          new Object[] { servicio.getNumControl() }, new DyctSeguimientoMapper());

            return lDyctSeguimientoDTO;*/
            return null;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.BUSCARSEGUIMIENTOSPORSERVICIO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(servicio));
            throw dae;
        }
    }

    @Override
    public void borrarSeguimientos(DyctDocumentoDTO dyctDocumentoDTO, List<DyctSeguimientoDTO> lista) throws SIATException{

        try {

            for (int i = 0; i < lista.size(); i++) {

                jdbcTemplateDYC.update(SQLOracleDyC.EMITIRRESOLUCION_BORRARSEGUMIENTOS.toString(),
                                       new Object[] { dyctDocumentoDTO.getNumControlDoc(),
                                                      lista.get(i).getIdSeguimiento() });

            }

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.EMITIRRESOLUCION_BORRARSEGUMIENTOS +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " dyctDocumentoDTO " + dyctDocumentoDTO);
            throw new SIATException(dae);
        }

    }

    @Override
    public void limpiarSeguimientos(DyctDocumentoDTO dyctDocumentoDTO) throws SIATException{

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.EMITIRRESOLUCION_LIMPIARSEGUMIENTOS.toString(),
                                   new Object[] { dyctDocumentoDTO.getDycpServicioDTO().getNumControl() });

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.EMITIRRESOLUCION_BORRARSEGUMIENTOS +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " dyctDocumentoDTO " + dyctDocumentoDTO);
            throw new SIATException(dae);
        }

    }

    /**
     * Inserta un seguimiento a un documento de requerimiento
     *
     * @param objetoDyctSeguimiento objeto DyctSeguimientoDTO que contiene todos los datos del seguimiento
     */
    @Override
    public void insertar(DyctSeguimientoDTO objetoDyctSeguimiento) throws SIATException {
        try {
            obtenerSecuencia();
            log.debug("secuencia idSeguimiento ->" + secuencia);
            objetoDyctSeguimiento.setIdSeguimiento(secuencia);
    
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_SEGUIMIENTO.toString(),
                                   new Object[] { objetoDyctSeguimiento.getIdSeguimiento(),
                                                  objetoDyctSeguimiento.getDyccAccionSegDTO().getIdAccionSeg(),
                                                  objetoDyctSeguimiento.getDyctDocumentoDTO().getNumControlDoc(),
                                                  objetoDyctSeguimiento.getResponsable(), objetoDyctSeguimiento.getFecha(),
                                                  objetoDyctSeguimiento.getComentarios() });
        } catch (Exception dae) {
            log.error("ocurrió un error al insertar seguimiento ->" + dae.getMessage());
            ManejadorLogException.getTraceError(dae);
            throw new SIATException(dae);
        }
    }

    /**
     * Obtiene la secuencia para el ID de la declaracion.
     */
    private void obtenerSecuencia() {
        secuencia = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTAR_SECUENCIASEGUIMIENTO.toString(), Integer.class);
    }


}
