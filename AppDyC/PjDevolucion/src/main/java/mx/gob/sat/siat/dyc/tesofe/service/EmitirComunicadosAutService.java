package mx.gob.sat.siat.dyc.tesofe.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface EmitirComunicadosAutService {
    void emitirComunicadosAutomaticamente(String numeroControl, int tipoComunicado, String [] datosTESOFE) throws SIATException;
}
