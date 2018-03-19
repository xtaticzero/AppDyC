package mx.gob.sat.mat.dyc.registrarinformaciondeicep.service.impl;

import java.util.HashMap;
import java.util.Map;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.RegistroPistasAuditoriaServiceImpl;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "bdDlgDetalleIcep")
public class DlgDetalleIcepServiceImpl
{
    private static final Logger LOG = Logger.getLogger(DlgDetalleIcepServiceImpl.class);

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;
    
    @Autowired
    private RegistroPistasAuditoriaServiceImpl registroPistasAudService;

    @Autowired
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @Transactional(readOnly = false, rollbackFor = SIATException.class)
    public Map<String, Object> insertar(Map<String, Object> params)
    {
        Map<String, Object> resultadoInsertar = new HashMap<String, Object>();
        LOG.debug("INICIO insertar");
        LOG.debug("params ->" + params);

        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        DyccConceptoDTO concepto = new DyccConceptoDTO();
        concepto.setIdConcepto((Integer)params.get("idConcepto"));
        DyccPeriodoDTO periodo = new DyccPeriodoDTO();
        periodo.setIdPeriodo((Integer)params.get("idPeriodo"));
        DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
        ejercicio.setIdEjercicio((Integer)params.get("ejercicio"));

        saldoIcep.setRfc((String)params.get("rfc"));
        saldoIcep.setDyccConceptoDTO(concepto);
        saldoIcep.setDyccPeriodoDTO(periodo);
        saldoIcep.setDyccEjercicioDTO(ejercicio);
        saldoIcep.setDyccOrigenSaldoDTO(BuscadorConstantes.obtenerOrigenSaldo((Integer)params.get("tipoSaldo")));

        try{
            daoSaldoIcep.insert(saldoIcep);
            resultadoInsertar.put("success", Boolean.TRUE);
            resultadoInsertar.put("idSaldoIcep", saldoIcep.getIdSaldoIcep());
            //RegistraPista
            Map<String, String> valsDinamicosPistAud = new HashMap<String, String>();
            AccesoUsr accesoUsrL = serviceObtenerSesion.execute();
            valsDinamicosPistAud.put("<IdIcep>", saldoIcep.getIdSaldoIcep().toString());
            valsDinamicosPistAud.put("<NombreEmpleado>", accesoUsrL.getNombreCompleto());
            valsDinamicosPistAud.put("<NumEmpleado>", accesoUsrL.getNumeroEmp());
            registroPistasAudService.registrarPistaAuditoria(ConstantesACC.PISTAS_IDMENSAJE_CREAR_ICEP, ConstantesACC.IDGRUPOOPERACION_REGISTRA_INFO_ICEP, ConstantesACC.MOVIMIENTO_PISTASA_REGISTRA_ICEP, valsDinamicosPistAud);
        }
        catch (Exception e) {
            resultadoInsertar.put("success", Boolean.FALSE);
            LOG.error(e.getMessage());
            if(e.getMessage().contains("DuplicateKeyException")){
                resultadoInsertar.put("mensaje", "El icep con saldo a favor ya existe");
            }
            else{
                resultadoInsertar.put("mensaje", e.getMessage());
            }
        }
  
        return resultadoInsertar;
    }
}
