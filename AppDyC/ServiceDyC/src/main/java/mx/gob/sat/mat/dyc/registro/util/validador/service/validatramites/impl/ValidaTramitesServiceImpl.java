package mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.ValidaTramitesService;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.dao.tipotramite.DycaValidaTramiteDAO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaValidaTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccValidacionDTO;
import mx.gob.sat.siat.dyc.domain.tramites.TramitesValidacionDTO;
import mx.gob.sat.siat.dyc.registro.util.validador.ValidaTramitesDAO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Jesús E. Flores Garcia
 * @date 24/02/14
 */
@Service(value = "validaTramitesService")
public class ValidaTramitesServiceImpl implements ValidaTramitesService {

    private static final Logger LOG = Logger.getLogger(ValidaTramitesServiceImpl.class);

    private static final int ID_DECLARACIONES_TIPO_1 = 1;
    private static final int ID_DECLARACIONES_TIPO_4 = 4;
    private static final int ID_NUMERO_DE_DOCUMENTO = 33;
    private static final int ID_IMPORTE_DEVOLUCIONES_COMP = 8;
    private static final int ID_CONSULTAR_DIOT = 29;
    private static final int ID_SALDO_A_FAVOR = 35;
    private static final int ID_CANTIDAD_A_FAVOR = 36;
    private static final int ID_IMPORTE_A_RECUPERAR = 40;
    private static final int ID_IMPORTE_RETENIDO_RECAUDADO = 41;
    private static final String LABEL_IMPORTE_A_RECUPERAR = "Importe a recuperar";
    private static final String LABEL_IMPORTE_PAGO_INDEBIDO = "Importe del pago indebido";
    private static final String LABEL_IMPORTE_RETENIDO_RECAUDADO = "Importe retenido y recaudado";
    private static final String LABEL_IMPORTE_SALDO_A_FAVOR = "Importe saldo a favor";

    private static final Integer[] ROLES_BASICOS =
        new Integer[] { (int)ConstantesTipoRol.PERSONA_MORAL, (int)ConstantesTipoRol.ROL_PERSONA_MORAL,
                        (int)ConstantesTipoRol.PERSONA_FISICA, (int)ConstantesTipoRol.ROL_PERSONA_FISICA };

    @Autowired
    private ValidaTramitesDAO validacionTramitesDAO;

    @Autowired
    private DycaValidaTramiteDAO daoValidaTramite;

    @Override
    @Transactional(readOnly = true)
    public ParamOutputTO<Integer> getTramitesXValidacion(Integer idValidacion) throws SIATException {
        return new ParamOutputTO<Integer>(validacionTramitesDAO.tramitesPorDeclaracion(idValidacion));
    }


    @Override
    @Transactional(readOnly = true)
    public ParamOutputTO<TramitesValidacionDTO> getTramitesValidacion(int tramite) throws SIATException {
        TramitesValidacionDTO tramitesVal = new TramitesValidacionDTO();
        tramitesVal.setTramUltimaDelclaracion(validacionTramitesDAO.tramitesPorDeclaracion(ID_DECLARACIONES_TIPO_1).contains(tramite));
        tramitesVal.setTramConsultaSaldoICEPSP(validacionTramitesDAO.tramitesPorDeclaracion(ID_DECLARACIONES_TIPO_4).contains(tramite));
        tramitesVal.setTramitesNumDocumento(validacionTramitesDAO.tramitesPorDeclaracion(ID_NUMERO_DE_DOCUMENTO).contains(tramite));
        tramitesVal.setTramitesRNFDC19(validacionTramitesDAO.tramitesPorDeclaracion(ID_IMPORTE_DEVOLUCIONES_COMP).contains(tramite));
        tramitesVal.setTramitesDIOT(validacionTramitesDAO.tramitesPorDeclaracion(ID_CONSULTAR_DIOT).contains(tramite));
        tramitesVal.setPresentacionSaldo(getPresentacionSaldo(tramite));
        return new ParamOutputTO<TramitesValidacionDTO>(tramitesVal);
    }

