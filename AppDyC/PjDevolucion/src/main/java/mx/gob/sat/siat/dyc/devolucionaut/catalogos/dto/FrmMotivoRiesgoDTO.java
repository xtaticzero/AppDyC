package mx.gob.sat.siat.dyc.devolucionaut.catalogos.dto;

import java.io.Serializable;

/**
 *
 * @author Jose Luis Aguilar
 */
public class FrmMotivoRiesgoDTO implements Serializable{
    @SuppressWarnings("compatibility:4418182852203385374")
    private static final long serialVersionUID = 1L;
    
    // Dialog Acciones
    private String tituloDialog;
    private String mensaje;
    private String accion;
    
    // Banderas para mostar o ocultar elementos
    private boolean bandCodigo;
    private boolean bandModelo;
    private boolean bandRegla;
    private boolean bandEstado;
    private boolean bandBtnGuardar;
    private boolean bandBtnBuscar;
    private boolean banderaMsg;
    private boolean bandActivoCodigo;
    private boolean bandActivoModelo;
    private boolean bandActivoEstado;

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

    public boolean isBandCodigo() {
        return bandCodigo;
    }

    public void setBandCodigo(boolean bandCodigo) {
        this.bandCodigo = bandCodigo;
    }

    public boolean isBandModelo() {
        return bandModelo;
    }

    public void setBandModelo(boolean bandModelo) {
        this.bandModelo = bandModelo;
    }

    public boolean isBandRegla() {
        return bandRegla;
    }

    public void setBandRegla(boolean bandRegla) {
        this.bandRegla = bandRegla;
    }

    public boolean isBandEstado() {
        return bandEstado;
    }

    public void setBandEstado(boolean bandEstado) {
        this.bandEstado = bandEstado;
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

    public boolean isBanderaMsg() {
        return banderaMsg;
    }

    public void setBanderaMsg(boolean banderaMsg) {
        this.banderaMsg = banderaMsg;
    }

    public boolean isBandActivoCodigo() {
        return bandActivoCodigo;
    }

    public void setBandActivoCodigo(boolean bandActivoCodigo) {
        this.bandActivoCodigo = bandActivoCodigo;
    }

    public boolean isBandActivoModelo() {
        return bandActivoModelo;
    }

    public void setBandActivoModelo(boolean bandActivoModelo) {
        this.bandActivoModelo = bandActivoModelo;
    }

    public boolean isBandActivoEstado() {
        return bandActivoEstado;
    }

    public void setBandActivoEstado(boolean bandActivoEstado) {
        this.bandActivoEstado = bandActivoEstado;
    }
    
    
    
}
