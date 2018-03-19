/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.automaticasiva.service;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.siat.dyc.automaticasiva.domain.DycAutomaticasIvaInsertarSolicitudTO;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.EDycAutomaticasIvaEstadoCasoDevolucion;
import mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion;

/**
 *
 * @author GAER8674
 */
public interface CrearCasoAutomaticasIvaService {
    
    String FECHA_PRESENTACION = "fechaPresentacion";

    void insertarServicioAutomaticasIva(String numeroControl, String rfc, Integer claveSir, String boid) throws DycServiceExcepcion;

    void insertarSolicitud(DycAutomaticasIvaInsertarSolicitudTO solicitudTO) throws DycServiceExcepcion;

    void insertarContribuyente(String numeroControl, Date fechaConsultaARFCAmpliado,
            IdCInterno rfcAmpliadoResponse) throws DycServiceExcepcion;

    void insertarDeclaracion(Date fechaPresentacion, BigDecimal importeSaldoF,
            String numeroControl, String numeroOperacion) throws DycServiceExcepcion;

    Integer insertarCuentaBancoArchivo(String numControl) throws DycServiceExcepcion;

    void insertarCuentaBanco(String clabe, String numeroControl, Date fechaPresentacion, Integer idArchivo) throws DycServiceExcepcion;

    void insertarResolucion(String numeroControl,
            Date fechaPresentacion,
            Date fechaAprobacionMorsa,
            BigDecimal importeSaldoF,
            EDycAutomaticasIvaEstadoCasoDevolucion estadoCasoDevolucion,
            BigDecimal importeAutorizado) throws DycServiceExcepcion;
    
    void insertarResolucionAut(String numeroControl,
            Date fechaPresentacion,
            Date fechaAprobacionMorsa,
            BigDecimal importeSaldoF,
            EDycAutomaticasIvaEstadoCasoDevolucion estadoCasoDevolucion,
            BigDecimal importeAutorizado,
            BigDecimal importeNeto) throws DycServiceExcepcion;
}
