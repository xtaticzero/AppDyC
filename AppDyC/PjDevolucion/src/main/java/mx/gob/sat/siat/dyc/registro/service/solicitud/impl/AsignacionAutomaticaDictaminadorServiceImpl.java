/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
package mx.gob.sat.siat.dyc.registro.service.solicitud.impl;

import java.sql.SQLException;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import mx.gob.sat.siat.dyc.dao.catalogo.DyccMontoDAO;
import mx.gob.sat.siat.dyc.dao.asignartramite.DycaConfigDictaminadorDAO;
import mx.gob.sat.siat.dyc.dao.asignartramite.DycaTipoServicioAsignarDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccCalDictaMinDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaConfigDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaTipoServicioAsignarDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.generico.util.competencia.CompetenciaUtils;
import mx.gob.sat.siat.dyc.registro.bean.ReglaRnfdcVO;
import mx.gob.sat.siat.dyc.registro.dao.solicitud.ReglaRnfdcDAO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.AsignacionAutomaticaDictaminadorService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesAsignarAuDic;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author J. Isaac Carbajal Bernal
 * @Since v0.2 10/02/2014
 */
@Service(value = "asignacionAutomaticaDictaminadorService")
public class AsignacionAutomaticaDictaminadorServiceImpl implements AsignacionAutomaticaDictaminadorService {

    private Logger log = Logger.getLogger(AsignacionAutomaticaDictaminadorServiceImpl.class);

    @Autowired
    private DyccDictaminadorDAO daoDictaminador;

    @Autowired
    private DyccCalDictaMinDAO daoCalDictaMin;

    @Autowired
    private IDycpServicioDAO daoServicio;

    @Autowired
    @Qualifier(value = "iDycpCompensacionDAO")
    private IDycpCompensacionDAO dycpCompensacionDAO;

    @Autowired
    private ReglaRnfdcDAO reglaRnfdcDAO;

    @Autowired(required = true)
    private AdmcUnidadAdmvaDAO daoUnidadAdm;

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;
    
    @Autowired
    private DycaTipoServicioAsignarDAO daoTipoServicioAsignar;
    
    @Autowired
    private DycaConfigDictaminadorDAO daoConfigDictaminador;
    
    @Autowired
    private DyccMontoDAO daoMonto;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int asignarDictaminador(Object tramiteDTO, int tipoServicio) throws SQLException {
        int returnVal = ConstantesDyCNumerico.VALOR_0;
        switch (tipoServicio) {

            case (ConstantesAsignarAuDic.SERVICIO_SOLICITUD_DEVOLUCION):
                DycpSolicitudDTO solicitudDev = (DycpSolicitudDTO) tramiteDTO;
                returnVal = asignarSolicitudDevolicion(solicitudDev);
                break;
            case (ConstantesAsignarAuDic.SERVICIO_DEVOLUCION_AUTOMATICA):
                log.debug("Aqui va el cast de devolucion automatica DTO");
                break;
            case (ConstantesAsignarAuDic.SERVICIO_CASO_DE_COMPENSACION):
            case (ConstantesAsignarAuDic.SERVICIO_AVISO_DE_COMPENSACION):
                DycpCompensacionDTO compensacionDTO = (DycpCompensacionDTO) tramiteDTO;
                returnVal = asignaCompensacion(compensacionDTO);
                break;
            default:
                returnVal = -1;
                break;
        }

        return returnVal;
    }

