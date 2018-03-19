/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.vo;

/**
 * @author Federico Chopin Gachuz
 * @date Abril 11, 2015
 * */
public class SolicitudAdministrarSolVO extends SolAdministrarSolVO {

    @SuppressWarnings("compatibility:7838943577779974147")
    private static final long serialVersionUID = 1L;

    private String tipoTramite;
    private String estadoNotificacion;
    private String estadoSolicitud;
    private String estadoComp;
    private String estado;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private boolean editFolio;
    private boolean tieneFolioNyV;
    private boolean tieneFechaNotificacion;
    private String folioNyVF;
    private String fechaNyVF;
  
    public SolicitudAdministrarSolVO() {
        super();
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setEstadoNotificacion(String estadoNotificacion) {
        this.estadoNotificacion = estadoNotificacion;
    }

    public String getEstadoNotificacion() {
        return estadoNotificacion;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setEstadoComp(String estadoComp) {
        this.estadoComp = estadoComp;
    }

    public String getEstadoComp() {
        return estadoComp;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isEditFolio() {
        
        return this.editFolio;
            
    }

    /**
     *
     * @return tiene FolioNyV en la tabla DYCT_DOCUMENTO
     */
    public boolean isTieneFolioNyV() {
        return tieneFolioNyV;
    }

    public void setTieneFolioNyV(boolean tieneFolioNyV) {
        this.tieneFolioNyV = tieneFolioNyV;
    }

    /**
     *
     * @return tiene fecha de notificacion en la tabla DYCT_REQINFO
     */
    public boolean isTieneFechaNotificacion() {
        return tieneFechaNotificacion;
    }

    public void setTieneFechaNotificacion(boolean tieneFechaNotificacion) {
        this.tieneFechaNotificacion = tieneFechaNotificacion;
    }

    
    public void setEditFolio(boolean editFolio) {
        this.editFolio = editFolio;
    }

    public String getFolioNyVF() {
        return folioNyVF;
    }

    public void setFolioNyVF(String folioNyVF) {
        this.folioNyVF = folioNyVF;
    }

    public String getFechaNyVF() {
        return fechaNyVF;
    }

    public void setFechaNyVF(String fechaNyVF) {
        this.fechaNyVF = fechaNyVF;
    }

    
    
}

