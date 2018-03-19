package mx.gob.sat.siat.dyc.service;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;


public interface DyccAprobadorService {

    List<DyccAprobadorDTO> mostrarAprobadorConTrabajo(Integer claveAdm, Integer centroCostoOp, Integer opcion);

    void actualizarCargaTrabajoAprob(Integer nuempleadoAprob, Integer nuevoEmpleado) throws SIATException;

    void actualizarAprobadorDocumento(Integer numeroEmpleadoAprobador,
                                      String numeroControlDocumento) throws SIATException;

    List<DictaminadorSolBean> consultarTramitesAsociadosAprobador(DyccAprobadorDTO seleccionAprob) throws SIATException;

    List<DictaminadorSolBean> consultarTramitesAsociadosAprobadorPorFecha(DyccAprobadorDTO seleccionAprob, Date fechaInicio,
                                                                  Date fechaFin) throws SIATException;

    void reasignacionAprobador(List<DictaminadorSolBean> listaSeleccionSolicitudes,
                               DyccAprobadorDTO aprobadorDestinoReasignacion, Integer empleadoResponsable) throws SIATException;
}
