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

import mx.gob.sat.siat.dyc.admcat.dto.matenercalendariodictaminador.MantenerCalendarioIndividualDTO;

import org.primefaces.model.SelectableDataModel;


/**
 * @author  Alfredo Ramirez
 * @since   11/09/2013
 */
public class MantenerCalendarioGeneralDataModel extends ListDataModel<MantenerCalendarioIndividualDTO> implements SelectableDataModel<MantenerCalendarioIndividualDTO> {

    public MantenerCalendarioGeneralDataModel() {
    }

    public MantenerCalendarioGeneralDataModel(List<MantenerCalendarioIndividualDTO> data) {
        super(data);
    }

    @Override
    public MantenerCalendarioIndividualDTO getRowData(String rowKey) {
        List<MantenerCalendarioIndividualDTO> calendarios = (List<MantenerCalendarioIndividualDTO>)getWrappedData();
        for (MantenerCalendarioIndividualDTO calendario : calendarios) {
            if (String.valueOf(calendario.getNumCalendario()).equals(rowKey)) {
                return calendario;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(MantenerCalendarioIndividualDTO calendario) {
        return calendario.getNumCalendario();
    }

}
