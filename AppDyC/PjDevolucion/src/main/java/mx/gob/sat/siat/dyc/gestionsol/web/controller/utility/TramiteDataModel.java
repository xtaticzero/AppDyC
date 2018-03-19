package mx.gob.sat.siat.dyc.gestionsol.web.controller.utility;

import mx.gob.sat.siat.dyc.domain.resolucion.FilaGridTramitesBean;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;


public class TramiteDataModel extends ListDataModel<FilaGridTramitesBean> implements SelectableDataModel<FilaGridTramitesBean>
{
    public TramiteDataModel() {
        super();
    }

    public TramiteDataModel(List<FilaGridTramitesBean> casosCompensacion) {
        super(casosCompensacion);
    }

    @Override
    public Object getRowKey(FilaGridTramitesBean filaGridCasosCompBean) {
        return filaGridCasosCompBean.getNumControl();
    }

    @Override
    public FilaGridTramitesBean getRowData(String rowKey) {
        List<FilaGridTramitesBean> filas = (List<FilaGridTramitesBean>)getWrappedData();
        for (FilaGridTramitesBean fila : filas) {
            if (fila.getNumControl().equals(rowKey)) {
                return fila;
            }
        }
        return null;
    }
}
