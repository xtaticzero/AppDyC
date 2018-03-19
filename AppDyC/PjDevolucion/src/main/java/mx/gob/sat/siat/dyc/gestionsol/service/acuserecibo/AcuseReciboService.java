/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Alfredo Ramirez
 * @since 20/09/2013
 */
public interface AcuseReciboService {

    ConsultarDevolucionesContribuyenteDTO solicitudPorNumControl(String numControl, String rfc);

    void generarAcuseRecibo(Integer tipos, String numControl, String tfc, boolean isAdm, boolean isReimpresion) throws SIATException;

    void mostrarReporteAvisos(String numControl, String rfc, boolean isAdm) throws SIATException;

    void mostrarReporteAvisosDownload(String numControl, String rfc, boolean isAdm) throws SIATException;

    void mostrarReporteAvisos(String numControl, String rfc) throws SIATException;

    void mostrarAcuseContestarRequerimiento(final String numDocumento, final String rfcContribuyente, boolean consideraRfc) throws SIATException;

    void mostrarAcuseContestarRequerimiento(final String numDocumento) throws SIATException;

    List<DyctDocumentoDTO> consultaNumeroDoc(String numControl) throws SIATException;

    String getRFCOwner(String numControl, String rfc) throws SIATException;

    Map<String, Object> consultarSolicitud(String rfc, String numControl, int servicio,
            Integer tipos, boolean isReimpresion) throws ParseException;

    String getRFCOwnerXFolioAviso(String folioAviso, String rfc) throws SIATException;

    void generarAcuseReciboReimpresion(Integer tipos, String numControl, String tfc, boolean isAdm, boolean consideraRfc, String numControlDoc) throws SIATException;
}
