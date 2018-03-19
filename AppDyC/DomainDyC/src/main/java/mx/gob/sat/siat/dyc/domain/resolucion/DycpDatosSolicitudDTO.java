/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

/**
 *
 * @author jose.aguilarl
 */
public class DycpDatosSolicitudDTO implements Serializable {


    @SuppressWarnings("compatibility:5688717448376675893")
    private static final long serialVersionUID = 1L;
    
    private String numControl;
    private Integer idEstadoSolicitud;
    private Integer numDictaminador;
    
    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public Integer getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public Integer getNumDictaminador() {
        return numDictaminador;
    }

    public void setNumDictaminador(Integer numDictaminador) {
        this.numDictaminador = numDictaminador;
    }
    
}
