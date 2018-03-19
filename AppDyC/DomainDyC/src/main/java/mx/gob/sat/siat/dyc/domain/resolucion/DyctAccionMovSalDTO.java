package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.movsaldo.DyctAccionPrivAjusDTO;


/**
 *
 * @author Softtek
 */
public class DyctAccionMovSalDTO implements Serializable
{
    @SuppressWarnings("compatibility:-4716995393890408531")
    private static final long serialVersionUID = 1L;

    private Integer idAccionMovSal;
    private DyctMovSaldoDTO dyctMovSaldoDTO;
    private TipoAccionMovSaldo tipoAccionMovSal;
    private Date fechaRegistro;
    private String justificacion;
    private DyctAccionPrivAjusDTO dyctAccionPrivAjusDTO;

    public Integer getIdAccionMovSal() {
        return idAccionMovSal;
    }

    public void setIdAccionMovSal(Integer idAccionMovSal) {
        this.idAccionMovSal = idAccionMovSal;
    }

    public DyctMovSaldoDTO getDyctMovSaldoDTO() {
        return dyctMovSaldoDTO;
    }

    public void setDyctMovSaldoDTO(DyctMovSaldoDTO dyctMovSaldoDTO) {
        this.dyctMovSaldoDTO = dyctMovSaldoDTO;
    }

    public Date getFechaRegistro() {
        if (null != this.fechaRegistro) {
            return (Date)this.fechaRegistro.clone();
        } else {
            return null;
        }
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
         }
        else{
            this.fechaRegistro = null;
        }
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    
    public enum TipoAccionMovSaldo {
        INVALIDAR(1, "Se realiza acción para que el movimiento del saldo deje afectar en control de saldos"),
        VALIDAR(2, "Se realiza acción para que el movimiento del saldo vuelva a afectar en control de saldos"),
        CREAR(3, "Se crea un nuevo movimiento (abono o cargo) para un ICEP");

        private Integer id;
        private String descripcion;

        TipoAccionMovSaldo(Integer i, String d) {
            id = i;
            descripcion = d;
        }

        public Integer getId() {
            return id;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
    public enum TipoAccionPermisoAjuste {
        OTORGAR(1, "Se otorga permiso para realizar ajustes en control de saldos"),
        REVOCAR(2, "Se revoca permiso para realizar ajustes en control de saldos");

        private Integer id;
        private String descripcion;

        TipoAccionPermisoAjuste(Integer i, String d) {
            id = i;
            descripcion = d;
        }

        public Integer getId() {
            return id;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    public TipoAccionMovSaldo getTipoAccionMovSal() {
        return tipoAccionMovSal;
    }

    public void setTipoAccionMovSal(TipoAccionMovSaldo tipoAccionMovSal) {
        this.tipoAccionMovSal = tipoAccionMovSal;
    }

    public DyctAccionPrivAjusDTO getDyctAccionPrivAjusDTO() {
        return dyctAccionPrivAjusDTO;
    }

    public void setDyctAccionPrivAjusDTO(DyctAccionPrivAjusDTO dyctAccionPrivAjusDTO) {
        this.dyctAccionPrivAjusDTO = dyctAccionPrivAjusDTO;
    }
}
