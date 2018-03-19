package mx.gob.sat.siat.dyc.casocomp.service;

import java.util.Map;

import mx.gob.sat.siat.dyc.domain.vistas.EnumTipoUnidadAdmvaDyC;
import mx.gob.sat.siat.dyc.trabajo.util.constante.EnumRol;


public interface DictaminarCompensacionService
{
    Map<String, Object> obtenerInfoIniDictaminarCC(String numControlComp, EnumRol rol, EnumTipoUnidadAdmvaDyC tipoAdm);

    void insertarNota(Map<String, Object> params);
}
