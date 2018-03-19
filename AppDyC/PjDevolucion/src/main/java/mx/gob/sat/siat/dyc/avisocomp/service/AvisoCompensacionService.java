/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.service;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DataUploadVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosAvisoFieldVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosDestinoAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosOrigenAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.PendienteVO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author  Alfredo Ramirez
 * @since   20/11/2013
 * @author  Yolanda Martínez Sánchez
 *
 */
public interface AvisoCompensacionService {

    List<PendienteVO> obtenerAvisosPendientes(String rfc);

    String obtenerNumeroControl(String claveSirNumControl) throws SIATException;

    List<DataUploadVO> getDatasAvisoCompensacionByFolio(Integer folioAvisoTemp);

    DyccTipoPeriodoDTO getDyccTipoPeriodoByIdPeriodo(Integer idPeriodo);

    String insertarCompensacion(DatosAvisoFieldVO datosAvisoField, String cadenaOriginal,
                                String selloDigital) throws SIATException;

    String insertarAvisoCompensacion(DatosAvisoFieldVO datosAvisoField, String cadenaOriginal,
                                     String selloDigital) throws SIATException;

    void insertarCompensacion(DatosAvisoFieldVO datosAvisoField) throws SIATException;

    DycpAvisoCompDTO getDycpAvisoCompDTOByFolioAviso(String folioAviso, String rfcContribuyente) throws SIATException;

    DyccTipoTramiteDTO getDycpTipoTramiteDTOById(Integer idTipoTramite);

    List<Date> validarHoraDeRegistroCompensacion();

    List<DatosDestinoAcuseVO> obtieneDestinoParaAcuse(String folioAviso);

    List<DatosOrigenAcuseVO> obtieneOrigenParaAcuse(String folioAviso);

    List<AnexoVO> buscarAnexosPorNumcontrol(String numcontrol);

    List<ArchivoVO> buscaArchivosPorFolioAviso(String folioAviso);

    List<DyctDeclaraIcepDTO> buscaAvisoXNumControlRemanente(String numControlRemanente, String rfcContribuyente);

    DycpAvisoCompDTO buscaAvisoCompensacion(String folioAviso) throws SIATException;
    
    DyctSaldoIcepDTO encontrarIcep(final String rfc, final int idConcepto, final int idEjercicio,
                                      final int idPeriodo);

}


