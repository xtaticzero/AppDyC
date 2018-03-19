package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridBusquedaIceps;

import org.apache.log4j.Logger;

import org.primefaces.model.SelectableDataModel;


public class GridBusquedaIcepsDataModel extends ListDataModel<FilaGridBusquedaIceps> implements SelectableDataModel<FilaGridBusquedaIceps>
{
    private static final Logger LOG = Logger.getLogger(GridBusquedaIcepsDataModel.class);

    public GridBusquedaIcepsDataModel() {
        super();
    }

    public GridBusquedaIcepsDataModel(List<FilaGridBusquedaIceps> iceps) {
        super(iceps);
    }

    @Override
    public Object getRowKey(FilaGridBusquedaIceps filaIcep) {
        return filaIcep.getIdSaldoIcep();
    }

    @Override
    public FilaGridBusquedaIceps getRowData(String rowKey)
    {
        LOG.debug("rowKey ->" + rowKey + "<-");
        if("".equals(rowKey)){
            return ((List<FilaGridBusquedaIceps>)getWrappedData()).get(0);
        }
        for (FilaGridBusquedaIceps fila : (List<FilaGridBusquedaIceps>)getWrappedData())
        {
            String idAux = fila.getIdSaldoIcep().toString();
            if (idAux.equals(rowKey)) {
                return fila;
            }
        }
        return null;
    }

}
