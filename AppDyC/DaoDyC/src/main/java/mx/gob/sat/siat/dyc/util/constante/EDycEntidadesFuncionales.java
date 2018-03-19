/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.util.constante;

/**
 *
 * @author GAER8674
 */
public enum EDycEntidadesFuncionales {
    DEV_AUTOMATICAS_IVA("DEVIVA", "11"),
    RFC_AMPLIADO("RFCAMPL", "12"),
    BUZON_NOTIFICACIONES("BUZONNOTIF", "13"),
    BUZON_TRIBUTARIO("BUZONTRIB", "14"),
    BD_DYC("BDDYC", "15"),
    ;
    
    private final String entidad;
    private final String codigo;

    private EDycEntidadesFuncionales(String entidad, String codigo) {
        this.entidad = entidad;
        this.codigo = codigo;
    }


    /**
     * @return the entidad
     */
    public String getEntidad() {
        return entidad;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
}