    public int asignarSolicitudDevolicion(DycpSolicitudDTO solicitudDev) throws SQLException {
        String datosRNFD35;
        AdmcUnidadAdmvaDTO cbzUnidad = new AdmcUnidadAdmvaDTO();
        cbzUnidad.setClaveSir(solicitudDev.getDycpServicioDTO().getClaveAdm());
        cbzUnidad = daoUnidadAdm.consultarUnidadAdmvaList(cbzUnidad).get(ConstantesDyCNumerico.VALOR_0);
        Integer competencia = CompetenciaUtils.identificaCompetencia(solicitudDev.getDycpServicioDTO().getClaveAdm());
        switch (competencia) {
            case ConstantesIds.COMPETENCIA_AGGC:
            case ConstantesIds.COMPETENCIA_AGACE:
            case ConstantesIds.COMPETENCIA_AGH:
                return asignacionDictaminadoresRNFDC29(solicitudDev, cbzUnidad);
            default:
                /*AGACE*/
                if (!validaAdmRFContribuyente(solicitudDev.getDycpServicioDTO(), ConstantesAsignarAuDic.SERVICIO_SOLICITUD_DEVOLUCION)) {
                    datosRNFD35 = reglaRnfdcDAO.consultaReglaRNFDC35AGAFF(solicitudDev.getDycpServicioDTO().getRfcContribuyente());
                    if (StringUtils.isNotBlank(datosRNFD35)) {
                        return validaAdmContribuyente(datosRNFD35, solicitudDev, cbzUnidad);
                    } else {
                        return asignacionDictaminadoresRNFDC29(solicitudDev, cbzUnidad);
                    }
                } else {
                    return asignacionDictaminadoresRNFDC29(solicitudDev, cbzUnidad);
                }
        }
    }

    private boolean validaAdmRFContribuyente(DycpServicioDTO servicioDTO, int tipoServicio) {

        boolean haCambiadoAdm = true;

        DycpServicioDTO servicio = daoServicio.obtenerUltimoServicioXContribuyente(servicioDTO.getRfcContribuyente(), tipoServicio);

        if (servicio != null) {
            Integer admActual = servicioDTO.getClaveAdm();
            Integer admUltimoServicio = servicio.getClaveAdm();
            haCambiadoAdm = (admActual.equals(admUltimoServicio)) ? false : true;
        }

        return haCambiadoAdm;
    }

    private Integer asignacionDictaminadoresRNFDC29(DycpSolicitudDTO solicitudDev, AdmcUnidadAdmvaDTO cbzUnidad) {
        int valorRetorna = 0;
        boolean esInasistente;
        boolean esDisponible;
        List<DictaminadorVO> dictaminadores
                = daoDictaminador.consultarDictaminadorPorCargaAleatorio(solicitudDev.getDycpServicioDTO().getClaveAdm(),
                        cbzUnidad.getClaveAgrs());
        dictaminadores=dictAsignadoTramitesDevolucion(dictaminadores,solicitudDev);
        if (dictaminadores.size() == ConstantesDyC2.SIZE_DE_LISTA_VACIA) {
            valorRetorna = ConstantesDyC2.ZERO_VALUE;
            log.error("No encontro dictaminador con clave ADM -->"
                    + solicitudDev.getDycpServicioDTO().getClaveAdm() + " Clave AGR--> " + cbzUnidad.getClaveAgrs());
            return ConstantesDyC2.ZERO_VALUE;
        } else {
            Iterator it = dictaminadores.iterator();
            while (it.hasNext()) {
                DictaminadorVO dyccDictaminadorAleatorio = dictaminadores.get(0);
                dictaminadores.remove(dyccDictaminadorAleatorio);

                esInasistente
                        = daoCalDictaMin.validarDiaActualA(dyccDictaminadorAleatorio.getNumEmpleado(), solicitudDev.getFechaPresentacion());

                if (esInasistente) {
                    esDisponible
                            = daoCalDictaMin.validarRegresoDictaminadorA(dyccDictaminadorAleatorio.getNumEmpleado(),
                                    solicitudDev.getFechaPresentacion());
                } else {
                    esDisponible
                            = daoCalDictaMin.valida4DiasInhabilesContinuosA(dyccDictaminadorAleatorio.getNumEmpleado(),
                                    solicitudDev.getFechaPresentacion());
                }
                if (esDisponible) {
                    valorRetorna = dyccDictaminadorAleatorio.getNumEmpleado();
                    break;
                }
            }

        }
        return valorRetorna;
    }

