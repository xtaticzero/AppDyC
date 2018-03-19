/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.batch.devautomaticas.service;

import mx.gob.sat.mat.dyc.batch.devautomaticas.dto.TramiteDTO;
import mx.gob.sat.mat.dyc.batch.devautomaticas.exception.DevAutomaticasException;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public interface DycAutomaticasService {
    void procesarTramite(TramiteDTO tramite) throws DevAutomaticasException;
}
