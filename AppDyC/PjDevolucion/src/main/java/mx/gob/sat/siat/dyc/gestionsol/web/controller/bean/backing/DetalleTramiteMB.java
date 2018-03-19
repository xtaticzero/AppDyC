/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 * Managed Bean para consultar expediente
 * @author Federico Chopin
 * @date  23/04/2015
 */
@ManagedBean(name = "detalleTramiteMB")
@SessionScoped
public class DetalleTramiteMB {
    
    public String irConsultarExpediente1() {

        return "detalleTramite1";

    }
    
    public String irConsultarExpediente2() {

        return "detalleTramite3";

    }
    
}
