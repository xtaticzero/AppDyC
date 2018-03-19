/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class CargaMasivaAutomaticasRegistroVO implements Serializable {
    
    private String fechaCarga;
    private String responsable;
    private String fechaInicioProceso;
    private String fechaTerminoProceso;
    private int numeroSolicitudesProcesar;
    private int numeroSolicitudesExitosas;
    private int numeroSolicitudesFallidas;
private String rutaArchivoProcesar;
private String rutaArchivoExitoso;
private String rutaArchivoFallido;
    private Date fechaInicioProcesoDate;
    private Date fechaTerminoProcesoDate;

    public Date getFechaInicioProcesoDate() {
          if (fechaInicioProcesoDate != null) {
            return (Date)fechaInicioProcesoDate.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicioProcesoDate(Date fechaInicioProcesoDate) {
          if (fechaInicioProcesoDate != null) {
            this.fechaInicioProcesoDate = (Date)fechaInicioProcesoDate.clone();
        } else {
            this.fechaInicioProcesoDate = null;
        }
    }

    public Date getFechaTerminoProcesoDate() {
         if (fechaTerminoProcesoDate != null) {
            return (Date)fechaTerminoProcesoDate.clone();
        } else {
            return null;
        }
    }

    public void setFechaTerminoProcesoDate(Date fechaTerminoProcesoDate) {
        if (fechaTerminoProcesoDate != null) {
            this.fechaTerminoProcesoDate = (Date)fechaTerminoProcesoDate.clone();
        } else {
            this.fechaTerminoProcesoDate = null;
        }
    }
    
    public String getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getResponsable() {
        return responsable;
    }

  
    public String getRutaArchivoExitoso() {
        return rutaArchivoExitoso;
    }

    public void setRutaArchivoExitoso(String rutaArchivoExitoso) {
        this.rutaArchivoExitoso = rutaArchivoExitoso;
    }

    public String getRutaArchivoFallido() {
        return rutaArchivoFallido;
    }

    public void setRutaArchivoFallido(String rutaArchivoFallido) {
        this.rutaArchivoFallido = rutaArchivoFallido;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getFechaInicioProceso() {
        return fechaInicioProceso;
    }

    public void setFechaInicioProceso(String fechaInicioProceso) {
        this.fechaInicioProceso = fechaInicioProceso;
    }

    public String getFechaTerminoProceso() {
        return fechaTerminoProceso;
    }

    public void setFechaTerminoProceso(String fechaTerminoProceso) {
        this.fechaTerminoProceso = fechaTerminoProceso;
    }

    public int getNumeroSolicitudesProcesar() {
        return numeroSolicitudesProcesar;
    }

    public void setNumeroSolicitudesProcesar(int numeroSolicitudesProcesar) {
        this.numeroSolicitudesProcesar = numeroSolicitudesProcesar;
    }

    public int getNumeroSolicitudesExitosas() {
        return numeroSolicitudesExitosas;
    }

    public void setNumeroSolicitudesExitosas(int numeroSolicitudesExitosas) {
        this.numeroSolicitudesExitosas = numeroSolicitudesExitosas;
    }

    public int getNumeroSolicitudesFallidas() {
        return numeroSolicitudesFallidas;
    }

    public void setNumeroSolicitudesFallidas(int numeroSolicitudesFallidas) {
        this.numeroSolicitudesFallidas = numeroSolicitudesFallidas;
    }

    public String getRutaArchivoProcesar() {
        return rutaArchivoProcesar;
    }

    public void setRutaArchivoProcesar(String rutaArchivoProcesar) {
        this.rutaArchivoProcesar = rutaArchivoProcesar;
    }
    
}
