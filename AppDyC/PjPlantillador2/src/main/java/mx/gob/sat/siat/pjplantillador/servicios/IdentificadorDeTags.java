/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.pjplantillador.servicios;

/**
 *
 * @author socrates
 */
public class IdentificadorDeTags {
    private int bandera;
    private String tag;
    private String numTag;

    public IdentificadorDeTags(int bandera, String tag, String numTag) {
        this.bandera = bandera;
        this.tag = tag;
        this.numTag = numTag;
    }    

    public int getBandera() {
        return bandera;
    }

    public String getNumTag() {
        return numTag;
    }

    public void setNumTag(String numTag) {
        this.numTag = numTag;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }        
}
