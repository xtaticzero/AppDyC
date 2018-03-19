/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
 */
package mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.impl;

import java.io.InputStreamReader;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import mx.gob.sat.siat.dyc.avisocomp.service.AvisoCompensacionService;
import mx.gob.sat.siat.dyc.avisocomp.util.ValidadorAvisoCompensacion;
import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosDestinoAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosOrigenAcuseVO;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.generico.util.Reporte;
import mx.gob.sat.siat.dyc.gestionsol.dao.acuserecibo.AcuseReciboDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.AcuseReciboService;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ConsultarExpedienteService;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajes;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;


import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alfredo Ramirez
 * @since 20/09/2013
 *
 */
@Service(value = "acuseReciboService")
public class AcuseReciboServiceImpl implements AcuseReciboService {

    private static final Logger LOGGER = Logger.getLogger(AcuseReciboServiceImpl.class);
    private static final String NOTIFICACION = "notificacion";
    @Autowired
    private AcuseReciboDAO acuseReciboDAO;
    @Autowired
    private DyccMensajeUsrService consultarDyccMensajeUsrService;
    @Autowired
    private ConsultarExpedienteService consultarExpedienteService;
    @Autowired
    private AvisoCompensacionService avisoCompensacionService;
    @Autowired
    private ValidadorAvisoCompensacion validadorAvisoCompensacion;
    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;
    @Autowired
    private IDycpServicioDAO dycpServicioDAO;
    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    private static final String NUM_CONTROL = "numControl";
    private static final String NUMERO_FOLIO = "numeroFolio";
    public static final String CONTESTAR_REQUERIMIENTO = "ContestarRequerimiento_";
    public static final String NUMERO_CONTROL_DOC = "numControlDoc";

    @Override
    public ConsultarDevolucionesContribuyenteDTO solicitudPorNumControl(String numControl, String rfc) {
        ConsultarDevolucionesContribuyenteDTO solicitud = new ConsultarDevolucionesContribuyenteDTO();
        try {
            solicitud = acuseReciboDAO.solicitudPorNumControl(numControl, rfc);
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return solicitud;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void generarAcuseRecibo(Integer tipos, String numControl, String tfc, boolean isAdm, boolean isReimpresion) throws SIATException {
        DyctContribuyenteDTO contribuyenteDto = consultarExpedienteService.buscarNumcontrol(numControl);
        JAXBContext jaxbContext;
        Unmarshaller jaxbUnmarshaller;
        Map<String, Object> hm;

        if (null != contribuyenteDto) {
            try {
                jaxbContext = JAXBContext.newInstance(PersonaDTO.class);
                jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                InputStreamReader datosContribuyente = new InputStreamReader(contribuyenteDto.getDatosContribuyente(), ConstantesDyC1.CODIFICACION_UTF8);
                PersonaDTO personaDTO = (PersonaDTO) jaxbUnmarshaller.unmarshal(datosContribuyente);

                if (tfc.equals(personaDTO.getRfcVigente()) || isAdm) {
                    PersonaIdentificacionDTO personaIdentificacionDto = personaDTO.getPersonaIdentificacion();
                    String tipo = personaIdentificacionDto.getTipoPersona();
                    hm = acuseReciboDAO.consultarSolicitud(personaDTO.getRfcVigente(), numControl, 1, tipos, isReimpresion);
                    String nombreContribuyente = obtieneNombreRazon(tipo, personaIdentificacionDto);

                    if (numControl.equals(hm.get(NUMERO_FOLIO))) {
                        hm.put("nombreDenominacion", nombreContribuyente);
                        getDatosAcuse(tipos, hm);
                    } else {
                        LOGGER.error("Error numero recibido-->" + numControl + "Numero consultado-->"
                                + hm.get(NUMERO_FOLIO));
                        throw new SIATException();
                    }
                } else {
                    muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_1);
                }
            } catch (Exception e) {
                throw new SIATException(e);
            }
        } else {
            muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_1);
        }
    }

    @Override
    public Map<String, Object> consultarSolicitud(String rfc, String numControl, int servicio,
            Integer tipos, boolean isReimpresion) throws ParseException {

        return acuseReciboDAO.consultarSolicitud(rfc, numControl, servicio, tipos, isReimpresion);

    }

