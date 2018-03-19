package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridPapelesTrabajoBean;

import org.primefaces.model.SelectableDataModel;


public class PapelesTrabajoDataModel  extends ListDataModel<FilaGridPapelesTrabajoBean> implements SelectableDataModel<FilaGridPapelesTrabajoBean>
{
    public PapelesTrabajoDataModel() {
        super();
    }

    public PapelesTrabajoDataModel(List<FilaGridPapelesTrabajoBean> papelesTrabajo)
    {
        super(papelesTrabajo);
    }

    @Override
    public Object getRowKey(FilaGridPapelesTrabajoBean filaGridPapelesTrabajoBean) {
        return filaGridPapelesTrabajoBean.getIdPapelTrabajo();
    }

    @Override
    public FilaGridPapelesTrabajoBean getRowData(String rowKey)
    {
        List<FilaGridPapelesTrabajoBean> filas = (List<FilaGridPapelesTrabajoBean>)getWrappedData();
        for (FilaGridPapelesTrabajoBean fila : filas)
        {
            long id = Long.parseLong(rowKey);
            if (fila.getIdPapelTrabajo().longValue() == id) {
                return fila;
            }
        }
        return null;
    }
}
