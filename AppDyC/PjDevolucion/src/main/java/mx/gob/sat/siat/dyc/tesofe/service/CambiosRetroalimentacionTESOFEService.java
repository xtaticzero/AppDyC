package mx.gob.sat.siat.dyc.tesofe.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface CambiosRetroalimentacionTESOFEService {
    void buscarFlujoAlterno(String[] datosRenglon) throws SIATException;
}
