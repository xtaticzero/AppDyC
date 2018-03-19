package mx.gob.sat.siat.dyc.dao.detalleicep;

import java.math.BigDecimal;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpDatosSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.vo.DyctSaldoIcepAuxVO;
import mx.gob.sat.siat.dyc.vo.IcepVO;

public interface DyctSaldoIcepDAO {

    DyctSaldoIcepDTO encontrar(Integer idSaldoIcep);

    void eliminarIcep(Long idSaldoIcep) throws SIATException;

    List<DyctSaldoIcepAuxVO> selectIcep(Long idSaldoIcep) throws SIATException;

    /**
     * Metodo que obtiene el registro de [DYCT_SALDOICEP] por ICEP y RFC.
     *
     * @since 23-Ene-2014
     * @param icepDTO
     * @param rfc
     * @return
     */
    DyctSaldoIcepDTO getSaldoIcepByIcep(IcepVO icepDTO, String rfc) throws SIATException;

    boolean existeXIcepRfc(IcepVO icepDTO) throws SIATException;

    void actualizarRemanente(Integer idSaldoIcep, BigDecimal remanente) throws SIATException;

    boolean existeXnumControl(String numControl) throws SIATException;

    List<DyctSaldoIcepDTO> obtenerXnumCOntrol(String numControl) throws SIATException;

    boolean existeXId(long idSaldoICEP) throws SIATException;

    DyctSaldoIcepDTO obtenerXId(long idSaldoICEP) throws SIATException;

    List<DyctSaldoIcepDTO> obtenerSaldosDisponibles() throws SIATException;

    void insert(DyctSaldoIcepDTO dyctSaldoIcepDTO) throws SIATException;

    Integer obtenerSecuencia() throws SIATException;

    DyctSaldoIcepDTO obtenerXIcepRfc(IcepVO icepDTO) throws SIATException;

    List<DyctSaldoIcepDTO> selecXRfc(String rfc) throws SIATException;

    DyctSaldoIcepDTO encontrar(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo);

    /**
     * @see {@link mx.gob.sat.siat.dyc.dao.impl.DyctSaldoIcepDAOImpl.encontrar}
     */
    DyctSaldoIcepDTO encontrar(final String rfc, final DyccConceptoDTO concepto, final DyccEjercicioDTO ejercicio,
            final DyccPeriodoDTO periodo, final DyccOrigenSaldoDTO dyccOrigenSaldoEjercicioDTO);

    List<DyctSaldoIcepDTO> selecXRfcConceptoEjercicioPeriodo(String rfc, DyccConceptoDTO concepto,
            DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo);

    void actualizarRemFebaseActrem(DyctSaldoIcepDTO saldoIcep) throws SIATException;

    DyctSaldoIcepDTO encontrar(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo, Integer origenSaldo) throws DycDaoExcepcion;

    List<DycpDatosSolicitudDTO> getSolicitud(Integer idSaldoIcep);

    DyctSaldoIcepDTO encontrarIcep(final String rfc, final int idConcepto, final int idEjercicio,
            final int idPeriodo);

    List<DyctSaldoIcepDTO> selecXRfcConceptoEjercicioPeriodoOrigen(String rfc, DyccConceptoDTO concepto,
            DyccEjercicioDTO ejercicio, DyccPeriodoDTO periodo, DyccOrigenSaldoDTO origenSaldo);

    String getCompensacionPorIcep(Integer idSaldoIcep);

}
