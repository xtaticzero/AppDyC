/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.banco.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.banco.DyctCuentaBancoDAO;
import mx.gob.sat.siat.dyc.dao.banco.impl.mapper.CuentasClabeMapper;
import mx.gob.sat.siat.dyc.dao.banco.impl.mapper.DyctCuentaBancoMapper;
import mx.gob.sat.siat.dyc.dao.banco.impl.mapper.ReemplazaCuentaMapper;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.EDycDaoCodigoError;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.RemplazaCuentaClabeVO;
import mx.gob.sat.siat.dyc.vo.ReqCuentaClabeVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("dyctCuentaBancoDAOImpl")
public class DyctCuentaBancoDAOImpl implements DyctCuentaBancoDAO {

    private Logger log = Logger.getLogger(DyctCuentaBancoDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public DyctCuentaBancoDAOImpl() {
        super();
    }

    @Override
    public void createCuentaBanco(DyctCuentaBancoDTO input) throws SIATException {
        log.info("INIT CREATE CUENTA BANCO");
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATECUENTABANCO.toString(),
                    new Object[]{input.getClabe(), input.getDycpSolicitudDTO().getNumControl(),
                        input.getDyccInstCreditoDTO().getIdInstCredito(),
                        input.getDyctArchivoDTO().getIdArchivo()});
        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.CREATECUENTABANCO);
            throw e;
        }
        log.info("SUCCESS CREATE CUENTA BANCO");
    }

    @Override
    public void insertar(DyctCuentaBancoDTO input) throws DycDaoExcepcion {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATECUENTABANCO_DI.toString(),
                    new Object[]{input.getClabe(),
                        input.getDycpSolicitudDTO().getNumControl(),
                        input.getDyccInstCreditoDTO().getIdInstCredito(),
                        input.getFechaRegistro(),
                        input.getFechaUltimaMod(),
                        input.getCuentaValida(),
                        input.getDyctArchivoDTO().getIdArchivo()});
        } catch (DataAccessException e) {
            throw new DycDaoExcepcion(EDycDaoCodigoError.BD_DYC_CUENTABANCO_INSERT_GENERAL, null, e);
        }
    }

    @Override
    public List<ReqCuentaClabeVO> getReqCuentasCLABE(String rfc) {
        try {
            return jdbcTemplateDYC.query(SQLOracleDyC.GET_REQ_CUENTAS_CLABE_PENDIENTES.toString(), new Object[]{rfc},
                    new CuentasClabeMapper());
        } catch (DataAccessException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.GET_REQ_CUENTAS_CLABE_PENDIENTES.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc);
            throw e;
        }
    }

    /**
     * Metodo para actualizar cuenta banco su cable, id de institucion y fecha
     * ultima de moficacion DyctCuenta nace noefectuado actualizar cuentavalida
     * --> 1 --> 0 --> 1 DyctResolucion nace enviaTESOFE actualizarCuenta
     * pagoenviado --> 0 --> 1 --> 0 --LADP[Luis ALberto Dominguez Palomino]
     *
     * @param dyctCuentaBancoDTO
     */
    @Override
    public void update(DyctCuentaBancoDTO dyctCuentaBancoDTO) {
        jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_CUENTA_CLABE.toString(),
                new Object[]{dyctCuentaBancoDTO.getClabe(), dyctCuentaBancoDTO.getDyccInstCreditoDTO().getIdInstCredito(),
                    dyctCuentaBancoDTO.getCuentaValida(),
                    dyctCuentaBancoDTO.getDycpSolicitudDTO().getNumControl()});
    }

    /**
     * Metodo para remplazar cuenta banco su cable, id de institucion y fecha
     * ultima de moficacion, idarchivo --LADP[Luis ALberto Dominguez Palomino]
     *
     * @param dyctCuentaBancoDTO
     */
    @Override
    public void remplaza(DyctCuentaBancoDTO dyctCuentaBancoDTO) {
        jdbcTemplateDYC.update(SQLOracleDyC.REEMPLAZAR_CUENTA_CLABE.toString(),
                new Object[]{dyctCuentaBancoDTO.getClabe(), dyctCuentaBancoDTO.getDyccInstCreditoDTO().getIdInstCredito(),
                    dyctCuentaBancoDTO.getCuentaValida(),
                    dyctCuentaBancoDTO.getDycpSolicitudDTO().getNumControl()});
    }

    /**
     * Consulta por numero de control y regresa un objeto Dyct_cuentabanco lleno
     * --LADP[Luis ALberto Dominguez Palomino]
     *
     * @param dyctCuentaBancoDTO
     * @return
     */
    @Override
    public DyctCuentaBancoDTO consultaXNumCtrl(DyctCuentaBancoDTO dyctCuentaBancoDTO) throws SIATException {
        DyctCuentaBancoDTO cuentaBanco = null;
        try {
            cuentaBanco
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CUENTA_CLABE_X_NUMCTRL.toString(), new Object[]{dyctCuentaBancoDTO.getDycpSolicitudDTO().getNumControl()},
                            new DyctCuentaBancoMapper());
        } catch (Exception siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + SQLOracleDyC.CUENTA_CLABE_X_NUMCTRL.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctCuentaBancoDTO));
            throw new SIATException(siatE);
        }
        return cuentaBanco;
    }

    /**
     * Consulta por numero de control y regresa un objeto de
     * RemplazaCuentaClabeVO --LADP[Luis ALberto Dominguez Palomino]
     *
     * @param string
     * @return
     */
    @Override
    public RemplazaCuentaClabeVO consultaNumCtrlTramite(String numControl) throws SIATException {
        RemplazaCuentaClabeVO cuentaClabeVO = null;
        try {
            cuentaClabeVO
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_NUMCTRL_X_TRAMITE.toString(), new Object[]{numControl}, new ReemplazaCuentaMapper());
        } catch (Exception siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + SQLOracleDyC.CONSULTA_NUMCTRL_X_TRAMITE.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
            throw new SIATException(siatE);
        }
        return cuentaClabeVO;
    }

    /**
     * Consulta las cuentas clabe por si el pago de tesofe fue realizado por rfc
     * --LADP[Luis ALberto Dominguez Palomino]
     *
     * @param rfc
     * @return
     */
    @Override
    public List<RemplazaCuentaClabeVO> consultaCuentaClabeXPagoTesofe(String rfc) {
        return jdbcTemplateDYC.query(SQLOracleDyC.CUENTAS_CLABE_X_PAGOTESOFE.toString(), new Object[]{rfc}, new ReemplazaCuentaMapper());
    }

    /**
     * Actualiza el campo de cuentaValida, el cual se utiliza en el proceso de
     * retroalimentacion de la TESOFE. Toma el valor de '1' cuando el abono fue
     * efectuado. Toma el valor de '0' cuando el abono fue NO efectuado.
     *
     * @param numeroControl Numero de control de la solicitud.
     */
    @Override
    public void actualizarCuentaValida(Integer status, String numeroControl) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_CUENTAVALIDA.toString(), new Object[]{status, numeroControl});
        } catch (DataAccessException dae) {
            log.error("actualizarCuentaValida(); parametros: status=" + status + "; numeroControl=" + numeroControl
                    + "; causa:" + dae);
            throw new SIATException(dae);
        }
    }
}
