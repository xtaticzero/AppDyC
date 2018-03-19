package mx.gob.sat.siat.dyc.registro.service.contribuyente.impl;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctContribTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.registro.dao.contribuyente.ContribuyenteDAO;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ContribuyenteService;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("insertaContribuyenteServiceImpl")
@Transactional
public class ContribuyenteServiceImpl implements ContribuyenteService {

    private Logger log = Logger.getLogger(ContribuyenteServiceImpl.class.getName());

    @Autowired
    private ContribuyenteDAO insertaContribuyenteDAO;

    private RolesContribuyenteDTO rolesContribuyente;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createContribuyenteDYCT(String query, DyctContribuyenteDTO input) throws SIATException {
        try {
            insertaContribuyenteDAO.createContribuyenteDYCT(query, input);
        } catch (DataAccessException e) {
            throw new SIATException(e);
        }


        log.debug("end createContribuyenteDYCT".toUpperCase());
    }

    @Override
    public ParamOutputTO<DyctContribTempDTO> findContrinbuyenteTemp(int folio) throws SIATException {
        try {
            return new ParamOutputTO<DyctContribTempDTO>(insertaContribuyenteDAO.findContrinbuyenteTemp(folio));
        } catch (DataAccessException dae) {
            log.error(dae.getMessage());
        }
        return null;
    }

    @Override
    public RolesContribuyenteDTO rolesContribuyente(List<PersonaRolDTO> roles) {
        rolesContribuyente = new RolesContribuyenteDTO();
        for (PersonaRolDTO rol : roles) {

            if (validaFechaBajaRol(rol.getFechaBajaRol())) {
                obtenerRoles(rol.getClaveRol());
            }

        }
        return rolesContribuyente;
    }

    private void obtenerRoles(int rol) {
        if (rol == ConstantesTipoRol.PERSONA_MORAL || rol == ConstantesTipoRol.ROL_PERSONA_MORAL) {
            rolesContribuyente.setPersonaMoral(Boolean.TRUE);
        }
        if (rol == ConstantesTipoRol.PERSONA_FISICA || rol == ConstantesTipoRol.ROL_PERSONA_FISICA) {
            rolesContribuyente.setPersonaFisica(Boolean.TRUE);
        }
        if (rol == ConstantesTipoRol.GRAN_CONTRIBUYENTE_PF) {
            rolesContribuyente.setGranContribuyente(Boolean.TRUE);
        }
        if (rol == ConstantesTipoRol.GRAN_CONTRIBUYENTE_PM) {
            rolesContribuyente.setGranContribuyente(Boolean.TRUE);
        }
        if (rol == ConstantesIds.SOC_MERCAN_CONTROL) {
            rolesContribuyente.setSociedadControladora(Boolean.TRUE);
        }
        isDictaminador(rol);

    }

    private void isDictaminador(int rol) {

        if (rol == ConstantesIds.OBLIGADO_DICTAMEN_PM) {
            rolesContribuyente.setDictaminado(Boolean.TRUE);
        }
        if (rol == ConstantesIds.DICTAMINADO_OPCIONAL_PM) {
            rolesContribuyente.setDictaminado(Boolean.TRUE);
        }
        if (rol == ConstantesIds.CONTRIBUYENTE_DICTAMIN_PF) {
            rolesContribuyente.setDictaminado(Boolean.TRUE);
        }
        if (rol == ConstantesIds.CONTRIBUYENTE_DICTAMIN_PM) {
            rolesContribuyente.setDictaminado(Boolean.TRUE);
        }
        if (rol == ConstantesIds.DICTAMINADO_OPCIONAL_PF) {
            rolesContribuyente.setDictaminado(Boolean.TRUE);
        }
        if (rol == ConstantesIds.OBLIGADO_DICTAMEN_PF) {
            rolesContribuyente.setDictaminado(Boolean.TRUE);
        }
        if (rol == ConstantesTipoRol.ESTADO_EXTRANJERO) {
            rolesContribuyente.setEstadoExtranjero(Boolean.TRUE);
        }
    }

    private boolean validaFechaBajaRol(Date fechaBaja) {
        if (null != fechaBaja) {
            try {
                if (fechaBaja.getTime() >= new Date().getTime()) {
                    return Boolean.TRUE;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return false;
    }

    public void setRolesContribuyente(RolesContribuyenteDTO rolesContribuyente) {
        this.rolesContribuyente = rolesContribuyente;
    }

    public RolesContribuyenteDTO getRolesContribuyente() {
        return rolesContribuyente;
    }
}