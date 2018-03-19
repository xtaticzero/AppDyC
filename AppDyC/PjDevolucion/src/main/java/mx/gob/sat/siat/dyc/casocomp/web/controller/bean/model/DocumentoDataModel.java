package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridDocumentosBean;

import org.primefaces.model.SelectableDataModel;


public class DocumentoDataModel extends ListDataModel<FilaGridDocumentosBean> implements SelectableDataModel<FilaGridDocumentosBean>
{
    public DocumentoDataModel() {
        super();
    }

    public DocumentoDataModel(List<FilaGridDocumentosBean> documentos) {
        super(documentos);
    }

    @Override
    public Object getRowKey(FilaGridDocumentosBean filaGridDocumentos) {
        return filaGridDocumentos.getNumControlDoc();
    }

    @Override
    public FilaGridDocumentosBean getRowData(String rowKey)
    {
        List<FilaGridDocumentosBean> filas = (List<FilaGridDocumentosBean>)getWrappedData();
        for (FilaGridDocumentosBean fila : filas) {
            if (fila.getNumControlDoc().equals(rowKey)) {
                return fila;
            }
        }
        return null;
    }
}
