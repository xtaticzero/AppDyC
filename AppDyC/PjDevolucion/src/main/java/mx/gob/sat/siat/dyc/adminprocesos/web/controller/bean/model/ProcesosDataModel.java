package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import mx.gob.sat.siat.dyc.domain.adminproceso.DyctTareasDTO;

import org.primefaces.model.SelectableDataModel;


public class ProcesosDataModel extends ListDataModel<DyctTareasDTO> implements SelectableDataModel<DyctTareasDTO> {

    public ProcesosDataModel() {
        super();    
    }
    
    public ProcesosDataModel(List<DyctTareasDTO> listaProcesos) {
        super(listaProcesos);
    }

    @Override
    public Object getRowKey(DyctTareasDTO dyctTareasDTO) {
        return dyctTareasDTO.getDyctProcesosDTO().getIdProceso();
    }

    @Override
    public DyctTareasDTO getRowData(String rowKey) {
        int idProcesoElejido = Integer.valueOf(rowKey);
        
        @SuppressWarnings("unchecked")
        List<DyctTareasDTO> listaPasajeros = (List<DyctTareasDTO>) getWrappedData();

        for (DyctTareasDTO tarea : listaPasajeros) {
            if(tarea.getDyctProcesosDTO().getIdProceso() == idProcesoElejido) {
                return tarea;
            }
        }
        return null;
    }
}
