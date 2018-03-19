package mx.gob.sat.siat.dyc.generico.util.common;

import java.io.Serializable;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;


public class SIATDataModel<T> extends ListDataModel<T> implements SelectableDataModel<T>, Serializable {


    @SuppressWarnings ("compatibility:7048465838073592567")
    private static final long serialVersionUID = 5364722060351708092L;

    public SIATDataModel() {
        super();
    }

    @Override
    public T getRowData(String arg0) {

        @SuppressWarnings("unchecked")
        List<T> rows = (List<T>)getWrappedData();
        for (T row : rows) {
            if (row.toString().equals(arg0)) {
                return row;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(T arg0) {
        return arg0;
    }

}
