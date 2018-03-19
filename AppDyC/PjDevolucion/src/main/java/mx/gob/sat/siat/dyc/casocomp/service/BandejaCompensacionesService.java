package mx.gob.sat.siat.dyc.casocomp.service;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.ContribuyenteBean;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridCasosCompBean;


public interface BandejaCompensacionesService
{
    List<FilaGridCasosCompBean> obtenerCompensaciones(Map<String, Object> params);

    ContribuyenteBean obtenerContribuyente(String numControl);
}