    private Integer validaAdmContribuyente(String datosRNFD35, DycpSolicitudDTO solicitudDev,
            AdmcUnidadAdmvaDTO claveAdmActual) {
        String[] campos = datosRNFD35.split("-");
        if (Integer.valueOf(campos[1]).equals(claveAdmActual.getClaveSir())) {
            return Integer.valueOf(campos[0]);

        } else {
            return asignacionDictaminadoresRNFDC29(solicitudDev, claveAdmActual);
        }
    }

    private DyctSaldoIcepDTO creaICEPCompensacion(DycpCompensacionDTO compensacionDTO) throws SIATException {
        DycpCompensacionDTO compesacion = dycpCompensacionDAO.encontrar(compensacionDTO.getDycpServicioDTO().getNumControl());
        return daoSaldoIcep.encontrar(compesacion.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep());
    }
    
    private List<DictaminadorVO> dictAsignadoTramitesDevolucion(List<DictaminadorVO> dictaminadores,DycpSolicitudDTO solicitudDev){
        log.info("Inicia Asignacion de tramites de devolucion");
        List<DictaminadorVO> dictHabilitados=new ArrayList<DictaminadorVO>();
        List<DycaTipoServicioAsignarDTO> listaTramXDictaminador;
        List<DycaConfigDictaminadorDTO> listaConfigDicXServicio;
        Boolean dictAsignado=Boolean.FALSE;
        try{
            for (DictaminadorVO dict : dictaminadores) {
                listaTramXDictaminador=daoTipoServicioAsignar.obtenerTramXDictaminador(dict.getNumEmpleado(),Boolean.TRUE);
                if(listaTramXDictaminador != null){
                    for (DycaTipoServicioAsignarDTO tramXDictaminador : listaTramXDictaminador) {
                        listaConfigDicXServicio=daoConfigDictaminador.obtenerConfigDicXServicio(tramXDictaminador.getIdServicioAsignar(), Boolean.TRUE);
                        if(listaConfigDicXServicio != null){
                            for (DycaConfigDictaminadorDTO configDicXServicio : listaConfigDicXServicio) {
                                if (configDicXServicio.getIdTipoTramite().equals(solicitudDev.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite())
                                        && daoMonto.obtenerMontoXId(tramXDictaminador.getIdMonto()).getMontoMax().compareTo(solicitudDev.getImporteSolicitado()) >= 0) {
                                    dictAsignado = Boolean.TRUE;
                                    dictHabilitados.add(dict);
                                    break;
                                }
                            }
                        }
                        if(dictAsignado){
                            break;
                        }
                    }
                }
                dictAsignado=Boolean.FALSE;
            }
            if(dictHabilitados.isEmpty()){
                log.info("No se encontraron dictaminadores asignados al tramite de devolucion: "+solicitudDev.getNumControl()+" ADM: "+solicitudDev.getClaveAdm());
            }
            return dictHabilitados;
        }catch(DAOException e){
            log.error("Error al validar los tramites de devolucion asignados a los dictaminadores: "+e.getMessage());
            return dictaminadores;
        }
    }
    
