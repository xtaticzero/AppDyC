/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.tipotramite.impl;


import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.tipotramite.DyccPlazoLegalTipoDAO;
import mx.gob.sat.siat.dyc.admcat.dao.tipotramite.impl.mapper.DyccPlazoLegalTipoMapper;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.DyccPlazoLegalTipoDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Clase Implementacion DAO para la tabla [DYCC_PLAZOLEGAL]
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 19, 2013
 * @since 0.1
 *
 * */
@Repository(value = "dyccPlazoLegalTipoDAO")
public class DyccPlazoLegalTipoDAOImpl implements DyccPlazoLegalTipoDAO{
    private static final Logger LOG = Logger.getLogger(DyccPlazoLegalTipoDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private List<DyccPlazoLegalTipoDTO> listaPlazo;

    public DyccPlazoLegalTipoDAOImpl() {
        super();
        listaPlazo = new ArrayList<DyccPlazoLegalTipoDTO>();
    }

    @Override
    public List<DyccPlazoLegalTipoDTO> obtenerListaPlazo() throws SQLException {
        try {
            this.setListaPlazo(jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEPLAZOLEGALTIPO.toString(),
                                                     new DyccPlazoLegalTipoMapper()));
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEPLAZOLEGALTIPO);
        }
        return this.getListaPlazo();
    }

    // TODO: ACCESSOR'S  ***********************************************************

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setListaPlazo(List<DyccPlazoLegalTipoDTO> listaPlazo) {
        this.listaPlazo = listaPlazo;
    }

    public List<DyccPlazoLegalTipoDTO> getListaPlazo() {
        return listaPlazo;
    }
}
