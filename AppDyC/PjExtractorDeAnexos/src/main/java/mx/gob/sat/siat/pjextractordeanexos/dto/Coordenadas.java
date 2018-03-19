package mx.gob.sat.siat.pjextractordeanexos.dto;

public class Coordenadas {
    public Coordenadas() {
        super();
    }
    
    public Coordenadas(Integer x, Integer y) {
        this.x=x;
        this.y=y;
    }
    
    private Integer x;
    private Integer y;

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getX() {
        return x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getY() {
        return y;
    }
}
