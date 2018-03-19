/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class RetroalimentacionPagoVO implements Serializable{

    private Date fechaPago;
    private String motivoRechazo;

    public Date getFechaPago() {
        if (fechaPago != null) {
            return (Date)fechaPago.clone();
        } else {
            return null;
        }         
    }

    public void setFechaPago(Date fechaPago) {
        if (fechaPago != null) {
            this.fechaPago = (Date)fechaPago.clone();
        } else {
            this.fechaPago = null;
        }
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }
    
    
    
}