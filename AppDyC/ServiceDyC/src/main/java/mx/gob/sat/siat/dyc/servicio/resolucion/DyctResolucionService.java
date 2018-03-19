package mx.gob.sat.siat.dyc.servicio.resolucion;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.EmitirResolucionVO;

import java.util.Date;
import java.util.List;

public interface DyctResolucionService {
    DyctResolucionDTO consultarDatosResolucionXNumeroControl(String numControl) throws SIATException;

    DyctResolucionDTO obtener(String numControl) throws SIATException;

    Integer existeResolucionAprobadaEnAprobacion(String numControl) throws SIATException;

    Integer existeResolucionNoAprobada(String numControl) throws SIATException;

    Integer existeResolucion(String numControl) throws SIATException;

    void actualizarEstResol(Integer idEstResol, String numControl);

    boolean existe(String numControl);

    void actualizarSaldoAFavor(String numControl, double saldoAFavorAntRes, double saldoAFavorDesRes) throws SIATException;

    List<EmitirResolucionVO> buscarInformacionRequerida(String numControl, int tipoDocumento);

    List<EmitirResolucionVO> buscarAnexosRequerir(String numControl, int tipoDocumento);

    List<EmitirResolucionVO> buscarOtraInfoRequerir(String numControl, int tipoDocumento);

    void actualizarPagoEnviado(Integer pagoEnviado, String numControl) throws SIATException;

    List<DyctResolucionDTO> obtenerAprobadasPorIdSaldoICEP(Integer idSaldoICEP) throws SIATException;

    void insertar (DyctResolucionDTO  dyctResolucionDTO)throws SIATException;

    void insertResolucionSimulador(String numControl, double importeAutorizado, double importeNegado, double interes) throws SIATException;

    void actualizarFechaAprobacion(String numeroControl) throws SIATException;

    void actualizarClaveRastreo(String numeroControl, String claveRastreo) throws SIATException;

    DyctResolucionDTO consultarDatosResolucionXCveRastreo(String numControl) throws SIATException;

    DyctResolucionDTO encontrar(DycpSolicitudDTO solicitud);

    boolean verificarSiEsAGAFoAGGC(String claveRastreo, String cveAdministracion);

    /**
     * Actualiza las fechas de pago, presentacion y emision al generar un registro en TESOFE.
     *
     * @param fechaPresentacion
     * @param fechaPago
     * @param numControl
     * @throws SIATException
     */
    void actualizarFechasDeEnvioATESOFE(Date fechaPresentacion, Date fechaPago, String numControl) throws SIATException;
}
