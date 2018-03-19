/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao.util.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.util.DyctDevIvaDAO;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DyctDevIvaMapper;
import mx.gob.sat.siat.dyc.domain.DyctDevAutoIvaDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author GAER8674
 */
@Repository
public class DyctDevIvaDAOImpl implements DyctDevIvaDAO{
    private static final Logger LOGGER = Logger.getLogger(DyctDevIvaDAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    public DyctDevIvaDAOImpl() {}

    
    @Override
    public List<DyctDevAutoIvaDTO> selectXNumeroLote(Long numeroLote) {
        LOGGER.info(SQLOracleDyC.DYCT_DEVIVA_SELECT_X_NUMEROLOTE + ConstantesDyC1.LOG_SEPARADOR_TUPLA_SQL + numeroLote);
        return this.jdbcTemplateDYC.query(SQLOracleDyC.DYCT_DEVIVA_SELECT_X_NUMEROLOTE.toString(), new Object[] { numeroLote }, new DyctDevIvaMapper());
    }

    @Override
    public void actualizaEstado(DyctDevAutoIvaDTO dev, int estado) {
        LOGGER.info(SQLOracleDyC.AUTOMATICAS_ACTUALIZAESTADO + " || " + estado + " || " + dev.getNumeroOperacion());
        jdbcTemplateDYC.update(SQLOracleDyC.AUTOMATICAS_ACTUALIZAESTADO.toString(),
                               new Object[] {estado, dev.getNumeroOperacion()});
    }

    @Override
    public List<DyctDevAutoIvaDTO> selectNuevasDevoluciones() {
        LOGGER.info(SQLOracleDyC.AUTOMATICAS_SELECCIONANUEVAS);
        return this.jdbcTemplateDYC.query(SQLOracleDyC.AUTOMATICAS_SELECCIONANUEVAS.toString(), new Object[] { }, new DyctDevIvaMapper());
    }

}

/**
     public void actualizarDocumentoAprobacion(Integer numEmpleadoAprob, Long idDocumento) throws SIATException{

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTO,
                                   new Object[] { numEmpleadoAprob, idDocumento });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " numEmpleadoAprob " + numEmpleadoAprob + " idDocumento " +
                      idDocumento);
            throw new SIATException(dae);
        }
 */