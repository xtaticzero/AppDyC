package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.math.BigDecimal;


/**
 * DTO de la tabla DYCT_LIQUIDACION
 * @author  Luis Alberto Dominguez LADP
 * @since   29/08/2014
 */
public class DyctLiquidacionDTO implements Serializable {


    @SuppressWarnings("compatibility:5738078641778407323")
    private static final long serialVersionUID = 1L;
    private DyctResolCompDTO dyctResolCompDTO;
    private BigDecimal importeActualizar;
    private BigDecimal importeRecargo;
    private BigDecimal importeMulta;
    private String numDocDeterminante;
    private String fundamentacion;
    private String mesInicioInpc;
    private String mesFinalInpc;
    private Integer anioInicialInpc;
    private Integer anioFinalInpc;
    private String mesInicioTasaRec;
    private String mesFinalTasaRec;
    private Integer anioInicialTasaRec;
    private Integer anioFinalTasaRec;
    private BigDecimal importeImprocedente;

    public DyctLiquidacionDTO() {
        super();
    }

    public void setImporteActualizar(BigDecimal importeActualizar) {
        this.importeActualizar = importeActualizar;
    }

    public BigDecimal getImporteActualizar() {
        return importeActualizar;
    }

    public void setImporteRecargo(BigDecimal importeRecargo) {
        this.importeRecargo = importeRecargo;
    }

    public BigDecimal getImporteRecargo() {
        return importeRecargo;
    }

    public void setImporteMulta(BigDecimal importeMulta) {
        this.importeMulta = importeMulta;
    }

    public BigDecimal getImporteMulta() {
        return importeMulta;
    }

    public void setNumDocDeterminante(String numDocDeterminante) {
        this.numDocDeterminante = numDocDeterminante;
    }

    public String getNumDocDeterminante() {
        return numDocDeterminante;
    }

    public void setMesInicioInpc(String mesInicioInpc) {
        this.mesInicioInpc = mesInicioInpc;
    }

    public String getMesInicioInpc() {
        return mesInicioInpc;
    }

    public void setMesInicioTasaRec(String mesInicioTasaRec) {
        this.mesInicioTasaRec = mesInicioTasaRec;
    }

    public String getMesInicioTasaRec() {
        return mesInicioTasaRec;
    }

    public void setFundamentacion(String fundamentacion) {
        this.fundamentacion = fundamentacion;
    }

    public String getFundamentacion() {
        return fundamentacion;
    }

    public void setMesFinalInpc(String mesFinalInpc) {
        this.mesFinalInpc = mesFinalInpc;
    }

    public String getMesFinalInpc() {
        return mesFinalInpc;
    }

    public void setAnioInicialInpc(Integer anioInicialInpc) {
        this.anioInicialInpc = anioInicialInpc;
    }

    public Integer getAnioInicialInpc() {
        return anioInicialInpc;
    }

    public void setAnioFinalInpc(Integer anioFinalInpc) {
        this.anioFinalInpc = anioFinalInpc;
    }

    public Integer getAnioFinalInpc() {
        return anioFinalInpc;
    }

    public void setMesFinalTasaRec(String mesFinalTasaRec) {
        this.mesFinalTasaRec = mesFinalTasaRec;
    }

    public String getMesFinalTasaRec() {
        return mesFinalTasaRec;
    }

    public void setAnioInicialTasaRec(Integer anioInicialTasaRec) {
        this.anioInicialTasaRec = anioInicialTasaRec;
    }

    public Integer getAnioInicialTasaRec() {
        return anioInicialTasaRec;
    }

    public void setAnioFinalTasaRec(Integer anioFinalTasaRec) {
        this.anioFinalTasaRec = anioFinalTasaRec;
    }

    public Integer getAnioFinalTasaRec() {
        return anioFinalTasaRec;
    }

    public void setImporteImprocedente(BigDecimal importeImprocedente) {
        this.importeImprocedente = importeImprocedente;
    }

    public BigDecimal getImporteImprocedente() {
        return importeImprocedente;
    }

    public void setDyctResolCompDTO(DyctResolCompDTO dyctResolCompDTO) {
        this.dyctResolCompDTO = dyctResolCompDTO;
    }

    public DyctResolCompDTO getDyctResolCompDTO() {
        return dyctResolCompDTO;
    }
}
