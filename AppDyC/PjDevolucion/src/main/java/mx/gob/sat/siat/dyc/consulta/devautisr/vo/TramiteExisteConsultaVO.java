/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.vo;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class TramiteExisteConsultaVO implements Serializable{
    private long folioDeclaracion;
    private int idEstadoProceso;
    private String descripcionEstado;
    
    public long getFolioDeclaracion() {
        return folioDeclaracion;
    }

    public void setFolioDeclaracion(long folioDeclaracion) {
        this.folioDeclaracion = folioDeclaracion;
    }

    public int getIdEstadoProceso() {
        return idEstadoProceso;
    }

    public void setIdEstadoProceso(int idEstadoProceso) {
        this.idEstadoProceso = idEstadoProceso;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }
   
}
