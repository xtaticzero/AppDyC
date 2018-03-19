package mx.gob.sat.siat.dyc.registro.service.solicitud.impl;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSolRfcControlDTO;
import mx.gob.sat.siat.dyc.registro.dao.solicitud.RegistroConsultaDAO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistroConsultaService;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


/**
 * Registro Consulta ServiceImpl
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 ,
 */
@Service(value = "registroConsultaService")
public class RegistroConsultaServiceImpl implements RegistroConsultaService
{
    private static final Logger LOG = Logger.getLogger(RegistroConsultaServiceImpl.class.getName());

    @Autowired
    private RegistroConsultaDAO registroConsultaDAO;

    /**
     *
     * @param numcontrol
     * @return Regresa una lista de tipo TramiteDTO
     */
    @Override
    @Transactional(readOnly = true)
    public ParamOutputTO<String> getAllRfcControlador(String numcontrol) throws SIATException {
        return new ParamOutputTO<String>(registroConsultaDAO.getAllRfcControlador(numcontrol));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createRfcControlador(DyctSolRfcControlDTO dyccSolRfcControlDTO) throws SIATException {
        LOG.info("INIT REGISTRAR RFCs CONTROLADOR");
        registroConsultaDAO.createControlador(dyccSolRfcControlDTO);
        LOG.info("END REGISTRAR RFCs CONTROLADOR");
    }


}
