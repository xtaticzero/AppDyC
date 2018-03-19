package mx.gob.sat.siat.dyc.admcat.service.casosverificar.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.service.casosverificar.CasosAVerificarService;
import mx.gob.sat.siat.dyc.dao.motivo.DyccMotivoRfcDAO;
import mx.gob.sat.siat.dyc.dao.rfc.DycbEstadoRfcDAO;
import mx.gob.sat.siat.dyc.dao.rfc.DycpRfcDAO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DycbEstadoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DyccMotivoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "casosAVerificarService")
public class CasosAVerificarServiceImpl implements CasosAVerificarService {

    private Logger log = Logger.getLogger(CasosAVerificarServiceImpl.class);

    @Autowired
    private DycpRfcDAO dycpRfcDAO;

    @Autowired
    private DycbEstadoRfcDAO dycbEstadoRfcDAO;

    @Autowired
    private DyccMotivoRfcDAO dyccMotivoRfcDAO;

    @Autowired
    private PersonaIDCService personaIDCService;

    @Override
    public List<DycbEstadoRfcDTO> mostrarRfcNoConfiables(String rfc, Integer activo, Integer inactivo, Integer padron) {
        List<DycbEstadoRfcDTO> listaCompleta = new ArrayList<DycbEstadoRfcDTO>();
        List<DycpRfcDTO> listaNoConfiable = dycpRfcDAO.obtenerRfcNoConfiables(rfc, activo, inactivo, padron);

        for (DycpRfcDTO personas : listaNoConfiable) {
            List<DycbEstadoRfcDTO> ultimoRegistro =
                dycbEstadoRfcDAO.buscaRegistros(personas.getRfc(), ConstantesDyCNumerico.VALOR_3,
                                                ConstantesDyCNumerico.VALOR_4);
            List<DyccMotivoRfcDTO> listaMotivos = mostrarMotivos(ultimoRegistro.get(0).getDyccTipoAccionRfcDTO().getIdTipoAccionRfc());
            for (int i = 0; i < listaMotivos.size(); i++) {
                if (listaMotivos.get(i).getIdMotivoRfc().equals(ultimoRegistro.get(0).getDyccMotivoRfcDTO().getIdMotivoRfc())) {
                    ultimoRegistro.get(0).setDyccMotivoRfcDTO(listaMotivos.get(i));
                    ultimoRegistro.get(0).setDycpRfcDTO(personas);
                    listaCompleta.add(ultimoRegistro.get(0));
                    break;
                }
            }
        }
        return listaCompleta;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = SIATException.class)
    public boolean insertarRfcNoConfiable(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException {

        try {
            DycpRfcDTO encontrar = dycpRfcDAO.encontrar(dycbEstadoRfcDTO.getDycpRfcDTO().getRfc());
            if (encontrar != null && encontrar.getPadronNoConfiable() == ConstantesDyCNumerico.VALOR_1){
                return Boolean.FALSE;
                }
            else if (encontrar != null && encontrar.getPadronNoConfiable() == ConstantesDyCNumerico.VALOR_0) {
                dycpRfcDAO.actualizarNoConfiable(dycbEstadoRfcDTO.getDycpRfcDTO());
                dycbEstadoRfcDAO.insertar(dycbEstadoRfcDTO);
            } else{
                dycpRfcDAO.insertar(dycbEstadoRfcDTO.getDycpRfcDTO());
                dycbEstadoRfcDAO.insertar(dycbEstadoRfcDTO);
            }
            return Boolean.TRUE;
        } catch (SIATException e) {
            log.info("Se presento un error al insertar la informacion ");
            throw new SIATException(e);
        }

    }

    @Override
    public List<DyccMotivoRfcDTO> mostrarMotivos(Integer tipoAccion) {
        return dyccMotivoRfcDAO.obtenerMotivosPorTipoAccion(tipoAccion);
    }

    @Override
    public String buscaRfcEnAMpliado(String rfc) {
        String razonSocial = null;
        try {
            PersonaDTO contribuyente = new PersonaDTO();
            contribuyente.setRfc(rfc);
            contribuyente = personaIDCService.buscaPersona(contribuyente);

            if (contribuyente != null) {
                contribuyente.setPersonaIdentificacion(personaIDCService.buscaPersonaBoId(contribuyente));
                if (contribuyente.getPersonaIdentificacion().getTipoPersona().equals("F")) {
                    razonSocial =
                            contribuyente.getPersonaIdentificacion().getApPaterno() + " " + contribuyente.getPersonaIdentificacion().getApMaterno() +
                            " " + contribuyente.getPersonaIdentificacion().getNombre();
                } else {
                    razonSocial = contribuyente.getPersonaIdentificacion().getRazonSocial();
                }
            }
        } catch (SIATException e) {
            log.info("Ocurrio un error" + e.getMessage());
        }
        return razonSocial;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = SIATException.class)
    public void modificarRfcNoConfiable(DycbEstadoRfcDTO dycbEstadoRfcDTO) throws SIATException {
        try {
            dycpRfcDAO.actualizarNoConfiable(dycbEstadoRfcDTO.getDycpRfcDTO());
            dycbEstadoRfcDAO.insertar(dycbEstadoRfcDTO);
        } catch (SIATException e) {
            log.info("Ocurrio un error" + e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    public List<DycbEstadoRfcDTO> bitacoraXRFC(String rfc) {
        return dycbEstadoRfcDAO.buscaRegistros(rfc, ConstantesDyCNumerico.VALOR_3, ConstantesDyCNumerico.VALOR_4);
    }

    @Override
    public DycpRfcDTO encontrarRfc(String rfc) {
        return dycpRfcDAO.encontrar(rfc);
    }

    public void setDycpRfcDAO(DycpRfcDAO dycpRfcDAO) {
        this.dycpRfcDAO = dycpRfcDAO;
    }

    public DycpRfcDAO getDycpRfcDAO() {
        return dycpRfcDAO;
    }

    public void setDycbEstadoRfcDAO(DycbEstadoRfcDAO dycbEstadoRfcDAO) {
        this.dycbEstadoRfcDAO = dycbEstadoRfcDAO;
    }

    public DycbEstadoRfcDAO getDycbEstadoRfcDAO() {
        return dycbEstadoRfcDAO;
    }

    public void setDyccMotivoRfcDAO(DyccMotivoRfcDAO dyccMotivoRfcDAO) {
        this.dyccMotivoRfcDAO = dyccMotivoRfcDAO;
    }

    public DyccMotivoRfcDAO getDyccMotivoRfcDAO() {
        return dyccMotivoRfcDAO;
    }

    public void setPersonaIDCService(PersonaIDCService personaIDCService) {
        this.personaIDCService = personaIDCService;
    }

    public PersonaIDCService getPersonaIDCService() {
        return personaIDCService;
    }


}
