/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.vo;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSeguimientoDTO;

/**
 * @author Federico Chopin Gachuz
 * @date Abril 10, 2014
 * */
public class SeguimientoAdministrarSolVO extends DyctSeguimientoDTO {

    @SuppressWarnings("compatibility:4096496334092601949")
    private static final long serialVersionUID = 1L;

    public SeguimientoAdministrarSolVO() {
        super();
    }

    private String accion;
    private String nombreArchivo;


    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getAccion() {
        return accion;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }
}
