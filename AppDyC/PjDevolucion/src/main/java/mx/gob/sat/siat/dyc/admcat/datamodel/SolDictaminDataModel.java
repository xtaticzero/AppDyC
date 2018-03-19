/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.admcat.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;

import org.primefaces.model.SelectableDataModel;


/**
 * @since   06/09/2013
 */
public class SolDictaminDataModel extends ListDataModel<DictaminadorSolBean> implements SelectableDataModel<DictaminadorSolBean> {

    public SolDictaminDataModel(List<DictaminadorSolBean> data) {
        super(data);
    }

    @Override
    public DictaminadorSolBean getRowData(String rowKey) {

        List<DictaminadorSolBean> solicitudes = (List<DictaminadorSolBean>)getWrappedData();

        for (DictaminadorSolBean solicitud : solicitudes) {
            if (solicitud.getNumControl().equals(rowKey)) {
                return solicitud;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DictaminadorSolBean solicitud) {
        return solicitud.getNumControl();
    }

}

