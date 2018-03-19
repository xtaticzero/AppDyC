package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridCasosCompBean;

import org.primefaces.model.SelectableDataModel;


public class CasoCompensacionDataModel extends ListDataModel<FilaGridCasosCompBean> implements SelectableDataModel<FilaGridCasosCompBean> {
    public CasoCompensacionDataModel() {
        super();
    }

    public CasoCompensacionDataModel(List<FilaGridCasosCompBean> casosCompensacion) {
        super(casosCompensacion);
    }

    @Override
    public Object getRowKey(FilaGridCasosCompBean filaGridCasosCompBean) {
        return filaGridCasosCompBean.getNumeroControl();
    }

    @Override
    public FilaGridCasosCompBean getRowData(String rowKey) {
        List<FilaGridCasosCompBean> filas = (List<FilaGridCasosCompBean>)getWrappedData();
        for (FilaGridCasosCompBean fila : filas) {
            if (fila.getNumeroControl().equals(rowKey)) {
                return fila;
            }
        }
        return null;
    }
}