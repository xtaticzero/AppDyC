/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.dao.acuserecibo;

import java.text.ParseException;

import java.util.Map;

import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Alfredo Ramirez
 * @since 20/09/2013
 */
public interface AcuseReciboDAO {

    String CLAVE_RFC = "claveRfc";
    String CONCEPTO = "concepto";
    String ID_CONCEPTO = "idConcepto";
    String EJERCICIO = "ejercicio";
    String IMPORTE_SOLICITADO = "importeSolicitado";
    String ID_IMPUESTO = "idImpuesto";
    String IMPUESTO = "impuesto";
    String TIPO_SALDO = "tipoSaldo";
    String CLAVE_ADM = "claveAdm";
    String FECHA_INICIO_TRAMITE = "fechaInicioTramite";
    String FECHA = "fecha";
    String NUMERO_FOLIO = "numeroFolio";
    String ADMIN_LOCAL = "adminLocal";
    String ORIGEN_DEVOLUCUION = "origenDevolucion";
    String TIPO_TRAMITE = "tipoTramite";
    String TIPO_PERIODICIDAD = "tipoPeriocidad";
    String NUMERO_OPERACION = "numOperacion";
    String NUMERO_OPERACION_DEV = "numOperacionDev";
    String NUMERO_DOCUMENTO = "numDocumento";
    String MANIFIESTA = "manifiesta";
    String LBL_PROTESTA = "lblProtesta";
    String PROTESTA = "protesta";
    String LBL_NOTIFICACION = "lblNotificacion";
    String INFO_AGROPECUARIO = "infoagropecuario";
    String NOTIFICACION_1 = "notificacion1";
    String APLICA_FACILIDAD = "aplicafacilidad";
    String NOTIFICACION_2 = "notificacion2";
    String DECLARACION_NORMAL = "Normal";
    String DECLARACION_COMPLEMENTARIA = "Complementaria";
    String TIPO_DECLARACION = "tipoDeclaracion";
    String FECHA_CAUSACION = "fechaCausacion";
    String FECHA_PRESENTACION_DEV = "fechaPresentacionDev";
    String FECHA_PRESENTACION = "fechaPresentacion";
    String LBL_DOC_OPE = "lblDocOpe";
    String LBL_NUM_OPERACION = "Número de Operación:";
    String TXT_DOCOPE = "txtDocOpe";
    String LBL_NUM_DOCUMENTO = "Número de Documento:";
    String LBL_REGLA = "lblRegla";
    String SALDO_A_FAVOR = "saldoFavor";
    String IMPORE_DEVOLUCION = "importeDevolucion";
    String IMPORTE_ACREDITACION = "importeAcreditacion";
    String SEGUNDA_DECLARACION = "segundaDeclaracion";
    String ANEXO_SOLICITUD = "anexoSolicitud";
    String DOCUMENTO_SOLICITUD = "documentoSolicitud";
    String ID_PERIODO = "idPeriodo";
    String PERIODO = "periodo";
    String CADENA_ORIGINAL = "cadenaOriginal";
    String SELLO_ORIGINAL = "selloDigital";
    String NUM_CONTROL = "numControl";
    String RUTA_IMAGEN = "RUTA_IMAGEN";
    String TIPOPERSONA = "TIPOPERSONA";
    String DENOMINACION = "nombreDenominacion";
    String NOMBRE_CONTRIBUYENTE = "nombreCompleto";
    String LBL_2DO_REQ = "lbl2doReq";
    String FOLIO = "folio";
    String NUMCONTROLDOC = "numControlDoc";

    Map<String, Object> consultarSolicitud(String rfc, String numControl, int servicio,
            Integer tipos, boolean isReimpresion) throws ParseException;

    Map<String, Object> queryRequest(String numControl) throws ParseException;

    ConsultarDevolucionesContribuyenteDTO solicitudPorNumControl(String numControl, String rfc);

    Map<String, Object> getDatosAcuseReq(final String numDocumento, final String rfcContribuyente, boolean consideraRfc) throws SIATException;

    Map<String, Object> getDatosAcuseReq(String numDocumento) throws SIATException;

    Map<String, Object> consultarDatosAcuseSolventacion(String rfc, String numControl, int servicio,
            Integer tipos, boolean consideraRfc, String numControlDoc) throws ParseException;

}
