package mx.gob.sat.siat.dyc.comunica.service;

import mx.gob.sat.siat.nyv.sistema.webservice.RegistroActoAdministrativoVO;
import mx.gob.sat.siat.nyv.sistema.webservice.ResponseRegistro;
import mx.gob.sat.siat.nyv.sistema.webservice.WSNyVSistemaImplProxy;

import org.apache.log4j.Logger;


public class EnviarNyV extends Thread {
    
    private ResponseRegistro respuesta;
    private WSNyVSistemaImplProxy wSNyVSistemaImpl;
    private RegistroActoAdministrativoVO registroActoAdministrativoVO;
    public static final Logger LOGGER = Logger.getLogger(EnviarNyV.class);
    
    
    @Override
    public void run() {
        
        try {
            respuesta = wSNyVSistemaImpl.registrarActoAdministrativo(registroActoAdministrativoVO);
            LOGGER.info("JAHO - Se hizo el registro del acto administrativo.");
            
        } catch (Exception e) {
            LOGGER.error("Hubo un error en la ejecucion del WS de NyV: "+e);
        }
    }

    public void setWSNyVSistemaImpl(WSNyVSistemaImplProxy wSNyVSistemaImpl) {
        this.wSNyVSistemaImpl = wSNyVSistemaImpl;
    }

    public WSNyVSistemaImplProxy getWSNyVSistemaImpl() {
        return wSNyVSistemaImpl;
    }

    public void setRegistroActoAdministrativoVO(RegistroActoAdministrativoVO registroActoAdministrativoVO) {
        this.registroActoAdministrativoVO = registroActoAdministrativoVO;
    }

    public RegistroActoAdministrativoVO getRegistroActoAdministrativoVO() {
        return registroActoAdministrativoVO;
    }

    public void setRespuesta(ResponseRegistro respuesta) {
        this.respuesta = respuesta;
    }

    public ResponseRegistro getRespuesta() {
        return respuesta;
    }
}
