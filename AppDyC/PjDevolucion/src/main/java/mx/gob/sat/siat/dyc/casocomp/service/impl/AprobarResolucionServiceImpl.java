package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.service.AprobarResolucionService;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.ReasignarManSolicDevolucionyCasosCompService;
import mx.gob.sat.siat.dyc.trabajo.service.ValidarRNFDC1013Service;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("serviceAprobarResolComp")
public class AprobarResolucionServiceImpl implements AprobarResolucionService {

    private static final Logger LOG = Logger.getLogger(AprobarResolucionServiceImpl.class);

    @Autowired
    private IAdmCasosCompensacionService serviceAdmCC;

    @Autowired
    private RegistroPistasAuditoria serviceRegistroPistasAud;

    @Autowired
    private DyctResolCompDAO daoResolComp;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private ValidarRNFDC1013Service serviceValidarML;

    @Autowired
    private DyccAprobadorDAO daoAprobador;

    @Autowired
    private ReasignarManSolicDevolucionyCasosCompService reasignacionTramites;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = SIATException.class)
    public Map<String, Object> determinarResolucion(Map<String, Object> params) throws SIATException {

        Map<String, Object> respuesta = new HashMap<String, Object>();
        Integer claveResolucion = (Integer) params.get("claveResolucion");
        Integer claveADM = (Integer) params.get("claveADM");
        String obs = (String) params.get("observaciones");

        DycpCompensacionDTO compensacion;

        try {

            compensacion = daoCompensacion.encontrar((String) params.get(ConstantesACC.NUMEROCONTROL));
            DycpServicioDTO servicio = compensacion.getDycpServicioDTO();
            DyctResolCompDTO resolComp = new DyctResolCompDTO();

            if (claveResolucion == ConstantesACC.CLAVE_APROBAR_RESOLUCION) {

                LOG.debug("Se aprobo la resolucion para la compensacion ->"
                        + compensacion.getDycpServicioDTO().getNumControl());

                DyccAprobadorDTO aprobador = daoAprobador.encontrar((Integer) params.get("numEmpleado"));

                if (serviceValidarML.validaMonto(aprobador, compensacion.getImporteCompensado(), claveADM)
                        && !(Boolean) params.get("seEscala")) {

                    LOG.debug("el servicio ->" + serviceValidarML
                            + "<- determino que el monto de la compensación NO excede el monto límite de aprobación para el usuario firmado");

                    compensacion.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.REGISTRO);

                    daoCompensacion.actualizarEstadocomp(compensacion);

                    resolComp.setDycpCompensacionDTO(compensacion);
                    resolComp.setDyccAccionSegDTO(Constantes.AccionesSeg.APROBACION);
                    resolComp.setFechaResolucion(new Date());
                    resolComp.setObservaciones(obs);
                    resolComp.setDyccEstResolDTO(Constantes.EstadosResolucion.APROBADA);

                    daoResolComp.actualizar(resolComp);

                    try {

                        Map<String, String> valsDinamicosPistAud = new HashMap<String, String>();
                        valsDinamicosPistAud.put("<NumeroDeControl>", servicio.getNumControl());

                        resgitrarPistaAuditoria(ConstantesACC.PISTAS_IDMENSAJE_APROB_VALIDA, valsDinamicosPistAud,
                                (String) params.get(ConstantesACC.NUMEROCONTROL));

                        serviceRegistroPistasAud.addMensajesReemplazo(valsDinamicosPistAud);

                        respuesta.put("mensaje",
                                serviceRegistroPistasAud.obtenerMensaje(ConstantesACC.PISTAS_IDMENSAJE_APROB_VALIDA,
                                        ConstantesDyCNumerico.VALOR_1,
                                        ConstantesACC.IDGRUPOOPERACION_PISTASAUDITORIA));

                    } catch (Exception e) {
                        LOG.error("ocurrio un error al Registrar Pistas de Auditoria; mensaje ->" + e.getMessage()
                                + "<-");
                    }

                    respuesta.put("seCompletoResolucion", Boolean.TRUE);

                } else {

                    RequestContext.getCurrentInstance().execute("dlgAResolucion.show()");
                    RequestContext.getCurrentInstance().execute("dlgAEscalar.hide()");

                    LOG.debug("el servicio ->" + serviceValidarML
                            + "<- determino que el monto de la compensación EXCEDE el monto límite de aprobación para el usuario firmado ó pidió escalar");

                    respuesta.put("superiores",
                            serviceAdmCC.obtenerSuperioresAprobarResol(claveADM, Integer.parseInt(params.get("centroCosto").toString()),
                                    Integer.parseInt(params.get("numEmpleado").toString()),
                                    Integer.parseInt(params.get("nivelAprobador").toString())));
                    respuesta.put("seCompletoResolucion", Boolean.FALSE);

                }
            } else if (claveResolucion == ConstantesACC.CLAVE_NO_APROBAR_RESOLUCION) {
                LOG.info("Se rechazo la resolucion para la compensacion ->" + servicio.getNumControl());

                compensacion.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.EN_PROCESO);

                if (((Boolean) params.get("realizarAsignacion"))) {

                    Integer numEmpDictaminador = null;
                    if (((Boolean) params.get("esCambioEmpleado"))) {
                        numEmpDictaminador = (Integer) params.get("numAprobReasignado");
                    } else {
                        numEmpDictaminador = (Integer) params.get("numDictaminador");
                    }
                    reasignaTramiteADictaminador(compensacion, numEmpDictaminador);
                }

                daoCompensacion.actualizarEstadocomp(compensacion);

                resolComp.setDycpCompensacionDTO(compensacion);
                resolComp.setDyccAccionSegDTO(Constantes.AccionesSeg.RECHAZO);
                resolComp.setFechaResolucion(new Date());
                resolComp.setObservaciones(obs);
                resolComp.setDyccEstResolDTO(Constantes.EstadosResolucion.NO_APROBADA);

                daoResolComp.actualizar(resolComp);

                Map<String, String> valsDinamicosPistAud = new HashMap<String, String>();
                valsDinamicosPistAud.put("<NumeroDeControl>", servicio.getNumControl());
                if (((Boolean) params.get("esCambioEmpleado"))) {

                    valsDinamicosPistAud.put("<NumeroDeEmpleado>", params.get("numAprobReasignado").toString());
                    valsDinamicosPistAud.put("<NombreDelEmpleado>", params.get("nombreAprobReasignado").toString());
                } else {
                    valsDinamicosPistAud.put("<NumeroDeEmpleado>", params.get("numDictaminador").toString());
                    valsDinamicosPistAud.put("<NombreDelEmpleado>", params.get("dictaminador").toString());
                }

                resgitrarPistaAuditoria(ConstantesACC.PISTAS_IDMENSAJE_RECHAZO_APROB, valsDinamicosPistAud,
                        servicio.getNumControl());

                serviceRegistroPistasAud.addMensajesReemplazo(valsDinamicosPistAud);

                respuesta.put("mensaje",
                        serviceRegistroPistasAud.obtenerMensaje(ConstantesACC.PISTAS_IDMENSAJE_RECHAZO_APROB,
                                ConstantesDyCNumerico.VALOR_1,
                                ConstantesACC.IDGRUPOOPERACION_PISTASAUDITORIA));

                respuesta.put("seCompletoResolucion", Boolean.TRUE);
            }

        } catch (SIATException e) {
            throw new SIATException(e);
        }

        respuesta.put("success", Boolean.TRUE);

        return respuesta;
    }

    private void reasignaTramiteADictaminador(DycpCompensacionDTO compensacion, Integer numEmpDictaminador) {

        List<DictaminadorSolBean> listaSolicitudes = getListaSolicitudes(compensacion);

        DyccDictaminadorDTO dictaminadorDestino = new DyccDictaminadorDTO();
        dictaminadorDestino.setNumEmpleado(numEmpDictaminador);

        reasignacionTramites.reasignacionManualSolicitud(listaSolicitudes, null, dictaminadorDestino, null);
    }

    private List<DictaminadorSolBean> getListaSolicitudes(DycpCompensacionDTO compensacion) {

        DictaminadorSolBean solicitud = new DictaminadorSolBean();

        solicitud.setNumControl(compensacion.getNumControl());

        List<DictaminadorSolBean> listaSolicitudes = new ArrayList<DictaminadorSolBean>();
        listaSolicitudes.add(solicitud);

        return listaSolicitudes;
    }

    @Override
    public boolean puedeEscalar(Map<String, Object> params) {
        boolean esEscalable = false;
        try {
            DyccAprobadorDTO aprobador = daoAprobador.encontrar((Integer) params.get("numEmpleado"));
            DycpCompensacionDTO compensacion;
            compensacion = daoCompensacion.encontrar((String) params.get(ConstantesACC.NUMEROCONTROL));
            Integer claveADM = (Integer) params.get("claveADM");
            Integer nivelAProb = (Integer) params.get("nivelAprobador");
            esEscalable
                    = nivelAProb > 2 && serviceValidarML.validaMonto(aprobador, compensacion.getImporteCompensado(),
                            claveADM);
        } catch (SIATException e) {
            LOG.error("No se pudo validar el importe, no se preguntará si escala: " + e.getCause());
        }
        return esEscalable;
    }

    @Override
    public Map<String, Object> escalarAprobacion(Map<String, Object> params) throws SIATException {
        Integer numEmpleadoAprob = (Integer) params.get("numEmpleadoAprob");
        String numControl = (String) params.get(ConstantesACC.NUMEROCONTROL);
        DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            aprobador.setNumEmpleado(numEmpleadoAprob);

            DycpServicioDTO servicio = new DycpServicioDTO();
            servicio.setNumControl(numControl);
            DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
            compensacion.setNumControl(numControl);
            compensacion.setDycpServicioDTO(servicio);
            compensacion.setDyccAprobadorDTO(aprobador);
            daoCompensacion.actualizar(compensacion);

            respuesta.put("success", Boolean.TRUE);

        } catch (SIATException e) {
            throw new SIATException(e);
        }
        return respuesta;
    }

    private void resgitrarPistaAuditoria(Integer idMensaje, Map<String, String> valoresDinamicos,
            String numControl) throws SIATException {
        PistaAuditoriaVO pistaAuditoria = new PistaAuditoriaVO();
        pistaAuditoria.setIdProceso(Procesos.DYC00012);
        pistaAuditoria.setIdGrupoOperacion(ConstantesACC.IDGRUPOOPERACION_PISTASAUDITORIA);
        pistaAuditoria.setIdMensaje(idMensaje);
        pistaAuditoria.setMovimiento(ConstantesACC.MOVIMIENTO_PISTASA_APROBAR_RESOL);
        pistaAuditoria.setIdentificador(numControl);
        pistaAuditoria.setRemplaceMensajes(valoresDinamicos);
        serviceRegistroPistasAud.registrarPistaAuditoria(pistaAuditoria);
        LOG.info("Termina el registro de pistas de auditoria");
    }
}
