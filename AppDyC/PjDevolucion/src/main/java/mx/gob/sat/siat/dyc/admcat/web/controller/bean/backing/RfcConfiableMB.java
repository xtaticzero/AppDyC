package mx.gob.sat.siat.dyc.admcat.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.service.casosverificar.CasosAVerificarService;
import mx.gob.sat.siat.dyc.admcat.service.casosverificar.RfcConfiablesService;
import mx.gob.sat.siat.dyc.domain.rfc.DycbEstadoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DyccMotivoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DyccTipoAccionRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.generico.util.UtilsValidaSesion;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;


@ManagedBean(name = "rfcConfiableMB")
@SessionScoped
public class RfcConfiableMB {

    private static final Logger LOG = Logger.getLogger(RfcConfiableMB.class.getName());
    private static final String MSG = "msg";
    private static final String PRINCIPAL = "wgdPrincipal.hide();";

    @ManagedProperty(value = "#{rfcConfiablesService}")
    private RfcConfiablesService rfcConfiablesService;

    @ManagedProperty(value = "#{casosAVerificarService}")
    private CasosAVerificarService casosAVerificarService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private Mensaje mensaje = new Mensaje();

    private List<DycpRfcDTO> listaRfcConfiables;
    private DycpRfcDTO seleccionConfiable;

    private List<DycbEstadoRfcDTO> listaBitacoraRfcConfiables;
    private DycpRfcDTO insertarConfiable;

    private Boolean panelInsertar;
    private Boolean panelModificar;
    private Boolean panelBitacor;
    private Boolean restriccion;
    private AccesoUsr accesoUsr;

    @PostConstruct
    public void init() {
        listaRfcConfiables = new ArrayList<DycpRfcDTO>();
        seleccionConfiable = new DycpRfcDTO();
        insertarConfiable = new DycpRfcDTO();
        accesoUsr = serviceObtenerSesion.execute();
        UtilsValidaSesion.validarSesion(accesoUsr, Procesos.DYC00022);

        if (accesoUsr.getClaveSir().trim().matches("90|91|97")) {
            restriccion = Boolean.TRUE;
        } else {
            panelInsertar = Boolean.FALSE;
            panelModificar = Boolean.FALSE;
            panelBitacor = Boolean.FALSE;
            restriccion = Boolean.FALSE;
            mostrarRfcConfiables();
        }
    }

    public void mostrarRfcConfiables() {
        listaRfcConfiables =
                rfcConfiablesService.mostrarRfcConfiables(ConstantesDyCNumerico.VALOR_1, ConstantesDyCNumerico.VALOR_0,
                                                          ConstantesDyCNumerico.VALOR_1);
    }

    public void opcionesRealizar() {
        RequestContext request = RequestContext.getCurrentInstance();
        Map<String, String> parametros =
            FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (parametros.get("opcionConfi").equals("btn1ns3rt")) {
            panelInsertar = Boolean.TRUE;
            panelModificar = Boolean.FALSE;
            panelBitacor = Boolean.FALSE;
            request.execute("wgdPrincipal.show()");
        } else if (parametros.get("opcionConfi").equals("btnM0d1f1c4r")) {
            if (seleccionConfiable != null) {
                panelInsertar = Boolean.FALSE;
                panelModificar = Boolean.TRUE;
                panelBitacor = Boolean.FALSE;
                request.execute("wgdPrincipal.show()");
            } else {
                mensaje.addInfo(MSG, "Debe seleccionar un registro");
                request.update(MSG);
            }
        } else {
            if (seleccionConfiable != null) {
                listaBitacoraRfcConfiables = new ArrayList<DycbEstadoRfcDTO>();
                listaBitacoraRfcConfiables = rfcConfiablesService.listaBitacoraCOnfiables(seleccionConfiable.getRfc());
                panelInsertar = Boolean.FALSE;
                panelModificar = Boolean.FALSE;
                panelBitacor = Boolean.TRUE;
                request.execute("wgdPrincipal.show()");
            } else {
                mensaje.addInfo(MSG, "Debe seleccionar un registro");
                request.update(MSG);
            }
        }
    }

    public void buscaRfcEnAmpliado() {
        RequestContext request = RequestContext.getCurrentInstance();

        DycpRfcDTO rfcEncontrado = casosAVerificarService.encontrarRfc(insertarConfiable.getRfc().toUpperCase());
        if (rfcEncontrado != null && rfcEncontrado.getPadronConfiable() == ConstantesDyCNumerico.VALOR_1) {
            mensaje.addInfo(MSG, "Ya existe el RFC ingresado.");
            request.execute(PRINCIPAL);
            request.update("pnlPrincipalRFCNo:msg");
        } else {
            String nombre = casosAVerificarService.buscaRfcEnAMpliado(insertarConfiable.getRfc());
            if (nombre != null) {
                insertarConfiable.setNombreCompleto(nombre);
            } else {
                mensaje.addInfo(MSG, "No existe el RFC, favor de volverlo a intentar.");
                reset();
                request.execute(PRINCIPAL);
                request.update("pnlPrincipalRFCNo:msg");
            }
        }
    }

