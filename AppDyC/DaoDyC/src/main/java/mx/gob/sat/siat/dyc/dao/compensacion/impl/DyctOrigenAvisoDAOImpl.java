/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.compensacion.impl;

import mx.gob.sat.siat.dyc.dao.compensacion.DyctOrigenAvisoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOrigenAvisoDTO;
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
 *
 * DAO creado para el DTO DyctOrigenAvisoDTO
 * @author  Alfredo Ramirez
 * @since   07/07/2014
 *
 */
@Repository(value = "dyctOrigenAvisoDAO")
public class DyctOrigenAvisoDAOImpl implements DyctOrigenAvisoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctOrigenAvisoDAOImpl.class);

    public DyctOrigenAvisoDAOImpl() {
        super();
    }

    @Override
    public void insertar(String numControl, DyctOrigenAvisoDTO dyctOrigenAvisoDTO) throws SIATException {
        try {
            /**
            TIPOSALDO                      NOT NULL VARCHAR2(3)
            NUMCONTROLREM                  NOT NULL VARCHAR2(15)*/
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_ORIGENAVISO.toString(),
                                   new Object[] { numControl, 
                                                  dyctOrigenAvisoDTO.getImpActualizado(),
                                                  dyctOrigenAvisoDTO.getImpRemanente(),
                                                  dyctOrigenAvisoDTO.getDiotNumOperacion(),
                                                  dyctOrigenAvisoDTO.getDiotFechaPresenta(),
                                                  dyctOrigenAvisoDTO.getEspSubOrigen(), 
                                                  dyctOrigenAvisoDTO.getPresentoDiot(),
                                                  dyctOrigenAvisoDTO.getEsRemanente() });
            log.info("Se guardo en dyct_origenaviso : " + numControl);
        } catch (DataAccessException siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAR_ORIGENAVISO + ExtractorUtil.ejecutar(dyctOrigenAvisoDTO));
            throw new SIATException(siatE);
        }
    }
}
