/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.controlsaldos.service;

import java.util.List;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridDevolucionesBean;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;

/**
 *
 * @author Gregorio.Serapio
 */
public interface DevueltoHelper {
    List<FilaGridDevolucionesBean> obtenerDevoluciones(DyctSaldoIcepDTO saldoIcep);
}
