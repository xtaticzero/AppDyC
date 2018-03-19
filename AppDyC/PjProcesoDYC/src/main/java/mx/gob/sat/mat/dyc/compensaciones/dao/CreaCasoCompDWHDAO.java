/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.mat.dyc.compensaciones.dao;

import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;

/**
 *
 * @author christian.ventura
 */
public interface CreaCasoCompDWHDAO {

    List<CasoCompensacionVO> selecXDeclaracion(Date fechaBuscar);
    
}
