/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.vo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author root
 */
public class DatosTramiteISRVO implements Serializable {

    private static final long serialVersionUID = -7113036234021414625L;
    
    private String mensajeRespuestaConsulta;
    private String estadoConsulta;

    private String rfcContribuyente;
    private int ejercicio;
    private short existeTramites;
    private List<TramiteExisteConsultaVO> tramitesExisteConsulta;

    public short getExisteTramites() {
        return existeTramites;
    }

    public void setExisteTramites(short existeTramites) {
        this.existeTramites = existeTramites;
    }

    public List<TramiteExisteConsultaVO> getTramitesExisteConsulta() {
        return tramitesExisteConsulta;
    }

    public void setTramitesExisteConsulta(List<TramiteExisteConsultaVO> tramitesExisteConsulta) {
        this.tramitesExisteConsulta = tramitesExisteConsulta;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getMensajeRespuestaConsulta() {
        return mensajeRespuestaConsulta;
    }

    public void setMensajeRespuestaConsulta(String mensajeRespuestaConsulta) {
        this.mensajeRespuestaConsulta = mensajeRespuestaConsulta;
    }

    public String getEstadoConsulta() {
        return estadoConsulta;
    }

    public void setEstadoConsulta(String estadoConsulta) {
        this.estadoConsulta = estadoConsulta;
    }
}