    private List<DictaminadorVO> dictAsignadoTramitesCompensacion(List<DictaminadorVO> dictaminadores,DycpCompensacionDTO compensacionDTO){
        log.info("Inicia Asignacion de tramites de compensacion");
        List<DictaminadorVO> dictHabilitados=new ArrayList<DictaminadorVO>();
        List<DycaTipoServicioAsignarDTO> listaTramXDictaminador;
        List<DycaConfigDictaminadorDTO> listaConfigDicXServicio;
        Boolean dictAsignado=Boolean.FALSE;
        try{
            for (DictaminadorVO dict : dictaminadores) {
                listaTramXDictaminador=daoTipoServicioAsignar.obtenerTramXDictaminador(dict.getNumEmpleado(),Boolean.TRUE);
                if(listaTramXDictaminador != null){
                    for (DycaTipoServicioAsignarDTO tramXDictaminador : listaTramXDictaminador) {
                        listaConfigDicXServicio=daoConfigDictaminador.obtenerConfigDicXServicio(tramXDictaminador.getIdServicioAsignar(), Boolean.TRUE);
                        if(listaConfigDicXServicio != null){
                            for (DycaConfigDictaminadorDTO configDicXServicio : listaConfigDicXServicio) {
                                if (configDicXServicio.getIdTipoTramite().equals(compensacionDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite())
                                        && daoMonto.obtenerMontoXId(tramXDictaminador.getIdMonto()).getMontoMax().compareTo(compensacionDTO.getImporteCompensado()) >= 0) {
                                    dictAsignado = Boolean.TRUE;
                                    dictHabilitados.add(dict);
                                    break;
                                }
                            }
                        }
                        if(dictAsignado){
                            break;
                        }
                    }
                }
                dictAsignado=Boolean.FALSE;
            }
            if(dictHabilitados.isEmpty()){
                log.info("No se encontraron dictaminadores asignados al tramite de compensacion: "+compensacionDTO.getNumControl()+" ADM: "+compensacionDTO.getClaveAdm());
            }
            return dictHabilitados;
        }catch(DAOException e){
            log.error("Error al validar los tramites de compensacion asignados a los dictaminadores: "+e.getMessage());
            return dictaminadores;
        }
    }
    
