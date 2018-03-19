package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.backing;

import java.util.Date;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.adminprocesos.dto.ArchivosHistoricoDetalleDTO;
import mx.gob.sat.siat.dyc.adminprocesos.service.ArchivosHistoricoService;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.Utils;

import org.apache.log4j.Logger;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@ManagedBean(name = "archivosHistoricoMB")
@ViewScoped
public class ArchivosHistoricoMB {
    public ArchivosHistoricoMB() {
        super();
        bandera = false;
    }
    private Date fecha;
    private boolean bandera;
    private int noRegistrosExitosos;
    private int noRegistrosFallidos;

    private static final Logger LOGGER = Logger.getLogger(ArchivosHistoricoMB.class);

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{archivosHistoricoService}")
    private ArchivosHistoricoService archivosHistoricoService;

    /**
     * Una vez cargados los elementos en pantalla, valida si el usuario tiene permisos para ejecutar el proceso.
     */
    @PostConstruct
    public void init() {
        try {
            Utils.validarSesion(serviceObtenerSesion.execute(), mx.gob.sat.siat.dyc.util.constante.Procesos.DYC00018);

        } catch (Exception e) {
            LOGGER.error("Error al cargar la informacion: " + e);
        }
    }

    /**
     * Busca el n&uacute;mero de registros que se han podido modificar en el fileSystem. Obtiene 3 datos escenciales:<br />
     *  - La cantidad de registros procesados.<br />
     *  - La cantidad de registros que no se pudieron copiar a otro fileSystem.<br />
     *  - El detalle de los registros que nos se pudieron copiar de un fileSystem a otro.<br />
     */
    private void buscarInformacionProcesada() {
        ArchivosHistoricoDetalleDTO objeto = null;
        try {
            objeto = archivosHistoricoService.prepararInformacionDeTramitesProcesados(fecha);
            noRegistrosExitosos = objeto.getNoRegistrosExitosos();
            noRegistrosFallidos = objeto.getNoRegistrosFallidos();

        } catch (Exception f) {
            LOGGER.warn(f);
        }
    }

    /**
     * Cada vez que selecciona una fecha; busca si existen documentos que fueron movidos a otro filesystem.
     * Activa en pantalla la cantidad de tr&aacute;mites que fueron movidos exitosamente y aquellos que
     * no lo fueron. Activa un bot&oacute;n que da la posibilidad de descargar los t&aacute;mites que tuvieron
     * fallo durante el copiado.
     *
     * @param e Par&aacute;metro de pantalla.
     */
    public void onDateSelect(SelectEvent e) {
        buscarInformacionProcesada();
        bandera = Boolean.TRUE;
    }

    public void setFecha(Date fecha) {
        if (fecha != null) {
            this.fecha = (Date)fecha.clone();
        } else {
            this.fecha = null;
        }
    }

    public Date getFecha() {
        if (fecha != null) {
            return (Date)fecha.clone();
        } else {
            return null;
        }
    }

    public void setNoRegistrosExitosos(int noRegistrosExitosos) {
        this.noRegistrosExitosos = noRegistrosExitosos;
    }

    public int getNoRegistrosExitosos() {
        return noRegistrosExitosos;
    }

    public void setNoRegistrosFallidos(int noRegistrosFallidos) {
        this.noRegistrosFallidos = noRegistrosFallidos;
    }

    public int getNoRegistrosFallidos() {
        return noRegistrosFallidos;
    }

    public StreamedContent getArchivoConRegistrosFallidos() {
        ArchivoCargaDescarga archivo = new ArchivoCargaDescarga();
        return archivo.descargarArchivo("/siat/dyc/fallidos.txt");
    }

    public void setArchivosHistoricoService(ArchivosHistoricoService archivosHistoricoService) {
        this.archivosHistoricoService = archivosHistoricoService;
    }

    public ArchivosHistoricoService getArchivosHistoricoService() {
        return archivosHistoricoService;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }
}
