package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.service.EmitirResolucionService;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("serviceEmitirResolucion")
public class EmitirResolucionServiceImpl implements EmitirResolucionService {
    private static final Logger LOG = Logger.getLogger(EmitirResolucionServiceImpl.class);

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DyctResolCompDAO daoResolComp;

    @Autowired
    private RegistroPistasAuditoria serviceRegistroPistasAud;
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { SIATException.class })
    public String registrarCompensacion ( Map<String, Object> params ){

        LOG.debug("INICIO registrarCompensacion");

        PistaAuditoriaVO pistaAuditoria = new PistaAuditoriaVO();
        String mensaje;
        String numControl = (String) params.get("numControl");

        try {
            DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
            aprobador.setNumEmpleado((Integer)params.get("numEmpleadoAprobador"));
            DycpServicioDTO servicio = new DycpServicioDTO();
            servicio.setNumControl(numControl);
            DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
            compensacion.setNumControl(numControl);
            compensacion.setDycpServicioDTO(servicio);
            compensacion.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.EN_REVISION);
            LOG.info("el estatus de la compensacion ->" + compensacion.getDycpServicioDTO().getNumControl() +
                     "<- sera cambiado En revision");
            compensacion.setDyccAprobadorDTO(aprobador);
            daoCompensacion.actualizar(compensacion);
    
            DyctResolCompDTO resolComp = new DyctResolCompDTO();
            resolComp.setDycpCompensacionDTO(compensacion);
            resolComp.setDyccAccionSegDTO(Constantes.AccionesSeg.ESCALACION);
            resolComp.setFechaResolucion(new Date());
            resolComp.setDyccEstResolDTO(Constantes.EstadosResolucion.EN_APROBACION);
            resolComp.setDyccTipoResolDTO(Constantes.TiposResolucion.REGISTRAR_CASOCOMP);
            DyctResolCompDTO resolComTmp = daoResolComp.encontrar(compensacion);
            if (null == resolComTmp) {
                daoResolComp.insertar(resolComp);
            } else {
                daoResolComp.actualizar(resolComp);
            }

            pistaAuditoria.setIdProceso(Procesos.DYC00012);
            pistaAuditoria.setIdGrupoOperacion(ConstantesACC.IDGRUPOOPERACION_PISTASAUDITORIA);
            pistaAuditoria.setIdMensaje(ConstantesDyCNumerico.VALOR_1);
            pistaAuditoria.setMovimiento(ConstantesACC.MOVIMIENTO_PISTASA_EMITE_RESOLUCION);
            pistaAuditoria.setIdentificador((String)params.get("numControl"));

            HashMap<String, String> remplaceMensajes = new HashMap<String, String>();
            remplaceMensajes.put("<NumeroDeControl>", numControl);
            pistaAuditoria.setRemplaceMensajes(remplaceMensajes);

            serviceRegistroPistasAud.registrarPistaAuditoria(pistaAuditoria);

            mensaje =
                    serviceRegistroPistasAud.obtenerMensaje(ConstantesDyCNumerico.VALOR_1, ConstantesDyCNumerico.VALOR_3,
                                                            ConstantesDyCNumerico.VALOR_51);
        } catch (SIATException e) {
            mensaje = "ocurrio un error al registrar las pistas de auditoria; registrar Compensacion: " + numControl;
            LOG.error(mensaje + "; mensaje de la excepcion ->" + e.getMessage());
        }
        return mensaje;
    }
    
}