    private int asignarAleatorioDictaminadorCompensacion(DycpCompensacionDTO compensacionDTO) throws SQLException {
        int valorRetorna = 0;
        boolean esInasistente;
        boolean esDisponible;

        AdmcUnidadAdmvaDTO unidadAdmva = new AdmcUnidadAdmvaDTO();
        unidadAdmva.setClaveSir(compensacionDTO.getDycpServicioDTO().getClaveAdm());
        unidadAdmva = daoUnidadAdm.consultarUnidadAdmvaList(unidadAdmva).get(ConstantesDyCNumerico.VALOR_0);

        List<DictaminadorVO> dictaminadores
                = daoDictaminador.consultarDictaminadorPorCargaAleatorio(compensacionDTO.getDycpServicioDTO().getClaveAdm(),
                        unidadAdmva.getClaveAgrs());
        dictaminadores=dictAsignadoTramitesCompensacion(dictaminadores,compensacionDTO);
        for (DictaminadorVO dictaminador : dictaminadores) {
            esInasistente = daoCalDictaMin.validarDiaActualA(dictaminador.getNumEmpleado(),
                    compensacionDTO.getFechaPresentaDec());

            if (esInasistente) {
                esDisponible = daoCalDictaMin.validarRegresoDictaminadorA(dictaminador.getNumEmpleado(),
                        compensacionDTO.getFechaPresentaDec());
            } else {
                esDisponible = daoCalDictaMin.valida4DiasInhabilesContinuosA(dictaminador.getNumEmpleado(),
                        compensacionDTO.getFechaPresentaDec());
            }

            if (esDisponible) {
                String numControl = StringUtils.isNotBlank(compensacionDTO.getDycpServicioDTO().getNumControl())
                        ? compensacionDTO.getDycpServicioDTO().getNumControl().trim() : compensacionDTO.getNumControl();

                if (daoServicio.updateDictaminador(dictaminador.getNumEmpleado(), numControl) > 0) {
                    log.info(" Se asigno la compensacion: " + numControl + "<- al dictaminador: " + dictaminador.getNumEmpleado());

                } else {
                    log.error("se intento asignar el dictaminador " + dictaminador.getNumEmpleado() + " a la compensacion ->"
                            + numControl + " pero la compensacion aún no existe en la base de datos");
                    compensacionDTO.getDycpServicioDTO().setDyccDictaminadorDTO(dictaminador);
                    compensacionDTO.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.EN_PROCESO);
                }

                valorRetorna = 1;
                break;
            }

        }
        return valorRetorna;
    }

    public int asignaCompensacion(DycpCompensacionDTO compensacionDTO) throws SQLException {

        int valorRetorna = 0;

        try {
            Integer competencia = CompetenciaUtils.identificaCompetencia(compensacionDTO.getDycpServicioDTO().getClaveAdm());
            switch (competencia) {
                case ConstantesIds.COMPETENCIA_AGGAF:
                case ConstantesIds.COMPETENCIA_AGH:

                    DyctSaldoIcepDTO saldoICEP = creaICEPCompensacion(compensacionDTO);
                    ReglaRnfdcVO regla33 = reglaRnfdcDAO.consultaReglaRnfdc(saldoICEP, compensacionDTO);
                    AdmcUnidadAdmvaDTO cbzUnidad = new AdmcUnidadAdmvaDTO();
                    cbzUnidad.setClaveSir(compensacionDTO.getDycpServicioDTO().getClaveAdm());
                    log.info("clave adm asig compensa:" + compensacionDTO.getDycpServicioDTO().getClaveAdm());
                    cbzUnidad = daoUnidadAdm.consultarUnidadAdmvaList(cbzUnidad).get(ConstantesDyCNumerico.VALOR_0);
                    log.info("clave sir asig compensa:" + cbzUnidad.getClaveSir());
                    if ((regla33 != null) && (regla33.getEmpleadoDicta() != null && regla33.getEmpleadoDicta() != 0)
                            && validaAdmAnterior(regla33.getEmpleadoDicta(), cbzUnidad.getClaveSir(), compensacionDTO)) {

                        if (daoServicio.updateDictaminador(regla33.getEmpleadoDicta(), compensacionDTO.getDycpServicioDTO().getNumControl()) > 0) {
                            log.info("No. Empleado: " + regla33.getEmpleadoDicta() + " al Numero de Control: "
                                    + compensacionDTO.getNumControl());
                            valorRetorna = 1;
                        }
                    } else {
                        //No encontro dictaminador previo
                        return asignarAleatorioDictaminadorCompensacion(compensacionDTO);
                    }
                default:
                    return asignarAleatorioDictaminadorCompensacion(compensacionDTO);
            }

        } catch (SIATException e) {
            log.error("Ocurrio error en asignaCompensacion; mensaje ->" + e.getMessage());
        }
        return valorRetorna;
    }

    private boolean validaAdmAnterior(Integer empleadoDicta, Integer claveAdmContribuyente, DycpCompensacionDTO compensacionDTO) {

        boolean haCambiadoAdm = false;

        if (compensacionDTO.getDycpServicioAnteriorDTO() != null) {
            haCambiadoAdm = (compensacionDTO.getDycpServicioDTO().getClaveAdm().equals(compensacionDTO.getDycpServicioAnteriorDTO().getClaveAdm()))
                    ? false : true;
        } else if (!validaAdmRFContribuyente(compensacionDTO.getDycpServicioDTO(), ConstantesAsignarAuDic.SERVICIO_AVISO_DE_COMPENSACION)) {
            haCambiadoAdm = validaAdmAnteriorDict(empleadoDicta, claveAdmContribuyente);
        } else {
            haCambiadoAdm = true;
        }

        return haCambiadoAdm;
    }

    private boolean validaAdmAnteriorDict(Integer empleadoDicta, Integer claveAdmContribuyente) {
        boolean isEqualADM = false;
        try {
            Integer cveAdmDictaminador = daoDictaminador.obtenerClaveAdmDictaminador(empleadoDicta);
            isEqualADM = claveAdmContribuyente.equals(cveAdmDictaminador);
        } catch (SQLException ex) {
            log.error("Error al obtener la claveADM");
        }
        return isEqualADM;
    }

}
