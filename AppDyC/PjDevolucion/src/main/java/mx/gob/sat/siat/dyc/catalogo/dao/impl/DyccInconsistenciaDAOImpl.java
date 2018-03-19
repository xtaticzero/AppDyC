/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.catalogo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccInconsistenciaDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DycaInconsistTempMapper;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DycaSolInconsistMapper;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccInconsistenciaMapper;
import mx.gob.sat.siat.dyc.dao.devolucionaut.impl.mapper.DyctDictAutomaticaMapper;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaInconsistTempDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;

import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Implementaci&oacute;n DAO para consulta de inconsistencias
 *
 * */
@Repository(value = "dyccInconsistenciaDAO")
public class DyccInconsistenciaDAOImpl implements DyccInconsistenciaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccInconsistenciaDAOImpl.class);

    public DyccInconsistenciaDAOImpl() {
        super();
    }

    @Override
    public DyccInconsistDTO buscarInconsistencia(int idInconsistencia) {
        try {
            DyccInconsistDTO dyccInconsistenciaDTO =
                jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTARCATALOGO_MOSTRARINCONSISTENCIAS,
                                               new Object[] { idInconsistencia }, new DyccInconsistenciaMapper());
            return dyccInconsistenciaDTO;
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGO_MOSTRARINCONSISTENCIAS + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      idInconsistencia);
            throw dae;
        }
    }

    @Override
    public List<DycaSolInconsistDTO> buscarSolicitudDev(String numControl) {

        try {

            List<DycaSolInconsistDTO> lDycaSolInconsistDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARINCONSISTENCIASPORNUMERODECONTROL.toString(),
                                      new Object[] { numControl }, new DycaSolInconsistMapper());
            return lDycaSolInconsistDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARINCONSISTENCIASPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

    }

    @Override
    public List<DyctDictAutomaticaDTO> buscarMotivosRiesgo(String numControl) {

        try {
            List<DyctDictAutomaticaDTO> lDycMotivosRiesgosDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGO_MOTIVOSRIESGO.toString(),
                                      new Object[] { numControl }, new DyctDictAutomaticaMapper());
            return lDycMotivosRiesgosDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGO_MOTIVOSRIESGO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

    }

    @Override
    public void insertarInconsistencia(DycaSolInconsistDTO dycaSolInconsistDTO) {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAINCONSISTENCIAS.toString(),
                                   new Object[] { dycaSolInconsistDTO.getDyccInconsistDTO().getIdInconsistencia(),
                                                  dycaSolInconsistDTO.getDycpSolicitudDTO().getNumControl(),
                                                  dycaSolInconsistDTO.getDescripcion() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAINCONSISTENCIAS.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO );
            throw dae;
        }
    }

    @Override
    public List<DycaSolInconsistDTO> findInconsistenciasTemp(int folio) {
        List<DycaSolInconsistDTO> lDyccInconsistenciaDTO = null;
        try {
            lDyccInconsistenciaDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.FIND_INCOSISTENCIAS_TEMP.toString(), new Object[] { folio },
                                          new DycaInconsistTempMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.FIND_INCOSISTENCIAS_TEMP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de FOLIO " +
                      folio);
            throw dae;
        }

        if (!lDyccInconsistenciaDTO.isEmpty()) {
            return lDyccInconsistenciaDTO;
        }

        return new ArrayList<DycaSolInconsistDTO>();

    }

    @Override
    public void createInconsitenciasTemp(DycaInconsistTempDTO inputSolTemp) {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATE_INCONSISTENCAS_TEMP.toString(),
                                   new Object[] { inputSolTemp.getDyccInconsistDTO().getIdInconsistencia(),
                                                  inputSolTemp.getDescripcion(),
                                                  inputSolTemp.getDyctServicioTempDTO().getFolioServTemp() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      "createInconsitenciasTemp");
            throw dae;
        }

    }
}


