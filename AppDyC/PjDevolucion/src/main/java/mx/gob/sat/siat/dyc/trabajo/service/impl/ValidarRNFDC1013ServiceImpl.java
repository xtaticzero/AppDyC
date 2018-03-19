package mx.gob.sat.siat.dyc.trabajo.service.impl;

import java.math.BigDecimal;

import mx.gob.sat.siat.dyc.dao.parametros.DyccParamentroAppDAO;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.EnumTipoUnidadAdmvaDyC;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ComentarioService;
import mx.gob.sat.siat.dyc.trabajo.service.ValidarRNFDC1013Service;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesClavesYRoles;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Huetzin C
 * @date Noviembre, 2014
 * */
@Service("serviceValidarRNFDC1013")
public class ValidarRNFDC1013ServiceImpl implements ValidarRNFDC1013Service {
    private static final Logger LOG = Logger.getLogger(ValidarRNFDC1013ServiceImpl.class);


    @Autowired
    private DyccParamentroAppDAO daoParametros;

    @Autowired
    private ComentarioService comentarioSer;

    public Boolean validar(DyccAprobadorDTO aprobador, BigDecimal montoAutorizar,
                           EnumTipoUnidadAdmvaDyC tipoAdministracion) throws SIATException {
        Integer claveNivel;
        if (aprobador.getClaveNivel() == 0 || aprobador.getClaveNivel() == null) {
            LOG.info("el aprobador con NumEmpleado " + aprobador.getNumEmpleado() +
                     " NO tiene asiganda una ClaveNivel se le asignara JEFEDEP por default");
            claveNivel = ConstantesClavesYRoles.JEFEDEP;
        } else {
            claveNivel = aprobador.getClaveNivel();
        }
        LOG.debug("monto a autorizar ->" + montoAutorizar + "<-\n claveNivel ->" + claveNivel +
                  "<-\n tipoAdministracion ->" + tipoAdministracion + "<-");

        DyccParametrosAppDTO parametro = null;
        boolean tipoAdminValida =
            (tipoAdministracion == EnumTipoUnidadAdmvaDyC.LOCAL || tipoAdministracion == EnumTipoUnidadAdmvaDyC.GRANDES_CONTRIBUYENTES ||
             tipoAdministracion == EnumTipoUnidadAdmvaDyC.DEVOLUCIONES);

        if (claveNivel == ConstantesClavesYRoles.JEFEDEP && tipoAdministracion == EnumTipoUnidadAdmvaDyC.LOCAL) {
            parametro = daoParametros.encontrar(ConstantesClavesYRoles.IDPARAM_MONTOLIMITE_JEFEDPTO_AGAFF);
        } else if (claveNivel == ConstantesClavesYRoles.JEFEDEP &&
            tipoAdministracion == EnumTipoUnidadAdmvaDyC.GRANDES_CONTRIBUYENTES) {
            parametro = daoParametros.encontrar(ConstantesClavesYRoles.IDPARAM_MONTOLIMITE_JEFEDPTO_ACGC);
        } else if (claveNivel == ConstantesClavesYRoles.SUBADMIN &&
            tipoAdministracion == EnumTipoUnidadAdmvaDyC.LOCAL) {
            parametro = daoParametros.encontrar(ConstantesClavesYRoles.IDPARAM_MONTOLIMITE_SUBADM_AGAFF);
        } else if (claveNivel == ConstantesClavesYRoles.SUBADMIN &&
            tipoAdministracion == EnumTipoUnidadAdmvaDyC.GRANDES_CONTRIBUYENTES) {
            parametro = daoParametros.encontrar(ConstantesClavesYRoles.IDPARAM_MONTOLIMITE_SUBADM_ACGC);
        } else if (claveNivel == ConstantesClavesYRoles.ADMIN &&
            (tipoAdministracion == EnumTipoUnidadAdmvaDyC.GRANDES_CONTRIBUYENTES ||
                    tipoAdministracion == EnumTipoUnidadAdmvaDyC.LOCAL)) {
            parametro = daoParametros.encontrar(ConstantesClavesYRoles.IDPARAM_MONTOLIMITE_ADMAREA_ACGC);
        } else if ((claveNivel == ConstantesClavesYRoles.ADMINC || claveNivel == ConstantesClavesYRoles.ADMING) && tipoAdminValida) {
            LOG.debug("el usuario firmado NO tiene restricciones en cuanto el Monto límite a autorizar");
            return Boolean.TRUE;
        } else {
            throw new SIATException("Error al validar monto Límite, NO existe la combinación de parámetros recibida, params recibidos: numEmpleadoAprobador ->" +
                                    aprobador.getNumEmpleado() + "<- importeSolic ->" + montoAutorizar +
                                    "<- tipoAdministracion ->" + tipoAdministracion + "<-");
        }

        return montoAutorizar.compareTo(parametro.getValor()) == -ConstantesDyCNumerico.VALOR_1 ? Boolean.TRUE :
               montoAutorizar.compareTo(parametro.getValor()) == ConstantesDyCNumerico.VALOR_0 ? Boolean.TRUE : false;
    }


