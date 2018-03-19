package mx.gob.sat.siat.dyc.devolucionaut.catalogos.dto;

import java.io.Serializable;

/**
 *
 * @author jose.aguilarl
 */
public class FrmTramiteDictaminacionAutomaticaDTO implements Serializable {
    @SuppressWarnings("compatibility:4418182852203385374")
    private static final long serialVersionUID = 1L;
    
    // Dialog Acciones
    private String tituloDialog;
    private String mensaje;
    private String accion;
    
    // Banderas para mostar o ocultar elementos
    private boolean bandOrigenDev;
    private boolean bandTipoTram;
    private boolean bandBtnGuardar;
    private boolean bandBtnBuscar;
    private boolean bandBtnConsultar;
    private boolean bandTipoTramTxt;
    private boolean bandOrigenDevTxt;
    private boolean bandModelo;
    private boolean bandModeloTxt;
    private boolean bandModeloCbo;
    private boolean bandConceptoTxt;
    private boolean bandImpuestoTxt;
    private boolean bandDictamenAut;
    private boolean banderaMsg;
    private boolean registroActivo;
    
    // Atributos del registro a guardar
    private String strMsgOrigenDevolucion;
    private String strMsgTipoTramite;
    private String strMsgEstado;

    public String getTituloDialog() {
        return tituloDialog;
    }

    public void setTituloDialog(String tituloDialog) {
        this.tituloDialog = tituloDialog;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public boolean isBandOrigenDev() {
        return bandOrigenDev;
    }

    public void setBandOrigenDev(boolean bandOrigenDev) {
        this.bandOrigenDev = bandOrigenDev;
    }

    public boolean isBandTipoTram() {
        return bandTipoTram;
    }

    public void setBandTipoTram(boolean bandTipoTram) {
        this.bandTipoTram = bandTipoTram;
    }

    public boolean isBandBtnGuardar() {
        return bandBtnGuardar;
    }

    public void setBandBtnGuardar(boolean bandBtnGuardar) {
        this.bandBtnGuardar = bandBtnGuardar;
    }

    public boolean isBandBtnBuscar() {
        return bandBtnBuscar;
    }

    public void setBandBtnBuscar(boolean bandBtnBuscar) {
        this.bandBtnBuscar = bandBtnBuscar;
    }

    public boolean isBandTipoTramTxt() {
        return bandTipoTramTxt;
    }

    public void setBandTipoTramTxt(boolean bandTipoTramTxt) {
        this.bandTipoTramTxt = bandTipoTramTxt;
    }

    public boolean isBandOrigenDevTxt() {
        return bandOrigenDevTxt;
    }

    public void setBandOrigenDevTxt(boolean bandOrigenDevTxt) {
        this.bandOrigenDevTxt = bandOrigenDevTxt;
    }

    public boolean isBanderaMsg() {
        return banderaMsg;
    }

    public void setBanderaMsg(boolean banderaMsg) {
        this.banderaMsg = banderaMsg;
    }

    public String getStrMsgOrigenDevolucion() {
        return strMsgOrigenDevolucion;
    }

    public void setStrMsgOrigenDevolucion(String strMsgOrigenDevolucion) {
        this.strMsgOrigenDevolucion = strMsgOrigenDevolucion;
    }

    public String getStrMsgTipoTramite() {
        return strMsgTipoTramite;
    }

    public void setStrMsgTipoTramite(String strMsgTipoTramite) {
        this.strMsgTipoTramite = strMsgTipoTramite;
    }


    public String getStrMsgEstado() {
        return strMsgEstado;
    }

    public void setStrMsgEstado(String strMsgEstado) {
        this.strMsgEstado = strMsgEstado;
    }

    public boolean isBandModelo() {
        return bandModelo;
    }

    public void setBandModelo(boolean bandModelo) {
        this.bandModelo = bandModelo;
    }

    public boolean isBandDictamenAut() {
        return bandDictamenAut;
    }

    public void setBandDictamenAut(boolean bandDictamenAut) {
        this.bandDictamenAut = bandDictamenAut;
    }

    public boolean isBandBtnConsultar() {
        return bandBtnConsultar;
    }

    public void setBandBtnConsultar(boolean bandBtnConsultar) {
        this.bandBtnConsultar = bandBtnConsultar;
    }

    public boolean isBandConceptoTxt() {
        return bandConceptoTxt;
    }

    public void setBandConceptoTxt(boolean bandConceptoTxt) {
        this.bandConceptoTxt = bandConceptoTxt;
    }

    public boolean isBandImpuestoTxt() {
        return bandImpuestoTxt;
    }

    public void setBandImpuestoTxt(boolean bandImpuestoTxt) {
        this.bandImpuestoTxt = bandImpuestoTxt;
    }

    public boolean isBandModeloTxt() {
        return bandModeloTxt;
    }

    public void setBandModeloTxt(boolean bandModeloTxt) {
        this.bandModeloTxt = bandModeloTxt;
    }

    public boolean isBandModeloCbo() {
        return bandModeloCbo;
    }

    public void setBandModeloCbo(boolean bandModeloCbo) {
        this.bandModeloCbo = bandModeloCbo;
    }

    public boolean isRegistroActivo() {
        return registroActivo;
    }

    public void setRegistroActivo(boolean registroActivo) {
        this.registroActivo = registroActivo;
    }
    
}
