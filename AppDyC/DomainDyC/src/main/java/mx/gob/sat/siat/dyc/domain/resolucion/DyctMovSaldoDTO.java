package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;


public class DyctMovSaldoDTO  implements Serializable
{
    @SuppressWarnings("compatibility:329560905797516041")
    private static final long serialVersionUID = 1L;

    private Integer idMovSaldo;
    private DyctSaldoIcepDTO dyctSaldoIcepDTO;
    private BigDecimal importe;
    private Date fechaRegistro;
    private Date fechaOrigen;
    private DyccMovIcepDTO dyccMovIcepDTO;
    private String idOrigen;
    private Date fechaFin;

    private List<DyctAccionMovSalDTO> dyctAccionMovSalDTOList;

    public Integer getIdMovSaldo() {
        return idMovSaldo;
    }

    public void setIdMovSaldo(Integer idMovSaldo) {
        this.idMovSaldo = idMovSaldo;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepDTO() {
        return dyctSaldoIcepDTO;
    }

    public void setDyctSaldoIcepDTO(DyctSaldoIcepDTO dyctSaldoIcepDTO) {
        this.dyctSaldoIcepDTO = dyctSaldoIcepDTO;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Date getFechaRegistro() {
        if (null != fechaRegistro) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaOrigen() {
        if (null != fechaOrigen) {
            return (Date)fechaOrigen.clone();
        } else {
            return null;
        }
    }

    public void setFechaOrigen(Date fechaOrigen) {
        if (null != fechaOrigen) {
            this.fechaOrigen = (Date)fechaOrigen.clone();
        } else {
            this.fechaOrigen = null;
        }
    }

    public DyccMovIcepDTO getDyccMovIcepDTO() {
        return dyccMovIcepDTO;
    }

    public void setDyccMovIcepDTO(DyccMovIcepDTO dyccMovIcepDTO) {
        this.dyccMovIcepDTO = dyccMovIcepDTO;
    }

    public String getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(String idOrigen) {
        this.idOrigen = idOrigen;
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date) fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date) fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }
    
    public List<DyctAccionMovSalDTO> getDyctAccionMovSalDTOList() {
        return dyctAccionMovSalDTOList;
    }

    public void setDyctAccionMovSalDTOList(List<DyctAccionMovSalDTO> dyctAccionMovSalDTOList) {
        this.dyctAccionMovSalDTOList = dyctAccionMovSalDTOList;
    }
}
