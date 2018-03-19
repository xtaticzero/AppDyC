/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.rfcampl.domain;

/**
 * <p>Para envio de valores al validador del Request (Solo los datos que valida 
 * internamente el Web Service los demas datos se pasan sin validar).</p>
 * <em>TO para enviar parametros a un metodo.</em>
 * @author GAER8674
 */
public class RfcAmplValidarDatosTO {
    private String rfc;
    private String user;
    private String password;
    private String secciones;

    public RfcAmplValidarDatosTO() {}

    public RfcAmplValidarDatosTO(String rfc, String user, String password, String secciones) {
        this.rfc = rfc;
        this.user = user;
        this.password = password;
        this.secciones = secciones;
    }
    
    
    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the secciones
     */
    public String getSecciones() {
        return secciones;
    }

    /**
     * @param secciones the secciones to set
     */
    public void setSecciones(String secciones) {
        this.secciones = secciones;
    }
}
