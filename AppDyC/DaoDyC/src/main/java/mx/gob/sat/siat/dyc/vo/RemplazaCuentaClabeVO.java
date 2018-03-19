package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import java.util.Date;
import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;

public class RemplazaCuentaClabeVO implements Serializable {

    @SuppressWarnings("compatibility:-6460202534070424351")
    private static final long serialVersionUID = 1L;

    private String cuenta;
    private String banco;
    private Date fechaRegistro;
    private Date fechaUltima;
    private Integer cuentaValida;
    private Integer idArchivo;
    private DyccInstCreditoDTO dyccInstCreditoDTO;

    public RemplazaCuentaClabeVO() {
        super();
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getBanco() {
        return banco;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if (fechaRegistro != null) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setFechaUltima(Date fechaUltima) {
        if (fechaUltima != null) {
            this.fechaUltima = (Date)fechaUltima.clone();
        } else {
            this.fechaUltima = null;
        }
    }

    public Date getFechaUltima() {
        if (fechaUltima != null) {
            return (Date)fechaUltima.clone();
        } else {
            return null;
        }
    }

    public void setCuentaValida(Integer cuentaValida) {
        this.cuentaValida = cuentaValida;
    }

    public Integer getCuentaValida() {
        return cuentaValida;
    }

    public void setIdArchivo(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Integer getIdArchivo() {
        return idArchivo;
    }

    /**
     * @return the dyccInstCreditoDTO
     */
    public DyccInstCreditoDTO getDyccInstCreditoDTO() {
        return dyccInstCreditoDTO;
    }

    /**
     * @param dyccInstCreditoDTO the dyccInstCreditoDTO to set
     */
    public void setDyccInstCreditoDTO(DyccInstCreditoDTO dyccInstCreditoDTO) {
        this.dyccInstCreditoDTO = dyccInstCreditoDTO;
    }
}
