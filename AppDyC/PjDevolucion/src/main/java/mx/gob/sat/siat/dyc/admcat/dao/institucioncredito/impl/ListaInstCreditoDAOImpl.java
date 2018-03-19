/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.institucioncredito.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.institucioncredito.ListaInstCreditoDAO;
import mx.gob.sat.siat.dyc.admcat.dao.institucioncredito.impl.mapper.ListaInstCreditoMapper;
import mx.gob.sat.siat.dyc.admcat.dto.institucioncredito.ListaInstitucionCreditoDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Implementacion DAO Caso de uso mantener instituciones de credito de DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
@Repository(value = "listaInstCreditoDAO")
public class ListaInstCreditoDAOImpl implements ListaInstCreditoDAO {
    private static final Logger LOG = Logger.getLogger(ListaInstCreditoDAOImpl.class);

    @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;

    private List<ListaInstitucionCreditoDTO> instCredito;
    private ListaInstitucionCreditoDTO linstCredito;


    public ListaInstCreditoDAOImpl() {
        super();
        instCredito = new ArrayList<ListaInstitucionCreditoDTO>();
        linstCredito = new ListaInstitucionCreditoDTO();
    }


    @Override
    public List<ListaInstitucionCreditoDTO> listaInstCredito() throws SQLException {
        try {
            this.setInstCredito(jdbcTemplateDYC.query(SQLOracleDyC.INSTITUCIONCREDITO_LISTAR.toString().concat(" ORDER BY IDINSTCREDITO ASC "), new ListaInstCreditoMapper()));
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + SQLOracleDyC.INSTITUCIONCREDITO_LISTAR);
            throw e;
        }
        return this.getInstCredito();
    }

    @Override
    public ListaInstitucionCreditoDTO existeInstCredito(int idInstCredito) throws SQLException {
        try {
            linstCredito =
                    (ListaInstitucionCreditoDTO)jdbcTemplateDYC.queryForObject(SQLOracleDyC.INSTITUCIONCREDITO_LISTAR.toString().concat("  AND IDINSTCREDITO = ? "),
                                                                               new Object[] { idInstCredito },
                                                                               new BeanPropertyRowMapper(ListaInstitucionCreditoDTO.class));
        } catch (DataAccessException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + SQLOracleDyC.INSTITUCIONCREDITO_LISTAR);
            throw e;
        }
        return linstCredito;
    }

    // TODO: ACCESSORS **********************************************

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setInstCredito(List<ListaInstitucionCreditoDTO> instCredito) {
        this.instCredito = instCredito;
    }

    public List<ListaInstitucionCreditoDTO> getInstCredito() {
        return instCredito;
    }

    public void setLinstCredito(ListaInstitucionCreditoDTO linstCredito) {
        this.linstCredito = linstCredito;
    }

    public ListaInstitucionCreditoDTO getLinstCredito() {
        return linstCredito;
    }
}
