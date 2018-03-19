/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.pjplantillador.dto;

/**
 *
 * @author socrates
 */
public class InfoExcel {
    private String  nombre;
    private char    tipo;
    private int     tamano;
    private String  contenido;

    public InfoExcel() {
        this.nombre = "";
        this.tipo = ' ';
        this.tamano = 0;
        this.contenido = "";
    }    
    
    public InfoExcel(String nombre, char tipo, int tamano, String contenido) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.tamano = tamano;
        this.contenido = contenido;
    }

    public InfoExcel(InfoExcel item) {
        this.nombre = item.getNombre();
        this.tipo = item.getTipo();
        this.tamano = item.getTamano();
        this.contenido = item.getContenido();
    }       
        
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }    
}
