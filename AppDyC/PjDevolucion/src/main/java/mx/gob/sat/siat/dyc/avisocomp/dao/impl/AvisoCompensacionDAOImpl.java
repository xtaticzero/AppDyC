/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.dao.AvisoCompensacionDAO;
import mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper.AvisoCompensacionMapper;
import mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper.AvisoDestinoAcuseMapper;
import mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper.AvisoOrigenAcuseMapper;
import mx.gob.sat.siat.dyc.avisocomp.dao.impl.mapper.DataUploadMapper;
import mx.gob.sat.siat.dyc.avisocomp.vo.DataUploadVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosDestinoAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosOrigenAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.PendienteVO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author  Alfredo Ramirez
 * @since   30/08/2013
 * @author  Yolanda Martínez Sánchez
 */
@Repository(value = "avisoCompensacionDAO")
public class AvisoCompensacionDAOImpl implements AvisoCompensacionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(AvisoCompensacionDAOImpl.class);

    @Override
    public List<PendienteVO> obtenerAvisosPendientes(String rfc) {
        List<PendienteVO> pendienteVO = new ArrayList<PendienteVO>();
        try {
            pendienteVO =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_AVISOS_COMP_TEMP.toString(), new Object[] { rfc }, new AvisoCompensacionMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.OBTENER_AVISOS_COMP_TEMP.toString());
        }
        return pendienteVO;
    }

    @Override
    public List<DataUploadVO> getRowsAvisoCompensacion(Integer folioAvisoTemp) {

        List<DataUploadVO> dataUploadVO = new ArrayList<DataUploadVO>();

        try {
            dataUploadVO =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.GET_DATA_BY_REQUIREMENT_AC.toString(), new Object[] { folioAvisoTemp }, new DataUploadMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.GET_DATA_BY_REQUIREMENT_AC.toString());
        }

        return dataUploadVO;
    }

    @Override
    public List<DatosDestinoAcuseVO> obtieneDestinoParaAcuse(String folioAviso) {
        List<DatosDestinoAcuseVO> destinoAcuse = new ArrayList();
        try {
            destinoAcuse =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_ACUSE_DESTINO.toString(), new Object[] { folioAviso }, new AvisoDestinoAcuseMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_ACUSE_DESTINO.toString());
        }
        return destinoAcuse;
    }

    @Override
    public List<DatosOrigenAcuseVO> obtieneOrigenParaAcuse(String folioAviso) {
        List<DatosOrigenAcuseVO> origenAcuse = new ArrayList();
        try {
            origenAcuse =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_ACUSE_ORIGEN.toString(), new Object[] { folioAviso }, new AvisoOrigenAcuseMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_ACUSE_ORIGEN.toString());
        }
        return origenAcuse;
    }
}