    /**
     * @AdrianAguilar
     * Este método consulta la tabla DYCC_PARAMETROSAPP, en base a la claveADM y el niveldel aprob obtiene el monto a aprobar, si este cumple con las reglas permite continuar el flujo,
     * de lo contrario pide escalación
     *
     * */
    public boolean validaMonto(DyccAprobadorDTO datosAprobador, BigDecimal montoAutorizar, Integer claveSir) {

        boolean puedeContinuar = false;

        int esAgaff = 0;

        switch (claveSir) {

        case ConstantesDyCNumerico.VALOR_80:
        case ConstantesDyCNumerico.VALOR_81:
        case ConstantesDyCNumerico.VALOR_82:
        case ConstantesDyCNumerico.VALOR_90:
        case ConstantesDyCNumerico.VALOR_91:
        case ConstantesDyCNumerico.VALOR_97:
            esAgaff = 0;
            break;
        default:
            esAgaff = 1;
            break;
        }

        BigDecimal maximoAdm = new BigDecimal(ConstantesDyC.MONTO_ADM_POR_DEFECTO);
        BigDecimal maximoSubAdm = new BigDecimal(ConstantesDyC.MONTO_SUBADM_POR_DEFECTO);

        try {
            maximoAdm =
                    comentarioSer.consultarMontosLimitesPorNivel(claveSir, ConstantesClavesYRoles.ADMIN,
                        ConstantesDyC.COMPENSACION);
            maximoSubAdm =
                    comentarioSer.consultarMontosLimitesPorNivel(claveSir, ConstantesClavesYRoles.SUBADMIN,
                        ConstantesDyC.COMPENSACION);
        } catch (SIATException e) {
            LOG.error("No se entontráron en base los montos, se utilizarán valores por defecto: " + e);
        }
        if (datosAprobador.getClaveNivel() <= ConstantesClavesYRoles.ADMINC + esAgaff) {
            puedeContinuar = Boolean.TRUE;
        } else if (montoAutorizar.compareTo(maximoAdm) > 0) {
            puedeContinuar = false;
        } else if (montoAutorizar.compareTo(maximoSubAdm) <= 0 &&
                   datosAprobador.getClaveNivel() <= ConstantesClavesYRoles.SUBADMIN +
            esAgaff) {
            puedeContinuar = Boolean.TRUE;
        } else if (montoAutorizar.compareTo(maximoAdm) <= 0 &&
                   datosAprobador.getClaveNivel() <= ConstantesClavesYRoles.ADMIN +
            esAgaff) {
            puedeContinuar = Boolean.TRUE;
        } else {
            puedeContinuar = false;
        }
        return puedeContinuar;
    }

}
