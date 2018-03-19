/*
*  Todos los Derechos Reservados 2014 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.pjplantillador.servicios;

/**
 * Clase que proporciona las funciones base de los servicios.
 * @author Agustin Romero Mata / Softtek
 */
public class BaseServicios {            
    private String docTemplate;
    private String docDestino; 
    
    /**
     * Constructor de default
     * @param docTemplate
     * @param docDestino 
     */
    public BaseServicios(String docTemplate, String docDestino) {

        super();
        this.docTemplate = docTemplate;
        this.docDestino = docDestino;
    }

    /**
     * Obtiene el documento plantilla
     * @return 
     */
    public String getDocTemplate() {
    
        return docTemplate;
    }
    
    /**
     * Inicializa el documento plantilla
     * @param docTemplate 
     */
    public void setDocTemplate(String docTemplate) {
    
        this.docTemplate = docTemplate;
    }
    
    /**
     * Obtiene el documento destino
     * @return 
     */
    public String getDocDestino() {
    
        return docDestino;
    }
    
    /**
     * Inicializa el documento destino
     * @param docDestino 
     */
    public void setDocDestino(String docDestino) {
    
        this.docDestino = docDestino;
    }
}
