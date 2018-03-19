/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dto.institucioncredito;

import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;

/**
 * DTO Lista Institucion Credito
 * @author [LuFerMX] Luis Fernando Barrios Quiroz [LuFerMX]
 * @date Septiembre 5, 2013
 * @since 0.1
 *
 * */
public class ListaInstitucionCreditoDTO extends DyccInstCreditoDTO {

    @SuppressWarnings("compatibility:-1027829666482987139")
    private static final long serialVersionUID = 1L;

    private String estado;

    public ListaInstitucionCreditoDTO() {
        super();
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
