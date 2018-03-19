package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridUsuariosLoginAuxBean;

import org.primefaces.model.SelectableDataModel;


public class UsuariosAuxDataModel extends ListDataModel<FilaGridUsuariosLoginAuxBean> implements SelectableDataModel<FilaGridUsuariosLoginAuxBean>
{
    public UsuariosAuxDataModel() {
        super();
    }

    public UsuariosAuxDataModel(List<FilaGridUsuariosLoginAuxBean> casosCompensacion) {
        super(casosCompensacion);
    }

    @Override
    public Object getRowKey(FilaGridUsuariosLoginAuxBean filaGridCasosCompBean) {
        return filaGridCasosCompBean.getNumEmpleado();
    }

    @Override
    public FilaGridUsuariosLoginAuxBean getRowData(String rowKey) {
        List<FilaGridUsuariosLoginAuxBean> filas = (List<FilaGridUsuariosLoginAuxBean>)getWrappedData();
        for (FilaGridUsuariosLoginAuxBean fila : filas) {
            if (fila.getNumEmpleado().equals(rowKey)) {
                return fila;
            }
        }
        return null;
    }
}
