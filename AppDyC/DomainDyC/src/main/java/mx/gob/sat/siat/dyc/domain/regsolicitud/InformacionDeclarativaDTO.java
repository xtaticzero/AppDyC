package mx.gob.sat.siat.dyc.domain.regsolicitud;

import java.io.Serializable;

import java.util.Date;


/**
 * @author JEFG
 */
public class InformacionDeclarativaDTO implements Serializable {
    @SuppressWarnings("compatibility:5863430696138611193")
    private static final long serialVersionUID = 8718365652232927073L;

    private String diotNumOperacion;
    private Date diotFechPrecentacion;
    private Integer altexNumConstancia;


    public InformacionDeclarativaDTO() {
        super();
    }


    public void setDiotFechPrecentacion(Date diotFechPrecentacion) {
        if (diotFechPrecentacion != null) {
            this.diotFechPrecentacion = (Date)diotFechPrecentacion.clone();
        } else {
            this.diotFechPrecentacion = null;
        }
    }

    public Date getDiotFechPrecentacion() {
        if (diotFechPrecentacion != null) {
            return (Date)diotFechPrecentacion.clone();
        } else {
            return null;
        }
    }

    public void setAltexNumConstancia(Integer altexNumConstancia) {
        this.altexNumConstancia = altexNumConstancia;
    }

    public Integer getAltexNumConstancia() {
        return altexNumConstancia;
    }

    public void setDiotNumOperacion(String diotNumOperacion) {
        this.diotNumOperacion = diotNumOperacion;
    }

    public String getDiotNumOperacion() {
        return diotNumOperacion;
    }
}
