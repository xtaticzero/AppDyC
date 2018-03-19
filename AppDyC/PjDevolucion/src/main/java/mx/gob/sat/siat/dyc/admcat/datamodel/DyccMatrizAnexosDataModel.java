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

import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;

import org.primefaces.model.SelectableDataModel;


/**
 * @author  Alfredo Ramirez
 * @since   05/09/2013
 */
public class DyccMatrizAnexosDataModel extends ListDataModel<DyccMatrizAnexosDTO> implements SelectableDataModel<DyccMatrizAnexosDTO> {

    public DyccMatrizAnexosDataModel() {
    }

    public DyccMatrizAnexosDataModel(List<DyccMatrizAnexosDTO> data) {
        super(data);
    }

    @Override
    public DyccMatrizAnexosDTO getRowData(String rowKey) {
        List<DyccMatrizAnexosDTO> anexos = (List<DyccMatrizAnexosDTO>)getWrappedData();
        for (DyccMatrizAnexosDTO anexo : anexos) {
            if (String.valueOf(anexo.getIdAnexo()).equals(rowKey)) {
                return anexo;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DyccMatrizAnexosDTO anexo) {
        return anexo.getIdAnexo();
    }

}
