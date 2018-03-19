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

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.primefaces.model.SelectableDataModel;


/**
 * @since   06/09/2013
 */
public class TramiteDataModel extends ListDataModel<DyccTipoTramiteDTO> implements SelectableDataModel<DyccTipoTramiteDTO> {

    public TramiteDataModel() {
    }

    public TramiteDataModel(List<DyccTipoTramiteDTO> data) {
        super(data);
    }

    @Override
    public DyccTipoTramiteDTO getRowData(String rowKey) {

        List<DyccTipoTramiteDTO> tramites = (List<DyccTipoTramiteDTO>)getWrappedData();

        for (DyccTipoTramiteDTO tramite : tramites) {
            if (String.valueOf(tramite.getIdTipoTramite()).equals(rowKey)) {
                return tramite;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DyccTipoTramiteDTO tramite) {
        return tramite.getIdTipoTramite();
    }

}
