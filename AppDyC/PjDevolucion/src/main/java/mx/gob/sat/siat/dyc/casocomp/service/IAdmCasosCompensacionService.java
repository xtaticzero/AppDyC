package mx.gob.sat.siat.dyc.casocomp.service;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridUsuariosLoginAuxBean;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.vistas.EnumTipoUnidadAdmvaDyC;
import mx.gob.sat.siat.dyc.trabajo.util.constante.EnumRol;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface IAdmCasosCompensacionService {
    List<ItemComboBean> obtenerSuperiores();

    List<ItemComboBean> obtenerSuperiores(Integer claveADM, Integer cCosto);
    
    List<ItemComboBean> obtenerSuperioresAprobarResol(Integer claveADM, Integer cCosto, Integer numEmpleado, Integer nivel);

    List<FilaGridUsuariosLoginAuxBean> obtenerUsuariosLoginAux();

    Map<String, Object> obtenerDatosSesion(Integer numEmpleado, EnumRol rol);
    
    String guardarArchivo(DycpServicioDTO servicio, InputStream secuenciaEntrada, String nombreArchivo) throws SIATException;
    
    EnumTipoUnidadAdmvaDyC obtnerTipoUnidadAdmva(Integer claveAdm);
}
