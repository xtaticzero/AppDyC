/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.domain.regsolicitud;

/** Clase dummy que simula la interfaz de registrador de devoluciones
 * 
 * @author Paola Rivera
 */
public class RegistradorDevolucionesExampleDTO {
    public RegistradorDevolucionesExampleDTO() {
        super();
    }

    /**
     * @param tipoRegistrador
     * @param rfc
     * @param nombre
     */
    public RegistradorDevolucionesExampleDTO(String tipoRegistrador, String rfc, String nombre) {
        this.tipoRegistrador = tipoRegistrador;
        this.rfc = rfc;
        this.nombre = nombre;                                 
    }
    
    private String tipoRegistrador;
    private String rfc;
    private String nombre;

    public void setTipoRegistrador(String tipoRegistrador) {
        this.tipoRegistrador = tipoRegistrador;
    }

    public String getTipoRegistrador() {
        return tipoRegistrador;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
