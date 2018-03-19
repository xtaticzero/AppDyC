package mx.gob.sat.siat.dyc.service.impl;

import java.util.HashMap;
import java.util.Map;

import mx.gob.sat.siat.dyc.service.MantenerDocumentoService;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;


@Service("serviceDummyMantenerDocumento")
public class DummyMantenerDocumentoServiceImpl implements MantenerDocumentoService
{   
    private Logger log = Logger.getLogger(DummyMantenerDocumentoServiceImpl.class);
    
    @Override
    public Map<String, Object> ejecutar(Map<String, Object> params)
    {
        log.info("DummyMantenerDocumentoServiceImpl INICIO ejecutar");
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("resultado", Boolean.TRUE);
        respuesta.put("mensaje", "Se simulo generacion de documento");
        respuesta.put("url", "/prueba/ruta");
        respuesta.put("nombreArchivo", "archivoDePrueba1.doc");
        return respuesta;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
}
