/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.vo;

import mx.gob.sat.siat.dyc.domain.anexo.DycaSolAnexoTramDTO;

/**
 * @author Federico Chopin Gachuz
 * @date Abril 10, 2014
 *
 * */
public class ConsultarExpedienteVO extends DycaSolAnexoTramDTO{
    @SuppressWarnings("compatibility:-4818948207778282863")
    private static final long serialVersionUID = 1L;

    public ConsultarExpedienteVO() {
        super();
    }
    
    private String nombreAnexo;

    public void setNombreAnexo(String nombreAnexo) {
        this.nombreAnexo = nombreAnexo;
    }

    public String getNombreAnexo() {
        return nombreAnexo;
    }
}
