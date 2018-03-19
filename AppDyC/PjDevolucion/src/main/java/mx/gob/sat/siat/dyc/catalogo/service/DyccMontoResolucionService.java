/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoResolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;

/**
 *
 * @author root
 */
public interface DyccMontoResolucionService {

    void insertarMonto(DyccMontoResolucionDTO montoResolucion, PistaAuditoriaVO pistasAuditoria) throws SIATException;

    void actualizarMonto(DyccMontoResolucionDTO montoResolucion,  PistaAuditoriaVO pistasAuditoria) throws SIATException;

    void inactivarMonto(DyccMontoResolucionDTO montoResolucion,  PistaAuditoriaVO pistasAuditoria) throws SIATException;

    DyccMontoResolucionDTO existeMontoXIdActivo(Integer idTipoTramite);

    List<DyccMontoResolucionDTO> findAllMontos() throws SIATException;

    DyccMontoResolucionDTO existeMontoAutorizado(DyccMontoResolucionDTO montoResolucion) throws SIATException;
}
