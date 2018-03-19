package mx.gob.sat.siat.dyc.admcat.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.service.casosverificar.CasosAVerificarService;
import mx.gob.sat.siat.dyc.adminprocesos.util.constante.Constantes;
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


@ManagedBean(name = "rfcNoConfiable")
@ViewScoped
public class RfcNoConfiableMB {

    @ManagedProperty(value = "#{casosAVerificarService}")
    private CasosAVerificarService casosAVerificarService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private Mensaje mensaje = new Mensaje();

    private List<DycbEstadoRfcDTO> obtenerRFCNoConfiable;
    private List<DycbEstadoRfcDTO> bitacora;
    private DycbEstadoRfcDTO seleccionaRfcNoConfiable;

    private List<DyccMotivoRfcDTO> obtenerMotivos;

    private DyccMotivoRfcDTO dyccMotivoRfcDTO;
    private DycpRfcDTO insertarRfcNoConfiable;
    private DycbEstadoRfcDTO dycbEstadoRfcDTO;

    private Boolean mostrarPanelInsertar;
    private Boolean mostrarPanelModificar;
    private Boolean mostrarPanelBitacora;
    private Boolean restriccion;
    private AccesoUsr accesoUsr;

    private static final Logger LOG = Logger.getLogger(RfcNoConfiableMB.class.getName());
    private static final String MSG = "msg";
    private static final String PRINCIPAL = "wgdPrincipal.hide();";
    private static final int RFC_MORAL_POSICIONES = 13;
    private static final int RFC_FISICA_POSICIONES = 12;

    private String trfc;

    @PostConstruct
    public void init() {
        obtenerRFCNoConfiable = new ArrayList<DycbEstadoRfcDTO>();
        obtenerMotivos = new ArrayList<DyccMotivoRfcDTO>();
        dyccMotivoRfcDTO = new DyccMotivoRfcDTO();
        dycbEstadoRfcDTO = new DycbEstadoRfcDTO();
        insertarRfcNoConfiable = new DycpRfcDTO();
        seleccionaRfcNoConfiable = new DycbEstadoRfcDTO();
        accesoUsr = serviceObtenerSesion.execute();
        UtilsValidaSesion.validarSesion(accesoUsr, Procesos.DYC00021);

        if (accesoUsr.getClaveSir().trim().matches("90|91|97")) {
            restriccion = Boolean.TRUE;
        } else {
            mostrarPanelInsertar = Boolean.FALSE;
            mostrarPanelModificar = Boolean.FALSE;
            mostrarPanelBitacora = Boolean.FALSE;
            restriccion = Boolean.FALSE;
        }

    }

    public void consultarRfcAction() {
        if (trfc.length() < RFC_FISICA_POSICIONES || trfc.length() > RFC_MORAL_POSICIONES) {
            mensaje.addError(MSG, "Debe capturar un RFC a 12 o 13 posiciones");
        } else {
            obtenerRFCNoConf(trfc);
        }
    }

    public void borraRfc() {
        trfc = Constantes.EMPTY;
    }

    public void obtenerRFCNoConf(String rfc) {
        obtenerRFCNoConfiable = casosAVerificarService.mostrarRfcNoConfiables(rfc,
                ConstantesDyCNumerico.VALOR_1, ConstantesDyCNumerico.VALOR_0,
                ConstantesDyCNumerico.VALOR_1);
    }

