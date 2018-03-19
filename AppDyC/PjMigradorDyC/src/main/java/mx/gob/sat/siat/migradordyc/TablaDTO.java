package mx.gob.sat.siat.migradordyc;

import java.util.List;

public class TablaDTO
{
    private String nombre;
    private List<ColumnaDTO> columnas;
    private int[] tiposColumnas;
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ColumnaDTO> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<ColumnaDTO> columnas) {
        this.columnas = columnas;
    }

    public int[] getTiposColumnas() {
        return tiposColumnas;
    }

    public void setTiposColumnas(int[] tiposColumnas) {
        this.tiposColumnas = tiposColumnas;
    }
}
