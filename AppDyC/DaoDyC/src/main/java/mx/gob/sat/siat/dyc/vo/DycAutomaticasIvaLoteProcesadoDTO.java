/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.vo;

import java.util.List;

/**
 *
 * @author GAER8674
 */
public class DycAutomaticasIvaLoteProcesadoDTO {
    private int devolucionesProcesadas;
    private int devolucionesNoProcesadas;
    private List<DycAutomaticasIvaProcesadoDTO> datosDevolucionesProcesadas;
    private List<DycAutomaticasIvaProcesadoDTO> datosDevolucionesProcesadasDesistidas;
    private List<DycAutomaticasIvaProcesadoDTO> datosDevolucionesNoProcesadas;

    /**
     * @return the devolucionesProcesadas
     */
    public int getDevolucionesProcesadas() {
        return devolucionesProcesadas;
    }

    /**
     * @param devolucionesProcesadas the devolucionesProcesadas to set
     */
    public void setDevolucionesProcesadas(int devolucionesProcesadas) {
        this.devolucionesProcesadas = devolucionesProcesadas;
    }

    /**
     * @return the devolucionesNoProcesadas
     */
    public int getDevolucionesNoProcesadas() {
        return devolucionesNoProcesadas;
    }

    /**
     * @param devolucionesNoProcesadas the devolucionesNoProcesadas to set
     */
    public void setDevolucionesNoProcesadas(int devolucionesNoProcesadas) {
        this.devolucionesNoProcesadas = devolucionesNoProcesadas;
    }

    /**
     * @return the datosDevolucionesProcesadas
     */
    public List<DycAutomaticasIvaProcesadoDTO> getDatosDevolucionesProcesadas() {
        return datosDevolucionesProcesadas;
    }

    /**
     * @param datosDevolucionesProcesadas the datosDevolucionesProcesadas to set
     */
    public void setDatosDevolucionesProcesadas(List<DycAutomaticasIvaProcesadoDTO> datosDevolucionesProcesadas) {
        this.datosDevolucionesProcesadas = datosDevolucionesProcesadas;
    }

    /**
     * @return the datosDevolucionesNoProcesadas
     */
    public List<DycAutomaticasIvaProcesadoDTO> getDatosDevolucionesNoProcesadas() {
        return datosDevolucionesNoProcesadas;
    }

    /**
     * @param datosDevolucionesNoProcesadas the datosDevolucionesNoProcesadas to set
     */
    public void setDatosDevolucionesNoProcesadas(List<DycAutomaticasIvaProcesadoDTO> datosDevolucionesNoProcesadas) {
        this.datosDevolucionesNoProcesadas = datosDevolucionesNoProcesadas;
    }

    /**
     * @return the datosDevolucionesProcesadasDesistidas
     */
    public List<DycAutomaticasIvaProcesadoDTO> getDatosDevolucionesProcesadasDesistidas() {
        return datosDevolucionesProcesadasDesistidas;
    }

    /**
     * @param datosDevolucionesProcesadasDesistidas the datosDevolucionesProcesadasDesistidas to set
     */
    public void setDatosDevolucionesProcesadasDesistidas(List<DycAutomaticasIvaProcesadoDTO> datosDevolucionesProcesadasDesistidas) {
        this.datosDevolucionesProcesadasDesistidas = datosDevolucionesProcesadasDesistidas;
    }
}
