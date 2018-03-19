/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.pjplantillador.dto;

/**
 * Clase que almacena los datos de los archivos de configuracion
 * y de la plantilla.
 * @author Agustin Romero Mata / Softtek
 */
public class Plantilla {
    private String archConfiguracion;
    private String archPlantilla;           
    
    /**
     * Constructor parametrizado de la clase.
     * @param archConfiguracion
     * @param archPlantilla 
     */
    public Plantilla(String archConfiguracion, String archPlantilla) {

        super();
        this.archConfiguracion = archConfiguracion;
        this.archPlantilla = archPlantilla;
    }

    /**
     * Obtiene el archConfiguracion de la plantilla
     * @return 
     */
    public String getArchConfiguracion() {
    
        return archConfiguracion;
    }
    
    /**
     * Inicializa el archConfiguracion de la plantilla
     * @param archConfiguracion 
     */
    public void setArchConfiguracion(String archConfiguracion) {
    
        this.archConfiguracion = archConfiguracion;
    }
    
    /**
     * Obtiene el archPlantilla de la plantilla
     * @return 
     */
    public String getArchPlantilla() {
    
        return archPlantilla;
    }
    
    /**
     * Inicializa el archPlantilla de la plantilla
     * @param archPlantilla 
     */
    public void setArchPlantilla(String archPlantilla) {
    
        this.archPlantilla = archPlantilla;
    }    
}