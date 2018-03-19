/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.declaracion.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.declaracion.DyctNotasExpDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper.DyctNotasExpMapper;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctNotaDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 07, 2013
 *
 * */
@Repository(value = "dyctNotasExpDAO")
public class DyctNotasExpDAOImpl implements DyctNotasExpDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;


    public DyctNotasExpDAOImpl() {
        super();
    }

    private Logger log = Logger.getLogger(DyctNotasExpDAOImpl.class.getName());

    @Override
    public void insertarNota(DyctNotaDTO dyctNotaDTO) throws SIATException{

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARNOTA.toString(),
                                   new Object[] { dyctNotaDTO.getTexto(), dyctNotaDTO.getFecha(),
                                                  dyctNotaDTO.getUsuario(),
                                                  dyctNotaDTO.getDycpServicioDTO().getNumControl() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARNOTA + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctNotaDTO));
            throw new SIATException(dae);
        }

    }

    @Override
    public List<DyctNotaDTO> buscarNotasXNumControl(String numControl) {

        try {

            List<DyctNotaDTO> lDyctNotaDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARNOTASPORNUMERODECONTROL,
                                      new Object[] { numControl }, new DyctNotasExpMapper());
            return lDyctNotaDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARNOTASPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

}

