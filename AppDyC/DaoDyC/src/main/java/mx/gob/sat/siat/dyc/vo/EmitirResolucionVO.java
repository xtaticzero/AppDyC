/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

/**
 * @author Federico Chopin Gachuz
 * @date Abril 11, 2014
 *
 * */
public class EmitirResolucionVO implements Serializable {
    @SuppressWarnings("compatibility:4567086560531831258")
    private static final long serialVersionUID = -8018558487412655961L;

    public EmitirResolucionVO() {
        super();
    }
    
    private String informacion;


    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getInformacion() {
        return informacion;
    }
}
