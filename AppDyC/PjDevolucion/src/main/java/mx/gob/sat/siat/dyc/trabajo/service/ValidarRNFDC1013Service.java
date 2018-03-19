package mx.gob.sat.siat.dyc.trabajo.service;

import java.math.BigDecimal;

import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.EnumTipoUnidadAdmvaDyC;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface ValidarRNFDC1013Service
{
    Boolean validar(DyccAprobadorDTO aprobador, BigDecimal montoAutorizar, EnumTipoUnidadAdmvaDyC tipoAdministracion) throws SIATException ;
    boolean validaMonto(DyccAprobadorDTO datosAprobador, BigDecimal montoAutorizar, Integer claveSir) ;
}
