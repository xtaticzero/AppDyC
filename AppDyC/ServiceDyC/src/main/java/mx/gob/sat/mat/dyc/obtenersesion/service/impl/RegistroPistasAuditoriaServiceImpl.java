package mx.gob.sat.mat.dyc.obtenersesion.service.impl;

import java.util.Map;

import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "registroPistasAuditoriaService")
public class RegistroPistasAuditoriaServiceImpl {


    @Autowired
    private RegistroPistasAuditoria serviceRegistroPistasAud;

    private static final Logger LOG = Logger.getLogger(RegistroPistasAuditoriaServiceImpl.class);

    public RegistroPistasAuditoriaServiceImpl() {
        super();
    }

    public void registrarPistaAuditoria(Integer idMensaje, Integer idGrupo, Integer idMovimiento,
                                        Map<String, String> valoresDinamicos) throws SIATException {
        PistaAuditoriaVO pistaAuditoria = new PistaAuditoriaVO();
        pistaAuditoria.setIdProceso(Procesos.DYC00011);
        pistaAuditoria.setIdGrupoOperacion(idGrupo);
        pistaAuditoria.setIdMensaje(idMensaje);
        pistaAuditoria.setMovimiento(idMovimiento);
        pistaAuditoria.setRemplaceMensajes(valoresDinamicos);
        serviceRegistroPistasAud.registrarPistaAuditoria(pistaAuditoria);
        LOG.info("Termina el registro de pistas de auditoria");
    }
}
