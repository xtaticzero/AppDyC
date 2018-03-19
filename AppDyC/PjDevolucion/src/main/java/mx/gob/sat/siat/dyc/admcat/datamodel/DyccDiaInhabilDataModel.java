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

import mx.gob.sat.siat.dyc.domain.DyccDiaInhabilDTO;

import org.primefaces.model.SelectableDataModel;


/**
 * @author  Alfredo Ramirez
 * @since   03/09/2013
 */
public class DyccDiaInhabilDataModel extends ListDataModel<DyccDiaInhabilDTO> implements SelectableDataModel<DyccDiaInhabilDTO> {

    public DyccDiaInhabilDataModel() {
    }

    public DyccDiaInhabilDataModel(List<DyccDiaInhabilDTO> data) {
        super(data);
    }

    @Override
    public DyccDiaInhabilDTO getRowData(String rowKey) {
        List<DyccDiaInhabilDTO> dias = (List<DyccDiaInhabilDTO>)getWrappedData();
        for (DyccDiaInhabilDTO dia : dias) {
            if (String.valueOf(dia.getFecha()).equals(rowKey)) {
                return dia;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DyccDiaInhabilDTO dia) {
        return dia.getFecha();
    }

}
