package mx.gob.sat.siat.dyc.vo;

import mx.gob.sat.siat.dyc.domain.adminproceso.DyctSegProcesoDTO;
import mx.gob.sat.siat.dyc.domain.adminproceso.DyctTareasDTO;

public class Procesos {
    private DyctTareasDTO dyctTareasDTO;
    private DyctSegProcesoDTO dyctSegProcesoDTO;
    private String ruta;
    private String ejecucion;
    private String descripcionHorarioEjecucion;

    public void setDyctTareasDTO(DyctTareasDTO dyctTareasDTO) {
        this.dyctTareasDTO = dyctTareasDTO;
    }

    public DyctTareasDTO getDyctTareasDTO() {
        return dyctTareasDTO;
    }

    public void setDyctSegProcesoDTO(DyctSegProcesoDTO dyctSegProcesoDTO) {
        this.dyctSegProcesoDTO = dyctSegProcesoDTO;
    }

    public DyctSegProcesoDTO getDyctSegProcesoDTO() {
        return dyctSegProcesoDTO;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setEjecucion(String ejecucion) {
        this.ejecucion = ejecucion;
    }

    public String getEjecucion() {
        return ejecucion;
    }

    public void setDescripcionHorarioEjecucion(String descripcionHorarioEjecucion) {
        this.descripcionHorarioEjecucion = descripcionHorarioEjecucion;
    }

    public String getDescripcionHorarioEjecucion() {
        return descripcionHorarioEjecucion;
    }
}
