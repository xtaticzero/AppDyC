package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridFacturasProvBean;

import org.primefaces.model.SelectableDataModel;


public class FacturaDataModel extends ListDataModel<FilaGridFacturasProvBean> implements SelectableDataModel<FilaGridFacturasProvBean> {
    public FacturaDataModel() {
        super();
    }

    public FacturaDataModel(List<FilaGridFacturasProvBean> facturas) {
        super(facturas);
    }


    @Override
    public Object getRowKey(FilaGridFacturasProvBean fila) {
        return fila.getNumFactura();
    }

    @Override
    public FilaGridFacturasProvBean getRowData(String numeroFactura) {
        List<FilaGridFacturasProvBean> filas = (List<FilaGridFacturasProvBean>)getWrappedData();
        for (FilaGridFacturasProvBean fila : filas) {
            if (fila.getNumFactura().equals(numeroFactura)) {
                return fila;
            }
        }
        return null;
    }
}