    public void opcionAccion() {
        RequestContext request = RequestContext.getCurrentInstance();
        Map<String, String> parametro = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();
        String opcion = parametro.get("opcionConfi");
        if (opcion.equals("btn1ns3rt")) {
            //Se settean los valores por default para que no muestre los valores de la inserción anterior
            insertarRfcNoConfiable.setRfc(Constantes.EMPTY);
            insertarRfcNoConfiable.setNombreCompleto(Constantes.EMPTY);
            dyccMotivoRfcDTO.setIdMotivoRfc(0);
            
            mostrarPanelInsertar = Boolean.TRUE;
            mostrarPanelModificar = Boolean.FALSE;
            mostrarPanelBitacora = Boolean.FALSE;
            obtenerMotivos = casosAVerificarService.mostrarMotivos(ConstantesDyCNumerico.VALOR_3);
            request.execute("wgdPrincipal.show();");
        } else if (opcion.equals("btnM0d1f1c4r")) {
            if (seleccionaRfcNoConfiable != null) {
                mostrarPanelInsertar = Boolean.FALSE;
                mostrarPanelModificar = Boolean.TRUE;
                mostrarPanelBitacora = Boolean.FALSE;
                obtenerMotivos = casosAVerificarService.mostrarMotivos(seleccionaRfcNoConfiable
                        .getDyccTipoAccionRfcDTO().getIdTipoAccionRfc());
                request.execute("wgdPrincipal.show();");
                obtenerRFCNoConf(seleccionaRfcNoConfiable.getDycpRfcDTO().getRfc());
            } else {
                mensaje.addInfo(MSG, "Debe seleccionar un registro.");
                request.update(MSG);
            }
        } else {
            if (seleccionaRfcNoConfiable != null) {
                bitacora = new ArrayList<DycbEstadoRfcDTO>();
                mostrarPanelInsertar = Boolean.FALSE;
                mostrarPanelModificar = Boolean.FALSE;
                mostrarPanelBitacora = Boolean.TRUE;
                bitacora = casosAVerificarService.bitacoraXRFC(seleccionaRfcNoConfiable
                        .getDycpRfcDTO().getRfc());
                request.execute("wgdPrincipal.show();");
            } else {
                mensaje.addInfo(MSG, "Debe seleccionar un registro");
                request.update(MSG);
            }
        }
    }

    public void opcionMotivos() {
        if (seleccionaRfcNoConfiable.getDycpRfcDTO().getEsNoConfiable() == 1) {
            obtenerMotivos = casosAVerificarService.mostrarMotivos(ConstantesDyCNumerico.VALOR_3);
            Integer valorActivo = seleccionaRfcNoConfiable.getDyccMotivoRfcDTO().getIdMotivoRfc()
                    - obtenerMotivos.size();
            for (int i = 0; i < obtenerMotivos.size(); i++) {
                if (obtenerMotivos.get(i).getIdMotivoRfc().equals(valorActivo)) {
                    seleccionaRfcNoConfiable.setDyccMotivoRfcDTO(obtenerMotivos.get(i));
                    break;
                }
            }
        } else {
            obtenerMotivos = casosAVerificarService.mostrarMotivos(ConstantesDyCNumerico.VALOR_4);
            Integer valorNegativo = seleccionaRfcNoConfiable.getDyccMotivoRfcDTO().getIdMotivoRfc()
                    + obtenerMotivos.size();
            for (int i = 0; i < obtenerMotivos.size(); i++) {
                if (obtenerMotivos.get(i).getIdMotivoRfc().equals(valorNegativo)) {
                    seleccionaRfcNoConfiable.setDyccMotivoRfcDTO(obtenerMotivos.get(i));
                    break;
                }
            }
        }
    }

    public void buscaRfcEnAmpliado() {
        RequestContext request = RequestContext.getCurrentInstance();

        DycpRfcDTO rfcEncontrado = casosAVerificarService.encontrarRfc(insertarRfcNoConfiable
                .getRfc());
        if (rfcEncontrado != null
                && rfcEncontrado.getEsNoConfiable() == ConstantesDyCNumerico.VALOR_1) {
            mensaje.addInfo(MSG, "Ya existe el RFC ingresado.");
            request.update("pnlPrincipalRFCNo:msg");
        } else {
            String nombre = casosAVerificarService.buscaRfcEnAMpliado(insertarRfcNoConfiable
                    .getRfc());
            if (nombre != null) {
                insertarRfcNoConfiable.setNombreCompleto(nombre);
            } else {
                mensaje.addInfo(MSG, "No existe el RFC, favor de volverlo a intentar.");
                request.execute(PRINCIPAL);
                request.update("pnlPrincipalRFCNo:msg");
            }
        }
    }

