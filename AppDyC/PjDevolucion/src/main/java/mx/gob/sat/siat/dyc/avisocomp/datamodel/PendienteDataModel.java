/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.avisocomp.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import mx.gob.sat.siat.dyc.avisocomp.vo.PendienteVO;

import org.primefaces.model.SelectableDataModel;


/**
 * @author  Alfredo Ramirez
 * @since   31/07/2013
 */
public class PendienteDataModel extends ListDataModel<PendienteVO> implements SelectableDataModel<PendienteVO> {

    public PendienteDataModel() {
    }

    public PendienteDataModel(List<PendienteVO> data) {
        super(data);
    }

    @Override
    public PendienteVO getRowData(String rowKey) {
        List<PendienteVO> avisos = (List<PendienteVO>)getWrappedData();
        for (PendienteVO aviso : avisos) {
            if (String.valueOf(aviso.getPeriodo()).equals(rowKey)) {
                return aviso;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(PendienteVO aviso) {
        return aviso.getPeriodo();
    }
}
