/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.service.DyccImpuestoService;
import mx.gob.sat.siat.dyc.dao.concepto.DyccImpuestoDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Paola Rivera
 */
@Service(value = "dyccImpuestoService")
public class DyccImpuestoServiceImpl implements DyccImpuestoService {

    @Autowired
    private DyccImpuestoDAO dyccImpuestoDAO;

    @Override
    public DyccImpuestoDTO obtieneImpuestoPorIdTramite(long idTipoTramite) {
        return dyccImpuestoDAO.obtieneImpuestoPorTramite(idTipoTramite);
    }
    
    @Override
    public DyccImpuestoDTO encontrar(Integer id) {
        return dyccImpuestoDAO.encontrar(id);
    }

    @Override
    public List<DyccImpuestoDTO> obtieneImpuestos() {
        return dyccImpuestoDAO.obtieneImpuestos();
    }

    @Override
    public List<DyccImpuestoDTO> obtieneImpuestos(int idImpuesto) {
        return dyccImpuestoDAO.obtieneImpuestos(idImpuesto);
    }

    @Override
    public DyccImpuestoDTO impuestoXConcepto(DyccConceptoDTO concepto) {
        return dyccImpuestoDAO.seleccionarXconcepto(concepto);
    }
}
