/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao.impl;


import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccTipoAvisoDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccTipoAvisoMapper;
import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * DAO creado para el DTO DyccTipoAvisoDTO
 * @author  Alfredo Ramirez
 * @since   21/11/2013
 *
 */
@Repository(value = "dyccTipoAvisoDAO")
public class DyccTipoAvisoDAOImpl implements DyccTipoAvisoDAO{

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccTipoAvisoDAOImpl.class);

    public DyccTipoAvisoDAOImpl() {
        super();
    }

    @Override
    public List<DyccTipoAvisoDTO> obtenerTiposDeAviso() {
        List<DyccTipoAvisoDTO> dyccTipoAviso = null;
        try {
            dyccTipoAviso =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.SQL_OBTENER_TIPOS_AVISOS.toString(), new Object[] { }, new DyccTipoAvisoMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.SQL_OBTENER_TIPOS_AVISOS.toString());
            throw dae;
        }
        return dyccTipoAviso;
    }

}