    public void insertar() {
        RequestContext request = RequestContext.getCurrentInstance();
        try {
            DycbEstadoRfcDTO dycbEstadoRfcDTO = new DycbEstadoRfcDTO();
            insertarConfiable.setEsConfiable(ConstantesDyCNumerico.VALOR_1);
            insertarConfiable.setEsNoConfiable(ConstantesDyCNumerico.VALOR_0);
            insertarConfiable.setPadronConfiable(ConstantesDyCNumerico.VALOR_1);
            insertarConfiable.setPadronNoConfiable(ConstantesDyCNumerico.VALOR_0);
            insertarConfiable.setRfc(insertarConfiable.getRfc().toUpperCase());
            dycbEstadoRfcDTO.setDycpRfcDTO(insertarConfiable);
            DyccMotivoRfcDTO dyccMotivoRfcDTO = new DyccMotivoRfcDTO();
            dyccMotivoRfcDTO.setIdMotivoRfc(null);
            dycbEstadoRfcDTO.setDyccMotivoRfcDTO(dyccMotivoRfcDTO);
            DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO = new DyccTipoAccionRfcDTO();
            dyccTipoAccionRfcDTO.setIdTipoAccionRfc(ConstantesDyCNumerico.VALOR_1);
            dycbEstadoRfcDTO.setDyccTipoAccionRfcDTO(dyccTipoAccionRfcDTO);
            dycbEstadoRfcDTO.setUsuarioResp(accesoUsr.getUsuario());


            rfcConfiablesService.insertarConfiable(dycbEstadoRfcDTO);
            mostrarRfcConfiables();

            mensaje.addInfo(MSG, "Se registró correctamente el RFC: " + insertarConfiable.getRfc());
            request.update(MSG);
            reset();
        } catch (SIATException e) {
            LOG.error("Error al registrar RFCNoConfiable --> " + e.getMessage());
            mensaje.addInfo(MSG, "Ocurrio un error al registrar el RFC: " + insertarConfiable.getRfc());
            reset();
        }
        request.execute(PRINCIPAL);

    }

    public void modificar() {
        RequestContext request = RequestContext.getCurrentInstance();
        try {
            DycbEstadoRfcDTO dycbEstadoRfcDTO = new DycbEstadoRfcDTO();
            DyccMotivoRfcDTO motivo = new DyccMotivoRfcDTO();
            DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO = new DyccTipoAccionRfcDTO();
            if (seleccionConfiable.getEsConfiable() != 0) {
                dyccTipoAccionRfcDTO.setIdTipoAccionRfc(ConstantesDyCNumerico.VALOR_1);
                dycbEstadoRfcDTO.setDyccTipoAccionRfcDTO(dyccTipoAccionRfcDTO);
            } else {
                dyccTipoAccionRfcDTO.setIdTipoAccionRfc(ConstantesDyCNumerico.VALOR_2);
                dycbEstadoRfcDTO.setDyccTipoAccionRfcDTO(dyccTipoAccionRfcDTO);
            }

            dycbEstadoRfcDTO.setDycpRfcDTO(seleccionConfiable);
            motivo.setIdMotivoRfc(null);
            dycbEstadoRfcDTO.setDyccMotivoRfcDTO(motivo);
            dycbEstadoRfcDTO.setObservaciones("");

            dycbEstadoRfcDTO.setUsuarioResp(accesoUsr.getUsuario());

            rfcConfiablesService.modificarRfcConfiable(dycbEstadoRfcDTO);
            mostrarRfcConfiables();

            mensaje.addInfo(MSG, "Se modificó correctamente el RFC: " + seleccionConfiable.getRfc());
            request.update(MSG);
            reset();
        } catch (SIATException e) {
            LOG.error("Error al modificar RFCNoConfiable --> " + e.getMessage());
            mensaje.addInfo(MSG, "Ocurrio un error al modificar el RFC: " + seleccionConfiable.getRfc());
            reset();
        }
        request.execute(PRINCIPAL);
    }

    public void reset() {
        init();
    }

    public void setRfcConfiablesService(RfcConfiablesService rfcConfiablesService) {
        this.rfcConfiablesService = rfcConfiablesService;
    }

    public RfcConfiablesService getRfcConfiablesService() {
        return rfcConfiablesService;
    }

    public void setSeleccionConfiable(DycpRfcDTO seleccionConfiable) {
        this.seleccionConfiable = seleccionConfiable;
    }

    public DycpRfcDTO getSeleccionConfiable() {
        return seleccionConfiable;
    }

    public void setListaRfcConfiables(List<DycpRfcDTO> listaRfcConfiables) {
        this.listaRfcConfiables = listaRfcConfiables;
    }

    public List<DycpRfcDTO> getListaRfcConfiables() {
        return listaRfcConfiables;
    }

    public void setInsertarConfiable(DycpRfcDTO insertarConfiable) {
        this.insertarConfiable = insertarConfiable;
    }

    public DycpRfcDTO getInsertarConfiable() {
        return insertarConfiable;
    }

    public void setListaBitacoraRfcConfiables(List<DycbEstadoRfcDTO> listaBitacoraRfcConfiables) {
        this.listaBitacoraRfcConfiables = listaBitacoraRfcConfiables;
    }

    public List<DycbEstadoRfcDTO> getListaBitacoraRfcConfiables() {
        return listaBitacoraRfcConfiables;
    }

    public void setPanelInsertar(Boolean panelInsertar) {
        this.panelInsertar = panelInsertar;
    }

    public Boolean getPanelInsertar() {
        return panelInsertar;
    }

    public void setPanelModificar(Boolean panelModificar) {
        this.panelModificar = panelModificar;
    }

    public Boolean getPanelModificar() {
        return panelModificar;
    }

    public void setPanelBitacor(Boolean panelBitacor) {
        this.panelBitacor = panelBitacor;
    }

    public Boolean getPanelBitacor() {
        return panelBitacor;
    }

    public void setCasosAVerificarService(CasosAVerificarService casosAVerificarService) {
        this.casosAVerificarService = casosAVerificarService;
    }

    public CasosAVerificarService getCasosAVerificarService() {
        return casosAVerificarService;
    }

    public void setRestriccion(Boolean restriccion) {
        this.restriccion = restriccion;
    }

    public Boolean getRestriccion() {
        return restriccion;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
