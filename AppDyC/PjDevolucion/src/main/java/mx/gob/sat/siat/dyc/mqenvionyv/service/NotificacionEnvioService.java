/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.dyc.mqenvionyv.service;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.AprobarDocumentoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface NotificacionEnvioService {
    void enviarANYV(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException;
}
