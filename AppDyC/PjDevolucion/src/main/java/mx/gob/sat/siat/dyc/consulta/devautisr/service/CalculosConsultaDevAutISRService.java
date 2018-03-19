/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.service;

import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRDetalleVO;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public interface CalculosConsultaDevAutISRService {

    TramiteDevAutISRBO determinarDiferenciasDeclaraciones(DatosTramiteISRDetalleVO datosTramiteISRDetalleVO);
    
}
