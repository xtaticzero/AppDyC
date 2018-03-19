package mx.gob.sat.siat.dyc.devolucionaut.catalogos.dto;

import java.io.Serializable;

/**
 *
 * @author Jose Luis Aguilar
 */
public class FrmMontoSaldoAFavorDTO implements Serializable{
    @SuppressWarnings("compatibility:4418182852203385374")
    private static final long serialVersionUID = 1L;
    
    // Dialog Acciones
    private String tituloDialog;
    private String mensaje;
    private String accion;
    
    // Banderas para mostar o ocultar elementos
    private boolean bandOrigenDev;
    private boolean bandTipoTram;
    private boolean bandMontoSAF;
    private boolean bandBtnGuardar;
    private boolean bandBtnBuscar;
    private boolean bandTipoTramTxt;
    private boolean bandOrigenDevTxt;
    private boolean bandEstado;
    private boolean banderaMsg;
    private boolean bandActivo;
    
    // Atributos del registro a guardar
    private String strMsgOrigenDevolucion;
    private String strMsgTipoTramite;
    private String strMsgMontoSAF;
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

    public String getStrMsgMontoSAF() {
        return strMsgMontoSAF;
    }

    public void setStrMsgMontoSAF(String strMsgMontoSAF) {
        this.strMsgMontoSAF = strMsgMontoSAF;
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

    public boolean isBandMontoSAF() {
        return bandMontoSAF;
    }

    public void setBandMontoSAF(boolean bandMontoSAF) {
        this.bandMontoSAF = bandMontoSAF;
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

    public boolean isBandEstado() {
        return bandEstado;
    }

    public void setBandEstado(boolean bandEstado) {
        this.bandEstado = bandEstado;
    }

    public String getStrMsgEstado() {
        return strMsgEstado;
    }

    public void setStrMsgEstado(String strMsgEstado) {
        this.strMsgEstado = strMsgEstado;
    }

    public boolean isBanderaMsg() {
        return banderaMsg;
    }

    public void setBanderaMsg(boolean banderaMsg) {
        this.banderaMsg = banderaMsg;
    }

    public boolean isBandActivo() {
        return bandActivo;
    }

    public void setBandActivo(boolean bandActivo) {
        this.bandActivo = bandActivo;
    }
}
