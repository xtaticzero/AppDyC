package mx.gob.sat.siat.dyc.servicio.service.immex.impl;

/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

import mx.gob.sat.siat.dyc.servicio.dao.immex.ImmexDAO;
import mx.gob.sat.siat.dyc.servicio.dto.immex.ImmexDTO;
import mx.gob.sat.siat.dyc.servicio.service.immex.ImmexService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * ConsultarPadronEmpresasCertificadasIMMEXe
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 31 Octubre 2013
 */

@Service(value = "immexService")
public class ImmexServiceImpl implements ImmexService {

    @Autowired
    private ImmexDAO immexDAO;

    private ImmexDTO immex;

    public ImmexServiceImpl() {
        super();
        immex = new ImmexDTO();
    }

    /**
     *
     * @param immexDTO
     * @return
     */
    @Override
    public ImmexDTO obtienenImmexSP(ImmexDTO immexDTO) {
        immex = this.immexDAO.obtieneImmexSP(immexDTO);
        return immex;
    }

    // ACCESSOR'S **************************************

    public void setImmexDAO(ImmexDAO immexDAO) {
        this.immexDAO = immexDAO;
    }

    public ImmexDAO getImmexDAO() {
        return immexDAO;
    }

    public void setImmex(ImmexDTO immex) {
        this.immex = immex;
    }

    public ImmexDTO getImmex() {
        return immex;
    }
}
