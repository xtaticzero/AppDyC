package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSolRfcControlDTO;
import mx.gob.sat.siat.dyc.registro.dao.solicitud.RegistroConsultaDAO;
import mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper.ControlRFCMapper;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
 * Registro Consulta DAOImpl
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0
 */
@Repository(value = "registroConsultaDAO")
public class RegistroConsultaDAOImpl implements RegistroConsultaDAO {
    private static final Logger LOG = Logger.getLogger(RegistroConsultaDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;


    /**
     *
     * @param numcontrol
     * @return
     */
    @Override
    public List<String> getAllRfcControlador(String numcontrol) throws SIATException {
        try {
            return jdbcTemplateDYC.query(SQLOracleDyC.SQLRFCCONTROLADOR.toString(), new Object[] { numcontrol },
                                         new ControlRFCMapper());
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.SQLRFCCONTROLADOR.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + numcontrol);
            throw new SIATException(e);
        }
    }


    @Override
    public void createControlador(DyctSolRfcControlDTO dyccSolRfcControlDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATE_CONTROLADOR.toString(),
                                   new Object[] { dyccSolRfcControlDTO.getRfc().trim(),
                                                  dyccSolRfcControlDTO.getDycpSolicitudDTO().getNumControl() });
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.CREATE_CONTROLADOR.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + dyccSolRfcControlDTO.getRfc());
            throw new SIATException(e);
        }
    }
}
