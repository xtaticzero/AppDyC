/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.plazostramite.dao.impl;

import java.util.List;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.plazostramite.dao.PlazosTramiteDao;
import mx.gob.sat.siat.plazostramite.dao.mapper.PlazoTramiteMapper;
import mx.gob.sat.siat.plazostramite.vo.PlazoTramiteVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Repository(value = "plazosTramiteDao")
public class PlazosTramiteDaoImpl implements PlazosTramiteDao {

    private final Logger log = Logger.getLogger(PlazosTramiteDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    private static final StringBuilder ADMINISTRARCOMPENSACIONES_ACTUALIZARESTADOTRAMITE
            = new StringBuilder(" UPDATE DYCP_COMPENSACION SET IDESTADOCOMP = 3 WHERE NUMCONTROL = ?");
    private static final StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARESTADOTRAMITE
            = new StringBuilder(" UPDATE DYCP_SOLICITUD SET IDESTADOSOLICITUD = 15 WHERE NUMCONTROL = ?");
    private static final StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARESTADOREQUERIMIENTO
            = new StringBuilder(" UPDATE DYCT_DOCUMENTO SET IDESTADOREQ = 4 WHERE NUMCONTROLDOC = ?");

    private static final StringBuilder BUSCARSOLICITUDES
            = new StringBuilder("  select sol.NUMCONTROL,doc.NUMCONTROLDOC,doc.IDTIPODOCUMENTO,req.FECHANOTIFICACION,req.FECHASOLVENTACION,sol.IDESTADOSOLICITUD from DYCP_SOLICITUD sol inner join DYCT_DOCUMENTO doc on (sol.NUMCONTROL=doc.NUMCONTROL AND doc.IDTIPODOCUMENTO IN (1,2)) \n"
                    + "   inner join DYCT_REQINFO req on (req.NUMCONTROLDOC=doc.NUMCONTROLDOC) where req.FECHASOLVENTACION is null and req.FECHANOTIFICACION is not null   and sol.IDESTADOSOLICITUD   IN(4)  ");
    private static final StringBuilder BUSCARCOMPENSACIONES
            = new StringBuilder(" select com.NUMCONTROL,doc.NUMCONTROLDOC,doc.IDTIPODOCUMENTO,req.FECHANOTIFICACION,req.FECHASOLVENTACION,com.IDESTADOCOMP from DYCP_COMPENSACION      com  inner join DYCT_DOCUMENTO doc on (com.NUMCONTROL=doc.NUMCONTROL AND doc.IDTIPODOCUMENTO IN (1)) \n"
                    + "   inner join DYCT_REQINFO req on (req.NUMCONTROLDOC=doc.NUMCONTROLDOC) where req.FECHASOLVENTACION is  null and req.FECHANOTIFICACION is not null  and com.IDESTADOCOMP in (6)  ");

    @Transactional(value = "transactionManagerPlazoTramite", readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    @Override
    public void actualizarEstadosComp(String numControl, String numControlDoc) {
        String qry = "";
        try {
            actualizarEstadoReq(numControlDoc);
            qry = ADMINISTRARCOMPENSACIONES_ACTUALIZARESTADOTRAMITE.toString();
            jdbcTemplateDYC.update(qry, new Object[]{numControl});

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + qry,dae);
            throw dae;
        }

    }

    @Transactional(value = "transactionManagerPlazoTramite", readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    @Override
    public void actualizarEstados(String numControl, String numControlDoc) {
        String qry = "";
        try {

            actualizarEstadoReq(numControlDoc);
            qry = ADMINISTRARSOLICITUDES_ACTUALIZARESTADOTRAMITE.toString();
            jdbcTemplateDYC.update(qry, new Object[]{numControl});

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + qry,dae);
            throw dae;
        }

    }

    @Override
    public void actualizarEstadoReq(String numControlDoc) {
        String qry = "";
        try {
            qry = ADMINISTRARSOLICITUDES_ACTUALIZARESTADOREQUERIMIENTO.toString();
            jdbcTemplateDYC.update(qry, new Object[]{numControlDoc});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + qry,dae);
            throw dae;
        }
    }

    @Override
    public List<PlazoTramiteVO> buscarTramitesPlazos() {

        try {
            List<PlazoTramiteVO> lSolicitudAdministrarSolVO
                    = jdbcTemplateDYC.query(BUSCARSOLICITUDES.toString(),
                            new PlazoTramiteMapper());
            return lSolicitudAdministrarSolVO;
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO
                    + BUSCARSOLICITUDES.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO,dae);
            throw dae;
        }
    }

    @Override
    public List<PlazoTramiteVO> buscarTramitesCompensacionesPlazos() {

        try {
            List<PlazoTramiteVO> lSolicitudAdministrarComVO
                    = jdbcTemplateDYC.query(BUSCARCOMPENSACIONES.toString(),
                            new PlazoTramiteMapper());
            return lSolicitudAdministrarComVO;
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO
                    + BUSCARCOMPENSACIONES.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO,dae);
            throw dae;
        }
    }
}
