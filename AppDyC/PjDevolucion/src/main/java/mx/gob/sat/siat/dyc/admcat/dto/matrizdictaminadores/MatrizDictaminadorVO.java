/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;


/**
 * DTO Caso de uso Mantener Matriz de Dictaminadores
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 15, 2013
 * @since 0.1   Toy........................
 *
 * */
public class MatrizDictaminadorVO extends DyccDictaminadorDTO {

    @SuppressWarnings("compatibility:-8000458898741307815")
    private static final long serialVersionUID = 1L;

    private String admon;
    private String comisionadoTxt;
    private String nombreCompleto;

    public void setAdmon(String admon) {
        this.admon = admon;
    }

    public String getAdmon() {
        return admon;
    }

    public void setComisionadoTxt(String comisionadoTxt) {
        this.comisionadoTxt = comisionadoTxt;
    }

    public String getComisionadoTxt() {
        return comisionadoTxt;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
}