    private void muestraMensaje(String numControl, Integer servicio) throws SIATException {

        if (servicio == ConstantesDyCNumerico.VALOR_1) {
            throw new SIATException(consultarDyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_1,
                    ConstantesDyCNumerico.VALOR_82).getDescripcion()
                    + numControl);
        } else {
            throw new SIATException(consultarDyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_2,
                    ConstantesDyCNumerico.VALOR_82).getDescripcion()
                    + numControl);
        }
    }

    private void getDatosAcuse(Integer tipo, Map<String, Object> hm) throws SIATException {
        Reporte reporteGenerico = new Reporte();
        hm.put("lblManifiesta",
                consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_53, ConstantesMensajes.CU_1).getDescripcion());
        if (hm.get("protesta").equals("1")) {
            hm.put("lblProtesta",
                    consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_51, ConstantesMensajes.CU_1).getDescripcion());
        } else if (hm.get("protesta").equals("0")) {
            hm.put("lblProtesta",
                    consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_52, ConstantesMensajes.CU_1).getDescripcion());
        }
        if (!hm.get("notificacion1").equals("")) {
            hm.put("lblNotificacion",
                    consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_55, ConstantesMensajes.CU_1).getDescripcion());
            if (hm.get("notificacion1").equals("1")) {
                hm.put(NOTIFICACION, "1");
            } else if (hm.get("notificacion1").equals("0")) {
                hm.put(NOTIFICACION, "0");
            }
        }
        if (!hm.get("notificacion2").equals("")) {
            hm.put("lblNotificacion",
                    consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_54, ConstantesMensajes.CU_1).getDescripcion());
            if (hm.get("notificacion2").equals("1")) {
                hm.put(NOTIFICACION, "1");

            } else if (hm.get("notificacion2").equals("0")) {
                hm.put(NOTIFICACION, "0");
            }
        }
        hm.put("RUTA_IMAGEN", ConstantesDyCURL.URL_IMAGENES);
        if (tipo == ConstantesDyCNumerico.VALOR_1) {
            reporteGenerico.imprimirReporte(ConstantesDyCURL.REPORTE_REIMPRESION_ACUSE_RECIBO, hm,
                    "AcuseDyC_" + hm.get(NUM_CONTROL));
        } else {
            reporteGenerico.imprimirReporte(ConstantesDyCURL.REPORTE_REIMPRESION_ACUSE_CUENTA, hm,
                    "AcuseCuentaClabe_" + hm.get(NUM_CONTROL));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void mostrarReporteAvisos(String numControl, String rfc, boolean isAdm) throws SIATException {
        List<DatosDestinoAcuseVO> datosDestino = avisoCompensacionService.obtieneDestinoParaAcuse(numControl);
        Reporte reporteGenerico = new Reporte();
        List<AnexoVO> listaAnexos;
        List<ArchivoVO> listaArchivos;
        if (datosDestino.size() > 0) {
            if (isAdm || rfc.equals(datosDestino.get(ConstantesDyCNumerico.VALOR_0).getRfcContribuyente())
                    && datosDestino.get(ConstantesDyCNumerico.VALOR_0).getFolioAviso() != null) {
                listaAnexos = new ArrayList<AnexoVO>();
                listaArchivos = avisoCompensacionService.buscaArchivosPorFolioAviso(numControl);
                List<DatosOrigenAcuseVO> datosOrigen = avisoCompensacionService.obtieneOrigenParaAcuse(numControl);

                for (Iterator itera = datosOrigen.iterator(); itera.hasNext();) {
                    DatosOrigenAcuseVO origen = (DatosOrigenAcuseVO) itera.next();
                    List<AnexoVO> resultado
                            = avisoCompensacionService.buscarAnexosPorNumcontrol(origen.getNumControl());
                    listaAnexos.addAll(resultado);
                }

                Map<String, Object> aviso
                        = validadorAvisoCompensacion.datosParaAcuseAviso(datosDestino, listaAnexos, listaArchivos,
                                datosOrigen);

                try {
                    reporteGenerico.imprimirReporte(ConstantesDyCURL.REPORTE_ACUSE_REGISTRO_AVISO_EN_LINEA, aviso,
                            "Aviso".concat(numControl));
                } catch (Exception e) {
                    throw new SIATException(e);
                }
            } else {
                muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_3);
            }
        } else {
            muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_3);
        }
    }

    @Transactional(readOnly = true)
    public void mostrarReporteAvisosDownload(String numControl, String rfc, boolean isAdm) throws SIATException {
        List<DatosDestinoAcuseVO> datosDestino = avisoCompensacionService.obtieneDestinoParaAcuse(numControl);
        Reporte reporteGenerico = new Reporte();
        List<AnexoVO> listaAnexos;
        List<ArchivoVO> listaArchivos;
        if (datosDestino.size() > 0) {
            if (rfc.equals(datosDestino.get(ConstantesDyCNumerico.VALOR_0).getRfcContribuyente())
                    && datosDestino.get(ConstantesDyCNumerico.VALOR_0).getFolioAviso() != null) {
                listaAnexos = new ArrayList<AnexoVO>();
                listaArchivos = avisoCompensacionService.buscaArchivosPorFolioAviso(numControl);
                List<DatosOrigenAcuseVO> datosOrigen = avisoCompensacionService.obtieneOrigenParaAcuse(numControl);

                for (Iterator itera = datosOrigen.iterator(); itera.hasNext();) {
                    DatosOrigenAcuseVO origen = (DatosOrigenAcuseVO) itera.next();
                    List<AnexoVO> resultado
                            = avisoCompensacionService.buscarAnexosPorNumcontrol(origen.getNumControl());
                    listaAnexos.addAll(resultado);
                }

                Map<String, Object> aviso
                        = validadorAvisoCompensacion.datosParaAcuseAviso(datosDestino, listaAnexos, listaArchivos,
                                datosOrigen);

                try {
                    reporteGenerico.imprimirReporteDownload(ConstantesDyCURL.REPORTE_ACUSE_REGISTRO_AVISO_EN_LINEA, aviso,
                            "Aviso".concat(numControl));
                } catch (Exception e) {
                    throw new SIATException(e);
                }
            } else {
                muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_3);
            }
        } else {
            muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_3);
        }
    }

    @Transactional(readOnly = true)
    public void mostrarReporteAvisos(String numControl, String rfc) throws SIATException {
        List<DatosDestinoAcuseVO> datosDestino = avisoCompensacionService.obtieneDestinoParaAcuse(numControl);
        Reporte reporteGenerico = new Reporte();
        List<AnexoVO> listaAnexos;
        List<ArchivoVO> listaArchivos;
        if (datosDestino.size() > 0) {
            if (rfc.equals(datosDestino.get(ConstantesDyCNumerico.VALOR_0).getRfcContribuyente())
                    && datosDestino.get(ConstantesDyCNumerico.VALOR_0).getFolioAviso() != null) {
                listaAnexos = new ArrayList<AnexoVO>();
                listaArchivos = avisoCompensacionService.buscaArchivosPorFolioAviso(numControl);
                List<DatosOrigenAcuseVO> datosOrigen = avisoCompensacionService.obtieneOrigenParaAcuse(numControl);

                for (Iterator itera = datosOrigen.iterator(); itera.hasNext();) {
                    DatosOrigenAcuseVO origen = (DatosOrigenAcuseVO) itera.next();
                    List<AnexoVO> resultado
                            = avisoCompensacionService.buscarAnexosPorNumcontrol(origen.getNumControl());
                    listaAnexos.addAll(resultado);
                }

                Map<String, Object> aviso
                        = validadorAvisoCompensacion.datosParaAcuseAviso(datosDestino, listaAnexos, listaArchivos,
                                datosOrigen);

                try {
                    reporteGenerico.imprimirReporte(ConstantesDyCURL.REPORTE_ACUSE_REGISTRO_AVISO_EN_LINEA, aviso,
                            "Aviso".concat(numControl));
                } catch (Exception e) {
                    throw new SIATException(e);
                }
            } else {
                muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_3);
            }
        } else {
            muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_3);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void mostrarAcuseContestarRequerimiento(final String numDocumento) throws SIATException {
        Reporte reporteGenerico = new Reporte();
        Map<String, Object> hm;

        try {
            hm = acuseReciboDAO.getDatosAcuseReq(numDocumento);

            if (null != hm.get(NUMERO_FOLIO) && numDocumento.equals(hm.get(NUMERO_CONTROL_DOC))) {
                reporteGenerico.imprimirReporte(ConstantesDyCURL.ACUSE_REQ_SOLICITUD, hm, CONTESTAR_REQUERIMIENTO + hm.get(NUMERO_FOLIO));
            } else if (null != hm.get(NUM_CONTROL) && numDocumento.equals(hm.get(NUMERO_CONTROL_DOC))) {

                reporteGenerico.imprimirReporte(ConstantesDyCURL.ACUSE_REQ_AVISO_COMP, hm, CONTESTAR_REQUERIMIENTO + hm.get(NUM_CONTROL));
            } else {
                LOGGER.error("Parametro: " + numDocumento);
                LOGGER.error("Consulta: " + hm.get(NUMERO_CONTROL_DOC));
                throw new SIATException();
            }

        } catch (DataAccessException e) {
            throw new SIATException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void mostrarAcuseContestarRequerimiento(final String numDocumento, final String rfcContribuyente, boolean consideraRfc) throws SIATException {
        Reporte reporteGenerico = new Reporte();
        Map<String, Object> hm;

        try {
            hm = acuseReciboDAO.getDatosAcuseReq(numDocumento, rfcContribuyente, consideraRfc);

            if (null != hm.get(NUMERO_FOLIO) && numDocumento.equals(hm.get(NUMERO_CONTROL_DOC))) {
                reporteGenerico.imprimirReporte(ConstantesDyCURL.ACUSE_REQ_SOLICITUD, hm, CONTESTAR_REQUERIMIENTO + hm.get(NUMERO_FOLIO));
            } else if (null != hm.get(NUM_CONTROL) && numDocumento.equals(hm.get(NUMERO_CONTROL_DOC))) {

                reporteGenerico.imprimirReporte(ConstantesDyCURL.ACUSE_REQ_AVISO_COMP, hm, CONTESTAR_REQUERIMIENTO + hm.get(NUM_CONTROL));
            } else {
                LOGGER.error("Parametro: " + numDocumento);
                LOGGER.error("Consulta: " + hm.get(NUMERO_CONTROL_DOC));
                throw new SIATException();
            }

        } catch (DataAccessException e) {
            throw new SIATException(e);
        }
    }

    private String obtieneNombreRazon(String tipo, PersonaIdentificacionDTO personaIdentificacionDto) {
        if (tipo.equals("F")) {
            return personaIdentificacionDto.getNombre() + " " + personaIdentificacionDto.getApPaterno() + " "
                    + personaIdentificacionDto.getApMaterno();
        } else {
            return personaIdentificacionDto.getRazonSocial() + " " + personaIdentificacionDto.getTipoSociedad();
        }
    }

    @Override
    public List<DyctDocumentoDTO> consultaNumeroDoc(String numControl) throws SIATException {
        return dyctDocumentoDAO.consultaNumeroDoc(numControl);
    }

    @Override
    public String getRFCOwner(String numControl, String rfc) throws SIATException {
        if (!rfc.equals(dycpServicioDAO.getRFCOwner(numControl))) {
            throw new SIATException();
        }
        return null;
    }

    @Override
    public String getRFCOwnerXFolioAviso(String folioAviso, String rfc) throws SIATException {
        List<DycpCompensacionDTO> compensacion = daoCompensacion.buscaXFolioAviso(folioAviso);

        if (compensacion != null && !compensacion.isEmpty()) {
            for (DycpCompensacionDTO compensacionDTO : compensacion) {
                if (!rfc.equals(dycpServicioDAO.getRFCOwner(compensacionDTO.getDycpServicioDTO().getNumControl()))) {
                    throw new SIATException();
                }
            }
        } else {
            throw new SIATException();
        }

        return null;
    }

    @Override
    public void generarAcuseReciboReimpresion(Integer tipos, String numControl, String tfc, boolean isAdm, boolean consideraRfc, String numControlDoc) throws SIATException {
        DyctContribuyenteDTO contribuyenteDto = consultarExpedienteService.buscarNumcontrol(numControl);
        JAXBContext jaxbContext;
        Unmarshaller jaxbUnmarshaller;
        Map<String, Object> hm;

        if (null != contribuyenteDto) {
            try {
                jaxbContext = JAXBContext.newInstance(PersonaDTO.class);
                jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                InputStreamReader datosContribuyente = new InputStreamReader(contribuyenteDto.getDatosContribuyente(), ConstantesDyC1.CODIFICACION_UTF8);
                PersonaDTO personaDTO = (PersonaDTO) jaxbUnmarshaller.unmarshal(datosContribuyente);

                if (tfc.equals(personaDTO.getRfcVigente()) || isAdm) {
                    PersonaIdentificacionDTO personaIdentificacionDto = personaDTO.getPersonaIdentificacion();
                    String tipo = personaIdentificacionDto.getTipoPersona();
                    hm = acuseReciboDAO.consultarDatosAcuseSolventacion(personaDTO.getRfcVigente(), numControl, 1, tipos, consideraRfc, numControlDoc);
                    String nombreContribuyente = obtieneNombreRazon(tipo, personaIdentificacionDto);

                    if (numControl.equals(hm.get(NUMERO_FOLIO))) {
                        hm.put("nombreDenominacion", nombreContribuyente);
                        getDatosAcuse(tipos, hm);
                    } else {
                        LOGGER.error("Error numero recibido-->" + numControl + "Numero consultado-->"
                                + hm.get(NUMERO_FOLIO));
                        throw new SIATException();
                    }
                } else {
                    muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_1);
                }
            } catch (Exception e) {
                throw new SIATException(e);
            }
        } else {
            muestraMensaje(numControl, ConstantesDyCNumerico.VALOR_1);
        }
    }

}
