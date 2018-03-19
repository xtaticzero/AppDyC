/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*/
package mx.gob.sat.siat.dyc.registro.service.solicitud;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;


/**
 * @author J. Isaac Carbajal Bernal
 */
public interface ReasignarManSolicDevolucionyCasosCompService {
    
     /**
          * @param selectedSolicitudesArrList
          * @param selectedDictaminador
          * @param selectedDictaminadorOnDlg
          * @param numControl
          * @return
          */
         String reasignacionManualSolicitud(List<DictaminadorSolBean> selectedSolicitudesArrList, 
                                                   DyccDictaminadorDTO selectedDictaminador, 
                                                   DyccDictaminadorDTO selectedDictaminadorOnDlg, String numControl);
         String reasignacionManualResponsable(List<DictaminadorSolBean> selectedSolicitudesArrList, 
                                                   DyccDictaminadorDTO selectedDictaminador, 
                                                   DyccDictaminadorDTO selectedDictaminadorOnDlg, Integer numeroEmpleadoResponsable);
    
}
