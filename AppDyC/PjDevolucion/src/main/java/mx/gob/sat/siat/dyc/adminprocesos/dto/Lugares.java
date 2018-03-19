package mx.gob.sat.siat.dyc.adminprocesos.dto;

public class Lugares {
    private Integer valor;
    private String nombre;
    
    public Lugares(Integer valor, String nombre) {
        this.valor = valor;
        this.nombre=nombre;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
