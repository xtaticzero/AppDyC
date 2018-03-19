/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.dyc.controlsaldos.service;

import java.util.Map;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author christian.ventura
 */
public interface ProcesarDeclaracionCompBussinesDelInterface {

    void afectarSaldosXDeclaComplementaria(Map<String, Object> params) throws SIATException;

    DyctSaldoIcepDTO obtenerIcep(Map<String, Object> params);
    
}
