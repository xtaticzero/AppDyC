package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Huetzin Cruz Lozano
 */
@Service(value = "delegateDeclaManual")
public class DeclaracionManualBussinesDel
{
    private static final Logger LOG = Logger.getLogger(RegistrosManualesServiceImpl.class.getName());
    
    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;

    public Map<String, Object> validarDeclaracion(Map<String, Object> params)
    {
        LOG.debug("INICIO validarDeclaracion");
        
        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        saldoIcep.setIdSaldoIcep((Integer)params.get("idSaldoIcep"));
        
        Map<String, Object> respuesta = new HashMap<String, Object>();
        DyctDeclaraIcepDTO declaraIcep = new DyctDeclaraIcepDTO();
        Integer origenDeclara = params.get("origenDeclara") != null ? ((Integer) params.get("origenDeclara")) : Constantes.OrigenesDeclaracion.VALIDADA_EMPLEADO; 
        declaraIcep.setOrigenDeclara(origenDeclara);
        declaraIcep.setValidacionDWH(new Date());
        try{
            declaraIcep.setUsrRegistro((String)params.get("usuarioValida"));
            declaraIcep.setNotas((String)params.get("justificacion"));
            declaraIcep.setIdDeclaraIcep((Integer)params.get("idDeclaraIcep"));
            declaraIcep.setDyctSaldoIcepDTO(saldoIcep);

            daoDeclaraIcep.actualizarSinNulos(declaraIcep);
            respuesta.put("success", Boolean.TRUE);
        }
        catch(Exception e){
            ManejadorLogException.getTraceError(e);
            respuesta.put("success", Boolean.FALSE);
            respuesta.put("mensaje", e.getMessage());
        }
        return respuesta;
    }
}
