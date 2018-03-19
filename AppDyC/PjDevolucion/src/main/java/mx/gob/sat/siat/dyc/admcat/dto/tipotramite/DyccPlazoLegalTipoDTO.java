/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dto.tipotramite;

import mx.gob.sat.siat.dyc.admcat.vo.PlazoLegalVO;


/**
 * Clase DTO para la tabla [DYCC_PLAZOLEGAL]
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 19, 2013
 * @since 0.1
 *
 * */
public class DyccPlazoLegalTipoDTO extends PlazoLegalVO {

    @SuppressWarnings("compatibility:6046499179259166546")
    private static final long serialVersionUID = 1L;

    private String descripcion;

    public DyccPlazoLegalTipoDTO() {
        super();
    }

    /** ACCESSOR'S */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