    public void insertar() {
        RequestContext request = RequestContext.getCurrentInstance();
        try {
            insertarRfcNoConfiable.setEsConfiable(ConstantesDyCNumerico.VALOR_0);
            insertarRfcNoConfiable.setEsNoConfiable(ConstantesDyCNumerico.VALOR_1);
            insertarRfcNoConfiable.setPadronConfiable(ConstantesDyCNumerico.VALOR_0);
            insertarRfcNoConfiable.setPadronNoConfiable(ConstantesDyCNumerico.VALOR_1);
            insertarRfcNoConfiable.setRfc(insertarRfcNoConfiable.getRfc().toUpperCase());
            dycbEstadoRfcDTO.setDycpRfcDTO(insertarRfcNoConfiable);
            dycbEstadoRfcDTO.setDyccMotivoRfcDTO(dyccMotivoRfcDTO);
            DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO = new DyccTipoAccionRfcDTO();
            dyccTipoAccionRfcDTO.setIdTipoAccionRfc(ConstantesDyCNumerico.VALOR_3);
            dycbEstadoRfcDTO.setDyccTipoAccionRfcDTO(dyccTipoAccionRfcDTO);
            dycbEstadoRfcDTO.setUsuarioResp(accesoUsr.getUsuario());

            if (casosAVerificarService.insertarRfcNoConfiable(dycbEstadoRfcDTO)){
                mensaje.addInfo(MSG,
                    "Se registró correctamente el RFC: " + insertarRfcNoConfiable.getRfc());     
                }
            else{
                mensaje.addInfo(MSG,
                    "El RFC: " + insertarRfcNoConfiable.getRfc() + " ya se encuentra dado de alta.");     
                }
            obtenerRFCNoConf(insertarRfcNoConfiable.getRfc().toUpperCase());
            request.update(MSG);
        } catch (SIATException e) {
            LOG.error("Error al registrar RFCNoConfiable --> " + e.getMessage());
            mensaje.addInfo(MSG,
                    "El RFC: " + insertarRfcNoConfiable.getRfc() + " no existe, favor de verificarlo.");
        }
        request.execute(PRINCIPAL);
    }

    public void modificar() {
        RequestContext request = RequestContext.getCurrentInstance();
        try {
            DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO = new DyccTipoAccionRfcDTO();
            if (seleccionaRfcNoConfiable.getDycpRfcDTO().getEsNoConfiable() != 0) {
                dyccTipoAccionRfcDTO.setIdTipoAccionRfc(ConstantesDyCNumerico.VALOR_3);
                seleccionaRfcNoConfiable.setDyccTipoAccionRfcDTO(dyccTipoAccionRfcDTO);
            } else {
                dyccTipoAccionRfcDTO.setIdTipoAccionRfc(ConstantesDyCNumerico.VALOR_4);
                seleccionaRfcNoConfiable.setDyccTipoAccionRfcDTO(dyccTipoAccionRfcDTO);
            }

            if (seleccionaRfcNoConfiable.getDyccTipoAccionRfcDTO().getIdTipoAccionRfc() == ConstantesDyCNumerico.VALOR_3) {
                if (seleccionaRfcNoConfiable.getDyccMotivoRfcDTO().getIdMotivoRfc() != ConstantesDyCNumerico.VALOR_8) {
                    seleccionaRfcNoConfiable.setObservaciones(Constantes.EMPTY);
                }
            } else {
                if (seleccionaRfcNoConfiable.getDyccMotivoRfcDTO().getIdMotivoRfc() != ConstantesDyCNumerico.VALOR_16) {
                    seleccionaRfcNoConfiable.setObservaciones(Constantes.EMPTY);
                }
            }

            seleccionaRfcNoConfiable.setUsuarioResp(accesoUsr.getUsuario());

            casosAVerificarService.modificarRfcNoConfiable(seleccionaRfcNoConfiable);

            obtenerRFCNoConf(seleccionaRfcNoConfiable.getDycpRfcDTO().getRfc());

            mensaje.addInfo(MSG, "Se modificó correctamente el RFC: "  + seleccionaRfcNoConfiable.getDycpRfcDTO().getRfc());
            request.update(MSG);
        } catch (SIATException e) {
            LOG.error("Error al modificar RFCNoConfiable --> " + e.getMessage());
            mensaje.addInfo(MSG, "Ocurrió un error al modificar el RFC: " + seleccionaRfcNoConfiable.getDycpRfcDTO().getRfc());
        }
        request.execute(PRINCIPAL);
    }

