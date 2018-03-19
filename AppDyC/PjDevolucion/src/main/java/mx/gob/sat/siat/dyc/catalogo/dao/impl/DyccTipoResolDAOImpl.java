/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.catalogo.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccTipoResolDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccTipoResolMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Federico Chopin Gachuz
 * @date Diciembre 11, 2013
 *
 * */
@Repository(value = "dyccTipoResolDAO")
public class DyccTipoResolDAOImpl implements DyccTipoResolDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccTipoResolDAOImpl.class.getName());

    public DyccTipoResolDAOImpl() {
        super();
    }

    @Override
    public List<DyccTipoResolDTO> buscarTiposResolucion() {

        try {

            List<DyccTipoResolDTO> lDyccTipoResolDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.EMITIRRESOLUCION_BUSCARTIPOSRESOLUCION.toString(), new DyccTipoResolMapper());
            return lDyccTipoResolDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_BUSCARTIPOSRESOLUCION.toString());
            throw dae;
        }
    }
    
    @Override
    public List<DyccTipoResolDTO> buscarTiposResolucionPreautorizada() {

        try {

            List<DyccTipoResolDTO> lDyccTipoResolDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.EMITIRRESOLUCION_BUSCARTIPOSRESOLUCIONPREAUTORIZADA.toString(), new DyccTipoResolMapper());
            return lDyccTipoResolDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.EMITIRRESOLUCION_BUSCARTIPOSRESOLUCIONPREAUTORIZADA.toString());
            throw dae;
        }
    }
    
    
    public List<DyccTipoResolDTO> buscarTiposResolucion(int idTipoServicio){
        try {

            List<DyccTipoResolDTO> lDyccTipoResolDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.BUSCARTIPOSRESOLUCION_POR_TIPOSERVICIO.toString(), new Object[] { idTipoServicio },new DyccTipoResolMapper());
            return lDyccTipoResolDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.BUSCARTIPOSRESOLUCION_POR_TIPOSERVICIO.toString());
            throw dae;
        }
    }
}
