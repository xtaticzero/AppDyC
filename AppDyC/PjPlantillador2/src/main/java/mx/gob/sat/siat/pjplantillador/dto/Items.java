/*
*  Todos los Derechos Reservados 2014 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.pjplantillador.dto;

/**
 * Clase intermedia que almacena los datos a presentar.
 * @author Agustin Romero Mata / Softtek
 */
public class Items {
    private String id;
    private String valor;
    
    /**
     * Constructor de default de la clase.
     * @param id
     * @param valor 
     */
    public Items(String id, String valor) {

        super();
        this.id = id;
        this.valor = valor;
    }
    
    /**
     * Obtiene el id del Item
     * @return 
     */
    public String getId() {
    
        return id;
    }
    
    /**
     * Inicializa el id del Item
     * @param id 
     */
    public void setId(String id) {
    
        this.id = id;
    }
    
    /**
     * Obtiene el valor del Item
     * @return 
     */
    public String getValor() {
    
        return valor;
    }
    
    /**
     * Inicializa el valor del Item
     * @param valor 
     */     
    public void setValor(String valor) {
    
        this.valor = valor;
    }
}
