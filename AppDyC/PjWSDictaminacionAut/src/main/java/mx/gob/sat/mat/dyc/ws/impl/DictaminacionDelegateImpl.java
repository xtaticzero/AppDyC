package mx.gob.sat.mat.dyc.ws.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.MotivoRiesgoService;
import mx.gob.sat.mat.dyc.devolucionaut.service.DyccModeloService;
import mx.gob.sat.mat.dyc.devolucionaut.service.DyctDictAutomaticaService;
import mx.gob.sat.mat.dyc.ws.DictaminacionDelegate;
import mx.gob.sat.mat.dyc.ws.domain.Dictaminacion;
import mx.gob.sat.mat.dyc.ws.domain.Notificacion;
import mx.gob.sat.mat.dyc.ws.domain.ServiceFault;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.siat.dyc.domain.devolucionaut.constante.MarcResultado;
import mx.gob.sat.mat.dyc.ws.util.constante.TipoExcepcion;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccModelo;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMotivoRiesgoDTO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteInfoAdicional;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
@Component("dictaminacionDelegate")
public class DictaminacionDelegateImpl implements DictaminacionDelegate {

    private static final Logger LOGGER = Logger.getLogger(DictaminacionDelegateImpl.class);
    private static final int ID_GRUPO_OPERACION = 101;
    private static final int ID_MENSAJE = 1;
    private static final int ID_TIPO = 3;
    private static final int ID_MOVIMIENTO = 490;

    @Autowired
    private DyctDictAutomaticaService dictAutomaticaService;

    @Autowired
    private MotivoRiesgoService motivoRiesgoService;

    @Autowired
    private DyccModeloService dyccModeloService;

    @Autowired
    private RegistroPistasAuditoria registroPistasAuditoria;

    @Autowired
    private DyccMensajeUsrService dyccMensajeUsrService;

    @Override
    public Notificacion procesar(Dictaminacion dictaminacion, WebServiceContext wsContext) {

        Notificacion notificacion;
        try {
            validarException(wsContext);
            DyctDictAutomaticaDTO automaticaDTO = parsearDTO(dictaminacion);
            validarMarcResultado(automaticaDTO);

            dictAutomaticaService.insertarDictAutomatica(automaticaDTO);
            notificacion = new Notificacion("SUCCESS", "0", "TRANSACCION EXITOSA");

            guardarPistaAuditoria(dictaminacion, wsContext);
        } catch (ServiceException e) {
            notificacion = new Notificacion("ERROR", e.getFaultInfo().getFaultCode(), e.getFaultInfo().getFaultString());
            LOGGER.error("ERROR EN EL XML REQUEST:: " + e.getMessage());

        } catch (SIATException e) {
            TipoExcepcion tipoExcepcion = TipoExcepcion.CONNECTION;
            boolean primaryKey = e.getCause().getMessage().toUpperCase().contains("ORA-00001");
            boolean forentKey = e.getCause().getMessage().toUpperCase().contains("ORA-02291");

            if (primaryKey || forentKey) {
                tipoExcepcion = primaryKey ? TipoExcepcion.INTEGRITY_PK : TipoExcepcion.INTEGRITY_FK;
            }
            LOGGER.error("ERROR GUARDAR SOLICITUD:: " + dictaminacion.getNumControl() + " " + tipoExcepcion.getDescripcion());
            notificacion = new Notificacion("ERROR", tipoExcepcion.getCodigo(), tipoExcepcion.getDescripcion());

        }
        return notificacion;
    }

