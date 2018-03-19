package mx.gob.sat.siat.dyc.adminprocesos.dto;

import java.util.List;

public class ArchivosHistoricoDetalleDTO {
    public ArchivosHistoricoDetalleDTO() {
        super();
    }
    private int noRegistrosExitosos;
    private int noRegistrosFallidos;
    private List<RegistroFallidoDTO> listaRegistroFallidos;

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

    public void setListaRegistroFallidos(List<RegistroFallidoDTO> listaRegistroFallidos) {
        this.listaRegistroFallidos = listaRegistroFallidos;
    }

    public List<RegistroFallidoDTO> getListaRegistroFallidos() {
        return listaRegistroFallidos;
    }
}
