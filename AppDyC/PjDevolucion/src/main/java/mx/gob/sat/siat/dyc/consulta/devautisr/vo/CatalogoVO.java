/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.vo;

import java.io.Serializable;

/**
 *
 *
 */
public class CatalogoVO implements Serializable {

    private static final long serialVersionUID = 5647623476619482156L;

    private String itemLabel;

    private Integer itemValue;

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public Integer getItemValue() {
        return itemValue;
    }

    public void setItemValue(Integer itemValue) {
        this.itemValue = itemValue;
    }

}
