/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import mx.gob.sat.siat.dyc.domain.regsolicitud.ContribuyenteDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;


@ManagedBean(name ="ConsultaDatosGralSolMB")
@SessionScoped
public class ConsultaDatosGralSolMB {
    
   private ContribuyenteDTO contribuyente;
   
    
    @PostConstruct
    public void init() {
        contribuyente = new ContribuyenteDTO();
        contribuyente.setRfc((String)Utils.getParametro("txtRFC"));
    }


    public void setContribuyente(ContribuyenteDTO contribuyente) {
        this.contribuyente = contribuyente;
    }

    public ContribuyenteDTO getContribuyente() {
        return contribuyente;
    }
}