    private void validarException(WebServiceContext wsContext) throws ServiceException {
        try {
            HttpSession httpSession = ((HttpServletRequest) wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST)).getSession();
            ServiceException serviceException = (ServiceException) httpSession.getAttribute("ServiceException");
            if (serviceException != null) {
                throw serviceException;
            }

        } catch (IllegalStateException ex) {
            LOGGER.error("IllegalStateException:: " + ex.getMessage());
        } catch (ClassCastException ex) {
            LOGGER.error("ClassCastException:: " + ex.getMessage());
        }
    }

    private DyctDictAutomaticaDTO parsearDTO(Dictaminacion dictaminacion) throws ServiceException {

        DyccModelo dyccModelo = null;
        try {
            dyccModelo = dyccModeloService.consultarPorDescripcion(dictaminacion.getModelo().trim().toUpperCase());
        } catch (SIATException ex) {
            LOGGER.error("ERROR AL CONSULTAR EL MODELO:: " + ex.getMessage());
            ServiceFault serviceFault = new ServiceFault(TipoExcepcion.CONNECTION.getCodigo(), TipoExcepcion.CONNECTION.getDescripcion());
            throw new ServiceException(serviceFault.getFaultString(), serviceFault, ex);
        }

        DyctDictAutomaticaDTO automaticaDTO = new DyctDictAutomaticaDTO();
        automaticaDTO.setNumControl(dictaminacion.getNumControl().trim().toUpperCase());
        automaticaDTO.setNumLote(dictaminacion.getNumLote());
        automaticaDTO.setRfc(dictaminacion.getRfc().trim().toUpperCase());
        automaticaDTO.setImpuesto(dictaminacion.getImpuesto());
        automaticaDTO.setConcepto(dictaminacion.getConcepto());
        automaticaDTO.setNumOperacion(dictaminacion.getNumOperacion());
        automaticaDTO.setImporteSaldoF(dictaminacion.getImporteSaldoF());
        automaticaDTO.setFechaProcModelo(dictaminacion.getFechaProcModelo().toGregorianCalendar().getTime());
        automaticaDTO.setFechaRegistro(new Date());
        automaticaDTO.setIdModelo(dyccModelo.getIdModelo());
        automaticaDTO.setIdMarcResultado(dictaminacion.getMarcResultado());
        automaticaDTO.setMotRiesgo(dictaminacion.getMotRiesgo());
        automaticaDTO.setFechaProceso(null);

        return automaticaDTO;
    }

    private void validarMarcResultado(DyctDictAutomaticaDTO automaticaDTO) throws ServiceException {

        if (automaticaDTO.getIdMarcResultado().equals(MarcResultado.CON_RIESGO.getCodigo())) {

            DycMotivoRiesgoDTO dycMotivoRiesgoDTO = new DycMotivoRiesgoDTO();
            dycMotivoRiesgoDTO.setCodigo(automaticaDTO.getMotRiesgo());
            dycMotivoRiesgoDTO.setModelo(automaticaDTO.getIdModelo().toString());

            DycMotivoRiesgoDTO motivoRiesgoDTO = null;
            try {
                List<DycMotivoRiesgoDTO> riesgoDTOs = motivoRiesgoService.consultarFiltro(dycMotivoRiesgoDTO);
                motivoRiesgoDTO = (riesgoDTOs != null && !riesgoDTOs.isEmpty()) ? riesgoDTOs.get(0) : null;
            } catch (SIATException ex) {
                LOGGER.error("ERROR EN LA CONSULTA MOTIVO DE RIESGO:: " + ex.getMessage());
                ServiceFault serviceFault = new ServiceFault(TipoExcepcion.CONNECTION.getCodigo(), TipoExcepcion.CONNECTION.getDescripcion());
                throw new ServiceException(serviceFault.getFaultString(), serviceFault, ex);
            }

            if (motivoRiesgoDTO == null || !motivoRiesgoDTO.getEstado().equalsIgnoreCase("Activo")) {
                throw new ServiceException(TipoExcepcion.INTEGRITY_RIESGO.getCodigo(), TipoExcepcion.INTEGRITY_RIESGO.getDescripcion());
            }

        }
    }

    private void guardarPistaAuditoria(Dictaminacion dictaminacion, WebServiceContext wsContext) {

        try {
            HttpSession httpSession = ((HttpServletRequest) wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST)).getSession();
            AccesoUsr accesoUsr = llenaSessionDummy();
            httpSession.setAttribute("accesoEF", accesoUsr);
            httpSession.setAttribute(ConstanteInfoAdicional.USUARIO, accesoUsr.getUsuario());
            httpSession.setAttribute("roles", accesoUsr.getRoles());
            httpSession.setAttribute("portal", "accesoEF");

            DyccMensajeUsrDTO mensajeUsr = dyccMensajeUsrService.obtieneMensaje(ID_MENSAJE, ID_GRUPO_OPERACION, ID_TIPO);
            String mensaje = mensajeUsr.getDescripcion().replace("<RFC>", dictaminacion.getRfc()).replace("<MARCRESULTADO>", dictaminacion.getMarcResultado()).replace("<Usuario>", accesoUsr.getNombres());

            SegbMovimientoDTO movimientoDTO = MovimientoUtil.addMovimiento(httpSession, ConstantesDyC1.CLAVE_SYS, Procesos.DYC00106, new Date(), new Date(), ID_MOVIMIENTO, mensaje);
            movimientoDTO.setIdentificador(dictaminacion.getNumControl());
            registroPistasAuditoria.registrarPistaAuditoria(movimientoDTO);
        } catch (AccesoDenegadoException ex) {
            LOGGER.error("AccesoDenegadoException:: " + ex.getMessage());
        } catch (SIATException ex) {
            LOGGER.error("ERROR AL REGISTRAR LA PISTA DE AUDITORIA:: " + ex.getMessage());
        } catch (IllegalStateException ex) {
            LOGGER.error("IllegalStateException:: " + ex.getMessage());
        } catch (ClassCastException ex) {
            LOGGER.error("ClassCastException:: " + ex.getMessage());
        }
    }

    private AccesoUsr llenaSessionDummy() {
        AccesoUsr accesoUsr = new AccesoUsr();
        Random ran = new Random();

        accesoUsr.setClaveAdminCentral("9000000000");
        accesoUsr.setLocalidad("5620010602");
        accesoUsr.setUsuario("RAHM580403ME3");
        accesoUsr.setMenu("1");
        accesoUsr.setTipoAuth("4");
        accesoUsr.setTipoAuth("2");

        accesoUsr.setClaveSir("13");
        accesoUsr.setRfcCorto("GOFC786O");
        accesoUsr.setNombreCompleto("WS SIVAD-MORSA DICTAMINACION AUTOMATICA ");
        accesoUsr.setClaveAdminGral("9000000000");

        accesoUsr.setSessionID("1404100" + ran.nextInt(ConstantesDyCNumerico.VALOR_9000000 + 1));
        accesoUsr.setSessionIDNovell("2B9238F207F94C272A325A40EA7952FF");
        accesoUsr.setNumeroEmp("53408");
        accesoUsr.setIdTipoPersona("00");
        accesoUsr.setIp("[lo (Software Loopback Interface 1)=/127.0.0.1][lo (Software Loopback Interface 1)=/0:0:0:0:0:0:0:1][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/99.91.8.68][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/fe80:0:0:0:8cb7:9e8f:c0ce:c501%11][net5 (Adaptador 6to4 de Microsoft)=/2002:635b:844:0:0:0:635b:844][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/10.57.232.16][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/fe80:0:0:0:799c:770b:4c10:6569%18][eth10 (VirtualBox Host-Only Ethernet Adapter)=/192.168.56.1][eth10 (VirtualBox Host-Only Ethernet Adapter)=/fe80:0:0:0:a8e5:c230:89e1:9d8b%22] ");
        accesoUsr.setLocalidadOp("900E040301");
        accesoUsr.setLocalidadCRM("G-96");
        accesoUsr.setNombres("WS SIVAD-MORSA");
        accesoUsr.setPrimerApellido("DICTAMINACION");
        accesoUsr.setRoles("SAT_NYV_ADM_GRL_AC,SAT_DYC_DICT,SAT_DYC_CONS_PISTA_COBR");
        accesoUsr.setSegundoApellido("AUTOMATICA");
        accesoUsr.setUsuarioOficina("ADMON GRAL GRANDES CONTRIB");

        accesoUsr.setCentroCosto("562");
        accesoUsr.setCentroCostoOp("562");
        accesoUsr.setCentroDatos("1");
        accesoUsr.setMac("[lo (Software Loopback Interface 1)=][eth3 (Intel(R) 82579LM Gigabit Network Connection)=90-B1-1C-7C-C0-E0][net5 (Adaptador 6to4 de Microsoft)=00-00-00-00-00-00-00-E0][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=00-05-9A-3C-78-00][eth10 (VirtualBox Host-Only Ethernet Adapter)=08-00-27-00-44-9E] ");

        Map<Integer, String> proceso = new HashMap<Integer, String>();
        proceso.put(ConstantesDyCNumerico.VALOR_136, Procesos.DYC00002);
        proceso.put(ConstantesDyCNumerico.VALOR_137, Procesos.DYC00003);
        proceso.put(ConstantesDyCNumerico.VALOR_130, Procesos.DYC00005);
        proceso.put(ConstantesDyCNumerico.VALOR_132, Procesos.DYC00006);
        proceso.put(ConstantesDyCNumerico.VALOR_722, Procesos.DYC00011);
        proceso.put(ConstantesDyCNumerico.VALOR_116, Procesos.DYC00106);

        accesoUsr.setProcesos(proceso);

        return accesoUsr;
    }
}
