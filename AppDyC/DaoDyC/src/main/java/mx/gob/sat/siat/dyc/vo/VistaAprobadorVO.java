/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.vo;

import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;

/**
 * Clase DTO para la tabla [DYCC_APROBADOR] + Descripcion Administracion Comision.
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Octubre 24, 2013
 * */
public class VistaAprobadorVO extends DyccAprobadorDTO {

    @SuppressWarnings("compatibility:-881330125597653641")
    private static final long serialVersionUID = 1L;

    private String descAdministracion;

    public VistaAprobadorVO() {
        super();
    }

    public void setDescAdministracion(String descAdministracion) {
        this.descAdministracion = descAdministracion;
    }

    public String getDescAdministracion() {
        return descAdministracion;
    }
}
