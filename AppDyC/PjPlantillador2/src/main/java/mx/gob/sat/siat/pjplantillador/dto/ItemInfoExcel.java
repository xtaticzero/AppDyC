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
public class ItemInfoExcel extends InfoExcel {
    private String hoja;
    private String renglon;
    private String columna;

    public ItemInfoExcel() {
        super("", ' ', 0, "");
        
        this.hoja = "";
        this.renglon = "";
        this.columna = "";
    }

    public ItemInfoExcel(String hoja, String renglon, String columna, 
            String nombre, char tipo, int tamano, String contenido) {        
        super(nombre, tipo, tamano, contenido);
        
        this.hoja = hoja;
        this.renglon = renglon;
        this.columna = columna;
    }
    
    public String getHoja() {
        return hoja;
    }

    public void setHoja(String hoja) {
        this.hoja = hoja;
    }

    public String getRenglon() {
        return renglon;
    }

    public void setRenglon(String renglon) {
        this.renglon = renglon;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }    
    
    public boolean ValorBuscado(String Columna) {
        boolean retorno = false;
        String temp = this.columna + this.renglon;
        
        if (temp.equals(Columna)) {
                retorno = Boolean.TRUE;
        }
                
        return retorno;
    }
}
