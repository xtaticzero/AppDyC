/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.dyc.tesofe.dto;

import java.math.BigDecimal;

/**
 *
 * @author Lalo
 */
public class DatosPagoDTO {
    private BigDecimal importeTotal;
    private Integer numeroDeRegistros;

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Integer getNumeroDeRegistros() {
        return numeroDeRegistros;
    }

    public void setNumeroDeRegistros(Integer numeroDeRegistros) {
        this.numeroDeRegistros = numeroDeRegistros;
    }
    
}
