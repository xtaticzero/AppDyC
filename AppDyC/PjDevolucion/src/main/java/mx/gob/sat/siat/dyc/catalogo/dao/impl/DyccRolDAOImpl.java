/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.mantenermatrizhabilitacionanexos.impl.mapper.MantenerMatrizHabilitacionAnexosRolesMapper;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccRolDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper.DyccRolMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
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
 * DAO creado para el DTO DyccRolDTO
 * @author  Alfredo Ramirez
 * @modifiedBy Jesus Alfredo Hernandez Orozco
 * @since   22/08/2013
 *
 */
@Repository(value = "dyccRolDAO")
public class DyccRolDAOImpl implements DyccRolDAO {

    private static final String CONSULTA_X_IDTIPOTRAMITE =
        "select a.IDROL,a.DESCRIPCION,a.ORDENSEC,a.PERMITESELECCION,a.FECHAINICIO,a.FECHAFIN \n" +
        "from Dycc_Rol a\n" +
        "inner join Dycc_TramiteRol b on (a.idRol=b.idRol)\n" +
        "where b.idTipoTramite=?";

    private static final String CONSULTA_X_IDTIPOTRAMITE_FECHAFIN =
        "select a.IDROL,a.DESCRIPCION,a.ORDENSEC,a.PERMITESELECCION,a.FECHAINICIO,a.FECHAFIN \n" +
        "from Dycc_Rol a\n" +
        "inner join Dycc_TramiteRol b on (a.idRol=b.idRol)\n" +
        "where b.idTipoTramite=? and b.fechafin is null";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccRolDAOImpl.class);

    public DyccRolDAOImpl() {
        super();
    }

    @Override
    public List<DyccRolDTO> consultarRoles() {
        List<DyccRolDTO> dyccRol = new ArrayList<DyccRolDTO>();
        try {
            dyccRol =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLES.toString(), new Object[] { },
                                               new DyccRolMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLES);
        }
        return dyccRol;
    }

    /**
     * @author  YO
     * @param idRol
     * @return
     */
    @Override
    public List<DyccRolDTO> consultarRoles(int idRol) {
        List<DyccRolDTO> dyccRol = new ArrayList<DyccRolDTO>();
        try {
            dyccRol =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLES.toString().concat(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLESWHEREID.toString()),
                                               new Object[] { idRol }, new DyccRolMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLES + ConstantesDyC1.TEXTO_3_ERROR_DAO + idRol);
        }
        return dyccRol;
    }


    /**
     * @author YO
     * @param cero
     * @return
     */
    @Override
    public List<DyccRolDTO> consultarRolesCero(boolean cero) {
        List<DyccRolDTO> dyccRol = new ArrayList<DyccRolDTO>();
        try {
            dyccRol =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLES.toString().concat(cero ? SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLESWHERECERO :
                                                                                                              SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLESWHERENOTCERO),
                                               new DyccRolMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLES);
        }
        return dyccRol;
    }


    @Override
    public List<DyccRolDTO> obtieneRolesPorAnexo(int anexo) {
        List<DyccRolDTO> rol = new ArrayList<DyccRolDTO>();
        try {
            rol =
 this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLESPORANEXO.toString(), new Object[] { anexo },
                            new MantenerMatrizHabilitacionAnexosRolesMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEROLESPORANEXO + ConstantesDyC1.TEXTO_3_ERROR_DAO + anexo);
        }
        return rol;
    }

    /**
     * Se utiliza para consultar los roles que estan asociados a un tipo de tramite (consulta que se utiliza para dar
     * mantenimiento a los catalogos).
     *
     * @param idTipoTramite identificador del tipo de tramite utilizado para hacer la busqueda.
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccRolDTO> consultarXIdTipoTramite(Integer idTipoTramite) throws SIATException {
        List<DyccRolDTO> roles = null;
        try {
            roles =
this.jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE, new Object[] { idTipoTramite }, new MantenerMatrizHabilitacionAnexosRolesMapper());

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoTramite);
            throw new SIATException(e);
        }
        return roles;
    }

    /**
     * Se utiliza para consultar los roles que estan asociados a un tipo de tramite tomando en cuenta la fecha fin
     * igual a null(consulta que se utiliza para dar mantenimiento a los catalogos en la pantalla de modificacion de
     * tipos de tramite).
     *
     * @param idTipoTramite identificador del tipo de tramite utilizado para hacer la busqueda.
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccRolDTO> consultarXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException {
        List<DyccRolDTO> roles = null;
        try {
            roles =
this.jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE_FECHAFIN, new Object[] { idTipoTramite }, new MantenerMatrizHabilitacionAnexosRolesMapper());

        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOTRAMITE_FECHAFIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idTipoTramite:" +
                      idTipoTramite);
            throw new SIATException(e);
        }
        return roles;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