    public void reset() {
        init();
    }

    public void setCasosAVerificarService(CasosAVerificarService casosAVerificarService) {
        this.casosAVerificarService = casosAVerificarService;
    }

    public CasosAVerificarService getCasosAVerificarService() {
        return casosAVerificarService;
    }

    public void setObtenerRFCNoConfiable(List<DycbEstadoRfcDTO> obtenerRFCNoConfiable) {
        this.obtenerRFCNoConfiable = obtenerRFCNoConfiable;
    }

    public List<DycbEstadoRfcDTO> getObtenerRFCNoConfiable() {
        return obtenerRFCNoConfiable;
    }

    public void setObtenerMotivos(List<DyccMotivoRfcDTO> obtenerMotivos) {
        this.obtenerMotivos = obtenerMotivos;
    }

    public List<DyccMotivoRfcDTO> getObtenerMotivos() {
        return obtenerMotivos;
    }

    public void setSeleccionaRfcNoConfiable(DycbEstadoRfcDTO seleccionaRfcNoConfiable) {
        this.seleccionaRfcNoConfiable = seleccionaRfcNoConfiable;
    }

    public DycbEstadoRfcDTO getSeleccionaRfcNoConfiable() {
        return seleccionaRfcNoConfiable;
    }

    public void setDyccMotivoRfcDTO(DyccMotivoRfcDTO dyccMotivoRfcDTO) {
        this.dyccMotivoRfcDTO = dyccMotivoRfcDTO;
    }

    public DyccMotivoRfcDTO getDyccMotivoRfcDTO() {
        return dyccMotivoRfcDTO;
    }

    public void setDycbEstadoRfcDTO(DycbEstadoRfcDTO dycbEstadoRfcDTO) {
        this.dycbEstadoRfcDTO = dycbEstadoRfcDTO;
    }

    public DycbEstadoRfcDTO getDycbEstadoRfcDTO() {
        return dycbEstadoRfcDTO;
    }

    public void setInsertarRfcNoConfiable(DycpRfcDTO insertarRfcNoConfiable) {
        this.insertarRfcNoConfiable = insertarRfcNoConfiable;
    }

    public DycpRfcDTO getInsertarRfcNoConfiable() {
        return insertarRfcNoConfiable;
    }

    public void setMostrarPanelInsertar(Boolean mostrarPanelInsertar) {
        this.mostrarPanelInsertar = mostrarPanelInsertar;
    }

    public Boolean getMostrarPanelInsertar() {
        return mostrarPanelInsertar;
    }

    public void setMostrarPanelModificar(Boolean mostrarPanelModificar) {
        this.mostrarPanelModificar = mostrarPanelModificar;
    }

    public Boolean getMostrarPanelModificar() {
        return mostrarPanelModificar;
    }

    public void setMostrarPanelBitacora(Boolean mostrarPanelBitacora) {
        this.mostrarPanelBitacora = mostrarPanelBitacora;
    }

    public Boolean getMostrarPanelBitacora() {
        return mostrarPanelBitacora;
    }

    public void setBitacora(List<DycbEstadoRfcDTO> bitacora) {
        this.bitacora = bitacora;
    }

    public List<DycbEstadoRfcDTO> getBitacora() {
        return bitacora;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
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

    public void setTrfc(String trfc) {
        if (trfc != null) {
            this.trfc = trfc.trim().toUpperCase();
        } else {
            this.trfc = null;
        }
    }

    public String getTrfc() {
        return trfc;
    }
}
