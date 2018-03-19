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

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;

import org.primefaces.model.SelectableDataModel;


/**
 * @author  Alfredo Ramirez
 * @since   06/09/2013
 */
public class RolDataModel extends ListDataModel<DyccRolDTO> implements SelectableDataModel<DyccRolDTO> {

    public RolDataModel() {
    }

    public RolDataModel(List<DyccRolDTO> data) {
        super(data);
    }

    @Override
    public DyccRolDTO getRowData(String rowKey) {

        List<DyccRolDTO> roles = (List<DyccRolDTO>)getWrappedData();

        for (DyccRolDTO rol : roles) {
            if (String.valueOf(rol.getIdRol()).equals(rowKey)) {
                return rol;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(DyccRolDTO rol) {
        return rol.getIdRol();
    }

}
