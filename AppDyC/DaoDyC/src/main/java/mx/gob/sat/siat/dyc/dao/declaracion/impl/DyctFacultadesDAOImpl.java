package mx.gob.sat.siat.dyc.dao.declaracion.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.declaracion.DyctFacultadesDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper.DyctFacultadesMapper;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyctFacultadesDAO")
public class DyctFacultadesDAOImpl implements DyctFacultadesDAO {

    private Logger log = Logger.getLogger(DyctFacultadesDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public List<DyctFacultadesDTO> buscaDocumentosFacultades(Integer numEmpleadoAprob) {
        List<DyctFacultadesDTO> lista = null;
        String sql = "SELECT * FROM DYCT_FACULTADES WHERE NUMEMPLEADOAPROB = ? AND FECHAFIN IS NULL";
        try {
            lista = this.jdbcTemplateDYC.query(sql, new Object[] { numEmpleadoAprob }, new DyctFacultadesMapper());

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + numEmpleadoAprob);
        }
        return lista;
    }

    @Override
    public void actualizarNumEmpleadoAprob(Integer numEmpleadoAprob, Integer idFacultades) throws SIATException {
        String sql = "UPDATE DYCT_FACULTADES SET NUMEMPLEADOAPROB = ? WHERE IDFACULTADES = ?";
        try {
            this.jdbcTemplateDYC.update(sql, new Object[] { numEmpleadoAprob, idFacultades });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + numEmpleadoAprob + "y" + idFacultades);
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyctFacultadesDTO> buscarFacultadesXNumControl(String numControl) {
        try {

            List<DyctFacultadesDTO> lDyctFacultadesDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARFACULTADES_BUSCARTRAMITEPORNUMERODECONTROL.toString(),
                                      new Object[] { numControl }, new DyctFacultadesMapper());
            return lDyctFacultadesDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARFACULTADES_BUSCARTRAMITEPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

    }
    
    @Override
    public List<DyctFacultadesDTO> consultarDocumentoAprobador(Integer numEmpleado) {
       try {

           List<DyctFacultadesDTO> lDyctFacultadesDTO =
               jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_DOCUMENTO_APROBADORES_ART22.toString(),
                                     new Object[] { numEmpleado }, new DyctFacultadesMapper());
           return lDyctFacultadesDTO;

       } catch (DataAccessException dae) {
           log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTARFACULTADES_BUSCARTRAMITEPORNUMERODECONTROL +
                     ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numEmpleado);
           throw dae;
       }

    }
}
