/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.util.constante.enums;

/**
 *
 * @author root
 */
public enum EstadoDevISREnum {

    PROCESO(1, "En proceso"),
    PREAUTORIZADO(2, "Pre autorizado"),
    REVISION_POR_USUARIO(3, "En revisión por usuario"),
    AUTORIZADA_POR_PROCESO(4, "Autorizado por proceso"),
    RECHAZADO_POR_PROCESO(5, "Rechazado por proceso"),
    RECHAZADO_POR_CONTROL_SALDO(6, "Rechazado por control de saldos"),
    PROCESO_PAGO(7, "En proceso de pago"),
    PAGADO(8, "Pagado"),
    NO_PAGADO(9, "No pagado"),
    AUTORIZADA_POR_AUTORIDAD(10, "Autorizado por autoridad"),
    PROCEDENTE(11, "Procedente"),
    AUTORIZADA_POR_USUARIO(12, "Autorizado por usuario "),
    RECHAZADO_POR_USUARIO(13, "Rechazado por usuario"),
    REPROCESO(14, "En reproceso");

    private final int id;
    private final String descripcion;

    private EstadoDevISREnum(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public static EstadoDevISREnum parse(int idEstatus) {
        for (EstadoDevISREnum estadoDevISREnum : EstadoDevISREnum.values()) {
            if (estadoDevISREnum.getId() == idEstatus) {
                return estadoDevISREnum;
            }
        }
        return null;
    }
    

}
