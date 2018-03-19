package mx.gob.sat.siat.dyc.adminprocesos.dto;

public class Mes {
    private int numeroMes;
    private String nombre;
    
    public Mes (int numeroMes, String nombre) {
       this.numeroMes=numeroMes;
       this.nombre=nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNumeroMes(int numeroMes) {
        this.numeroMes = numeroMes;
    }

    public int getNumeroMes() {
        return numeroMes;
    }
}
