package mx.gob.sat.siat.dyc.util.common;

import java.io.Serializable;

import java.math.BigDecimal;


public class CatalogoTO implements Serializable {

    @SuppressWarnings("compatibility:-1227557129761060396")
    private static final long serialVersionUID = 1L;
    private int idNum;
    private String descripcion;
    private String idString;
    private BigDecimal valor;
    private String nombre;
    

    /** default constructor */
    public CatalogoTO() {
    }

    /** minimal constructor */
    public CatalogoTO(int idNum) {
        this.idNum = idNum;
    }

    /** full constructor */
    public CatalogoTO(int idNum, String descripcion, String idString) {
        this.idNum = idNum;
        this.descripcion = descripcion;
        this.idString = idString;
    }

    public int getIdNum() {
        return idNum;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getIdString() {
        return idString;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