    @Override
    public boolean validaTramite(Integer idValidacion, Integer tramite) {
        LOG.debug("idValidacion ->" + idValidacion + "; tramite ->" + tramite);
        DyccValidacionDTO validacion = new DyccValidacionDTO();
        validacion.setIdValidacion(idValidacion);
        List<DycaValidaTramiteDTO> relsValTram = daoValidaTramite.selecXValidacion(validacion);
        for (DycaValidaTramiteDTO valTramite : relsValTram) {
            DyccTipoTramiteDTO tipoTramite = valTramite.getDyccTipoTramiteDTO();
            if (tipoTramite.getIdTipoTramite() == tramite.intValue()) {
                return Boolean.TRUE;
            }
        }

        return false;
    }

    private String getPresentacionSaldo(int tramite) throws SIATException {
        String tipoSaldo = null;
        if (validacionTramitesDAO.tramitesPorDeclaracion(ID_SALDO_A_FAVOR).contains(tramite)) {
            tipoSaldo = LABEL_IMPORTE_SALDO_A_FAVOR;
        } else if (validacionTramitesDAO.tramitesPorDeclaracion(ID_CANTIDAD_A_FAVOR).contains(tramite)) {
            tipoSaldo = LABEL_IMPORTE_PAGO_INDEBIDO;
        } else if (validacionTramitesDAO.tramitesPorDeclaracion(ID_IMPORTE_A_RECUPERAR).contains(tramite)) {
            tipoSaldo = LABEL_IMPORTE_A_RECUPERAR;
        } else if (validacionTramitesDAO.tramitesPorDeclaracion(ID_IMPORTE_RETENIDO_RECAUDADO).contains(tramite)) {
            tipoSaldo = LABEL_IMPORTE_RETENIDO_RECAUDADO;
        } else {
            tipoSaldo = "NO_PRESENTA_SALDO";
        }
        return tipoSaldo;
    }

    @Override
    public Map<String, Object> validarRolesContribuyente(List<PersonaRolDTO> roles) {
        LOG.debug("INICIO validarRolesContribuyente");
        Map<String, Object> resp = new HashMap<String, Object>();
        boolean success = false;
        List<String> listInconsist = new ArrayList<String>();
        for (PersonaRolDTO rol : roles) {
            LOG.debug("ROL: " + rol.getClaveRol() + " descripcionRol:" + rol.getDescripcionRol() + " fechaAltaRol:" +
                      rol.getFechaAltaRol() + " fechaBajaRol:" + rol.getFechaBajaRol());

            if (Arrays.asList(ROLES_BASICOS).contains(rol.getClaveRol())) {
                LOG.debug("el rol " + rol.getDescripcionRol() + " es un rol basico; se analizará que este vigente");
                String inconsist = encontrarInconsistencias(rol);
                if (inconsist != null) {
                    listInconsist.add(inconsist);
                } else {
                    success = Boolean.TRUE;
                }
            }
        }

        resp.put("success", success);
        resp.put("inconsistencias", listInconsist);
        return resp;
    }

    public String encontrarInconsistencias(PersonaRolDTO rol) {
        if (rol.getFechaAltaRol() != null) {
            if (new Date().before(rol.getFechaAltaRol())) {
                return "El rol '" + rol.getClaveRol() + " - " + rol.getDescripcionRol() +
                    "' aún no esta vigente, lo estará hasta el " + Utils.formatearFechaHora(rol.getFechaAltaRol());
            }
        } else {
            return "No es posible validar la vigencia del rol '" + rol.getClaveRol() + " - " +
                rol.getDescripcionRol() + "' debido a que no se proporciona la fecha de alta";
        }

        if (rol.getFechaBajaRol() != null && new Date().after(rol.getFechaBajaRol())) {
            return "El rol '" + rol.getClaveRol() + " - " + rol.getDescripcionRol() + "' se encuentra vencido";
        }

        return null;
    }
}
