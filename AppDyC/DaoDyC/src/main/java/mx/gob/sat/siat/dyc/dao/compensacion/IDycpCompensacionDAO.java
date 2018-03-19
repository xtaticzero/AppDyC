/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.compensacion;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface IDycpCompensacionDAO {
    DycpCompensacionDTO encontrar(String numControl) throws SIATException;
    
    DycpCompensacionDTO buscarCompensacionIceps(String numControl) throws SIATException;

    List<DycpCompensacionDTO> selecXTiposervicio(DyccTipoServicioDTO tipoServicio) throws SIATException;

    void actualizar(Integer numEpleado, String numControl) throws SIATException;

    List<DycpCompensacionDTO> selecXDictaminadorEstadocomp(DyccDictaminadorDTO dictaminador,
                                                           DyccEstadoCompDTO estadoComp);

    List<DycpCompensacionDTO> selecXEstadocompClaveadm(DyccEstadoCompDTO estadoComp, Integer claveAdm);

    void actualizarStatus(Integer status, String numControl) throws SIATException;

    void insertar(DycpCompensacionDTO dycpCompensacionDTO, boolean regAut) throws SIATException;

    List<DycpCompensacionDTO> selecXEstadocomp(DyccEstadoCompDTO estadoComp);

    int actualizarEstadocomp(DycpCompensacionDTO compensacion);

    void insertarCompensacion(DycpAvisoCompDTO dycpAvisoCompDTO) throws SIATException;

    void actualizar(DycpCompensacionDTO compensacion)  throws SIATException;
    
    List<DycpCompensacionDTO> obtenerAprobadasXIdSaldoOrigen (Integer idSaldoIcep) throws SIATException; 
    
    List<DycpCompensacionDTO> obtenerAprobadasXIdSaldoDestino (Integer idSaldoIcep) throws SIATException; 
    
    List<DycpCompensacionDTO> selecXDictaminadorEstadocompOrder(DyccDictaminadorDTO dictaminador,
                                                                 DyccEstadoCompDTO estadoComp, String orderBy);
    
    List<DycpCompensacionDTO> selecXEstadocompClaveadmOrder(DyccEstadoCompDTO estadoComp, Integer claveAdm, String orderBy);
    
    List<DycpCompensacionDTO> selecXEstadocompClaveadmOrderRegistrada(DyccEstadoCompDTO estadoComp, Integer claveAdm, String orderBy, Integer numEmpleado);
    
    List<DycpCompensacionDTO> selecXSaldoiceporigen(DyctSaldoIcepDTO saldoIcep);
    
    List<DycpCompensacionDTO> selecXSaldoicepdestino(DyctSaldoIcepDTO saldoIcep);
    
    List<DycpCompensacionDTO> buscaXFolioAviso(String folioAvisoNormal);
    
    List<DycpCompensacionDTO> obtenerCompensacionResol(String numControl,int and) throws SIATException;
    
    List<DycpCompensacionDTO> obtenerCompensacionResol(String numControl) throws SIATException;
    
    List<DycpCompensacionDTO> buscaCompensacionAprobador(Integer numEmpleadoAprob);
    
    void actualizarNumEmpleadoAprob(Integer numEmpleadoAprob, String numControl) throws SIATException;
    
    List<DycpCompensacionDTO> selecXAvisocomp(DycpAvisoCompDTO avisoComp);
}
